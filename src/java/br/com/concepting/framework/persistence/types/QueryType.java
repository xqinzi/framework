package br.com.concepting.framework.persistence.types;

/**
 * Classe que define as constantes para os tipos de queries.
 *
 * @author fvilarinho
 * @since 1.0
 */
public enum QueryType{
	/**
	 * Constante que define o tipo de query para retornar um único modelo de dados a partir de seu(s) 
	 * identificador(es).
	 */
	FIND,

	/**
	 * Constante que define o tipo de query para retornar uma lista de modelos de dados a partir da(s) 
	 * propriedade(s) de pesquisa.
	 */
	SEARCH,

	/**
	 * Constante que define o tipo de query para retornar uma lista de modelos de dados a partir de um 
	 * relacionamento.
	 */
	LOAD_REFERENCE;
}
