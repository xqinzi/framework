package br.com.concepting.framework.model.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.concepting.framework.controller.form.util.ActionFormValidator;
import br.com.concepting.framework.service.types.ServiceType;

/**
 * Classe que define as anotações de um modelo de dados.
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
	 * Define o identificador do template ser considerado no processamento da anotação. 
	 *
	 * @return String contendo o identificador do template.
	 */
	String templateId() default "default";

	/**
	 * Define o identificador do repositório o qual o modelo de dados deverá
	 * ser persistido.
	 * 
	 * @return String contendo o identificador do repositório.
	 */
	String mappedRepositoryId() default "";

	/**
	 * Define as anotações das propriedades do modelo de dados.
	 * 
	 * @return Array contendo as anotações das propriedades.
	 */
	Property[] mappedProperties() default @Property;

    /**
     * Define o identificador das configurações do repositório de persistência a
     * ser utilizado.
     * 
     * @return String contendo o identificador das configurações de persistência.
     */
    String persistenceResourceId() default "";

    /**
	 * Define a classe responsável pela validação da propriedades do modelo de
	 * dados.
	 * 
	 * @return Classe responsável pela validação do modelo de dados.
	 */
	Class<? extends ActionFormValidator> actionFormValidatorClass() default ActionFormValidator.class;

	/**
	 * Define a máscara de descrição do modelo de dados.
	 * Essa máscara poderá conter os valores de propriedades do modelo de dados, que 
	 * serão substituídos dinamicamente.
	 * Para colocar uma propriedade na máscara, basta seguir o padrão abaixo:
	 * 
	 * {@code #{<nome-da-propriedade-do-modelo-de-dados>}}
	 * 
	 * Ex:
	 * {@code #{id} - #{nome}}
	 * 
	 * O exemplo acima irá definir uma string com o conteúdo '1 - Teste' caso a propriedade 
	 * id possuir valor 1 e a propriedade nome possuir o valor Teste.
	 *
	 * @return String contendo a máscara de descrição do modelo de dados.
	 */
	String descriptionPattern() default "";
	
	/**
	 * Indica se as classes de persistência devem ser geradas.
	 * Por será gerado somente quando houver mapeamento no repositório, porém é possível forçar 
	 * a geração em casos onde eu não tenho um repositório definido.
	 *
	 * @return True/False.
	 */
	boolean generatePersistence() default false;
	
	/**
	 * Indica se as classes de serviço devem ser geradas.
	 *
	 * @return True/False.
	 */
	boolean generateService() default false;
	
	/**
	 * Indica o tipo de serviço que deve ser gerado.
	 * 
	 * @return Constante que define o tipo de serviço.
	 */
	ServiceType serviceType() default ServiceType.CLASS;
}