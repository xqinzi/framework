package br.com.concepting.framework.web.taglibs;

import br.com.concepting.framework.web.action.types.ActionType;

/**
 * Classe que define o componente visual para o botão com a função de pesquisa de dados.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class SearchButtonTag extends ButtonTag{
	/**
	 * @see br.com.concepting.framework.web.taglibs.ButtonTag#initialize()
	 */
	protected void initialize() throws Throwable{
	    if(getAction() == null)
	        setAction(ActionType.SEARCH);
	    
	    super.initialize();
	}
}