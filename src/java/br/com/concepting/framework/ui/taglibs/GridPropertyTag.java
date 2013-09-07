package br.com.concepting.framework.ui.taglibs;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.controller.action.types.ActionType;
import br.com.concepting.framework.controller.helpers.RequestInfo;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.processors.ExpressionProcessor;
import br.com.concepting.framework.processors.ExpressionProcessorUtil;
import br.com.concepting.framework.security.constants.SecurityConstants;
import br.com.concepting.framework.ui.constants.TaglibConstants;
import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.Pager;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.util.types.ComponentType;
import br.com.concepting.framework.util.types.PagerActionType;
import br.com.concepting.framework.util.types.ScopeType;
import br.com.concepting.framework.util.types.SortOrderType;

/**
 * Classe que define o componente visual grid (tabela de dados).
 * 
 * @author fvilarinho
 * @since 2.0
 */
public class GridPropertyTag extends BaseOptionsPropertyTag{
	private String                    onSelect                         = "";
    private String                    onSelectAction                   = "";
    private String                    onSelectActionForward            = "";
    private String                    onSelectActionForwardOnFail      = "";
    private String                    onSelectActionUpdateViews        = "";
    private Boolean                   onSelectActionValidate           = false;
    private String                    onSelectActionValidateProperties = "";
	private Boolean                   showSelection                    = true;
	private String                    selectionColumnLabel             = "";
	private String                    headerLinkStyleClass             = "";
	private String                    headerLinkStyle                  = "";
	private String                    headerStyleClass                 = "";
	private String                    headerStyle                      = "";
	private String                    detailLinkStyleClass             = "";
	private String                    detailLinkStyle                  = "";
    private String                    detailStyleClass                 = "";
    private String                    detailStyle                      = "";
	private String                    aggregateStyleClass              = "";
	private String                    aggregateStyle                   = "";
	private Collection<GridColumnTag> columnsTags                      = null;
	private PagerTag                  pagerTag                         = null;
	private List<ButtonTag>           buttonsTags                      = null;
	private List<GridRowStateTag>     rowStatesTags                    = null;

    /**
     * Retorna o evento a ser executado quando uma opção de seleção for selecionada.
     * 
     * @return String contendo o evento a ser executado.
     */
    public String getOnSelect(){
        return onSelect;
    }

    /**
     * Define o evento a ser executado quando uma opção de seleção for selecionada.
     * 
     * @param onSelect String contendo o evento a ser executado.
     */
    public void setOnSelect(String onSelect){
        this.onSelect = onSelect;
    }
	
    /**
     * Retorna o identificador da ação do evento de seleção de um item.
     * 
     * @return String contendo o identificador da ação.
     */
    public String getOnSelectAction(){
        return onSelectAction;
    }

    /**
     * Define o identificador da ação do evento de seleção de um item.
     * 
     * @param onSelectAction String contendo o identificador da ação.
     */
    public void setOnSelectAction(String onSelectAction){
        this.onSelectAction = onSelectAction;
    }

    /**
     * Retorna o identificador do redirecionamento da ação do evento de seleção de um item.
     * 
     * @return String contendo o identificador da redirecionamento.
     */
    public String getOnSelectActionForward(){
        return onSelectActionForward;
    }

    /**
     * Define o identificador do redirecionamento da ação do evento de seleção de um item.
     * 
     * @param onSelectActionForward String contendo o identificador da redirecionamento.
     */
    public void setOnSelectActionForward(String onSelectActionForward){
        this.onSelectActionForward = onSelectActionForward;
    }

    /**
     * Retorna o identificador do redirecionamento em caso de falha da ação do evento de seleção de um item.
     * 
     * @return String contendo o identificador da redirecionamento.
     */
    public String getOnSelectActionForwardOnFail(){
        return onSelectActionForwardOnFail;
    }

    /**
     * Define o identificador do redirecionamento em caso de falha da ação do evento de seleção de um item.
     * 
     * @param onSelectActionForwardOnFail String contendo o identificador da redirecionamento.
     */
    public void setOnSelectActionForwardOnFail(String onSelectActionForwardOnFail){
        this.onSelectActionForwardOnFail = onSelectActionForwardOnFail;
    }

    /**
     * Retorna os identificadores das views que serão atualizadas após a execução da ação de seleção de um item.
     *  
     * @return String contendo os identificadores das views.
     */
    public String getOnSelectActionUpdateViews(){
        return onSelectActionUpdateViews;
    }

    /**
     * Define os identificadores das views que serão atualizadas após a execução da ação de seleção de um item.
     *  
     * @param onSelectActionUpdateViews String contendo os identificadores das views.
     */
    public void setOnSelectActionUpdateViews(String onSelectActionUpdateViews){
        this.onSelectActionUpdateViews = onSelectActionUpdateViews;
    }

    /**
     * Indica se o modelo de dados do formulário deve ser validado na execução da ação de seleção de um item.
     *  
     * @return True/False.
     */
    public Boolean getOnSelectActionValidate(){
        return onSelectActionValidate;
    }

    /**
     * Define se o modelo de dados do formulário deve ser validado na execução da ação de seleção de um item.
     *  
     * @param onSelectActionValidate True/False.
     */
    public void setOnSelectActionValidate(Boolean onSelectActionValidate){
        this.onSelectActionValidate = onSelectActionValidate;
    }

    /**
     * Retorna as propriedades do modelo de dados do formulário deve ser validado na execução da ação de seleção de um item.
     *  
     * @return String contendo os identificadores das propriedades.
     */
    public String getOnSelectActionValidateProperties(){
        return onSelectActionValidateProperties;
    }

    /**
     * Define as propriedades do modelo de dados do formulário deve ser validado na execução da ação de seleção de um item.
     *  
     * @param onSelectActionValidateProperties String contendo os identificadores das propriedades.
     */
    public void setOnSelectActionValidateProperties(String onSelectActionValidateProperties){
        this.onSelectActionValidateProperties = onSelectActionValidateProperties;
    }

    /**
	 * Retorna o estilo CSS para o agrupamento.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getAggregateStyleClass(){
    	return aggregateStyleClass;
    }

	/**
	 * Define o estilo CSS para o agrupamento.
	 * 
	 * @param aggregateStyleClass String contendo o estilo CSS.
	 */
	public void setAggregateStyleClass(String aggregateStyleClass){
    	this.aggregateStyleClass = aggregateStyleClass;
    }

	/**
	 * Retorna o estilo CSS para o agrupamento.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getAggregateStyle(){
    	return aggregateStyle;
    }

	/**
	 * Define o estilo CSS para o agrupamento.
	 * 
	 * @param aggregateStyle String contendo o estilo CSS.
	 */
	public void setAggregateStyle(String aggregateStyle){
    	this.aggregateStyle = aggregateStyle;
    }

	/**
	 * Retorna a lista de colunas do componente.
	 *
	 * @return Lista contendo as colunas.
	 */
	public Collection<GridColumnTag> getColumnsTags(){
    	return columnsTags;
    }

	/**
	 * Define a lista de colunas do componente.
	 *
	 * @param columnsTags Lista contendo as colunas.
	 */
	public void setColumnsTags(Collection<GridColumnTag> columnsTags){
    	this.columnsTags = columnsTags;
    }

	/**
	 * Retorna a lista de botões do componente.
	 *
	 * @return Lista contendo os botões.
	 */
	public List<ButtonTag> getButtonsTags(){
    	return buttonsTags;
    }

	/**
	 * Define a lista de botões do componente.
	 *
	 * @param buttonsTags Lista contendo os botões.
	 */
	public void setButtonsTags(List<ButtonTag> buttonsTags){
    	this.buttonsTags = buttonsTags;
    }

	/**
	 * Retorna a lista de características das linhas do componente.
	 *
	 * @return Lista contendo as características da linha.
	 */
	public List<GridRowStateTag> getRowStatesTags(){
    	return rowStatesTags;
    }

	/**
	 * Define a lista de características das linhas do componente.
	 *
	 * @param rowStatesTags Lista contendo as características da linha.
	 */
	public void setRowStatesTags(List<GridRowStateTag> rowStatesTags){
    	this.rowStatesTags = rowStatesTags;
    }

	/**
	 * Retorna o estilo CSS para o link do detalhe.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getDetailLinkStyleClass(){
		return detailLinkStyleClass;
	}

	/**
	 * Define o estilo CSS para o link do detalhe.
	 * 
	 * @param detailLinkStyleClass String contendo o estilo CSS.
	 */
	public void setDetailLinkStyleClass(String detailLinkStyleClass){
		this.detailLinkStyleClass = detailLinkStyleClass;
	}

	/**
	 * Retorna o estilo CSS para o link do detalhe.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getDetailLinkStyle(){
		return detailLinkStyle;
	}

	/**
	 * Define o estilo CSS para o link do detalhe.
	 * 
	 * @param detailLinkStyle String contendo o estilo CSS.
	 */
	public void setDetailLinkStyle(String detailLinkStyle){
		this.detailLinkStyle = detailLinkStyle;
	}

	/**
	 * Retorna o estilo CSS para o link do cabeçalho.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getHeaderLinkStyle(){
		return headerLinkStyle;
	}

	/**
	 * Define o estilo CSS para o link do cabeçalho.
	 * 
	 * @param headerLinkStyle String contendo o estilo CSS.
	 */
	public void setHeaderLinkStyle(String headerLinkStyle){
		this.headerLinkStyle = headerLinkStyle;
	}

	/**
	 * Retorna o estilo CSS para o link do cabeçalho.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getHeaderLinkStyleClass(){
		return headerLinkStyleClass;
	}

	/**
	 * Define o estilo CSS para o link do cabeçalho.
	 * 
	 * @param headerLinkStyleClass String contendo o estilo CSS.
	 */
	public void setHeaderLinkStyleClass(String headerLinkStyleClass){
		this.headerLinkStyleClass = headerLinkStyleClass;
	}

	/**
	 * Retorna o estilo CSS para o detalhe.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getDetailStyleClass(){
		return detailStyleClass;
	}

	/**
	 * Define o estilo CSS para o detalhe.
	 * 
	 * @param detailStyleClass String contendo o estilo CSS.
	 */
	public void setDetailStyleClass(String detailStyleClass){
		this.detailStyleClass = detailStyleClass;
	}

	/**
	 * Retorna o estilo CSS para o cabeçalho.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getHeaderStyleClass(){
		return headerStyleClass;
	}

	/**
	 * Define o estilo CSS para o cabeçalho.
	 * 
	 * @param headerStyleClass String contendo o estilo CSS.
	 */
	public void setHeaderStyleClass(String headerStyleClass){
		this.headerStyleClass = headerStyleClass;
	}

	/**
	 * Retorna o estilo CSS para o detalhe.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getDetailStyle(){
		return detailStyle;
	}

	/**
	 * Define o estilo CSS para o detalhe.
	 * 
	 * @param detailStyle String contendo o estilo CSS.
	 */
	public void setDetailStyle(String detailStyle){
		this.detailStyle = detailStyle;
	}

	/**
	 * Retorna o estilo CSS para o cabeçalho.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getHeaderStyle(){
		return headerStyle;
	}

	/**
	 * Define o estilo CSS para o cabeçalho.
	 * 
	 * @param headerStyle String contendo o estilo CSS.
	 */
	public void setHeaderStyle(String headerStyle){
		this.headerStyle = headerStyle;
	}

	/**
	 * Indica se o componente deve conter as opções de seleção.
	 * 
	 * @return True/False.
	 */
	public Boolean showSelection(){
		return showSelection;
	}
	
    /**
     * Indica se o componente deve conter as opções de seleção.
     * 
     * @return True/False.
     */
    public Boolean isShowSelection(){
        return showSelection();
    }

    /**
     * Indica se o componente deve conter as opções de seleção.
     * 
     * @return True/False.
     */
    public Boolean getShowSelection(){
        return isShowSelection();
    }

	/**
	 * Define se o componente deve conter as opções de seleção.
	 * 
	 * @param showSelection True/False.
	 */
	public void setShowSelection(Boolean showSelection){
		this.showSelection = showSelection;
	}

	/**
	 * Retorna o label da coluna de seleção das opções.
	 * 
	 * @return String contendo o label da coluna de seleção das opções.
	 */
	public String getSelectionColumnLabel(){
		return selectionColumnLabel;
	}

	/**
	 * Define o label da coluna de seleção das opções.
	 * 
	 * @param selectionColumnLabel String contendo o label da coluna de seleção das opções.
	 */
	public void setSelectionColumnLabel(String selectionColumnLabel){
		this.selectionColumnLabel = selectionColumnLabel;
	}

	/**
	 * Define a instância do componente visual de paginação.
	 * 
	 * @param pagerTag Instância contendo as propriedades do componente visual de paginação.
	 */
	protected void setPagerTag(PagerTag pagerTag){
		if(pagerTag != null){
		    PageContext pageContext = getPageContext();
		    PrintWriter out         = getOut();
		    
			pagerTag.setPageContext(pageContext);
			pagerTag.setOut(out);
            pagerTag.setParent(null);
		}

		this.pagerTag = pagerTag;
	}

	/**
	 * Retorna a instância do componente visual de paginação.
	 * 
	 * @return Instância contendo as propriedades do componente visual de paginação.
	 */
	protected PagerTag getPagerTag(){
		return pagerTag;
	}

	/**
	 * Adiciona um botão ao componente.
	 * 
	 * @param buttonTag Instância contendo as propriedades do botão.
	 */
	protected void addButtonTag(ButtonTag buttonTag){
		if(buttonsTags == null)
			buttonsTags = new LinkedList<ButtonTag>();

        PageContext pageContext = getPageContext();
        PrintWriter out         = getOut();
        
        buttonTag.setPageContext(pageContext);
        buttonTag.setOut(out);
        buttonTag.setParent(null);

        buttonsTags.add(buttonTag);
	}

	/**
	 * Adiciona uma coluna ao componente.
	 * 
	 * @param columnTag Instância contendo as propriedades do botão.
	 */
	protected void addColumnTag(GridColumnTag columnTag){
		if(columnsTags == null)
			columnsTags = new LinkedList<GridColumnTag>();

		columnsTags.add(columnTag);
	}

	/**
	 * Adiciona uma característica para as linhas do componente.
	 * 
	 * @param rowStateTag Instância contendo a característica desejada.
	 */
	protected void addRowStateTag(GridRowStateTag rowStateTag){
		if(rowStatesTags == null)
			rowStatesTags = new LinkedList<GridRowStateTag>();

		rowStatesTags.add(rowStateTag);
	}
	
	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseOptionsPropertyTag#initialize()
	 */
	protected void initialize() throws Throwable{
	    setComponentType(ComponentType.GRID);
	    
        super.initialize();
    }

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseActionFormElementTag#renderOpen()
	 */
	protected void renderOpen() throws Throwable{
	    PagerTag pagerTag = getPagerTag();
	    
		if(pagerTag != null){
		    Pager pager = pagerTag.getPager();
		    
		    setDataStartIndex(pager.getStartIndex());
			setDataEndIndex(pager.getEndIndex());
		}
		else{
		    setDataStartIndex(0);
		    setDataEndIndex(0);
		    
			Collection dataValues = getDataValues();
			
			if(dataValues != null && dataValues.size() > 0)
				setDataEndIndex(dataValues.size() - 1);
		}
		
        renderLabelAttribute();
        renderPatternAttribute();
        renderDataAttributes();
        renderDataIndexesAttributes();
		renderHeader();
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#renderBody()
	 */
	protected void renderBody() throws Throwable{
		renderDetails();
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BasePropertyTag#renderClose()
	 */
	protected void renderClose() throws Throwable{
		renderFooter();
	}
	
	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseOptionsPropertyTag#renderDataAttributes()
	 */
	protected void renderDataAttributes() throws Throwable{
		super.renderDataAttributes();
		
		PropertyInfo propertyInfo = getPropertyInfo();
		
        if(propertyInfo != null){
    		String        name    = getName();
    		StringBuilder tagName = new StringBuilder();
    		
            tagName.append(name);
            tagName.append(".");
            tagName.append(AttributeConstants.EDITABLE_DATA_KEY);
    
            HiddenPropertyTag editableDataTag = new HiddenPropertyTag();
    
            editableDataTag.setPageContext(pageContext);
    		editableDataTag.setName(tagName.toString());
    		editableDataTag.setValue(getData());
    		editableDataTag.doStartTag();
    		editableDataTag.doEndTag();
    
            tagName.delete(0, tagName.length());
            tagName.append(name);
            tagName.append(".");
            tagName.append(AttributeConstants.EDITABLE_DATA_SCOPE_KEY);
    
            HiddenPropertyTag editableDataScopeTag = new HiddenPropertyTag();
    
            editableDataScopeTag.setPageContext(pageContext);
    		editableDataScopeTag.setName(tagName.toString());
    		editableDataScopeTag.setValue(getDataScope());
    		editableDataScopeTag.doStartTag();
    		editableDataScopeTag.doEndTag();
		
    		if(propertyInfo.isCollection()){
    			HiddenPropertyTag noItemSelectedTag = new HiddenPropertyTag();
    
    			noItemSelectedTag.setPageContext(pageContext);
    			noItemSelectedTag.setName(name);
    			noItemSelectedTag.doStartTag();
    			noItemSelectedTag.doEndTag();
    		}
		}
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseOptionsPropertyTag#renderDataIndexesAttributes()
	 */
	protected void renderDataIndexesAttributes() throws Throwable{
	    super.renderDataIndexesAttributes();

        Collection dataValues = getDataValues();
	    
	    if(getPropertyInfo() != null && dataValues != null && dataValues.size() > 0){
    	    String        name       = getName();
    		StringBuilder tagName    = new StringBuilder();
    		
            tagName.append(name);
            tagName.append(".");
            tagName.append(AttributeConstants.SORT_PROPERTY_KEY);
    
            RequestInfo       requestInfo     = getRequestInfo();
            String            sortProperty    = requestInfo.getSortProperty();
            HiddenPropertyTag sortPropertyTag = new HiddenPropertyTag();
    
            sortPropertyTag.setPageContext(pageContext);
    		sortPropertyTag.setName(tagName.toString());
    		sortPropertyTag.setValue(sortProperty);
    		sortPropertyTag.doStartTag();
    		sortPropertyTag.doEndTag();
    
            tagName.delete(0, tagName.length());
            tagName.append(name);
            tagName.append(".");
            tagName.append(AttributeConstants.SORT_ORDER_KEY);
    
            SortOrderType     sortOrder    = requestInfo.getSortOrder();
            HiddenPropertyTag sortOrderTag = new HiddenPropertyTag();
    
            sortOrderTag.setPageContext(pageContext);
    		sortOrderTag.setName(tagName.toString());
    		sortOrderTag.setValue(sortOrder);
    		sortOrderTag.doStartTag();
    		sortOrderTag.doEndTag();
	    }
	}

	/**
	 * Renderiza o rodapé (controles) do componente.
	 * 
	 * @throws Throwable
	 */
	private void renderFooter() throws Throwable{
		println("<tr>");
		print("<td");
		
		if(columnsTags != null && columnsTags.size() > 0){
		    Integer columnsSize = columnsTags.size();
		    
		    if(showSelection())
		        columnsSize++;
		    
			print(" colspan=\"");
			print(columnsSize);
			print("\"");
		}
		
        print(" align=\"");
        print(AlignmentType.CENTER);
        println("\">");

		renderControls();

		println("</td>");
		println("</tr>");
		println("</table>");

		println("</td>");
		println("</tr>");
		println("</table>");
	}

	/**
	 * Renderiza os detalhes (dados) do componente.
	 * 
	 * @throws Throwable
	 */
    private void renderDetails() throws Throwable{
	    PropertyInfo propertyInfo     = getPropertyInfo();
	    List         dataValues       = getDataValues();
        String       columnStyleClass = "";
	    
		if(propertyInfo != null && ((propertyInfo.isModel() || propertyInfo.hasModel()) || (!propertyInfo.isModel() && !propertyInfo.hasModel())) && dataValues != null && dataValues.size() > 0){
		    String                   name                          = getName();
            RequestInfo              requestInfo                   = getRequestInfo();
		    Integer                  dataStartIndex                = getDataStartIndex();
		    Integer                  dataEndIndex                  = getDataEndIndex();
            Locale                   currentLanguage               = systemController.getCurrentLanguage();
			BaseModel                currentModel                  = null;
            StringBuilder            tagName                       = null;
            StringBuilder            optionTagId                   = null;
			BaseOptionPropertyTag    optionTag                     = null;
			Object                   optionValue                   = null;
			ImageTag                 imageTag                      = null;
            BasePropertyTag          editableDataColumnTag         = null;
            GridColumnGroupTag       columnGroupTag                = null;
			PropertyInfo             columnPropertyInfo            = null;
			String                   columnName                    = "";
			String                   columnLabel                   = "";
			Integer                  columnSize                    = 0;
			Object                   columnValue                   = null;
			String                   columnValueLabel              = "";
			Map                      columnValueMapInstance        = null;
			String                   columnData                    = "";
			ScopeType                columnDataScope               = null;
			Boolean                  columnEditable                = false;
			Boolean                  columnHasMultipleLines        = false;
			String                   columnLinkStyleClass          = "";
			String                   columnLinkStyle               = "";
			String                   columnStyle                   = "";
			Integer                  columnRows                    = 0;
			Integer                  columnColumns                 = 0;
			AlignmentType            columnAlignment               = null;   
			String                   columnWidth                   = "";
			String                   columnPattern                 = "";
			Boolean                  columnUseAdditionalFormatting = false;
			Integer                  columnPrecision               = 0;
			String                   columnLink                    = "";
			String                   columnOnSelect                = "";
			String                   columnOnBlur                  = "";
			String                   columnOnClick                 = "";
			StringBuilder            columnOnClickContent          = null;
			String                   columnOnChange                = "";
			String                   columnOnFocus                 = "";
			String                   columnOnKeyPress              = "";
			String                   columnOnKeyUp                 = "";
			String                   columnOnKeyDown               = "";
			String                   columnOnMouseOver             = "";
			String                   columnOnMouseOut              = "";
			String                   columnTooltip                 = "";
            Boolean                  rowRemove                     = false;
			GridRowStateTag          rowStateTag                   = null;
			GridColumnStateTag       columnStateTag                = null;
			List<GridColumnStateTag> columnStatesTags              = null;
			Boolean                  expressionResult              = false;
			ExpressionProcessor      expressionProcessor           = getExpressionProcessor();
			String                   aggregates[]                  = null;
			Object                   aggregateValue                = null;
			Object                   aggregatesValues[]            = null;
			SortOrderType            aggregatesSortOrders[]        = null;
			List<String>             aggregatesList                = new LinkedList<String>();
			Integer                  aggregateIndex                = 0;
			String                   aggregateStyleClass           = "";
			String                   aggregateStyle                = "";
			
			if(columnsTags != null && columnsTags.size() > 0){
				for(GridColumnTag columnTag : columnsTags){
					if(columnTag.getParent() instanceof GridColumnGroupTag)
    					columnGroupTag = (GridColumnGroupTag)columnTag.getParent();
					else
						columnGroupTag = null;
    					
    				if(columnTag.getName().length() > 0 && columnGroupTag != null && columnGroupTag.aggregate())
    				    aggregatesList.add(columnTag.getName());
				}
				
				if(requestInfo.getSortProperty().length() > 0)
				    aggregatesList.add(requestInfo.getSortProperty());
				
				if(dataValues != null){
					if(aggregatesList.size() > 0){
						aggregatesValues     = new Object[aggregatesList.size()];
						aggregatesSortOrders = new SortOrderType[aggregatesList.size()];
                        aggregates           = new String[aggregatesList.size()];
						aggregates           = aggregatesList.toArray(aggregates); 
						
						if(requestInfo.getSortProperty().length() > 0)
							aggregatesSortOrders[aggregatesSortOrders.length - 1] = requestInfo.getSortOrder();
						
						dataValues = ModelUtil.aggregateAndSort(dataValues, aggregates, aggregatesSortOrders);
					}
					else{
						if(requestInfo.getSortProperty().length() > 0)
							ModelUtil.sort(dataValues, requestInfo.getSortProperty(), requestInfo.getSortOrder());
					}
					
					setDataValues(dataValues);
				}
    
    			ExpressionProcessorUtil.addVariable(SecurityConstants.LOGIN_SESSION_KEY, securityController.getLoginSession());
    			
    			for(Integer cont1 = dataStartIndex ; cont1 <= dataEndIndex ; cont1++){
    				ExpressionProcessorUtil.addVariable(AttributeConstants.ROW_NUMBER_KEY, cont1);
    
    				currentModel = (BaseModel)(cont1 < dataValues.size() ? dataValues.get(cont1) : null);
    				if(currentModel != null){
    					expressionProcessor.setDeclaration(currentModel);
    					
    					rowStateTag    = null;
    					columnStateTag = null;
    					
    					if(rowStatesTags != null){
    					    rowStateTag      = null;
    					    expressionResult = false;
    					    
    						for(int cont2 = 0 ; cont2 < rowStatesTags.size() ; cont2++){
    						    rowStateTag = rowStatesTags.get(cont2);
    						    
    							try{
        							expressionResult = expressionProcessor.evaluate(rowStateTag.getExpression());
        							if(expressionResult)
        							    break;
    							}
    							catch(Throwable e){
    							}
    						}
    					}
    					
    					if(!expressionResult)
    					    rowStateTag = null;
    					
                        columnLinkStyleClass = "";
                        columnLinkStyle      = "";
                        columnStyleClass     = "";
                        columnStyle          = "";

                        if(rowStateTag != null){
    					    rowRemove            = rowStateTag.remove();
                            columnLinkStyleClass = rowStateTag.getLinkStyleClass();
                            columnLinkStyle      = rowStateTag.getLinkStyle();
                            columnStyleClass     = rowStateTag.getStyleClass();
                            columnStyle          = rowStateTag.getStyle();
                            columnLinkStyleClass = PropertyUtil.fillPropertiesInString(currentModel, columnLinkStyleClass);
                            columnLinkStyle      = PropertyUtil.fillPropertiesInString(currentModel, columnLinkStyle);
                            columnStyleClass     = PropertyUtil.fillPropertiesInString(currentModel, columnStyleClass);
                            columnStyle          = PropertyUtil.fillPropertiesInString(currentModel, columnStyle);
                            columnLinkStyleClass = ExpressionProcessorUtil.fillVariablesInString(columnLinkStyleClass);
                            columnLinkStyle      = ExpressionProcessorUtil.fillVariablesInString(columnLinkStyle);
                            columnStyleClass     = ExpressionProcessorUtil.fillVariablesInString(columnStyleClass);
                            columnStyle          = ExpressionProcessorUtil.fillVariablesInString(columnStyle);
    					}
    					
                        if(!rowRemove){
        					if(aggregates != null && aggregates.length > 0){
        						aggregateIndex = 0;
        						
        						for(GridColumnTag columnTag : columnsTags){
        						    columnName             = columnTag.getName();
        						    columnPattern          = columnTag.getPattern();
        						    columnValueMapInstance = columnTag.getValueMapInstance();
        						    
        						    try{
            							columnPropertyInfo            = PropertyUtil.getPropertyInfo(currentModel, columnTag.getName());
            							columnUseAdditionalFormatting = columnPropertyInfo.useAdditionalFormatting();
            							columnPrecision               = columnPropertyInfo.getPrecision();
            							
            							if(columnPattern.length() == 0)
            							    columnPattern = columnPropertyInfo.getPattern();
            							
                                        if(columnPattern.length() == 0)
                                            columnPattern = NumberUtil.getDefaultPattern(columnPropertyInfo.getClazz(), columnUseAdditionalFormatting, columnPrecision);
        						    }
        						    catch(Throwable e){
        						        columnPropertyInfo            = null;
        						        columnUseAdditionalFormatting = false;
        						        columnPrecision               = Constants.DEFAULT_NUMBER_PRECISION;
        						    }
            							
        							if(columnTag.getParent() instanceof GridColumnGroupTag)
        								columnGroupTag = (GridColumnGroupTag)columnTag.getParent();
        							else
        								columnGroupTag = null;
            							
        							if(columnGroupTag != null && columnGroupTag.aggregate()){
            							aggregateValue = aggregatesValues[aggregateIndex];
        
        							    try{
        							        columnValue = PropertyUtil.getProperty(currentModel, columnName);
        							    }
        							    catch(Throwable e){
            							    columnValue = columnTag.getValue();
            							}
                								
        							    columnValueLabel = PropertyUtil.format(columnValue, columnValueMapInstance, columnPattern, columnUseAdditionalFormatting, columnPrecision, currentLanguage);
        							    
                                        if(aggregateValue == null || !aggregateValue.equals(columnValueLabel)){
        		    						aggregateStyleClass = columnGroupTag.getStyleClass();
        		    						
                                            if(aggregateStyleClass.length() == 0)
                                                aggregateStyleClass = getAggregateStyleClass();
                                            
                                            if(aggregateStyleClass.length() == 0)
                                                aggregateStyleClass = TaglibConstants.DEFAULT_GRID_AGGREGATE_STYLE_CLASS;
                                            
                                            aggregateStyle = columnGroupTag.getStyle();
                                            
                                            if(aggregateStyle.length() == 0)
                                                aggregateStyle = detailStyle;
                                            
                                            println("<tr>");
                                            print("<td class=\"");
                                            print(aggregateStyleClass);
        	    							print("\"");
        	    							
        		    						if(aggregateStyle.length() > 0){
        		    							print(" style=\"");
        	    								print(aggregateStyle);
        	    								print("\"");
        		    						}
        		    						
    	    								print(" colspan=\"");
        	    							print((columnsTags.size() + (showSelection() ? 1 : 0)) - aggregates.length);
        	    							print("\">&nbsp;");
        	    							println(columnValueLabel);
        									println("</td>");
        									println("</tr>");
        									
        									aggregatesValues[aggregateIndex] = columnValueLabel;
        									
        									for(Integer pos = (aggregateIndex + 1) ; pos < aggregatesValues.length ; pos++)
        										aggregatesValues[pos] = null;
        								}
            							
                                        aggregateIndex++;
        						    }
        						}
        					}
        
        					println("<tr>");
    

                            if(showSelection()){
                                if(columnStyleClass.length() == 0)
                                    columnStyleClass = detailStyleClass;
                                
                                if(detailStyleClass.length() == 0)
                                    columnStyleClass = TaglibConstants.DEFAULT_GRID_DETAIL_STYLE_CLASS;

                                if(columnStyle.length() == 0)
                                    columnStyle = detailStyle;
                                
                                print("<td class=\"");
    						    print(columnStyleClass);
       							print("\"");
    
       							if(columnStyle.length() > 0){
    								print(" style=\"");
    								print(columnStyle);
    								print("\"");
    							}
        
        						print(" align=\"");
        						print(AlignmentType.CENTER);
        						println("\" width=\"1\">");
        
                                if(optionTagId == null)
                                    optionTagId = new StringBuilder();
                                else
                                    optionTagId.delete(0, optionTagId.length());
        
                                optionTagId.append(name);
                                optionTagId.append(cont1);
    
                                if(propertyInfo.isCollection())
        							optionTag = new CheckPropertyTag();
        						else
        							optionTag = new RadioPropertyTag();
        
                                optionTag.setPageContext(pageContext);
        						optionTag.setId(optionTagId.toString());
        						optionTag.setName(name);
        						optionTag.setShowLabel(false);
        
        						optionValue = currentModel;
        						
        						if(propertyInfo.isModel() || propertyInfo.hasModel())
        							optionTag.setOptionIndex(cont1);
        						else
        						    optionValue = PropertyUtil.getProperty(optionValue, propertyInfo.getId());
        						
                                optionTag.setOptionValue(optionValue);
        
        						columnOnSelect = PropertyUtil.fillPropertiesInString(currentModel, onSelect, currentLanguage);
        						columnOnSelect = ExpressionProcessorUtil.fillVariablesInString(columnOnSelect, currentLanguage);
        
        						optionTag.setMultipleSelection(hasMultipleSelection());
        						optionTag.setOnClick(columnOnSelect);
        						optionTag.setOnClickAction(onSelectAction);
        						optionTag.setOnClickActionForward(onSelectActionForward);
        						optionTag.setOnClickActionForwardOnFail(onSelectActionForwardOnFail);
        						optionTag.setOnClickActionUpdateViews(onSelectActionUpdateViews);
        						optionTag.setOnClickActionValidate(onSelectActionValidate);
        						optionTag.setOnClickActionValidateProperties(onSelectActionValidateProperties);
        						optionTag.setEnabled(isEnabled());
                                optionTag.setParent(this);
        						optionTag.doStartTag();
        						optionTag.doEndTag();
        
        						println("</td>");
        					}
        					
    						for(GridColumnTag columnTag : columnsTags){
                                columnSize             = columnTag.getSize();
                                columnRows             = columnTag.getRows();
                                columnColumns          = columnTag.getColumns();
    						    columnName             = columnTag.getName();
    						    columnLabel            = columnTag.getLabel();
    						    columnWidth            = columnTag.getWidth();
    						    columnLink             = columnTag.getLink();
                                columnData             = columnTag.getData();
                                columnDataScope        = columnTag.getDataScopeType();
                                columnOnBlur           = columnTag.getOnBlur();
                                columnOnFocus          = columnTag.getOnFocus();
                                columnOnChange         = columnTag.getOnChange();
                                columnOnClick          = columnTag.getOnClick();
                                columnOnKeyPress       = columnTag.getOnKeyPress();
                                columnOnKeyUp          = columnTag.getOnKeyUp();
                                columnOnKeyDown        = columnTag.getOnKeyDown();
                                columnOnMouseOver      = columnTag.getOnMouseOver();
                                columnOnMouseOut       = columnTag.getOnMouseOut();
                                columnPattern          = columnTag.getPattern();
                                columnEditable         = false;
                                columnHasMultipleLines = false;
                                
                                try{
                                    columnPropertyInfo = PropertyUtil.getPropertyInfo(currentModel, columnName);
                                }
                                catch(Throwable e){
                                    columnPropertyInfo = null;                                  
                                }

                                if(columnPattern.length() == 0)
                                    if(columnPropertyInfo != null)
                                        columnPattern = columnPropertyInfo.getPattern();
                                
                                if(columnPropertyInfo != null)
                                    columnUseAdditionalFormatting = columnPropertyInfo.useAdditionalFormatting();

                                if(columnPropertyInfo != null)
                                    columnPrecision = columnPropertyInfo.getPrecision();

                                if(columnPattern.length() == 0)
                                    if(columnPropertyInfo != null)
                                        columnPattern = NumberUtil.getDefaultPattern(columnPropertyInfo.getClazz(), columnUseAdditionalFormatting, columnPrecision);

                                if(columnTag.isEditable() != null)
                                    columnEditable = columnTag.isEditable();
                                
                                if(columnTag.hasMultipleLines() != null)
                                    columnHasMultipleLines = columnTag.hasMultipleLines();

                                columnValue = columnTag.getValue();

                                try{
                                    if(columnValue == null)
                                        columnValue = PropertyUtil.getProperty(currentModel, columnName);
                                    else
                                        columnValue = expressionProcessor.evaluate(columnValue.toString());
                                }
                                catch(Throwable e){
                                    columnValue        = getInvalidPropertyMessage();
                                    columnPropertyInfo = null;
                                } 

                                columnValueMapInstance = columnTag.getValueMapInstance();

                                if(columnTag.getParent() instanceof GridColumnGroupTag)
    								columnGroupTag = (GridColumnGroupTag)columnTag.getParent();
    							else
    								columnGroupTag = null;

								if(columnGroupTag == null || !columnGroupTag.aggregate()){
								    columnStatesTags = columnTag.getColumnStatesTags();
								    columnStateTag   = null;
								    expressionResult = false;
								    
        							if(columnStatesTags != null && columnStatesTags.size() > 0){
        								for(int cont2 = 0 ; cont2 < columnStatesTags.size() ; cont2++){
        								    columnStateTag = columnStatesTags.get(cont2);
        								    
        									try{
            									expressionResult = expressionProcessor.evaluate(columnStateTag.getExpression()); 
            									if(expressionResult)
            									    break;
            								}
        									catch(Throwable e){
        									}
        								}
        							}
        							
        							if(!expressionResult)
        							    columnStateTag = null;
        							
        							if(columnStateTag != null){
										if(columnStateTag.isEditable() != null)
											columnEditable = columnStateTag.isEditable();
            										
										if(columnStateTag.hasMultipleLines() != null)
											columnHasMultipleLines = columnStateTag.hasMultipleLines();
            										
                                        if(columnStateTag.getSize() > 0)
                                            columnSize = columnStateTag.getSize();

                                        if(columnStateTag.getRows() > 0)
											columnRows = columnStateTag.getRows();
            										
										if(columnStateTag.getColumns() > 0)
											columnColumns = columnStateTag.getColumns();
            										
										if(columnStateTag.getPattern().length() > 0)
											columnPattern = columnStateTag.getPattern();
            										
										if(columnStateTag.getOnBlur().length() > 0)
											columnOnBlur = columnStateTag.getOnBlur();
            										
										if(columnStateTag.getOnFocus().length() > 0)
											columnOnFocus = columnStateTag.getOnFocus();
										
										if(columnStateTag.getOnChange().length() > 0)
											columnOnChange = columnStateTag.getOnChange();
										
										if(columnStateTag.getOnClick().length() > 0)
											columnOnClick = columnStateTag.getOnClick();
										
										if(columnStateTag.getOnKeyPress().length() > 0)
											columnOnKeyPress = columnStateTag.getOnKeyPress();
										
										if(columnStateTag.getOnKeyUp().length() > 0)
											columnOnKeyUp = columnStateTag.getOnKeyUp();
										
										if(columnStateTag.getOnKeyDown().length() > 0)
											columnOnKeyDown = columnStateTag.getOnKeyDown();
										
										if(columnStateTag.getOnMouseOver().length() > 0)
											columnOnMouseOver = columnStateTag.getOnMouseOver();
										
										if(columnStateTag.getOnMouseOut().length() > 0)
											columnOnMouseOut = columnStateTag.getOnMouseOut();

    									if(columnStateTag.getData().length() > 0)
    										columnData = columnStateTag.getData();
    									
    									if(columnStateTag.getDataScope() != null)
    										columnDataScope = columnStateTag.getDataScopeType();

    									if(columnStateTag.getValue() != null){
    										try{
    											columnValue = expressionProcessor.evaluate(StringUtil.trim(columnStateTag.getValue()));
    										}
    										catch(Throwable e){
    										}
        								}
        							}
        							
        		                    if(columnStateTag != null){
        							    columnLinkStyleClass = columnStateTag.getLinkStyleClass();
        							    columnLinkStyle      = columnStateTag.getLinkStyle();
        							    columnStyleClass     = columnStateTag.getStyleClass();
        							    columnStyle          = columnStateTag.getStyle();
        							}
        							
        							if(columnLinkStyleClass.length() == 0)
        							    columnLinkStyleClass = columnTag.getLinkStyleClass();

                                    if(columnLinkStyle.length() == 0)
                                        columnLinkStyle = columnTag.getLinkStyle();
        							
                                    if(columnStyleClass.length() == 0)
                                        columnStyleClass = columnTag.getStyleClass();

                                    if(columnStyle.length() == 0)
                                        columnStyle = columnTag.getStyle();
                                    
                                    if(columnLinkStyleClass.length() == 0)
                                        columnLinkStyleClass = detailLinkStyleClass;

                                    if(columnLinkStyle.length() == 0)
                                        columnLinkStyle = detailLinkStyle;
                                    
                                    if(columnStyleClass.length() == 0)
                                        columnStyleClass = detailStyleClass;

                                    if(columnStyle.length() == 0)
                                        columnStyle = detailStyle;
                                    
                                    if(columnLinkStyleClass.length() == 0)
                                        columnLinkStyleClass = TaglibConstants.DEFAULT_GRID_DETAIL_LINK_STYLE_CLASS;

                                    if(columnStyleClass.length() == 0)
                                        columnStyleClass = TaglibConstants.DEFAULT_GRID_DETAIL_STYLE_CLASS;
                                    
                                    columnLinkStyleClass = PropertyUtil.fillPropertiesInString(currentModel, columnLinkStyleClass, currentLanguage);
                                    columnLinkStyle      = PropertyUtil.fillPropertiesInString(currentModel, columnLinkStyle, currentLanguage);
                                    columnStyleClass     = PropertyUtil.fillPropertiesInString(currentModel, columnStyleClass, currentLanguage);
                                    columnStyle          = PropertyUtil.fillPropertiesInString(currentModel, columnStyle, currentLanguage);
                                    columnLinkStyleClass = ExpressionProcessorUtil.fillVariablesInString(columnLinkStyleClass, currentLanguage);
                                    columnLinkStyle      = ExpressionProcessorUtil.fillVariablesInString(columnLinkStyle, currentLanguage);
                                    columnStyleClass     = ExpressionProcessorUtil.fillVariablesInString(columnStyleClass, currentLanguage);
                                    columnStyle          = ExpressionProcessorUtil.fillVariablesInString(columnStyle, currentLanguage);
        							
                                    print("<td class=\"");
    								print(columnStyleClass);
       								print("\"");
        								
        							if(columnStyle.length() > 0){
        								print(" style=\"");
        								print(columnStyle);
        								print("\"");
        							}
        
        							if(columnWidth.length() > 0){
        								print(" width=\"");
        								print(columnWidth);
        								print("\"");
        							}
        
        							if(columnPropertyInfo != null || columnValue != null){
        								columnAlignment = columnTag.getAlignmentType();
        							
        								if(columnPropertyInfo != null){
            								if(columnPropertyInfo.isNumber())
            									columnAlignment = AlignmentType.RIGHT;
            								else if(columnPropertyInfo.isDate() || columnPropertyInfo.isBoolean() || columnPropertyInfo.isByteArray())
            									columnAlignment = AlignmentType.CENTER;
        								}
        								
            							if(columnAlignment != null){
            								print(" align=\"");
            								print(columnAlignment);
            								print("\"");
            							}
            							
        								columnLink        = PropertyUtil.fillPropertiesInString(currentModel, columnLink, currentLanguage);
        								columnTooltip     = PropertyUtil.fillPropertiesInString(currentModel, columnTooltip, currentLanguage);
        								columnOnBlur      = PropertyUtil.fillPropertiesInString(currentModel, columnOnBlur, currentLanguage);
        								columnOnFocus     = PropertyUtil.fillPropertiesInString(currentModel, columnOnFocus, currentLanguage);
        								columnOnChange    = PropertyUtil.fillPropertiesInString(currentModel, columnOnChange, currentLanguage);
        								columnOnClick     = PropertyUtil.fillPropertiesInString(currentModel, columnOnClick, currentLanguage);
        								columnOnKeyPress  = PropertyUtil.fillPropertiesInString(currentModel, columnOnKeyPress, currentLanguage);
        								columnOnKeyUp     = PropertyUtil.fillPropertiesInString(currentModel, columnOnKeyUp, currentLanguage);
        								columnOnKeyDown   = PropertyUtil.fillPropertiesInString(currentModel, columnOnKeyDown, currentLanguage);
        								columnOnMouseOver = PropertyUtil.fillPropertiesInString(currentModel, columnOnMouseOver, currentLanguage);
        								columnOnMouseOut  = PropertyUtil.fillPropertiesInString(currentModel, columnOnMouseOut, currentLanguage);
        								columnLink        = ExpressionProcessorUtil.fillVariablesInString(columnLink, currentLanguage);
        								columnTooltip     = ExpressionProcessorUtil.fillVariablesInString(columnTooltip, currentLanguage);
        								columnOnBlur      = ExpressionProcessorUtil.fillVariablesInString(columnOnBlur, currentLanguage);
        								columnOnFocus     = ExpressionProcessorUtil.fillVariablesInString(columnOnFocus, currentLanguage);
        								columnOnChange    = ExpressionProcessorUtil.fillVariablesInString(columnOnChange, currentLanguage);
        								columnOnClick     = ExpressionProcessorUtil.fillVariablesInString(columnOnClick, currentLanguage);
        								columnOnKeyPress  = ExpressionProcessorUtil.fillVariablesInString(columnOnKeyPress, currentLanguage);
        								columnOnKeyUp     = ExpressionProcessorUtil.fillVariablesInString(columnOnKeyUp, currentLanguage);
        								columnOnKeyDown   = ExpressionProcessorUtil.fillVariablesInString(columnOnKeyDown, currentLanguage);
        								columnOnMouseOver = ExpressionProcessorUtil.fillVariablesInString(columnOnMouseOver, currentLanguage);
        								columnOnMouseOut  = ExpressionProcessorUtil.fillVariablesInString(columnOnMouseOut, currentLanguage);
        								
        								println(">");
        								
    									columnValueLabel = PropertyUtil.format(columnValue, columnValueMapInstance, columnPattern, columnUseAdditionalFormatting, columnPrecision, currentLanguage);
    									columnValueLabel = StringUtil.replaceAll(columnValueLabel, StringUtil.getLineBreak(), "");
    									columnValueLabel = StringUtil.decode(columnValueLabel);

										if(!columnTag.isImage()){
    										if(tagName == null)
    										    tagName = new StringBuilder();
    										else
    										    tagName.delete(0, tagName.length());
    
    										tagName.append(getName());
    										tagName.append("_");
    										tagName.append(columnName);
    										tagName.append("_");
    										tagName.append(AttributeConstants.EDITABLE_DATA_COLUMN_KEY);
    										tagName.append(cont1);

    										if(columnEditable || (columnPropertyInfo != null && columnPropertyInfo.isBoolean())){
        										if(columnPropertyInfo != null && columnPropertyInfo.isDate()){
        											editableDataColumnTag = new CalendarPropertyTag();
        
        											((CalendarPropertyTag)editableDataColumnTag).setPattern(columnPattern);
        										}
        										else if(columnPropertyInfo != null && columnPropertyInfo.isBoolean()){
        											editableDataColumnTag = new CheckPropertyTag();
        
        											((BaseOptionPropertyTag)editableDataColumnTag).setOptionValue(columnValue);
        										}
        										else if(columnPropertyInfo != null && (columnPropertyInfo.isModel() || columnPropertyInfo.hasModel()) || columnData.length() > 0){
        											editableDataColumnTag = new ListPropertyTag();
        											((ListPropertyTag)editableDataColumnTag).setOptionStates(columnTag.getOptionStates());
        										}
        										else{
        											if(!columnHasMultipleLines)
        												editableDataColumnTag = new TextPropertyTag();
        											else{
        												editableDataColumnTag = new TextAreaPropertyTag();
        
        												if(columnRows > 0)
        													((TextAreaPropertyTag)editableDataColumnTag).setRows(columnRows);
        
        												if(columnColumns > 0)
        													((TextAreaPropertyTag)editableDataColumnTag).setColumns(columnColumns);
        											}
        
        											((TextPropertyTag)editableDataColumnTag).setPattern(columnPattern);
        										}
        
        										editableDataColumnTag.setPageContext(pageContext);
        										editableDataColumnTag.setPropertyInfo(columnPropertyInfo);
        										editableDataColumnTag.setName(tagName.toString());
        										editableDataColumnTag.setLabel(columnLabel);
        										editableDataColumnTag.setValue(columnValue);
        										editableDataColumnTag.setShowLabel(false);

        										if(columnPropertyInfo != null && !columnPropertyInfo.isDate() && !columnPropertyInfo.isBoolean() && columnSize == 0)
                                                    editableDataColumnTag.setStyle("width: 100%;");
        										
        										if(columnOnClickContent == null)
        											columnOnClickContent = new StringBuilder();
        										else
        											columnOnClickContent.delete(0, columnOnClickContent.length());
        										
                                                if(optionTagId != null){
                                                    columnOnClickContent.append("getObject('");
                                                    columnOnClickContent.append(optionTagId.toString());
                                                    columnOnClickContent.append("').click(); ");
                                                }

                                                columnOnClickContent.append(columnOnClick);

        										editableDataColumnTag.setOnBlur(columnOnBlur);
        										editableDataColumnTag.setOnChange(columnOnChange);
        										editableDataColumnTag.setOnClick(columnOnClickContent.toString());
                                                editableDataColumnTag.setOnFocus(columnOnFocus);
        										editableDataColumnTag.setOnKeyPress(columnOnKeyPress);
        										editableDataColumnTag.setOnKeyUp(columnOnKeyUp);
        										editableDataColumnTag.setOnKeyDown(columnOnKeyDown);
        										editableDataColumnTag.setOnMouseOver(columnOnMouseOver);
        										editableDataColumnTag.setOnMouseOut(columnOnMouseOut);
        										editableDataColumnTag.setTooltip(columnTooltip);
        
        										if(editableDataColumnTag instanceof CheckPropertyTag)
        											editableDataColumnTag.setEnabled(columnEditable);
        										else if(editableDataColumnTag instanceof ListPropertyTag){
        											((ListPropertyTag)editableDataColumnTag).setData(columnData);
        											((ListPropertyTag)editableDataColumnTag).setDataScopeType(columnDataScope);
        											((ListPropertyTag)editableDataColumnTag).setSize(columnSize);
        											((ListPropertyTag)editableDataColumnTag).setValueMapInstance(columnValueMapInstance);
        										}
        										else if(editableDataColumnTag instanceof TextPropertyTag){
    												((TextPropertyTag)editableDataColumnTag).setAlignmentType(columnAlignment);
        											((TextPropertyTag)editableDataColumnTag).setSize(columnSize);
        										}

        										editableDataColumnTag.setParent(columnTag);
        										editableDataColumnTag.setFocus(columnTag.focus());
        										editableDataColumnTag.doStartTag();
        										editableDataColumnTag.doEndTag();
        									}
        									else{
        										if((columnSize > 0 && columnValueLabel.length() > 0 && columnValueLabel.length() > columnSize) || columnLink.length() > 0){
        											print("<a class=\"");
        											print(columnLinkStyleClass);
    												print("\"");
        
    												if(columnLinkStyle.length() > 0){
    													print(" style=\"");
    													print(columnLinkStyle);
    													print("\"");
    												}
        
        											if(columnLink.length() > 0){
        												print(" onClick=\"");
        												print(columnLink);
        												print("\"");
        											}
        
        											if((columnSize > 0 && columnValueLabel.length() > columnSize)){
        												print("title=\"");
        												print(columnValueLabel);
        												println("\"");
        											}
        
        											print(">");
        										}
        
        										if(columnValueLabel.length() == 0)
        											println("&nbsp;");
        										else{
        											if(columnSize > 0 && columnValueLabel.length() > columnSize){
        												print(columnValueLabel.substring(0, columnSize));
        												println("...");
        											}
        											else
        												println(columnValueLabel);
        										}
        
        										if((columnSize > 0 && columnValueLabel.length() > 0 && columnValueLabel.length() > columnSize) || columnLink.length() > 0)
        											println("</a>");
        									}
        								}
        								else{
        									if(columnValue != null){
        										if(tagName == null)
        										    tagName = new StringBuilder();
        										else
        										    tagName.delete(0, tagName.length());
        
        										if(columnName.length() > 0){
        										    tagName.append(name);
        										    tagName.append(".");
        										    tagName.append(columnName);
        										}
        										else{
        										    tagName.append("image");
        										    tagName.append((int)(Math.random() * 9999));
        										}
        
        										imageTag = new ImageTag();
        										imageTag.setPageContext(pageContext);
        										imageTag.setName(tagName.toString());
        										imageTag.setValue(columnValue);
        										imageTag.setWidth(columnTag.getImageWidth());
        										imageTag.setHeight(columnTag.getImageHeight());
        										imageTag.setOnClick(columnOnClick);
        										imageTag.setOnClickAction(getOnClickAction());
        										imageTag.setOnClickActionForward(getOnClickActionForward());
        										imageTag.setOnClickActionForwardOnFail(getOnClickActionForwardOnFail());
        										imageTag.setOnClickActionUpdateViews(getOnClickActionUpdateViews());
        										imageTag.setOnClickActionValidate(getOnClickActionValidate());
        										imageTag.setOnClickActionValidateProperties(getOnClickActionValidateProperties());
                                                imageTag.setOnMouseOver(columnOnMouseOver);
                                                imageTag.setOnMouseOverAction(getOnMouseOverAction());
                                                imageTag.setOnMouseOverActionForward(getOnMouseOverActionForward());
                                                imageTag.setOnMouseOverActionForwardOnFail(getOnMouseOverActionForwardOnFail());
                                                imageTag.setOnMouseOverActionUpdateViews(getOnMouseOverActionUpdateViews());
                                                imageTag.setOnMouseOverActionValidate(getOnMouseOverActionValidate());
                                                imageTag.setOnMouseOverActionValidateProperties(getOnMouseOverActionValidateProperties());
                                                imageTag.setOnMouseOut(columnOnMouseOut);
                                                imageTag.setOnMouseOutAction(getOnMouseOutAction());
                                                imageTag.setOnMouseOutActionForward(getOnMouseOutActionForward());
                                                imageTag.setOnMouseOutActionForwardOnFail(getOnMouseOutActionForwardOnFail());
                                                imageTag.setOnMouseOutActionUpdateViews(getOnMouseOutActionUpdateViews());
                                                imageTag.setOnMouseOutActionValidate(getOnMouseOutActionValidate());
                                                imageTag.setOnMouseOutActionValidateProperties(getOnMouseOutActionValidateProperties());
        										imageTag.setTooltip(columnTooltip);
        										imageTag.setShowLabel(false);
        										imageTag.doStartTag();
        										imageTag.doEndTag();
        									}
        									else
        										println("&nbsp;");
        								}
        							}
        							else{
        								println(">");
        
        								columnValueLabel = getInvalidPropertyMessage();
        								columnValueLabel = StringUtil.replaceAll(columnValueLabel, StringUtil.getLineBreak(), "");
        								columnValueLabel = StringUtil.decode(columnValueLabel);
        
        								if((columnSize > 0 && columnValueLabel.length() > 0 && columnValueLabel.length() > columnSize) || columnLink.length() > 0){
        									print("<a class=\"");
        									print(columnLinkStyleClass);
    										print("\"");
        
    										if(columnLinkStyle.length() > 0){
    											print(" style=\"");
    											print(columnLinkStyle);
    											print("\"");
    										}
        
        									if(columnLink.length() > 0){
        										print(" onClick=\"");
        										print(columnLink);
        										print("\"");
        									}
        
        									if((columnSize > 0 && columnValueLabel.length() > columnSize)){
        										print(" title=\"");
        										print(columnValueLabel);
        										println("\"");
        									}
        
        									print(">");
        								}
        								
        								if(columnValueLabel.length() == 0)
        									println("&nbsp;");
        								else{
        									if(columnSize > 0 && columnValueLabel.length() > columnSize){
        										print(columnValueLabel.substring(0, columnSize));
        										println("...");
        									}
        									else
        										println(columnValueLabel);
        								}
        
        								if((columnSize > 0 && columnValueLabel.length() > 0 && columnValueLabel.length() > columnSize) || columnLink.length() > 0)
        									println("</a>");
        							}
        
        							println("</td>");
    							}
    						}
        
        					println("</tr>");
        				}
    				}
    			}
    		}
		}
		else{
			println("<tr>");
			print("<td align=\"");
			print(AlignmentType.CENTER);
			print("\" height=\"50\" class=\"");
			
	        columnStyleClass = detailStyleClass;
	        
	        if(columnStyleClass.length() == 0)
	            columnStyleClass = TaglibConstants.DEFAULT_GRID_DETAIL_STYLE_CLASS;
	        
	        print(columnStyleClass);
			print("\"");

			if(detailStyle.length() > 0){
				print(" style=\"");
				print(detailStyle);
				print("\"");
			}

			if(columnsTags != null && columnsTags.size() > 0){
				print(" colspan=\"");
				print(columnsTags.size() + (showSelection() ? 1 : 0));
				print("\"");
			}
			
			print(">&nbsp;");

			if(propertyInfo == null)
				print(getInvalidPropertyMessage());
			else
				print(getDataIsEmptyMessage());

			print("&nbsp;");
			println("</td>");
			println("</tr>");
		}
	}

	/**
	 * Renderiza o cabeçalho (colunas) do componente.
	 * 
	 * @throws Throwable
	 */
	private void renderHeader() throws Throwable{
        RequestInfo  requestInfo    = getRequestInfo();
	    PropertyInfo propertyInfo   = getPropertyInfo();
	    String       name           = getName();
	    String       actionFormName = getActionFormName();
        String       width          = getWidth();
	    List         dataValues     = getDataValues();

        print("<table");

		if(width.length() > 0){
			print(" width=\"");
			print(width);
			print("\"");
		}
		
		renderTooltip();

		println(">");

		println("<tr>");
		println("<td>");

        print("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"");
        print(TaglibConstants.DEFAULT_PANEL_STYLE_CLASS);
        println("\">");
		println("<tr>");

		if(showSelection())
			println("<td></td>");

		if(columnsTags != null && columnsTags.size() > 0){
			GridColumnGroupTag columnGroup                 = null;
            String             currentColumnGroupName      = "";
            String             currentColumnGroupLabel     = "";
			AlignmentType      currentColumnGroupAlignment = null;
            String             currentColumnGroupTooltip   = "";
			Integer            columnsInGroup              = 0;
            String             columnHeaderLinkStyleClass  = headerLinkStyleClass;
            String             columnHeaderLinkStyle       = headerLinkStyle;
            String             columnHeaderStyleClass      = headerStyleClass;
            String             columnHeaderStyle           = headerStyle;
            
            if(columnHeaderLinkStyleClass.length() == 0)
                columnHeaderLinkStyleClass = TaglibConstants.DEFAULT_GRID_HEADER_LINK_STYLE_CLASS; 

            if(columnHeaderStyleClass.length() == 0)
                columnHeaderStyleClass = TaglibConstants.DEFAULT_GRID_HEADER_STYLE_CLASS; 

			for(GridColumnTag columnTag : columnsTags){
			    if(columnTag.getParent() instanceof GridColumnGroupTag){
			        columnGroup = (GridColumnGroupTag)columnTag.getParent();
			        
    				if(columnGroup.getName().length() > 0){
    					if(currentColumnGroupName.length() == 0){
    						currentColumnGroupName      = columnGroup.getName();
    						currentColumnGroupLabel     = columnGroup.getLabel();
    						currentColumnGroupTooltip   = columnGroup.getTooltip();
    						currentColumnGroupAlignment = columnGroup.getAlignmentType();
    					}
    					
        				if(!currentColumnGroupName.equals(columnGroup.getName())){
        					if(columnsInGroup > 0){
        						print("<td class=\"");
        						print(columnHeaderStyleClass);
        						print("\"");
        
        						if(columnHeaderStyle.length() > 0){
        							print(" style=\"");
        							print(columnHeaderStyle);
        							print("\"");
        						}
        
        						print(" colspan=\"");
        						print(columnsInGroup);
        						print("\"");
        
        						if(currentColumnGroupAlignment != null){
        							print(" align=\"");
        							print(currentColumnGroupAlignment);
        							print("\"");
        						}
        
        						println(">");
        						
        						if(currentColumnGroupTooltip.length() > 0){
        							print("<a title=\"");
        							print(currentColumnGroupTooltip);
        							print("\">");
        						}
        						
        						println(currentColumnGroupLabel);
        
        						if(currentColumnGroupTooltip.length() > 0)
        							println("</a>");
        						
        						println("</td>");
        					}
        					else
        						println("<td></td>");
        						
                            currentColumnGroupName      = columnGroup.getName();
                            currentColumnGroupLabel     = columnGroup.getLabel();
                            currentColumnGroupTooltip   = columnGroup.getTooltip();
                            currentColumnGroupAlignment = columnGroup.getAlignmentType();
        					columnsInGroup              = 0;
        				}
    
        				columnsInGroup++;
    				}
			    }
			    else{
                    if(columnsInGroup > 0){
                        print("<td class=\"");
                        print(columnHeaderStyleClass);
                        print("\"");

                        if(columnHeaderStyle.length() > 0){
                            print(" style=\"");
                            print(columnHeaderStyle);
                            print("\"");
                        }

                        print(" colspan=\"");
                        print(columnsInGroup);
                        print("\"");

                        if(currentColumnGroupAlignment != null){
                            print(" align=\"");
                            print(currentColumnGroupAlignment);
                            print("\"");
                        }

                        println(">");
                        
                        if(currentColumnGroupTooltip.length() > 0){
                            print("<a title=\"");
                            print(currentColumnGroupTooltip);
                            print("\">");
                        }
                        
                        println(currentColumnGroupLabel);

                        if(currentColumnGroupTooltip.length() > 0)
                            println("</a>");
                        
                        println("</td>");
                    }

                    columnsInGroup = 0;
			    }
			}
			
            if(columnsInGroup > 0){
                print("<td class=\"");
                print(columnHeaderStyleClass);
                print("\"");

                if(columnHeaderStyle.length() > 0){
                    print(" style=\"");
                    print(columnHeaderStyle);
                    print("\"");
                }

                print(" colspan=\"");
                print(columnsInGroup);
                print("\"");

                if(currentColumnGroupAlignment != null){
                    print(" align=\"");
                    print(currentColumnGroupAlignment);
                    print("\"");
                }

                println(">");
                
                if(currentColumnGroupTooltip.length() > 0){
                    print("<a title=\"");
                    print(currentColumnGroupTooltip);
                    print("\">");
                }
                
                println(currentColumnGroupLabel);

                if(currentColumnGroupTooltip.length() > 0)
                    println("</a>");
                
                println("</td>");
            }

			println("</tr>");
			println("<tr>");

            if(showSelection()){
                print("<td class=\"");
				print(columnHeaderStyleClass);
				print("\"");

				if(columnHeaderStyle.length() > 0){
					print(" style=\"");
					print(columnHeaderStyle);
					print("\"");
				}

				print(" align=\"");
				print(AlignmentType.CENTER);
				println("\" width=\"1\">");
				print("<b>");

				if(propertyInfo != null && propertyInfo.isCollection()){
					print("<a class=\"");
					print(columnHeaderLinkStyleClass);
					print("\"");

					if(columnHeaderLinkStyle.length() > 0){
						print(" style=\"");
						print(columnHeaderLinkStyle);
						print("\"");
					}
					
					Integer dataStartIndex = getDataStartIndex();
					Integer dataEndIndex   = getDataEndIndex();

					print(" href=\"javascript:selectDeselectAllGridRows('");
					print(name);
					print("', ");
					print(dataStartIndex);
					print(", ");
					print(dataEndIndex);
					print(");\">");
				}

				if(selectionColumnLabel.length() > 0)
					print(selectionColumnLabel);

				if(propertyInfo != null && propertyInfo.isCollection())
					print("(*)</a>");
				else if(selectionColumnLabel.length() == 0)
					print("&nbsp;");

				println("</b>");
				println("</td>");
			}

            String  columnWidth     = "";
			Integer columnsSize     = columnsTags.size();
			Double  aggregatesWidth = 0d;
			Double  columnsWidth    = 100d;

			for(GridColumnTag columnTag : columnsTags){
			    columnWidth = columnTag.getWidth();
			    
				if(columnWidth.length() > 0){
					if(columnTag.getParent() instanceof GridColumnGroupTag && dataValues != null && dataValues.size() > 0)
						columnGroup = (GridColumnGroupTag)columnTag.getParent();
					else
						columnGroup = null;
					
					if(columnGroup != null && columnGroup.aggregate()){
						aggregatesWidth += Double.parseDouble(StringUtil.replaceAll(columnWidth, "%", ""));

						columnsSize--;
						columnsWidth -= aggregatesWidth;
					}
					else{
						columnsSize--;
                        columnsWidth -= Double.parseDouble(StringUtil.replaceAll(columnWidth, "%", ""));;
					}
				}
			}

			String        columnName       = "";
			String        columnLabel      = "";
			AlignmentType columnAlignment  = null;
			String        columnTooltip    = "";
			Double        columnWidthValue = 0d;
			
			for(GridColumnTag columnTag : columnsTags){
			    columnName      = columnTag.getName();
                columnWidth     = columnTag.getWidth();
                columnAlignment = columnTag.getAlignmentType();
			    columnLabel     = columnTag.getLabel();
                columnTooltip   = columnTag.getTooltip();
			    
				if(columnTag.getParent() instanceof GridColumnGroupTag && dataValues != null && dataValues.size() > 0)
					columnGroup = (GridColumnGroupTag)columnTag.getParent();
				else
					columnGroup = null;
				
				if(columnGroup == null || !columnGroup.aggregate()){
    				print("<td class=\"");
    				print(columnHeaderStyleClass);
    				print("\"");
    
    				if(columnHeaderStyle.length() > 0){
    					print(" style=\"");
    					print(columnHeaderStyle);
    					print("\"");
    				}
    
    				print(" width=\"");
    
    				if(columnWidth.length() > 0){
    				    columnWidthValue = Double.parseDouble(StringUtil.replaceAll(columnWidth, "%", ""));
    					columnWidthValue = (columnWidthValue + (aggregatesWidth / (columnsSize == 0 ? 1 : columnsSize)));
    				}
    				else
    				    columnWidthValue = (columnsWidth / columnsSize); 
    
                    print(Math.floor(columnWidthValue));

                    if(columnWidth.endsWith("%") || columnWidth.length() == 0)
                        print("%");
                    
    				print("\"");
    
    				if(columnAlignment != null){
    					print(" align=\"");
    					print(columnAlignment);
    					print("\"");
    				}
    
    				println(">");
    
                    print("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"");
                    print(TaglibConstants.DEFAULT_PANEL_STYLE_CLASS);
                    println("\">");
    				println("<tr>");
    				print("<td");
    
    				if(columnAlignment != null){
    					print(" align=\"");
    					print(columnAlignment);
    					print("\"");
    				}
    
    				println(">");
    
    				print("<a");
    				
    				if(columnTooltip.length() > 0){
    					print(" title=\"");
    					print(columnTooltip);
    					print("\"");
    				}
    
					print(" class=\"");
					print(columnHeaderLinkStyleClass);
					print("\"");
    
    				if(columnHeaderLinkStyle.length() > 0){
    					print(" style=\"");
    					print(columnHeaderLinkStyle);
    					print("\"");
    				}
    
    				if(columnName.length() > 0){
        				print(" href=\"javascript: setObjectValue('");
        				print(name);
        				print(".");
        				print(AttributeConstants.SORT_PROPERTY_KEY);
        				print("', '");
        				print(columnName);
        				print("'); setObjectValue('");
        				print(name);
        				print(".");
        				print(AttributeConstants.SORT_ORDER_KEY);
        				print("', '");
    
        				if(columnName.equals(requestInfo.getSortProperty())){
        					if(requestInfo.getSortOrder() == SortOrderType.ASCEND)
        						print(SortOrderType.DESCEND);
        					else
        						print(SortOrderType.ASCEND);
        				}
        				else
        					print(SortOrderType.ASCEND);
        				
        				print("');");

        				if(pagerTag != null){
            				print(" setObjectValue('");
            				print(name);
            				print(".");
            				print(AttributeConstants.PAGER_ACTION_KEY);
            				print("', '");
            				print(PagerActionType.REFRESH_PAGE);
            				print("');");
        				}
        				
            			print(" document.");
        				print(actionFormName);
        				print(".");
        				print(AttributeConstants.ACTION_KEY);
        				print(".value = '");
        				print(ActionType.REFRESH.getMethod());
        				print("'; submitForm(document.");
        				print(actionFormName);
        				print(");\"");
    				}
    				
    				print("><b>");
    				print(columnLabel);
    				println("</b>");
    				println("</a>");
    
    				if(columnName.length() > 0){
        				if(columnName.equals(requestInfo.getSortProperty())){
        					println("</td>");
        					
        					print("<td align=\"");
        					print(AlignmentType.RIGHT);
        					print("\" width=\"1\">");
        					print("<div class=\"");
        					print(requestInfo.getSortOrder().getOperator());
        					println("endOrderIcon\"></div>");
        				}
    				}
    
    				println("</td>");
    				println("</tr>");
    				println("</table>");
    				println("</td>");
    			}
			}
		}

		println("</tr>");
	}

	/**
	 * Renderiza os controles (paginação, botões) do componente.
	 * 
	 * @throws Throwable
	 */
	private void renderControls() throws Throwable{
		print("<table class=\"");
		print(TaglibConstants.DEFAULT_GRID_CONTROL_STYLE_CLASS);
		println("\">");
		println("<tr>");

		renderPager();
		renderButtons();

		println("</tr>");
		println("</table>");
	}

	/**
	 * Renderiza a paginação do componente.
	 * 
	 * @throws Throwable
	 */
	private void renderPager() throws Throwable{
		if(pagerTag != null){
			print("<td align=\"");
			print(AlignmentType.CENTER);
			println("\">");

			pagerTag.render();

			println("</td>");
		}
	}

	/**
	 * Renderiza os botões do componente.
	 * 
	 * @throws Throwable
	 */
	private void renderButtons() throws Throwable{
		if(buttonsTags != null && buttonsTags.size() > 0){
			println("<td width=\"5\"></td>");

			Collection dataValues = getDataValues();
			Integer    cont       = 0;
			ButtonTag  buttonTag  = null;
			
			for(cont = 0 ; cont < buttonsTags.size() ; cont++){
			    buttonTag = buttonsTags.get(cont);
			    
			    if(buttonTag.showOnlyWithData()){
					if(dataValues != null && dataValues.size() > 0){
						println("<td>");

						buttonTag.render();

						println("</td>");
					}
				}
				else{
					println("<td>");

					buttonTag.render();

					println("</td>");
				}
			}
		}
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BasePropertyTag#clearAttributes()
	 */
	protected void clearAttributes(){
		super.clearAttributes();

		setOnSelect("");
		setOnSelectAction("");
		setOnSelectActionForward("");
		setOnSelectActionForwardOnFail("");
		setOnSelectActionUpdateViews("");
		setOnSelectActionValidate(false);
		setOnSelectActionValidateProperties("");
		setShowSelection(true);
		setSelectionColumnLabel("");
		setHeaderLinkStyleClass("");
		setHeaderLinkStyle("");
		setHeaderStyleClass("");
		setHeaderStyle("");
		setDetailLinkStyleClass("");
		setDetailLinkStyle("");
		setDetailStyleClass("");
		setDetailStyle("");
		setAggregateStyleClass("");
		setAggregateStyle("");
		setColumnsTags(null);
		setButtonsTags(null);
		setRowStatesTags(null);
	}
}