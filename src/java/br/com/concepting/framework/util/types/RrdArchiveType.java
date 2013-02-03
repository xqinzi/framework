package br.com.concepting.framework.util.types;

/**
 * Classe que define as constantes dos tipos de consolidações de um dado em um arquivo RRD. 
 *
 * @author fvilarinho
 * @since 1.0
 */
public enum RrdArchiveType{
	/**
	 * Constante que define a consolidação pela média aritmética.
	 */
	AVERAGE,

	/**
	 * Constante que define a consolidação pelo valor mínimo.
	 */
	MIN,

	/**
	 * Constante que define a consolidação pelo valor máximo.
	 */
	MAX,
	
	/**
	 * Constante que define a consolidação pelo último valor.
	 */
	LAST
}
