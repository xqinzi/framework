package br.com.concepting.framework.service.annotations;

/** 
 * Classe que define as anotações para uma transação das classes de serviço.
 * 
 * @author fvilarinho
 * @since 4.0
 */
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
