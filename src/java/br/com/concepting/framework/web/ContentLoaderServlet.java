package br.com.concepting.framework.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.concepting.framework.util.constants.AttributeConstants;
import br.com.concepting.framework.web.types.ContentType;
import br.com.concepting.framework.web.types.ScopeType;

/**
 * Classe que define o servlet para download de conteúdo.
 *
 * @author fvilarinho
 * @since 1.0
 */
public class ContentLoaderServlet extends HttpServlet{
	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	    doPost(request, response);
    }

    /**
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	SystemController controller       = new SystemController(request, response);
    	String           contentId        = controller.getRequestInfoValue(AttributeConstants.CONTENT_ID_KEY);
    	ContentType      contentType      = ContentType.BINARY;
    	String           contentData      = controller.getRequestInfoValue(AttributeConstants.CONTENT_DATA_KEY);
    	ScopeType        contentDataScope = ScopeType.SESSION;

    	try{
    		contentType      = ContentType.valueOf(controller.getRequestInfoValue(AttributeConstants.CONTENT_TYPE_KEY).toUpperCase());
        	contentDataScope = ScopeType.valueOf(controller.getRequestInfoValue(AttributeConstants.CONTENT_DATA_SCOPE_KEY).toUpperCase());
    	}
    	catch(Throwable e){
    	}
    	
    	byte contentDataBytes[] = controller.findAttribute(contentData, contentDataScope);
    	
    	controller.outputContent(contentDataBytes, contentType, contentId);
    }
}