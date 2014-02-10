package br.com.concepting.framework.controller.form.helpers;

import br.com.concepting.framework.controller.form.types.ActionFormMessageType;
import br.com.concepting.framework.model.types.ValidationType;

/**
 * Classe que define uma mensagem de valida��o.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class ValidationActionFormMessage extends ActionFormMessage{
    private static final long serialVersionUID = 8110226146359639868L;

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param key Constante que define o tipo de valida��o.
     */
   public ValidationActionFormMessage(ValidationType key){
       this(key.getId());
   }

   /**
    * Construtor - Inicializa objetos e/ou vari�veis internas.
    * 
    * @param key String contendo o identificador da valida��o.
    */
   public ValidationActionFormMessage(String key){
       super(ActionFormMessageType.VALIDATION, key);
   }
}