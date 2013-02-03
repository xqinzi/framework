package br.com.concepting.framework.processors;

import java.io.InputStream;
import java.util.Locale;

import br.com.concepting.framework.resource.constants.ResourceConstants;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.XmlReader;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Classe que implementa um processador lógico de inclusão de conteúdo.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class IncludeProcessor extends ExpressionProcessor{
    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param domain String contendo o identificador do domínio do processamento.
     * @param declaration Instância contendo o objeto a ser considerado no processamento.
     * @param content Instância do conteúdo XML.
     * @param language Instância contendo as propriedades do idioma a ser utilizado no
     * processamento.
     */
    public IncludeProcessor(String domain, Object declaration, XmlNode content, Locale language){
        super(domain, declaration, content, language);
    }

    /**
     * @see br.com.concepting.framework.processors.ExpressionProcessor#process()
     */
    public String process() throws Throwable{
        String        value      = StringUtil.trim(getValue());
        StringBuilder resourceId = new StringBuilder();
        
        resourceId.append(ResourceConstants.DEFAULT_RESOURCES_DIR);
        resourceId.append(value);
        
        InputStream in      = getClass().getResourceAsStream(resourceId.toString()); 
        XmlReader   reader  = new XmlReader(in);
        XmlNode     content = reader.getRoot();
        
        setContent(content);
        
        return super.process();
    }
}
