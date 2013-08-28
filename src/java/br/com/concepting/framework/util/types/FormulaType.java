package br.com.concepting.framework.util.types;

/** 
 * Classe que define as constantes para os tipos de fórmula suportados.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public enum FormulaType{
    /**
     * Constante que define nenhuma fórmula.
     */
    NONE,
    
    /**
     * Constante que define a fórmula de somatória.
     */
    SUM,
    
    /**
     * Constante que define a fórmula de mínimo.
     */
    MIN,
    
    /**
     * Constante que define a fórmula de máximo.
     */
    MAX,
    
    /**
     * Constante que define a fórmula de média.
     */
    AVERAGE("avg");
    
    private String id;
    
    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     */
    private FormulaType(){
        setId(this.name().toLowerCase());
    }
    
    /**
     * Construtor - Define o valor da constante.
     * 
     * @param id String contendo o identificador da fórmula.
     */
    private FormulaType(String id){
        setId(id);
    }

    /**
     * Retorna o identificador do tipo da fórmula.
     * 
     * @return String contendo o identificador do tipo da fórmula.
     */
    public String getId(){
        return id;
    }

    /**
     * Define o identificador do tipo da fórmula.
     * 
     * @param id String contendo o identificador do tipo da fórmula.
     */
    public void setId(String id){
        this.id = id;
    }
}