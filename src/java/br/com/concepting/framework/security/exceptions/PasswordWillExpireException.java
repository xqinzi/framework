package br.com.concepting.framework.security.exceptions;

import br.com.concepting.framework.exceptions.ExpectedWarningException;

/**
 * Classe que define a exce��o quando a senha de um usu�rio ir� expirar.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class PasswordWillExpireException extends ExpectedWarningException{
    private Integer daysUntilExpiration = 0;
    
    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param daysUntilExpiration Valor inteiro contendo o n�mero de dias at� a expira��o.
     */
    public PasswordWillExpireException(Integer daysUntilExpiration){
        super();
        
        setDaysUntilExpiration(daysUntilExpiration);
    }

    /**
     * Retorna o n�mero de dias at� a expira��o da senha.
     * 
     * @return Valor inteiro contendo o n�mero de dias at� a expira��o.
     */
    public Integer getDaysUntilExpiration(){
        return daysUntilExpiration;
    }

    /**
     * Define o n�mero de dias at� a expira��o da senha.
     * 
     * @param daysUntilExpiration Valor inteiro contendo o n�mero de dias at� a expira��o.
     */
    public void setDaysUntilExpiration(Integer daysUntilExpiration){
        this.daysUntilExpiration = daysUntilExpiration;
    }
}