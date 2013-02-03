package br.com.concepting.framework.persistence.resource;

import br.com.concepting.framework.resource.FactoryResourceLoader;
import br.com.concepting.framework.resource.exceptions.InvalidResourceException;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Classe que define a estrutura básica para leitura/manipulação de configurações de drivers de 
 * comunicação com repositórios de persistência de dados.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class PersistenceFactoryResourceLoader extends FactoryResourceLoader{
	/**
	 * Construtor - Efetua a leitura do arquivo de configurações default.
	 * 
	 * @throws InvalidResourceException
	 */
	public PersistenceFactoryResourceLoader() throws InvalidResourceException{
		super();
	}

	/**
	 * Construtor - Efetua a leitura de um arquivo de configurações específico.
	 * 
	 * @param resourceId String contendo o identificador do arquivo.
	 * @throws InvalidResourceException
	 */
	public PersistenceFactoryResourceLoader(String resourceId) throws InvalidResourceException{
		super(resourceId);
	}

	/**
	 * Construtor - Efetua a leitura de um arquivo de configurações específico.
	 * 
	 * @param resourceDir String contendo o diretório de armazenamento do arquivo.
	 * @param resourceId String contendo o identificador do arquivo.
	 * @throws InvalidResourceException
	 */
	public PersistenceFactoryResourceLoader(String resourceDir, String resourceId) throws InvalidResourceException{
		super(resourceDir, resourceId);
	}

	/**
	 * @see br.com.concepting.framework.resource.XmlResourceLoader#parseResource()
	 */
    protected <C> C parseResource() throws InvalidResourceException{
		XmlNode content = super.parseResource();

		content = content.getNode("persistenceFactory");
		if(content == null)
			throw new InvalidResourceException(getResourceId());

		return (C)content;
	}
}