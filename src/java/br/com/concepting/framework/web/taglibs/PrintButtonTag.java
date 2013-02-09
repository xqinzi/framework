package br.com.concepting.framework.web.taglibs;

import br.com.concepting.framework.web.action.types.ActionType;

/**
 * Classe que define o componente visual para o botão com a função de impressão.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class PrintButtonTag extends ConfirmButtonTag{
    /**
     * @see br.com.concepting.framework.web.taglibs.ButtonTag#showOnlyWithData()
     */
    public Boolean showOnlyWithData(){
        return true;
    }

    /**
	 * @see br.com.concepting.framework.web.taglibs.ConfirmButtonTag#initialize()
	 */
	protected void initialize() throws Throwable{
        if(getAction() == null)
            setAction(ActionType.PRINT);
		
		super.initialize();
	}
}