package br.com.concepting.framework.security.model;

import java.util.List;

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
 * Classe que define o modelo de dados de um grupo de usuários.
 * 
 * @author fvilarinho
 * @since 1.0
 */
@Model
public class GroupModel extends BaseModel{
	@Property(isIdentity = true, isForSearch = true)
	private Integer id = 0;

	@Property(isForSearch = true, searchCondition = ConditionType.CONTEXT, contextSearchType = ContextSearchType.BOTH, validations = ValidationType.REQUIRED)
	private String title = "";

	@Property(isForSearch = true, searchCondition = ConditionType.CONTEXT, contextSearchType = ContextSearchType.BOTH)
	private String description = "";

	@Property(relationType = RelationType.MANY_TO_MANY)
	private List<UserModel> users = null;

	@Property(relationType = RelationType.MANY_TO_MANY)
	private List<ObjectModel> objects = null;
	
	/**
	 * Retorna os usuários vinculados ao grupo.
	 * 
	 * @return Lista contendo os usuários do grupo.
	 */
    public <U extends UserModel> List<U> getUsers(){
    	return (List<U>)users;
    }

	/**
	 * Define os usuários vinculados ao grupo.
	 * 
	 * @param users Lista contendo os usuários do grupo.
	 */
    public <U extends UserModel> void setUsers(List<U> users){
    	this.users = (List)users;
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
	 * Retorna o título do grupo.
	 * 
	 * @return String contendo o título do grupo.
	 */
	public String getTitle(){
		return title;
	}

	/**
	 * Define o título do grupo.
	 * 
	 * @param title String contendo o título do grupo.
	 */
	public void setTitle(String title){
		this.title = title;
	}
	
	/**
	 * Retorna a descrição do grupo.
	 * 
	 * @return String contendo a descrição.
	 */
	public String getDescription(){
    	return description;
    }

	/**
	 * Define a descrição do grupo.
	 * 
	 * @param description String contendo a descrição.
	 */
	public void setDescription(String description){
    	this.description = description;
    }
	
	/**
	 * Retorna os objetos de um ou mais formulários vinculados ao grupo.
	 * 
	 * @return Lista contendo os objetos vinculados.
	 */
    public <O extends ObjectModel> List<O> getObjects(){
    	return (List<O>)objects;
    }

	/**
	 * Define os objetos de um ou mais formulários vinculados ao grupo.
	 * 
	 * @param objects Lista contendo os objetos vinculados.
	 */
    public <O extends ObjectModel> void setObjects(List<O> objects){
    	this.objects = (List)objects;
    }

	/**
	 * Verifica se o grupo possui uma permissão para um objeto específico.
	 * 
	 * @param compareObject Instância contendo as propriedades do objeto.
	 * @return True/False.
	 */
	public Boolean hasPermission(ObjectModel compareObject){
		return (objects.contains(compareObject));
	}

	/**
	 * Verifica se o grupo possui permissões.
	 * 
	 * @return True/False.
	 */
	public Boolean hasPermissions(){
		return (objects != null && objects.size() > 0);
	}

	/**
	 * Verifica se o grupo possui permissões para um módulo do sistema específico.
	 * 
	 * @param compareSystemModule Instância contendo o módulo do sistema.
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