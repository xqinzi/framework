package br.com.concepting.framework.persistence;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.LockOptions;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.ObjectDeletedException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.StaleStateException;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.exceptions.ItemAlreadyExistsException;
import br.com.concepting.framework.model.exceptions.ItemNotFoundException;
import br.com.concepting.framework.model.helpers.ModelFilter;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.persistence.interfaces.IDAO;
import br.com.concepting.framework.persistence.resource.PersistenceResource;
import br.com.concepting.framework.persistence.types.QueryType;
import br.com.concepting.framework.persistence.types.RelationType;
import br.com.concepting.framework.persistence.util.HibernateQueryBuilder;
import br.com.concepting.framework.persistence.util.HibernateUtil;

/**
 * Classe que define a estrutura básica para as classes de persistência de modelos de dados 
 * utilizando Hibernate.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class HibernateDAO extends BaseDAO{
    /**
     * Construtor - Inicializa a classe de persistência.
     */
	public HibernateDAO(){
		super();
	}
	
    /**
     * Construtor - Inicializa a classe de persistência.
     * 
     * @param dao Instância da classe de persistência a ser encapsulada.
     */
    public HibernateDAO(IDAO dao){
        super(dao);
    }
    
    /**
     * Reconecta o objeto com o repositório de persistência.
     * 
     * @param model Instância do modelo de dados.
     */
    private <M extends BaseModel> void reattachModel(M model){
        Session connection = getConnection();
        
        try{
            connection.buildLockRequest(LockOptions.NONE).lock(model);
        }
        catch(Throwable e){
        }
    }
    
    /**
     * @see br.com.concepting.framework.persistence.interfaces.IDAO#begin()
     */
	public void begin() throws InternalErrorException{
	    try{
    	    Session     connection  = openConnection();
    	    Transaction transaction = connection.getTransaction();
    	    
    	    if(transaction != null){
    	        Integer transactionTimeout = getTransactionTimeout();
    	        
    	        try{
        	        if(transactionTimeout != null && transactionTimeout > 0)
        	            transaction.setTimeout(getTransactionTimeout());
    	        }
    	        catch(Throwable e){
    	        }
    	        
    	        transaction.begin();
    	    
    	        setTransaction(transaction);
    	    }
	    }
	    catch(Throwable e){
	        throw new InternalErrorException(e);
	    }
    }

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.IDAO#commit()
	 */
    public void commit() throws InternalErrorException{
        try{
            Transaction transaction = getTransaction();

            if(transaction != null)
                transaction.commit();
        }
        catch(Throwable e){
            throw new InternalErrorException(e);
        }
        finally{
            setTransaction(null);
            
            closeConnection();
        }
    }

    /**
     * @see br.com.concepting.framework.persistence.interfaces.IDAO#rollback()
     */
    public void rollback() throws InternalErrorException{
        try{
            Transaction transaction = getTransaction();

            if(transaction != null)
                transaction.commit();
        }
        catch(Throwable e){
            throw new InternalErrorException(e);
        }
        finally{
            setTransaction(null);
            
            closeConnection();
        }
    }

    /**
     * @see br.com.concepting.framework.persistence.interfaces.IDAO#openConnection()
     */
    public <C> C openConnection() throws InternalErrorException{
        Session connection = getConnection();
        
        try{
            if(connection == null || !connection.isOpen()){
                PersistenceResource persistenceRespurce = getPersistenceResource();
                
                connection = HibernateUtil.getSession(persistenceRespurce);
                
                setConnection(connection);
            }
            
            return (C)connection;
        }
        catch(Throwable e){
            throw new InternalErrorException(e);
        }
    }

    /**
     * @see br.com.concepting.framework.persistence.interfaces.IDAO#closeConnection()
     */
    public void closeConnection(){
        try{
            Session connection = getConnection();
            
            connection.close();
        }
        catch(Throwable e){
        }
    }

	/**
	 * @see br.com.concepting.framework.persistence.BaseDAO#delete(br.com.concepting.framework.model.BaseModel)
	 */
	public <M extends BaseModel> void delete(M model) throws InternalErrorException{
	    if(model == null)
	        return;

        try{
            Query query = HibernateQueryBuilder.build(QueryType.DELETE, model, this);
        
            query.executeUpdate();
		}
		catch(ObjectNotFoundException e){
		}
		catch(ObjectDeletedException e){
		}
        catch(StaleStateException e){
        }
		catch(HibernateException e){
			throw new InternalErrorException(e);
		}
	}

	/**
	 * @see br.com.concepting.framework.persistence.BaseDAO#find(br.com.concepting.framework.model.BaseModel)
	 */
    public <M extends BaseModel> M find(M model) throws InternalErrorException, ItemNotFoundException{
        if(model == null)
            throw new ItemNotFoundException();

        try{
			Query query       = HibernateQueryBuilder.build(QueryType.FIND, model, this);
			M     queryResult = (M)query.uniqueResult();

			if(queryResult == null)
				throw new ItemNotFoundException();

			return queryResult;
		}
		catch(ObjectNotFoundException e){
			throw new ItemNotFoundException();
		}
		catch(NonUniqueResultException e){
			throw new ItemNotFoundException();
		}
		catch(HibernateException e){
			throw new InternalErrorException(e);
		}
	}

	/**
	 * @see br.com.concepting.framework.persistence.BaseDAO#list()
	 */
    public <C extends Collection> C list() throws InternalErrorException{
		return (C)search(null);
	}

	/**
	 * @see br.com.concepting.framework.persistence.BaseDAO#search(br.com.concepting.framework.model.BaseModel)
	 */
    public <M extends BaseModel, C extends Collection> C search(M model) throws InternalErrorException{
		return (C)search(model, null);
	}

    /**
     * @see br.com.concepting.framework.persistence.BaseDAO#search(br.com.concepting.framework.model.BaseModel, br.com.concepting.framework.model.helpers.ModelFilter)
     */
    public <M extends BaseModel, C extends Collection> C search(M model, ModelFilter modelFilter) throws InternalErrorException{
		try{
			Query   query     = HibernateQueryBuilder.build(QueryType.SEARCH, model, modelFilter, this);
			List<M> modelList = query.list();
			
			modelList = ModelUtil.filterByPhonetic(model, modelList);

			return (C)modelList;
		}
        catch(IllegalAccessException e){
            throw new InternalErrorException(e);
        }
        catch(InvocationTargetException e){
            throw new InternalErrorException(e);
        }
        catch(NoSuchMethodException e){
            throw new InternalErrorException(e);
        }
		catch(HibernateException e){
            throw new InternalErrorException(e);
		}
	}

	/**
	 * @see br.com.concepting.framework.persistence.BaseDAO#loadReference(br.com.concepting.framework.model.BaseModel, java.lang.String)
	 */	
	public <M extends BaseModel> M loadReference(M model, String referencePropertyId) throws InternalErrorException{
		if(model == null)
			return model;
		
        reattachModel(model);
        
		Class     modelClass = model.getClass();
		ModelInfo modelInfo  = ModelUtil.getModelInfo(modelClass);

		if(modelInfo == null)
			return model;

		PropertyInfo propertyInfo = modelInfo.getPropertyInfo(referencePropertyId);

		if(propertyInfo == null || propertyInfo.getRelationType() == RelationType.NONE)
			return model;

		Object referenceProperty = null;
		
        try{
            referenceProperty = PropertyUtil.getProperty(model, referencePropertyId);
        }
        catch(Throwable e){
            throw new InternalErrorException(e);
        }
		
		if(propertyInfo.hasModel()){
			List<M> modelList = (List<M>)referenceProperty;
			
			if(modelList != null)
				for(Integer cont = 0 ; cont < modelList.size() ; cont++)
					modelList.get(cont);
		}

		return model;
	}

	/**
	 * @see br.com.concepting.framework.persistence.BaseDAO#saveReference(br.com.concepting.framework.model.BaseModel, java.lang.String)
	 */
	public <M extends BaseModel> void saveReference(M model, String referencePropertyId) throws InternalErrorException{
		try{
			if(model == null)
				return;

			Class     modelClass = model.getClass();
			ModelInfo modelInfo  = ModelUtil.getModelInfo(modelClass); 

			if(modelInfo == null) 
				return;

			PropertyInfo propertyInfo = modelInfo.getPropertyInfo(referencePropertyId);

			if(propertyInfo == null || propertyInfo.getRelationType() == RelationType.NONE)
				return;

			Object referencePropertyValueBuffer = PropertyUtil.getProperty(model, referencePropertyId);

			reattachModel(model);
			
			if(propertyInfo.getRelationType() != RelationType.ONE_TO_ONE)
			    PropertyUtil.setProperty(model, referencePropertyId, null);

			PropertyUtil.setProperty(model, referencePropertyId, referencePropertyValueBuffer);
		}
		catch(Throwable e){
			throw new InternalErrorException(e);
		}
	}

	/**
	 * @see br.com.concepting.framework.persistence.BaseDAO#deleteAll(java.util.Collection)
	 */
	public <M extends BaseModel> void deleteAll(Collection<M> modelList) throws InternalErrorException{
	    if(modelList == null || modelList.size() == 0)
	        return;
	    
		M           model    = null;
		Iterator<M> iterator = modelList.iterator();

		while(iterator.hasNext()){
			model = iterator.next();
			
			delete(model);
		}
	}

	/**
	 * @see br.com.concepting.framework.persistence.BaseDAO#deleteAll()
	 */
	public <M extends BaseModel> void deleteAll() throws InternalErrorException{
		Collection<M> modelList = list();

		deleteAll(modelList);
	}

	/**
	 * @see br.com.concepting.framework.persistence.BaseDAO#save(br.com.concepting.framework.model.BaseModel)
	 */
	public <M extends BaseModel> void save(M model) throws ItemAlreadyExistsException, InternalErrorException{
        if(model == null)
            return;

        try{
            ModelUtil.fillPhoneticProperties(model);
            
            Session connection = getConnection();

			connection.saveOrUpdate(model);
		}
		catch(NonUniqueObjectException e){
		    throw new ItemAlreadyExistsException();
		}
		catch(ObjectNotFoundException e){
		}
		catch(StaleStateException e){
		}
		catch(ConstraintViolationException e){
    		if(e.getMessage().toLowerCase().contains("duplicate"))
    			throw new ItemAlreadyExistsException();
    		
			throw new InternalErrorException(e);
		}
		catch(HibernateException e){
			throw new InternalErrorException(e);
		}
        catch(InvocationTargetException e){
            throw new InternalErrorException(e);
        }
        catch(IllegalAccessException e){
            throw new InternalErrorException(e);        
        }
        catch(NoSuchMethodException e){
            throw new InternalErrorException(e);
        }
	}

	/**
	 * @see br.com.concepting.framework.persistence.BaseDAO#insert(br.com.concepting.framework.model.BaseModel)
	 */
	public <M extends BaseModel> void insert(M model) throws ItemAlreadyExistsException, InternalErrorException{
        if(model == null)
            return;

		try{
		    ModelUtil.fillPhoneticProperties(model);
		    
	        Session connection = getConnection();

            connection.save(model);
		}
		catch(NonUniqueObjectException e){
		    throw new ItemAlreadyExistsException();
		}
		catch(ObjectNotFoundException e){
		}
		catch(StaleStateException e){
		}
		catch(ConstraintViolationException e){
    		if(e.getMessage().toLowerCase().contains("duplicate"))
    			throw new ItemAlreadyExistsException();
    		
			throw new InternalErrorException(e);
		}
		catch(HibernateException e){
			throw new InternalErrorException(e);
		}
        catch(InvocationTargetException e){
            throw new InternalErrorException(e);
        }
        catch(IllegalAccessException e){
            throw new InternalErrorException(e);        
        }
        catch(NoSuchMethodException e){
            throw new InternalErrorException(e);
        }
	}

	/**
	 * @see br.com.concepting.framework.persistence.BaseDAO#update(br.com.concepting.framework.model.BaseModel)
	 */
	public <M extends BaseModel> void update(M model) throws InternalErrorException{
	    if(model == null)
	        return;
	    
		try{
            ModelUtil.fillPhoneticProperties(model);
            
	        Session connection = getConnection();

	        connection.merge(model);
		}
		catch(NonUniqueObjectException e){
		}
		catch(ObjectNotFoundException e){
		}
		catch(StaleStateException e){
		}
		catch(ConstraintViolationException e){
			throw new InternalErrorException(e);
		}
		catch(HibernateException e){
			throw new InternalErrorException(e);
		}
        catch(InvocationTargetException e){
            throw new InternalErrorException(e);
        }
        catch(IllegalAccessException e){
            throw new InternalErrorException(e);        
        }
        catch(NoSuchMethodException e){
            throw new InternalErrorException(e);
        }
	}

	/**
	 * @see br.com.concepting.framework.persistence.BaseDAO#saveAll(java.util.Collection)
	 */
	public <M extends BaseModel> void saveAll(Collection<M> modelList) throws ItemAlreadyExistsException, InternalErrorException{
	    if(modelList == null || modelList.size() == 0)
	        return;
	     
		try{
	        Session     connection = getConnection();
            M           model      = null;
			Iterator<M> iterator   = modelList.iterator();
			
			while(iterator.hasNext()){
				model = iterator.next();
				
				ModelUtil.fillPhoneticProperties(model);
				
				connection.saveOrUpdate(model);
			}
		}
		catch(NonUniqueObjectException e){
            throw new ItemAlreadyExistsException();
		}
		catch(ObjectNotFoundException e){
		}
		catch(StaleStateException e){
		}
		catch(ConstraintViolationException e){
    		if(e.getMessage().toLowerCase().contains("duplicate"))
    			throw new ItemAlreadyExistsException();

    		throw new ItemAlreadyExistsException();
		}
		catch(HibernateException e){
			throw new InternalErrorException(e);
		}
        catch(InvocationTargetException e){
            throw new InternalErrorException(e);
        }
        catch(IllegalAccessException e){
            throw new InternalErrorException(e);        
        }
        catch(NoSuchMethodException e){
            throw new InternalErrorException(e);
        }
	}

	/**
	 * @see br.com.concepting.framework.persistence.BaseDAO#insertAll(java.util.Collection)
	 */
	public <M extends BaseModel> void insertAll(Collection<M> modelList) throws ItemAlreadyExistsException, InternalErrorException{
	    if(modelList == null || modelList.size() == 0)
	        return;
	    
		try{
	        Session     connection = getConnection();
			M           model      = null;
			Iterator<M> iterator   = modelList.iterator();
			
			while(iterator.hasNext()){
    			model = iterator.next();
    			
                ModelUtil.fillPhoneticProperties(model);
                
    			connection.save(model);
    		}
    	}
    	catch(NonUniqueObjectException e){
            throw new ItemAlreadyExistsException();
    	}
		catch(ObjectNotFoundException e){
		}
    	catch(StaleStateException e){
    	}
    	catch(ConstraintViolationException e){
    		if(e.getMessage().toLowerCase().contains("duplicate"))
    			throw new ItemAlreadyExistsException();
    		
    		throw new InternalErrorException(e);
    	}
    	catch(HibernateException e){
    		throw new InternalErrorException(e);
    	}
        catch(InvocationTargetException e){
            throw new InternalErrorException(e);
        }
        catch(IllegalAccessException e){
            throw new InternalErrorException(e);        
        }
        catch(NoSuchMethodException e){
            throw new InternalErrorException(e);
        }
	}

	/**
	 * @see br.com.concepting.framework.persistence.BaseDAO#updateAll(java.util.Collection)
	 */
	public <M extends BaseModel> void updateAll(Collection<M> modelList) throws InternalErrorException{
	    if(modelList == null || modelList.size() == 0)
	        return;
	    
		try{
	        Session     connection = getConnection();
    		M           model      = null;
    		Iterator<M> iterator   = modelList.iterator();
    		
    		while(iterator.hasNext()){
    			model = iterator.next();
                
                ModelUtil.fillPhoneticProperties(model);
    			
    			connection.merge(model);
    		}
		}
		catch(NonUniqueObjectException e){
		}
		catch(ObjectNotFoundException e){
		}
		catch(StaleStateException e){
		}
		catch(ConstraintViolationException e){
			throw new InternalErrorException(e);
		}
		catch(HibernateException e){
			throw new InternalErrorException(e);
		}
        catch(InvocationTargetException e){
            throw new InternalErrorException(e);
        }
        catch(IllegalAccessException e){
            throw new InternalErrorException(e);        
        }
        catch(NoSuchMethodException e){
            throw new InternalErrorException(e);
        }
	}
}