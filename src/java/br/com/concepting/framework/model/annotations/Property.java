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
 * Classe que define a anota��o para uma propriedade em um modelo de dados.
 * 
 * @author fvilarinho
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Property{
	/**
	 * Define que a propriedade � chave de identifica��o de um modelo de dados.
	 * 
	 * @return True/False.
	 */
	boolean isIdentity() default false;
	
	/**
	 * Define que a chave de identifica��o do modelo de dados dever� ser gerada 
	 * automaticamente.
	 *
	 * @return True/False.
	 */
	boolean autoGenerateIdentity() default true;
	
	/**
	 * Define que a propriedade � �nica para identifica��o de um modelo de dados.
	 *
	 * @return True/False.
	 */
	boolean isUnique() default false;
	
	/**
	 * Define o identificador do �ndice ou chave vinculada � propriedade.
	 * 
	 * @return String contendo o identificador do �ndice ou chave.
	 */
	String keyId() default "";
	
	/**
	 * Define o identificador da chave do relacionamento.
	 * 
	 * @return String contendo o identificador da chave.
	 */
	String foreignKeyId() default "";
	
	/**
	 * Indica se o relacionamento deve ser validado quando o mesmo n�o possuir integridade 
	 * relacional.
	 * 
	 * @return True/False.
	 */
	boolean constrained() default true;
	
	/**
	 * Indica que a propriedade deve ou n�o conter valores nulos.
	 * 
	 * @return True/False.
	 */
	boolean nullable() default true;

	/**
	 * Define que a propriedade ser� considerada para pesquisa.
	 * 
	 * @return True/False.
	 */
	boolean isForSearch() default false;

	/**
	 * Define a condi��o de pesquisa da propriedade.
	 * Somente ser� considerada quando a propriedade for de pesquisa.
	 * 
	 * @return Inst�ncia contendo a condi��o de pesquisa.
	 */
	ConditionType searchCondition() default ConditionType.EQUAL;

	/**
	 * Define os tipos de pesquisa por contexto.
	 * Somente ser� considerada quando a propriedade searchCondition for do tipo CONTEXT.
	 * 
	 * @return Inst�ncia contendo os tipos de pesquisa por contexto.
	 */
	ContextSearchType contextSearchType() default ContextSearchType.BOTH;

	/**
	 * Define se a pesquisa ser� sens�vel a letras mai�sculas/min�sculas.
	 * 
	 * @return True/False.
	 */
	boolean caseSensitiveSearch() default true;

	/**
	 * Define a porcentagem de acertividade a ser utilizada quando a condi��o de pesquisa
	 * fon�tica.
	 * 
	 * @return Valor em ponto flutuante contendo a porcentagem de acertividade.
	 */
	double phoneticAccuracy() default 50.0;

	/**
	 * Define que a propriedade ser� auditada.
	 * 
	 * @return True/False.
	 */
	boolean isAuditable() default false;

	/**
	 * Define que o separador de milhar deve ser utilizado na formata��o.
	 * 
	 * @return True/False.
	 */
	boolean useGroupSeparator() default false;

	/**
	 * Define o identificador da propriedade a ser populada no reposit�rio de persist�ncia.
	 * 
	 * @return String contendo o mapeamento.
	 */
	String propertyId() default "";

	/**
	 * Define o identificador da propriedade que ser� considerada para efetuar pesquisa 
	 * fon�tica.
	 * 
	 * @return String contendo o identificador da propriedade.
	 */
	String phoneticPropertyId() default "";
	
	/**
	 * Define o identificador da propriedade que ser� considerada para efetuar uma 
	 * pesquisa.
	 * 
	 * @return String contendo o identificador da propriedade.
	 */
	String searchPropertyId() default "";
	
	/**
	 * Define a propriedade do modelo de dados que identifica o tipo de classe para a propriedade 
	 * especificada na anota��o.
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
	 * Define se deve ser feito cascade nas opera��es de grava��o dos dados.
	 *
	 * @return True/False.
	 */
	boolean cascadeOnSave() default false;
	
	/**
	 * Define se deve ser feito cascade nas opera��es de exclus�o dos dados.
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
	 * Define o tipo do mapeamento da propriedade no reposit�rio de persist�ncia.
	 * 
	 * @return String contendo o tipo do mapeamento.
	 */
	String mappedPropertyType() default "";

	/**
	 * Define o mapeamento da propriedade no reposit�rio de persist�ncia.
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
	 * @return Inst�ncia contendo o tipo de relacionamento.
	 */
	RelationType relationType() default RelationType.NONE;

	/**
	 * Define o tipo de jun��o do relacionamento.
	 *
	 * @return Inst�ncia contendo o tipo de jun��o.
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
	 * Define o identificador do reposit�rio para relacionamentos NxN.
	 * 
	 * @return String contendo o identificador do reposit�rio.
	 */
	String mappedRelationRepositoryId() default "";
	
	/**
	 * Define a express�o da f�rmula vinculada � propriedade.
	 * 
	 * @return String contendo a express�o da f�rmula.
	 */
	String formulaExpression() default "";
	
    /**
     * Define o tipo de f�rmula vinculada � propriedade.
     * 
     * @return Constante que define o tipo de f�rmula.
     */
	FormulaType formulaType() default FormulaType.NONE;

	/**
	 * Define o tipo de ordena��o a ser utilizada.
	 * 
	 * @return Inst�ncia contendo o tipo de ordena��o.
	 */
	SortOrderType sortOrder() default SortOrderType.NONE;

	/**
	 * Define as valida��es da propriedade.
	 * 
	 * @return Array contendo as valida��es da propriedade.
	 */
	ValidationType[] validations() default {ValidationType.NONE};

	/**
	 * Define a condi��o de compara��o da propriedade.
	 * Utilizado quando a valida��o definida for do tipo 'compare'.
	 * 
	 * @return Inst�ncia contendo o tipo de compara��o.
	 */
	ConditionType compareCondition() default ConditionType.NONE;

	/**
	 * Define o identificador da propriedade de compara��o.
	 * Somente utilizado quando a valida��o definida for do tipo 'compare'.
	 * 
	 * @return String contendo o identificador da propriedade.
	 */
	String comparePropertyId() default "";

	/**
	 * Define a quantidade m�xima de palavras a ser validada quando a valida��o for do 
	 * tipo 'wordCount'.
	 * 
	 * @return Valor inteiro contendo a quantidade m�xima de palavras.
	 */
	int wordCount() default 0;

	/**
	 * Define o tamanho m�nimo que o conte�do de uma propriedade dever� ter.
	 * Somente utilizado quando a valida��o definida for do tipo 'minimumLength'.
	 * 
	 * @return Valor inteiro contendo o tamanho m�nimo da propriedade.
	 */
	int minimumLength() default 0;

	/**
	 * Define o tamanho m�ximo que o conte�do de uma propriedade dever� ter.
	 * Somente utilizado quando a valida��o definida for do tipo 'maximumLength'.
	 * 
	 * @return Valor inteiro contendo o tamanho m�ximo da propriedade.
	 */
	int maximumLength() default 0;

	/**
	 * Define o 'range' inicial da propriedade.
	 * Somente utilizado quando a valida��o definida for do tipo 'range'.
	 * 
	 * @return String contendo o valor do 'range' inicial.
	 */
	String startRange() default "";

	/**
	 * Define o 'range' final da propriedade.
	 * Somente utilizado quando a valida��o definida for do tipo 'range'.
	 * 
	 * @return String contendo o valor do 'range' final.
	 */
	String endRange() default "";

	/**
	 * Define a m�scara de formata��o/valida��o da propriedade.
	 * 
	 * @return String contendo a m�scara de formata��o/valida��o.
	 */
	String pattern() default "";
	
	/**
	 * Retorna a express�o regular para valida��o da propriedade (Valor = "")
	 * 
	 * @return String contendo a express�o regular.
	 */
	String regularExpression() default "";

	/**
	 * Define o identificador da rotina de valida��o customizada para propriedade.
	 * 
	 * @return String contendo o identificador da rotina de valida��o.
	 */
	String customValidationId() default "";

	/**
	 * Define se a m�scara de formata��o ser� persistida na propriedade.
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
	 * Define o n�mero de casas decimais de precis�o.
	 * 
	 * @return Valor inteiro contendo o n�mero de casas decimais.
	 */
	int precision() default 2;
	
	/**
	 * Define o idioma a ser utilizado na formata��o/parsing da propriedade.
	 * 
	 * @return String contendo o identificador do idioma a ser utilizado.
	 */
	String language() default "";
	
    /**
     * Define a propriedade que armazena o idioma a ser utilizado na formata��o/parsing da propriedade.
     * 
     * @return String contendo o identificador da propriedade.
     */
	String languagePropertyId() default "";
}