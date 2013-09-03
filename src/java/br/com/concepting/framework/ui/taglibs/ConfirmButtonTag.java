package br.com.concepting.framework.ui.taglibs;

import br.com.concepting.framework.controller.form.types.ActionFormMessageType;
import br.com.concepting.framework.resource.PropertiesResource;
import br.com.concepting.framework.util.StringUtil;

/**
 * Classe que define o componente visual confirmButton (botão de confirmação).
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class ConfirmButtonTag extends ButtonTag{
	private String messageKey = "";
	private String message    = null;

    /**
	 * Retorna a mensagem de confirmação.
	 * 
	 * @return String contendo a mensagem de confirmação.
	 */
	public String getMessage(){
		return message;
	}

	/**
	 * Define a mensagem de confirmação.
	 * 
	 * @param message String contendo a mensagem de confirmação.
	 */
	public void setMessage(String message){
		this.message = message;
	}

	/**
	 * Retorna a chave que armazena a mensagem em um arquivo de propriedades.
	 * 
	 * @return String contendo a chave que armazena a mensagem
	 */
	public String getMessageKey(){
		return messageKey;
	}

	/**
	 * Define a chave que armazena a mensagem em um arquivo de propriedades.
	 * 
	 * @param messageKey String contendo a chave que armazena a mensagem
	 */
	public void setMessageKey(String messageKey){
		this.messageKey = messageKey;
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#initialize()
	 */
	protected void initialize() throws Throwable{
		String name  = getName();
		String tagId = getTagId();
		
		if(name.length() == 0)
			name = tagId;
	
		super.initialize();

        String message = getMessage();
        
        if(message == null){
            String messageKey = getMessageKey();
		
            if(messageKey.length() > 0){
                StringBuilder propertyId = new StringBuilder();

                propertyId.append(name);
                propertyId.append(".");
                propertyId.append(messageKey);

                PropertiesResource resources = getI18nResource();

                message = resources.getProperty(propertyId.toString(), false);
     			
                if(message == null){
                    resources = getDefaultI18nResource();
                    message   = resources.getProperty(propertyId.toString(), false);

                    if(message == null){
         				propertyId.delete(0, propertyId.length());
         				propertyId.append(tagId);
         				propertyId.append(".");
         				propertyId.append(messageKey);
         				
         				message = StringUtil.trim(resources.getProperty(propertyId.toString()));
                    }
     			}

                if(message != null)
                    setMessage(message);
 			}
		}
	}
	
	protected void renderOpen() throws Throwable{
        ConfirmDialogBoxTag confirmDialogBoxTag = null;
        
        if(message != null){
            confirmDialogBoxTag = new ConfirmDialogBoxTag();
            confirmDialogBoxTag.setPageContext(pageContext);
            confirmDialogBoxTag.setMessage(message);
            confirmDialogBoxTag.setType(ActionFormMessageType.WARNING);
            confirmDialogBoxTag.doStartTag();

            StringBuilder onClickContent = new StringBuilder();

            onClickContent.append("showDialogBox('");
            onClickContent.append(confirmDialogBoxTag.getName());
            onClickContent.append("', true);");

            setOnClick(onClickContent.toString());

            confirmDialogBoxTag.setOnConfirm(getOnClick());
            confirmDialogBoxTag.setOnConfirmAction(getAction());
            confirmDialogBoxTag.setOnConfirmActionForward(getForward());
            confirmDialogBoxTag.setOnConfirmActionForwardOnFail(getForwardOnFail());
            confirmDialogBoxTag.setOnConfirmActionUpdateViews(getUpdateViews());
            confirmDialogBoxTag.setOnConfirmActionValidate(getValidate());
            confirmDialogBoxTag.setOnConfirmActionValidateProperties(getValidateProperties());
            confirmDialogBoxTag.setParent(getParent());
            confirmDialogBoxTag.doEndTag();
        }
	    
        super.renderOpen();
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.ButtonTag#clearAttributes()
	 */
    protected void clearAttributes(){
	    super.clearAttributes();
	     
	    setMessageKey("");
	    setMessage(null);
    }
}