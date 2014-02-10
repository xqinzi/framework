package br.com.concepting.framework.resource;

import java.io.FileInputStream;
import java.util.Properties;

import br.com.concepting.framework.resource.exceptions.InvalidResourceException;

/**
 * Classe que define a estrutura básica para leitura/manipulação de arquivos de propriedades.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class PropertiesResourceLoader extends BaseResourceLoader<PropertiesResource>{
	/**
	 * Construtor - Inicializa objetos e/ou variáveis internas.
	 */
	protected PropertiesResourceLoader(){
		super();
	}

	/**
	 * Construtor - Efetua a leitura de um arquivo de propriedades específico.
	 * 
	 * @param resourceId String contendo o identificador do arquivo.
	 * @throws InvalidResourceException
	 */
	public PropertiesResourceLoader(String resourceId) throws InvalidResourceException{
		super(resourceId);
	}

	/**
	 * Construtor - Efetua a leitura de um arquivo de propriedades específico.
	 * 
	 * @param resourceDir String contendo o diretório de armazenamento do arquivo.
	 * @param resourceId String contendo o identificador do arquivo.
	 * @throws InvalidResourceException
	 */
	public PropertiesResourceLoader(String resourceDir, String resourceId) throws InvalidResourceException{
		super(resourceDir, resourceId);
	}

	/**
	 * @see br.com.concepting.framework.resource.BaseResourceLoader#parseResource()
	 */
    protected PropertiesResource parseResource() throws InvalidResourceException{
        PropertiesResource content = null;

        try{
			Properties    properties       = new Properties();
			StringBuilder resourceIdBuffer = new StringBuilder();

			resourceIdBuffer.append(getResourceId());
			resourceIdBuffer.append(".properties");
			
			if(getResourceDir().length() > 0)
				properties.load(new FileInputStream(resourceIdBuffer.toString()));
			else
				properties.load(getClass().getClassLoader().getResourceAsStream(resourceIdBuffer.toString()));

			content = new PropertiesResource(properties);

			return content;
		}
		catch(Throwable e){
			throw new InvalidResourceException(getResourceId());
		}
	}
}