package br.com.concepting.framework.service.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * Classe que define as anotações para uma transação das classes de serviço.
 * 
 * @author fvilarinho
 * @since 4.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ServiceTransaction{
    /**
     * Anotação que define o timeout da transação.
     * 
     * @return Valor inteiro contendo o timeout.
     */
    int transactionTimeout() default 60;
    
    /**
     * Define as exceções que farão com que seja feito o rollback.
     *  
     * @return Array contendo as exceções.
     */
    Class[] rollbackFor() default {Throwable.class, Exception.class};
}
