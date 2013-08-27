package br.com.concepting.framework.audit.constants;

import br.com.concepting.framework.resource.constants.ResourceConstants;

/**
 * Classe que define as constantes utilizadas pelas rotinas de auditoria.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public abstract class AuditorConstants{
    /**
     * Constante que define o tamanho default da fila de registros de auditoria.
     */
    public static final Integer DEFAULT_QUEUE_SIZE = 1;
    
    /**
     * Constante que define o identificador do arquivo de configurações de auditoria.
     */
    public static final String  DEFAULT_RESOURCE_ID = ResourceConstants.DEFAULT_RESOURCES_DIR.concat("auditorResources.xml");
    
    /**
     * Constante que define o identificador da configuração de auditoria para a 
     * rotina de geração de código.
     */
    public static final String  DEFAULT_GENERATE_CODE_RESOURCE_KEY = "generate.code";
    
    /**
     * Constante que define o identificador do atributo da classe de layout para as 
     * mensagens de auditoria.
     */
    public static final String  DEFAULT_LAYOUT_CLASS_KEY = "layoutClass";
    
    /**
     * Constante que define o identificador do atributo da máscara de formatação 
     * das mensagens de auditoria.
     */
    public static final String  DEFAULT_LAYOUT_PATTERN_KEY = "layoutPattern";
}
