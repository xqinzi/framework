package br.com.concepting.framework.util.types;

/**
 * Classe que define as constantes dos tipos de dados em um arquivo RRD. 
 *
 * @author fvilarinho
 * @since 1.0
 */
public enum RrdDatasourceType{
	/**
	 * Constante que define que um valor real no momento. 
	 */
	GAUGE,
	
	/**
	 * Constante que define que o valor a ser armazenado � do tipo contador, ou seja, 
	 * sempre incremental mas sem sobrecarga. 
	 */
	COUNTER,

	/**
	 * Constante que define que o valor a ser armazenado � do tipo contador, ou seja, 
	 * sempre incremental mas com sobrecarga. 
	 */
	DERIVE,

	/**
	 * Constante que define que o valor ser� descartado ap�s a sua leitura. 
	 */
	ABSOLUTE;
}
