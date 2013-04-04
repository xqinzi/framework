package br.com.concepting.framework.service.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * Classe que define as anota��es para uma transa��o das classes de servi�o.
 * 
 * @author fvilarinho
 * @since 4.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ServiceTransaction{
    /**
     * Anota��o que define o timeout da transa��o.
     * 
     * @return Valor inteiro contendo o timeout.
     */
    int transactionTimeout() default 60;
    
    /**
     * Define as exce��es que far�o com que seja feito o rollback.
     *  
     * @return Array contendo as exce��es.
     */
    Class[] rollbackFor() default {Throwable.class, Exception.class};
}
