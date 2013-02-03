package br.com.concepting.framework.util.exceptions;

/** 
 * Classe que define a exce��o para erros experados da aplica��o.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class ExpectedErrorException extends ExpectedException{
    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     */
	protected ExpectedErrorException(){
        super();
    }

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param messageKey String contendo o identificador da chave da mensagem.
     */
    public ExpectedErrorException(String messageKey){
		super(messageKey);
	}

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param exception Inst�ncia da exce��o a ser encapsulada.
     */
	public ExpectedErrorException(Throwable exception){
		super(exception);
	}

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param messageKey String contendo o identificador da chave da mensagem.
     * @param exception Inst�ncia da exce��o a ser encapsulada.
     */
    public ExpectedErrorException(String messageKey, Throwable exception){
        super(messageKey, exception);
    }
}