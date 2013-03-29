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
import br.com.concepting.framework.persistence.util.PersistenceUtil;
import br.com.concepting.framework.util.MethodUtil;
import br.com.concepting.framework.util.types.TransactionType;

/**
 * Classe que define a estrutura básica para as classes de persistência de modelos de dados.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class BaseDAO implements IDAO{
    private PersistenceResource persistenceResource = null;
    private Object              connection          = null;
    private Object              transaction         = null;
    private TransactionType     transactionType     = null;
    private Integer             transactionTimeout  = null;

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
	public BaseDAO(IDAO dao){
	    this();
	
	    if(dao != null){
	        setConnection(dao.getConnection());
	        setPersistenceResource(dao.getPersistenceResource());
	        setTransactionType(dao.getTransactionType());
	        setTransactionTimeout(dao.getTransactionTimeout());
	    }
	    
	    setTransaction(null);
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.IDAO#getTransactionTimeout()
	 */
    public Integer getTransactionTimeout(){
        return transactionTimeout;
    }

    /**
     * @see br.com.concepting.framework.persistence.interfaces.IDAO#setTransactionTimeout(java.lang.Integer)
     */
    public void setTransactionTimeout(Integer transactionTimeout){
        this.transactionTimeout = transactionTimeout;
    }

    /**
	 * @see br.com.concepting.framework.persistence.interfaces.IDAO#getTransactionType()
	 */
	public TransactionType getTransactionType(){
	    return transactionType;
    }

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.IDAO#setTransactionType(br.com.concepting.framework.service.types.TransactionType)
	 */
    public void setTransactionType(TransactionType transactionType){
        this.transactionType = transactionType;
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
	 * @see br.com.concepting.framework.persistence.interfaces.IDAO#getConnection()
	 */
    public <C> C getConnection(){
        return (C)connection;
	}

    /**
     * @see br.com.concepting.framework.persistence.interfaces.IDAO#setConnection(java.lang.Object)
     */
	public <C> void setConnection(C connection){
	    this.connection = connection;
	}
	
	/**
	 * @see br.com.concepting.framework.persistence.interfaces.IDAO#getTransaction()
	 */
	public <T> T getTransaction(){
	    return (T)transaction;
	}
	
	/**
	 * @see br.com.concepting.framework.persistence.interfaces.IDAO#setTransaction(java.lang.Object)
	 */
	public <T> void setTransaction(T transaction){
	    this.transaction = transaction;
	}

	/**
	 * Retorna a query vinculada ao método.
	 * A query é definida na anotação Query.
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
	 * Retorna a instância de uma classe de persistência a partir do seu modelo de dados.
	 * 
	 * @param modelClass Classe do modelo de dados desejado.
	 * @return Instância da classe de persistência desejada.
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
}