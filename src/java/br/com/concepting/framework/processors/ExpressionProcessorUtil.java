package br.com.concepting.framework.processors;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.concepting.framework.caching.CachedObject;
import br.com.concepting.framework.caching.Cacher;
import br.com.concepting.framework.caching.CacherManager;
import br.com.concepting.framework.model.exceptions.ItemAlreadyExistsException;
import br.com.concepting.framework.model.exceptions.ItemNotFoundException;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.util.LanguageUtil;
import br.com.concepting.framework.util.StringUtil;

/**
 * Classe utilit�ria respons�vel por manipular processadores l�gicos.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class ExpressionProcessorUtil{
    /**
     * Retorna o conte�do de uma vari�vel.
     * 
     * @param name String contendo o identificador da vari�vel.
     * @return Inst�ncia do conte�do da vari�vel.
     */
	public static Object getVariable(String name){
		return getVariable(ExpressionProcessorUtil.class.getName(), name);
	}

    /**
     * Retorna o conte�do de uma vari�vel.
     * 
     * @param domain String contendo o identificador do dom�nio.
     * @param name String contendo o identificador da vari�vel.
     * @return Inst�ncia do conte�do da vari�vel.
     */
	public static Object getVariable(String domain, String name){
		Cacher       cacher         = CacherManager.getInstance().getCacher(domain);
		CachedObject cachedObject   = null;
		String       variablesIds[] = StringUtil.split(name, ".");
		Object       variableValue  = null;

		try{
			cachedObject  = cacher.get(variablesIds[0]);
			variableValue = cachedObject.getContent();

			for(Integer cont = 1 ; cont < variablesIds.length ; cont++){
				try{
					variableValue = PropertyUtil.getProperty(variableValue, variablesIds[cont]);
				}
				catch(Throwable e){
					variableValue = null;

					break;
				}
			}
		}
		catch(ItemNotFoundException e){
		}

		return variableValue;
	}
	
	/**
	 * Cria uma vari�vel.
	 * 
     * @param name String contendo o identificador da vari�vel.
	 * @param value Inst�ncia do conte�do da vari�vel.
	 */
	public static void addVariable(String name, Object value){
		addVariable(ExpressionProcessorUtil.class.getName(), name, value);
	}

    /**
     * Cria uma vari�vel.
     * 
     * @param domain String contendo o identificador do dom�nio.
     * @param name String contendo o identificador da vari�vel.
     * @param value Inst�ncia do conte�do da vari�vel.
     */
	public static <O> void addVariable(String domain, String name, O value){
		Cacher       cacher       = CacherManager.getInstance().getCacher(domain);
		CachedObject cachedObject = new CachedObject();

		cachedObject.setId(name);
		cachedObject.setContent(value);

		try{
			cacher.add(cachedObject);
		}
		catch(ItemAlreadyExistsException e){
			try{
				cacher.set(cachedObject);
			}
			catch(ItemNotFoundException e1){
			}
		}
	}

    /**
     * Efetua a substitui��o das vari�veis em uma string.
     * A vari�vel em uma string � definida da seguinte maneira:
     * 
     * {@code @{<nome-da-vari�vel>}}
     * 
     * Ex:
     * {@code @{variavel}}
     * 
     * @param value String desejada.
     * @return String ap�s a substitui��o.
     */
	public static String fillVariablesInString(String value){
		return fillVariablesInString(ExpressionProcessorUtil.class.getName(), value);
	}

    /**
     * Efetua a substitui��o das vari�veis em uma string.
     * A vari�vel em uma string � definida da seguinte maneira:
     * 
     * {@code @{<nome-da-vari�vel>}}
     * 
     * Ex:
     * {@code @{variavel}}
     * 
     * @param domain String contendo o identificador do dom�nio.
     * @param value String desejada.
     * @return String ap�s a substitui��o.
     */
	public static String fillVariablesInString(String domain, String value){
		return fillVariablesInString(domain, value, LanguageUtil.getDefaultLanguage());
	}

    /**
     * Efetua a substitui��o das vari�veis em uma string.
     * A vari�vel em uma string � definida da seguinte maneira:
     * 
     * {@code @{<nome-da-vari�vel>}}
     * 
     * Ex:
     * {@code @{variavel}}
     * 
     * @param value String desejada.
     * @param language Inst�ncia contendo as propriedades do idioma.
     * @return String ap�s a substitui��o.
     */
	public static String fillVariablesInString(String value, Locale language){
		return fillVariablesInString(ExpressionProcessorUtil.class.getName(), value, language);
	}


    /**
     * Efetua a substitui��o das vari�veis em uma string.
     * A vari�vel em uma string � definida da seguinte maneira:
     * 
     * {@code @{<nome-da-vari�vel>}}
     * 
     * Ex:
     * {@code @{variavel}}
     * 
     * @param domain String contendo o identificador do dom�nio.
     * @param value String desejada.
     * @param language Inst�ncia contendo as propriedades do idioma.
     * @return String ap�s a substitui��o.
     */
	public static String fillVariablesInString(String domain, String value, Locale language){
		Pattern             pattern                  = Pattern.compile("\\@\\{(.*?)(\\((.*?)\\))?\\}");
		Matcher             matcher                  = pattern.matcher(value);
		String              variableExpression       = "";
		String              variableExpressionBuffer = "";
		String              variablePattern          = "";
		Object              variableValue            = null;
		ExpressionProcessor expressionProcessor      = new ExpressionProcessor(domain, language);

		while(matcher.find()){
			variableExpression = matcher.group();
			variablePattern    = StringUtil.trim(matcher.group(3));
			
			try{
				if(variablePattern.length() > 0){
					variableExpressionBuffer = StringUtil.replaceAll(variableExpression, variablePattern, "");
					variableExpressionBuffer = StringUtil.replaceAll(variableExpressionBuffer, "(", "");
					variableExpressionBuffer = StringUtil.replaceAll(variableExpressionBuffer, ")", "");
				}
				else
					variableExpressionBuffer = variableExpression;
				
				variableValue = expressionProcessor.evaluate(variableExpressionBuffer);
			}
			catch(Throwable e){
				variableValue = null;
			}
			
			if(variablePattern.length() > 0)
				variableValue = PropertyUtil.format(variableValue, variablePattern, language);
			else
				variableValue = StringUtil.trim(variableValue);
			
			value = StringUtil.replaceAll(value, variableExpression, (String)variableValue);
		}

		return value;
	}
}