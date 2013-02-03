package br.com.concepting.framework.web.taglibs;

import br.com.concepting.framework.resource.PropertiesResource;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;

/**
 * Classe que define o componente de label vinculado a uma propriedade de um modelo de 
 * dados ou uma chave no arquivo de recursos.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class LabelTag extends BasePropertyTag{
	/**
	 * @see br.com.concepting.framework.web.taglibs.BasePropertyTag#initialize()
	 */
	protected void initialize() throws Throwable{
	    String name  = getName();
	    Object value = null;
	    
		if(name.length() == 0){
		    String resourceId = getResourceId();
		    
			if(resourceId.length() == 0){
    			ActionFormTag formTag = (ActionFormTag)findAncestorWithClass(this, ActionFormTag.class);
    			
    			if(formTag != null){
    			    resourceId = formTag.getResourceId();
    				
    			    setResourceId(resourceId);
    			}
			}
			
			PropertiesResource resources   = getI18nResource();
			String             resourceKey = getResourceKey();

			value = resources.getProperty(resourceKey, false);
				
			if(value == null){
    			resources = getDefaultI18nResource();
    			value     = StringUtil.trim(resources.getProperty(getResourceKey()));
			}
			
			if(value != null)
			    setValue(value);
		}
		else
			super.initialize();
		
		String styleClass = getStyleClass();
		
        if(styleClass.length() == 0){
            styleClass = TaglibConstants.DEFAULT_LABEL_STYLE_CLASS;
            
            setStyleClass(styleClass);
        }
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#renderOpen()
	 */
	protected void renderOpen() throws Throwable{
	    String name = getName();
	    
		if(name.length() > 0)
			super.renderOpen();

		print("<span ");
		
		renderAttributes();

		println(">");
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BasePropertyTag#renderBody()
	 */
	protected void renderBody() throws Throwable{
		println(getFormattedValue());
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BasePropertyTag#renderClose()
	 */
	protected void renderClose() throws Throwable{
		println("</span>");
     	
		String name = getName();
		
		if(name.length() > 0)
			super.renderClose();
	}
}