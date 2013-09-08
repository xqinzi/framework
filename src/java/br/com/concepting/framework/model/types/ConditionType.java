package br.com.concepting.framework.model.types;


/** 
 * Classe que define as constantes das condi��es para pesquisa e/ou compara��o de valores.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum ConditionType{
	/**
	 * Constante que define nenhuma condi��o.
	 */
	NONE,

	/**
	 * Constante que define a condi��o igual.
	 */
	EQUAL("="),

	/**
	 * Constante que define a condi��o diferente.
	 */
	NOT_EQUAL("<>"),

	/**
	 * Constante que define a condi��o maior ou igual.
	 */
	GREATER_EQUAL_THAN(">="),

	/**
	 * Constante que define a condi��o maior.
	 */
	GREATER_THAN(">"),

	/**
	 * Constante que define a condi��o menor ou igual.
	 */
	LESS_EQUAL_THAN("<="),

	/**
	 * Constante que define a condi��o menor.
	 */
	LESS_THAN("<"),

	/**
	 * Constante que define a condi��o de pesquisa por contexto, ou seja, pesquisa por partes.
	 */
	CONTEXT("like"),

	/**
	 * Constante que define a condi��o de pesquisa between, ou seja, entre valores.
	 */
	BETWEEN("between"),

	/**
	 * Constante que define a condi��o de pesquisa fon�tica.
	 */
	PHONETIC,

	/**
	 * Constante que define a condi��o de pesquisa que ir� considerar uma lista de valores.
	 */
	IN("in"),

	/**
	 * Constante que define a condi��o de pesquisa que n�o ir� considerar uma lista de valores.
	 */
	NOT_IN("not in"),
	
	/**
	 * Constante que define a condi��o de pesquisa onde ser�o considerados valores nulos. 
	 */
	IS_NULL("is null"),
	
	/**
	 * Constante que define a condi��o de pesquisa onde n�o ser�o considerados valores nulos. 
	 */
	NOT_IS_NULL("not is null");

	private String operator = "";
	
    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     */
	private ConditionType(){
	}

	/**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param operator String contendo o valor desejado.
	 */
	private ConditionType(String operator){
	    this();
	    
		this.operator = operator;
	}
	
	/**
	 * Retorna o operador da condi��o.
	 * 
	 * @return String contendo o operador.
	 */
	public String getOperator(){
        return operator;
    }

    /**
     * Define o operador da condi��o.
     * 
     * @param operator String contendo o operador.
     */
    public void setOperator(String operator){
        this.operator = operator;
    }
}