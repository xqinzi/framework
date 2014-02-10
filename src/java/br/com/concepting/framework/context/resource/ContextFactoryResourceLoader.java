package br.com.concepting.framework.context.resource;

import br.com.concepting.framework.resource.FactoryResourceLoader;
import br.com.concepting.framework.resource.exceptions.InvalidResourceException;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Classe respons�vel pela leitura/manipula��o das configura��es de comunica��o 
 * com o servidor de aplica��es.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class ContextFactoryResourceLoader extends FactoryResourceLoader{
	/**
	 * Construtor - Inicializa classe de leitura/manipula��o do arquivo de configura��es default 
	 * para comunica��o com o servidor de aplica��es.
	 * 
	 * @throws InvalidResourceException
	 */
	public ContextFactoryResourceLoader() throws InvalidResourceException{
		super();
	}

	/**
     * Construtor - Inicializa classe de leitura/manipula��o de um arquivo de configura��es 
     * para comunica��o com o servidor de aplica��es.
	 * 
	 * @param resourceId String contendo identificador do arquivo de configura��es.
	 * @throws InvalidResourceException
	 */
	public ContextFactoryResourceLoader(String resourceId) throws InvalidResourceException{
		super(resourceId);
	}

	/**
     * Construtor - Inicializa classe de leitura/manipula��o de um arquivo de configura��es 
     * para comunica��o com o servidor de aplica��es.
	 * 
	 * @param resourceDir String contendo identificador do diret�rio de armazenamento 
	 * do arquivo de configura��es.
	 * @param resourceId String contendo identificador do arquivo de configura��es.
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