package br.com.concepting.framework.web.taglibs;

import java.util.Collection;
import java.util.List;

import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.util.interfaces.IEnum;

/**
 * Classe que define a estrutura b�sica para o componente visual de uma op��o de sele��o.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class BaseOptionPropertyTag extends BaseOptionsPropertyTag{
	private Boolean selected    = false;
	private Object  optionValue = null;
	private String  optionIndex = "";

	/**
	 * Retorna a inst�ncia contendo o valor da op��o.
	 * 
	 * @return Inst�ncia contendo o valor da op��o.
	 */
	public Object getOptionValue(){
		return optionValue;
	}

	/**
	 * Define a inst�ncia contendo o valor da op��o.
	 * 
	 * @param optionValue Inst�ncia contendo o valor da op��o.
	 */
	public void setOptionValue(Object optionValue){
		this.optionValue = optionValue;
	}

	/**
	 * Retorna o �ndice da op��o.
	 * 
	 * @return String contendo o �ndice.
	 */
	protected String getOptionIndex(){
		return optionIndex;
	}
	
    /**
     * Define o �ndice da op��o.
     * 
     * @param optionIndex Valor inteiro contendo o �ndice.
     */
	protected void setOptionIndex(Integer optionIndex){
	    if(optionIndex != null)
	        this.optionIndex = optionIndex.toString();
	    else
	        this.optionIndex = "";
	}

    /**
     * Define o �ndice da op��o.
     * 
     * @param optionIndex String contendo o �ndice.
     */
	protected void setOptionIndex(String optionIndex){
		this.optionIndex = optionIndex;
	}
	
	/**
	 * Indica se a op��o est� selecionada.
	 * 
	 * @return True/False.
	 */
	protected Boolean isSelected(){
		return selected;
	}

	/**
	 * Define se a op��o est� selecionada.
	 * 
	 * @param checked True/False.
	 */
	protected void setSelected(Boolean selected){
		this.selected = selected;
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BasePropertyTag#getFormattedValue()
	 */
	protected String getFormattedValue(){
		if(optionIndex.length() > 0){
			StringBuilder buffer = new StringBuilder();

			buffer.append("objectId{");
			buffer.append(optionIndex);
			buffer.append("}");

			return buffer.toString();
		}

		Object       optionValue  = getOptionValue();
		PropertyInfo propertyInfo = getPropertyInfo();
		
		if(optionValue != null){
			if(propertyInfo != null){
                if(propertyInfo.isEnum()){
                    IEnum constant = (IEnum)optionValue;
                    
                    optionValue = constant.getKey();
                }
                else if(!propertyInfo.isModel() && !propertyInfo.hasModel()){
		            try{
		                String propertyId = propertyInfo.getId();
		            
		                optionValue = PropertyUtil.getProperty(optionValue, propertyId);
		            }
		            catch(Throwable e){
		            }
				}
			}
			
			return PropertyUtil.format(optionValue, getValueMapInstance(), getPattern(), useAdditionalFormatting(), systemController.getCurrentLanguage());
		}

		return super.getFormattedValue();
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#initialize()
	 */
	protected void initialize() throws Throwable{
		super.initialize();

		PropertyInfo propertyInfo = getPropertyInfo();
		
		if(propertyInfo != null){
            Object value = getValue();
    
            if(value != null){
    	        Object optionValue = getOptionValue();
    
                if(optionValue instanceof IEnum && !(value instanceof IEnum)){
                    IEnum enumInstance = (IEnum)optionValue;
                    
                    setSelected(value.equals(enumInstance.getKey()));
                }
                else{
                    if(propertyInfo.isCollection()){
                        if(optionValue != null){
                            Collection values  = (Collection)value;
        
                            setSelected(values.contains(optionValue));
                        }
                    }
                    else if(propertyInfo.isBoolean())
                        setSelected(Boolean.parseBoolean(value.toString()));
                    else if(optionValue instanceof BaseModel && !(value instanceof BaseModel)){
                        ModelInfo modelInfo = ModelUtil.getModelInfo(optionValue.getClass());
                        
                        if(modelInfo != null){
                            List<PropertyInfo> identityProperties = modelInfo.getIdentityPropertiesInfo();
                            
                            if(identityProperties != null && identityProperties.size() == 1){
                                PropertyInfo identityPropertyInfo = identityProperties.get(0);
                                Object       optionValueProperty  = PropertyUtil.getProperty(optionValue, identityPropertyInfo.getId());
                                
                                setSelected(value.equals(optionValueProperty));
                            }
                        }
                    }
        	        else
                        setSelected(value.equals(optionValue));
                }
    		}
		}
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderAttributes()
	 */
	protected void renderAttributes() throws Throwable{
		super.renderAttributes();
		
		renderSelectionFlag();
	}

	/**
	 * Renderiza o flag de sele��o da op��o.
	 * 
	 * @throws Throwable
	 */
	protected void renderSelectionFlag() throws Throwable{
        if(isSelected())
            print(" checked");
	}
	
	/**
	 * @see br.com.concepting.framework.web.taglibs.BasePropertyTag#renderBody()
	 */
	protected void renderBody() throws Throwable{
	    if(getPropertyInfo() != null)
	        super.renderBody();
        else{
            print("<span");
            
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

            print(">");
            print(getInvalidPropertyMessage());
            println("</span>");
        }
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#renderLabelBody()
	 */
	protected void renderLabelBody() throws Throwable{
		if(showLabel()){
			if(optionValue != null)
				println(getLabel());
			else
				super.renderLabelBody();
		}
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BasePropertyTag#clearAttributes()
	 */
	protected void clearAttributes(){
		super.clearAttributes();
		
		setSelected(false);
		setOptionValue(null);
		setOptionIndex("");
	}
}