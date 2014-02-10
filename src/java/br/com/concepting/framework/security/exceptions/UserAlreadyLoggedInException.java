package br.com.concepting.framework.security.exceptions;

import br.com.concepting.framework.exceptions.ExpectedErrorException;

/**
 * Classe que define a exceção quando o usuário já está logado.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class UserAlreadyLoggedInException extends ExpectedErrorException{
    private static final long serialVersionUID = 7385854870847137681L;
}