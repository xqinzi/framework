package br.com.concepting.framework.ui.taglibs;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.controller.form.BaseActionForm;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.ui.constants.TaglibConstants;
import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.util.types.ComponentType;
import br.com.concepting.framework.util.types.ScopeType;
 
/**
 * Classe que define o componente visual text (entrada de texto com ou sem mascaramento).
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class TextPropertyTag extends BasePropertyTag{
	private Integer size                                          = 0;
	private Integer maxlength                                     = 0;
	private Boolean readOnly                                      = false;
	private Boolean autoComplete                                  = false;
	private String  autoCompleteLabelProperty                     = "";
	private String  autoCompleteData                              = "";
	private String  autoCompleteDataScope                         = "";
	private String  autoCompleteAction                            = "";
	private String  autoCompleteActionUpdateViews                 = "";
	private Boolean autoCompleteActionValidate                    = false;
	private String  autoCompleteActionValidateProperties          = "";
    private String  autoCompleteSelectionAction                   = "";
    private String  autoCompleteSelectionActionUpdateViews        = "";
    private Boolean autoCompleteSelectionActionValidate           = false;
    private String  autoCompleteSelectionActionValidateProperties = "";
    
	public String getAutoCompleteSelectionAction(){
        return autoCompleteSelectionAction;
    }

    public void setAutoCompleteSelectionAction(String autoCompleteSelectionAction){
        this.autoCompleteSelectionAction = autoCompleteSelectionAction;
    }

    public String getAutoCompleteSelectionActionUpdateViews(){
        return autoCompleteSelectionActionUpdateViews;
    }

    public void setAutoCompleteSelectionActionUpdateViews(String autoCompleteSelectionActionUpdateViews){
        this.autoCompleteSelectionActionUpdateViews = autoCompleteSelectionActionUpdateViews;
    }

    public Boolean getAutoCompleteSelectionActionValidate(){
        return autoCompleteSelectionActionValidate;
    }

    public void setAutoCompleteSelectionActionValidate(Boolean autoCompleteSelectionActionValidate){
        this.autoCompleteSelectionActionValidate = autoCompleteSelectionActionValidate;
    }

    public String getAutoCompleteSelectionActionValidateProperties(){
        return autoCompleteSelectionActionValidateProperties;
    }

    public void setAutoCompleteSelectionActionValidateProperties(String autoCompleteSelectionActionValidateProperties){
        this.autoCompleteSelectionActionValidateProperties = autoCompleteSelectionActionValidateProperties;
    }

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
     * Retorna o escopo de armazenamento dos dados do autocomplete.
     * 
     * @return Constante que define o escopo de armazenamento.
     */
    protected void setAutoCompleteDataScopeType(ScopeType scopeType){
        if(scopeType != null)
            this.autoCompleteDataScope = scopeType.toString();
        else
            this.autoCompleteDataScope = "";
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
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#renderAttributes()
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
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#initialize()
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
                
                content.append("\t");
                content.append(name);
                content.append(StringUtil.capitalize(AttributeConstants.AUTO_COMPLETE_KEY));
                content.append(StringUtil.capitalize(AttributeConstants.TIMER_KEY));
                content.append("Stop();");
                content.append(StringUtil.getLineBreak());
                content.append(StringUtil.getLineBreak());
                
                content.append("\t");
                content.append(name);
                content.append(StringUtil.capitalize(AttributeConstants.AUTO_COMPLETE_KEY));
                content.append(StringUtil.capitalize(AttributeConstants.TIMER_KEY));
                content.append(" = setTimeout(\"");
                content.append(name);
                content.append(StringUtil.capitalize(AttributeConstants.AUTO_COMPLETE_KEY));
                content.append(StringUtil.capitalize(AttributeConstants.TIMER_KEY));
                content.append("Process()\", 1000);");
                content.append(StringUtil.getLineBreak());
                
                content.append("}");
                content.append(StringUtil.getLineBreak());
                content.append(StringUtil.getLineBreak());
                
                content.append("function ");
                content.append(name);
                content.append(StringUtil.capitalize(AttributeConstants.AUTO_COMPLETE_KEY));
                content.append(StringUtil.capitalize(AttributeConstants.TIMER_KEY));
                content.append("Process(){");
                content.append(StringUtil.getLineBreak());
                
                content.append("\tdocument.");
                content.append(actionFormName);
                content.append(".");
                content.append(AttributeConstants.ACTION_KEY);
                content.append(".value = \"");
                content.append(autoCompleteAction);
                content.append("\";");
                content.append(StringUtil.getLineBreak());
                
                if(autoCompleteActionUpdateViews.length() > 0){
                    content.append("\tdocument.");
                    content.append(actionFormName);
                    content.append(".");
                    content.append(AttributeConstants.UPDATE_VIEWS_KEY);
                    content.append(".value = \"");
                    content.append(autoCompleteActionUpdateViews);
                    content.append("\";");
                    content.append(StringUtil.getLineBreak());
                }
                
                if(autoCompleteActionValidate){
                    content.append("\tdocument.");
                    content.append(actionFormName);
                    content.append(".");
                    
                    if(isForSearch())
                        content.append(AttributeConstants.VALIDATE_SEARCH_MODEL_KEY);
                    else
                        content.append(AttributeConstants.VALIDATE_MODEL_KEY);
                    
                    content.append(".value = \"");
                    content.append(autoCompleteActionValidate);
                    content.append("\";");
                    content.append(StringUtil.getLineBreak());
                    
                    if(autoCompleteActionValidateProperties.length() > 0){
                        content.append("\tdocument.");
                        content.append(actionFormName);
                        content.append(".");
                        content.append(AttributeConstants.VALIDATE_PROPERTIES_KEY);
                        content.append(".value = \"");
                        content.append(autoCompleteActionValidateProperties);
                        content.append("\";");
                        content.append(StringUtil.getLineBreak());
                    }
                }
                
                content.append(StringUtil.getLineBreak());
                content.append("\t");
                content.append(name);
                content.append(StringUtil.capitalize(AttributeConstants.AUTO_COMPLETE_KEY));
                content.append(StringUtil.capitalize(AttributeConstants.TIMER_KEY));
                content.append("Stop();");
                content.append(StringUtil.getLineBreak());
                content.append(StringUtil.getLineBreak());

                content.append("\tsubmitForm(document.");
                content.append(actionFormName);
                content.append(");");
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
            String         name       = getName();
	        BaseActionForm actionForm = systemController.getActionForm(getActionFormName());
	        
	        if(autoCompleteAction.equals(actionForm.getAction())){
                ScopeType autoCompleteDataScopeType = getAutoCompleteDataScopeType();

                if(autoCompleteDataScopeType == null){
                    autoCompleteDataScopeType = ScopeType.FORM;
                    
                    setAutoCompleteDataScopeType(autoCompleteDataScopeType);
                }

                if(autoCompleteData.length() > 0){
                    String actionFormName = getActionFormName();

                    if(!autoCompleteData.startsWith(actionFormName)){
                        StringBuilder propertyId = new StringBuilder();
                        
                        if(autoCompleteDataScopeType == ScopeType.FORM || autoCompleteDataScopeType == ScopeType.MODEL){
                            propertyId.append(actionFormName);
                            propertyId.append(".");
                            
                            if(autoCompleteDataScopeType == ScopeType.MODEL){
                                if(isForSearch())
                                    propertyId.append(AttributeConstants.SEARCH_MODEL_KEY);
                                else
                                    propertyId.append(AttributeConstants.MODEL_KEY);
                                
                                propertyId.append(".");
                            }
                        }
                        
                        propertyId.append(autoCompleteData);
                    
                        autoCompleteData = propertyId.toString();
                    }
                    
                    List autoCompleteDataValues = systemController.findAttribute(autoCompleteData, autoCompleteDataScopeType);
    	        
        	        if(autoCompleteDataValues != null && autoCompleteDataValues.size() > 0){
                        print("<div id=\"");
                        print(name);
                        print(".");
                        print(TaglibConstants.AUTO_COMPLETE_BOX_ID);
                        print("\" class=\"");
                        print(TaglibConstants.DEFAULT_AUTO_COMPLETE_BOX_STYLE_CLASS);
                        println("\">");
            
                        print("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"");
                        print(TaglibConstants.DEFAULT_PANEL_STYLE_CLASS);
                        println("\">");
                        
                        ModelInfo                autoCompleteModelInfo          = null; 
                        Collection<PropertyInfo> autoCompletePropertiesInfo     = null;
                        PropertyInfo             autoCompletePropertyInfo       = null;
                        Object                   autoCompletePropertyLabelValue = null;
                        Object                   autoCompletePropertyValue      = null;
                        Integer                  cont                           = 0;
                        Integer                  pos                            = name.lastIndexOf(".");
                        String                   autoCompletePropertyPrefix     = (pos >= 0 ? name.substring(0, pos) : "");
                        
                        for(Object autoCompleteDataValue : autoCompleteDataValues){
                            autoCompleteModelInfo = ModelUtil.getModelInfo(autoCompleteDataValue.getClass());
                            
                            if(autoCompleteModelInfo != null){
                                if(autoCompleteLabelProperty.length() > 0){
                                    autoCompletePropertyInfo = autoCompleteModelInfo.getPropertyInfo(autoCompleteLabelProperty);
                                    
                                    if(autoCompletePropertyInfo != null){
                                        autoCompletePropertyLabelValue = PropertyUtil.getProperty(autoCompleteDataValue, autoCompletePropertyInfo.getId());
                                        autoCompletePropertyLabelValue = PropertyUtil.format(autoCompletePropertyLabelValue, null, autoCompletePropertyInfo.getPattern(), autoCompletePropertyInfo.useAdditionalFormatting(), autoCompletePropertyInfo.getPrecision(), systemController.getCurrentLanguage());
                                    }
                                    else
                                        autoCompletePropertyLabelValue = autoCompleteDataValue;
                                }
                                else
                                    autoCompletePropertyLabelValue = autoCompleteDataValue;
                            }
                            else
                                autoCompletePropertyLabelValue = autoCompleteDataValue;
    
                            println("<tr>");
                            print("<td class=\"");
                            print(TaglibConstants.DEFAULT_AUTO_COMPLETE_BOX_ITEM_STYLE_CLASS);
                            print("\" onClick=\"");
                            
                            if(autoCompleteModelInfo != null){
                                autoCompletePropertiesInfo = autoCompleteModelInfo.getPropertiesInfo();
                                cont                       = 0;
                                
                                for(PropertyInfo autoCompletePropertyInfoItem : autoCompletePropertiesInfo){
                                    if((autoCompletePropertyPrefix.startsWith("search.") && autoCompletePropertyInfoItem.isForSearch()) || (!autoCompletePropertyPrefix.startsWith("search.") && !autoCompletePropertyInfoItem.isForSearch())){
                                        if(cont > 0)
                                            print(" ");
                                            
                                        print("setObjectValue('");
                                        
                                        if(autoCompletePropertyPrefix.length() > 0){
                                            print(autoCompletePropertyPrefix);
                                            print(".");
                                        }
    
                                        print(autoCompletePropertyInfoItem.getId());
                                        print("', '");
                                        
                                        autoCompletePropertyValue = PropertyUtil.getProperty(autoCompleteDataValue, autoCompletePropertyInfoItem.getId());
                                        
                                        print(autoCompletePropertyValue);
                                        
                                        print("');");
                                        
                                        cont++;
                                    }
                                }
                            }
                            else{
                                print("setObjectValue('");
                                print(name);
                                print("', '");
                                print(autoCompleteDataValue);
                                print("');");
                            }
                                
                            print(" showHideAutoCompleteBox('");
                            print(name);
                            print("');");
                            
                            if(autoCompleteSelectionAction.length() > 0){
                                print(" document.");
                                print(actionFormName);
                                print(".");
                                print(AttributeConstants.ACTION_KEY);
                                print(".value = '");
                                print(autoCompleteSelectionAction);
                                print("';");
                                
                                if(autoCompleteSelectionActionUpdateViews.length() > 0){
                                    print(" document.");
                                    print(actionFormName);
                                    print(".");
                                    print(AttributeConstants.UPDATE_VIEWS_KEY);
                                    print(".value = '");
                                    print(autoCompleteSelectionActionUpdateViews);
                                    print("';");
                                }
                                
                                if(autoCompleteSelectionActionValidate){
                                    print(" document.");
                                    print(actionFormName);
                                    print(".");
                                    
                                    if(isForSearch())
                                        print(AttributeConstants.VALIDATE_SEARCH_MODEL_KEY);
                                    else
                                        print(AttributeConstants.VALIDATE_MODEL_KEY);
                                    
                                    print(".value = '");
                                    print(autoCompleteSelectionActionValidate);
                                    print("';");
                                    
                                    if(autoCompleteSelectionActionValidateProperties.length() > 0){
                                        print(" document.");
                                        print(actionFormName);
                                        print(".");
                                        print(AttributeConstants.VALIDATE_PROPERTIES_KEY);
                                        print(".value = '");
                                        print(autoCompleteActionValidateProperties);
                                        print("';");
                                    }
                                }

                                print(" submitForm(document.");
                                print(actionFormName);
                                print(");");
                            }
                            
                            println("\">");
                            
                            print("&nbsp;");
                            print(autoCompletePropertyLabelValue);
                            println("&nbsp;");
                            
                            println("</td>");
                            println("</tr>");
                        }
        	            
                        println("</table>");

                        println("</div>");
                        
                        StringBuilder autoCompleteBoxScriptContent = new StringBuilder();
                        
                        autoCompleteBoxScriptContent.append("showHideAutoCompleteBox(\"");
                        autoCompleteBoxScriptContent.append(name);
                        autoCompleteBoxScriptContent.append("\");");
                        
                        ScriptTag scriptTag = new ScriptTag();
                        
                        scriptTag.setPageContext(pageContext);
                        scriptTag.setContent(autoCompleteBoxScriptContent.toString());
                        scriptTag.doStartTag();
                        scriptTag.doEndTag();
            	    }
                }
            }
	    }
	}
	
	
	/**
	 * @see br.com.concepting.framework.ui.taglibs.BasePropertyTag#clearAttributes()
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