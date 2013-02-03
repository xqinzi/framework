package br.com.concepting.framework.util.types;

import br.com.concepting.framework.util.interfaces.IEnum;

/**
 * Classe que define as constantes para os tipos de ordena��o.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum SortOrderType implements IEnum{
	/**
	 * Constante que define nenhum tipo de ordena��o.
	 */
	NONE("none"),

	/**
	 * Constante que define a ordena��o crescente.
	 */
	ASCEND("asc"),

	/**
	 * Constante que define a ordena��o decrescente.
	 */
	DESCEND("desc");

	private String key;

	/**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param key String contendo o valor desejado.
	 */
	private SortOrderType(String key){
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
	public IEnum toEnum(Object value) throws IllegalArgumentException{
		return toSortOrderType((String)value);
	}

	/**
	 * Converte uma string em uma inst�ncia da constante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @return Inst�ncia da constante.
	 */
	public static SortOrderType toSortOrderType(String value) throws IllegalArgumentException{
		if(value == null)
			throw new IllegalArgumentException();

		for(SortOrderType constant : values())
			if(value.equals(constant.getKey()))
				return constant;
		
		return valueOf(value.toUpperCase());
	}
}