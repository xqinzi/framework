package br.com.concepting.framework.exceptions;

/**
 * Classe que define a exceção quando for identificado um conteúdo com problema de sintaxe 
 * em uma expressão.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class IncorrectSyntaxException extends InternalErrorException{
    private String content = "";
    
    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     */
    public IncorrectSyntaxException(){
        super();
    }
    
    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param content String do conteúdo com problema de sintaxe.
     */
    public IncorrectSyntaxException(String content){
        super();
        
        setContent(content);
    }

    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param content String do conteúdo com problema de sintaxe.
     * @param exception Instância da exceção a ser encapsulada.
     */
    public IncorrectSyntaxException(String content, Throwable exception){
        this(exception);
        
        setContent(content);
    }

    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param exception Instância da exceção a ser encapsulada.
     */
    public IncorrectSyntaxException(Throwable exception){
        super(exception);
    }

    /**
     * Retorna o conteúdo inválido.
     * 
     * @return String do conteúdo com problema de sintaxe.
     */
    public String getContent(){
        return content;
    }

    /**
     * Define o conteúdo inválido.
     * 
     * @param content String do conteúdo com problema de sintaxe.
     */
    public void setContent(String content){
        this.content = content;
    }
}