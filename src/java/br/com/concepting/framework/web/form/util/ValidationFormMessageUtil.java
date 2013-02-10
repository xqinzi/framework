package br.com.concepting.framework.web.form.util;

import br.com.concepting.framework.model.types.ConditionType;
import br.com.concepting.framework.model.types.ValidationType;
import br.com.concepting.framework.util.constants.AttributeConstants;
import br.com.concepting.framework.web.form.helpers.ValidationFormMessage;

/**
 * Classe utilitária responsável pela manipulação de mensagens de validação.
 * 
 * @author fvilarinho
 * @since 3.0 
 */
public class ValidationFormMessageUtil extends ActionFormMessageUtil{
    /**
     * Gera a mensagem que indica que uma propriedade do modelo de dados tem 
     * obrigatoriedade no seu preenchimento.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     */
    public static ValidationFormMessage createValidationRequiredMessage(String name, String label){
        ValidationFormMessage validationFormMessage = new ValidationFormMessage(ValidationType.REQUIRED);
        
        validationFormMessage.addAttribute(AttributeConstants.NAME_KEY, name);
        validationFormMessage.addAttribute(AttributeConstants.LABEL_KEY, label);
        
        return validationFormMessage;
    }
    
    /**
     * Gera a mensagem de validação de data/horário inválida de uma propriedade do modelo 
     * de dados.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     * @param pattern String contendo a máscara de validação da data/horário.
     */
    public static ValidationFormMessage createValidationDateTimeMessage(String name, String label, String pattern){
        ValidationFormMessage validationFormMessage = new ValidationFormMessage(ValidationType.DATE_TIME);
        
        validationFormMessage.addAttribute(AttributeConstants.NAME_KEY, name);
        validationFormMessage.addAttribute(AttributeConstants.LABEL_KEY, label);
        validationFormMessage.addAttribute(AttributeConstants.PATTERN_KEY, pattern);
        
       return validationFormMessage;
    }

    /**
     * Gera a mensagem de validação de valores numéricos inválidos de uma propriedade 
     * do modelo de dados.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     */
    public static ValidationFormMessage createValidationNumberMessage(String name, String label){
        ValidationFormMessage validationFormMessage = new ValidationFormMessage(ValidationType.NUMBER);
        
        validationFormMessage.addAttribute(AttributeConstants.NAME_KEY, name);
        validationFormMessage.addAttribute(AttributeConstants.LABEL_KEY, label);
        
        return validationFormMessage;
    }
    
    /**
     * Gera a mensagem de comparação de dados de uma propriedade do modelo de dados.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     * @param compareCondition Constante que indica o tipo de condição de comparação.
     * @param compareName String contendo o identificador da propriedade para comparação.
     * @param compareLabel String contendo o label da propriedade para comparação.
     */
    public static ValidationFormMessage createValidationCompareMessage(String name, String label, ConditionType compareCondition, String compareName, String compareLabel){
        ValidationFormMessage validationFormMessage = new ValidationFormMessage(ValidationType.COMPARE);
        
        validationFormMessage.addAttribute(AttributeConstants.NAME_KEY, name);
        validationFormMessage.addAttribute(AttributeConstants.LABEL_KEY, label);
        validationFormMessage.addAttribute(AttributeConstants.COMPARE_CONDITION_KEY, compareCondition.getOperator());
        validationFormMessage.addAttribute(AttributeConstants.COMPARE_NAME_KEY, compareName);
        validationFormMessage.addAttribute(AttributeConstants.COMPARE_LABEL_KEY, compareLabel);
        
        return validationFormMessage;
    }

    /**
     * Gera a mensagem de validação de número máximo de palavras aceito por uma 
     * propriedade do modelo de dados.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     * @param wordCount Valor inteiro contendo o número máximo de palavras aceitas.
     */
    public static ValidationFormMessage createValidationWordCountMessage(String name, String label, Integer wordCount){
        ValidationFormMessage validationFormMessage = new ValidationFormMessage(ValidationType.WORD_COUNT);
        
        validationFormMessage.addAttribute(AttributeConstants.NAME_KEY, name);
        validationFormMessage.addAttribute(AttributeConstants.LABEL_KEY, label);
        validationFormMessage.addAttribute(AttributeConstants.WORD_COUNT_KEY, wordCount);
        
        return validationFormMessage;
    }

    /**
     * Gera a mensagem de validação de tamanho mínimo de caracteres aceito por uma 
     * propriedade do modelo de dados.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     * @param minimumLength Valor numérico contendo o número mínimo de caracteres aceito.
     */
    public static ValidationFormMessage createValidationMinimumLengthMessage(String name, String label, Integer minimumLength){
        ValidationFormMessage validationFormMessage = new ValidationFormMessage(ValidationType.MINIMUM_LENGTH);
        
        validationFormMessage.addAttribute(AttributeConstants.NAME_KEY, name);
        validationFormMessage.addAttribute(AttributeConstants.LABEL_KEY, label);
        validationFormMessage.addAttribute(AttributeConstants.MINIMUM_LENGTH_KEY, minimumLength);
        
        return validationFormMessage;
    }

    /**
     * Gera a mensagem de validação de tamanho máximo de caracteres aceito por uma 
     * propriedade do modelo de dados.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     * @param maximumLength Valor numérico contendo o número máximo de caracteres aceito.
     */
    public static ValidationFormMessage createValidationMaximumLengthMessage(String name, String label, Integer maximumLength){
        ValidationFormMessage validationFormMessage = new ValidationFormMessage(ValidationType.MAXIMUM_LENGTH);
        
        validationFormMessage.addAttribute(AttributeConstants.NAME_KEY, name);
        validationFormMessage.addAttribute(AttributeConstants.LABEL_KEY, label);
        validationFormMessage.addAttribute(AttributeConstants.MAXIMUM_LENGTH_KEY, maximumLength);
        
        return validationFormMessage;
    }

    /**
     * Gera a mensagem de validação de uma expressão regular de uma propriedade do 
     * modelo de dados.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     * @param regularExpression String contendo a expressão regular desejada.
     */
    public static ValidationFormMessage createValidationRegularExpressionMessage(String name, String label, String regularExpression){
        ValidationFormMessage validationFormMessage = new ValidationFormMessage(ValidationType.REGULAR_EXPRESSION);
        
        validationFormMessage.addAttribute(AttributeConstants.NAME_KEY, name);
        validationFormMessage.addAttribute(AttributeConstants.LABEL_KEY, label);
        validationFormMessage.addAttribute(AttributeConstants.REGULAR_EXPRESSION_KEY, regularExpression);
        
        return validationFormMessage;
    }

    /**
     * Gera a mensagem de validação de e-Mail inválido de uma propriedade do modelo 
     * de dados.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     */
    public static ValidationFormMessage createValidationEmailMessage(String name, String label){
        ValidationFormMessage validationFormMessage = new ValidationFormMessage(ValidationType.EMAIL);
        
        validationFormMessage.addAttribute(AttributeConstants.NAME_KEY, name);
        validationFormMessage.addAttribute(AttributeConstants.LABEL_KEY, label);
        
        return validationFormMessage;
    }

    /**
     * Gera a mensagem de validação de máscara de uma propriedade do modelo de dados.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     * @param pattern String contendo a máscara de validação.
     */
    public static ValidationFormMessage createValidationPatternMessage(String name, String label, String pattern){
        ValidationFormMessage validationFormMessage = new ValidationFormMessage(ValidationType.PATTERN);
        
        validationFormMessage.addAttribute(AttributeConstants.NAME_KEY, name);
        validationFormMessage.addAttribute(AttributeConstants.LABEL_KEY, label);
        validationFormMessage.addAttribute(AttributeConstants.PATTERN_KEY, pattern);
        
        return validationFormMessage;
    }

    /**
     * Gera a mensagem de validação de range de dados de uma propriedade do modelo de 
     * dados.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     * @param startRange Instância contendo o range inicial.
     * @param endRange Instância contendo o range final.
     */
    public static ValidationFormMessage createValidationRangeMessage(String name, String label, Object startRange, Object endRange){
        ValidationFormMessage validationFormMessage = new ValidationFormMessage(ValidationType.RANGE);
        
        validationFormMessage.addAttribute(AttributeConstants.NAME_KEY, name);
        validationFormMessage.addAttribute(AttributeConstants.LABEL_KEY, label);
        validationFormMessage.addAttribute(AttributeConstants.START_RANGE_KEY, startRange);
        validationFormMessage.addAttribute(AttributeConstants.END_RANGE_KEY, endRange);
        
        return validationFormMessage;
    }

    /**
     * Gera a mensagem de validação customizada de uma propriedade do modelo de dados.
     * 
     * @param validation String contendo o identificador do validador da propriedade.
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     */
    public static ValidationFormMessage createValidationCustomMessage(String validation, String name, String label){
        ValidationFormMessage validationFormMessage = new ValidationFormMessage(validation);
        
        validationFormMessage.addAttribute(AttributeConstants.NAME_KEY, name);
        validationFormMessage.addAttribute(AttributeConstants.LABEL_KEY, label);
        
        return validationFormMessage;
    }
}