package br.com.concepting.framework.web.taglibs;

import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.struts.taglib.html.HiddenTag;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.resource.I18nResourceLoader;
import br.com.concepting.framework.resource.PropertiesResource;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.web.SystemController;
import br.com.concepting.framework.web.form.types.ActionFormMessageType;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;
import br.com.concepting.framework.web.types.DisplayType;
import br.com.concepting.framework.web.types.ScopeType;
import br.com.concepting.framework.web.types.VisibilityType;
 
/**
 * Classe que define o componente visual do formulário para entrada de dados.
 * 
 * @author fvilarinho
 * @since 2.0
 */
public class ActionFormTag extends org.apache.struts.taglib.html.FormTag{
	private   String           language         = "";
	private   String           resourceDir      = "";
	private   String           resourceId       = "";
    protected SystemController systemController = null;
	protected PrintWriter      out              = null;

    /**
	 * Retorna o identificador do diretório que contém os arquivos de propriedades.
	 * 
	 * @return String contendo o identificador do diretório.
	 */
	public String getResourceDir(){
    	return resourceDir;
    }

	/**
	 * Define o identificador do diretório que contém os arquivos de propriedades.
	 * 
	 * @param resourceDir String contendo o identificador do diretório.
	 */
	public void setResourceDir(String resourceDir){
    	this.resourceDir = resourceDir;
    }

	/**
	 * Retorna o idioma atual do formulário.
	 *
	 * @return String contendo o identificador do idioma atual.
	 */
	public String getLanguage(){
    	return language;
    }
	
	/**
	 * Retorna a codificação atual do formulário.
	 * 
	 * @return String contendo a codificação atual.
	 */
	public String getEncoding(){
	    return getAcceptCharset();
	}
	
    /**
     * Define a codificação atual do formulário.
     * 
     * @param encoding String contendo a codificação atual.
     */
	public void setEncoding(String encoding){
	    setAcceptCharset(encoding);
	}

	/**
	 * Define o idioma atual do formulário.
	 *
	 * @param language String contendo o identificador do idioma atual.
	 */
	public void setLanguage(String language){
    	this.language = language;
    }

	/**
	 * Retorna o identificador do arquivo de propriedades a ser utilizado pelos componentes 
	 * do formulário.
	 * 
	 * @return String contendo o identificador do arquivo de propriedades.
	 */
	public String getResourceId(){
		return resourceId;
	}

	/**
	 * Define o identificador do arquivo de propriedades a ser utilizado pelos componentes 
	 * do formulário.
	 * 
	 * @param resourceId String contendo o identificador do arquivo de propriedades.
	 */
	public void setResourceId(String resourceId){
		this.resourceId = resourceId;
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
	 * Retorna a instância contendo as propriedades do arquivo de internacionalização 
	 * específico.
	 * 
	 * @param resourceDir String contendo o identificador do diretório dos arquivos.
	 * @param resourceId String contendo o identificador do arquivo.
	 * @return Instância contendo as propriedades do arquivo de internacionalização.
	 * @throws Throwable
	 */
	protected PropertiesResource getI18nResource(String resourceDir, String resourceId) throws Throwable{
		Locale             currentLanguage = systemController.getCurrentLanguage();
		I18nResourceLoader loader          = null;
		
		if(resourceId.length() == 0)
			loader = new I18nResourceLoader(currentLanguage);
		else	
			loader = new I18nResourceLoader(resourceDir, resourceId, currentLanguage);
		
		PropertiesResource resource = loader.getContent();
		
		return resource;
	}

	/**
	 * Retorna a instância contendo as propriedades do arquivo de internacionalização 
	 * específico.
	 * 
	 * @param resourceId String contendo o identificador do arquivo.
	 * @return Instância contendo as propriedades do arquivo de internacionalização.
	 * @throws Throwable
	 */
	protected PropertiesResource getI18nResource(String resourceId) throws Throwable{
		return getI18nResource("", resourceId);
	}

	/**
	 * Retorna a instância contendo as propriedades do arquivo de internacionalização do 
	 * componente.
	 * 
	 * @return Instância contendo as propriedades do arquivo de internacionalização.
	 * @throws Throwable
	 */
	protected PropertiesResource getI18nResource() throws Throwable{
		return getI18nResource(getResourceDir(), getResourceId());
	}

	/**
	 * Retorna a instância contendo as propriedades do arquivo de internacionalização default.
	 * 
	 * @return Instância contendo as propriedades do arquivo de internacionalização.
	 * @throws Throwable
	 */
	protected PropertiesResource getDefaultI18nResource() throws Throwable{
		return getI18nResource("", "");
	}
	
    /**
     * Inicializa as propriedades do componente.
     * 
     * @throws Throwable
     */
	protected void initialize() throws Throwable{
	    setOnsubmit("return false");
	    
        PageTag pageTag = (PageTag)findAncestorWithClass(this, PageTag.class);
        
        if(pageTag != null){
            if(resourceId.length() == 0){
                resourceId = pageTag.getResourceId();
                
                setResourceId(resourceId);
            }
            
            String encoding = StringUtil.trim(getEncoding());
            
            if(encoding.length() == 0){
                encoding = pageTag.getEncoding();
                
                setEncoding(encoding);
            }
            
            if(language.length() == 0){
                language = pageTag.getLanguage();
                
                setLanguage(language);
            }
        }
	}
	
    /**
     * Renderiza a abertura do componente.
     * 
     * @throws Throwable
     */
	protected void renderOpen() throws Throwable{
        if(findAncestorWithClass(this, PageTag.class) == null)
            PageTag.renderImports(systemController);
	    
        renderAttributes();
        
        out.println();

        out.print("<div id=\"");
        out.print(getBeanName());
        out.print(".");
        out.print(TaglibConstants.LOADING_BOX_KEY);
        out.print("\" class=\"");
        out.print(TaglibConstants.DEFAULT_LOADING_BOX_STYLE_CLASS);
        out.print("\" style=\"visibility: ");
        out.print(VisibilityType.HIDDEN);
        out.println(";\">");
        
        PropertiesResource resource = getDefaultI18nResource();
        String             message  = StringUtil.trim(resource.getProperty(TaglibConstants.LOADING_BOX_INFO_KEY));
 
        out.print("<span id=\"");
        out.print(getBeanName());
        out.print(".");
        out.print(TaglibConstants.LOADING_BOX_INFO_KEY);
        out.print("\" class=\"");
        out.print(TaglibConstants.DEFAULT_LOADING_BOX_INFO_STYLE_CLASS);
        out.println("\">");
        
        out.println("<table>");
        
        out.println("<tr>");
        out.print("<td align=\"");
        out.print(AlignmentType.CENTER);
        out.println("\">");
        out.print("<div class=\"");
        out.print(TaglibConstants.DEFAULT_LOADING_BOX_ICON_STYLE_CLASS);
        out.println("\"></div>");
        out.println("</td>");
        out.println("</tr>");
        
        out.println("<tr>");
        out.print("<td class=\"");
        out.print(TaglibConstants.DEFAULT_LOADING_BOX_TEXT_STYLE_CLASS);
        out.print("\">");
        out.print(message);
        out.println("</td>");
        out.println("</tr>");
        
        out.println("</table>");
        
        out.println("</span>");

        message = StringUtil.trim(resource.getProperty(TaglibConstants.LOADING_BOX_ERROR_KEY));
        
        out.print("<span id=\"");
        out.print(getBeanName());
        out.print(".");
        out.print(TaglibConstants.LOADING_BOX_ERROR_KEY);
        out.print("\" class=\"");
        out.print(TaglibConstants.DEFAULT_LOADING_BOX_TEXT_STYLE_CLASS);
        out.print("\" style=\"display: ");
        out.print(DisplayType.NONE);
        out.println(";\">");
        
        out.println("<table>");
        
        out.println("<tr>");
        out.print("<td class=\"");
        out.print(TaglibConstants.DEFAULT_LOADING_BOX_TEXT_STYLE_CLASS);
        out.print("\">");
        out.print(message);
        out.println("</td>");
        out.println("</tr>");
        
        out.println("<tr>");
        out.print("<td align=\"");
        out.print(AlignmentType.CENTER);
        out.println("\">");
        
        ConfirmButtonTag closeButtonTag = new ConfirmButtonTag();

        closeButtonTag.setPageContext(pageContext);
        closeButtonTag.setOnClick("hideLoadingBox();");
        closeButtonTag.doStartTag();
        closeButtonTag.doEndTag();

        out.println("</td>");
        out.println("</tr>");
        
        out.println("</table>");

        out.println("</span>");
        
        out.println("</div>");
	}
	
    /**
     * Renderiza o fechamento do componente.
     * 
     * @throws Throwable
     */
	protected void renderClose() throws Throwable{
        renderMessageBoxes(systemController);
        
        if(findAncestorWithClass(this, PageTag.class) == null)
            PageTag.renderEvents(systemController);
	}
	

    /**
     * Renderiza as caixas de mensagens da página.
     * 
     * @param systemController Instância da classe de controle de requisições.
     * @throws Throwable
     */
    protected void renderMessageBoxes(SystemController systemController) throws Throwable{
        PageContext   pageContext        = systemController.getPageContext();
        MessageBoxTag errorMessageBoxTag = new MessageBoxTag();

        errorMessageBoxTag.setPageContext(pageContext);
        errorMessageBoxTag.setShowException(true);
        errorMessageBoxTag.setActionFormTag(this);
        errorMessageBoxTag.setShowOnLoad(true);
        errorMessageBoxTag.doStartTag();
        errorMessageBoxTag.doEndTag();
        
        MessageBoxTag infoMessageBoxTag = new MessageBoxTag();

        infoMessageBoxTag.setPageContext(pageContext);
        infoMessageBoxTag.setType(ActionFormMessageType.INFO);
        infoMessageBoxTag.setActionFormTag(this);
        infoMessageBoxTag.setShowOnLoad(true);
        infoMessageBoxTag.doStartTag();
        infoMessageBoxTag.doEndTag();

        MessageBoxTag warningMessageBoxTag = new MessageBoxTag();

        warningMessageBoxTag.setPageContext(pageContext);
        warningMessageBoxTag.setType(ActionFormMessageType.WARNING);
        warningMessageBoxTag.setActionFormTag(this);
        warningMessageBoxTag.setShowOnLoad(true);
        warningMessageBoxTag.doStartTag();
        warningMessageBoxTag.doEndTag();

        errorMessageBoxTag = new MessageBoxTag();
        errorMessageBoxTag.setPageContext(pageContext);
        errorMessageBoxTag.setType(ActionFormMessageType.ERROR);
        errorMessageBoxTag.setActionFormTag(this);
        errorMessageBoxTag.setShowOnLoad(true);
        errorMessageBoxTag.doStartTag();
        errorMessageBoxTag.doEndTag();

        MessageBoxTag validationMessageBoxTag = new MessageBoxTag();

        validationMessageBoxTag.setPageContext(pageContext);
        validationMessageBoxTag.setType(ActionFormMessageType.VALIDATION);
        validationMessageBoxTag.setActionFormTag(this);
        validationMessageBoxTag.setShowOnLoad(true);
        validationMessageBoxTag.doStartTag();
        validationMessageBoxTag.doEndTag();
    }

	/**
	 * Renderiza os atributos do componente.
	 * 
	 * @throws Throwable
	 */
	protected void renderAttributes() throws Throwable{
	    out.println();

        HiddenTag actionTag = new HiddenTag();
        
        actionTag.setPageContext(pageContext);
        actionTag.setName(getBeanName());
        actionTag.setProperty(AttributeConstants.ACTION_KEY);
        actionTag.doStartTag();
        actionTag.doEndTag();

        out.println();

        HiddenTag lastActionTag = new HiddenTag();
        
        lastActionTag.setPageContext(pageContext);
        lastActionTag.setName(getBeanName());
        lastActionTag.setProperty(AttributeConstants.LAST_ACTION_KEY);
        lastActionTag.doStartTag();
        lastActionTag.doEndTag();

        out.println();

        HiddenTag forwardTag = new HiddenTag();

        forwardTag.setPageContext(pageContext);
        forwardTag.setName(getBeanName());
        forwardTag.setProperty(AttributeConstants.FORWARD_KEY);
        forwardTag.setValue("");
        forwardTag.doStartTag();
        forwardTag.doEndTag();

        out.println();

        HiddenTag forwardOnFailTag = new HiddenTag();

        forwardOnFailTag.setPageContext(pageContext);
        forwardOnFailTag.setName(getBeanName());
        forwardOnFailTag.setProperty(AttributeConstants.FORWARD_ON_FAIL_KEY);
        forwardOnFailTag.setValue("");
        forwardOnFailTag.doStartTag();
        forwardOnFailTag.doEndTag();

        out.println();

        HiddenTag updateViewsTag = new HiddenTag();

        updateViewsTag.setPageContext(pageContext);
        updateViewsTag.setName(getBeanName());
        updateViewsTag.setProperty(AttributeConstants.UPDATE_VIEWS_KEY);
        updateViewsTag.setValue("");
        updateViewsTag.doStartTag();
        updateViewsTag.doEndTag();

        out.println();

        HiddenTag validateModelTag = new HiddenTag();

        validateModelTag.setPageContext(pageContext);
        validateModelTag.setName(getBeanName());
        validateModelTag.setProperty(AttributeConstants.VALIDATE_MODEL_KEY);
        validateModelTag.setValue("false");
        validateModelTag.doStartTag();
        validateModelTag.doEndTag();

        out.println();

        HiddenTag validateSearchModelTag = new HiddenTag();
        
        validateSearchModelTag.setPageContext(pageContext);
        validateSearchModelTag.setName(getBeanName());
        validateSearchModelTag.setProperty(AttributeConstants.VALIDATE_SEARCH_MODEL_KEY);
        validateSearchModelTag.setValue("false");
        validateSearchModelTag.doStartTag();
        validateSearchModelTag.doEndTag();

        out.println();

        HiddenTag validatePropertiesTag = new HiddenTag();
        
        validatePropertiesTag.setPageContext(pageContext);
        validatePropertiesTag.setName(getBeanName());
        validatePropertiesTag.setProperty(AttributeConstants.VALIDATE_PROPERTIES_KEY);
        validatePropertiesTag.setValue("");
        validatePropertiesTag.doStartTag();
        validatePropertiesTag.doEndTag();
	}
	
	/**
	 * Limpa as propriedades do componente.
	 */
	protected void clearAttributes(){
	    setLanguage("");
	    setEncoding("");
	    setResourceId("");
	    setResourceDir("");
	}
	
    /**
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag() throws JspException{
        if(systemController != null){
            try{
                initialize();
                
                super.doStartTag();

                out.println();

                renderOpen();
                
                systemController.setAttribute(AttributeConstants.CURRENT_ACTION_FORM_KEY, this, ScopeType.REQUEST);
            }
            catch(Throwable e){
                systemController.setCurrentException(e);
            }
        }

        return EVAL_BODY_INCLUDE;
    }
    
    /**
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    public int doEndTag() throws JspException{
        if(systemController != null){
            try{
                out.println();
                
                renderClose();
                
                super.doEndTag();
            }
            catch(Throwable e){
                systemController.setCurrentException(e);
            }
            finally{
                clearAttributes();
            }
        }
        
        return EVAL_PAGE;
    }
}