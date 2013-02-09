package br.com.concepting.framework.util.constants;

import br.com.concepting.framework.web.types.ContentType;

/**
 * Classe que define as constantes utilizadas nas rotinas de emissão de relatórios.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public abstract class ReportConstants{
    /**
     * Constante que define o tipo de exportação default do relatório.
     */
    public static final ContentType DEFAULT_REPORT_EXPORT_TYPE = ContentType.PDF;
    
    /**
     * Constante que define a largura default de uma página texto.
     */
    public static final Integer DEFAULT_REPORT_TEXT_PAGE_WIDTH = 120;

    /**
     * Constante que define a altura default de uma página texto.
     */
    public static final Integer DEFAULT_REPORT_TEXT_PAGE_HEIGHT = 80;
    
    /**
     * Constante que define o identificador do diretório dos subreports.
     */
    public static final String DEFAULT_SUB_REPORT_DIR_KEY = "SUBREPORT_DIR";
    
    /**
     * Constante que define o identificador da chave que armazena o tipo de exportação do 
     * relatório.
     */
    public static final String REPORT_EXPORT_TYPE_KEY = "exportType";
    
    /**
     * Constante que define o identificador da chave que armazena a largura default de uma página 
     * texto. 
     */
    public static final String REPORT_TEXT_PAGE_WIDTH_KEY = "textPageWidth";

    /**
     * Constante que define o identificador da chave que armazena a altura default de uma página 
     * texto. 
     */
    public static final String REPORT_TEXT_PAGE_HEIGHT_KEY = "textPageHeight";
}