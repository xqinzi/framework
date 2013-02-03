package br.com.concepting.framework.security.model;

import java.util.Date;

import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.SystemModuleModel;
import br.com.concepting.framework.model.SystemSessionModel;
import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;
import br.com.concepting.framework.persistence.types.RelationType;
import br.com.concepting.framework.util.types.SortOrderType;

/**
 * Classe que define o modelo de dados que armazena os dados da sess�o de login de um usu�rio.
 * 
 * @author fvilarinho
 * @since 1.0
 */
@Model(descriptionPattern="#{title} #{version}")
public class LoginSessionModel extends BaseModel{
	@Property(isIdentity = true, isForSearch = true)
	private Long id = 0l;

	@Property(isTime=true, sortOrder=SortOrderType.DESCEND)
	private Date date = null;
	
	@Property(isAuditable=true)
	private SystemSessionModel systemSession = null;

	@Property(relationType = RelationType.ONE_TO_ONE)
	private UserModel user = null;

	@Property(relationType = RelationType.ONE_TO_ONE)
	private SystemModuleModel systemModule = null;
	
    /**
     * Retorna a inst�ncia do modelo de dados que armazena os dados da sess�o do sistema.
     * 
     * @return Inst�ncia do modelo de dados.
     */
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
	 * Retorna a data do login do usu�rio.
	 *
	 * @return Inst�ncia contendo a data do login.
	 */
	public Date getDate(){
     	return date;
     }

	/**
	 * Define a data do login do usu�rio.
	 *
	 * @param date Inst�ncia contendo a data do login.
	 */
	public void setDate(Date date){
     	this.date = date;
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
    public <L extends UserModel> L getUser(){
		return (L)user;
	}

	/**
	 * Define a inst�ncia do usu�rio autenticado.
	 * 
	 * @param user Inst�ncia contendo as propriedades do usu�rio.
	 */
	public <L extends UserModel> void setUser(L user){
		this.user = user;
	}

	/**
	 * Retorna a inst�ncia do sistema ou m�dulo atual acessado pelo usu�rio.
	 * 
	 * @return Inst�ncia contendo as propriedades do sistema.
	 */
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