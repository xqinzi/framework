package br.com.concepting.framework.model.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.concepting.framework.model.types.ConditionType;
import br.com.concepting.framework.model.types.ContextSearchType;
import br.com.concepting.framework.model.types.ValidationType;
import br.com.concepting.framework.persistence.types.RelationJoinType;
import br.com.concepting.framework.persistence.types.RelationType;
import br.com.concepting.framework.util.types.FormulaType;
import br.com.concepting.framework.util.types.SortOrderType;

/**
 * Classe que define a anotação para uma propriedade em um modelo de dados.
 * 
 * @author fvilarinho
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Property{
	/**
	 * Define que a propriedade é chave de identificação de um modelo de dados.
	 * 
	 * @return True/False.
	 */
	boolean isIdentity() default false;
	
	/**
	 * Define que a chave de identificação do modelo de dados deverá ser gerada 
	 * automaticamente.
	 *
	 * @return True/False.
	 */
	boolean autoGenerateIdentity() default true;
	
	/**
	 * Define que a propriedade é única para identificação de um modelo de dados.
	 *
	 * @return True/False.
	 */
	boolean isUnique() default false;
	
	/**
	 * Define o identificador do índice ou chave vinculada à propriedade.
	 * 
	 * @return String contendo o identificador do índice ou chave.
	 */
	String keyId() default "";
	
	/**
	 * Define o identificador da chave do relacionamento.
	 * 
	 * @return String contendo o identificador da chave.
	 */
	String foreignKeyId() default "";
	
	/**
	 * Indica se o relacionamento deve ser validado quando o mesmo não possuir integridade 
	 * relacional.
	 * 
	 * @return True/False.
	 */
	boolean constrained() default true;
	
	/**
	 * Indica que a propriedade deve ou não conter valores nulos.
	 * 
	 * @return True/False.
	 */
	boolean nullable() default true;

	/**
	 * Define que a propriedade será considerada para pesquisa.
	 * 
	 * @return True/False.
	 */
	boolean isForSearch() default false;

	/**
	 * Define a condição de pesquisa da propriedade.
	 * Somente será considerada quando a propriedade for de pesquisa.
	 * 
	 * @return Instância contendo a condição de pesquisa.
	 */
	ConditionType searchCondition() default ConditionType.EQUAL;

	/**
	 * Define os tipos de pesquisa por contexto.
	 * Somente será considerada quando a propriedade searchCondition for do tipo CONTEXT.
	 * 
	 * @return Instância contendo os tipos de pesquisa por contexto.
	 */
	ContextSearchType contextSearchType() default ContextSearchType.BOTH;

	/**
	 * Define se a pesquisa será sensível a letras maiúsculas/minúsculas.
	 * 
	 * @return True/False.
	 */
	boolean caseSensitiveSearch() default true;

	/**
	 * Define a porcentagem de acertividade a ser utilizada quando a condição de pesquisa
	 * fonética.
	 * 
	 * @return Valor em ponto flutuante contendo a porcentagem de acertividade.
	 */
	double phoneticAccuracy() default 50.0;

	/**
	 * Define que a propriedade será auditada.
	 * 
	 * @return True/False.
	 */
	boolean isAuditable() default false;

	/**
	 * Define que o separador de milhar deve ser utilizado na formatação.
	 * 
	 * @return True/False.
	 */
	boolean useGroupSeparator() default false;

	/**
	 * Define o identificador da propriedade a ser populada no repositório de persistência.
	 * 
	 * @return String contendo o mapeamento.
	 */
	String propertyId() default "";

	/**
	 * Define o identificador da propriedade que será considerada para efetuar pesquisa 
	 * fonética.
	 * 
	 * @return String contendo o identificador da propriedade.
	 */
	String phoneticPropertyId() default "";
	
	/**
	 * Define o identificador da propriedade que será considerada para efetuar uma 
	 * pesquisa.
	 * 
	 * @return String contendo o identificador da propriedade.
	 */
	String searchPropertyId() default "";
	
	/**
	 * Define a propriedade do modelo de dados que identifica o tipo de classe para a propriedade 
	 * especificada na anotação.
	 *  
	 * @return String contendo o identificador da propriedade.
	 */
	String classPropertyId() default "";

	/**
	 * Define a classe de um relacionamento 1x1.
	 * 
	 * @return Classe do relacionamento.
	 */
	Class relationClass() default Object.class;

	/**
	 * Define a classe de um relacionamento NxN ou 1xN.
	 * 
	 * @return Classe do relacionamento.
	 */
	Class relationCollectionItemsClass() default Object.class;
	
	/**
	 * Define se deve ser feito cascade nas operações de gravação dos dados.
	 *
	 * @return True/False.
	 */
	boolean cascadeOnSave() default false;
	
	/**
	 * Define se deve ser feito cascade nas operações de exclusão dos dados.
	 *
	 * @return True/False.
	 */
	boolean cascadeOnDelete() default false;

	/**
	 * Define o mapeamento das propriedades em um componente.
	 * 
	 * @return String contendo o mapeamento das propriedades.
	 */
	String[] propertiesIds() default {};
	
	/**
	 * Define o tipo do mapeamento da propriedade no repositório de persistência.
	 * 
	 * @return String contendo o tipo do mapeamento.
	 */
	String mappedPropertyType() default "";

	/**
	 * Define o mapeamento da propriedade no repositório de persistência.
	 * 
	 * @return String contendo o mapeamento.
	 */
	String mappedPropertyId() default "";

	/**
	 * Define os identificadores das chaves do relacionamento.
	 * 
	 * @return String contendo os identificadores.
	 */
	String[] mappedPropertiesIds() default {};

	/**
	 * Define o tipo de relacionamento.
	 * 
	 * @return Instância contendo o tipo de relacionamento.
	 */
	RelationType relationType() default RelationType.NONE;

	/**
	 * Define o tipo de junção do relacionamento.
	 *
	 * @return Instância contendo o tipo de junção.
	 */
	RelationJoinType relationJoinType() default RelationJoinType.NONE;
	
	/**
	 * Define o tipo de dado da propriedade.
	 *
	 * @return Classe que define o tipo de dado da propriedade.
	 */
	Class propertyClass() default Object.class;

	/**
	 * Define os identificadores das chaves do relacionamento.
	 * 
	 * @return String contendo os identificadores.
	 */
	String[] mappedRelationPropertiesIds() default {};

	/**
	 * Define o identificador do repositório para relacionamentos NxN.
	 * 
	 * @return String contendo o identificador do repositório.
	 */
	String mappedRelationRepositoryId() default "";
	
	/**
	 * Define a expressão da fórmula vinculada à propriedade.
	 * 
	 * @return String contendo a expressão da fórmula.
	 */
	String formulaExpression() default "";
	
    /**
     * Define o tipo de fórmula vinculada à propriedade.
     * 
     * @return Constante que define o tipo de fórmula.
     */
	FormulaType formulaType() default FormulaType.NONE;

	/**
	 * Define o tipo de ordenação a ser utilizada.
	 * 
	 * @return Instância contendo o tipo de ordenação.
	 */
	SortOrderType sortOrder() default SortOrderType.NONE;

	/**
	 * Define as validações da propriedade.
	 * 
	 * @return Array contendo as validações da propriedade.
	 */
	ValidationType[] validations() default {ValidationType.NONE};

	/**
	 * Define a condição de comparação da propriedade.
	 * Utilizado quando a validação definida for do tipo 'compare'.
	 * 
	 * @return Instância contendo o tipo de comparação.
	 */
	ConditionType compareCondition() default ConditionType.NONE;

	/**
	 * Define o identificador da propriedade de comparação.
	 * Somente utilizado quando a validação definida for do tipo 'compare'.
	 * 
	 * @return String contendo o identificador da propriedade.
	 */
	String comparePropertyId() default "";

	/**
	 * Define a quantidade máxima de palavras a ser validada quando a validação for do 
	 * tipo 'wordCount'.
	 * 
	 * @return Valor inteiro contendo a quantidade máxima de palavras.
	 */
	int wordCount() default 0;

	/**
	 * Define o tamanho mínimo que o conteúdo de uma propriedade deverá ter.
	 * Somente utilizado quando a validação definida for do tipo 'minimumLength'.
	 * 
	 * @return Valor inteiro contendo o tamanho mínimo da propriedade.
	 */
	int minimumLength() default 0;

	/**
	 * Define o tamanho máximo que o conteúdo de uma propriedade deverá ter.
	 * Somente utilizado quando a validação definida for do tipo 'maximumLength'.
	 * 
	 * @return Valor inteiro contendo o tamanho máximo da propriedade.
	 */
	int maximumLength() default 0;

	/**
	 * Define o 'range' inicial da propriedade.
	 * Somente utilizado quando a validação definida for do tipo 'range'.
	 * 
	 * @return String contendo o valor do 'range' inicial.
	 */
	String startRange() default "";

	/**
	 * Define o 'range' final da propriedade.
	 * Somente utilizado quando a validação definida for do tipo 'range'.
	 * 
	 * @return String contendo o valor do 'range' final.
	 */
	String endRange() default "";

	/**
	 * Define a máscara de formatação/validação da propriedade.
	 * 
	 * @return String contendo a máscara de formatação/validação.
	 */
	String pattern() default "";
	
	/**
	 * Retorna a expressão regular para validação da propriedade (Valor = "")
	 * 
	 * @return String contendo a expressão regular.
	 */
	String regularExpression() default "";

	/**
	 * Define o identificador da rotina de validação customizada para propriedade.
	 * 
	 * @return String contendo o identificador da rotina de validação.
	 */
	String customValidationId() default "";

	/**
	 * Define se a máscara de formatação será persistida na propriedade.
	 * 
	 * @return True/False.
	 */
	boolean persistPattern() default true;
	
	/**
	 * Define um texto extra para a propriedade. 
	 *
	 * @return String do texto desejado.
	 */
	String tag() default "";

	/**
	 * Define o número de casas decimais de precisão.
	 * 
	 * @return Valor inteiro contendo o número de casas decimais.
	 */
	int precision() default 2;
	
	/**
	 * Define o idioma a ser utilizado na formatação/parsing da propriedade.
	 * 
	 * @return String contendo o identificador do idioma a ser utilizado.
	 */
	String language() default "";
	
    /**
     * Define a propriedade que armazena o idioma a ser utilizado na formatação/parsing da propriedade.
     * 
     * @return String contendo o identificador da propriedade.
     */
	String languagePropertyId() default "";
}