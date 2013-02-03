package br.com.concepting.framework.util.types;

import br.com.concepting.framework.util.interfaces.IEnum;

/** 
 * Classe que define as constantes para os atributos de uma instância de data/horário.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum DateFieldType implements IEnum{
	/**
	 * Constante que define o atributo de milisegundos.
	 */
	MILISECONDS(0),

	/**
	 * Constante que define o atributo de segundos.
	 */
	SECONDS(1),

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

	private Integer key;

	/**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param key Valor inteiro que define a constante.
	 */
	private DateFieldType(Integer key){
		setKey(key);
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IEnum#getKey()
	 */
    public <O> O getKey(){
		return (O)key;
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IEnum#setKey(java.lang.Object)
	 */
	public <O> void setKey(O key){
		this.key = (Integer)key;
	}
	
	/**
	 * Retorna o valor inteiro que define a constante.
	 *
	 * @return Valor inteiro da constante.
	 */
	public Integer toInteger(){
		return key;
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IEnum#toEnum(java.lang.Object)
	 */
	public <O> IEnum toEnum(O value) throws IllegalArgumentException{
		return toDateFieldType((String)value);
	}

	/**
	 * Converte uma string em uma instância da constante.
	 * 
	 * @param value Instância contendo o valor desejado.
	 * @return Instância da constante.
	 */
	public static DateFieldType toDateFieldType(Object value) throws IllegalArgumentException{
		if(value == null)
			throw new IllegalArgumentException();
		
		Object keyValue = null;
		
		for(DateFieldType constant : values()){
			keyValue = constant.getKey();
			keyValue = keyValue.toString();
			
			if(value.equals(keyValue))
				return constant;
		}

		return valueOf(value.toString().toUpperCase());
	}
}