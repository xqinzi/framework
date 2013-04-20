package br.com.concepting.framework.model;

import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;

@Model 
public class ExpressionModel extends BaseModel{
    @Property(isIdentity=true)
    private Integer id = 0;
    
    @Property
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

    public String getValue(){
        return value;
    }

    public void setValue(String value){
        this.value = value;
    }
}