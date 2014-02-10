package br.com.concepting.framework.service;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Collection;

import org.apache.commons.beanutils.ConstructorUtils;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.exceptions.ItemAlreadyExistsException;
import br.com.concepting.framework.model.exceptions.ItemNotFoundException;
import br.com.concepting.framework.model.helpers.ModelFilter;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.persistence.interfaces.IDAO;
import br.com.concepting.framework.persistence.resource.PersistenceResource;
import br.com.concepting.framework.persistence.resource.PersistenceResourceLoader;
import br.com.concepting.framework.persistence.util.PersistenceUtil;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.service.annotations.Service;
import br.com.concepting.framework.service.interfaces.IService;
import br.com.concepting.framework.service.types.ServiceType;
import br.com.concepting.framework.service.util.ServiceUtil;
 
/**
 * Classe que define a estrutura básica para uma classe de serviço.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class BaseService implements IService{
    private IDAO              currentPersistence = null;
    private LoginSessionModel loginSession       = null;
    private Integer           timeout            = null;

    /**
     * @see br.com.concepting.framework.service.interfaces.IService#getLoginSession()
     */
    @SuppressWarnings("unchecked")
    public <L extends LoginSessionModel> L getLoginSession(){
        return (L)loginSession;
    }

    /**
     * @see br.com.concepting.framework.service.interfaces.IService#setLoginSession(br.com.concepting.framework.security.model.LoginSessionModel)
     */
    public <L extends LoginSessionModel> void setLoginSession(L loginSession){
        this.loginSession = loginSession;
    }

    /**
     * Indica se a classe de serviço irá gerenciar transações.
     *
     * @return True/False.
     */
    private Boolean useTransaction(){
        Class<? extends BaseService> serviceClass = getClass();
        Class<?>                     interfaces[] = serviceClass.getInterfaces();
        Service                      annotation   = null;

        for(Class<?> interfaceItem : interfaces){
            annotation = interfaceItem.getAnnotation(Service.class);
            
            if(annotation != null)
                break;
        }

        if(annotation != null && annotation.type() != ServiceType.CLASS && annotation.type() != ServiceType.WEB_SERVICE)
            return false;

        return true;
    }

    /**
     * @see br.com.concepting.framework.service.interfaces.IService#getTimeout()
     */
    public Integer getTimeout(){
        return timeout;
    }

    /**
     * @see br.com.concepting.framework.service.interfaces.IService#setTimeout(java.lang.Integer)
     */
    public void setTimeout(Integer timeout){
        this.timeout = timeout;
    }

    /**
     * @see br.com.concepting.framework.service.interfaces.IService#begin()
     */
	public void begin() throws InternalErrorException{
	    try{
            if(currentPersistence == null)
                currentPersistence = getPersistence();
	    }
	    catch(Throwable e){
	    }
	    
	    if(currentPersistence != null){
	        currentPersistence.initialize();
	        
            if(!useTransaction()){
                currentPersistence.setTimeout(timeout);
                currentPersistence.begin();
            }
	    }
    }

    /**
	 * @see br.com.concepting.framework.service.interfaces.IService#commit()
	 */
	public void commit() throws InternalErrorException{
        if(currentPersistence != null && useTransaction())
            currentPersistence.commit();
	}

	/**
	 * @see br.com.concepting.framework.service.interfaces.IService#rollback()
	 */
	public void rollback() throws InternalErrorException{
        if(currentPersistence != null && useTransaction())
            currentPersistence.rollback();
	}

	/**
	 * Retorna a instância da classe de serviço vinculada a um modelo de dados.
	 * Esta instância irá utilizar a transação já iniciada, caso houve, senão irá criar uma nova 
	 * transação caso a classe de serviço gerencie transações.
	 * 
	 * @param modelClass Classe do modelo de dados desejado.
	 * @return Instância da classe de serviço.
	 * @throws InternalErrorException
	 */
    protected <S extends IService, M extends BaseModel> S getService(Class<M> modelClass) throws InternalErrorException{
        S                 service      = ServiceUtil.getService(modelClass);
        LoginSessionModel loginSession = getLoginSession();
        
        try{
            service.setLoginSession(loginSession);
        }
        catch(RemoteException e){
        }
        
        return service;
	}

	/**
	 * Retorna a instância da classe de persistência vinculada a um modelo de dados.
	 *
	 * @param modelClass Classe do modelo de dados desejado.
	 * @return Instância da classe de persistência.
	 * @throws InternalErrorException
	 */
    protected <D extends IDAO, M extends BaseModel> D getPersistence(Class<M> modelClass) throws InternalErrorException{
		try{
            Class<D> persistenceClass    = PersistenceUtil.getPersistenceClassByModel(modelClass);
            D        persistenceInstance = null;
            
            if(currentPersistence == null){
                ModelInfo                 modelInfo                 = ModelUtil.getModelInfo(modelClass);
                String                    persistenceResourceId     = modelInfo.getPersistenceResourceId();
                PersistenceResourceLoader persistenceResourceLoader = new PersistenceResourceLoader();
                PersistenceResource       persistenceResource       = persistenceResourceLoader.get(persistenceResourceId);
                
                persistenceInstance = (D)ConstructorUtils.invokeConstructor(persistenceClass, null);
                
                persistenceInstance.setPersistenceResource(persistenceResource);
            }
            else
                persistenceInstance = (D)ConstructorUtils.invokeConstructor(persistenceClass, currentPersistence);

            return persistenceInstance;
		}
		catch(ClassNotFoundException e){
			throw new InternalErrorException(e);
		}
		catch(NoSuchMethodException e){
			throw new InternalErrorException(e);
		}
		catch(IllegalAccessException e){
			throw new InternalErrorException(e);
		}
		catch(InvocationTargetException e){
			throw new InternalErrorException(e);
		}
		catch(InstantiationException e){
			throw new InternalErrorException(e);
		}
	}

    /**
	 * Retorna a instância da classe de persistência vinculada a classe de serviço.
	 * 
	 * @return Instância da classe de persistência.
	 * @throws InternalErrorException
	 */
    @SuppressWarnings("unchecked")
    protected <D extends IDAO, M extends BaseModel> D getPersistence() throws InternalErrorException{
		try{
			Class<M> modelClass  = ModelUtil.getModelClassByService(getClass());
			IDAO     persistence = getPersistence(modelClass);

			return (D)persistence;
		}
		catch(ClassNotFoundException e){
			throw new InternalErrorException(e);
		}
	}

	/**
	 * @see br.com.concepting.framework.service.interfaces.IService#list()
	 */
    @SuppressWarnings("unchecked")
    public <M extends BaseModel, C extends Collection<M>> C list() throws InternalErrorException{
		IDAO persistence = getPersistence();

		return (C)persistence.list();
	}

	/**
	 * @see br.com.concepting.framework.service.interfaces.IService#search(br.com.concepting.framework.model.BaseModel)
	 */
    @SuppressWarnings("unchecked")
    public <M extends BaseModel, C extends Collection<M>> C search(M model) throws InternalErrorException{
		IDAO persistence = getPersistence();

		return (C)persistence.search(model);
	}

    /**
     * @see br.com.concepting.framework.service.interfaces.IService#search(br.com.concepting.framework.model.BaseModel, br.com.concepting.framework.model.helpers.ModelFilter)
     */
    @SuppressWarnings("unchecked")
    public <M extends BaseModel, C extends Collection<M>> C search(M model, ModelFilter modelFilter) throws InternalErrorException{
		IDAO persistence = getPersistence();

		return (C)persistence.search(model, modelFilter);
	}

	/**
	 * @see br.com.concepting.framework.service.interfaces.IService#find(br.com.concepting.framework.model.BaseModel)
	 */
	public <M extends BaseModel> M find(M model) throws ItemNotFoundException, InternalErrorException{
		IDAO persistence = getPersistence();

		return persistence.find(model);
	}

	/**
	 * @see br.com.concepting.framework.service.interfaces.IService#delete(br.com.concepting.framework.model.BaseModel)
	 */
	public <M extends BaseModel> void delete(M model) throws InternalErrorException{
		IDAO persistence = getPersistence();

		persistence.delete(model);
	}

	/**
	 * @see br.com.concepting.framework.service.interfaces.IService#deleteAll(java.util.Collection)
	 */
	public <M extends BaseModel> void deleteAll(Collection<M> modelList) throws InternalErrorException{
		IDAO persistence = getPersistence();

		persistence.deleteAll(modelList);
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#save(br.com.concepting.framework.model.BaseModel)
	 */
	public <M extends BaseModel> void save(M model) throws ItemAlreadyExistsException, InternalErrorException{
		IDAO persistence = getPersistence();

		persistence.save(model);
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#saveAll(java.util.Collection)
	 */
	public <M extends BaseModel> void saveAll(Collection<M> modelList) throws ItemAlreadyExistsException, InternalErrorException{
		IDAO persistence = getPersistence();

		persistence.saveAll(modelList);
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#insert(br.com.concepting.framework.model.BaseModel)
	 */
	public <M extends BaseModel> void insert(M model) throws ItemAlreadyExistsException, InternalErrorException{
        IDAO persistence = getPersistence();

		persistence.insert(model);
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#insertAll(java.util.Collection)
	 */
	public <M extends BaseModel> void insertAll(Collection<M> modelList) throws ItemAlreadyExistsException, InternalErrorException{
		IDAO persistence = getPersistence();

		persistence.insertAll(modelList);
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#insert(br.com.concepting.framework.model.BaseModel)
	 */
	public <M extends BaseModel> void update(M model) throws ItemAlreadyExistsException, InternalErrorException{
		IDAO persistence = getPersistence();

		persistence.update(model);
	}

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.ICrud#insertAll(java.util.Collection)
	 */
	public <M extends BaseModel> void updateAll(Collection<M> modelList) throws ItemAlreadyExistsException, InternalErrorException{
		IDAO persistence = getPersistence();

		persistence.updateAll(modelList);
	}

	/**
	 * @see br.com.concepting.framework.service.interfaces.IService#deleteAll()
	 */
	public <M extends BaseModel> void deleteAll() throws InternalErrorException{
		IDAO persistence = getPersistence();

		persistence.deleteAll();
	}

	/**
	 * @see br.com.concepting.framework.service.interfaces.IService#loadReference(br.com.concepting.framework.model.BaseModel, java.lang.String)
	 */
	public <M extends BaseModel> M loadReference(M model, String referencePropertyId) throws InternalErrorException{
		IDAO persistence = getPersistence();

		return persistence.loadReference(model, referencePropertyId);
	}

	/**
	 * @see br.com.concepting.framework.service.interfaces.IService#saveReference(br.com.concepting.framework.model.BaseModel, java.lang.String)
	 */
	public <M extends BaseModel> void saveReference(M model, String referencePropertyId) throws InternalErrorException{
		IDAO persistence = getPersistence();

		persistence.saveReference(model, referencePropertyId);
	}
}