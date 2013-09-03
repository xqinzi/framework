package br.com.concepting.framework.ui.taglibs;

import br.com.concepting.framework.controller.action.types.ActionType;

/**
 * Classe que define o componente visual saveButton (botão salvar).
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class SaveButtonTag extends ConfirmButtonTag{
    /**
     * @see br.com.concepting.framework.ui.taglibs.ButtonTag#showOnlyWithData()
     */
    public Boolean showOnlyWithData(){
        return true;
    }

    /**
     * @see br.com.concepting.framework.ui.taglibs.ConfirmButtonTag#initialize()
     */
    protected void initialize() throws Throwable{
        if(!hasAction())
            setAction(ActionType.SAVE);
        
        super.initialize();
    }
}