package br.com.concepting.framework.network.snmp.helpers;

/**
 * Classe auxiliar que armazena o resultado de uma requisi��o SNMP na rede.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class SnmpData{
	private SnmpRequest request  = null;
	private String      instance = "";
	private Integer     type     = 0;
	private Object      value    = null;

	/**
	 * Retorna a inst�ncia contendo as propriedades da requisi��o SNMP.
	 * 
	 * @return Inst�ncia contendo as propriedades da requisi��o.
	 */
	public SnmpRequest getRequest(){
		return request;
	}

	/**
	 * Define a inst�ncia contendo as propriedades da requisi��o SNMP.
	 * 
	 * @param request Inst�ncia contendo as propriedades da requisi��o.
	 */
	public void setRequest(SnmpRequest request){
		this.request = request;
	}

	/**
	 * Retorna a inst�ncia do resultado da requisi��o SNMP.
	 * 
	 * @return String contendo a inst�ncia do resultado.
	 */
	public String getInstance(){
		return instance;
	}

	/**
	 * Define a inst�ncia do resultado da requisi��o SNMP.
	 * 
	 * @param instance String contendo a inst�ncia do resultado.
	 */
	public void setInstance(String instance){
		this.instance = instance;
	}

	/**
	 * Retorna o tipo de dado do resultado da requisi��o SNMP.
	 * 
	 * @return Valor num�rico contendo o tipo de dado do resultado.
	 */
	public Integer getType(){
		return type;
	}

	/**
	 * Define o tipo de dado do resultado da requisi��o SNMP.
	 * 
	 * @param type Valor num�rico contendo o tipo de dado do resultado.
	 */
	public void setType(Integer type){
		this.type = type;
	}

	/**
	 * Retorna o valor do resultado da requisi��o SNMP.
	 * 
	 * @return Inst�ncia contendo o valor do resultado.
	 */
	public Object getValue(){
		return value;
	}

	/**
	 * Define o valor do resultado da requisi��o SNMP.
	 * 
	 * @param value Inst�ncia contendo o valor do resultado.
	 */
	public void setValue(Object value){
		this.value = value;
	}
}