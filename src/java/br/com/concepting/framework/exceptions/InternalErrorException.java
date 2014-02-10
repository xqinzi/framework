package br.com.concepting.framework.exceptions;

/**
 * Classe que define a exce��o para erros internos da aplica��o.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class InternalErrorException extends Exception{
    private static final long serialVersionUID = -8931105241132588446L;

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     */
    public InternalErrorException(){
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