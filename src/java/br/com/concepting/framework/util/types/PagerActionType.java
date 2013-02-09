package br.com.concepting.framework.util.types;

/**
 * Classe que define as constantes para paginação.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum PagerActionType{
	/**
	 * Constante que define a ação para posicionar na primeira página.
	 */
	FIRST_PAGE,

	/**
	 * Constante que define a ação para posicionar na página anterior.
	 */
	PREVIOUS_PAGE,

	/**
	 * Constante que define a ação para posicionar na próxima página.
	 */
	NEXT_PAGE,

	/**
	 * Constante que define a ação para posicionar na última página.
	 */
	LAST_PAGE,

	/**
	 * Constante que define a ação para atualizar a página atual.
	 */
	REFRESH_PAGE;
}