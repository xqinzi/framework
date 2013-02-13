package br.com.concepting.framework.model.helpers;

import br.com.concepting.framework.model.types.ConditionType;
import br.com.concepting.framework.model.types.ContextSearchType;
import br.com.concepting.framework.model.types.ValidationType;
import br.com.concepting.framework.persistence.types.RelationJoinType;
import br.com.concepting.framework.persistence.types.RelationType;
import br.com.concepting.framework.util.types.SortOrderType;

/**
 * Classe auxiliar que armazena as caracter�sticas de uma propriedade de um modelo de dados.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class PropertyInfo implements Comparable, Cloneable{
	private String            id                            = "";
	private Class             clazz                         = null;
	private Boolean           isModel                       = false;
	private Boolean           hasModel                      = false;
	private Boolean           isIdentity                    = false;
	private Boolean           isUnique                      = false;
	private Boolean           isForSearch                   = false;
	private Boolean           isAuditable                   = false;
	private Boolean           isEnum                        = false;
	private Boolean           hasEnum                       = false;
	private Boolean           isCollection                  = false;
	private Boolean           isDate                        = false;
	private Boolean           isTime                        = false;
	private Boolean           isBoolean                     = false;
	private Boolean           isNumber                      = false;
	private Boolean           isByteArray                   = false;
	private Boolean           isCurrency                    = false;
	private Boolean           autoGenerateIdentity          = false;
	private String            keyId                         = "";
	private String            foreignKeyId                  = "";
	private Boolean           constrained                   = false;
	private Boolean           nullable                      = false;
	private ConditionType     searchCondition               = null;
	private ContextSearchType contextSearchType             = null;
	private Boolean           caseSensitiveSearch           = false;
	private Double            similarityAccuracy            = 0d;
	private Boolean           useGroupSeparator             = false;
	private Class             collectionItemsClass          = null;
	private RelationType      relationType                  = null;
	private RelationJoinType  relationJoinType              = null;
	private Boolean           cascadeOnSave                 = false;
	private Boolean           cascadeOnDelete               = false;
	private Class             propertyClass                 = null;
	private String            propertyId                    = "";
	private String            phoneticPropertyId            = "";
	private String            classPropertyId               = "";
	private String            searchPropertyId              = "";
	private String            propertiesIds[]               = null;
	private String            mappedPropertyId              = "";
	private String            mappedPropertiesIds[]         = null;
	private String            mappedRelationPropertiesIds[] = null;
	private String            mappedRelationRepositoryId    = "";
	private String            formula                       = "";
	private SortOrderType     sortOrder                     = null;
	private ValidationType    validations[]                 = null;
	private Integer           minimumLength                 = 0;
	private Integer           maximumLength                 = 0;
	private Integer           wordCount                     = 0;
	private String            startRange                    = "";
	private String            endRange                      = "";
	private String            pattern                       = "";
	private Boolean           persistPattern                = false;
    private String            regularExpression             = "";
	private String            customValidationId            = "";
	private ConditionType     compareCondition              = null;
	private String            comparePropertyId             = "";
	private String            tag                           = "";
	private Integer           precision                     = 0;
	private String            language                      = "";
	
	/**
	 * Construtor - Inicializa objetos e/ou vari�veis internas.
	 */
	public PropertyInfo(){
	    super();
	}
	
	/**
	 * Retorna o identificador do idioma a ser utilizado na formata��o/parsing da
	 * propriedade do modelo de dados.
	 * 
	 * @return String contendo o identificador da propriedade.
	 */
	public String getLanguage(){
        return language;
    }

    /**
     * Define o identificador do idioma a ser utilizado na formata��o/parsing da
     * propriedade do modelo de dados.
     * 
     * @param language String contendo o identificador da propriedade.
     */
    public void setLanguage(String language){
        this.language = language;
    }

    /**
	 * Indica se a propriedade � um valor monet�rio.
	 * 
	 * @return True/False.
	 */
    public Boolean isCurrency(){
        return isCurrency;
    }

    /**
     * Indica se a propriedade � um valor monet�rio.
     * 
     * @return True/False.
     */
    public Boolean getIsCurrency(){
        return isCurrency();
    }

    /**
     * Define se a propriedade � um valor monet�rio.
     * 
     * @param isCurrency True/False.
     */
    public void setIsCurrency(Boolean isCurrency){
        this.isCurrency = isCurrency;
    }

    /**
	 * Retorna o n�mero de casas decimais de precis�o.
	 * 
	 * @return Valor inteiro contendo o n�mero de casas decimais.
	 */
	public Integer getPrecision(){
        return precision;
    }

    /**
     * Define o n�mero de casas decimais de precis�o.
     * 
     * @param precision Valor inteiro contendo o n�mero de casas decimais.
     */
    public void setPrecision(Integer precision){
        this.precision = precision;
    }

    /**
	 * Retorna a express�o regular definida.
	 * 
	 * @return String contendo a express�o regular.
	 */
    public String getRegularExpression(){
        return regularExpression;
    }

    /**
     * Define a express�o regular definida.
     * 
     * @param regularExpression String contendo a express�o regular.
     */
    public void setRegularExpression(String regularExpression){
        this.regularExpression = regularExpression;
    }
    
    /**
     * Indica se deve ser usado as configura��es adicionais de formata��o.
     * 
     * @return True/False.
     */
    public Boolean useAdditionalFormatting(){
        if(isDate())
            return isTime();
        else if(isNumber())
            return useGroupSeparator();
        
        return false;
    }

    /**
     * Retorna a propriedade do modelo de dados que identifica o tipo de classe para a 
     * propriedade especificada na anota��o.
     *  
     * @return String contendo o identificador da propriedade.
     */
	public String getClassPropertyId(){
    	return classPropertyId;
    }

    /**
     * Define a propriedade do modelo de dados que identifica o tipo de classe para a 
     * propriedade especificada na anota��o.
     *  
     * @param classPropertyId String contendo o identificador da propriedade.
     */
	public void setClassPropertyId(String classPropertyId){
    	this.classPropertyId = classPropertyId;
    }

	/**
	 * Indica se a propriedade aceita valores nulos.
	 * 
	 * @return True/False.
	 */
	public Boolean isNullable(){
    	return nullable;
    }

	/**
	 * Indica se a propriedade aceita valores nulos.
	 * 
	 * @return True/False.
	 */
	public Boolean getNullable(){
    	return isNullable();
    }

	/**
	 * Define se a propriedade aceita valores nulos.
	 * 
	 * @param nullable True/False.
	 */
	public void setNullable(Boolean nullable){
    	this.nullable = nullable;
    }

	/**
	 * Indica se a validade da integridade relacional deve ser verificada.
	 * 
	 * @return True/False.
	 */
	public Boolean getConstrained(){
    	return constrained;
    }

	/**
	 * Define se a validade da integridade relacional deve ser
	 * verificada.
	 * 
	 * @param constrained True/False.
	 */
	public void setConstrained(Boolean constrained){
    	this.constrained = constrained;
    }

	/**
	 * Retorna o identificador da chave do relacionamento.
	 * 
	 * @return String contendo o identificador da chave.
	 */
	public String getForeignKeyId(){
    	return foreignKeyId;
    }

	/**
	 * Define o identificador da chave do relacionamento.
	 * 
	 * @param foreignKeyId String contendo o identificador da chave.
	 */
	public void setForeignKeyId(String foreignKeyId){
    	this.foreignKeyId = foreignKeyId;
    }

	/**
	 * Retorna o identificador do �ndice ou chave vinculado � propriedade.
	 * 
	 * @return String contendo o identificador do �ndice ou chave.
	 */
	public String getKeyId(){
    	return keyId;
    }

	/**
	 * Define o identificador do �ndice ou chave vinculado � propriedade.
	 * 
	 * @param keyId String contendo o identificador do �ndice ou chave.
	 */
	public void setKeyId(String keyId){
    	this.keyId = keyId;
    }

	/**
	 * Retorna o conte�do do marcador customizado. 
	 *
	 * @return String contendo o marcador.
	 */
	public String getTag(){
    	return tag;
    }

	/**
	 * Define o conte�do do marcador customizado. 
	 *
	 * @param tag String contendo o marcador.
	 */
	public void setTag(String tag){
    	this.tag = tag;
    }

	/**
	 * Retorna o tipo de dado da propriedade.
	 *
	 * @return Inst�ncia contendo o tipo de dado da propriedade.
	 */
	public Class getPropertyClass(){
		return propertyClass;
	}

	/**
	 * Define o tipo de dado da propriedade.
	 *
	 * @param propertyClass Inst�ncia contendo o tipo de dado da propriedade.
	 */
	public void setPropertyClass(Class propertyClass){
		this.propertyClass = propertyClass;
	}

	/**
	 * Retorna o tipo de jun��o com o relacionamento.
	 *
	 * @return Constante que define o tipo de jun��o com o relacionamento.
	 */
	public RelationJoinType getRelationJoinType(){
		return relationJoinType;
	}

	/**
	 * Define o tipo de jun��o com o relacionamento.
	 *
	 * @param relationJoinType Constante que define o tipo de jun��o com o 
	 * relacionamento.
	 */
	public void setRelationJoinType(RelationJoinType relationJoinType){
		this.relationJoinType = relationJoinType;
	}

	/**
	 * Indica se deve ser feito cascade nas opera��es de grava��o dos dados.
	 * 
	 * @return True/False.
	 */
	public Boolean cascadeOnSave(){
		return cascadeOnSave;
	}

	/**
	 * Indica se deve ser feito cascade nas opera��es de grava��o dos dados.
	 * 
	 * @return True/False.
	 */
	public Boolean getCascadeOnSave(){
		return cascadeOnSave();
	}

	/**
	 * Define se deve ser feito cascade nas opera��es de grava��o dos dados.
	 * 
	 * @param cascadeOnSave True/False.
	 */
	public void setCascadeOnSave(Boolean cascadeOnSave){
		this.cascadeOnSave = cascadeOnSave;
	}

	/**
	 * Indica se deve ser feito cascade nas opera��es de exclus�o dos dados.
	 * 
	 * @return True/False.
	 */
	public Boolean cascadeOnDelete(){
		return cascadeOnDelete;
	}

	/**
	 * Indica se deve ser feito cascade nas opera��es de exclus�o dos dados.
	 * 
	 * @return True/False.
	 */
	public Boolean getCascadeOnDelete(){
		return cascadeOnDelete();
	}

	/**
	 * Define se deve ser feito cascade nas opera��es de exclus�o dos dados.
	 * 
	 * @param cascadeOnDelete True/False.
	 */
	public void setCascadeOnDelete(Boolean cascadeOnDelete){
		this.cascadeOnDelete = cascadeOnDelete;
	}

	/**
	 * Indica que a chave de identifica��o do modelo de dados dever� ser 
	 * gerada automaticamente.
	 * 
	 * @return True/False.
	 */
	public Boolean autoGenerateIdentity(){
		return autoGenerateIdentity;
	}

	/**
	 * Indica que a chave de identifica��o do modelo de dados dever� ser 
	 * gerada automaticamente.
	 * 
	 * @return True/False.
	 */
	public Boolean getAutoGenerateIdentity(){
		return autoGenerateIdentity();
	}

	/**
	 * Define que a chave de identifica��o do modelo de dados dever� ser 
	 * gerada automaticamente.
	 * 
	 * param autoGenerateIdentity True/False.
	 */
	public void setAutoGenerateIdentity(Boolean autoGenerateIdentity){
		this.autoGenerateIdentity = autoGenerateIdentity;
	}

	/**
	 * Retorna o identificador da propriedade que ser� considerada para 
	 * efetuar pesquisa fon�tica.
	 * 
	 * @return String contendo o identificador da propriedade.
	 */
	public String getPhoneticPropertyId(){
		return phoneticPropertyId;
	}

	/**
	 * Define o identificador da propriedade que ser� considerada para 
	 * efetuar pesquisa fon�tica.
	 * 
	 * @param phoneticPropertyId String contendo o identificador da 
	 * propriedade.
	 */
	public void setPhoneticPropertyId(String phoneticPropertyId){
		this.phoneticPropertyId = phoneticPropertyId;
	}

	/**
	 * Retorna o identificador da propriedade que ser� considerada para 
	 * efetuar uma pesquisa.
	 * 
	 * @return String contendo o identificador da propriedade.
	 */
	public String getSearchPropertyId(){
		return searchPropertyId;
	}

	/**
	 * Define o identificador da propriedade que ser� considerada para 
	 * efetuar uma pesquisa.
	 * 
	 * @param searchPropertyId String contendo o identificador da propriedade.
	 */
	public void setSearchPropertyId(String searchPropertyId){
		this.searchPropertyId = searchPropertyId;
	}

	/**
	 * Indica que o separador de milhar deve ser utilizado na formata��o.
	 * 
	 * @return True/False.
	 */
	public Boolean getUseGroupSeparator(){
		return useGroupSeparator();
	}

	/**
	 * Indica que a propriedade � �nica para identifica��o de um modelo de 
	 * dados.
	 * 
	 * @return True/False.
	 */
	public Boolean isUnique(){
		return isUnique;
	}

	/**
	 * Indica que a propriedade � �nica para identifica��o de um modelo de 
	 * dados.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsUnique(){
		return isUnique();
	}

	/**
	 * Define que a propriedade � �nica para identifica��o de um modelo de 
	 * dados.
	 * 
	 * @param isUnique True/False.
	 */
	public void setIsUnique(Boolean isUnique){
		this.isUnique = isUnique;
	}

	/**
	 * Retorna o mapeamento da propriedade no reposit�rio de persist�ncia.
	 * 
	 * @return String contendo o mapeamento.
	 */
	public String getMappedPropertyId(){
		return mappedPropertyId;
	}

	/**
	 * Define o mapeamento da propriedade no reposit�rio de persist�ncia.
	 * 
	 * @param mappedPropertyId String contendo o mapeamento.
	 */
	public void setMappedPropertyId(String mappedPropertyId){
		this.mappedPropertyId = mappedPropertyId;
	}

	/**
	 * Retorna os identificadores das chaves do relacionamento.
	 * 
	 * @return String contendo os identificadores.
	 */
	public String[] getMappedPropertiesIds(){
		return mappedPropertiesIds;
	}

	/**
	 * Define os identificadores das chaves do relacionamento.
	 * 
	 * @param mappedPropertiesIds String contendo os identificadores.
	 */
	public void setMappedPropertiesIds(String mappedPropertiesIds[]){
		this.mappedPropertiesIds = mappedPropertiesIds;
	}

	/**
	 * Indica se a propriedade � do tipo modelo de dados.
	 * 
	 * @return True/False.
	 */
	public Boolean isModel(){
		return isModel;
	}

	/**
	 * Indica se a propriedade � do tipo modelo de dados.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsModel(){
		return isModel();
	}

	/**
	 * Define se a propriedade � do tipo modelo de dados.
	 * 
	 * @param isModel True/False.
	 */
	public void setIsModel(Boolean isModel){
		this.isModel = isModel;
	}

	/**
	 * Indica se a propriedade contem modelos de dados.
	 * 
	 * @return True/False.
	 */
	public Boolean hasModel(){
		return hasModel;
	}

	/**
	 * Indica se a propriedade contem modelos de dados.
	 * 
	 * @return True/False.
	 */
	public Boolean getHasModel(){
		return hasModel();
	}

	/**
	 * Define se a propriedade contem modelos de dados.
	 * 
	 * @param hasModel True/False.
	 */
	public void setHasModel(Boolean hasModel){
		this.hasModel = hasModel;
	}

	/**
	 * Indica se a propriedade � uma data.
	 * 
	 * @return True/False.
	 */
	public Boolean isDate(){
		return isDate;
	}

	/**
	 * Indica se a propriedade � uma data.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsDate(){
		return isDate();
	}

	/**
	 * Indica se a propriedade � uma data/hor�rio.
	 * 
	 * @return True/False.
	 */
	public Boolean isTime(){
		return isTime;
	}

	/**
	 * Indica se a propriedade � uma data/hor�rio.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsTime(){
		return isTime();
	}

	/**
	 * Indica se a propriedade � um valor booleano.
	 * 
	 * @return True/False.
	 */
	public Boolean isBoolean(){
		return isBoolean;
	}

	/**
	 * Indica se a propriedade � um valor booleano.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsBoolean(){
		return isBoolean();
	}

	/**
	 * Indica se a propriedade � um valor num�rico.
	 * 
	 * @return True/False.
	 */
	public Boolean isNumber(){
		return isNumber;
	}

	/**
	 * Indica se a propriedade � um valor num�rico.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsNumber(){
		return isNumber();
	}

	/**
	 * Retorna o identificador da propriedade.
	 * 
	 * @return String contendo o identificador.
	 */
	public String getId(){
		return id;
	}

	/**
	 * Define o identificador da propriedade.
	 * 
	 * @param id String contendo o identificador.
	 */
	public void setId(String id){
		this.id = id;
	}

	/**
	 * Retorna o tipo da propriedade.
	 * 
	 * @return Classe do tipo da propriedade.
	 */
	public Class getClazz(){
		return clazz;
	}

	/**
	 * Define o tipo da propriedade.
	 * 
	 * @param clazz Classe do tipo da propriedade.
	 */
	public void setClazz(Class clazz){
		this.clazz = clazz;
	}

	/**
	 * Retorna o tipo da classe dos itens da cole��o.
	 * 
	 * @return Classe do tipo dos itens da cole��o.
	 */
	public Class getCollectionItemsClass(){
		return collectionItemsClass;
	}
	
	/**
	 * Define o tipo da classe dos itens da cole��o.
	 * 
	 * @param collectionItemsClass Classe do tipo dos itens da cole��o.
	 */
	public void setCollectionItemsClass(Class collectionItemsClass){
		this.collectionItemsClass = collectionItemsClass;
	}

	/**
	 * Retorna que a propriedade � chave de identifica��o de um modelo de 
	 * dados.
	 * 
	 * @return True/False.
	 */
	public Boolean isIdentity(){
		return isIdentity;
	}

	/**
	 * Retorna que a propriedade � chave de identifica��o de um modelo de 
	 * dados.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsIdentity(){
		return isIdentity();
	}

	/**
	 * Define que a propriedade � chave de identifica��o de um modelo de 
	 * dados.
	 * 
	 * @param isIdentity True/False.
	 */
	public void setIsIdentity(Boolean isIdentity){
		this.isIdentity = isIdentity;
	}

	/**
	 * Indica que a propriedade ser� considerada para pesquisa.
	 * 
	 * @return True/False.
	 */
	public Boolean isForSearch(){
		return isForSearch;
	}

	/**
	 * Indica que a propriedade ser� considerada para pesquisa.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsForSearch(){
		return isForSearch();
	}

	/**
	 * Define que a propriedade ser� considerada para pesquisa.
	 * 
	 * @param isForSearch True/False.
	 */
	public void setIsForSearch(Boolean isForSearch){
		this.isForSearch = isForSearch;
	}

	/**
	 * Retorna o tamanho m�nimo que o conte�do de uma propriedade dever� ter. 
	 * Somente utilizado quando a valida��o definida for do tipo 'minimumLength'.
	 * 
	 * @return Valor inteiro contendo o tamanho m�nimo da propriedade.
	 */
	public Integer getMinimumLength(){
		return minimumLength;
	}

	/**
	 * Define o tamanho m�nimo que o conte�do de uma propriedade dever� ter. 
	 * Somente utilizado quando a valida��o definida for do tipo 'minimumLength'.
	 * 
	 * @param minimumLength Valor inteiro contendo o tamanho m�nimo da 
	 *                      propriedade.
	 */
	public void setMinimumLength(Integer minimumLength){
		this.minimumLength = minimumLength;
	}

	/**
	 * Retorna o tamanho m�ximo que o conte�do de uma propriedade dever� ter. 
	 * Somente utilizado quando a valida��o definida for do tipo 'maximumLength'.
	 * 
	 * @return Valor inteiro contendo o tamanho m�ximo da propriedade.
	 */
	public Integer getMaximumLength(){
		return maximumLength;
	}

	/**
	 * Define o tamanho m�ximo que o conte�do de uma propriedade dever� ter. 
	 * Somente utilizado quando a valida��o definida for do tipo 
	 * 'maximumLength'.
	 * 
	 * @param maximumLength Valor inteiro contendo o tamanho m�ximo da 
	 * propriedade.
	 */
	public void setMaximumLength(Integer maximumLength){
		this.maximumLength = maximumLength;
	}

	/**
	 * Retorna a m�scara de formata��o/valida��o da propriedade.
	 * 
	 * @return String contendo a m�scara de formata��o/valida��o.
	 */
	public String getPattern(){
		return pattern;
	}

	/**
	 * Define a m�scara de formata��o/valida��o da propriedade.
	 * 
	 * @param pattern String contendo a m�scara de formata��o/valida��o.
	 */
	public void setPattern(String pattern){
		this.pattern = pattern;
	}

	/**
	 * Retorna o identificador da rotina de valida��o customizada para 
	 * propriedade.
	 * 
	 * @return String contendo o identificador da rotina de valida��o.
	 */
	public String getCustomValidationId(){
		return customValidationId;
	}

	/**
	 * Define o identificador da rotina de valida��o customizada para 
	 * propriedade.
	 * 
	 * @param customValidationId String contendo o identificador da rotina de 
	 * valida��o.
	 */
	public void setCustomValidationId(String customValidationId){
		this.customValidationId = customValidationId;
	}

	/**
	 * Indica que a propriedade ser� auditada.
	 * 
	 * @return True/False.
	 */
	public Boolean isAuditable(){
		return isAuditable;
	}

	/**
	 * Indica que a propriedade ser� auditada.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsAuditable(){
		return isAuditable();
	}

	/**
	 * Define que a propriedade ser� auditada.
	 * 
	 * @param isAuditable True/False.
	 */
	public void setIsAuditable(Boolean isAuditable){
		this.isAuditable = isAuditable;
	}

	/**
	 * Retorna as valida��es da propriedade.
	 * 
	 * @return Array contendo as valida��es da propriedade.
	 */
	public ValidationType[] getValidations(){
		return validations;
	}

	/**
	 * Define as valida��es da propriedade.
	 * 
	 * @param validations Array contendo as valida��es da propriedade.
	 */
	public void setValidations(ValidationType validations[]){
		this.validations = validations;
	}

	/**
	 * Indica se existe uma valida��o parametrizada.
	 * 
	 * @param validation Constante que define a valida��o desejada.
	 * @return True/False.
	 */
	public Boolean hasValidation(ValidationType validation){
		if(validations != null)
			for(Integer cont = 0 ; cont < validations.length ; cont++)
				if(validations[cont].equals(validation))
					return true;

		return false;
	}

	/**
	 * Define se a propriedade � um valor booleano.
	 * 
	 * @param isBoolean True/False.
	 */
	public void setIsBoolean(Boolean isBoolean){
		this.isBoolean = isBoolean;
	}

	/**
	 * Define se a propriedade � uma data.
	 * 
	 * @param isDate True/False.
	 */
	public void setIsDate(Boolean isDate){
		this.isDate = isDate;
	}

	/**
	 * Define se a propriedade � uma data/hor�rio.
	 * 
	 * @param isTime True/False.
	 */
	public void setIsTime(Boolean isTime){
		this.isTime = isTime;
	}

	/**
	 * Define se a propriedade � um valor num�rico.
	 * 
	 * @param isNumber True/False.
	 */
	public void setIsNumber(Boolean isNumber){
		this.isNumber = isNumber;
	}

	/**
	 * Retorna o tipo de relacionamento.
	 * 
	 * @return Inst�ncia contendo o tipo de relacionamento.
	 */
	public RelationType getRelationType(){
		return relationType;
	}

	/**
	 * Define o tipo de relacionamento.
	 * 
	 * @param relationType Inst�ncia contendo o tipo de relacionamento.
	 */
	public void setRelationType(RelationType relationType){
		this.relationType = relationType;
	}

	/**
	 * Indica se a propriedade � uma cole��o.
	 * 
	 * @return True/False.
	 */
	public Boolean isCollection(){
		return isCollection;
	}

	/**
	 * Indica se a propriedade � uma cole��o.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsCollection(){
		return isCollection();
	}

	/**
	 * Define se a propriedade � uma cole��o.
	 * 
	 * @param isCollection True/False.
	 */
	public void setIsCollection(Boolean isCollection){
		this.isCollection = isCollection;
	}

	/**
	 * Indica se a propriedade � um array de bytes.
	 * 
	 * @return True/False.
	 */
	public Boolean isByteArray(){
		return isByteArray;
	}

	/**
	 * Indica se a propriedade � um array de bytes.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsByteArray(){
		return isByteArray();
	}

	/**
	 * Define se a propriedade � um array de bytes.
	 * 
	 * @param isByteArray True/False.
	 */
	public void setIsByteArray(Boolean isByteArray){
		this.isByteArray = isByteArray;
	}

	/**
	 * Retorna o mapeamento das propriedades em um componente.
	 * 
	 * @return String contendo o mapeamento das propriedades.
	 */
	public String[] getPropertiesIds(){
		return propertiesIds;
	}

	/**
	 * Define o mapeamento das propriedades em um componente.
	 * 
	 * @param propertiesIds String contendo o mapeamento das propriedades.
	 */
	public void setPropertiesIds(String[] propertiesIds){
		this.propertiesIds = propertiesIds;
	}

	/**
	 * Indica se a propriedade � uma constante.
	 * 
	 * @return True/False.
	 */
	public Boolean isEnum(){
		return isEnum;
	}

	/**
	 * Indica se a propriedade � uma constante.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsEnum(){
		return isEnum();
	}

	/**
	 * Define se a propriedade � uma constante.
	 * 
	 * @param isEnum True/False.
	 */
	public void setIsEnum(Boolean isEnum){
		this.isEnum= isEnum;
	}

	/**
	 * Indica se a propriedade contem constantes.
	 * 
	 * @return True/False.
	 */
	public Boolean hasEnum(){
		return hasEnum;
	}

	/**
	 * Indica se a propriedade contem constantes.
	 * 
	 * @return True/False.
	 */
	public Boolean getHasEnum(){
		return hasEnum();
	}

	/**
	 * Define se a propriedade contem constantes.
	 * 
	 * @param hasEnum True/False.
	 */
	public void setHasEnum(Boolean hasEnum){
		this.hasEnum = hasEnum;
	}

	/**
	 * Define o identificador do reposit�rio para relacionamentos NxN.
	 * 
	 * @param mappedRelationRepositoryId String contendo o identificador do 
	 * reposit�rio.
	 */
	public void setMappedRelationRepositoryId(String mappedRelationRepositoryId){
		this.mappedRelationRepositoryId = mappedRelationRepositoryId;
	}

	/**
	 * Retorna a condi��o de pesquisa da propriedade. Somente ser� 
	 * considerada quando a propriedade for de pesquisa.
	 * 
	 * @return Inst�ncia contendo a condi��o de pesquisa.
	 */
	public ConditionType getSearchCondition(){
		return searchCondition;
	}

	/**
	 * Define a condi��o de pesquisa da propriedade. Somente ser� considerada 
	 * quando a propriedade for de pesquisa.
	 * 
	 * @param searchCondition Inst�ncia contendo a condi��o de pesquisa.
	 */
	public void setSearchCondition(ConditionType searchCondition){
		this.searchCondition = searchCondition;
	}

	/**
	 * Retorna o identificador do reposit�rio para relacionamentos NxN.
	 * 
	 * @return String contendo o identificador do reposit�rio.
	 */
	public String getMappedRelationRepositoryId(){
		return mappedRelationRepositoryId;
	}

	/**
	 * Retorna os tipos de pesquisa por contexto. Somente ser� considerada 
	 * quando a propriedade searchCondition for do tipo CONTEXT.
	 * 
	 * @return Inst�ncia contendo os tipos de pesquisa por contexto.
	 */
	public ContextSearchType getContextSearchType(){
		return contextSearchType;
	}

	/**
	 * Define os tipos de pesquisa por contexto. Somente ser� considerada 
	 * quando a propriedade searchCondition for do tipo CONTEXT.
	 * 
	 * @param contextSearchType Inst�ncia contendo os tipos de pesquisa por 
	 * contexto.
	 */
	public void setContextSearchType(ContextSearchType contextSearchType){
		this.contextSearchType = contextSearchType;
	}

	/**
	 * Indica se a pesquisa ser� sens�vel a letras mai�sculas/min�sculas.
	 * 
	 * @return True/False.
	 */
	public Boolean isCaseSensitiveSearch(){
		return caseSensitiveSearch;
	}

	/**
	 * Indica se a pesquisa ser� sens�vel a letras mai�sculas/min�sculas.
	 * 
	 * @return True/False.
	 */
	public Boolean getCaseSensitiveSearch(){
		return isCaseSensitiveSearch();
	}

	/**
	 * Define se a pesquisa ser� sens�vel a letras mai�sculas/min�sculas.
	 * 
	 * @param caseSensitiveSearch True/False.
	 */
	public void setCaseSensitiveSearch(Boolean caseSensitiveSearch){
		this.caseSensitiveSearch = caseSensitiveSearch;
	}

	/**
	 * Retorna os identificadores das chaves do relacionamento.
	 * 
	 * @return String contendo os identificadores.
	 */
	public String[] getMappedRelationPropertiesIds(){
		return mappedRelationPropertiesIds;
	}

	/**
	 * Define os identificadores das chaves do relacionamento.
	 * 
	 * @param mappedRelationPropertiesIds String contendo os identificadores.
	 */
	public void setMappedRelationPropertiesIds(String[] mappedRelationPropertiesIds){
		this.mappedRelationPropertiesIds = mappedRelationPropertiesIds;
	}

	/**
	 * Indica que o separador de milhar deve ser utilizado na formata��o.
	 * 
	 * @return True/False.
	 */
	public Boolean useGroupSeparator(){
		return useGroupSeparator;
	}

	/**
	 * Define que o separador de milhar deve ser utilizado na formata��o.
	 * 
	 * @param useGroupSeparator True/False.
	 */
	public void setUseGroupSeparator(Boolean useGroupSeparator){
		this.useGroupSeparator = useGroupSeparator;
	}

	/**
	 * Retorna uma f�rmula para a propriedade.
	 * 
	 * @return String contendo a f�rmula.
	 */
	public String getFormula(){
		return formula;
	}

	/**
	 * Define uma f�rmula para a propriedade.
	 * 
	 * @param formula String contendo a f�rmula.
	 */
	public void setFormula(String formula){
		this.formula = formula;
	}

	/**
	 * Retorna o tipo de ordena��o a ser utilizada.
	 * 
	 * @return Inst�ncia contendo o tipo de ordena��o.
	 */
	public SortOrderType getSortOrder(){
		return sortOrder;
	}

	/**
	 * Define o tipo de ordena��o a ser utilizada.
	 * 
	 * @param sortOrder Inst�ncia contendo o tipo de ordena��o.
	 */
	public void setSortOrder(SortOrderType sortOrder){
		this.sortOrder = sortOrder;
	}

	/**
	 * Retorna a porcentagem de similaridade a ser utilizada quando a 
	 * condi��o de pesquisa definida for por similaridade.
	 * 
	 * @return Valor em ponto flutuante contendo a porcentagem de 
	 * similaridade.
	 */
	public Double getSimilarityAccuracy(){
		return similarityAccuracy;
	}

	/**
	 * Define a porcentagem de similaridade a ser utilizada quando a condi��o 
	 * de pesquisa definida for por similaridade.
	 * 
	 * @param similarityAccuracy Valor em ponto flutuante contendo a 
	 * porcentagem de similaridade.
	 */
	public void setSimilarityAccuracy(Double similarityAccuracy){
		this.similarityAccuracy = similarityAccuracy;
	}

	/**
	 * Retorna o identificador da propriedade a ser populada no reposit�rio 
	 * de persist�ncia.
	 * 
	 * @return String contendo o mapeamento.
	 */
	public String getPropertyId(){
		return propertyId;
	}

	/**
	 * Define o identificador da propriedade a ser populada no reposit�rio 
	 * de persist�ncia.
	 * 
	 * @param propertyId String contendo o mapeamento.
	 */
	public void setPropertyId(String propertyId){
		this.propertyId = propertyId;
	}

	/**
	 * Retorna o 'range' final da propriedade. Somente utilizado quando a 
	 * valida��o definida for do tipo 'range'.
	 * 
	 * @return String contendo o valor do 'range' final.
	 */
	public String getEndRange(){
		return endRange;
	}

	/**
	 * Define o 'range' final da propriedade. Somente utilizado quando a 
	 * valida��o definida for do tipo 'range'.
	 * 
	 * @param endRange String contendo o valor do 'range' final.
	 */
	public void setEndRange(String endRange){
		this.endRange = endRange;
	}

	/**
	 * Retorna o 'range' inicial da propriedade. Somente utilizado quando a 
	 * valida��o definida for do tipo 'range'.
	 * 
	 * @return String contendo o valor do 'range' inicial.
	 */
	public String getStartRange(){
		return startRange;
	}

	/**
	 * Define o 'range' inicial da propriedade. Somente utilizado quando a 
	 * valida��o definida for do tipo 'range'.
	 * 
	 * @param startRange String contendo o valor do 'range' inicial.
	 */
	public void setStartRange(String startRange){
		this.startRange = startRange;
	}

	/**
	 * Indica se a m�scara de formata��o ser� persistida na propriedade.
	 * 
	 * @return True/False.
	 */
	public Boolean persistPattern(){
		return persistPattern;
	}

	/**
	 * Indica se a m�scara de formata��o ser� persistida na propriedade.
	 * 
	 * @return True/False.
	 */
	public Boolean getPersistPattern(){
		return persistPattern();
	}

	/**
	 * Define se a m�scara de formata��o ser� persistida na propriedade.
	 * 
	 * @param persistPattern True/False.
	 */
	public void setPersistPattern(Boolean persistPattern){
		this.persistPattern = persistPattern;
	}

	/**
	 * Retorna a condi��o de compara��o da propriedade. Utilizado quando a 
	 * valida��o definida for do tipo 'compare'.
	 * 
	 * @return Inst�ncia contendo o tipo de compara��o.
	 */
	public ConditionType getCompareCondition(){
		return compareCondition;
	}

	/**
	 * Define a condi��o de compara��o da propriedade. Utilizado quando a 
	 * valida��o definida for do tipo 'compare'.
	 * 
	 * @param compareCondition Inst�ncia contendo o tipo de compara��o.
	 */
	public void setCompareCondition(ConditionType compareCondition){
		this.compareCondition = compareCondition;
	}

	/**
	 * Retorna o identificador da propriedade de compara��o. Somente 
	 * utilizado quando a valida��o definida for do tipo 'compare'.
	 * 
	 * @return String contendo o identificador da propriedade.
	 */
	public String getComparePropertyId(){
		return comparePropertyId;
	}

	/**
	 * Define o identificador da propriedade de compara��o. Somente utilizado 
	 * quando a valida��o definida for do tipo 'compare'.
	 * 
	 * @param comparePropertyId String contendo o identificador da 
	 * propriedade.
	 */
	public void setComparePropertyId(String comparePropertyId){
		this.comparePropertyId = comparePropertyId;
	}

	/**
	 * Retorna a quantidade m�xima de palavras a ser validada quando a 
	 * valida��o for do tipo 'wordCount'.
	 * 
	 * @return Valor inteiro contendo a quantidade m�xima de palavras.
	 */
	public Integer getWordCount(){
		return wordCount;
	}

	/**
	 * Define a quantidade m�xima de palavras a ser validada quando a 
	 * valida��o for do tipo 'wordCount'.
	 * 
	 * @param wordCount Valor inteiro contendo a quantidade m�xima de 
	 * palavras.
	 */
	public void setWordCount(Integer wordCount){
		this.wordCount = wordCount;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object object){
		PropertyInfo comparePropertyInfo = (PropertyInfo)object;

		if(comparePropertyInfo == null)
		    return false;
		
		return (id.equals(comparePropertyInfo.getId()));
	}

    /**
	 * @see java.lang.Object#clone()
	 */
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode(){
	    return id.hashCode();
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
    public int compareTo(Object object){
        PropertyInfo comparePropertyInfo = (PropertyInfo)object;

        if(comparePropertyInfo == null)
            return -1;

        return(id.compareTo(comparePropertyInfo.getId()));
    }
}