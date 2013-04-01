package br.com.concepting.framework.web;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.jstl.core.Config;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.model.SystemSessionModel;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.security.web.SecurityController;
import br.com.concepting.framework.util.FileUtil;
import br.com.concepting.framework.util.LanguageUtil;
import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.PagerActionType;
import br.com.concepting.framework.util.types.SortOrderType;
import br.com.concepting.framework.web.constants.SystemConstants;
import br.com.concepting.framework.web.form.ActionFormMessageController;
import br.com.concepting.framework.web.form.BaseActionForm;
import br.com.concepting.framework.web.helpers.RequestInfo;
import br.com.concepting.framework.web.types.ContentType;
import br.com.concepting.framework.web.types.ScopeType;

/**
 * Classe respons�vel pelo controle das requisi��es do sistema.
 *
 * @author fvilarinho
 * @since 1.0
 */
public class SystemController{
	private PageContext                 pageContext                 = null;
	private HttpServletRequest          request                     = null;
	private HttpServletResponse         response                    = null;
	private HttpSession                 session                     = null;
	private Boolean                     hasForwardOrRedirect        = false;
	private Boolean                     hasOutputContent            = false;
	private SecurityController          securityController          = null;
    private ActionFormMessageController actionFormMessageController = null;
	
    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.

     * @param session Inst�ncia contendo as propriedades da sess�o.
     */
	public SystemController(HttpSession session){
	    super();
	    
	    setSession(session);
        
        this.securityController          = new SecurityController(this);
        this.actionFormMessageController = new ActionFormMessageController(this);
	}

	/**
	 * Construtor - Inicializa objetos e/ou vari�veis internas.
	 *
	 * @param request Inst�ncia contendo as propriedades da requisi��o.
	 * @param response Inst�ncia contendo as propriedades da resposta da requisi��o.
	 */
	public SystemController(HttpServletRequest request, HttpServletResponse response){
		super();
		
		setRequest(request);
		setResponse(response);
		setEncoding(getEncoding());
		
		this.securityController          = new SecurityController(this);
		this.actionFormMessageController = new ActionFormMessageController(this);
	}

	/**
	 * Construtor - Inicializa objetos e/ou vari�veis internas.
	 *
	 * @param pageContext Inst�ncia contendo as propriedades de contexto. 
	 */
	public SystemController(PageContext pageContext){
		this((HttpServletRequest)pageContext.getRequest(), (HttpServletResponse)pageContext.getResponse());

		this.pageContext = pageContext;
	}
	
    /**
     * Define o identificador do tema (skin) atual.
     * 
     * @param currentSkin String contendo o identificador do tema (skin).
     */
	public void setCurrentSkin(String currentSkin){
	    if(currentSkin.length() == 0)
	        currentSkin = SystemConstants.DEFAULT_SKIN;
	    
        LoginSessionModel  loginSession  = securityController.getLoginSession();
        SystemSessionModel systemSession = loginSession.getSystemSession();

        systemSession.setCurrentSkin(currentSkin);
        
        securityController.setLoginSession(loginSession);
	}
	
    /**
     * Retorna o identificador do tema (skin) atual.
     * 
     * @return String contendo o identificador do tema (skin).
     */
	public String getCurrentSkin(){
	    LoginSessionModel  loginSession  = securityController.getLoginSession();
	    SystemSessionModel systemSession = loginSession.getSystemSession();
	    
	    return systemSession.getCurrentSkin();
	}
	
    /**
     * Define o idioma atual.
     * 
     * @param currentLanguage String contendo o identificador do idioma.
     */
	public void setCurrentLanguage(String currentLanguage){
	    Locale language = null;
	    
	    if(currentLanguage.length() == 0)
	        language = LanguageUtil.getDefaultLanguage();
	    else
	        language = LanguageUtil.getLanguageByString(currentLanguage);
	    
	    if(language == null)
	        language = LanguageUtil.getDefaultLanguage();
	    
	    setCurrentLanguage(language);
	}
	
    /**
     * Define o idioma atual.
     * 
     * @param currentLanguage Inst�ncia contendo as propriedades do idioma.
     */
	public void setCurrentLanguage(Locale currentLanguage){
	    if(currentLanguage == null)
	        currentLanguage = LanguageUtil.getDefaultLanguage();
	    
        LoginSessionModel loginSession = securityController.getLoginSession();
        
        if(loginSession != null){
            SystemSessionModel systemSession = loginSession.getSystemSession();
            
            systemSession.setCurrentLanguage(currentLanguage);
            
            securityController.setLoginSession(loginSession);
        }
        
        session.setAttribute(SystemConstants.CURRENT_LANGUAGE_KEY, currentLanguage);
        
        Config.set(session, Config.FMT_LOCALE, currentLanguage);
        
        addCookie(SystemConstants.CURRENT_LANGUAGE_KEY, currentLanguage.toString(), true);
	}
	
	/**
	 * Retorna a inst�ncia contendo as propriedades do idioma atual.
	 * 
	 * @return Inst�ncia contendo as propriedades do idioma.
	 */
    public Locale getCurrentLanguage(){
        Locale currentLanguage = null;
        Cookie cookie          = getCookie(SystemConstants.CURRENT_LANGUAGE_KEY);
        
        if(cookie != null)
            currentLanguage = LanguageUtil.getLanguageByString(cookie.getValue());
        
        if(currentLanguage == null){
            currentLanguage = LanguageUtil.getDefaultLanguage();
            
            setCurrentLanguage(currentLanguage);
        }
        
        return currentLanguage;
    }

    /**
	 * Retorna o controlador de mensagens do sistema.
	 * 
	 * @return Inst�ncia do controlador de mensagens do sistema.
	 */
	public ActionFormMessageController getActionFormMessageController(){
	    return actionFormMessageController;
	}
	
    /**
     * Retorna o controlador de seguran�a do sistema.
     * 
     * @return Inst�ncia do controlador de seguran�a do sistema.
     */
	public SecurityController getSecurityController(){
	    return securityController;
	}

	/**
	 * Retorna o tipo de codifica��o.
	 *
	 * @return String contendo o tipo de codifica��o.
	 */
	public String getEncoding(){
		String encoding = StringUtil.trim(getRequest().getParameter(Constants.ENCODING_KEY));
		
		if(encoding.length() == 0)
			encoding = StringUtil.trim(findAttribute(Constants.ENCODING_KEY, ScopeType.SESSION));
		
		if(encoding.length() == 0)
		    encoding = Constants.DEFAULT_ENCODING;
		
		return encoding;
	}
	
	/**
	 * Define o tipo de codifica��o.
	 *
	 * @param encoding String contendo o tipo de codifica��o.
	 */
	public void setEncoding(String encoding){
		if(encoding.length() == 0)
			encoding = Constants.DEFAULT_ENCODING;
		
		setAttribute(Constants.ENCODING_KEY, encoding, ScopeType.SESSION);

		if(response != null && encoding.length() > 0)
			response.setCharacterEncoding(encoding);
	}
	
	/**
	 * Retorna a inst�ncia contendo as propriedades de contexto da taglib atual.
	 *
	 * @return Inst�ncia contendo as propriedades de contexto da taglib.
	 */
	public PageContext getPageContext(){
    	return pageContext;
    }
	
	/**
	 * Indica se foi feito algum repasse/redirecionamento.
	 *
	 * @return True/False.
	 */
	public Boolean hasForwardOrRedirect(){
		return hasForwardOrRedirect;
	}

	/**
	 * Indica se foi feito algum output de conte�do.
	 *
	 * @return True/False.
	 */
	public Boolean hasOutputContent(){
		return hasOutputContent;
	}

	/**
	 * Retorna a inst�ncia contendo as propriedades da requisi��o.
	 *
	 * @return Inst�ncia contendo as propriedades da requisi��o.
	 */
	public HttpServletRequest getRequest(){
		return request;
	}
	
	/**
	 * Define a inst�ncia contendo as propriedades da requisi��o.
	 *
	 * @param request Inst�ncia contendo as propriedades da requisi��o.
	 */
	private void setRequest(HttpServletRequest request){
		this.request = request;
	}

	/**
	 * Retorna a inst�ncia contendo as propriedades de resposta da requisi��o.
	 *
	 * @return Inst�ncia contendo as propriedades de resposta.
	 */
	public HttpServletResponse getResponse(){
		return response;
	}
	
	/**
	 * Define a inst�ncia contendo as propriedades de resposta da requisi��o.
	 *
	 * @param response Inst�ncia contendo as propriedades de resposta.
	 */
	private void setResponse(HttpServletResponse response){
		this.response = response;
	}

	/**
	 * Retorna a inst�ncia contendo as propriedades da sess�o.
	 *
	 * @return Inst�ncia contendo as propriedades de sess�o.
	 */
	public HttpSession getSession(){
	    if(getRequest() != null)
	        session = getRequest().getSession();
	    
		return session;
	}
	
    /**
     * Define a inst�ncia contendo as propriedades da sess�o.
     *
     * @param session Inst�ncia contendo as propriedades de sess�o.
     */
	public void setSession(HttpSession session){
	    this.session = session;
	}
	

	/**
	 * Retorna um array contendo as propriedades dos cookies armazenados.
	 *
	 * @return Array contendo as propriedades dos cookies.
	 */
	public Cookie[] getCookies(){
		return getRequest().getCookies();
	}
	
	/**
	 * Adiciona uma novo cookie.
	 *  
	 * @param name String contendo o identificador do cookie.
	 * @param value String contendo o valor do cookie.
	 */
	public void addCookie(String name, String value){
		addCookie(name, value, false);
	}
	
	/**
	 * Adiciona uma novo cookie.
	 *  
	 * @param name String contendo o identificador do cookie.
	 * @param value String contendo o valor do cookie.
	 * @param persistent Indica se o cookie ser� persistido (True/False).
	 */
	public void addCookie(String name, String value, Boolean persistent){
		if(persistent)
			addCookie(name, value, 60 * 60 * 24 * 365 * 1000);
		else
			addCookie(name, value, -1);
	}

	/**
	 * Adiciona uma novo cookie.
	 *  
	 * @param name String contendo o identificador do cookie.
	 * @param value String contendo o valor do cookie.
	 * @param maxAge Valor inteiro em segundos contendo o tempo de persist�ncia do cookie.
	 */
	public void addCookie(String name, String value, Integer maxAge){
		Cookie cookie = new Cookie(name, value);
		
		cookie.setPath("/");

		if(maxAge > 0)
			cookie.setMaxAge(maxAge);
		
		getResponse().addCookie(cookie);
	}

	/**
	 * Retorna a inst�ncia contendo as propriedades de um cookie espec�fico. 
	 *
	 * @param name String contendo o identificador do cookie.
	 * @return Inst�ncia contendo as propriedades do cookie desejado.
	 */
	public Cookie getCookie(String name){
		Cookie cookies[] = getCookies();

		if(cookies != null)
			for(Cookie cookie : cookies)
				if(cookie.getName().equals(name)) 
					return cookie;

		return null;
	}

	/**
	 * Retorna o stream de output de dados.
	 *
	 * @return Inst�ncia contendo o stream de output.
	 */
	public PrintWriter getOut(){
		try{
			if(getPageContext() != null)
				return new PrintWriter(getPageContext().getOut());
				
			return new PrintWriter(response.getOutputStream());
		}
		catch(Throwable e){}

		return null;
	}
	
	/**
	 * Retorna o valor de um atributo a partir de um escopo de armazenamento.
	 * O identificador do atributo pode ser definido da seguinte maneira:
	 * <nome-do-atributo>.<nome-do-atributo-filho>.<nome-do-atributo-filho>
	 * Ex:
	 * loginSession.user.name
	 * O exemplo acima ir� retorna o valor no atributo nome, da inst�ncia user que fica 
	 * armazenada na inst�ncia loginSession.
	 * 
	 * @param attributeId String contendo o identificador do atributo.
	 * @param scopeType Constante que define o escopo de armazenamento do atributo.
	 */
    public <T> T findAttribute(String attributeId, ScopeType scopeType){
    	Object instance = null;
    	
    	if(StringUtil.trim(attributeId).length() > 0){
    		try{
        		Integer pos              = attributeId.indexOf(".");
        		String  firstAttributeId = "";
        		
        		if(pos >= 0){
        			firstAttributeId = attributeId.substring(0, pos);
        			attributeId      = attributeId.substring(pos + 1);
        		}
        		else
        			firstAttributeId = attributeId;
    
        		switch(scopeType){
        			case APPLICATION:{
        				instance = getSession().getServletContext().getAttribute(firstAttributeId);
        				
        				break;
        			}
        			case SESSION:{
        				instance = getSession().getAttribute(firstAttributeId);
        				
        				break;
        			}
        			case REQUEST:{
    			        instance = getRequest().getAttribute(firstAttributeId);
        				
        				break;
        			}
        			default:{
        				instance = getSession().getAttribute(firstAttributeId);
        				
        				break;
        			}
        		}
        	
        		if(instance != null && !firstAttributeId.equals(attributeId))
    				instance = PropertyUtil.getProperty(instance, attributeId);
    		}
    		catch(Throwable e){
    			instance = null;
    		}
    	}

		return (T)instance;
	}

	/**
	 * Define o valor de um atributo a partir de um escopo de armazenamento.
	 * O identificador do atributo pode ser definido da seguinte maneira:
	 * <nome-do-atributo>.<nome-do-atributo-filho>.<nome-do-atributo-filho>
	 * Ex:
	 * loginSession.user.name
	 * O exemplo acima ir� setar o valor no atributo nome, da inst�ncia user que fica 
	 * armazenada na inst�ncia loginSession.
	 * 
	 * @param attributeId String contendo o identificador do atributo.
	 * @param attributeValue Inst�ncia contendo o valor do atributo.
	 * @param scopeType Constante que define o escopo de armazenamento do atributo.
	 */
	public <T> void setAttribute(String attributeId, T attributeValue, ScopeType scopeType){
    	if(StringUtil.trim(attributeId).length() > 0){
    		if(scopeType == ScopeType.APPLICATION)
       			getSession().getServletContext().setAttribute(attributeId, attributeValue);
    		else if(scopeType == ScopeType.SESSION)
       			getSession().setAttribute(attributeId, attributeValue);
    		else if(scopeType == ScopeType.REQUEST)
		        getRequest().setAttribute(attributeId, attributeValue);
    		else{
    			try{
            		Integer pos              = attributeId.indexOf(".");
        			String  firstAttributeId = "";
        		
        			if(pos >= 0){
        				firstAttributeId = attributeId.substring(0, pos);
        				attributeId      = attributeId.substring(pos + 1);
        				
            			Object instance = findAttribute(firstAttributeId, scopeType);
            			
            			if(instance != null)
            				PropertyUtil.setProperty(instance, attributeId, attributeValue);
            			
            			getSession().setAttribute(firstAttributeId, instance);
        			}
    			}
    			catch(Throwable e){
    			}
    		}
		}
	}

	/**
	 * Efetua o repasse das propriedades da requisi��o atual para a URL de exibi��o de erros default.
	 *
	 * @param exception Inst�ncia da exception gerada.
	 */
	public void forward(Throwable exception){
		setCurrentException(exception);

		forward(SystemConstants.DEFAULT_ERROR_PAGE);
	}

	/**
	 * Efetua o redirecionamento para uma URL espec�fica.
	 *
	 * @param url String contendo a URL desejada.
	 */
	public void redirect(String url){
	    StringBuilder urlBuffer = new StringBuilder();
	    
	    urlBuffer.append(getContextPath());
	    urlBuffer.append("/");
	    urlBuffer.append(url);
	    
		try{
			hasForwardOrRedirect = true;

			getResponse().sendRedirect(urlBuffer.toString());
		}
		catch(Throwable e){
			hasForwardOrRedirect = false;
		}
	}

	/**
	 * Efetua o redirecionamento para a URL de exibi��o de erros default.
	 *
	 * @param exception Inst�ncia da exception gerada.
	 */
	public void redirect(Throwable exception){
		setCurrentException(exception);

		redirect(SystemConstants.DEFAULT_ERROR_PAGE);
	}

	/**
	 * Efetua o repasse das propriedades da requisi��o atual para uma URL espec�fica.
	 *
	 * @param url String contendo a URL desejada.
	 */
	public void forward(String url){
		try{
			hasForwardOrRedirect = true;

			getRequest().getRequestDispatcher(url).forward(getRequest(), getResponse());
		}
		catch(Throwable e){
			hasForwardOrRedirect = false;
		}
	}
	
    /**
     * Retorna a inst�ncia da exce��o atual.
     *
     * @return Inst�ncia da exce��o.
     */
    public Throwable getCurrentException(){
        return findAttribute(AttributeConstants.CURRENT_EXCEPTION_KEY, ScopeType.SESSION);
    }

    /**
     * Define a inst�ncia da exce��o atual.
     *
     * @param exception Inst�ncia da exce��o gerada.
     */
    public void setCurrentException(Throwable exception){
        setAttribute(AttributeConstants.CURRENT_EXCEPTION_KEY, exception, ScopeType.SESSION);
    }
	
	/**
	 * Gera o output de um conte�do.
	 *
	 * @param contentData Array de bytes contendo o conte�do desejado. 
	 * @param contentType Constante que define o tipo de conte�do.
	 */
	public void outputContent(byte contentData[], ContentType contentType){
		outputContent(contentData, contentType.toString());
	}

	/**
	 * Gera o output de um conte�do.
	 *
	 * @param contentData Array de bytes contendo o conte�do desejado. 
	 * @param contentType String que define o tipo de conte�do.
	 */
	public void outputContent(byte contentData[], String contentType){
		outputContent(contentData, contentType, "");
	}

	/**
	 * Gera o output de um conte�do.
	 *
	 * @param contentData Array de bytes contendo o conte�do desejado. 
	 * @param contentType Constante que define o tipo de conte�do.
	 * @param contentId String contendo o identificador do conte�do.
	 */
	public void outputContent(byte contentData[], ContentType contentType, String contentId){
		outputContent(contentData, contentType.toString(), contentId);
	}

	/**
	 * Gera o output de um conte�do.
	 *
	 * @param contentData Array de bytes contendo o conte�do desejado. 
	 * @param contentType String que define o tipo de conte�do.
	 * @param contentId String contendo o identificador do conte�do.
	 */
	public void outputContent(byte contentData[], String contentType, String contentId){
		if(contentData != null){
			try{
     			if(contentId.length() > 0){
     				StringBuilder fileNameBuffer = new StringBuilder();
     
     				fileNameBuffer.append("attachment; filename=\"");
     				fileNameBuffer.append(contentId);
     				fileNameBuffer.append("\"");
     
     				getResponse().setHeader("Content-Disposition", fileNameBuffer.toString());
     			}
     
     			getResponse().setHeader("Expires", "0");
     			getResponse().setHeader("Cache-Control", "no-cache");
     			getResponse().setHeader("Pragma" ,"no-cache");     			
     			getResponse().setContentType(contentType.toString());
     			getResponse().setContentLength(contentData.length);
     
     			ServletOutputStream out = getResponse().getOutputStream();
     
     			out.write(contentData);
     			out.flush();
     			out.close();
     			
     			hasForwardOrRedirect = true;
			}
			catch(Throwable e){
				hasOutputContent = false;
			}
		}
		else
			hasOutputContent = false;
	}
	
	/**
	 * Retorna a inst�ncia contendo as propriedades do par�metro da requisi��o a partir do seu identificador.
	 *
	 * @param name String contendo o identificador do par�metro da requisi��o.
	 * @return Inst�ncia contendo as propriedades do par�metro da requisi��o.
	 */
	public RequestInfo getRequestInfo(String name){
		RequestInfo requestInfo = new RequestInfo();
		
        requestInfo.setCurrentGuide(getRequestInfoCurrentGuide(name));
        requestInfo.setCurrentNode(getRequestInfoCurrentNode(name));
        requestInfo.setCurrentSection(getRequestInfoCurrentSection(name));
        requestInfo.setData(getRequestInfoData(name, false));
        requestInfo.setDataScope(getRequestInfoDataScope(name, false));
        requestInfo.setDataStartIndex(getRequestInfoDataStartIndex(name));
        requestInfo.setDataEndIndex(getRequestInfoDataEndIndex(name));
        requestInfo.setEditableData(getRequestInfoData(name, true));
        requestInfo.setEditableDataScope(getRequestInfoDataScope(name, true));
        requestInfo.setHasMultipleSelection(hasRequestInfoMultipleSelection(name));
        requestInfo.setIsEditable(isRequestInfoEditable(name));
        requestInfo.setItemsPerPage(getRequestInfoItemsPerPage(name));
		requestInfo.setName(name);
		requestInfo.setLabel(getRequestInfoLabel(name));
        requestInfo.setPagerAction(getRequestInfoPagerAction(name));
        requestInfo.setPattern(getRequestInfoPattern(name));
        requestInfo.setSortOrder(getRequestInfoSortOrder(name));
		requestInfo.setSortProperty(getRequestInfoSortProperty(name));
        requestInfo.setValue(getRequestInfoValue(name));
        requestInfo.setValues(getRequestInfoValues(name));
		
		return requestInfo;
	}
	
	/**
	 * Indica se a coluna do grid, vinculada a um modelo de dados, � edit�vel.
	 *  
	 * @param name String contendo o identificador da coluna.
	 * @return True/False.
	 */
	private Boolean isRequestInfoEditable(String name){
		return name.contains(AttributeConstants.EDITABLE_DATA_COLUMN_KEY);
	}
	
	/**
	 * Retorna o valor da requisi��o de uma propriedade do modelo de dados.
	 * 
	 * @param name String contendo o identificador da propriedade.
	 * @return String contendo o valor da requisi��o.
	 */
    public String getRequestInfoValue(String name){
		String encoding = getEncoding();
		
		if(encoding.length() == 0)
		    encoding = FileUtil.getEncoding();
		
		String value = StringUtil.trim(getRequest().getParameter(name));
		
		try{
			value = URLDecoder.decode(value, encoding);
		}
		catch(Throwable e){
		}
		
		return value;
	}
	
    /**
     * Retorna os valores (em caso de m�ltipla sele��o) da requisi��o de uma propriedade 
     * do modelo de dados.
     * 
     * @param name String contendo o identificador da propriedade.
     * @return Array contendo os valores da requisi��o.
     */
	private String[] getRequestInfoValues(String name){
		String encoding = getEncoding();
		
		if(encoding.length() == 0)
		    encoding = FileUtil.getEncoding();
		
		String value    = "";
		String values[] = getRequest().getParameterValues(name); 
		
		try{
			for(Integer cont = 0 ; cont < values.length ; cont++){
				value        = StringUtil.trim(values[cont]);
				values[cont] = URLDecoder.decode(value, encoding);
			}
		}
		catch(Throwable e){
		}
		
		return values;
	}
	
	/**
	 * Retorna o identificador da aba selecionada do componente guides (guias).
	 * 
	 * @param name String contendo o identificador do componente.
	 * @return String contendo o identificador da aba.
	 */
	private String getRequestInfoCurrentGuide(String name){
		StringBuilder key = new StringBuilder();

		key.append(name);
		key.append(".");
		key.append(AttributeConstants.CURRENT_GUIDE_KEY);

		return StringUtil.trim(getRequest().getParameter(key.toString()));
	}

    /**
     * Retorna o identificador do n� selecionado do componente treeView (�rvore).
     * 
     * @param name String contendo o identificador do componente.
     * @return String contendo o identificador do n�.
     */
    private String getRequestInfoCurrentNode(String name){
        StringBuilder key = new StringBuilder();

        key.append(name);
        key.append(".");
        key.append(AttributeConstants.CURRENT_NODE_KEY);

        return StringUtil.trim(getRequest().getParameter(key.toString()));
    }

    /**
     * Retorna a se��o atual.
     * 
     * @param name String contendo o identificador do componente.
     */
    private String getRequestInfoCurrentSection(String name){
        StringBuilder key = new StringBuilder();

        key.append(name);
        key.append(".");
        key.append(AttributeConstants.CURRENT_SECTION_KEY);

        return StringUtil.trim(getRequest().getParameter(key.toString()));
    }

    /**
     * Retorna a coluna selecionada para ordena��o do componente grid.
     * 
     * @param name String contendo o identificador do componente.
     * @return String contendo o identificador da coluna.
     */
    private String getRequestInfoSortProperty(String name){
		StringBuilder key = new StringBuilder();

		key.append(name);
		key.append(".");
		key.append(AttributeConstants.SORT_PROPERTY_KEY);

		return StringUtil.trim(getRequest().getParameter(key.toString()));
	}
	
    /**
     * Retorna o tipo de ordena��o de uma coluna do componente grid.
     * 
     * @param name String contendo o identificador do componente.
     * @return Constante que define o tipo de ordena��o.
     */
	private SortOrderType getRequestInfoSortOrder(String name){
		StringBuilder key = new StringBuilder();

		key.append(name);
		key.append(".");
		key.append(AttributeConstants.SORT_ORDER_KEY);

		try{
			String sortOrder = StringUtil.trim(getRequest().getParameter(key.toString()));
            		
			return SortOrderType.valueOf(sortOrder.toUpperCase());
		}
		catch(Throwable e){
			return Constants.DEFAULT_SORT_ORDER_TYPE;
		}
	}

	/**
	 * Retorna o n�mero de itens por p�gina do componente pager (paginador).
	 * 
     * @param name String contendo o identificador do componente.
	 * @return Valor num�rico contendo o n�mero de itens por p�gina.
	 */
	private Integer getRequestInfoItemsPerPage(String name){
    	StringBuilder key = new StringBuilder();
    	
		key.append(name);
		key.append(".");
		key.append(AttributeConstants.ITEMS_PER_PAGE_KEY);
		
		String itemsPerPage = StringUtil.trim(getRequest().getParameter(key.toString()));
		
        try{
            return NumberUtil.parseInt(itemsPerPage);
        }
        catch(ParseException e){
            return 0;
        }
    }
	
	/**
	 * Retorna a a��o de pagina��o executada pelo componente pager (paginador).
	 * 
	 * @param name String contendo o identificador do componente.
	 * @return Constante que define a a��o de pagina��o.
	 */
    private PagerActionType getRequestInfoPagerAction(String name){
    	StringBuilder key = new StringBuilder();

		key.append(name);
		key.append(".");
		key.append(AttributeConstants.PAGER_ACTION_KEY);
		
		try{
		    String pagerActionType = StringUtil.trim(getRequest().getParameter(key.toString())).toUpperCase();
		    
			return PagerActionType.valueOf(pagerActionType);
		}
		catch(Throwable e){
			return PagerActionType.REFRESH_PAGE;
		}
    }

    /**
     * Indica se a propriedade do modelo de dados permite m�ltipla sele��o.
     * 
     * @param name String contendo o nome da propriedade.
     * @return True/False.
     */
    private Boolean hasRequestInfoMultipleSelection(String name){
    	StringBuilder key = new StringBuilder();

		key.append(name);
		key.append(".");
		key.append(AttributeConstants.HAS_MULTIPLE_SELECTION_KEY);
		
		try{
			return Boolean.parseBoolean(StringUtil.trim(getRequest().getParameter(key.toString())));
		}
		catch(Throwable e){
			return false;
		}
    }

    /**
     * Retorna a m�scara de formata��o/valida��o de uma propriedade do modelo de dados.
     *  
     * @param name String contendo o nome da propriedade.
     * @return String contendo a m�scara.
     */
	private String getRequestInfoPattern(String name){
		StringBuilder key = new StringBuilder();
		
		key.append(name);
		key.append(".");
		key.append(AttributeConstants.PATTERN_KEY);

		String encoding = StringUtil.trim(getEncoding());
		String value    = StringUtil.trim(getRequest().getParameter(key.toString()));
		
		try{
			value = URLDecoder.decode(value, encoding);
		}
		catch(Throwable e){
        }
		
		return value;
	}

	/**
	 * Retorna o label de uma propriedade do modelo de dados.
	 *  
     * @param name String contendo o nome da propriedade.
	 * @return String contendo o label.
	 */
	private String getRequestInfoLabel(String name){
		StringBuilder key = new StringBuilder();
		
		key.append(name);
		key.append(".");
		key.append(AttributeConstants.LABEL_KEY);

		String encoding = StringUtil.trim(getEncoding());
		String value    = StringUtil.trim(getRequest().getParameter(key.toString()));
		
		try{
			value = URLDecoder.decode(value, encoding);
		}
		catch(Throwable e){
        }
		
		if(value.length() == 0)
		    value = name;
		
		return value;
	}

	/**
	 * Retorna o identificador dos dados vinculados a uma propriedade do modelo de dados.
	 * 
     * @param name String contendo o nome da propriedade.
	 * @param isEditable Indica se a propriedade � edit�vel.
	 * @return String contendo o identificador dos dados.
	 */
	private String getRequestInfoData(String name, Boolean isEditable){
		StringBuilder key = new StringBuilder();

		if(isEditable){
			Integer pos = name.indexOf("_");
			
			if(pos >= 0)
				key.append(name.substring(0, pos));
				
			key.append(".");
			key.append(AttributeConstants.EDITABLE_DATA_KEY);
		}
		else{ 
			key.append(name);
			key.append(".");
			key.append(AttributeConstants.DATA_KEY);
		}

		return StringUtil.trim(getRequest().getParameter(key.toString()));
	}

    /**
     * Retorna o escopo de armazenamento dos dados vinculados a uma propriedade do modelo 
     * de dados.
     * 
     * @param name String contendo o nome da propriedade.
     * @return Constante que define o escopo de armazenamento.
     */
	private ScopeType getRequestInfoDataScope(String name, Boolean isEditable){
		StringBuilder key = new StringBuilder();

		if(isEditable){
			Integer pos = name.indexOf("_");
			
			if(pos >= 0)
				key.append(name.substring(0, pos));
				
			key.append(".");
			key.append(AttributeConstants.EDITABLE_DATA_SCOPE_KEY);
		}
		else{ 
			key.append(name);
			key.append(".");
			key.append(AttributeConstants.DATA_SCOPE_KEY);
		}

		try{
		    String scopeType = StringUtil.trim(getRequest().getParameter(key.toString())).toUpperCase();
		    
			return ScopeType.valueOf(scopeType);
		}
		catch(Throwable e){
			return SystemConstants.DEFAULT_SCOPE_TYPE;
		}
	}

    /**
     * Retorna o �ndice inicial dos dados vinculados a uma propriedade do modelo de dados.
     * 
     * @param name String contendo o nome da propriedade.
     * @return Valor num�rico contendo o �ndice inicial.
     */
	private Integer getRequestInfoDataStartIndex(String name){
		StringBuilder key = new StringBuilder();

		key.append(name);
		key.append(".");
		key.append(AttributeConstants.DATA_START_INDEX_KEY);

		String  index = StringUtil.trim(getRequest().getParameter(key.toString()));
		Integer value = 0;
		
		if(index.length() > 0){
			try{
				value = NumberUtil.parseInt(index);
			}
			catch(Throwable e){
				value = 0;
			}
		}

		return value;
	}

	/**
	 * Retorna o �ndice final dos dados vinculados a uma propriedade do modelo de dados.
	 * 
	 * @param name String contendo o nome da propriedade.
	 * @return Valor num�rico contendo o �ndice final.
	 */
    private Integer getRequestInfoDataEndIndex(String name){
        StringBuilder key = new StringBuilder();

        key.append(name);
        key.append(".");
        key.append(AttributeConstants.DATA_END_INDEX_KEY);

        String  index = StringUtil.trim(getRequest().getParameter(key.toString()));
        Integer value = 0;
        
        if(index.length() > 0){
            try{
                value = NumberUtil.parseInt(index);
            }
            catch(Throwable e){
                value = 0;
            }
        }

        return value;
    }

    /**
     * Retorna a lista de requisi��es contendo os dados das propriedades do modelo de dados.
     * 
     * @return Inst�ncia contendo a lista de requisi��es.
     */
    public Collection<RequestInfo> getRequestInfos(){
		List<String>        requestInfoNames = new LinkedList<String>();
		String              requestInfoName  = "";
		Enumeration<String> enumeration      = getRequest().getParameterNames();
		List<RequestInfo>   requestInfos     = new LinkedList<RequestInfo>();

		while(enumeration.hasMoreElements()){
			requestInfoName = enumeration.nextElement();
		
 			if(!requestInfoName.equals(AttributeConstants.ACTION_KEY) && 
 			   !requestInfoName.endsWith(".".concat(AttributeConstants.CURRENT_GUIDE_KEY)) &&
               !requestInfoName.endsWith(".".concat(AttributeConstants.CURRENT_NODE_KEY)) &&
               !requestInfoName.endsWith(".".concat(AttributeConstants.CURRENT_SECTION_KEY)) &&
               !requestInfoName.endsWith(".".concat(AttributeConstants.DATA_KEY)) &&
               !requestInfoName.endsWith(".".concat(AttributeConstants.DATA_SCOPE_KEY)) &&
               !requestInfoName.endsWith(".".concat(AttributeConstants.DATA_START_INDEX_KEY)) &&
               !requestInfoName.endsWith(".".concat(AttributeConstants.DATA_END_INDEX_KEY)) &&
               !requestInfoName.endsWith(".".concat(AttributeConstants.EDITABLE_DATA_KEY)) &&
               !requestInfoName.endsWith(".".concat(AttributeConstants.EDITABLE_DATA_SCOPE_KEY)) &&
               !requestInfoName.endsWith(".".concat(AttributeConstants.ERROR_TRACE_KEY)) &&
               !requestInfoName.equals(AttributeConstants.FORWARD_KEY) && 
               !requestInfoName.equals(AttributeConstants.FORWARD_ON_FAIL_KEY) && 
               !requestInfoName.endsWith(".".concat(AttributeConstants.HAS_MULTIPLE_SELECTION_KEY)) &&
 			   !requestInfoName.endsWith(".".concat(AttributeConstants.IS_NODE_EXPANDED_KEY)) &&
 	 		   !requestInfoName.endsWith(".".concat(AttributeConstants.ITEMS_PER_PAGE_KEY)) &&
 	 		   !requestInfoName.endsWith(".".concat(AttributeConstants.LABEL_KEY)) &&
               !requestInfoName.equals(AttributeConstants.LAST_ACTION_KEY) &&
               !requestInfoName.endsWith(".".concat(AttributeConstants.PAGER_ACTION_KEY)) &&
               !requestInfoName.endsWith(".".concat(AttributeConstants.PAGER_ON_FORM_KEY)) &&
               !requestInfoName.endsWith(".".concat(AttributeConstants.SORT_PROPERTY_KEY)) &&
 			   !requestInfoName.endsWith(".".concat(AttributeConstants.SORT_ORDER_KEY)) &&
 			   !requestInfoName.endsWith(".".concat(AttributeConstants.PATTERN_KEY)) &&
               !requestInfoName.endsWith(".".concat(AttributeConstants.TOOLTIP_KEY)) &&
               !requestInfoName.equals(AttributeConstants.UPDATE_VIEWS_KEY) &&
               !requestInfoName.equals(AttributeConstants.UPLOAD_DATA_KEY) &&
               !requestInfoName.equals(AttributeConstants.UPLOAD_DATA_PROPERTY_KEY) &&
               !requestInfoName.equals(AttributeConstants.UPLOAD_FILENAME_TYPE_PROPERTY_KEY) &&
               !requestInfoName.equals(AttributeConstants.UPLOAD_CONTENT_TYPE_PROPERTY_KEY) &&
 			   !requestInfoName.equals(AttributeConstants.VALIDATE_MODEL_KEY) &&
               !requestInfoName.equals(AttributeConstants.VALIDATE_PROPERTIES_KEY) &&
               !requestInfoName.equals(AttributeConstants.VALIDATE_SEARCH_MODEL_KEY))
 			    requestInfoNames.add(requestInfoName);
		}

		Collections.sort(requestInfoNames);
		
		for(Integer cont = 0 ; cont < requestInfoNames.size() ; cont++){
		    requestInfoName = requestInfoNames.get(cont);
		    
		    requestInfos.add(getRequestInfo(requestInfoName));
		}
		
		return requestInfos;
	}
    
    /**
     * Retorna o caminho raiz do contexto.
     *
     * @return String contendo o caminho raiz.
     */
    public String getContextPath(){
        return StringUtil.trim(getSession().getServletContext().getContextPath());
    }
    
    /**
     * Retorna a inst�ncia de um formul�rio espec�fico.
     * 
     * @param name String contendo o identificador do formul�rio.
     * @return Inst�ncia contendo as propriedades do formul�rio.
     */
    public <F extends BaseActionForm> F getActionForm(String name){
        return findAttribute(name, ScopeType.SESSION);
    }
}