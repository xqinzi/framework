package br.com.concepting.framework.web.taglibs;

/**
 * Classe que define as características de uma linha, em uma tabela de dados, quando uma 
 * expressão for validada.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class GridRowStateTag extends OptionStateTag{
	private String linkStyleClass = "";
	private String linkStyle      = "";

	/**
	 * Retorna o estilo CSS do link.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getLinkStyleClass(){
		return linkStyleClass;
	}

	/**
	 * Define o estilo CSS do link da coluna.
	 * 
	 * @param linkStyleClass String contendo o estilo CSS.
	 */
	public void setLinkStyleClass(String linkStyleClass){
		this.linkStyleClass = linkStyleClass;
	}

	/**
	 * Retorna o estilo CSS do link da coluna.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getLinkStyle(){
		return linkStyle;
	}

	/**
	 * Define o estilo CSS do link da coluna.
	 * 
	 * @param linkStyle String contendo o estilo CSS.
	 */
	public void setLinkStyle(String linkStyle){
		this.linkStyle = linkStyle;
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#initialize()
	 */
	protected void initialize() throws Throwable{
	}
    
	/**
	 * @see br.com.concepting.framework.web.taglibs.OptionStateTag#render()
	 */
    protected void render() throws Throwable{
        GridPropertyTag gridTag = (GridPropertyTag)findAncestorWithClass(this, GridPropertyTag.class);

        if(gridTag != null){
            GridRowStateTag rowState = (GridRowStateTag)this.clone();

            gridTag.addRowStateTag(rowState);
        }
    }

    /**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#clearAttributes()
	 */
	protected void clearAttributes(){
		super.clearAttributes();

		setLinkStyleClass("");
		setLinkStyle("");
	}
}