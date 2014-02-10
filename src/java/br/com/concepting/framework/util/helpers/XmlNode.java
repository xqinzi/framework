package br.com.concepting.framework.util.helpers;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Element;

import br.com.concepting.framework.util.StringUtil;

/**
 * Classe que define um nó XML.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class XmlNode extends Node{
    private static final long serialVersionUID = 3468250465462747310L;
    
    private String              namespace  = "";
	private String              name       = "";
	private String              value      = "";
	private String              text       = "";
	private String              body       = "";
	private Integer             index      = 0;
	private Integer             level      = 0;
	private Map<String, String> attributes = null;

    /**
     * Construtor - Cria um novo nó.
     */
    public XmlNode(){
        super();
    }
    
    /**
     * Construtor - Cria um novo nó definindo.
     * 
     * @param name String contendo o identificador do nó.
     */
    public XmlNode(String name){
        this();
        
        setName(name);
    }

    /**
     * Construtor - Cria um novo nó.
     * 
     * @param name String contendo o identificador do nó.
     * @param value String contendo o valor do nó.
     */
    public XmlNode(String name, String value){
        this(name);

        setValue(value);
    }
    
    /**
     * Construtor - Cria um novo nó.
     * 
     * @param node Instância contendo os dados do nó.
     * @param index Valor inteiro contedo o índice de armazenamento do nó.
     * @param level Valor inteiro contedo o nível de armazenamento do nó.
     */
    public XmlNode(Element node, Integer index, Integer level){
        this();

        this.namespace = node.getNamespacePrefix();
        this.name      = node.getName();
        this.body      = removeNamespaces(node.asXML());
        
        buildChildNodes(node);
        
        @SuppressWarnings("unchecked")
        List<Attribute> attributes = node.attributes();
        
        buildAttributes(attributes);

        this.level = level;
        this.index = index;
        this.value = node.getTextTrim();
        this.text  = buildText(node);
    }
    
    /**
     * Retorna o texto do nó.
     * 
     * @param node Instância contendo as propriedades do nó.
     * @return String contendo o texto do nó.
     */
    private String buildText(Element node){
        String text = "";

        if(node != null){
            text = removeNamespaces(node.asXML());

            if(text.length() > 0){
                Integer pos = text.indexOf(">");

                if(pos >= 0)
                    text = text.substring(pos + 1);

                pos = text.lastIndexOf("</");
                if(pos >= 0)
                    text = text.substring(0, pos);

                if(text.length() > 0){
                    int asc = text.charAt(0);

                    if(asc == 10)
                        text = text.substring(1);

                    if(text.length() > 0){
                        asc = text.charAt(text.length() - 1);
                        if(asc == 10)
                            text = text.substring(0, text.length() - 1);
                    }
                }
            }
        }

        return text;
    }
    
    /**
     * Remove as definições do nomespaces nas tags.
     * 
     * @param value String contendo as tags com namespace definido.
     * @return String sem namespaces.
     */
    private String removeNamespaces(String value){
        value = StringUtil.replaceAll(value, " xmlns:concepting=\"default namespace\"", "");
        value = StringUtil.replaceAll(value, " xmlns:c=\"default namespace\"", "");
        value = StringUtil.replaceAll(value, " xmlns:jsp=\"default namespace\"", "");
        
        return value;
    }

    /**
     * Monta o mapeamento dos atributos.
     * 
     * @param attributes Lista contendo os dados dos atributos.
     */
    private void buildAttributes(List<Attribute> attributes){
        this.attributes = new LinkedHashMap<String, String>();

        for(Attribute attribute : attributes)
            this.attributes.put(attribute.getName(), attribute.getValue());
    }

    /**
     * Monta as nós filhos.
     * 
     * @param node Instância contendo os dados do nó.
     */
    private void buildChildNodes(Element node){
        if(node != null){
            @SuppressWarnings("unchecked")
            Collection<Element> nodes = node.elements();

            if(nodes != null && nodes.size() > 0){
                XmlNode childNode = null;
                Integer index     = 0;

                for(Element item : nodes){
                    if(item.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE){
                        childNode = new XmlNode(item, index, level + 1);

                        addChildNode(childNode);

                        index++;
                    }
                }
            }
        }
    }
    
    /**
     * Retorna o identificador do nomespace do nó.
     * 
     * @return String contendo o identificador do nomespace.
     */
    public String getNamespace(){
        return namespace;
    }

    /**
     * Define o identificador do nomespace do nó.
     * 
     * @param namespace String contendo o identificador do nomespace.
     */
    public void setNamespace(String namespace){
        this.namespace = namespace;
    }

    /**
     * Retorna o texto do nó.
     * 
     * @return String contendo o texto do nó.
     */
    public String getText(){
        return text;
    }

    /**
     * Define o texto do nó.
     * 
     * @param text String contendo o texto do nó.
     */
    public void setText(String text){
        this.text = text;
    }
    
    /**
     * Retorna o valor do nó.
     * 
     * @return String contendo o valor do nó.
     */
    public String getValue(){
        return value;
    }

    /**
     * Retorna o valor do nó.
     * 
     * @param value String contendo o valor do nó.
     */
    public void setValue(String value){
        this.value = value;
    }

    /**
     * Retorna o conteúdo do nó.
     * 
     * @return String do conteúdo do nó.
     */
    public String getBody(){
        return body;
    }

    /**
     * Define o conteúdo do nó.
     * 
     * @param body String do conteúdo do nó.
     */
    public void setBody(String body){
        this.body = body;
    }

    /**
     * Retorna o nome do nó.
     * 
     * @return String contendo o nome do nó.
     */
    public String getName(){
        return name;
    }

    /**
     * Define o nome do nó.
     * 
     * @param name String contendo o nome do nó.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Retorna o índice de armazenamento do nó.
     * 
     * @return Valor inteiro contendo o índice.
     */
    public Integer getIndex(){
        return index;
    }

    /**
     * Define o índice de armazenamento do nó.
     * 
     * @param index Valor inteiro contendo o índice.
     */
    public void setIndex(Integer index){
        this.index = index;
    }

    /**
     * Retorna o nível de armazenamento do nó.
     * 
     * @return Valor inteiro contendo o nível.
     */
    public Integer getLevel(){
        return level;
    }

    /**
     * Define o nível de armazenamento do nó.
     * 
     * @param level Valor inteiro contendo o nível.
     */
    public void setLevel(Integer level){
        this.level = level;
    }

    /**
     * Retorna os dados de um nó a partir do seu identificador ou pelo seu xPath.
     * 
     * @param xpathName String contendo o identificador ou xPath do nó.
     * @return Instância contendo os dados do nó.
     */
    public XmlNode getNode(String xpathName){
        String              xpathNames[] = StringUtil.split(xpathName, ".");
        Collection<XmlNode> nodes        = getChildNodes();
        XmlNode             node         = null;

        if(nodes != null){
            for(Integer cont = 0 ; cont < xpathNames.length ; cont++){
                for(XmlNode item : nodes){
                    node = item;
                    
                    if(item.getName().equals(xpathNames[cont])){
                        if(item.hasChildNodes())
                            nodes = item.getChildNodes();

                        break;
                    }

                    node = null;
                }
            }
        }

        return node;
    }

    /**
     * Retorna os dados de um nó a partir do seu índice de armazenamento.
     * 
     * @param index Valor inteiro contendo o índice de armazenamento.
     * @return Instância contendo os dados do nó.
     */
    public XmlNode getNode(Integer index){
        List<XmlNode> nodes = getChildNodes();

        try{
            return nodes.get(index);
        }
        catch(Throwable e){
            return null;
        }
    }

    /**
     * Retorna o valor do atributo a partir do seu identificador.
     * 
     * @param name String contendo o identificador do atributo.
     * @return String contendo o valor do atributo.
     */
    public String getAttribute(String name){
        if(attributes == null)
            return null;

        return attributes.get(name);
    }

    /**
     * Adiciona um novo atributo.
     *
     * @param name String contendo o identificador do atributo.
     * @param value String do valor do atributo.
     */
    public void addAttribute(String name, String value){
        if(attributes == null)
            attributes = new LinkedHashMap<String, String>();
        
        attributes.put(name, value);
    }

    /**
     * Retorna o valor do atributo a partir do seu índice de armazenamento.
     * 
     * @param index Valor inteiro contendo o índice de armazenamento.
     * @return String contendo o valor do atributo.
     */
    public String getAttribute(Integer index){
        if(attributes == null)
            return null;

        Integer cont = 0;

        for(String attributeId : attributes.keySet()){
            if(cont.equals(index))
                return attributes.get(attributeId);
        }

        return null;
    }

    /**
     * Retorna mapa contendo os atributos do nó.
     * 
     * @return Instância do mapa contendo os atributos do nó.
     */
    public Map<String, String> getAttributes(){
        return attributes;
    }

    /**
     * Define mapa contendo os atributos do nó.
     * 
     * @param attributes Instância do mapa contendo os atributos do nó.
     */
    public void setAttributes(Map<String, String> attributes){
        this.attributes = attributes;
    }
}