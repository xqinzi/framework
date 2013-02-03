package br.com.concepting.framework.resource.exceptions;

import br.com.concepting.framework.util.exceptions.IncorrectSyntaxException;

/**
 * Classe que define a exce��o quando um recurso n�o p�de ser lido ou n�o foi encontrado.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class InvalidResourceException extends IncorrectSyntaxException{
	private String resourceId = "";
	
	/**
	 * Construtor - Inicializa objetos e/ou vari�veis internas.
	 * 
	 * @param resourceId String contendo o identificador do arquivo de configura��es.
	 */
	public InvalidResourceException(String resourceId){
	    super();
	    
	    setResourceId(resourceId);
	}

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param resourceId String contendo o identificador do arquivo de configura��es.
     * @param exception Inst�ncia da exce��o a ser encapsulada.
     */
    public InvalidResourceException(String resourceId, Throwable exception){
        super(exception);
        
        setResourceId(resourceId);
    }

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param resourceId String contendo o identificador do arquivo de configura��es.
     * @param content String do conte�do inv�lido.
     */
    public InvalidResourceException(String resourceId, String content){
		super(content);

		setResourceId(resourceId);
	}
	
    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param resourceId String contendo o identificador do arquivo de configura��es.
     * @param exception Inst�ncia da exce��o a ser encapsulada.
     */
	public InvalidResourceException(String resourceId, String content, Throwable exception){
		super(content, exception);
		
		setResourceId(resourceId);
	}

	/**
	 * Retorna o identificador do arquivo de configura��es.
	 * 
	 * @return String contendo o identificador do arquivo.
	 */
	public String getResourceId(){
		return resourceId;
	}

    /**
     * Define o identificador do arquivo de configura��es.
     * 
     * @param resourceId String contendo o identificador do arquivo.
     */
	public void setResourceId(String resourceId){
		this.resourceId = resourceId;
	}
}