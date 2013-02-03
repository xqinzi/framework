package br.com.concepting.framework.network.mail.types;

import br.com.concepting.framework.util.interfaces.IEnum;

/**
 * Constante que define os protocolos de armazenamento de mensagens de e-Mail.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum MailStorageType implements IEnum{
	/**
	 * Constante que define o protocolo de armazenamento POP3.
	 */
	POP3("pop3"),

	/**
	 * Constante que define o protocolo de armazenamento IMAP.
	 */
	IMAP("imap");

	private String key;

	/**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param key String contendo o valor desejado.
	 */
	private MailStorageType(String key){
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
		return toMailStorage((String)value);
	}

	/**
	 * Converte uma string em uma instância da constante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @return Instância da constante.
	 */
	public static MailStorageType toMailStorage(String value) throws IllegalArgumentException{
		if(value == null)
			throw new IllegalArgumentException();

		return valueOf(value.toUpperCase());
	}
}