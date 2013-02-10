package br.com.concepting.framework.util.types;

/** 
 * Classe que define as constantes para os atributos de uma instância de data/horário.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum DateFieldType{
	/**
	 * Constante que define o atributo de milisegundos.
	 */
	MILLISECONDS(1000),

	/**
	 * Constante que define o atributo de segundos.
	 */
	SECONDS(MILLISECONDS.getMilliseconds() * 1),

	/**
	 * Constante que define o atributo de minutos.
	 */
	MINUTES(SECONDS.getMilliseconds() * 60),

	/**
	 * Constante que define o atributo de horas.
	 */
	HOURS(MINUTES.getMilliseconds() * 60),

	/**
	 * Constante que define o atributo de dias.
	 */
	DAY(HOURS.getMilliseconds() * 24),

	/**
	 * Constante que define o atributo de meses.
	 */
	MONTH(DAY.getMilliseconds() * 30),

	/**
	 * Constante que define o atributo de anos.
	 */
	YEAR(MONTH.getMilliseconds() * 12);

	private Integer milliseconds;

	/**
	 * Construtor - Define a quantidade de milisegundos.
	 * 
	 * @param milliseconds Valor inteiro que define a quantidade de milisegundos.
	 */
	private DateFieldType(Integer milliseconds){
		setMilliseconds(milliseconds);
	}

	/**
	 * Retorna a quantidade de milisegundos.
	 * 
	 * @return Valor inteiro que define a quantidade de milisegundos.
	 */
    public Integer getMilliseconds(){
        return milliseconds;
    }

    /**
     * Define a quantidade de milisegundos.
     * 
     * @param milliseconds Valor inteiro que define a quantidade de milisegundos.
     */
    public void setMilliseconds(Integer milliseconds){
        this.milliseconds = milliseconds;
    }
}