package br.com.concepting.framework.processors;

import java.util.LinkedHashMap;
import java.util.Map;

import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Classe utilitária para manipulação dos processadores lógicos.
 * 
 * @author fvilarinho
 * @since 2.0
 */
public abstract class ProcessorUtil{
	private static Map<String, Class>  validProcessors          = new LinkedHashMap<String, Class>();
	private static Map<String, String> validProcessorAttributes = new LinkedHashMap<String, String>();

	static{
        validProcessors.put("evaluate", EvaluateProcessor.class);
	    validProcessors.put("iterate", IterateProcessor.class);
	    validProcessors.put("for", IterateProcessor.class);
	    validProcessors.put("expression", ExpressionProcessor.class);
	    validProcessors.put("if", ExpressionProcessor.class);
	    validProcessors.put("set", SetProcessor.class);
        validProcessors.put("include", IncludeProcessor.class);
		
        validProcessorAttributes.put("var", "name");
        validProcessorAttributes.put("resource", "value");
	    validProcessorAttributes.put("values", "value");
	    validProcessorAttributes.put("data", "value");
	    validProcessorAttributes.put("expression", "value");
	    validProcessorAttributes.put("expr", "value");
	}

	/**
	 * Retorna a classe do processador lógico a partir do conteúdo XML.
	 * 
	 * @param node Instância do conteúdo XML.
	 * @return Classe do processador lógico.
	 */
	public static Class getClass(XmlNode node){
	    Class clazz = null;
	    
	    if(node.getNamespace().length() == 0){
    	    clazz = validProcessors.get(node.getName());
    	    if(clazz == null)
    	        clazz = BaseProcessor.class;
	    }
	    else
	        clazz = BaseProcessor.class;

	    return clazz;
	}
	
	/**
	 * Retorna o identificador do atributo do processador lógico.
	 * 
	 * @param alias String contendo o apelido do atributo.
	 * @return String contendo o identificador do atributo.
	 */
	public static String getAttributeNameByAlias(String alias){
	    String result = StringUtil.trim(validProcessorAttributes.get(alias));
	    
	    if(result.length() == 0)
	        result = alias;
	    
	    return result;
	}
}