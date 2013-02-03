package br.com.concepting.framework.web.form.util;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.concepting.framework.model.types.ConditionType;
import br.com.concepting.framework.model.types.ValidationType;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.util.ExceptionUtil;
import br.com.concepting.framework.util.LanguageUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.constants.AttributeConstants;
import br.com.concepting.framework.web.form.helpers.ActionFormMessage;
import br.com.concepting.framework.web.form.types.ActionFormMessageType;

/**
 * Classe utilit�ria respons�vel pela manipula��o de mensagens de sistema.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public abstract class ActionFormMessageUtil{
    /**
     * Gera a mensagem que indica que uma propriedade do modelo de dados tem 
     * obrigatoriedade no seu preenchimento.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     */
    public static ActionFormMessage createValidationRequiredMessage(String name, String label){
        ActionFormMessage actionFormMessage = new ActionFormMessage(ActionFormMessageType.VALIDATION, ValidationType.REQUIRED.toString());
        
        actionFormMessage.addAttribute(AttributeConstants.NAME_KEY, name);
        actionFormMessage.addAttribute(AttributeConstants.LABEL_KEY, label);
        
        return actionFormMessage;
    }
    
    /**
     * Gera a mensagem de valida��o de data/hor�rio inv�lida de uma propriedade do modelo 
     * de dados.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     * @param pattern String contendo a m�scara de valida��o da data/hor�rio.
     */
    public static ActionFormMessage createValidationDateTimeMessage(String name, String label, String pattern){
        ActionFormMessage actionFormMessage = new ActionFormMessage(ActionFormMessageType.VALIDATION, ValidationType.DATE_TIME.toString());
        
        actionFormMessage.addAttribute(AttributeConstants.NAME_KEY, name);
        actionFormMessage.addAttribute(AttributeConstants.LABEL_KEY, label);
        actionFormMessage.addAttribute(AttributeConstants.PATTERN_KEY, pattern);
        
       return actionFormMessage;
    }

    /**
     * Gera a mensagem de valida��o de valores num�ricos inv�lidos de uma propriedade 
     * do modelo de dados.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     */
    public static ActionFormMessage createValidationNumberMessage(String name, String label){
        ActionFormMessage actionFormMessage = new ActionFormMessage(ActionFormMessageType.VALIDATION, ValidationType.NUMBER.toString());
        
        actionFormMessage.addAttribute(AttributeConstants.NAME_KEY, name);
        actionFormMessage.addAttribute(AttributeConstants.LABEL_KEY, label);
        
        return actionFormMessage;
    }
    
    /**
     * Gera a mensagem de compara��o de dados de uma propriedade do modelo de dados.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     * @param compareCondition Constante que indica o tipo de condi��o de compara��o.
     * @param compareName String contendo o identificador da propriedade para compara��o.
     * @param compareLabel String contendo o label da propriedade para compara��o.
     */
    public static ActionFormMessage createValidationCompareMessage(String name, String label, ConditionType compareCondition, String compareName, String compareLabel){
        ActionFormMessage actionFormMessage = new ActionFormMessage(ActionFormMessageType.VALIDATION, ValidationType.COMPARE.toString());
        
        actionFormMessage.addAttribute(AttributeConstants.NAME_KEY, name);
        actionFormMessage.addAttribute(AttributeConstants.LABEL_KEY, label);
        actionFormMessage.addAttribute(AttributeConstants.COMPARE_CONDITION_KEY, compareCondition.toString());
        actionFormMessage.addAttribute(AttributeConstants.COMPARE_NAME_KEY, compareName);
        actionFormMessage.addAttribute(AttributeConstants.COMPARE_LABEL_KEY, compareLabel);
        
        return actionFormMessage;
    }

    /**
     * Gera a mensagem de valida��o de n�mero m�ximo de palavras aceito por uma 
     * propriedade do modelo de dados.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     * @param wordCount Valor inteiro contendo o n�mero m�ximo de palavras aceitas.
     */
    public static ActionFormMessage createValidationWordCountMessage(String name, String label, Integer wordCount){
        ActionFormMessage actionFormMessage = new ActionFormMessage(ActionFormMessageType.VALIDATION, ValidationType.WORD_COUNT.toString());
        
        actionFormMessage.addAttribute(AttributeConstants.NAME_KEY, name);
        actionFormMessage.addAttribute(AttributeConstants.LABEL_KEY, label);
        actionFormMessage.addAttribute(AttributeConstants.WORD_COUNT_KEY, wordCount);
        
        return actionFormMessage;
    }

    /**
     * Gera a mensagem de valida��o de tamanho m�nimo de caracteres aceito por uma 
     * propriedade do modelo de dados.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     * @param minimumLength Valor num�rico contendo o n�mero m�nimo de caracteres aceito.
     */
    public static ActionFormMessage createValidationMinimumLengthMessage(String name, String label, Integer minimumLength){
        ActionFormMessage actionFormMessage = new ActionFormMessage(ActionFormMessageType.VALIDATION, ValidationType.MINIMUM_LENGTH.toString());
        
        actionFormMessage.addAttribute(AttributeConstants.NAME_KEY, name);
        actionFormMessage.addAttribute(AttributeConstants.LABEL_KEY, label);
        actionFormMessage.addAttribute(AttributeConstants.MINIMUM_LENGTH_KEY, minimumLength);
        
        return actionFormMessage;
    }

    /**
     * Gera a mensagem de valida��o de tamanho m�ximo de caracteres aceito por uma 
     * propriedade do modelo de dados.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     * @param maximumLength Valor num�rico contendo o n�mero m�ximo de caracteres aceito.
     */
    public static ActionFormMessage createValidationMaximumLengthMessage(String name, String label, Integer maximumLength){
        ActionFormMessage actionFormMessage = new ActionFormMessage(ActionFormMessageType.VALIDATION, ValidationType.MAXIMUM_LENGTH.toString());
        
        actionFormMessage.addAttribute(AttributeConstants.NAME_KEY, name);
        actionFormMessage.addAttribute(AttributeConstants.LABEL_KEY, label);
        actionFormMessage.addAttribute(AttributeConstants.MAXIMUM_LENGTH_KEY, maximumLength);
        
        return actionFormMessage;
    }

    /**
     * Gera a mensagem de valida��o de uma express�o regular de uma propriedade do 
     * modelo de dados.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     * @param regularExpression String contendo a express�o regular desejada.
     */
    public static ActionFormMessage createValidationRegularExpressionMessage(String name, String label, String regularExpression){
        ActionFormMessage actionFormMessage = new ActionFormMessage(ActionFormMessageType.VALIDATION, ValidationType.REGULAR_EXPRESSION.toString());
        
        actionFormMessage.addAttribute(AttributeConstants.NAME_KEY, name);
        actionFormMessage.addAttribute(AttributeConstants.LABEL_KEY, label);
        actionFormMessage.addAttribute(AttributeConstants.REGULAR_EXPRESSION_KEY, regularExpression);
        
        return actionFormMessage;
    }

    /**
     * Gera a mensagem de valida��o de e-Mail inv�lido de uma propriedade do modelo 
     * de dados.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     */
    public static ActionFormMessage createValidationEmailMessage(String name, String label){
        ActionFormMessage actionFormMessage = new ActionFormMessage(ActionFormMessageType.VALIDATION, ValidationType.EMAIL.toString());
        
        actionFormMessage.addAttribute(AttributeConstants.NAME_KEY, name);
        actionFormMessage.addAttribute(AttributeConstants.LABEL_KEY, label);
        
        return actionFormMessage;
    }

    /**
     * Gera a mensagem de valida��o de m�scara de uma propriedade do modelo de dados.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     * @param pattern String contendo a m�scara de valida��o.
     */
    public static ActionFormMessage createValidationPatternMessage(String name, String label, String pattern){
        ActionFormMessage actionFormMessage = new ActionFormMessage(ActionFormMessageType.VALIDATION, ValidationType.PATTERN.toString());
        
        actionFormMessage.addAttribute(AttributeConstants.NAME_KEY, name);
        actionFormMessage.addAttribute(AttributeConstants.LABEL_KEY, label);
        actionFormMessage.addAttribute(AttributeConstants.PATTERN_KEY, pattern);
        
        return actionFormMessage;
    }

    /**
     * Gera a mensagem de valida��o de range de dados de uma propriedade do modelo de 
     * dados.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     * @param startRange Inst�ncia contendo o range inicial.
     * @param endRange Inst�ncia contendo o range final.
     */
    public static ActionFormMessage createValidationRangeMessage(String name, String label, Object startRange, Object endRange){
        ActionFormMessage actionFormMessage = new ActionFormMessage(ActionFormMessageType.VALIDATION, ValidationType.PATTERN.toString());
        
        actionFormMessage.addAttribute(AttributeConstants.NAME_KEY, name);
        actionFormMessage.addAttribute(AttributeConstants.LABEL_KEY, label);
        actionFormMessage.addAttribute(AttributeConstants.START_RANGE_KEY, startRange);
        actionFormMessage.addAttribute(AttributeConstants.END_RANGE_KEY, endRange);
        
        return actionFormMessage;
    }

    /**
     * Gera a mensagem de valida��o customizada de uma propriedade do modelo de dados.
     * 
     * @param validation String contendo o identificador do validador da propriedade.
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     */
    public static ActionFormMessage createValidationCustomMessage(String validation, String name, String label){
        ActionFormMessage actionFormMessage = new ActionFormMessage(ActionFormMessageType.VALIDATION, validation);
        
        actionFormMessage.addAttribute(AttributeConstants.NAME_KEY, name);
        actionFormMessage.addAttribute(AttributeConstants.LABEL_KEY, label);
        
        return actionFormMessage;
    }
    
    /**
     * Gera uma mensagem informativa.
     * 
     * @param key String contendo o identificador da chave da mensagem.
     */
    public static ActionFormMessage createInfoMessage(String key){
        return new ActionFormMessage(ActionFormMessageType.INFO, key);
    }

    /**
     * Gera uma mensagem de alerta.
     * 
     * @param key String contendo o identificador da chave da mensagem.
     */
    public static ActionFormMessage createWarningMessage(String key){
        return new ActionFormMessage(ActionFormMessageType.WARNING, key);
    }

    /**
     * Gera uma mensagem de erro.
     * 
     * @param key String contendo o identificador da chave da mensagem.
     */
    public static ActionFormMessage createErrorMessage(String key){
        return new ActionFormMessage(ActionFormMessageType.ERROR, key);
    }
    
    /**
     * Gera uma mensagem de sistema a partir de uma exce��o.
     * 
     * @param e Inst�ncia da exce��o desejada.
     */
    public static ActionFormMessage createMessage(Throwable e){
        String key = ExceptionUtil.getExceptionId(e);
        
        key = key.substring(0, 1).toLowerCase().concat(key.substring(1));
        
        ActionFormMessageType messageType = null;

        if(ExceptionUtil.isInternalErrorException(e))
            messageType = ActionFormMessageType.ERROR;
        else if(ExceptionUtil.isExpectedErrorException(e))
            messageType = ActionFormMessageType.ERROR;
        else if(ExceptionUtil.isExpectedWarningException(e))
            messageType = ActionFormMessageType.WARNING;
        else
            messageType = ActionFormMessageType.ERROR;
        
        ActionFormMessage actionFormMessage = new ActionFormMessage(messageType, key);
        Field             fields[]          = e.getClass().getDeclaredFields();
        
        if(fields != null && fields.length > 0){
            String name  = "";
            Object value = null;
            
            for(Field field : fields){
                name = field.getName();
                
                try{
                    value = PropertyUtil.getProperty(e, name);
                }
                catch(Throwable e1){
                }
                
                actionFormMessage.addAttribute(name, value);
            }
        }
        
        return actionFormMessage;
    }
    
    /**
     * Efetua a substitui��o dos atributos de uma mensagem em uma string.
     * O atributo em uma string � definido da seguinte maneira:
     * 
     * {@code ${<nome-do-atributo>}}
     * 
     * Ex:
     * {@code #{label}}
     * 
     * O identificador ser� substitu�do com o valor do atributo label da mensagem.
     *
     * @param instance Inst�ncia da mensagem desejada.
     * @param value String desejada.
     * @return String ap�s a substitui��o.
     */
    public static String fillAttributesInString(ActionFormMessage instance, String value){
        return fillAttributesInString(instance, value, LanguageUtil.getDefaultLanguage());
    }

    /**
     * Efetua a substitui��o dos atributos de uma mensagem em uma string.
     * O atributo em uma string � definido da seguinte maneira:
     * 
     * {@code ${<nome-do-atributo>}}
     * 
     * Ex:
     * {@code #{label}}
     * 
     * O identificador ser� substitu�do com o valor do atributo label da mensagem.
     *
     * @param instance Inst�ncia da mensagem desejada.
     * @param value String desejada.
     * @param language Inst�ncia do idioma desejado.
     * @return String ap�s a substitui��o.
     */
    public static String fillAttributesInString(ActionFormMessage instance, String value, Locale language){
        String buffer = value;

        if(value != null && value.length() > 0){
            Pattern pattern             = Pattern.compile("\\$\\{(.*?)\\}");
            Matcher matcher             = pattern.matcher(value);
            String  attributeExpression = "";
            String  attributeName       = "";
            String  attributeValue      = "";

            while(matcher.find()){
                attributeExpression = matcher.group();
                attributeName       = StringUtil.trim(matcher.group(1));
                attributeValue      = PropertyUtil.format(instance.getAttribute(attributeName), language);
                buffer              = StringUtil.replaceAll(buffer, attributeExpression, attributeValue);
            }
        }

        return buffer;
    }
}