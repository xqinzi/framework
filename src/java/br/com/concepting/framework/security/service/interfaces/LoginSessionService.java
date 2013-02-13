package br.com.concepting.framework.security.service.interfaces;

import java.rmi.RemoteException;

import br.com.concepting.framework.audit.annotations.Auditable;
import br.com.concepting.framework.model.SystemModuleModel;
import br.com.concepting.framework.model.SystemSessionModel;
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
import br.com.concepting.framework.service.annotations.Service;
import br.com.concepting.framework.service.interfaces.IService;
import br.com.concepting.framework.service.types.ServiceTransactionType;
import br.com.concepting.framework.util.exceptions.InternalErrorException;

/**
 * Interface contendo as assinaturas dos métodos que implementam as regras de negócio que envolvem 
 * o modelo de dados 'LoginSessionModel'.
 * 
 * @author fvilarinho
 * @since 1.0
 */
@Auditable(entityId="loginSession")
public interface LoginSessionService extends IService{
    @Auditable(businessId="sendForgottenPassword")
    @Service(transactionType=ServiceTransactionType.READ_WRITE)
    public <L extends LoginSessionModel, U extends UserModel> void sendForgottenPassword(L loginSession) throws UserNotFoundException, UserBlockedException, InternalErrorException, RemoteException;
    
    /**
     * Efetua o login do usuário.
     * 
     * @param loginSession Instância do modelo de dados da sessão de login do usuário.
     * @throws UserAlreadyLoggedInException
     * @throws UserNotFoundException
     * @throws UserBlockedException
     * @throws ExpiredPasswordException
     * @throws PasswordWillExpireException
     * @throws InvalidPasswordException
     * @throws PermissionDeniedException
     * @throws InternalErrorException
     * @throws RemoteException
     */
	@Auditable(businessId="logIn")
    @Service(transactionType=ServiceTransactionType.READ_WRITE)
	public <L extends LoginSessionModel, U extends UserModel, S extends SystemModuleModel, SS extends SystemSessionModel, LP extends LoginParametersModel, A extends AccessListModel, H extends HostModel> void logIn(L loginSession) throws UserAlreadyLoggedInException, UserNotFoundException, UserBlockedException, ExpiredPasswordException, PasswordWillExpireException, InvalidPasswordException, PermissionDeniedException, InternalErrorException, RemoteException;
	
	/**
	 * Efetua a mudança de senha do usuário.
	 * 
     * @param loginSession Instância do modelo de dados da sessão de login do usuário.
	 * @throws PasswordsNotMatchException
	 * @throws InternalErrorException
	 * @throws RemoteException
	 */
	@Auditable(businessId="changePassword")
    @Service(transactionType=ServiceTransactionType.READ_WRITE)
	public <L extends LoginSessionModel, U extends UserModel> void changePassword(L loginSession) throws PasswordsNotMatchException, InternalErrorException, RemoteException;

	/**
	 * Efetua o logout do usuário.
	 * 
     * @param loginSession Instância do modelo de dados da sessão de login do usuário.
	 * @throws InternalErrorException
	 * @throws RemoteException
	 */
    @Auditable(businessId="logOut")
    @Service(transactionType=ServiceTransactionType.READ_WRITE)
    public <L extends LoginSessionModel> void logOut(L loginSession) throws InternalErrorException, RemoteException;
}