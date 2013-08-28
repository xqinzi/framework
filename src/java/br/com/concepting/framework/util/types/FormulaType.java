package br.com.concepting.framework.util.types;

/** 
 * Classe que define as constantes para os tipos de f�rmula suportados.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public enum FormulaType{
    /**
     * Constante que define nenhuma f�rmula.
     */
    NONE,
    
    /**
     * Constante que define a f�rmula de somat�ria.
     */
    SUM,
    
    /**
     * Constante que define a f�rmula de m�nimo.
     */
    MIN,
    
    /**
     * Constante que define a f�rmula de m�ximo.
     */
    MAX,
    
    /**
     * Constante que define a f�rmula de m�dia.
     */
    AVERAGE("avg");
    
    private String id;
    
    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     */
    private FormulaType(){
        setId(this.name().toLowerCase());
    }
    
    /**
     * Construtor - Define o valor da constante.
     * 
     * @param id String contendo o identificador da f�rmula.
     */
    private FormulaType(String id){
        setId(id);
    }

    /**
     * Retorna o identificador do tipo da f�rmula.
     * 
     * @return String contendo o identificador do tipo da f�rmula.
     */
    public String getId(){
        return id;
    }

    /**
     * Define o identificador do tipo da f�rmula.
     * 
     * @param id String contendo o identificador do tipo da f�rmula.
     */
    public void setId(String id){
        this.id = id;
    }
}