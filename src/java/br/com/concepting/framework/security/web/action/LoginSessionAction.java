package br.com.concepting.framework.security.web.action;

import br.com.concepting.framework.security.exceptions.LoginSessionExpiredException;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.security.model.UserModel;
import br.com.concepting.framework.security.service.interfaces.LoginSessionService;
import br.com.concepting.framework.security.web.form.LoginSessionActionForm;
import br.com.concepting.framework.web.action.BaseAction;

/**
 * Classe que define as a��es do login de um usu�rio.
 * 
 * @author fvilarinho
 * @since 1.0
 */ 
public class LoginSessionAction extends BaseAction{
    public void init() throws Throwable{
        super.init();
        
        LoginSessionActionForm actionForm   = getActionForm();
        LoginSessionModel      loginSession = securityController.getLoginSession();
        
        actionForm.setModel(loginSession);
    }
    
	/**
	 * Efetua o login do usu�rio.
	 * 
	 * @throws Throwable
	 */
	public void logIn() throws Throwable{
	    LoginSessionActionForm actionForm   = getActionForm();
	    LoginSessionModel      loginSession = actionForm.getModel();
        LoginSessionService    service      = getService();

        try{
    	    service.logIn(loginSession);
        }
        finally{
    	    actionForm.setModel(loginSession);
    	    
    	    securityController.setLoginSession(loginSession);
        }
	}

	/**
	 * Inicia a mudan�a da senha do login do usu�rio.
	 *
	 * @throws Throwable
	 */
	public void loadChangePassword() throws Throwable{
        LoginSessionActionForm actionForm   = getActionForm();
        LoginSessionModel      loginSession = actionForm.getModel();
        UserModel              user         = loginSession.getUser();

        user.setChangePassword(true);
        
        loginSession.setUser(user);

        actionForm.setModel(loginSession);
	}
	
	/**
	 * Confirma a mudan�a da senha do login do usu�rio.
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
	 * Cancela a mudan�a de senha.
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
    
    public void loadForgotPassword() throws Throwable{
        LoginSessionActionForm actionForm   = getActionForm();
        LoginSessionModel      loginSession = actionForm.getModel();
        UserModel              user         = loginSession.getUser();

        user.setEmail("");
        
        loginSession.setUser(user);

        actionForm.setModel(loginSession);
    }
    
    public void sendForgottenPassword() throws Throwable{
        LoginSessionActionForm actionForm   = getActionForm();
        LoginSessionModel      loginSession = actionForm.getModel();
        LoginSessionService    service      = getService();
        
        service.sendForgottenPassword(loginSession);
        
        actionFormMessageController.addSuccessMessage();
    }

    /**
     * Efetua o logout do usu�rio.
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