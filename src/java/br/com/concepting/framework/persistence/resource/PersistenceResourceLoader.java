package br.com.concepting.framework.persistence.resource;

import java.util.List;

import br.com.concepting.framework.context.constants.ContextConstants;
import br.com.concepting.framework.context.resource.ContextResource;
import br.com.concepting.framework.context.resource.ContextResourceLoader;
import br.com.concepting.framework.persistence.constants.PersistenceConstants;
import br.com.concepting.framework.resource.FactoryResource;
import br.com.concepting.framework.resource.XmlResourceLoader;
import br.com.concepting.framework.resource.exceptions.InvalidResourceException;
import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.XmlNode;
 
/**
 * Classe responsável pela leitura/manipulação das configurações de persistência.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class PersistenceResourceLoader extends XmlResourceLoader<PersistenceResource>{
	/**
	 * Construtor - Inicializa classe de leitura/manipulação do arquivo de configurações de 
	 * persistência default.
	 * 
	 * @throws InvalidResourceException
	 */
	public PersistenceResourceLoader() throws InvalidResourceException{
		super(PersistenceConstants.DEFAULT_RESOURCE_ID);
	}

	/**
	 * Construtor - Inicializa classe de leitura/manipulação de um arquivo de configurações de 
	 * persistência.
	 * 
	 * @param resourceId String contendo o identificador do arquivo de configurações de persistência.
	 * @throws InvalidResourceException
	 */
	public PersistenceResourceLoader(String resourceId) throws InvalidResourceException{
		super(resourceId);
	}

	/**
	 * Construtor - Inicializa classe de leitura/manipulação de um arquivo de configurações de 
	 * persistência.
	 * 
	 * @param resourceDir String contendo o diretório de armazenamento do arquivo de configurações 
	 * de persistência.
	 * @param resourceId String contendo o identificador do arquivo de configurações de persistência.
	 * @throws InvalidResourceException
	 */
	public PersistenceResourceLoader(String resourceDir, String resourceId) throws InvalidResourceException{
		super(resourceDir, resourceId);
	}

	/**
	 * @see br.com.concepting.framework.resource.XmlResourceLoader#get(java.lang.String)
	 */
    public PersistenceResource get(String id) throws InvalidResourceException{
		PersistenceResource persistenceResource = super.get(id);
		XmlNode             resource            = persistenceResource.getContent();
		XmlNode             resourceNode        = null;
		String              timeout             = resource.getAttribute("timeout");

		if(timeout.length() > 0){
			try{
				persistenceResource.setTimeout(NumberUtil.parseInt(timeout));
			}
			catch(Throwable e){
				throw new InvalidResourceException(getResourceId(), resource.getText(), e);
			}
		}

		resourceNode = resource.getNode("lookupName");
		
		if(resourceNode != null){
			String lookupName = StringUtil.trim(resourceNode.getValue());

			if(lookupName.length() == 0)
				throw new InvalidResourceException(getResourceId(), resourceNode.getText());

			persistenceResource.setLookupName(lookupName);

			String                contextResourceId = StringUtil.trim(resource.getAttribute("contextResourceId"));
			ContextResource       contextResource   = null;
			ContextResourceLoader resourceLoader    = null;

			if(getResourceDir().length() > 0)
				resourceLoader = new ContextResourceLoader(getResourceDir(), ContextConstants.DEFAULT_RESOURCE_ID);
			else
				resourceLoader = new ContextResourceLoader();

			if(contextResourceId.length() > 0)
				contextResource = resourceLoader.get(contextResourceId);
			else
				contextResource = resourceLoader.getDefault();

			persistenceResource.setContextResource(contextResource);
		}
		else{
			resourceNode = resource.getNode("serverName");
			
			if(resourceNode != null){
				String serverName = StringUtil.trim(resourceNode.getValue());

				if(serverName.length() == 0)
					throw new InvalidResourceException(getResourceId(), resourceNode.getText());

				persistenceResource.setServerName(serverName);
			}
			else
				throw new InvalidResourceException(getResourceId(), resource.getText());

			resourceNode = resource.getNode("serverPort");
			
			if(resourceNode != null){
				try{
					String serverPort = StringUtil.trim(resourceNode.getValue());

					persistenceResource.setServerPort(NumberUtil.parseInt(serverPort));
				}
				catch(Throwable e){
					throw new InvalidResourceException(getResourceId(), resourceNode.getText(), e);
				}
			}
			else
				throw new InvalidResourceException(getResourceId(), resource.getText());

			resourceNode = resource.getNode("user");
			
			if(resourceNode != null){
				String user = StringUtil.trim(resourceNode.getValue());

				if(user.length() == 0)
					throw new InvalidResourceException(getResourceId(), resourceNode.getText());

				persistenceResource.setUser(user);
			}
			else
				throw new InvalidResourceException(getResourceId(), resource.getText());

			resourceNode = resource.getNode("password");
			
			if(resourceNode != null){
				String password = StringUtil.trim(resourceNode.getValue());

				persistenceResource.setPassword(password);
			}

			resourceNode = resource.getNode("repositoryId");
			
			if(resourceNode!= null){
				String repositoryId = StringUtil.trim(resourceNode.getValue());

				if(repositoryId.length() == 0)
					throw new InvalidResourceException(getResourceId(), resourceNode.getText());

				persistenceResource.setRepositoryId(repositoryId);
			}
			else
				throw new InvalidResourceException(getResourceId(), resource.getText());
		}

		String                           factoryResourceId = StringUtil.trim(resource.getAttribute("factoryResourceId"));
		FactoryResource                  factoryResource   = null;
		PersistenceFactoryResourceLoader resourceLoader    = null;

		if(getResourceDir().length() > 0)
			resourceLoader = new PersistenceFactoryResourceLoader(getResourceDir(), PersistenceConstants.DEFAULT_RESOURCE_ID);
		else
			resourceLoader = new PersistenceFactoryResourceLoader();

		if(factoryResourceId.length() == 0)
			factoryResource = resourceLoader.getDefault();
		else
			factoryResource = resourceLoader.get(factoryResourceId);

		persistenceResource.setFactoryResource(factoryResource);

		XmlNode optionsResource     = resource.getNode("options");
		String  optionResourceId    = "";
		String  optionResourceValue = "";

		if(optionsResource != null){
		    List<XmlNode> childNodes = optionsResource.getChildNodes();
		    
		    for(XmlNode childNode : childNodes){
				optionResourceId = StringUtil.trim(childNode.getAttribute("id"));
				
				if(optionResourceId.length() == 0)
					throw new InvalidResourceException(getResourceId(), childNode.getText());

				optionResourceValue = StringUtil.trim(childNode.getAttribute("value"));
				
				if(optionResourceValue.length() == 0)
					throw new InvalidResourceException(getResourceId(), childNode.getText());

				persistenceResource.addOption(optionResourceId, optionResourceValue);
			}
		}
		
		XmlNode root             = resource.getParent();
		XmlNode mappingsResource = root.getNode("mappings");
		
		if(mappingsResource != null){
		    List<XmlNode> childNodes = mappingsResource.getChildNodes();
		    
		    for(XmlNode childNode : childNodes)
		        persistenceResource.addMapping(childNode.getValue());
		}

		return persistenceResource;
	}
}