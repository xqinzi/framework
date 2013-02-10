package br.com.concepting.framework.web.types;

import br.com.concepting.framework.util.StringUtil;

/** 
 * Classe que define as constantes dos tipos de componentes.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum ComponentType{
	/**
	 * Constante que define um campo invis�vel.
	 */
	HIDDEN,

	/**
	 * Constantoe que define um campo de marca��o.
	 */
	CHECKBOX,

	/**
	 * Constante que define um campo de sele��o.
	 */
	RADIO,

	/**
	 * Constante que define uma lista de op��es.
	 */
	LIST,
	
    /**
     * Constante que define um label.
     */
	LABEL,

    /**
     * Constante que define um campo de calend�rio.
     */
    CALENDAR,

    /**
     * Constante que define um campo texto comum.
     */
    TEXTBOX,
    
    /**
     * Constante que define um campo texto de m�ltiplas linhas.
     */
    TEXTAREA,

    /**
     * Constante que define um campo de senha.
     */
    PASSWORD,

    /**
     * Constante que define um bot�o comum.
     */
    BUTTON,

    /**
     * Constante que define um bot�o de descartar altera��es (Undo).
     */
    UNDO_BUTTON,

    /**
     * Constante que define uma lista de sele��o.
     */
    OPTIONS,

    /**
     * Constante que define uma imagem.
     */
    IMAGE,

    /**
	 * Constante que define uma tabela de dados.
	 */
	GRID,
	
	/**
	 * Constante que define uma coluna em uma tabela de dados.
	 */
	GRID_COLUMN,
	
	/**
	 * Constante que define um paginador.
	 */
	PAGER,
    
    /**
     * Constante que define um componente de visualiza��o em �rvore.
     */
    TREEVIEW,
	
	/**
	 * Constante que define uma barra de menus.
	 */
	MENUBAR,

    /**
     * Constante que define um item de menu.
     */
    MENUITEM,
    
    /**
     * Constante que define um separador de itens de menu.
     */
    MENUITEM_SEPARATOR,

    /**
     * Constante que define uma aba.
     */
    GUIDES,

    /**
	 * Constante que define uma aba.
	 */
	GUIDE,
	
	/**
	 * Constante que define um conjunto de se��es.
	 */
	ACCORDION,
	
    /**
     * Constante que define uma se��o.
     */
	SECTION,
	
	/**
	 * Constante que define uma barra de progress�o.
	 */
	PROGRESS_BAR;
	
	/**
	 * Retorna o identificador do tipo de componente.
	 * 
	 * @return String contendo o identificador do tipo de componente.
	 */
	public String getId(){
	    if(this == UNDO_BUTTON)
	        return "reset";
	    
	    String        parts[]     = StringUtil.split(toString(), "_");
	    StringBuilder componentId = new StringBuilder();
	    
	    for(int cont = 0 ; cont < parts.length ; cont++){
	        if(cont == 0)
	            componentId.append(parts[cont].toLowerCase());
	        else
	            componentId.append(StringUtil.capitalize(parts[cont]));
	    }
	    
	    return componentId.toString();
	}
}