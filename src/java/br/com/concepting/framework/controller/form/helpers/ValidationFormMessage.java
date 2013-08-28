package br.com.concepting.framework.controller.form.helpers;

import br.com.concepting.framework.controller.form.types.ActionFormMessageType;
import br.com.concepting.framework.model.types.ValidationType;

/**
 * Classe que define uma mensagem de valida��o.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class ValidationFormMessage extends ActionFormMessage{
    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param key Constante que define o tipo de valida��o.
     */
   public ValidationFormMessage(ValidationType key){
       this(key.getId());
   }

   /**
    * Construtor - Inicializa objetos e/ou vari�veis internas.
    * 
    * @param key String contendo o identificador da valida��o.
    */
   public ValidationFormMessage(String key){
       super(ActionFormMessageType.VALIDATION, key);
   }
}