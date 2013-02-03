package br.com.concepting.framework.util.interfaces;
 
/**
 * Interface que define a estrutura b�sica para uma m�trica.
 *  
 * @author fvilarinho
 * @since 1.0
 */
public interface IMetric{
	/**
	 * Retorna o valor da m�trica.
	 *
	 * @return Valor em ponto flutuante contendo o valor.
	 */
	public Double getMetricValue();

	/**
	 * Define o valor da m�trica.
	 *
	 * @param metricValue Valor em ponto flutuante contendo o valor.
	 */
	public void setMetricValue(Double metricValue);

	/**
	 * Retorna a unidade da m�trica.
	 *
	 * @return String contendo a unidade da m�trica.
	 */
	public String getMetricUnit();

	/**
	 * Define a unidade da m�trica.
	 *
	 * @param metricUnit String contendo a unidade da m�trica.
	 */
	public void setMetricUnit(String metricUnit);
}
