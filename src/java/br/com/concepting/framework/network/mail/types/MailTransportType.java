package br.com.concepting.framework.network.mail.types;

import br.com.concepting.framework.util.interfaces.IEnum;

/**
 * Constante que define os protocolos de transporte de mensagens de e-Mail.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum MailTransportType implements IEnum{
	/**
	 * Constante que define o protocolo de transporte SMTP.
	 */
	SMTP("smtp"),

	/**
	 * Constante que define o protocolo de transporte ESMTP.
	 */
	ESMTP("esmtp");

	private String key;

	/**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param key String contendo o valor desejado.
	 */
	private MailTransportType(String key){
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
		return toMailTransport((String)value);
	}

	/**
	 * Converte uma string em uma instância da constante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @return Instância da constante.
	 */
	public static MailTransportType toMailTransport(String value) throws IllegalArgumentException{
		if(value == null)
			throw new IllegalArgumentException();

		return valueOf(value.toUpperCase());
	}
}