package br.com.concepting.framework.resource.exceptions;

import br.com.concepting.framework.util.exceptions.IncorrectSyntaxException;

/**
 * Classe que define a exceção quando um recurso não pôde ser lido ou não foi encontrado.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class InvalidResourceException extends IncorrectSyntaxException{
	private String resourceId = "";
	
	/**
	 * Construtor - Inicializa objetos e/ou variáveis internas.
	 * 
	 * @param resourceId String contendo o identificador do arquivo de configurações.
	 */
	public InvalidResourceException(String resourceId){
	    super();
	    
	    setResourceId(resourceId);
	}

    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param resourceId String contendo o identificador do arquivo de configurações.
     * @param exception Instância da exceção a ser encapsulada.
     */
    public InvalidResourceException(String resourceId, Throwable exception){
        super(exception);
        
        setResourceId(resourceId);
    }

    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param resourceId String contendo o identificador do arquivo de configurações.
     * @param content String do conteúdo inválido.
     */
    public InvalidResourceException(String resourceId, String content){
		super(content);

		setResourceId(resourceId);
	}
	
    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param resourceId String contendo o identificador do arquivo de configurações.
     * @param exception Instância da exceção a ser encapsulada.
     */
	public InvalidResourceException(String resourceId, String content, Throwable exception){
		super(content, exception);
		
		setResourceId(resourceId);
	}

	/**
	 * Retorna o identificador do arquivo de configurações.
	 * 
	 * @return String contendo o identificador do arquivo.
	 */
	public String getResourceId(){
		return resourceId;
	}

    /**
     * Define o identificador do arquivo de configurações.
     * 
     * @param resourceId String contendo o identificador do arquivo.
     */
	public void setResourceId(String resourceId){
		this.resourceId = resourceId;
	}
}