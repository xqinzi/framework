package br.com.concepting.framework.controller.form;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.struts.action.ActionMessages;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.controller.SystemController;
import br.com.concepting.framework.controller.form.helpers.ActionFormMessage;
import br.com.concepting.framework.controller.form.types.ActionFormMessageType;
import br.com.concepting.framework.controller.form.util.ActionFormMessageUtil;
import br.com.concepting.framework.controller.form.util.ActionFormValidationMessageUtil;
import br.com.concepting.framework.model.types.ConditionType;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.web.types.ScopeType;

/**
 * Classe responsável pelo controle das mensagens do sistema.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class ActionFormMessageController{
    private SystemController systemController = null;
    
    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param systemController Instância do controlador de requisições.
     */
    public ActionFormMessageController(SystemController systemController){
        super();
        
        this.systemController = systemController;
    }
    
    /**
     * Limpa todas as mensagens do sistema.
     */
    public void clearAllMessages(){
        clearMessages(ActionFormMessageType.INFO);
        clearMessages(ActionFormMessageType.ERROR);
        clearMessages(ActionFormMessageType.WARNING);
        clearMessages(ActionFormMessageType.VALIDATION);
        
        if(systemController != null)
            systemController.setCurrentException(null);
    }
    
    /**
     * Limpa todas as mensagens do sistema a partir de um tipo específico.
     * 
     * @param type Constante que define o tipo da mensagem.
     */
    public void clearMessages(ActionFormMessageType type){
        if(systemController != null)
            systemController.setAttribute(type.toString(), null, ScopeType.SESSION);
    }
    
    /**
     * Retorna a lista de mensagens do sistema a partir de um tipo específico.
     * 
     * @param type Constante que define o tipo da mensagem.
     * @return Instância contendo os tipos de mensagem.
     */
    public ActionMessages getMessages(ActionFormMessageType type){
        if(systemController != null)
            return systemController.findAttribute(type.toString(), ScopeType.SESSION);
        
        return null;
    }
    
    /**
     * Retorna se uma propriedade do modelo de dados possui mensagens de validação.
     * 
     * @param name String contendo o identificador da propriedade desejada.
     * @return True/False.
     */
    public Boolean hasValidationMessage(String name){
        List<ActionFormMessage> validationMessages = getValidationMessages(name);
        
        return (validationMessages != null && validationMessages.size() > 0);
    }
    
    /**
     * Retorna a lista de mensagens de validação de uma propriedade do modelo de dados.
     * 
     * @param name String contendo o identificador da propriedade desejada.
     * @return Lista contendo as mensagens de validação.
     */
    public List<ActionFormMessage> getValidationMessages(String name){
        ActionMessages          actionMessages     = getMessages(ActionFormMessageType.VALIDATION);
        List<ActionFormMessage> validationMessages = null;

        if(actionMessages != null && actionMessages.size() > 0){
            ActionFormMessage           validationMessage = null;
            String                      value             = "";
            Iterator<ActionFormMessage> iterator          = actionMessages.get();

            while(iterator.hasNext()){
                validationMessage = iterator.next();
                value             = validationMessage.getAttribute(AttributeConstants.NAME_KEY);
                
                if(value != null && value.equals(name)){
                    if(validationMessages == null)
                        validationMessages = new LinkedList<ActionFormMessage>();
                    
                    validationMessages.add(validationMessage);
                }
            }
        }

        return validationMessages;
    }

    /**
     * Adiciona uma mensagem no sistema.
     * 
     * @param actionFormMessage Instância contendo as propriedades da mensagem.
     */
    public void addMessage(ActionFormMessage actionFormMessage){
        ActionFormMessageType type               = actionFormMessage.getType();
        ActionMessages        actionFormMessages = getMessages(type);

        if(actionFormMessages == null) 
            actionFormMessages = new ActionMessages();

        if(actionFormMessages.size() > 0){
            ActionFormMessage           item        = null;
            Iterator<ActionFormMessage> iterator    = actionFormMessages.get();
            String                      key         = actionFormMessage.getKey();
            String                      name        = "";
            String                      compareName = StringUtil.trim(actionFormMessage.getAttribute(AttributeConstants.NAME_KEY));

            while(iterator.hasNext()){
                item = iterator.next();
                name = StringUtil.trim(item.getAttribute(AttributeConstants.NAME_KEY));
                
                if(type.equals(item.getType()) && key.equals(item.getKey())){
                    if(name.length() > 0 && name.equals(compareName))
                        return;
                    else if(name.length() == 0)
                        return;
                }
            }
        }
        
        actionFormMessages.add(ActionMessages.GLOBAL_MESSAGE, actionFormMessage);
    
        systemController.setAttribute(type.toString(), actionFormMessages, ScopeType.SESSION);
    }
    
    /**
     * Adiciona uma mensagem de operação realizada com sucesso.
     */
    public void addSuccessMessage(){
        addInfoMessage(AttributeConstants.SUCCESS_KEY);
    }
    
    /**
     * Adiciona uma mensagem informativa.
     * 
     * @param key String contendo o identificador da chave da mensagem.
     */
    public void addInfoMessage(String key){
        addMessage(ActionFormMessageUtil.createInfoMessage(key));
    }

    /**
     * Adiciona uma mensagem de alerta.
     * 
     * @param key String contendo o identificador da chave da mensagem.
     */
    public void addWarningMessage(String key){
        addMessage(ActionFormMessageUtil.createWarningMessage(key));
    }

    /**
     * Adiciona uma mensagem de erro.
     * 
     * @param key String contendo o identificador da chave da mensagem.
     */
    public void addErrorMessage(String key){
        addMessage(ActionFormMessageUtil.createErrorMessage(key));
    }
    
    /**
     * Adiciona uma mensagem no sistema a partir de uma exceção.
     * 
     * @param e Instância da exceção desejada.
     */
    public void addMessage(Throwable e){
        addMessage(ActionFormMessageUtil.createMessage(e));
    }
    
    /**
     * Adiciona a mensagem que indica que uma propriedade do modelo de dados tem 
     * obrigatoriedade no seu preenchimento.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     */
    public void addValidationRequiredMessage(String name, String label){
        addMessage(ActionFormValidationMessageUtil.createValidationRequiredMessage(name, label));
    }
    
    /**
     * Adiciona a mensagem de validação de data/horário inválida de uma propriedade do modelo 
     * de dados.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     * @param pattern String contendo a máscara de validação da data/horário.
     */
    public void addValidationDateTimeMessage(String name, String label, String pattern){
        addMessage(ActionFormValidationMessageUtil.createValidationDateTimeMessage(name, label, pattern));
    }

    /**
     * Adiciona a mensagem de validação de valores numéricos inválidos de uma propriedade 
     * do modelo de dados.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     */
    public void addValidationNumberMessage(String name, String label){
        addMessage(ActionFormValidationMessageUtil.createValidationNumberMessage(name, label));
    }
    
    /**
     * Adiciona a mensagem de comparação de dados de uma propriedade do modelo de dados.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     * @param compareCondition Constante que indica o tipo de condição de comparação.
     * @param compareName String contendo o identificador da propriedade para comparação.
     * @param compareLabel String contendo o label da propriedade para comparação.
     */
    public void addValidationCompareMessage(String name, String label, ConditionType compareCondition, String compareName, String compareLabel){
        addMessage(ActionFormValidationMessageUtil.createValidationCompareMessage(name, label, compareCondition, compareName, compareLabel));
    }

    /**
     * Adiciona a mensagem de validação de número máximo de palavras aceito por uma 
     * propriedade do modelo de dados.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     * @param wordCount Valor inteiro contendo o número máximo de palavras aceitas.
     */
    public void addValidationWordCountMessage(String name, String label, Integer wordCount){
        addMessage(ActionFormValidationMessageUtil.createValidationWordCountMessage(name, label, wordCount));
    }

    /**
     * Adiciona a mensagem de validação de tamanho mínimo de caracteres aceito por uma 
     * propriedade do modelo de dados.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     * @param minimumLength Valor numérico contendo o número mínimo de caracteres aceito.
     */
    public void addValidationMinimumLengthMessage(String name, String label, Integer minimumLength){
        addMessage(ActionFormValidationMessageUtil.createValidationMinimumLengthMessage(name, label, minimumLength));
    }

    /**
     * Adiciona a mensagem de validação de tamanho máximo de caracteres aceito por uma 
     * propriedade do modelo de dados.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     * @param maximumLength Valor numérico contendo o número máximo de caracteres aceito.
     */
    public void addValidationMaximumLengthMessage(String name, String label, Integer maximumLength){
        addMessage(ActionFormValidationMessageUtil.createValidationMaximumLengthMessage(name, label, maximumLength));
    }

    /**
     * Adiciona a mensagem de validação de uma expressão regular de uma propriedade do 
     * modelo de dados.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     * @param regularExpression String contendo a expressão regular desejada.
     */
    public void addValidationRegularExpressionMessage(String name, String label, String regularExpression){
        addMessage(ActionFormValidationMessageUtil.createValidationRegularExpressionMessage(name, label, regularExpression));
    }

    /**
     * Adiciona a mensagem de validação de e-Mail inválido de uma propriedade do modelo 
     * de dados.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     */
    public void addValidationEmailMessage(String name, String label){
        addMessage(ActionFormValidationMessageUtil.createValidationEmailMessage(name, label));
    }

    /**
     * Adiciona a mensagem de validação de máscara de uma propriedade do modelo de dados.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     * @param pattern String contendo a máscara de validação.
     */
    public void addValidationPatternMessage(String name, String label, String pattern){
        addMessage(ActionFormValidationMessageUtil.createValidationPatternMessage(name, label, pattern));
    }

    /**
     * Adiciona a mensagem de validação de range de dados de uma propriedade do modelo de 
     * dados.
     * 
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     * @param startRange Instância contendo o range inicial.
     * @param endRange Instância contendo o range final.
     */
    public void addValidationRangeMessage(String name, String label, Object startRange, Object endRange){
        addMessage(ActionFormValidationMessageUtil.createValidationRangeMessage(name, label, startRange, endRange));
    }

    /**
     * Adiciona a mensagem de validação customizada de uma propriedade do modelo de dados.
     * 
     * @param validation String contendo o identificador do validador da propriedade.
     * @param name String contendo o identificador da propriedade.
     * @param label String contendo o label da propriedade.
     */
    public void addValidationCustomMessage(String validation, String name, String label){
        addMessage(ActionFormValidationMessageUtil.createValidationCustomMessage(validation, name, label));
    }
}
