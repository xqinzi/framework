package br.com.concepting.framework.security.model;

import java.util.List;

import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.ExpressionModel;
import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;
import br.com.concepting.framework.model.types.ConditionType;
import br.com.concepting.framework.model.types.ContextSearchType;
import br.com.concepting.framework.model.types.ValidationType;
import br.com.concepting.framework.persistence.types.RelationType;

/**
 * Classe que define o modelo de dados para uma lista de acessos.
 * 
 * @author fvilarinho
 * @since 3.0
 */
@Model 
public class AccessListModel extends BaseModel{
    @Property(isIdentity=true)
    private Integer id = 0;
    
    @Property(isForSearch = true, searchCondition = ConditionType.CONTEXT, contextSearchType = ContextSearchType.BOTH, validations = ValidationType.REQUIRED)
    private String title = "";

    @Property(isForSearch = true, searchCondition = ConditionType.CONTEXT, contextSearchType = ContextSearchType.BOTH)
    private String description = "";

    @Property(relationType=RelationType.MANY_TO_MANY, cascadeOnSave=true)
    private List<ExpressionModel> expressions = null;
    
    @Property
    private Boolean whitelist = true;
    
    /**
     * Retorna a descrição da lista de acesso.
     * 
     * @return String contendo a descrição.
     */
    public String getDescription(){
        return description;
    }

    /**
     * Define a descrição da lista de acesso.
     * 
     * @param description String contendo a descrição.
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Indica se é uma whitelist ou não.
     * 
     * @return True/False.
     */
    public Boolean getWhitelist(){
        return isWhitelist();
    }

    /**
     * Indica se é uma whitelist ou não.
     * 
     * @return True/False.
     */
    public Boolean isWhitelist(){
        return whitelist;
    }

    /**
     * Define se é uma whitelist ou não.
     * 
     * @param whitelist True/False.
     */
    public void setWhitelist(Boolean whitelist){
        this.whitelist = whitelist;
    }

    /**
     * Retorna o identificador da lista.
     * 
     * @return Valor inteiro contendo o identificador da lista.
     */
    public Integer getId(){
        return id;
    }

    /**
     * Define o identificador da lista.
     * 
     * @param id Valor inteiro contendo o identificador da lista.
     */
    public void setId(Integer id){
        this.id = id;
    }

    /**
     * Retorna o título da lista.
     * 
     * @return String contendo o título da lista.
     */
    public String getTitle(){
        return title;
    }

    /**
     * Define o título da lista.
     * 
     * @param name String contendo o título da lista.
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * Retorna as expressões da lista de acessos.
     * 
     * @return Lista contendo as expressões.
     */
    public <E extends ExpressionModel> List<E> getExpressions(){
        return (List<E>)expressions;
    }

    /**
     * Define as expressões da lista de acessos.
     * 
     * @param expressions Lista contendo as expressões.
     */
    public <E extends ExpressionModel> void setExpressions(List<E> expressions){
        this.expressions = (List)expressions;
    }
}