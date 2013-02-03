package br.com.concepting.framework.persistence.resource;

import br.com.concepting.framework.resource.FactoryResourceLoader;
import br.com.concepting.framework.resource.exceptions.InvalidResourceException;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Classe que define a estrutura b�sica para leitura/manipula��o de configura��es de drivers de 
 * comunica��o com reposit�rios de persist�ncia de dados.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class PersistenceFactoryResourceLoader extends FactoryResourceLoader{
	/**
	 * Construtor - Efetua a leitura do arquivo de configura��es default.
	 * 
	 * @throws InvalidResourceException
	 */
	public PersistenceFactoryResourceLoader() throws InvalidResourceException{
		super();
	}

	/**
	 * Construtor - Efetua a leitura de um arquivo de configura��es espec�fico.
	 * 
	 * @param resourceId String contendo o identificador do arquivo.
	 * @throws InvalidResourceException
	 */
	public PersistenceFactoryResourceLoader(String resourceId) throws InvalidResourceException{
		super(resourceId);
	}

	/**
	 * Construtor - Efetua a leitura de um arquivo de configura��es espec�fico.
	 * 
	 * @param resourceDir String contendo o diret�rio de armazenamento do arquivo.
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