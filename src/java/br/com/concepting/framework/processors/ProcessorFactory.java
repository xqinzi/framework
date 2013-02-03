package br.com.concepting.framework.processors;

import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.ConstructorUtils;

import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.util.LanguageUtil;
import br.com.concepting.framework.util.exceptions.IncorrectSyntaxException;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Classe que implementa a factory de processadores lógicos.
 * 
 * @author fvilarinho
 * @since 2.0
 */
public class ProcessorFactory{
	private static ProcessorFactory instance = null;

	/**
	 * Construtor - Inicializa objetos e/ou variáveis internas.
	 */
	private ProcessorFactory(){
		super();
	}

	/**
	 * Retorna a instância da factory de processadores lógicos.
	 * 
	 * @return Instância da factory de processadores lógicos.
	 */
	public static ProcessorFactory getInstance(){
		if(instance == null)
			instance = new ProcessorFactory();

		return instance;
	}

	/**
	 * Retorna um processador lógico a partir de um conteúdo XML.
	 * 
	 * @param declaration Instância contendo o objeto a ser considerado no processamento.
	 * @param content Instância do conteúdo XML.
	 * @return Instância do processador lógico.
	 * @throws IncorrectSyntaxException
	 */
	public <L extends BaseProcessor> L getProcessor(Object declaration, XmlNode content) throws IncorrectSyntaxException{
		return getProcessor(ExpressionProcessorUtil.class.getName(), declaration, content);
	}

    /**
     * Retorna um processador lógico a partir de um conteúdo XML.
     * 
     * @param domain String contendo o identificador do domínio do processamento.
     * @param declaration Instância contendo o objeto a ser considerado no processamento.
     * @param content Instância do conteúdo XML.
     * @return Instância do processador lógico.
     * @throws IncorrectSyntaxException
     */
	public <L extends BaseProcessor> L getProcessor(String domain, Object declaration, XmlNode content) throws IncorrectSyntaxException{
		return getProcessor(domain, declaration, content, LanguageUtil.getDefaultLanguage());
	}
	
    /**
     * Retorna um processador lógico a partir de um conteúdo XML.
     * 
     * @param declaration Instância contendo o objeto a ser considerado no processamento.
     * @param content Instância do conteúdo XML.
     * @param language Instância contendo as propriedades do idioma a ser utilizado no
     * processamento.
     * @return Instância do processador lógico.
     * @throws IncorrectSyntaxException
     */
	public <L extends BaseProcessor> L getProcessor(Object declaration, XmlNode content, Locale language) throws IncorrectSyntaxException{
		return getProcessor(ExpressionProcessorUtil.class.getName(), declaration, content, language);
	}

    /**
     * Retorna um processador lógico a partir de um conteúdo XML.
     * 
     * @param domain String contendo o identificador do domínio do processamento.
     * @param declaration Instância contendo o objeto a ser considerado no processamento.
     * @param content Instância do conteúdo XML.
     * @param language Instância contendo as propriedades do idioma a ser utilizado no
     * processamento.
     * @return Instância do processador lógico.
     * @throws IncorrectSyntaxException
     */
    public <L extends BaseProcessor> L getProcessor(String domain, Object declaration, XmlNode content, Locale language) throws IncorrectSyntaxException{
		Class clazz     = ProcessorUtil.getClass(content);
        L     processor = null;
        
        try{
            processor = (L)ConstructorUtils.invokeConstructor(clazz, new Object[]{domain, declaration, content, language}); 
        }
        catch(Throwable e){
            throw new IncorrectSyntaxException(content.getText(), e);
        }
        
        if(processor.hasLogic()){
    		Map<String, String> attributes = content.getAttributes();
    		Object              value      = null;
    		
    		for(String name : attributes.keySet()){
                value = attributes.get(name);
    		    name  = ProcessorUtil.getAttributeNameByAlias(name);
    		    
    		    try{
    		        clazz = processor.getClass();
    		        clazz = PropertyUtil.getPropertyClass(clazz, name);
    		        value = ConstructorUtils.invokeConstructor(clazz, value);
    		        
    		        PropertyUtil.setProperty(processor, name, value);
    		    }
    		    catch(Throwable e){
		            throw new IncorrectSyntaxException(content.getText(), e);
    		    }
    		}
        }

		return processor;
	}
}