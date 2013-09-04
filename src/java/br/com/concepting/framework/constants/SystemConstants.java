package br.com.concepting.framework.constants;

import br.com.concepting.framework.resource.constants.ResourceConstants;
import br.com.concepting.framework.util.types.ScopeType;

/**
 * Classe que define as constantes gerais utilizadas pela aplicação.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public abstract class SystemConstants{
    /**
     * Constante que define a URL default da página de erros default. 
     */
    public static final String DEFAULT_ERROR_PAGE = "/errorPage.jsp";

    /**
     * Constante que define o identificador do arquivo de configurações default.
     */
    public static final String DEFAULT_RESOURCE_ID = ResourceConstants.DEFAULT_RESOURCES_DIR.concat("systemResources.xml");
    
    /**
     * Constante que define o escopo de armazemamento default das propriedades da página.
     */
    public static final ScopeType DEFAULT_SCOPE_TYPE = ScopeType.FORM;

    /**
     * Constante que define o skin (tema) default para a página.
     */
    public static final String DEFAULT_SKIN = "default";
    
    /**
     * Constante que define o identificador do servlet que recebe as requisições.
     */
    public static final String DEFAULT_ACTION_SERVLET_CLASS_ID = "org.apache.struts.action.ActionServlet";
    
    /**
     * Constante que define o identificador do servlet que efetua o carregamento de conteúdo.
     */
    public static final String DEFAULT_CONTENT_LOADER_SERVLET_CLASS_ID = "br.com.concepting.framework.controller.ContentLoaderServlet";

    /**
     * Constante que define o identificador do repositório de WEB Services.
     */
    public static final String DEFAULT_WEB_SERVICES_REPOSITORY_ID = "/axis2/services/";
}