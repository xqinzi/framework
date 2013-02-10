package br.com.concepting.framework.web.taglibs;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;

import javax.servlet.jsp.JspException;

import org.apache.struts.action.ActionMessages;

import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.resource.PropertiesResource;
import br.com.concepting.framework.util.ExceptionUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.constants.AttributeConstants;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.web.form.helpers.ActionFormMessage;
import br.com.concepting.framework.web.form.types.ActionFormMessageType;
import br.com.concepting.framework.web.form.util.ActionFormMessageUtil;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;

/**
 * Classe que define o componente visual para uma caixa de mensagem.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class MessageBoxTag extends DialogBoxTag{
    private ActionFormMessageType type          = null;
    private Boolean               showException = false;
	private String                message       = "";
	private Collection<String>    messages      = null;
	private Throwable             exception     = null;
    
    /**
     * Retorna o tipo do componente.
     * 
     * @return Constante que define o tipo do componente.
     */
    public ActionFormMessageType getType(){
        return type;
    }

    /**
     * Define o tipo do componente.
     * 
     * @param type Constante que define o tipo do componente.
     */
    protected void setType(ActionFormMessageType type){
        this.type = type;
    }
  
    /**
     * Define o tipo do componente.
     * 
     * @param type String contendo o tipo do componente.
     */
    public void setType(String type){
        if(type.length() > 0)
            this.type = ActionFormMessageType.valueOf(type.toUpperCase());
        else
            this.type = ActionFormMessageType.INFO;
    }

    /**
	 * Retorna a lista contendo as mensagens geradas.
	 *
	 * @return Instância contendo as mensagens.
	 */
	protected Collection<String> getMessages(){
    	return messages;
    }

	/**
	 * Define a lista contendo as mensagens geradas.
	 *
	 * @param messages Instância contendo as mensagens.
	 */
	protected void setMessages(Collection<String> messages){
    	this.messages = messages;
    }

	/**
	 * Retorna a exceção atual.
	 *
	 * @return Instância da exceção.
	 */
	protected Throwable getException(){
    	return exception;
    }

	/**
	 * Define a exceção atual.
	 *
	 * @param exception Instância da exceção.
	 */
	protected void setException(Throwable exception){
    	this.exception = exception;
    }

	/**
	 * Retorna a texto da mensagem.
	 * 
	 * @return String contendo o texto da mensagem.
	 */
	public String getMessage(){
		return message;
	}

    /**
     * Define a texto da mensagem.
     * 
     * @param message String contendo o texto da mensagem.
     */
	public void setMessage(String message){
		this.message = message;
	}

	/**
	 * Indica que deve ser exibido o erro atual.
	 * 
	 * @return True/False.
	 */
	public Boolean showException(){
		return showException;
	}

	/**
	 * Define que deve ser exibido o erro atual.
	 * 
	 * @param showException True/False.
	 */
	public void setShowException(Boolean showException){
		this.showException = showException;
	}

    /**
	 * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#initialize()
	 */
    protected void initialize() throws Throwable{
        PropertiesResource defaultResources = getDefaultI18nResource();
        String             resourceId       = getResourceId();
        
        if(resourceId.length() == 0){
            resourceId = TaglibConstants.DEFAULT_MESSAGE_BOX_I18N_RESOURCE_ID;
            
            setResourceId(resourceId);
        }
        
        PropertiesResource resources = getI18nResource();

        if(getName().length() == 0)
            setShowOnLoad(true);
        
        StringBuilder propertyId = null;

        if(getName().length() == 0){
            propertyId = new StringBuilder();
            propertyId.append(getTagId());
            propertyId.append((int)(Math.random() * 1000));
            
            setName(propertyId.toString());
        }

        if(showException()){
			exception = systemController.getCurrentException();

			try{
				if(exception == null)
					exception = pageContext.getException();
			}
			catch(Throwable e){
				exception = e;
			}
			
			type = ActionFormMessageType.ERROR;

			if(exception != null){
			    message = ExceptionUtil.getTrace(exception); 
				
			    setMessage(message);
			    
			    systemController.setCurrentException(null);
			}
		}
		else{
            messages = new LinkedList<String>();

            String resourceKey = getResourceKey();

            if(resourceKey.length() == 0 && message.length() == 0){
	            ActionMessages actionFormMessages = actionFormMessageController.getMessages(type);
			
	            if(actionFormMessages != null && actionFormMessages.size() > 0){
	                Locale                      currentLanguage      = systemController.getCurrentLanguage();
	                Iterator<ActionFormMessage> iterator             = actionFormMessages.get();
	                ActionFormMessage           actionFormMessage    = null;
	                
	                while(iterator.hasNext()){
	                    actionFormMessage = iterator.next();
	                    
	                    if(!actionFormMessage.displayed()){
                            if(propertyId == null)
                                propertyId = new StringBuilder();
                            else
                                propertyId.delete(0, propertyId.length());
                            
                            propertyId.append(actionFormMessage.getType().toString().toLowerCase());
                            propertyId.append(".");
                            propertyId.append(actionFormMessage.getKey());
    
    						message = defaultResources.getProperty(propertyId.toString(), false);
        					
        					if(message == null)
        					    if(resources != null)
        					        message = StringUtil.trim(resources.getProperty(propertyId.toString()));
        					
        					message = PropertyUtil.fillPropertiesInString(actionFormMessage, message, currentLanguage);
        					message = ActionFormMessageUtil.fillAttributesInString(actionFormMessage, message, currentLanguage);
        
        					if(!messages.contains(message))
        						messages.add(message);
        					
        					actionFormMessage.setDisplayed(true);
	                    }
	                }
	                
	                actionFormMessageController.clearMessages(type);
	            }
	        }
	        else if(resourceKey.length() > 0 && message.length() == 0){
    		    propertyId.delete(0, propertyId.length());
    			propertyId.append(type.toString().toLowerCase());
    			propertyId.append(".");
    			propertyId.append(resourceKey);
			
    			if(resources != null)
    			    message = resources.getProperty(propertyId.toString(), false);
			
    			if(message == null)
    			    message = StringUtil.trim(defaultResources.getProperty(propertyId.toString()));
	        }

            if(!messages.contains(message) && message.length() > 0)
                messages.add(message);
		}
        
        String title = getTitle();
        
        if(title == null){
            if(propertyId == null)
                propertyId = new StringBuilder();
            else
                propertyId.delete(0, propertyId.length());
            
            propertyId.append(type.toString().toLowerCase());
            propertyId.append(".");
            propertyId.append(AttributeConstants.TITLE_KEY);
            
            title = resources.getProperty(propertyId.toString(), false);
                
            if(title == null)
                title = StringUtil.trim(defaultResources.getProperty(propertyId.toString()));
            
            setTitle(title);
        }
        
        super.initialize();
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#renderBody()
	 */
	protected void renderBody() throws Throwable{
	    String name = getName();
	    
		println("<table>");
		println("<tr>");
		print("<td align=\"");
		print(AlignmentType.RIGHT);
		print("\" valign=\"");
		print(AlignmentType.TOP);
		print("\">");
		print("<div class=\"");
		print(TaglibConstants.DEFAULT_MESSAGE_BOX_STYLE_CLASS);
		print(StringUtil.capitalize(type.toString().toLowerCase()));
		println("Icon\"></div>");
		println("</td>");
		
		print("<td align=\"");
		print(AlignmentType.CENTER);
		println("\">");
		print("<table class=\"");
		print(TaglibConstants.DEFAULT_DIALOG_BOX_TEXT_STYLE_CLASS);
		println("\">");
		println("<tr>");

		if(showException()){
		    PropertiesResource resources = getI18nResource(TaglibConstants.DEFAULT_MESSAGE_BOX_I18N_RESOURCE_ID);
		    
			println("<td>");
			print("<b>");
			print(StringUtil.trim(resources.getProperty(TaglibConstants.DEFAULT_MESSAGE_BOX_ERROR_ID_LABEL_KEY)));
			println(":</b>");
			println("</td>");
			println("</tr>");

			println("<tr>");
			println("<td>");
			println(exception.getClass().getName());
			println("&nbsp;");
			println("</td>");
			println("</tr>");

			println("<tr>");
			println("<td>");

			print("<a href=\"javascript:showHideErrorTraceDetails('");
			print(name);
			print("');\" class=\"");
			print(TaglibConstants.DEFAULT_LINK_STYLE_CLASS);
			print("\"><b>");
			print(StringUtil.trim(resources.getProperty(TaglibConstants.DEFAULT_MESSAGE_BOX_ERROR_TRACE_LABEL_KEY)));
			println("</b></a>");

            StringBuilder tagName = new StringBuilder();

            tagName.append(name);
            tagName.append(".");
            tagName.append(AttributeConstants.ERROR_TRACE_KEY);

            TextAreaPropertyTag errorTraceTag = new TextAreaPropertyTag();
            
            errorTraceTag.setPageContext(pageContext);
			errorTraceTag.setName(tagName.toString());
			errorTraceTag.setValue(message);
 			errorTraceTag.setShowLabel(false);
 			errorTraceTag.setResourceId(TaglibConstants.DEFAULT_MESSAGE_BOX_I18N_RESOURCE_ID);
 			errorTraceTag.setResourceKey(AttributeConstants.ERROR_TRACE_KEY);
			errorTraceTag.setStyleClass(TaglibConstants.DEFAULT_MESSAGE_BOX_ERROR_TRACE_STYLE_CLASS);
			errorTraceTag.setStyle("display: NONE;");
			errorTraceTag.setReadOnly(true);
			errorTraceTag.doStartTag();
			errorTraceTag.doEndTag();

			println("</td>");
		}
		else{
			println("<td style=\"white-space: NOWRAP;\">");

			if(getResourceKey().length() == 0){
				if(messages != null && messages.size() > 0){
					for(String message : messages){
					    if(messages.size() > 1)
					        print("&#8226;&nbsp;");
						
						message = StringUtil.replaceAll(message, StringUtil.getLineBreak(), "");
						
						print(message);
						println("<br/>");
					}
				}
			}
			else{
			    message = StringUtil.replaceAll(message, StringUtil.getLineBreak(), "");
			    
				println(message);
			}

			println("</td>");
		}

		println("</tr>");
		println("</table>");
				
		println("</td>");
        println("</tr>");
		println("</table>");
	}
	
	protected void renderClose() throws Throwable{
	    super.renderClose();
        
        StringBuilder content = new StringBuilder();
        
        content.append("addLoadEvent(centralizeDialogBoxes);");
        content.append(StringUtil.getLineBreak());
        content.append(";");
        content.append("addResizeEvent(centralizeDialogBoxes);");
        content.append(StringUtil.getLineBreak());
        content.append(";");
        
        ScriptTag scriptTag = new ScriptTag();
        
        scriptTag.setPageContext(pageContext);
        scriptTag.setContent(content.toString());
        scriptTag.doStartTag();
        scriptTag.doEndTag();
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#render()
	 */
	protected void render() throws Throwable{
		if((getResourceKey().length() > 0 && getResourceId().length() > 0) || (showException() && exception != null) || (messages != null && messages.size() > 0))
			super.render();
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public int doStartTag() throws JspException{
		if(systemController != null){
			try{
				initialize();
			}
			catch(Throwable e){
				throw new JspException(e);
			}
		}

		return EVAL_BODY_INCLUDE;
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	public int doEndTag() throws JspException{
		if(systemController != null){
			try{
				render();
			}
			catch(Throwable e){
				throw new JspException(e);
			}
			finally{
				clearAttributes();
			}
		}

		return SKIP_BODY;
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#clearAttributes()
	 */
    protected void clearAttributes(){
	    super.clearAttributes();

	    setMessage("");
	    setShowException(false);
	    setMessages(null);
	    setException(null);
    }
}