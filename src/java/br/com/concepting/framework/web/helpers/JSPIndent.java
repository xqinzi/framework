package br.com.concepting.framework.web.helpers;

import java.util.Collection;
import java.util.LinkedList;

import br.com.concepting.framework.util.helpers.Indent;

/** 
 * Classe que define as regras de indentação de páginas JSP.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class JSPIndent extends Indent{
	private static Collection<JSPIndent> rules = null;

	static{
		rules = new LinkedList<JSPIndent>();
		rules.add(new JSPIndent("<", "</"));
		rules.add(new JSPIndent("<", "/>"));
		rules.add(new JSPIndent("<%", "%>"));
		rules.add(new JSPIndent("/*", "*/", 1, true));
	}

	/**
	 * Construtor - Define os caracteres iniciais e finais para indentação.
	 * 
	 * @param startChar String contendo os caracteres iniciais.
	 * @param endChar String contendo os caracteres finais.
	 */
	public JSPIndent(String startChar, String endChar){
		super(startChar, endChar);
	}

	/**
	 * Construtor - Define os caracteres iniciais e finais para indentação.
	 * 
	 * @param startChar String contendo os caracteres iniciais.
	 * @param endChar String contendo os caracteres finais.
	 * @param indentCount Valor inteiro contendo a quantidade de repetições a serem feitas.
	 */
	public JSPIndent(String startChar, String endChar, Integer indentCount){
		super(startChar, endChar, indentCount);
	}

	/**
	 * Construtor - Define os caracteres iniciais e finais para indentação.
	 * 
	 * @param startChar String contendo os caracteres iniciais.
	 * @param endChar String contendo os caracteres finais.
	 * @param indentCount Valor inteiro contendo a quantidade de repetições a serem feitas.
	 * @param backAfterEndChar Indica que a indentação deve voltar quando os caracteres finais forem 
	 * encontrados.
	 */
	public JSPIndent(String startChar, String endChar, Integer indentCount, Boolean backAfterEndChar){
		super(startChar, endChar, indentCount, backAfterEndChar);
	}

	/**
	 * Construtor - Define os caracteres iniciais e finais para indentação.
	 * 
	 * @param startChar String contendo os caracteres iniciais.
	 * @param endChar String contendo os caracteres finais.
	 * @param indentCount Valor inteiro contendo a quantidade de repetições a serem feitas.
	 * @param indentChar String contendo o caracter de indentação.
	 */
	public JSPIndent(String startChar, String endChar, Integer indentCount, String indentChar){
		super(startChar, endChar, indentCount, indentChar);
	}

	/**
	 * Construtor - Define os caracteres iniciais e finais para indentação.
	 * 
	 * @param startChar String contendo os caracteres iniciais.
	 * @param endChar String contendo os caracteres finais.
	 * @param indentCount Valor inteiro contendo a quantidade de repetições a serem feitas.
	 * @param indentChar String contendo o caracter de indentação.
	 * @param backAfterEndChar Indica que a indentação deve voltar quando os caracteres finais forem 
	 * encontrados.
	 */
	public JSPIndent(String startChar, String endChar, Integer indentCount, String indentChar, Boolean backAfterEndChar){
		super(startChar, endChar, indentCount, indentChar, backAfterEndChar);
	}

	/**
	 * Retorna as regras de indentação.
	 * 
	 * @return Lista contendo as regras de indentação.
	 */
	public static Collection<JSPIndent> getRules(){
		return rules;
	}
}