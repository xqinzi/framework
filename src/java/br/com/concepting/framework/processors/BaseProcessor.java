package br.com.concepting.framework.processors;

import java.util.Collection;
import java.util.Locale;

import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Classe que define a estrutura básica para um processamento lógico.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class BaseProcessor{
	private String  domain      = "";
	private Object  declaration = null;
	private XmlNode content     = null;
	private Locale  language    = null;

    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param domain String contendo o identificador do domínio do processamento.
     * @param declaration Instância contendo o objeto a ser considerado no processamento.
     * @param content Instância do conteúdo XML.
     * @param language Instância contendo as propriedades do idioma a ser utilizado no
     * processamento.
     */
	public BaseProcessor(String domain, Object declaration, XmlNode content, Locale language){
		super();

		setDomain(domain);
		setDeclaration(declaration);
		setContent(content);
		setLanguage(language);
	}

	/**
	 * Indica se é um processador lógico.
	 * 
	 * @return True/False.
	 */
	protected Boolean hasLogic(){
	    return false;
	}
	
	/**
	 * Retorna o identificador do domínio do processador lógico.
	 * 
	 * @return String contendo o identificador do domínio.
	 */
	public String getDomain(){
    	return domain;
    }

    /**
     * Define o identificador do domínio do processador lógico.
     * 
     * @param domain String contendo o identificador do domínio.
     */
	public void setDomain(String domain){
    	this.domain = domain;
    }

	/**
	 * Retorna a instância do idioma utilizado pelo processador lógico.
	 * 
	 * @return Instância do idioma a ser utilizado.
	 */
	public Locale getLanguage(){
		return language;
	}
	
    /**
     * Define a instância do idioma utilizado pelo processador lógico.
     * 
     * @param language Instância do idioma a ser utilizado.
     */
	public void setLanguage(Locale language){
		this.language = language;
	}

	/**
	 * Retorna a instância do objeto a ser utilizado no processamento lógico.
	 * 
	 * @return Instância do objeto a ser utilizado.
	 */
	public Object getDeclaration(){
		return declaration;
	}

    /**
     * Define a instância do objeto a ser utilizado no processamento lógico.
     * 
     * @param declaration Instância do objeto a ser utilizado.
     */
	public void setDeclaration(Object declaration){
		this.declaration = declaration;
	}

	/**
	 * Retorna o conteúdo XML a ser utilizado no processamento lógico.
	 * 
	 * @return Instância do conteúdo XML a ser utilizado.
	 */
	public XmlNode getContent(){
		return content;
	}

    /**
     * Define o conteúdo XML a ser utilizado no processamento lógico.
     * 
     * @param content Instância do conteúdo XML a ser utilizado.
     */
	public void setContent(XmlNode content){
		this.content = content;
	}

	/**
	 * Efetua o processamento lógico do conteúdo XML.
	 * 
	 * @return String contendo o resultado do processamento.
	 * @throws Throwable
	 */
	public String process() throws Throwable{
		ProcessorFactory processorFactory = ProcessorFactory.getInstance();
		BaseProcessor    processor        = null;
		Collection       nodeChilds       = content.getChildNodes();
		String           nodeBody         = "";
		StringBuilder    nodeValue        = new StringBuilder();
        String           nodeText         = StringUtil.trim(content.getText());
		XmlNode          node             = null;
		Integer          cont             = 0;

		if(nodeChilds != null && nodeChilds.size() > 0){
			while(true){
				node = content.getNode(cont);
				if(node == null)
					break;
				
			    nodeBody = StringUtil.trim(node.getBody());
			    
			    if(nodeValue.length() > 0)
			        nodeValue.delete(0, nodeValue.length());
			    
                processor = processorFactory.getProcessor(domain, declaration, node, language);

                nodeValue.append(StringUtil.trim(processor.process()));
				
				if(processor.hasLogic())
				    nodeText = StringUtil.replaceAll(nodeText, nodeBody, nodeValue.toString());
				else{
				    nodeBody = StringUtil.trim(node.getText());

				    if(!nodeBody.equals(nodeValue.toString()))
				        nodeText = StringUtil.replaceAll(nodeText, nodeBody, nodeValue.toString());
				}

				cont++;
			}
		}

		nodeText = StringUtil.trim(nodeText);
		nodeText = StringUtil.decode(nodeText);
		nodeText = ExpressionProcessorUtil.fillVariablesInString(domain, nodeText, language);
		nodeText = PropertyUtil.fillPropertiesInString(declaration, nodeText, language);

		return nodeText;
    }
}