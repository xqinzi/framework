package br.com.concepting.framework.util.types;

import br.com.concepting.framework.util.interfaces.IMetric;

/**
 * Classe que define as constantes para as m�tricas de valores num�ricos.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public enum NumberMetricType implements IMetric{
	/**
	 * Constante que define milhares.
	 */
	THOUSAND(1000d, "k"),

	/**
	 * Constante que define milh�es.
	 */
	MILLION(Math.pow(1000, 2), "mi"),

	/**
	 * Constante que define bilh�es.
	 */
	BILLION(Math.pow(1000, 3), "bi"),

	/**
	 * Constante que define trilh�es.
	 */
	TRILLION(Math.pow(1000, 4), "tri");

	private Double metricValue;
	private String metricUnit;

	/**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param metricValue Valor em ponto flutuante contendo o valor da m�trica.
	 * @param metricUnit String contendo a unidade de m�trica.
	 */
	private NumberMetricType(Double metricValue, String metricUnit){
		setMetricValue(metricValue);
		setMetricUnit(metricUnit);
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IMetric#getMetricValue()
	 */
	public Double getMetricValue(){
		return metricValue;
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IMetric#setMetricValue(java.lang.Double)
	 */
	public void setMetricValue(Double metricValue){
		this.metricValue = metricValue;
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IMetric#getMetricUnit()
	 */
	public String getMetricUnit(){
    	return metricUnit;
    }

	/**
	 * @see br.com.concepting.framework.util.interfaces.IMetric#setMetricUnit(java.lang.String)
	 */
	public void setMetricUnit(String metricUnit){
    	this.metricUnit = metricUnit;
    }
}