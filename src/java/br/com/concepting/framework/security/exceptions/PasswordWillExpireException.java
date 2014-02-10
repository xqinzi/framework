package br.com.concepting.framework.security.exceptions;

import br.com.concepting.framework.exceptions.ExpectedWarningException;

/**
 * Classe que define a exce��o quando a senha de um usu�rio ir� expirar.
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
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param daysUntilExpiration Valor inteiro contendo o n�mero de dias at� a expira��o.
     * @param hoursUntilExpiration Valor inteiro contendo o n�mero de horas at� a expira��o.
     * @param minutesUntilExpiration Valor inteiro contendo o n�mero de minutos at� a expira��o.
     * @param secondsUntilExpiration Valor inteiro contendo o n�mero de segundos at� a expira��o.
     */
    public PasswordWillExpireException(Integer daysUntilExpiration, Integer hoursUntilExpiration, Integer minutesUntilExpiration, Integer secondsUntilExpiration){
        super();
        
        setDaysUntilExpiration(daysUntilExpiration);
        setHoursUntilExpiration(hoursUntilExpiration);
        setMinutesUntilExpiration(minutesUntilExpiration);
        setSecondsUntilExpiration(secondsUntilExpiration);
    }
    
    /**
     * Retorna o n�mero de horas at� a expira��o da senha.
     * 
     * @return Valor inteiro contendo o n�mero de horas at� a expira��o.
     */
    public Integer getHoursUntilExpiration(){
        return hoursUntilExpiration;
    }

    /**
     * Define o n�mero de horas at� a expira��o da senha.
     * 
     * @param hoursUntilExpiration Valor inteiro contendo o n�mero de horas at� a expira��o.
     */
    public void setHoursUntilExpiration(Integer hoursUntilExpiration){
        this.hoursUntilExpiration = hoursUntilExpiration;
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

    /**
     * Retorna o n�mero de minutos at� a expira��o da senha.
     * 
     * @return Valor inteiro contendo o n�mero de minutos at� a expira��o.
     */
    public Integer getMinutesUntilExpiration(){
        return minutesUntilExpiration;
    }

    /**
     * Define o n�mero de minutos at� a expira��o da senha.
     * 
     * @param minutesUntilExpiration Valor inteiro contendo o n�mero de minutos at� a expira��o.
     */
    public void setMinutesUntilExpiration(Integer minutesUntilExpiration){
        this.minutesUntilExpiration = minutesUntilExpiration;
    }

    /**
     * Retorna o n�mero de segundos at� a expira��o da senha.
     * 
     * @return Valor inteiro contendo o n�mero de segundos at� a expira��o.
     */
    public Integer getSecondsUntilExpiration(){
        return secondsUntilExpiration;
    }

    /**
     * Define o n�mero de segundos at� a expira��o da senha.
     * 
     * @param secondsUntilExpiration Valor inteiro contendo o n�mero de segundos at� a expira��o.
     */
    public void setSecondsUntilExpiration(Integer secondsUntilExpiration){
        this.secondsUntilExpiration = secondsUntilExpiration;
    }
}