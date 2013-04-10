package br.com.concepting.framework.web.taglibs;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.util.LanguageUtil;
import br.com.concepting.framework.util.StringUtil;

/**
 * Classe que define a estrutura básica para o componente visual de uma opção de seleção.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class BaseOptionPropertyTag extends BaseOptionsPropertyTag{
	private Boolean selected    = false;
	private Object  optionValue = null;
	private String  optionIndex = "";

	/**
	 * Retorna a instância contendo o valor da opção.
	 * 
	 * @return Instância contendo o valor da opção.
	 */
	public Object getOptionValue(){
		return optionValue;
	}

	/**
	 * Define a instância contendo o valor da opção.
	 * 
	 * @param optionValue Instância contendo o valor da opção.
	 */
	public void setOptionValue(Object optionValue){
		this.optionValue = optionValue;
	}

	/**
	 * Retorna o índice da opção.
	 * 
	 * @return String contendo o índice.
	 */
	protected String getOptionIndex(){
		return optionIndex;
	}
	
    /**
     * Define o índice da opção.
     * 
     * @param optionIndex Valor inteiro contendo o índice.
     */
	protected void setOptionIndex(Integer optionIndex){
	    if(optionIndex != null)
	        this.optionIndex = optionIndex.toString();
	    else
	        this.optionIndex = "";
	}

    /**
     * Define o índice da opção.
     * 
     * @param optionIndex String contendo o índice.
     */
	protected void setOptionIndex(String optionIndex){
		this.optionIndex = optionIndex;
	}
	
	/**
	 * Indica se a opção está selecionada.
	 * 
	 * @return True/False.
	 */
	protected Boolean isSelected(){
		return selected;
	}

	/**
	 * Define se a opção está selecionada.
	 * 
	 * @param checked True/False.
	 */
	protected void setSelected(Boolean selected){
		this.selected = selected;
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BasePropertyTag#getFormattedValue()
	 */
	protected String getFormattedValue() throws Throwable{
		if(optionIndex.length() > 0){
			StringBuilder buffer = new StringBuilder();

			buffer.append("objectId{");
			buffer.append(optionIndex);
			buffer.append("}");

			return buffer.toString();
		}

		Object       optionValue  = getOptionValue();
        Locale       language     = systemController.getCurrentLanguage();
		PropertyInfo propertyInfo = getPropertyInfo();
		
		if(optionValue != null){
			if(propertyInfo != null){
			    String languageId = propertyInfo.getLanguage();
			    
			    if(languageId.length() == 0){
			        String languagePropertyId = propertyInfo.getLanguagePropertyId();
			        
			        if(languagePropertyId.length() > 0){
			            languageId = (String)PropertyUtil.getProperty(optionValue, languagePropertyId);
			            language   = LanguageUtil.getLanguageByString(languageId);
			        }
			    }
			    else
			        language = LanguageUtil.getLanguageByString(languageId);
			    
                if(!propertyInfo.isModel() && !propertyInfo.hasModel()){
		            try{
		                String propertyId = propertyInfo.getId();
		            
		                optionValue = PropertyUtil.getProperty(optionValue, propertyId);
		            }
		            catch(Throwable e){
		            }
				}
			}
			
			return PropertyUtil.format(optionValue, getValueMapInstance(), getPattern(), useAdditionalFormatting(), getPrecision(), language);
		}

		return super.getFormattedValue();
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#initialize()
	 */
	protected void initialize() throws Throwable{
		super.initialize();

		PropertyInfo propertyInfo = getPropertyInfo();
        Object       value        = getValue();

        if(value != null){
	        Object optionValue = getOptionValue();

	        if(propertyInfo != null){
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
                    setSelected(StringUtil.trim(value).equals(StringUtil.trim(optionValue)));
	        }
	        else
	            setSelected(StringUtil.trim(value).equals(StringUtil.trim(optionValue)));
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
	 * Renderiza o flag de seleção da opção.
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