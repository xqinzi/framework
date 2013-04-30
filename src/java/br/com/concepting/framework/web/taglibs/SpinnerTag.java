package br.com.concepting.framework.web.taglibs;

import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.util.NumberUtil;

public class SpinnerTag extends TextPropertyTag{
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
                addButtonTag.setStyleClass("addSpinnerButton");
                addButtonTag.setLabelStyleClass("spinnerButtonLabel");
                addButtonTag.setLabel("+");
                
                StringBuilder onClick = new StringBuilder();
                
                onClick.append("if(Number(document.getElementById('");
                onClick.append(getName());
                onClick.append("').value) < ");
                onClick.append(maximumValue);
                onClick.append(") { ");
                onClick.append("document.getElementById('");
                onClick.append(getName());
                onClick.append("').value = ");
                onClick.append("document.getElementById('");
                onClick.append(getName());
                onClick.append("').value = Number(");
                onClick.append("document.getElementById('");
                onClick.append(getName());
                onClick.append("').value) + 1; }");
                
                addButtonTag.setOnClick(onClick.toString());
                addButtonTag.doStartTag();
                addButtonTag.doEndTag();
                
                println("</td>");
                println("<td>");
                
                ButtonTag subtractButtonTag = new ButtonTag();
                
                subtractButtonTag.setPageContext(pageContext);
                subtractButtonTag.setStyleClass("subtractSpinnerButton");
                subtractButtonTag.setLabelStyleClass("spinnerButtonLabel");
                
                onClick = new StringBuilder();
                onClick.append("if(Number(document.getElementById('");
                onClick.append(getName());
                onClick.append("').value) > ");
                onClick.append(minimumValue);
                onClick.append(") { ");
                onClick.append("document.getElementById('");
                onClick.append(getName());
                onClick.append("').value = ");
                onClick.append("document.getElementById('");
                onClick.append(getName());
                onClick.append("').value = Number(");
                onClick.append("document.getElementById('");
                onClick.append(getName());
                onClick.append("').value) - 1; }");

                subtractButtonTag.setOnClick(onClick.toString());
                subtractButtonTag.setLabel("-");
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
