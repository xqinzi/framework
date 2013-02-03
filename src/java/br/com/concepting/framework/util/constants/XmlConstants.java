package br.com.concepting.framework.util.constants;

/**
 * Classe que define as constantes utilizadas pelas rotinas de manipula��o de conte�do XML.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public abstract class XmlConstants{
    /**
     * Constante que define o identificador da feature de n�o utilizar os DTDs para valida��o do 
     * conte�do XML.
     */
    public static final String NON_DTD_VALIDATION_FEATURE_ID = "http://apache.org/xml/features/nonvalidating/load-external-dtd";

    /**
     * Constante que define o identificador da feature de n�o valida��o do conte�do XML.
     */
    public static final String NON_VALIDATION_FEATURE_ID = "http://xml.org/sax/features/validation";
}
