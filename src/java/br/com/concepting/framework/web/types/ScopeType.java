package br.com.concepting.framework.web.types;

/** 
 * Classe que define as constantes dos tipos de escopo de armazenamento de dados.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum ScopeType{
	/**
	 * Constante que define o escopo de aplica��o (Global).
	 */
	APPLICATION,

	/**
	 * Constante que define o escopo de sess�o (Para cada sess�o aberta pelo usu�rio).
	 */
	SESSION,

	/**
	 * Constante que define o escopo de requisi��o (Para cada requisi��o solicitada pelo usu�rio).
	 */
	REQUEST,

	/**
	 * Constante que define o escopo de formul�rio (Armazenado em um formul�rio).
	 */
	FORM,

	/**
	 * Constante que define o escopo de modelo de dados (Armazenado em um modelo de dados).
	 */
	MODEL;
}