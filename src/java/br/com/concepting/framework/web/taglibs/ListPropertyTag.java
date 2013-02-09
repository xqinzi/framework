package br.com.concepting.framework.web.taglibs;

import java.util.List;

import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.processors.ExpressionProcessor;
import br.com.concepting.framework.resource.PropertiesResource;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.constants.AttributeConstants;
import br.com.concepting.framework.util.helpers.Node;
import br.com.concepting.framework.web.types.ComponentType;

/**
 * Classe que define o componente visual de uma lista de opções para seleção.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class ListPropertyTag extends OptionsPropertyTag{
	private Integer size = 0;

    /**
	 * Retorna o número de opções visíveis.
	 * 
	 * @return Valor inteiro contendo o número de opções.
	 */
	public Integer getSize(){
		return size;
	}

	/**
	 * Define o número de opções visíveis.
	 * 
	 * @param size Valor inteiro contendo o número de opções.
	 */
	public void setSize(Integer size){
		this.size = size;
	}
	
	/**
	 * @see br.com.concepting.framework.web.taglibs.OptionsPropertyTag#initialize()
	 */
	protected void initialize() throws Throwable{
        setComponentType(ComponentType.LIST);

        super.initialize();
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderAttributes()
	 */
	protected void renderAttributes() throws Throwable{
		super.renderAttributes();
		
		PropertyInfo propertyInfo = getPropertyInfo();

		if(propertyInfo != null && propertyInfo.isCollection())
			print(" multiple");
		
		if(!isEnabled())
			print(" disabled");

		Integer size = getSize();
		
		if(size > 0){
			print(" size=\"");
			print(size);
			print("\"");
		}
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderOpen()
	 */
	protected void renderOpen() throws Throwable{
		super.renderOpen();

		print("<select");

		renderAttributes();

		println(">");
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderBody()
	 */
	protected void renderBody() throws Throwable{
		String       firstOptionTagLabel = "";
		PropertyInfo propertyInfo        = getPropertyInfo();
		
		if(propertyInfo != null){
		    Integer size         = getSize();
		    Boolean isCollection = propertyInfo.isCollection();
		    
			if(!isCollection && size == 0){
     			PropertiesResource resources = getDefaultI18nResource();
     
     			firstOptionTagLabel = StringUtil.trim(resources.getProperty(AttributeConstants.SELECT_AN_ITEM_KEY));
			}
			else{
		        List dataValues = getDataValues();
		        
		        if(dataValues == null || dataValues.size() == 0)
	                firstOptionTagLabel = getDataIsEmptyMessage();
			}
		}
		else
		    firstOptionTagLabel = getInvalidPropertyMessage();
		    
		if(firstOptionTagLabel.length() > 0){
     		ListOptionPropertyTag firstOptionTag = new ListOptionPropertyTag();
     		
     		firstOptionTag.setPageContext(pageContext);
     		firstOptionTag.setPropertyInfo(propertyInfo);
     		firstOptionTag.setName(getName());
     		firstOptionTag.setLabel(firstOptionTagLabel);
     		firstOptionTag.setValue("");
     		firstOptionTag.setParent(this);
     		firstOptionTag.doStartTag();
     		firstOptionTag.doEndTag();
		}
		
		renderOptions();
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderClose()
	 */
	protected void renderClose() throws Throwable{
		println("</select>");

		super.renderClose();
	}
	
	/**
	 * Renderiza as opções de seleção do componente.
	 * 
	 * @throws Throwable
	 */
	protected void renderOptions() throws Throwable{
	    PropertyInfo propertyInfo = getPropertyInfo();
		List         dataValues   = getDataValues();
		
    	if(propertyInfo != null && dataValues != null && dataValues.size() > 0)
    	    renderOptions(dataValues, null, "", 0);
	}
	
	/**
     * Renderiza as opções de seleção do componente.
	 * 
	 * @param options Lista contendo as opções.
	 * @param parent Instância contendo a opção pai.
     * @param index String contendo o identificador da posição.
	 * @param level Valor inteiro contendo o nível do posicionamento das opções.
	 * @throws Throwable
	 */
	protected void renderOptions(List options, Object parent, String index, Integer level) throws Throwable{
        PropertyInfo          propertyInfo         = getPropertyInfo();
        String                pattern              = getPattern();
	    Object                value                = getValue();
		Object                option               = null;         
		ListOptionPropertyTag optionTag            = null;
		Object                optionTagLabel       = null;
		StringBuilder         optionTagLabelBuffer = new StringBuilder();
		StringBuilder         optionIndex          = new StringBuilder();
		OptionStateTag        optionState          = null;
        List                  optionChilds         = null;
		List<OptionStateTag>  optionsStates        = getOptionStates();
		String                expression           = "";
		Boolean               expressionResult     = false;
		ExpressionProcessor   expressionProcessor  = getExpressionProcessor();

		for(int cont1 = 0 ; cont1 < options.size() ; cont1++){
			option = options.get(cont1);
			
			if(optionsStates != null && options.size() > 0){
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
    			if(!(option instanceof Node) || ((parent == null && ((Node)option).getParent() == null) || (parent != null && parent.equals(((Node)option).getParent())))){
    				if(getOptionLabelName().length() > 0)
    					optionTagLabel = PropertyUtil.getProperty(option, getOptionLabelName());
    				else
    					optionTagLabel = option;
    
    				if(optionTagLabelBuffer.length() > 0)
    				    optionTagLabelBuffer.delete(0, optionTagLabelBuffer.length());
    				
    				optionTagLabelBuffer.append(StringUtil.replicate("-", level * 3));
    				optionTagLabelBuffer.append(" ");
    				
    				if(!propertyInfo.getClass().equals(optionTagLabel.getClass()))
    				    pattern = "";
    				else
    				    pattern = getPattern();
    				
				    optionTagLabelBuffer.append(PropertyUtil.format(optionTagLabel, getValueMapInstance(), pattern, useAdditionalFormatting(), systemController.getCurrentLanguage()));
    
    				optionTag = new ListOptionPropertyTag();
    				optionTag.setPageContext(pageContext);
                    optionTag.setPropertyInfo(propertyInfo);
    				optionTag.setName(getName());
    				optionTag.setLabel(optionTagLabelBuffer.toString());
    				
    				if(optionState != null){
                        optionTag.setStyle(optionState.getStyle());
    				    optionTag.setStyleClass(optionState.getStyleClass());
    				}
    
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
                    else if(!propertyInfo.isEnum() && !propertyInfo.hasEnum()){
                        try{
                            option = PropertyUtil.getProperty(option, propertyInfo.getId());
                        }
                        catch(Throwable e){
                        }
                    }
    				
    				optionTag.setOptionValue(option);
    				optionTag.setValue(value);
    				optionTag.setEnabled(isEnabled());
    				optionTag.setParent(this);
    				optionTag.doStartTag();
    				optionTag.doEndTag();
    
    				if(option instanceof Node){
    					if(((Node)option).hasChildNodes()){
    						optionChilds = ((Node)option).getChildNodes();
    
    						renderOptions(optionChilds, option, optionIndex.toString(), level + 1);
    					}
    				}
    			}
			}
		}
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.OptionsPropertyTag#clearAttributes()
	 */
    protected void clearAttributes(){
	    super.clearAttributes();
	     
	    setSize(0);
    }
}