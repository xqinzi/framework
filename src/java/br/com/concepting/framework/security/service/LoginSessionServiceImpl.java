package br.com.concepting.framework.security.service;

import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.ExpressionModel;
import br.com.concepting.framework.model.SystemModuleModel;
import br.com.concepting.framework.model.SystemSessionModel;
import br.com.concepting.framework.model.exceptions.ItemAlreadyExistsException;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.network.util.NetworkUtil;
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
import br.com.concepting.framework.security.model.LoginParametersModel;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.security.model.UserModel;
import br.com.concepting.framework.security.service.interfaces.LoginSessionService;
import br.com.concepting.framework.security.util.CryptoDigest;
import br.com.concepting.framework.service.BaseRemoteService;
import br.com.concepting.framework.util.DateTimeUtil;
import br.com.concepting.framework.util.helpers.DateTime;
import br.com.concepting.framework.util.types.DateFieldType;

/**
 * Classe contendo os métodos que implementam as regras de negócio que envolvem o modelo de dados 
 * 'LoginSessionModel'.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class LoginSessionServiceImpl extends BaseRemoteService implements LoginSessionService{
    private static final long serialVersionUID = -2559276809638316352L;

    /**
     * @see br.com.concepting.framework.security.service.interfaces.LoginSessionService#logIn(br.com.concepting.framework.security.model.LoginSessionModel)
     */
	public <L extends LoginSessionModel> void logIn(L loginSession) throws UserNotFoundException, UserBlockedException, InvalidPasswordException, InternalErrorException, UserAlreadyLoggedInException, PermissionDeniedException, ExpiredPasswordException, PasswordWillExpireException{
	    if(loginSession != null){
	        loginSession.setId(null);
	        
	        UserModel user = loginSession.getUser();
	        
            user.setId(null);
            user.setActive(null);

            IDAO userDao = getPersistence(user.getClass());

            try{
	            Collection<UserModel> users = userDao.search(user);
            
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
                
                if(user.getSystem())
                    throw new UserNotFoundException();
                    
                if(!password.equals(user.getPassword()))
                	throw new InvalidPasswordException();
                        
                if(!user.isActive())
                	throw new UserBlockedException();
            
                user = userDao.loadReference(user, "loginParameters");
                
                SystemSessionModel   systemSession      = loginSession.getSystemSession();
                String               ip                 = systemSession.getIp();
                LoginParametersModel loginParameters    = user.getLoginParameters();
                Boolean              expiredPassword    = false;
                Boolean              passwordWillExpire = false;
                Integer              daysUntilExpire    = 0;
                Integer              hoursUntilExpire   = 0;
                Integer              minutesUntilExpire = 0;
                Integer              secondsUntilExpire = 0;
                
                if(loginParameters != null){
                    IDAO loginParametersDao = getPersistence(loginParameters.getClass());
                    
                    loginParameters = loginParametersDao.loadReference(loginParameters, "accessLists");
                    
                    Collection<AccessListModel> accessLists = loginParameters.getAccessLists();
                    
                    if(accessLists != null && accessLists.size() > 0){
                        IDAO                        accessListDao = getPersistence(accessLists.iterator().next().getClass());
                        Collection<ExpressionModel> expressions   = null;
                        Boolean                     found         = false;
                        
                        for(AccessListModel accessList : accessLists){
                            accessList  = accessListDao.loadReference(accessList, "hosts");
                            expressions = accessList.getExpressions();
                            found       = true;
                            
                            if(!accessList.getWhitelist()){
                                if(expressions != null && expressions.size() > 0){
                                    for(ExpressionModel expression : expressions){
                                        if(NetworkUtil.isIpMatches(ip, expression.getValue()))
                                            throw new PermissionDeniedException();
                                    }
                                }
                            }
                            else{
                                if(expressions != null && expressions.size() > 0){
                                    for(ExpressionModel expression : expressions){
                                        if(NetworkUtil.isIpMatches(ip, expression.getValue())){
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
                
                    DateTime expirePasswordDate     = user.getExpirePasswordDate();
                    Calendar calendar               = Calendar.getInstance();
                    DateTime now                    = new DateTime(calendar.getTimeInMillis());
                    Integer  expirePasswordInterval = loginParameters.getExpirePasswordInterval();
                    Integer  changePasswordInterval = loginParameters.getChangePasswordInterval();
                
                    if(expirePasswordInterval != null && expirePasswordInterval > 0){
                        if(expirePasswordDate == null){
                            expirePasswordDate = DateTimeUtil.add(now, expirePasswordInterval, DateFieldType.DAY);
                    
                            user.setExpirePasswordDate(expirePasswordDate);
                            
                            loginSession.setUser(user);
                        }
                        
                        daysUntilExpire    = DateTimeUtil.diff(expirePasswordDate, now, DateFieldType.DAY).intValue();
                        hoursUntilExpire   = DateTimeUtil.diff(expirePasswordDate, now, DateFieldType.HOURS).intValue() - (daysUntilExpire * 24);
                        minutesUntilExpire = DateTimeUtil.diff(expirePasswordDate, now, DateFieldType.MINUTES).intValue() - (hoursUntilExpire * 60);
                        
                        if(minutesUntilExpire == 0)
                            secondsUntilExpire = DateTimeUtil.diff(expirePasswordDate, now, DateFieldType.MINUTES).intValue();
                        else
                            secondsUntilExpire = 60 - calendar.get(Calendar.SECOND);

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
        
                loginSession.setCreateDate(new DateTime());
                loginSession.setUser(user);

                if(!user.isSuperUser()){
                    SystemModuleModel systemModule = loginSession.getSystemModule();
            
                    if(!user.hasPermission(systemModule))
                        throw new PermissionDeniedException();
                }
    
                try{
                    loginSessionDao.save(loginSession);
                }
                catch(ItemAlreadyExistsException e){
                    throw new UserAlreadyLoggedInException();
                }
                
                user.setLastUpdateDate(new DateTime());

                if(expiredPassword){
                    user.setChangePassword(true);
                    user.setNewPassword("");
                    
                    userDao.update(user);
            
                    throw new ExpiredPasswordException();
                }
                
                userDao.update(user);
        
                if(passwordWillExpire)
                    throw new PasswordWillExpireException(daysUntilExpire, hoursUntilExpire, minutesUntilExpire, secondsUntilExpire);
            }
            finally{
                loginSession.setUser(user);
            }
        }
	}

	/**
	 * @see br.com.concepting.framework.security.service.interfaces.LoginSessionService#changePassword(br.com.concepting.framework.security.model.LoginSessionModel)
	 */
    public <L extends LoginSessionModel> void changePassword(L loginSession) throws PasswordsNotMatchException, InternalErrorException{
        if(loginSession != null && loginSession.getId() != null && loginSession.getId() > 0){
            UserModel user            = loginSession.getUser();
            String    newPassword     = user.getNewPassword();
            String    confirmPassword = user.getConfirmPassword();
            
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
        
                DateTime lastUpdateDate = new DateTime();
        
                user.setLastUpdateDate(lastUpdateDate);
        
                LoginParametersModel loginParameters = user.getLoginParameters();
            
                if(loginParameters != null){
                    Integer expirePasswordInterval = loginParameters.getExpirePasswordInterval();
                    
                    if(expirePasswordInterval != null){
                        DateTime expirePasswordDate = DateTimeUtil.add(lastUpdateDate, expirePasswordInterval, DateFieldType.DAY);
                        
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
			loginSession.getUser().setId(null);
			loginSession.getUser().setChangePassword(false);
			loginSession.getUser().setPassword("");
			
			ModelInfo modelInfo = ModelUtil.getModelInfo(loginSession.getUser().getClass());
			
			if(modelInfo != null){
			    List<PropertyInfo> propertiesInfo = modelInfo.getPropertiesInfo();
			    
			    if(propertiesInfo != null && propertiesInfo.size() > 0){
			        for(PropertyInfo propertyInfo : propertiesInfo){
			            if(propertyInfo.isForSearch() || propertyInfo.isModel() || propertyInfo.hasModel()){
			                try{
			                    PropertyUtil.setProperty(loginSession.getUser(), propertyInfo.getId(), null);
			                }
			                catch(Throwable e){
			                }
			            }
			        }
			    }
			}
		}
	}

	/**
	 * @see br.com.concepting.framework.security.service.interfaces.LoginSessionService#sendForgottenPassword(br.com.concepting.framework.security.model.LoginSessionModel)
	 */
    public <L extends LoginSessionModel> void sendForgottenPassword(L loginSession) throws UserNotFoundException, UserBlockedException, InternalErrorException, RemoteException{
        if(loginSession != null){
            UserModel       user  = loginSession.getUser();
            IDAO            dao   = getPersistence(user.getClass());
            List<UserModel> users = dao.search(user);
            
            if(users == null || users.size() == 0)
                throw new UserNotFoundException();
            
            user = users.iterator().next();
            
            if(!user.isActive())
                throw new UserBlockedException();
            
            sendForgottenPasswordMessage(user);
        }
    }

    /**
     * Envia mensagem contendo a nova senha do usuário.
     * 
     * @param user Instância do modelo de dados contendo as informações do usuário.
     * @throws InternalErrorException
     * @throws RemoteException
     */
    protected abstract <L extends LoginSessionModel, U extends UserModel> void sendForgottenPasswordMessage(U user) throws InternalErrorException, RemoteException;
}