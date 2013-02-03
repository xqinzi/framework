package br.com.concepting.framework.web.types;

import br.com.concepting.framework.util.interfaces.IEnum;

/** 
 * Classe que define as constantes dos tipos de escopo de armazenamento de dados.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum ScopeType implements IEnum{
	/**
	 * Constante que define o escopo de aplica��o (Global).
	 */
	APPLICATION("application"),

	/**
	 * Constante que define o escopo de sess�o (Para cada sess�o aberta pelo usu�rio).
	 */
	SESSION("session"),

	/**
	 * Constante que define o escopo de requisi��o (Para cada requisi��o solicitada pelo usu�rio).
	 */
	REQUEST("request"),

	/**
	 * Constante que define o escopo de formul�rio (Armazenado em um formul�rio).
	 */
	FORM("form"),

	/**
	 * Constante que define o escopo de modelo de dados (Armazenado em um modelo de dados).
	 */
	MODEL("model");

	private String key;

	/**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param key String contendo o valor desejado.
	 */
	private ScopeType(String key){
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
	 * @see java.lang.Enum#toString()
	 */
	public String toString(){
		return key;
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IEnum#toEnum(java.lang.Object)
	 */
	public <O> IEnum toEnum(O value) throws IllegalArgumentException{
		return toScopeType((String)value);
	}

	/**
	 * Converte uma string em uma inst�ncia da constante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @return Inst�ncia da constante.
	 */
	public static ScopeType toScopeType(String value) throws IllegalArgumentException{
		if(value == null)
			throw new IllegalArgumentException();
		
		for(ScopeType constant : values())
			if(value.equals(constant.getKey()))
				return constant;

		throw new IllegalArgumentException(value);
	}
}