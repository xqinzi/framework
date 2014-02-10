package br.com.concepting.framework.model.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.concepting.framework.controller.form.util.ActionFormValidator;
import br.com.concepting.framework.service.types.ServiceType;

/**
 * Classe que define as anota��es de um modelo de dados.
 * 
 * @author fvilarinho
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Model{
	/**
	 * Define o identificador do caso de uso vinculado ao modelo de dados.
	 * 
	 * @return String contendo o identificador do caso de uso.
	 */
	String useCase() default "";
	
	/**
	 * Define o identificador do template ser considerado no processamento da anota��o. 
	 *
	 * @return String contendo o identificador do template.
	 */
	String templateId() default "default";

	/**
	 * Define o identificador do reposit�rio o qual o modelo de dados dever�
	 * ser persistido.
	 * 
	 * @return String contendo o identificador do reposit�rio.
	 */
	String mappedRepositoryId() default "";

	/**
	 * Define as anota��es das propriedades do modelo de dados.
	 * 
	 * @return Array contendo as anota��es das propriedades.
	 */
	Property[] mappedProperties() default @Property;

    /**
     * Define o identificador das configura��es do reposit�rio de persist�ncia a
     * ser utilizado.
     * 
     * @return String contendo o identificador das configura��es de persist�ncia.
     */
    String persistenceResourceId() default "";

    /**
	 * Define a classe respons�vel pela valida��o da propriedades do modelo de
	 * dados.
	 * 
	 * @return Classe respons�vel pela valida��o do modelo de dados.
	 */
	Class<? extends ActionFormValidator> actionFormValidatorClass() default ActionFormValidator.class;

	/**
	 * Define a m�scara de descri��o do modelo de dados.
	 * Essa m�scara poder� conter os valores de propriedades do modelo de dados, que 
	 * ser�o substitu�dos dinamicamente.
	 * Para colocar uma propriedade na m�scara, basta seguir o padr�o abaixo:
	 * 
	 * {@code #{<nome-da-propriedade-do-modelo-de-dados>}}
	 * 
	 * Ex:
	 * {@code #{id} - #{nome}}
	 * 
	 * O exemplo acima ir� definir uma string com o conte�do '1 - Teste' caso a propriedade 
	 * id possuir valor 1 e a propriedade nome possuir o valor Teste.
	 *
	 * @return String contendo a m�scara de descri��o do modelo de dados.
	 */
	String descriptionPattern() default "";
	
	/**
	 * Indica se as classes de persist�ncia devem ser geradas.
	 * Por ser� gerado somente quando houver mapeamento no reposit�rio, por�m � poss�vel for�ar 
	 * a gera��o em casos onde eu n�o tenho um reposit�rio definido.
	 *
	 * @return True/False.
	 */
	boolean generatePersistence() default false;
	
	/**
	 * Indica se as classes de servi�o devem ser geradas.
	 *
	 * @return True/False.
	 */
	boolean generateService() default false;
	
	/**
	 * Indica o tipo de servi�o que deve ser gerado.
	 * 
	 * @return Constante que define o tipo de servi�o.
	 */
	ServiceType serviceType() default ServiceType.CLASS;
}