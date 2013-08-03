package br.com.concepting.framework.model;

import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;
import br.com.concepting.framework.model.types.ValidationType;

/**
 * Classe que define a estrutura básica para o modelo de dados para uma expressão.
 * 
 * @author fvilarinho
 * @since 3.0
 */
@Model 
public class ExpressionModel extends BaseModel{
    @Property(isIdentity=true)
    private Integer id = 0;
    
    @Property(validations=ValidationType.REQUIRED, isUnique=true)
    private String value = "";

    /**
     * Retorna o identificador do host.
     * 
     * @return Valor inteiro contendo o identificador do host.
     */
    public Integer getId(){
        return id;
    }

    /**
     * Define o identificador do host.
     * 
     * @param id Valor inteiro contendo o identificador do host.
     */
    public void setId(Integer id){
        this.id = id;
    }

    /**
     * Retorna o valor da expressão.
     * 
     * @return String contendo o valor da expressão.
     */
    public String getValue(){
        return value;
    }

    /**
     * Define o valor da expressão.
     * 
     * @param value String contendo o valor da expressão.
     */
    public void setValue(String value){
        this.value = value;
    }
}