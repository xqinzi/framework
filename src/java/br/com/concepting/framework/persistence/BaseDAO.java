package br.com.concepting.framework.persistence;

import java.lang.reflect.Method;
import java.util.Collection;

import org.apache.commons.beanutils.ConstructorUtils;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.exceptions.ItemAlreadyExistsException;
import br.com.concepting.framework.model.exceptions.ItemNotFoundException;
import br.com.concepting.framework.persistence.annotations.Query;
import br.com.concepting.framework.persistence.helpers.ModelFilter;
import br.com.concepting.framework.persistence.interfaces.IDAO;
import br.com.concepting.framework.persistence.resource.PersistenceResource;
import br.com.concepting.framework.persistence.resource.PersistenceResourceLoader;
import br.com.concepting.framework.persistence.util.PersistenceUtil;
import br.com.concepting.framework.util.MethodUtil;
import br.com.concepting.framework.util.StringUtil;

/**
 * Classe que define a estrutura b�sica para as classes de persist�ncia de modelos de dados.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class BaseDAO implements IDAO{
	private Object              transaction         = null;
	private Object              connection          = null;
	private PersistenceResource persistenceResource = null;
	private BaseDAO             currentPersistence  = null;
	private Boolean             useTransaction      = true;

	/**
	 * Construtor - Inicializa a persist�ncia.
	 */
	public BaseDAO(){
		super();
	}

	/**
	 * Construtor - Inicializa a persist�ncia utilizando uma outra conex�o j� utilizada.
	 * 
	 * @param currentPersistence Inst�ncia da persist�ncia desejada.
	 */
	public BaseDAO(BaseDAO currentPersistence){
		this();

		if(currentPersistence != null){
		    setCurrentPersistence(currentPersistence);
			setUseTransaction(false);
		}
	}

	/**
	 * Construtor - Inicializa classe de persist�ncia considerando uma transa��o j� iniciada.
	 * 
	 * @param useTransaction Indica se ir� usar transa��o.
	 */
	public BaseDAO(Boolean useTransaction){
		this();

		setUseTransaction(useTransaction);
	}

	/**
	 * Indica se deve-se usar o controle transacional.
	 * 
	 * @return True/False.
	 */
	protected Boolean useTransaction(){
		return useTransaction;
	}
	
    /**
     * Define se deve-se usar o controle transacional.
     * 
     * @param useTransaction True/False.
     */
	protected void setUseTransaction(Boolean useTransaction){
	    this.useTransaction = useTransaction;
	}
	
	/**
	 * Retorna a inst�ncia da transa��o.
	 *
	 * @return Inst�ncia da transa��o.
	 */
    protected <O> O getTransaction(){
    	return (O)transaction;
    }

	/**
	 * Define a inst�ncia da transa��o.
	 *
	 * @param transaction Inst�ncia da transa��o.
	 */
	protected <O> void setTransaction(O transaction){
    	this.transaction = transaction;
    }

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.IDAO#openConnection(java.lang.String)
	 */
    public <C> C openConnection(String persistenceResourceId) throws InternalErrorException{
		PersistenceResourceLoader persistenceResourceLoader = new PersistenceResourceLoader();
		PersistenceResource       persistenceResource       = null;

		if(StringUtil.trim(persistenceResourceId).length() > 0)
			persistenceResource = persistenceResourceLoader.get(persistenceResourceId);
		else
			persistenceResource = persistenceResourceLoader.getDefault();

		return openConnection(persistenceResource);
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.IDAO#openConnection(br.com.concepting.framework.persistence.resource.PersistenceResource)
	 */
    public <C> C openConnection(PersistenceResource persistenceResource) throws InternalErrorException{
        return getConnection();
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.IDAO#openConnection()
	 */
    public <C> C openConnection() throws InternalErrorException{
        return getConnection();
	}

    /**
     * Retorna a inst�ncia da conex�o com o reposit�rio de persist�ncia.
     * 
     * @return Inst�ncia da conex�o contendo o reposit�rio de persist�ncia.
     */
    protected <C> C getConnection(){
		if(currentPersistence != null)
			return (C)currentPersistence.getConnection();
		
		return (C)connection;
	}

	/**
	 * Define a inst�ncia da conex�o com o reposit�rio de persist�ncia.
	 * 
	 * @param connection Inst�ncia da conex�o com o reposit�rio.
	 */
	protected <C> void setConnection(C connection){
		if(currentPersistence != null)
			currentPersistence.setConnection(connection);
		else	
			this.connection = connection;
	}

	/**
	 * Retorna a inst�ncia da classe de persist�ncia atual.
	 *
	 * @return Inst�ncia da classe de persist�ncia.
	 */
	public PersistenceResource getPersistenceResource(){
		if(currentPersistence != null)
			return currentPersistence.getPersistenceResource();
		
    	return persistenceResource;
    }

	/**
	 * Define a inst�ncia da classe de persist�ncia atual.
	 *
	 * @param persistenceResource Inst�ncia da classe de persist�ncia.
	 */
	public void setPersistenceResource(PersistenceResource persistenceResource){
		if(currentPersistence != null)
			currentPersistence.setPersistenceResource(persistenceResource);
		else
			this.persistenceResource = persistenceResource;
    }

	/**
	 * Retorna a inst�ncia da classe de persist�ncia atual.
	 *
	 * @return Inst�ncia da classe de persist�ncia.
	 */
    protected <D extends BaseDAO> D getCurrentPersistence(){
    	return (D)currentPersistence;
    }

	/**
	 * Define a inst�ncia da classe de persist�ncia atual.
	 *
	 * @param currentPersistence Inst�ncia da classe de persist�ncia.
	 */
	protected <D extends BaseDAO> void setCurrentPersistence(D currentPersistence){
    	this.currentPersistence = currentPersistence;
    }

	/**
	 * Retorna a query vinculada ao m�todo.
	 * A query � definida na anota��o Query.
	 * 
	 * @return String contendo a query.
	 * @throws InternalErrorException
	 */
	protected String getQueryStatement() throws InternalErrorException{
		try{
    		Method method         = MethodUtil.getMethodFromStackTrace(2);
    		Query  annotation     = method.getAnnotation(Query.class);
    		String queryStatement = "";
    
    		if(annotation != null)
    			queryStatement = annotation.value();
    
    		return queryStatement;
		}
		catch(Throwable e){
			throw new InternalErrorException(e);
		}
	}

	/**
	 * Retorna a inst�ncia de uma classe de persist�ncia a partir do seu modelo de dados.
	 * 
	 * @param modelClass Classe do modelo de dados desejado.
	 * @return Inst�ncia da classe de persist�ncia desejada.
	 * @throws InternalErrorException
	 */
    protected <D extends IDAO, M extends BaseModel> D getPersistence(Class<M> modelClass) throws InternalErrorException{
		try{
			Class<D> persistenceClass    = PersistenceUtil.getPersistenceClassByModel(modelClass);
			D        persistenceInstance = (D)ConstructorUtils.invokeConstructor(persistenceClass, this);

			return persistenceInstance;
		}
		catch(Throwable e){
			throw new InternalErrorException(e);
		}
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#list()
	 */
	public <C extends Collection> C list() throws InternalErrorException{
		return null;
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#search(br.com.concepting.framework.model.BaseModel)
	 */
	public <M extends BaseModel, C extends Collection> C search(M model) throws InternalErrorException{
		return null;
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#search(br.com.concepting.framework.model.BaseModel, br.com.concepting.framework.persistence.helpers.ModelFilter)
	 */
	public <M extends BaseModel, C extends Collection> C search(M model, ModelFilter modelFilter) throws InternalErrorException{
		return null;
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#find(br.com.concepting.framework.model.BaseModel)
	 */
	public <M extends BaseModel> M find(M model) throws ItemNotFoundException, InternalErrorException{
		return model;
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#delete(br.com.concepting.framework.model.BaseModel)
	 */
	public <M extends BaseModel> void delete(M model) throws InternalErrorException{
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#deleteAll(java.util.Collection)
	 */
	public <M extends BaseModel> void deleteAll(Collection<M> modelList) throws InternalErrorException{
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#deleteAll()
	 */
	public <M extends BaseModel> void deleteAll() throws InternalErrorException{
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#loadReference(br.com.concepting.framework.model.BaseModel, java.lang.String)
	 */
	public <M extends BaseModel> M loadReference(M model, String referencePropertyId) throws InternalErrorException{
		return model;
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#saveReference(br.com.concepting.framework.model.BaseModel, java.lang.String)
	 */
	public <M extends BaseModel> void saveReference(M model, String referencePropertyId) throws InternalErrorException{
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#save(br.com.concepting.framework.model.BaseModel)
	 */
	public <M extends BaseModel> void save(M model) throws ItemAlreadyExistsException, InternalErrorException {
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#saveAll(java.util.Collection)
	 */
	public <M extends BaseModel> void saveAll(Collection<M> modelList) throws ItemAlreadyExistsException, InternalErrorException {
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#saveAll(java.util.Collection)
	 */
	public <M extends BaseModel> void insertAll(Collection<M> modelList) throws ItemAlreadyExistsException, InternalErrorException {
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#saveAll(java.util.Collection)
	 */
	public <M extends BaseModel> void updateAll(Collection<M> modelList) throws InternalErrorException {
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#insert(br.com.concepting.framework.model.BaseModel)
	 */
	public <M extends BaseModel> void insert(M model) throws ItemAlreadyExistsException, InternalErrorException {
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#update(br.com.concepting.framework.model.BaseModel)
	 */
	public <M extends BaseModel> void update(M model) throws InternalErrorException {
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.IDAO#closeConnection()
	 */
	public void closeConnection(){
		setConnection(null);
		setTransaction(null);
		setCurrentPersistence(null);
    }
	
	/**
	 * @see br.com.concepting.framework.persistence.interfaces.IDAO#begin()
	 */
	public void begin() throws InternalErrorException{
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.IDAO#commit()
	 */
	public void commit() throws InternalErrorException{
		closeConnection();
    }

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.IDAO#rollback()
	 */
	public void rollback() throws InternalErrorException{
		closeConnection();
    }
}