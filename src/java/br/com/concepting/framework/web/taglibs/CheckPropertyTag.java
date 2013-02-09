package br.com.concepting.framework.web.taglibs;

import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.web.types.ComponentType;

/**
 * Classe que define o componente visual para uma op��o de marca��o.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class CheckPropertyTag extends BaseOptionPropertyTag{
    /**
     * @see br.com.concepting.framework.web.taglibs.BaseOptionPropertyTag#initialize()
     */
    protected void initialize() throws Throwable{
	    setComponentType(ComponentType.CHECKBOX);
        
	    StringBuilder tagId = new StringBuilder();
        
        tagId.append(getName());
        tagId.append((int)(Math.random() * 9999));
        
        super.initialize();
        
        PropertyInfo propertyInfo = getPropertyInfo();
        
        if(propertyInfo != null && propertyInfo.isBoolean()){
            StringBuilder onClickContent = new StringBuilder();
            
            onClickContent.append("document.getElementById('");
            onClickContent.append(tagId);
            onClickContent.append("').value = this.checked;");
        
            String onClick = getOnClick();
            
            if(onClick.length() > 0){
                onClickContent.append(" ");
                onClickContent.append(onClick);
            }

            setOnClick(onClickContent.toString());
            
            HiddenPropertyTag selectionTag = new HiddenPropertyTag();

            selectionTag.setPageContext(pageContext);
            selectionTag.setId(tagId.toString());
            selectionTag.setName(getName());
            selectionTag.setValue(getValue());
            selectionTag.doStartTag();
            selectionTag.doEndTag();
        }
        else
            setId(tagId.toString());
	}
}