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
    private String nome = "";
    
    @Property(relationType=RelationType.MANY_TO_MANY)
    private List<HostModel> hosts = null;
    
    @Property
    private Boolean whitelist = false;
    
    public Boolean getWhitelist(){
        return whitelist;
    }

    public void setWhitelist(Boolean whitelist){
        this.whitelist = whitelist;
    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public <H extends HostModel> List<H> getHosts(){
        return (List<H>)hosts;
    }

    public <H extends HostModel> void setHosts(List<H> hosts){
        this.hosts = (List)hosts;
    }
}
