package br.com.concepting.framework.model;

import java.util.Collection;

import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;
import br.com.concepting.framework.model.types.ConditionType;
import br.com.concepting.framework.model.types.ContextSearchType;
import br.com.concepting.framework.model.types.ValidationType;
import br.com.concepting.framework.persistence.types.RelationJoinType;
import br.com.concepting.framework.persistence.types.RelationType;
import br.com.concepting.framework.security.model.GroupModel;
import br.com.concepting.framework.util.types.ComponentType;

/**
 * Classe que define a estrutura b�sica de um objeto vinculado a um formul�rio.
 * 
 * @author fvilarinho
 * @since 1.0
 */
@Model(descriptionPattern="#{title}")
public class ObjectModel extends BaseModel{
    private static final long serialVersionUID = 4904881181797474284L;

    @Property(isIdentity = true, isForSearch = true)
	private Long id = 0l; 

	@Property(isForSearch = true, searchCondition = ConditionType.CONTEXT, contextSearchType = ContextSearchType.BOTH, validations = ValidationType.REQUIRED)
	private ComponentType type = null;

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
	private String iconUrl = "";
	
    @Property
	private byte iconData[] = null;
	
    @Property
	private String iconWidth = "";
	
    @Property
	private String iconHeight = "";
	
	@Property
	private Long sequence = 0l;
	
	@Property(relationType = RelationType.MANY_TO_MANY)
	private Collection<GroupModel> groups = null;
	
    /**
	 * Retorna a URL que armazena o �cone do objeto.
	 * 
	 * @return String contendo a URL.
	 */
	public String getIconUrl(){
        return iconUrl;
    }

    /**
     * Define a URL que armazena o �cone do objeto.
     * 
     * @param iconUrl String contendo a URL.
     */
    public void setIconUrl(String iconUrl){
        this.iconUrl = iconUrl;
    }

    /**
     * Retorna os dados que definem o �cone do objeto.
     * 
     * @return Array de bytes contendo os dados que definem o �cone.
     */
    public byte[] getIconData(){
        return iconData;
    }

    /**
     * Define os dados que definem o �cone do objeto.
     * 
     * @param iconData Array de bytes contendo os dados que definem o �cone.
     */
    public void setIconData(byte[] iconData){
        this.iconData = iconData;
    }

    /**
     * Retorna a largura do �cone.
     * 
     * @return String contendo a largura do �cone.
     */
    public String getIconWidth(){
        return iconWidth;
    }

    /**
     * Define a largura do �cone.
     * 
     * @param iconWidth String contendo a largura do �cone.
     */
    public void setIconWidth(String iconWidth){
        this.iconWidth = iconWidth;
    }

    /**
     * Retorna a altura do �cone.
     * 
     * @return String contendo a altura do �cone.
     */
    public String getIconHeight(){
        return iconHeight;
    }

    /**
     * Define a altura do �cone.
     * 
     * @param iconHeight String contendo a altura do �cone.
     */
    public void setIconHeight(String iconHeight){
        this.iconHeight = iconHeight;
    }

    /**
	 * Retorna a lista de grupos vinculados ao objeto.
	 * 
	 * @return Lista contendo a lista de grupos.
	 */
	@SuppressWarnings("unchecked")
    public <G extends GroupModel, C extends Collection<G>> C getGroups(){
        return (C)groups;
    }

    /**
     * Define a lista de grupos vinculados ao objeto.
     * 
     * @param groups Lista contendo a lista de grupos.
     */
    @SuppressWarnings("unchecked")
    public <G extends GroupModel, C extends Collection<G>> void setGroups(C groups){
        this.groups = (Collection<GroupModel>)groups;
    }

    /**
	 * Retorna a sequ�ncia de montagem/exibi��o do objeto.
	 * 
	 * @return Valor inteiro contendo a sequ�ncia.
	 */
	public Long getSequence(){
    	return sequence;
    }

	/**
	 * Define a sequ�ncia de montagem/exibi��o do objeto.
	 * 
	 * @param sequence Valor inteiro contendo a sequ�ncia.
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
	 * Define o tipo do objeto.
	 * 
	 * @param type Constante que define do tipo do objeto.
	 */
	public void setType(ComponentType type){
        this.type = type;
	}

	/**
	 * Retorna o tipo do objeto.
	 * 
	 * @return Constante que define o tipo do objeto.
	 */
	public ComponentType getType(){
		return type;
	}

    /**
	 * Retorna a inst�ncia do formul�rio vinculado ao formul�rio.
	 * 
	 * @return Inst�ncia contendo os dados do formul�rio.
	 */
    @SuppressWarnings("unchecked")
    public <F extends FormModel> F getForm(){
		return (F)form;
	}

	/**
	 * Define a inst�ncia do formul�rio vinculado ao formul�rio.
	 * 
	 * @param form Inst�ncia contendo os dados do formul�rio.
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
	 * Retorna a a��o do objeto.
	 * 
	 * @return String contendo a a��o do objeto.
	 */
	public String getAction(){
		return action;
	}

	/**
	 * Define a a��o do objeto.
	 * 
	 * @param action String contendo a a��o do objeto.
	 */
	public void setAction(String action){
		this.action = action;
	}

	/**
	 * Retorna o destino da a��o do objeto.
	 * 
	 * @return String contendo o destino da a��o.
	 */
	public String getActionTarget(){
		return actionTarget;
	}

	/**
	 * Define o destino da a��o do objeto.
	 * 
	 * @param actionTarget String contendo o destino da a��o.
	 */
	public void setActionTarget(String actionTarget){
		this.actionTarget = actionTarget;
	}

	/**
	 * Retorna o t�tulo do objeto.
	 *  
	 * @return String contendo o t�tulo.
	 */
	public String getTitle(){
		return title;
	}

	/**
	 * Define o t�tulo do objeto.
	 *  
	 * @param title String contendo o t�tulo.
	 */
	public void setTitle(String title){
		this.title = title;
	}

	/**
	 * Retorna a descri��o do objeto.
	 * 
	 * @return String contendo a descri��o.
	 */
	public String getDescription(){
		return description;
	}

    /**
     * Define a descri��o do objeto.
     * 
     * @param description String contendo a descri��o.
     */
	public void setDescription(String description){
		this.description = description;
	}
}