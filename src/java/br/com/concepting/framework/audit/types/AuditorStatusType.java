package br.com.concepting.framework.audit.types;

import br.com.concepting.framework.util.interfaces.IEnum;

/**
 * Classe que define as constantes dos status de processamento da auditora.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum AuditorStatusType implements IEnum{
	/**
	 * Constante que define o identificador da mensagem de in�cio de processamento.
	 */
	INITIALIZING("init"),

	/**
	 * Constante que define o identificador da mensagem quando n�o ocorreram erros 
	 * no processamento.
	 */
	PROCESSED_WITHOUT_ERROR("processed"),

	/**
	 * Constante que define o identificador da mensagem quando ocorreram erros no 
	 * processamento.
	 */
	PROCESSED_WITH_ERROR("error");

	private String key;
	
	/**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param key String contendo o valor desejado.
	 */
	private AuditorStatusType(String key){
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
		return toAuditorStatusType((String)value);
	}
	
	/**
	 * Converte uma string em uma inst�ncia da constante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @return Inst�ncia da constante.
	 */
	public static AuditorStatusType toAuditorStatusType(String value) throws IllegalArgumentException{
		if(value == null)
			throw new IllegalArgumentException();
		
		for(AuditorStatusType constant : values())
			if(value.equals(constant.getKey()))
				return constant;
		
		throw new IllegalArgumentException(value);
	}
}