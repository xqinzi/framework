package br.com.concepting.framework.ui.taglibs;

import br.com.concepting.framework.util.types.ComponentType;

/**
 * Classe que define o componente visual password (entrada de senhas).
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class PasswordPropertyTag extends TextPropertyTag{
    /**
	 * @see br.com.concepting.framework.ui.taglibs.TextPropertyTag#initialize()
	 */
	protected void initialize() throws Throwable{
		setComponentType(ComponentType.PASSWORD);
		
		super.initialize();
	}
}