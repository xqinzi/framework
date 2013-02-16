
package br.com.concepting.framework.model.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.ConstructorUtils;
import org.dom4j.DocumentException;

import br.com.concepting.framework.caching.Cacher;
import br.com.concepting.framework.caching.CacherManager;
import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;
import br.com.concepting.framework.model.exceptions.ItemNotFoundException;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.persistence.interfaces.IDAO;
import br.com.concepting.framework.service.interfaces.IService;
import br.com.concepting.framework.util.ByteUtil;
import br.com.concepting.framework.util.DateTimeUtil;
import br.com.concepting.framework.util.LanguageUtil;
import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.XmlReader;
import br.com.concepting.framework.util.XmlWriter;
import br.com.concepting.framework.util.helpers.DateTime;
import br.com.concepting.framework.util.helpers.XmlNode;
import br.com.concepting.framework.util.types.SortOrderType;
import br.com.concepting.framework.web.action.BaseAction;
import br.com.concepting.framework.web.form.BaseActionForm;

/**
 * Classe utilitária responsável pela manipulação de propriedades e características de um 
 * modelo de dados.
 * 
 * @author fvilarinho
 * @since 1.0 
 */
public class ModelUtil{
    /**
     * Retorna a classe de um modelo de dados a partir de sua classe de persistência.
     * 
     * @param persistenceClass Classe de persistência utilizada.
     * @return Classe do modelo de dados desejado.
     * @throws ClassNotFoundException
     */
    public static <M extends BaseModel, D extends IDAO> Class<M> getModelClassByPersistence(Class<D> persistenceClass) throws ClassNotFoundException{
		String modelClassId = StringUtil.replaceLast(persistenceClass.getName(), "DAOImpl", "Model");

		modelClassId = StringUtil.replaceLast(modelClassId, "DAO", "Model");
		modelClassId = StringUtil.replaceAll(modelClassId, ".persistence", ".model");

		return (Class<M>)Class.forName(modelClassId);
	}
	
    /**
     * Retorna a classe de um modelo de dados a partir de sua classe de serviço.
     * 
     * @param serviceClass Classe de serviço utilizada.
     * @return Classe do modelo de dados desejado.
     * @throws ClassNotFoundException
     */
    public static <M extends BaseModel, S extends IService> Class<M> getModelClassByService(Class<S> serviceClass) throws ClassNotFoundException{
		String modelClassId = StringUtil.replaceLast(serviceClass.getName(), "ServiceImpl", "Model");

		modelClassId = StringUtil.replaceLast(modelClassId, "Service", "Model");
		modelClassId = StringUtil.replaceAll(modelClassId, ".service", ".model");

		return (Class<M>)Class.forName(modelClassId);
	}

    /**
     * Retorna a classe de um modelo de dados a partir de sua classe de formulário.
     * 
     * @param actionFormClass Classe de formulário utilizada.
     * @return Classe do modelo de dados desejado.
     * @throws ClassNotFoundException
     */
    public static <M extends BaseModel, F extends BaseActionForm> Class<M> getModelClassByActionForm(Class<F> actionFormClass) throws ClassNotFoundException{
		String modelClassId = StringUtil.replaceLast(actionFormClass.getName(), "ActionForm", "Model");

		modelClassId = StringUtil.replaceAll(modelClassId, ".web.form", ".model");

		return (Class<M>)Class.forName(modelClassId);
	}

    /**
     * Retorna a classe de um modelo de dados a partir de sua classe de ações.
     * 
     * @param actionClass Classe de formulário utilizada.
     * @return Classe do modelo de dados desejado.
     * @throws ClassNotFoundException
     */
    public static <M extends BaseModel, A extends BaseAction> Class<M> getModelClassByAction(Class<A> actionClass) throws ClassNotFoundException{
		String modelClassId  = StringUtil.replaceLast(actionClass.getName(), "Action", "Model");

		modelClassId = StringUtil.replaceAll(modelClassId, ".web.action", ".model");

		return (Class<M>)Class.forName(modelClassId);
	}

    /**
	 * Retorna a instância contendo as características (em cache) de um modelo de dados.
	 * 
	 * @param modelClass Classe do modelo de dados desejado.
	 * @return Instância contendo as características do modelo de dados.
	 */
	public static <M extends BaseModel> ModelInfo getModelInfo(Class<?> modelClass){
		ModelInfo modelInfo = null;

		if(modelClass != null){
			String  modelClassName = modelClass.getName();
			Integer cont           = modelClassName.indexOf("$");
			
			if(cont >= 0){
				modelClassName = modelClassName.substring(0, cont);

				try{
					modelClass = Class.forName(modelClassName);
				}
				catch(Throwable e){
				}
			}
				
			Cacher cacher = CacherManager.getInstance().getCacher(ModelUtil.class);

			try{
				modelInfo = (ModelInfo)cacher.get(modelClassName);
			}
			catch(ItemNotFoundException e){
				Model modelAnnotation = modelClass.getAnnotation(Model.class);

				if(modelAnnotation != null){
					Property mappedProperties[] = modelAnnotation.mappedProperties();

					modelInfo = new ModelInfo();
					modelInfo.setId(modelClassName);
					modelInfo.setClazz(modelClass);
					modelInfo.setValidatorClass(modelAnnotation.validatorClass());
					modelInfo.setMappedRepositoryId(modelAnnotation.mappedRepositoryId());
					modelInfo.setDescriptionPattern(modelAnnotation.descriptionPattern());
					modelInfo.setMappedProperties(mappedProperties);
					modelInfo.setUseCase(modelAnnotation.useCase());
					modelInfo.setTemplateId(modelAnnotation.templateId());
					modelInfo.setGeneratePersistence(modelAnnotation.generatePersistence());
					modelInfo.setGenerateService(modelAnnotation.generateService());
					modelInfo.setServiceType(modelAnnotation.serviceType());

					List<PropertyInfo> propertiesInfo = null;
					PropertyInfo       propertyInfo   = null;

					for(Property mappedProperty : mappedProperties){
						if(mappedProperty.propertyId().length() > 0){
							try{
								propertyInfo = PropertyUtil.getPropertyInfo(modelClass, mappedProperty);

								if(propertiesInfo == null)
									propertiesInfo = new LinkedList<PropertyInfo>();

								propertiesInfo.add(propertyInfo);
							}
							catch(Throwable e1){
							}
						}
					}

					Collection<PropertyInfo> propertiesInfoBuffer = PropertyUtil.getPropertiesInfo(modelClass);
					Integer                  pos                  = 0;

					if(propertiesInfoBuffer != null){
						if(propertiesInfo == null)
							propertiesInfo = new LinkedList<PropertyInfo>();

						for(PropertyInfo propertyInfoBuffer : propertiesInfoBuffer){
							pos = propertiesInfo.indexOf(propertyInfoBuffer);
							
							if(pos < 0)
								propertiesInfo.add(propertyInfoBuffer);
						}
					}

					modelInfo.setPropertiesInfo(propertiesInfo);

					try{
						cacher.add(modelInfo);
					}
					catch(Throwable e1){
					}
				}
			}
		}

		return modelInfo;
	}
	
	/**
	 * Retorna uma sub-lista de modelos de dados onde o valor propriedade de um item corresponda ao 
	 * valor especificado.
	 * 
	 * @param list Lista contendo os modelos de dados.
	 * @param propertyId String contendo o identificador da propriedade desejada.
	 * @param propertyValue Instância contendo o valor desejado.
	 * @return Sub-lista contendo os modelos de dados.
	 */
    public static <M extends BaseModel, C extends List<M>> C subList(C list, String propertyId, Object propertyValue){
		List<M> resultList           = new LinkedList<M>();
		M       item                 = null;
		Object  comparePropertyValue = null;
		
		for(Integer cont = 0 ; cont < list.size() ; cont++){
			item = list.get(cont);
			if(item != null){
				try{
    				comparePropertyValue = PropertyUtil.getProperty(item, propertyId);
    				if(comparePropertyValue != null && propertyValue.equals(comparePropertyValue))
						resultList.add(item);
				}
				catch(Throwable e){
				}
			}
		}
		
		return (C)resultList;
	}

	/**
	 * Ordena uma lista de modelos de dados.

	 * @param list Instância contendo a lista de modelos de dados.
	 */
	public static <M extends BaseModel, C extends List<M>> void sort(C list){
		sort(list, Constants.DEFAULT_SORT_ORDER_TYPE);
	}

	/**
	 * Ordena uma lista de modelos de dados especificando a propriedade de ordenação.
	 * 
	 * @param list Instância contendo a lista de modelos de dados.
	 * @param sortProperty String contendo o identificador da propriedade.
	 */
	public static <M extends BaseModel, C extends List<M>> void sort(C list, String sortProperty){
		sort(list, sortProperty, Constants.DEFAULT_SORT_ORDER_TYPE);
	}

	/**
	 * Ordena uma lista de modelos de dados especificando o tipo de ordenação.

	 * @param list Instância contendo a lista de modelos de dados.
	 * @param sortOrder Constante contendo o tipo de ordenação.
	 */
    public static <M extends BaseModel, C extends List<M>> void sort(C list, SortOrderType sortOrder){
		if(sortOrder == SortOrderType.ASCEND)
			Collections.sort(list);
		else
			Collections.sort(list, Collections.reverseOrder());
	}
	
	/**
	 * Ordena uma lista de modelos de dados especificando a propriedade de ordenação e o tipo de 
	 * ordenação.
	 * 
	 * @param list Instância contendo a lista de modelos de dados.
	 * @param sortProperty String contendo o identificador da propriedade.
	 * @param sortOrder Constante contendo o tipo de ordenação.
	 */
	public static <M extends BaseModel, C extends List<M>> void sort(C list, String sortProperty, SortOrderType sortOrder){
		if(list != null){
    		for(BaseModel item : list)
    			item.setSortProperty(sortProperty);

    		sort(list, sortOrder);
		}
	}
	
	/**
	 * Agrupa uma lista de modelos de dados a partir de uma ou mais propriedades.
	 * 
	 * @param list Lista contendo os modelos de dados.
	 * @param propertiesIds Array contendo as propriedades do agrupamento.
	 * @return Lista contendo os modelos de dados já agrupados.
	 */
	public static <M extends BaseModel, C extends List<M>> C aggregate(C list, String propertiesIds[]){
		return aggregateAndSort(list, propertiesIds, null);
	}
	
	/**
	 * Agrupa e ordena uma lista de modelos de dados a partir de uma ou mais 
	 * propriedades.
	 * 
	 * @param list Lista contendo os modelos de dados.
	 * @param propertiesIds Array contendo as propriedades do agrupamento.
	 * @param sortOrders Array contendo os tipos de ordenação da propriedades.
	 * @return Lista contendo os modelos de dados já agrupados.
	 */
    public static <M extends BaseModel, C extends List<M>> C aggregateAndSort(C list, String propertiesIds[], SortOrderType sortOrders[]){
		M         bufferItem            = null;
		List<M>   bufferList            = new LinkedList<M>();
		List<M>   bufferSubList         = null;
		String    bufferPropertiesIds[] = null; 
		SortOrderType bufferSortOrders[]    = null;
		String    propertyId            = propertiesIds[0];
		SortOrderType sortOrder             = (sortOrders != null ? sortOrders[0] : null);
		Object    propertyValue         = null;
		List<M>   resultList            = new LinkedList<M>();
	
		bufferList.addAll(list);

		if(sortOrder == null)
			sortOrder = Constants.DEFAULT_SORT_ORDER_TYPE;
		
		sort(bufferList, propertyId, sortOrder);
		
		if((propertiesIds.length - 1) > 0){
    		for(Integer cont = 0 ; cont < bufferList.size() ; cont++){
    			bufferItem = bufferList.get(cont);
    			if(bufferItem != null){
    				try{
        				propertyValue       = PropertyUtil.getProperty(bufferItem, propertyId);
    					bufferSubList       = subList(bufferList, propertyId, propertyValue);
    					bufferPropertiesIds = Arrays.copyOfRange(propertiesIds, 1, propertiesIds.length);
    					bufferSortOrders    = (sortOrders != null ? Arrays.copyOfRange(sortOrders, 1, sortOrders.length) : null);
    					bufferSubList       = aggregateAndSort(bufferSubList, bufferPropertiesIds, bufferSortOrders);

    					resultList.addAll(bufferSubList);
    					bufferList.removeAll(bufferSubList);
        					
    					cont = -1;
    				}
    				catch(Throwable e){
    				}
    			}
    		}
		}
		else
			resultList = bufferList;
		
		return (C)resultList;
	}
	
	/**
	 * Sumariza os valores de uma propriedade em uma lista de modelos de dados.
	 * 
	 * @param list Lista contendo os modelos de dados.
	 * @param propertyId String contendo o identificador do modelo de dados.
	 * @return Valor numérico contendo a somatória calculada.
	 */
    public static <N extends Number, C extends List<M>, M extends BaseModel> N sum(C list, String propertyId){
		Number result = null;
		Number buffer = null;
		
		try{
    		for(M item : list){
				buffer = (Number)PropertyUtil.getProperty(item, propertyId);
				
				if(result == null)
					result = buffer;
				else
					result = NumberUtil.add(result, buffer);
    		}
		}
		catch(Throwable e){
		}
		
		return (N)result;
	}

	/**
	 * Calcula a média dos valores de uma propriedade em uma lista de modelos de dados.
	 * 
	 * @param list Lista contendo os modelos de dados.
	 * @param propertyId String contendo o identificador do modelo de dados.
	 * @return Valor numérico contendo a média calculada.
	 */
    public static <N extends Number, C extends List<M>, M extends BaseModel> N average(C list, String propertyId){
		Number result = sum(list, propertyId);
		
		result = NumberUtil.divide(result, list.size());
		
		return (N)result;
	}

	/**
	 * Retorna o modelo de dados cuja propriedade contenha o maior valor na lista.
	 * 
	 * @param list Lista contendo os modelos de dados.
	 * @param propertyId String contendo o identificador do modelo de dados.
	 * @return Instância contendo o modelo de dados.
	 */
    public static <C extends List<M>, M extends BaseModel> M max(C list, String propertyId){
		try{
    		for(BaseModel item : list)
    			item.setSortProperty(propertyId);
    		
    		return (M)Collections.max(list);
		}
		catch(Throwable e){
		}
		
		return null;
	}

	/**
	 * Retorna o modelo de dados cuja propriedade contenha o menor valor na lista.
	 * 
	 * @param list Lista contendo os modelos de dados.
	 * @param propertyId String contendo o identificador do modelo de dados.
	 * @return Instância contendo o modelo de dados.
	 */
    public static <C extends List<M>, M extends BaseModel> M min(C list, String propertyId){
		try{
    		for(BaseModel item : list)
    			item.setSortProperty(propertyId);
    		
    		return (M)Collections.min(list);
		}
		catch(Throwable e){
		}
		
		return null;
	}
	
    /**
     * Transforma um arquivo XML em uma lista de modelo de dados.
     * 
     * @param file Instância contendo as propriedades do arquivo XML.
     * @return Lista de modelo de dados.
     * @throws IOException
     */
	public static <M extends BaseModel> List<M> fromXmlFile(File file) throws IOException{
	    return fromXmlFile(file, LanguageUtil.getDefaultLanguage());
	}
	
    /**
     * Transforma um arquivo XML em uma lista de modelo de dados.
     * 
     * @param file Instância contendo as propriedades do arquivo XML.
     * @param language Instância contendo as propriedades do idioma desejado.
     * @return Lista de modelo de dados.
     * @throws IOException
     */
    public static <M extends BaseModel> List<M> fromXmlFile(File file, Locale language) throws IOException{
        FileInputStream in = new FileInputStream(file);
        
        return fromXmlStream(in, language);
    }
    
    /**
     * Transforma um stream de dados com conteúdo XML em uma lista de modelo de dados.
     * 
     * @param in Instância do stream de dados desejado.
     * @return Lista de modelo de dados.
     * @throws IOException
     */
    public static <M extends BaseModel> List<M> fromXmlStream(InputStream in) throws IOException{
        return fromXmlStream(in, LanguageUtil.getDefaultLanguage());
    }
    
	public static <M extends BaseModel> List<M> fromXmlStream(InputStream in, Locale language) throws IOException{
	    XmlReader     reader     = new XmlReader(in);
	    XmlNode       node       = reader.getRoot();
	    List<XmlNode> childNodes = node.getChildNodes();
	    
	    return fromXmlNodes(childNodes, language);
	}
	
    public static <M extends BaseModel> List<M> fromXmlNodes(List<XmlNode> nodes){
        return fromXmlNodes(nodes, LanguageUtil.getDefaultLanguage());
    }
    
	public static <M extends BaseModel> List<M> fromXmlNodes(List<XmlNode> nodes, Locale language){
	    List<M> result = new LinkedList<M>();
	    M       item   = null;
	    
	    for(XmlNode node : nodes){
	        item = fromXmlNode(node, language);
	        
	        result.add(item);
	    }
	    
	    return result;
	}
	
	public static <M extends BaseModel> M fromXmlNode(XmlNode node){
	    return fromXmlNode(node, LanguageUtil.getDefaultLanguage());
	}
	
    public static <M extends BaseModel> M fromXmlNode(XmlNode node, Locale language){
	    if(language == null)
	        return fromXmlNode(node);
	    
	    M model = null;
	    
	    if(node != null){
	        try{
    	        Class     modelClass = Class.forName(node.getAttribute("class"));
                ModelInfo modelInfo  = ModelUtil.getModelInfo(modelClass);               
    	        
                if(modelInfo != null){
                    model = (M)ConstructorUtils.invokeConstructor(modelClass, null);
                    
                    Collection<XmlNode> childNodes = node.getChildNodes();
                    
                    if(childNodes != null && childNodes.size() > 0){
                        PropertyInfo propertyInfo  = null;
                        String       propertyName  = "";
                        Object       propertyValue = null;
                        
                        for(XmlNode childNode : childNodes){
                            propertyInfo = modelInfo.getPropertyInfo(childNode.getAttribute("name"));
                            
                            if(propertyInfo != null){
                                propertyName = propertyInfo.getId();
                                
                                if(propertyInfo.isModel()){
                                    node          = (XmlNode)childNode.getChildNodes().iterator().next();
                                    propertyValue = fromXmlNode(node, language);
                                    
                                    PropertyUtil.setProperty(model, propertyName, propertyValue);
                                }
                                else if(propertyInfo.isCollection()){
                                    
                                }
                                else if(propertyInfo.isDate()){
                                    propertyValue = DateTimeUtil.parse(childNode.getValue(), language);
                                    
                                    PropertyUtil.setProperty(model, propertyName, propertyValue);
                                }
                                else if(propertyInfo.isNumber()){
                                    propertyValue = NumberUtil.parse(propertyInfo.getClazz(), childNode.getValue());
                                    
                                    PropertyUtil.setProperty(model, propertyName, propertyValue);
                                }
                                else if(propertyInfo.isBoolean()){
                                    propertyValue = Boolean.parseBoolean(childNode.getValue());
                                    
                                    PropertyUtil.setProperty(model, propertyName, propertyValue);
                                }
                                else if(propertyInfo.isByteArray()){
                                    propertyValue = ByteUtil.fromBase64(childNode.getValue());
                                    
                                    PropertyUtil.setProperty(model, propertyName, propertyValue);
                                }
                                else if(propertyInfo.isEnum()){
                                    propertyValue = Enum.valueOf(propertyInfo.getClazz(), childNode.getValue().toUpperCase());

                                    PropertyUtil.setProperty(model, propertyName, propertyValue);
                                }
                                else{
                                    propertyValue = childNode.getValue();
                                    
                                    PropertyUtil.setProperty(model, propertyName, propertyValue);
                                }
                            }
                        }
                    }
                }
	        }
	        catch(Throwable e){
	        }
	    }
	    
	    return model;
	}
	
	public static <M extends BaseModel> void toXmlFile(File file, List<M> list) throws IOException, DocumentException{
	    toXmlFile(file, list, LanguageUtil.getDefaultLanguage());
	}
	
    public static <M extends BaseModel> void toXmlFile(File file, List<M> list, Locale language) throws IOException, DocumentException{
        XmlNode node = new XmlNode("modelList");
        
        for(M item : list)
            node.addChildNode(toXmlNode(item, language));
        
        XmlWriter writer = new XmlWriter(file);
        
        writer.write(node);
    }
    
    public static <M extends BaseModel> void toXmlFile(File file, M model) throws IOException, DocumentException{
        toXmlFile(file, model, LanguageUtil.getDefaultLanguage());
    }
    
	public static <M extends BaseModel> void toXmlFile(File file, M model, Locale language) throws IOException, DocumentException{
	    XmlNode   node   = toXmlNode(model, language);
	    XmlWriter writer = new XmlWriter(file);
	    
	    writer.write(node);
	}
	
    /**
     * Transforma um modelo de dados em uma instância de um nó XML.
     * 
     * @param model Instância do modelo de dados desejado.
     * @return Instância do nó XML.
     */
	public static <M extends BaseModel> XmlNode toXmlNode(M model){
	    return toXmlNode(model, LanguageUtil.getDefaultLanguage());
	}
	
	/**
	 * Transforma um modelo de dados em uma instância de um nó XML.
	 * 
	 * @param model Instância do modelo de dados desejado.
	 * @param language Instância contendo as propriedades de idioma desejado.
	 * @return Instância do nó XML.
	 */
	public static <M extends BaseModel> XmlNode toXmlNode(M model, Locale language){
	    if(language == null)
	        return toXmlNode(model);
	    
	    XmlNode node = null;
	    
	    if(model != null){
	        ModelInfo modelInfo = ModelUtil.getModelInfo(model.getClass());
	        
	        if(modelInfo != null){
	            node = new XmlNode("model");
	            node.addAttribute("class", modelInfo.getClazz().getName());

                XmlNode                  childNode      = null;
                String                   itemValue      = "";
                XmlNode                  itemChildNode  = null;
	            Collection<PropertyInfo> propertiesInfo = modelInfo.getPropertiesInfo();
	            String                   propertyName   = "";
	            Object                   propertyValue  = null;
	            Collection               propertyValues = null;
	            
	            for(PropertyInfo propertyInfo : propertiesInfo){
                    try{
                        propertyName  = propertyInfo.getId();
                        propertyValue = PropertyUtil.getProperty(model, propertyName);

                        if(propertyValue != null){
                            if(propertyInfo.isCollection())
                                childNode = new XmlNode("collection");
                            else
                                childNode = new XmlNode("property");
                            
        	                childNode.addAttribute("name", propertyName);
	                    
        	                if(propertyInfo.isModel())
        	                    childNode.addChildNode(toXmlNode((BaseModel)propertyValue, language));
        	                else if(propertyInfo.isCollection()){
        	                    propertyValues = (Collection)propertyValue;
                            
        	                    for(Object item : propertyValues){
        	                        if(propertyInfo.hasModel())
        	                            itemChildNode = toXmlNode((BaseModel)item, language);
        	                        else if(PropertyUtil.isDate(propertyInfo.getCollectionItemsClass())){
        	                            itemValue = DateTimeUtil.format((Date)item, language);

        	                            itemChildNode = new XmlNode("item", itemValue);
        	                        }
                                    else if(PropertyUtil.isTime(propertyInfo.getCollectionItemsClass())){
                                        itemValue = DateTimeUtil.format((DateTime)item, language);

                                        itemChildNode = new XmlNode("item", itemValue);
                                    }
                                    else if(PropertyUtil.isNumber(propertyInfo.getCollectionItemsClass())){
                                        itemValue = NumberUtil.format((Number)item, language);
                                        
                                        itemChildNode = new XmlNode("item", itemValue);
                                    }
                                    else if(PropertyUtil.isByteArray(propertyInfo.getCollectionItemsClass())){
                                        itemValue = ByteUtil.toBase64((byte[])item);
                                        
                                        itemChildNode = new XmlNode("item", itemValue);
                                    }
                                    else
                                        itemChildNode = new XmlNode("item", item.toString());
        	                        
        	                        childNode.addChildNode(itemChildNode);
        	                    }
                            }
    	                    else if(propertyInfo.isDate()){
	                            propertyValue = DateTimeUtil.format((Date)propertyValue, language);
                                
	                            childNode.setValue((String)propertyValue);
    	                    }
                            else if(propertyInfo.isTime()){
                                propertyValue = DateTimeUtil.format((DateTime)propertyValue, language);
                                
                                childNode.setValue((String)propertyValue);
                            }
    	                    else if(propertyInfo.isNumber()){
    	                        propertyValue = NumberUtil.format((Number)propertyValue, language);
                                
    	                        childNode.setValue((String)propertyValue);
                            }
                            else if(propertyInfo.isByteArray()){
                                propertyValue = ByteUtil.toBase64((byte[])propertyValue);
                                
                                childNode.setValue((String)propertyValue);
                            }
                            else
                                childNode.setValue(propertyValue.toString());
    	                    
    	                    node.addChildNode(childNode);
                        }
	                }
	                catch(Throwable e){
	                }
	            }
	        }
	    }
	    
	    return node;
	}
}