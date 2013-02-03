package br.com.concepting.framework.web.taglibs;

import java.util.List;

import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.processors.ExpressionProcessor;
import br.com.concepting.framework.util.types.PositionType;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;
import br.com.concepting.framework.web.types.ComponentType;

/**
 * Classe que define o componente visual para um grupo de seleção de opções.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class OptionsPropertyTag extends BaseOptionsPropertyTag{
	private Integer optionsPerRow         = 0;
	private String  optionLabelStyle      = "";
	private String  optionLabelStyleClass = "";
	private String  optionLabelProperty   = "";
	private String  onSelect              = "";

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
	public String getOptionLabelName(){
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
	    
	    String type = getType();
	    
	    if(type.length() == 0){
	        type = ComponentType.OPTIONS.toString();
	        
	        setType(type);
	    }
	    
	    String labelPosition = getLabelPosition();

		if(labelPosition.equals(PositionType.TOP.toString()) && !type.equals(ComponentType.LIST.toString())){
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
	}
	
	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderOpen()
	 */
	protected void renderOpen() throws Throwable{
		Boolean showLabel     = showLabel();
		String  type          = getType();
		String  labelPosition = getLabelPosition();
		
		if(labelPosition.equals(PositionType.TOP.toString()) && !type.equals(ComponentType.LIST.toString()))
			setShowLabel(false);

        renderLabelAttribute();
        renderPatternAttribute();
        renderDataAttributes();
        renderDataIndexesAttributes();

        super.renderOpen();
		
		setShowLabel(showLabel);

		if(!type.equals(ComponentType.LIST.toString())){
			print("<fieldset");

	        renderTooltip();

			println(">");

			if(labelPosition.equals(PositionType.TOP.toString()) && showLabel){
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
		
		PropertyInfo propertyInfo = getPropertyInfo();
		
		if(propertyInfo != null && propertyInfo.isCollection() && !getType().equals(ComponentType.LIST.toString())){
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
		if(!getType().equals(ComponentType.LIST.toString()))
			println("</fieldset>");

		super.renderClose();
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#renderLabelOpen()
	 */
	protected void renderLabelOpen() throws Throwable{
		if(!getType().equals(ComponentType.LIST.toString())){
			if(getLabelPosition().equals(PositionType.TOP.toString())){
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
		if(!getType().equals(ComponentType.LIST.toString())){
			if(getLabelPosition().equals(PositionType.TOP.toString()))
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
            renderOptions(dataValues);
	}

	/**
	 * Renderiza as opções de seleção do componente.
	 * 
	 * @param options Lista contendo as instâncias das opções de seleção.
	 * @throws Throwable
	 */
	private void renderOptions(List options) throws Throwable{
        PropertyInfo          propertyInfo        = getPropertyInfo();
        String                pattern             = getPattern();
	    Object                value               = getValue();
		Object                option              = null;
		BaseOptionPropertyTag optionTag           = null;
		Object                optionTagLabel      = null;
		OptionStateTag        optionState         = null;
		List<OptionStateTag>  optionsStates       = getOptionStates();
		Integer               optionsPerRowCont   = 1;
		String                expression          = "";
		Boolean               expressionResult    = false;
		ExpressionProcessor   expressionProcessor = getExpressionProcessor();

		println("<table class=\"panel\">");
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
    
    			if(getOptionLabelName().length() > 0)
    				optionTagLabel = PropertyUtil.getProperty(option, getOptionLabelName());
    			else
    				optionTagLabel = option;
    			
                if(!propertyInfo.getClass().equals(optionTagLabel.getClass()))
                    pattern = "";
                else
                    pattern = getPattern();
                
                optionTagLabel = PropertyUtil.format(optionTagLabel, getValueMapInstance(), pattern, useAdditionalFormatting(), systemController.getCurrentLanguage());
    
    			if(propertyInfo.isCollection())
    				optionTag = new CheckPropertyTag();
    			else
    				optionTag = new RadioPropertyTag();
    
    			optionTag.setPageContext(pageContext);
    			optionTag.setName(getName());
    			optionTag.setLabel(optionTagLabel.toString());
                optionTag.setLabelPosition(PositionType.RIGHT);
                
                if(optionState != null){
                    optionTag.setLabelStyle(optionState.getStyle());
                    optionTag.setLabelStyleClass(optionState.getStyleClass());
                }
                else
                    optionTag.setLabelStyle(optionLabelStyle);
                    optionTag.setLabelStyleClass(optionLabelStyleClass);
    
                if(propertyInfo.isModel() || propertyInfo.hasModel())
                    optionTag.setOptionIndex(cont1);
                else if(!propertyInfo.isEnum() && !propertyInfo.hasEnum()){
                    try{
                        option = PropertyUtil.getProperty(option, propertyInfo.getId());
                    }
                    catch(Throwable e){
                    }
                }
                
                optionTag.setOptionValue(option);
                optionTag.setValue(value);
    			optionTag.setOnClick(PropertyUtil.fillPropertiesInString(option, getOnClick()));
    			optionTag.setEnabled(isEnabled());
    			optionTag.setParent(this);
    			optionTag.doStartTag();
    			optionTag.doEndTag();
    
    			println("</td>");
    
    			if(optionsPerRow.equals(optionsPerRowCont)){
    				println("</tr>");
    				println("<tr>");
    				
    				optionsPerRowCont = 1;
    			}
    			else
    				optionsPerRowCont++;
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

	    setOptionsPerRow(0);
	    setOptionLabelStyle("");
        setOptionLabelStyleClass("");
	    setOptionLabelProperty("");
	    setOnSelect("");
    }
}