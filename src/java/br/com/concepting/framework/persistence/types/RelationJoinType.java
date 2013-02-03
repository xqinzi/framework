package br.com.concepting.framework.persistence.types;

import org.hibernate.sql.JoinFragment;

import br.com.concepting.framework.util.interfaces.IEnum;

/**
 * Classe que define as constantes para os tipos de junções do relacionamento.
 *
 * @author fvilarinho
 * @since 1.0
 */
public enum RelationJoinType implements IEnum{
	/**
	 * Constante que define nenhum tipo de join.
	 */
	NONE("none"),
	
	/**
	 * Constante que define o tipo inner join.
	 */
	INNER("inner join"),

	/**
	 * Constante que define o tipo left outer join.
	 */
	LEFT_OUTER("left outer join"),

	/**
	 * Constante que define o tipo right outer join.
	 */
	RIGHT_OUTER("right outer join");

	private String key;

	/**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param key String contendo o valor desejado.
	 */
	private RelationJoinType(String key){
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
		return toRelationFetchType((String)value);
	}

	/**
	 * Converte uma string em uma instância da constante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @return Instância da constante.
	 */
	public static RelationJoinType toRelationFetchType(String value) throws IllegalArgumentException{
		if(value == null) 
			throw new IllegalArgumentException();

		return valueOf(value.toUpperCase());
	}
	
	/**
	 * Retorna o identificador do join do framework Hibernate.
	 *
	 * @return Valor inteiro contendo o identificador do join.
	 */
	public Integer toJoinFragment(){
		if(this == INNER)
			return JoinFragment.INNER_JOIN;
		else if(this == LEFT_OUTER)
			return JoinFragment.LEFT_OUTER_JOIN;
		else if(this == RIGHT_OUTER)
			return JoinFragment.RIGHT_OUTER_JOIN;
		
		return null;
	}
}