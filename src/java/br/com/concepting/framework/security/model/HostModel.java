package br.com.concepting.framework.security.model;

import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;

/**
 * Classe que define o modelo de dados que armazena as propriedades de um host.
 * 
 * @author fvilarinho
 * @since 3.0
 */
@Model 
public class HostModel extends BaseModel{
    @Property(isIdentity=true)
    private Integer id = 0;
    
    @Property(isForSearch=true)
    private String name = "";
    
    @Property(isForSearch=true)
    private String ip = "";

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
     * Retorna o nome do host.
     * 
     * @return String contendo o nome do host.
     */
    public String getName(){
        return name;
    }

    /**
     * Define o nome do host.
     * 
     * @param naem String contendo o nome do host.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Retorna o IP do host.
     * 
     * @return String contendo o IP do host.
     */
    public String getIp(){
        return ip;
    }

    /**
     * Define o IP do host.
     * 
     * @param ip String contendo o IP do host.
     */
    public void setIp(String ip){
        this.ip = ip;
    }
}