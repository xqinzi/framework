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
	private AuditorModel model = null;
	
	@Property(isIdentity=true)
	private String clazz = "";
	
	@Property(isIdentity=true)
	private String id = "";
	
	@Property
	private String type = "";
		
	@Property
	private Object value = null;
	
    /**
     * Retorna a instância do modelo de dados que armazena o registro de auditoria.
     *
     * @return Instância do modelo de dados.
     */
    public AuditorModel getModel(){
        return model;
    }

    /**
     * Define a instância do modelo de dados que armazena o registro de auditoria.
     *
     * @param model Instância do modelo de dados.
     */
    public void setModel(AuditorModel model){
        this.model = model;
    }
	
    /**
     * Retorna o identificador da classe do modelo de dados auditado.
     *
     * @return String contendo o identificador da classe do modelo de dados. 
     */
	public String getClazz(){
        return clazz;
    }

    /**
     * Define o identificador da classe do modelo de dados auditado.
     *
     * @param clazz String contendo o identificador da classe do modelo de dados. 
     */
    public void setClazz(String clazz){
        this.clazz = clazz;
    }

	/**
	 * Retorna o identificador da propriedade auditada.
	 *
	 * @return String contendo o identificador da propriedade. 
	 */
	public String getId(){
    	return id;
    }

    /**
     * Define o identificador da propriedade auditada.
     *
     * @param id String contendo o identificador da propriedade. 
     */
	public void setId(String id){
    	this.id = id;
    }

	/**
	 * Retorna o identificador do tipo de dados da propriedade auditada.
	 *
	 * @return String contendo o identificador do tipo de dados. 
	 */
	public String getType(){
    	return type;
    }

    /**
     * Define o identificador do tipo de dados da propriedade auditada.
     *
     * @param type String contendo o identificador do tipo de dados. 
     */
	public void setType(String type){
    	this.type = type;
    }

	/**
	 * Retorna o valor da propriedade auditada.
	 *
	 * @return Instância do valor da propriedade. 
	 */
    public <O> O getValue(){
    	return (O)value;
    }

    /**
     * Define o valor da propriedade auditada.
     *
     * @param value Instância do valor da propriedade. 
     */
	public <O> void setValue(O value){
    	this.value = value;
    }
}