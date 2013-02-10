package br.com.concepting.framework.web.taglibs;

import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.util.types.ComponentType;
import br.com.concepting.framework.util.types.PagerActionType;
import br.com.concepting.framework.util.types.PositionType;
import br.com.concepting.framework.web.action.types.ActionType;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;

/**
 * Classe que define o componente visual para um botão genérico.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class ButtonTag extends BaseActionFormElementTag{
	private String          action             = "";
	private String          forward            = "";
	private String          forwardOnFail      = "";
    private String          iconStyleClass     = "";
    private String          iconStyle          = "";
    private String          iconUrl            = "";
    private PagerActionType pagerAction        = null;
    private String          updateViews        = "";
	private Boolean         validate           = false;
	private String          validateProperties = "";
	
	/**
	 * Indica se possui ação definida.
	 * 
	 * @return True/False.
	 */
	protected Boolean hasAction(){
	    return (StringUtil.trim(action).length() > 0);
	}

    /**
     * Retorna os identificadores das views (separados por vírgula) a serem atualizadas após o
     * processamento da ação requisitada.
     * 
     * @return String contendo os identificadores das views.
     */
	public String getUpdateViews(){
        return updateViews;
    }

    /**
     * Define os identificadores das views (separados por vírgula) a serem atualizadas após o
     * processamento da ação requisitada.
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
     * Retorna a ação de paginação a ser executada.
     * 
     * @return Constante que define a ação de paginação.
     */
	public PagerActionType getPagerAction(){
     	return pagerAction;
    }

    /**
     * Define a ação de paginação a ser executada.
     * 
     * @param pagerAction Constante que define a ação de paginação.
     */
	protected void setPagerAction(PagerActionType pagerAction){
     	this.pagerAction = pagerAction;
    }
	
    /**
     * Define a ação de paginação a ser executada.
     * 
     * @param pagerAction String que define a ação de paginação.
     */
	public void setPagerAction(String pagerAction){
	    if(pagerAction.length() > 0)
	        this.pagerAction = PagerActionType.valueOf(pagerAction.toUpperCase());
	    else
	        this.pagerAction = null; 
	}

    /**
     * Retorna a ação a ser executada.
     * 
     * @return String que define a ação.
     */
	public String getAction(){
		return action;
	}

    /**
     * Define a ação a ser executada.
     * 
     * @param action String que define a ação.
     */
	public void setAction(String action){
        this.action = action;
	}
	
    /**
     * Define a ação a ser executada.
     * 
     * @param action Constante que define a ação.
     */
	protected void setAction(ActionType action){
	    if(action != null)
	        this.action = action.getMethod();
        else
            this.action = "";
	}

	/**
	 * Retorna o identificador do redirecionamento da ação.
	 * 
	 * @return String contendo o identificador do redirecionamento.
	 */
	public String getForward(){
		return forward;
	}

	/**
	 * Define o identificador do redirecionamento da ação.
	 * 
	 * @param forward String contendo o identificador do redirecionamento.
	 */
	public void setForward(String forward){
		this.forward = forward;
	}

	/**
	 * Retorna o identificador do redirecionamento da ação quando ocorrer erros.
	 * 
	 * @return String contendo o identificador do redirecionamento.
	 */
	public String getForwardOnFail(){
		return forwardOnFail;
	}

	/**
	 * Define o identificador do redirecionamento da ação quando ocorrer erros.
	 * 
	 * @param forwardOnFail String contendo o identificador do redirecionamento.
	 */
	public void setForwardOnFail(String forwardOnFail){
		this.forwardOnFail = forwardOnFail;
	}

	/**
	 * Indica se a ação deve executar a validação dos dados do formulário.
	 * 
	 * @return True/False.
	 */
	public Boolean validate(){
		return validate;
	}
	
    /**
     * Indica se a ação deve executar a validação dos dados do formulário.
     * 
     * @return True/False.
     */
    public Boolean getValidate(){
        return validate();
    }

	/**
	 * Define se a ação deve executar a validação dos dados do formulário.
	 * 
	 * @param validate True/False.
	 */
	public void setValidate(Boolean validate){
		this.validate = validate;
	}

	/**
	 * Retorna o estilo CSS para o ícone vinculado ao componente.
	 * 
	 * @return String contendo o estilo CSS para o ícone.
	 */
	public String getIconStyle(){
		return iconStyle;
	}

	/**
	 * Define o estilo CSS para o ícone vinculado ao componente.
	 * 
	 * @param iconStyle String contendo o estilo CSS para o ícone.
	 */
	public void setIconStyle(String iconStyle){
		this.iconStyle = iconStyle;
	}

	/**
	 * Retorna o estilo CSS para o ícone vinculado ao componente.
	 * 
	 * @return String contendo o estilo CSS para o ícone.
	 */
	public String getIconStyleClass(){
		return iconStyleClass;
	}

	/**
	 * Define o estilo CSS para o ícone vinculado ao componente.
	 * 
	 * @param iconStyleClass String contendo o estilo CSS para o ícone.
	 */
	public void setIconStyleClass(String iconStyleClass){
		this.iconStyleClass = iconStyleClass;
	}

	/**
	 * Retorna a URL do ícone vinculado ao componente.
	 * 
	 * @return String contendo a URL do ícone.
	 */
	public String getIconUrl(){
		return iconUrl;
	}

	/**
	 * Define a URL do ícone vinculado ao componente.
	 * 
	 * @param iconUrl String contendo a URL do ícone.
	 */
	public void setIconUrl(String iconUrl){
		this.iconUrl = iconUrl;
	}

	/**
	 * Retorna uma string delimitada contendo as propriedades a serem validadas.
	 * 
	 * @return String contendo as propriedades a serem validadas.
	 */
	public String getValidateProperties(){
		return validateProperties;
	}

	/**
	 * Defime uma string delimitada contendo as propriedades a serem validadas.
	 * 
	 * @param validateProperties String contendo as propriedades a serem validadas.
	 */
	public void setValidateProperties(String validateProperties){
		this.validateProperties = validateProperties;
	}

	/**
	 * Monta o ícone vinculado ao componente.
	 * 
	 * @throws Throwable
	 */
	protected void buildIcon() throws Throwable{
		if(iconUrl.length() > 0 || iconStyleClass.length() > 0 || iconStyle.length() > 0){
            print("<td align=\"");
            print(AlignmentType.CENTER);
            println("\">");

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
			
			if(getLabelPosition() == PositionType.BOTTOM){
				println("</tr>");
				println("<tr>");
			}
		}
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#renderLabel()
	 */
	protected void renderLabel() throws Throwable{
	    super.renderLabel();
		
		if(getLabelPosition() == PositionType.TOP){
		    println("</tr>");
		    println("<tr>");
		}
	}
	
	protected void renderLabelBody() throws Throwable{
	    println(getLabel());
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderOpen()
	 */
	protected void renderOpen() throws Throwable{
		print("<button");

		renderAttributes();

		println(">");
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderBody()
	 */
	protected void renderBody() throws Throwable{
        print("<table class\"");
        print(TaglibConstants.DEFAULT_PANEL_STYLE_CLASS);
        println("\">");
		println("<tr>");

		PositionType labelPosition = getLabelPosition();
		
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
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderClose()
	 */
	protected void renderClose() throws Throwable{
		println("</button>");
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#initialize()
	 */
	protected void initialize() throws Throwable{
	    if(getComponentType() == null)
	        setComponentType(ComponentType.BUTTON);

	    PositionType labelPosition = getLabelPosition();
	    
        if(labelPosition == null){
            labelPosition = PositionType.RIGHT;
            
            setLabelPosition(labelPosition);
        }
        
        AlignmentType labelAlignment = getLabelAlignment();
	    
	    if(labelAlignment == null){
    	    if(labelPosition == PositionType.TOP || labelPosition == PositionType.BOTTOM)
    	        labelAlignment = AlignmentType.CENTER;
    	    else if(labelPosition == PositionType.LEFT)
                labelAlignment = AlignmentType.RIGHT;
            else if(labelPosition == PositionType.RIGHT)
                labelAlignment = AlignmentType.LEFT;
    	    
    	    setLabelAlignment(labelAlignment);
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
				styleClassContent.append("Disabled");

			setStyleClass(styleClassContent.toString());
		}
		
		String iconStyleClass = getIconStyleClass();

		if(iconStyleClass.length() == 0){
            StringBuilder iconStyleClassContent = new StringBuilder();
			
			iconStyleClassContent.append(name);
			iconStyleClassContent.append("Icon");

			if(!isEnabled)
				iconStyleClassContent.append("Disabled");

			setIconStyleClass(iconStyleClassContent.toString());
		}
		
		String labelStyleClass = getLabelStyleClass();
		
		if(labelStyleClass.length() == 0){
            StringBuilder labelStyleClassContent = new StringBuilder();
            
			labelStyleClassContent.append(name);
			labelStyleClassContent.append("Label");
			
			if(!isEnabled)
				labelStyleClassContent.append("Disabled");
			
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

		String                   actionForm          = getActionForm();
		GridPropertyTag          gridTag             = (GridPropertyTag)findAncestorWithClass(this, GridPropertyTag.class);
		SearchPropertiesGroupTag searchPropertiesTag = (SearchPropertiesGroupTag)findAncestorWithClass(this, SearchPropertiesGroupTag.class);
		StringBuilder            onClickContent      = null;
		String                   onClick             = getOnClick();

		if(actionForm.length() > 0){
            onClickContent = new StringBuilder();
            onClickContent.append(onClick);
            
            if(onClick.length() > 0 && !onClick.endsWith(";"))
                onClickContent.append("; ");
            
		    if(action.length() > 0){
     			onClickContent.append("document.");
     			onClickContent.append(actionForm);
     			
     			if(searchPropertiesTag != null)
     				onClickContent.append(".validateSearchModel.value = ");
     			else
     				onClickContent.append(".validateModel.value = ");
     			
     			onClickContent.append(validate);
     			onClickContent.append(";");
     			
     			if(validateProperties.length() > 0){
     				onClickContent.append(" document.");
     				onClickContent.append(actionForm);
     				onClickContent.append(".validateProperties.value = '");
     				onClickContent.append(validateProperties);
     				onClickContent.append("';");
     			}
     			
     			onClickContent.append(" document.");
     			onClickContent.append(actionForm);
     			onClickContent.append(".action.value = '");
     			onClickContent.append(action);
     			onClickContent.append("';");
     			
     			if(forward.length() > 0){
     				onClickContent.append(" document.");
     				onClickContent.append(actionForm);
     				onClickContent.append(".forward.value = '");
     				onClickContent.append(forward);
     				onClickContent.append("';");
     			}
     			
     			if(forwardOnFail.length() > 0){
     				onClickContent.append(" document.");
     				onClickContent.append(actionForm);
     				onClickContent.append(".forwardOnFail.value = '");
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
                    onClickContent.append(actionForm);
                    onClickContent.append("');");
                }
                else{
                    if(updateViews.length() > 0){
                        onClickContent.append(" document.");
                        onClickContent.append(actionForm);
                        onClickContent.append(".updateViews.value = '");
                        onClickContent.append(updateViews);
                        onClickContent.append("';");
                    }

                    onClickContent.append(" submitForm(document.");
         			onClickContent.append(actionForm);
         			onClickContent.append(");");
                }
                
                setOnClick(onClickContent.toString());
		    }
		}
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#render()
	 */
	protected void render() throws Throwable{
	    Boolean hasPermission = hasPermission();
	    
		if(hasPermission){
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
	 * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#clearAttributes()
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