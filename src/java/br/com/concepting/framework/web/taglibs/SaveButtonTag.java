package br.com.concepting.framework.web.taglibs;

import br.com.concepting.framework.web.action.types.ActionType;

/**
 * Classe que define o componente visual para o botão com a função de gravação de dados.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class SaveButtonTag extends ConfirmButtonTag{
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
        String action = getAction();
        
        if(action.length() == 0)
            setAction(ActionType.SAVE);
        
        super.initialize();
    }
}