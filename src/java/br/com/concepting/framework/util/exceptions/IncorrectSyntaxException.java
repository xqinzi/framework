package br.com.concepting.framework.util.exceptions;

/**
 * Classe que define a exce��o quando um conte�do possui conte�do inv�lido.
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
     * @param content String do conte�do inv�lido.
     */
    public IncorrectSyntaxException(String content){
        super();
        
        setContent(content);
    }

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param content String do conte�do inv�lido.
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
     * @return String do conte�do inv�lido.
     */
    public String getContent(){
        return content;
    }

    /**
     * Define o conte�do inv�lido.
     * 
     * @param content String do conte�do inv�lido.
     */
    public void setContent(String content){
        this.content = content;
    }
}