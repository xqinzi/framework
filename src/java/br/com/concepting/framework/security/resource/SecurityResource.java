package br.com.concepting.framework.security.resource;

import java.util.Collection;

import br.com.concepting.framework.resource.BaseResource;

/**
 * Classe responsável pelo armazenamento das configurações de segurança.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class SecurityResource extends BaseResource{
    private String             sessionClass    = "";
    private Integer            sessionTimeout  = 0;
    private Collection<String> excludePatterns = null;
    
    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     */
    public SecurityResource(){
        super();
    }
    
    /**
     * Retorna a classe do modelo de dados que armazena os dados da sessão de login.
     * 
     * @return String contendo o identificador da classe do modelo de dados da sessão.
     */
    public String getSessionClass(){
        return sessionClass;
    }
    
    /**
     * Define a classe do modelo de dados que armazena os dados da sessão de login.
     * 
     * @param sessionClass String contendo o identificador da classe do modelo de dados da sessão.
     */
    public void setSessionClass(String sessionClass){
        this.sessionClass = sessionClass;
    }
    
    /**
     * Retorna o tempo de expiração (em minutos) da sessão de login.
     * 
     * @return Valor inteiro contendo o tempo de expiração da sessão.
     */
    public Integer getSessionTimeout(){
        return sessionTimeout;
    }
    
    /**
     * Define o tempo de expiração (em minutos) da sessão de login.
     * 
     * @param sessionTimeout Valor inteiro contendo o tempo de expiração da sessão.
     */
    public void setSessionTimeout(Integer sessionTimeout){
        this.sessionTimeout = sessionTimeout;
    }
    
    /**
     * Retorna uma lista de URLs que serão excluídas no filtro de segurança.
     * 
     * @return Lista de string contendo as URLs.
     */
    public Collection<String> getExcludePatterns(){
        return excludePatterns;
    }
    
    /**
     * Define uma lista de URLs que serão excluídas no filtro de segurança.
     * 
     * @param excludePatterns Lista de string contendo as URLs.
     */
    public void setExcludePatterns(Collection<String> excludePatterns){
        this.excludePatterns = excludePatterns;
    }
}
