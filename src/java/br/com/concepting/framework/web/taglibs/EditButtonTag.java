package br.com.concepting.framework.web.taglibs;

import br.com.concepting.framework.web.action.types.ActionType;

/**
 * Classe que define o componente visual para o botão com a função editar um item selecionado.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class EditButtonTag extends ButtonTag{
    /**
     * @see br.com.concepting.framework.web.taglibs.ButtonTag#showOnlyWithData()
     */
    public Boolean showOnlyWithData(){
        return true;
    }

    /**
     * @see br.com.concepting.framework.web.taglibs.ButtonTag#initialize()
     */
    protected void initialize() throws Throwable{
        if(!hasAction())
            setAction(ActionType.EDIT);
	    
	    super.initialize();
	}
}