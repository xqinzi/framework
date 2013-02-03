package br.com.concepting.framework.util.types;

/**
 * Classe que define as constantes dos tipos de consolida��es de um dado em um arquivo RRD. 
 *
 * @author fvilarinho
 * @since 1.0
 */
public enum RrdArchiveType{
	/**
	 * Constante que define a consolida��o pela m�dia aritm�tica.
	 */
	AVERAGE,

	/**
	 * Constante que define a consolida��o pelo valor m�nimo.
	 */
	MIN,

	/**
	 * Constante que define a consolida��o pelo valor m�ximo.
	 */
	MAX,
	
	/**
	 * Constante que define a consolida��o pelo �ltimo valor.
	 */
	LAST
}
