package br.com.concepting.framework.exceptions;

/**
 * Classe que define a exce��o para alertas experados da aplica��o.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class ExpectedWarningException extends ExpectedException{
    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     */
	public ExpectedWarningException(){
        super();
    }

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param messageKey String contendo o identificador da chave da mensagem.
     */
    public ExpectedWarningException(String messageKey){
	    super(messageKey);
	}

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param exception Inst�ncia da exce��o a ser encapsulada.
     */
	public ExpectedWarningException(Throwable exception){
		super(exception);
	}

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param messageKey String contendo o identificador da chave da mensagem.
     * @param exception Inst�ncia da exce��o a ser encapsulada.
     */
    public ExpectedWarningException(String messageKey, Throwable exception){
        super(messageKey, exception);
    }
}