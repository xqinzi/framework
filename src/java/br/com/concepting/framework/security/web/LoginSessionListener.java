package br.com.concepting.framework.security.web;

import java.util.Collection;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.SystemModuleModel;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.security.model.UserModel;
import br.com.concepting.framework.security.resource.SecurityResource;
import br.com.concepting.framework.security.resource.SecurityResourceLoader;
import br.com.concepting.framework.security.service.interfaces.LoginSessionService;
import br.com.concepting.framework.service.interfaces.IService;
import br.com.concepting.framework.service.util.ServiceUtil;
import br.com.concepting.framework.util.ExceptionUtil;
import br.com.concepting.framework.web.SystemController;
import br.com.concepting.framework.web.form.ActionFormMessageController;

/**
 * Listener responsável por efetuar o logout quando a sessão do login expirar.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class LoginSessionListener implements HttpSessionListener{
    protected SystemController systemController = null;
    
    /**
     * Retorna a instância da classe de serviço a partir de seu modelo de dados.
     * 
     * @param modelClass Classe que define o modelo de dados.
     * @return Instância da classe de serviço desejada.
     * @throws Throwable
     */
    protected <S extends IService, M extends BaseModel> S getService(Class<M> modelClass) throws Throwable{
        SecurityController  securityController = systemController.getSecurityController();
        LoginSessionModel   loginSession       = securityController.getLoginSession();
        S                   service            = ServiceUtil.getService(modelClass);
        
        service.setLoginSession(loginSession);
        
        return service; 
    }
    
    /**
     * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent event){
        systemController = new SystemController(event.getSession());
        
        try{
            initialize();
            onCreate();
        }
        catch(Throwable e){
            e = ExceptionUtil.getOriginException(e);

            ActionFormMessageController actionFormMessageController = systemController.getActionFormMessageController();

            if(ExceptionUtil.isExpectedException(e))
                actionFormMessageController.addMessage(e);
            else if(!ExceptionUtil.isInternalErrorException(e))
                e = new InternalErrorException(e);

            if(ExceptionUtil.isInternalErrorException(e))
                systemController.setCurrentException(e);
        }
    }
    
    /**
     * Inicializa a sessão.
     *  
     * @throws Throwable
     */
    protected void initialize() throws Throwable{
        SecurityResourceLoader securityResourceLoader = new SecurityResourceLoader();
        SecurityResource       securityResource       = securityResourceLoader.getDefault();
        
        systemController.getSession().setMaxInactiveInterval(securityResource.getSessionTimeout() * 60);
    }
    
    /**
     * Executado no momento do instanciamento da sessão.
     * 
     * @throws Throwable
     */
    protected void onCreate() throws Throwable{
        SecurityController            securityController = systemController.getSecurityController();
        LoginSessionModel             loginSession       = securityController.getLoginSession();
        SystemModuleModel             systemModule       = loginSession.getSystemModule();
        IService                      service            = getService(systemModule.getClass());
        Collection<SystemModuleModel> systemModules      = service.search(systemModule);
    
        if(systemModules.size() > 0){
            systemModule = systemModules.iterator().next();
            systemModule = service.loadReference(systemModule, "forms");
        }
    
        loginSession.setSystemModule(systemModule);
    
        securityController.setLoginSession(loginSession);
    }
    
    /**
     * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent event){
        systemController = new SystemController(event.getSession());
        
        try{
            onDestroy();
        }
        catch(Throwable e){
            e = ExceptionUtil.getOriginException(e);
            
            ActionFormMessageController actionFormMessageController = systemController.getActionFormMessageController();

            if(ExceptionUtil.isExpectedException(e))
                actionFormMessageController.addMessage(e);
            else if(!ExceptionUtil.isInternalErrorException(e))
                e = new InternalErrorException(e);
            
            if(ExceptionUtil.isInternalErrorException(e))
                systemController.forward(e);
        }
    }
    
    /**
     * Executado no momento da finalização da sessão.
     * 
     * @throws Throwable
     */
    protected void onDestroy() throws Throwable{
        SecurityController securityController = systemController.getSecurityController();
        LoginSessionModel  loginSession       = securityController.getLoginSession();
        
        if(loginSession != null && loginSession.getId() != null && loginSession.getId() > 0){
            UserModel user = loginSession.getUser();
            
            if(user != null && user.getId() > 0 && user.getId() > 0){
                LoginSessionService service = getService(loginSession.getClass());

                service.logOut(loginSession);
            }
        }
    }
}