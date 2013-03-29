package br.com.concepting.framework.security.web;

import org.apache.commons.beanutils.ConstructorUtils;

import br.com.concepting.framework.model.SystemModuleModel;
import br.com.concepting.framework.model.SystemSessionModel;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.resource.SystemResource;
import br.com.concepting.framework.resource.SystemResourceLoader;
import br.com.concepting.framework.security.constants.SecurityConstants;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.security.model.UserModel;
import br.com.concepting.framework.security.resource.SecurityResource;
import br.com.concepting.framework.security.resource.SecurityResourceLoader;
import br.com.concepting.framework.web.SystemController;
import br.com.concepting.framework.web.types.ScopeType;

/**
 * Classe responsável pelo controle das requisições de segurança do sistema.
 *
 * @author fvilarinho
 * @since 1.0
 */
public class SecurityController{
    private SystemController systemController = null;
    
    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param systemController Instância do controlador de requisicões do sistema.
     */
    public SecurityController(SystemController systemController){
        super();
        
        this.systemController = systemController;
    }
    
    /**
     * Indica se o usuário está autenticado.
     * 
     * @return True/False.
     */
    public <L extends LoginSessionModel> Boolean isAuthenticated(){
        L loginSession = getLoginSession();
        
        return (loginSession != null && loginSession.isAuthenticated());
    }

    /**
     * Retorna a instância contendo as propriedades do login atual.
     *
     * @return Instância contendo as propriedades do login.
     */
    public <L extends LoginSessionModel, U extends UserModel> L getLoginSession(){
        L loginSession = null;
        
        try{
            SecurityResourceLoader loader            = new SecurityResourceLoader();
            SecurityResource       securityResource  = loader.getDefault();
            Class                  loginSessionClass = null;

            loginSession = systemController.findAttribute(SecurityConstants.LOGIN_SESSION_KEY, ScopeType.SESSION);
        
            if(loginSession == null){
                loginSessionClass = Class.forName(securityResource.getSessionClass());

                ModelInfo modelInfo = ModelUtil.getModelInfo(loginSessionClass);
                
                if(modelInfo != null){
                    loginSession = (L)ConstructorUtils.invokeConstructor(loginSessionClass, null);
            
                    UserModel    user         = loginSession.getUser();
                    PropertyInfo propertyInfo = modelInfo.getPropertyInfo("user");
            
                    if(propertyInfo != null){
                        if(user == null){
                            user = (UserModel)ConstructorUtils.invokeConstructor(propertyInfo.getClazz(), null);
                    
                            loginSession.setUser(user);
                        }
                    }
            
                    SystemModuleModel systemModule = loginSession.getSystemModule();
                    
                    propertyInfo = modelInfo.getPropertyInfo("systemModule");
                    
                    if(propertyInfo != null){
                        if(systemModule == null){
                            systemModule = (SystemModuleModel)ConstructorUtils.invokeConstructor(propertyInfo.getClazz(), null);
                            
                            systemModule.setUrl(systemController.getContextPath());
                    
                            loginSession.setSystemModule(systemModule);
                        }
                    }
                    
                    SystemSessionModel systemSession = new SystemSessionModel();
                    
                    systemSession.setId(systemController.getSession().getId());
                    
                    SystemResourceLoader systemResourceLoader = new SystemResourceLoader();
                    SystemResource       systemResource       = systemResourceLoader.get();
                            
                    systemSession.setCurrentSkin(systemResource.getDefaultSkin());
                    systemSession.setCurrentLanguage(systemResource.getDefaultLanguage());
                    
                    loginSession.setSystemSession(systemSession);
                    
                    systemController.setAttribute(SecurityConstants.LOGIN_SESSION_KEY, loginSession, ScopeType.SESSION);
                }
            }
        }
        catch(Throwable e){
        }
        
        return loginSession;
    }

    /**
     * Define a instância contendo as propriedades do login atual.
     *
     * @param loginSession Instância contendo as propriedades do login.
     */
    public <L extends LoginSessionModel> void setLoginSession(L loginSession){
        systemController.setAttribute(SecurityConstants.LOGIN_SESSION_KEY, loginSession, ScopeType.SESSION);
    }
}
