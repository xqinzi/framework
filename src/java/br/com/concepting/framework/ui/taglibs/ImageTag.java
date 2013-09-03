package br.com.concepting.framework.ui.taglibs;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.ui.taglibs.constants.TaglibConstants;
import br.com.concepting.framework.util.ImageUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.ComponentType;
import br.com.concepting.framework.util.types.ScopeType;

/**
 * Classe que define o componente visual image (imagem).
 * 
 * @author fvilarinho
 * @since 1.0
 */ 
public class ImageTag extends BaseOptionsPropertyTag{
    /**
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#renderAttributes()
	 */
	protected void renderAttributes() throws Throwable{
		super.renderAttributes();
		
		print(" src=\"");
		
		Object       value        = getValue();
		PropertyInfo propertyInfo = getPropertyInfo();
		
		if(value instanceof byte[] || (propertyInfo != null && propertyInfo.isByteArray())){
			print(systemController.getContextPath());
			print("/");
			print("contentLoaderServlet?");
			print(AttributeConstants.CONTENT_DATA_KEY);
			print("=");
			
			String    actionFormName = getActionFormName();
			ScopeType scopeType      = ScopeType.SESSION;
			
			if(propertyInfo != null){
    			print(actionFormName);
    			
    			if(isForSearch())
    			    print(AttributeConstants.SEARCH_MODEL_KEY);
    			else
                    print(AttributeConstants.MODEL_KEY);
    			
    			print(getName());
			}
			else{
				StringBuilder contentId = new StringBuilder();
				
				contentId.append(AttributeConstants.IMAGE_KEY);
				contentId.append((int)(Math.random() * 9999));
				
				systemController.setAttribute(contentId.toString(), value, scopeType);
				
				print(contentId.toString());
			}
			
			print("&");
			print(AttributeConstants.CONTENT_DATA_SCOPE_KEY);
			print("=");
			print(scopeType);
			
			try{
    			print("&");
    			print(AttributeConstants.CONTENT_TYPE_KEY);
    			print("=");
    			print(ImageUtil.getImageFormat((byte[])value));
			}
			catch(Throwable e){
			}
			
			print("&");
			print(AttributeConstants.REFRESH_KEY);
			print("=");
			print((int)(Math.random() * 9999));
		}
		else{
		    String url  = StringUtil.trim(value);
	        String skin = systemController.getCurrentSkin();
	                
	        if(url.startsWith("/")){
	            print(systemController.getContextPath());
	            print(TaglibConstants.DEFAULT_SKINS_RESOURCES_DIR);
	            print(skin);
	        }
	            
	        print(url);
		}
		
		print("\"");
	}
	
	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseOptionsPropertyTag#initialize()
	 */
	protected void initialize() throws Throwable{
        setComponentType(ComponentType.IMAGE);

        super.initialize();
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseActionFormElementTag#renderOpen()
	 */
	protected void renderOpen() throws Throwable{
		if(getPropertyInfo() != null)
			super.renderOpen();
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#renderBody()
	 */
	protected void renderBody() throws Throwable{
	    String value = StringUtil.trim(getValue());
	    
		if(value.length() > 0){
    		print("<img");
    
    		renderAttributes();
    
    		println(">");
		}
        else{
            print("<span");
            
            String labelStyle = getLabelStyle();

            if(labelStyle.length() > 0){
                print(" style=\"");
                print(labelStyle);
                print(";\"");
            }
            
            String labelStyleClass = getLabelStyleClass();

            if(labelStyleClass.length() > 0){
                print(" class=\"");
                print(labelStyleClass);
                print("\"");
            }

            println(">");
            
            String message = "";
            
            if(getPropertyInfo() == null)
                message = getInvalidPropertyMessage();
            else
                message = getDataIsEmptyMessage();

            println(message);
            
            println("</span>");
        }
	}
	
	/**
	 * @see br.com.concepting.framework.ui.taglibs.BasePropertyTag#renderClose()
	 */
	protected void renderClose() throws Throwable{
		if(getPropertyInfo() != null)
			super.renderClose();
	}
}