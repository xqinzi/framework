package br.com.concepting.framework.model.types;

import br.com.concepting.framework.util.interfaces.IEnum;

/** 
 * Classe que define as constantes das condições para pesquisa e/ou comparação de valores.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum ConditionType implements IEnum{
	/**
	 * Constante que define nenhuma condição.
	 */
	NONE("none"),

	/**
	 * Constante que define a condição igual.
	 */
	EQUAL("="),

	/**
	 * Constante que define a condição diferente.
	 */
	NOT_EQUAL("<>"),

	/**
	 * Constante que define a condição maior ou igual.
	 */
	GREATER_EQUAL_THAN(">="),

	/**
	 * Constante que define a condição maior.
	 */
	GREATER_THAN(">"),

	/**
	 * Constante que define a condição menor ou igual.
	 */
	LESS_EQUAL_THAN("<="),

	/**
	 * Constante que define a condição menor.
	 */
	LESS_THAN("<"),

	/**
	 * Constante que define a condição de pesquisa por contexto, ou seja, pesquisa por partes.
	 */
	CONTEXT("like"),

	/**
	 * Constante que define a condição de pesquisa between, ou seja, entre valores.
	 */
	BETWEEN("between"),

	/**
	 * Constante que define a condição de pesquisa por similaridade.
	 */
	SIMILARITY("similarity"),

	/**
	 * Constante que define a condição de pesquisa que irá considerar uma lista de valores.
	 */
	IN("in"),

	/**
	 * Constante que define a condição de pesquisa que não irá considerar uma lista de valores.
	 */
	NOT_IN("not in"),
	
	/**
	 * Constante que define a condição de pesquisa onde serão considerados valores nulos. 
	 */
	IS_NULL("is null"),
	
	/**
	 * Constante que define a condição de pesquisa onde não serão considerados valores nulos. 
	 */
	NOT_IS_NULL("not is null");

	private String key;

	/**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param key String contendo o valor desejado.
	 */
	private ConditionType(String key){
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
		return toConditionType((String)value);
	}

	/**
	 * Converte uma string em uma instância da constante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @return Instância da constante.
	 */
	public static ConditionType toConditionType(String value) throws IllegalArgumentException{
		if(value == null)
			throw new IllegalArgumentException();
		
		for(ConditionType constant : values())
			if(value.equals(constant.getKey()))
				return constant;

		return valueOf(value.toUpperCase());
	}
}