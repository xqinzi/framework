package br.com.concepting.framework.caching;

import java.io.Serializable;
import java.util.Date;

/**
 * Classe respons�vel por armazenar um conte�do em cache.
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
	 * Retorna o identificador do conte�do.
	 * 
	 * @return String contendo o identificador.
	 */
	public String getId(){
		return id;
	}

	/**
	 * Define o identificador do conte�do.
	 * 
	 * @param id String contendo o identificador.
	 */
	public void setId(String id){
		this.id = id;
	}

	/**
	 * Flag que define se a inst�ncia est� em cache.
	 * 
	 * @return True/False.
	 */
	public Boolean isCached(){
		if(cache != null)
			return true;

		return false;
	}

	/**
	 * Retorna o conte�do da inst�ncia.
	 * 
	 * @return Inst�ncia do objeto que define o conte�do.
	 */
    public <C> C getContent(){
		return (C)content;
	}

	/**
	 * Define o conte�do da inst�ncia.
	 * 
	 * @param content Inst�ncia do objeto que define o conte�do.
	 */
	public <C> void setContent(C content){
		this.content = content;
	}

	/**
	 * Retorna a data de entrada ao conte�do no cache. Geralmente � utilizado para 
	 * controle de expira��o.
	 * 
	 * @return Data de entrada do conte�do no cache.
	 */
	public Date getCacheDate(){
		return cacheDate;
	}

	/**
	 * Define a data de entrada ao conte�do no cache. Geralmente � utilizado para 
	 * controle de expira��o.
	 * 
	 * @param cacheDate Data de entrada do conte�do no cache.
	 */
	public void setCacheDate(Date cacheDate){
		this.cacheDate = cacheDate;
	}

	/**
	 * Retorna a �ltima data de acesso ao conte�do. Geralmente � utilizado para 
	 * controle de expira��o.
	 * 
	 * @return Data do �ltimo acesso.
	 */
	public Date getLastAccess(){
		return lastAccess;
	}

	/**
	 * Define a �ltima data de acesso ao conte�do. Geralmente � utilizado para 
	 * controle de expira��o.
	 * 
	 * @param lastAccess Data do �ltimo acesso.
	 */
	public void setLastAccess(Date lastAccess){
		this.lastAccess = lastAccess;
	}

	/**
	 * Vincula o conte�do � uma inst�ncia de cache.
	 * 
	 * @param cache Inst�ncia do cache.
	 */
	protected void attach(Cacher cache){
		this.cache = cache;
	}
}