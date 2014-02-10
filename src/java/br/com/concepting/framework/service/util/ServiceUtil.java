package br.com.concepting.framework.service.util;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.service.interfaces.IService;
import br.com.concepting.framework.util.StringUtil;
 
/**
 * Classe utilitária para manipulação das classes de serviço.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class ServiceUtil{
    /**
     * Retorna a instância da classe de uma classe serviço específica.
     * 
     * @param modelClass Classe do modelo de dados vinculado à classe de serviço desejada.
     * @return Instância da classe de serviço.
     * @throws InternalErrorException
     */
    public static <S extends IService> S getService(Class<?> modelClass) throws InternalErrorException{
        return getService(modelClass, true);
    }

    /**
	 * Retorna a instância da classe de uma classe serviço específica.
	 * 
	 * @param modelClass Classe do modelo de dados vinculado à classe de serviço desejada.
	 * @param auditable
	 * @return Instância da classe de serviço.
	 * @throws InternalErrorException
	 */
	public static <S extends IService> S getService(Class<?> modelClass, Boolean auditable) throws InternalErrorException{
	    ServiceLocator serviceLocator = new ServiceLocator(modelClass, auditable);
	    
	    return serviceLocator.lookup();
	}

	/**
	 * Retorna a classe de serviço vinculada a um modelo de dados.
	 * 
	 * @param modelClass Classe do modelo de dados.
	 * @return Classe de serviço.
	 * @throws ClassNotFoundException
	 */
    @SuppressWarnings("unchecked")
    public static <S extends IService> Class<S> getServiceClassByModel(Class<?> modelClass) throws ClassNotFoundException{
		return (Class<S>)Class.forName(getServiceClassNameByModel(modelClass));
	}

	/**
	 * Retorna o identificador da classe de serviço vinculada a um modelo de dados.
	 * 
	 * @param modelClass Classe do modelo de dados.
	 * @return String contendo o identificador da classe de serviço.
	 */
	public static String getServiceClassNameByModel(Class<?> modelClass){
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
    public static String getServiceNameByModel(Class<?> modelClass){
        return StringUtil.replaceLast(modelClass.getSimpleName(), "Model", "ServiceImpl");
    }

    /**
	 * Retorna o identificador da interface utilizada na classe de serviço vinculada a um modelo de 
	 * dados.
	 * 
	 * @param modelClass Classe do modelo de dados.
	 * @return String contendo o identificador da interface utilizada na classe de serviço.
	 */
	public static String getServiceInterfaceClassNameByModel(Class<?> modelClass){
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
    public static String getServiceInterfaceNameByModel(Class<?> modelClass){
        return StringUtil.replaceLast(modelClass.getSimpleName(), "Model", "Service");
    }

    /**
	 * Retorna a interface da classe de serviço a partir de um modelo de dados.
	 *
	 * @param modelClass Classe do modelo de dados.
	 * @return Interface da classe de serviço.
	 * @throws ClassNotFoundException
	 */
    @SuppressWarnings("unchecked")
    public static <S extends IService> Class<S> getServiceInterfaceClassByModel(Class<?> modelClass) throws ClassNotFoundException{
		return (Class<S>)Class.forName(getServiceInterfaceClassNameByModel(modelClass));
	}

	/**
	 * Retorna o identificador da interface home utilizada na classe de serviço vinculada a um modelo 
	 * de dados.
	 * 
	 * @param modelClass Classe do modelo de dados.
	 * @return String contendo o identificador da interface home utilizada na classe de serviço.
	 */
	public static String getServiceHomeInterfaceClassNameByModel(Class<?> modelClass){
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
    public static String getServiceHomeInterfaceNameByModel(Class<?> modelClass){
        return StringUtil.replaceLast(modelClass.getSimpleName(), "Model", "ServiceHome");
    }
    
    /**
     * Retorna a interface home da classe de serviço a partir de um modelo de dados.
     *
     * @param modelClass Classe do modelo de dados.
     * @return Interface da classe de serviço.
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public static <S extends IService>Class<S> getServiceHomeInterfaceClassByModel(Class<?> modelClass) throws ClassNotFoundException{
        return (Class<S>)Class.forName(getServiceHomeInterfaceClassNameByModel(modelClass));
    }

    /**
	 * Retorna o identificador da interface remota utilizada na classe de serviço vinculada a um 
	 * modelo de dados.
	 * 
	 * @param modelClass Classe do modelo de dados.
	 * @return String contendo o identificador da interface remota utilizada na classe de serviço.
	 */
	public static String getServiceRemoteInterfaceClassNameByModel(Class<?> modelClass){
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
    public static String getServiceRemoteInterfaceNameByModel(Class<?> modelClass){
        return StringUtil.replaceLast(modelClass.getSimpleName(), "Model", "ServiceRemote");
    }
    
    /**
     * Retorna a interface remota da classe de serviço a partir de um modelo de dados.
     *
     * @param modelClass Classe do modelo de dados.
     * @return Interface da classe de serviço.
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public static <S extends IService> Class<S> getServiceRemoteInterfaceClassByModel(Class<?> modelClass) throws ClassNotFoundException{
        return (Class<S>)Class.forName(getServiceRemoteInterfaceClassNameByModel(modelClass));
    }

    /**
     * Retorna o nome do pacote onde a classe do serviço está armazenada.
     * 
     * @param modelClass Classe do modelo de dados.
     * @return String contendo o nome do pacote.
     */
    public static String getServicePackageByModel(Class<?> modelClass){
        return StringUtil.replaceAll(modelClass.getPackage().getName(), ".model", ".service");
    }
    
    /**
     * Retorna o nome do pacote onde as interfaces do serviço está armazenada.
     * 
     * @param modelClass Classe do modelo de dados.
     * @return String contendo o nome do pacote.
     */
    public static String getServiceInterfacePackageByModel(Class<?> modelClass){
        return StringUtil.replaceAll(modelClass.getPackage().getName(), ".model", ".service.interfaces");
    }
}