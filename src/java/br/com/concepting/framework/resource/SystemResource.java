package br.com.concepting.framework.resource;

import java.util.Collection;
import java.util.Locale;

import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Classe respons�vel por armazenar as configura��es do sistema.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class SystemResource extends BaseResource<XmlNode>{
    private static final long serialVersionUID = -1115220620296191917L;
    
    private String             loginPage       = "";
    private String             mainPage        = "";
    private Collection<Locale> languages       = null;
    private Locale             defaultLanguage = null;
    private Collection<String> skins           = null;
    private String             defaultSkin     = "";
    
    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     */
    public SystemResource(){
        super();
    }
    
    /**
     * Retorna a URL da p�gina de login do sistema.
     * 
     * @return String contendo a URL da p�gina de login.
     */
    public String getLoginPage(){
        return loginPage;
    }
    
    /**
     * Define a URL da p�gina de login do sistema.
     * 
     * @param loginPage String contendo a URL da p�gina de login.
     */
    public void setLoginPage(String loginPage){
        this.loginPage = loginPage;
    }
    
    /**
     * Retorna a URL da p�gina principal do sistema.
     * 
     * @return String contendo a URL da p�gina principal.
     */
    public String getMainPage(){
        return mainPage;
    }
    
    /**
     * Define a URL da p�gina principal do sistema.
     * 
     * @param mainPage String contendo a URL da p�gina principal.
     */
    public void setMainPage(String mainPage){
        this.mainPage = mainPage;
    }
    
    /**
     * Retorna a lista de skins (temas) dispon�veis.
     * 
     * @return Lista de skins (temas) dispon�veis.
     */
    public Collection<String> getSkins(){
        return skins;
    }
    
    /**
     * Define a lista de skins (temas) dispon�veis.
     * 
     * @param skins Lista de skins (temas) dispon�veis.
     */
    public void setSkins(Collection<String> skins){
        this.skins = skins;
    }

    /**
     * Retorna a lista de idiomas dispon�veis.
     * 
     * @return Lista de idiomas dispon�veis.
     */
    public Collection<Locale> getLanguages(){
        return languages;
    }

    /**
     * Define a lista de idiomas dispon�veis.
     * 
     * @param languages Lista de idiomas dispon�veis.
     */
    public void setLanguages(Collection<Locale> languages){
        this.languages = languages;
    }

    /**
     * Retorna o idioma default do sistema.
     * 
     * @return Inst�ncia contendo as propriedades do idioma.
     */
    public Locale getDefaultLanguage(){
        return defaultLanguage;
    }

    /**
     * Define o idioma default do sistema.
     * 
     * @param defaultLanguage Inst�ncia contendo as propriedades do idioma.
     */
    public void setDefaultLanguage(Locale defaultLanguage){
        this.defaultLanguage = defaultLanguage;
    }

    /**
     * Retorna o skin (tema) default do sistema.
     * 
     * @return String contendo o identificador do skin (tema) default.
     */
    public String getDefaultSkin(){
        return defaultSkin;
    }

    /**
     * Define o skin (tema) default do sistema.
     * 
     * @param defaultSkin String contendo o identificador do skin (tema) default.
     */
    public void setDefaultSkin(String defaultSkin){
        this.defaultSkin = defaultSkin;
    }
}
