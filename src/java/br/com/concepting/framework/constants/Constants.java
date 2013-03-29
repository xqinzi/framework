package br.com.concepting.framework.constants;

import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.DateFieldType;
import br.com.concepting.framework.util.types.SortOrderType;

/**
 * Classe que define as constantes gerais.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public abstract class Constants{
    /**
     * Constante que define o tamanho do buffer de leitura.
     */
    public static final Integer DEFAULT_BUFFER_SIZE = 4096;
    
    /**
     * Constante que define o tipo de timeout de operaçòes.
     */
    public static final Integer DEFAULT_TIMEOUT = 60;
    
    /**
     * Constante que define o tipo de timeout de operações.
     */
    public static final DateFieldType DEFAULT_TIMEOUT_TYPE = DateFieldType.SECONDS;
    
    /**
     * Constante que define a codificação default.
     */
    public static final String DEFAULT_ENCODING = "iso-8859-1";
    
    /**
     * Constante que define o caracter default para indentação.
     */
    public static final String DEFAULT_INDENT_CHARACTER = String.valueOf(StringUtil.chr(32));
    
    /**
     * Constante que define o tamanho de caracteres default para indentação.
     */
    public static final Integer DEFAULT_INDENT_SIZE = 4;
    
    /**
     * Constante que define o número de itens por página default.
     */
    public static final Integer DEFAULT_ITEMS_PER_PAGE = 5;
    
    /**
     * Constante que define o caracter delimitador default.
     */
    public static final String DEFAULT_STRING_DELIMITER  = ",";
    
    /**
     * Constante que define o tipo de ordenação default.
     */
    public static final SortOrderType DEFAULT_SORT_ORDER_TYPE = SortOrderType.ASCEND;
    
    /**
     * Constante que define o identificador da chave que armazena a codificação.
     */
    public static final String ENCODING_KEY = "acceptCharset";
    
    /**
     * Constante que define o número de decimais de precisão default.
     */
    public static final Integer DEFAULT_NUMBER_PRECISION = 2;
}
