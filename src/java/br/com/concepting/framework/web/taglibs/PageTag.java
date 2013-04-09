package br.com.concepting.framework.web.taglibs;

import java.io.PrintWriter;
import java.text.DateFormatSymbols;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.struts.taglib.html.HtmlTag;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.util.ExceptionUtil;
import br.com.concepting.framework.util.LanguageUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.web.SystemController;
import br.com.concepting.framework.web.form.types.ActionFormMessageType;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;
import br.com.concepting.framework.web.types.VisibilityType;

/**
 * Classe que define o componente visual de uma página WEB.
 *
 * @author fvilarinho
 * @since 1.0
 */
public class PageTag extends HtmlTag{
    private   String           title            = "";
    private   String           encoding         = "";
	private   String           language         = "";
	private   String           resourceId       = "";
    protected SystemController systemController = null;
    protected PrintWriter      out              = null;

	/**
	 * Retorna o título da página.
	 *
	 * @return String contendo o título da página.
	 */
	public String getTitle(){
    	return title;
    }

	/**
	 * Define o título da página.
	 *
	 * @param title String contendo o título da página.
	 */
	public void setTitle(String title){
    	this.title = title;
    }

	/**
	 * Retorna o idioma atual da página.
	 *
	 * @return String contendo o identificador do idioma atual.
	 */
	public String getLanguage(){
    	return language;
    }

	/**
	 * Define o idioma atual da página.
	 *
	 * @param language String contendo o identificador do idioma atual.
	 */
	public void setLanguage(String language){
    	this.language = language;
    }

	/**
	 * Retorna o identificador do arquivo de propriedades a ser utilizado pelos componentes 
	 * da página.
	 * 
	 * @return String contendo o identificador do arquivo de propriedades.
	 */
	public String getResourceId(){
		return resourceId;
	}

	/**
	 * Define o identificador do arquivo de propriedades a ser utilizado pelos componentes 
	 * da página.
	 * 
	 * @param resourceId String contendo o identificador do arquivo de propriedades.
	 */
	public void setResourceId(String resourceId){
		this.resourceId = resourceId;
	}

	/**
	 * Retorna o tipo de codificação.
	 *
	 * @return String contendo o tipo de codificação.
	 */
	public String getEncoding(){
    	return encoding;
    }

	/**
	 * Define o tipo de codificação.
	 *
	 * @param encoding String contendo o tipo de codificação.
	 */
	public void setEncoding(String encoding){
    	this.encoding = encoding;
    }

	/**
	 * @see javax.servlet.jsp.tagext.TagSupport#setPageContext(javax.servlet.jsp.PageContext)
	 */
	public void setPageContext(PageContext pageContext){
		super.setPageContext(pageContext);
		
        systemController = new SystemController(pageContext);
        out              = systemController.getOut();
	}
	
	/**
	 * @see org.apache.struts.taglib.html.HtmlTag#doStartTag()
	 */
	public int doStartTag() throws JspException{
		if(systemController != null){
    		try{
        		super.doStartTag();
        
        		out.println();
        		
            	out.println("<head>");
            	
            	org.apache.struts.taglib.html.BaseTag baseTag = new org.apache.struts.taglib.html.BaseTag();
            	
            	baseTag.setPageContext(pageContext);
            	baseTag.doStartTag();
            	baseTag.doEndTag();
 
        		out.println();
        		
        		String encoding = StringUtil.trim(getEncoding());
        		
        		if(encoding.length() == 0){
        		    encoding = Constants.DEFAULT_ENCODING;
        		    
        		    setEncoding(encoding);
        		}

        		if(encoding.length() > 0){
            		out.print("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=");
            		out.print(encoding);
            		out.println("\">");
            		
            		systemController.setEncoding(encoding);
            	}
        		
        		if(language.length() > 0){
            		out.print("<meta http-equiv=\"Content-Language\" content=\"");
            		out.print(language);
            		out.println("\">");
            		
            		systemController.setCurrentLanguage(language);
        		}
        		
        		if(title.length() > 0){
        			out.print("<title>");
        			out.print(title);
        			out.println("</title>");
        		}
            	
            	renderImports();

            	out.println("</head>");
            	out.println("<body>");
            	
                out.print("<div id=\"");
                out.print(AttributeConstants.SHADE_KEY);
                out.print("\" class=\"");
                out.print(TaglibConstants.DEFAULT_SHADE_STYLE_CLASS);
                out.print("\" style=\"visibility: ");
                out.print(VisibilityType.HIDDEN);
                out.println(";\"></div>");
    		}
    		catch(Throwable e){
                if(!ExceptionUtil.isExpectedException(e) && !ExceptionUtil.isInternalErrorException(e))
                    systemController.forward(new InternalErrorException(e));
                else
                    systemController.forward(e);
    		}
		}
		
		return EVAL_BODY_INCLUDE;
    }

	/**
	 * Renderiza imports da página, ou seja, inclui as referências de javascripts e 
	 * estilos CSS.
	 *
	 * @throws Throwable
	 */
	private void renderImports() throws Throwable{
		renderImports(systemController);
	}
	
	/**
	 * Renderiza os eventos de página.
	 * 
	 * @throws Throwable
	 */
	private void renderEvents() throws Throwable{
	    renderEvents(systemController);
	}
	
	/**
	 * Renderiza as caixas de mensagem da página.
	 * 
	 * @throws Throwable
	 */
	private void renderMessageBoxes() throws Throwable{
	    renderMessageBoxes(systemController);
	}
	
    /**
     * Renderiza as eventos da página.
     * 
     * @param systemController Instância da classe de controle de requisições.
     * @throws Throwable
     */
    protected static void renderEvents(SystemController systemController) throws Throwable{
        StringBuilder content = new StringBuilder();
        
        content.append("addClickEvent(hideAllMenus);");
        content.append(StringUtil.getLineBreak());

        content.append("addClickEvent(hideAllParentMenus);");
        content.append(StringUtil.getLineBreak());

        content.append("addLoadEvent(hideLoadingBox);");
        content.append(StringUtil.getLineBreak());

        content.append("addLoadEvent(hideParentLoadingBox);");
        content.append(StringUtil.getLineBreak());
        
        content.append("addScrollEvent(centralizeDialogBoxes);");
        content.append(StringUtil.getLineBreak());
        
        ScriptTag scriptTag = new ScriptTag();
        
        scriptTag.setPageContext(systemController.getPageContext());
        scriptTag.setContent(content.toString());
        scriptTag.doStartTag();
        scriptTag.doEndTag();
    }

    /**
     * Renderiza as caixas de mensagens da página.
     * 
     * @param systemController Instância da classe de controle de requisições.
     * @throws Throwable
     */
    protected static void renderMessageBoxes(SystemController systemController) throws Throwable{
        PageContext   pageContext        = systemController.getPageContext();
        MessageBoxTag errorMessageBoxTag = new MessageBoxTag();

        errorMessageBoxTag.setPageContext(pageContext);
        errorMessageBoxTag.setShowException(true);
        errorMessageBoxTag.doStartTag();
        errorMessageBoxTag.doEndTag();
        
        MessageBoxTag infoMessageBoxTag = new MessageBoxTag();

        infoMessageBoxTag.setPageContext(pageContext);
        infoMessageBoxTag.setType(ActionFormMessageType.INFO);
        infoMessageBoxTag.doStartTag();
        infoMessageBoxTag.doEndTag();

        MessageBoxTag warningMessageBoxTag = new MessageBoxTag();

        warningMessageBoxTag.setPageContext(pageContext);
        warningMessageBoxTag.setType(ActionFormMessageType.WARNING);
        warningMessageBoxTag.setShowOnLoad(true);
        warningMessageBoxTag.doStartTag();
        warningMessageBoxTag.doEndTag();

        errorMessageBoxTag = new MessageBoxTag();
        errorMessageBoxTag.setPageContext(pageContext);
        errorMessageBoxTag.setType(ActionFormMessageType.ERROR);
        errorMessageBoxTag.doStartTag();
        errorMessageBoxTag.doEndTag();

        MessageBoxTag validationMessageBoxTag = new MessageBoxTag();

        validationMessageBoxTag.setPageContext(pageContext);
        validationMessageBoxTag.setType(ActionFormMessageType.VALIDATION);
        validationMessageBoxTag.doStartTag();
        validationMessageBoxTag.doEndTag();
    }
	
	/**
	 * Renderiza imports da página, ou seja, inclui as referências de javascripts e 
	 * estilos CSS.
	 *
     * @param systemController Instância da classe de controle de requisições.
	 * @throws Throwable
	 */
	protected static void renderImports(SystemController systemController) throws Throwable{
	    PageContext pageContext = systemController.getPageContext();
    	ScriptTag   scriptTag   = new ScriptTag();
    	StyleTag    styleTag    = new StyleTag();
    	
        scriptTag.setPageContext(pageContext);
        scriptTag.setUrl(TaglibConstants.DEFAULT_SCRIPT_RESOURCE_ID);
        scriptTag.doStartTag();
        scriptTag.doEndTag();

        styleTag.setPageContext(pageContext);
        styleTag.setUrl(TaglibConstants.DEFAULT_STYLE_RESOURCE_ID);
        styleTag.doStartTag();
        styleTag.doEndTag();

        scriptTag.setUrl(TaglibConstants.DEFAULT_CALENDAR_SCRIPT_RESOURCE_ID);
		scriptTag.doStartTag();
		scriptTag.doEndTag();
		
        scriptTag.setUrl(TaglibConstants.DEFAULT_PAGE_SCRIPT_RESOURCE_ID);
        scriptTag.doStartTag();
        scriptTag.doEndTag();

        styleTag.setUrl(TaglibConstants.DEFAULT_PAGE_STYLE_RESOURCE_ID);
        styleTag.doStartTag();
        styleTag.doEndTag();

        scriptTag.setUrl(TaglibConstants.DEFAULT_PAGE_SCRIPT_RESOURCE_ID.substring(1));
        scriptTag.doStartTag();
        scriptTag.doEndTag();

        styleTag.setUrl(TaglibConstants.DEFAULT_PAGE_STYLE_RESOURCE_ID.substring(1));
        styleTag.doStartTag();
        styleTag.doEndTag();

        StringBuilder content = new StringBuilder();
        
        content.append("initializeCalendarWeekNames(");

        Locale currentLanguage = systemController.getCurrentLanguage();
        
        if(currentLanguage == null)
            currentLanguage = LanguageUtil.getDefaultLanguage();
        
        DateFormatSymbols symbols     = new DateFormatSymbols(currentLanguage);
        String            weekNames[] = symbols.getWeekdays();

        for(Integer cont = 0 ; cont < weekNames.length ; cont++){
            if(cont > 0)
                content.append(", ");
            
            content.append("\"");
            content.append(weekNames[cont]);
            content.append("\"");
        }
        
        content.append(");");
        content.append(StringUtil.getLineBreak());
        
        scriptTag.setContent(content.toString());
        scriptTag.doStartTag();
        scriptTag.doEndTag();

        content.delete(0, content.length());
        content.append("initializeCalendarMonthNames(");

        String monthNames[] = symbols.getMonths();
        
        for(Integer cont = 0 ; cont < monthNames.length ; cont++){
            if(cont > 0)
                content.append(", ");

            content.append("\"");
            content.append(monthNames[cont]);
            content.append("\"");
        }
        
        content.append(");");
        content.append(StringUtil.getLineBreak());

        scriptTag.setContent(content.toString());
        scriptTag.doStartTag();
        scriptTag.doEndTag();

		styleTag.setUrl(TaglibConstants.DEFAULT_CALENDAR_STYLE_RESOURCE_ID);
		styleTag.doStartTag();
		styleTag.doEndTag();

        scriptTag.setUrl(TaglibConstants.DEFAULT_DIALOG_BOX_SCRIPT_RESOURCE_ID);
        scriptTag.doStartTag();
        scriptTag.doEndTag();

        styleTag.setUrl(TaglibConstants.DEFAULT_DIALOG_BOX_STYLE_RESOURCE_ID);
        styleTag.doStartTag();
        styleTag.doEndTag();

        scriptTag.setUrl(TaglibConstants.DEFAULT_GRID_SCRIPT_RESOURCE_ID);
		scriptTag.doStartTag();
		scriptTag.doEndTag();

		styleTag.setUrl(TaglibConstants.DEFAULT_GRID_STYLE_RESOURCE_ID);
		styleTag.doStartTag();
		styleTag.doEndTag();

		scriptTag.setUrl(TaglibConstants.DEFAULT_GUIDES_SCRIPT_RESOURCE_ID);
		scriptTag.doStartTag();
		scriptTag.doEndTag();

		styleTag.setUrl(TaglibConstants.DEFAULT_GUIDES_STYLE_RESOURCE_ID);
		styleTag.doStartTag();
		styleTag.doEndTag();

		scriptTag.setUrl(TaglibConstants.DEFAULT_MENU_BAR_SCRIPT_RESOURCE_ID);
		scriptTag.doStartTag();
		scriptTag.doEndTag();		

		styleTag.setUrl(TaglibConstants.DEFAULT_MENU_BAR_STYLE_RESOURCE_ID);
		styleTag.doStartTag();
		styleTag.doEndTag();

		scriptTag.setUrl(TaglibConstants.DEFAULT_MESSAGE_BOX_SCRIPT_RESOURCE_ID);
		scriptTag.doStartTag();
		scriptTag.doEndTag();

		styleTag.setUrl(TaglibConstants.DEFAULT_MESSAGE_BOX_STYLE_RESOURCE_ID);
		styleTag.doStartTag();
		styleTag.doEndTag();

		scriptTag.setUrl(TaglibConstants.DEFAULT_PAGER_SCRIPT_RESOURCE_ID);
		scriptTag.doStartTag();
		scriptTag.doEndTag();

		styleTag.setUrl(TaglibConstants.DEFAULT_PAGER_STYLE_RESOURCE_ID);
		styleTag.doStartTag();
		styleTag.doEndTag();

		scriptTag.setUrl(TaglibConstants.DEFAULT_PROGRESS_BAR_SCRIPT_RESOURCE_ID);
		scriptTag.doStartTag();
		scriptTag.doEndTag();

		styleTag.setUrl(TaglibConstants.DEFAULT_PROGRESS_BAR_STYLE_RESOURCE_ID);
		styleTag.doStartTag();
		styleTag.doEndTag();

        scriptTag.setUrl(TaglibConstants.DEFAULT_TREE_VIEW_SCRIPT_RESOURCE_ID);
		scriptTag.doStartTag();
		scriptTag.doEndTag();

		styleTag.setUrl(TaglibConstants.DEFAULT_TREE_VIEW_STYLE_RESOURCE_ID);
		styleTag.doStartTag();
		styleTag.doEndTag();
	}

    /**
     * @see org.apache.struts.taglib.html.HtmlTag#doEndTag()
     */
    public int doEndTag() throws JspException{
    	if(systemController != null){
        	try{
        	    renderMessageBoxes();
        	    renderEvents();
        	    
                out.println("</body>");
        	}
    		catch(Throwable e){
    		    systemController.setCurrentException(e);
    		}
    	}
    	
	    return EVAL_PAGE;
    }
}