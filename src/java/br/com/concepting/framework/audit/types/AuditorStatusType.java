package br.com.concepting.framework.audit.types;

/**
 * Classe que define as constantes dos status de processamento da auditora.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum AuditorStatusType{
    /**
     * Constante que define o identificador da mensagem de inicializa��o do 
     * processamento.
     */
    INIT,
    
	/**
	 * Constante que define o identificador da mensagem quando n�o ocorreram erros 
	 * no processamento.
	 */
	PROCESSED,

	/**
	 * Constante que define o identificador da mensagem quando ocorreram erros no 
	 * processamento.
	 */
	ERROR;

}