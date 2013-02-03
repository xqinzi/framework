package br.com.concepting.framework.persistence.helpers;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import br.com.concepting.framework.model.types.ConditionType;
import br.com.concepting.framework.util.types.SortOrderType;

/**
 * Classe que armazena os par�metros para o filtro de modelos de dados em uma pesquisa no 
 * reposit�rio de persist�ncia.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class ModelFilter{
	private Collection<String>         returnProperties   = null;
	private Map<String, ConditionType> propertyConditions = null;
	private Map<String, Object>        propertyValues     = null;
	private Map<String, SortOrderType> sortOrders         = null;

	/**
	 * Retorna a lista dos identificadores das propriedades que devem ser carregado no modelo de dados.
	 * Se n�o for especificado nada, ser�o carregadas todas as propriedades.
	 * 
	 * @return Lista contendo os identificadores.
	 */
	public Collection<String> getReturnProperties(){
		return returnProperties;
	}

	/**
	 * Define a lista dos identificadores das propriedades que devem ser carregadas no modelo de dados.
	 * Se n�o for especificado nada, ser�o carregadas todas as propriedades.
	 * 
	 * @param returnProperties Lista contendo os identificadores.
	 */
	public void setReturnProperties(Collection<String> returnProperties){
		this.returnProperties = returnProperties;
	}

	/**
	 * Adiciona uma propriedade a ser carregada no modelo de dados.
	 * 
	 * @param property String contendo a propriedade desejada.
	 */
	public void addReturnProperty(String property){
		if(returnProperties == null)
			returnProperties = new LinkedList<String>();

		returnProperties.add(property);
	}

	/**
	 * Retorna as condi��es de pesquisa das propriedades do modelo de dados.
	 * 
	 * @return Mapa contendo as condi��es de pesquisa das propriedades.
	 */
	public Map<String, ConditionType> getPropertyConditions(){
		return propertyConditions;
	}

	/**
	 * Define as condi��es de pesquisa das propriedades do modelo de dados.
	 * 
	 * @param propertyConditions Mapa contendo as condi��es de pesquisa das propriedades.
	 */
	public void setPropertyConditions(Map<String, ConditionType> propertyConditions){
		this.propertyConditions = propertyConditions;
	}

	/**
	 * Adiciona uma condi��o de pesquisa para uma propriedade do modelo de dados.
	 * 
	 * @param propertyId String contendo o identificador da propriedade.
	 * @param condition Inst�ncia contendo a condi��o de pesquisa desejada.
	 */
	public void addPropertyCondition(String propertyId, ConditionType condition){
		if(propertyConditions == null)
			propertyConditions = new LinkedHashMap<String, ConditionType>();

		propertyConditions.put(propertyId, condition);
	}

	/**
	 * Retorna os valores de pesquisa das propriedades do modelo de dados.
	 * 
	 * @return Mapa contendo os valores de pesquisa das propriedades.
	 */
	public Map<String, Object> getPropertyValues(){
		return propertyValues;
	}

	/**
	 * Define os valores de pesquisa das propriedades do modelo de dados.
	 * 
	 * @param propertyValues Mapa contendo os valores de pesquisa das propriedades.
	 */
	public void setPropertyValues(Map<String, Object> propertyValues){
		this.propertyValues = propertyValues;
	}

	/**
	 * Adiciona um valor de pesquisa para uma propriedade do modelo de dados.
	 * 
	 * @param propertyId String contendo o identificador da propriedade.
	 * @param value Inst�ncia contendo o valor de pesquisa desejada.
	 */
	public void addPropertyValue(String propertyId, Object value){
		if(propertyValues == null)
			propertyValues = new LinkedHashMap<String, Object>();

		propertyValues.put(propertyId, value);
	}

	/**
	 * Retorna os tipos de ordena��o das propriedades do modelo de dados.
	 * 
	 * @return Mapa contendo os tipos de ordena��o das propriedades.
	 */
	public Map<String, SortOrderType> getSortOrders(){
		return sortOrders;
	}

	/**
	 * Define os tipos de ordena��o das propriedades do modelo de dados.
	 * 
	 * @param sortOrders Mapa contendo os tipos de ordena��o das propriedades.
	 */
	public void setSortOrders(Map<String, SortOrderType> sortOrders){
		this.sortOrders = sortOrders;
	}

	/**
	 * Adiciona um tipo de ordena��o para uma propriedade do modelo de dados.
	 * 
	 * @param propertyId String contendo o identificador da propriedade.
	 * @param sortOrder Inst�ncia contendo o tipo de ordena��o desejado.
	 */
	public void addSortOrder(String propertyId, SortOrderType sortOrder){
		if(sortOrders == null)
			sortOrders = new LinkedHashMap<String, SortOrderType>();

		sortOrders.put(propertyId, sortOrder);
	}
}