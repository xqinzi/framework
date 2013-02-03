package br.com.concepting.framework.web.types;

import br.com.concepting.framework.util.interfaces.IEnum;

/** 
 * Classe que define as constantes dos tipos de componentes.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum ComponentType implements IEnum{
	/**
	 * Constante que define um campo invis�vel.
	 */
	HIDDEN("hidden"),

	/**
	 * Constantoe que define um campo de marca��o.
	 */
	CHECK("checkBox"),

	/**
	 * Constante que define um campo de sele��o.
	 */
	RADIO("radio"),

	/**
	 * Constante que define uma lista de op��es.
	 */
	LIST("list"),
	
    /**
     * Constante que define um label.
     */
	LABEL("label"),

    /**
     * Constante que define um campo de calend�rio.
     */
    CALENDAR("calendar"),

    /**
     * Constante que define um campo texto comum.
     */
    TEXT_BOX("textBox"),
    
    /**
     * Constante que define um campo texto de m�ltiplas linhas.
     */
    TEXT_AREA("textArea"),

    /**
     * Constante que define um campo de senha.
     */
    PASSWORD("password"),

    /**
     * Constante que define um bot�o comum.
     */
    BUTTON("button"),

    /**
     * Constante que define um bot�o de descartar altera��es (Undo).
     */
    RESET_BUTTON("reset"),

    /**
     * Constante que define uma lista de sele��o.
     */
    OPTIONS("options"),

    /**
     * Constante que define uma imagem.
     */
    IMAGE("image"),

    /**
	 * Constante que define uma tabela de dados.
	 */
	GRID("grid"),
	
	/**
	 * Constante que define uma coluna em uma tabela de dados.
	 */
	GRID_COLUMN("gridColumn"),
	
	/**
	 * Constante que define um paginador.
	 */
	PAGER("pager"),
    
    /**
     * Constante que define um componente de visualiza��o em �rvore.
     */
    TREE_VIEW("treeView"),
	
	/**
	 * Constante que define uma barra de menus.
	 */
	MENU_BAR("menuBar"),

    /**
     * Constante que define um item de menu.
     */
    MENU_ITEM("menuItem"),
    
    /**
     * Constante que define um separador de itens de menu.
     */
    MENU_ITEM_SEPARATOR("menuItemSeparator"),

    /**
     * Constante que define uma aba.
     */
    GUIDES("guides"),

    /**
	 * Constante que define uma aba.
	 */
	GUIDE("guide"),
	
	ACCORDION("accordion"),
	
	SECTION("section"),
	
	/**
	 * Constante que define uma barra de progress�o.
	 */
	PROGRESS_BAR("progressBar");

	private String key;

    /**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param key String contendo o valor desejado.
	 */
	private ComponentType(String key){
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
	 * @see java.lang.Enum#toString()
	 */
	public String toString(){
		return key;
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IEnum#toEnum(java.lang.Object)
	 */
	public <O> IEnum toEnum(O value) throws IllegalArgumentException{
		return toComponentType((String)value);
	}

	/**
	 * Converte uma string em uma inst�ncia da constante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @return Inst�ncia da constante.
	 */
	public static ComponentType toComponentType(String value) throws IllegalArgumentException{
		if(value == null)
			throw new IllegalArgumentException();

		for(ComponentType constant : values())
			if(value.toUpperCase().equals(constant.toString().toUpperCase()))
				return constant;
		
		throw new IllegalArgumentException(value);
	}
}