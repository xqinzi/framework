package br.com.concepting.framework.util.types;

import br.com.concepting.framework.util.interfaces.IEnum;

/**
 * Classe que define as constantes para paginação.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum PagerActionType implements IEnum{
	/**
	 * Constante que define a ação para posicionar na primeira página.
	 */
	FIRST_PAGE("firstPage"),

	/**
	 * Constante que define a ação para posicionar na página anterior.
	 */
	PREVIOUS_PAGE("previousPage"),

	/**
	 * Constante que define a ação para posicionar na próxima página.
	 */
	NEXT_PAGE("nextPage"),

	/**
	 * Constante que define a ação para posicionar na última página.
	 */
	LAST_PAGE("lastPage"),

	/**
	 * Constante que define a ação para atualizar a página atual.
	 */
	REFRESH_PAGE("refreshPage");

	private String key;

	/**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param key String contendo o valor desejado.
	 */
	private PagerActionType(String key){
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
		return toPagerActionType((String)value);
	}

	/**
	 * Converte uma string em uma instância da constante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @return Instância da constante.
	 */
	public static PagerActionType toPagerActionType(String value) throws IllegalArgumentException{
		if(value == null)
			throw new IllegalArgumentException();

		for(PagerActionType constant : values())
			if(value.equals(constant.getKey()))
				return constant;
		
		return valueOf(value.toUpperCase());
	}
}