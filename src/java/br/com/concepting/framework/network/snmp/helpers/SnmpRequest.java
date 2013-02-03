package br.com.concepting.framework.network.snmp.helpers;

/**
 * Classe auxiliar que armazena as propriedades de uma requisição SNMP na rede.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class SnmpRequest{
	private String oid          = "";
	private String expression = "";
	private String formula      = "";
	
	/**
	 * Retorna a fórmula que deve ser aplicada aos resultados da requisição.
	 * 
	 * @return String contendo a fórmula desejada.
	 */
	public String getFormula(){
    	return formula;
    }

	/**
	 * Define a fórmula que deve ser aplicada aos resultados da requisição.
	 * 
	 * @param formula String contendo a fórmula desejada.
	 */
	public void setFormula(String formula){
    	this.formula = formula;
    }
	
	/**
	 * Retorna a condição que deve ser respeitada para que os resultados da requisição sejam validos.
	 *  
	 * @return String contendo a condição (Expressão booleana).
	 */
	public String getExpression(){
    	return expression;
    }

	/**
	 * Define a condição que deve ser respeitada para que os resultados da requisição sejam validos.
	 *  
	 * @param expression String contendo a condição (Expressão booleana).
	 */
	public void setExpression(String expression){
    	this.expression = expression;
    }

	/**
	 * Retorna o identificador da requisição.
	 * 
	 * @return String contendo o identificador da requisição.
	 */
	public String getOid(){
		return oid;
	}

	/**
	 * Define o identificador da requisição.
	 * 
	 * @param oid String contendo o identificador da requisição.
	 */
	public void setOid(String oid){
		this.oid = oid;
	}
}