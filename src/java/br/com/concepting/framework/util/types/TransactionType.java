package br.com.concepting.framework.util.types;

/**
 * Classe que define as constantes para os tipos de transação.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum TransactionType{
    /**
     * Constante que define uma transação ações de leitura e escrita.
     */
    READ_WRITE,

    /**
     * Constante que define uma transação para ações de leitura.
     */
    READ_ONLY,

    /**
     * Constante que define nenhuma transação.
     */
    NONE;
}
