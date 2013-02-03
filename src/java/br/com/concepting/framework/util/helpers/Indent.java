package br.com.concepting.framework.util.helpers;

import br.com.concepting.framework.util.constants.Constants;

/**
 * Classe que define a estrutura básica para regras de indentação.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class Indent{
	private String  startChar        = "";
	private String  endChar          = "";
	private Integer indentCount      = Constants.DEFAULT_INDENT_SIZE;
	private String  indentChar       = Constants.DEFAULT_INDENT_CHARACTER;
	private Boolean backAfterEndChar = false;

	/**
	 * Construtor - Define os caracteres iniciais e finais para indentação.
	 * 
	 * @param startChar String contendo os caracteres iniciais.
	 * @param endChar String contendo os caracteres finais.
	 */
	public Indent(String startChar, String endChar){
		super();

		setStartChar(startChar);
		setEndChar(endChar);
	}

	/**
	 * Construtor - Define os caracteres iniciais e finais para indentação.
	 * 
	 * @param startChar String contendo os caracteres iniciais.
	 * @param endChar String contendo os caracteres finais.
	 * @param indentCount Valor inteiro contendo a quantidade de repetições a serem feitas.
	 */
	public Indent(String startChar, String endChar, Integer indentCount){
		this(startChar, endChar);

		setIndentCount(indentCount);
	}

	/**
	 * Construtor - Define os caracteres iniciais e finais para indentação.
	 * 
	 * @param startChar String contendo os caracteres iniciais.
	 * @param endChar String contendo os caracteres finais.
	 * @param indentCount Valor inteiro contendo a quantidade de repetições a serem feitas.
	 * @param backAfterEndChar Indica que a indentação deve voltar quando os caracteres 
	 * finais forem encontrados.
	 */
	public Indent(String startChar, String endChar, Integer indentCount, Boolean backAfterEndChar){
		this(startChar, endChar, indentCount);

		setBackAfterEndChar(backAfterEndChar);
	}

	/**
	 * Construtor - Define os caracteres iniciais e finais para indentação.
	 * 
	 * @param startChar String contendo os caracteres iniciais.
	 * @param endChar String contendo os caracteres finais.
	 * @param indentCount Valor inteiro contendo a quantidade de repetições a serem feitas.
	 * @param indentChar String contendo o caracter de indentação.
	 */
	public Indent(String startChar, String endChar, Integer indentCount, String indentChar){
		this(startChar, endChar, indentCount);

		setIndentChar(indentChar);
	}

	/**
	 * Construtor - Define os caracteres iniciais e finais para indentação.
	 * 
	 * @param startChar String contendo os caracteres iniciais.
	 * @param endChar String contendo os caracteres finais.
	 * @param indentCount Valor inteiro contendo a quantidade de repetições a serem feitas.
	 * @param indentChar String contendo o caracter de indentação.
	 * @param backAfterEndChar Indica que a indentação deve voltar quando os caracteres 
	 * finais forem encontrados.
	 */
	public Indent(String startChar, String endChar, Integer indentCount, String indentChar, Boolean backAfterEndChar){
		this(startChar, endChar, indentCount, indentChar);

		setBackAfterEndChar(backAfterEndChar);
	}

	/**
	 * Indica que a indentação deve voltar quando os caracteres finais forem encontrados.
	 * 
	 * @return True/False
	 */
	public Boolean isBackAfterEndChar(){
		return backAfterEndChar;
	}

	/**
	 * Define que a indentação deve voltar quando os caracteres finais forem encontrados.
	 * 
	 * @param backAfterEndChar True/False
	 */
	public void setBackAfterEndChar(Boolean backAfterEndChar){
		this.backAfterEndChar = backAfterEndChar;
	}

	/**
	 * Retorna os caracteres finais da indentação.
	 * 
	 * @return String contendo os caracteres finais.
	 */
	public String getEndChar(){
		return endChar;
	}

	/**
	 * Define os caracteres finais da indentação.
	 * 
	 * @param endChar String contendo os caracteres finais.
	 */
	public void setEndChar(String endChar){
		this.endChar = endChar;
	}

	/**
	 * Retorna os caracteres iniciais da indentação.
	 * 
	 * @return String contendo os caracteres iniciais.
	 */
	public String getStartChar(){
		return startChar;
	}

	/**
	 * Define os caracteres iniciais da indentação.
	 * 
	 * @param startChar String contendo os caracteres iniciais.
	 */
	public void setStartChar(String startChar){
		this.startChar = startChar;
	}

	/**
	 * Retorna a quantidade de repetições a serem feitas.
	 * 
	 * @return Valor inteiro contendo a quantidade de repetições a serem feitas.
	 */
	public Integer getIndentCount(){
		return indentCount;
	}

	/**
	 * Define a quantidade de repetições a serem feitas.
	 * 
	 * @param indentCount Valor inteiro contendo a quantidade de repetições a serem feitas.
	 */
	public void setIndentCount(Integer indentCount){
		this.indentCount = indentCount;
	}

	/**
	 * Retorna o caracter de indentação.
	 * 
	 * @return String contendo o caracter de indentação.
	 */
	public String getIndentChar(){
		return indentChar;
	}

	/**
	 * Define o caracter de indentação.
	 * 
	 * @param indentChar String contendo o caracter de indentação.
	 */
	public void setIndentChar(String indentChar){
		this.indentChar = indentChar;
	}
}