package br.com.concepting.framework.security.service.interfaces;

import java.rmi.RemoteException;

import br.com.concepting.framework.audit.annotations.Auditable;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.security.exceptions.ExpiredPasswordException;
import br.com.concepting.framework.security.exceptions.InvalidPasswordException;
import br.com.concepting.framework.security.exceptions.PasswordWillExpireException;
import br.com.concepting.framework.security.exceptions.PasswordsNotMatchException;
import br.com.concepting.framework.security.exceptions.PermissionDeniedException;
import br.com.concepting.framework.security.exceptions.UserAlreadyLoggedInException;
import br.com.concepting.framework.security.exceptions.UserBlockedException;
import br.com.concepting.framework.security.exceptions.UserNotFoundException;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.service.annotations.ServiceTransaction;
import br.com.concepting.framework.service.interfaces.IService;

/**
 * Interface contendo as assinaturas dos métodos que implementam as regras de negócio que envolvem 
 * o modelo de dados 'LoginSessionModel'.
 * 
 * @author fvilarinho
 * @since 1.0
 */
@Auditable
public interface LoginSessionService extends IService{
    @Auditable
    @ServiceTransaction
    public <L extends LoginSessionModel> void sendForgottenPassword(L loginSession) throws UserNotFoundException, UserBlockedException, InternalErrorException, RemoteException;
    
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
	@Auditable
    @ServiceTransaction 
	public <L extends LoginSessionModel> void logIn(L loginSession) throws UserAlreadyLoggedInException, UserNotFoundException, UserBlockedException, ExpiredPasswordException, PasswordWillExpireException, InvalidPasswordException, PermissionDeniedException, InternalErrorException, RemoteException;
	
	/**
	 * Efetua a mudança de senha do usuário.
	 * 
     * @param loginSession Instância do modelo de dados da sessão de login do usuário.
	 * @throws PasswordsNotMatchException
	 * @throws InternalErrorException
	 * @throws RemoteException
	 */
	@Auditable
    @ServiceTransaction
	public <L extends LoginSessionModel> void changePassword(L loginSession) throws PasswordsNotMatchException, InternalErrorException, RemoteException;

	/**
	 * Efetua o logout do usuário.
	 * 
     * @param loginSession Instância do modelo de dados da sessão de login do usuário.
	 * @throws InternalErrorException
	 * @throws RemoteException
	 */
    @Auditable
    @ServiceTransaction
    public <L extends LoginSessionModel> void logOut(L loginSession) throws InternalErrorException, RemoteException;
}