package br.com.concepting.framework.ui.taglibs;

import br.com.concepting.framework.controller.action.types.ActionType;

/**
 * Classe que define o componente visual editButton (bot�o editar).
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class EditButtonTag extends ButtonTag{
    /**
     * @see br.com.concepting.framework.ui.taglibs.ButtonTag#showOnlyWithData()
     */
    public Boolean showOnlyWithData(){
        return true;
    }

    /**
     * @see br.com.concepting.framework.ui.taglibs.ButtonTag#initialize()
     */
    protected void initialize() throws Throwable{
        if(!hasAction())
            setAction(ActionType.EDIT);
	    
	    super.initialize();
	}
}