package br.com.concepting.framework.model.types;


/**
 * Classe que define as constantes dos tipos de validações das propriedades de um modelo de 
 * dados.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum ValidationType{
	/**
	 * Constante que define nenhum tipo de validação.
	 */
	NONE,

	/**
	 * Constante que define a validação de obrigatoriedade de preenchimento.
	 */
	REQUIRED,

	/**
	 * Constante que define a validação de datas/horários.
	 */
	DATE_TIME,

	/**
	 * Constante que define a validação de valores numéricos.
	 */
	NUMBER,

	/**
	 * Constante que define a validação de comparação de dois valores.
	 */
	COMPARE,

	/**
	 * Constante que define a validação de contagem de palavras para um valor.
	 */
	WORD_COUNT,

	/**
	 * Constante que define a validação do número mínimo de caracteres de um valor.
	 */
	MINIMUM_LENGTH,

	/**
	 * Constante que define a validação do número máximo de caracteres de um valor.
	 */
	MAXIMUM_LENGTH,

	/**
	 * Constante que define a validação de intervalo de valores.
	 */
	RANGE,

	/**
	 * Constante que define a validação de e-Mail.
	 */
	EMAIL,

	/**
	 * Constante que define a validação de uma máscara.
	 */
	PATTERN,
	
    /**
     * Constante que define a validação de uma expressão regular.
     */
	REGULAR_EXPRESSION,

	/**
	 * Constante que define a validação customização.
	 */
	CUSTOM;
}