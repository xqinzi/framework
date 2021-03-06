package br.com.concepting.framework.context.resource;

import br.com.concepting.framework.context.constants.ContextConstants;
import br.com.concepting.framework.context.types.ContextFactoryType;
import br.com.concepting.framework.network.constants.NetworkConstants;
import br.com.concepting.framework.resource.FactoryResource;
import br.com.concepting.framework.resource.XmlResourceLoader;
import br.com.concepting.framework.resource.constants.FactoryConstants;
import br.com.concepting.framework.resource.exceptions.InvalidResourceException;
import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Classe responsável pela leitura/manipulação das configurações para comunicação 
 * com o servidor de aplicações.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class ContextResourceLoader extends XmlResourceLoader<ContextResource>{
	/**
	 * Construtor - Inicializa classe de leitura/manipulação do arquivo de configurações default
	 * para comunicação com o servidor de aplicações.
	 * 
	 * @throws InvalidResourceException
	 */
	public ContextResourceLoader() throws InvalidResourceException{
		super(ContextConstants.DEFAULT_RESOURCE_ID);
	}

	/**
	 * Construtor - Inicializa classe de leitura/manipulação de um arquivo de configurações
	 * para comunicação com o servidor de aplicações. 
	 * 
	 * @param resourceId String contendo o identificador do arquivo de configurações do 
	 * contexto.
	 * @throws InvalidResourceException
	 */
	public ContextResourceLoader(String resourceId) throws InvalidResourceException{
		super(resourceId);
	}

	/**
	 * Construtor - Inicializa classe de leitura/manipulação de um arquivo de configurações 
	 * do contexto.
	 * 
	 * @param resourceDir String contendo o diretório de armazenamento do arquivo de 
	 * configurações do contexto.
	 * @param resourceId String contendo o identificador do arquivo de configurações do 
	 * contexto.
	 * @throws InvalidResourceException
	 */
	public ContextResourceLoader(String resourceDir, String resourceId) throws InvalidResourceException{
		super(resourceDir, resourceId);
	}

	/**
	 * @see br.com.concepting.framework.resource.XmlResourceLoader#get(java.lang.String)
	 */
    public ContextResource get(String id) throws InvalidResourceException{
		ContextResource    contextResource    = super.get(id);
		XmlNode            resource           = contextResource.getContent();
        String             factoryResourceId  = StringUtil.trim(resource.getAttribute("factoryResourceId"));
        FactoryResource    factoryResource    = null;
        ContextFactoryType contextFactoryType = null;

        if(factoryResourceId.length() > 0){
            ContextFactoryResourceLoader factoryResourceLoader = null;

            if(getResourceDir().length() > 0)
                factoryResourceLoader = new ContextFactoryResourceLoader(getResourceDir(), FactoryConstants.DEFAULT_RESOURCE_ID);
            else
                factoryResourceLoader = new ContextFactoryResourceLoader();

            factoryResource = factoryResourceLoader.get(factoryResourceId);

            contextResource.setFactoryResource(factoryResource);
            
            contextFactoryType = ContextFactoryType.valueOf(factoryResource.getType().toUpperCase());
        }
        else
            contextFactoryType = ContextFactoryType.TOMCAT;

        XmlNode resourceNode = resource.getNode("serverName");
        
		if(resourceNode != null){
			String serverName = StringUtil.trim(resourceNode.getValue());

			if(serverName.length() == 0)
				throw new InvalidResourceException(getResourceId(), resourceNode.getText());

			contextResource.setServerName(serverName);
		}
		else
		    contextResource.setServerName(NetworkConstants.LOCALHOST_ID);

        resourceNode = resource.getNode("lookupPort");
        
        if(resourceNode != null){
            try{
                Integer lookupPort = NumberUtil.parseInt(StringUtil.trim(resourceNode.getValue()));

                contextResource.setLookupPort(lookupPort);
            }
            catch(Throwable e){
                throw new InvalidResourceException(getResourceId(), resourceNode.getText(), e);
            }
        }
        else
            contextResource.setLookupPort(contextFactoryType.getDefaultLookupPort());

        resourceNode = resource.getNode("serverPort");
        
		if(resourceNode != null){
			try{
				Integer serverPort = NumberUtil.parseInt(StringUtil.trim(resourceNode.getValue()));

				contextResource.setServerPort(serverPort);
			}
			catch(Throwable e){
				throw new InvalidResourceException(getResourceId(), resourceNode.getText(), e);
			}
        }
        else
            contextResource.setLookupPort(contextFactoryType.getDefaultServerPort());
		
		Boolean useSsl = false;
		
		try{
		    useSsl = Boolean.parseBoolean(resource.getAttribute("useSsl"));
		}
		catch(Throwable e){
		}

		contextResource.setUseSsl(useSsl);

		return contextResource;
	}
}