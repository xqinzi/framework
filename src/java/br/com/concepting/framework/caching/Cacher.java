package br.com.concepting.framework.caching;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.model.exceptions.ItemAlreadyExistsException;
import br.com.concepting.framework.model.exceptions.ItemNotFoundException;
import br.com.concepting.framework.util.DateTimeUtil;
import br.com.concepting.framework.util.types.DateFieldType;

/**
 * Classe que implementa um cache de conte�do em mem�ria.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class Cacher implements Serializable{
	private String                    id          = null;
	private Map<String, CachedObject> history     = null;
	private Integer                   timeout     = null;
	private DateFieldType             timeoutType = Constants.DEFAULT_CACHER_TIMEOUT_TYPE;

	/**
	 * Construtor - Inicializa vari�veis e/ou objetos internos.
	 */
	private Cacher(){
		super();

		history = new TreeMap<String, CachedObject>();
	}

	/**
	 * Construtor - Inicializa o cache.
	 * 
	 * @param id String contendo o identificador do cache.
	 */
	public Cacher(String id){
		this();

		this.id = id;
	}

	/**
	 * Construtor - Inicializa o cache.
	 * 
	 * @param id String contendo o identificador do cache.
	 * @param timeout Valor inteiro contendo o tempo de expira��o.
	 */
	public Cacher(String id, Integer timeout){
		this(id);

		this.timeout = timeout;
	}

	/**
	 * Construtor - Inicializa o cache.
	 * 
	 * @param id String contendo o identificador do cache.
	 * @param timeout Valor inteiro contendo o tempo de expira��o.
	 * @param timeoutType Constante que define o tipo do valor do timeout.
	 */
	public Cacher(String id, Integer timeout, DateFieldType timeoutType){
		this(id, timeout);

		this.timeoutType = timeoutType;
	}
	
	/**
	 * Retorna o tipo do valor do timeout.
	 *
	 * @return Constante que define o tipo do valor do timeout.
	 */
	public DateFieldType getTimeoutType(){
    	return timeoutType;
    }

	/**
	 * Define o tipo do valor do timeout.
	 *
	 * @param timeoutType Constante que define o tipo do valor do timeout.
	 */
	public void setTimeoutType(DateFieldType timeoutType){
    	this.timeoutType = timeoutType;
    }

	/**
	 * Define o identificador do cache.
	 * 
	 * @param id String contendo o identificador do cache.
	 */
	public void setId(String id){
    	this.id = id;
    }

	/**
	 * Expira todos os conte�dos armazenados.
	 */
	public void expire(){
		history.clear();
	}

	/**
	 * Adiciona um novo conte�do.
	 * 
	 * @param object Inst�ncia do conte�do.
	 * @throws ItemAlreadyExistsException
	 */
	public synchronized void add(CachedObject object) throws ItemAlreadyExistsException{
		if(object != null){
			if(!contains(object)){
				object.setCacheDate(new Date());
				object.setLastAccess(null);

				history.put(object.getId(), object);
			}
			else
				throw new ItemAlreadyExistsException();
		}
	}

	/**
	 * Atualiza um conte�do j� armazenado.
	 * 
	 * @param object Inst�ncia do conte�do.
	 * @throws ItemNotFoundException
	 */
	public synchronized void set(CachedObject object) throws ItemNotFoundException{
		if(object != null){
			if(contains(object)){
				object.setCacheDate(new Date());
				object.setLastAccess(null);

				history.put(object.getId(), object);
			}
			else
				throw new ItemNotFoundException();
		}
	}

	/**
	 * Remove um conte�do j� armazenado.
	 * 
	 * @param object Inst�ncia do conte�do
	 * @throws ItemNotFoundException
	 */
	public synchronized void remove(CachedObject object) throws ItemNotFoundException{
		if(object != null){
			if(contains(object))
				history.remove(object.getId());
			else
				throw new ItemNotFoundException();
		}
	}

	/**
	 * Retorna um conte�do armazenado.
	 * 
	 * @param id String contendo o identificador do conte�do.
	 * @return Inst�ncia do conte�do.
	 * @throws ItemNotFoundException
	 */
	public synchronized CachedObject get(String id) throws ItemNotFoundException{
		Date timeoutDate = null;

		for(CachedObject cachedObject : history.values()){
			if(cachedObject.getId().equals(id)){
				if(getTimeout() != null && getTimeout() > 0){
					timeoutDate = cachedObject.getLastAccess();
					if(timeoutDate == null)
						timeoutDate = cachedObject.getCacheDate();

					if(DateTimeUtil.diff(new Date(), timeoutDate, getTimeoutType()) >= timeout){
						remove(cachedObject);

						throw new ItemNotFoundException();
					}
				}

				cachedObject.setLastAccess(new Date());

				return cachedObject;
			}
		}

		throw new ItemNotFoundException();
	}

	/**
	 * Verifica se um conte�do est� armazenado.
	 * 
	 * @param object Inst�ncia do conte�do.
	 * @return True/False.
	 */
	public Boolean contains(CachedObject object){
		return history.containsKey(object.getId());
	}

	/**
	 * Retorna o tempo de expira��o.
	 * 
	 * @return Valor inteiro contendo o tempo de expira��o.
	 */
	public Integer getTimeout(){
		return timeout;
	}

	/**
	 * Define o tempo de expira��o.
	 * 
	 * @param timeout Valor inteiro contendo o tempo de expira��o.
	 */
	protected void setTimeout(Integer timeout){
		this.timeout = timeout;
	}

	/**
	 * Retorna a quantidade de elementos armazenados.
	 * 
	 * @return Valor inteiro contendo a quantidade de elementos.
	 */
	public Integer getSize(){
		return history.size();
	}

	/**
	 * Retorna o identificador do cache.
	 * 
	 * @return String contendo o identificador do cache.
	 */
	public String getId(){
		return id;
	}
}