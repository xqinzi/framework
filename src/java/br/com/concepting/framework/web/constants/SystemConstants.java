package br.com.concepting.framework.web.constants;

import br.com.concepting.framework.resource.constants.ResourceConstants;
import br.com.concepting.framework.web.types.ScopeType;

/**
 * Classe que define as constantes utilizadas pelas classes que manipulam conte�do WEB.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public abstract class SystemConstants extends ResourceConstants{
    /**
     * Constante que define o diret�rio de armazenamento default dos scripts de p�gina.
     */
    public static final String DEFAULT_SCRIPTS_RESOURCES_DIR = "/scripts/";
    
    /**
     * Constante que define o script de p�gina comum para todos os componentes da p�gina.
     */
    public static final String DEFAULT_SCRIPT_RESOURCE_ID = DEFAULT_SCRIPTS_RESOURCES_DIR.concat("common.js");
    
    /**
     * Constante que define o diret�rio de armazenamento default dos estilos de p�gina.
     */
    public static final String DEFAULT_STYLES_RESOURCES_DIR = "/styles/";
    
    /**
     * Constante que define o estilo CSS da p�gina comum.
     */
    public static final String DEFAULT_STYLE_RESOURCE_ID = DEFAULT_STYLES_RESOURCES_DIR.concat("common.css");
    
    /**
     * Constante que define a URL default da p�gina de erros default. 
     */
    public static final String DEFAULT_ERROR_PAGE = "/errorPage.jsp";
    
    /**
     * Constante que define o diret�rio de armazenamento default das imagens da p�gina.
     */
    public static final String DEFAULT_IMAGES_RESOURCES_DIR = "/images/";
    
    /**
     * Constante que define o identificador do arquivo de configura��es default.
     */
    public static final String DEFAULT_RESOURCE_ID = DEFAULT_RESOURCES_DIR.concat("systemResources.xml");
    
    /**
     * Constante que define o escopo de armazemamento default das propriedades da p�gina.
     */
    public static final ScopeType DEFAULT_SCOPE_TYPE = ScopeType.FORM;

    /**
     * Constante que define o script de p�gina default.
     */
    public static final String DEFAULT_PAGE_SCRIPT_RESOURCE_ID = "/scripts/script.js";
    
    /**
     * Constante que define o estilo CSS da p�gina default.
     */
    public static final String DEFAULT_PAGE_STYLE_RESOURCE_ID = "/styles/style.css";
    
    /**
     * Constante que define o skin (tema) default para a p�gina.
     */
    public static final String DEFAULT_SKIN = "default";
    
    /**
     * Constante que define o identificador do servlet que recebe as requisi��es.
     */
    public static final String DEFAULT_ACTION_SERVLET_CLASS_ID = "org.apache.struts.action.ActionServlet";
    
    /**
     * Constante que define o identificador do servlet que efetua o carregamento de conte�do.
     */
    public static final String DEFAULT_CONTENT_LOADER_SERVLET_CLASS_ID = "br.com.concepting.framework.web.ContentLoaderServlet";

    /**
     * Constante que define o identificador do reposit�rio de WEB Services.
     */
    public static final String DEFAULT_WEB_SERVICES_REPOSITORY_ID = "/axis2/services/";

    /**
     * Constante que define o identificador da propriedade que armazena o idioma corrente.  
     */
    public static final String CURRENT_LANGUAGE_KEY = "org.apache.struts.action.LOCALE";
}