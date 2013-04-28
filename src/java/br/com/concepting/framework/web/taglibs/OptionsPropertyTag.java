package br.com.concepting.framework.web.taglibs;

import java.util.List;
import java.util.Locale;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.model.ObjectModel;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.processors.ExpressionProcessor;
import br.com.concepting.framework.processors.ExpressionProcessorUtil;
import br.com.concepting.framework.resource.PropertiesResource;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.Node;
import br.com.concepting.framework.util.types.ComponentType;
import br.com.concepting.framework.util.types.PositionType;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;

/**
 * Classe que define o componente visual para um grupo de seleção de opções.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class OptionsPropertyTag extends BaseOptionsPropertyTag{
    private String  optionResourceId                 = "";
	private Integer optionsPerRow                    = 0;
	private String  optionLabelStyle                 = "";
	private String  optionLabelStyleClass            = "";
	private String  optionLabelProperty              = "";
	private String  onSelect                         = "";
    private String  onSelectAction                   = "";
    private String  onSelectActionForward            = "";
    private String  onSelectActionForwardOnFail      = "";
    private String  onSelectActionUpdateViews        = "";
    private Boolean onSelectActionValidate           = false;
    private String  onSelectActionValidateProperties = "";
    
    /**
     * Retorna o identificador da propriedade para o label da opção.
     * 
     * @return String contendo o identificador da propriedade.
     */
    public String getOptionResourceId(){
        return optionResourceId;
    }

    /**
     * Define o identificador da propriedade para o label da opção.
     * 
     * @param optionResourceId String contendo o identificador da propriedade.
     */
    public void setOptionResourceId(String optionResourceId){
        this.optionResourceId = optionResourceId;
    }

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
	 * Retorna o número de opções por linha devem ser exibidas.
	 * 
	 * @return Valor inteiro contendo o número de opções.
	 */
	public Integer getOptionsPerRow(){
		return optionsPerRow;
	}

	/**
	 * Define o número de opções por linha devem ser exibidas.
	 * 
	 * @param optionsPerRow Valor inteiro contendo o número de opções.
	 */
	public void setOptionsPerRow(Integer optionsPerRow){
		this.optionsPerRow = optionsPerRow;
	}

	/**
	 * Retorna o estilo CSS para o label das opções .
	 * 
	 * @return String contendo o estilo CSS para o label.
	 */
	public String getOptionLabelStyle(){
		return optionLabelStyle;
	}

	/**
	 * Define o estilo CSS para o label das opções .
	 * 
	 * @param optionLabelStyle String contendo o estilo CSS para o label.
	 */
	public void setOptionLabelStyle(String optionLabelStyle){
		this.optionLabelStyle = optionLabelStyle;
	}

	/**
	 * Retorna o estilo CSS para o label das opções .
	 * 
	 * @return String contendo o estilo CSS para o label.
	 */
	public String getOptionLabelStyleClass(){
		return optionLabelStyleClass;
	}

	/**
	 * Define o estilo CSS para o label das opções .
	 * 
	 * @param optionLabelStyleClass String contendo o estilo CSS para o label.
	 */
	public void setOptionLabelStyleClass(String optionLabelStyleClass){
		this.optionLabelStyleClass = optionLabelStyleClass;
	}

	/**
	 * Retorna a propriedade do modelo de dados para o label da opção.
	 * 
	 * @return String contendo a propriedade do modelo de dados.
	 */
	public String getOptionLabelProperty(){
		return optionLabelProperty;
	}

	/**
	 * Define a propriedade do modelo de dados para o label da opção.
	 * 
	 * @param optionLabelProperty String contendo a propriedade do modelo de dados.
	 */
	public void setOptionLabelProperty(String optionLabelProperty){
		this.optionLabelProperty = optionLabelProperty;
	}

	/**
	 * Retorna o evento a ser executado quando uma opção for selecionada.
	 * 
	 * @return String contendo o evento a ser executado.
	 */
	public String getOnSelect(){
		return onSelect;
	}

	/**
	 * Define o evento a ser executado quando uma opção for selecionada.
	 * 
	 * @param onSelect String contendo o evento a ser executado.
	 */
	public void setOnSelect(String onSelect){
		this.onSelect = onSelect;
	}
 
	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#initialize()
	 */
	protected void initialize() throws Throwable{
	    if(optionsPerRow == 0)
	        optionsPerRow = TaglibConstants.DEFAULT_OPTIONS_PER_ROW;
	    
	    ComponentType componentType = getComponentType();
	    
	    if(componentType == null){
	        componentType = ComponentType.OPTIONS;
	        
	        setComponentType(componentType);
	    }
	    
	    PositionType labelPosition = getLabelPositionType();

		if(labelPosition == PositionType.TOP && componentType != ComponentType.LIST){
		    String labelStyleClass = getLabelStyleClass();
		    
			if(labelStyleClass.length() == 0){
			    labelStyleClass = TaglibConstants.DEFAULT_OPTIONS_LABEL_STYLE_CLASS;
			    
				setLabelStyleClass(labelStyleClass);
			}
		}
		
		String optionLabelStyleClass = getOptionLabelStyleClass();

		if(optionLabelStyleClass.length() == 0){
		    optionLabelStyleClass = TaglibConstants.DEFAULT_LABEL_STYLE_CLASS;
		    
			setOptionLabelStyleClass(optionLabelStyleClass);
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
                
                if(isForSearch())
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
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderOpen()
	 */
	protected void renderOpen() throws Throwable{
		Boolean       showLabel     = showLabel();
		ComponentType componentType = getComponentType();
		PositionType  labelPosition = getLabelPositionType();
		
		if(labelPosition == PositionType.TOP && componentType != ComponentType.LIST)
			setShowLabel(false);

        renderLabelAttribute();
        renderPatternAttribute();
        renderDataAttributes();
        renderDataIndexesAttributes();

        super.renderOpen();
		
		setShowLabel(showLabel);

		if(componentType != ComponentType.LIST){
			print("<fieldset");

	        renderTooltip();

			println(">");

			if(labelPosition == PositionType.TOP && showLabel){
				renderLabelOpen();
				renderLabelBody();
				renderLabelClose();
			}
		}
	}
	
	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseOptionsPropertyTag#renderDataAttributes()
	 */
	protected void renderDataAttributes() throws Throwable{
		super.renderDataAttributes();
		
		ComponentType componentType = getComponentType();
		PropertyInfo  propertyInfo  = getPropertyInfo();
		
		if(propertyInfo != null && propertyInfo.isCollection() && componentType != ComponentType.LIST){
			HiddenPropertyTag noItemSelectedTag = new HiddenPropertyTag();

			noItemSelectedTag.setPageContext(pageContext);
			noItemSelectedTag.setId(getId());
			noItemSelectedTag.setName(getName());
            noItemSelectedTag.setValue(getValue());
			noItemSelectedTag.doStartTag();
			noItemSelectedTag.doEndTag();
		}
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderClose()
	 */
	protected void renderClose() throws Throwable{
		if(getComponentType() != ComponentType.LIST)
			println("</fieldset>");

		super.renderClose();
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#renderLabelOpen()
	 */
	protected void renderLabelOpen() throws Throwable{
        if(getComponentType() != ComponentType.LIST){
			if(getLabelPositionType() == PositionType.TOP){
				print("<legend");
				
				String labelStyleClass = getLabelStyleClass();

                if(labelStyleClass.length() > 0){
                    print(" class=\"");
                    print(labelStyleClass);
                    print("\"");
                }
                
                String labelStyle = getLabelStyle();
                
                if(labelStyle.length() > 0){
					print(" style=\"");
					print(labelStyle);
					print(";\"");
				}

				println(">");
			}
			else
				super.renderLabelOpen();
		}
		else
			super.renderLabelOpen();
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#renderLabelClose()
	 */
	protected void renderLabelClose() throws Throwable{
        if(getComponentType() != ComponentType.LIST){
			if(getLabelPositionType() == PositionType.TOP)
				println("</legend>");
			else
				super.renderLabelClose();
		}
		else
			super.renderLabelClose();
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderBody()
	 */
	protected void renderBody() throws Throwable{
	    PropertyInfo propertyInfo = getPropertyInfo();
        List         dataValues   = getDataValues();
        
		if(propertyInfo != null && dataValues != null && dataValues.size() > 0)
			renderOptions();
		else{
			print("<span");
			
			String optionLabelStyleClass = getOptionLabelStyleClass();

            if(optionLabelStyleClass.length() > 0){
                print(" class=\"");
                print(optionLabelStyleClass);
                print("\"");
            }
            
            String optionLabelStyle = getOptionLabelStyle();

            if(optionLabelStyle.length() > 0){
				print(" style=\"");
				print(optionLabelStyle);
				print(";\"");
			}

			print(">");
			
			if(propertyInfo == null)
				print(getInvalidPropertyMessage());
			else
				print(getDataIsEmptyMessage());

			println("</span>");
		}
	}
	
	/**
	 * Renderiza as opções de seleção do componente.
	 * 
	 * @throws Throwable
	 */
	protected void renderOptions() throws Throwable{
    	List dataValues = getDataValues();

        if(dataValues != null && dataValues.size() > 0)
            renderOptions(dataValues, null, "", 0);
	}

	/**
	 * Renderiza as opções de seleção do componente.
	 * 
	 * @param options Lista contendo as instâncias das opções de seleção.
	 * @throws Throwable
	 */
    protected void renderOptions(List options, Object parent, String index, Integer level) throws Throwable{
        Locale                currentLanguage         = systemController.getCurrentLanguage();
        PropertyInfo          propertyInfo            = getPropertyInfo();
	    Object                value                   = getValue();
		Object                option                  = null;
		String                optionOnSelect          = "";
		BaseOptionPropertyTag optionTag               = null;
		Object                optionTagLabel          = null;
		StringBuilder         optionTagLabelBuffer    = new StringBuilder();
        PropertyInfo          optionLabelPropertyInfo = null;
        StringBuilder         optionIndex             = new StringBuilder();
		OptionStateTag        optionState             = null;
		Boolean               optionPerRowReached     = false;
        List                  optionChilds            = null;
		List<OptionStateTag>  optionsStates           = getOptionStates();
		Integer               optionsPerRowCont       = 1;
        PropertiesResource    resources               = null;
        
        if(optionResourceId.length() > 0)
            resources = getI18nResource(optionResourceId);
        else
            resources = getI18nResource();
        
        PropertiesResource    defaultResources    = getDefaultI18nResource();
        String                expression          = "";
		Boolean               expressionResult    = false;
		ExpressionProcessor   expressionProcessor = getExpressionProcessor();

        print("<table class=\"");
        print(TaglibConstants.DEFAULT_PANEL_STYLE_CLASS);
        println("\">");
		println("<tr>");

		for(int cont1 = 0 ; cont1 < options.size() ; cont1++){
			option = options.get(cont1);
			
			if(optionsStates != null){
    			expressionProcessor.setDeclaration(option);
    			
    			for(int cont2 = 0 ; cont2 < optionsStates.size() ; cont2++){
    				optionState      = optionsStates.get(cont2);
    				expression       = optionState.getExpression();
    				expressionResult = expressionProcessor.evaluate(expression); 
    				
    				if(expressionResult)
    					break;
    				
    				optionState = null;
    			}
			}
			
			if(optionState == null || !optionState.remove()){
    			println("<td>");
    
                if(optionLabelProperty.length() > 0){
                    optionLabelPropertyInfo = PropertyUtil.getPropertyInfo(option.getClass(), optionLabelProperty);
                    
                    if(optionLabelPropertyInfo != null){
                        optionTagLabel = PropertyUtil.getProperty(option, optionLabelProperty);
                        optionTagLabel = PropertyUtil.format(optionTagLabel, getValueMapInstance(), optionLabelPropertyInfo.getPattern(), optionLabelPropertyInfo.useAdditionalFormatting(), optionLabelPropertyInfo.getPrecision(), currentLanguage);
                    }
                    else{
                        if(propertyInfo != null && (propertyInfo.isModel() || propertyInfo.hasModel()))
                            optionTagLabel = option.toString();
                        else
                            optionTagLabel = PropertyUtil.format(option.toString(), getValueMapInstance(), getPattern(), useAdditionalFormatting(), getPrecision(), currentLanguage);
                    }
                }
                else{
                    if(propertyInfo != null && (propertyInfo.isModel() || propertyInfo.hasModel()))
                        optionTagLabel = option.toString();
                    else
                        optionTagLabel = PropertyUtil.format(option.toString(), getValueMapInstance(), getPattern(), useAdditionalFormatting(), getPrecision(), currentLanguage);
                }
                
                if(option instanceof ObjectModel && StringUtil.trim(optionTagLabel).length() == 0){
                    if(((ObjectModel)option).getType() == ComponentType.MENU_ITEM_SEPARATOR)
                        optionTagLabel = "&nbsp;";
                    else{
                        optionTagLabel = resources.getProperty(((ObjectModel)option).getName().concat(".").concat(AttributeConstants.LABEL_KEY), false);
                        
                        if(optionTagLabel == null)
                            optionTagLabel = defaultResources.getProperty(((ObjectModel)option).getName().concat(".").concat(AttributeConstants.LABEL_KEY), false);
                        
                        if(optionTagLabel == null)
                            optionTagLabel = ((ObjectModel)option).getName();
                    }
                }

                if(optionTagLabelBuffer.length() > 0)
                    optionTagLabelBuffer.delete(0, optionTagLabelBuffer.length());
                
                optionTagLabelBuffer.append(StringUtil.replicate("-", level * 3));
                optionTagLabelBuffer.append(" ");
                optionTagLabelBuffer.append(optionTagLabel);
    
    			if(propertyInfo.isCollection())
    				optionTag = new CheckPropertyTag();
    			else
    				optionTag = new RadioPropertyTag();
    
    			optionTag.setPageContext(pageContext);
    			optionTag.setName(getName());
    			optionTag.setLabel(optionTagLabel.toString());
                optionTag.setLabelPositionType(PositionType.RIGHT);
                
                if(optionState != null){
                    optionTag.setLabelStyle(optionState.getStyle());
                    optionTag.setLabelStyleClass(optionState.getStyleClass());
                }
                else{
                    optionTag.setLabelStyle(optionLabelStyle);
                    optionTag.setLabelStyleClass(optionLabelStyleClass);
                }
                    
                if(propertyInfo != null){
                    if(propertyInfo.isModel() || propertyInfo.hasModel()){
                        if(optionIndex.length() > 0)
                            optionIndex.delete(0, optionIndex.length());
                        
                        if(index.length() > 0){
                            optionIndex.append(index);
                            optionIndex.append("_");
                        }
    
                        optionIndex.append(cont1);
    
                        optionTag.setOptionIndex(optionIndex.toString());
                    }
                }
                
                optionOnSelect = PropertyUtil.fillPropertiesInString(option, onSelect, currentLanguage);
                optionOnSelect = ExpressionProcessorUtil.fillVariablesInString(optionOnSelect, currentLanguage);
                
                optionTag.setOptionValue(option);
                optionTag.setValue(value);
    			optionTag.setOnClick(onSelect);
    			optionTag.setOnClickAction(onSelectAction);
    			optionTag.setOnClickActionForward(onSelectActionForward);
    			optionTag.setOnClickActionForwardOnFail(onSelectActionForwardOnFail);
    			optionTag.setOnClickActionUpdateViews(onSelectActionUpdateViews);
    			optionTag.setOnClickActionValidate(onSelectActionValidate);
    			optionTag.setOnClickActionValidateProperties(onSelectActionValidateProperties);
    			optionTag.setEnabled(isEnabled());
    			optionTag.setParent(this);
    			optionTag.doStartTag();
    			optionTag.doEndTag();
    
    			println("</td>");
    
    			if(optionsPerRow.equals(optionsPerRowCont)){
    				println("</tr>");
    				println("<tr>");
    				
    				optionsPerRowCont = 1;
    				
    				optionPerRowReached = true;
    			}
    			else{
    				optionsPerRowCont++;
    				
    				optionPerRowReached = false;
    			}
    			
                if(option instanceof Node){
                    if(((Node)option).hasChildNodes()){
                        println("<td>");
                        
                        optionChilds = ((Node)option).getChildNodes();

                        renderOptions(optionChilds, option, optionIndex.toString(), level + 1);
                        
                        println("</td>");
                        
                        if(optionPerRowReached){
                            println("</tr>");
                            println("<tr>");
                        }
                    }
                }
			}
		}

		println("</tr>");
		println("</table>");
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BasePropertyTag#clearAttributes()
	 */
    protected void clearAttributes(){
    	super.clearAttributes();

    	setOptionResourceId("");
	    setOptionsPerRow(0);
	    setOptionLabelStyle("");
        setOptionLabelStyleClass("");
	    setOptionLabelProperty("");
	    setOnSelect("");
        setOnSelectAction("");
        setOnSelectActionForward("");
        setOnSelectActionForwardOnFail("");
        setOnSelectActionUpdateViews("");
        setOnSelectActionValidate(false);
        setOnSelectActionValidateProperties("");
    }
}