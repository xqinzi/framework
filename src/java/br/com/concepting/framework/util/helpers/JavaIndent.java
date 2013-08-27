package br.com.concepting.framework.util.helpers;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Classe que define as regras de indenta��o de arquivos de c�digo fonte JAVA.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class JavaIndent extends Indent{
	private static Collection<JavaIndent> rules = null;

	static{
		rules = new LinkedList<JavaIndent>();
		rules.add(new JavaIndent("/*", "*/", 1, true));
		rules.add(new JavaIndent("if(", ";", true));
		rules.add(new JavaIndent("while(", ";", true));
		rules.add(new JavaIndent("for(", ";", true));
        rules.add(new JavaIndent("{", "}"));
	}

	/**
	 * Construtor - Define os caracteres iniciais e finais para indenta��o.
	 * 
	 * @param startChar String contendo os caracteres iniciais.
	 * @param endChar String contendo os caracteres finais.
	 */
	public JavaIndent(String startChar, String endChar){
		super(startChar, endChar);
	}

	/**
	 * Construtor - Define os caracteres iniciais e finais para indenta��o.
	 * 
	 * @param startChar String contendo os caracteres iniciais.
	 * @param endChar String contendo os caracteres finais.
	 * @param indentCount Valor inteiro contendo a quantidade de repeti��es a serem feitas.
	 */
	public JavaIndent(String startChar, String endChar, Integer indentCount){
		super(startChar, endChar, indentCount);
	}

	/**
	 * Construtor - Define os caracteres iniciais e finais para indenta��o.
	 * 
	 * @param startChar String contendo os caracteres iniciais.
	 * @param endChar String contendo os caracteres finais.
	 * @param indentCount Valor inteiro contendo a quantidade de repeti��es a serem feitas.
	 * @param backAfterEndChar Indica que a indenta��o deve voltar quando os caracteres 
	 * finais forem encontrados.
	 */
	public JavaIndent(String startChar, String endChar, Integer indentCount, Boolean backAfterEndChar){
		super(startChar, endChar, indentCount, backAfterEndChar);
	}
	
    /**
     * Construtor - Define os caracteres iniciais e finais para indenta��o.
     * 
     * @param startChar String contendo os caracteres iniciais.
     * @param endChar String contendo os caracteres finais.
     * @param backAfterEndChar Indica que a indenta��o deve voltar quando os caracteres 
     * finais forem encontrados.
     */
	public JavaIndent(String startChar, String endChar, Boolean backAfterEndChar){
        super(startChar, endChar, backAfterEndChar);
    }

	/**
	 * Construtor - Define os caracteres iniciais e finais para indenta��o.
	 * 
	 * @param startChar String contendo os caracteres iniciais.
	 * @param endChar String contendo os caracteres finais.
	 * @param indentCount Valor inteiro contendo a quantidade de repeti��es a serem feitas.
	 * @param indentChar String contendo o caracter de indenta��o.
	 */
	public JavaIndent(String startChar, String endChar, Integer indentCount, String indentChar){
		super(startChar, endChar, indentCount, indentChar);
	}

	/**
	 * Construtor - Define os caracteres iniciais e finais para indenta��o.
	 * 
	 * @param startChar String contendo os caracteres iniciais.
	 * @param endChar String contendo os caracteres finais.
	 * @param indentCount Valor inteiro contendo a quantidade de repeti��es a serem feitas.
	 * @param indentChar String contendo o caracter de indenta��o.
	 * @param backAfterEndChar Indica que a indenta��o deve voltar quando os caracteres 
	 * finais forem encontrados.
	 */
	public JavaIndent(String startChar, String endChar, Integer indentCount, String indentChar, Boolean backAfterEndChar){
		super(startChar, endChar, indentCount, indentChar, backAfterEndChar);
	}

	/**
	 * Retorna as regras de indenta��o.
	 * 
	 * @return Lista contendo as regras de indenta��o.
	 */
	public static Collection<JavaIndent> getRules(){
		return rules;
	}
}