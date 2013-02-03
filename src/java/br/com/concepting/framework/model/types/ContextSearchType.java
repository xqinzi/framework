package br.com.concepting.framework.model.types;

import br.com.concepting.framework.util.interfaces.IEnum;

/**
 * Classe que define as constantes para os tipos de pesquisa por contexto.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum ContextSearchType implements IEnum{
	/**
	 * Constante que define o tipo de pesquisa por prefixo, ou seja, não irá considerar como 
	 * filtro da pesquisa o prefixo de um valor.
	 */
	PREFIX("prefix"),

	/**
	 * Constante que define o tipo de pesquisa por sufixo, ou seja, não irá considerar como 
	 * filtro da pesquisa o sufixo de um valor.
	 */
	SUFFIX("suffix"),

	/**
	 * Constante que define o tipo de pesquisa por prefixo e sufixo, ou seja, não irá considerar 
	 * como filtro da pesquisa o prefixo e o sufixo de um valor.
	 */
	BOTH("both");

	private String key;

	/**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param key String contendo o valor desejado.
	 */
	private ContextSearchType(String key){
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
		return toContextSearchType((String)value);
	}

	/**
	 * Converte uma string em uma instância da constante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @return Instância da constante.
	 */
	public static ContextSearchType toContextSearchType(String value) throws IllegalArgumentException{
		if(value == null) 
			throw new IllegalArgumentException();

		return valueOf(value.toUpperCase());
	}
}