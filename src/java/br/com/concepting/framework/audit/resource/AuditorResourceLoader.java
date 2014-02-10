package br.com.concepting.framework.audit.resource;

import br.com.concepting.framework.audit.constants.AuditorConstants;
import br.com.concepting.framework.resource.FactoryResource;
import br.com.concepting.framework.resource.XmlResourceLoader;
import br.com.concepting.framework.resource.exceptions.InvalidResourceException;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Classe que define a estrutura básica para leitura de arquivos de configurações 
 * de auditoria.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class AuditorResourceLoader extends XmlResourceLoader<AuditorResource>{
	/**
	 * Construtor - Efetua a leitura do arquivo de configurações default.
	 * 
	 * @throws InvalidResourceException
	 */
	public AuditorResourceLoader() throws InvalidResourceException{
		super(AuditorConstants.DEFAULT_RESOURCE_ID);
	}

	/**
	 * Construtor - Efetua a leitura de um arquivo de configurações específico.
	 * 
	 * @param resourceId String contendo o identificador do arquivo.
	 * @throws InvalidResourceException
	 */
	public AuditorResourceLoader(String resourceId) throws InvalidResourceException{
		super(resourceId);
	}

	/**
	 * Construtor - Efetua a leitura de um arquivo de configurações específico.
	 * 
	 * @param resourceDir String contendo o diretório de armazenamento do arquivo.
	 * @param resourceId String contendo o identificador do arquivo.
	 * @throws InvalidResourceException
	 */
	public AuditorResourceLoader(String resourceDir, String resourceId) throws InvalidResourceException{
		super(resourceDir, resourceId);
	}

	/**
	 * @see br.com.concepting.framework.resource.XmlResourceLoader#get(java.lang.String)
	 */
    public AuditorResource get(String id) throws InvalidResourceException{
		AuditorResource auditorResource   = super.get(id);
		XmlNode         resource          = auditorResource.getContent();
		XmlNode         appendersResource = resource.getNode("appenders");

		auditorResource.setLevel(StringUtil.trim(resource.getAttribute("level")));

		if(appendersResource != null){
            FactoryResource factoryResource  = null;
			XmlNode         appenderResource = null;
			String          appenderClass    = "";
			Integer         cont1            = 0;
			Integer         cont2            = 0;

			while(true){
				appenderResource = appendersResource.getNode(cont1);
				
				if(appenderResource == null)
					break;

				if(appenderResource.getName().equals("appender")){
					appenderClass = StringUtil.trim(appenderResource.getAttribute("class"));
					
					if(appenderClass.length() == 0)
						throw new InvalidResourceException(getResourceId(), appenderResource.getText());

					factoryResource = new FactoryResource();
					factoryResource.setClazz(appenderClass);

					XmlNode optionsResource = appenderResource.getNode("options");

					if(optionsResource != null){
						XmlNode optionResource      = null;
						String  optionResourceId    = "";
						String  optionResourceValue = "";

						cont2 = 0;
						
						while(true){
							optionResource = optionsResource.getNode(cont2);
							
							if(optionResource == null)
								break;

							optionResourceId = StringUtil.trim(optionResource.getAttribute("id"));
							
							if(optionResourceId.length() == 0)
								throw new InvalidResourceException(getResourceId(), optionResource.getText());

							optionResourceValue = StringUtil.trim(optionResource.getAttribute("value"));
							
							factoryResource.addOption(optionResourceId, optionResourceValue);

							cont2++;
						}
					}

					auditorResource.addAppender(factoryResource);
				}

				cont1++;
			}
		}

		return auditorResource;
	}
}