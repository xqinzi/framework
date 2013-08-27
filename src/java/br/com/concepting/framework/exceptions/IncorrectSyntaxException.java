package br.com.concepting.framework.exceptions;

/**
 * Classe que define a exce��o quando for identificado um conte�do com problema de sintaxe 
 * em uma express�o.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class IncorrectSyntaxException extends InternalErrorException{
    private String content = "";
    
    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     */
    public IncorrectSyntaxException(){
        super();
    }
    
    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param content String do conte�do com problema de sintaxe.
     */
    public IncorrectSyntaxException(String content){
        super();
        
        setContent(content);
    }

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param content String do conte�do com problema de sintaxe.
     * @param exception Inst�ncia da exce��o a ser encapsulada.
     */
    public IncorrectSyntaxException(String content, Throwable exception){
        this(exception);
        
        setContent(content);
    }

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param exception Inst�ncia da exce��o a ser encapsulada.
     */
    public IncorrectSyntaxException(Throwable exception){
        super(exception);
    }

    /**
     * Retorna o conte�do inv�lido.
     * 
     * @return String do conte�do com problema de sintaxe.
     */
    public String getContent(){
        return content;
    }

    /**
     * Define o conte�do inv�lido.
     * 
     * @param content String do conte�do com problema de sintaxe.
     */
    public void setContent(String content){
        this.content = content;
    }
}