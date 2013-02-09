package br.com.concepting.framework.persistence.types;

/**
 * Classe que define as constantes para os tipos de junções do relacionamento.
 *
 * @author fvilarinho
 * @since 1.0
 */
public enum RelationJoinType{
	NONE,
	
	/**
	 * Constante que define o tipo inner join.
	 */
	INNER_JOIN,

	/**
	 * Constante que define o tipo left join.
	 */
	LEFT_JOIN,

	/**
	 * Constante que define o tipo right join.
	 */
	RIGHT_JOIN;
}