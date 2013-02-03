package br.com.concepting.framework.security.service;

import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import br.com.concepting.framework.model.SystemModuleModel;
import br.com.concepting.framework.model.SystemSessionModel;
import br.com.concepting.framework.model.exceptions.ItemAlreadyExistsException;
import br.com.concepting.framework.persistence.interfaces.IDAO;
import br.com.concepting.framework.security.exceptions.ExpiredPasswordException;
import br.com.concepting.framework.security.exceptions.InvalidPasswordException;
import br.com.concepting.framework.security.exceptions.PasswordWillExpireException;
import br.com.concepting.framework.security.exceptions.PasswordsNotMatchException;
import br.com.concepting.framework.security.exceptions.PermissionDeniedException;
import br.com.concepting.framework.security.exceptions.UserAlreadyLoggedInException;
import br.com.concepting.framework.security.exceptions.UserBlockedException;
import br.com.concepting.framework.security.exceptions.UserNotFoundException;
import br.com.concepting.framework.security.model.AccessListModel;
import br.com.concepting.framework.security.model.HostModel;
import br.com.concepting.framework.security.model.LoginParametersModel;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.security.model.UserModel;
import br.com.concepting.framework.security.service.interfaces.LoginSessionService;
import br.com.concepting.framework.security.util.CryptoDigest;
import br.com.concepting.framework.service.BaseRemoteService;
import br.com.concepting.framework.util.DateTimeUtil;
import br.com.concepting.framework.util.exceptions.InternalErrorException;
import br.com.concepting.framework.util.types.DateFieldType;

/**
 * Classe contendo os métodos que implementam as regras de negócio que envolvem o modelo de dados 
 * 'LoginSessionModel'.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class LoginSessionServiceImpl extends BaseRemoteService implements LoginSessionService{
    /**
     * @see br.com.concepting.framework.security.service.interfaces.LoginSessionService#logIn(br.com.concepting.framework.security.model.LoginSessionModel)
     */
	public <L extends LoginSessionModel, U extends UserModel, S extends SystemModuleModel, SS extends SystemSessionModel, LP extends LoginParametersModel, A extends AccessListModel, H extends HostModel> void logIn(L loginSession) throws UserNotFoundException, UserBlockedException, InvalidPasswordException, InternalErrorException, UserAlreadyLoggedInException, PermissionDeniedException, ExpiredPasswordException, PasswordWillExpireException{
	    if(loginSession != null){
	        loginSession.setId(null);
	        
	        U user = loginSession.getUser();
	        
            user.setId(null);
            user.setActive(null);

            IDAO userDao = getPersistence(user.getClass());

            try{
	            Collection<U> users = userDao.search(user);
            
                if(users == null || users.size() == 0)
                	throw new UserNotFoundException();
                
                String password = user.getPassword();
                        
                if(password.length() > 0){
                	try{
                    	CryptoDigest digest = new CryptoDigest(true);
                    	
                    	password = digest.encrypt(password);
                	}
                    catch(NoSuchAlgorithmException e){
                    	throw new InternalErrorException(e);
                    }
                }
                        
                user = users.iterator().next();
                    
                if(!password.equals(user.getPassword()))
                	throw new InvalidPasswordException();
                        
                if(!user.isActive())
                	throw new UserBlockedException();
            
                user = userDao.loadReference(user, "loginParameters");
                
                SS      systemSession      = loginSession.getSystemSession();
                String  ip                 = systemSession.getIp();
                LP      loginParameters    = user.getLoginParameters();
                Boolean expiredPassword    = false;
                Boolean passwordWillExpire = false;
                Integer daysUntilExpire    = 0;
                
                if(loginParameters != null){
                    IDAO loginParametersDao = getPersistence(loginParameters.getClass());
                    
                    loginParameters = loginParametersDao.loadReference(loginParameters, "accessLists");
                    
                    List<A> accessLists = loginParameters.getAccessLists();
                    
                    if(accessLists != null && accessLists.size() > 0){
                        IDAO    accessListDao = getPersistence(accessLists.iterator().next().getClass());
                        List<H> hosts         = null;
                        Boolean found         = false;
                        
                        for(A accessList : accessLists){
                            accessList = accessListDao.loadReference(accessList, "hosts");
                            hosts      = accessList.getHosts();
                            found      = true;
                            
                            if(!accessList.getWhitelist()){
                                if(hosts != null && hosts.size() > 0){
                                    for(H host : hosts){
                                        if(ip.equals(host.getIp()))
                                            throw new PermissionDeniedException();
                                    }
                                }
                            }
                            else{
                                if(hosts != null && hosts.size() > 0){
                                    for(H host : hosts){
                                        if(ip.equals(host.getIp())){
                                            found = true;
                                            
                                            break;
                                        }
                                    }
                                    
                                    if(!found)
                                        throw new PermissionDeniedException();
                                }
                            }
                        }
                    }
                
                    Date    expirePasswordDate     = user.getExpirePasswordDate();
                    Date    now                    = new Date();
                    Integer expirePasswordInterval = loginParameters.getExpirePasswordInterval();
                    Integer changePasswordInterval = loginParameters.getChangePasswordInterval();
                
                    if(expirePasswordInterval != null && expirePasswordInterval > 0){
                        if(expirePasswordDate == null){
                            expirePasswordDate = DateTimeUtil.add(now, expirePasswordInterval, DateFieldType.DAY);
                    
                            user.setExpirePasswordDate(expirePasswordDate);
                            
                            loginSession.setUser(user);
                        }
                        
                        daysUntilExpire = DateTimeUtil.diff(expirePasswordDate, now, DateFieldType.DAY).intValue();

                        if(changePasswordInterval != null && changePasswordInterval > 0)
                            if(daysUntilExpire <= changePasswordInterval)
                                passwordWillExpire = true;
                    }
                
                    if((expirePasswordDate != null && now.after(expirePasswordDate)) || user.changePassword()){
                        expiredPassword    = true;
                        passwordWillExpire = false;
                    }
                }
    
                IDAO    loginSessionDao = getPersistence();
                Boolean multipleLogins  = (loginParameters != null ? loginParameters.getMultipleLogins() : true);
                Boolean superUser       = user.getSuperUser();
        
                if(!superUser){
                    if(multipleLogins == null || !multipleLogins){
                        Long id = loginSession.getId();
                
                        loginSession.setId(null);
                
                        Collection<L> loginSessions = loginSessionDao.search(loginSession);
                
                        loginSession.setId(id);
                
                        if(loginSessions != null && loginSessions.size() > 0)
                            throw new UserAlreadyLoggedInException();
                    }
                }
    
                user = userDao.loadReference(user, "groups");
        
                loginSession.setDate(new Date());
                loginSession.setUser(user);

                if(!user.isSuperUser()){
                    S systemModule = loginSession.getSystemModule();
            
                    if(!user.hasPermission(systemModule))
                        throw new PermissionDeniedException();
                }
    
                try{
                    loginSessionDao.save(loginSession);
                }
                catch(ItemAlreadyExistsException e){
                    throw new UserAlreadyLoggedInException();
                }

                if(expiredPassword){
                    user.setChangePassword(true);
                    user.setNewPassword("");
            
                    throw new ExpiredPasswordException();
                }
        
                if(passwordWillExpire)
                    throw new PasswordWillExpireException(daysUntilExpire);
            }
            finally{
                userDao.update(user);
                
                loginSession.setUser(user);
            }
        }
	}

	/**
	 * @see br.com.concepting.framework.security.service.interfaces.LoginSessionService#changePassword(br.com.concepting.framework.security.model.LoginSessionModel)
	 */
    public <L extends LoginSessionModel, U extends UserModel> void changePassword(L loginSession) throws PasswordsNotMatchException, InternalErrorException{
        if(loginSession != null && loginSession.getId() != null && loginSession.getId() > 0){
            U      user            = loginSession.getUser();
            String newPassword     = user.getNewPassword();
            String confirmPassword = user.getConfirmPassword();
            
            user = loginSession.getUser();
            
            if(user != null && user.getId() != null && user.getId() > 0){
                user.setNewPassword(newPassword);
                user.setConfirmPassword(confirmPassword);
        
                if(!newPassword.equals(confirmPassword))
                    throw new PasswordsNotMatchException();
                
                IDAO         userDao = getPersistence(user.getClass()); 
                CryptoDigest digest  = null;
        
                if(newPassword.length() > 0){
                    try{
                        digest      = new CryptoDigest(true);
                        newPassword = digest.encrypt(newPassword);
                    }
                    catch(Throwable e){
                        throw new InternalErrorException(e);
                    }
                }
        
                user.setPassword(newPassword);
        
                Date lastUpdateDate = new Date();
        
                user.setLastUpdateDate(lastUpdateDate);
        
                LoginParametersModel loginParameters = user.getLoginParameters();
            
                if(loginParameters != null){
                    Integer expirePasswordInterval = loginParameters.getExpirePasswordInterval();
                    
                    if(expirePasswordInterval != null){
                        Date expirePasswordDate = DateTimeUtil.add(lastUpdateDate, expirePasswordInterval, DateFieldType.DAY);
                        
                        user.setExpirePasswordDate(expirePasswordDate);
                    }
                }
    
                user.setChangePassword(false);
        
                userDao.update(user);
                
                loginSession.setUser(user);
            }
        }
    }

    /**
     * @see br.com.concepting.framework.security.service.interfaces.LoginSessionService#logOut(br.com.concepting.framework.security.model.LoginSessionModel)
     */
	public <L extends LoginSessionModel> void logOut(L loginSession) throws InternalErrorException{
		if(loginSession != null && loginSession.getId() != null && loginSession.getId() > 0){
			IDAO dao = getPersistence();
			
			dao.delete(loginSession);
			
			loginSession.setId(null);
			loginSession.setUser(null);
		}
	}

	/**
	 * @see br.com.concepting.framework.security.service.interfaces.LoginSessionService#sendForgottenPassword(br.com.concepting.framework.security.model.LoginSessionModel)
	 */
    public <L extends LoginSessionModel, U extends UserModel> void sendForgottenPassword(L loginSession) throws UserNotFoundException, UserBlockedException, InternalErrorException, RemoteException{
        if(loginSession != null){
            U       user  = loginSession.getUser();
            IDAO    dao   = getPersistence(user.getClass());
            List<U> users = dao.search(user);
            
            if(users == null || users.size() == 0)
                throw new UserNotFoundException();
            
            user = users.iterator().next();
            if(!user.isActive())
                throw new UserBlockedException();
            
            sendForgottenPasswordMessage(user);
        }
    }
    
    protected abstract <L extends LoginSessionModel, U extends UserModel> void sendForgottenPasswordMessage(U user) throws InternalErrorException, RemoteException;
}