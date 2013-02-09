package br.com.concepting.framework.persistence.types;

/**
 * Classe que define as constantes para os tipos de relacionamento de um modelo de dados para 
 * persistência.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum RelationType{
	/**
	 * Constante que define nenhum tipo de relacionamento.
	 */
	NONE,

	/**
	 * Constante que define o tipo 1x1.
	 */
	ONE_TO_ONE,

	/**
	 * Constante que define o tipo 1xN.
	 */
	ONE_TO_MANY,

	/**
	 * Constante que define o tipo NxN.
	 */
	MANY_TO_MANY;
}