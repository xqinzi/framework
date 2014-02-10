package br.com.concepting.framework.security.exceptions;

import br.com.concepting.framework.exceptions.ExpectedWarningException;

/**
 * Classe que define a exceção quando a senha de um usuário irá expirar.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class PasswordWillExpireException extends ExpectedWarningException{
    private static final long serialVersionUID = 5978487541406636762L;
    
    private Integer daysUntilExpiration    = 0;
    private Integer hoursUntilExpiration   = 0;
    private Integer minutesUntilExpiration = 0;
    private Integer secondsUntilExpiration = 0;
    
    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param daysUntilExpiration Valor inteiro contendo o número de dias até a expiração.
     * @param hoursUntilExpiration Valor inteiro contendo o número de horas até a expiração.
     * @param minutesUntilExpiration Valor inteiro contendo o número de minutos até a expiração.
     * @param secondsUntilExpiration Valor inteiro contendo o número de segundos até a expiração.
     */
    public PasswordWillExpireException(Integer daysUntilExpiration, Integer hoursUntilExpiration, Integer minutesUntilExpiration, Integer secondsUntilExpiration){
        super();
        
        setDaysUntilExpiration(daysUntilExpiration);
        setHoursUntilExpiration(hoursUntilExpiration);
        setMinutesUntilExpiration(minutesUntilExpiration);
        setSecondsUntilExpiration(secondsUntilExpiration);
    }
    
    /**
     * Retorna o número de horas até a expiração da senha.
     * 
     * @return Valor inteiro contendo o número de horas até a expiração.
     */
    public Integer getHoursUntilExpiration(){
        return hoursUntilExpiration;
    }

    /**
     * Define o número de horas até a expiração da senha.
     * 
     * @param hoursUntilExpiration Valor inteiro contendo o número de horas até a expiração.
     */
    public void setHoursUntilExpiration(Integer hoursUntilExpiration){
        this.hoursUntilExpiration = hoursUntilExpiration;
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

    /**
     * Retorna o número de minutos até a expiração da senha.
     * 
     * @return Valor inteiro contendo o número de minutos até a expiração.
     */
    public Integer getMinutesUntilExpiration(){
        return minutesUntilExpiration;
    }

    /**
     * Define o número de minutos até a expiração da senha.
     * 
     * @param minutesUntilExpiration Valor inteiro contendo o número de minutos até a expiração.
     */
    public void setMinutesUntilExpiration(Integer minutesUntilExpiration){
        this.minutesUntilExpiration = minutesUntilExpiration;
    }

    /**
     * Retorna o número de segundos até a expiração da senha.
     * 
     * @return Valor inteiro contendo o número de segundos até a expiração.
     */
    public Integer getSecondsUntilExpiration(){
        return secondsUntilExpiration;
    }

    /**
     * Define o número de segundos até a expiração da senha.
     * 
     * @param secondsUntilExpiration Valor inteiro contendo o número de segundos até a expiração.
     */
    public void setSecondsUntilExpiration(Integer secondsUntilExpiration){
        this.secondsUntilExpiration = secondsUntilExpiration;
    }
}