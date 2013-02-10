package br.com.concepting.framework.web.form.types;

/**
 * Classe que define as constantes para os tipos de mensagens.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum ActionFormMessageType{
	/**
	 * Constante que define o tipo de mensagem para informações.
	 */
	INFO,

	/**
	 * Constante que define o tipo de mensagem para avisos.
	 */
	WARNING,

	/**
	 * Constante que define o tipo de mensagem para erros.
	 */
	ERROR,

	/**
	 * Constante que define o tipo de mensagem para validação.
	 */
	VALIDATION;
	
	/**
	 * @see java.lang.Enum#toString()
	 */
	public String toString(){
	    return super.toString().toLowerCase();
	}
}