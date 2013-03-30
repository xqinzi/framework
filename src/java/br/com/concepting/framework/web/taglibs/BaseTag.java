package br.com.concepting.framework.web.taglibs;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.resource.I18nResourceLoader;
import br.com.concepting.framework.resource.PropertiesResource;
import br.com.concepting.framework.security.web.SecurityController;
import br.com.concepting.framework.util.ExceptionUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.ComponentType;
import br.com.concepting.framework.web.SystemController;

/**
 * Classe que define a estrutura básica para um componente visual.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class BaseTag extends BodyTagSupport implements Cloneable{
    private   ComponentType      componentType           = null;
	private   String             name                    = "";
	private   String             styleClass              = "";
	private   String             style                   = "";
	private   String             onBlur                  = "";
    private   String             onFocus                 = "";
	private   String             onClick                 = "";
	private   String             onMouseOver             = "";
	private   String             onMouseOut              = "";
	private   String             width                   = "";
	private   String             height                  = "";
	private   String             tooltip                 = null;
	private   Boolean            enabled                 = true;
	private   String             resourceDir             = "";
	private   String             resourceId              = "";
	private   String             resourceKey             = "";
	private   Boolean            rendered                = true;
	private   Boolean            renderWhenAuthenticated = false;
    protected SystemController   systemController        = null;
    protected SecurityController securityController      = null;
    protected PrintWriter        out                     = null;
    
    /**
     * Indica se o componente será renderizado somente quando um usuário estiver autenticado.
     * 
     * @return True/False.
     */
    public Boolean isRenderWhenAuthenticated(){
        return renderWhenAuthenticated;
    }

    /**
     * Indica se o componente será renderizado somente quando um usuário estiver autenticado.
     * 
     * @return True/False.
     */
    public Boolean getRenderWhenAuthenticated(){
        return isRenderWhenAuthenticated();
    }

    /**
     * Define se o componente será renderizado somente quando um usuário estiver autenticado.
     * 
     * @param renderWhenAuthenticated True/False.
     */
    public void setRenderWhenAuthenticated(Boolean renderWhenAuthenticated){
        this.renderWhenAuthenticated = renderWhenAuthenticated;
    }

    /**
     * Indica se o componente será renderizado.
     * 
     * @return True/False.
     */
    public Boolean isRendered(){
        return rendered;
    }

    /**
     * Indica se o componente será renderizado.
     * 
     * @return True/False.
     */
    public Boolean getRendered(){
        return isRendered();
    }

    /**
     * Define se o componente será renderizado.
     * 
     * @param rendered True/False.
     */
    public void setRendered(Boolean rendered){
        this.rendered = rendered;
    }

    /**
     * Retorna o tipo do componente.
     * 
     * @return Constante que define o tipo do componente.
     */
	protected ComponentType getComponentType(){
        return componentType;
    }

    /**
     * Define o tipo do componente.
     * 
     * @param componentType Constante que define o tipo do componente.
     */
    protected void setComponentType(ComponentType componentType){
        this.componentType = componentType;
    }

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
	 * Exibe um conteúdo.
	 * 
	 * @param value Instância do conteúdo a ser apresentado.
	 * @throws IOException
	 */
	protected <O> void print(O value) throws IOException{
		out.print(value);
	}
	
	/**
	 * Exibe um conteúdo pulando uma linha.
	 * 
	 * @param value Instância do conteúdo a ser apresentado.
	 * @throws IOException
	 */
	protected <O> void println(O value) throws IOException{
		print(value);
		println();
	}
	
	/**
	 * Exibe uma nova linha.
	 * 
	 * @throws IOException
	 */
	protected void println() throws IOException{
		out.println();
	}
	
	/**
	 * Retorna o tamanho do componente. 
	 *
	 * @return String contendo a definição do tamanho do componente.
	 */
	public String getWidth(){
    	return width;
    }

	/**
	 * Define o tamanho do componente. 
	 *
	 * @param width String contendo a definição do tamanho do componente.
	 */
	public void setWidth(String width){
    	this.width = width;
    }

	/**
	 * Retorna a altura do componente. 
	 *
	 * @return String contendo a definição da altura do componente.
	 */
	public String getHeight(){
    	return height;
    }

	/**
	 * Define a altura do componente. 
	 *
	 * @param height String contendo a definição da altura do componente.
	 */
	public void setHeight(String height){
    	this.height = height;
    }

	/**
	 * Retorna o identificador do componente.
	 * 
	 * @return String contendo o identificador.
	 */
	protected String getTagId(){
		StringBuilder componentId = new StringBuilder();
		String        buffer      = getClass().getSimpleName();

		buffer = StringUtil.replaceLast(buffer, "Tag", "");

		componentId.append(buffer.substring(0, 1).toLowerCase());
		componentId.append(buffer.substring(1));

		return componentId.toString();
	}

	/**
	 * Retorna o identificador do componente.
	 * 
	 * @return String contendo identificador.
	 */
	public String getName(){
		return name;
	}

	/**
	 * Define o identificador do componente.
	 * 
	 * @param name String contendo identificador.
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * Retorna o identificador do arquivo de recursos que armazena as propriedades do 
	 * componente.
	 * 
	 * @return String contendo o identificador do arquivo de recursos.
	 */
	public String getResourceId(){
		return resourceId;
	}
	 
	/**
	 * Retorna o identificador da propriedade armazenada no arquivo de recursos.
	 *
	 * @return String contendo o identificador da propriedade.
	 */
	public String getResourceKey(){
		return resourceKey;
	}

	/**
	 * Define o identificador do arquivo de recursos que armazena as propriedades do 
	 * componente.
	 * 
	 * @param resourceId String contendo o identificador do arquivo de
	 *                   recursos.
	 */
	public void setResourceId(String resourceId){
		this.resourceId = resourceId;
	}
	
	/**
	 * Define o identificador da propriedade armazenada no arquivo de recursos.
	 *
	 * @param resourceKey String contendo o identificador da propriedade.
	 */
	public void setResourceKey(String resourceKey){
		this.resourceKey = resourceKey;
	}

	/**
	 * Retorna o estilo CSS do componente visual.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getStyleClass(){
		return styleClass;
	}

	/**
	 * Define o estilo CSS do componente visual.
	 * 
	 * @param styleClass String contendo o estilo CSS.
	 */
	public void setStyleClass(String styleClass){
		this.styleClass = styleClass;
	}

	/**
	 * Retorna o estilo CSS do componente visual.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getStyle(){
		return style;
	}

	/**
	 * Define o estilo CSS do componente visual.
	 * 
	 * @param style String contendo o estilo CSS.
	 */
	public void setStyle(String style){
		this.style = style;
	}

	/**
	 * Retorna o evento a ser executado quando o componente perder o foco.
	 * 
	 * @return String contendo o evento a ser executado.
	 */
	public String getOnBlur(){
		return onBlur;
	}

	/**
	 * Define o evento a ser executado quando o componente perder o foco.
	 * 
	 * @param onBlur String contendo o evento a ser executado.
	 */
	public void setOnBlur(String onBlur){
		this.onBlur = onBlur;
	}

	/**
	 * Retorna o evento a ser executado quando o componente for clicado.
	 * 
	 * @return String contendo o evento a ser executado.
	 */
	public String getOnClick(){
		return onClick;
	}

	/**
	 * Define o evento a ser executado quando o componente for clicado.
	 * 
	 * @param onClick String contendo o evento a ser executado.
	 */
	public void setOnClick(String onClick){
		this.onClick = onClick;
	}

	/**
	 * Retorna o evento a ser executado quando o componente receber o foco.
	 * 
	 * @return String contendo o evento a ser executado.
	 */
	public String getOnFocus(){
		return onFocus;
	}

	/**
	 * Define o evento a ser executado quando o componente receber o foco.
	 * 
	 * @param onFocus String contendo o evento a ser executado.
	 */
	public void setOnFocus(String onFocus){
		this.onFocus = onFocus;
	}

	/**
	 * Retorna o evento a ser executado quando o ponteiro do mouse sair do componente.
	 * 
	 * @return String contendo o evento a ser executado.
	 */
	public String getOnMouseOut(){
		return onMouseOut;
	}

	/**
	 * Define o evento a ser executado quando o ponteiro do mouse sair do componente.
	 * 
	 * @param onMouseOut String contendo o evento a ser executado.
	 */
	public void setOnMouseOut(String onMouseOut){
		this.onMouseOut = onMouseOut;
	}

	/**
	 * Retorna o evento a ser executado quando o ponteiro do mouse passar sobre o componente.
	 * 
	 * @return String contendo o evento a ser executado.
	 */
	public String getOnMouseOver(){
		return onMouseOver;
	}

	/**
	 * Define o evento a ser executado quando o ponteiro do mouse passar sobre o componente.
	 * 
	 * @param onMouseOver String contendo o evento a ser executado.
	 */
	public void setOnMouseOver(String onMouseOver){
		this.onMouseOver = onMouseOver;
	}

	/**
	 * Retorna a dica do componente.
	 * 
	 * @return String contendo a dica do componente.
	 */
	public String getTooltip(){
		return tooltip;
	}

	/**
	 * Define a dica do componente.
	 * 
	 * @param tooltip String contendo a dica do componente.
	 */
	public void setTooltip(String tooltip){
		this.tooltip = tooltip;
	}

	/**
	 * Indica se o componente está habilitado.
	 * 
	 * @return True/False.
	 */
	public Boolean isEnabled(){
     	return enabled;
    }

    /**
     * Indica se o componente está habilitado.
     * 
     * @return True/False.
     */
    public Boolean getEnabled(){
        return isEnabled();
    }

    /**
	 * Define se o componente está habilitado.
	 * 
	 * @param enabled True/False.
	 */
	public void setEnabled(Boolean enabled){
		this.enabled = enabled;
	}
	
	/**
	 * @see javax.servlet.jsp.tagext.TagSupport#setPageContext(javax.servlet.jsp.PageContext)
	 */
	public void setPageContext(PageContext pageContext){
		super.setPageContext(pageContext);
		
        systemController   = new SystemController(pageContext);
        securityController = systemController.getSecurityController();
        out                = systemController.getOut();
	}

	/**
	 * Define o handler de output do componente.
	 *
	 * @param out Instância do handler de output.
	 */
	protected void setOut(PrintWriter out){
		this.out = out;
	}

	/**
	 * Retorna a instância contendo as configurações onde o componente será renderizado.
	 */
	protected PageContext getPageContext(){
		return pageContext;
	}

	/**
	 * Retorna a instância contendo as configurações para renderização.
	 * 
	 * @return Instância contendo as configurações de renderização.
	 */
	protected PrintWriter getOut(){
		return out;
	}

	/**
	 * Renderiza os atributos do componente.
	 * 
	 * @throws Throwable
	 */
	protected void renderAttributes() throws Throwable{
		print(" id=\"");
		
		String id = StringUtil.trim(getId());
		
		if(id.length() == 0)
		    print(name);
		else
		    print(id);
		
		print("\" name=\"");
		print(name);
		print("\"");
		
		if(styleClass.length() > 0){
			print(" class=\"");
			print(styleClass);
			print("\"");
		}
		
        if(style.length() > 0){
            print(" style=\"");
            print(style);
            print("\"");
        }

		if(onBlur.length() > 0){
			print(" onBlur=\"");
			print(onBlur);
			print("\"");
		}
		
		if(onFocus.length() > 0){
			print(" onFocus=\"");
			print(onFocus);
			print("\"");
		}
		
		if(onClick.length() > 0){
			print(" onClick=\"");
			print(onClick);
			print("\"");
		}
		
		if(onMouseOver.length() > 0){
			print(" onMouseOver=\"");
			print(onMouseOver);
			print("\"");
		}
		
		if(onMouseOut.length() > 0){
			print(" onMouseOut=\"");
			print(onMouseOut);
			print("\"");
		}
		
		renderTooltip();
	}

	/**
	 * Renderiza o tooltip do componente.
	 * 
	 * @throws Throwable
	 */
	protected void renderTooltip() throws Throwable{
		if(tooltip != null && tooltip.length() > 0){
			print(" title=\"");
			print(tooltip);
			print("\"");
		}
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
	    StringBuilder buffer = new StringBuilder(style);
	    
	    if(width.length() > 0){
	        if(style.indexOf("width:") < 0){
	            buffer.append("width: ");
	            buffer.append(width);
	            
	            if(!width.endsWith("%"))
	                buffer.append("px; ");
	        }
	    }
	    
        if(height.length() > 0){
            if(style.indexOf("height:") < 0){
                buffer.append("height: ");
                buffer.append(height);
                
                if(!height.endsWith("%"))
                    buffer.append("px; ");
            }
        }
        
        style = buffer.toString();

		if(tooltip == null && name.length() > 0){
			StringBuilder propertyId  = new StringBuilder();

			if(resourceKey.length() > 0)
				propertyId.append(resourceKey);
			else
				propertyId.append(name);
			
			propertyId.append(".");
			propertyId.append(AttributeConstants.TOOLTIP_KEY);

     		PropertiesResource resources = getI18nResource();

 		    tooltip = resources.getProperty(propertyId.toString(), false);
     			
 			if(tooltip == null){
 				resources = getDefaultI18nResource();
 				tooltip   = StringUtil.trim(resources.getProperty(propertyId.toString(), false));
 			}
		}
		else
		    tooltip = "";
	}

	/**
	 * Renderiza a abertura do componente.
	 * 
	 * @throws Throwable
	 */
	protected void renderOpen() throws Throwable{
	}

	/**
	 * Renderiza o corpo do componente.
	 * 
	 * @throws Throwable
	 */
	protected void renderBody() throws Throwable{
	}

	/**
	 * Renderiza o fechamento do componente.
	 * 
	 * @throws Throwable
	 */
	protected void renderClose() throws Throwable{
	}

	/**
	 * Renderiza o componente.
	 * 
	 * @throws Throwable
	 */
	protected void render() throws Throwable{
	    if(renderWhenAuthenticated)
	        rendered = securityController.isAuthenticated();
	    
	    if(rendered){
    		renderOpen();
    		renderBody();
    		renderClose();
	    }
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public int doStartTag() throws JspException{
		if(systemController != null){
    		try{
    			initialize();
    		}
    		catch(Throwable e){
                if(!ExceptionUtil.isExpectedException(e) && !ExceptionUtil.isInternalErrorException(e))
                    systemController.forward(new InternalErrorException(e));
                else
                    systemController.forward(e);
    		}
		}

		return super.doStartTag();
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	public int doEndTag() throws JspException{
		if(systemController != null){
    		try{
    			render();
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

		return super.doEndTag();
	}

	/**
	 * Limpa as propriedades do componente.
	 */
	protected void clearAttributes(){
		setId(null);
		setName("");
		setStyleClass("");
		setStyle("");
		setOnBlur("");
		setOnClick("");
		setOnFocus("");
		setOnMouseOut("");
		setOnMouseOver("");
		setTooltip(null);
		setEnabled(true);
		setResourceId("");
		setResourceKey("");
		setRendered(true);
		setRenderWhenAuthenticated(false);
	}
}