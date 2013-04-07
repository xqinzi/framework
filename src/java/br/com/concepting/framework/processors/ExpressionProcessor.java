package br.com.concepting.framework.processors;

import java.util.Locale;

import br.com.concepting.framework.processors.constants.ProcessorConstants;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Classe que implementa um processador l�gico de express�es condicionais.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class ExpressionProcessor extends EvaluateProcessor{
    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     */
    public ExpressionProcessor(){
        super();
    }
    
    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param domain String contendo o identificador do dom�nio do processamento.
     */
    public ExpressionProcessor(String domain){
        super(domain);
    }
    
    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param language Inst�ncia contendo as propriedades do idioma a ser utilizado no
     * processamento.
     */
    public ExpressionProcessor(Locale language){
        super(language);
    }

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param domain String contendo o identificador do dom�nio do processamento.
     * @param language Inst�ncia contendo as propriedades do idioma a ser utilizado no
     * processamento.
     */
    public ExpressionProcessor(String domain, Locale language){
        super(domain, language);
    }

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param declaration Inst�ncia contendo o objeto a ser considerado no processamento.
     */
    public ExpressionProcessor(Object declaration){
        super(declaration);
    }

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param domain String contendo o identificador do dom�nio do processamento.
     * @param declaration Inst�ncia contendo o objeto a ser considerado no processamento.
     */
    public ExpressionProcessor(String domain, Object declaration){
        super(domain, declaration);
    }

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param declaration Inst�ncia contendo o objeto a ser considerado no processamento.
     * @param language Inst�ncia contendo as propriedades do idioma a ser utilizado no
     * processamento.
     */
    public ExpressionProcessor(Object declaration, Locale language){
        super(declaration, language);
    }

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param domain String contendo o identificador do dom�nio do processamento.
     * @param declaration Inst�ncia contendo o objeto a ser considerado no processamento.
     * @param language Inst�ncia contendo as propriedades do idioma a ser utilizado no
     * processamento.
     */
    public ExpressionProcessor(String domain, Object declaration, Locale language){
        super(domain, declaration, language);
    }

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param domain String contendo o identificador do dom�nio do processamento.
     * @param declaration Inst�ncia contendo o objeto a ser considerado no processamento.
     * @param content Inst�ncia do conte�do XML.
     */
    public ExpressionProcessor(String domain, Object declaration, XmlNode content){
        super(domain, declaration, content);
    }

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param domain String contendo o identificador do dom�nio do processamento.
     * @param declaration Inst�ncia contendo o objeto a ser considerado no processamento.
     * @param content Inst�ncia do conte�do XML.
     * @param language Inst�ncia contendo as propriedades do idioma a ser utilizado no
     * processamento.
     */
    public ExpressionProcessor(String domain, Object declaration, XmlNode content, Locale language){
        super(domain, declaration, content, language);
    }

    /**
	 * @see br.com.concepting.framework.processors.BaseProcessor#process()
	 */
	public String process() throws Throwable{
	    String value = StringUtil.trim(getValue());
	    
	    if(value.length() > 0){
    	    Object result = super.evaluate();
    
    		if(result instanceof Boolean)
    		    if((Boolean)result)
    				return super.process();
    
    		return ProcessorConstants.REMOVE_TAG;
	    }
	    
	    return super.process();
	}
}