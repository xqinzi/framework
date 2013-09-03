package br.com.concepting.framework.ui.taglibs;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.controller.helpers.RequestInfo;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.resource.PropertiesResource;
import br.com.concepting.framework.ui.taglibs.constants.TaglibConstants;
import br.com.concepting.framework.util.Pager;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.util.types.PagerActionType;
import br.com.concepting.framework.util.types.ScopeType;

/**
 * Classe que define o componente visual pager (paginação).
 * 
 * @author fvilarinho
 * @since 3.0
 */ 
public class PagerTag extends BaseOptionsPropertyTag{
	private Pager   pager                 = null;
	private Boolean showItemsPerPage      = true;
	private Boolean showItemsPerPageLabel = true;
	private Integer itemsPerPage          = 0;
	private Boolean pagerOnForm           = true;
	private String  updateViews           = "";
    
	/**
	 * Indica se o componente está vinculado ao formulário.
	 * 
	 * @return True/False.
	 */
    protected Boolean pagerOnForm() {
		return pagerOnForm;
	}

    /**
     * Indica se o componente está vinculado ao formulário.
     * 
     * @return True/False.
     */
    protected Boolean getPagerOnForm() {
		return pagerOnForm();
	}

    /**
     * Define se o componente está vinculado ao formulário.
     * 
     * @param pagerOnForm True/False.
     */
	protected void setPagerOnForm(Boolean pagerOnForm) {
		this.pagerOnForm = pagerOnForm;
	}
	
    /**
     * Retorna os identificadores das views (separados por vírgula) a serem atualizadas após o
     * processamento da ação requisitada.
     * 
     * @return String contendo os identificadores das views.
     */
	public String getUpdateViews(){
        return updateViews;
    }

    /**
     * Define os identificadores das views (separados por vírgula) a serem atualizadas após o
     * processamento da ação requisitada.
     * 
     * @param updateViews String contendo os identificadores das views.
     */
    public void setUpdateViews(String updateViews){
        this.updateViews = updateViews;
    }

    /**
     * Indica se o label da caixa de texto do número de itens por página deve ser exibido.
     * 
     * @return True/False.
     */
    public Boolean showItemsPerPageLabel(){
        return showItemsPerPageLabel;
    }

    /**
	 * Indica se o label da caixa de texto do número de itens por página deve ser exibido.
	 * 
	 * @return True/False.
	 */
	public Boolean getShowItemsPerPageLabel(){
    	return showItemsPerPageLabel();
    }

	/**
	 * Define se o label da caixa de texto do número de itens por página deve ser exibido.
	 * 
	 * @param showItemsPerPageLabel True/False.
	 */
	public void setShowItemsPerPageLabel(Boolean showItemsPerPageLabel){
    	this.showItemsPerPageLabel = showItemsPerPageLabel;
    }

	/**
	 * Indica se a caixa de texto do número de itens por página deve ser exibida.
	 * 
	 * @return True/False.
	 */
	public Boolean getShowItemsPerPage(){
    	return showItemsPerPage;
    }

	/**
	 * Indica se a caixa de texto do número de itens por página deve ser exibida.
	 * 
	 * @return True/False.
	 */
	public Boolean showItemsPerPage(){
		return showItemsPerPage;
	}

	/**
	 * Define se a caixa de texto do número de itens por página deve ser exibida.
	 * 
	 * @param showItemsPerPage True/False.
	 */
	public void setShowItemsPerPage(Boolean showItemsPerPage){
		this.showItemsPerPage = showItemsPerPage;
	}

	/**
	 * Retorna o número de itens por página a ser exibido.
	 * 
	 * @return Valor inteiro contendo o número de itens por página.
	 */
	public Integer getItemsPerPage(){
    	return itemsPerPage;
    }

	/**
	 * Define o número de itens por página a ser exibido.
	 * 
	 * @param itemsPerPage Valor inteiro contendo o número de itens por página.
	 */
	public void setItemsPerPage(Integer itemsPerPage){
		this.itemsPerPage = itemsPerPage;
	}
	
	/**
	 * Define se a caixa de texto do número de itens por página deve ser exibida.
	 * 
	 * @param pager Instância do paginador.
	 */
	protected void setPager(Pager pager){
    	this.pager = pager;
    }

	/**
	 * Retorna a instância do paginador.
	 * 
	 * @return Instância contendo o paginador.
	 */
	protected Pager getPager(){
	    if(isRendered()){
    		PropertyInfo propertyInfo = getPropertyInfo();
    	    
    	    if(propertyInfo == null || propertyInfo.isCollection())
    	    	return null;
    	    
            RequestInfo  requestInfo = getRequestInfo();
    		String       name        = getName();
    
    		if(pager == null){
        		Boolean            foundPager  = false;
        		Map<String, Pager> pagers      = systemController.findAttribute(TaglibConstants.PAGER_MAP_KEY, ScopeType.SESSION);
        		PagerActionType    pagerAction = requestInfo.getPagerAction();
        		
        		if(pagers != null && pagerAction != null){
        			pager = pagers.get(name);
        			
        			if(pager != null)
        				foundPager = true;
        		}
        		
        		if(!foundPager){
        			pager = new Pager();
        			
        			if(pagers == null)
        				pagers = new LinkedHashMap<String, Pager>();
        			
        			pagers.put(name, pager);
        			
        			systemController.setAttribute(TaglibConstants.PAGER_MAP_KEY, pagers, ScopeType.SESSION);
        		}
    		}
    		
    		Integer itemsPerPage = 0;
    
    		if(pagerOnForm)
    			itemsPerPage = 1;
    		else{
    			itemsPerPage = requestInfo.getItemsPerPage();
    			
    			StringBuilder cookieId = new StringBuilder();
    			
    			cookieId.append(getActionFormName());
    			cookieId.append(".");
    			cookieId.append(name);
    			cookieId.append(".");
    			cookieId.append(AttributeConstants.ITEMS_PER_PAGE_KEY);
    			
    			Cookie cookie = systemController.getCookie(cookieId.toString());
    			
    	    	if(itemsPerPage == 0){
    	    		if(cookie != null)
    	    		    itemsPerPage = Integer.parseInt(cookie.getValue());
    	    		else if(getItemsPerPage() == 0){
    	    		    itemsPerPage = Constants.DEFAULT_ITEMS_PER_PAGE;
    	
    	    			systemController.addCookie(cookieId.toString(), itemsPerPage.toString(), true);
    	    		}
    	    		
    	    		setItemsPerPage(itemsPerPage);
    	    	}
    	    	else{
    	    		setItemsPerPage(itemsPerPage);
    	
    				systemController.addCookie(cookieId.toString(), itemsPerPage.toString(), true);
    			}
    		}
    
        	pager.setItemsPerPage(itemsPerPage);
        	
        	List dataValues = getDataValues();
        	
    		pager.setData(dataValues);
    		
    		return pager;
	    }
	    
	    return null;
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#initialize()
	 */
	protected void initialize() throws Throwable{
		GridPropertyTag gridTag = (GridPropertyTag)findAncestorWithClass(this, GridPropertyTag.class);

		if(gridTag != null){
			setName(gridTag.getName());
			setData(gridTag.getData());
			setDataValues(gridTag.getDataValues());
			setDataScope(gridTag.getDataScope());
			setValueMap(gridTag.getValueMap());
			setValueMapInstance(gridTag.getValueMapInstance());
			setPagerOnForm(false);
		}

        super.initialize();

		Pager pager = getPager();
		
		if(pager != null){
			if(pagerOnForm){
			    setDataStartIndex(0);
			    setDataEndIndex(0);
			    
				List dataValues = getDataValues();
				
				if(dataValues != null && dataValues.size() > 0)
					setDataEndIndex(dataValues.size() - 1);
			}
			
			Integer         currentPage = pager.getCurrentPage();
			RequestInfo     requestInfo = getRequestInfo();
			PagerActionType pagerAction = requestInfo.getPagerAction();
				
			if(pagerAction == null)
				pagerAction = PagerActionType.REFRESH_PAGE;
			
			if(pagerAction == PagerActionType.REFRESH_PAGE)
				pager.goTo(currentPage);
			else
				pager.moveTo(pagerAction);
			
			if(gridTag != null){
			    PagerTag pagerTag = (PagerTag)this.clone();
			    
				gridTag.setPagerTag(pagerTag);
			}
		}
	}
	
	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#renderBody()
	 */
	protected void renderBody() throws Throwable{
		GridPropertyTag gridTag      = (GridPropertyTag)findAncestorWithClass(this, GridPropertyTag.class);
		PropertyInfo    propertyInfo = getPropertyInfo();
		
		if(propertyInfo == null || propertyInfo.isCollection()){
			print("<table class=\"");
			print(TaglibConstants.DEFAULT_PAGER_STYLE_CLASS);
			println("\">");
			println("<tr>");
            print("<td align=\"");
            print(AlignmentType.CENTER);
            print("\" class=\"");
            print(TaglibConstants.DEFAULT_LABEL_STYLE_CLASS);
            println("\">");
			println(getInvalidPropertyMessage());
			println("</td>");
			println("</tr>");
			println("</table>");
		}
		else{
			if(gridTag == null){
	            print("<table class=\"");
	            print(TaglibConstants.DEFAULT_PAGER_STYLE_CLASS);
	            println("\">");
				println("<tr>");
	
				if(showItemsPerPage() && !pagerOnForm){
                    print("<td align=\"");
                    print(AlignmentType.CENTER);
                    println("\">");
	
					ItemsPerPagePropertyTag itemsPerPageTag = new ItemsPerPagePropertyTag(this);
			
					itemsPerPageTag.doStartTag();
					itemsPerPageTag.doEndTag();
	
					println("</td>");
				}
	
                print("<td align=\"");
                print(AlignmentType.CENTER);
                println("\">");
	
				FirstPageButtonTag firstPageButtonTag = new FirstPageButtonTag(this);
	
				firstPageButtonTag.doStartTag();
				firstPageButtonTag.doEndTag();
	
				println("</td>");
	
                print("<td align=\"");
                print(AlignmentType.CENTER);
                println("\">");
	
				PreviousPageButtonTag previousPageButtonTag = new PreviousPageButtonTag(this);
	
				previousPageButtonTag.doStartTag();
				previousPageButtonTag.doEndTag();
	
				println("</td>");
				
                print("<td align=\"");
                print(AlignmentType.CENTER);
                println("\">");
	
				print("<table class=\"");
				print(TaglibConstants.DEFAULT_PAGER_DISPLAY_STYLE_CLASS);
				println("\">");
				println("<tr>");
                print("<td align=\"");
                print(AlignmentType.CENTER);
                println("\">");
				
				Integer currentPage = pager.getCurrentPage();
				
				print(currentPage);
				print("/");
				
				Integer pages = pager.getPages();
				
				print(pages);
				
				println("</td>");
				println("</tr>");
				println("</table>");
				
				println("</td>");
				
                print("<td align=\"");
                print(AlignmentType.CENTER);
                println("\">");
	
				NextPageButtonTag nextPageButtonTag = new NextPageButtonTag(this);
	
				nextPageButtonTag.doStartTag();
				nextPageButtonTag.doEndTag();
	
				println("</td>");
				
                print("<td align=\"");
                print(AlignmentType.CENTER);
                println("\">");
	
				LastPageButtonTag lastPageButtonTag = new LastPageButtonTag(this);
	
				lastPageButtonTag.doStartTag();
				lastPageButtonTag.doEndTag();
	
				println("</td>");
				
				println("</tr>");
				println("</table>");
			}
		}
	}
	
	/**
	 * @see br.com.concepting.framework.ui.taglibs.BasePropertyTag#renderOpen()
	 */
	protected void renderOpen() throws Throwable {
        String        name    = getName();
        StringBuilder tagName = new StringBuilder();

        tagName.append(name);
        tagName.append(".");
        tagName.append(AttributeConstants.PAGER_ACTION_KEY);

        HiddenPropertyTag pagerActionTag = new HiddenPropertyTag();

        pagerActionTag.setPageContext(pageContext);
        pagerActionTag.setName(tagName.toString());
        pagerActionTag.setValue(PagerActionType.REFRESH_PAGE);
        pagerActionTag.doStartTag();
        pagerActionTag.doEndTag();

        if(pagerOnForm){
			PropertyInfo propertyInfo = getPropertyInfo();
			
			if(propertyInfo != null && propertyInfo.isModel()){
				tagName.delete(0, tagName.length());
	            tagName.append(name);
	            tagName.append(".");
	            tagName.append(AttributeConstants.PAGER_ON_FORM_KEY);
	
	            HiddenPropertyTag pagerOnFormTag = new HiddenPropertyTag();
	
	            pagerOnFormTag.setPageContext(pageContext);
	            pagerOnFormTag.setName(tagName.toString());
	            pagerOnFormTag.setValue(pagerOnForm);
				pagerOnFormTag.doStartTag();
				pagerOnFormTag.doEndTag();
				
				Pager         pager     = getPager();
				Integer       dataIndex = pager.getCurrentPage() - 1;
				StringBuilder dataValue = new StringBuilder();
				
				dataValue.append("objectId{");
				dataValue.append(dataIndex);
				dataValue.append("}");
				
				HiddenPropertyTag propertyTag = new HiddenPropertyTag();
				
				propertyTag.setPageContext(pageContext);
				propertyTag.setName(name);
				propertyTag.setValue(dataValue.toString());
				propertyTag.doStartTag();
				propertyTag.doEndTag();
			}

	        renderLabelAttribute();
	        renderPatternAttribute();
	        renderDataAttributes();
	        renderDataIndexesAttributes();
		}
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#render()
	 */
	protected void render() throws Throwable{
		GridPropertyTag gridTag = (GridPropertyTag)findAncestorWithClass(this, GridPropertyTag.class);

		if(gridTag == null)
			super.render();
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BasePropertyTag#renderClose()
	 */
	protected void renderClose() throws Throwable{
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseActionFormElementTag#clearAttributes()
	 */
    protected void clearAttributes(){
	    super.clearAttributes();

	    setShowItemsPerPage(true);
	    setItemsPerPage(0);
	    setPagerOnForm(true);
	    setPager(null);
    }

    /**
     * Classe que define o componente visual para o botão primeira página.
     * 
     * @author fvilarinho
     * @since 1.0
     */
    private class FirstPageButtonTag extends ButtonTag{
    	/**
    	 * Construtor - Inicializa objetos e/ou variáveis internas.
    	 * 
    	 * @param pagerTag Instância do componente de paginação.
    	 */
    	public FirstPageButtonTag(PagerTag pagerTag){
    		super();
    		
    		setPageContext(pagerTag.getPageContext());
    		setOut(pagerTag.getOut());
    		setResourceId(TaglibConstants.DEFAULT_PAGER_I18N_RESOURCE_ID);
    		
    		Pager pager = pagerTag.getPager();
    
    		if(!pager.showFirstPageButton())
    			setEnabled(false);
    		else{
        		StringBuilder onClickContent = new StringBuilder();
        	    
    			onClickContent.append("moveToFirstPage('");
                onClickContent.append(pagerTag.getName());
                onClickContent.append("', '");
                onClickContent.append(updateViews);
                onClickContent.append("', '");
                onClickContent.append(pagerTag.getActionFormName());
                onClickContent.append("');");
    			
    			setOnClick(onClickContent.toString());
    		}
    	}
    }

    /**
     * Classe que define o componente visual para o botão página anterior.
     * 
     * @author fvilarinho
     * @since 1.0
     */
    private class PreviousPageButtonTag extends ButtonTag{
    	/**
    	 * Construtor - Inicializa objetos e/ou variáveis internas.
    	 * 
    	 * @param pagerTag Instância do componente de paginação.
    	 */
    	public PreviousPageButtonTag(PagerTag pagerTag){
            super();
            
            setPageContext(pagerTag.getPageContext());
            setOut(pagerTag.getOut());
            setResourceId(TaglibConstants.DEFAULT_PAGER_I18N_RESOURCE_ID);
            
            Pager pager = pagerTag.getPager();
    
            if(!pager.showPreviousPageButton())
                setEnabled(false);
            else{
                StringBuilder onClickContent = new StringBuilder();
                
                onClickContent.append("moveToPreviousPage('");
                onClickContent.append(pagerTag.getName());
                onClickContent.append("', '");
                onClickContent.append(updateViews);
                onClickContent.append("', '");
                onClickContent.append(pagerTag.getActionFormName());
                onClickContent.append("');");
                
                setOnClick(onClickContent.toString());
            }
    	}
    }

    /**
     * Classe que define o componente visual para o botão próxima página.
     * 
     * @author fvilarinho
     * @since 1.0
     */
    private class NextPageButtonTag extends ButtonTag{
    	/**
    	 * Construtor - Inicializa objetos e/ou variáveis internas.
    	 * 
    	 * @param pagerTag Instância do componente de paginação.
    	 */
    	public NextPageButtonTag(PagerTag pagerTag){
            super();
            
            setPageContext(pagerTag.getPageContext());
            setOut(pagerTag.getOut());
            setResourceId(TaglibConstants.DEFAULT_PAGER_I18N_RESOURCE_ID);
            
            Pager pager = pagerTag.getPager();
    
            if(!pager.showNextPageButton())
                setEnabled(false);
            else{
                StringBuilder onClickContent = new StringBuilder();
                
                onClickContent.append("moveToNextPage('");
                onClickContent.append(pagerTag.getName());
                onClickContent.append("', '");
                onClickContent.append(updateViews);
                onClickContent.append("', '");
                onClickContent.append(pagerTag.getActionFormName());
                onClickContent.append("');");
                
                setOnClick(onClickContent.toString());
            }
    	}
    }

    /**
     * Classe que define o componente visual para o botão última página.
     * 
     * @author fvilarinho
     * @since 1.0
     */
    private class LastPageButtonTag extends ButtonTag{
    	/**
    	 * Construtor - Inicializa objetos e/ou variáveis internas.
    	 * 
    	 * @param pagerTag Instância do componente de paginação.
    	 */
    	public LastPageButtonTag(PagerTag pagerTag){
            super();
            
            setPageContext(pagerTag.getPageContext());
            setOut(pagerTag.getOut());
            setResourceId(TaglibConstants.DEFAULT_PAGER_I18N_RESOURCE_ID);
            
            Pager pager = pagerTag.getPager();
    
            if(!pager.showLastPageButton())
                setEnabled(false);
            else{
                StringBuilder onClickContent = new StringBuilder();
                
                onClickContent.append("moveToLastPage('");
                onClickContent.append(pagerTag.getName());
                onClickContent.append("', '");
                onClickContent.append(updateViews);
                onClickContent.append("', '");
                onClickContent.append(pagerTag.getActionFormName());
                onClickContent.append("');");
                
                setOnClick(onClickContent.toString());
            }
    	}
    }
    
    /**
     * Classe que define o componente visual para o campo de itens por página.
     * 
     * @author fvilarinho
     * @since 1.0
     */
    private class ItemsPerPagePropertyTag extends TextPropertyTag{
    	/**
    	 * Construtor - Inicializa objetos e/ou variáveis internas.
    	 * 
    	 * @param pagerTag Instância do componente de paginação.
    	 * @throws Throwable
    	 */
    	public ItemsPerPagePropertyTag(PagerTag pagerTag) throws Throwable{
    		super();
    		
    		setPageContext(pagerTag.getPageContext());
    		setOut(pagerTag.getOut());
    		
            setResourceId(TaglibConstants.DEFAULT_PAGER_I18N_RESOURCE_ID);
    		
            StringBuilder tagName  = new StringBuilder();

            tagName.append(pagerTag.getName());
            tagName.append(".");
            tagName.append(AttributeConstants.ITEMS_PER_PAGE_KEY);
            
            setName(tagName.toString());

            PropertiesResource resources = getI18nResource();
            String             label     = resources.getProperty(TaglibConstants.DEFAULT_PAGER_ITEMS_PER_PAGE_LABEL_KEY, false);
            String             tooltip   = resources.getProperty(TaglibConstants.DEFAULT_PAGER_ITEMS_PER_PAGE_TOOLTIP_KEY, false);
            
            if(label == null || tooltip == null){
                resources = getDefaultI18nResource();
                
                if(label == null)
                    label = StringUtil.trim(resources.getProperty(TaglibConstants.DEFAULT_PAGER_ITEMS_PER_PAGE_LABEL_KEY));
                
                if(tooltip == null)
                    tooltip = StringUtil.trim(resources.getProperty(TaglibConstants.DEFAULT_PAGER_ITEMS_PER_PAGE_TOOLTIP_KEY));
            }
			
			setLabel(label);
            setTooltip(tooltip);
			setShowLabel(pagerTag.showItemsPerPageLabel());
			setSize(Constants.DEFAULT_ITEMS_PER_PAGE);
			setMaxlength(Constants.DEFAULT_ITEMS_PER_PAGE);
			setAlignment(AlignmentType.RIGHT.toString());
			
			Pager   pager        = pagerTag.getPager();
			Integer itemsPerPage = pager.getItemsPerPage();
			
			setValue(itemsPerPage);
			
			StringBuilder onKeyPressContent = new StringBuilder();
			
			onKeyPressContent.append("if(getKeyPressed(event) == 13) { refreshPage('");
            onKeyPressContent.append(pagerTag.getName());
            onKeyPressContent.append("', '");
            onKeyPressContent.append(updateViews);
			onKeyPressContent.append("', '");
            onKeyPressContent.append(pagerTag.getActionFormName());
            onKeyPressContent.append("'); } else { formatNumberObject(this, '###', event); }");
			
			this.setOnKeyPress(onKeyPressContent.toString());
    	}
    }
}