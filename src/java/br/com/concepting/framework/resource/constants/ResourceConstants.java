package br.com.concepting.framework.resource.constants;

import br.com.concepting.framework.constants.Constants;

/**
 * Classe que define as constantes utilizadas pelas rotinas de manipulação de configurações.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class ResourceConstants extends Constants{
    /**
     * Constante que define o caminho para armazenamento para os arquivos de configurações.
     */
    public static final String DEFAULT_RESOURCES_DIR = "etc/resources/";
    
    /**
     * Constante que define o caminho para armazenamento dos arquivos de internacionalização.
     */
    public static final String DEFAULT_I18N_RESOURCES_DIR = DEFAULT_RESOURCES_DIR.concat("i18n/");
    
    /**
     * Constante que define o arquivo de internacionalização default.
     */
    public static final String DEFAULT_I18N_RESOURCE_ID = DEFAULT_I18N_RESOURCES_DIR.concat("common");
    
    /**
     * Constante que define o caminho de armazenamento dos arquivos de relatório.
     */
    public static final String DEFAULT_REPORTS_DIR = DEFAULT_RESOURCES_DIR.concat("reports/");
    
    /**
     * Constante que define o arquivo de internacionalização de relatórios default.
     */
    public static final String DEFAULT_REPORTS_I18N_DIR = DEFAULT_I18N_RESOURCES_DIR.concat("reports/");
}