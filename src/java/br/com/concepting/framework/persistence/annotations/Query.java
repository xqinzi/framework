package br.com.concepting.framework.persistence.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * Anota��o que define a query (HQL ou SQL) vinculada a um m�todo da classe de persist�ncia.
 * 
 * @author fvilarinho
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Query{
    /**
     * Indica se a linguagem � nativa do banco de dados.
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