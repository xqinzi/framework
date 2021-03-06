package br.com.concepting.framework.ui.taglibs;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.controller.action.types.ActionType;
import br.com.concepting.framework.ui.constants.TaglibConstants;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.util.types.ComponentType;
import br.com.concepting.framework.util.types.PagerActionType;
import br.com.concepting.framework.util.types.PositionType;

/**
 * Classe que define o componente visual button (bot�o gen�rico).
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class ButtonTag extends BaseActionFormElementTag{
	private String  action             = "";
	private String  forward            = "";
	private String  forwardOnFail      = "";
    private String  iconStyleClass     = "";
    private String  iconStyle          = "";
    private String  iconUrl            = "";
    private String  pagerAction        = "";
    private String  updateViews        = "";
	private Boolean validate           = false;
	private String  validateProperties = "";
	
	/**
	 * Indica se possui a��o definida.
	 * 
	 * @return True/False.
	 */
	protected Boolean hasAction(){
	    return (StringUtil.trim(action).length() > 0);
	}

    /**
     * Retorna os identificadores das views (separados por v�rgula) a serem atualizadas ap�s o
     * processamento da a��o requisitada.
     * 
     * @return String contendo os identificadores das views.
     */
	public String getUpdateViews(){
        return updateViews;
    }

    /**
     * Define os identificadores das views (separados por v�rgula) a serem atualizadas ap�s o
     * processamento da a��o requisitada.
     * 
     * @param updateViews String contendo os identificadores das views.
     */
    public void setUpdateViews(String updateViews){
        this.updateViews = updateViews;
    }

    /**
	 * Indica que o componente deve ser exibido somente quando existir dados.
	 * 
	 * @return True/False.
	 */
    public Boolean showOnlyWithData(){
        return false;
    }

    /**
     * Retorna a a��o de pagina��o a ser executada.
     * 
     * @return Constante que define a a��o de pagina��o.
     */
	public PagerActionType getPagerAction(){
	    try{
	        return PagerActionType.valueOf(pagerAction);
	    }
	    catch(Throwable e){
	        return null;
	    }
    }

    /**
     * Define a a��o de pagina��o a ser executada.
     * 
     * @param pagerAction Constante que define a a��o de pagina��o.
     */
	protected void setPagerAction(PagerActionType pagerAction){
	    if(pagerAction != null)
	        this.pagerAction = pagerAction.toString();
	    else
	        this.pagerAction = "";
    }
	
    /**
     * Define a a��o de pagina��o a ser executada.
     * 
     * @param pagerAction String que define a a��o de pagina��o.
     */
	public void setPagerAction(String pagerAction){
	    this.pagerAction = StringUtil.trim(pagerAction).toUpperCase();
	}

    /**
     * Retorna a a��o a ser executada.
     * 
     * @return String que define a a��o.
     */
	public String getAction(){
		return action;
	}

    /**
     * Define a a��o a ser executada.
     * 
     * @param action String que define a a��o.
     */
	public void setAction(String action){
        this.action = action;
	}
	
    /**
     * Define a a��o a ser executada.
     * 
     * @param action Constante que define a a��o.
     */
	protected void setAction(ActionType action){
	    if(action != null)
	        this.action = action.getMethod();
        else
            this.action = "";
	}

	/**
	 * Retorna o identificador do redirecionamento da a��o.
	 * 
	 * @return String contendo o identificador do redirecionamento.
	 */
	public String getForward(){
		return forward;
	}

	/**
	 * Define o identificador do redirecionamento da a��o.
	 * 
	 * @param forward String contendo o identificador do redirecionamento.
	 */
	public void setForward(String forward){
		this.forward = forward;
	}

	/**
	 * Retorna o identificador do redirecionamento da a��o quando ocorrer erros.
	 * 
	 * @return String contendo o identificador do redirecionamento.
	 */
	public String getForwardOnFail(){
		return forwardOnFail;
	}

	/**
	 * Define o identificador do redirecionamento da a��o quando ocorrer erros.
	 * 
	 * @param forwardOnFail String contendo o identificador do redirecionamento.
	 */
	public void setForwardOnFail(String forwardOnFail){
		this.forwardOnFail = forwardOnFail;
	}

	/**
	 * Indica se a a��o deve executar a valida��o dos dados do formul�rio.
	 * 
	 * @return True/False.
	 */
	public Boolean validate(){
		return validate;
	}
	
    /**
     * Indica se a a��o deve executar a valida��o dos dados do formul�rio.
     * 
     * @return True/False.
     */
    public Boolean getValidate(){
        return validate();
    }

	/**
	 * Define se a a��o deve executar a valida��o dos dados do formul�rio.
	 * 
	 * @param validate True/False.
	 */
	public void setValidate(Boolean validate){
		this.validate = validate;
	}

	/**
	 * Retorna o estilo CSS para o �cone vinculado ao componente.
	 * 
	 * @return String contendo o estilo CSS para o �cone.
	 */
	public String getIconStyle(){
		return iconStyle;
	}

	/**
	 * Define o estilo CSS para o �cone vinculado ao componente.
	 * 
	 * @param iconStyle String contendo o estilo CSS para o �cone.
	 */
	public void setIconStyle(String iconStyle){
		this.iconStyle = iconStyle;
	}

	/**
	 * Retorna o estilo CSS para o �cone vinculado ao componente.
	 * 
	 * @return String contendo o estilo CSS para o �cone.
	 */
	public String getIconStyleClass(){
		return iconStyleClass;
	}

	/**
	 * Define o estilo CSS para o �cone vinculado ao componente.
	 * 
	 * @param iconStyleClass String contendo o estilo CSS para o �cone.
	 */
	public void setIconStyleClass(String iconStyleClass){
		this.iconStyleClass = iconStyleClass;
	}

	/**
	 * Retorna a URL do �cone vinculado ao componente.
	 * 
	 * @return String contendo a URL do �cone.
	 */
	public String getIconUrl(){
		return iconUrl;
	}

	/**
	 * Define a URL do �cone vinculado ao componente.
	 * 
	 * @param iconUrl String contendo a URL do �cone.
	 */
	public void setIconUrl(String iconUrl){
		this.iconUrl = iconUrl;
	}

	/**
	 * Retorna uma string delimitada contendo as propriedades a serem validadas no
	 * processamento da a��o.
	 * 
	 * @return String contendo as propriedades a serem validadas.
	 */
	public String getValidateProperties(){
		return validateProperties;
	}

	/**
	 * Defime uma string delimitada contendo as propriedades a serem validadas no
	 * processamento da a��o.
	 * 
	 * @param validateProperties String contendo as propriedades a serem validadas.
	 */
	public void setValidateProperties(String validateProperties){
		this.validateProperties = validateProperties;
	}

	/**
	 * Monta o �cone vinculado ao componente.
	 * 
	 * @throws Throwable
	 */
	protected void buildIcon() throws Throwable{
		if(iconUrl.length() > 0 || iconStyleClass.length() > 0 || iconStyle.length() > 0){
            print("<td align=\"");
            print(AlignmentType.CENTER);
            println("\" width=\"1\">");

			if(iconUrl.length() > 0){
				print("<img src=\"");
				print(iconUrl);
				println("\">");
			}
			else{
				print("<div");

				if(iconStyleClass.length() > 0){
					print(" class=\"");
					print(iconStyleClass);
					print("\"");
				}

				if(iconStyle.length() > 0){
					print(" style=\"");
					print(iconStyle);
					print("\"");
				}

				println("></div>");
			}

			println("</td>");
			
			if(getLabelPositionType() == PositionType.BOTTOM){
				println("</tr>");
				println("<tr>");
			}
		}
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseActionFormElementTag#renderLabel()
	 */
	protected void renderLabel() throws Throwable{
	    super.renderLabel();
		
		if(getLabelPositionType() == PositionType.TOP){
		    println("</tr>");
		    println("<tr>");
		}
	}
	
	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseActionFormElementTag#renderLabelBody()
	 */
	protected void renderLabelBody() throws Throwable{
	    println(getLabel());
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#renderOpen()
	 */
	protected void renderOpen() throws Throwable{
		print("<button");

		renderAttributes();

		println(">");
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#renderBody()
	 */
	protected void renderBody() throws Throwable{
        print("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"");
        print(TaglibConstants.DEFAULT_PANEL_STYLE_CLASS);
        println("\">");
		println("<tr>");

		PositionType labelPosition = getLabelPositionType();
		
		if(labelPosition == PositionType.LEFT || labelPosition == PositionType.TOP){
            renderLabel();
		    buildIcon();
		}
		else{
            buildIcon();
            renderLabel();
		}

		println("</tr>");
		println("</table>");
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#renderClose()
	 */
	protected void renderClose() throws Throwable{
		println("</button>");
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#initialize()
	 */
	protected void initialize() throws Throwable{
	    if(getComponentType() == null)
	        setComponentType(ComponentType.BUTTON);

	    PositionType labelPosition = getLabelPositionType();
	    
        if(labelPosition == null){
            labelPosition = PositionType.RIGHT;
            
            setLabelPositionType(labelPosition);
        }
        
        AlignmentType labelAlignment = getLabelAlignmentType();
	    
	    if(labelAlignment == null){
    	    if(labelPosition == PositionType.TOP || labelPosition == PositionType.BOTTOM)
    	        labelAlignment = AlignmentType.CENTER;
    	    else if(labelPosition == PositionType.LEFT)
                labelAlignment = AlignmentType.RIGHT;
            else if(labelPosition == PositionType.RIGHT)
                labelAlignment = AlignmentType.LEFT;
    	    
    	    setLabelAlignmentType(labelAlignment);
	    }

		String name  = getName();
		String tagId = getTagId();
		
		if(name.length() == 0)
			name = tagId;

		String  styleClass = getStyleClass();
		Boolean isEnabled  = isEnabled();
		
		if(styleClass.length() == 0){
	        StringBuilder styleClassContent = new StringBuilder();
			
			styleClassContent.append(name);

			if(!isEnabled)
				styleClassContent.append(StringUtil.capitalize(AttributeConstants.DISABLED_KEY));

			setStyleClass(styleClassContent.toString());
		}
		
		String iconStyleClass = getIconStyleClass();

		if(iconStyleClass.length() == 0){
            StringBuilder iconStyleClassContent = new StringBuilder();
			
			iconStyleClassContent.append(name);
			iconStyleClassContent.append(StringUtil.capitalize(AttributeConstants.ICON_KEY));
 
			if(!isEnabled)
				iconStyleClassContent.append(StringUtil.capitalize(AttributeConstants.DISABLED_KEY));

			setIconStyleClass(iconStyleClassContent.toString());
		}
		
		String labelStyleClass = getLabelStyleClass();
		
		if(labelStyleClass.length() == 0){
            StringBuilder labelStyleClassContent = new StringBuilder();
            
			labelStyleClassContent.append(name);
			labelStyleClassContent.append(StringUtil.capitalize(AttributeConstants.LABEL_KEY));
			
			if(!isEnabled)
				labelStyleClassContent.append(StringUtil.capitalize(AttributeConstants.DISABLED_KEY));
			
			setLabelStyleClass(labelStyleClassContent.toString());
		}
		
		setName(name);
	
		super.initialize();
		
		if(name.equals(tagId)){
            StringBuilder buffer = new StringBuilder();
            
			buffer.append(name);
			buffer.append((int)(Math.random() * 9999));
			
			setName(buffer.toString());
		}

		String                   actionFormName      = getActionFormName();
		GridPropertyTag          gridTag             = (GridPropertyTag)findAncestorWithClass(this, GridPropertyTag.class);
		SearchPropertiesGroupTag searchPropertiesTag = (SearchPropertiesGroupTag)findAncestorWithClass(this, SearchPropertiesGroupTag.class);
		StringBuilder            onClickContent      = null;
		String                   onClick             = getOnClick();

		if(actionFormName.length() > 0){
            onClickContent = new StringBuilder();
            onClickContent.append(onClick);
            
            if(onClick.length() > 0 && !onClick.endsWith(";"))
                onClickContent.append("; ");
            
		    if(action.length() > 0){
     			onClickContent.append("document.");
     			onClickContent.append(actionFormName);
     			onClickContent.append(".");
     			
     			if(searchPropertiesTag != null)
     			    onClickContent.append(AttributeConstants.VALIDATE_SEARCH_MODEL_KEY);
     			else
     			    onClickContent.append(AttributeConstants.VALIDATE_MODEL_KEY);

 				onClickContent.append(".value = ");
     			onClickContent.append(validate);
     			onClickContent.append(";");
     			
     			if(validateProperties.length() > 0){
     				onClickContent.append(" document.");
     				onClickContent.append(actionFormName);
     				onClickContent.append(".");
     				onClickContent.append(AttributeConstants.VALIDATE_PROPERTIES_KEY);
     				onClickContent.append(".value = '");
     				onClickContent.append(validateProperties);
     				onClickContent.append("';");
     			}
     			
     			onClickContent.append(" document.");
     			onClickContent.append(actionFormName);
                onClickContent.append(".");
                onClickContent.append(AttributeConstants.ACTION_KEY);
     			onClickContent.append(".value = '");
     			onClickContent.append(action);
     			onClickContent.append("';");
     			
     			if(forward.length() > 0){
     				onClickContent.append(" document.");
     				onClickContent.append(actionFormName);
     				onClickContent.append(".");
     				onClickContent.append(AttributeConstants.FORWARD_KEY);
     				onClickContent.append(".value = '");
     				onClickContent.append(forward);
     				onClickContent.append("';");
     			}
     			
     			if(forwardOnFail.length() > 0){
     				onClickContent.append(" document.");
     				onClickContent.append(actionFormName);
                    onClickContent.append(".");
                    onClickContent.append(AttributeConstants.FORWARD_ON_FAIL_KEY);
                    onClickContent.append(".value = '");
     				onClickContent.append(forwardOnFail);
     				onClickContent.append("';");
     			}

                if(gridTag != null && hasAction()){
                    onClickContent.append(" pagerAction('");
                    onClickContent.append(gridTag.getName());
                    onClickContent.append("', '");
                    onClickContent.append(pagerAction);
                    onClickContent.append("', '");
                    onClickContent.append(updateViews);
                    onClickContent.append("', '");
                    onClickContent.append(actionFormName);
                    onClickContent.append("');");
                }
                else{
                    if(updateViews.length() > 0){
                        onClickContent.append(" document.");
                        onClickContent.append(actionFormName);
                        onClickContent.append(".");
                        onClickContent.append(AttributeConstants.UPDATE_VIEWS_KEY);
                        onClickContent.append(".value = '");
                        onClickContent.append(updateViews);
                        onClickContent.append("';");
                    }

                    onClickContent.append(" submitForm(document.");
         			onClickContent.append(actionFormName);
         			onClickContent.append(");");
                }
                
                setOnClick(onClickContent.toString());
		    }
		}
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#render()
	 */
	protected void render() throws Throwable{
	    Boolean hasPermission = hasPermission();
	    
		if(hasPermission && isRendered()){
			GridPropertyTag gridTag = (GridPropertyTag)findAncestorWithClass(this, GridPropertyTag.class);

			if(gridTag != null){
				ButtonTag buttonTag = (ButtonTag)this.clone();

				buttonTag.setParent(null);
				buttonTag.setPageContext(gridTag.getPageContext());
				buttonTag.setOut(gridTag.getOut());
				
				gridTag.addButtonTag(buttonTag);
			}
			else
				super.render();
		}
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseActionFormElementTag#clearAttributes()
	 */
    protected void clearAttributes(){
    	super.clearAttributes();

    	setAction("");
    	setPagerAction("");
    	setForward("");
    	setForwardOnFail("");
    	setValidate(false);
    	setValidateProperties("");
    	setIconStyle("");
    	setIconStyleClass("");
    	setIconUrl("");
        setUpdateViews("");
    }
}