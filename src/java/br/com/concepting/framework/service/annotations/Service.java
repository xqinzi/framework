package br.com.concepting.framework.service.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.concepting.framework.service.types.ServiceTransactionType;
import br.com.concepting.framework.service.types.ServiceType;
import br.com.concepting.framework.service.util.ServiceInterceptor;
import br.com.concepting.framework.web.types.ScopeType;
 
/** 
 * Classe que define as anotações para uma classe de serviço.
 * 
 * @author fvilarinho
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Service{
	/**
	 * Anotação que define o tipo de serviço.
	 * 
	 * @return Constante que define o tipo de serviço.
	 */
	ServiceType type() default ServiceType.CLASS;
	
	/**
	 * Anotação que define o escopo do serviço.
	 * 
	 * @return Constante que define o escopo do serviço.
	 */
	ScopeType scope() default ScopeType.APPLICATION;
	
	/**
	 * Anotação que define o identificador para localização da classe de serviço.
	 * Somente utilizado quando o tipo de serviço for remoto.
	 * 
	 * @return String contendo o identificador para localização da classe de serviço.
	 */
	String name() default "";

	/**
	 * Anotação que define o timeout para conexão com o serviço.
	 *
	 * @return Valor inteiro contendo o timeout de conexão.
	 */
	int timeout() default 0;

	/**
	 * Anotação que define o identificador das configurações do contexto.
	 * Somente utilizado quando o tipo de serviço for remoto.
	 * 
	 * @return String contendo o identificador das configurações do contexto.
	 */
	String contextResourceId() default "";

    /**
     * Anotação que define a classe que intercepta a execução dos métodos.
     * 
     * @return Classe interceptadora.
     */
    Class interceptor() default ServiceInterceptor.class;
    
    /**
     * Anotação que define o tipo da transação.
     * 
     * @return Constante que define o tipo da transação.
     */
    ServiceTransactionType transactionType() default ServiceTransactionType.NONE;
    
    /**
     * Define as exceções que farão com que seja feito o rollback.
     *  
     * @return Array contendo as exceções.
     */
    Class[] rollbackFor() default {Throwable.class, Exception.class};
}