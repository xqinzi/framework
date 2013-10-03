package br.com.concepting.framework.ui.taglibs;

import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.ui.constants.TaglibConstants;
import br.com.concepting.framework.util.NumberUtil;
 
/**
 * Classe que define o componente visual slider (barra de deslize para controle de valores numéricos). 
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class SliderPropertyTag extends TextPropertyTag{
    private Long maximumValue = null;

    /**
     * Retorna o valor máximo a ser alcançado.
     * 
     * @return Valor numérico máximo a ser alcançado.
     */
    public Long getMaximumValue(){
        return maximumValue;
    }

    /**
     * Define o valor máximo a ser alcançado.
     * 
     * @param maximumValue Valor numérico máximo a ser alcançado.
     */
    public void setMaximumValue(Long maximumValue){
        this.maximumValue = maximumValue;
    }
    
    /**
     * @see br.com.concepting.framework.ui.taglibs.BasePropertyTag#renderBody()
     */
    protected void renderBody() throws Throwable{
        Object       value        = getValue();
        PropertyInfo propertyInfo = getPropertyInfo();
        
        if(value instanceof Number || (propertyInfo != null && propertyInfo.isNumber())){
            println("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
            
            println("<tr>");
            
            println("<td>");
            
            String name = getName();
    
            println("<div>");
            
            print("<div id=\"");
            print(name);
            print(".");
            print(TaglibConstants.SLIDER_BAR_CONTROL_ID);
            print("\" class=\"");
            print(TaglibConstants.DEFAULT_SLIDER_BAR_CONTROL_STYLE_CLASS);
            print("\"");
            
            if(getEnabled() && !getReadOnly()){
                print(" onMouseDown=\"dragSliderBarControl('");
                print(name);
                print("', event);\" onMouseUp=\"dropSliderBarControl();\" onMouseOut=\"dropSliderBarControl();\" onMouseMove=\"slideIt(event);\"");
            }
            
            println("></div>");
    
            print("<div id=\"");
            print(name);
            print(".");
            print(TaglibConstants.SLIDER_BAR_ID);
            print("\" class=\"");
            print(TaglibConstants.DEFAULT_SLIDER_BAR_STYLE_CLASS);
            print("\"></div>");
    
            println("</div>");
            
            println("</td>");
            
            println("<td width=\"5\"></td>");
    
            println("<td>");
            
            super.renderBody();
            
            println("</td>");
            
            println("</tr>");
            
            println("</table>");
            
            String        width         = getWidth();  
            StringBuilder scriptContent = new StringBuilder();
            
            scriptContent.append("initializeSlider('");
            scriptContent.append(name);
            scriptContent.append("', ");
            scriptContent.append(NumberUtil.parseInt(width));
            scriptContent.append(", ");
            scriptContent.append(maximumValue);
            scriptContent.append(", ");
            scriptContent.append(getValue());
            scriptContent.append(");");
            
            ScriptTag scriptTag = new ScriptTag();
            
            scriptTag.setPageContext(pageContext);
            scriptTag.setContent(scriptContent.toString());
            scriptTag.doStartTag();
            scriptTag.doEndTag();
        }
        else{
            print("<span class=\"");
            print(TaglibConstants.DEFAULT_LABEL_STYLE_CLASS);
            println("\">");
            println(getInvalidPropertyMessage());
            println("</span>");
        }
    }
    
    /**
     * @see br.com.concepting.framework.ui.taglibs.TextPropertyTag#initialize()
     */
    protected void initialize() throws Throwable{
        String width = getWidth();
        
        if(width.length() == 0)
            width = TaglibConstants.DEFAULT_SLIDER_BAR_WIDTH.toString();
        
        setWidth("");
        
        super.initialize();
        
        Object       value        = getValue();
        PropertyInfo propertyInfo = getPropertyInfo();
        
        if(value instanceof Number || (propertyInfo != null && propertyInfo.isNumber())){
            setWidth(width);
    
            if(maximumValue == null)
                maximumValue = NumberUtil.getMaximumRange((propertyInfo != null ? propertyInfo.getClazz() : value.getClass())).longValue();
            
            setSize((int)(Math.log10(maximumValue)) + 1);
            setMaxlength(getSize());
    
            StringBuilder onBlurContent = new StringBuilder();
            
            onBlurContent.append("setSliderPosition('");
            onBlurContent.append(getName());
            onBlurContent.append("');");
            
            String onBlur = getOnBlur();
            
            if(onBlur.length() > 0){
                onBlurContent.append(" ");
                onBlurContent.append(onBlur);
                
                if(!onBlur.endsWith(";"))
                    onBlurContent.append(";");
            }
            
            setOnBlur(onBlurContent.toString());
        }
    }
 
    /**
     * @see br.com.concepting.framework.ui.taglibs.BasePropertyTag#clearAttributes()
     */
    protected void clearAttributes(){
        super.clearAttributes();
        
        setMaximumValue(null);
    }
}