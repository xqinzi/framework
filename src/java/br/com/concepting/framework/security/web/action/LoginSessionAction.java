package br.com.concepting.framework.security.web.action;

import br.com.concepting.framework.security.exceptions.LoginSessionExpiredException;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.security.model.UserModel;
import br.com.concepting.framework.security.service.interfaces.LoginSessionService;
import br.com.concepting.framework.security.web.form.LoginSessionActionForm;
import br.com.concepting.framework.web.action.BaseAction;
import br.com.concepting.framework.web.helpers.RequestInfo;

/**
 * Classe que define as ações do login de um usuário.
 * 
 * @author fvilarinho
 * @since 1.0
 */ 
public class LoginSessionAction extends BaseAction{
    /**
     * Efetua o carregamento da tela de login.
     * 
     * @throws Throwable
     */
    public void init() throws Throwable{
        super.init();
        
        LoginSessionActionForm actionForm   = getActionForm();
        LoginSessionModel      loginSession = securityController.getLoginSession();
        UserModel              user         = loginSession.getUser();
        
        user.setName(securityController.getRememberedUser());
        user.setPassword(securityController.getRememberedPassword());
        
        if(user.getName().length() > 0)
            loginSession.setRememberUserAndPassword(true);
        else
            loginSession.setRememberUserAndPassword(false);
        
        loginSession.setUser(user);
        
        actionForm.setModel(loginSession);
    }
    
	/**
	 * Efetua o login do usuário.
	 * 
	 * @throws Throwable
	 */
	public void logIn() throws Throwable{
	    LoginSessionActionForm actionForm          = getActionForm();
	    LoginSessionModel      currentLoginSession = securityController.getLoginSession();
	    LoginSessionModel      loginSession        = actionForm.getModel();
	    
	    loginSession.setSystemModule(currentLoginSession.getSystemModule());
	    loginSession.setSystemSession(currentLoginSession.getSystemSession());
	    
        LoginSessionService service = getService();

        try{
    	    service.logIn(loginSession);
        }
        finally{
    	    actionForm.setModel(loginSession);
    	    
    	    securityController.setLoginSession(loginSession);
        }
	}

	/**
	 * Inicia a mudança da senha do login do usuário.
	 *
	 * @throws Throwable
	 */
	public void loadChangePassword() throws Throwable{
        LoginSessionActionForm actionForm   = getActionForm();
        LoginSessionModel      loginSession = actionForm.getModel();
        UserModel              user         = loginSession.getUser();

        user.setChangePassword(true);
        user.setNewPassword("");
        user.setConfirmPassword("");
        
        loginSession.setUser(user);

        actionForm.setModel(loginSession);
	}
	
	/**
	 * Confirma a mudança da senha do login do usuário.
	 *
	 * @throws Throwable
	 */
	public void changePassword() throws Throwable{
	    LoginSessionActionForm actionForm   = getActionForm();
		LoginSessionModel      loginSession = actionForm.getModel();
		LoginSessionService    service      = getService();
		
		service.changePassword(loginSession);
		
		actionFormMessageController.addSuccessMessage();
	}
	
	/**
	 * Cancela a mudança de senha.
	 * 
	 * @throws Throwable
	 */
	public void cancelChangePassword() throws Throwable{
        LoginSessionActionForm actionForm   = getActionForm();
        LoginSessionModel      loginSession = actionForm.getModel();
		UserModel              user         = loginSession.getUser();
		
		user.setChangePassword(false);
		
        loginSession.setUser(user);

        actionForm.setModel(loginSession);
	}
	
	/**
	 * Lembra o usuário e senha do usuário.
	 * 
	 * @throws Throwable
	 */
    public void rememberUserAndPassword() throws Throwable{
        LoginSessionActionForm actionForm   = getActionForm();
        LoginSessionModel      loginSession = actionForm.getModel();

        if(loginSession.rememberUserAndPassword()){
            UserModel user = loginSession.getUser();
            
            if(user != null && user.getName().length() > 0)
                securityController.rememberUserAndPasword(user);
            else{
                loginSession.setRememberUserAndPassword(false);
                
                actionForm.setModel(loginSession);
                
                securityController.forgetUserAndPassword();
                
                RequestInfo requestInfo = systemController.getRequestInfo("user.name");
                
                actionFormMessageController.addValidationRequiredMessage(requestInfo.getName(), requestInfo.getLabel());
            }
        }
        else
            securityController.forgetUserAndPassword();
    }
    
    /**
     * Carrega formulário para envio de senha perdida.
     * 
     * @throws Throwable
     */
    public void loadForgotPassword() throws Throwable{
        LoginSessionActionForm actionForm   = getActionForm();
        LoginSessionModel      loginSession = actionForm.getModel();
        UserModel              user         = loginSession.getUser();

        user.setEmail("");
        
        loginSession.setUser(user);

        actionForm.setModel(loginSession);
    }
    
    /**
     * Envia para o usuário a sua senha perdida.
     * 
     * @throws Throwable
     */
    public void sendForgottenPassword() throws Throwable{
        LoginSessionActionForm actionForm   = getActionForm();
        LoginSessionModel      loginSession = actionForm.getModel();
        LoginSessionService    service      = getService();
        
        service.sendForgottenPassword(loginSession);
        
        actionFormMessageController.addSuccessMessage();
    }

    /**
     * Efetua o logout do usuário.
     * 
     * @throws Throwable
     */
    public void logOut() throws Throwable{
        LoginSessionActionForm actionForm   = getActionForm();
        LoginSessionModel      loginSession = actionForm.getModel();
        LoginSessionService    service      = getService();
        
        service.logOut(loginSession);

        actionFormMessageController.addMessage(new LoginSessionExpiredException());

        init();
    }
}