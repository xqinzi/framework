package br.com.concepting.framework.web.taglibs;

import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;

public class SpinnerPropertyTag extends TextPropertyTag{
    private Long maximumValue = null;
    private Long minimumValue = null;
    
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
     * @see br.com.concepting.framework.web.taglibs.TextPropertyTag#initialize()
     */
    protected void initialize() throws Throwable{
        super.initialize();
        
        setReadOnly(true);
        
        PropertyInfo propertyInfo = getPropertyInfo();
        
        if(propertyInfo.isNumber()){
            if(maximumValue == null)
                maximumValue = NumberUtil.getMaximumRange(propertyInfo.getClazz()).longValue();

            if(minimumValue == null)
                minimumValue = NumberUtil.getMinimumRange(propertyInfo.getClazz()).longValue();
        }
    }

    /**
     * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#render()
     */
    protected void render() throws Throwable{
        if(isRendered()){
            PropertyInfo propertyInfo = getPropertyInfo();
            
            if(propertyInfo != null && propertyInfo.isNumber()){
                println("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
                println("<tr>");
                println("<td>");
                
                super.render();
                
                println("</td>");
                println("<td>");
                
                ButtonTag addButtonTag = new ButtonTag();
                
                addButtonTag.setPageContext(pageContext);
                addButtonTag.setResourceId(TaglibConstants.DEFAULT_SPINNER_I18N_RESOURCE_ID);
                addButtonTag.setName("addSpinnerButton");
                addButtonTag.setLabelStyleClass("spinnerButtonLabel");
                
                StringBuilder onClick = new StringBuilder();
                
                onClick.append("addSpinnerValue('");
                onClick.append(getName());
                onClick.append("', ");
                onClick.append(maximumValue);
                onClick.append(");");
                
                addButtonTag.setOnClick(onClick.toString());
                addButtonTag.doStartTag();
                addButtonTag.doEndTag();
                
                println("</td>");
                println("<td>");
                
                ButtonTag subtractButtonTag = new ButtonTag();
                
                subtractButtonTag.setPageContext(pageContext);
                subtractButtonTag.setResourceId(TaglibConstants.DEFAULT_SPINNER_I18N_RESOURCE_ID);
                subtractButtonTag.setName("subtractSpinnerButton");
                subtractButtonTag.setLabelStyleClass("spinnerButtonLabel");
                
                onClick = new StringBuilder();
                onClick.append("subtractSpinnerValue('");
                onClick.append(getName());
                onClick.append("', ");
                onClick.append(minimumValue);
                onClick.append(");");

                subtractButtonTag.setOnClick(onClick.toString());
                subtractButtonTag.doStartTag();
                subtractButtonTag.doEndTag();
                
                println("</td>");
                println("</tr>");
                println("</table>");
            }
            else
                super.render();
        }
    }
    
    /**
     * @see br.com.concepting.framework.web.taglibs.TextPropertyTag#clearAttributes()
     */
    protected void clearAttributes(){
        super.clearAttributes();
        
        setMaximumValue(null);
        setMinimumValue(null);
    }
}
