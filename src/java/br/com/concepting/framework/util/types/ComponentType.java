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
	 * Constante que define um campo invis�vel.
	 */
	HIDDEN,

	/**
	 * Constantoe que define um campo de marca��o.
	 */
	CHECK_BOX,

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
    TEXT_BOX,
    
    /**
     * Constante que define um campo texto de m�ltiplas linhas.
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
     * Constante que define um bot�o comum.
     */
    BUTTON,

    /**
     * Constante que define um bot�o de descartar altera��es (Undo).
     */
    UNDO_BUTTON("RESET"),

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
	PROGRESS_BAR,
	
	/**
	 * Constante que define um componente de upload.
	 */
	UPLOAD("FILE"),
	
	/**
	 * Constante que define um timer.
	 */
	TIMER;
	
	private String id   = "";
	private String type = "";
	
	private ComponentType(){
        String        parts[]     = StringUtil.split(toString(), "_");
        StringBuilder componentId = new StringBuilder();
        
        for(int cont = 0 ; cont < parts.length ; cont++){
            if(cont == 0)
                componentId.append(parts[cont].toLowerCase());
            else
                componentId.append(StringUtil.capitalize(parts[cont]));
        }
        
        setId(componentId.toString());
        setType(StringUtil.replaceAll(toString(), "_", ""));
        
	}
	
	private ComponentType(String type){
	    setId(toString());
	    setType(type);
	}
	
    /**
     * Retorna o identificador do componente.
     * 
     * @return String contendo o identificador do componente.
     */
	public String getId(){
	    return id;
	}
	
    /**
     * Define o identificador do componente.
     * 
     * @param id String contendo o identificador do componente.
     */
	public void setId(String id){
	    this.id = id;
	}
	
    /**
     * Retorna o identificador do tipo do componente.
     * 
     * @return String contendo o identificador do tipo do componente.
     */
	public String getType(){
	    return type;
	}
	
    /**
     * Define o identificador do tipo do componente.
     * 
     * @param type String contendo o identificador do tipo do componente.
     */
	public void setType(String type){
        this.type = type;
    }
}