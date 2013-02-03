package br.com.concepting.framework.util.constants;

/**
 * Classe que define as constantes utilizadas pelas rotinas de manipulação de conteúdo XML.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public abstract class XmlConstants{
    /**
     * Constante que define o identificador da feature de não utilizar os DTDs para validação do 
     * conteúdo XML.
     */
    public static final String NON_DTD_VALIDATION_FEATURE_ID = "http://apache.org/xml/features/nonvalidating/load-external-dtd";

    /**
     * Constante que define o identificador da feature de não validação do conteúdo XML.
     */
    public static final String NON_VALIDATION_FEATURE_ID = "http://xml.org/sax/features/validation";
}
