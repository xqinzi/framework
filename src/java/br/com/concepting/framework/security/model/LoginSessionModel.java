package br.com.concepting.framework.security.model;

import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.SystemModuleModel;
import br.com.concepting.framework.model.SystemSessionModel;
import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;
import br.com.concepting.framework.persistence.types.RelationType;
import br.com.concepting.framework.util.helpers.DateTime;
import br.com.concepting.framework.util.types.SortOrderType;

/**
 * Classe que define o modelo de dados que armazena os dados da sess�o de login de um usu�rio.
 * 
 * @author fvilarinho
 * @since 1.0
 */
@Model(descriptionPattern="#{title} #{version}")
public class LoginSessionModel extends BaseModel{
    private static final long serialVersionUID = -8507789965528582224L;

    @Property(isIdentity = true, isForSearch = true)
	private Long id = 0l;

	@Property(sortOrder=SortOrderType.DESCEND)
	private DateTime createDate = null;
	
	@Property(isAuditable=true)
	private SystemSessionModel systemSession = null;

	@Property(relationType = RelationType.ONE_TO_ONE)
	private UserModel user = null;

	@Property(relationType = RelationType.ONE_TO_ONE)
	private SystemModuleModel systemModule = null;
	
	@Property
	private Boolean rememberUserAndPassword = false;

	/**
	 * Indica se o usu�rio e senha devem ser lembrados no login.
	 * 
	 * @return True/False.
	 */
    public Boolean rememberUserAndPassword(){
        return rememberUserAndPassword;
    }

    /**
     * Indica se o usu�rio e senha devem ser lembrados no login.
     * 
     * @return True/False.
     */
    public Boolean getRememberUserAndPassword(){
        return rememberUserAndPassword();
    }

    /**
     * Define se o usu�rio e senha deve ser lembrado no login.
     * 
     * @param rememberUserAndPassword True/False.
     */
    public void setRememberUserAndPassword(Boolean rememberUserAndPassword){
        this.rememberUserAndPassword = rememberUserAndPassword;
    }

    /**
     * Retorna a inst�ncia do modelo de dados que armazena os dados da sess�o do sistema.
     * 
     * @return Inst�ncia do modelo de dados.
     */
    @SuppressWarnings("unchecked")
    public <SS extends SystemSessionModel> SS getSystemSession(){
        return (SS)systemSession;
    }

    /**
     * Define a inst�ncia do modelo de dados que armazena os dados da sess�o do sistema.
     * 
     * @param systemSession Inst�ncia do modelo de dados.
     */
    public <SS extends SystemSessionModel> void setSystemSession(SS systemSession){
        this.systemSession = systemSession;
    }

	/**
	 * Retorna a data/hor�rio do login do usu�rio.
	 *
	 * @return Inst�ncia contendo a data/hor�rio do login.
	 */
	public DateTime getCreateDate(){
     	return createDate;
     }

	/**
	 * Define a data/hor�rio do login do usu�rio.
	 *
	 * @param createDate Inst�ncia contendo a data/hor�rio do login.
	 */
	public void setCreateDate(DateTime createDate){
     	this.createDate = createDate;
     }

	/**
	 * Retorna o identificador da sess�o de login do usu�rio.
	 *
	 * @return Valor inteiro longo contendo o identificador da sess�o de login.
	 */
	public Long getId(){
		return id;
	}

	/**
	 * Define o identificador da sess�o de login do usu�rio.
	 *
	 * @param id Valor inteiro longo contendo o identificador da sess�o de 
	 *           login.
	 */
	public void setId(Long id){
		this.id = id;
	}

	/**
	 * Retorna a inst�ncia do usu�rio autenticado.
	 * 
	 * @return Inst�ncia contendo as propriedades do usu�rio.
	 */
    @SuppressWarnings("unchecked")
    public <U extends UserModel> U getUser(){
		return (U)user;
	}

	/**
	 * Define a inst�ncia do usu�rio autenticado.
	 * 
	 * @param user Inst�ncia contendo as propriedades do usu�rio.
	 */
	public <U extends UserModel> void setUser(U user){
		this.user = user;
	}

	/**
	 * Retorna a inst�ncia do sistema ou m�dulo atual acessado pelo usu�rio.
	 * 
	 * @return Inst�ncia contendo as propriedades do sistema.
	 */
    @SuppressWarnings("unchecked")
    public <S extends SystemModuleModel> S getSystemModule(){
		return (S)systemModule;
	}

	/**
	 * Define a inst�ncia do sistema ou m�dulo atual acessado pelo usu�rio.
	 * 
	 * @param systemModule Inst�ncia contendo as propriedades do sistema.
	 */
	public <S extends SystemModuleModel> void setSystemModule(S systemModule){
		this.systemModule = systemModule;
	}
}