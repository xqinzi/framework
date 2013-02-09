package br.com.concepting.framework.model.types;


/** 
 * Classe que define as constantes das condições para pesquisa e/ou comparação de valores.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum ConditionType{
	/**
	 * Constante que define nenhuma condição.
	 */
	NONE,

	/**
	 * Constante que define a condição igual.
	 */
	EQUAL("="),

	/**
	 * Constante que define a condição diferente.
	 */
	NOT_EQUAL("<>"),

	/**
	 * Constante que define a condição maior ou igual.
	 */
	GREATER_EQUAL_THAN(">="),

	/**
	 * Constante que define a condição maior.
	 */
	GREATER_THAN(">"),

	/**
	 * Constante que define a condição menor ou igual.
	 */
	LESS_EQUAL_THAN("<="),

	/**
	 * Constante que define a condição menor.
	 */
	LESS_THAN("<"),

	/**
	 * Constante que define a condição de pesquisa por contexto, ou seja, pesquisa por partes.
	 */
	CONTEXT("like"),

	/**
	 * Constante que define a condição de pesquisa between, ou seja, entre valores.
	 */
	BETWEEN("between"),

	/**
	 * Constante que define a condição de pesquisa por similaridade.
	 */
	SIMILARITY("similarity"),

	/**
	 * Constante que define a condição de pesquisa que irá considerar uma lista de valores.
	 */
	IN("in"),

	/**
	 * Constante que define a condição de pesquisa que não irá considerar uma lista de valores.
	 */
	NOT_IN("not in"),
	
	/**
	 * Constante que define a condição de pesquisa onde serão considerados valores nulos. 
	 */
	IS_NULL("is null"),
	
	/**
	 * Constante que define a condição de pesquisa onde não serão considerados valores nulos. 
	 */
	NOT_IS_NULL("not is null");

	private String operator = "";
	
	private ConditionType(){
	}

	/**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param operator String contendo o valor desejado.
	 */
	private ConditionType(String operator){
		this.operator = operator;
	}
	
	public String getOperator(){
        return operator;
    }

    public void setOperator(String operator){
        this.operator = operator;
    }
}