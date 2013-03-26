package br.com.concepting.framework.model;

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
    
    @Property
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