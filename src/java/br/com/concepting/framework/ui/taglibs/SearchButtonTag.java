package br.com.concepting.framework.ui.taglibs;

import br.com.concepting.framework.controller.action.types.ActionType;

/**
 * Classe que define o componente visual searchButton (botão pesquisar).
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class SearchButtonTag extends ButtonTag{
	/**
	 * @see br.com.concepting.framework.ui.taglibs.ButtonTag#initialize()
	 */
	protected void initialize() throws Throwable{
	    if(!hasAction())
	        setAction(ActionType.SEARCH);
	    
	    super.initialize();
	}
}