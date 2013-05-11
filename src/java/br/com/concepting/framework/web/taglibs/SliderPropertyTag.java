package br.com.concepting.framework.web.taglibs;

import br.com.concepting.framework.util.StringUtil;

public class SliderPropertyTag extends BasePropertyTag{
    private Long maximumValue = null;
    private Long minimumValue = null;
    private Long step         = 1l;
    
    public Long getStep(){
        return step;
    }

    public void setStep(Long step){
        this.step = step;
    }

    public Long getMaximumValue(){
        return maximumValue;
    }

    public void setMaximumValue(Long maximumValue){
        this.maximumValue = maximumValue;
    }

    public Long getMinimumValue(){
        return minimumValue;
    }

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
        println(".sliderBarControl\" class=\"sliderBarControl\" onMouseDown=\"dragSliderBarControl(this, event);\" onMouseOut=\"dropSliderBarControl(this, event);\" onMouseUp=\"dropSliderBarControl(this, event);\"></div>");
        
        print("<div id=\"");
        print(name);
        println(".sliderBarContent\" class=\"sliderBarContent\">");
        
        print("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"");
        print(width);
        println("\">");
        println("<tr>");
        println("<td class=\"leftSliderBar\"></td>");
        println("<td class=\"sliderBar\"></td>");
        println("<td class=\"rightSliderBar\"></td>");
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