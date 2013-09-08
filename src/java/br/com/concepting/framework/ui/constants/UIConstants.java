package br.com.concepting.framework.ui.constants;

/**
 * Classe que define as constantes utilizadas na camada de apresentação.
 * 
 * @author fvilarinho
 * @since 3.0
 */ 
public abstract class UIConstants{
    /**
     * Constante que define a URL default da página de erros default. 
     */
    public static final String DEFAULT_ERROR_PAGE = "/errorPage.jsp";
    
    /**
     * Constante que define o diretório de armazenamento default dos scripts de página.
     */
    public static final String DEFAULT_SCRIPTS_RESOURCES_DIR = "/scripts/";

    /**
     * Constante que define o diretório de armazenamento default dos estilos de página.
     */
    public static final String DEFAULT_STYLES_RESOURCES_DIR = "/styles/";

    /**
     * Constante que define o diretório de armazenamento default das imagens da página.
     */
    public static final String DEFAULT_IMAGES_RESOURCES_DIR = "/images/";

    /**
     * Constante que define o identificador do arquivo de script default.
     */
    public static final String DEFAULT_COMMON_SCRIPT_RESOURCE_ID = UIConstants.DEFAULT_SCRIPTS_RESOURCES_DIR.concat("common.js");
    
    /**
     * Constante que define o identificador do arquivo de estilos CSS default.
     */
    public static final String DEFAULT_COMMON_STYLE_RESOURCE_ID = UIConstants.DEFAULT_STYLES_RESOURCES_DIR.concat("common.css");
}
