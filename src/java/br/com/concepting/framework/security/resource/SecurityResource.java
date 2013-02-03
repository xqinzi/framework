package br.com.concepting.framework.security.resource;

import java.util.Collection;

import br.com.concepting.framework.resource.BaseResource;

/**
 * Classe respons�vel pelo armazenamento das configura��es de seguran�a.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class SecurityResource extends BaseResource{
    private String             sessionClass    = "";
    private Integer            sessionTimeout  = 0;
    private Collection<String> excludePatterns = null;
    
    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     */
    public SecurityResource(){
        super();
    }
    
    /**
     * Retorna a classe do modelo de dados que armazena os dados da sess�o de login.
     * 
     * @return String contendo o identificador da classe do modelo de dados da sess�o.
     */
    public String getSessionClass(){
        return sessionClass;
    }
    
    /**
     * Define a classe do modelo de dados que armazena os dados da sess�o de login.
     * 
     * @param sessionClass String contendo o identificador da classe do modelo de dados da sess�o.
     */
    public void setSessionClass(String sessionClass){
        this.sessionClass = sessionClass;
    }
    
    /**
     * Retorna o tempo de expira��o (em minutos) da sess�o de login.
     * 
     * @return Valor inteiro contendo o tempo de expira��o da sess�o.
     */
    public Integer getSessionTimeout(){
        return sessionTimeout;
    }
    
    /**
     * Define o tempo de expira��o (em minutos) da sess�o de login.
     * 
     * @param sessionTimeout Valor inteiro contendo o tempo de expira��o da sess�o.
     */
    public void setSessionTimeout(Integer sessionTimeout){
        this.sessionTimeout = sessionTimeout;
    }
    
    /**
     * Retorna uma lista de URLs que ser�o exclu�das no filtro de seguran�a.
     * 
     * @return Lista de string contendo as URLs.
     */
    public Collection<String> getExcludePatterns(){
        return excludePatterns;
    }
    
    /**
     * Define uma lista de URLs que ser�o exclu�das no filtro de seguran�a.
     * 
     * @param excludePatterns Lista de string contendo as URLs.
     */
    public void setExcludePatterns(Collection<String> excludePatterns){
        this.excludePatterns = excludePatterns;
    }
}
