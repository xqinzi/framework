package br.com.concepting.framework.service.util;

import br.com.concepting.framework.caching.CachedObject;
import br.com.concepting.framework.caching.Cacher;
import br.com.concepting.framework.caching.CacherManager;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.exceptions.ItemAlreadyExistsException;
import br.com.concepting.framework.model.exceptions.ItemNotFoundException;
import br.com.concepting.framework.persistence.interfaces.IDAO;
import br.com.concepting.framework.security.constants.SecurityConstants;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.service.interfaces.IService;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.exceptions.InternalErrorException;
 
/**
 * Classe utilitária para manipulação das classes de serviço.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class ServiceUtil{
    public static <S extends IService, L extends LoginSessionModel> void setLoginSession(L loginSession, S service){
        if(service != null){
            CachedObject object = new CachedObject();
            
            object.setId(SecurityConstants.LOGIN_SESSION_KEY);
            
            Cacher cacher = CacherManager.getInstance().getCacher(service.toString());

            try{
                cacher.remove(object);
            }
            catch(ItemNotFoundException e){
            }

            object.setContent(loginSession);
        
            try{
                cacher.add(object);
            }
            catch(ItemAlreadyExistsException e){
            }
        }
    }
    
    public static <S extends IService, L extends LoginSessionModel> L getLoginSession(S service){
        L loginSession = null;
        
        if(service != null){
            CachedObject object = null;
            Cacher       cacher = CacherManager.getInstance().getCacher(service.toString());
        
            try{
                object       = cacher.get(SecurityConstants.LOGIN_SESSION_KEY);
                loginSession = object.getContent();
            }
            catch(ItemNotFoundException e){
            }
        }
        
        return loginSession;
    }
    
    public static <S extends IService, D extends IDAO> void setCurrentPersistence(D currentPersistence, S service){
        if(service != null){
            CachedObject object = new CachedObject();
            
            object.setId(service.getClass().getName());
            
            Cacher cacher = CacherManager.getInstance().getCacher(service.toString());

            try{
                cacher.remove(object);
            }
            catch(ItemNotFoundException e){
            }

            object.setContent(currentPersistence);
        
            try{
                cacher.add(object);
            }
            catch(ItemAlreadyExistsException e){
            }
        }
    }
    
    public static <S extends IService, D extends IDAO> D getCurrentPersistence(S service){
        D dao = null;
        
        if(service != null){
            CachedObject object = null;
            Cacher       cacher = CacherManager.getInstance().getCacher(service.toString());
        
            try{
                object = cacher.get(service.getClass().getName());
                dao    = object.getContent();
            }
            catch(ItemNotFoundException e){
            }
        }
        
        return dao;
    }
    
	/**
	 * Retorna a instância da classe de uma classe serviço específica.
	 * 
	 * @param modelClass Classe do modelo de dados vinculado à classe de serviço desejada.
	 * @return Instância da classe de serviço.
	 * @throws InternalErrorException
	 */
	public static <S extends IService, M extends BaseModel> S instantiate(Class<M> modelClass) throws InternalErrorException{
	    ServiceLocator serviceLocator = new ServiceLocator(modelClass);
	    
	    return serviceLocator.lookup();
	}

	/**
	 * Retorna a classe de serviço vinculada a um modelo de dados.
	 * 
	 * @param modelClass Classe do modelo de dados.
	 * @return Classe de serviço.
	 * @throws ClassNotFoundException
	 */
    public static <M extends BaseModel, S extends IService> Class<S> getServiceClassByModel(Class<M> modelClass) throws ClassNotFoundException{
		return (Class<S>)Class.forName(getServiceClassNameByModel(modelClass));
	}

	/**
	 * Retorna o identificador da classe de serviço vinculada a um modelo de dados.
	 * 
	 * @param modelClass Classe do modelo de dados.
	 * @return String contendo o identificador da classe de serviço.
	 */
	public static <M extends BaseModel> String getServiceClassNameByModel(Class<M> modelClass){
		String serviceClassId = StringUtil.replaceLast(modelClass.getName(), "Model", "ServiceImpl");

		serviceClassId = StringUtil.replaceAll(serviceClassId, ".model", ".service");

		return serviceClassId;
	}

    /**
     * Retorna o identificador do serviço vinculada a um modelo de dados.
     * 
     * @param modelClass Classe do modelo de dados.
     * @return String contendo o identificador do serviço.
     */
    public static <M extends BaseModel> String getServiceNameByModel(Class<M> modelClass){
        return StringUtil.replaceLast(modelClass.getSimpleName(), "Model", "ServiceImpl");
    }

    /**
	 * Retorna o identificador da interface utilizada na classe de serviço vinculada a um modelo de 
	 * dados.
	 * 
	 * @param modelClass Classe do modelo de dados.
	 * @return String contendo o identificador da interface utilizada na classe de serviço.
	 */
	public static <M extends BaseModel> String getServiceInterfaceClassNameByModel(Class<M> modelClass){
		String serviceInterfaceId = StringUtil.replaceLast(modelClass.getName(), "Model", "Service");

		serviceInterfaceId = StringUtil.replaceAll(serviceInterfaceId, ".model", ".service.interfaces");

		return serviceInterfaceId;
	}

    /**
     * Retorna o identificador da interface utilizada na classe de serviço vinculada a um modelo de 
     * dados.
     * 
     * @param modelClass Classe do modelo de dados.
     * @return String contendo o identificador da interface utilizada na classe de serviço.
     */
    public static <M extends BaseModel> String getServiceInterfaceNameByModel(Class<M> modelClass){
        return StringUtil.replaceLast(modelClass.getSimpleName(), "Model", "Service");
    }

    /**
	 * Retorna a interface da classe de serviço a partir de um modelo de dados.
	 *
	 * @param modelClass Classe do modelo de dados.
	 * @return Interface da classe de serviço.
	 * @throws ClassNotFoundException
	 */
    public static <S extends IService, M extends BaseModel> Class<S> getServiceInterfaceClassByModel(Class<M> modelClass) throws ClassNotFoundException{
		return (Class<S>)Class.forName(getServiceInterfaceClassNameByModel(modelClass));
	}

	/**
	 * Retorna o identificador da interface home utilizada na classe de serviço vinculada a um modelo 
	 * de dados.
	 * 
	 * @param modelClass Classe do modelo de dados.
	 * @return String contendo o identificador da interface home utilizada na classe de serviço.
	 */
	public static <M extends BaseModel> String getServiceHomeInterfaceClassNameByModel(Class<M> modelClass){
		String serviceHomeInterfaceId = StringUtil.replaceLast(modelClass.getName(), "Model", "ServiceHome");

		serviceHomeInterfaceId = StringUtil.replaceAll(serviceHomeInterfaceId, ".model", ".service.interfaces");

		return serviceHomeInterfaceId;
	}

    /**
     * Retorna o identificador da interface home utilizada na classe de serviço vinculada a um modelo 
     * de dados.
     * 
     * @param modelClass Classe do modelo de dados.
     * @return String contendo o identificador da interface home utilizada na classe de serviço.
     */
    public static <M extends BaseModel> String getServiceHomeInterfaceNameByModel(Class<M> modelClass){
        return StringUtil.replaceLast(modelClass.getSimpleName(), "Model", "ServiceHome");
    }
    
    /**
     * Retorna a interface home da classe de serviço a partir de um modelo de dados.
     *
     * @param modelClass Classe do modelo de dados.
     * @return Interface da classe de serviço.
     * @throws ClassNotFoundException
     */
    public static <S extends IService, M extends BaseModel> Class<S> getServiceHomeInterfaceClassByModel(Class<M> modelClass) throws ClassNotFoundException{
        return (Class<S>)Class.forName(getServiceHomeInterfaceClassNameByModel(modelClass));
    }

    /**
	 * Retorna o identificador da interface remota utilizada na classe de serviço vinculada a um 
	 * modelo de dados.
	 * 
	 * @param modelClass Classe do modelo de dados.
	 * @return String contendo o identificador da interface remota utilizada na classe de serviço.
	 */
	public static <M extends BaseModel> String getServiceRemoteInterfaceClassNameByModel(Class<M> modelClass){
		String serviceRemoteInterfaceId = StringUtil.replaceLast(modelClass.getName(), "Model", "ServiceRemote");

		serviceRemoteInterfaceId = StringUtil.replaceAll(serviceRemoteInterfaceId, ".model", ".service.interfaces");

		return serviceRemoteInterfaceId;
	}
	
    /**
     * Retorna o identificador da interface remota utilizada na classe de serviço vinculada a um 
     * modelo de dados.
     * 
     * @param modelClass Classe do modelo de dados.
     * @return String contendo o identificador da interface remota utilizada na classe de serviço.
     */
    public static <M extends BaseModel> String getServiceRemoteInterfaceNameByModel(Class<M> modelClass){
        return StringUtil.replaceLast(modelClass.getSimpleName(), "Model", "ServiceRemote");
    }
    
    /**
     * Retorna a interface remota da classe de serviço a partir de um modelo de dados.
     *
     * @param modelClass Classe do modelo de dados.
     * @return Interface da classe de serviço.
     * @throws ClassNotFoundException
     */
    public static <S extends IService, M extends BaseModel> Class<S> getServiceRemoteInterfaceClassByModel(Class<M> modelClass) throws ClassNotFoundException{
        return (Class<S>)Class.forName(getServiceRemoteInterfaceClassNameByModel(modelClass));
    }

    /**
     * Retorna o nome do pacote onde a classe do serviço está armazenada.
     * 
     * @param modelClass Classe do modelo de dados.
     * @return String contendo o nome do pacote.
     */
    public static <M extends BaseModel> String getServicePackageByModel(Class<M> modelClass){
        return StringUtil.replaceAll(modelClass.getPackage().getName(), ".model", ".service");
    }
    
    /**
     * Retorna o nome do pacote onde as interfaces do serviço está armazenada.
     * 
     * @param modelClass Classe do modelo de dados.
     * @return String contendo o nome do pacote.
     */
    public static <M extends BaseModel> String getServiceInterfacePackageByModel(Class<M> modelClass){
        return StringUtil.replaceAll(modelClass.getPackage().getName(), ".model", ".service.interfaces");
    }
}