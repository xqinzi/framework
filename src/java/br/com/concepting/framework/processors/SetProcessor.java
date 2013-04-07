package br.com.concepting.framework.processors;

import java.util.Locale;

import br.com.concepting.framework.processors.constants.ProcessorConstants;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Classe que implementa um processador l�gico para declara��o de vari�veis.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class SetProcessor extends ExpressionProcessor{
	private String name = "";

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param domain String contendo o identificador do dom�nio do processamento.
     * @param declaration Inst�ncia contendo o objeto a ser considerado no processamento.
     * @param content Inst�ncia do conte�do XML.
     * @param language Inst�ncia contendo as propriedades do idioma a ser utilizado no
     * processamento.
     */
	public SetProcessor(String domain, Object declaration, XmlNode content, Locale language){
		super(domain, declaration, content, language);
	}

	/**
	 * Retorna o identificador da vari�vel.
	 * 
	 * @return String contendo o identificador da vari�vel.
	 */
	public String getName(){
		return name;
	}

    /**
     * Define o identificador da vari�vel.
     * 
     * @param name String contendo o identificador da vari�vel.
     */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * @see br.com.concepting.framework.processors.BaseProcessor#process()
	 */
	public String process() throws Throwable{
	    String value = StringUtil.trim(getValue());
	    
	    if(value.length() > 0)
	        addVariable(name, evaluate());
	    else
	        addVariable(name, super.process());

	    return ProcessorConstants.REMOVE_TAG;
	}
}