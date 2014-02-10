package br.com.concepting.framework.controller.form.helpers;

import br.com.concepting.framework.controller.form.types.ActionFormMessageType;
import br.com.concepting.framework.model.types.ValidationType;

/**
 * Classe que define uma mensagem de validação.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class ValidationActionFormMessage extends ActionFormMessage{
    private static final long serialVersionUID = 8110226146359639868L;

    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param key Constante que define o tipo de validação.
     */
   public ValidationActionFormMessage(ValidationType key){
       this(key.getId());
   }

   /**
    * Construtor - Inicializa objetos e/ou variáveis internas.
    * 
    * @param key String contendo o identificador da validação.
    */
   public ValidationActionFormMessage(String key){
       super(ActionFormMessageType.VALIDATION, key);
   }
}