package br.com.concepting.framework.security.model;

import java.util.List;

import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;
import br.com.concepting.framework.persistence.types.RelationType;

@Model 
public class AccessListModel extends BaseModel{
    @Property(isIdentity=true)
    private Integer id = 0;
    
    @Property(isForSearch=true)
    private String name = "";
    
    @Property(relationType=RelationType.MANY_TO_MANY)
    private List<HostModel> hosts = null;
    
    @Property
    private Boolean whitelist = false;
    
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
     * Retorna o name da lista.
     * 
     * @return String contendo o name da lista.
     */
    public String getName(){
        return name;
    }

    /**
     * Define o name da lista.
     * 
     * @param name String contendo o name da lista.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Retorna a lista de hosts.
     * 
     * @return Lista de hosts.
     */
    public <H extends HostModel> List<H> getHosts(){
        return (List<H>)hosts;
    }

    /**
     * Define a lista de hosts.
     * 
     * @param hosts Lista de hosts.
     */
    public <H extends HostModel> void setHosts(List<H> hosts){
        this.hosts = (List)hosts;
    }
}