package br.com.concepting.framework.web.taglibs;

import java.util.List;
import java.util.Locale;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.util.types.ComponentType;
import br.com.concepting.framework.web.form.BaseActionForm;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;
import br.com.concepting.framework.web.types.ScopeType;
 
/**
 * Classe que define o componente visual para entrada de texto com ou sem mascaramento.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class TextPropertyTag extends BasePropertyTag{
	private Integer size                                 = 0;
	private Integer maxlength                            = 0;
	private Boolean readOnly                             = false;
	private Boolean autoComplete                         = false;
	private String  autoCompleteLabelProperty            = "";
	private String  autoCompleteData                     = "";
	private String  autoCompleteDataScope                = "";
	private String  autoCompleteAction                   = "";
	private String  autoCompleteActionUpdateViews        = "";
	private Boolean autoCompleteActionValidate           = false;
	private String  autoCompleteActionValidateProperties = "";
	
	/**
	 * Retorna o identificador da propriedade do label do item de autocomplete.
	 * 
	 * @return String contendo o identificador da propriedade.
	 */
	public String getAutoCompleteLabelProperty(){
        return autoCompleteLabelProperty;
    }

    /**
     * Define o identificador da propriedade do label do item de autocomplete.
     * 
     * @param autoCompleteLabelProperty String contendo o identificador da propriedade.
     */
    public void setAutoCompleteLabelProperty(String autoCompleteLabelProperty){
        this.autoCompleteLabelProperty = autoCompleteLabelProperty;
    }

    /**
     * Retorna o identificador onde os dados do autocomplete estão armazenados.
     * 
     * @return String contendo o identificador dos dados do autocomplete.
     */
    public String getAutoCompleteData(){
        return autoCompleteData;
    }

    /**
     * Define o identificador onde os dados do autocomplete estão armazenados.
     * 
     * @param autoCompleteData String contendo o identificador dos dados do autocomplete.
     */
    public void setAutoCompleteData(String autoCompleteData){
        this.autoCompleteData = autoCompleteData;
    }

    /**
     * Retorna o escopo de armazenamento dos dados do autocomplete.
     * 
     * @return String contendo o escopo de armazenamento.
     */
    public String getAutoCompleteDataScope(){
        return autoCompleteDataScope;
    }

    /**
     * Define o escopo de armazenamento dos dados do autocomplete.
     * 
     * @param autoCompleteDataScope String contendo o escopo de armazenamento.
     */
    public void setAutoCompleteDataScope(String autoCompleteDataScope){
        this.autoCompleteDataScope = autoCompleteDataScope;
    }

    /**
     * Retorna o escopo de armazenamento dos dados do autocomplete.
     * 
     * @return Constante que define o escopo de armazenamento.
     */
    protected ScopeType getAutoCompleteDataScopeType(){
        try{
            return ScopeType.valueOf(autoCompleteDataScope);
        }
        catch(Throwable e){
            return null;
        }
    }        

    /**
     * Indica se o recurso de autocomplete está habilitado.
     * 
     * @return True/False.
     */
    public Boolean autoComplete(){
        return autoComplete;
    }

    /**
     * Indica se o recurso de autocomplete está habilitado.
     * 
     * @return True/False.
     */
    public Boolean getAutoComplete(){
        return autoComplete();
    }

    /**
     * Define se o recurso de autocomplete está habilitado.
     * 
     * @param autoComplete True/False.
     */
    public void setAutoComplete(Boolean autoComplete){
        this.autoComplete = autoComplete;
    }

    /**
     * Retorna a ação de autocomplete a ser executada.
     * 
     * @return String que define a ação.
     */
    public String getAutoCompleteAction(){
        return autoCompleteAction;
    }

    /**
     * Define a ação de autocomplete a ser executada.
     * 
     * @param autoCompleteAction String que define a ação.
     */
    public void setAutoCompleteAction(String autoCompleteAction){
        this.autoCompleteAction = autoCompleteAction;
    }

    /**
     * Retorna os identificadores das views (separados por vírgula) a serem atualizadas após o
     * processamento da ação do autocomplete.
     * 
     * @return String contendo os identificadores das views.
     */
    public String getAutoCompleteActionUpdateViews(){
        return autoCompleteActionUpdateViews;
    }

    /**
     * Define os identificadores das views (separados por vírgula) a serem atualizadas após o
     * processamento da ação do autocomplete.
     * 
     * @param autoCompleteActionUpdateViews String contendo os identificadores das views.
     */
    public void setAutoCompleteActionUpdateViews(String autoCompleteActionUpdateViews){
        this.autoCompleteActionUpdateViews = autoCompleteActionUpdateViews;
    }

    /**
     * Indica se a ação de autocomplete deve executar a validação dos dados do formulário.
     * 
     * @return True/False.
     */
    public Boolean getAutoCompleteActionValidate(){
        return autoCompleteActionValidate;
    }

    /**
     * Define se a ação de autocomplete deve executar a validação dos dados do formulário.
     * 
     * @param autoCompleteActionValidate True/False.
     */
    public void setAutoCompleteActionValidate(Boolean autoCompleteActionValidate){
        this.autoCompleteActionValidate = autoCompleteActionValidate;
    }

    /**
     * Retorna uma string delimitada contendo as propriedades a serem validadas no processamento
     * da ação do autocomplete.
     * 
     * @return String contendo as propriedades a serem validadas.
     */
    public String getAutoCompleteActionValidateProperties(){
        return autoCompleteActionValidateProperties;
    }

    /**
     * Define uma string delimitada contendo as propriedades a serem validadas no processamento
     * da ação do autocomplete.
     * 
     * @param autoCompleteActionValidateProperties String contendo as propriedades a serem validadas.
     */
    public void setAutoCompleteActionValidateProperties(String autoCompleteActionValidateProperties){
        this.autoCompleteActionValidateProperties = autoCompleteActionValidateProperties;
    }

    /**
	 * Indica se o campo é de somente leitura.
	 * 
	 * @return True/False.
	 */
	public Boolean isReadOnly(){
		return readOnly;
	}
	
	/**
	 * Indica se o campo é de somente leitura.
	 * 
	 * @return True/False.
	 */
	public Boolean getReadOnly(){
		return isReadOnly();
	}
	
	/**
	 * Define se o campo permite somente leitura.
	 * 
	 * @param readOnly True/False.
	 */
	public void setReadOnly(Boolean readOnly){
		this.readOnly = readOnly;
	}
	
	/**
	 * Retorna o tamanho do componente definido em caracteres.
	 * 
	 * @return Valor inteiro contendo do componente em caracteres.
	 */
	public Integer getSize(){
		return size;
	}
	
	/**
	 * Define o tamanho do componente definido em caracteres.
	 * 
	 * @param size Valor inteiro contendo do componente em caracteres.
	 */
	public void setSize(Integer size){
		this.size = size;
	}
	
	/**
	 * Retorna o número máximo de caracteres permitidos para digitação.
	 * 
	 * @return Valor inteiro contendo o número máximo de caracteres.
	 */
	public Integer getMaxlength(){
		return maxlength;
	}
	
	/**
	 * Define o número máximo de caracteres permitidos para digitação.
	 * 
	 * @param maxlength Valor inteiro contendo o número máximo de caracteres.
	 */
	public void setMaxlength(Integer maxlength){
		this.maxlength = maxlength;
	}
	
	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderAttributes()
	 */
	protected void renderAttributes() throws Throwable{
		super.renderAttributes();
		
		Integer size = getSize();
		
		if(size > 0){
			print(" size=\"");
			print(size + 1);
			print("\"");
		}
		
		Integer maxLength = getMaxlength();
		
		if(maxLength > 0){
			print(" maxlength=\"");
			print(maxLength);
			print("\"");
		}
		
		Boolean isReadOnly = isReadOnly();
		
		if(isReadOnly)
			print(" readOnly");
	}
	
	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#initialize()
	 */
	protected void initialize() throws Throwable{
	    if(getComponentType() == null)
	        setComponentType(ComponentType.TEXT_BOX);
	    
		super.initialize();
		
		PropertyInfo propertyInfo = getPropertyInfo();
		String       value        = getFormattedValue();
		Integer      size         = 0;
		
		if(propertyInfo == null){
		    size = value.length();
		    
		    setMaxlength(size);
		    setSize(size);
		}
		else{
			if(actionFormMessageController.hasValidationMessage(getName()))
				value = getRequestInfo().getValue();
			
            Integer maximumLength = getMaxlength();
            
            if(maximumLength == 0)
                maximumLength = propertyInfo.getMaximumLength();

            size = getSize();
		
			if(size == 0)
			    size = maximumLength;
			
			String pattern         = getPattern();
			Locale currentLanguage = systemController.getCurrentLanguage();
			
			if(propertyInfo.isNumber() && pattern.length() > 0)
				pattern = NumberUtil.normalizePattern(pattern, currentLanguage);
			
			if(pattern.length() > 0){
		        maxlength = pattern.length();
    			    
			    if(size == 0)
			        size = pattern.length() + 2;
			    
				String        onKeyPress        = getOnKeyPress();
				StringBuilder onKeyPressContent = new StringBuilder();
				
				onKeyPressContent.append("format");
				
				if(propertyInfo.isNumber())
					onKeyPressContent.append("Number");
				
				onKeyPressContent.append("Object(this, '");
				onKeyPressContent.append(pattern);
				onKeyPressContent.append("', event); ");
				onKeyPressContent.append(onKeyPress);

				setOnKeyPress(onKeyPressContent.toString());
			}
            
            if(maximumLength > 0)
                setMaxlength(maximumLength);
            
            if(size > 0)
                setSize(size);
            
            if(autoComplete){
                String        name           = StringUtil.replaceAll(getName(), ".", "_");
                String        actionFormName = getActionFormName();
                StringBuilder content        = new StringBuilder();
                
                content.append("var ");
                content.append(name);
                content.append(StringUtil.capitalize(AttributeConstants.AUTO_COMPLETE_KEY));
                content.append(StringUtil.capitalize(AttributeConstants.TIMER_KEY));
                content.append(" = null;");
                content.append(StringUtil.getLineBreak());
                content.append(StringUtil.getLineBreak());
                
                content.append("function ");
                content.append(name);
                content.append(StringUtil.capitalize(AttributeConstants.AUTO_COMPLETE_KEY));
                content.append(StringUtil.capitalize(AttributeConstants.TIMER_KEY));
                content.append("Stop(){");
                content.append(StringUtil.getLineBreak());
                
                content.append("\tif(");
                content.append(name);
                content.append(StringUtil.capitalize(AttributeConstants.AUTO_COMPLETE_KEY));
                content.append(StringUtil.capitalize(AttributeConstants.TIMER_KEY));
                content.append(")");
                content.append(StringUtil.getLineBreak());
                
                content.append("\t\tclearTimeout(");
                content.append(name);
                content.append(StringUtil.capitalize(AttributeConstants.AUTO_COMPLETE_KEY));
                content.append(StringUtil.capitalize(AttributeConstants.TIMER_KEY));
                content.append(");");
                content.append(StringUtil.getLineBreak());
                
                content.append("}");
                content.append(StringUtil.getLineBreak());
                content.append(StringUtil.getLineBreak());
                
                content.append("function ");
                content.append(name);
                content.append(StringUtil.capitalize(AttributeConstants.AUTO_COMPLETE_KEY));
                content.append(StringUtil.capitalize(AttributeConstants.TIMER_KEY));
                content.append("Start(){");
                content.append(StringUtil.getLineBreak());
                
                content.append("\tif(!");
                content.append(name);
                content.append(StringUtil.capitalize(AttributeConstants.AUTO_COMPLETE_KEY));
                content.append(StringUtil.capitalize(AttributeConstants.TIMER_KEY));
                content.append("){");
                content.append(StringUtil.getLineBreak());
                content.append(StringUtil.getLineBreak());
                
                content.append("\t\tdocument.");
                content.append(actionFormName);
                content.append(".");
                content.append(AttributeConstants.ACTION_KEY);
                content.append(".value = '");
                content.append(autoCompleteAction);
                content.append("';");
                content.append(StringUtil.getLineBreak());
                
                content.append("\t\tdocument.");
                content.append(actionFormName);
                content.append(".");
                content.append(AttributeConstants.UPDATE_VIEWS_KEY);
                content.append(".value = '");
                content.append(autoCompleteActionUpdateViews);
                content.append("';");
                content.append(StringUtil.getLineBreak());
                
                if(autoCompleteActionValidate){
                    content.append("\t\tdocument.");
                    content.append(actionFormName);
                    content.append(".");
                    
                    if(isForSearch())
                        content.append(AttributeConstants.VALIDATE_SEARCH_MODEL_KEY);
                    else
                        content.append(AttributeConstants.VALIDATE_MODEL_KEY);
                    
                    content.append(".value = '");
                    content.append(autoCompleteActionValidate);
                    content.append("';");
                    content.append(StringUtil.getLineBreak());
                    
                    if(autoCompleteActionValidateProperties.length() > 0){
                        content.append("\t\tdocument.");
                        content.append(actionFormName);
                        content.append(".");
                        content.append(AttributeConstants.VALIDATE_PROPERTIES_KEY);
                        content.append(".value = '");
                        content.append(autoCompleteActionValidateProperties);
                        content.append("';");
                        content.append(StringUtil.getLineBreak());
                    }
                }
                
                content.append("\t\t");
                content.append(name);
                content.append(StringUtil.capitalize(AttributeConstants.AUTO_COMPLETE_KEY));
                content.append(StringUtil.capitalize(AttributeConstants.TIMER_KEY));
                content.append("Stop();");
                content.append(StringUtil.getLineBreak());

                content.append("\t\tsubmitForm(document.");
                content.append(actionFormName);
                content.append(");");
                content.append(StringUtil.getLineBreak());
                
                content.append("\t\t}");
                content.append(StringUtil.getLineBreak());
                
                content.append("\t}");
                content.append(StringUtil.getLineBreak());
                
                content.append("}");
                
                ScriptTag scriptTag = new ScriptTag();
                
                scriptTag.setPageContext(pageContext);
                scriptTag.setContent(content.toString());
                scriptTag.doStartTag();
                scriptTag.doEndTag();
                
                String        onKeyPress        = getOnKeyPress();
                StringBuilder onKeyPressContent = new StringBuilder();
                
                onKeyPressContent.append(name);
                onKeyPressContent.append(StringUtil.capitalize(AttributeConstants.AUTO_COMPLETE_KEY));
                onKeyPressContent.append(StringUtil.capitalize(AttributeConstants.TIMER_KEY));
                onKeyPressContent.append("Stop(); ");
                onKeyPressContent.append(name);
                onKeyPressContent.append(StringUtil.capitalize(AttributeConstants.AUTO_COMPLETE_KEY));
                onKeyPressContent.append(StringUtil.capitalize(AttributeConstants.TIMER_KEY));
                onKeyPressContent.append("Start(); ");
                onKeyPressContent.append(onKeyPress);
                
                setOnKeyPress(onKeyPressContent.toString());
            }
		}
		
		AlignmentType alignment    = getAlignmentType();
		String        style        = getStyle();
		StringBuilder styleContent = new StringBuilder();
		
		if(alignment != null){
			styleContent.append("text-align: ");
			styleContent.append(alignment);
			styleContent.append(";");
			
			if(style.length() > 0){
				styleContent.append(" ");
				styleContent.append(style);
			}
			
			setStyle(styleContent.toString());
		}
	}
	
	protected void renderBody() throws Throwable{
	    super.renderBody();
	    
	    if(autoComplete){
	        BaseActionForm actionForm = systemController.getActionForm(getActionFormName());
	        
	        if(autoCompleteAction.equals(actionForm.getAction())){
        	    print("<div id=\"");
        	    print(getName());
        	    print(".");
        	    print(TaglibConstants.AUTO_COMPLETE_BOX_ID);
        	    print("\" class=\"");
        	    print(TaglibConstants.DEFAULT_AUTO_COMPLETE_BOX_STYLE_CLASS);
        	    println("\">");
    
        	    ScopeType autoCompleteDataScope = getAutoCompleteDataScopeType();
        	    
        	    if(autoCompleteDataScope != null){
        	        List autoCompleteDataValues = systemController.findAttribute(autoCompleteData, autoCompleteDataScope);
        	        
        	        if(autoCompleteDataValues != null && autoCompleteDataValues.size() > 0){
                        print("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"");
                        print(TaglibConstants.DEFAULT_PANEL_STYLE_CLASS);
                        println("\">");
                        
                        ModelInfo    autoCompleteModelInfo    = null; 
                        PropertyInfo autoCompletePropertyInfo = null;
                        Object       autoCompleteValue        = null;
                        
                        for(Object autoCompleteDataValue : autoCompleteDataValues){
                            autoCompleteModelInfo = ModelUtil.getModelInfo(autoCompleteDataValue.getClass());
                            
                            if(autoCompleteModelInfo != null){
                                autoCompletePropertyInfo = autoCompleteModelInfo.getPropertyInfo(autoCompleteLabelProperty);
                                
                                if(autoCompletePropertyInfo != null){
                                    autoCompleteValue = PropertyUtil.getProperty(autoCompleteDataValue, autoCompletePropertyInfo.getId());
                                    autoCompleteValue = PropertyUtil.format(autoCompleteValue, null, autoCompletePropertyInfo.getPattern(), autoCompletePropertyInfo.useAdditionalFormatting(), autoCompletePropertyInfo.getPrecision(), systemController.getCurrentLanguage());
                                }
                                else
                                    autoCompleteValue = autoCompleteDataValue;
                            }
                            else
                                autoCompleteValue = autoCompleteDataValue;
    
                            println("<tr>");
                            print("<td class=\"");
                            print(TaglibConstants.DEFAULT_AUTO_COMPLETE_BOX_ITEM_STYLE_CLASS);
                            print("\" onClick=\"setObjectValue('");
                            print(getName());
                            print("', '");
                            print(autoCompleteValue);
                            println("');\">");
                            
                            println(autoCompleteValue);
                            
                            println("</td>");
                            println("</tr>");
                        }
        	            
                        println("</table>");
        	        }
        	    }
        	    
        	    println("</div>");
            }
	    }
	}
	
	
	/**
	 * @see br.com.concepting.framework.web.taglibs.BasePropertyTag#clearAttributes()
	 */
	protected void clearAttributes(){
		super.clearAttributes();
		
		setAlignment("");
		setSize(0);
		setMaxlength(0);
		setReadOnly(false);
		setAutoCompleteLabelProperty("");
		setAutoCompleteData("");
		setAutoCompleteDataScope("");
		setAutoCompleteAction("");
		setAutoCompleteActionUpdateViews("");
		setAutoCompleteActionValidate(false);
		setAutoCompleteActionValidateProperties("");
	}
}