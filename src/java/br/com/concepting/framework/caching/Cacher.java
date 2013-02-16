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
 * Classe que implementa um cache de conteúdo em memória.
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
	 * Construtor - Inicializa variáveis e/ou objetos internos.
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
	 * @param timeout Valor inteiro contendo o tempo de expiração.
	 */
	public Cacher(String id, Integer timeout){
		this(id);

		this.timeout = timeout;
	}

	/**
	 * Construtor - Inicializa o cache.
	 * 
	 * @param id String contendo o identificador do cache.
	 * @param timeout Valor inteiro contendo o tempo de expiração.
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
	 * Expira todos os conteúdos armazenados.
	 */
	public void expire(){
		history.clear();
	}

	/**
	 * Adiciona um novo conteúdo.
	 * 
	 * @param object Instância do conteúdo.
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
	 * Atualiza um conteúdo já armazenado.
	 * 
	 * @param object Instância do conteúdo.
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
	 * Remove um conteúdo já armazenado.
	 * 
	 * @param object Instância do conteúdo
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
	 * Retorna um conteúdo armazenado.
	 * 
	 * @param id String contendo o identificador do conteúdo.
	 * @return Instância do conteúdo.
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
	 * Verifica se um conteúdo está armazenado.
	 * 
	 * @param object Instância do conteúdo.
	 * @return True/False.
	 */
	public Boolean contains(CachedObject object){
		return history.containsKey(object.getId());
	}

	/**
	 * Retorna o tempo de expiração.
	 * 
	 * @return Valor inteiro contendo o tempo de expiração.
	 */
	public Integer getTimeout(){
		return timeout;
	}

	/**
	 * Define o tempo de expiração.
	 * 
	 * @param timeout Valor inteiro contendo o tempo de expiração.
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