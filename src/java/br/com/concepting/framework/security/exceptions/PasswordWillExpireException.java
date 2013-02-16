package br.com.concepting.framework.security.exceptions;

import br.com.concepting.framework.exceptions.ExpectedWarningException;

/**
 * Classe que define a exceção quando a senha de um usuário irá expirar.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class PasswordWillExpireException extends ExpectedWarningException{
    private Integer daysUntilExpiration = 0;
    
    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param daysUntilExpiration Valor inteiro contendo o número de dias até a expiração.
     */
    public PasswordWillExpireException(Integer daysUntilExpiration){
        super();
        
        setDaysUntilExpiration(daysUntilExpiration);
    }

    /**
     * Retorna o número de dias até a expiração da senha.
     * 
     * @return Valor inteiro contendo o número de dias até a expiração.
     */
    public Integer getDaysUntilExpiration(){
        return daysUntilExpiration;
    }

    /**
     * Define o número de dias até a expiração da senha.
     * 
     * @param daysUntilExpiration Valor inteiro contendo o número de dias até a expiração.
     */
    public void setDaysUntilExpiration(Integer daysUntilExpiration){
        this.daysUntilExpiration = daysUntilExpiration;
    }
}