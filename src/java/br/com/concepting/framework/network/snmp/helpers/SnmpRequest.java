package br.com.concepting.framework.network.snmp.helpers;

/**
 * Classe auxiliar que armazena as propriedades de uma requisi��o SNMP na rede.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class SnmpRequest{
	private String oid          = "";
	private String expression = "";
	private String formula      = "";
	
	/**
	 * Retorna a f�rmula que deve ser aplicada aos resultados da requisi��o.
	 * 
	 * @return String contendo a f�rmula desejada.
	 */
	public String getFormula(){
    	return formula;
    }

	/**
	 * Define a f�rmula que deve ser aplicada aos resultados da requisi��o.
	 * 
	 * @param formula String contendo a f�rmula desejada.
	 */
	public void setFormula(String formula){
    	this.formula = formula;
    }
	
	/**
	 * Retorna a condi��o que deve ser respeitada para que os resultados da requisi��o sejam validos.
	 *  
	 * @return String contendo a condi��o (Express�o booleana).
	 */
	public String getExpression(){
    	return expression;
    }

	/**
	 * Define a condi��o que deve ser respeitada para que os resultados da requisi��o sejam validos.
	 *  
	 * @param expression String contendo a condi��o (Express�o booleana).
	 */
	public void setExpression(String expression){
    	this.expression = expression;
    }

	/**
	 * Retorna o identificador da requisi��o.
	 * 
	 * @return String contendo o identificador da requisi��o.
	 */
	public String getOid(){
		return oid;
	}

	/**
	 * Define o identificador da requisi��o.
	 * 
	 * @param oid String contendo o identificador da requisi��o.
	 */
	public void setOid(String oid){
		this.oid = oid;
	}
}