package br.com.concepting.framework.web.taglibs;

import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;

/**
 * Classe que define o componente visual spinner (controle de valores númericos). 
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class SpinnerPropertyTag extends TextPropertyTag{
    private Long maximumValue = null;
    private Long minimumValue = null;
    private Long step         = 1l;
    
    /**
     * Retorna o tamanho do passo a ser acrescido ou diminuído do valor.
     * 
     * @return Valor numérico contendo o tamanho do passo.
     */
    public Long getStep(){
        return step;
    }

    /**
     * Define o tamanho do passo a ser acrescido ou diminuído do valor.
     * 
     * @param step Valor numérico contendo o tamanho do passo.
     */
    public void setStep(Long step){
        this.step = step;
    }

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
     * Retorn o valor mínimo a ser alcançado.
     * 
     * @return Valor numérico mínimo a ser alcançado.
     */
    public Long getMinimumValue(){
        return minimumValue;
    }

    /**
     * Define o valor mínimo a ser alcançado.
     * 
     * @param minimumValue Valor numérico mínimo a ser alcançado.
     */
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
                addButtonTag.setName(TaglibConstants.DEFAULT_ADD_SPINNER_BUTTON);
                addButtonTag.setLabelStyleClass(TaglibConstants.DEFAULT_SPINNER_BUTTON_LABEL);
                
                StringBuilder onClick = new StringBuilder();
                
                onClick.append("addSpinnerValue('");
                onClick.append(getName());
                onClick.append("', ");
                onClick.append(maximumValue);
                onClick.append(", ");
                onClick.append(step);
                onClick.append(");");
                
                addButtonTag.setOnClick(onClick.toString());
                addButtonTag.doStartTag();
                addButtonTag.doEndTag();
                
                println("</td>");
                println("<td>");
                
                ButtonTag subtractButtonTag = new ButtonTag();
                
                subtractButtonTag.setPageContext(pageContext);
                subtractButtonTag.setResourceId(TaglibConstants.DEFAULT_SPINNER_I18N_RESOURCE_ID);
                subtractButtonTag.setName(TaglibConstants.DEFAULT_SUBTRACT_SPINNER_BUTTON);
                subtractButtonTag.setLabelStyleClass(TaglibConstants.DEFAULT_SPINNER_BUTTON_LABEL);
                
                onClick = new StringBuilder();
                onClick.append("subtractSpinnerValue('");
                onClick.append(getName());
                onClick.append("', ");
                onClick.append(minimumValue);
                onClick.append(", ");
                onClick.append(step);
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
        setStep(1l);
    }
}
