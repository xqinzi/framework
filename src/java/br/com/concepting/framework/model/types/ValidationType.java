package br.com.concepting.framework.model.types;

import br.com.concepting.framework.util.interfaces.IEnum;

/**
 * Classe que define as constantes dos tipos de validações das propriedades de um modelo de 
 * dados.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum ValidationType implements IEnum{
	/**
	 * Constante que define nenhum tipo de validação.
	 */
	NONE("none"),

	/**
	 * Constante que define a validação de obrigatoriedade de preenchimento.
	 */
	REQUIRED("required"),

	/**
	 * Constante que define a validação de datas/horários.
	 */
	DATE_TIME("dateTime"),

	/**
	 * Constante que define a validação de valores numéricos.
	 */
	NUMBER("number"),

	/**
	 * Constante que define a validação de comparação de dois valores.
	 */
	COMPARE("compare"),

	/**
	 * Constante que define a validação de contagem de palavras para um valor.
	 */
	WORD_COUNT("wordCount"),

	/**
	 * Constante que define a validação do número mínimo de caracteres de um valor.
	 */
	MINIMUM_LENGTH("minimumLength"),

	/**
	 * Constante que define a validação do número máximo de caracteres de um valor.
	 */
	MAXIMUM_LENGTH("maximumLength"),

	/**
	 * Constante que define a validação de intervalo de valores.
	 */
	RANGE("range"),

	/**
	 * Constante que define a validação de e-Mail.
	 */
	EMAIL("email"),

	/**
	 * Constante que define a validação de uma máscara.
	 */
	PATTERN("pattern"),
	
    /**
     * Constante que define a validação de uma expressão regular.
     */
	REGULAR_EXPRESSION("regularExpression"),

	/**
	 * Constante que define a validação customização.
	 */
	CUSTOM("custom");

	private String key;

	/**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param key String contendo o valor desejado.
	 */
	private ValidationType(String key){
		setKey(key);
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IEnum#getKey()
	 */
    public <O> O getKey(){
		return (O)key;
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IEnum#setKey(java.lang.Object)
	 */
	public <O> void setKey(O key){
		this.key = (String)key;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return key;
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IEnum#toEnum(java.lang.Object)
	 */
	public <O> IEnum toEnum(O value) throws IllegalArgumentException{
		return toValidationType((String)value);
	}

	/**
	 * Converte uma string em uma instância da constante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @return Instância da constante.
	 */
	public static ValidationType toValidationType(String value) throws IllegalArgumentException{
		if(value == null)
			throw new IllegalArgumentException();
		
		for(ValidationType constant : values())
			if(value.equals(constant.getKey()))
				return constant;

		return valueOf(value.toUpperCase());
	}
}