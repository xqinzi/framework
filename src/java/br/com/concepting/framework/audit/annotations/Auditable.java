package br.com.concepting.framework.audit.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Classe que define a anotação para auditoria.
 * Esta anotação pode ser utilizada nas definições de classes e em métodos.
 * 
 * @author fvilarinho
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Auditable{
	/**
	 * Define o identificador das configurações de auditoria desejada.
	 * 
	 * @return String contendo o identificador das configurações.
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
	 * Define o identificador da regra de negócio a ser auditada.
	 * Geralmente utilizado para nomear um método.
	 * 
	 * @return String contendo o identificador da regra de negócio.
	 */
	String businessId() default "";
	
	/**
	 * Define a lista dos identificadores dos argumentos que devem ser auditados.
	 * 
	 * @return Array contendo a lista de argumentos desejados.
	 */
	String[] businessArgumentsIds() default {};
}