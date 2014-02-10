package br.com.concepting.framework.model.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.BinaryType;
import org.hibernate.type.BooleanType;
import org.hibernate.type.ByteType;
import org.hibernate.type.CurrencyType;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.EnumType;
import org.hibernate.type.FloatType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.SerializableType;
import org.hibernate.type.ShortType;
import org.hibernate.type.StringType;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.persistence.types.DateTimeType;
import br.com.concepting.framework.processors.ExpressionProcessor;
import br.com.concepting.framework.resource.PropertiesResource;
import br.com.concepting.framework.util.DateTimeUtil;
import br.com.concepting.framework.util.LanguageUtil;
import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.Currency;
import br.com.concepting.framework.util.helpers.DateTime;
import br.com.concepting.framework.util.helpers.Node;

/**
 * Classe utilitária responsável por manipular informações de propriedades de classes/método 
 * via Reflection.
 *
 * @author fvilarinho
 * @since 1.0 
 */
public class PropertyUtil extends PropertyUtils{
	/**
	 * Limpa todas as propriedades de uma instância específica. 
	 *
	 * @param instance Instância desejada.
	 */
    public static void clearAllProperties(Object instance){
		if(instance == null)
			return;
		
		Class<?> superClass = instance.getClass();
		Class<?> clazz      = null;
		Field    fields[]   = null;
		
		while(superClass != null){
			fields = superClass.getDeclaredFields();
			
     		for(Field fieldItem : fields){
     		    clazz = fieldItem.getType();
     		    
     			try{
     			    if(PropertyUtil.isNumber(clazz))
     			        setProperty(instance, fieldItem.getName(), NumberUtil.parse(clazz, "0"));
     			    else if(PropertyUtil.isBoolean(clazz))
     			        setProperty(instance, fieldItem.getName(), false);
         			else if(PropertyUtil.isString(fieldItem.getType()))
         				setProperty(instance, fieldItem.getName(), "");
         			else
         				setProperty(instance, fieldItem.getName(), null);
     			}
     			catch(Throwable e){
     			}
     		}
     		
     		superClass = superClass.getSuperclass();
		}
	}

    /**
     * Indica se a classe é do tipo String.
     * 
     * @param clazz Classe a ser verificada.
     * @return True/False.
     */
	public static Boolean isString(Class<?> clazz){
	    return (clazz.equals(String.class));
	}
	
    /**
     * Indica se a classe é do tipo Boolean.
     * 
     * @param clazz Classe a ser verificada.
     * @return True/False.
     */
	public static Boolean isBoolean(Class<?> clazz){
        return (clazz.equals(boolean.class) || clazz.equals(Boolean.class));
	}
	
    /**
     * Indica se a classe é do tipo Date.
     * 
     * @param clazz Classe a ser verificada.
     * @return True/False.
     */
	public static Boolean isDate(Class<?> clazz){
	    return clazz.equals(Date.class) || clazz.equals(DateTime.class);
	}
	
    /**
     * Indica se a classe é do tipo DateTime.
     * 
     * @param clazz Classe a ser verificada.
     * @return True/False.
     */
    public static Boolean isTime(Class<?> clazz){
        return clazz.equals(DateTime.class);
    }

    /**
     * Indica se a classe é um array de bytes.
     * 
     * @param clazz Classe a ser verificada.
     * @return True/False.
     */
    public static Boolean isByteArray(Class<?> clazz){
	    return (clazz.equals(byte[].class) || clazz.equals(Byte[].class));
	}
	
    /**
     * Indica se a classe é do tipo Integer.
     * 
     * @param clazz Classe a ser verificada.
     * @return True/False.
     */
	public static Boolean isInteger(Class<?> clazz){
	    return (clazz.equals(int.class) || clazz.equals(Integer.class));
	}
	
    /**
     * Indica se a classe é do tipo Long.
     * 
     * @param clazz Classe a ser verificada.
     * @return True/False.
     */
    public static Boolean isLong(Class<?> clazz){
        return (clazz.equals(long.class) || clazz.equals(Long.class));
    }
    
    /**
     * Indica se a classe é do tipo Short.
     * 
     * @param clazz Classe a ser verificada.
     * @return True/False.
     */
    public static Boolean isShort(Class<?> clazz){
        return (clazz.equals(short.class) || clazz.equals(Short.class));
    }
    
    /**
     * Indica se a classe é do tipo Byte.
     * 
     * @param clazz Classe a ser verificada.
     * @return True/False.
     */
    public static Boolean isByte(Class<?> clazz){
        return (clazz.equals(byte.class) || clazz.equals(Byte.class));
    }
    
    /**
     * Indica se a classe é do tipo Float.
     * 
     * @param clazz Classe a ser verificada.
     * @return True/False.
     */
    public static Boolean isFloat(Class<?> clazz){
        return (clazz.equals(float.class) || clazz.equals(Float.class));
    }

    /**
     * Indica se a classe é do tipo Double.
     * 
     * @param clazz Classe a ser verificada.
     * @return True/False.
     */
    public static Boolean isDouble(Class<?> clazz){
        return (clazz.equals(double.class) || clazz.equals(Double.class));
    }

    /**
     * Indica se a classe é do tipo BigInteger.
     * 
     * @param clazz Classe a ser verificada.
     * @return True/False.
     */
    public static Boolean isBigInteger(Class<?> clazz){
        return (clazz.equals(BigInteger.class));
    }

    /**
     * Indica se a classe é do tipo BigDecimal.
     * 
     * @param clazz Classe a ser verificada.
     * @return True/False.
     */
    public static Boolean isBigDecimal(Class<?> clazz){
        return (clazz.equals(BigDecimal.class));
    }

    /**
     * Indica se a classe é um número.
     * 
     * @param clazz Classe a ser verificada.
     * @return True/False.
     */
    public static Boolean isNumber(Class<?> clazz){
        return (isByte(clazz) || isShort(clazz) || isInteger(clazz) || isLong(clazz) || isFloat(clazz) || isDouble(clazz) || isBigInteger(clazz) || isBigDecimal(clazz));
	}
	
    /**
     * Indica se a classe é um número.
     * 
     * @param clazz Classe a ser verificada.
     * @return True/False.
     */
    public static Boolean isCurrency(Class<?> clazz){
        return (clazz.equals(Currency.class));
    }

    /**
     * Indica se a classe é um modelo de dados.
     * 
     * @param clazz Classe a ser verificada.
     * @return True/False.
     */
    public static Boolean isModel(Class<?> clazz){
        Class<?> superClass = clazz;
        
        while(true){
            if(superClass.getSuperclass() == null || superClass.getSuperclass().equals(Node.class))
                break;
            
            superClass = superClass.getSuperclass();
        }

        return (clazz.getAnnotation(Model.class) != null && superClass.equals(BaseModel.class));
	}
	
    /**
     * Indica se a classe é uma coleção.
     * 
     * @param clazz Classe a ser verificada.
     * @return True/False.
     */
	public static Boolean isCollection(Class<?> clazz){
        Class<?> superClass = clazz;
        
        while(true){
            if(superClass.getSuperclass() == null || superClass.getSuperclass().equals(Object.class))
                break;
            
            superClass = superClass.getSuperclass();
        }

        return (superClass.equals(AbstractCollection.class) || superClass.equals(Collection.class));
	}
	
    /**
     * Indica se a classe é um Enum.
     * 
     * @param clazz Classe a ser verificada.
     * @return True/False.
     */
	public static Boolean isEnum(Class<?> clazz){
	    return clazz.isEnum();
	}

	/**
	 * Efetua o merge das propriedades de duas instâncias.
	 *
	 * @param instance1 Instância 1.
	 * @param instance2 Instância 2.
	 * @return Instância após processamento.
	 */
	public static <O> O mergeAllProperties(O instance1, O instance2){
		if(instance1 == null)
			return instance2;

		if(instance2 == null)
			return instance1;

		Object   propertyValue1 = null;
		Object   propertyValue2 = null;
		String   propertyValue  = "";
		Class<?> clazz          = instance1.getClass();
		Field    fields[]       = null;
		
		while(clazz != null){
			fields = clazz.getDeclaredFields();

			for(Field fieldItem : fields){
				try{
					propertyValue1 = getProperty(instance1, fieldItem.getName());
					propertyValue2 = getProperty(instance2, fieldItem.getName());
					propertyValue  = StringUtil.trim(propertyValue1);
					
					if(propertyValue.length() > 0 && !propertyValue.equals("none")){
						if(propertyValue2 != null && !propertyValue1.equals(propertyValue2)){
          					if(propertyValue1 instanceof BaseModel){
          						propertyValue2 = mergeAllProperties(propertyValue1, propertyValue2);
          
          						setProperty(instance2, fieldItem.getName(), propertyValue2);
          					}
          					else
     							setProperty(instance2, fieldItem.getName(), propertyValue1);
						}
					}
				}
				catch(Throwable e){
				}
			}

			clazz = clazz.getSuperclass();
		}

		return instance2;
	}

	/**
	 * Efetua a formatação de uma instância.
	 *
	 * @param instance Instância a ser formatada.
	 * @param language Instância contendo as propriedades do idioma desejado.
	 * @return String formatada.
	 */
	public static String format(Object instance, Locale language){
	    if(language == null)
	        return format(instance, null, "", false, Constants.DEFAULT_NUMBER_PRECISION, LanguageUtil.getDefaultLanguage());
	    
		return format(instance, null, "", false, Constants.DEFAULT_NUMBER_PRECISION, language);
	}

	/**
	 * Efetua a formatação de uma instância.
	 *
	 * @param instance Instância a ser formatada.
	 * @param pattern String contendo a máscara desejada.
	 * @return String formatada.
	 */
	public static String format(Object instance, String pattern){
		return format(instance, pattern, LanguageUtil.getDefaultLanguage());
	}

	/**
	 * Efetua a formatação de uma instância.
	 *
	 * @param instance Instância a ser formatada.
	 * @param language Instância contendo as propriedades do idioma desejado.
	 * @param pattern String contendo a máscara desejada.
	 * @return String formatada.
	 */
	public static String format(Object instance, String pattern, Locale language){
	    if(language == null)
	        format(instance, null, pattern, false, Constants.DEFAULT_NUMBER_PRECISION, LanguageUtil.getDefaultLanguage());
	    
		return format(instance, null, pattern, false, Constants.DEFAULT_NUMBER_PRECISION, language);
	}
	
    /**
     * Efetua a formatação de uma instância.
     *
     * @param instance Instância a ser formatada.
     * @return String formatada.
     */
    public static String format(Object instance){
        return format(instance, null, "", false, Constants.DEFAULT_NUMBER_PRECISION, LanguageUtil.getDefaultLanguage());
    }

    /**
	 * Efetua a formatação de uma instância.
	 *
	 * @param instance Instância a ser formatada.
	 * @param useAdditionalFormatting Indica se deve ser usado o caracter separador de milhar quando 
	 * a instância for uma valor numérico.
	 * @return String formatada.
	 */
	public static String format(Object instance, Boolean useAdditionalFormatting){
        return format(instance, null, "", useAdditionalFormatting, Constants.DEFAULT_NUMBER_PRECISION, LanguageUtil.getDefaultLanguage());
	}

    /**
     * Efetua a formatação de uma instância.
     *
     * @param instance Instância a ser formatada.
     * @param useAdditionalFormatting Indica se deve ser usado o caracter separador de milhar quando 
     * a instância for uma valor numérico.
     * @param language Instância contendo as propriedades do idioma desejado.
     * @return String formatada.
     */
    public static String format(Object instance, Boolean useAdditionalFormatting, Locale language){
        if(language == null)
            return format(instance, null, "", useAdditionalFormatting, Constants.DEFAULT_NUMBER_PRECISION, LanguageUtil.getDefaultLanguage());
        
        return format(instance, null, "", useAdditionalFormatting, Constants.DEFAULT_NUMBER_PRECISION, language);
    }

    /**
	 * Efetua a formatação de uma instância.
	 *
	 * @param instance Instância a ser formatada.
	 * @param useAdditionalFormatting Indica se deve ser usado o caracter separador de milhar quando 
	 * a instância for uma valor numérico.
	 * @param precision Valor numérico contendo as decimais de precisão.
	 * @param language Instância contendo as propriedades do idioma desejado.
	 * @return String formatada.
	 */
	public static String format(Object instance, Boolean useAdditionalFormatting, Integer precision, Locale language){
	    if(language == null)
	        return format(instance, null, "", useAdditionalFormatting, precision, LanguageUtil.getDefaultLanguage());
	    
	    if(precision == null)
	        return format(instance, null, "", useAdditionalFormatting, Constants.DEFAULT_NUMBER_PRECISION, language);
	    
		return format(instance, null, "", useAdditionalFormatting, precision, language);
	}

	/**
	 * Efetua a formatação de uma instância.
	 *
	 * @param instance Instância a ser formatada.
	 * @param valueMap Mapa de valores das propriedades da instância.
	 * @param pattern String contendo a máscara desejada.
	 * @param useAdditionalFormatting Indica se deve ser usado o caracter separador de milhar quando
     * a instância for uma valor numérico. 
	 * @param precision Valor inteiro contendo o número de decimais de precisão. Usado quando
	 * a instância for uma valor numérico.
	 * @param language Instância contendo as propriedades do idioma desejado.
	 * @return String formatada.
	 */
    public static String format(Object instance, Map<String, ?> valueMap, String pattern, Boolean useAdditionalFormatting, Integer precision, Locale language){
		String result = "";
		
		if(instance instanceof Date){
		    if(valueMap != null){
    		    Object valueInstance = valueMap.get(instance);
    		    
    		    if(valueInstance != null)
    		        instance = valueInstance;
		    }
		    
		    Date value = (Date)instance;
		    
			if(pattern.length() == 0)
		        result = DateTimeUtil.format(value, language);
			else
				result = DateTimeUtil.format(value, pattern, language);
		}
		else if(instance instanceof Number){
		    if(valueMap != null){
                Object valueInstance = valueMap.get(instance);
                
                if(valueInstance != null)
                    instance = valueInstance;
		    }

            Number value = (Number)instance;
		    
			if(pattern.length() == 0)
				result = NumberUtil.format(value, useAdditionalFormatting, precision, language);
			else
				result = NumberUtil.format(value, pattern, language);
		}
		else if(instance instanceof Collection){
			StringBuilder resultBuffer = new StringBuilder();
			
			for(Object item : (Collection<?>)instance){
				if(resultBuffer.length() > 0)
					resultBuffer.append(", ");

				if(valueMap != null)
					item = valueMap.get(item);
				
				resultBuffer.append(format(item, valueMap, pattern, useAdditionalFormatting, precision, language));
			}
			
			result = resultBuffer.toString();
		}
		else{
            if(valueMap != null){
                Object valueInstance = valueMap.get(instance);
                
                if(valueInstance != null)
                    instance = valueInstance;
            }
		    
			result = StringUtil.trim(instance);

			if(pattern.length() > 0 && result.length() > 0)
				result = StringUtil.format(result, pattern);
		}
		
		return result;
	}

	/**
	 * Efetua a substituição das propriedades de uma instância em uma string.
	 * A propriedade em uma string é definida da seguinte maneira:
	 * 
	 * {@code #{<nome-da-propriedade>.<nome-da-propriedade-filha>}}
	 * 
	 * Ex:
	 * {@code #{loginSession.user.name}}
	 * 
	 * O identificador será substituído com o valor da propriedade name da instância user 
	 * armazenada na instância loginSession.
	 *
	 * @param instance Instância a ser utilizada para substituição.
	 * @param value String desejada.
	 * @return String após a substituição.
	 */
	public static String fillPropertiesInString(Object instance, String value){
		return fillPropertiesInString(instance, value, true);
	}

	/**
	 * Efetua a substituição das propriedades de uma instância em uma string.
	 * A propriedade em uma string é definida da seguinte maneira:
	 * 
	 * {@code #{<nome-da-propriedade>.<nome-da-propriedade-filha>}}
	 * 
	 * Ex:
	 * {@code #{loginSession.user.name}}
	 * 
	 * O identificador será substituído com o valor da propriedade name da instância user 
	 * armazenada na instância loginSession.
	 *
	 * @param instance Instância a ser utilizada para substituição.
	 * @param value String desejada.
	 * @param language Instância contendo as configurações do idioma desejado.
	 * @return String após a substituição.
	 */
	public static String fillPropertiesInString(Object instance, String value, Locale language){
		return fillPropertiesInString(instance, value, true, language);
	}
	
	/**
	 * Efetua a substituição das propriedades de uma instância em uma string.
	 * A propriedade em uma string é definida da seguinte maneira:
	 * 
	 * {@code #{<nome-da-propriedade>.<nome-da-propriedade-filha>}}
	 * 
	 * Ex:
	 * {@code #{loginSession.user.name}}
	 * 
	 * O identificador será substituído com o valor da propriedade name da instância user 
	 * armazenada na instância loginSession.
	 *
	 * @param instance Instância a ser utilizada para substituição.
	 * @param value String desejada.
	 * @param replaceNotFoundMatches Indica se as propriedades não encontradas devem ser 
	 * substituídas por valores vazios.
	 * @return String após a substituição.
	 */
	public static String fillPropertiesInString(Object instance, String value, boolean replaceNotFoundMatches){
		return fillPropertiesInString(instance, value, replaceNotFoundMatches, null);
	}
	
	/**
	 * Efetua a substituição das propriedades de uma instância em uma string.
	 * A propriedade em uma string é definida da seguinte maneira:
	 * 
	 * {@code #{<nome-da-propriedade>.<nome-da-propriedade-filha>}}
	 * 
	 * Ex:
	 * {@code #{loginSession.user.name}}
	 * 
	 * O identificador será substituído com o valor da propriedade name da instância user 
	 * armazenada na instância loginSession.
	 *
	 * @param instance Instância a ser utilizada para substituição.
	 * @param value String desejada.
	 * @param replaceNotFoundMatches Indica se as propriedades não encontradas devem ser 
	 * substituídas por valores vazios.
	 * @param language Instância contendo as configurações do idioma desejado.
	 * @return String após a substituição.
	 */
	public static String fillPropertiesInString(Object instance, String value, boolean replaceNotFoundMatches, Locale language){
		if(StringUtil.trim(value).length() > 0 && instance != null){
			PropertyInfo        propertyInfo             = null;
			Pattern             pattern                  = Pattern.compile("\\#\\{(.*?)(\\((.*?)\\))?\\}");
			Matcher             matcher                  = pattern.matcher(value);
			String              propertyExpression       = "";
			String              propertyExpressionBuffer = "";
			String              propertyName             = "";
			String              propertyPattern          = "";
			Object              propertyValue            = null;
			ExpressionProcessor expressionProcessor      = new ExpressionProcessor(instance, language);
			
			if(language == null)
				language = LanguageUtil.getDefaultLanguage();
			
			while(matcher.find()){
				propertyExpression = matcher.group();
				propertyName       = StringUtil.trim(matcher.group(1));
				propertyPattern    = StringUtil.trim(matcher.group(3));
				
				try{
					if(propertyPattern.length() > 0){
						propertyExpressionBuffer = StringUtil.replaceAll(propertyExpression, propertyPattern, "");
						propertyExpressionBuffer = StringUtil.replaceAll(propertyExpressionBuffer, "(", "");
						propertyExpressionBuffer = StringUtil.replaceAll(propertyExpressionBuffer, ")", "");
					}
					else
						propertyExpressionBuffer = propertyExpression;

					propertyValue = expressionProcessor.evaluate(propertyExpressionBuffer);
				}
				catch(Throwable e){
					propertyValue = null;
				}
				
				try{
					propertyInfo = getPropertyInfo(instance.getClass(), propertyName);
				}
				catch(Throwable e){
					propertyInfo = null;
				}
				
				if((propertyValue == null && replaceNotFoundMatches) || propertyValue != null){
					Boolean useAdditionalFormatting = false;
					
					if(propertyPattern.length() == 0){
    					if(propertyInfo != null && propertyPattern.length() == 0){
         					propertyPattern = propertyInfo.getPattern();
         					
         					if(propertyInfo.isDate())
         						useAdditionalFormatting = propertyInfo.isTime();
         					else if(propertyInfo.isNumber())
         						useAdditionalFormatting = propertyInfo.useGroupSeparator();
    					}
					}
					
					if(propertyPattern.length() > 0)
						propertyValue = format(propertyValue, propertyPattern, language);
					else
						propertyValue = format(propertyValue, useAdditionalFormatting, language);

					value = StringUtil.replaceAll(value, propertyExpression, (String)propertyValue);
				}
			}
		}

		return value;
	}
	
    /**
     * Efetua a substituição das propriedades de um arquivo em uma string.
     * A propriedade em uma string é definida da seguinte maneira:
     * 
     * {@code ${<nome-da-propriedade>.<nome-da-propriedade-filha>}}
     * 
     * Ex:
     * {@code #{loginSession.user.name}}
     * 
     * O identificador será substituído com o valor da propriedade name da instância user 
     * armazenada na instância loginSession.
     *
     * @param propertiesResource Instância contendo as propriedades a serem utilizadas para 
     * substituição.
     * @param value String desejada.
     * @return String após a substituição.
     */
	public static String fillPropertiesResourceInString(PropertiesResource propertiesResource, String value){
		String buffer = value;

		if(value != null && value.length() > 0){
			Pattern pattern              = Pattern.compile("\\$\\{(.*?)\\}");
			Matcher matcher              = pattern.matcher(value);
			String  propertiesExpression = "";
			String  propertiesName       = "";
			String  propertiesValue      = "";

			while(matcher.find()){
				propertiesExpression = matcher.group();
				propertiesName       = StringUtil.trim(matcher.group(1));
				propertiesValue      = StringUtil.trim(propertiesResource.getProperty(propertiesName, false));
				buffer               = StringUtil.replaceAll(buffer, propertiesExpression, propertiesValue);
				buffer               = fillPropertiesResourceInString(propertiesResource, buffer);
			}
		}

		return buffer;
	}

	/**
	 * Efetua a substituição das variáveis de ambiente em uma string.
	 * A variável de ambiente em uma string é definida da seguinte maneira:
	 * 
	 * Ex:
	 * {@code ${<nome-da-variável>}}
	 *
	 * @param value String desejada.
	 * @return String após a substituição.
	 */
	public static String fillEnvironmentPropertiesInString(String value){
		String buffer = value;

		if(value != null && value.length() > 0){
			Pattern pattern               = Pattern.compile("\\$\\{(.*?)\\}");
			Matcher matcher               = pattern.matcher(value);
			String  environmentExpression = "";
			String  environmentName       = "";
			String  environmentValue      = "";

			while(matcher.find()){
				environmentExpression = matcher.group();
				environmentName       = StringUtil.trim(matcher.group(1));
				environmentValue      = StringUtil.trim(System.getenv(environmentName));
				buffer                = StringUtil.replaceAll(buffer, environmentExpression, environmentValue);
				buffer                = fillEnvironmentPropertiesInString(buffer);
			}
		}

		return buffer;
	}

	/**
	 * Retorna o tipo genérico de uma propriedade em uma classe.
	 *
	 * @param instanceClass Classe desejada.
	 * @param name String contendo o identificador da propriedade.
	 * @return Classe do tipo genérico da propriedade.
	 * @throws ClassNotFoundException
	 * @throws NoSuchFieldException 
	 */
	public static Class<?> getGenericPropertyClass(Class<?> instanceClass, String name) throws ClassNotFoundException, NoSuchFieldException{
		Field propertyField = getPropertyField(instanceClass, name);
		
		return getGenericPropertyClass(propertyField);
	}

	/**
	 * Retorna o tipo genérico de uma propriedade em uma classe.
	 *
	 * @param field Instância contendo as definições da propriedade.
	 * @return Classe do tipo genérico da propriedade.
	 * @throws ClassNotFoundException
	 */
	public static Class<?> getGenericPropertyClass(Field field) throws ClassNotFoundException{
		Type     genericType   = field.getGenericType();
		Type     type          = field.getType();
		String   typeName      = field.getType().getName();
		Class<?> propertyClass = null;

		if(!genericType.equals(type)){
			String buffer = StringUtil.replaceAll(genericType.toString(), typeName, "");
			
			buffer = StringUtil.replaceAll(buffer, "<", "");
			buffer = StringUtil.replaceAll(buffer, ">", "");
			
			propertyClass = Class.forName(buffer);
		}
		else
			propertyClass = Object.class;

		return propertyClass;
	}

	/**
	 * Retorna as definições de uma propriedade em uma classe.
	 *
	 * @param instanceClass Classe desejada.
	 * @param name String contendo o identificador da propriedade.
	 * @return Instância contendo as definições da propriedade.
	 * @throws NoSuchFieldException 
	 */
	public static Field getPropertyField(Class<?> instanceClass, String name) throws NoSuchFieldException{
		String   names[] = StringUtil.split(name, ".");
		Class<?> clazz   = instanceClass;
		Field    field   = null;
		
		for(String nameItem : names){
			while(clazz != null){
				try{
        			field = clazz.getDeclaredField(nameItem);
        			clazz = field.getType();
        			
        			break;
				}
				catch(Throwable e){
					clazz = clazz.getSuperclass();
				}
			}
		}
		
		if(field == null)
			throw new NoSuchFieldException(name);
		
		return field;
	}

	/**
	 * Retorna o tipo de uma propriedade em uma classe.
	 *
	 * @param instanceClass Classe desejada.
	 * @param name String contendo o identificador da propriedade.
	 * @return Classe do tipo da propriedade.
	 * @throws NoSuchFieldException 
	 */
	public static Class<?> getPropertyClass(Class<?> instanceClass, String name) throws NoSuchFieldException{
		ModelInfo modelInfo     = ModelUtil.getModelInfo(instanceClass);
		Class<?>  propertyClass = null;
		
		if(modelInfo != null){
			PropertyInfo propertyInfo = modelInfo.getPropertyInfo(name);
			
			if(propertyInfo != null)
				propertyClass = propertyInfo.getClazz();
		}
		
		if(propertyClass != null)
			return getPropertyClass(propertyClass);
		
		Field propertyField = getPropertyField(instanceClass, name);

		return getPropertyClass(propertyField);
	}
	
	/**
	 * Retorna o tipo de uma propriedade em uma classe.
	 *
	 * @param propertyField Instância contendo as definições da propriedade.
	 * @return Classe do tipo genérico da propriedade.
	 */
	private static Class<?> getPropertyClass(Field propertyField){
		if(propertyField != null){
			Class<?> propertyClass = propertyField.getType();

			return getPropertyClass(propertyClass);
		}
		
		return null;
	}

	/**
	 * Retorna o tipo instanciável de uma classe.
	 *
	 * @param propertyClass Classe desejada.
	 * @return Classe do tipo instanciável.
	 */
	public static Class<?> getPropertyClass(Class<?> propertyClass){
		if(propertyClass.equals(Collection.class) || propertyClass.equals(List.class))
			propertyClass = LinkedList.class;
		else if(propertyClass.equals(Set.class))
			propertyClass = LinkedHashSet.class;
		else if(propertyClass.equals(Map.class))
			propertyClass = LinkedHashMap.class;
		
		return propertyClass;
	}

	/**
	 * Define o valor de uma propriedade.
	 *
	 * @param instance Instância desejada.
	 * @param name String contendo o identificador da propriedade.
	 * @param value Instância contendo o valor da propriedade.
	 * @throws InvocationTargetException 
	 */
	public static void setProperty(Object instance, String name, Object value) throws InvocationTargetException{
		if(instance == null)
			return;

		String   propertyNames[]     = StringUtil.split(name, ".");
		String   propertyName        = "";
		Class<?> propertyClass       = null;
		Object   propertyValue       = instance;
		Object   propertyValueBuffer = null;

		try{
    		for(Integer cont = 0 ; cont < (propertyNames.length - 1) ; cont++){
    			propertyName        = propertyNames[cont];
    			propertyClass       = getPropertyClass(propertyValue.getClass(), propertyName);
    			propertyValueBuffer = getProperty(propertyValue, propertyName);
    			
    			if(propertyValueBuffer == null)
    				propertyValueBuffer = ConstructorUtils.invokeConstructor(propertyClass, null);
    			
    			PropertyUtils.setProperty(propertyValue, propertyName, propertyValueBuffer);
    			
    			propertyValue = propertyValueBuffer;
    		}
    		
			propertyName = propertyNames[propertyNames.length - 1];
			
    		PropertyUtils.setProperty(propertyValue, propertyName, value);
		}
		catch(Throwable e){
			throw new InvocationTargetException(e);
		}
	}

	/**
	 * Retorna o valor de uma propriedade.
	 *
	 * @param instance Instância desejada.
	 * @param name String contendo o identificador da propriedade.
	 * @return Instância contendo o valor da propriedade.
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static Object getProperty(Object instance, String name) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		String propertyNames[] = StringUtil.split(name, ".");
		Object propertyValue   = instance;

		for(String propertyName : propertyNames){
			propertyValue = PropertyUtils.getProperty(propertyValue, propertyName);
			
			if(propertyValue == null)
				break;
		}

		return propertyValue;
	}
	
	/**
     * Retorna a instância contendo as características de uma propriedade.
     * 
     * @param instance Instância desejada.
     * @param name String contendo o identificador da propriedade.
     * @return Instância contendo as características da propriedade.
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws NoSuchFieldException
	 */
	public static PropertyInfo getPropertyInfo(Object instance, String name) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException{
	    if(instance == null)
	        return null;
	    
	    PropertyInfo propertyInfo = null;
	    
	    try{
	        propertyInfo = (PropertyInfo)getPropertyInfo(instance.getClass(), name).clone();
	    }
	    catch(CloneNotSupportedException e){
	    }
		
	    if(propertyInfo != null){
		    String classPropertyId = propertyInfo.getClassPropertyId();
		
		    if(classPropertyId.length() > 0){
		        String className = StringUtil.trim(PropertyUtil.getProperty(instance, classPropertyId));
			
		        if(className.length() > 0){
		            Class<?> clazz = Class.forName(className);
				
		            propertyInfo.setClazz(clazz);
		        }
			}
	    }
		
		return propertyInfo;
	}

	/**
	 * Retorna a instância contendo as características de uma propriedade.
	 *
	 * @param instanceClass Classe desejada.
	 * @param name String contendo o identificador da propriedade.
	 * @return Instância contendo as características da propriedade.
	 * @throws ClassNotFoundException 
	 * @throws NoSuchFieldException 
	 */
	public static PropertyInfo getPropertyInfo(Class<?> instanceClass, String name) throws ClassNotFoundException, NoSuchFieldException{
	    if(instanceClass == null)
	        return null;
	    
	    ModelInfo modelInfo = ModelUtil.getModelInfo(instanceClass);
	    
	    if(modelInfo != null)
	        return modelInfo.getPropertyInfo(name);
	    
		Field    propertyField      = getPropertyField(instanceClass, name);
		Property propertyAnnotation = propertyField.getAnnotation(Property.class);
		
		return getPropertyInfo(propertyField, propertyAnnotation);
	}

	/**
	 * Retorna a instância contendo as características de uma propriedade.
	 *
	 * @param instanceClass Classe desejada.
	 * @param propertyAnnotation Instância contendo as anotações da propriedade.
	 * @return Instância contendo as características da propriedade.
	 * @throws ClassNotFoundException 
	 * @throws NoSuchFieldException 
	 */
	public static PropertyInfo getPropertyInfo(Class<?> instanceClass, Property propertyAnnotation) throws ClassNotFoundException, NoSuchFieldException{
		Field propertyField = getPropertyField(instanceClass, propertyAnnotation.propertyId());
		
		return getPropertyInfo(propertyField, propertyAnnotation);
	}
	
	/**
	 * Retorna a instância contendo as características de uma propriedade.
	 *
	 * @param propertyField Instância contendo as definições da propriedade. 
	 * @param propertyAnnotation Instância contendo as anotações da propriedade.
	 * @return Instância contendo as características da propriedade.
	 * @throws ClassNotFoundException
	 */
	private static PropertyInfo getPropertyInfo(Field propertyField, Property propertyAnnotation) throws ClassNotFoundException{
		PropertyInfo propertyInfo  = new PropertyInfo();
		Class<?>     propertyClass = null;
		
		propertyInfo.setId(propertyField.getName());

		if(propertyAnnotation != null){
    		if(!propertyAnnotation.propertyClass().equals(Object.class))
    			propertyClass = propertyAnnotation.propertyClass();
    		else if(!propertyAnnotation.relationClass().equals(Object.class))
        		propertyClass = propertyAnnotation.relationClass();
    		else
    		    propertyClass = getPropertyClass(propertyField);
		}
		else
		    propertyClass = getPropertyClass(propertyField);
    		
		propertyInfo.setClazz(propertyClass);
		propertyInfo.setIsEnum(isEnum(propertyClass));
		propertyInfo.setIsBoolean(isBoolean(propertyClass));
		propertyInfo.setIsByteArray(isByteArray(propertyClass));
		propertyInfo.setIsCollection(isCollection(propertyClass));
		propertyInfo.setIsDate(isDate(propertyClass));
		propertyInfo.setIsTime(isTime(propertyClass));
		propertyInfo.setIsModel(isModel(propertyClass));
		propertyInfo.setIsNumber(isNumber(propertyClass));
		propertyInfo.setIsCurrency(isCurrency(propertyClass));
		propertyInfo.setIsString(isString(propertyClass));
		
		if(propertyInfo.isCollection()){
			Class<?> collectionItemsClass = null;

			if(propertyAnnotation != null && !propertyAnnotation.relationCollectionItemsClass().equals(Object.class))
				collectionItemsClass = propertyAnnotation.relationCollectionItemsClass();
			else
				collectionItemsClass = getGenericPropertyClass(propertyField);
			
			propertyInfo.setCollectionItemsClass(collectionItemsClass);
			propertyInfo.setHasModel(isModel(collectionItemsClass));
			propertyInfo.setHasEnum(isEnum(collectionItemsClass));
		}
    
		if(propertyAnnotation != null){
	        propertyInfo.setMappedPropertyType(propertyAnnotation.mappedPropertyType());
    		propertyInfo.setIsUnique(propertyAnnotation.isUnique());
    		propertyInfo.setIsAuditable(propertyAnnotation.isAuditable());
    		propertyInfo.setIsIdentity(propertyAnnotation.isIdentity());
    		propertyInfo.setKeyId(propertyAnnotation.keyId());
    		propertyInfo.setForeignKeyId(propertyAnnotation.foreignKeyId());
    		propertyInfo.setConstrained(propertyAnnotation.constrained());
    		propertyInfo.setNullable(propertyAnnotation.nullable());
    		propertyInfo.setAutoGenerateIdentity(propertyAnnotation.autoGenerateIdentity());
    		propertyInfo.setIsForSearch(propertyAnnotation.isForSearch());
    		propertyInfo.setSearchCondition(propertyAnnotation.searchCondition());
    		propertyInfo.setCaseSensitiveSearch(propertyAnnotation.caseSensitiveSearch());
    		propertyInfo.setContextSearchType(propertyAnnotation.contextSearchType());
    		propertyInfo.setValidations(propertyAnnotation.validations());
    		propertyInfo.setCustomValidationId(propertyAnnotation.customValidationId());
    		propertyInfo.setMinimumLength(propertyAnnotation.minimumLength());
    		propertyInfo.setMaximumLength(propertyAnnotation.maximumLength());
    		propertyInfo.setStartRange(propertyAnnotation.startRange());
    		propertyInfo.setEndRange(propertyAnnotation.endRange());
    		propertyInfo.setPattern(propertyAnnotation.pattern());
    		propertyInfo.setRegularExpression(propertyAnnotation.regularExpression());
    		propertyInfo.setPersistPattern(propertyAnnotation.persistPattern());
    		propertyInfo.setPropertyId(propertyAnnotation.propertyId());
    		propertyInfo.setSearchPropertyId(propertyAnnotation.searchPropertyId());
    		propertyInfo.setPhoneticPropertyId(propertyAnnotation.phoneticPropertyId());
    		propertyInfo.setClassPropertyId(propertyAnnotation.classPropertyId());
    		propertyInfo.setPropertiesIds(propertyAnnotation.propertiesIds());
    		propertyInfo.setMappedPropertyId(propertyAnnotation.mappedPropertyId());
    		propertyInfo.setMappedPropertiesIds(propertyAnnotation.mappedPropertiesIds());
    		propertyInfo.setRelationType(propertyAnnotation.relationType());
    		propertyInfo.setRelationJoinType(propertyAnnotation.relationJoinType());
    		propertyInfo.setCascadeOnSave(propertyAnnotation.cascadeOnSave());
    		propertyInfo.setCascadeOnDelete(propertyAnnotation.cascadeOnDelete());
    		propertyInfo.setMappedRelationPropertiesIds(propertyAnnotation.mappedRelationPropertiesIds());
    		propertyInfo.setMappedRelationRepositoryId(propertyAnnotation.mappedRelationRepositoryId());
    		propertyInfo.setUseGroupSeparator(propertyAnnotation.useGroupSeparator());
    		propertyInfo.setFormulaExpression(propertyAnnotation.formulaExpression());
    		propertyInfo.setFormulaType(propertyAnnotation.formulaType());
    		propertyInfo.setSortOrder(propertyAnnotation.sortOrder());
    		propertyInfo.setPhoneticAccuracy(propertyAnnotation.phoneticAccuracy());
    		propertyInfo.setCompareCondition(propertyAnnotation.compareCondition());
    		propertyInfo.setComparePropertyId(propertyAnnotation.comparePropertyId());
    		propertyInfo.setWordCount(propertyAnnotation.wordCount());
    		propertyInfo.setTag(propertyAnnotation.tag());
    		propertyInfo.setPrecision(propertyAnnotation.precision());
    		propertyInfo.setLanguage(propertyAnnotation.language());
		}
		
        if(propertyInfo.getMappedPropertyType().length() == 0){
            if(isEnum(propertyClass))
                propertyInfo.setMappedPropertyType(EnumType.class.getName());
            else if(isBoolean(propertyClass))
                propertyInfo.setMappedPropertyType(BooleanType.INSTANCE.getName());
            else if(isByteArray(propertyClass))
                propertyInfo.setMappedPropertyType(BinaryType.INSTANCE.getName());
            else if(isTime(propertyClass))
                propertyInfo.setMappedPropertyType(DateTimeType.class.getName());
            else if(isDate(propertyClass))
                propertyInfo.setMappedPropertyType(DateType.INSTANCE.getName());
            else if(isBigDecimal(propertyClass))
                propertyInfo.setMappedPropertyType(BigDecimalType.INSTANCE.getName());
            else if(isBigInteger(propertyClass))
                propertyInfo.setMappedPropertyType(BigIntegerType.INSTANCE.getName());
            else if(isByte(propertyClass))
                propertyInfo.setMappedPropertyType(ByteType.INSTANCE.getName());
            else if(isCurrency(propertyClass))
                propertyInfo.setMappedPropertyType(CurrencyType.INSTANCE.getName());
            else if(isDouble(propertyClass))
                propertyInfo.setMappedPropertyType(DoubleType.INSTANCE.getName());
            else if(isFloat(propertyClass))
                propertyInfo.setMappedPropertyType(FloatType.INSTANCE.getName());
            else if(isInteger(propertyClass))
                propertyInfo.setMappedPropertyType(IntegerType.INSTANCE.getName());
            else if(isLong(propertyClass))
                propertyInfo.setMappedPropertyType(LongType.INSTANCE.getName());
            else if(isShort(propertyClass))
                propertyInfo.setMappedPropertyType(ShortType.INSTANCE.getName());
            else if(isString(propertyClass))
                propertyInfo.setMappedPropertyType(StringType.INSTANCE.getName());
            else
                propertyInfo.setMappedPropertyType(SerializableType.INSTANCE.getName());
        }
    			
    	return propertyInfo;
	}

	/**
	 * Retorna uma lista contendo as características das propriedades de uma classe.
	 *
	 * @param instanceClass Classe desejada.
	 * @return Lista contendo as características das propriedades.
	 */
	public static Collection<PropertyInfo> getPropertiesInfo(Class<?> instanceClass){
		Collection<PropertyInfo> propertiesInfo     = new LinkedList<PropertyInfo>();
		PropertyInfo             propertyInfo       = null;
		Field                    propertyFields[]   = null;
		Property                 propertyAnnotation = null;

		while(true){
			propertyFields = instanceClass.getDeclaredFields();

			for(Field propertyField : propertyFields){
			    propertyAnnotation = propertyField.getAnnotation(Property.class);
			    
			    if(propertyAnnotation != null){
    				try{
    					propertyInfo = getPropertyInfo(propertyField, propertyAnnotation);
    					
    					if(propertyInfo != null)
    						propertiesInfo.add(propertyInfo);
    				}
    				catch(Throwable e){
    				}
			    }
			}

			instanceClass = instanceClass.getSuperclass();
			
			if(instanceClass == null)
				break;
		}

		return propertiesInfo;
	}
}