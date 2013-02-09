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
	MILISECONDS(1000),

	/**
	 * Constante que define o atributo de segundos.
	 */
	SECONDS(MILISECONDS.toInteger() * 1),

	/**
	 * Constante que define o atributo de minutos.
	 */
	MINUTES(SECONDS.toInteger() * 60),

	/**
	 * Constante que define o atributo de horas.
	 */
	HOURS(MINUTES.toInteger() * 60),

	/**
	 * Constante que define o atributo de dias.
	 */
	DAY(HOURS.toInteger() * 24),

	/**
	 * Constante que define o atributo de meses.
	 */
	MONTH(DAY.toInteger() * 30),

	/**
	 * Constante que define o atributo de anos.
	 */
	YEAR(MONTH.toInteger() * 12);

	private Integer value;

	/**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param value Valor inteiro que define a constante.
	 */
	private DateFieldType(Integer value){
		setValue(value);
	}
	
	public Integer getValue(){
        return value;
    }

    public void setValue(Integer value){
        this.value = value;
    }

    /**
	 * Retorna o valor inteiro que define a constante.
	 *
	 * @return Valor inteiro da constante.
	 */
	public Integer toInteger(){
		return value;
	}
}