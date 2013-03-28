package br.com.concepting.framework.web.taglibs;

import javax.servlet.jsp.JspException;

import br.com.concepting.framework.resource.SystemResource;
import br.com.concepting.framework.resource.SystemResourceLoader;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.security.model.UserModel;
import br.com.concepting.framework.security.resource.SecurityResource;
import br.com.concepting.framework.security.resource.SecurityResourceLoader;
import br.com.concepting.framework.web.taglibs.BaseTag;

/**
 * Classe que define o componente visual para inicialização da aplicação.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class SystemTag extends BaseTag{
    private SystemResource   systemResource   = null;
    private SecurityResource securityResource = null;

    /**
     * Retorna a instância contendo as configurações da aplicação.
     * 
     * @return Instância contendo as configurações da aplicação.
     */
    protected SystemResource getSystemResource(){
        return systemResource;
    }

    /**
     * Define a instância contendo as configurações da aplicação.
     * 
     * @param systemResource Instância contendo as configurações da aplicação.
     */
    protected void setSystemResource(SystemResource systemResource){
        this.systemResource = systemResource;
    }

    /**
     * Retorna as configurações de segurança da aplicação.
     * 
     * @return Instância contendo as configurações de segurança.
     */
    protected SecurityResource getSecurityResource(){
        return securityResource;
    }

    /**
     * Retorna as configurações de segurança da aplicação.
     * 
     * @return Instância contendo as configurações de segurança.
     */
    protected void setSecurityResource(SecurityResource securityResource){
        this.securityResource = securityResource;
    }
    
    /**
     * @see br.com.concepting.framework.web.taglibs.BaseTag#initialize()
     */
    protected void initialize() throws Throwable{
        if(systemResource == null){
            SystemResourceLoader loader = new SystemResourceLoader();
            
            systemResource = loader.get();
        }
        
        if(securityResource == null){
            SecurityResourceLoader loader = new SecurityResourceLoader();
            
            securityResource = loader.get();
        }
    }
    
    /**
     * @see br.com.concepting.framework.web.taglibs.BaseTag#doEndTag()
     */
    public int doEndTag() throws JspException{
        LoginSessionModel loginSession = securityController.getLoginSession();
        
        if(loginSession != null && loginSession.getId() != null && loginSession.getId() > 0){
            UserModel user = loginSession.getUser();
           
            if(user != null && user.changePassword()){
                if(systemResource.getLoginPage().length() > 0)
                    systemController.forward(systemResource.getLoginPage());
            }
            else{
                if(systemResource.getMainPage().length() > 0)
                    systemController.forward(systemResource.getMainPage());
            }
        }
        else{
            if(systemResource.getLoginPage().length() > 0)
                systemController.forward(systemResource.getLoginPage());
        }
        
        return super.doEndTag();
    }
}