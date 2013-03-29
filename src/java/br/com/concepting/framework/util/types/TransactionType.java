package br.com.concepting.framework.util.types;

/**
 * Classe que define as constantes para os tipos de transa��o.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum TransactionType{
    /**
     * Constante que define uma transa��o a��es de leitura e escrita.
     */
    READ_WRITE,

    /**
     * Constante que define uma transa��o para a��es de leitura.
     */
    READ_ONLY,

    /**
     * Constante que define nenhuma transa��o.
     */
    NONE;
}
