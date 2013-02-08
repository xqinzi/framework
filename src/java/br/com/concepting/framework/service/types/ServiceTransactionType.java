package br.com.concepting.framework.service.types;

/**
 * Classe que define as constantes para os tipos de transação de uma classe de serviço.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum ServiceTransactionType{
    /**
     * Constante que define uma transação de leitura e escrita.
     */
    READ_WRITE,

    /**
     * Constante que define uma transação de somente leitura.
     */
    READ_ONLY,

    /**
     * Constante que define nenhuma transação.
     */
    NONE;
}
