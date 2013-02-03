package br.com.concepting.framework.util.interfaces;
 
/**
 * Interface que define a estrutura básica para uma métrica.
 *  
 * @author fvilarinho
 * @since 1.0
 */
public interface IMetric{
	/**
	 * Retorna o valor da métrica.
	 *
	 * @return Valor em ponto flutuante contendo o valor.
	 */
	public Double getMetricValue();

	/**
	 * Define o valor da métrica.
	 *
	 * @param metricValue Valor em ponto flutuante contendo o valor.
	 */
	public void setMetricValue(Double metricValue);

	/**
	 * Retorna a unidade da métrica.
	 *
	 * @return String contendo a unidade da métrica.
	 */
	public String getMetricUnit();

	/**
	 * Define a unidade da métrica.
	 *
	 * @param metricUnit String contendo a unidade da métrica.
	 */
	public void setMetricUnit(String metricUnit);
}
