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
 * Classe utilitária responsável por manipular processadores lógicos.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class ExpressionProcessorUtil{
    /**
     * Retorna o conteúdo de uma variável.
     * 
     * @param name String contendo o identificador da variável.
     * @return Instância do conteúdo da variável.
     */
	public static Object getVariable(String name){
		return getVariable(ExpressionProcessorUtil.class.getName(), name);
	}

    /**
     * Retorna o conteúdo de uma variável.
     * 
     * @param domain String contendo o identificador do domínio.
     * @param name String contendo o identificador da variável.
     * @return Instância do conteúdo da variável.
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
	 * Cria uma variável.
	 * 
     * @param name String contendo o identificador da variável.
	 * @param value Instância do conteúdo da variável.
	 */
	public static void addVariable(String name, Object value){
		addVariable(ExpressionProcessorUtil.class.getName(), name, value);
	}

    /**
     * Cria uma variável.
     * 
     * @param domain String contendo o identificador do domínio.
     * @param name String contendo o identificador da variável.
     * @param value Instância do conteúdo da variável.
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
     * Efetua a substituição das variáveis em uma string.
     * A variável em uma string é definida da seguinte maneira:
     * 
     * {@code @{<nome-da-variável>}}
     * 
     * Ex:
     * {@code @{variavel}}
     * 
     * @param value String desejada.
     * @return String após a substituição.
     */
	public static String fillVariablesInString(String value){
		return fillVariablesInString(ExpressionProcessorUtil.class.getName(), value);
	}

    /**
     * Efetua a substituição das variáveis em uma string.
     * A variável em uma string é definida da seguinte maneira:
     * 
     * {@code @{<nome-da-variável>}}
     * 
     * Ex:
     * {@code @{variavel}}
     * 
     * @param domain String contendo o identificador do domínio.
     * @param value String desejada.
     * @return String após a substituição.
     */
	public static String fillVariablesInString(String domain, String value){
		return fillVariablesInString(domain, value, LanguageUtil.getDefaultLanguage());
	}

    /**
     * Efetua a substituição das variáveis em uma string.
     * A variável em uma string é definida da seguinte maneira:
     * 
     * {@code @{<nome-da-variável>}}
     * 
     * Ex:
     * {@code @{variavel}}
     * 
     * @param value String desejada.
     * @param language Instância contendo as propriedades do idioma.
     * @return String após a substituição.
     */
	public static String fillVariablesInString(String value, Locale language){
		return fillVariablesInString(ExpressionProcessorUtil.class.getName(), value, language);
	}


    /**
     * Efetua a substituição das variáveis em uma string.
     * A variável em uma string é definida da seguinte maneira:
     * 
     * {@code @{<nome-da-variável>}}
     * 
     * Ex:
     * {@code @{variavel}}
     * 
     * @param domain String contendo o identificador do domínio.
     * @param value String desejada.
     * @param language Instância contendo as propriedades do idioma.
     * @return String após a substituição.
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