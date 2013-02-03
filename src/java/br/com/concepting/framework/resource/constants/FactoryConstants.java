package br.com.concepting.framework.resource.constants;

/**
 * Classe que define as constantes utilizadas pelas rotinas que efetuam comunica��o com os 
 * servidores de aplica��es e/ou banco de dados.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class FactoryConstants extends ResourceConstants{
    /**
     * Constante que define o arquivo de configura��es para comunica��o de servidores de 
     * aplica��es e/ou banco de dados default.
     */
    public static final String DEFAULT_RESOURCE_ID = DEFAULT_RESOURCES_DIR.concat("factoryResources.xml");
}
