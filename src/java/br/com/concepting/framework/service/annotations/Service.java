package br.com.concepting.framework.service.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.concepting.framework.service.types.ServiceType;
import br.com.concepting.framework.service.util.ServiceInterceptor;
import br.com.concepting.framework.util.types.ScopeType;
 
/** 
 * Classe que define as anota��es para uma classe de servi�o.
 * 
 * @author fvilarinho
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Service{
	/**
	 * Anota��o que define o tipo de servi�o.
	 * 
	 * @return Constante que define o tipo de servi�o.
	 */
	ServiceType type() default ServiceType.CLASS;
	
	/**
	 * Anota��o que define o escopo do servi�o.
	 * 
	 * @return Constante que define o escopo do servi�o.
	 */
	ScopeType scope() default ScopeType.APPLICATION;
	
	/**
	 * Anota��o que define o identificador para localiza��o da classe de servi�o.
	 * Somente utilizado quando o tipo de servi�o for remoto.
	 * 
	 * @return String contendo o identificador para localiza��o da classe de servi�o.
	 */
	String name() default "";

	/**
	 * Anota��o que define o identificador das configura��es do contexto.
	 * Somente utilizado quando o tipo de servi�o for remoto.
	 * 
	 * @return String contendo o identificador das configura��es do contexto.
	 */
	String contextResourceId() default "";

    /**
     * Anota��o que define a classe que intercepta a execu��o dos m�todos.
     * 
     * @return Classe interceptadora.
     */
    Class interceptor() default ServiceInterceptor.class;
}