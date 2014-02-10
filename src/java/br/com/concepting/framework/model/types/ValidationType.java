package br.com.concepting.framework.model.types;

import br.com.concepting.framework.util.StringUtil;

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
	
    /**
     * Retorna o identificador do tipo de valida��o.
     * 
     * @return String contendo o identificador do tipo de valida��o.
     */
    public String getId(){
        String        parts[]      = StringUtil.split(toString(), "_");
        StringBuilder validationId = new StringBuilder();
        
        for(int cont = 0 ; cont < parts.length ; cont++){
            if(cont == 0)
                validationId.append(parts[cont].toLowerCase());
            else
                validationId.append(StringUtil.capitalize(parts[cont]));
        }
        
        return validationId.toString();
    }
}