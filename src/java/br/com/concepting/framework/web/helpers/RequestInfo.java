package br.com.concepting.framework.web.helpers;

import br.com.concepting.framework.util.types.PagerActionType;
import br.com.concepting.framework.util.types.SortOrderType;
import br.com.concepting.framework.web.types.ScopeType;

/**
 * Classe auxiliar que define as propriedades de um par�metro da requisi��o.
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
     * Retorna o identificador da se��o atual.
     * 
     * @return String contendo o identificador da se��o atual.
     */
    public String getCurrentSection(){
        return currentSection;
    }

    /**
     * Define o identificador da se��o atual.
     * 
     * @param currentSession String contendo o identificador da se��o atual.
     */
    public void setCurrentSection(String currentSection){
        this.currentSection = currentSection;
    }

    /**
     * Retorna a a��o de pagina��o executada.
     * 
     * @return Constante que define a a��o de pagina��o executada.
     */
	public PagerActionType getPagerAction(){
        return pagerAction;
    }

    /**
     * Define a a��o de pagina��o executada.
     * 
     * @param pagerAction Constante que define a a��o de pagina��o executada.
     */
    public void setPagerAction(PagerActionType pagerAction){
        this.pagerAction = pagerAction;
    }

    /**
	 * Retorna o identificador da coluna selecionada para ordena��o.
	 * 
	 * @return String contendo o identificador.
	 */
	public String getSortProperty(){
		return sortProperty;
	}
	
	/**
	 * Define o identificador da coluna selecionada para ordena��o.
	 * 
	 * @param sortProperty String contendo o identificador.
	 */
	public void setSortProperty(String sortProperty){
		this.sortProperty = sortProperty;
	}
	
	/**
	 * Retorna o identificador da propriedade que armazena os dados edit�veis.
	 * 
	 * @return String contendo o identificador da propriedade que armazena os dados edit�veis.
	 */
	public String getEditableData(){
		return editableData;
	}
	
	/**
	 * Define o identificador da propriedade que armazena os dados edit�veis.
	 * 
	 * @param editableData String contendo o identificador da propriedade que armazena os dados 
	 * edit�veis.
	 */
	public void setEditableData(String editableData){
		this.editableData = editableData;
	}
	
	/**
	 * Retorna o escopo da propriedade que armazena os dados edit�veis.
	 * 
	 * @return Inst�ncia que define o escopo da propriedade que armazena os dados edit�veis.
	 */
	public ScopeType getEditableDataScope(){
		return editableDataScope;
	}
	
	/**
	 * Define o escopo da propriedade que armazena os dados edit�veis.
	 * 
	 * @param editableDataScope Inst�ncia que define o escopo da propriedade que armazena os dados 
	 * edit�veis.
	 */
	public void setEditableDataScope(ScopeType editableDataScope){
		this.editableDataScope = editableDataScope;
	}
	
	/**
	 * Retorna a quantidade de itens por p�gina a serem exibidos.
	 * 
	 * @return Valor inteiro contendo a quantidade de itens por p�gina.
	 */
	public Integer getItemsPerPage(){
		return itemsPerPage;
	}
	
	/**
	 * Define a quantidade de itens por p�gina a serem exibidos.
	 * 
	 * @param itemsPerPage Valor inteiro contendo a quantidade de itens por p�gina.
	 */
	public void setItemsPerPage(Integer itemsPerPage){
		this.itemsPerPage = itemsPerPage;
	}
	
	/**
	 * Retorna o tipo de ordena��o a ser executado.
	 * 
	 * @return Constante contendo o tipo de ordena��o.
	 */
	public SortOrderType getSortOrder(){
		return sortOrder;
	}
	
	/**
	 * Define o tipo de ordena��o a ser executado.
	 * 
	 * @param sortOrder Constante contendo o tipo de ordena��o.
	 */
	public void setSortOrder(SortOrderType sortOrder){
		this.sortOrder = sortOrder;
	}
	
	/**
	 * Indica se o par�metro da requisi��o � uma coluna edit�vel.
	 * 
	 * @return True/False.
	 */
	public Boolean isEditable(){
		return isEditable;
	}
	
	/**
	 * Indica se o par�metro da requisi��o � uma coluna edit�vel.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsEditable(){
		return isEditable();
	}
	
	/**
	 * Define se o par�metro da requisi��o � uma coluna edit�vel.
	 * 
	 * @param isEditable True/False.
	 */
	public void setIsEditable(Boolean isEditable){
		this.isEditable = isEditable;
	}
	
	/**
	 * Retorna um array contendo os valores das op��es selecionadas.
	 * 
	 * @return Array contendo os valores das op��es selecionadas.
	 */
	public String[] getValues(){
		return values;
	}
	
	/**
	 * Define um array contendo os valores das op��es selecionadas.
	 * 
	 * @param values Array contendo os valores das op��es selecionadas.
	 */
	public void setValues(String[] values){
		this.values = values;
	}
	
	/**
	 * Retorna o nome do par�metro da requisi��o.
	 * 
	 * @return String contendo o nome do par�metro.
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Define o nome do par�metro da requisi��o.
	 * 
	 * @param name String contendo o nome do par�metro.
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Retorna o label do par�metro da requisi��o.
	 * 
	 * @return String contendo o label do par�metro.
	 */
	public String getLabel(){
		return label;
	}
	
	/**
	 * Define o label do par�metro da requisi��o.
	 * 
	 * @param label String contendo o label do par�metro.
	 */
	public void setLabel(String label){
		this.label = label;
	}
	
	/**
	 * Retorna o valor do par�metro da requisi��o.
	 * 
	 * @return String contendo o valor do par�metro.
	 */
	public String getValue(){
		return value;
	}
	
	/**
	 * Define o valor do par�metro da requisi��o.
	 * 
	 * @param value String contendo o valor do par�metro.
	 */
	public void setValue(String value){
		this.value = value;
	}
	
	/**
	 * Retorna a m�scara de formata��o do par�metro da requisi��o.
	 * 
	 * @return String contendo a m�scara de formata��o.
	 */
	public String getPattern(){
		return pattern;
	}
	 
	/**
	 * Define a m�scara de formata��o do par�metro da requisi��o.
	 * 
	 * @param pattern String contendo a m�scara de formata��o.
	 */
	public void setPattern(String pattern){
		this.pattern = pattern;
	}
	
	/**
	 * Retorna o identificador das op��es de sele��o do par�metro da requisi��o.
	 * 
	 * @return String contendo o identificador das op��es de sele��o.
	 */
	public String getData(){
		return data;
	}
	
	/**
	 * Define o identificador das op��es de sele��o do par�metro da requisi��o.
	 * 
	 * @param data String contendo o identificador das op��es de sele��o.
	 */
	public void setData(String data){
		this.data = data;
	}
	
	/**
	 * Retorna o escopo de armazenamento das op��es de sele��o do par�metro da requisi��o.
	 * 
	 * @return Constante que define o escope de armazenamento.
	 */
	public ScopeType getDataScope(){
		return dataScope;
	}
	
	/**
	 * Define o escopo de armazenamento das op��es de sele��o do par�metro da requisi��o.
	 * 
	 * @param dataScope Constante que define o escope de armazenamento.
	 */
	public void setDataScope(ScopeType dataScope){
		this.dataScope = dataScope;
	}
	
	/**
	 * Retorna o �ndice inicial das op��es de sele��o do par�metro da requisi��o.
	 * 
	 * @return Valor inteiro contendo o �ndice inicial.
	 */
	public Integer getDataStartIndex(){
		return dataStartIndex;
	}
	
	/**
	 * Define o �ndice inicial das op��es de sele��o do par�metro da requisi��o.
	 * 
	 * @param dataStartIndex Valor inteiro contendo o �ndice inicial.
	 */
	public void setDataStartIndex(Integer dataStartIndex){
		this.dataStartIndex = dataStartIndex;
	}
	
	/**
	 * Retorna o �ndice final das op��es de sele��o do par�metro da requisi��o.
	 * 
	 * @return Valor inteiro contendo o �ndice final.
	 */
	public Integer getDataEndIndex(){
		return dataEndIndex;
	}
	
	/**
	 * Define o �ndice final das op��es de sele��o do par�metro da requisi��o.
	 * 
	 * @param dataEndIndex Valor inteiro contendo o �ndice final.
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
     * Retorna o identificador do n� selecionado no treeView (�rvore).
     * 
     * @return String contendo o identificador do n� selecionado na treeView (�rvore).
     */
    public String getCurrentNode(){
        return currentNode;
    }

    /**
     * Define o identificador do n� selecionado no treeView (�rvore).
     * 
     * @param currentNode String contendo o identificador do n� selecionado na 
     * treeView (�rvore).
     */
    public void setCurrentNode(String currentNode){
        this.currentNode = currentNode;
    }

    /**
     * Indica se o componente aceita m�ltipla sele��o.
     * 
     * @return True/False.
     */
    public Boolean hasMultipleSelection(){
        return hasMultipleSelection;
    }

    /**
     * Indica se o componente aceita m�ltipla sele��o.
     * 
     * @return True/False.
     */
    public Boolean getHasMultipleSelection(){
        return hasMultipleSelection();
    }

    /**
     * Define se o componente aceita m�ltipla sele��o.
     * 
     * @param hasMultipleSelection True/False.
     */
    public void setHasMultipleSelection(Boolean hasMultipleSelection){
        this.hasMultipleSelection = hasMultipleSelection;
    }
}