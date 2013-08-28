package br.com.concepting.framework.ui.taglibs;

/**
 * Classe que define as caracter�sticas de uma coluna, em uma tabela de dados, quando uma 
 * express�o for validada.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class GridColumnStateTag extends GridColumnTag{
	private String expression = "";
	
	/**
	 * Retorna a express�o a ser validada para que as caracter�sticas da coluna sejam 
	 * aplicadas.
	 * 
	 * @return String contendo a express�o a ser validada.
	 */
	public String getExpression(){
		return expression;
	}

	/**
	 * Define a express�o a ser validada para que as caracter�sticas da coluna sejam 
	 * aplicadas.
	 * 
	 * @param expression String contendo a express�o a ser validada.
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