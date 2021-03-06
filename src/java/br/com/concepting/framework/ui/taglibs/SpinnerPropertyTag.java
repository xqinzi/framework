package br.com.concepting.framework.ui.taglibs;

import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.ui.constants.TaglibConstants;
import br.com.concepting.framework.util.NumberUtil;

/**
 * Classe que define o componente visual spinner (controle de valores n�mericos). 
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class SpinnerPropertyTag extends TextPropertyTag{
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
     * @see br.com.concepting.framework.ui.taglibs.TextPropertyTag#initialize()
     */
    protected void initialize() throws Throwable{
        super.initialize();
        
        setReadOnly(true);
        
        Object       value        = getValue();
        PropertyInfo propertyInfo = getPropertyInfo();
        
        if(value instanceof Number || (propertyInfo != null && propertyInfo.isNumber())){
            if(maximumValue == null)
                maximumValue = NumberUtil.getMaximumRange((propertyInfo != null ? propertyInfo.getClazz() : value.getClass())).longValue();

            if(minimumValue == null)
                minimumValue = NumberUtil.getMinimumRange((propertyInfo != null ? propertyInfo.getClazz() : value.getClass())).longValue();
        }
    }

    /**
     * @see br.com.concepting.framework.ui.taglibs.BaseActionFormElementTag#render()
     */
    protected void renderBody() throws Throwable{
        Object       value        = getValue();
        PropertyInfo propertyInfo = getPropertyInfo();
        
        if(value instanceof Number || (propertyInfo != null && propertyInfo.isNumber())){
            println("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
            
            println("<tr>");
            
            println("<td>");
            
            super.renderBody();
            
            println("</td>");
            
            println("<td width=\"5\"></td>");

            println("<td>");
            
            ButtonTag addButtonTag = new ButtonTag();
            
            addButtonTag.setPageContext(pageContext);
            addButtonTag.setResourceId(TaglibConstants.DEFAULT_SPINNER_I18N_RESOURCE_ID);
            addButtonTag.setName(TaglibConstants.DEFAULT_ADD_SPINNER_BUTTON_STYLE_CLASS);
            addButtonTag.setLabelStyleClass(TaglibConstants.DEFAULT_SPINNER_BUTTON_LABEL_STYLE_CLASS);
            
            StringBuilder onClick = new StringBuilder();
            
            onClick.append("addSpinnerValue('");
            onClick.append(getName());
            onClick.append("', ");
            onClick.append(maximumValue);
            onClick.append(", ");
            onClick.append(step);
            onClick.append(");");
            
            addButtonTag.setOnClick(onClick.toString());
            
            if(getEnabled() && !getReadOnly())
                addButtonTag.setEnabled(true);
            else
                addButtonTag.setEnabled(false);
            
            addButtonTag.doStartTag();
            addButtonTag.doEndTag();
            
            println("</td>");
            println("<td>");
            
            ButtonTag subtractButtonTag = new ButtonTag();
            
            subtractButtonTag.setPageContext(pageContext);
            subtractButtonTag.setResourceId(TaglibConstants.DEFAULT_SPINNER_I18N_RESOURCE_ID);
            subtractButtonTag.setName(TaglibConstants.DEFAULT_SUBTRACT_SPINNER_BUTTON_STYLE_CLASS);
            subtractButtonTag.setLabelStyleClass(TaglibConstants.DEFAULT_SPINNER_BUTTON_LABEL_STYLE_CLASS);
            
            onClick = new StringBuilder();
            onClick.append("subtractSpinnerValue('");
            onClick.append(getName());
            onClick.append("', ");
            onClick.append(minimumValue);
            onClick.append(", ");
            onClick.append(step);
            onClick.append(");");

            subtractButtonTag.setOnClick(onClick.toString());
            
            if(getEnabled() && !getReadOnly())
                subtractButtonTag.setEnabled(true);
            else
                subtractButtonTag.setEnabled(false);
            
            subtractButtonTag.doStartTag();
            subtractButtonTag.doEndTag();
            
            println("</td>");
            
            println("</tr>");
            
            println("</table>");
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
     * @see br.com.concepting.framework.ui.taglibs.TextPropertyTag#clearAttributes()
     */
    protected void clearAttributes(){
        super.clearAttributes();
        
        setMaximumValue(null);
        setMinimumValue(null);
        setStep(1l);
    }
}
