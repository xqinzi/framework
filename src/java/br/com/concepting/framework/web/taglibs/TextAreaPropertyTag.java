package br.com.concepting.framework.web.taglibs;

import br.com.concepting.framework.web.types.ComponentType;

/**
 * Classe que define o componente visual para entrada de texto em múltiplas linhas.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class TextAreaPropertyTag extends TextPropertyTag{
	private Integer rows    = 0;
	private Integer columns = 0;

	/**
	 * Retorna o número máximo de caracteres por linha.
	 * 
	 * @return Valor inteiro contendo o número máximo de caracteres por linha.
	 */
	public Integer getColumns(){
		return columns;
	}

	/**
	 * Define o número máximo de caracteres por linha.
	 * 
	 * @param columns Valor inteiro contendo o número máximo de caracteres por linha.
	 */
	public void setColumns(Integer columns){
		this.columns = columns;
	}

	/**
	 * Retorna o número máximo de linhas.
	 * 
	 * @return Valor inteiro contendo o número máximo de linhas.
	 */
	public Integer getRows(){
		return rows;
	}

	/**
	 * Define o número máximo de linhas.
	 * 
	 * @param rows Valor inteiro contendo o número máximo de linhas.
	 */
	public void setRows(Integer rows){
		this.rows = rows;
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BasePropertyTag#renderValue()
	 */
	protected void renderValue() throws Throwable{
		println(getFormattedValue());
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderAttributes()
	 */
	protected void renderAttributes() throws Throwable{
		super.renderAttributes();

		Integer rows = getRows();
		
		if(rows > 0){
			print(" rows=\"");
			print(rows);
			print("\"");
		}
		
		Integer columns = getColumns();

		if(columns > 0){
			print(" cols=\"");
			print(columns);
			print("\"");
		}
	}
	
	/**
	 * @see br.com.concepting.framework.web.taglibs.TextPropertyTag#initialize()
	 */
	protected void initialize() throws Throwable{
	    setComponentType(ComponentType.TEXTAREA);

	    super.initialize();
	}
	
	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#renderOpen()
	 */
	protected void renderOpen() throws Throwable{
		super.renderOpen();
		
		print("<textarea");

		renderAttributes();

		print(">");
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderBody()
	 */
	protected void renderBody() throws Throwable{
		renderValue();
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BasePropertyTag#renderClose()
	 */
	protected void renderClose() throws Throwable{
		print("</textarea>");

		super.renderClose();
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.TextPropertyTag#clearAttributes()
	 */
    protected void clearAttributes(){
	    super.clearAttributes();
	     
	    setRows(0);
	    setColumns(0);
    }
}