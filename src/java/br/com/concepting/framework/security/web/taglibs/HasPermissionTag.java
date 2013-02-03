package br.com.concepting.framework.security.web.taglibs;

import javax.servlet.jsp.JspException;

import br.com.concepting.framework.model.ObjectModel;
import br.com.concepting.framework.model.SystemModuleModel;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.security.model.UserModel;
import br.com.concepting.framework.web.taglibs.BaseTag;

/**
 * Verifica se o logIn atual possui a devida permissão.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class HasPermissionTag extends BaseTag{
	private Object value = null;
	
	/**
	 * Retorna a instância que define a permissão a ser verificada.
	 * 
	 * @return Instância que define a permissão desejada.
	 */
	public Object getValue(){
		return value;
	}
	
	/**
	 * Defime a instância que define a permissão a ser verificada.
	 * 
	 * @param value Instância que define a permissão desejada.
	 */
	public void setValue(Object value){
		this.value = value;
	}
	
	/**
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public int doStartTag() throws JspException{
		LoginSessionModel loginSession  = securityController.getLoginSession();
		Boolean           hasPermission = true;
		
		if(loginSession != null){
		    hasPermission = (loginSession.getId() != null && loginSession.getId() > 0);
        
		    if(hasPermission){
	            UserModel user = loginSession.getUser();
            
	            hasPermission = (user != null && user.getId() != null && user.getId() > 0 && !user.changePassword()); 
            
	            if(hasPermission){
	                if(user.isSuperUser())
	                    hasPermission = true;
	                else{
	                    if(value instanceof SystemModuleModel)
	                        hasPermission = user.hasPermission((SystemModuleModel)value);
	                    else if(value instanceof ObjectModel)
	                        hasPermission = user.hasPermission((ObjectModel)value);
	                }
	            }
            }
        }

        if(hasPermission)
            return EVAL_BODY_INCLUDE;
		
		return SKIP_BODY;
	}
	
	/**
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	public int doEndTag() throws JspException{
		return SKIP_BODY;
	}
}
	