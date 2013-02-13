package br.com.concepting.framework.model.helpers;

import br.com.concepting.framework.model.types.ConditionType;
import br.com.concepting.framework.model.types.ContextSearchType;
import br.com.concepting.framework.model.types.ValidationType;
import br.com.concepting.framework.persistence.types.RelationJoinType;
import br.com.concepting.framework.persistence.types.RelationType;
import br.com.concepting.framework.util.types.SortOrderType;

/**
 * Classe auxiliar que armazena as características de uma propriedade de um modelo de dados.
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
	 * Construtor - Inicializa objetos e/ou variáveis internas.
	 */
	public PropertyInfo(){
	    super();
	}
	
	/**
	 * Retorna o identificador do idioma a ser utilizado na formatação/parsing da
	 * propriedade do modelo de dados.
	 * 
	 * @return String contendo o identificador da propriedade.
	 */
	public String getLanguage(){
        return language;
    }

    /**
     * Define o identificador do idioma a ser utilizado na formatação/parsing da
     * propriedade do modelo de dados.
     * 
     * @param language String contendo o identificador da propriedade.
     */
    public void setLanguage(String language){
        this.language = language;
    }

    /**
	 * Indica se a propriedade é um valor monetário.
	 * 
	 * @return True/False.
	 */
    public Boolean isCurrency(){
        return isCurrency;
    }

    /**
     * Indica se a propriedade é um valor monetário.
     * 
     * @return True/False.
     */
    public Boolean getIsCurrency(){
        return isCurrency();
    }

    /**
     * Define se a propriedade é um valor monetário.
     * 
     * @param isCurrency True/False.
     */
    public void setIsCurrency(Boolean isCurrency){
        this.isCurrency = isCurrency;
    }

    /**
	 * Retorna o número de casas decimais de precisão.
	 * 
	 * @return Valor inteiro contendo o número de casas decimais.
	 */
	public Integer getPrecision(){
        return precision;
    }

    /**
     * Define o número de casas decimais de precisão.
     * 
     * @param precision Valor inteiro contendo o número de casas decimais.
     */
    public void setPrecision(Integer precision){
        this.precision = precision;
    }

    /**
	 * Retorna a expressão regular definida.
	 * 
	 * @return String contendo a expressão regular.
	 */
    public String getRegularExpression(){
        return regularExpression;
    }

    /**
     * Define a expressão regular definida.
     * 
     * @param regularExpression String contendo a expressão regular.
     */
    public void setRegularExpression(String regularExpression){
        this.regularExpression = regularExpression;
    }
    
    /**
     * Indica se deve ser usado as configurações adicionais de formatação.
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
     * propriedade especificada na anotação.
     *  
     * @return String contendo o identificador da propriedade.
     */
	public String getClassPropertyId(){
    	return classPropertyId;
    }

    /**
     * Define a propriedade do modelo de dados que identifica o tipo de classe para a 
     * propriedade especificada na anotação.
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
	 * Retorna o identificador do índice ou chave vinculado à propriedade.
	 * 
	 * @return String contendo o identificador do índice ou chave.
	 */
	public String getKeyId(){
    	return keyId;
    }

	/**
	 * Define o identificador do índice ou chave vinculado à propriedade.
	 * 
	 * @param keyId String contendo o identificador do índice ou chave.
	 */
	public void setKeyId(String keyId){
    	this.keyId = keyId;
    }

	/**
	 * Retorna o conteúdo do marcador customizado. 
	 *
	 * @return String contendo o marcador.
	 */
	public String getTag(){
    	return tag;
    }

	/**
	 * Define o conteúdo do marcador customizado. 
	 *
	 * @param tag String contendo o marcador.
	 */
	public void setTag(String tag){
    	this.tag = tag;
    }

	/**
	 * Retorna o tipo de dado da propriedade.
	 *
	 * @return Instância contendo o tipo de dado da propriedade.
	 */
	public Class getPropertyClass(){
		return propertyClass;
	}

	/**
	 * Define o tipo de dado da propriedade.
	 *
	 * @param propertyClass Instância contendo o tipo de dado da propriedade.
	 */
	public void setPropertyClass(Class propertyClass){
		this.propertyClass = propertyClass;
	}

	/**
	 * Retorna o tipo de junção com o relacionamento.
	 *
	 * @return Constante que define o tipo de junção com o relacionamento.
	 */
	public RelationJoinType getRelationJoinType(){
		return relationJoinType;
	}

	/**
	 * Define o tipo de junção com o relacionamento.
	 *
	 * @param relationJoinType Constante que define o tipo de junção com o 
	 * relacionamento.
	 */
	public void setRelationJoinType(RelationJoinType relationJoinType){
		this.relationJoinType = relationJoinType;
	}

	/**
	 * Indica se deve ser feito cascade nas operações de gravação dos dados.
	 * 
	 * @return True/False.
	 */
	public Boolean cascadeOnSave(){
		return cascadeOnSave;
	}

	/**
	 * Indica se deve ser feito cascade nas operações de gravação dos dados.
	 * 
	 * @return True/False.
	 */
	public Boolean getCascadeOnSave(){
		return cascadeOnSave();
	}

	/**
	 * Define se deve ser feito cascade nas operações de gravação dos dados.
	 * 
	 * @param cascadeOnSave True/False.
	 */
	public void setCascadeOnSave(Boolean cascadeOnSave){
		this.cascadeOnSave = cascadeOnSave;
	}

	/**
	 * Indica se deve ser feito cascade nas operações de exclusão dos dados.
	 * 
	 * @return True/False.
	 */
	public Boolean cascadeOnDelete(){
		return cascadeOnDelete;
	}

	/**
	 * Indica se deve ser feito cascade nas operações de exclusão dos dados.
	 * 
	 * @return True/False.
	 */
	public Boolean getCascadeOnDelete(){
		return cascadeOnDelete();
	}

	/**
	 * Define se deve ser feito cascade nas operações de exclusão dos dados.
	 * 
	 * @param cascadeOnDelete True/False.
	 */
	public void setCascadeOnDelete(Boolean cascadeOnDelete){
		this.cascadeOnDelete = cascadeOnDelete;
	}

	/**
	 * Indica que a chave de identificação do modelo de dados deverá ser 
	 * gerada automaticamente.
	 * 
	 * @return True/False.
	 */
	public Boolean autoGenerateIdentity(){
		return autoGenerateIdentity;
	}

	/**
	 * Indica que a chave de identificação do modelo de dados deverá ser 
	 * gerada automaticamente.
	 * 
	 * @return True/False.
	 */
	public Boolean getAutoGenerateIdentity(){
		return autoGenerateIdentity();
	}

	/**
	 * Define que a chave de identificação do modelo de dados deverá ser 
	 * gerada automaticamente.
	 * 
	 * param autoGenerateIdentity True/False.
	 */
	public void setAutoGenerateIdentity(Boolean autoGenerateIdentity){
		this.autoGenerateIdentity = autoGenerateIdentity;
	}

	/**
	 * Retorna o identificador da propriedade que será considerada para 
	 * efetuar pesquisa fonética.
	 * 
	 * @return String contendo o identificador da propriedade.
	 */
	public String getPhoneticPropertyId(){
		return phoneticPropertyId;
	}

	/**
	 * Define o identificador da propriedade que será considerada para 
	 * efetuar pesquisa fonética.
	 * 
	 * @param phoneticPropertyId String contendo o identificador da 
	 * propriedade.
	 */
	public void setPhoneticPropertyId(String phoneticPropertyId){
		this.phoneticPropertyId = phoneticPropertyId;
	}

	/**
	 * Retorna o identificador da propriedade que será considerada para 
	 * efetuar uma pesquisa.
	 * 
	 * @return String contendo o identificador da propriedade.
	 */
	public String getSearchPropertyId(){
		return searchPropertyId;
	}

	/**
	 * Define o identificador da propriedade que será considerada para 
	 * efetuar uma pesquisa.
	 * 
	 * @param searchPropertyId String contendo o identificador da propriedade.
	 */
	public void setSearchPropertyId(String searchPropertyId){
		this.searchPropertyId = searchPropertyId;
	}

	/**
	 * Indica que o separador de milhar deve ser utilizado na formatação.
	 * 
	 * @return True/False.
	 */
	public Boolean getUseGroupSeparator(){
		return useGroupSeparator();
	}

	/**
	 * Indica que a propriedade é única para identificação de um modelo de 
	 * dados.
	 * 
	 * @return True/False.
	 */
	public Boolean isUnique(){
		return isUnique;
	}

	/**
	 * Indica que a propriedade é única para identificação de um modelo de 
	 * dados.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsUnique(){
		return isUnique();
	}

	/**
	 * Define que a propriedade é única para identificação de um modelo de 
	 * dados.
	 * 
	 * @param isUnique True/False.
	 */
	public void setIsUnique(Boolean isUnique){
		this.isUnique = isUnique;
	}

	/**
	 * Retorna o mapeamento da propriedade no repositório de persistência.
	 * 
	 * @return String contendo o mapeamento.
	 */
	public String getMappedPropertyId(){
		return mappedPropertyId;
	}

	/**
	 * Define o mapeamento da propriedade no repositório de persistência.
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
	 * Indica se a propriedade é do tipo modelo de dados.
	 * 
	 * @return True/False.
	 */
	public Boolean isModel(){
		return isModel;
	}

	/**
	 * Indica se a propriedade é do tipo modelo de dados.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsModel(){
		return isModel();
	}

	/**
	 * Define se a propriedade é do tipo modelo de dados.
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
	 * Indica se a propriedade é uma data.
	 * 
	 * @return True/False.
	 */
	public Boolean isDate(){
		return isDate;
	}

	/**
	 * Indica se a propriedade é uma data.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsDate(){
		return isDate();
	}

	/**
	 * Indica se a propriedade é uma data/horário.
	 * 
	 * @return True/False.
	 */
	public Boolean isTime(){
		return isTime;
	}

	/**
	 * Indica se a propriedade é uma data/horário.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsTime(){
		return isTime();
	}

	/**
	 * Indica se a propriedade é um valor booleano.
	 * 
	 * @return True/False.
	 */
	public Boolean isBoolean(){
		return isBoolean;
	}

	/**
	 * Indica se a propriedade é um valor booleano.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsBoolean(){
		return isBoolean();
	}

	/**
	 * Indica se a propriedade é um valor numérico.
	 * 
	 * @return True/False.
	 */
	public Boolean isNumber(){
		return isNumber;
	}

	/**
	 * Indica se a propriedade é um valor numérico.
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
	 * Retorna o tipo da classe dos itens da coleção.
	 * 
	 * @return Classe do tipo dos itens da coleção.
	 */
	public Class getCollectionItemsClass(){
		return collectionItemsClass;
	}
	
	/**
	 * Define o tipo da classe dos itens da coleção.
	 * 
	 * @param collectionItemsClass Classe do tipo dos itens da coleção.
	 */
	public void setCollectionItemsClass(Class collectionItemsClass){
		this.collectionItemsClass = collectionItemsClass;
	}

	/**
	 * Retorna que a propriedade é chave de identificação de um modelo de 
	 * dados.
	 * 
	 * @return True/False.
	 */
	public Boolean isIdentity(){
		return isIdentity;
	}

	/**
	 * Retorna que a propriedade é chave de identificação de um modelo de 
	 * dados.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsIdentity(){
		return isIdentity();
	}

	/**
	 * Define que a propriedade é chave de identificação de um modelo de 
	 * dados.
	 * 
	 * @param isIdentity True/False.
	 */
	public void setIsIdentity(Boolean isIdentity){
		this.isIdentity = isIdentity;
	}

	/**
	 * Indica que a propriedade será considerada para pesquisa.
	 * 
	 * @return True/False.
	 */
	public Boolean isForSearch(){
		return isForSearch;
	}

	/**
	 * Indica que a propriedade será considerada para pesquisa.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsForSearch(){
		return isForSearch();
	}

	/**
	 * Define que a propriedade será considerada para pesquisa.
	 * 
	 * @param isForSearch True/False.
	 */
	public void setIsForSearch(Boolean isForSearch){
		this.isForSearch = isForSearch;
	}

	/**
	 * Retorna o tamanho mínimo que o conteúdo de uma propriedade deverá ter. 
	 * Somente utilizado quando a validação definida for do tipo 'minimumLength'.
	 * 
	 * @return Valor inteiro contendo o tamanho mínimo da propriedade.
	 */
	public Integer getMinimumLength(){
		return minimumLength;
	}

	/**
	 * Define o tamanho mínimo que o conteúdo de uma propriedade deverá ter. 
	 * Somente utilizado quando a validação definida for do tipo 'minimumLength'.
	 * 
	 * @param minimumLength Valor inteiro contendo o tamanho mínimo da 
	 *                      propriedade.
	 */
	public void setMinimumLength(Integer minimumLength){
		this.minimumLength = minimumLength;
	}

	/**
	 * Retorna o tamanho máximo que o conteúdo de uma propriedade deverá ter. 
	 * Somente utilizado quando a validação definida for do tipo 'maximumLength'.
	 * 
	 * @return Valor inteiro contendo o tamanho máximo da propriedade.
	 */
	public Integer getMaximumLength(){
		return maximumLength;
	}

	/**
	 * Define o tamanho máximo que o conteúdo de uma propriedade deverá ter. 
	 * Somente utilizado quando a validação definida for do tipo 
	 * 'maximumLength'.
	 * 
	 * @param maximumLength Valor inteiro contendo o tamanho máximo da 
	 * propriedade.
	 */
	public void setMaximumLength(Integer maximumLength){
		this.maximumLength = maximumLength;
	}

	/**
	 * Retorna a máscara de formatação/validação da propriedade.
	 * 
	 * @return String contendo a máscara de formatação/validação.
	 */
	public String getPattern(){
		return pattern;
	}

	/**
	 * Define a máscara de formatação/validação da propriedade.
	 * 
	 * @param pattern String contendo a máscara de formatação/validação.
	 */
	public void setPattern(String pattern){
		this.pattern = pattern;
	}

	/**
	 * Retorna o identificador da rotina de validação customizada para 
	 * propriedade.
	 * 
	 * @return String contendo o identificador da rotina de validação.
	 */
	public String getCustomValidationId(){
		return customValidationId;
	}

	/**
	 * Define o identificador da rotina de validação customizada para 
	 * propriedade.
	 * 
	 * @param customValidationId String contendo o identificador da rotina de 
	 * validação.
	 */
	public void setCustomValidationId(String customValidationId){
		this.customValidationId = customValidationId;
	}

	/**
	 * Indica que a propriedade será auditada.
	 * 
	 * @return True/False.
	 */
	public Boolean isAuditable(){
		return isAuditable;
	}

	/**
	 * Indica que a propriedade será auditada.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsAuditable(){
		return isAuditable();
	}

	/**
	 * Define que a propriedade será auditada.
	 * 
	 * @param isAuditable True/False.
	 */
	public void setIsAuditable(Boolean isAuditable){
		this.isAuditable = isAuditable;
	}

	/**
	 * Retorna as validações da propriedade.
	 * 
	 * @return Array contendo as validações da propriedade.
	 */
	public ValidationType[] getValidations(){
		return validations;
	}

	/**
	 * Define as validações da propriedade.
	 * 
	 * @param validations Array contendo as validações da propriedade.
	 */
	public void setValidations(ValidationType validations[]){
		this.validations = validations;
	}

	/**
	 * Indica se existe uma validação parametrizada.
	 * 
	 * @param validation Constante que define a validação desejada.
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
	 * Define se a propriedade é um valor booleano.
	 * 
	 * @param isBoolean True/False.
	 */
	public void setIsBoolean(Boolean isBoolean){
		this.isBoolean = isBoolean;
	}

	/**
	 * Define se a propriedade é uma data.
	 * 
	 * @param isDate True/False.
	 */
	public void setIsDate(Boolean isDate){
		this.isDate = isDate;
	}

	/**
	 * Define se a propriedade é uma data/horário.
	 * 
	 * @param isTime True/False.
	 */
	public void setIsTime(Boolean isTime){
		this.isTime = isTime;
	}

	/**
	 * Define se a propriedade é um valor numérico.
	 * 
	 * @param isNumber True/False.
	 */
	public void setIsNumber(Boolean isNumber){
		this.isNumber = isNumber;
	}

	/**
	 * Retorna o tipo de relacionamento.
	 * 
	 * @return Instância contendo o tipo de relacionamento.
	 */
	public RelationType getRelationType(){
		return relationType;
	}

	/**
	 * Define o tipo de relacionamento.
	 * 
	 * @param relationType Instância contendo o tipo de relacionamento.
	 */
	public void setRelationType(RelationType relationType){
		this.relationType = relationType;
	}

	/**
	 * Indica se a propriedade é uma coleção.
	 * 
	 * @return True/False.
	 */
	public Boolean isCollection(){
		return isCollection;
	}

	/**
	 * Indica se a propriedade é uma coleção.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsCollection(){
		return isCollection();
	}

	/**
	 * Define se a propriedade é uma coleção.
	 * 
	 * @param isCollection True/False.
	 */
	public void setIsCollection(Boolean isCollection){
		this.isCollection = isCollection;
	}

	/**
	 * Indica se a propriedade é um array de bytes.
	 * 
	 * @return True/False.
	 */
	public Boolean isByteArray(){
		return isByteArray;
	}

	/**
	 * Indica se a propriedade é um array de bytes.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsByteArray(){
		return isByteArray();
	}

	/**
	 * Define se a propriedade é um array de bytes.
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
	 * Indica se a propriedade é uma constante.
	 * 
	 * @return True/False.
	 */
	public Boolean isEnum(){
		return isEnum;
	}

	/**
	 * Indica se a propriedade é uma constante.
	 * 
	 * @return True/False.
	 */
	public Boolean getIsEnum(){
		return isEnum();
	}

	/**
	 * Define se a propriedade é uma constante.
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
	 * Define o identificador do repositório para relacionamentos NxN.
	 * 
	 * @param mappedRelationRepositoryId String contendo o identificador do 
	 * repositório.
	 */
	public void setMappedRelationRepositoryId(String mappedRelationRepositoryId){
		this.mappedRelationRepositoryId = mappedRelationRepositoryId;
	}

	/**
	 * Retorna a condição de pesquisa da propriedade. Somente será 
	 * considerada quando a propriedade for de pesquisa.
	 * 
	 * @return Instância contendo a condição de pesquisa.
	 */
	public ConditionType getSearchCondition(){
		return searchCondition;
	}

	/**
	 * Define a condição de pesquisa da propriedade. Somente será considerada 
	 * quando a propriedade for de pesquisa.
	 * 
	 * @param searchCondition Instância contendo a condição de pesquisa.
	 */
	public void setSearchCondition(ConditionType searchCondition){
		this.searchCondition = searchCondition;
	}

	/**
	 * Retorna o identificador do repositório para relacionamentos NxN.
	 * 
	 * @return String contendo o identificador do repositório.
	 */
	public String getMappedRelationRepositoryId(){
		return mappedRelationRepositoryId;
	}

	/**
	 * Retorna os tipos de pesquisa por contexto. Somente será considerada 
	 * quando a propriedade searchCondition for do tipo CONTEXT.
	 * 
	 * @return Instância contendo os tipos de pesquisa por contexto.
	 */
	public ContextSearchType getContextSearchType(){
		return contextSearchType;
	}

	/**
	 * Define os tipos de pesquisa por contexto. Somente será considerada 
	 * quando a propriedade searchCondition for do tipo CONTEXT.
	 * 
	 * @param contextSearchType Instância contendo os tipos de pesquisa por 
	 * contexto.
	 */
	public void setContextSearchType(ContextSearchType contextSearchType){
		this.contextSearchType = contextSearchType;
	}

	/**
	 * Indica se a pesquisa será sensível a letras maiúsculas/minúsculas.
	 * 
	 * @return True/False.
	 */
	public Boolean isCaseSensitiveSearch(){
		return caseSensitiveSearch;
	}

	/**
	 * Indica se a pesquisa será sensível a letras maiúsculas/minúsculas.
	 * 
	 * @return True/False.
	 */
	public Boolean getCaseSensitiveSearch(){
		return isCaseSensitiveSearch();
	}

	/**
	 * Define se a pesquisa será sensível a letras maiúsculas/minúsculas.
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
	 * Indica que o separador de milhar deve ser utilizado na formatação.
	 * 
	 * @return True/False.
	 */
	public Boolean useGroupSeparator(){
		return useGroupSeparator;
	}

	/**
	 * Define que o separador de milhar deve ser utilizado na formatação.
	 * 
	 * @param useGroupSeparator True/False.
	 */
	public void setUseGroupSeparator(Boolean useGroupSeparator){
		this.useGroupSeparator = useGroupSeparator;
	}

	/**
	 * Retorna uma fórmula para a propriedade.
	 * 
	 * @return String contendo a fórmula.
	 */
	public String getFormula(){
		return formula;
	}

	/**
	 * Define uma fórmula para a propriedade.
	 * 
	 * @param formula String contendo a fórmula.
	 */
	public void setFormula(String formula){
		this.formula = formula;
	}

	/**
	 * Retorna o tipo de ordenação a ser utilizada.
	 * 
	 * @return Instância contendo o tipo de ordenação.
	 */
	public SortOrderType getSortOrder(){
		return sortOrder;
	}

	/**
	 * Define o tipo de ordenação a ser utilizada.
	 * 
	 * @param sortOrder Instância contendo o tipo de ordenação.
	 */
	public void setSortOrder(SortOrderType sortOrder){
		this.sortOrder = sortOrder;
	}

	/**
	 * Retorna a porcentagem de similaridade a ser utilizada quando a 
	 * condição de pesquisa definida for por similaridade.
	 * 
	 * @return Valor em ponto flutuante contendo a porcentagem de 
	 * similaridade.
	 */
	public Double getSimilarityAccuracy(){
		return similarityAccuracy;
	}

	/**
	 * Define a porcentagem de similaridade a ser utilizada quando a condição 
	 * de pesquisa definida for por similaridade.
	 * 
	 * @param similarityAccuracy Valor em ponto flutuante contendo a 
	 * porcentagem de similaridade.
	 */
	public void setSimilarityAccuracy(Double similarityAccuracy){
		this.similarityAccuracy = similarityAccuracy;
	}

	/**
	 * Retorna o identificador da propriedade a ser populada no repositório 
	 * de persistência.
	 * 
	 * @return String contendo o mapeamento.
	 */
	public String getPropertyId(){
		return propertyId;
	}

	/**
	 * Define o identificador da propriedade a ser populada no repositório 
	 * de persistência.
	 * 
	 * @param propertyId String contendo o mapeamento.
	 */
	public void setPropertyId(String propertyId){
		this.propertyId = propertyId;
	}

	/**
	 * Retorna o 'range' final da propriedade. Somente utilizado quando a 
	 * validação definida for do tipo 'range'.
	 * 
	 * @return String contendo o valor do 'range' final.
	 */
	public String getEndRange(){
		return endRange;
	}

	/**
	 * Define o 'range' final da propriedade. Somente utilizado quando a 
	 * validação definida for do tipo 'range'.
	 * 
	 * @param endRange String contendo o valor do 'range' final.
	 */
	public void setEndRange(String endRange){
		this.endRange = endRange;
	}

	/**
	 * Retorna o 'range' inicial da propriedade. Somente utilizado quando a 
	 * validação definida for do tipo 'range'.
	 * 
	 * @return String contendo o valor do 'range' inicial.
	 */
	public String getStartRange(){
		return startRange;
	}

	/**
	 * Define o 'range' inicial da propriedade. Somente utilizado quando a 
	 * validação definida for do tipo 'range'.
	 * 
	 * @param startRange String contendo o valor do 'range' inicial.
	 */
	public void setStartRange(String startRange){
		this.startRange = startRange;
	}

	/**
	 * Indica se a máscara de formatação será persistida na propriedade.
	 * 
	 * @return True/False.
	 */
	public Boolean persistPattern(){
		return persistPattern;
	}

	/**
	 * Indica se a máscara de formatação será persistida na propriedade.
	 * 
	 * @return True/False.
	 */
	public Boolean getPersistPattern(){
		return persistPattern();
	}

	/**
	 * Define se a máscara de formatação será persistida na propriedade.
	 * 
	 * @param persistPattern True/False.
	 */
	public void setPersistPattern(Boolean persistPattern){
		this.persistPattern = persistPattern;
	}

	/**
	 * Retorna a condição de comparação da propriedade. Utilizado quando a 
	 * validação definida for do tipo 'compare'.
	 * 
	 * @return Instância contendo o tipo de comparação.
	 */
	public ConditionType getCompareCondition(){
		return compareCondition;
	}

	/**
	 * Define a condição de comparação da propriedade. Utilizado quando a 
	 * validação definida for do tipo 'compare'.
	 * 
	 * @param compareCondition Instância contendo o tipo de comparação.
	 */
	public void setCompareCondition(ConditionType compareCondition){
		this.compareCondition = compareCondition;
	}

	/**
	 * Retorna o identificador da propriedade de comparação. Somente 
	 * utilizado quando a validação definida for do tipo 'compare'.
	 * 
	 * @return String contendo o identificador da propriedade.
	 */
	public String getComparePropertyId(){
		return comparePropertyId;
	}

	/**
	 * Define o identificador da propriedade de comparação. Somente utilizado 
	 * quando a validação definida for do tipo 'compare'.
	 * 
	 * @param comparePropertyId String contendo o identificador da 
	 * propriedade.
	 */
	public void setComparePropertyId(String comparePropertyId){
		this.comparePropertyId = comparePropertyId;
	}

	/**
	 * Retorna a quantidade máxima de palavras a ser validada quando a 
	 * validação for do tipo 'wordCount'.
	 * 
	 * @return Valor inteiro contendo a quantidade máxima de palavras.
	 */
	public Integer getWordCount(){
		return wordCount;
	}

	/**
	 * Define a quantidade máxima de palavras a ser validada quando a 
	 * validação for do tipo 'wordCount'.
	 * 
	 * @param wordCount Valor inteiro contendo a quantidade máxima de 
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