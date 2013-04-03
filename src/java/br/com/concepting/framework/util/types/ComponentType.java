package br.com.concepting.framework.util.types;

import br.com.concepting.framework.util.StringUtil;

/** 
 * Classe que define as constantes dos tipos de componentes.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum ComponentType{
	/**
	 * Constante que define um campo invisível.
	 */
	HIDDEN,

	/**
	 * Constantoe que define um campo de marcação.
	 */
	CHECK_BOX,

	/**
	 * Constante que define um campo de seleção.
	 */
	RADIO,

	/**
	 * Constante que define uma lista de opções.
	 */
	LIST,
	
    /**
     * Constante que define um label.
     */
	LABEL,

    /**
     * Constante que define um campo de calendário.
     */
    CALENDAR,

    /**
     * Constante que define um campo texto comum.
     */
    TEXT_BOX,
    
    /**
     * Constante que define um campo texto de múltiplas linhas.
     */
    TEXT_AREA,

    /**
     * Constante que define um campo de senha.
     */
    PASSWORD,
    
    /**
     * Constante que define um link.
     */
    LINK,

    /**
     * Constante que define um botão comum.
     */
    BUTTON,

    /**
     * Constante que define um botão de descartar alterações (Undo).
     */
    UNDO_BUTTON,

    /**
     * Constante que define uma lista de seleção.
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
     * Constante que define um componente de visualização em árvore.
     */
    TREE_VIEW,
	
	/**
	 * Constante que define uma barra de menus.
	 */
	MENU_BAR,

    /**
     * Constante que define um item de menu.
     */
    MENU_ITEM,
    
    /**
     * Constante que define um separador de itens de menu.
     */
    MENU_ITEM_SEPARATOR,

    /**
     * Constante que define uma aba.
     */
    GUIDES,

    /**
	 * Constante que define uma aba.
	 */
	GUIDE,
	
	/**
	 * Constante que define um conjunto de seções.
	 */
	ACCORDION,
	
    /**
     * Constante que define uma seção.
     */
	SECTION,
	
	/**
	 * Constante que define uma barra de progressão.
	 */
	PROGRESS_BAR;
	
	/**
	 * Retorna o identificador do componente.
	 * 
	 * @return String contendo o identificador do componente.
	 */
	public String getId(){
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
	
    /**
     * Retorna o identificador do tipo do componente.
     * 
     * @return String contendo o identificador do tipo do componente.
     */
	public String getType(){
        if(this == UNDO_BUTTON)
            return "RESET";

        return StringUtil.replaceAll(toString(), "_", "");
	}
}