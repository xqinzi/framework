package br.com.concepting.framework.util.exceptions;

/** 
 * Classe que define a exceção para erros experados da aplicação.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class ExpectedErrorException extends ExpectedException{
    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     */
	protected ExpectedErrorException(){
        super();
    }

    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param messageKey String contendo o identificador da chave da mensagem.
     */
    public ExpectedErrorException(String messageKey){
		super(messageKey);
	}

    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param exception Instância da exceção a ser encapsulada.
     */
	public ExpectedErrorException(Throwable exception){
		super(exception);
	}

    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param messageKey String contendo o identificador da chave da mensagem.
     * @param exception Instância da exceção a ser encapsulada.
     */
    public ExpectedErrorException(String messageKey, Throwable exception){
        super(messageKey, exception);
    }
}