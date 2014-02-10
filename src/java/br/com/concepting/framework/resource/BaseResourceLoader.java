package br.com.concepting.framework.resource;

import br.com.concepting.framework.caching.CachedObject;
import br.com.concepting.framework.caching.Cacher;
import br.com.concepting.framework.caching.CacherManager;
import br.com.concepting.framework.model.exceptions.ItemNotFoundException;
import br.com.concepting.framework.resource.exceptions.InvalidResourceException;
import br.com.concepting.framework.util.StringUtil;

/**
 * Classe que define a estrutura básica para leitura/manipulação de recursos.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class BaseResourceLoader<R>{
	protected Cacher<R> cacher      = null;
	private   R         content     = null;
	private   String    resourceDir = "";
	private   String    resourceId  = "";

	/**
	 * Construtor - Inicializa objetos e/ou variáveis internas.
	 * 
	 * @throws InvalidResourceException
	 */
	protected BaseResourceLoader(){
		super();

		cacher = CacherManager.getInstance().getCacher(getClass());
	}

	/**
	 * Construtor - Efetua a leitura de um recurso específico
	 * 
	 * @param resourceId String contendo o identificador do recurso.
	 * @throws InvalidResourceException
	 */
	protected BaseResourceLoader(String resourceId) throws InvalidResourceException{
		this("", resourceId);
	}

	/**
	 * Construtor - Efetua a leitura de um recurso específico
	 * 
	 * @param resourceDir String contendo o diretório de armazenamento do recurso.
	 * @param resourceId String contendo o identificador do recurso.
	 * @throws InvalidResourceException
	 */
	protected BaseResourceLoader(String resourceDir, String resourceId) throws InvalidResourceException{
		this();

		setResourceDir(resourceDir);

		StringBuilder buffer = new StringBuilder();

		if(resourceDir.length() > 0){
			buffer.append(resourceDir);
			buffer.append(StringUtil.getDirectorySeparator());
		}

		buffer.append(resourceId);

		setResourceId(buffer.toString());

		loadResource();
	}
	
	/**
	 * Retorna a instância do cache do arquivo de recursos.
	 *
	 * @return Instância do cache.
	 * @throws ItemNotFoundException
	 */
	protected CachedObject<R> getCachedResource() throws ItemNotFoundException{
		return cacher.get(resourceId);
	}

	/**
	 * Carrega o recurso em cache.
	 * 
	 * @throws InvalidResourceException
	 */
	protected void loadResource() throws InvalidResourceException{
		CachedObject<R> object = null;

		try{
			object  = getCachedResource();
			content = object.getContent();
		}
		catch(ItemNotFoundException e){
			setContent(parseResource());

			object = new CachedObject<R>();
			object.setId(resourceId);
			object.setContent(content);

			try{
				cacher.add(object);
			}
			catch(Throwable e1){
			}
		}
	}

	/**
	 * Efetua a conversão e a filtragem dos dados lidos.
	 * 
	 * @return Instância contendo os dados após processamento.
	 * @throws InvalidResourceException
	 */
	protected R parseResource() throws InvalidResourceException{
		return null;
	}

	/**
	 * Retorna os dados do recurso.
	 * 
	 * @return Instância contendo os dados do recurso.
	 */
    public R getContent(){
		return content;
	}

	/**
	 * Define os dados do recurso.
	 * 
	 * @param content Instância contendo os dados do recurso.
	 */
	protected void setContent(R content){
		this.content = content;
	}

	/**
	 * Retorna o identificador do recurso.
	 * 
	 * @return String contendo o identificador do recurso.
	 */
	public String getResourceId(){
		return resourceId;
	}

	/**
	 * Define o identificador do recurso.
	 * 
	 * @param resourceId String contendo o identificador do recurso.
	 */
	public void setResourceId(String resourceId){
		this.resourceId = resourceId;
	}

	/**
	 * Retorna o diretório de armazenamento do recurso.
	 * 
	 * @return String contendo o diretório de armazenamento do recurso.
	 */
	public String getResourceDir(){
		return resourceDir;
	}

	/**
	 * Define o diretório de armazenamento do recurso.
	 * 
	 * @param resourceDir String contendo o diretório de armazenamento.
	 */
	public void setResourceDir(String resourceDir){
		this.resourceDir = resourceDir;
	}

	/**
	 * Retorna a instância do cache para armazenamento.
	 * 
	 * @return Instância do cache.
	 */
	protected Cacher<R> getCacher(){
		return cacher;
	}
}