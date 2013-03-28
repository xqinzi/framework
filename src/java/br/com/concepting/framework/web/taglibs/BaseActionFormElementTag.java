package br.com.concepting.framework.web.taglibs;

import javax.servlet.jsp.PageContext;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.model.FormModel;
import br.com.concepting.framework.model.ObjectModel;
import br.com.concepting.framework.model.SystemModuleModel;
import br.com.concepting.framework.resource.PropertiesResource;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.security.model.UserModel;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.util.types.ComponentType;
import br.com.concepting.framework.util.types.PositionType;
import br.com.concepting.framework.web.form.ActionFormMessageController;
import br.com.concepting.framework.web.helpers.RequestInfo;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;
import br.com.concepting.framework.web.types.ScopeType;

/**
 * Classe que define a estrutura básica para um componente de um formulário.
 * 
 * @author fvilarinho
 * @since 2.0
 */
public abstract class BaseActionFormElementTag extends BaseTag{
    private   String                      alignment                   = "";
	private   String                      label                       = null;
	private   String                      labelStyleClass             = "";
	private   String                      labelStyle                  = "";
	private   String                      labelAlignment              = "";
	private   String                      labelVerticalAlignment      = "";
	private   String                      labelPosition               = "";
	private   Boolean                     showLabel                   = true;
	private   String                      actionForm                  = "";
    private   ActionFormTag               actionFormTag               = null;
	private   Boolean                     hasPermission               = true;
    private   RequestInfo                 requestInfo                 = null;
    protected ActionFormMessageController actionFormMessageController = null;
    
    /**
     * @see br.com.concepting.framework.web.taglibs.BaseTag#setPageContext(javax.servlet.jsp.PageContext)
     */
    public void setPageContext(PageContext pageContext){
        super.setPageContext(pageContext);

        actionFormMessageController = systemController.getActionFormMessageController();
        securityController          = systemController.getSecurityController();
    }

    /**
     * Retorna a instância contendo as propriedades da requisição do componente.
     * 
     * @return Instância contendo as propriedades da requisição do componente.
     */
    protected RequestInfo getRequestInfo(){
        return requestInfo;
    }

    /**
     * Define a instância contendo as propriedades da requisição do componente.
     * 
     * @param requestInfo Instância contendo as propriedades da requisição do componente.
     */
    protected void setRequestInfo(RequestInfo requestInfo){
        this.requestInfo = requestInfo;
    }

    /**
     * Retorna o tipo de alinhamento do componente.
     * 
     * @return String que define o tipo de alinhamento do componente.
     */
    public String getAlignment(){
        return alignment;
    }
    
    /**
     * Retorna a tipo de alinhamento do componente.
     * 
     * @return Constante que define o alinhamento do componente.
     */
    protected AlignmentType getAlignmentType(){
        try{
            return AlignmentType.valueOf(alignment);
        }
        catch(Throwable e){
            return null;
        }
    }
    
    /**
     * Define o tipo de alinhamento do componente.
     * 
     * @param alignment String que define o tipo de alinhamento do componente.
     */
    public void setAlignment(String alignment){
        this.alignment = alignment;
    }

    /**
     * Define o tipo de alinhamento do componente.
     * 
     * @param alignment Constante que define o tipo de alinhamento do componente.
     */
    protected void setAlignmentType(AlignmentType alignment){
        if(alignment != null)
            this.alignment = alignment.toString();
        else
            this.alignment = "";
    }

    /**
     * Retorna o tipo de alinhamento do label do componente.
     * 
     * @return String que define o tipo de alinhamento.
     */
    public String getLabelAlignment(){
        return labelAlignment;
    }
    
    /**
     * Retorna o tipo de alinhamento do label do componente.
     * 
     * @return Constante que define o tipo de alinhamento.
     */
    protected AlignmentType getLabelAlignmentType(){
        try{
            return AlignmentType.valueOf(labelAlignment);
        }
        catch(Throwable e){
            return null;
        }
    }

    /**
     * Define o tipo de alinhamento do label do componente.
     * 
     * @param labelAlignment String que define o tipo de alinhamento.
     */
    public void setLabelAlignment(String labelAlignment){
        this.labelAlignment = StringUtil.trim(labelAlignment).toUpperCase();
    }

    /**
     * Define o tipo de alinhamento do label do componente.
     * 
     * @param labelAlignment Constante que define o tipo de alinhamento.
     */
    protected void setLabelAlignmentType(AlignmentType labelAlignment){
        if(labelAlignment != null)
            this.labelAlignment = labelAlignment.toString();
        else
            this.labelAlignment = "";
    }

    /**
     * Retorna o tipo de alinhamento vertical do label do componente.
     * 
     * @return String que define o tipo de alinhamento vertical.
     */
    public String getLabelVerticalAlignment(){
        return labelVerticalAlignment;
    }

    /**
     * Retorna o tipo de alinhamento vertical do label do componente.
     * 
     * @return Constante que define o tipo de alinhamento vertical.
     */
    protected AlignmentType getLabelVerticalAlignmentType(){
        try{
            return AlignmentType.valueOf(labelVerticalAlignment);
        }
        catch(Throwable e){
            return null;
        }
    }

    /**
     * Define o tipo de alinhamento vertical do label do componente.
     * 
     * @param labelVerticalAlignment String que define o tipo de alinhamento vertical.
     */
    public void setLabelVerticalAlignment(String labelVerticalAlignment){
        this.labelVerticalAlignment = StringUtil.trim(labelVerticalAlignment).toUpperCase();
    }

    /**
     * Define o tipo de alinhamento vertical do label do componente.
     * 
     * @param labelAlignment Constante que define o tipo de alinhamento vertical.
     */
    protected void setLabelVerticalAlignment(AlignmentType labelVerticalAlignment){
        if(labelVerticalAlignment != null)
            this.labelVerticalAlignment = labelVerticalAlignment.toString();
        else
            this.labelVerticalAlignment = ""; 
    }

    /**
     * Retorna o identificador do formulário onde o componente está localizado.
     * 
     * @return String contendo o identificador do formulário.
     */
    public String getActionForm(){
        return actionForm;
    }

    /**
     * Define o identificador do formulário onde o componente está localizado.
     * 
     * @param actionForm String contendo o identificador do formulário.
     */
    public void setActionForm(String actionForm){
        this.actionForm = actionForm;
    }
    
    /**
     * Retorna a instância do componente de formulário.
     * 
     * @return Instância do componente de formulário. 
     */
    protected ActionFormTag getActionFormTag(){
        if(actionFormTag == null){
            actionFormTag = (ActionFormTag)findAncestorWithClass(this, ActionFormTag.class);
            
            if(actionFormTag == null)
                actionFormTag =  systemController.findAttribute(AttributeConstants.CURRENT_ACTION_FORM_KEY, ScopeType.REQUEST);
            
            if(actionFormTag != null && StringUtil.trim(actionForm).length() == 0)
                actionForm = StringUtil.trim(actionFormTag.getBeanName());
        }

        return actionFormTag;
    }

    /**
     * Define a instância do componente de formulário.
     * 
     * @param actionFormTag Instância do componente de formulário. 
     */
    protected void setActionFormTag(ActionFormTag actionFormTag){
        this.actionFormTag = actionFormTag;
        
        if(actionFormTag != null)
            this.actionForm = actionFormTag.getBeanName();
    }

    /**
     * Retorna a posição do label do componente.
     * 
     * @return String que define a posição do label.
     */
	public String getLabelPosition(){
	    return labelPosition;
	}
	
    /**
     * Retorna a posição do label do componente.
     * 
     * @return Constante que define a posição do label.
     */
	protected PositionType getLabelPositionType(){
        try{
            return PositionType.valueOf(labelPosition);
        }
        catch(Throwable e){
            return null;
        }
	}

	/**
     * Define a posição do label do componente.
     * 
     * @param labelPosition String contendo a posição do label.
     */
    public void setLabelPosition(String labelPosition){
        this.labelPosition = StringUtil.trim(labelPosition).toUpperCase();
    }

    /**
     * Define a posição do label do componente.
     * 
     * @param labelPosition Constante que define a posição do label.
     */
	protected void setLabelPositionType(PositionType labelPosition){
	    if(labelPosition != null)
	        this.labelPosition = labelPosition.toString();
	    else
	        this.labelPosition = "";
	}

	/**
	 * Retorna o label do componente.
	 * 
	 * @return String contendo o label.
	 */
	public String getLabel(){
		return label;
	}

	/**
	 * Define o label do componente.
	 * 
	 * @param label String contendo o label.
	 */
	public void setLabel(String label){
		this.label = label;
	}

	/**
	 * Indica se o label do componente deve ser exibido.
	 * 
	 * @return True/False.
	 */
	public Boolean showLabel(){
		return showLabel;
	}

	/**
	 * Define se o label do componente deve ser exibido.
	 * 
	 * @param showLabel True/False.
	 */
	public void setShowLabel(Boolean showLabel){
		this.showLabel = showLabel;
	}

	/**
	 * Retorna o estilo CSS para o label do componente.
	 * 
	 * @return String contendo o estilo CSS para o label.
	 */
	public String getLabelStyleClass(){
		return labelStyleClass;
	}

	/**
	 * Define o estilo CSS para o label do componente.
	 * 
	 * @param labelStyleClass String contendo o estilo CSS para o label.
	 */
	public void setLabelStyleClass(String labelStyleClass){
		this.labelStyleClass = labelStyleClass;
	}

	/**
	 * Retorna o estilo CSS para o label do componente.
	 * 
	 * @return String contendo o estilo CSS para o label.
	 */
	public String getLabelStyle(){
		return labelStyle;
	}

	/**
	 * Define o estilo CSS para o label do componente.
	 * 
	 * @param labelStyle String contendo o estilo CSS para o label.
	 */
	public void setLabelStyle(String labelStyle){
		this.labelStyle = labelStyle;
	}

	/**
	 * Indica se o usuário autenticado possui permissão de visualizar o componente.
	 * 
	 * @return True/False.
	 */
	protected Boolean hasPermission(){
		return hasPermission;
	}

	/**
	 * Define se o usuário autenticado possui permissão de visualizar o componente.
	 * 
	 * @param hasPermission True/False.
	 */
	protected void setHasPermission(Boolean hasPermission){
     	this.hasPermission = hasPermission;
    }

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#initialize()
	 */
	protected void initialize() throws Throwable{
	    PositionType labelPosition = getLabelPositionType();
	    
		if(labelPosition == null){
		    labelPosition = PositionType.LEFT;
		    
		    setLabelPositionType(labelPosition);
		}
		
		AlignmentType labelAlignment = getLabelAlignmentType();
		
        if(labelAlignment == null){
            if(labelPosition == PositionType.TOP || labelPosition == PositionType.BOTTOM)
                labelAlignment = AlignmentType.LEFT;
            else if(labelPosition == PositionType.LEFT)
                labelAlignment = AlignmentType.RIGHT;
            else if(labelPosition == PositionType.RIGHT)
                labelAlignment = AlignmentType.LEFT;
            
            setLabelAlignmentType(labelAlignment);
        }
        
        ActionFormTag actionFormTag = getActionFormTag();
        String        actionForm    = getActionForm();
        String        name          = getName();
        String        resourceId    = getResourceId();

        if(actionFormTag != null){
            if(resourceId.length() == 0){
                resourceId = actionFormTag.getResourceId();
                
                setResourceId(resourceId);
            }
        }
        
        if(actionForm.length() > 0 && name.length() > 0){
		    Boolean hasPermission = true;
		    
            try{
    			LoginSessionModel loginSession = securityController.getLoginSession();
    			SystemModuleModel systemModule = (loginSession != null ? loginSession.getSystemModule() : null);
    			FormModel         form         = (systemModule != null ? systemModule.getForm(actionForm) : null);
    			ObjectModel       object       = (form != null ? form.getObject(name) : null);
    
    			if(object != null){
    			    if(label == null)
    			        label = object.getTitle();
    			    
    			    String tooltip = getTooltip(); 
    
    			    if(tooltip == null){
    			        tooltip = object.getDescription();
    			        
    					setTooltip(tooltip);
    			    }

                    hasPermission = (loginSession != null && loginSession.getId() != null && loginSession.getId() > 0);
                    
                    if(hasPermission){
                        UserModel user = loginSession.getUser();
                        
                        hasPermission = (user != null && user.getId() != null && user.getId() > 0 && !user.changePassword()); 
                            
                        if(hasPermission){
                            if(user.isSuperUser())
                                hasPermission = true;
                            else
                                hasPermission = user.hasPermission(object);
                        }
                    }
    			}
    		}
    		catch(Throwable e){
    		    hasPermission = false;
    		}

            setHasPermission(hasPermission);
		}

		if(labelStyleClass.length() == 0)
		    labelStyleClass = TaglibConstants.DEFAULT_LABEL_STYLE_CLASS;
		
		String        styleClass    = getStyleClass();
		ComponentType componentType = getComponentType();

		if(styleClass.length() == 0 && componentType != null){
		    styleClass = componentType.getId();
		    
			setStyleClass(styleClass);
		}
		
        String resourceKey = getResourceKey();

        if(label == null && (name.length() > 0 || resourceKey.length() > 0)){
     		StringBuilder propertyId  = new StringBuilder();
     
     		if(resourceKey.length() > 0)
     			propertyId.append(resourceKey);
     		else
     			propertyId.append(name);
     		
     		propertyId.append(".");
     		propertyId.append(AttributeConstants.LABEL_KEY);
     
     		PropertiesResource resources = getI18nResource();
     		
 			label = resources.getProperty(propertyId.toString(), false);
 			
 			if(label == null){
                resources = getDefaultI18nResource();
                label     = StringUtil.trim(resources.getProperty(propertyId.toString()));
 			}
		}

        if(name.length() > 0)
            requestInfo = systemController.getRequestInfo(name);
        
        super.initialize();
	}

	/**
	 * Renderiza a abertura do label do componente.
	 * 
	 * @throws Throwable
	 */
	protected void renderLabelOpen() throws Throwable{
		print("<td class=\"");
	    print(labelStyleClass);
	    print("\"");
		
        if(labelStyle.length() > 0){
            print(" style=\"");
			print(labelStyle);
			print("\"");
		}
		
        print(" align=\"");
        print(labelAlignment);
	    print("\"");
	    
	    if(labelVerticalAlignment != null){
	        print(" valign=\"");
            print(labelVerticalAlignment);
            print("\"");
	    }

		renderTooltip();
		
		println(">");
	}

	/**
	 * Renderiza o corpo do label do componente.
	 */
	protected void renderLabelBody() throws Throwable{
	    if(label != null && label.length() > 0){
    		print(label);
    		print(":&nbsp;");
	    }
	}

	/**
	 * Renderiza o fechamento do label do componente.
	 * 
	 * @throws Throwable
	 */
	protected void renderLabelClose() throws Throwable{
		println("</td>");
	}

	/**
	 * Renderiza o label do componente.
	 * 
	 * @throws Throwable
	 */
	protected void renderLabel() throws Throwable{
		if(showLabel()){
			renderLabelOpen();
            renderLabelBody();
            renderLabelClose();
		}
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderAttributes()
	 */
	protected void renderAttributes() throws Throwable{
	    ComponentType componentType = getComponentType();
	    
	    if(componentType != null){
    		if(componentType != ComponentType.LIST){
         		print(" type=\"");
     		    print(componentType.getType());
         		print("\"");
    		}
	    }

		super.renderAttributes();

		if(!isEnabled())
			print(" disabled");
	}
	
	/**
     * Renderiza o atributo que define o label do componente visual.
     * 
	 * @throws Throwable
	 */
	protected void renderLabelAttribute() throws Throwable{
        if(isEnabled()){
            StringBuilder tagName = new StringBuilder();
            
            tagName.append(getName());
            tagName.append(".");
            tagName.append(AttributeConstants.LABEL_KEY);

            HiddenPropertyTag labelPropertyTag = new HiddenPropertyTag();

            labelPropertyTag.setPageContext(pageContext);
            labelPropertyTag.setName(tagName.toString());
            labelPropertyTag.setValue(getLabel());
            labelPropertyTag.doStartTag();
            labelPropertyTag.doEndTag();
        }
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderOpen()
	 */
	protected void renderOpen() throws Throwable{
		super.renderOpen();
		
		renderLabelAttribute();

		GridColumnTag columnTag = (GridColumnTag)findAncestorWithClass(this, GridColumnTag.class);
		
		print("<table");
		
        if(columnTag != null){
            print(" class=\"");
            print(TaglibConstants.DEFAULT_PANEL_STYLE_CLASS);
            print("\"");
        }
        
        println(">");
		
		println("<tr>");
		
		PositionType labelPosition = getLabelPositionType();
		
		if(labelPosition == PositionType.LEFT || labelPosition == PositionType.TOP){
			renderLabel();
			
			if(labelPosition == PositionType.TOP){
			    println("</tr>");
			    println("<tr>");
			}
		}

		print("<td valign=\"");
		print(AlignmentType.TOP);
		print("\" align=\"");
		print(alignment);
		println("\">");
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderBody()
	 */
	protected void renderBody() throws Throwable{
		print("<input ");

		renderAttributes();

		print(">");
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderClose()
	 */
	protected void renderClose() throws Throwable{
		println("</td>");

		PositionType labelPosition = getLabelPositionType();
		
		if(labelPosition == PositionType.RIGHT || labelPosition == PositionType.BOTTOM){
            if(labelPosition == PositionType.BOTTOM){
                println("</tr>");
                println("<tr>");
            }

            renderLabel();
		}
		
		println("</tr>");
		println("</table>");

		super.renderClose();
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#render()
	 */
	protected void render() throws Throwable{
		if(hasPermission())
			super.render();
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#clearAttributes()
	 */
	protected void clearAttributes(){
		super.clearAttributes();
		
		setComponentType(null);
		setLabel(null);
		setLabelStyle("");
		setLabelStyleClass("");
		setLabelPosition("");
		setLabelAlignment("");
		setLabelVerticalAlignment("");
		setShowLabel(true);
		setActionForm("");
		setActionFormTag(null);
		setHasPermission(true);
		setRequestInfo(null);
		setAlignment("");
	}
}