package br.com.concepting.framework.caching;

import java.io.Serializable;
import java.util.Date;

import br.com.concepting.framework.util.helpers.DateTime;

/**
 * Classe respons�vel por armazenar um conte�do em cache.
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
    public O getContent(){
		return content;
	}

	/**
	 * Define o conte�do da inst�ncia.
	 * 
	 * @param content Inst�ncia do objeto que define o conte�do.
	 */
	public void setContent(O content){
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
	public void setCacheDate(DateTime cacheDate){
		this.cacheDate = cacheDate;
	}

	/**
	 * Retorna a �ltima data de acesso ao conte�do. Geralmente � utilizado para 
	 * controle de expira��o.
	 * 
	 * @return Data do �ltimo acesso.
	 */
	public DateTime getLastAccess(){
		return lastAccess;
	}

	/**
	 * Define a �ltima data de acesso ao conte�do. Geralmente � utilizado para 
	 * controle de expira��o.
	 * 
	 * @param lastAccess Data do �ltimo acesso.
	 */
	public void setLastAccess(DateTime lastAccess){
		this.lastAccess = lastAccess;
	}

	/**
	 * Vincula o conte�do � uma inst�ncia de cache.
	 * 
	 * @param cache Inst�ncia do cache.
	 */
	protected void attach(Cacher<O> cache){
		this.cache = cache;
	}
}