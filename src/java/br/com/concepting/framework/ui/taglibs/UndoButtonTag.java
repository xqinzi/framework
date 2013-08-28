package br.com.concepting.framework.ui.taglibs;

import br.com.concepting.framework.util.types.ComponentType;

/**
 * Classe que define o componente visual para o botão com a função "undo" ou "reset".
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class UndoButtonTag extends ButtonTag{
	/**
	 * @see br.com.concepting.framework.ui.taglibs.ButtonTag#initialize()
	 */
	protected void initialize() throws Throwable{
	    setComponentType(ComponentType.UNDO_BUTTON);
	    
	    super.initialize();
	}
}