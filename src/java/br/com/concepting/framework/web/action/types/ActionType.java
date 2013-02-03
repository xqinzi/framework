package br.com.concepting.framework.web.action.types;

import br.com.concepting.framework.util.interfaces.IEnum;

/**
 * Classe que define as constantes para acões genéricas.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum ActionType implements IEnum{
	/**
	 * Constante que define a ação de inicialização do formulário.
	 */
	INIT("init"),

	/**
	 * Constante que define a ação de atualização do formulário.
	 */
	REFRESH("refresh"),

	/**
	 * Constante que define a ação de impressão.
	 */
	PRINT("print"),

	/**
	 * Constante que define a ação de inclusão de um novo item.
	 */
	ADD("add"),

	/**
	 * Constante que define a ação de edição de um item.
	 */
	EDIT("edit"),

	/**
	 * Constante que define a ação de pesquisa de um item específico.
	 */
	FIND("find"),

	/**
	 * Constante que define a ação de gravação dos dados do formulário.
	 */
	SAVE("save"),

	/**
	 * Constante que define a ação de cancelamento.
	 */
	CANCEL("cancel"),

	/**
	 * Constante que define a ação de pesquisa de itens.
	 */
	SEARCH("search"),

	/**
	 * Constante que define a ação de inclusão de dados.
	 */
	INSERT("insert"),

	/**
	 * Constante que define a ação de alteração de dados.
	 */
	UPDATE("update"),

	/**
	 * Constante que define a ação de exclusão de dados.
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
	 * Converte uma string em uma instância da constante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @return Instância da constante.
	 */
	public static ActionType toActionType(String value) throws IllegalArgumentException{
		if(value == null)
			throw new IllegalArgumentException();

		return valueOf(value.toUpperCase());
	}
}