package br.com.concepting.framework.security.model;

import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;

@Model
public class HostModel extends BaseModel{
    @Property(isIdentity=true)
    private Integer id = 0;
    
    @Property(isForSearch=true)
    private String name = "";
    
    @Property(isForSearch=true)
    private String ip = "";

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getIp(){
        return ip;
    }

    public void setIp(String ip){
        this.ip = ip;
    }
}
