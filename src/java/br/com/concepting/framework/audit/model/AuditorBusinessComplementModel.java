package br.com.concepting.framework.audit.model;

import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;

/**
 * Classe que define o modelo de dados que armazenas os complementos de auditoria.
 * 
 * @author fvilarinho
 * @since 1.0
 */
@Model
public class AuditorBusinessComplementModel extends BaseModel{
	@Property(isIdentity=true)
	private AuditorModel auditor = null;
	
	@Property(isIdentity=true)
	private String modelClass = "";
	
	@Property(isIdentity=true)
	private String propertyId = "";
	
	@Property
	private String propertyType = "";
		
	@Property
	private Object propertyValue = null;
	
    /**
     * Retorna a instância do modelo de dados que armazena o registro de auditoria.
     *
     * @return Instância do modelo de dados.
     */
    public AuditorModel getAuditor(){
        return auditor;
    }

    /**
     * Define a instância do modelo de dados que armazena o registro de auditoria.
     *
     * @param auditor Instância do modelo de dados.
     */
    public void setAuditor(AuditorModel auditor){
        this.auditor = auditor;
    }
	
    /**
     * Retorna o identificador da classe do modelo de dados auditado.
     *
     * @return String contendo o identificador da classe do modelo de dados. 
     */
	public String getModelClass(){
        return modelClass;
    }

    /**
     * Define o identificador da classe do modelo de dados auditado.
     *
     * @param modelClass String contendo o identificador da classe do modelo de dados. 
     */
    public void setModelClass(String modelClass){
        this.modelClass = modelClass;
    }

	/**
	 * Retorna o identificador da propriedade auditada.
	 *
	 * @return String contendo o identificador da propriedade. 
	 */
	public String getPropertyId(){
    	return propertyId;
    }

    /**
     * Define o identificador da propriedade auditada.
     *
     * @param propertyId String contendo o identificador da propriedade. 
     */
	public void setPropertyId(String propertyId){
    	this.propertyId = propertyId;
    }

	/**
	 * Retorna o identificador do tipo de dados da propriedade auditada.
	 *
	 * @return String contendo o identificador do tipo de dados. 
	 */
	public String getPropertyType(){
    	return propertyType;
    }

    /**
     * Define o identificador do tipo de dados da propriedade auditada.
     *
     * @param propertyType String contendo o identificador do tipo de dados. 
     */
	public void setPropertyType(String propertyType){
    	this.propertyType = propertyType;
    }

	/**
	 * Retorna o valor da propriedade auditada.
	 *
	 * @return Instância do valor da propriedade. 
	 */
    public <O> O getPropertyValue(){
    	return (O)propertyValue;
    }

    /**
     * Define o valor da propriedade auditada.
     *
     * @param propertyValue Instância do valor da propriedade. 
     */
	public <O> void setPropertyValue(O propertyValue){
    	this.propertyValue = propertyValue;
    }
}