package br.com.concepting.framework.web.helpers;

import br.com.concepting.framework.util.types.PagerActionType;
import br.com.concepting.framework.util.types.SortOrderType;
import br.com.concepting.framework.web.types.ScopeType;

/**
 * Classe auxiliar que define as propriedades de um parâmetro da requisição.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class RequestInfo{
    private String          currentGuide         = "";
    private String          currentNode          = "";
    private String          currentSection       = "";
    private String          data                 = "";
    private ScopeType       dataScope            = null;
    private Integer         dataStartIndex       = 0;
    private Integer         dataEndIndex         = 0;
    private String          editableData         = "";
    private ScopeType       editableDataScope    = null;
    private Boolean         hasMultipleSelection = false;
    private Boolean         isEditable           = false;
    private Integer         itemsPerPage         = 0;
    private String          label                = "";
    private String          name                 = "";
    private PagerActionType pagerAction          = null;
    private String          pattern              = "";
    private SortOrderType   sortOrder            = null;
	private String          sortProperty         = "";
    private String          value                = "";
    private String          values[]             = null;
    
    /**
     * Retorna o identificador da seção atual.
     * 
     * @return String contendo o identificador da seção atual.
     */
    public String getCurrentSection(){
        return currentSection;
    }

    /**
     * Define o identificador da seção atual.
     * 
     * @param currentSession String contendo o identificador da seção atual.
     */
    public void setCurrentSection(String currentSection){
        this.currentSection = currentSection;
    }

    /**
     * Retorna a ação de paginação executada.
     * 
     * @return Constante que define a ação de paginação executada.
     */
	public PagerActionType getPagerAction(){
        return pagerAction;
    }

    /**
     * Define a ação de paginação executada.
     * 
     * @param pagerAction Constante que define a ação de paginação executada.
     */
    public void setPagerAction(PagerActionType pagerAction){
        this.pagerAction = pagerAction;
    }

    /**
	 * Retorna o identificador da coluna selecionada para ordenação.
	 * 
	 * @return String contendo o identificador.
	 */
	public String getSortProperty(){
		return sortProperty;
	}
	
	/**
	 * Define o identificador da coluna selecionada para ordenação.
	 * 
	 * @param sortProperty String contendo o identificador.
	 */
	public void setSortProperty(String sortProperty){
		this.sortProperty = sortProperty;
	}
	
	/**
	 * Retorna o identificador da propriedade que armazena os dados editáveis.
	 * 
	 * @return String contendo o identificador da propriedade que armazena os dados editáveis.
	 */
	public String getEditableData(){
		return editableData;
	}
	
	/**
	 * Define o identificador da propriedade que armazena os dados editáveis.
	 * 
	 * @param editableData String contendo o identificador da propriedade que armazena os dados 
	 * editáveis.
	 */
	public void setEditableData(String editableData){
		this.editableData = editableData;
	}
	
	/**
	 * Retorna o escopo da propriedade que armazena os dados editáveis.
	 * 
	 * @return Instância que define o escopo da propriedade que armazena os dados editáveis.
	 */
	public ScopeType getEditableDataScope(){
		return editableDataScope;
	}
	
	/**
	 * Define o escopo da propriedade que armazena os dados editáveis.
	 * 
	 * @param editableDataScope Instância que define o escopo da propriedade que armazena os dados 
	 * editáveis.
	 */
	public void setEditableDataScope(ScopeType editableDataScope){
		this.editableDataScope = editableDataScope;
	}
	
	/**
	 * Retorna a quantidade de itens por página a serem exibidos.
	 * 
	 * @return Valor inteiro contendo a quantidade de itens por página.
	 */
	public Integer getItemsPerPage(){
		return itemsPerPage;
	}
	
	/**
	 * Define a quantidade de itens por página a serem exibidos.
	 * 
	 * @param itemsPerPage Valor inteiro contendo a quantidade de itens por página.
	 */
	public void setItemsPerPage(Integer itemsPerPage){
		this.itemsPerPage = itemsPerPage;
	}
	
	/**
	 * Retorna o tipo de ordenação a ser executado.
	 * 
	 * @return Constante contendo o tipo de ordenação.
	 */
	public SortOrderType getSortOrder(){
		return sortOrder;
	}
	
	/**
	 * Define o tipo de ordenação a ser executado.
	 * 
	 * @param sortOrder Constante contendo o tipo de ordenação.
	 */
	public void setSortOrder(SortOrderType sortOrder){
		this.sortOrder = sortOrder;
	}
	
	/**
	 * Indica se o parâmetro da requisição é uma coluna editável.
	 * 
	 * @return True/False.
	 */
	public Boolean isEditable(){
		return isEditable;
	}
	
	/**
	 * Indica se o parâmetro da requisição é uma coluna editável.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsEditable(){
		return isEditable();
	}
	
	/**
	 * Define se o parâmetro da requisição é uma coluna editável.
	 * 
	 * @param isEditable True/False.
	 */
	public void setIsEditable(Boolean isEditable){
		this.isEditable = isEditable;
	}
	
	/**
	 * Retorna um array contendo os valores das opções selecionadas.
	 * 
	 * @return Array contendo os valores das opções selecionadas.
	 */
	public String[] getValues(){
		return values;
	}
	
	/**
	 * Define um array contendo os valores das opções selecionadas.
	 * 
	 * @param values Array contendo os valores das opções selecionadas.
	 */
	public void setValues(String[] values){
		this.values = values;
	}
	
	/**
	 * Retorna o nome do parâmetro da requisição.
	 * 
	 * @return String contendo o nome do parâmetro.
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Define o nome do parâmetro da requisição.
	 * 
	 * @param name String contendo o nome do parâmetro.
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Retorna o label do parâmetro da requisição.
	 * 
	 * @return String contendo o label do parâmetro.
	 */
	public String getLabel(){
		return label;
	}
	
	/**
	 * Define o label do parâmetro da requisição.
	 * 
	 * @param label String contendo o label do parâmetro.
	 */
	public void setLabel(String label){
		this.label = label;
	}
	
	/**
	 * Retorna o valor do parâmetro da requisição.
	 * 
	 * @return String contendo o valor do parâmetro.
	 */
	public String getValue(){
		return value;
	}
	
	/**
	 * Define o valor do parâmetro da requisição.
	 * 
	 * @param value String contendo o valor do parâmetro.
	 */
	public void setValue(String value){
		this.value = value;
	}
	
	/**
	 * Retorna a máscara de formatação do parâmetro da requisição.
	 * 
	 * @return String contendo a máscara de formatação.
	 */
	public String getPattern(){
		return pattern;
	}
	 
	/**
	 * Define a máscara de formatação do parâmetro da requisição.
	 * 
	 * @param pattern String contendo a máscara de formatação.
	 */
	public void setPattern(String pattern){
		this.pattern = pattern;
	}
	
	/**
	 * Retorna o identificador das opções de seleção do parâmetro da requisição.
	 * 
	 * @return String contendo o identificador das opções de seleção.
	 */
	public String getData(){
		return data;
	}
	
	/**
	 * Define o identificador das opções de seleção do parâmetro da requisição.
	 * 
	 * @param data String contendo o identificador das opções de seleção.
	 */
	public void setData(String data){
		this.data = data;
	}
	
	/**
	 * Retorna o escopo de armazenamento das opções de seleção do parâmetro da requisição.
	 * 
	 * @return Constante que define o escope de armazenamento.
	 */
	public ScopeType getDataScope(){
		return dataScope;
	}
	
	/**
	 * Define o escopo de armazenamento das opções de seleção do parâmetro da requisição.
	 * 
	 * @param dataScope Constante que define o escope de armazenamento.
	 */
	public void setDataScope(ScopeType dataScope){
		this.dataScope = dataScope;
	}
	
	/**
	 * Retorna o índice inicial das opções de seleção do parâmetro da requisição.
	 * 
	 * @return Valor inteiro contendo o índice inicial.
	 */
	public Integer getDataStartIndex(){
		return dataStartIndex;
	}
	
	/**
	 * Define o índice inicial das opções de seleção do parâmetro da requisição.
	 * 
	 * @param dataStartIndex Valor inteiro contendo o índice inicial.
	 */
	public void setDataStartIndex(Integer dataStartIndex){
		this.dataStartIndex = dataStartIndex;
	}
	
	/**
	 * Retorna o índice final das opções de seleção do parâmetro da requisição.
	 * 
	 * @return Valor inteiro contendo o índice final.
	 */
	public Integer getDataEndIndex(){
		return dataEndIndex;
	}
	
	/**
	 * Define o índice final das opções de seleção do parâmetro da requisição.
	 * 
	 * @param dataEndIndex Valor inteiro contendo o índice final.
	 */
	public void setDataEndIndex(Integer dataEndIndex){
		this.dataEndIndex = dataEndIndex;
	}

	/**
	 * Retorna o identificador da guia selecionada.
	 * 
	 * @return String contendo o identificador da guia selecionada.
	 */
    public String getCurrentGuide(){
        return currentGuide;
    }

    /**
     * Define o identificador da guia selecionada.
     * 
     * @param currentGuide String contendo o identificador da guia selecionada.
     */
    public void setCurrentGuide(String currentGuide){
        this.currentGuide = currentGuide;
    }

    /**
     * Retorna o identificador do nó selecionado no treeView (árvore).
     * 
     * @return String contendo o identificador do nó selecionado na treeView (árvore).
     */
    public String getCurrentNode(){
        return currentNode;
    }

    /**
     * Define o identificador do nó selecionado no treeView (árvore).
     * 
     * @param currentNode String contendo o identificador do nó selecionado na 
     * treeView (árvore).
     */
    public void setCurrentNode(String currentNode){
        this.currentNode = currentNode;
    }

    /**
     * Indica se o componente aceita múltipla seleção.
     * 
     * @return True/False.
     */
    public Boolean hasMultipleSelection(){
        return hasMultipleSelection;
    }

    /**
     * Indica se o componente aceita múltipla seleção.
     * 
     * @return True/False.
     */
    public Boolean getHasMultipleSelection(){
        return hasMultipleSelection();
    }

    /**
     * Define se o componente aceita múltipla seleção.
     * 
     * @param hasMultipleSelection True/False.
     */
    public void setHasMultipleSelection(Boolean hasMultipleSelection){
        this.hasMultipleSelection = hasMultipleSelection;
    }
}