package br.com.concepting.framework.processors;

import java.lang.reflect.Method;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.jexl.Expression;
import org.apache.commons.jexl.ExpressionFactory;
import org.apache.commons.jexl.JexlContext;
import org.apache.commons.jexl.JexlHelper;

import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.util.LanguageUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Classe que implementa um processador l�gico de express�es.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class EvaluateProcessor extends BaseProcessor{
	private String value = "";
	
    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     */
	public EvaluateProcessor(){
		this(ExpressionProcessorUtil.class.getName());
	}
	
    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param domain String contendo o identificador do dom�nio do processamento.
     */
	public EvaluateProcessor(String domain){
		this(domain, LanguageUtil.getDefaultLanguage());
	}
	
    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param language Inst�ncia contendo as propriedades do idioma a ser utilizado no
     * processamento.
     */
	public EvaluateProcessor(Locale language){
		this(ExpressionProcessorUtil.class.getName(), null, language);
	}

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param domain String contendo o identificador do dom�nio do processamento.
     * @param language Inst�ncia contendo as propriedades do idioma a ser utilizado no
     * processamento.
     */
	public EvaluateProcessor(String domain, Locale language){
		this(domain, null, language);
	}

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param declaration Inst�ncia contendo o objeto a ser considerado no processamento.
     */
	public EvaluateProcessor(Object declaration){
		this(ExpressionProcessorUtil.class.getName(), declaration);
	}

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param domain String contendo o identificador do dom�nio do processamento.
     * @param declaration Inst�ncia contendo o objeto a ser considerado no processamento.
     */
	public EvaluateProcessor(String domain, Object declaration){
		this(domain, declaration, LanguageUtil.getDefaultLanguage());
	}

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param declaration Inst�ncia contendo o objeto a ser considerado no processamento.
     * @param language Inst�ncia contendo as propriedades do idioma a ser utilizado no
     * processamento.
     */
	public EvaluateProcessor(Object declaration, Locale language){
		this(ExpressionProcessorUtil.class.getName(), declaration, language);
	}

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param domain String contendo o identificador do dom�nio do processamento.
     * @param declaration Inst�ncia contendo o objeto a ser considerado no processamento.
     * @param language Inst�ncia contendo as propriedades do idioma a ser utilizado no
     * processamento.
     */
	public EvaluateProcessor(String domain, Object declaration, Locale language){
		this(domain, declaration, null, language);
	}

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param domain String contendo o identificador do dom�nio do processamento.
     * @param declaration Inst�ncia contendo o objeto a ser considerado no processamento.
     * @param content Inst�ncia do conte�do XML.
     */
	public EvaluateProcessor(String domain, Object declaration, XmlNode content){
		this(domain, declaration, content, LanguageUtil.getDefaultLanguage());
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
	public EvaluateProcessor(String domain, Object declaration, XmlNode content, Locale language){
		super(domain, declaration, content, language);
	}
	
	/**
	 * @see br.com.concepting.framework.processors.BaseProcessor#hasLogic()
	 */
    protected Boolean hasLogic(){
        return true;
    }

    /**
     * Cria uma vari�vel.
     * 
     * @param name String contendo o identificador da vari�vel.
     * @param value Inst�ncia do conte�do da vari�vel.
     */
	public void addVariable(String name, Object value){
	    String domain = getDomain();
	    
		ExpressionProcessorUtil.addVariable(domain, name, value);
	}

    /**
     * Retorna o conte�do de uma vari�vel.
     * 
     * @param name String contendo o identificador da vari�vel.
     * @return Inst�ncia do conte�do da vari�vel.
     */
	public Object getVariable(String name){
	    String domain = getDomain();
	    
		return ExpressionProcessorUtil.getVariable(domain, name);
	}

	/**
	 * Retorna a express�o a ser processada.
	 * 
	 * @return String contendo o express�o a ser processada.
	 */
	public String getValue(){
		return value;
	}

    /**
     * Define a express�o a ser processada.
     * 
     * @param value String contendo o express�o a ser processada.
     */
	public void setValue(String value){
		this.value = value;
	}

    /**
     * Efetua o processamento de uma express�o.
     * 
     * @return Inst�ncia do processamento da express�o.
     * @throws Throwable
     */
	public <O> O evaluate() throws Throwable{
	    if(value.length() > 0)
	        return evaluate(value);
	    
	    return null;
	}

	/**
	 * Efetua o processamento de uma express�o.
	 * 
	 * @param value String contendo a express�o desejada.
	 * @return Inst�ncia do processamento da express�o.
	 * @throws Throwable
	 */
    public <O> O evaluate(String value) throws Throwable{
		Pattern pattern = Pattern.compile("^new (.+)\\((.*?)\\)$");
		Matcher matcher = pattern.matcher(value);

		if(matcher.find()){
			String clazzId            = matcher.group(1);
			String parametersId       = matcher.group(2);
			String parameters[]       = StringUtil.split(parametersId);
			String parameter          = "";
            Object parameterValue     = null;
			Object parametersValues[] = new Object[parameters.length];

			for(Integer cont = 0 ; cont < parameters.length ; cont++){
			    parameter = StringUtil.trim(parameters[cont]);

				if((parameter.startsWith("\'") && parameter.endsWith("\'")) || (parameter.startsWith("\"") && parameter.endsWith("\""))){
				    parameter = StringUtil.replaceAll(parameter, String.valueOf("\'"), "");
				    parameter = StringUtil.replaceAll(parameter, String.valueOf("\""), "");
				}

				parameterValue         = StringUtil.trim(evaluate(parameter));
				parametersValues[cont] = parameterValue;
			}

			Class clazz = Class.forName(clazzId);

			return (O)ConstructorUtils.invokeConstructor(clazz, parametersValues);
		}
		
		pattern = Pattern.compile("^(.+)\\((.*?)\\)$");
		matcher = pattern.matcher(value);

		if(matcher.find()){
			String clazzId = matcher.group(1);
			
			if(!clazzId.contains("#{") && !clazzId.contains("@{")){
     			String  parametersId       = matcher.group(2);
     			String  parameters[]       = StringUtil.split(parametersId);
     			String  parameter          = "";
                Object  parameterValue     = null;
     			Object  parametersValues[] = new Object[parameters.length];
     			Integer pos                = clazzId.lastIndexOf(".");
     			String  methodId           = clazzId.substring(pos + 1);
     
     			if(pos >= 0){
          			clazzId = clazzId.substring(0, pos);
          
          			Class clazz = Class.forName(clazzId);
          
                    for(Integer cont = 0 ; cont < parameters.length ; cont++){
                        parameter = StringUtil.trim(parameters[cont]);
          
                        if((parameter.startsWith("\'") && parameter.endsWith("\'")) || (parameter.startsWith("\"") && parameter.endsWith("\""))){
                            parameter = StringUtil.replaceAll(parameter, String.valueOf("\'"), "");
                            parameter = StringUtil.replaceAll(parameter, String.valueOf("\""), "");
                        }

                        parameterValue         = evaluate(parameter);
                        parametersValues[cont] = parameterValue;
          			}
          
          			Object instance = clazz.newInstance();
          
          			try{
          				return (O)MethodUtils.invokeMethod(instance, methodId, parametersValues);
          			}
          			catch(Throwable e){
          				try{
          					Method method = instance.getClass().getMethod(methodId);
          
          					return (O)method.invoke(instance);
          				}
          				catch(NoSuchMethodException e1){
          					return null;
          				}
          			}
     			}
     		}
		}

		pattern = Pattern.compile("\\#\\{(.*?)\\}|\\@\\{(.*?)\\}");
		matcher = pattern.matcher(value);
		
        Object      declaration     = getDeclaration();
		JexlContext context         = JexlHelper.createContext();
		String      valueBuffer     = value;
        String      tokenExpression = "";
        String      tokenName       = "";
        Object      tokenValue      = null; 
        
        if(matcher.find()){
            context.getVars().put("this", declaration);
            
    		do{
    			tokenExpression = matcher.group();
    			tokenName       = matcher.group(1);
    			
    			if(tokenName == null)
    				tokenName = matcher.group(2);
    			
                if(tokenExpression.contains("#{")){
         			if(!tokenName.equals("this")){
              			tokenValue = PropertyUtil.getProperty(declaration, tokenName);
    	          			
              			context.getVars().put(tokenName, tokenValue);
         			}
    			}
    			else if(tokenExpression.contains("@{")){
         			tokenValue = getVariable(tokenName);
              			
         			context.getVars().put(tokenName, tokenValue);
    			}
        			
    			valueBuffer = StringUtil.replaceAll(valueBuffer, tokenExpression, tokenName);
    		}
    		while(matcher.find());
		}
		else{
            valueBuffer = StringUtil.replaceAll(valueBuffer, String.valueOf("\'"), "");
            valueBuffer = StringUtil.replaceAll(valueBuffer, String.valueOf("\""), "");
    		pattern     = Pattern.compile("^(not|!)?\\ ?(true|false|[0-9]+)\\ ?(\\+|\\-|\\*|\\/|\\%|\\&+|\\|+)?\\ ?(not|!)?\\ ?(true|false|[0-9]+)?$");
    		matcher     = pattern.matcher(valueBuffer);
    
    		if(!matcher.find()){
     			context.getVars().put("value", valueBuffer);
    
     			try{
    				Expression expression = ExpressionFactory.createExpression("value");
    
    				return (O)expression.evaluate(context);
    			}
    			catch(Throwable e){
    				return (O)valueBuffer;
    			}
    		}
		}

		try{
			Expression expression = ExpressionFactory.createExpression(valueBuffer);

			return (O)expression.evaluate(context);
		}
		catch(Throwable e){
			return (O)"";
		}
	}

	/**
	 * @see br.com.concepting.framework.processors.BaseProcessor#process()
	 */
	public String process() throws Throwable{
	    if(getClass().equals(EvaluateProcessor.class)){
	        evaluate();
	        
	        return null;
	    }

	    return super.process();
	}
}