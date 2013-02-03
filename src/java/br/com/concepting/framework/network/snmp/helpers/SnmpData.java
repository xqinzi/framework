package br.com.concepting.framework.network.snmp.helpers;

/**
 * Classe auxiliar que armazena o resultado de uma requisição SNMP na rede.
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
	 * Retorna a instância contendo as propriedades da requisição SNMP.
	 * 
	 * @return Instância contendo as propriedades da requisição.
	 */
	public SnmpRequest getRequest(){
		return request;
	}

	/**
	 * Define a instância contendo as propriedades da requisição SNMP.
	 * 
	 * @param request Instância contendo as propriedades da requisição.
	 */
	public void setRequest(SnmpRequest request){
		this.request = request;
	}

	/**
	 * Retorna a instância do resultado da requisição SNMP.
	 * 
	 * @return String contendo a instância do resultado.
	 */
	public String getInstance(){
		return instance;
	}

	/**
	 * Define a instância do resultado da requisição SNMP.
	 * 
	 * @param instance String contendo a instância do resultado.
	 */
	public void setInstance(String instance){
		this.instance = instance;
	}

	/**
	 * Retorna o tipo de dado do resultado da requisição SNMP.
	 * 
	 * @return Valor numérico contendo o tipo de dado do resultado.
	 */
	public Integer getType(){
		return type;
	}

	/**
	 * Define o tipo de dado do resultado da requisição SNMP.
	 * 
	 * @param type Valor numérico contendo o tipo de dado do resultado.
	 */
	public void setType(Integer type){
		this.type = type;
	}

	/**
	 * Retorna o valor do resultado da requisição SNMP.
	 * 
	 * @return Instância contendo o valor do resultado.
	 */
	public Object getValue(){
		return value;
	}

	/**
	 * Define o valor do resultado da requisição SNMP.
	 * 
	 * @param value Instância contendo o valor do resultado.
	 */
	public void setValue(Object value){
		this.value = value;
	}
}