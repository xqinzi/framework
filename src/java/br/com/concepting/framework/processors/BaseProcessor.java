package br.com.concepting.framework.processors;

import java.util.Collection;
import java.util.Locale;

import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Classe que define a estrutura b�sica para um processamento l�gico.
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
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param domain String contendo o identificador do dom�nio do processamento.
     * @param declaration Inst�ncia contendo o objeto a ser considerado no processamento.
     * @param content Inst�ncia do conte�do XML.
     * @param language Inst�ncia contendo as propriedades do idioma a ser utilizado no
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
	 * Indica se � um processador l�gico.
	 * 
	 * @return True/False.
	 */
	protected Boolean hasLogic(){
	    return false;
	}
	
	/**
	 * Retorna o identificador do dom�nio do processador l�gico.
	 * 
	 * @return String contendo o identificador do dom�nio.
	 */
	public String getDomain(){
    	return domain;
    }

    /**
     * Define o identificador do dom�nio do processador l�gico.
     * 
     * @param domain String contendo o identificador do dom�nio.
     */
	public void setDomain(String domain){
    	this.domain = domain;
    }

	/**
	 * Retorna a inst�ncia do idioma utilizado pelo processador l�gico.
	 * 
	 * @return Inst�ncia do idioma a ser utilizado.
	 */
	public Locale getLanguage(){
		return language;
	}
	
    /**
     * Define a inst�ncia do idioma utilizado pelo processador l�gico.
     * 
     * @param language Inst�ncia do idioma a ser utilizado.
     */
	public void setLanguage(Locale language){
		this.language = language;
	}

	/**
	 * Retorna a inst�ncia do objeto a ser utilizado no processamento l�gico.
	 * 
	 * @return Inst�ncia do objeto a ser utilizado.
	 */
	public Object getDeclaration(){
		return declaration;
	}

    /**
     * Define a inst�ncia do objeto a ser utilizado no processamento l�gico.
     * 
     * @param declaration Inst�ncia do objeto a ser utilizado.
     */
	public void setDeclaration(Object declaration){
		this.declaration = declaration;
	}

	/**
	 * Retorna o conte�do XML a ser utilizado no processamento l�gico.
	 * 
	 * @return Inst�ncia do conte�do XML a ser utilizado.
	 */
	public XmlNode getContent(){
		return content;
	}

    /**
     * Define o conte�do XML a ser utilizado no processamento l�gico.
     * 
     * @param content Inst�ncia do conte�do XML a ser utilizado.
     */
	public void setContent(XmlNode content){
		this.content = content;
	}

	/**
	 * Efetua o processamento l�gico do conte�do XML.
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