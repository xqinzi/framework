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
 * Classe que define a estrutura b�sica para o modelo de dados de um formul�rio de um sistema.
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
	 * Retorna o identificador do formul�rio.
	 * 
	 * @return Valor inteiro contendo o identificador.
	 */
	public Integer getId(){
		return id;
	}

	/**
	 * Define o identificador do formul�rio.
	 * 
	 * @param id Valor inteiro contendo o identificador.
	 */
	public void setId(Integer id){
		this.id = id;
	}

	/**
	 * Retorna o nome do formul�rio.
	 * 
	 * @return String contendo nome do formul�rio.
	 */
	public String getName(){
		return name;
	}

	/**
	 * Define o nome do formul�rio.
	 * 
	 * @param name String contendo nome do formul�rio.
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * Retorna a lista de objetos do formul�rio.
	 * 
	 * @return Inst�ncia contendo a lista de objetos do formul�rio.
	 */
    public <O extends ObjectModel> List<O> getObjects(){
		return (List)objects;
	}

	/**
	 * Define a lista de objetos do formul�rio.
	 * 
	 * @param objects Inst�ncia contendo a lista de objetos do formul�rio.
	 */
    public <O extends ObjectModel> void setObjects(List<O> objects){
		this.objects = (List)objects;
	}

	/**
	 * Retorna a inst�ncia do m�dulo do sistema vinculado.
	 * 
	 * @return Inst�ncia contendo os dados do m�dulo do sistema.
	 */
    public <S extends SystemModuleModel> S getSystemModule(){
		return (S)systemModule;
	}

	/**
	 * Define a inst�ncia do m�dulo do sistema vinculado.
	 * 
	 * @param systemModule Inst�ncia contendo os dados do m�dulo do sistema.
	 */
	public <S extends SystemModuleModel> void setSystemModule(S systemModule){
		this.systemModule = systemModule;
	}

	/**
	 * Retorna o t�tulo do formul�rio.
	 * 
	 * @return String contendo t�tulo do formul�rio.
	 */
	public String getTitle(){
		return title;
	}

	/**
	 * Define o t�tulo do formul�rio.
	 * 
	 * @param title String contendo t�tulo do formul�rio.
	 */
	public void setTitle(String title){
		this.title = title;
	}

	/**
	 * Retorna a inst�ncia de um objeto vinculado a um formul�rio.
	 * 
	 * @param objectName String contendo o identificador do objeto.
	 * @return Inst�ncia do objeto vinculado.
	 */
    public <O extends ObjectModel> O getObject(String objectName){
		if(objects != null)
			for(ObjectModel object : objects)
				if(object.getName().equals(objectName))
					return (O)object;

		return null;
	}
}