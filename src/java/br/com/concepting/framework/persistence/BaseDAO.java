package br.com.concepting.framework.persistence;

import java.util.Collection;

import org.apache.commons.beanutils.ConstructorUtils;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.exceptions.ItemAlreadyExistsException;
import br.com.concepting.framework.model.exceptions.ItemNotFoundException;
import br.com.concepting.framework.model.helpers.ModelFilter;
import br.com.concepting.framework.persistence.interfaces.IDAO;
import br.com.concepting.framework.persistence.resource.PersistenceResource;
import br.com.concepting.framework.persistence.util.PersistenceUtil;

/**
 * Classe que define a estrutura básica para as classes de persistência de modelos de dados.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class BaseDAO<C, T> implements IDAO{
    private PersistenceResource persistenceResource = null;
    private C                   connection          = null;
    private T                   transaction         = null;
    private Integer             timeout             = null;

	/**
	 * Construtor - Inicializa a classe de persistência.
	 */
	public BaseDAO(){
		super();
	}
	
    /**
     * Construtor - Inicializa a classe de persistência.
     * 
     * @param dao Instância da classe de persistência a ser encapsulada.
     */
	public <D extends BaseDAO<C, T>> BaseDAO(D dao){
	    this();
	
	    if(dao != null){
	        setConnection(dao.getConnection());
	        setPersistenceResource(dao.getPersistenceResource());
	        setTimeout(dao.getTimeout());
	    }
	    
	    setTransaction(null);
	}
	
	/**
	 * @see br.com.concepting.framework.persistence.interfaces.IDAO#getTimeout()
	 */
    public Integer getTimeout(){
        return timeout;
    }

    /**
     * @see br.com.concepting.framework.persistence.interfaces.IDAO#setTimeout(java.lang.Integer)
     */
    public void setTimeout(Integer timeout){
        this.timeout = timeout;
    }

    /**
     * @see br.com.concepting.framework.persistence.interfaces.IDAO#setPersistenceResource(br.com.concepting.framework.persistence.resource.PersistenceResource)
     */
    public void setPersistenceResource(PersistenceResource persistenceResource){
       this.persistenceResource = persistenceResource;
    }

    /**
     * @see br.com.concepting.framework.persistence.interfaces.IDAO#getPersistenceResource()
     */
    public PersistenceResource getPersistenceResource(){
        return persistenceResource;
    }
    
    /**
     * 
     * @return
     * @throws InternalErrorException
     */
    protected C openConnection() throws InternalErrorException{
        return connection;
    }
    
    /**
     * 
     */
    protected void closeConnection(){
    }

    /**
     * 
     * @return
     */
    protected C getConnection(){
        return connection;
	}

    /**
     * 
     * @param connection
     */
	protected void setConnection(C connection){
	    this.connection = connection;
	}
	
	/**
	 * 
	 * @return
	 */
	protected T getTransaction(){
	    return transaction;
	}
	
	/**
	 * 
	 * @param transaction
	 */
	protected void setTransaction(T transaction){
	    this.transaction = transaction;
	}

	/**
	 * Retorna a instância de uma classe de persistência a partir do seu modelo de dados.
	 * 
	 * @param modelClass Classe do modelo de dados desejado.
	 * @return Instância da classe de persistência desejada.
	 * @throws InternalErrorException
	 */
    protected <D extends BaseDAO<C, T>> D getPersistence(Class<?> modelClass) throws InternalErrorException{
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
     * @see br.com.concepting.framework.persistence.interfaces.IDAO#initialize()
     */
    public void initialize() throws InternalErrorException{
        openConnection();
    }

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#list()
	 */
	public <M extends BaseModel, L extends Collection<M>> L list() throws InternalErrorException{
		return null;
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#search(br.com.concepting.framework.model.BaseModel)
	 */
	public <M extends BaseModel, L extends Collection<M>> L search(M model) throws InternalErrorException{
		return null;
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#search(br.com.concepting.framework.model.BaseModel, br.com.concepting.framework.model.helpers.ModelFilter)
	 */
	public <M extends BaseModel, L extends Collection<M>> L search(M model, ModelFilter modelFilter) throws InternalErrorException{
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
}