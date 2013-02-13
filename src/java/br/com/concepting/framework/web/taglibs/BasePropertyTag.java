package br.com.concepting.framework.web.taglibs;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.jsp.tagext.Tag;

import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.model.types.ValidationType;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.processors.ExpressionProcessorUtil;
import br.com.concepting.framework.resource.PropertiesResource;
import br.com.concepting.framework.util.DateTimeUtil;
import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.constants.AttributeConstants;
import br.com.concepting.framework.util.constants.Constants;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.util.types.PositionType;
import br.com.concepting.framework.web.form.BaseActionForm;
import br.com.concepting.framework.web.form.helpers.ActionFormMessage;
import br.com.concepting.framework.web.form.types.ActionFormMessageType;
import br.com.concepting.framework.web.form.util.ActionFormMessageUtil;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;
import br.com.concepting.framework.web.types.ScopeType;

/**
 * Classe que define o estrutura básica para o componente vinculado a uma propriedade de um 
 * modelo de dados.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class BasePropertyTag extends BaseActionFormElementTag{
    private String                   valueMap                 = "";
    private ScopeType                valueMapScope            = null;
    private Map                      valueMapInstance         = null;
	private String                   onChange                 = "";
	private String                   onKeyPress               = "";
	private String                   onKeyUp                  = "";
	private String                   onKeyDown                = "";
    private Boolean                  focus                    = false;
	private String                   pattern                  = "";
	private Object                   value                    = null;
	private String                   validationStyle          = "";
	private String                   validationStyleClass     = "";
	private SearchPropertiesGroupTag searchPropertiesGroupTag = null;
	private String                   invalidPropertyMessage   = "";
    private String                   dataIsEmptyMessage       = "";
	private Boolean                  showValidationMessages   = false;
    private PropertyInfo             propertyInfo             = null;
    
    /**
     * Retorna o identificador do mapa de valores das opções de seleção.
     *
     * @return String contendo o identificador do mapa de valores.
     */
    public String getValueMap(){
        return valueMap;
    }

    /**
     * Define o identificador do mapa de valores das opções de seleção.
     *
     * @param valueMap String contendo o identificador do mapa de valores.
     */
    public void setValueMap(String valueMap){
        this.valueMap = valueMap;
    }

    /**
     * Retorna o escopo de armazenamento do mapa de valores das opções de seleção.
     *
     * @return String Instância que define o escopo de armazenamento.
     */
    public ScopeType getValueMapScope(){
        return valueMapScope;
    }

    /**
     * Define o escopo de armazenamento do mapa de valores das opções de seleção.
     *
     * @param valueMapScope Instância que define o escopo de armazenamento.
     */
    protected void setValueMapScope(ScopeType valueMapScope){
        this.valueMapScope = valueMapScope;
    }

    /**
     * Define o escopo de armazenamento do mapa de valores das opções de seleção.
     *
     * @param valueMapScope String que define o escopo de armazenamento.
     */
    public void setValueMapScope(String valueMapScope){
        if(valueMapScope.length() > 0)
            this.valueMapScope = ScopeType.valueOf(valueMapScope.toUpperCase());
        else
            this.valueMapScope = null;
    }

    /**
     * Retorna a instância contendo o mapa de valores das opções de seleção.
     *
     * @return Instância contendo o mapa de valores.
     */
    protected Map getValueMapInstance(){
        return valueMapInstance;
    }

    /**
     * Define a instância contendo o mapa de valores das opções de seleção.
     *
     * @param valueMapInstance Instância contendo o mapa de valores.
     */
    protected void setValueMapInstance(Map valueMapInstance){
        this.valueMapInstance = valueMapInstance;
    }
    
    /**
     * Retorna o estilo CSS para a mensagem de validação.
     * 
     * @return String contendo o estilo CSS.
     */
    public String getValidationStyle(){
        return validationStyle;
    }

    /**
     * Define o estilo CSS para a mensagem de validação.
     * 
     * @param validationStyle String contendo o estilo CSS.
     */
    public void setValidationStyle(String validationStyle){
        this.validationStyle = validationStyle;
    }

    /**
     * Retorna o estilo CSS para a mensagem de validação.
     * 
     * @return String contendo o estilo CSS.
     */
    public String getValidationStyleClass(){
        return validationStyleClass;
    }

    /**
     * Define o estilo CSS para a mensagem de validação.
     * 
     * @param validationStyleClass String contendo o estilo CSS.
     */
    public void setValidationStyleClass(String validationStyleClass){
        this.validationStyleClass = validationStyleClass;
    }

    /**
     * Retorna a mensagem quando não existem dados a serem exibidos.
     *
     * @return String contendo a mensagem.
     */
    protected String getDataIsEmptyMessage(){
        return dataIsEmptyMessage;
    }

    /**
     * Define a mensagem quando não existem dados a serem exibidos.
     *
     * @param dataIsEmptyMessage String contendo a mensagem.
     */
    protected void setDataIsEmptyMessage(String dataIsEmptyMessage){
        this.dataIsEmptyMessage = dataIsEmptyMessage;
    }

    /**
     * Retorna a instância contendo os atributos da propriedade do modelo de dados 
     * relacionada ao componente.
     * 
     * @return Instância contendo os atributos da propriedade do modelo de dados.
     */
    protected PropertyInfo getPropertyInfo(){
        return propertyInfo;
    }
    
    /**
     * Define a instância contendo os atributos da propriedade do modelo de dados 
     * relacionada ao componente.
     * 
     * @param propertyInfo Instância contendo os atributos da propriedade do modelo de dados.
     */
    protected void setPropertyInfo(PropertyInfo propertyInfo){
        this.propertyInfo = propertyInfo;
    }

    /**
	 * Indica se a mensagem de validação para esse componente deve ser exibida.
	 * 
	 * @return True/False.
	 */
	public Boolean showValidationMessages(){
    	return showValidationMessages;
    }

    /**
     * Indica se a mensagem de validação para esse componente deve ser exibida.
     * 
     * @return True/False.
     */
    public Boolean isShowValidationMessages(){
        return showValidationMessages();
    }
    
    /**
     * Indica se a mensagem de validação para esse componente deve ser exibida.
     * 
     * @return True/False.
     */
    public Boolean getShowValidationMessages(){
        return showValidationMessages();
    }

    /**
	 * Define se a mensagem de validação para esse componente deve ser exibida.
	 * 
	 * @param showValidationMessages True/False.
	 */
	public void setShowValidationMessages(Boolean showValidationMessages){
    	this.showValidationMessages = showValidationMessages;
    }

	/**
	 * Indica se o foco deve ser aplicado ao componente.
	 *
	 * @return True/False.
	 */
	public Boolean focus(){
     	return focus;
    }
	
	/**
	 * Define se o foco deve ser aplicado ao componente.
	 *
	 * @param focus True/False.
	 */
	public void setFocus(Boolean focus){
		this.focus = focus;
	}

	/**
	 * Retorna a instância do grupo de propriedades de pesquisa.
	 *
	 * @return Instância do grupo de propriedades de pesquisa.
	 */
	protected SearchPropertiesGroupTag getSearchPropertiesGroupTag(){
    	return searchPropertiesGroupTag;
    }

	/**
	 * Define a instância do grupo de propriedades de pesquisa.
	 *
	 * @param searchPropertiesGroupTag Instância do grupo de propriedades de pesquisa.
	 */
	protected void setSearchPropertiesGroupTag(SearchPropertiesGroupTag searchPropertiesGroupTag){
    	this.searchPropertiesGroupTag = searchPropertiesGroupTag;
    }

	/**
	 * Retorna a máscara de formatação para o componente.
	 * 
	 * @return String contendo a máscara de formatação.
	 */
	protected String getPattern(){
	    if(propertyInfo != null){
	        if(this.pattern.length() == 0){
			    String  pattern         = propertyInfo.getPattern();
                Integer precision       = propertyInfo.getPrecision();
                Locale  currentLanguage = systemController.getCurrentLanguage();
				
				if(pattern.length() == 0){
				    Boolean useAdditionalFormatting = useAdditionalFormatting();
				    
					if(propertyInfo.isDate()){
					    if(useAdditionalFormatting)
					        pattern = DateTimeUtil.getDefaultDateTimePattern(currentLanguage);
					    else
					        pattern = DateTimeUtil.getDefaultDatePattern(currentLanguage);
					}
					else if(propertyInfo.isNumber())
						pattern = NumberUtil.getDefaultPattern(propertyInfo.getClazz(), useAdditionalFormatting, precision);
				}
				
				this.pattern = pattern;
			}
		}
		
		return this.pattern;
	}

	/**
	 * Define a máscara de formatação para o componente.
	 * 
	 * @param pattern String contendo a máscara de formatação.
	 */
	public void setPattern(String pattern){
		this.pattern = pattern;
	}

	/**
	 * Retorna a instância contendo o valor do componente.
	 * 
	 * @return Instância contendo o valor do componente.
	 */
	public Object getValue(){
		return value;
	}

	/**
	 * Define a instância contendo o valor do componente.
	 * 
	 * @param value Instância contendo o valor do componente.
	 */
	public void setValue(Object value){
		this.value = value;
	}

	/**
	 * Retorna o evento a ser executado quando o valor do componente for alterado.
	 * 
	 * @return String contendo o evento a ser executado.
	 */
	public String getOnChange(){
		return onChange;
	}

	/**
	 * Define o evento a ser executado quando o valor do componente for alterado.
	 * 
	 * @param onChange String contendo o evento a ser executado.
	 */
	public void setOnChange(String onChange){
		this.onChange = onChange;
	}

	/**
	 * Retorna o evento a ser executando quando uma tecla for pressionada.
	 * 
	 * @return String contendo o evento a ser executado.
	 */
	public String getOnKeyPress(){
		return onKeyPress;
	}

	/**
	 * Define o evento a ser executando quando uma tecla for pressionada.
	 * 
	 * @param onKeyPress String contendo o evento a ser executado.
	 */
	public void setOnKeyPress(String onKeyPress){
		this.onKeyPress = onKeyPress;
	}

	/**
	 * Retorna o evento a ser executando quando uma tecla for pressionada.
	 * 
	 * @return String contendo o evento a ser executado.
	 */
	public String getOnKeyUp(){
		return onKeyUp;
	}

	/**
	 * Define o evento a ser executando quando uma tecla for pressionada.
	 * 
	 * @param onKeyUp String contendo o evento a ser executado.
	 */
	public void setOnKeyUp(String onKeyUp){
		this.onKeyUp = onKeyUp;
	}

	/**
	 * Retorna o evento a ser executando quando a tecla pressionada for liberada.
	 * 
	 * @return String contendo o evento a ser executado.
	 */
	public String getOnKeyDown(){
		return onKeyDown;
	}

	/**
	 * Define o evento a ser executando quando a tecla pressionada for liberada.
	 * 
	 * @param onKeyDown String contendo o evento a ser executado.
	 */
	public void setOnKeyDown(String onKeyDown){
		this.onKeyDown = onKeyDown;
	}

	/**
	 * Indica se o componente foi definido para ser uma propriedade de pesquisa.
	 * 
	 * @return True/False.
	 */
	protected Boolean isForSearch(){
		searchPropertiesGroupTag = (SearchPropertiesGroupTag)findAncestorWithClass(this, SearchPropertiesGroupTag.class);
		
		return (searchPropertiesGroupTag != null || getName().indexOf("search.") >= 0);
	}
	
	/**
	 * Retorna a mensagem que define que a propriedade é inválida.
	 * 
	 * @return String contendo a mensagem de propriedade inválida.
	 */
	protected String getInvalidPropertyMessage(){
		return invalidPropertyMessage;
	}

	/**
	 * Define a mensagem que define que a propriedade é inválida.
	 * 
	 * @param invalidPropertyMessage String contendo a mensagem de propriedade inválida.
	 */
	protected void setInvalidPropertyMessage(String invalidPropertyMessage){
     	this.invalidPropertyMessage = invalidPropertyMessage;
    }
	
	/**
	 * Indica se deve ser usado as configurações adicionais de formatação.
	 * 
	 * @return True/False.
	 */
	protected Boolean useAdditionalFormatting(){
	    if(propertyInfo != null)
	        return propertyInfo.useAdditionalFormatting();
	    
	    return false;
	}
	
	/**
	 * Retorna a quantidade de decimais de precisão.
	 * 
	 * @return Valor inteiro contendo a quantidade de decimais.
	 */
	protected Integer getPrecision(){
	    if(propertyInfo != null)
	        return propertyInfo.getPrecision();
	    
	    return Constants.DEFAULT_NUMBER_PRECISION;
	}

	/**
	 * Retorna o valor do componente formatado.
	 * 
	 * @return String contendo o valor formatado.
	 */
	protected String getFormattedValue(){
	    if(getPropertyInfo() != null || getValue() != null)
    		return PropertyUtil.format(getValue(), valueMapInstance, getPattern(), useAdditionalFormatting(), getPrecision(), systemController.getCurrentLanguage());
	    
	    return getInvalidPropertyMessage();
	}

	/**
	 * Renderiza o valor do componente.
	 * 
	 * @throws Throwable
	 */
	protected void renderValue() throws Throwable{
		print(" value=\"");
		print(getFormattedValue());
		print("\"");
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#initialize()
	 */
	protected void initialize() throws Throwable{
        String name = getName();
        
        setName(StringUtil.replaceAll(name, "search.", ""));
        
        if(validationStyleClass.length() == 0)
            validationStyleClass = TaglibConstants.DEFAULT_VALIDATION_LABEL_STYLE_CLASS;

        super.initialize();

        setName(name);

        String         actionForm = getActionForm();
        BaseActionForm form       = systemController.getActionForm(actionForm);
        
        if(valueMap.length() > 0 && valueMapScope != null){
            if(!valueMap.startsWith(actionForm)){
                StringBuilder propertyId = new StringBuilder();

                if(valueMapScope == ScopeType.FORM || valueMapScope == ScopeType.MODEL){
                    propertyId.append(actionForm);
                    propertyId.append(".");
    
                    if(valueMapScope == ScopeType.MODEL){
                        if(isForSearch())
                            propertyId.append("searchModel");
                        else
                            propertyId.append("model");
                        
                        propertyId.append(".");
                    }
                }
                
                propertyId.append(valueMap);
            
                valueMap = propertyId.toString();
            }
            
            valueMapInstance = systemController.findAttribute(valueMap, valueMapScope);
        }

        PropertiesResource resources = getDefaultI18nResource();
        
        dataIsEmptyMessage = StringUtil.trim(resources.getProperty(AttributeConstants.DATA_IS_EMPTY_KEY));

        if(propertyInfo == null){
    		invalidPropertyMessage = StringUtil.trim(resources.getProperty(AttributeConstants.INVALID_PROPERTY_KEY));
        
    		if(actionForm.length() > 0){
     			BaseModel      model = (form != null ? (isForSearch() ? form.getSearchModel() : form.getModel()) : null);
         
     			if(model != null){
    				ModelInfo modelInfo = ModelUtil.getModelInfo(model.getClass());
    				
     				if(modelInfo != null){
                        name         = StringUtil.replaceAll(name, "search.", "");
    				    propertyInfo = modelInfo.getPropertyInfo(name);
    
    				    if(propertyInfo != null){
    						if(!propertyInfo.isForSearch() && isForSearch()){
    						    propertyInfo = null;
    						    value        = null;
    						}
    						else{
    						    try{
    						        value = PropertyUtil.getProperty(model, name);
    						    }
    						    catch(Throwable e){
    						        propertyInfo = null;
    						        value        = null;
    						    }
    						}
    					}
     				}
     			}
    		}
	    }
		
		if(propertyInfo != null){
		    AlignmentType alignment = getAlignment();
		    
			if(alignment == null){
				if(propertyInfo.isNumber())
				    alignment = AlignmentType.RIGHT;
				else if(propertyInfo.isDate() || propertyInfo.isBoolean() || propertyInfo.isByteArray())
				    alignment = AlignmentType.CENTER;
				else
				    alignment = AlignmentType.LEFT;
				
				setAlignment(alignment);
			}
			
			String onBlur          = getOnBlur();
			String onFocus         = getOnFocus();
			String onChange        = getOnChange();
			String onClick         = getOnClick();
			String onMouseOut      = getOnMouseOut();
			String onMouseOver     = getOnMouseOver();
			String styleClass      = getStyleClass();
			String style           = getStyle();
			Locale currentLanguage = systemController.getCurrentLanguage();
    
			onBlur      = PropertyUtil.fillPropertiesInString(value, onBlur, currentLanguage);
    		onFocus     = PropertyUtil.fillPropertiesInString(value, onFocus, currentLanguage);
    		onChange    = PropertyUtil.fillPropertiesInString(value, onChange, currentLanguage);
    		onClick     = PropertyUtil.fillPropertiesInString(value, onClick, currentLanguage);
    		onKeyDown   = PropertyUtil.fillPropertiesInString(value, onKeyDown, currentLanguage);
    		onKeyUp     = PropertyUtil.fillPropertiesInString(value, onKeyUp, currentLanguage);
    		onKeyPress  = PropertyUtil.fillPropertiesInString(value, onKeyPress, currentLanguage);
    		onMouseOut  = PropertyUtil.fillPropertiesInString(value, onMouseOut, currentLanguage);
    		onMouseOver = PropertyUtil.fillPropertiesInString(value, onMouseOver, currentLanguage);
    		styleClass  = PropertyUtil.fillPropertiesInString(value, styleClass, currentLanguage);
    		style       = PropertyUtil.fillPropertiesInString(value, style, currentLanguage);
            onBlur      = ExpressionProcessorUtil.fillVariablesInString(onBlur, currentLanguage);
            onFocus     = ExpressionProcessorUtil.fillVariablesInString(onFocus, currentLanguage);
            onChange    = ExpressionProcessorUtil.fillVariablesInString(onChange, currentLanguage);
            onClick     = ExpressionProcessorUtil.fillVariablesInString(onClick, currentLanguage);
            onKeyDown   = ExpressionProcessorUtil.fillVariablesInString(onKeyDown, currentLanguage);
            onKeyUp     = ExpressionProcessorUtil.fillVariablesInString(onKeyUp, currentLanguage);
            onKeyPress  = ExpressionProcessorUtil.fillVariablesInString(onKeyPress, currentLanguage);
            onMouseOut  = ExpressionProcessorUtil.fillVariablesInString(onMouseOut, currentLanguage);
            onMouseOver = ExpressionProcessorUtil.fillVariablesInString(onMouseOver, currentLanguage);
            styleClass  = ExpressionProcessorUtil.fillVariablesInString(styleClass, currentLanguage);
            style       = ExpressionProcessorUtil.fillVariablesInString(style, currentLanguage);
            
            setOnBlur(onBlur);
            setOnFocus(onFocus);
            setOnChange(onChange);
            setOnClick(onClick);
            setOnKeyDown(onKeyDown);
            setOnKeyPress(onKeyPress);
            setOnMouseOut(onMouseOut);
            setOnMouseOver(onMouseOver);
            setStyleClass(styleClass);
            setStyle(style);

    		if(isForSearch()){
    		    if(!name.startsWith("search.")){
        		    name = "search.".concat(name);
    
        		    setName(name);
    		    }
    		    
    		    if(searchPropertiesGroupTag != null){
    		        searchPropertiesGroupTag.addSearchProperty(name);
    		    
                    StringBuilder onKeyPressContent = new StringBuilder(onKeyPress);
                
                    onKeyPressContent.append("if(getKeyPressed(event) == 13) ");
                    onKeyPressContent.append("document.getElementById('");
                    onKeyPressContent.append(searchPropertiesGroupTag.getSearchButtonTag().getName());
                    onKeyPressContent.append("').click();");
        
                    setOnKeyPress(onKeyPressContent.toString());
    		    }
    		}
		}
	}
	
	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#renderOpen()
	 */
	protected void renderOpen() throws Throwable{
	    renderPatternAttribute();
	    
	    super.renderOpen();
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderAttributes()
	 */
	protected void renderAttributes() throws Throwable{
		super.renderAttributes();

		String onKeyPress = StringUtil.trim(getOnKeyPress());
		
		if(onKeyPress.length() > 0){
			print(" onKeyPress=\"");
			print(onKeyPress);
			print("\"");
		}

		String onKeyDown = StringUtil.trim(getOnKeyDown());
		
		if(onKeyDown.length() > 0){
			print(" onKeyDown=\"");
			print(onKeyDown);
			print("\"");
		}
		
		String onKeyUp = StringUtil.trim(getOnKeyUp());

		if(onKeyUp.length() > 0){
			print(" onKeyUp=\"");
			print(onKeyUp);
			print("\"");
		}
		
		String onChange = StringUtil.trim(getOnChange());

		if(onChange.length() > 0){
			print(" onChange=\"");
			print(onChange);
			print("\"");
		}
	}
	
	protected void renderLabelAttribute() throws Throwable{
        Tag parent = getParent();
        
        if(getPropertyInfo() != null && (parent == null || (parent instanceof GridColumnTag) || !(parent instanceof BaseOptionsPropertyTag)))
            super.renderLabelAttribute();
	}
	
	/**
	 * Renderiza o atributo que define a máscara de formatação do componente visual.
	 * 
	 * @throws Throwable
	 */
	protected void renderPatternAttribute() throws Throwable{
        String pattern = getPattern();
        
        if(pattern.length() > 0 && isEnabled()){
            Tag parent = getParent();
            
            if(getPropertyInfo() != null && (parent == null || (parent instanceof GridColumnTag) || !(parent instanceof BaseOptionsPropertyTag))){
                StringBuilder tagName = new StringBuilder();
    
                tagName.append(getName());
                tagName.append(".");
                tagName.append(AttributeConstants.PATTERN_KEY);

                HiddenPropertyTag patternTag = new HiddenPropertyTag();
                
                patternTag.setPageContext(pageContext);
                patternTag.setName(tagName.toString());
                patternTag.setValue(pattern);
                patternTag.doStartTag();
                patternTag.doEndTag();
            }
        }
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderBody()
	 */
	protected void renderBody() throws Throwable{
		print("<input");

		renderAttributes();
		renderValue();

		println(">");
	}
	
	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#renderLabelBody()
	 */
	protected void renderLabelBody() throws Throwable{
	    PropertyInfo propertyInfo = getPropertyInfo();
	    
	    if(propertyInfo != null && !isForSearch()){
	        ValidationType validations[] = propertyInfo.getValidations();
	        
	        if(validations != null){
    	        for(ValidationType validation : validations){
    	            if(validation == ValidationType.REQUIRED){
    	                println("(*) ");
    	                
    	                break;
    	            }
    	        }
	        }
	    }
	    
	    super.renderLabelBody();
	}

	/**
	 * Exibe as mensagens de validação para o componente.
	 * 
	 * @throws Throwable
	 */
	protected void renderValidationMessages() throws Throwable{
		List<ActionFormMessage> validationMessages = actionFormMessageController.getValidationMessages(getName());
		
		if(validationMessages != null && validationMessages.size() > 0){
			println("</td>");
			println("</tr>");

			println("<tr>");
			
			PositionType labelPosition = getLabelPosition();
			
	        if(labelPosition == PositionType.LEFT)
	            println("<td></td>");
			
			print("<td");
			
			if(validationStyle.length() > 0){
			    print(" style=\"");
			    print(validationStyle);
			    print("\"");
			}
			    
			print(" class=\"");
			print(validationStyleClass);
			println("\">");
			
			PropertiesResource    resources         = getI18nResource();
			PropertiesResource    defaultResources  = getDefaultI18nResource();
			ActionFormMessage     validationMessage = null;
			ActionFormMessageType type              = null;
			String                key               = "";
			String                message           = "";
			Locale                currentLanguage   = systemController.getCurrentLanguage();
			StringBuilder         propertyId        = null;
			
			for(Integer cont = 0 ; cont < validationMessages.size() ; cont++){
				validationMessage = validationMessages.get(cont);
                key               = validationMessage.getKey();
                type              = validationMessage.getType();
                
                if(!validationMessage.displayed()){
                    if(propertyId == null)
                        propertyId = new StringBuilder();
                    else
                        propertyId.delete(0, propertyId.length());
                    
                    propertyId.append(type);
                    propertyId.append(".");
                    propertyId.append(key);
                    
                    message = resources.getProperty(propertyId.toString(), false);
                    
                    if(message == null)
                        message = StringUtil.trim(defaultResources.getProperty(propertyId.toString()));
                    
                    message = PropertyUtil.fillPropertiesInString(validationMessage, message, currentLanguage);
                    message = ActionFormMessageUtil.fillAttributesInString(validationMessage, message, currentLanguage);
    				
    				print("&#8226;&nbsp;");
        			print(message);
        			println("<br/>");

        			validationMessage.setDisplayed(true);
                }
			}
			
            if(labelPosition == PositionType.RIGHT)
                println("</td></td>");
		}
	}
	
	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#renderClose()
	 */
	protected void renderClose() throws Throwable{
		if(showValidationMessages())
			renderValidationMessages();

		super.renderClose();
		
		if(focus()){
			StringBuilder focusScriptContent = new StringBuilder();
			
			focusScriptContent.append("focusObject('");
			focusScriptContent.append(getName());
			focusScriptContent.append("');");
			
            ScriptTag focusScriptTag = new ScriptTag();

            focusScriptTag.setPageContext(pageContext);
			focusScriptTag.setContent(focusScriptContent.toString());
			focusScriptTag.doStartTag();
			focusScriptTag.doEndTag();
		}
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#clearAttributes()
	 */
	protected void clearAttributes(){
		super.clearAttributes();
		
        setValueMap("");
        setValueMapScope("");
        setValueMapInstance(null);
		setOnChange("");
		setOnKeyPress("");
		setOnKeyDown("");
		setOnKeyUp("");
		setPattern("");
		setFocus(false);
		setValue(null);
		setValidationStyle("");
		setValidationStyleClass("");
        setInvalidPropertyMessage("");
		setShowValidationMessages(false);
		setSearchPropertiesGroupTag(null);
		setPropertyInfo(null);
	}
}