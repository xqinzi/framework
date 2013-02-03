package br.com.concepting.framework.web.action.types;

import br.com.concepting.framework.util.interfaces.IEnum;

/**
 * Classe que define as constantes para ac�es gen�ricas.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum ActionType implements IEnum{
	/**
	 * Constante que define a a��o de inicializa��o do formul�rio.
	 */
	INIT("init"),

	/**
	 * Constante que define a a��o de atualiza��o do formul�rio.
	 */
	REFRESH("refresh"),

	/**
	 * Constante que define a a��o de impress�o.
	 */
	PRINT("print"),

	/**
	 * Constante que define a a��o de inclus�o de um novo item.
	 */
	ADD("add"),

	/**
	 * Constante que define a a��o de edi��o de um item.
	 */
	EDIT("edit"),

	/**
	 * Constante que define a a��o de pesquisa de um item espec�fico.
	 */
	FIND("find"),

	/**
	 * Constante que define a a��o de grava��o dos dados do formul�rio.
	 */
	SAVE("save"),

	/**
	 * Constante que define a a��o de cancelamento.
	 */
	CANCEL("cancel"),

	/**
	 * Constante que define a a��o de pesquisa de itens.
	 */
	SEARCH("search"),

	/**
	 * Constante que define a a��o de inclus�o de dados.
	 */
	INSERT("insert"),

	/**
	 * Constante que define a a��o de altera��o de dados.
	 */
	UPDATE("update"),

	/**
	 * Constante que define a a��o de exclus�o de dados.
	 */
	DELETE("delete");

	private String key;

	/**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param key String contendo o valor desejado.
	 */
	private ActionType(String key){
		setKey(key);
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IEnum#getKey()
	 */
    public <O> O getKey(){
		return (O)key;
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IEnum#setKey(java.lang.Object)
	 */
	public <O> void setKey(O key){
		this.key = (String)key;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return key;
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IEnum#toEnum(java.lang.Object)
	 */
	public <O> IEnum toEnum(O value) throws IllegalArgumentException{
		return toActionType((String)value);
	}

	/**
	 * Converte uma string em uma inst�ncia da constante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @return Inst�ncia da constante.
	 */
	public static ActionType toActionType(String value) throws IllegalArgumentException{
		if(value == null)
			throw new IllegalArgumentException();

		return valueOf(value.toUpperCase());
	}
}