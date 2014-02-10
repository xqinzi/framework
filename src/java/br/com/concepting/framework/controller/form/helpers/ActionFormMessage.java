package br.com.concepting.framework.controller.form.helpers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.struts.action.ActionMessage;

import br.com.concepting.framework.controller.form.types.ActionFormMessageType;
import br.com.concepting.framework.util.StringUtil;
 
/**
 * Classe que define uma mensagem de sistema.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class ActionFormMessage extends ActionMessage{
    private static final long serialVersionUID = -7121791309962868261L;
    
    private ActionFormMessageType type       = null;
    private Map<String, Object>   attributes = null;
    private Boolean               displayed  = false;

    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param type Constante que define o tipo de mensagem.
     * @param key String contendo o chave da mensagem.
     */
    public ActionFormMessage(ActionFormMessageType type, String key){
        super(StringUtil.replaceAll(key, type.toString().concat("."), ""));
        
        setType(type);
    }
    
    /**
     * Indica se a mensagem já foi exibida.
     * 
     * @return True/False.
     */
    public Boolean displayed(){
        return displayed;
    }

    /**
     * Indica se a mensagem já foi exibida.
     * 
     * @return True/False.
     */
    public Boolean getDisplayed(){
        return displayed();
    }

    /**
     * Define se a mensagem já foi exibida.
     * 
     * @param displayed True/False.
     */
    public void setDisplayed(Boolean displayed){
        this.displayed = displayed;
    }

    /**
     * Retorna o tipo de mensagem desejada.
     * 
     * @return Constante o tipo de mensagem desejada.
     */
    public ActionFormMessageType getType(){
        return type;
    }

    /**
     * Define o tipo de mensagem desejada.
     * 
     * @param type Constante o tipo de mensagem desejada.
     */
    public void setType(ActionFormMessageType type){
        this.type = type;
    }

    /**
     * Adiciona um novo atributo na mensagem.
     *  
     * @param name String contendo o nome do atributo.
     * @param value Instância contendo o valor do atributo.
     */
    public <O> void addAttribute(String name, O value){
        if(attributes == null)
            attributes = new LinkedHashMap<String, Object>();
        
        attributes.put(name,  value);
    }

    /**
     * Retorna um atributo da mensagem.
     * 
     * @param name String contendo o nome do atributo.
     * @return Instância contendo o valor do atributo.
     */
    @SuppressWarnings("unchecked")
    public <O> O getAttribute(String name){
        if(attributes != null)
            return (O)attributes.get(name);
        
        return null;
    }

    /**
     * Retorna o mapa de atributos da mensagem.
     * 
     * @return Mapa contendo os atributos da mensagem.
     */
    @SuppressWarnings("unchecked")
    public <O> Map<String, O> getAttributes(){
        return (Map<String, O>)attributes;
    }

    /**
     * Define o mapa de atributos da mensagem.
     * 
     * @param attributes Mapa contendo os atributos da mensagem.
     */
    @SuppressWarnings("unchecked")
    public <O> void setAttributes(Map<String, O> attributes){
        this.attributes = (Map<String, Object>)attributes;
    }
}
