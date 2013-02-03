package br.com.concepting.framework.util.exceptions;

/**
 * Classe que define a exce��o para erros internos da aplica��o.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class InternalErrorException extends Exception{
    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     */
    protected InternalErrorException(){
        super();
    }
    
    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param messageKey String contendo o identificador da chave da mensagem.
     */
	public InternalErrorException(String messageKey){
		super(messageKey);
	}

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param exception Inst�ncia da exce��o a ser encapsulada.
     */
	public InternalErrorException(Throwable exception){
		super(exception);
	}
	
    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param messageKey String contendo o identificador da chave da mensagem.
     * @param exception Inst�ncia da exce��o a ser encapsulada.
     */
	public InternalErrorException(String messageKey, Throwable exception){
	    super(messageKey, exception);
	}
}