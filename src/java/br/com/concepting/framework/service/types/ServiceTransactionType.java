package br.com.concepting.framework.service.types;

/**
 * Classe que define as constantes para os tipos de transa��o de uma classe de servi�o.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum ServiceTransactionType{
    /**
     * Constante que define uma transa��o de leitura e escrita.
     */
    READ_WRITE,

    /**
     * Constante que define uma transa��o de somente leitura.
     */
    READ_ONLY,

    /**
     * Constante que define nenhuma transa��o.
     */
    NONE;
}
