package br.com.concepting.framework.model.helpers;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import br.com.concepting.framework.caching.CachedObject;
import br.com.concepting.framework.model.annotations.Property;
import br.com.concepting.framework.model.types.ValidationType;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.service.types.ServiceType;

/**
 * Classe que armazena as informações, em cache, de um modelo de dados.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class ModelInfo extends CachedObject{
	private Class              clazz                    = null;
	private List<PropertyInfo> propertiesInfo           = null;
	private List<PropertyInfo> identityPropertiesInfo   = null;
	private List<PropertyInfo> uniquePropertiesInfo     = null;
	private List<PropertyInfo> searchPropertiesInfo     = null;
	private List<PropertyInfo> validationPropertiesInfo = null;
	private List<PropertyInfo> auditablePropertiesInfo  = null;
	private Property           mappedProperties[]       = null;
    private String             mappedRepositoryId       = "";
	private String             persistenceResourceId    = "";
	private Class              validatorClass           = null;
	private String             useCase                  = "";
	private String             templateId               = "";
	private String             descriptionPattern       = "";
	private Boolean            generatePersistence      = false;
	private Boolean            generateService          = false;
	private ServiceType        serviceType              = ServiceType.CLASS;
	
	/**
	 * Retorna o identificador das configurações de persistência vinculada ao
	 * modelo de dados.
	 * 
	 * @return String contendo o identificador da configuração de persistência.
	 */
	public String getPersistenceResourceId(){
        return persistenceResourceId;
    }

    /**
     * Define o identificador das configurações de persistência vinculada ao
     * modelo de dados.
     * 
     * @param persistenceResourceId String contendo o identificador da configuração de persistência.
     */
    public void setPersistenceResourceId(String persistenceResourceId){
        this.persistenceResourceId = persistenceResourceId;
    }

    /**
	 * Retorna o tipo de serviço a ser usado para geração de código.
	 * 
	 * @return Constante que define o tipo de serviço.
	 */
	public ServiceType getServiceType(){
        return serviceType;
    }

    /**
     * Define o tipo de serviço a ser usado para geração de código.
     * 
     * @param serviceType Constante que define o tipo de serviço.
     */
    public void setServiceType(ServiceType serviceType){
        this.serviceType = serviceType;
    }

    /**
	 * Retorna o identificador do template a ser usado para geração de código.
	 *
	 * @return String contendo o identificador do template.
	 */
	public String getTemplateId(){
    	return templateId;
    }

	/**
	 * Define o identificador do template a ser usado para geração de código.
	 *
	 * @param templateId String contendo o identificador do template.
	 */
	public void setTemplateId(String templateId){
    	this.templateId = templateId;
    }

	/**
	 * Retorna a lista de propriedades que definem um único modelo de dados.
	 *
	 * @return Lista contendo as propriedades que definem um único modelo de dados.
	 */
	public List<PropertyInfo> getUniquePropertiesInfo(){
     	return uniquePropertiesInfo;
    }

	/**
	 * Define a lista de propriedades que definem um único modelo de dados.
	 *
	 * @param uniquePropertiesInfo Lista contendo as propriedades que definem um único modelo 
	 * de dados.
	 */
	public void setUniquePropertiesInfo(List<PropertyInfo> uniquePropertiesInfo){
     	this.uniquePropertiesInfo = uniquePropertiesInfo;
    }

	/**
	 * Retorna a classe do modelo de dados.
	 * 
	 * @return Classe do modelo de dados.
	 */
	public Class getClazz(){
		return clazz;
	}

	/**
	 * Define a classe do modelo de dados.
	 * 
	 * @param clazz Classe do modelo de dados.
	 */
	public void setClazz(Class clazz){
		this.clazz = clazz;
	}

	/**
	 * Retorna a lista contendo as informações de todas as propriedades do modelo de dados.
	 * 
	 * @return Lista contendo as informações das propriedades.
	 */
	public List<PropertyInfo> getPropertiesInfo(){
		return propertiesInfo;
	}

	/**
	 * Define a lista contendo as informações de todas as propriedades do modelo de dados.
	 * 
	 * @param propertiesInfo Lista contendo as informações das propriedades.
	 */
	public void setPropertiesInfo(List<PropertyInfo> propertiesInfo){
		this.propertiesInfo = propertiesInfo;

		identityPropertiesInfo   = new LinkedList<PropertyInfo>();
		uniquePropertiesInfo     = new LinkedList<PropertyInfo>();
		searchPropertiesInfo     = new LinkedList<PropertyInfo>();
		validationPropertiesInfo = new LinkedList<PropertyInfo>();
		auditablePropertiesInfo  = new LinkedList<PropertyInfo>();

		for(PropertyInfo propertyInfo : propertiesInfo){
			if(propertyInfo.isIdentity())
				identityPropertiesInfo.add(propertyInfo);

			if(propertyInfo.isUnique())
				uniquePropertiesInfo.add(propertyInfo);

			if(propertyInfo.isForSearch())
				searchPropertiesInfo.add(propertyInfo);

			if(propertyInfo.isAuditable())
				auditablePropertiesInfo.add(propertyInfo);

			if(propertyInfo.getValidations() != null && propertyInfo.getValidations().length > 0 && propertyInfo.getValidations()[0] != ValidationType.NONE)
				validationPropertiesInfo.add(propertyInfo);
		}

		if(identityPropertiesInfo.size() > 0)
			setIdentityPropertiesInfo(identityPropertiesInfo);

		if(uniquePropertiesInfo.size() > 0)
			setUniquePropertiesInfo(uniquePropertiesInfo);

		if(searchPropertiesInfo.size() > 0)
			setSearchPropertiesInfo(searchPropertiesInfo);

		if(validationPropertiesInfo.size() > 0)
			setValidationPropertiesInfo(validationPropertiesInfo);

		if(auditablePropertiesInfo.size() > 0)
			setAuditablePropertiesInfo(auditablePropertiesInfo);
	}

	/**
	 * Retorna a lista contendo as informações das propriedades chaves do modelo de dados.
	 * 
	 * @return Lista contendo as informações das propriedades.
	 */
	public List<PropertyInfo> getIdentityPropertiesInfo(){
		return identityPropertiesInfo;
	}

	/**
	 * Define a lista contendo as informações das propriedades chaves do modelo de dados.
	 * 
	 * @param identityPropertiesInfo Lista contendo as informações das propriedades.
	 */
	public void setIdentityPropertiesInfo(List<PropertyInfo> identityPropertiesInfo){
		this.identityPropertiesInfo = identityPropertiesInfo;
	}

	/**
	 * Retorna a lista contendo as informações das propriedades de pesquisa do modelo de dados.
	 * 
	 * @return Lista contendo as informações das propriedades.
	 */
	public Collection<PropertyInfo> getSearchPropertiesInfo(){
		return searchPropertiesInfo;
	}

	/**
	 * Define a lista contendo as informações das propriedades de pesquisa do modelo de dados.
	 * 
	 * @param searchPropertiesInfo Lista contendo as informações das propriedades.
	 */
	public void setSearchPropertiesInfo(List<PropertyInfo> searchPropertiesInfo){
		this.searchPropertiesInfo = searchPropertiesInfo;
	}

	/**
	 * Retorna a lista contendo as informações das propriedades para validação do modelo de 
	 * dados.
	 * 
	 * @return Lista contendo as informações das propriedades.
	 */
	public List<PropertyInfo> getValidationPropertiesInfo(){
		return validationPropertiesInfo;
	}

	/**
	 * Define a lista contendo as informações das propriedades para validação do modelo de 
	 * dados.
	 * 
	 * @param validationPropertiesInfo Lista contendo as informações das propriedades.
	 */
	public void setValidationPropertiesInfo(List<PropertyInfo> validationPropertiesInfo){
		this.validationPropertiesInfo = validationPropertiesInfo;
	}

	/**
	 * Retorna a lista contendo as informações das propriedades auditáveis do modelo de dados.
	 * 
	 * @return Lista contendo as informações das propriedades.
	 */
	public List<PropertyInfo> getAuditablePropertiesInfo(){
		return auditablePropertiesInfo;
	}

	/**
	 * Define a lista contendo as informações das propriedades auditáveis do modelo de dados.
	 * 
	 * @param auditablePropertiesInfo Lista contendo as informações das propriedades.
	 */
	public void setAuditablePropertiesInfo(List<PropertyInfo> auditablePropertiesInfo){
		this.auditablePropertiesInfo = auditablePropertiesInfo;
	}

	/**
	 * Retorna a instância contendo as informações (em cache) de uma propriedade específica.
	 * 
	 * @param propertyId String contendo o identificador da propriedade.
	 * @return Instância contendo as informações da propriedade desejada.
	 */
	public PropertyInfo getPropertyInfo(String propertyId){
		if(propertiesInfo != null){
			ModelInfo modelInfo        = null;
			Integer   pos              = propertyId.indexOf(".");
			String    propertyIdBuffer = (pos >= 0 ? propertyId.substring(0, pos) : propertyId);

			for(PropertyInfo propertyInfo : propertiesInfo){
				if(propertyIdBuffer.equals(propertyInfo.getId())){
					if(propertyInfo.isModel() && pos >= 0){
						modelInfo = ModelUtil.getModelInfo(propertyInfo.getClazz());
						
						if(modelInfo != null){
							propertyIdBuffer = propertyId.substring(pos + 1);

							return modelInfo.getPropertyInfo(propertyIdBuffer);
						}
					}
					else{
						if(propertyIdBuffer.equals(propertyId))
							return propertyInfo;
					}
				}
			}
		}

		return null;
	}

	/**
	 * Retorna a classe que implementa a validação do modelo de dados.
	 * 
	 * @return Classe que implementa a validação.
	 */
	public Class getValidatorClass(){
		return validatorClass;
	}

	/**
	 * Define a classe que implementa a validação do modelo de dados.
	 * 
	 * @param validatorClass Classe que implementa a validação.
	 */
	public void setValidatorClass(Class validatorClass){
		this.validatorClass = validatorClass;
	}

	/**
	 * Indica que o modelo de dados possui propriedades de validação,
	 * 
	 * @return True/False.
	 */
	public Boolean hasPropertiesValidation(){
		return (validationPropertiesInfo != null && validationPropertiesInfo.size() > 0);
	}

	/**
	 * Retorna o identificador do repositório para persistência do modelo de dados.
	 * 
	 * @return String contendo o identificador do repositório.
	 */
	public String getMappedRepositoryId(){
		return mappedRepositoryId;
	}

	/**
	 * Define o identificador do repositório para persistência do modelo de dados.
	 * 
	 * @param mappedRepositoryId String contendo o identificador do repositório.
	 */
	public void setMappedRepositoryId(String mappedRepositoryId){
		this.mappedRepositoryId = mappedRepositoryId;
	}

	/**
	 * Retorna o identificador do caso de uso vinculado ao modelo de dados.
	 * 
	 * @return String contendo o identificador do caso de uso.
	 */
	public String getUseCase(){
		return useCase;
	}

	/**
	 * Define o identificador do caso de uso vinculado ao modelo de dados.
	 * 
	 * @param useCase String contendo o identificador do caso de uso.
	 */
	public void setUseCase(String useCase){
		this.useCase = useCase;
	}
	
	/**
	 * Retorna um array contendo as anotações das propriedades do modelo de dados.
	 * Geralmente utilizado em casos de herança entre modelos genéricos.
	 * 
	 * @return Array contendo as anotações das propriedades do modelo de dados.
	 */
	public Property[] getMappedProperties(){
		return mappedProperties;
	}

	/**
	 * Define um array contendo as anotações das propriedades do modelo de dados.
	 * Geralmente utilizado em casos de herança entre modelos genéricos.
	 * 
	 * @param mappedProperties Array contendo as anotações das propriedades do modelo de dados.
	 */
	public void setMappedProperties(Property[] mappedProperties){
		this.mappedProperties = mappedProperties;
	}

	/**
	 * Retorna a máscara que descreve um modelo de dados.
	 *
	 * @return String contendo a máscara que descreve um modelo de dados.
	 */
	public String getDescriptionPattern(){
		return descriptionPattern;
	}

	/**
	 * Define a máscara que descreve um modelo de dados.
	 *
	 * @param descriptionPattern String contendo a máscara que descreve um modelo de dados.
	 */
	public void setDescriptionPattern(String descriptionPattern){
		this.descriptionPattern = descriptionPattern;
	}

	/**
	 * Indica se as classes de persistência devem ser geradas.
	 *
	 * @return True/False.
	 */
	public Boolean getGeneratePersistence(){
    	return generatePersistence;
    }

	/**
	 * Define se as classes de persistência devem ser geradas.
	 *
	 * @param generatePersistence True/False.
	 */
	public void setGeneratePersistence(Boolean generatePersistence){
    	this.generatePersistence = generatePersistence;
    }

	/**
	 * Indica se as classes de serviço devem ser geradas.
	 *
	 * @return True/False.
	 */
	public Boolean getGenerateService(){
    	return generateService;
    }

	/**
	 * Define se as classes de serviço devem ser geradas.
	 *
	 * @param generateService True/False.
	 */
	public void setGenerateService(Boolean generateService){
    	this.generateService = generateService;
    }
}