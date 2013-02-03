package br.com.concepting.framework.util.types;

import br.com.concepting.framework.util.interfaces.IEnum;

/**
 * Classe que define as constantes para pagina��o.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum PagerActionType implements IEnum{
	/**
	 * Constante que define a a��o para posicionar na primeira p�gina.
	 */
	FIRST_PAGE("firstPage"),

	/**
	 * Constante que define a a��o para posicionar na p�gina anterior.
	 */
	PREVIOUS_PAGE("previousPage"),

	/**
	 * Constante que define a a��o para posicionar na pr�xima p�gina.
	 */
	NEXT_PAGE("nextPage"),

	/**
	 * Constante que define a a��o para posicionar na �ltima p�gina.
	 */
	LAST_PAGE("lastPage"),

	/**
	 * Constante que define a a��o para atualizar a p�gina atual.
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
	 * Converte uma string em uma inst�ncia da constante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @return Inst�ncia da constante.
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