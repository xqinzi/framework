package br.com.concepting.framework.util.types;

/**
 * Classe que define as constantes para pagina��o.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum PagerActionType{
	/**
	 * Constante que define a a��o para posicionar na primeira p�gina.
	 */
	FIRST_PAGE,

	/**
	 * Constante que define a a��o para posicionar na p�gina anterior.
	 */
	PREVIOUS_PAGE,

	/**
	 * Constante que define a a��o para posicionar na pr�xima p�gina.
	 */
	NEXT_PAGE,

	/**
	 * Constante que define a a��o para posicionar na �ltima p�gina.
	 */
	LAST_PAGE,

	/**
	 * Constante que define a a��o para atualizar a p�gina atual.
	 */
	REFRESH_PAGE;
}