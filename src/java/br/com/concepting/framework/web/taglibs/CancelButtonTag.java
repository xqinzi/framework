package br.com.concepting.framework.web.taglibs;

import br.com.concepting.framework.web.action.types.ActionType;

/**
 * Classe que define o componente visual para o botão com a função de cancelar.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class CancelButtonTag extends ButtonTag{
    /**
     * @see br.com.concepting.framework.web.taglibs.ButtonTag#initialize()
     */
    protected void initialize() throws Throwable{
        String action = getAction();
        
        if(action.length() == 0)
            setAction(ActionType.CANCEL);
        
        super.initialize();
    }
}