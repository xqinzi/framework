package br.com.concepting.framework.security.web;

import javax.servlet.http.Cookie;

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
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.web.SystemController;
import br.com.concepting.framework.web.types.ScopeType;

/**
 * Classe respons�vel pelo controle das requisi��es de seguran�a do sistema.
 *
 * @author fvilarinho
 * @since 1.0
 */
public class SecurityController{
    private SystemController systemController = null;
    
    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param systemController Inst�ncia do controlador de requisic�es do sistema.
     */
    public SecurityController(SystemController systemController){
        super();
        
        this.systemController = systemController;
    }
    
    /**
     * Indica se a sess�o de login est� autenticada.
     * 
     * @return True/False.
     */
    public <L extends LoginSessionModel> Boolean isLoginSessionAuthenticated(){
        L loginSession = getLoginSession();
        
        return (loginSession != null && loginSession.getId() != null && loginSession.getId() > 0 && loginSession.getUser() != null && loginSession.getUser().getId() != null && loginSession.getUser().getId() > 0);
    }
    
    /**
     * Indica se a sess�o de login expirou.
     * 
     * @return True/False.
     */
    public Boolean isLoginSessionExpired(){
        if(!isLoginSessionAuthenticated()){
            Cookie cookie = systemController.getCookie(SecurityConstants.LOGIN_SESSION_KEY);
            
            if(cookie == null)
                return false;
            
            systemController.removeCookie(SecurityConstants.LOGIN_SESSION_KEY);
            
            return true;
        }
        
        return false;
    }

    /**
     * Retorna a inst�ncia contendo as propriedades do login atual.
     *
     * @return Inst�ncia contendo as propriedades do login.
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
                    SystemResource       systemResource       = systemResourceLoader.getDefault();
                            
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
     * Define a inst�ncia contendo as propriedades do login atual.
     *
     * @param loginSession Inst�ncia contendo as propriedades do login.
     */
    public <L extends LoginSessionModel> void setLoginSession(L loginSession){
        if(loginSession != null && loginSession.getId() != null && loginSession.getId() > 0){
            UserModel user = loginSession.getUser();
            
            if(user != null && user.getId() != null && user.getId() > 0)
                systemController.addCookie(SecurityConstants.LOGIN_SESSION_KEY, Boolean.TRUE.toString(), true);
        }
        
        systemController.setAttribute(SecurityConstants.LOGIN_SESSION_KEY, loginSession, ScopeType.SESSION);
    }
    
    /**
     * Retorna o nome do usu�rio marcado para ser lembrado.
     * 
     * @return String contendo o nome do usu�rio.
     */
    public String getRememberedUser(){
        Cookie cookie = systemController.getCookie(SecurityConstants.REMEMBER_USER_AND_PASSWORD_KEY);
        
        if(cookie != null){
            String value[] = StringUtil.split(StringUtil.trim(cookie.getValue()), "|");
            
            return value[0];
        }
        
        return "";
    }
    
    /**
     * Retorna a senha do usu�rio marcado para ser lembrado.
     * 
     * @return String contendo a senha do usu�rio.
     */
    public String getRememberedPassword(){
        Cookie cookie = systemController.getCookie(SecurityConstants.REMEMBER_USER_AND_PASSWORD_KEY);
        
        if(cookie != null){
            String value[] = StringUtil.split(StringUtil.trim(cookie.getValue()), "|");
            
            if(value.length > 1)
                return value[1];
        }
        
        return "";
    }

    /**
     * Efetua a marca��o para lembran�a de nome do usu�rio e senha.
     *  
     * @param user Inst�ncia contendo as informa��es do usu�rio.
     */
    public <U extends UserModel> void rememberUserAndPasword(U user){
        if(user != null && user.getName().length() > 0){
            String       userName = user.getName();
            String       password = user.getPassword();
            String       value    = userName.concat("|").concat(password);
                
            systemController.addCookie(SecurityConstants.REMEMBER_USER_AND_PASSWORD_KEY, value, true);
        }
    }
    
    /**
     * Esquece as informa��es de nome de usu�rio e senha marcados anteriormente para serem lembrados.
     */
    public void forgetUserAndPassword(){
        systemController.removeCookie(SecurityConstants.REMEMBER_USER_AND_PASSWORD_KEY);
    }
}
