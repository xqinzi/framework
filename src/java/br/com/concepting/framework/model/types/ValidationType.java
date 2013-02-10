package br.com.concepting.framework.model.types;


/**
 * Classe que define as constantes dos tipos de valida��es das propriedades de um modelo de 
 * dados.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum ValidationType{
	/**
	 * Constante que define nenhum tipo de valida��o.
	 */
	NONE,

	/**
	 * Constante que define a valida��o de obrigatoriedade de preenchimento.
	 */
	REQUIRED,

	/**
	 * Constante que define a valida��o de datas/hor�rios.
	 */
	DATE_TIME,

	/**
	 * Constante que define a valida��o de valores num�ricos.
	 */
	NUMBER,

	/**
	 * Constante que define a valida��o de compara��o de dois valores.
	 */
	COMPARE,

	/**
	 * Constante que define a valida��o de contagem de palavras para um valor.
	 */
	WORD_COUNT,

	/**
	 * Constante que define a valida��o do n�mero m�nimo de caracteres de um valor.
	 */
	MINIMUM_LENGTH,

	/**
	 * Constante que define a valida��o do n�mero m�ximo de caracteres de um valor.
	 */
	MAXIMUM_LENGTH,

	/**
	 * Constante que define a valida��o de intervalo de valores.
	 */
	RANGE,

	/**
	 * Constante que define a valida��o de e-Mail.
	 */
	EMAIL,

	/**
	 * Constante que define a valida��o de uma m�scara.
	 */
	PATTERN,
	
    /**
     * Constante que define a valida��o de uma express�o regular.
     */
	REGULAR_EXPRESSION,

	/**
	 * Constante que define a valida��o customiza��o.
	 */
	CUSTOM;
}