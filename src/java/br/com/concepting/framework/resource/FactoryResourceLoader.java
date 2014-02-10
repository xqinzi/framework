package br.com.concepting.framework.resource;

import br.com.concepting.framework.resource.constants.FactoryConstants;
import br.com.concepting.framework.resource.exceptions.InvalidResourceException;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Classe que define a estrutura b�sica para leitura/manipula��o de configura��es de comunica��o com
 * os servidores de aplica��es e/ou banco de dados.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class FactoryResourceLoader extends XmlResourceLoader<FactoryResource>{
	/**
	 * Construtor - Efetua a leitura do arquivo de configura��es default.
	 * 
	 * @throws InvalidResourceException
	 */
	public FactoryResourceLoader() throws InvalidResourceException{
		super(FactoryConstants.DEFAULT_RESOURCE_ID);
	}

	/**
	 * Construtor - Efetua a leitura de um arquivo de configura��es espec�fico.
	 * 
	 * @param resourceId String contendo o identificador do arquivo.
	 * @throws InvalidResourceException
	 */
	public FactoryResourceLoader(String resourceId) throws InvalidResourceException{
		super(resourceId);
	}

	/**
	 * Construtor - Efetua a leitura de um arquivo de configura��es espec�fico.
	 * 
	 * @param resourceDir String contendo o diret�rio de armazenamento do arquivo.
	 * @param resourceId String contendo o identificador do arquivo.
	 * @throws InvalidResourceException
	 */
	public FactoryResourceLoader(String resourceDir, String resourceId) throws InvalidResourceException{
		super(resourceDir, resourceId);
	}

	/**
	 * @see br.com.concepting.framework.resource.XmlResourceLoader#getResourceNodeId()
	 */
	protected String getResourceNodeId(){
		return "factory";
	}
	
	/**
	 * @see br.com.concepting.framework.resource.XmlResourceLoader#getResourceClass()
	 */
    protected Class<FactoryResource> getResourceClass() throws ClassNotFoundException{
    	return FactoryResource.class;
    }

	/**
	 * @see br.com.concepting.framework.resource.XmlResourceLoader#get(java.lang.String)
	 */
    public FactoryResource get(String id) throws InvalidResourceException{
		FactoryResource factoryResource = super.get(id);
		XmlNode         resource        = factoryResource.getContent();
        String          type            = StringUtil.trim(resource.getAttribute("type"));
        XmlNode         resourceNode    = null;
        
        factoryResource.setType(type);

		resourceNode = resource.getNode("class");
		
		if(resourceNode != null){
			String clazz = StringUtil.trim(resourceNode.getValue());

			if(clazz.length() == 0)
				throw new InvalidResourceException(getResourceId(), resource.getText());

			factoryResource.setClazz(clazz);
		}

		resourceNode = resource.getNode("url");
		
		if(resourceNode != null){
			String url = StringUtil.trim(resourceNode.getValue());

			if(url.length() == 0)
				throw new InvalidResourceException(getResourceId(), resource.getText());

			factoryResource.setUrl(url);
		}

        resourceNode = resource.getNode("options");
        
		if(resourceNode != null){
			XmlNode resourceOption = null;
			Integer cont           = 0;

			while(true){
				resourceOption = resourceNode.getNode(cont);
				if(resourceOption == null)
					break;

				String resourceOptionId = StringUtil.trim(resourceOption.getAttribute("id"));

				if(resourceOptionId.length() == 0)
					throw new InvalidResourceException(getResourceId(), resourceOption.getText());

				String resourceOptionValue = StringUtil.trim(resourceOption.getAttribute("value"));

				factoryResource.addOption(resourceOptionId, resourceOptionValue);

				cont++;
			}
		}

		return factoryResource;
	}
}