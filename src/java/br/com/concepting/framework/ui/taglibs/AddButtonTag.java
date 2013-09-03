package br.com.concepting.framework.ui.taglibs;

import br.com.concepting.framework.controller.action.types.ActionType;

/**
 * Classe que define o componente visual addButton (bot�o com a fun��o adicionar um novo item).
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class AddButtonTag extends ButtonTag{
	/**
	 * @see br.com.concepting.framework.ui.taglibs.ButtonTag#initialize()
	 */
	protected void initialize() throws Throwable{
	    if(!hasAction())
	        setAction(ActionType.ADD);
	    
	    super.initialize();
	}
}