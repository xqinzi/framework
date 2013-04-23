package br.com.concepting.framework.model;

import java.util.Collection;

import org.apache.commons.beanutils.MethodUtils;

import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.Node;

/**
 * Classe que define a estrutura básica de um modelo de dados.
 * 
 * @author fvilarinho
 * @since 1.0
 */
@Model
public abstract class BaseModel extends Node implements Comparable{
    @Property(useGroupSeparator=true, pattern="0.00")
	private Double similarityAccuracy = 0.0;
	private String sortProperty       = "";

    /**
	 * Retorna a porcentagem de similaridade do modelo de dados.
	 *
	 * @return Valor em ponto flutuante contendo a porcentagem de similaridade.
	 */
	public Double getSimilarityAccuracy(){
     	return similarityAccuracy;
    }

	/**
	 * Define a porcentagem de similaridade do modelo de dados.
	 *
	 * @param similarityAccuracy Valor em ponto flutuante contendo a porcentagem de similaridade.
	 */
	public void setSimilarityAccuracy(Double similarityAccuracy){
     	this.similarityAccuracy = similarityAccuracy;
    }

	/**
	 * Retorna a propriedade para ordenação/comparação de uma lista de modelos de dados.
	 * 
	 * @return String contendo o nome da propriedade.
	 */
	public String getSortProperty(){
		return sortProperty;
	}

	/**
	 * Define a propriedade para ordenação de uma lista de modelos de dados.
	 * 
	 * @param sortProperty String contendo o nome da propriedade.
	 */
	public void setSortProperty(String sortProperty){
		this.sortProperty = sortProperty;
	}

	/**
	 * Efetua a comparação entre duas instâncias.
	 * 
	 * @param object Instância do modelo de dados desejado.
	 * @return Valor inteiro contendo o resultado da comparação. 0 = Igual, 1 ou -1 = Diferente.
	 */
	public int compareTo(Object object){
		Integer result = 0;

		try{
			BaseModel compareObject = (BaseModel)object;
			Object    compareValue  = null;
			Object    currentValue  = null;

			sortProperty = StringUtil.trim(sortProperty);
			if(sortProperty.length() == 0){
				currentValue = toString();
				compareValue = compareObject.toString();
			}
			else{
    			currentValue = PropertyUtil.getProperty(this, sortProperty);
    			compareValue = PropertyUtil.getProperty(compareObject, sortProperty);
			}

			result = (Integer)(MethodUtils.invokeMethod(currentValue, "compareTo", compareValue));
		}
		catch(Throwable e){
		}
		
		if(result < 0)
			result = -1;
		else if(result > 0)
			result = 1;
		else
			return 0;
			
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object object){
		Boolean compareFlag = false;

		if(object instanceof BaseModel && (object.getClass().equals(getClass()) || object.getClass().getSuperclass().equals(getClass().getSuperclass()))){
			BaseModel compareModel = (BaseModel)object;
			Object    compareValue = null;
			Object    value        = null;
			ModelInfo modelInfo    = ModelUtil.getModelInfo(getClass());

			if(modelInfo != null){
				Collection<PropertyInfo> identitiesInfo = modelInfo.getIdentityPropertiesInfo();
				Collection<PropertyInfo> uniquesInfo    = modelInfo.getUniquePropertiesInfo();

				if(uniquesInfo != null){
					for(PropertyInfo uniqueInfo : uniquesInfo){
						try{
							value        = PropertyUtil.getProperty(this, uniqueInfo.getId());
							compareValue = PropertyUtil.getProperty(compareModel, uniqueInfo.getId());

							if(value == null && compareValue == null)
								compareFlag = true;
							else if(value == null && compareValue != null)
								compareFlag = false;
							else if(value != null && compareValue != null)
								compareFlag = value.equals(compareValue);
						}
						catch(Throwable e){
							compareFlag = false;
						}

						if(!compareFlag)
							break;
					}
				}

				if(!compareFlag){
     				if(identitiesInfo != null){
     					for(PropertyInfo identityInfo : identitiesInfo){
     						try{
     							value        = PropertyUtil.getProperty(this, identityInfo.getId());
     							compareValue = PropertyUtil.getProperty(compareModel, identityInfo.getId());

     							if(value != null && compareValue != null){
     							    if(identityInfo.isNumber()){
     							        if(((Number)value).intValue() > 0 || ((Number)compareValue).intValue() > 0)
     							           compareFlag = value.equals(compareValue);
     							    }
 							        else if(identityInfo.isString()){
 							            if(StringUtil.trim(value).length() > 0 || StringUtil.trim(compareValue).length() > 0)
 							                compareFlag = value.equals(compareValue);
 							        }
 							        else
 							            compareFlag = value.equals(compareValue);
     							}
     						}
     						catch(Throwable e){
     							compareFlag = false;
     						}
     
     						if(!compareFlag)
     							break;
     					}
     				}
				}
			}
		}

		return compareFlag;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		ModelInfo modelInfo = ModelUtil.getModelInfo(getClass());

		if(modelInfo != null){
			String descriptionPattern = modelInfo.getDescriptionPattern();
			
			if(descriptionPattern.length() > 0)
				return PropertyUtil.fillPropertiesInString(this, descriptionPattern);
		}

		return super.toString();
	}
}