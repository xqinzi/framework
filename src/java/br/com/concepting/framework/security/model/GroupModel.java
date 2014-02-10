package br.com.concepting.framework.security.model;

import java.util.Collection;

import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.FormModel;
import br.com.concepting.framework.model.ObjectModel;
import br.com.concepting.framework.model.SystemModuleModel;
import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;
import br.com.concepting.framework.model.types.ConditionType;
import br.com.concepting.framework.model.types.ContextSearchType;
import br.com.concepting.framework.model.types.ValidationType;
import br.com.concepting.framework.persistence.types.RelationType;

/**
 * Classe que define o modelo de dados de um grupo de usu�rios.
 * 
 * @author fvilarinho
 * @since 1.0
 */
@Model
public class GroupModel extends BaseModel{
    private static final long serialVersionUID = -5618172953127972175L;

    @Property(isIdentity = true, isForSearch = true)
	private Integer id = 0;

	@Property(isForSearch = true, searchCondition = ConditionType.CONTEXT, contextSearchType = ContextSearchType.BOTH, validations = ValidationType.REQUIRED)
	private String title = "";

	@Property(isForSearch = true, searchCondition = ConditionType.CONTEXT, contextSearchType = ContextSearchType.BOTH)
	private String description = "";

	@Property(relationType = RelationType.MANY_TO_MANY)
	private Collection<? extends UserModel> users = null;

	@Property(relationType = RelationType.MANY_TO_MANY)
	private Collection<? extends ObjectModel> objects = null;
	
	/**
	 * Retorna os usu�rios vinculados ao grupo.
	 * 
	 * @return Lista contendo os usu�rios do grupo.
	 */
    @SuppressWarnings("unchecked")
    public <U extends UserModel, C extends Collection<U>> C getUsers(){
    	return (C)users;
    }

	/**
	 * Define os usu�rios vinculados ao grupo.
	 * 
	 * @param users Lista contendo os usu�rios do grupo.
	 */
    public void setUsers(Collection<UserModel> users){
    	this.users = users;
    }

	/**
	 * Retorna o identificador do grupo.
	 * 
	 * @return Valor inteiro contendo o identificador do grupo.
	 */
	public Integer getId(){
		return id;
	}

	/**
	 * Define o identificador do grupo.
	 * 
	 * @param id Valor inteiro contendo o identificador do grupo.
	 */
	public void setId(Integer id){
		this.id = id;
	}

	/**
	 * Retorna o t�tulo do grupo.
	 * 
	 * @return String contendo o t�tulo do grupo.
	 */
	public String getTitle(){
		return title;
	}

	/**
	 * Define o t�tulo do grupo.
	 * 
	 * @param title String contendo o t�tulo do grupo.
	 */
	public void setTitle(String title){
		this.title = title;
	}
	
	/**
	 * Retorna a descri��o do grupo.
	 * 
	 * @return String contendo a descri��o.
	 */
	public String getDescription(){
    	return description;
    }

	/**
	 * Define a descri��o do grupo.
	 * 
	 * @param description String contendo a descri��o.
	 */
	public void setDescription(String description){
    	this.description = description;
    }
	
	/**
	 * Retorna os objetos de um ou mais formul�rios vinculados ao grupo.
	 * 
	 * @return Lista contendo os objetos vinculados.
	 */
    @SuppressWarnings("unchecked")
    public <O extends ObjectModel, C extends Collection<O>> C getObjects(){
    	return (C)objects;
    }

	/**
	 * Define os objetos de um ou mais formul�rios vinculados ao grupo.
	 * 
	 * @param objects Lista contendo os objetos vinculados.
	 */
    public void setObjects(Collection<ObjectModel> objects){
    	this.objects = objects;
    }

	/**
	 * Verifica se o grupo possui uma permiss�o para um objeto espec�fico.
	 * 
	 * @param compareObject Inst�ncia contendo as propriedades do objeto.
	 * @return True/False.
	 */
	public Boolean hasPermission(ObjectModel compareObject){
		return (objects.contains(compareObject));
	}

	/**
	 * Verifica se o grupo possui permiss�es.
	 * 
	 * @return True/False.
	 */
	public Boolean hasPermissions(){
		return (objects != null && objects.size() > 0);
	}

	/**
	 * Verifica se o grupo possui permiss�es para um m�dulo do sistema espec�fico.
	 * 
	 * @param compareSystemModule Inst�ncia contendo o m�dulo do sistema.
	 * @return True/False.
	 */
	public Boolean hasPermission(SystemModuleModel compareSystemModule){
		if(objects != null && objects.size() > 0){
			FormModel         form         = null;
			SystemModuleModel systemModule = null;

			for(ObjectModel object : objects){
				form = object.getForm();

				if(form != null){
					systemModule = form.getSystemModule();

					if(systemModule.equals(compareSystemModule))
						return true;
				}
			}
		}

		return false;
	}
}