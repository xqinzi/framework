package br.com.concepting.framework.service.types;

import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.interfaces.IEnum;

/**
 * Constante que define os tipos das classes de servi�o remoto.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum ServiceType implements IEnum{
	/**
	 * Constante que define um servi�o comum.
	 */
	CLASS("class"),

	/**
	 * Constante que define o servi�o do tipo stateless (n�o mant�m estado).
	 */
	STATELESS("stateless"),

	/**
	 * Constante que define o servi�o do tipo stateful (mant�m estado).
	 */
	STATEFUL("stateful"),
	
	/**
	 * Constante que define um WEB Service.
	 */
	WEB_SERVICE("webService");

	private String key;

	/**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param key String contendo o valor desejado.
	 */
	private ServiceType(String key){
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
		return toServiceType((String)value);
	}

	/**
	 * Converte uma string em uma inst�ncia da constante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @return Inst�ncia da constante.
	 */
	public static ServiceType toServiceType(String value) throws IllegalArgumentException{
		if(value == null)
			throw new IllegalArgumentException();

		return valueOf(StringUtil.trim(value).toUpperCase());
	}
}