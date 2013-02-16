package br.com.concepting.framework.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Classe utilitária para manipulação de conteúdo XML.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class XmlUtil{
    /**
     * Transforma um objeto XML em uma string.
     * 
     * @param node Instância contendo as propriedades do objeto XML.
     * @return String contendo o XML.
     * @throws UnsupportedEncodingException
     */
    public static String toString(XmlNode node) throws UnsupportedEncodingException{
        return toString(node, false);
    }
    
    /**
     * Transforma um objeto XML em uma string.
     * 
     * @param node Instância contendo as propriedades do objeto XML.
     * @param encode Indica se os dados devem ser codificados (HTML Encode).
     * @return String contendo o XML.
     * @throws UnsupportedEncodingException
     */
    public static String toString(XmlNode node, Boolean encode) throws UnsupportedEncodingException{
        return toString(node, encode, LanguageUtil.getDefaultLanguage());
    }

    /**
     * Transforma um objeto XML em uma string.
     * 
     * @param node Instância contendo as propriedades do objeto XML.
     * @param language Instância contendo as propriedades do idioma a ser utilizado na 
     * formatação dos dados.
     * @return String contendo o XML.
     * @throws UnsupportedEncodingException
     */
    public static String toString(XmlNode node, Locale language) throws UnsupportedEncodingException{
        return toString(node, false, language);
    }
    
    /**
     * Transforma um objeto XML em uma string.
     * 
     * @param node Instância contendo as propriedades do objeto XML.
     * @param encode Indica se os dados devem ser codificados (URL Encoding).
     * @param language Instância contendo as propriedades do idioma a ser utilizado na 
     * formatação dos dados.
     * @return String contendo o XML.
     * @throws UnsupportedEncodingException
     */
    public static String toString(XmlNode node, Boolean encode, Locale language) throws UnsupportedEncodingException{
        if(language == null)
            return toString(node, encode);
        
        StringBuilder buffer = new StringBuilder();

        buffer.append("<");
        
        if(node.getNamespace().length() > 0){
            buffer.append(node.getNamespace());
            buffer.append(":");
        }
        
        buffer.append(node.getName());

        Map<String, String> attributes = node.getAttributes();

        if(attributes != null && attributes.size() > 0){
            String value = "";
            
            for(String attributeId : attributes.keySet()){
                buffer.append(" ");
                buffer.append(attributeId);
                buffer.append("=\"");
                
                value = PropertyUtil.format(attributes.get(attributeId), language);
                
                if(encode)
                    value = URLEncoder.encode(value, Charset.defaultCharset().name());
                
                buffer.append(value);
                buffer.append("\"");
            }
        }

        buffer.append(">");
        buffer.append(StringUtil.getLineBreak());

        if(StringUtil.trim(node.getValue()).length() > 0){
            String value = PropertyUtil.format(node.getValue(), language);
            
            if(encode)
                value = URLEncoder.encode(value, Charset.defaultCharset().name());

            buffer.append(value);
            buffer.append(StringUtil.getLineBreak());
        }

        List<XmlNode> childNodes = node.getChildNodes();

        if(childNodes != null && childNodes.size() > 0){
            String childBuffer = "";

            for(XmlNode childNode : childNodes){
                childBuffer = toString(childNode, encode, language);

                buffer.append(childBuffer);
            }
        }

        buffer.append("</");
        
        if(node.getNamespace().length() > 0){
            buffer.append(node.getNamespace());
            buffer.append(":");
        }
        
        buffer.append(node.getName());
        buffer.append(">");
        buffer.append(StringUtil.getLineBreak());

        return buffer.toString();
    }

    /**
     * Transforma uma string em um objeto XML.
     * 
     * @param value String contendo o XML. 
     * @return Instância contendo as propriedades do objeto XML.
     * @throws DocumentException
     */
    public static XmlNode parseString(String value) throws DocumentException{
        return parseString(value, Charset.defaultCharset().toString());
    }

    /**
     * Transforma uma string em um objeto XML.
     * 
     * @param value String contendo o XML.
     * @param encoding String contendo o tipo de codificação a ser utilizada.
     * @return Instância contendo as propriedades do objeto XML.
     * @throws DocumentException
     */
    public static XmlNode parseString(String value, String encoding) throws DocumentException{
        Document document = DocumentHelper.parseText(value);
        
        document.setXMLEncoding(encoding);

        return new XmlNode(document.getRootElement(), 0, 0);
    }

    /**
     * Efetua a indentação do conteúdo XML.
     * 
     * @param value String a ser indentada.
     * @return String indentada.
     * @throws IOException
     * @throws DocumentException
     */
    public static String indent(String value) throws IOException, DocumentException{
        return indent(value, FileUtil.getEncoding());
    }

    /**
     * Efetua a indentação do conteúdo XML utilizando um tipo de codificação específica.
     * 
     * @param value String a ser indentada.
     * @param encoding String contendo o tipo de codificação a ser utilizada.
     * @return String indentada.
     * @throws IOException
     * @throws DocumentException
     */
    public static String indent(String value, String encoding) throws IOException, DocumentException{
        OutputFormat format = OutputFormat.createPrettyPrint();

        format.setIndent(Constants.DEFAULT_INDENT_CHARACTER);
        format.setIndentSize(Constants.DEFAULT_INDENT_SIZE);
        format.setEncoding(encoding);
        
        StringWriter out      = new StringWriter();
        Document     document = DocumentHelper.parseText(value);
        XMLWriter    writer   = new XMLWriter(out, format);

        writer.write(document);
        writer.close();

        return out.toString();
    }
} 