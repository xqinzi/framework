package br.com.concepting.framework.util.types;

/**
 * Classe que define as constantes para os tipos de ordenação.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum SortOrderType{
    /**
     * Constante que define nenhum tipo de ordenação.
     */
    NONE,
    
	/**
	 * Constante que define a ordenação crescente.
	 */
	ASCEND,

	/**
	 * Constante que define a ordenação decrescente.
	 */
	DESCEND;

    /**
     * Retorna o operador da junção do relacionamento.
     * 
     * @return String contendo o operador.
     */
    public String getOperator(){
        if(this == ASCEND)
            return toString().toLowerCase().substring(0, 3);
        else if(this == DESCEND)
            return toString().toLowerCase().substring(0, 4);
        
        return "";
    }
}