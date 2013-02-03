package br.com.concepting.framework.model;

import java.util.List;

import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;
import br.com.concepting.framework.model.types.ConditionType;
import br.com.concepting.framework.model.types.ContextSearchType;
import br.com.concepting.framework.model.types.ValidationType;
import br.com.concepting.framework.persistence.types.RelationJoinType;
import br.com.concepting.framework.persistence.types.RelationType;
import br.com.concepting.framework.util.types.SortOrderType;

/**
 * Classe que define a estrutura básica para o modelo de dados de um formulário de um sistema.
 * 
 * @author fvilarinho
 * @since 1.0
 */
@Model
public class FormModel extends BaseModel{
	@Property(isIdentity = true, isForSearch = true)
	private Integer id = 0;

	@Property(isForSearch = true, relationType = RelationType.ONE_TO_ONE, relationJoinType = RelationJoinType.INNER, validations = ValidationType.REQUIRED)
	private SystemModuleModel systemModule = null;

	@Property(isForSearch = true, searchCondition = ConditionType.CONTEXT, contextSearchType = ContextSearchType.BOTH, validations = ValidationType.REQUIRED)
	private String name = "";

	@Property(isForSearch = true, searchCondition = ConditionType.CONTEXT, contextSearchType = ContextSearchType.BOTH, validations = ValidationType.REQUIRED, sortOrder = SortOrderType.ASCEND)
	private String title = "";

	@Property(relationType = RelationType.ONE_TO_MANY)
	private List<ObjectModel> objects = null;

	/**
	 * Retorna o identificador do formulário.
	 * 
	 * @return Valor inteiro contendo o identificador.
	 */
	public Integer getId(){
		return id;
	}

	/**
	 * Define o identificador do formulário.
	 * 
	 * @param id Valor inteiro contendo o identificador.
	 */
	public void setId(Integer id){
		this.id = id;
	}

	/**
	 * Retorna o nome do formulário.
	 * 
	 * @return String contendo nome do formulário.
	 */
	public String getName(){
		return name;
	}

	/**
	 * Define o nome do formulário.
	 * 
	 * @param name String contendo nome do formulário.
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * Retorna a lista de objetos do formulário.
	 * 
	 * @return Instância contendo a lista de objetos do formulário.
	 */
    public <O extends ObjectModel> List<O> getObjects(){
		return (List)objects;
	}

	/**
	 * Define a lista de objetos do formulário.
	 * 
	 * @param objects Instância contendo a lista de objetos do formulário.
	 */
    public <O extends ObjectModel> void setObjects(List<O> objects){
		this.objects = (List)objects;
	}

	/**
	 * Retorna a instância do módulo do sistema vinculado.
	 * 
	 * @return Instância contendo os dados do módulo do sistema.
	 */
    public <S extends SystemModuleModel> S getSystemModule(){
		return (S)systemModule;
	}

	/**
	 * Define a instância do módulo do sistema vinculado.
	 * 
	 * @param systemModule Instância contendo os dados do módulo do sistema.
	 */
	public <S extends SystemModuleModel> void setSystemModule(S systemModule){
		this.systemModule = systemModule;
	}

	/**
	 * Retorna o título do formulário.
	 * 
	 * @return String contendo título do formulário.
	 */
	public String getTitle(){
		return title;
	}

	/**
	 * Define o título do formulário.
	 * 
	 * @param title String contendo título do formulário.
	 */
	public void setTitle(String title){
		this.title = title;
	}

	/**
	 * Retorna a instância de um objeto vinculado a um formulário.
	 * 
	 * @param objectName String contendo o identificador do objeto.
	 * @return Instância do objeto vinculado.
	 */
    public <O extends ObjectModel> O getObject(String objectName){
		if(objects != null)
			for(ObjectModel object : objects)
				if(object.getName().equals(objectName))
					return (O)object;

		return null;
	}
}