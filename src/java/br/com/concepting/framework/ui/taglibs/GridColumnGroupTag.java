package br.com.concepting.framework.ui.taglibs;

import br.com.concepting.framework.util.types.AlignmentType;

/**
 * Classe que define um agrupamento de colunas em uma tabela de dados.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class GridColumnGroupTag extends GridColumnTag{
	private Boolean aggregate = false;
	 
	/**
	 * Indica que a coluna deverá ser agrupada.
	 * 
	 * @return True/False.
	 */
	public Boolean aggregate(){
    	return aggregate;
    }

	/**
	 * Define se a coluna deverá ser agrupada.
	 * 
	 * @param aggregate True/False.
	 */
	public void setAggregate(Boolean aggregate){
    	this.aggregate = aggregate;
    }
	
	/**
	 * @see br.com.concepting.framework.ui.taglibs.GridColumnTag#initialize()
	 */
	protected void initialize() throws Throwable{
	    if(getAlignment() == null)
	        setAlignmentType(AlignmentType.CENTER);
	    
	    super.initialize();
	}
	
	/**
	 * @see br.com.concepting.framework.ui.taglibs.GridColumnTag#clearAttributes()
	 */
	protected void clearAttributes(){
	    super.clearAttributes();
	    
	    setAggregate(false);
	}
}