package br.com.concepting.framework.ui.taglibs;

import javax.servlet.jsp.PageContext;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.controller.form.ActionFormMessageController;
import br.com.concepting.framework.controller.helpers.RequestInfo;
import br.com.concepting.framework.model.FormModel;
import br.com.concepting.framework.model.ObjectModel;
import br.com.concepting.framework.model.SystemModuleModel;
import br.com.concepting.framework.resource.PropertiesResource;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.security.model.UserModel;
import br.com.concepting.framework.ui.constants.TaglibConstants;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.util.types.ComponentType;
import br.com.concepting.framework.util.types.PositionType;
import br.com.concepting.framework.util.types.ScopeType;

/**
 * Classe que define a estrutura básica para um objeto/componente de um formulário.
 * 
 * @author fvilarinho
 * @since 2.0
 */
public abstract class BaseActionFormElementTag extends BaseTag{
    private   String                      alignment                      = "";
	private   String                      label                          = null;
	private   String                      labelStyleClass                = "";
	private   String                      labelStyle                     = "";
	private   String                      labelAlignment                 = "";
	private   String                      labelVerticalAlignment         = "";
	private   String                      labelPosition                  = "";
	private   Boolean                     showLabel                      = true;
    private   Boolean                     multipleSelection              = null;
	private   String                      actionFormName                 = "";
    private   ActionFormTag               actionFormTag                  = null;
	private   Boolean                     hasPermission                  = true;
    private   RequestInfo                 requestInfo                    = null;
    private   String                      onBlurAction                   = "";
    private   String                      onBlurActionForward            = "";
    private   String                      onBlurActionForwardOnFail      = "";
    private   String                      onBlurActionUpdateViews        = "";
    private   String                      onFocusAction                  = "";
    private   String                      onFocusActionForward           = "";
    private   String                      onFocusActionForwardOnFail     = "";
    private   String                      onFocusActionUpdateViews       = "";
    private   String                      onClickAction                  = "";
    private   String                      onClickActionForward           = "";
    private   String                      onClickActionForwardOnFail     = "";
    private   String                      onClickActionUpdateViews       = "";
    private   String                      onMouseOverAction              = "";
    private   String                      onMouseOverActionForward       = "";
    private   String                      onMouseOverActionForwardOnFail = "";
    private   String                      onMouseOverActionUpdateViews   = "";
    private   String                      onMouseOutAction               = "";
    private   String                      onMouseOutActionForward        = "";
    private   String                      onMouseOutActionForwardOnFail  = "";
    private   String                      onMouseOutActionUpdateViews    = "";
    protected ActionFormMessageController actionFormMessageController    = null;
    
   /**
    * Indica se o componente terá múltipla seleção.
    * 
    * @return True/False.
    */
   public Boolean hasMultipleSelection(){
       return multipleSelection;
   }

   /**
    * Indica se o componente terá múltipla seleção.
    * 
    * @return True/False.
    */
   public Boolean getMultipleSelection(){
       return hasMultipleSelection();
   }

   /**
    * Define se o componente terá múltipla seleção.
    * 
    * @param multipleSelection True/False.
    */
   public void setMultipleSelection(Boolean multipleSelection){
       this.multipleSelection = multipleSelection;
   }

   /**
     * Retorna o identificador das views a serem atualizadas após a execução a ação de tirar o foco.
     * 
     * @return String contendo o identificador das views.
     */
    public String getOnBlurActionUpdateViews(){
        return onBlurActionUpdateViews;
    }

    /**
     * Define o identificador das views a serem atualizadas após a execução a ação de tirar o foco.
     * 
     * @param onBlurActionUpdateViews String contendo o identificador das views.
     */
    public void setOnBlurActionUpdateViews(String onBlurActionUpdateViews){
        this.onBlurActionUpdateViews = onBlurActionUpdateViews;
    }

    /**
     * Retorna o identificador das views a serem atualizadas após a execução a ação de colocar o foco.
     * 
     * @return String contendo o identificador das views.
     */
    public String getOnFocusActionUpdateViews(){
        return onFocusActionUpdateViews;
    }

    /**
     * Define o identificador das views a serem atualizadas após a execução a ação de colocar o foco.
     * 
     * @param onFocusActionUpdateViews String contendo o identificador das views.
     */
    public void setOnFocusActionUpdateViews(String onFocusActionUpdateViews){
        this.onFocusActionUpdateViews = onFocusActionUpdateViews;
    }

    /**
     * Retorna o identificador das views a serem atualizadas após a execução a ação de clique.
     * 
     * @return String contendo o identificador das views.
     */
    public String getOnClickActionUpdateViews(){
        return onClickActionUpdateViews;
    }

    /**
     * Define o identificador das views a serem atualizadas após a execução a ação de clique.
     * 
     * @param onClickActionUpdateViews String contendo o identificador das views.
     */
    public void setOnClickActionUpdateViews(String onClickActionUpdateViews){
        this.onClickActionUpdateViews = onClickActionUpdateViews;
    }

    /**
     * Retorna o identificador das views a serem atualizadas após a execução a ação de passar o mouse.
     * 
     * @return String contendo o identificador das views.
     */
    public String getOnMouseOverActionUpdateViews(){
        return onMouseOverActionUpdateViews;
    }

    /**
     * Define o identificador das views a serem atualizadas após a execução a ação de passar o mouse.
     * 
     * @param onMouseOverActionUpdateViews String contendo o identificador das views.
     */
    public void setOnMouseOverActionUpdateViews(String onMouseOverActionUpdateViews){
        this.onMouseOverActionUpdateViews = onMouseOverActionUpdateViews;
    }

    /**
     * Retorna o identificador das views a serem atualizadas após a execução a ação de tirar o mouse.
     * 
     * @return String contendo o identificador das views.
     */
    public String getOnMouseOutActionUpdateViews(){
        return onMouseOutActionUpdateViews;
    }

    /**
     * Define o identificador das views a serem atualizadas após a execução a ação de tirar o mouse.
     * 
     * @param onMouseOutActionUpdateViews String contendo o identificador das views.
     */
    public void setOnMouseOutActionUpdateViews(String onMouseOutActionUpdateViews){
        this.onMouseOutActionUpdateViews = onMouseOutActionUpdateViews;
    }

    /**
     * Retorna o identificador do redirecionamento após a execução a ação de colocar o foco.
     * 
     * @return String contendo o identificador do redirecionamento.
     */
    public String getOnBlurActionForward(){
        return onBlurActionForward;
    }

    /**
     * Define o identificador do redirecionamento após a execução a ação de colocar o foco.
     * 
     * @param onBlurActionForward String contendo o identificador do redirecionamento.
     */
    public void setOnBlurActionForward(String onBlurActionForward){
        this.onBlurActionForward = onBlurActionForward;
    }

    /**
     * Retorna o identificador do redirecionamento em caso de falha após a execução a ação de tirar o foco.
     * 
     * @return String contendo o identificador do redirecionamento.
     */
    public String getOnBlurActionForwardOnFail(){
        return onBlurActionForwardOnFail;
    }

    /**
     * Define o identificador do redirecionamento em caso de falha após a execução a ação de tirar o foco.
     * 
     * @param onBlurActionForwardOnFail String contendo o identificador do redirecionamento.
     */
    public void setOnBlurActionForwardOnFail(String onBlurActionForwardOnFail){
        this.onBlurActionForwardOnFail = onBlurActionForwardOnFail;
    }

    /**
     * Retorna o identificador do redirecionamento após a execução a ação de colocar o foco.
     * 
     * @return String contendo o identificador do redirecionamento.
     */
    public String getOnFocusActionForward(){
        return onFocusActionForward;
    }

    /**
     * Define o identificador do redirecionamento após a execução a ação de colocar o foco.
     * 
     * @param onFocusActionForward String contendo o identificador do redirecionamento.
     */
    public void setOnFocusActionForward(String onFocusActionForward){
        this.onFocusActionForward = onFocusActionForward;
    }

    /**
     * Retorna o identificador do redirecionamento em caso de falha após a execução a ação de colocar o foco.
     * 
     * @return String contendo o identificador do redirecionamento.
     */
    public String getOnFocusActionForwardOnFail(){
        return onFocusActionForwardOnFail;
    }

    /**
     * Define o identificador do redirecionamento em caso de falha após a execução a ação de colocar o foco.
     * 
     * @param onFocusActionForwardOnFail String contendo o identificador do redirecionamento.
     */
    public void setOnFocusActionForwardOnFail(String onFocusActionForwardOnFail){
        this.onFocusActionForwardOnFail = onFocusActionForwardOnFail;
    }

    /**
     * Retorna o identificador do redirecionamento após a execução a ação de clique.
     * 
     * @return String contendo o identificador do redirecionamento.
     */
    public String getOnClickActionForward(){
        return onClickActionForward;
    }

    /**
     * Define o identificador do redirecionamento após a execução a ação de clique.
     * 
     * @param onClickActionForward String contendo o identificador do redirecionamento.
     */
    public void setOnClickActionForward(String onClickActionForward){
        this.onClickActionForward = onClickActionForward;
    }

    /**
     * Retorna o identificador do redirecionamento em caso de falha após a execução a ação de clique.
     * 
     * @return String contendo o identificador do redirecionamento.
     */
    public String getOnClickActionForwardOnFail(){
        return onClickActionForwardOnFail;
    }

    /**
     * Define o identificador do redirecionamento em caso de falha após a execução a ação de clique.
     * 
     * @param onClickActionForwardOnFail String contendo o identificador do redirecionamento.
     */
    public void setOnClickActionForwardOnFail(String onClickActionForwardOnFail){
        this.onClickActionForwardOnFail = onClickActionForwardOnFail;
    }

    /**
     * Retorna o identificador do redirecionamento após a execução a ação de passar o mouse.
     * 
     * @return String contendo o identificador do redirecionamento.
     */
    public String getOnMouseOverActionForward(){
        return onMouseOverActionForward;
    }

    /**
     * Define o identificador do redirecionamento após a execução a ação de passar o mouse.
     * 
     * @param onMouseOverActionForward String contendo o identificador do redirecionamento.
     */
    public void setOnMouseOverActionForward(String onMouseOverActionForward){
        this.onMouseOverActionForward = onMouseOverActionForward;
    }

    /**
     * Retorna o identificador do redirecionamento em caso de falha após a execução a ação de passar o mouse.
     * 
     * @return String contendo o identificador do redirecionamento.
     */
    public String getOnMouseOverActionForwardOnFail(){
        return onMouseOverActionForwardOnFail;
    }

    /**
     * Define o identificador do redirecionamento em caso de falha após a execução a ação de passar o mouse.
     * 
     * @param onMouseOverActionForwardOnFail String contendo o identificador do redirecionamento.
     */
    public void setOnMouseOverActionForwardOnFail(String onMouseOverActionForwardOnFail){
        this.onMouseOverActionForwardOnFail = onMouseOverActionForwardOnFail;
    }

    /**
     * Retorna o identificador do redirecionamento após a execução a ação de tirar o mouse.
     * 
     * @return String contendo o identificador do redirecionamento.
     */
    public String getOnMouseOutActionForward(){
        return onMouseOutActionForward;
    }

    /**
     * Define o identificador do redirecionamento após a execução a ação de tirar o mouse.
     * 
     * @param onMouseOutActionForward String contendo o identificador do redirecionamento.
     */
    public void setOnMouseOutActionForward(String onMouseOutActionForward){
        this.onMouseOutActionForward = onMouseOutActionForward;
    }

    /**
     * Retorna o identificador do redirecionamento em caso de falha após a execução a ação de tirar o mouse.
     * 
     * @return String contendo o identificador do redirecionamento.
     */
    public String getOnMouseOutActionForwardOnFail(){
        return onMouseOutActionForwardOnFail;
    }

    /**
     * Define o identificador do redirecionamento em caso de falha após a execução a ação de tirar o mouse.
     * 
     * @param onMouseOutActionForwardOnFail String contendo o identificador do redirecionamento.
     */
    public void setOnMouseOutActionForwardOnFail(String onMouseOutActionForwardOnFail){
        this.onMouseOutActionForwardOnFail = onMouseOutActionForwardOnFail;
    }

    /**
     * Retorna o identificador da ação de tirar o foco.
     * 
     * @return String contendo o identificador da ação.
     */
    public String getOnBlurAction(){
        return onBlurAction;
    }

    /**
     * Define o identificador da ação de tirar o foco.
     * 
     * @param onBlurAction String contendo o identificador da ação.
     */
    public void setOnBlurAction(String onBlurAction){
        this.onBlurAction = onBlurAction;
    }

    /**
     * Retorna o identificador da ação de colocar o foco.
     * 
     * @return String contendo o identificador da ação.
     */
    public String getOnFocusAction(){
        return onFocusAction;
    }

    /**
     * Define o identificador da ação de colocar o foco.
     * 
     * @param onFocusAction String contendo o identificador da ação.
     */
    public void setOnFocusAction(String onFocusAction){
        this.onFocusAction = onFocusAction;
    }

    /**
     * Retorna o identificador da ação de clique.
     * 
     * @return String contendo o identificador da ação.
     */
    public String getOnClickAction(){
        return onClickAction;
    }

    /**
     * Define o identificador da ação de clique.
     * 
     * @param onClickAction String contendo o identificador da ação.
     */
    public void setOnClickAction(String onClickAction){
        this.onClickAction = onClickAction;
    }

    /**
     * Retorna o identificador da ação de colocar o mouse.
     * 
     * @return String contendo o identificador da ação.
     */
    public String getOnMouseOverAction(){
        return onMouseOverAction;
    }

    /**
     * Define o identificador da ação de colocar o mouse.
     * 
     * @param onMouseOverAction String contendo o identificador da ação.
     */
    public void setOnMouseOverAction(String onMouseOverAction){
        this.onMouseOverAction = onMouseOverAction;
    }

    /**
     * Retorna o identificador da ação de tirar o mouse.
     * 
     * @return String contendo o identificador da ação.
     */
    public String getOnMouseOutAction(){
        return onMouseOutAction;
    }

    /**
     * Define o identificador da ação de tirar o mouse.
     * 
     * @param onMouseOutAction String contendo o identificador da ação.
     */
    public void setOnMouseOutAction(String onMouseOutAction){
        this.onMouseOutAction = onMouseOutAction;
    }

    /**
     * @see br.com.concepting.framework.ui.taglibs.BaseTag#setPageContext(javax.servlet.jsp.PageContext)
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
    public String getActionFormName(){
        return actionFormName;
    }

    /**
     * Define o identificador do formulário onde o componente está localizado.
     * 
     * @param actionFormName String contendo o identificador do formulário.
     */
    public void setActionFormName(String actionFormName){
        this.actionFormName = actionFormName;
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
            
            if(actionFormTag != null && StringUtil.trim(actionFormName).length() == 0)
                actionFormName = actionFormTag.getBeanName();
        }
        
        actionFormName = StringUtil.trim(actionFormName);

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
            this.actionFormName = actionFormTag.getBeanName();
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
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#initialize()
	 */
	protected void initialize() throws Throwable{
	    PositionType labelPosition = getLabelPositionType();
	    
		if(labelPosition == null){
		    labelPosition = PositionType.LEFT;
		    
		    setLabelPositionType(labelPosition);
		}
		
		AlignmentType labelAlignment = getLabelAlignmentType();
		
        if(labelAlignment == null){
            if(labelPosition == PositionType.LEFT)
                labelAlignment = AlignmentType.RIGHT;
            else if(labelPosition == PositionType.RIGHT)
                labelAlignment = AlignmentType.LEFT;
            
            setLabelAlignmentType(labelAlignment);
        }
        
        ActionFormTag actionFormTag  = getActionFormTag();
        String        actionFormName = getActionFormName();
        String        name           = getName();
        String        resourceId     = getResourceId();

        if(actionFormTag != null){
            if(resourceId.length() == 0){
                resourceId = actionFormTag.getResourceId();
                
                setResourceId(resourceId);
            }
        }
        
        if(actionFormName.length() > 0){
            StringBuilder onBlurContent = new StringBuilder();
            
            if(onBlurAction.length() > 0){
                String onBlur = getOnBlur();
                
                if(onBlur.length() > 0){
                    onBlurContent.append(onBlur);
                    
                    if(!onBlur.endsWith(";"))
                        onBlurContent.append(";");
                    
                    onBlurContent.append(" ");
                }
                
                if(onBlurActionForward.length() > 0){
                    onBlurContent.append("document.");
                    onBlurContent.append(actionFormName);
                    onBlurContent.append(".");
                    onBlurContent.append(AttributeConstants.FORWARD_KEY);
                    onBlurContent.append(".value = '");
                    onBlurContent.append(onBlurActionForward);
                    onBlurContent.append("; ");
                }
                
                if(onBlurActionForwardOnFail.length() > 0){
                    onBlurContent.append("document.");
                    onBlurContent.append(actionFormName);
                    onBlurContent.append(".");
                    onBlurContent.append(AttributeConstants.FORWARD_ON_FAIL_KEY);
                    onBlurContent.append(".value = '");
                    onBlurContent.append(onBlurActionForwardOnFail);
                    onBlurContent.append("; ");
                }

                if(onBlurActionUpdateViews.length() > 0){
                    onBlurContent.append("document.");
                    onBlurContent.append(actionFormName);
                    onBlurContent.append(".");
                    onBlurContent.append(AttributeConstants.UPDATE_VIEWS_KEY);
                    onBlurContent.append(".value = '");
                    onBlurContent.append(onBlurActionUpdateViews);
                    onBlurContent.append("; ");
                }

                onBlurContent.append("document.");
                onBlurContent.append(actionFormName);
                onBlurContent.append(".");
                onBlurContent.append(AttributeConstants.ACTION_KEY);
                onBlurContent.append(".value = '");
                onBlurContent.append(onClickAction);
                onBlurContent.append("'; submitForm(document.");
                onBlurContent.append(actionFormName);
                onBlurContent.append(");");
                
                setOnBlur(onBlurContent.toString());
            }

            StringBuilder onFocusContent = new StringBuilder();
            
            if(onFocusAction.length() > 0){
                String onFocus = getOnFocus();
                
                if(onFocus.length() > 0){
                    onFocusContent.append(onFocus);
                    
                    if(!onFocus.endsWith(";"))
                        onFocusContent.append(";");
                    
                    onFocusContent.append(" ");
                }
                
                if(onFocusActionForward.length() > 0){
                    onFocusContent.append("document.");
                    onFocusContent.append(actionFormName);
                    onFocusContent.append(".");
                    onFocusContent.append(AttributeConstants.FORWARD_KEY);
                    onFocusContent.append(".value = '");
                    onFocusContent.append(onFocusActionForward);
                    onFocusContent.append("; ");
                }
                
                if(onFocusActionForwardOnFail.length() > 0){
                    onFocusContent.append("document.");
                    onFocusContent.append(actionFormName);
                    onFocusContent.append(".");
                    onFocusContent.append(AttributeConstants.FORWARD_ON_FAIL_KEY);
                    onFocusContent.append(".value = '");
                    onFocusContent.append(onFocusActionForwardOnFail);
                    onFocusContent.append("; ");
                }

                if(onFocusActionUpdateViews.length() > 0){
                    onFocusContent.append("document.");
                    onFocusContent.append(actionFormName);
                    onFocusContent.append(".");
                    onFocusContent.append(AttributeConstants.UPDATE_VIEWS_KEY);
                    onFocusContent.append(".value = '");
                    onFocusContent.append(onFocusActionUpdateViews);
                    onFocusContent.append("; ");
                }
                
                onFocusContent.append("document.");
                onFocusContent.append(actionFormName);
                onFocusContent.append(".");
                onFocusContent.append(AttributeConstants.ACTION_KEY);
                onFocusContent.append(".value = '");
                onFocusContent.append(onClickAction);
                onFocusContent.append("'; submitForm(document.");
                onFocusContent.append(actionFormName);
                onFocusContent.append(");");
                
                setOnFocus(onFocusContent.toString());
            }

            StringBuilder onClickContent = new StringBuilder();
            
            if(onClickAction.length() > 0){
                String onClick = getOnClick();
                
                if(onClick.length() > 0){
                    onClickContent.append(onClick);
                    
                    if(!onClick.endsWith(";"))
                        onClickContent.append(";");
                    
                    onClickContent.append(" ");
                }
                
                if(onClickActionForward.length() > 0){
                    onClickContent.append("document.");
                    onClickContent.append(actionFormName);
                    onClickContent.append(".");
                    onClickContent.append(AttributeConstants.FORWARD_KEY);
                    onClickContent.append(".value = '");
                    onClickContent.append(onClickActionForward);
                    onClickContent.append("; ");
                }
                
                if(onClickActionForwardOnFail.length() > 0){
                    onClickContent.append("document.");
                    onClickContent.append(actionFormName);
                    onClickContent.append(".");
                    onClickContent.append(AttributeConstants.FORWARD_ON_FAIL_KEY);
                    onClickContent.append(".value = '");
                    onClickContent.append(onClickActionForwardOnFail);
                    onClickContent.append("; ");
                }

                if(onClickActionUpdateViews.length() > 0){
                    onClickContent.append("document.");
                    onClickContent.append(actionFormName);
                    onClickContent.append(".");
                    onClickContent.append(AttributeConstants.UPDATE_VIEWS_KEY);
                    onClickContent.append(".value = '");
                    onClickContent.append(onClickActionUpdateViews);
                    onClickContent.append("; ");
                }

                onClickContent.append("document.");
                onClickContent.append(actionFormName);
                onClickContent.append(".");
                onClickContent.append(AttributeConstants.ACTION_KEY);
                onClickContent.append(".value = '");
                onClickContent.append(onClickAction);
                onClickContent.append("'; submitForm(document.");
                onClickContent.append(actionFormName);
                onClickContent.append(");");
                
                setOnClick(onClickContent.toString());
            }
            
            StringBuilder onMouseOverContent = new StringBuilder();
            
            if(onMouseOverAction.length() > 0){
                String onMouseOver = getOnMouseOver();
                
                if(onMouseOver.length() > 0){
                    onMouseOverContent.append(onMouseOver);
                    
                    if(!onMouseOver.endsWith(";"))
                        onMouseOverContent.append(";");
                    
                    onMouseOverContent.append(" ");
                }
                
                if(onMouseOverActionForward.length() > 0){
                    onMouseOverContent.append("document.");
                    onMouseOverContent.append(actionFormName);
                    onMouseOverContent.append(".");
                    onMouseOverContent.append(AttributeConstants.FORWARD_KEY);
                    onMouseOverContent.append(".value = '");
                    onMouseOverContent.append(onMouseOverActionForward);
                    onMouseOverContent.append("; ");
                }
                
                if(onMouseOverActionForwardOnFail.length() > 0){
                    onMouseOverContent.append("document.");
                    onMouseOverContent.append(actionFormName);
                    onMouseOverContent.append(".");
                    onMouseOverContent.append(AttributeConstants.FORWARD_ON_FAIL_KEY);
                    onMouseOverContent.append(".value = '");
                    onMouseOverContent.append(onMouseOverActionForwardOnFail);
                    onMouseOverContent.append("; ");
                }

                if(onMouseOverActionUpdateViews.length() > 0){
                    onMouseOverContent.append("document.");
                    onMouseOverContent.append(actionFormName);
                    onMouseOverContent.append(".");
                    onMouseOverContent.append(AttributeConstants.UPDATE_VIEWS_KEY);
                    onMouseOverContent.append(".value = '");
                    onMouseOverContent.append(onMouseOverActionUpdateViews);
                    onMouseOverContent.append("; ");
                }

                onMouseOverContent.append("document.");
                onMouseOverContent.append(actionFormName);
                onMouseOverContent.append(".");
                onMouseOverContent.append(AttributeConstants.ACTION_KEY);
                onMouseOverContent.append(".value = '");
                onMouseOverContent.append(onMouseOverAction);
                onMouseOverContent.append("'; submitForm(document.");
                onMouseOverContent.append(actionFormName);
                onMouseOverContent.append(");");
                
                setOnMouseOver(onMouseOverContent.toString());
            }
            
            StringBuilder onMouseOutContent = new StringBuilder();
            
            if(onMouseOutAction.length() > 0){
                String onMouseOut = getOnMouseOut();
                
                if(onMouseOut.length() > 0){
                    onMouseOutContent.append(onMouseOut);
                    
                    if(!onMouseOut.endsWith(";"))
                        onMouseOutContent.append(";");
                    
                    onMouseOutContent.append(" ");
                }
                
                if(onMouseOutActionForward.length() > 0){
                    onMouseOutContent.append("document.");
                    onMouseOutContent.append(actionFormName);
                    onMouseOutContent.append(".");
                    onMouseOutContent.append(AttributeConstants.FORWARD_KEY);
                    onMouseOutContent.append(".value = '");
                    onMouseOutContent.append(onMouseOutActionForward);
                    onMouseOutContent.append("; ");
                }
                
                if(onMouseOutActionForwardOnFail.length() > 0){
                    onMouseOutContent.append("document.");
                    onMouseOutContent.append(actionFormName);
                    onMouseOutContent.append(".");
                    onMouseOutContent.append(AttributeConstants.FORWARD_ON_FAIL_KEY);
                    onMouseOutContent.append(".value = '");
                    onMouseOutContent.append(onMouseOutActionForwardOnFail);
                    onMouseOutContent.append("; ");
                }

                if(onMouseOutActionUpdateViews.length() > 0){
                    onMouseOutContent.append("document.");
                    onMouseOutContent.append(actionFormName);
                    onMouseOutContent.append(".");
                    onMouseOutContent.append(AttributeConstants.UPDATE_VIEWS_KEY);
                    onMouseOutContent.append(".value = '");
                    onMouseOutContent.append(onMouseOutActionUpdateViews);
                    onMouseOutContent.append("; ");
                }

                onMouseOutContent.append("document.");
                onMouseOutContent.append(actionFormName);
                onMouseOutContent.append(".");
                onMouseOutContent.append(AttributeConstants.ACTION_KEY);
                onMouseOutContent.append(".value = '");
                onMouseOutContent.append(onMouseOutAction);
                onMouseOutContent.append("'; submitForm(document.");
                onMouseOutContent.append(actionFormName);
                onMouseOutContent.append(");");
                
                setOnMouseOut(onMouseOutContent.toString());
            }
        }

        Boolean hasPermission = true;
	    
        try{
			LoginSessionModel loginSession = securityController.getLoginSession();
			SystemModuleModel systemModule = (loginSession != null ? loginSession.getSystemModule() : null);
			FormModel         form         = (systemModule != null ? systemModule.getForm(actionFormName) : null);
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
	    
	    if(labelVerticalAlignment.length() > 0){
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
    		
    		if(getLabelPositionType() != PositionType.RIGHT)
    		    print(":&nbsp;");
    		
    		println();
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
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#renderAttributes()
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

		if(!isEnabled()){
			print(" ");
			print(AttributeConstants.DISABLED_KEY);
		}
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
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#renderOpen()
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
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#renderBody()
	 */
	protected void renderBody() throws Throwable{
		print("<input ");

		renderAttributes();

		print(">");
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#renderClose()
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
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#render()
	 */
	protected void render() throws Throwable{
		if(hasPermission())
			super.render();
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#clearAttributes()
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
		setActionFormName("");
		setActionFormTag(null);
		setHasPermission(true);
		setMultipleSelection(null);
		setRequestInfo(null);
		setOnBlurAction("");
		setOnBlurActionForward("");
		setOnBlurActionForwardOnFail("");
		setOnBlurActionUpdateViews("");
        setOnFocusAction("");
        setOnFocusActionForward("");
        setOnFocusActionForwardOnFail("");
        setOnFocusActionUpdateViews("");
        setOnClickAction("");
        setOnClickActionForward("");
        setOnClickActionForwardOnFail("");
        setOnClickActionUpdateViews("");
        setOnMouseOverAction("");
        setOnMouseOverActionForward("");
        setOnMouseOverActionForwardOnFail("");
        setOnMouseOverActionUpdateViews("");
        setOnMouseOutAction("");
        setOnMouseOutActionForward("");
        setOnMouseOutActionForwardOnFail("");
        setOnMouseOutActionUpdateViews("");
		setAlignment("");
	}
}