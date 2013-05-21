package br.com.concepting.framework.service.util;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.service.interfaces.IService;
import br.com.concepting.framework.util.StringUtil;
 
/**
 * Classe utilit�ria para manipula��o das classes de servi�o.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class ServiceUtil{
	/**
	 * Retorna a inst�ncia da classe de uma classe servi�o espec�fica.
	 * 
	 * @param modelClass Classe do modelo de dados vinculado � classe de servi�o desejada.
	 * @return Inst�ncia da classe de servi�o.
	 * @throws InternalErrorException
	 */
	public static <S extends IService, M extends BaseModel> S getService(Class<M> modelClass) throws InternalErrorException{
	    ServiceLocator serviceLocator = new ServiceLocator(modelClass);
	    
	    return serviceLocator.lookup();
	}

	/**
	 * Retorna a classe de servi�o vinculada a um modelo de dados.
	 * 
	 * @param modelClass Classe do modelo de dados.
	 * @return Classe de servi�o.
	 * @throws ClassNotFoundException
	 */
    public static <M extends BaseModel, S extends IService> Class<S> getServiceClassByModel(Class<M> modelClass) throws ClassNotFoundException{
		return (Class<S>)Class.forName(getServiceClassNameByModel(modelClass));
	}

	/**
	 * Retorna o identificador da classe de servi�o vinculada a um modelo de dados.
	 * 
	 * @param modelClass Classe do modelo de dados.
	 * @return String contendo o identificador da classe de servi�o.
	 */
	public static <M extends BaseModel> String getServiceClassNameByModel(Class<M> modelClass){
		String serviceClassId = StringUtil.replaceLast(modelClass.getName(), "Model", "ServiceImpl");

		serviceClassId = StringUtil.replaceAll(serviceClassId, ".model", ".service");

		return serviceClassId;
	}

    /**
     * Retorna o identificador do servi�o vinculada a um modelo de dados.
     * 
     * @param modelClass Classe do modelo de dados.
     * @return String contendo o identificador do servi�o.
     */
    public static <M extends BaseModel> String getServiceNameByModel(Class<M> modelClass){
        return StringUtil.replaceLast(modelClass.getSimpleName(), "Model", "ServiceImpl");
    }

    /**
	 * Retorna o identificador da interface utilizada na classe de servi�o vinculada a um modelo de 
	 * dados.
	 * 
	 * @param modelClass Classe do modelo de dados.
	 * @return String contendo o identificador da interface utilizada na classe de servi�o.
	 */
	public static <M extends BaseModel> String getServiceInterfaceClassNameByModel(Class<M> modelClass){
		String serviceInterfaceId = StringUtil.replaceLast(modelClass.getName(), "Model", "Service");

		serviceInterfaceId = StringUtil.replaceAll(serviceInterfaceId, ".model", ".service.interfaces");

		return serviceInterfaceId;
	}

    /**
     * Retorna o identificador da interface utilizada na classe de servi�o vinculada a um modelo de 
     * dados.
     * 
     * @param modelClass Classe do modelo de dados.
     * @return String contendo o identificador da interface utilizada na classe de servi�o.
     */
    public static <M extends BaseModel> String getServiceInterfaceNameByModel(Class<M> modelClass){
        return StringUtil.replaceLast(modelClass.getSimpleName(), "Model", "Service");
    }

    /**
	 * Retorna a interface da classe de servi�o a partir de um modelo de dados.
	 *
	 * @param modelClass Classe do modelo de dados.
	 * @return Interface da classe de servi�o.
	 * @throws ClassNotFoundException
	 */
    public static <S extends IService, M extends BaseModel> Class<S> getServiceInterfaceClassByModel(Class<M> modelClass) throws ClassNotFoundException{
		return (Class<S>)Class.forName(getServiceInterfaceClassNameByModel(modelClass));
	}

	/**
	 * Retorna o identificador da interface home utilizada na classe de servi�o vinculada a um modelo 
	 * de dados.
	 * 
	 * @param modelClass Classe do modelo de dados.
	 * @return String contendo o identificador da interface home utilizada na classe de servi�o.
	 */
	public static <M extends BaseModel> String getServiceHomeInterfaceClassNameByModel(Class<M> modelClass){
		String serviceHomeInterfaceId = StringUtil.replaceLast(modelClass.getName(), "Model", "ServiceHome");

		serviceHomeInterfaceId = StringUtil.replaceAll(serviceHomeInterfaceId, ".model", ".service.interfaces");

		return serviceHomeInterfaceId;
	}

    /**
     * Retorna o identificador da interface home utilizada na classe de servi�o vinculada a um modelo 
     * de dados.
     * 
     * @param modelClass Classe do modelo de dados.
     * @return String contendo o identificador da interface home utilizada na classe de servi�o.
     */
    public static <M extends BaseModel> String getServiceHomeInterfaceNameByModel(Class<M> modelClass){
        return StringUtil.replaceLast(modelClass.getSimpleName(), "Model", "ServiceHome");
    }
    
    /**
     * Retorna a interface home da classe de servi�o a partir de um modelo de dados.
     *
     * @param modelClass Classe do modelo de dados.
     * @return Interface da classe de servi�o.
     * @throws ClassNotFoundException
     */
    public static <S extends IService, M extends BaseModel> Class<S> getServiceHomeInterfaceClassByModel(Class<M> modelClass) throws ClassNotFoundException{
        return (Class<S>)Class.forName(getServiceHomeInterfaceClassNameByModel(modelClass));
    }

    /**
	 * Retorna o identificador da interface remota utilizada na classe de servi�o vinculada a um 
	 * modelo de dados.
	 * 
	 * @param modelClass Classe do modelo de dados.
	 * @return String contendo o identificador da interface remota utilizada na classe de servi�o.
	 */
	public static <M extends BaseModel> String getServiceRemoteInterfaceClassNameByModel(Class<M> modelClass){
		String serviceRemoteInterfaceId = StringUtil.replaceLast(modelClass.getName(), "Model", "ServiceRemote");

		serviceRemoteInterfaceId = StringUtil.replaceAll(serviceRemoteInterfaceId, ".model", ".service.interfaces");

		return serviceRemoteInterfaceId;
	}
	
    /**
     * Retorna o identificador da interface remota utilizada na classe de servi�o vinculada a um 
     * modelo de dados.
     * 
     * @param modelClass Classe do modelo de dados.
     * @return String contendo o identificador da interface remota utilizada na classe de servi�o.
     */
    public static <M extends BaseModel> String getServiceRemoteInterfaceNameByModel(Class<M> modelClass){
        return StringUtil.replaceLast(modelClass.getSimpleName(), "Model", "ServiceRemote");
    }
    
    /**
     * Retorna a interface remota da classe de servi�o a partir de um modelo de dados.
     *
     * @param modelClass Classe do modelo de dados.
     * @return Interface da classe de servi�o.
     * @throws ClassNotFoundException
     */
    public static <S extends IService, M extends BaseModel> Class<S> getServiceRemoteInterfaceClassByModel(Class<M> modelClass) throws ClassNotFoundException{
        return (Class<S>)Class.forName(getServiceRemoteInterfaceClassNameByModel(modelClass));
    }

    /**
     * Retorna o nome do pacote onde a classe do servi�o est� armazenada.
     * 
     * @param modelClass Classe do modelo de dados.
     * @return String contendo o nome do pacote.
     */
    public static <M extends BaseModel> String getServicePackageByModel(Class<M> modelClass){
        return StringUtil.replaceAll(modelClass.getPackage().getName(), ".model", ".service");
    }
    
    /**
     * Retorna o nome do pacote onde as interfaces do servi�o est� armazenada.
     * 
     * @param modelClass Classe do modelo de dados.
     * @return String contendo o nome do pacote.
     */
    public static <M extends BaseModel> String getServiceInterfacePackageByModel(Class<M> modelClass){
        return StringUtil.replaceAll(modelClass.getPackage().getName(), ".model", ".service.interfaces");
    }
}