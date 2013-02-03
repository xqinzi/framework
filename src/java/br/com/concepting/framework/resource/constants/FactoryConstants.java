package br.com.concepting.framework.resource.constants;

/**
 * Classe que define as constantes utilizadas pelas rotinas que efetuam comunicação com os 
 * servidores de aplicações e/ou banco de dados.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class FactoryConstants extends ResourceConstants{
    /**
     * Constante que define o arquivo de configurações para comunicação de servidores de 
     * aplicações e/ou banco de dados default.
     */
    public static final String DEFAULT_RESOURCE_ID = DEFAULT_RESOURCES_DIR.concat("factoryResources.xml");
}
