package br.com.concepting.framework.audit.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Classe que define a anota��o para auditoria.
 * Esta anota��o pode ser utilizada nas defini��es de classes e em m�todos.
 * 
 * @author fvilarinho
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Auditable{
	/**
	 * Define o identificador das configura��es de auditoria desejada.
	 * 
	 * @return String contendo o identificador das configura��es.
	 */
	String auditorResourceId() default "";

	/**
	 * Define o identificador da entidade a ser auditada.
	 * Geralmente utilizado para nomear uma classe.
	 * 
	 * @return String contendo o identificador da entidade.
	 */
	String entityId() default "";

	/**
	 * Define o identificador da regra de neg�cio a ser auditada.
	 * Geralmente utilizado para nomear um m�todo.
	 * 
	 * @return String contendo o identificador da regra de neg�cio.
	 */
	String businessId() default "";
	
	/**
	 * Define a lista dos identificadores dos argumentos que devem ser auditados.
	 * 
	 * @return Array contendo a lista de argumentos desejados.
	 */
	String[] businessArgumentsIds() default {};
}