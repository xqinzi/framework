package br.com.concepting.framework.web.taglibs;

import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.struts.taglib.html.HiddenTag;

import br.com.concepting.framework.resource.I18nResourceLoader;
import br.com.concepting.framework.resource.PropertiesResource;
import br.com.concepting.framework.util.ExceptionUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.constants.AttributeConstants;
import br.com.concepting.framework.util.exceptions.InternalErrorException;
import br.com.concepting.framework.web.SystemController;
import br.com.concepting.framework.web.types.ScopeType;
 
/**
 * Classe que define o componente visual do formul�rio para entrada de dados.
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
	 * Retorna o identificador do diret�rio que cont�m os arquivos de propriedades.
	 * 
	 * @return String contendo o identificador do diret�rio.
	 */
	public String getResourceDir(){
    	return resourceDir;
    }

	/**
	 * Define o identificador do diret�rio que cont�m os arquivos de propriedades.
	 * 
	 * @param resourceDir String contendo o identificador do diret�rio.
	 */
	public void setResourceDir(String resourceDir){
    	this.resourceDir = resourceDir;
    }

	/**
	 * Retorna o idioma atual do formul�rio.
	 *
	 * @return String contendo o identificador do idioma atual.
	 */
	public String getLanguage(){
    	return language;
    }
	
	/**
	 * Retorna a codifica��o atual do formul�rio.
	 * 
	 * @return String contendo a codifica��o atual.
	 */
	public String getEncoding(){
	    return getAcceptCharset();
	}
	
    /**
     * Define a codifica��o atual do formul�rio.
     * 
     * @param encoding String contendo a codifica��o atual.
     */
	public void setEncoding(String encoding){
	    setAcceptCharset(encoding);
	}

	/**
	 * Define o idioma atual do formul�rio.
	 *
	 * @param language String contendo o identificador do idioma atual.
	 */
	public void setLanguage(String language){
    	this.language = language;
    }

	/**
	 * Retorna o identificador do arquivo de propriedades a ser utilizado pelos componentes 
	 * do formul�rio.
	 * 
	 * @return String contendo o identificador do arquivo de propriedades.
	 */
	public String getResourceId(){
		return resourceId;
	}

	/**
	 * Define o identificador do arquivo de propriedades a ser utilizado pelos componentes 
	 * do formul�rio.
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
	 * Retorna a inst�ncia contendo as propriedades do arquivo de internacionaliza��o 
	 * espec�fico.
	 * 
	 * @param resourceDir String contendo o identificador do diret�rio dos arquivos.
	 * @param resourceId String contendo o identificador do arquivo.
	 * @return Inst�ncia contendo as propriedades do arquivo de internacionaliza��o.
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
	 * Retorna a inst�ncia contendo as propriedades do arquivo de internacionaliza��o 
	 * espec�fico.
	 * 
	 * @param resourceId String contendo o identificador do arquivo.
	 * @return Inst�ncia contendo as propriedades do arquivo de internacionaliza��o.
	 * @throws Throwable
	 */
	protected PropertiesResource getI18nResource(String resourceId) throws Throwable{
		return getI18nResource("", resourceId);
	}

	/**
	 * Retorna a inst�ncia contendo as propriedades do arquivo de internacionaliza��o do 
	 * componente.
	 * 
	 * @return Inst�ncia contendo as propriedades do arquivo de internacionaliza��o.
	 * @throws Throwable
	 */
	protected PropertiesResource getI18nResource() throws Throwable{
		return getI18nResource(getResourceDir(), getResourceId());
	}

	/**
	 * Retorna a inst�ncia contendo as propriedades do arquivo de internacionaliza��o default.
	 * 
	 * @return Inst�ncia contendo as propriedades do arquivo de internacionaliza��o.
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

        out.println("<div id=\"shade\" class=\"shade\" style=\"visibility: hidden;\"></div>");
        out.print("<div id=\"");
        out.print(getBeanName());
        out.println(".loadingBox\" class=\"loadingBox\" style=\"visibility: hidden;\">");
        
        PropertiesResource resource = getDefaultI18nResource();
        String             message  = StringUtil.trim(resource.getProperty(AttributeConstants.LOADING_KEY));

        out.print("<span id=\"");
        out.print(getBeanName());
        out.println(".loadingBoxInfo\">");
        
        out.println("<table>");
        
        out.println("<tr>");
        out.println("<td align=\"center\">");
        out.println("<div class=\"loadingBoxIcon\"></div>");
        out.println("</td>");
        out.println("</tr>");
        
        out.println("<tr>");
        out.println("<td class=\"loadingBoxText\">");
        out.print(message);
        out.println("</td>");
        out.println("</tr>");
        
        out.println("</table>");
        
        out.println("</span>");

        message = StringUtil.trim(resource.getProperty(AttributeConstants.LOADING_ERROR_KEY));
        
        out.print("<span id=\"");
        out.print(getBeanName());
        out.println(".loadingBoxError\" class=\"loadingBoxText\" style=\"display: none;\">");
        
        out.println("<table>");
        
        out.println("<tr>");
        out.println("<td class=\"loadingBoxText\">");
        out.print(message);
        out.println("</td>");
        out.println("</tr>");
        
        out.println("<tr>");
        out.println("<td align=\"center\">");
        
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
        if(findAncestorWithClass(this, PageTag.class) == null){
            PageTag.renderMessageBoxes(systemController);
            PageTag.renderEvents(systemController);
        }
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
                if(!ExceptionUtil.isExpectedException(e) && !ExceptionUtil.isInternalErrorException(e))
                    systemController.forward(new InternalErrorException(e));
                else
                    systemController.forward(e);
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
                if(!ExceptionUtil.isExpectedException(e) && !ExceptionUtil.isInternalErrorException(e))
                    systemController.forward(new InternalErrorException(e));
                else
                    systemController.forward(e);
            }
            finally{
                clearAttributes();
            }
        }
        
        return EVAL_PAGE;
    }
}