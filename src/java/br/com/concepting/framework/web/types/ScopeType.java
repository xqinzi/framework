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
	 * Constante que define o escopo de aplicação (Global).
	 */
	APPLICATION("application"),

	/**
	 * Constante que define o escopo de sessão (Para cada sessão aberta pelo usuário).
	 */
	SESSION("session"),

	/**
	 * Constante que define o escopo de requisição (Para cada requisição solicitada pelo usuário).
	 */
	REQUEST("request"),

	/**
	 * Constante que define o escopo de formulário (Armazenado em um formulário).
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
	 * Converte uma string em uma instância da constante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @return Instância da constante.
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