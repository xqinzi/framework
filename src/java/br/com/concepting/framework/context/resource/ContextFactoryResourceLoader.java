package br.com.concepting.framework.context.resource;

import br.com.concepting.framework.resource.FactoryResourceLoader;
import br.com.concepting.framework.resource.exceptions.InvalidResourceException;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Classe responsável pela leitura/manipulação das configurações de comunicação 
 * com o servidor de aplicações.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class ContextFactoryResourceLoader extends FactoryResourceLoader{
	/**
	 * Construtor - Inicializa classe de leitura/manipulação do arquivo de configurações default 
	 * para comunicação com o servidor de aplicações.
	 * 
	 * @throws InvalidResourceException
	 */
	public ContextFactoryResourceLoader() throws InvalidResourceException{
		super();
	}

	/**
     * Construtor - Inicializa classe de leitura/manipulação de um arquivo de configurações 
     * para comunicação com o servidor de aplicações.
	 * 
	 * @param resourceId String contendo identificador do arquivo de configurações.
	 * @throws InvalidResourceException
	 */
	public ContextFactoryResourceLoader(String resourceId) throws InvalidResourceException{
		super(resourceId);
	}

	/**
     * Construtor - Inicializa classe de leitura/manipulação de um arquivo de configurações 
     * para comunicação com o servidor de aplicações.
	 * 
	 * @param resourceDir String contendo identificador do diretório de armazenamento 
	 * do arquivo de configurações.
	 * @param resourceId String contendo identificador do arquivo de configurações.
	 * @throws InvalidResourceException
	 */
	public ContextFactoryResourceLoader(String resourceDir, String resourceId) throws InvalidResourceException{
		super(resourceDir, resourceId);
	}

	/**
	 * @see br.com.concepting.framework.resource.BaseResourceLoader#parseResource()
	 */
    protected XmlNode parseResource() throws InvalidResourceException{
		XmlNode content = super.parseResource();

		content = content.getNode("contextFactory");
		
		if(content == null)
			throw new InvalidResourceException(getResourceId());

		return content;
	}
}