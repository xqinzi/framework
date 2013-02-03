package br.com.concepting.framework.web.taglibs;


/**
 * Classe que define o componente visual para uma opção de seleção em uma lista de opções.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class ListOptionPropertyTag extends BaseOptionPropertyTag{
    /**
     * @see br.com.concepting.framework.web.taglibs.BaseOptionPropertyTag#renderSelectionFlag()
     */
	protected void renderSelectionFlag() throws Throwable{
		if(isSelected())
			print(" selected");
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#renderLabel()
	 */
	protected void renderLabel() throws Throwable{
		print(getLabel());
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#renderOpen()
	 */
	protected void renderOpen() throws Throwable{
		print("<option");

		renderAttributes();
		renderValue();

		print(">");
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderBody()
	 */
	protected void renderBody() throws Throwable{
		renderLabel();
	}
	
	/**
	 * @see br.com.concepting.framework.web.taglibs.BasePropertyTag#renderClose()
	 */
	protected void renderClose() throws Throwable{
		println("</option>");
	}
}