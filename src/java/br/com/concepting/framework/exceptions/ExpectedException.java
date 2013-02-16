package br.com.concepting.framework.exceptions;

/**
 * Classe que define a exceção para alertas experados da aplicação.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class ExpectedException extends Exception{
    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     */
    public ExpectedException(){
        super();
    }
    
    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param messageKey String contendo o identificador da chave da mensagem.
     */
	public ExpectedException(String messageKey){
		super(messageKey);
	}

    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param exception Instância da exceção a ser encapsulada.
     */
	public ExpectedException(Throwable exception){
		super(exception);
	}
	
    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param messageKey String contendo o identificador da chave da mensagem.
     * @param exception Instância da exceção a ser encapsulada.
     */
	public ExpectedException(String messageKey, Throwable exception){
	    super(messageKey, exception);
	}
}