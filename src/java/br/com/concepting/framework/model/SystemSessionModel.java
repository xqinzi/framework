package br.com.concepting.framework.model;

import java.util.Locale;

import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;
import br.com.concepting.framework.model.types.ConditionType;
import br.com.concepting.framework.model.types.ContextSearchType;
import br.com.concepting.framework.model.types.ValidationType;

/**
 * Classe que define a estrutura básica para o modelo de dados que armazena as informações da
 * sessão de sistema.
 * 
 * @author fvilarinho
 * @since 1.0
 */
@Model
public class SystemSessionModel extends BaseModel{
    @Property(isUnique=true, isAuditable=true)
    private String id = "";
    
    @Property(validations=ValidationType.REQUIRED, isForSearch=true, searchCondition=ConditionType.CONTEXT, contextSearchType=ContextSearchType.BOTH, caseSensitiveSearch=false)
    private String ip = "";
    
    @Property(validations=ValidationType.REQUIRED, isForSearch=true, searchCondition=ConditionType.CONTEXT, contextSearchType=ContextSearchType.BOTH, caseSensitiveSearch=false)
    private String hostName = "";
    
    @Property(validations=ValidationType.REQUIRED)
    private String currentSkin = "";
    
    @Property(validations=ValidationType.REQUIRED)
    private Locale currentLanguage = null;
    
    /**
     * Retorna o identificador do skin (tema) atual.
     * 
     * @return String contendo o identificador do tema.
     */
    public String getCurrentSkin(){
        return currentSkin;
    }

    /**
     * Define o identificador do skin (tema) atual.
     * 
     * @param currentSkin String contendo o identificador do tema.
     */
    public void setCurrentSkin(String currentSkin){
        this.currentSkin = currentSkin;
    }

    /**
     * Retorna a instância contendo as propriedades do idioma atual.
     * 
     * @return Instância contendo as propriedades do idioma.
     */
    public Locale getCurrentLanguage(){
        return currentLanguage;
    }

    /**
     * Define a instância contendo as propriedades do idioma atual.
     * 
     * @param currentLanguage Instância contendo as propriedades do idioma.
     */
    public void setCurrentLanguage(Locale currentLanguage){
        this.currentLanguage = currentLanguage;
    }

    /**
     * Retorna o identificador da sessão.
     * 
     * @return String contendo o identificador da sessão.
     */
    public String getId(){
        return id;
    }
    
    /**
     * Define o identificador da sessão.
     * 
     * @param id String contendo o identificador da sessão.
     */
    public void setId(String id){
        this.id = id;
    }
    
    /**
     * Retorna o IP da sessão.
     * 
     * @return String contendo o IP da sessão.
     */
    public String getIp(){
        return ip;
    }
    
    /**
     * Define o IP da sessão.
     * 
     * @param ip String contendo o IP da sessão.
     */
    public void setIp(String ip){
        this.ip = ip;
    }
    
    /**
     * Retorna o HostName da sessão.
     * 
     * @return String contendo o HostName da sessão.
     */
    public String getHostName(){
        return hostName;
    }
    
    /**
     * Define o HostName da sessão.
     * 
     * @param hostName String contendo o HostName da sessão.
     */
    public void setHostName(String hostName){
        this.hostName = hostName;
    }
}
