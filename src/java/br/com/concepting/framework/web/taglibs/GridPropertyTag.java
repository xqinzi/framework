package br.com.concepting.framework.web.taglibs;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.processors.ExpressionProcessor;
import br.com.concepting.framework.processors.ExpressionProcessorUtil;
import br.com.concepting.framework.security.constants.SecurityConstants;
import br.com.concepting.framework.util.Pager;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.constants.AttributeConstants;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.util.types.PagerActionType;
import br.com.concepting.framework.util.types.SortOrderType;
import br.com.concepting.framework.web.action.types.ActionType;
import br.com.concepting.framework.web.helpers.RequestInfo;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;
import br.com.concepting.framework.web.types.ComponentType;

/**
 * Classe que define o componente visual para uma tabela de dados.
 * 
 * @author fvilarinho
 * @since 2.0
 */
public class GridPropertyTag extends BaseOptionsPropertyTag{
	private String                    onSelect             = "";
	private Boolean                   showSelection        = true;
	private String                    selectionColumnLabel = "";
	private String                    headerLinkStyleClass = "";
	private String                    headerLinkStyle      = "";
	private String                    headerStyleClass     = "";
	private String                    headerStyle          = "";
	private String                    detailLinkStyleClass = "";
	private String                    detailLinkStyle      = "";
    private String                    detailStyleClass     = "";
    private String                    detailStyle          = "";
	private String                    aggregateStyleClass  = "";
	private String                    aggregateStyle       = "";
	private Collection<GridColumnTag> columnsTags          = null;
	private PagerTag                  pagerTag             = null;
	private List<ButtonTag>           buttonsTags          = null;
	private List<GridRowStateTag>     rowStatesTags        = null;

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
	 * Retorna a lista de bot�es do componente.
	 *
	 * @return Lista contendo os bot�es.
	 */
	public List<ButtonTag> getButtonsTags(){
    	return buttonsTags;
    }

	/**
	 * Define a lista de bot�es do componente.
	 *
	 * @param buttonsTags Lista contendo os bot�es.
	 */
	public void setButtonsTags(List<ButtonTag> buttonsTags){
    	this.buttonsTags = buttonsTags;
    }

	/**
	 * Retorna a lista de caracter�sticas das linhas do componente.
	 *
	 * @return Lista contendo as caracter�sticas da linha.
	 */
	public List<GridRowStateTag> getRowStatesTags(){
    	return rowStatesTags;
    }

	/**
	 * Define a lista de caracter�sticas das linhas do componente.
	 *
	 * @param rowStatesTags Lista contendo as caracter�sticas da linha.
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
	 * Retorna o estilo CSS para o link do cabe�alho.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getHeaderLinkStyle(){
		return headerLinkStyle;
	}

	/**
	 * Define o estilo CSS para o link do cabe�alho.
	 * 
	 * @param headerLinkStyle String contendo o estilo CSS.
	 */
	public void setHeaderLinkStyle(String headerLinkStyle){
		this.headerLinkStyle = headerLinkStyle;
	}

	/**
	 * Retorna o estilo CSS para o link do cabe�alho.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getHeaderLinkStyleClass(){
		return headerLinkStyleClass;
	}

	/**
	 * Define o estilo CSS para o link do cabe�alho.
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
	 * Retorna o estilo CSS para o cabe�alho.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getHeaderStyleClass(){
		return headerStyleClass;
	}

	/**
	 * Define o estilo CSS para o cabe�alho.
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
	 * Retorna o estilo CSS para o cabe�alho.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getHeaderStyle(){
		return headerStyle;
	}

	/**
	 * Define o estilo CSS para o cabe�alho.
	 * 
	 * @param headerStyle String contendo o estilo CSS.
	 */
	public void setHeaderStyle(String headerStyle){
		this.headerStyle = headerStyle;
	}

	/**
	 * Indica se o componente deve conter as op��es de sele��o.
	 * 
	 * @return True/False.
	 */
	public Boolean showSelection(){
		return showSelection;
	}

	/**
	 * Define se o componente deve conter as op��es de sele��o.
	 * 
	 * @param showSelection True/False.
	 */
	public void setShowSelection(Boolean showSelection){
		this.showSelection = showSelection;
	}

	/**
	 * Retorna o label da coluna de sele��o das op��es.
	 * 
	 * @return String contendo o label da coluna de sele��o das op��es.
	 */
	public String getSelectionColumnLabel(){
		return selectionColumnLabel;
	}

	/**
	 * Define o label da coluna de sele��o das op��es.
	 * 
	 * @param selectionColumnLabel String contendo o label da coluna de sele��o das op��es.
	 */
	public void setSelectionColumnLabel(String selectionColumnLabel){
		this.selectionColumnLabel = selectionColumnLabel;
	}

	/**
	 * Retorna o evento a ser executado quando uma op��o de sele��o for selecionada.
	 * 
	 * @return String contendo o evento a ser executado.
	 */
	public String getOnSelect(){
		return onSelect;
	}

	/**
	 * Define o evento a ser executado quando uma op��o de sele��o for selecionada.
	 * 
	 * @param onSelect String contendo o evento a ser executado.
	 */
	public void setOnSelect(String onSelect){
		this.onSelect = onSelect;
	}

	/**
	 * Define a inst�ncia do componente visual de pagina��o.
	 * 
	 * @param pagerTag Inst�ncia contendo as propriedades do componente visual de pagina��o.
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
	 * Retorna a inst�ncia do componente visual de pagina��o.
	 * 
	 * @return Inst�ncia contendo as propriedades do componente visual de pagina��o.
	 */
	protected PagerTag getPagerTag(){
		return pagerTag;
	}

	/**
	 * Adiciona um bot�o ao componente.
	 * 
	 * @param buttonTag Inst�ncia contendo as propriedades do bot�o.
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
	 * @param columnTag Inst�ncia contendo as propriedades do bot�o.
	 */
	protected void addColumnTag(GridColumnTag columnTag){
		if(columnsTags == null)
			columnsTags = new LinkedList<GridColumnTag>();

		columnsTags.add(columnTag);
	}

	/**
	 * Adiciona uma caracter�stica para as linhas do componente.
	 * 
	 * @param rowStateTag Inst�ncia contendo a caracter�stica desejada.
	 */
	protected void addRowStateTag(GridRowStateTag rowStateTag){
		if(rowStatesTags == null)
			rowStatesTags = new LinkedList<GridRowStateTag>();

		rowStatesTags.add(rowStateTag);
	}
	
	protected void initialize() throws Throwable{
	    setType(ComponentType.GRID);
	    
        super.initialize();
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#renderOpen()
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
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderBody()
	 */
	protected void renderBody() throws Throwable{
		renderDetails();
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BasePropertyTag#renderClose()
	 */
	protected void renderClose() throws Throwable{
		renderFooter();
	}
	
	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseOptionsPropertyTag#renderDataAttributes()
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
	 * @see br.com.concepting.framework.web.taglibs.BaseOptionsPropertyTag#renderDataIndexesAttributes()
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
	 * Renderiza o rodap� (controles) do componente.
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
		
		println(" align=\"center\">");

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
			String                   columnDataScope               = "";
			Boolean                  columnEditable                = false;
			Boolean                  columnHasMultipleLines        = false;
			String                   columnLinkStyleClass          = "";
			String                   columnLinkStyle               = "";
			String                   columnStyle                   = "";
			Integer                  columnRows                    = 0;
			Integer                  columnColumns                 = 0;
			String                   columnAlignment               = "";   
			String                   columnWidth                   = "";
			String                   columnPattern                 = "";
			Boolean                  columnUseAdditionalFormatting = false;
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
    			
    			for(Integer cont1 = dataStartIndex ; cont1 < dataEndIndex ; cont1++){
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
    					
    					if(rowStateTag != null)
    					    rowRemove = rowStateTag.remove();
    					
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
            							
            							if(columnPattern.length() == 0)
            							    columnPattern = columnPropertyInfo.getPattern();
        						    }
        						    catch(Throwable e){
        						        columnPropertyInfo            = null;
        						        columnUseAdditionalFormatting = false;
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
                								
        							    columnValueLabel = PropertyUtil.format(columnValue, columnValueMapInstance, columnPattern, columnUseAdditionalFormatting, currentLanguage);
        							    
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
    
                            columnLinkStyleClass = "";
                            columnLinkStyle      = "";
                            columnStyleClass     = "";
                            columnStyle          = "";

                            if(showSelection()){
        					    if(rowStateTag != null){
        					        columnStyleClass = rowStateTag.getStyleClass();
        					        columnStyle      = rowStateTag.getStyle();
        					    }
        					    
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
        
        						println(" align=\"center\" width=\"1\">");
        
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
        
        						optionTag.setOnClick(columnOnSelect);
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
                                columnDataScope        = columnTag.getDataScope();
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
                                columnLinkStyleClass   = "";
                                columnLinkStyle        = "";
                                columnStyleClass       = "";
                                columnStyle            = "";
                                
                                if(columnPattern.length() == 0)
                                    if(columnPropertyInfo != null)
                                        columnPattern = columnPropertyInfo.getPattern();
                                
                                if(columnPropertyInfo != null)
                                    columnUseAdditionalFormatting = columnPropertyInfo.useAdditionalFormatting();

                                if(columnTag.isEditable() != null)
                                    columnEditable = columnTag.isEditable();
                                
                                if(columnTag.hasMultipleLines() != null)
                                    columnHasMultipleLines = columnTag.hasMultipleLines();

                                columnValue = columnTag.getValue();
                                
                                try{
                                    columnPropertyInfo = PropertyUtil.getPropertyInfo(currentModel, columnName);
                                }
                                catch(Throwable e){
                                    columnPropertyInfo = null;                                  
                                }

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
    									
    									if(columnStateTag.getDataScope().length() > 0)
    										columnDataScope = columnStateTag.getDataScope();

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
        							
        							if(rowStateTag != null){
        							    if(columnLinkStyleClass.length() == 0)
        							        columnLinkStyleClass = rowStateTag.getLinkStyleClass();

                                        if(columnLinkStyle.length() == 0)
                                            columnLinkStyle = rowStateTag.getLinkStyle();

                                        if(columnStyleClass.length() == 0)
                                            columnStyleClass = rowStateTag.getStyleClass();

                                        if(columnStyle.length() == 0)
                                            columnStyle = rowStateTag.getStyle();
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
        								columnAlignment = columnTag.getAlignment();
        							
        								if(columnPropertyInfo != null){
            								if(columnPropertyInfo.isNumber())
            									columnAlignment = AlignmentType.RIGHT.toString();
            								else if(columnPropertyInfo.isDate() || columnPropertyInfo.isBoolean() || columnPropertyInfo.isByteArray())
            									columnAlignment = AlignmentType.CENTER.toString();
        								}
        								
            							if(columnAlignment.length() > 0){
            								print(" align=\"");
            								print(columnAlignment);
            								print("\"");
            							}
            							
        								columnLink           = PropertyUtil.fillPropertiesInString(currentModel, columnLink, currentLanguage);
        								columnTooltip        = PropertyUtil.fillPropertiesInString(currentModel, columnTooltip, currentLanguage);
        								columnOnBlur         = PropertyUtil.fillPropertiesInString(currentModel, columnOnBlur, currentLanguage);
        								columnOnFocus        = PropertyUtil.fillPropertiesInString(currentModel, columnOnFocus, currentLanguage);
        								columnOnChange       = PropertyUtil.fillPropertiesInString(currentModel, columnOnChange, currentLanguage);
        								columnOnClick        = PropertyUtil.fillPropertiesInString(currentModel, columnOnClick, currentLanguage);
        								columnOnKeyPress     = PropertyUtil.fillPropertiesInString(currentModel, columnOnKeyPress, currentLanguage);
        								columnOnKeyUp        = PropertyUtil.fillPropertiesInString(currentModel, columnOnKeyUp, currentLanguage);
        								columnOnKeyDown      = PropertyUtil.fillPropertiesInString(currentModel, columnOnKeyDown, currentLanguage);
        								columnOnMouseOver    = PropertyUtil.fillPropertiesInString(currentModel, columnOnMouseOver, currentLanguage);
        								columnOnMouseOut     = PropertyUtil.fillPropertiesInString(currentModel, columnOnMouseOut, currentLanguage);
        								columnLinkStyleClass = PropertyUtil.fillPropertiesInString(currentModel, columnLinkStyleClass, currentLanguage);
        								columnLinkStyle      = PropertyUtil.fillPropertiesInString(currentModel, columnLinkStyle, currentLanguage);
                                        columnStyleClass     = PropertyUtil.fillPropertiesInString(currentModel, columnStyleClass, currentLanguage);
                                        columnStyle          = PropertyUtil.fillPropertiesInString(currentModel, columnStyle, currentLanguage);
        								columnLink           = ExpressionProcessorUtil.fillVariablesInString(columnLink, currentLanguage);
        								columnTooltip        = ExpressionProcessorUtil.fillVariablesInString(columnTooltip, currentLanguage);
        								columnOnBlur         = ExpressionProcessorUtil.fillVariablesInString(columnOnBlur, currentLanguage);
        								columnOnFocus        = ExpressionProcessorUtil.fillVariablesInString(columnOnFocus, currentLanguage);
        								columnOnChange       = ExpressionProcessorUtil.fillVariablesInString(columnOnChange, currentLanguage);
        								columnOnClick        = ExpressionProcessorUtil.fillVariablesInString(columnOnClick, currentLanguage);
        								columnOnKeyPress     = ExpressionProcessorUtil.fillVariablesInString(columnOnKeyPress, currentLanguage);
        								columnOnKeyUp        = ExpressionProcessorUtil.fillVariablesInString(columnOnKeyUp, currentLanguage);
        								columnOnKeyDown      = ExpressionProcessorUtil.fillVariablesInString(columnOnKeyDown, currentLanguage);
        								columnOnMouseOver    = ExpressionProcessorUtil.fillVariablesInString(columnOnMouseOver, currentLanguage);
        								columnOnMouseOut     = ExpressionProcessorUtil.fillVariablesInString(columnOnMouseOut, currentLanguage);
                                        columnLinkStyleClass = ExpressionProcessorUtil.fillVariablesInString(columnLinkStyleClass, currentLanguage);
                                        columnLinkStyle      = ExpressionProcessorUtil.fillVariablesInString(columnLinkStyle, currentLanguage);
                                        columnStyleClass     = ExpressionProcessorUtil.fillVariablesInString(columnStyleClass, currentLanguage);
                                        columnStyle          = ExpressionProcessorUtil.fillVariablesInString(columnStyle, currentLanguage);
        								
        								println(">");
        								
    									columnValueLabel = PropertyUtil.format(columnValue, columnValueMapInstance, columnPattern, columnUseAdditionalFormatting, currentLanguage);
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
        										
        										columnOnClickContent.append(columnOnClick);
            										
                                                if(optionTagId != null){
                                                    if(columnOnClick.length() > 0 && !columnOnClick.endsWith(";"))
                                                        columnOnClickContent.append("; ");
            										
                                                    columnOnClickContent.append("document.getElementById('");
            										columnOnClickContent.append(optionTagId.toString());
            										columnOnClickContent.append("').click();");
        										}

        										editableDataColumnTag.setOnBlur(columnOnBlur);
        										editableDataColumnTag.setOnChange(columnOnChange);
        										editableDataColumnTag.setOnClick(columnOnClick);
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
        											((ListPropertyTag)editableDataColumnTag).setDataScope(columnDataScope);
        											((ListPropertyTag)editableDataColumnTag).setSize(columnSize);
        											((ListPropertyTag)editableDataColumnTag).setValueMapInstance(columnValueMapInstance);
        										}
        										else if(editableDataColumnTag instanceof TextPropertyTag){
    												((TextPropertyTag)editableDataColumnTag).setAlignment(columnAlignment);
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
        										imageTag.setOnClick(columnLink);
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
			print("<td align=\"center\" height=\"50\" class=\"");
			
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
	 * Renderiza o cabe�alho (colunas) do componente.
	 * 
	 * @throws Throwable
	 */
	private void renderHeader() throws Throwable{
        RequestInfo  requestInfo  = getRequestInfo();
	    PropertyInfo propertyInfo = getPropertyInfo();
	    String       name         = getName();
	    String       actionForm   = getActionForm();
        String       width        = getWidth();
	    List         dataValues   = getDataValues();

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

		println("<table class=\"panel\">");
		println("<tr>");

		if(showSelection())
			println("<td></td>");

		if(columnsTags != null && columnsTags.size() > 0){
			GridColumnGroupTag columnGroup                 = null;
            String             currentColumnGroupName      = "";
            String             currentColumnGroupLabel     = "";
			String             currentColumnGroupAlignment = "";
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
    						currentColumnGroupName      = StringUtil.trim(columnGroup.getName());
    						currentColumnGroupLabel     = StringUtil.trim(columnGroup.getLabel());
    						currentColumnGroupTooltip   = columnGroup.getTooltip();
    						currentColumnGroupAlignment = columnGroup.getAlignment();
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
        
        						if(currentColumnGroupAlignment.length() > 0){
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
        						
                            currentColumnGroupName      = StringUtil.trim(columnGroup.getName());
                            currentColumnGroupLabel     = columnGroup.getLabel();
                            currentColumnGroupTooltip   = columnGroup.getTooltip();
                            currentColumnGroupAlignment = columnGroup.getAlignment();
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

                        if(currentColumnGroupAlignment.length() > 0){
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

                if(currentColumnGroupAlignment.length() > 0){
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

				print(" align=\"center\" width=\"1\"><b>");

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
					println("(*)</a>");
				else if(selectionColumnLabel.length() == 0)
					println("&nbsp;");

				println("</b></td>");
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

			String columnName       = "";
			String columnLabel      = "";
			String columnAlignment  = "";
			String columnTooltip    = "";
			Double columnWidthValue = 0d;
			
			for(GridColumnTag columnTag : columnsTags){
			    columnName      = columnTag.getName();
                columnWidth     = columnTag.getWidth();
                columnAlignment = columnTag.getAlignment();
			    columnLabel     = StringUtil.trim(columnTag.getLabel());
                columnTooltip   = StringUtil.trim(columnTag.getTooltip());
			    
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
    
    				if(columnAlignment.length() > 0){
    					print(" align=\"");
    					print(columnAlignment);
    					print("\"");
    				}
    
    				println(">");
    
    				println("<table class=\"panel\">");
    				println("<tr>");
    				print("<td");
    
    				if(columnAlignment.length() > 0){
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
        				print(" href=\"javascript: document.getElementById('");
        				print(name);
        				print(".");
        				print(AttributeConstants.SORT_PROPERTY_KEY);
        				print("').value = '");
        				print(columnName);
        				print("'; document.getElementById('");
        				print(name);
        				print(".");
        				print(AttributeConstants.SORT_ORDER_KEY);
        				print("').value = '");
    
        				if(columnName.equals(requestInfo.getSortProperty())){
        					if(requestInfo.getSortOrder() == SortOrderType.ASCEND)
        						print(SortOrderType.DESCEND);
        					else
        						print(SortOrderType.ASCEND);
        				}
        				else
        					print(SortOrderType.ASCEND);
        				
        				print("';");

        				if(pagerTag != null){
            				print(" document.getElementById('");
            				print(name);
            				print(".");
            				print(AttributeConstants.PAGER_ACTION_KEY);
            				print("').value = '");
            				print(PagerActionType.REFRESH_PAGE);
            				print("';");
        				}
        				
            			print(" document.");
        				print(actionForm);
        				print(".action.value = '");
        				print(ActionType.REFRESH);
        				print("'; submitForm(document.");
        				print(actionForm);
        				print(");\"");
    				}
    				
    				print("><b>");
    				print(columnLabel);
    				println("</b>");
    				println("</a>");
    
    				if(columnName.length() > 0){
        				if(columnName.equals(requestInfo.getSortProperty())){
        					println("</td>");
        					println("<td align=\"right\" width=1>");
        					print("<div class=\"");
        					print(requestInfo.getSortOrder());
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
	 * Renderiza os controles (pagina��o, bot�es) do componente.
	 * 
	 * @throws Throwable
	 */
	private void renderControls() throws Throwable{
		println("<table class=\"gridControl\">");
		println("<tr>");

		renderPager();
		renderButtons();

		println("</tr>");
		println("</table>");
	}

	/**
	 * Renderiza a pagina��o do componente.
	 * 
	 * @throws Throwable
	 */
	private void renderPager() throws Throwable{
		if(pagerTag != null){
			println("<td align=\"center\">");

			pagerTag.render();

			println("</td>");
		}
	}

	/**
	 * Renderiza os bot�es do componente.
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
	 * @see br.com.concepting.framework.web.taglibs.BasePropertyTag#clearAttributes()
	 */
	protected void clearAttributes(){
		super.clearAttributes();

		setOnSelect("");
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