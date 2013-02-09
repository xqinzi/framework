package br.com.concepting.framework.model;

import java.util.List;

import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;
import br.com.concepting.framework.model.types.ConditionType;
import br.com.concepting.framework.model.types.ContextSearchType;
import br.com.concepting.framework.model.types.ValidationType;
import br.com.concepting.framework.persistence.types.RelationJoinType;
import br.com.concepting.framework.persistence.types.RelationType;
import br.com.concepting.framework.security.model.GroupModel;
import br.com.concepting.framework.web.types.ComponentType;

/**
 * Classe que define a estrutura básica de um objeto vinculado a um formulário.
 * 
 * @author fvilarinho
 * @since 1.0
 */
@Model(descriptionPattern="#{label}")
public class ObjectModel extends BaseModel{
	@Property(isIdentity = true, isForSearch = true)
	private Long id = 0l; 

	@Property(isForSearch = true, searchCondition = ConditionType.CONTEXT, contextSearchType = ContextSearchType.BOTH, validations = ValidationType.REQUIRED)
	private String type = "";

	@Property(relationType = RelationType.ONE_TO_ONE, isForSearch = true, relationJoinType = RelationJoinType.INNER_JOIN, validations = ValidationType.REQUIRED)
	private FormModel form = null;

	@Property(isForSearch = true, searchCondition = ConditionType.CONTEXT, contextSearchType = ContextSearchType.BOTH, validations = ValidationType.REQUIRED, isUnique=true)
	private String name = "";

	@Property(isForSearch = true, searchCondition = ConditionType.CONTEXT, contextSearchType = ContextSearchType.BOTH, validations = ValidationType.REQUIRED)
	private String title = "";

	@Property
	private String action = "";

	@Property
	private String actionTarget = "";

	@Property
	private String description = "";
	
	@Property
	private Long sequence = 0l;
	
	@Property(relationType = RelationType.MANY_TO_MANY)
	private List<GroupModel> groups = null;
	
	/**
	 * Retorna a lista de grupos vinculados ao objeto.
	 * 
	 * @return Lista contendo a lista de grupos.
	 */
	public List<GroupModel> getGroups(){
        return groups;
    }

    /**
     * Define a lista de grupos vinculados ao objeto.
     * 
     * @param groups Lista contendo a lista de grupos.
     */
    public <G extends GroupModel> void setGroups(List<G> groups){
        this.groups = (List)groups;
    }

    /**
	 * Retorna a sequência de montagem/exibição do objeto.
	 * 
	 * @return Valor inteiro contendo a sequência.
	 */
	public Long getSequence(){
    	return sequence;
    }

	/**
	 * Define a sequência de montagem/exibição do objeto.
	 * 
	 * @param sequence Valor inteiro contendo a sequência.
	 */
	public void setSequence(Long sequence){
    	this.sequence = sequence;
    }

	/**
	 * Retorna o identificador do objeto.
	 * 
	 * @return Valor inteiro longo contendo o identificador do objeto.
	 */
	public Long getId(){
		return id;
	}

	/**
	 * Define o identificador do objeto.
	 * 
	 * @param id Valor inteiro longo contendo o identificador do objeto.
	 */
	public void setId(Long id){
		this.id = id;
	}

	/**
	 * Retorna o tipo do objeto.
	 * 
	 * @return Instância que define a constante do tipo do objeto.
	 */
	public ComponentType getObjectType(){
	    if(type.length() == 0)
	        return null;
	    
		return ComponentType.valueOf(type.toUpperCase());
	}
	
	/**
	 * Define o tipo do objeto.
	 * 
	 * @param type Instância que define a constante do tipo do objeto.
	 */
	public void setType(ComponentType type){
	    if(type == null)
	        this.type = "";
	    else
	        this.type = type.toString();
	}

	/**
	 * Retorna o tipo do objeto.
	 * 
	 * @return String contendo o tipo do objeto.
	 */
	public String getType(){
		return type;
	}

	/**
	 * Define o tipo do objeto.
	 * 
	 * @param type String contendo o tipo do objeto.
	 */
	public void setType(String type){
		this.type = type;
	}

	/**
	 * Retorna a instância do formulário vinculado ao formulário.
	 * 
	 * @return Instância contendo os dados do formulário.
	 */
    public <F extends FormModel> F getForm(){
		return (F)form;
	}

	/**
	 * Define a instância do formulário vinculado ao formulário.
	 * 
	 * @param form Instância contendo os dados do formulário.
	 */
	public <F extends FormModel> void setForm(F form){
		this.form = form;
	}

	/**
	 * Retorna o nome do objeto.
	 * 
	 * @return String contendo o nome do objeto.
	 */
	public String getName(){
		return name;
	}

	/**
	 * Define o nome do objeto.
	 * 
	 * @param name String contendo o nome do objeto.
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * Retorna a ação do objeto.
	 * 
	 * @return String contendo a ação do objeto.
	 */
	public String getAction(){
		return action;
	}

	/**
	 * Define a ação do objeto.
	 * 
	 * @param action String contendo a ação do objeto.
	 */
	public void setAction(String action){
		this.action = action;
	}

	/**
	 * Retorna o destino da ação do objeto.
	 * 
	 * @return String contendo o destino da ação.
	 */
	public String getActionTarget(){
		return actionTarget;
	}

	/**
	 * Define o destino da ação do objeto.
	 * 
	 * @param actionTarget String contendo o destino da ação.
	 */
	public void setActionTarget(String actionTarget){
		this.actionTarget = actionTarget;
	}

	/**
	 * Retorna o título do objeto.
	 *  
	 * @return String contendo o título.
	 */
	public String getTitle(){
		return title;
	}

	/**
	 * Define o título do objeto.
	 *  
	 * @param label String contendo o título.
	 */
	public void setTitle(String title){
		this.title = title;
	}

	/**
	 * Retorna a descrição do objeto.
	 * 
	 * @return String contendo a descrição do objeto.
	 */
	public String getDescription(){
		return description;
	}

	/**
	 * Define a dica de exibição do objeto.
	 * 
	 * @param description String contendo a descrição do objeto.
	 */
	public void setDescription(String description){
		this.description = description;
	}
}