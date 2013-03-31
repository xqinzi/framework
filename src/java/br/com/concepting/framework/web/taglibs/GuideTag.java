package br.com.concepting.framework.web.taglibs;

import javax.servlet.jsp.tagext.BodyContent;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.ComponentType;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;

/**
 * Classe que define o componente visual para uma guia.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class GuideTag extends BaseActionFormElementTag{
	private String  onSelect                         = "";
	private String  onSelectAction                   = "";
	private String  onSelectActionForward            = "";
	private String  onSelectActionForwardOnFail      = "";
	private String  onSelectActionUpdateViews        = "";
	private Boolean onSelectActionValidate           = false;
	private String  onSelectActionValidateProperties = "";
	private String  content                          = null;
	
    /**
     * Retorna o identificador da ação do evento de seleção.
     * 
     * @return String contendo o identificador da ação.
     */
    public String getOnSelectAction(){
        return onSelectAction;
    }

    /**
     * Define o identificador da ação do evento de seleção.
     * 
     * @param onSelectAction String contendo o identificador da ação.
     */
    public void setOnSelectAction(String onSelectAction){
        this.onSelectAction = onSelectAction;
    }

    /**
     * Retorna o identificador do redirecionamento da ação do evento de seleção.
     * 
     * @return String contendo o identificador do redirecionamento da ação.
     */
    public String getOnSelectActionForward(){
        return onSelectActionForward;
    }

    /**
     * Define o identificador do redirecionamento da ação do evento de seleção.
     * 
     * @param onSelectActionForward String contendo o identificador do redirecionamento da ação.
     */
    public void setOnSelectActionForward(String onSelectActionForward){
        this.onSelectActionForward = onSelectActionForward;
    }

    /**
     * Retorna o identificador do redirecionamento, em caso de falha, da ação do evento de seleção.
     * 
     * @return String contendo o identificador do redirecionamento da ação.
     */
    public String getOnSelectActionForwardOnFail(){
        return onSelectActionForwardOnFail;
    }

    /**
     * Define o identificador do redirecionamento, em caso de falha, da ação do evento de seleção.
     * 
     * @param onSelectActionForwardOnFail String contendo o identificador do redirecionamento da ação.
     */
    public void setOnSelectActionForwardOnFail(String onSelectActionForwardOnFail){
        this.onSelectActionForwardOnFail = onSelectActionForwardOnFail;
    }

    /**
     * Retorna o identificador das views a serem atualizadas após a execução da ação do evento de seleção.
     * 
     * @return String contendo o identificador das views.
     */
    public String getOnSelectActionUpdateViews(){
        return onSelectActionUpdateViews;
    }

    /**
     * Define o identificador das views a serem atualizadas após a execução da ação do evento de seleção.
     * 
     * @param onSelectActionUpdateViews String contendo o identificador das views.
     */
    public void setOnSelectActionUpdateViews(String onSelectActionUpdateViews){
        this.onSelectActionUpdateViews = onSelectActionUpdateViews;
    }

    /**
     * Indica se o modelo de dados do formulário deve ser validado na execução da ação do evento de seleção.
     * 
     * @return True/False.
     */
    public Boolean getOnSelectActionValidate(){
        return onSelectActionValidate;
    }

    /**
     * Define se o modelo de dados do formulário deve ser validado na execução da ação do evento de seleção.
     * 
     * @param onSelectActionValidate True/False.
     */
    public void setOnSelectActionValidate(Boolean onSelectActionValidate){
        this.onSelectActionValidate = onSelectActionValidate;
    }

    /**
     * Retorna as propriedades do modelo de dados do formulário que devem ser validadas na execução da ação do evento de seleção.
     * 
     * @return String contendo os identificadores das propriedades do modelo de dados do formulário.
     */
    public String getOnSelectActionValidateProperties(){
        return onSelectActionValidateProperties;
    }

    /**
     * Define as propriedades do modelo de dados do formulário que devem ser validadas na execução da ação do evento de seleção.
     * 
     * @param onSelectActionValidateProperties String contendo os identificadores das propriedades do modelo de dados do formulário.
     */
    public void setOnSelectActionValidateProperties(String onSelectActionValidateProperties){
        this.onSelectActionValidateProperties = onSelectActionValidateProperties;
    }

    /**
	 * Retorna o conteúdo da guia.
	 * 
	 * @return String contendo o conteúdo da guia.
	 */
	public String getContent(){
		return content;
	}

	/**
	 * Define o conteúdo da guia.
	 * 
	 * @param content String contendo o conteúdo da guia.
	 */
	public void setContent(String content){
		this.content = content;
	}

	/**
	 * Retorna o evento a ser executado quando a aba for selecionada.
	 * 
	 * @return String contendo o evento a ser executado.
	 */
	public String getOnSelect(){
		return onSelect;
	}

	/**
	 * Define o evento a ser executado quando a aba for selecionada.
	 * 
	 * @param onSelect String contendo o evento a ser executado.
	 */
	public void setOnSelect(String onSelect){
		this.onSelect = onSelect;
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#initialize()
	 */
	protected void initialize() throws Throwable{
        setComponentType(ComponentType.GUIDE);
	    
        String labelStyleClass = getLabelStyleClass();
        
		if(labelStyleClass.length() == 0){
		    labelStyleClass = TaglibConstants.DEFAULT_GUIDE_LABEL_STYLE_CLASS;
		    
			setLabelStyleClass(labelStyleClass);
		}

		super.initialize();
		
        if(onSelectAction.length() > 0){
            String actionForm = getActionForm();
            
            if(actionForm.length() > 0){
                StringBuilder onSelectContent = new StringBuilder();
    
                if(onSelect.length() > 0){
                    onSelectContent.append(onSelect);
                    
                    if(!onSelect.endsWith(";"))
                        onSelectContent.append(";");
                    
                    onSelectContent.append(" ");
                }
                
                onSelectContent.append("document.");
                onSelectContent.append(actionForm);
                onSelectContent.append(".");
                
                SearchPropertiesGroupTag searchPropertiesTag = (SearchPropertiesGroupTag)findAncestorWithClass(this, SearchPropertiesGroupTag.class);
                
                if(searchPropertiesTag != null)
                    onSelectContent.append(AttributeConstants.VALIDATE_SEARCH_MODEL_KEY);
                else
                    onSelectContent.append(AttributeConstants.VALIDATE_MODEL_KEY);
                
                onSelectContent.append(".value = ");
                onSelectContent.append(onSelectActionValidate);
                onSelectContent.append(";");
                
                if(onSelectActionValidateProperties.length() > 0){
                    onSelectContent.append(" document.");
                    onSelectContent.append(actionForm);
                    onSelectContent.append(".");
                    onSelectContent.append(AttributeConstants.VALIDATE_PROPERTIES_KEY);
                    onSelectContent.append(".value = '");
                    onSelectContent.append(onSelectActionValidateProperties);
                    onSelectContent.append("'; ");
                }
                
                if(onSelectActionForward.length() > 0){
                    onSelectContent.append("document.");
                    onSelectContent.append(actionForm);
                    onSelectContent.append(".");
                    onSelectContent.append(AttributeConstants.FORWARD_KEY);
                    onSelectContent.append(".value = '");
                    onSelectContent.append(onSelectActionForward);
                    onSelectContent.append("; ");
                }
                
                if(onSelectActionForwardOnFail.length() > 0){
                    onSelectContent.append("document.");
                    onSelectContent.append(actionForm);
                    onSelectContent.append(".");
                    onSelectContent.append(AttributeConstants.FORWARD_ON_FAIL_KEY);
                    onSelectContent.append(".value = '");
                    onSelectContent.append(onSelectActionForwardOnFail);
                    onSelectContent.append("; ");
                }
    
                if(onSelectActionUpdateViews.length() > 0){
                    onSelectContent.append("document.");
                    onSelectContent.append(actionForm);
                    onSelectContent.append(".");
                    onSelectContent.append(AttributeConstants.UPDATE_VIEWS_KEY);
                    onSelectContent.append(".value = '");
                    onSelectContent.append(onSelectActionUpdateViews);
                    onSelectContent.append("; ");
                }
    
                onSelectContent.append("document.");
                onSelectContent.append(actionForm);
                onSelectContent.append(".");
                onSelectContent.append(AttributeConstants.ACTION_KEY);
                onSelectContent.append(".value = '");
                onSelectContent.append(onSelectAction);
                onSelectContent.append("'; submitForm(document.");
                onSelectContent.append(actionForm);
                onSelectContent.append(");");
                
                onSelect = onSelectContent.toString();
            }
        }
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#render()
	 */
	protected void render() throws Throwable{
		GuidesTag guidesTag = (GuidesTag)findAncestorWithClass(this, GuidesTag.class);

		if(guidesTag != null){
			if(hasPermission()){
				GuideTag    guideTag     = (GuideTag)this.clone();
				BodyContent bodyContent  = guideTag.getBodyContent();
				String      guideContent = guideTag.getContent();

				if(bodyContent != null && guideContent == null){
				    guideContent = StringUtil.trim(bodyContent.getString());
				    
					guideTag.setContent(guideContent);
				}

				guidesTag.addGuideTag(guideTag);
			}
		}
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#clearAttributes()
	 */
	protected void clearAttributes(){
		super.clearAttributes();

		setOnSelect("");
		setOnSelectAction("");
		setOnSelectActionForward("");
		setOnSelectActionForwardOnFail("");
		setOnSelectActionUpdateViews("");
		setOnSelectActionValidate(false);
		setOnSelectActionValidateProperties("");
		setContent(null);
	}
}