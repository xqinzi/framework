package br.com.concepting.framework.web.taglibs;

import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;
 
/**
 * Classe que define o componente visual slider (Barra de deslize). 
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class SliderPropertyTag extends BasePropertyTag{
    private Long maximumValue = null;
    private Long minimumValue = null;
    private Long step         = 1l;
    
    /**
     * Retorna o tamanho do passo a ser acrescido ou diminu�do do valor.
     * 
     * @return Valor num�rico contendo o tamanho do passo.
     */
    public Long getStep(){
        return step;
    }

    /**
     * Define o tamanho do passo a ser acrescido ou diminu�do do valor.
     * 
     * @param step Valor num�rico contendo o tamanho do passo.
     */
    public void setStep(Long step){
        this.step = step;
    }

    /**
     * Retorna o valor m�ximo a ser alcan�ado.
     * 
     * @return Valor num�rico m�ximo a ser alcan�ado.
     */
    public Long getMaximumValue(){
        return maximumValue;
    }

    /**
     * Define o valor m�ximo a ser alcan�ado.
     * 
     * @param maximumValue Valor num�rico m�ximo a ser alcan�ado.
     */
    public void setMaximumValue(Long maximumValue){
        this.maximumValue = maximumValue;
    }

    /**
     * Retorn o valor m�nimo a ser alcan�ado.
     * 
     * @return Valor num�rico m�nimo a ser alcan�ado.
     */
    public Long getMinimumValue(){
        return minimumValue;
    }

    /**
     * Define o valor m�nimo a ser alcan�ado.
     * 
     * @param minimumValue Valor num�rico m�nimo a ser alcan�ado.
     */
    public void setMinimumValue(Long minimumValue){
        this.minimumValue = minimumValue;
    }
    
    /**
     * @see br.com.concepting.framework.web.taglibs.BasePropertyTag#renderBody()
     */
    protected void renderBody() throws Throwable{
        String name  = StringUtil.trim(getName());
        String width = StringUtil.trim(getWidth());
        
        println("<div>");
        
        print("<div id=\"");
        print(name);
        print(".");
        print(TaglibConstants.DEFAULT_SLIDER_BAR_CONTROL_STYLE_CLASS);
        print("\" class=\"");
        print(TaglibConstants.DEFAULT_SLIDER_BAR_CONTROL_STYLE_CLASS);
        print("\" onMouseDown=\"dragSliderBar(this, event);\" onMouseOut=\"dropSliderBar(this, event);\" onMouseUp=\"dropSliderBar(this, event);\"></div>");
        
        print("<div id=\"");
        print(name);
        print(".");
        print(TaglibConstants.DEFAULT_SLIDER_BAR_STYLE_CLASS);
        print("\" class=\"");
        print(TaglibConstants.DEFAULT_SLIDER_BAR_STYLE_CLASS);
        println("\">");
        
        print("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"");
        print(width);
        println("\">");
        
        println("<tr>");
        
        print("<td class=\"");
        print(TaglibConstants.DEFAULT_LEFT_SLIDER_BAR_STYLE_CLASS);
        println("\"></td>");
        print("<td class=\"");
        print(TaglibConstants.DEFAULT_SLIDER_BAR_BODY_STYLE_CLASS);
        println("\"></td>");
        print("<td class=\"");
        print(TaglibConstants.DEFAULT_RIGHT_SLIDER_BAR_STYLE_CLASS);
        println("\"></td>");
        
        println("</tr>");
        
        println("</table>");
        
        println("</div>");
        
        println("</div>");
    }
 
    /**
     * @see br.com.concepting.framework.web.taglibs.BasePropertyTag#clearAttributes()
     */
    protected void clearAttributes(){
        super.clearAttributes();
        
        setMaximumValue(null);
        setMinimumValue(null);
        setStep(1l);
    }
}