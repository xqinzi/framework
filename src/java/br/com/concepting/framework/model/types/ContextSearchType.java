package br.com.concepting.framework.model.types;

/**
 * Classe que define as constantes para os tipos de pesquisa por contexto.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum ContextSearchType{
	/**
	 * Constante que define o tipo de pesquisa por prefixo, ou seja, não irá considerar como 
	 * filtro da pesquisa o prefixo de um valor.
	 */
	PREFIX,

	/**
	 * Constante que define o tipo de pesquisa por sufixo, ou seja, não irá considerar como 
	 * filtro da pesquisa o sufixo de um valor.
	 */
	SUFFIX,

	/**
	 * Constante que define o tipo de pesquisa por prefixo e sufixo, ou seja, não irá considerar 
	 * como filtro da pesquisa o prefixo e o sufixo de um valor.
	 */
	BOTH;
}