package br.com.concepting.framework.context.constants;

import br.com.concepting.framework.resource.constants.ResourceConstants;

/**
 * Classe que define as constantes utilizadas pelas rotinas de comunica��o com o 
 * servidor de aplica��o.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public abstract class ContextConstants{
    /**
     * Constante que define o identificador do arquivo de configura��es de contexto.
     */
    public static final String DEFAULT_RESOURCE_ID = ResourceConstants.DEFAULT_RESOURCES_DIR.concat("contextResources.xml");
}
