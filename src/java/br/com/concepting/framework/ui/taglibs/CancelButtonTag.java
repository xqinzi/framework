package br.com.concepting.framework.ui.taglibs;

import br.com.concepting.framework.controller.action.types.ActionType;

/**
 * Classe que define o componente visual cancelButton (bot�o cancelar).
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class CancelButtonTag extends ButtonTag{
    /**
     * @see br.com.concepting.framework.ui.taglibs.ButtonTag#initialize()
     */
    protected void initialize() throws Throwable{
        if(!hasAction())
            setAction(ActionType.CANCEL);
        
        super.initialize();
    }
}