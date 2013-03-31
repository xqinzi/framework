package br.com.concepting.framework.web.taglibs;

/**
 * Classe que define as caracter�sticas de uma op��o quando uma express�o for validada.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class OptionStateTag extends BaseTag{
	private String  expression = "";
	private Boolean remove     = false;
	
	/**
	 * Indica se a op��o deve ser removida da lista quando a f�rmula for validada com sucesso.
	 * 
	 * @return True/False.
	 */
	public Boolean remove(){
    	return remove;
    }

	/**
	 * Define se a op��o deve ser removida da lista quando a f�rmula for validada com sucesso.
	 * 
	 * @param remove True/False.
	 */
	public void setRemove(Boolean remove){
    	this.remove = remove;
    }

	/**
	 * Retorna a express�o a ser validada para que as caracter�sticas da coluna sejam aplicadas.
	 * 
	 * @return String contendo a express�o a ser validada.
	 */
	public String getExpression(){
		return expression;
	}

	/**
	 * Define a express�o a ser validada para que as caracter�sticas da coluna sejam aplicadas.
	 * 
	 * @param expression String contendo a express�o a ser validada.
	 */
	public void setExpression(String expression){
		this.expression = expression;
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#initialize()
	 */
	protected void initialize() throws Throwable{
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#render()
	 */
	protected void render() throws Throwable{
        GridColumnTag gridColumnTag = (GridColumnTag)findAncestorWithClass(this, GridColumnTag.class);

        if(gridColumnTag != null){
            OptionStateTag optionState = (OptionStateTag)this.clone();

            gridColumnTag.addOptionState(optionState);
        }

        OptionsPropertyTag optionsPropertyTag = (OptionsPropertyTag)findAncestorWithClass(this, OptionsPropertyTag.class);

        if(optionsPropertyTag != null){
            OptionStateTag optionState = (OptionStateTag)this.clone();

            optionsPropertyTag.addOptionState(optionState);
        }

        BaseOptionPropertyTag optionPropertyTag = (BaseOptionPropertyTag)findAncestorWithClass(this, BaseOptionPropertyTag.class);

        if(optionPropertyTag != null){
            OptionStateTag optionState = (OptionStateTag)this.clone();

            optionPropertyTag.addOptionState(optionState);
        }
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#clearAttributes()
	 */
	protected void clearAttributes(){
		super.clearAttributes();

		setExpression("");
		setRemove(false);
	}
}