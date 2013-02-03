package br.com.concepting.framework.caching;

import java.io.Serializable;
import java.util.Date;

/**
 * Classe responsável por armazenar um conteúdo em cache.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class CachedObject implements Serializable{
	private String id         = null;
	private Date   lastAccess = null;
	private Date   cacheDate  = null;
	private Object content    = null;
	private Cacher cache      = null;

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
    public <C> C getContent(){
		return (C)content;
	}

	/**
	 * Define o conteúdo da instância.
	 * 
	 * @param content Instância do objeto que define o conteúdo.
	 */
	public <C> void setContent(C content){
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
	public void setCacheDate(Date cacheDate){
		this.cacheDate = cacheDate;
	}

	/**
	 * Retorna a última data de acesso ao conteúdo. Geralmente é utilizado para 
	 * controle de expiração.
	 * 
	 * @return Data do último acesso.
	 */
	public Date getLastAccess(){
		return lastAccess;
	}

	/**
	 * Define a última data de acesso ao conteúdo. Geralmente é utilizado para 
	 * controle de expiração.
	 * 
	 * @param lastAccess Data do último acesso.
	 */
	public void setLastAccess(Date lastAccess){
		this.lastAccess = lastAccess;
	}

	/**
	 * Vincula o conteúdo à uma instância de cache.
	 * 
	 * @param cache Instância do cache.
	 */
	protected void attach(Cacher cache){
		this.cache = cache;
	}
}