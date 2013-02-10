package br.com.concepting.framework.web.form.helpers;

import br.com.concepting.framework.model.types.ValidationType;
import br.com.concepting.framework.web.form.types.ActionFormMessageType;

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