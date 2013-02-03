package br.com.concepting.framework.util.types;

import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.interfaces.IEnum;

/**
 * Classe que define as constantes para os tipos de alinhamento.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum AlignmentType implements IEnum{
    /**
     * Constante que define o alinhamento à esquerda.
     */
	LEFT("left"),

    /**
     * Constante que define o alinhamento centralizado.
     */
	CENTER("center"),

    /**
     * Constante que define o alinhamento à direita.
     */
	RIGHT("right"),
	
    /**
     * Constante que define o alinhamento no topo.
     */
	TOP("top"),
	
    /**
     * Constante que define o alinhamento no meio.
     */
	MIDDLE("middle"),
	
    /**
     * Constante que define o alinhamento no rodapé.
     */
	BOTTOM("bottom");

	private String key;

	/**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param key String contendo o valor desejado.
	 */
	private AlignmentType(String key){
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
		return toAlignmentType((String)value);
	}

	/**
	 * Converte uma string em uma instância da constante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @return Instância da constante.
	 */
	public static AlignmentType toAlignmentType(String value) throws IllegalArgumentException{
		return valueOf(StringUtil.trim(value).toUpperCase());
	}
}