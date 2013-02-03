package br.com.concepting.framework.web.form.types;

import br.com.concepting.framework.util.interfaces.IEnum;

/**
 * Classe que define as constantes para os tipos de mensagens em uma aplica��o.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum ActionFormMessageType implements IEnum{
	/**
	 * Constante que define o tipo de mensagem para informa��es.
	 */
	INFO("info"),

	/**
	 * Constante que define o tipo de mensagem para avisos.
	 */
	WARNING("warning"),

	/**
	 * Constante que define o tipo de mensagem para erros.
	 */
	ERROR("error"),

	/**
	 * Constante que define o tipo de mensagem para valida��o.
	 */
	VALIDATION("validation");

	private String key;

	/**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param key String contendo o valor desejado.
	 */
	private ActionFormMessageType(String key){
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
		return toActionFormMessageType((String)value);
	}

	/**
	 * Converte uma string em uma inst�ncia da constante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @return Inst�ncia da constante.
	 */
	public static ActionFormMessageType toActionFormMessageType(String value) throws IllegalArgumentException{
		if(value == null)
			throw new IllegalArgumentException();

		return valueOf(value.toUpperCase());
	}
}