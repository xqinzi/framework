package br.com.concepting.framework.exceptions;

/**
 * Classe que define a exce��o para alertas experados da aplica��o.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class ExpectedException extends Exception{
    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     */
    public ExpectedException(){
        super();
    }
    
    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param messageKey String contendo o identificador da chave da mensagem.
     */
	public ExpectedException(String messageKey){
		super(messageKey);
	}

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param exception Inst�ncia da exce��o a ser encapsulada.
     */
	public ExpectedException(Throwable exception){
		super(exception);
	}
	
    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param messageKey String contendo o identificador da chave da mensagem.
     * @param exception Inst�ncia da exce��o a ser encapsulada.
     */
	public ExpectedException(String messageKey, Throwable exception){
	    super(messageKey, exception);
	}
}