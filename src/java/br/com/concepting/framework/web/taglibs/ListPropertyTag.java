package br.com.concepting.framework.web.taglibs;

import java.util.List;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.processors.ExpressionProcessor;
import br.com.concepting.framework.resource.PropertiesResource;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.Node;
import br.com.concepting.framework.util.types.ComponentType;

/**
 * Classe que define o componente visual de uma lista de op��es para sele��o.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class ListPropertyTag extends OptionsPropertyTag{
	private Integer size                   = 0;
	private Boolean showFirstOption        = true;
	private String  firstOptionResourceKey = "";

    /**
     * Retorna o identificador da propriedade da op��o de informe de sele��o armazenada no arquivo de recursos.
     *
     * @return String contendo o identificador da propriedade.
     */
    public String getFirstOptionResourceKey(){
        return firstOptionResourceKey;
    }

    /**
     * Define o identificador da propriedade da op��o de informe de sele��o armazenada no arquivo de recursos.
     *
     * @param firstOptionResourceKey String contendo o identificador da propriedade.
     */
    public void setFirstOptionResourceKey(String firstOptionResourceKey){
        this.firstOptionResourceKey = firstOptionResourceKey;
    }

    /**
	 * Retorna o n�mero de op��es vis�veis.
	 * 
	 * @return Valor inteiro contendo o n�mero de op��es.
	 */
	public Integer getSize(){
		return size;
	}

	/**
	 * Define o n�mero de op��es vis�veis.
	 * 
	 * @param size Valor inteiro contendo o n�mero de op��es.
	 */
	public void setSize(Integer size){
		this.size = size;
	}
	
	/**
	 * Indica se a op��o de informe de sele��o deve ser exibida.
	 * 
	 * @return True/False.
	 */
    public Boolean isShowFirstOption(){
        return showFirstOption;
    }

    /**
     * Indica se a op��o de informe de sele��o deve ser exibida.
     * 
     * @return True/False.
     */
    public Boolean getShowFirstOption(){
        return isShowFirstOption();
    }

    /**
     * Define se a op��o de informe de sele��o deve ser exibida.
     * 
     * @param showFirstOption True/False.
     */
    public void setShowFirstOption(Boolean showFirstOption){
        this.showFirstOption = showFirstOption;
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
		
		if(showFirstOption){
    		if(propertyInfo != null){
    		    Integer size         = getSize();
    		    Boolean isCollection = propertyInfo.isCollection();
    		    
    			if(!isCollection && size == 0){
         			PropertiesResource resources   = getI18nResource();
         			String             resourceKey = "";
         			
         			if(firstOptionResourceKey.length() > 0){
         			    resources   = getI18nResource();
         			    resourceKey = firstOptionResourceKey;
         			}
         			else
         			    resourceKey = AttributeConstants.SELECT_AN_ITEM_KEY;
         
         			firstOptionTagLabel = StringUtil.trim(resources.getProperty(resourceKey));
    			}
    			else{
    		        List dataValues = getDataValues();
    		        
    		        if(dataValues == null || dataValues.size() == 0)
    	                firstOptionTagLabel = getDataIsEmptyMessage();
    			}
    		}
    		else
    		    firstOptionTagLabel = getInvalidPropertyMessage();
		}
		    
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
	 * Renderiza as op��es de sele��o do componente.
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
     * Renderiza as op��es de sele��o do componente.
	 * 
	 * @param options Lista contendo as op��es.
	 * @param parent Inst�ncia contendo a op��o pai.
     * @param index String contendo o identificador da posi��o.
	 * @param level Valor inteiro contendo o n�vel do posicionamento das op��es.
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
    				if(getOptionLabelProperty().length() > 0)
    					optionTagLabel = PropertyUtil.getProperty(option, getOptionLabelProperty());
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
    				
				    optionTagLabelBuffer.append(PropertyUtil.format(optionTagLabel, getValueMapInstance(), pattern, useAdditionalFormatting(), getPrecision(), systemController.getCurrentLanguage()));
    
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
	    setShowFirstOption(true);
	    setFirstOptionResourceKey("");
    }
}