package br.com.concepting.framework.persistence.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * Anotação que define a query (HQL ou SQL) vinculada a um método da classe de persistência.
 * 
 * @author fvilarinho
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Query{
    /**
     * Indica se a linguagem é nativa do banco de dados.
     * 
     * @return True/False.
     */
    boolean isNative() default false;
    
	/**
	 * Define a query vinculada.
	 * 
	 * @return String contendo a query
	 */
	String value() default "";
}