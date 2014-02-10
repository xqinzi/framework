package br.com.concepting.framework.security.exceptions;

import br.com.concepting.framework.exceptions.ExpectedErrorException;

/**
 * Classe que define a exce��o quando as senhas digitadas n�o conferem.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class PasswordsNotMatchException extends ExpectedErrorException{
    private static final long serialVersionUID = 7732613785655203712L;
}