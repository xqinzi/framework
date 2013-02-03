package br.com.concepting.framework.caching;

import java.util.LinkedHashMap;
import java.util.Map;

import br.com.concepting.framework.util.types.DateFieldType;

/**
 * Classe respons�vel por gerenciar as inst�ncias de cache.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class CacherManager{
	private static CacherManager instance = null;

	private Map<String, Cacher> cachers = null;

	/**
	 * Construtor - Inicializa vari�veis e/ou objetos internos.
	 */
	private CacherManager(){
		super();

		cachers = new LinkedHashMap<String, Cacher>();
	}

	/**
	 * Retorna a �nica inst�ncia do gerenciador de cache.
	 * 
	 * @return Inst�ncia do gerenciado.
	 */
	public static CacherManager getInstance(){
		if(instance == null)
			instance = new CacherManager();

		return instance;
	}

	/**
	 * Retorna a inst�ncia de um cache.
	 * 
	 * @param id String contendo o identificador.
	 * @param timeout Valor inteiro contendo o tempo de expira��o.
	 * @return Inst�ncia do cache desejado.
	 */
	public Cacher getCacher(String id, Integer timeout){
		Cacher cacher = getCacher(id);

		cacher.setTimeout(timeout);

		return cacher;
	}

	/**
	 * Retorna a inst�ncia de um cache.
	 * 
	 * @param id String contendo o identificador.
	 * @param timeout Valor inteiro contendo o tempo de expira��o.
	 * @param timeoutType Constante que define o tipo do valor do timeout.
	 * @return Inst�ncia do cache desejado.
	 */
	public Cacher getCacher(String id, Integer timeout, DateFieldType timeoutType){
		Cacher cacher = getCacher(id, timeout);

		cacher.setTimeoutType(timeoutType);

		return cacher;
	}

	/**
	 * Retorna uma inst�ncia de cache.
	 * 
	 * @param id String contendo o identificador.
	 * @return Inst�ncia do cache desejado.
	 */
	public Cacher getCacher(String id){
		StringBuilder cacheId = new StringBuilder();

		cacheId.append(Cacher.class.getName());
		cacheId.append("@");
		cacheId.append(id);

		Cacher cacher = cachers.get(cacheId.toString());

		if(cacher == null){
			cacher = new Cacher(cacheId.toString());

			cachers.put(cacheId.toString(), cacher);
		}

		return cacher;
	}

	/**
	 * Retorna uma inst�ncia de cache.
	 * 
	 * @param clazz Classe que identifica o cache.
	 * @return Inst�ncia do cache desejado.
	 */
	public Cacher getCacher(Class clazz){
		return getCacher(clazz.getName());
	}

	/**
	 * Retorna uma inst�ncia de cache.
	 * 
	 * @param clazz Classe que identifica o cache.
	 * @param timeout Valor inteiro contendo o tempo de expira��o.
	 * @return Inst�ncia do cache desejado.
	 */
	public Cacher getCacher(Class clazz, Integer timeout){
		return getCacher(clazz.getName(), timeout);
	}

	/**
	 * Retorna uma inst�ncia de cache.
	 * 
	 * @param clazz Classe que identifica o cache.
	 * @param timeout Valor inteiro contendo o tempo de expira��o.
	 * @param timeoutType Constante que define o tipo do valor do timeout.
	 * @return Inst�ncia do cache desejado.
	 */
	public Cacher getCacher(Class clazz, Integer timeout, DateFieldType timeoutType){
		return getCacher(clazz.getName(), timeout, timeoutType);
	}
}