package br.com.concepting.framework.controller.form.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.ConstructorUtils;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.controller.SystemController;
import br.com.concepting.framework.controller.form.ActionFormMessageController;
import br.com.concepting.framework.controller.form.BaseActionForm;
import br.com.concepting.framework.controller.helpers.RequestInfo;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.security.controller.SecurityController;
import br.com.concepting.framework.util.DateTimeUtil;
import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.ScopeType;

/** 
 * Classe ulilitário responsável por popular automaticamente as propriedades de um formulário.
 *
 * @author fvilarinho
 * @since 2.0
 */
public class ActionFormPopulator{
    private   BaseActionForm              actionForm                  = null;
    protected SystemController            systemController            = null;
    protected ActionFormMessageController actionFormMessageController = null;
    protected SecurityController          securityController          = null;
	
	/**
	 * Construtor - Inicializa objetos e/ou variáveis internas.
	 *
	 * @param actionForm Instância do formulário.
	 * @param systemController Instância do controlador de requisições do sistema.
	 */
	public ActionFormPopulator(BaseActionForm actionForm, SystemController systemController){
        super();

        this.actionForm                  = actionForm;
        this.systemController            = systemController;
        this.actionFormMessageController = systemController.getActionFormMessageController();
        this.securityController          = systemController.getSecurityController();
	}
 
	/**
	 * Popula as propriedades do formulário.
	 *
	 * @throws Throwable
	 */
	public void populate() throws Throwable{
		Collection<RequestInfo> requestInfos = systemController.getRequestInfos();
        Boolean                 isEditable   = false;
        
 		for(RequestInfo requestInfo : requestInfos){
 		    isEditable = requestInfo.isEditable();
 		    
 		    if(isEditable)
 		        populateEditable(requestInfo);
 		    else
 		        populate(requestInfo);
 		}
	}

	/**
	 * Popula uma propriedade do modelo de dados.
	 * 
	 * @param requestInfo Instância contendo as propriedades da requisição.
	 */
	private void populate(RequestInfo requestInfo){
		String    name  = requestInfo.getName();
		BaseModel model = null;
		
		if(name.startsWith("search."))
		    model = actionForm.getSearchModel();
		else
		    model = actionForm.getModel();
		
		if(model == null)
			return;
		
		Class     modelClass = model.getClass(); 
		ModelInfo modelInfo  = ModelUtil.getModelInfo(modelClass);
		
		if(modelInfo == null)
			return;
		
		name = StringUtil.replaceAll(name, "search.", "");
		
		PropertyInfo propertyInfo = modelInfo.getPropertyInfo(name);
		
		if(propertyInfo == null)
			return;
		
		try{
    		Object value = populate(requestInfo, propertyInfo);

    		PropertyUtil.setProperty(model, name, value);
		}
		catch(Throwable e){
		}
	}

    /**
     * Popula uma propriedade do modelo de dados vinculada a uma coluna de um grid.
     * 
     * @param requestInfo Instância contendo as propriedades da requisição.
     */
    private void populateEditable(RequestInfo requestInfo){
		String    name  = requestInfo.getName();
		BaseModel model = null;
		
		if(name.startsWith("search."))
		    model = actionForm.getSearchModel();
		else
		    model = actionForm.getModel();
		
		if(model == null)
		    return;
		
		Class     modelClass = model.getClass();
		ModelInfo modelInfo  = ModelUtil.getModelInfo(modelClass);
		
		if(modelInfo == null)
		    return;

		String       editableDataColumn[]     = StringUtil.split(name, "_");
		ScopeType    editableDataScope        = requestInfo.getEditableDataScope();
	    String       editableData             = requestInfo.getEditableData();
		List         editableDataValues       = systemController.findAttribute(editableData, editableDataScope);
		Object       editableDataValue        = null;
		Object       editableDataValuesItem   = null;
		PropertyInfo editableDataPropertyInfo = null;
		
		if(editableDataValues != null && editableDataValues.size() > 0){
			StringBuilder propertyId = new StringBuilder();

			propertyId.append(editableDataColumn[0]);
			propertyId.append(".");
			propertyId.append(editableDataColumn[1]);
			
			editableDataPropertyInfo = modelInfo.getPropertyInfo(propertyId.toString());
			
			if(editableDataPropertyInfo == null){
				Integer pos = editableDataColumn[0].lastIndexOf(".");

				if(pos >= 0)
					editableDataColumn[0] = editableDataColumn[0].substring(0, pos);
				
				propertyId.delete(0, propertyId.length());
				propertyId.append(editableDataColumn[0]);
				propertyId.append(".");
				propertyId.append(editableDataColumn[1]);
				
				editableDataPropertyInfo = modelInfo.getPropertyInfo(propertyId.toString());
			}
			
			if(editableDataPropertyInfo != null){
			    requestInfo.setName(propertyId.toString());
			    
				String editableDataValuesIndex = StringUtil.replaceAll(editableDataColumn[2], AttributeConstants.EDITABLE_DATA_COLUMN_KEY, "");
				
				try{
					editableDataValuesItem = getDataValuesItem(editableDataValues, editableDataValuesIndex);
					editableDataValue      = populate(requestInfo, editableDataPropertyInfo);
   
   					PropertyUtil.setProperty(editableDataValuesItem, editableDataColumn[1], editableDataValue);
				}
				catch(Throwable e){
				}
			}
		}
	}

    /**
     * Popula uma propriedade do modelo de dados.
     * 
     * @param requestInfo Instância contendo as propriedades da requisição.
     * @param propertyInfo Instância contendo os dados da propriedade.
     */
	private Object populate(RequestInfo requestInfo, PropertyInfo propertyInfo){
		Object value = null;

		try{
			if(propertyInfo.isModel())
				value = populateModelProperty(requestInfo, propertyInfo);
			else if(propertyInfo.isEnum())
				value = populateEnumProperty(requestInfo, propertyInfo);
			else if(propertyInfo.isCollection())
			    value = populateCollectionProperty(requestInfo, propertyInfo);
			else
				value = populateProperty(requestInfo, propertyInfo);
		}
		catch(Throwable e){
		}

		return value;
	}

    /**
     * Popula propriedades genéricas (data, string, valores booleanos, valores numéricos, etc) 
     * de um modelo de dados.
     * 
     * @param requestInfo Instância contendo as propriedades da requisição.
     * @param propertyInfo Instância contendo os dados da propriedade.
     */
    private Object populateProperty(RequestInfo requestInfo, PropertyInfo propertyInfo){
		try{
            Class  clazz           = (propertyInfo.isCollection() ? propertyInfo.getCollectionItemsClass() : propertyInfo.getClazz());
			Locale currentLanguage = systemController.getCurrentLanguage();
			String pattern         = requestInfo.getPattern();
			String value           = requestInfo.getValue();

			if(pattern.length() == 0)
				pattern = propertyInfo.getPattern();
			
			if(propertyInfo.isDate()){
				if(pattern.length() > 0)
					return DateTimeUtil.parse(value, pattern);

				return DateTimeUtil.parse(value, currentLanguage);
			}
			else if(propertyInfo.isNumber()){
				try{
					if(pattern.length() > 0)
						return NumberUtil.parse(clazz, value, pattern, currentLanguage);

					return NumberUtil.parse(clazz, value, currentLanguage);
				}
				catch(Throwable e){
					return null;
				}
			}
			else if(propertyInfo.isBoolean())
				return Boolean.parseBoolean(value);
			else{
				if(pattern.length() > 0){
				    Boolean persistPattern = propertyInfo.persistPattern();
				    
					if(!persistPattern)
						value = StringUtil.unformat(value, pattern);
				}

				try{
					return ConstructorUtils.invokeConstructor(clazz, value);
				}
				catch(Throwable e){
					return value;
				}
			}
		}
		catch(Throwable e){
			return null;
		}
	}

    /**
     * Popula propriedades que são modelos de dados.
     * 
     * @param requestInfo Instância contendo as propriedades da requisição.
     * @param propertyInfo Instância contendo os dados da propriedade.
     */
	private BaseModel populateModelProperty(RequestInfo requestInfo, PropertyInfo propertyInfo){
		BaseModel dataValue = null;

		try{
			String value = requestInfo.getValue();
			
			if(value.contains("objectId{")){
				ScopeType       dataScope       = requestInfo.getDataScope();
				String          data            = requestInfo.getData();
				List<BaseModel> dataValues      = systemController.findAttribute(data, dataScope);
				String          dataValuesIndex = StringUtil.replaceAll(value, "objectId{", "");
				
				dataValuesIndex = StringUtil.replaceAll(dataValuesIndex, "}", "");

				if(dataValues != null)
					dataValue = getDataValuesItem(dataValues, dataValuesIndex);
			}
		}
		catch(Throwable e){
		}

		return dataValue;
	}

    /**
     * Popula propriedades que são coleções de tipos genéricos ou modelos de dados.
     * 
     * @param requestInfo Instância contendo as propriedades da requisição.
     * @param propertyInfo Instância contendo os dados da propriedade.
     */
    private Collection populateCollectionProperty(RequestInfo requestInfo, PropertyInfo propertyInfo){
        String        name           = requestInfo.getName();
        String        formName       = actionForm.getName();
        String        values[]       = requestInfo.getValues();
        Integer       dataStartIndex = requestInfo.getDataStartIndex();
        Integer       dataEndIndex   = requestInfo.getDataEndIndex();
        StringBuilder propertyId     = new StringBuilder();
        
        propertyId.append(formName);
        propertyId.append(".");

        if(name.startsWith("search."))
            propertyId.append("searchModel.");
        else
            propertyId.append("model.");
        
        propertyId.append(StringUtil.replaceAll(name, "search.", ""));
        
        Collection list        = systemController.findAttribute(propertyId.toString(), ScopeType.SESSION);
        Collection currentList = new LinkedList();
        
        if(values != null && values.length > 0){
            if(list != null && list.size() > 0)
                currentList.addAll(list);

            Collection selectedList = new LinkedList();
            Object     selectedItem = null;

            for(String value : values){
                if(value.length() > 0){
                    requestInfo.setValue(value);
                    
                    if(propertyInfo.hasModel())
                        selectedItem = populateModelProperty(requestInfo, propertyInfo);
                    else if(propertyInfo.hasEnum())
                        selectedItem = populateEnumProperty(requestInfo, propertyInfo);
                    else
                        selectedItem = populateProperty(requestInfo, propertyInfo);
        
                    if(selectedItem != null)
                        selectedList.add(selectedItem);
                }
            }

            if(currentList.size() == 0)
                currentList = selectedList;
            else{
                if(dataEndIndex == 0)
                    currentList = selectedList;
                else{
                    ScopeType  dataScope  = requestInfo.getDataScope();
                    String     data       = requestInfo.getData();
                    List       dataValues = systemController.findAttribute(data.toString(), dataScope);
                    Collection removeList = new LinkedList();
                    List       bufferList = new LinkedList();

                    bufferList.addAll(dataValues.subList(dataStartIndex, dataEndIndex));

                    for(Object bufferItem : bufferList)
                        if(!selectedList.contains(bufferItem))
                            removeList.add(bufferItem);

                    if(removeList.size() > 0)
                        currentList.removeAll(removeList);

                    for(Object selectedProperty : selectedList)
                        if(!currentList.contains(selectedProperty))
                            currentList.add(selectedProperty);
                }
            }
        }
	    
		return currentList;
	}

    /**
     * Popula propriedades que são enumerations (constantes).
     * 
     * @param requestInfo Instância contendo as propriedades da requisição.
     * @param propertyInfo Instância contendo os dados da propriedade.
     */
	private Enum populateEnumProperty(RequestInfo requestInfo, PropertyInfo propertyInfo){
		try{
			Class  enumClass = (propertyInfo.isCollection() ? propertyInfo.getCollectionItemsClass() : propertyInfo.getClazz());
			String value     = StringUtil.trim(requestInfo.getValue()).toUpperCase();
			
			return Enum.valueOf(enumClass, value);
		}
		catch(IllegalArgumentException e){
			return null;
		}
	}

	/**
	 * Retorna um item de uma lista de dados a partir de seu índice de armazenamento.
	 * 
	 * @param dataValues Lista de dados desejada.
	 * @param dataValuesIndex String contendo o índice de armazenamento.
	 * @return Instância do item desejado.
	 */
    private <O> O getDataValuesItem(List<O> dataValues, String dataValuesIndex){
        Integer pos   = dataValuesIndex.indexOf("_");
        Integer index = 0;

        if(pos < 0){
            index = Integer.parseInt(dataValuesIndex);
            
            return dataValues.get(index);
        }

        index = Integer.parseInt(dataValuesIndex.substring(0, pos));
        
        BaseModel model = (BaseModel)dataValues.get(index);
        
        dataValues      = model.getChildNodes();
        dataValuesIndex = dataValuesIndex.substring(pos + 1);

        return getDataValuesItem(dataValues, dataValuesIndex);
    }
}