package br.com.concepting.framework.util.exceptions;

/**
 * Classe que define a exceção para erros internos da aplicação.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class InternalErrorException extends Exception{
    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     */
    protected InternalErrorException(){
        super();
    }
    
    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param messageKey String contendo o identificador da chave da mensagem.
     */
	public InternalErrorException(String messageKey){
		super(messageKey);
	}

    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param exception Instância da exceção a ser encapsulada.
     */
	public InternalErrorException(Throwable exception){
		super(exception);
	}
	
    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param messageKey String contendo o identificador da chave da mensagem.
     * @param exception Instância da exceção a ser encapsulada.
     */
	public InternalErrorException(String messageKey, Throwable exception){
	    super(messageKey, exception);
	}
}