package br.com.concepting.framework.persistence.util;

import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.persistence.interfaces.IDAO;
import br.com.concepting.framework.persistence.resource.PersistenceResource;
import br.com.concepting.framework.persistence.resource.PersistenceResourceLoader;
import br.com.concepting.framework.resource.exceptions.InvalidResourceException;
import br.com.concepting.framework.util.StringUtil;

/**
 * Classe utilitária com rotinas gerais de persistência.
 *
 * @author fvilarinho
 * @since 1.0
 */
public class PersistenceUtil{
	/**
	 * Retorna as configurações de persistência default.
	 *
	 * @return Instância contendo as configurações de persistência.
	 * @throws InvalidResourceException
	 */
	public static PersistenceResource getDefaultPersistenceResource() throws InvalidResourceException{
		PersistenceResourceLoader loader = new PersistenceResourceLoader();
		
		return loader.getDefault();
	}
	
	/**
	 * Retorna as configurações de persistência a partir de seu identificador.
	 *
	 * @param persistenceResourceId String contendo o identificador da configuração.
	 * @return Instância contendo as configurações de persistência.
	 * @throws InvalidResourceException
	 */
    public static <R extends PersistenceResource> R getPersistenceResource(String persistenceResourceId) throws InvalidResourceException{
		PersistenceResourceLoader resourceLoader = new PersistenceResourceLoader();
		PersistenceResource       resource = null;

		if(persistenceResourceId.length() == 0)
			resource = resourceLoader.getDefault();
		else
			resource = resourceLoader.get(persistenceResourceId);

		return (R)resource;
	}

	/**
	 * Retorna o nome completo da classe de persistência a partir de um modelo de dados.
	 *
	 * @param modelClass Classe do modelo de dados desejado.
	 * @return String contendo o identificador da classe de persistência.
	 */
	public static <M extends BaseModel> String getPersistenceClassNameByModel(Class<M> modelClass){
		String persistenceClassId = StringUtil.replaceLast(modelClass.getName(), "Model", "DAOImpl");
		
		return StringUtil.replaceAll(persistenceClassId, ".model", ".persistence");
	}

    /**
     * Retorna o nome da classe de persistência a partir de um modelo de dados.
     *
     * @param modelClass Classe do modelo de dados desejado.
     * @return String contendo o identificador da classe de persistência.
     */
    public static <M extends BaseModel> String getPersistenceNameByModel(Class<M> modelClass){
        return StringUtil.replaceLast(modelClass.getSimpleName(), "Model", "DAOImpl");
    }

    /**
	 * Retorna a classe de persistência a partir de uma modelo de dados.
	 *
	 * @param modelClass Classe do modelo de dados desejado.
	 * @return Classe de persistência.
	 * @throws ClassNotFoundException
	 */
    public static <M extends BaseModel, D extends IDAO> Class<D> getPersistenceClassByModel(Class<M> modelClass) throws ClassNotFoundException{
		return (Class<D>)Class.forName(getPersistenceClassNameByModel(modelClass));
	}

	/**
	 * Retorna o nome completo da interface da classe de persistência a partir de um modelo de dados.
	 *
	 * @param modelClass Classe do modelo de dados desejado.
	 * @return String contendo o identificador da interface da classe de persistência.
	 */
	public static <M extends BaseModel> String getPersistenceInterfaceClassNameByModel(Class<M> modelClass){
		String persistenceClassId = StringUtil.replaceLast(modelClass.getName(), "Model", "DAO");

		return StringUtil.replaceAll(persistenceClassId, ".model", ".persistence.interfaces");
	}

    /**
     * Retorna o nome da interface da classe de persistência a partir de um modelo de dados.
     *
     * @param modelClass Classe do modelo de dados desejado.
     * @return String contendo o identificador da interface da classe de persistência.
     */
    public static <M extends BaseModel> String getPersistenceInterfaceNameByModel(Class<M> modelClass){
        return StringUtil.replaceLast(modelClass.getSimpleName(), "Model", "DAO");
    }

    /**
	 * Retorna a interface da classe de persistência a partir de um modelo de dados.
	 *
	 * @param modelClass Classe do modelo de dados desejado.
	 * @return String contendo o identificador da interface da classe de persistência.
	 * @throws ClassNotFoundException 
	 */
    public static <M extends BaseModel, D extends IDAO> Class<D> getPersistenceInterfaceClassByModel(Class<M> modelClass) throws ClassNotFoundException{
		return (Class<D>)Class.forName(getPersistenceInterfaceClassNameByModel(modelClass));
	}
	
    /**
     * Retorna o nome do pacote onde a classe de persistência está armazenada.
     * 
     * @param modelClass Classe do modelo de dados.
     * @return String contendo o nome do pacote.
     */
    public static <M extends BaseModel> String getPersistencePackageByModel(Class<M> modelClass){
        return StringUtil.replaceAll(modelClass.getPackage().getName(), ".model", ".persistence");
    }

    /**
     * Retorna o nome do pacote onde a interface de persistência está armazenada.
     * 
     * @param modelClass Classe do modelo de dados.
     * @return String contendo o nome do pacote.
     */
    public static <M extends BaseModel> String getPersistenceInterfacePackageByModel(Class<M> modelClass){
        return StringUtil.replaceAll(modelClass.getPackage().getName(), ".model", ".persistence.interfaces");
    }
}