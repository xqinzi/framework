package br.com.concepting.framework.web.taglibs;

import br.com.concepting.framework.util.types.ComponentType;

/**
 * Classe que define uma propriedade não visível em um formulário.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class HiddenPropertyTag extends BasePropertyTag{
    /**
	 * @see br.com.concepting.framework.web.taglibs.BasePropertyTag#initialize()
	 */
	protected void initialize() throws Throwable{
	    setComponentType(ComponentType.HIDDEN);
	    
	    super.initialize();
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#render()
	 */
	protected void render() throws Throwable{
		renderBody();
	}
}