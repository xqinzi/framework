package br.com.concepting.framework.ui.taglibs;

import br.com.concepting.framework.controller.action.types.ActionType;

/**
 * Classe que define o componente visual deleteButton (botão excluir).
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class DeleteButtonTag extends ConfirmButtonTag{
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
            setAction(ActionType.DELETE);
		
		super.initialize();
	}
}