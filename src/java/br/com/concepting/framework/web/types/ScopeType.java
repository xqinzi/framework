package br.com.concepting.framework.web.types;

/** 
 * Classe que define as constantes dos tipos de escopo de armazenamento de dados.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum ScopeType{
	/**
	 * Constante que define o escopo de aplicação (Global).
	 */
	APPLICATION,

	/**
	 * Constante que define o escopo de sessão (Para cada sessão aberta pelo usuário).
	 */
	SESSION,

	/**
	 * Constante que define o escopo de requisição (Para cada requisição solicitada pelo usuário).
	 */
	REQUEST,

	/**
	 * Constante que define o escopo de formulário (Armazenado em um formulário).
	 */
	FORM,

	/**
	 * Constante que define o escopo de modelo de dados (Armazenado em um modelo de dados).
	 */
	MODEL;
}