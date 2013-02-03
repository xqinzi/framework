package br.com.concepting.framework.util.helpers;

import br.com.concepting.framework.util.constants.RrdConstants;
import br.com.concepting.framework.util.types.RrdDatasourceType;

/**
 * Classe que define uma tipo de dado a ser armazenado no arquivo RRD.
 *
 * @author fvilarinho
 * @since 1.0
 */
public class RrdDatasource{
	private String            name         = "";
	private RrdDatasourceType type         = RrdConstants.DEFAULT_RRD_DATASOURCE_TYPE;
	private Long              failInterval = RrdConstants.DEFAULT_RRD_FAIL_INTERVAL;
	private Double            minimumValue = Double.NaN;
	private Double            maximumValue = Double.NaN;
	
	/**
	 * Retorna o nome do tipo de dado.
	 *
	 * @return String contendo o nome do tipo de dado.
	 */
	public String getName(){
    	return name;
    }
	
	/**
	 * Define o nome do tipo de dado.
	 *
	 * @param name String contendo o nome do tipo de dado.
	 */
	public void setName(String name){
    	this.name = name;
    }
	
	/**
	 * Retorna a quantidade, em minutos, em que o valor de um campo pode ficar sem 
	 * atualização em um arquivo RRD.
	 * 
	 * @return Valor numérico contendo a quantidade em minutos.
	 */
	public Long getFailInterval(){
    	return failInterval;
    }
	
	/**
	 * Define a quantidade, em minutos, em que o valor de um campo pode ficar sem 
	 * atualização em um arquivo RRD.
	 * 
	 * @param failInterval Valor numérico contendo a quantidade em minutos.
	 */
	public void setFailInterval(Long failInterval){
    	this.failInterval = failInterval;
    }
	
	/**
	 * Retorna o valor mínimo suportado para o tipo de dado.
	 *
	 * @return Valor em ponto flutuante contendo o valor mínimo.
	 */
	public Double getMinimumValue(){
    	return minimumValue;
    }

	/**
	 * Define o valor mínimo suportado para o tipo de dado.
	 *
	 * @param minimumValue Valor em ponto flutuante contendo o valor mínimo.
	 */
	public void setMinimumValue(Double minimumValue){
    	this.minimumValue = minimumValue;
    }

	/**
	 * Retorna o valor máximo suportado para o tipo de dado.
	 *
	 * @return Valor em ponto flutuante contendo o valor máximo.
	 */
	public Double getMaximumValue(){
    	return maximumValue;
    }

	/**
	 * Define o valor máximo suportado para o tipo de dado.
	 *
	 * @param maximumValue Valor em ponto flutuante contendo o valor máximo.
	 */
	public void setMaximumValue(Double maximumValue){
    	this.maximumValue = maximumValue;
    }

	/**
	 * Define o tipo de dado a ser armazenado. 
	 *
	 * @param type Instância contendo o tipo de dado.
	 */
	public void setType(RrdDatasourceType type){
    	this.type = type;
    }

	/**
	 * Retorna o tipo de dado a ser armazenado. 
	 *
	 * @return Instância contendo o tipo de dado.
	 */
	public RrdDatasourceType getType(){
    	return type;
    }
}