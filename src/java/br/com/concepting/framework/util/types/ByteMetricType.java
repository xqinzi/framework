package br.com.concepting.framework.util.types;

import br.com.concepting.framework.util.interfaces.IMetric;

/**
 * Classe que define as constantes para as m�tricas de valores em bytes.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum ByteMetricType implements IMetric{
	/**
	 * Constante que define a m�trica kilobyte.
	 */
	KILO(1024d, "Kb"),

	/**
	 * Constante que define a m�trica megabyte.
	 */
	MEGA(Math.pow(1024, 2), "Mb"),

	/**
	 * Constante que define a m�trica gigabyte.
	 */
	GIGA(Math.pow(1024, 3), "Gb"),

	/**
	 * Constante que define a m�trica terabyte.
	 */
	TERA(Math.pow(1024, 4), "Tb");

	private Double metricValue;
	private String metricUnit;

	/**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param metricValue Valor em ponto flutuante contendo o valor da m�trica.
	 * @param metricUnit String contendo a unidade de m�trica.
	 */
	private ByteMetricType(Double metricValue, String metricUnit){
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

	/**
	 * Retorna o valor em bits para a m�trica.
	 *
	 * @return Valor em ponto flutuante contendo o valor em bits.
	 */
	public Double getBitMetricValue(){
		if(this == KILO)
			return KILO.getMetricValue() - 24;
		else if(this == MEGA)
			return MEGA.getMetricValue() - 48576;
		else if(this == GIGA)
			return GIGA.getMetricValue() - 73741824;
		else
			return TERA.getMetricValue() - 99511627776d;			
	}
}