package br.com.concepting.framework.persistence.constants;

import br.com.concepting.framework.resource.constants.ResourceConstants;

/**
 * Classe que define as constantes utilizadas nas rotinas de persist�ncia.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public abstract class PersistenceConstants extends ResourceConstants{
    /**
     * Constante que define o diret�rio de armazenamento default dos mapeamentos de 
     * persist�ncia dos modelos de dados.
     */
    public static final String DEFAULT_MAPPINGS_DIR = DEFAULT_RESOURCES_DIR.concat("mappings/");
    
    /**
     * Constante que define o identificador do arquivo de configura�es de persist�ncia 
     * default. 
     */
    public static final String DEFAULT_RESOURCE_ID = DEFAULT_RESOURCES_DIR.concat("persistenceResources.xml");
    
    /**
     * Constante que define o identificador da chave que define o atributo de n�mero m�ximo de 
     * registros que devem retornar em uma query.
     */
    public static final String DEFAULT_QUERY_MAXIMUM_RESULTS_KEY = "hibernate.query_maximum_results";
    
    /**
     * Constante que define o n�mero m�ximo default de registros que devem retornar em uma 
     * query.
     */
    public static final Integer DEFAULT_QUERY_MAXIMUM_RESULTS = 3000;
}
