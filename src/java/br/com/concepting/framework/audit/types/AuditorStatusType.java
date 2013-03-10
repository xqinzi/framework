package br.com.concepting.framework.audit.types;

/**
 * Classe que define as constantes dos status de processamento da auditora.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum AuditorStatusType{
    /**
     * Constante que define o identificador da mensagem de inicialização do 
     * processamento.
     */
    INIT("init"),
    
	/**
	 * Constante que define o identificador da mensagem quando não ocorreram erros 
	 * no processamento.
	 */
	PROCESSED("processed"),

	/**
	 * Constante que define o identificador da mensagem quando ocorreram erros no 
	 * processamento.
	 */
	PROCESSED_WITH_ERROR("error");
    
    private String description = "";
    
    /**
     * Construtor - Define a descrição da constante.
     * 
     * @param description String contendo a descrição.
     */
    private AuditorStatusType(String description){
        setDescription(description);
    }

    /**
     * Retorna a descrição da constante.
     * 
     * @return String contendo a descrição.
     */
    public String getDescription(){
        return description;
    }

    /**
     * Define a descrição da constante.
     * 
     * @param description String contendo a descrição.
     */
    public void setDescription(String description){
        this.description = description;
    }
}