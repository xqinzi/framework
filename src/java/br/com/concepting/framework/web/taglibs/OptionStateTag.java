package br.com.concepting.framework.web.taglibs;

/**
 * Classe que define as características de uma opção quando uma expressão for validada.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class OptionStateTag extends BaseTag{
	private String  expression = "";
	private Boolean remove     = false;
	
	/**
	 * Indica se a opção deve ser removida da lista quando a fórmula for validada com sucesso.
	 * 
	 * @return True/False.
	 */
	public Boolean remove(){
    	return remove;
    }

	/**
	 * Define se a opção deve ser removida da lista quando a fórmula for validada com sucesso.
	 * 
	 * @param remove True/False.
	 */
	public void setRemove(Boolean remove){
    	this.remove = remove;
    }

	/**
	 * Retorna a expressão a ser validada para que as características da coluna sejam aplicadas.
	 * 
	 * @return String contendo a expressão a ser validada.
	 */
	public String getExpression(){
		return expression;
	}

	/**
	 * Define a expressão a ser validada para que as características da coluna sejam aplicadas.
	 * 
	 * @param expression String contendo a expressão a ser validada.
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