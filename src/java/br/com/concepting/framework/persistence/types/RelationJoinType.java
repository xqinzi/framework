package br.com.concepting.framework.persistence.types;

import br.com.concepting.framework.util.StringUtil;

/**
 * Classe que define as constantes para os tipos de jun��es do relacionamento.
 *
 * @author fvilarinho
 * @since 1.0
 */
public enum RelationJoinType{
	NONE,
	
	/**
	 * Constante que define o tipo inner join.
	 */
	INNER_JOIN,

	/**
	 * Constante que define o tipo left join.
	 */
	LEFT_JOIN,

	/**
	 * Constante que define o tipo right join.
	 */
	RIGHT_JOIN;
	
    /**
     * Retorna o operador da jun��o do relacionamento.
     * 
     * @return String contendo o operador.
     */
    public String getOperator(){
        return StringUtil.replaceAll(toString().toLowerCase(), "_", " ");
    }
}