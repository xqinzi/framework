package br.com.concepting.framework.controller.form.util;
 
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.concepting.framework.controller.form.helpers.ActionFormMessage;
import br.com.concepting.framework.controller.form.types.ActionFormMessageType;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.util.ExceptionUtil;
import br.com.concepting.framework.util.LanguageUtil;
import br.com.concepting.framework.util.StringUtil;

/**
 * Classe utilitária responsável pela manipulação de mensagens de sistema.
 * 
 * @author fvilarinho
 * @since 3.0 
 */
public class ActionFormMessageUtil{
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
     * Gera uma mensagem de sistema a partir de uma exceção.
     * 
     * @param e Instância da exceção desejada.
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
     * Efetua a substituição dos atributos de uma mensagem em uma string.
     * O atributo em uma string é definido da seguinte maneira:
     * 
     * {@code ${<nome-do-atributo>}}
     * 
     * Ex:
     * {@code #{label}}
     * 
     * O identificador será substituído com o valor do atributo label da mensagem.
     *
     * @param instance Instância da mensagem desejada.
     * @param value String desejada.
     * @return String após a substituição.
     */
    public static String fillAttributesInString(ActionFormMessage instance, String value){
        return fillAttributesInString(instance, value, LanguageUtil.getDefaultLanguage());
    }

    /**
     * Efetua a substituição dos atributos de uma mensagem em uma string.
     * O atributo em uma string é definido da seguinte maneira:
     * 
     * {@code ${<nome-do-atributo>}}
     * 
     * Ex:
     * {@code #{label}}
     * 
     * O identificador será substituído com o valor do atributo label da mensagem.
     *
     * @param instance Instância da mensagem desejada.
     * @param value String desejada.
     * @param language Instância do idioma desejado.
     * @return String após a substituição.
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