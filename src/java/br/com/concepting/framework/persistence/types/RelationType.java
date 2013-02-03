package br.com.concepting.framework.persistence.types;

import br.com.concepting.framework.util.interfaces.IEnum;

/**
 * Classe que define as constantes para os tipos de relacionamento de um modelo de dados para 
 * persistência.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum RelationType implements IEnum{
	/**
	 * Constante que define nenhum tipo de relacionamento.
	 */
	NONE("none"),

	/**
	 * Constante que define o tipo 1x1.
	 */
	ONE_TO_ONE("oneToOne"),

	/**
	 * Constante que define o tipo 1xN.
	 */
	ONE_TO_MANY("oneToMany"),

	/**
	 * Constante que define o tipo NxN.
	 */
	MANY_TO_MANY("manyToMany");

	private String key;
	
	/**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param key String contendo o valor desejado.
	 */
	private RelationType(String key){
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
		return toRelationType((String)value);
	}

	/**
	 * Converte uma string em uma instância da constante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @return Instância da constante.
	 */
	public static RelationType toRelationType(String value) throws IllegalArgumentException{
		if(value == null)
			throw new IllegalArgumentException();

		for(RelationType constant : values()){
			if(value.equals(constant.getKey()))
				return constant;
		}
		
		throw new IllegalArgumentException(value);
	}
}