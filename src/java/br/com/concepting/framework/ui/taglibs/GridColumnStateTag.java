package br.com.concepting.framework.ui.taglibs;

/**
 * Classe que define as características de uma coluna, em uma tabela de dados, quando uma 
 * expressão for validada.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class GridColumnStateTag extends GridColumnTag{
	private String expression = "";
	
	/**
	 * Retorna a expressão a ser validada para que as características da coluna sejam 
	 * aplicadas.
	 * 
	 * @return String contendo a expressão a ser validada.
	 */
	public String getExpression(){
		return expression;
	}

	/**
	 * Define a expressão a ser validada para que as características da coluna sejam 
	 * aplicadas.
	 * 
	 * @param expression String contendo a expressão a ser validada.
	 */
	public void setExpression(String expression){
		this.expression = expression;
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#initialize()
	 */
	protected void initialize() throws Throwable{
	}
	
	/**
	 * @see br.com.concepting.framework.ui.taglibs.GridColumnTag#render()
	 */
	protected void render() throws Throwable{
        GridColumnTag gridColumnTag = (GridColumnTag)findAncestorWithClass(this, GridColumnTag.class);

        if(gridColumnTag != null){
            GridColumnStateTag columnStateTag = (GridColumnStateTag)this.clone();

            gridColumnTag.addColumnStateTag(columnStateTag);
        }
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#clearAttributes()
	 */
	protected void clearAttributes(){
		super.clearAttributes();

		setExpression("");
	}
}