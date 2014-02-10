package br.com.concepting.framework.resource;

import java.io.FileInputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import br.com.concepting.framework.caching.CachedObject;
import br.com.concepting.framework.model.exceptions.ItemNotFoundException;
import br.com.concepting.framework.resource.constants.ResourceConstants;
import br.com.concepting.framework.resource.exceptions.InvalidResourceException;
import br.com.concepting.framework.util.StringUtil;

/** 
 * Classe que define a estrutura b�sica para leitura/manipula��o de arquivos de propriedades 
 * para internacionaliza��o.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class I18nResourceLoader extends PropertiesResourceLoader{
	private Locale language = null;

	/**
	 * Construtor - Efetua a leitura do arquivo de propriedades default.
	 * 
	 * @param language Inst�ncia contendo as configura��es do idioma desejadas.
	 * @throws InvalidResourceException
	 */
	public I18nResourceLoader(Locale language) throws InvalidResourceException{
		this(ResourceConstants.DEFAULT_I18N_RESOURCE_ID, language);
	}

	/**
	 * Construtor - Efetua a leitura de um arquivo de propriedades espec�fico.
	 * 
	 * @param resourceId String contendo o identificador do arquivo.
	 * @param language Inst�ncia contendo as configura��es do idioma desejadas.
	 * @throws InvalidResourceException
	 */
	public I18nResourceLoader(String resourceId, Locale language) throws InvalidResourceException{
		super();

		setResourceId(resourceId);
		setLanguage(language);

		loadResource();
	}
	
	/**
	 * Construtor - Efetua a leitura de um arquivo de propriedades espec�fico.
	 * 
	 * @param resourceDir String contendo o diret�rio de armazenamento do arquivo.
	 * @param resourceId String contendo o identificador do arquivo.
	 * @param language Inst�ncia contendo as configura��es do idioma desejadas.
	 * @throws InvalidResourceException
	 */
	public I18nResourceLoader(String resourceDir, String resourceId, Locale language) throws InvalidResourceException{
		super();

		setResourceDir(resourceDir);
		setResourceId(resourceId);
		setLanguage(language);

		loadResource();
	}

	/**
	 * Retorna a inst�ncia contendo as configura��es do idioma atuais.
	 *
	 * @return Inst�ncia contendo as configura��es do idioma.
	 */
	public Locale getLanguage(){
    	return language;
    }

	/**
	 * Define a inst�ncia contendo as configura��es do idioma atuais.
	 *
	 * @param language Inst�ncia contendo as configura��es do idioma.
	 */
	public void setLanguage(Locale language){
    	this.language = language;
    }
	
	/**
	 * @see br.com.concepting.framework.resource.BaseResourceLoader#getCachedResource()
	 */
    protected CachedObject<PropertiesResource> getCachedResource() throws ItemNotFoundException{
    	StringBuilder buffer = new StringBuilder();
    	
    	buffer.append(getResourceId());
    	buffer.append("_");
    	buffer.append(getLanguage());
    	
	    return cacher.get(buffer.toString());
    }

	/**
	 * @see br.com.concepting.framework.resource.BaseResourceLoader#parseResource()
	 */
    protected PropertiesResource parseResource() throws InvalidResourceException{
		try{
			StringBuilder resourceId = new StringBuilder();
			
			resourceId.append(getResourceDir());
			resourceId.append(StringUtil.replaceAll(getResourceId(), ".", "/"));
			
			PropertiesResource content = null;
			
			if(getResourceDir().length() == 0){
				ResourceBundle bundle  = ResourceBundle.getBundle(resourceId.toString(), language);
				
				content = new PropertiesResource(bundle);
			}
			else{
				resourceId.append("_");
				resourceId.append(language.toString());
				resourceId.append(".properties");
				
				Properties properties = new Properties();
			
				properties.load(new FileInputStream(resourceId.toString()));
				
				content = new PropertiesResource(properties);
			}

			return content;
		}
		catch(Throwable e){
			throw new InvalidResourceException(getResourceId());
		}
	}
}