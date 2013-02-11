package br.com.concepting.framework.web.taglibs;

import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.types.ComponentType;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;

/**
 * Classe que define o componente visual para uma barra de progressão.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class ProgressBarTag extends BasePropertyTag{
	private Double maximumValue  = 0d;;
	private Double warningValue  = 0d;
	private Double criticalValue = 0d;

    /**
	 * Retorna o valor do threshold de criticidade baixa.
	 * 
	 * @return Valor numérico contendo o valor do threshold.
	 */
	public Double getWarningValue(){
    	return warningValue;
    }

	/**
	 * Define o valor do threshold de criticidade baixa.
	 * 
	 * @param warningValue Valor numérico contendo o valor do threshold.
	 */
	public void setWarningValue(Double warningValue){
    	this.warningValue = warningValue;
    }

	/**
	 * Retorna o valor do threshold de criticidade alta.
	 * 
	 * @return Valor numérico contendo o valor do threshold.
	 */
	public Double getCriticalValue(){
    	return criticalValue;
    }

	/**
	 * Define o valor do threshold de criticidade alta.
	 * 
	 * @param criticalValue Valor numérico contendo o valor do threshold.
	 */
	public void setCriticalValue(Double criticalValue){
    	this.criticalValue = criticalValue;
    }

	/**
	 * Retorna o limite de valores do componente.
	 * 
	 * @return Valor numérico contendo o limite. 
	 */
	public Double getMaximumValue(){
    	return maximumValue;
    }

	/**
	 * Define o limite de valores do componente.
	 * 
	 * @param maximumValue Valor numérico contendo o limite. 
	 */
	public void setMaximumValue(Double maximumValue){
    	this.maximumValue = maximumValue;
    }
	
	/**
	 * @see br.com.concepting.framework.web.taglibs.BasePropertyTag#initialize()
	 */
	protected void initialize() throws Throwable{
        setComponentType(ComponentType.PROGRESS_BAR);
        
        if(maximumValue <= 0)
            maximumValue = TaglibConstants.DEFAULT_PROGRESS_BAR_MAXIMUM_VALUE;

        super.initialize();
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#renderOpen()
	 */
	protected void renderOpen() throws Throwable{
		if(getPropertyInfo() != null)
			super.renderOpen();
		
        print("<table class=\"");
        print(TaglibConstants.DEFAULT_PANEL_STYLE_CLASS);
        println("\">");
		println("<tr>");
		println("<td class=\"");
		print(TaglibConstants.DEFAULT_LEFT_PROGRESS_BAR_STYLE_CLASS);
		println("\"></td>");
	}
	
	/**
	 * @see br.com.concepting.framework.web.taglibs.BasePropertyTag#renderBody()
	 */
	protected void renderBody() throws Throwable{
		Double  maximumValue = getMaximumValue();
		Double  value        = (getValue() != null ? Double.parseDouble(getValue().toString()) : 0d);
		Double  percent      = (value / maximumValue) * 100d;
		
		if(percent > 100)
			percent = 100d;
		else if(percent < 0)
			percent = 0d;
		
		String  width        = getWidth();
		Integer maximumWidth = (width.length() > 0 ? NumberUtil.parseInt(width) : 100);
		Integer currentWidth = (int)(maximumWidth * (percent / 100));
		String  symbol       = (width.indexOf("%") >= 0 ? "%" : "px");
		
		setValue(percent);
		
		if(percent > 0){
    		print("<td class=\"");
    		
    		Double warningValue  = getWarningValue();
            Double criticalValue = getCriticalValue();
    		
    		if(warningValue > 0){
    			if(percent < warningValue)
    			    print(TaglibConstants.DEFAULT_NORMAL_PROGRESS_BAR_STYLE_CLASS);
    			else{
    				if(criticalValue > 0){
    					if(percent < criticalValue)
    	                    print(TaglibConstants.DEFAULT_WARNING_PROGRESS_BAR_STYLE_CLASS);
    					else
                            print(TaglibConstants.DEFAULT_CRITICAL_PROGRESS_BAR_STYLE_CLASS);
    				}
    				else
                        print(TaglibConstants.DEFAULT_WARNING_PROGRESS_BAR_STYLE_CLASS);
    			}
    		}
    		else{
    			if(criticalValue > 0){
    				if(percent < criticalValue)
                        print(TaglibConstants.DEFAULT_NORMAL_PROGRESS_BAR_STYLE_CLASS);
    				else
                        print(TaglibConstants.DEFAULT_CRITICAL_PROGRESS_BAR_STYLE_CLASS);
    			}
    			else
                    print(TaglibConstants.DEFAULT_GENERAL_PROGRESS_BAR_STYLE_CLASS);
    		}
    
    		print("\" style=\"width: ");
    		print(currentWidth);
    		print(symbol);
    		println(";\"></td>");
		}
		
		print("<td class=\"");
		print(TaglibConstants.DEFAULT_EMPTY_PROGRESS_BAR_STYLE_CLASS);
		print("\" style=\"width: ");
		print(maximumWidth - currentWidth);
		print(symbol);
		println(";\"></td>");
	}
	
	/**
	 * @see br.com.concepting.framework.web.taglibs.BasePropertyTag#renderClose()
	 */
	protected void renderClose() throws Throwable{
		print("<td class=\"");
		print(TaglibConstants.DEFAULT_RIGHT_PROGRESS_BAR_STYLE_CLASS);
		println("\"></td>");
		
		print("<td class=\"");
		print(TaglibConstants.DEFAULT_PROGRESS_BAR_TEXT_STYLE_CLASS);
		print("\">&nbsp;&nbsp;");
		print(getFormattedValue());
		println("&nbsp;%</td>");
		
		println("</tr>");
		println("</table>");
		
		if(getPropertyInfo() != null)
			super.renderClose();
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BasePropertyTag#clearAttributes()
	 */
    protected void clearAttributes(){
	    super.clearAttributes();
	    
	    setMaximumValue(0d);
	    setWarningValue(0d);
	    setCriticalValue(0d);
    }
}