package br.com.concepting.framework.caching;

import java.io.Serializable;
import java.util.Date;

import br.com.concepting.framework.util.helpers.DateTime;

/**
 * Classe responsável por armazenar um conteúdo em cache.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class CachedObject<O> implements Serializable{
    private static final long serialVersionUID = 8165512408423991030L;
    
    private String    id         = null;
	private DateTime  lastAccess = null;
	private DateTime  cacheDate  = null;
	private O         content    = null;
	private Cacher<O> cache      = null;

	/**
	 * Retorna o identificador do conteúdo.
	 * 
	 * @return String contendo o identificador.
	 */
	public String getId(){
		return id;
	}

	/**
	 * Define o identificador do conteúdo.
	 * 
	 * @param id String contendo o identificador.
	 */
	public void setId(String id){
		this.id = id;
	}

	/**
	 * Flag que define se a instância está em cache.
	 * 
	 * @return True/False.
	 */
	public Boolean isCached(){
		if(cache != null)
			return true;

		return false;
	}

	/**
	 * Retorna o conteúdo da instância.
	 * 
	 * @return Instância do objeto que define o conteúdo.
	 */
    public O getContent(){
		return content;
	}

	/**
	 * Define o conteúdo da instância.
	 * 
	 * @param content Instância do objeto que define o conteúdo.
	 */
	public void setContent(O content){
		this.content = content;
	}

	/**
	 * Retorna a data de entrada ao conteúdo no cache. Geralmente é utilizado para 
	 * controle de expiração.
	 * 
	 * @return Data de entrada do conteúdo no cache.
	 */
	public Date getCacheDate(){
		return cacheDate;
	}

	/**
	 * Define a data de entrada ao conteúdo no cache. Geralmente é utilizado para 
	 * controle de expiração.
	 * 
	 * @param cacheDate Data de entrada do conteúdo no cache.
	 */
	public void setCacheDate(DateTime cacheDate){
		this.cacheDate = cacheDate;
	}

	/**
	 * Retorna a última data de acesso ao conteúdo. Geralmente é utilizado para 
	 * controle de expiração.
	 * 
	 * @return Data do último acesso.
	 */
	public DateTime getLastAccess(){
		return lastAccess;
	}

	/**
	 * Define a última data de acesso ao conteúdo. Geralmente é utilizado para 
	 * controle de expiração.
	 * 
	 * @param lastAccess Data do último acesso.
	 */
	public void setLastAccess(DateTime lastAccess){
		this.lastAccess = lastAccess;
	}

	/**
	 * Vincula o conteúdo à uma instância de cache.
	 * 
	 * @param cache Instância do cache.
	 */
	protected void attach(Cacher<O> cache){
		this.cache = cache;
	}
}