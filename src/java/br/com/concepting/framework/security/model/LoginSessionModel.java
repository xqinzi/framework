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
 * Classe que define o modelo de dados que armazena os dados da sessão de login de um usuário.
 * 
 * @author fvilarinho
 * @since 1.0
 */
@Model(descriptionPattern="#{title} #{version}")
public class LoginSessionModel extends BaseModel{
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
	
	/**
	 * Indica se o usuário está autenticado.
	 * 
	 * @return True/False.
	 */
	public Boolean isAuthenticated(){
	    return (id != null && id > 0 && user != null && user.getId() != null && user.getId() > 0);
	}
	
    /**
     * Retorna a instância do modelo de dados que armazena os dados da sessão do sistema.
     * 
     * @return Instância do modelo de dados.
     */
    public <SS extends SystemSessionModel> SS getSystemSession(){
        return (SS)systemSession;
    }

    /**
     * Define a instância do modelo de dados que armazena os dados da sessão do sistema.
     * 
     * @param systemSession Instância do modelo de dados.
     */
    public <SS extends SystemSessionModel> void setSystemSession(SS systemSession){
        this.systemSession = systemSession;
    }

	/**
	 * Retorna a data/horário do login do usuário.
	 *
	 * @return Instância contendo a data/horário do login.
	 */
	public DateTime getCreateDate(){
     	return createDate;
     }

	/**
	 * Define a data/horário do login do usuário.
	 *
	 * @param createDate Instância contendo a data/horário do login.
	 */
	public void setCreateDate(DateTime createDate){
     	this.createDate = createDate;
     }

	/**
	 * Retorna o identificador da sessão de login do usuário.
	 *
	 * @return Valor inteiro longo contendo o identificador da sessão de login.
	 */
	public Long getId(){
		return id;
	}

	/**
	 * Define o identificador da sessão de login do usuário.
	 *
	 * @param id Valor inteiro longo contendo o identificador da sessão de 
	 *           login.
	 */
	public void setId(Long id){
		this.id = id;
	}

	/**
	 * Retorna a instância do usuário autenticado.
	 * 
	 * @return Instância contendo as propriedades do usuário.
	 */
    public <L extends UserModel> L getUser(){
		return (L)user;
	}

	/**
	 * Define a instância do usuário autenticado.
	 * 
	 * @param user Instância contendo as propriedades do usuário.
	 */
	public <L extends UserModel> void setUser(L user){
		this.user = user;
	}

	/**
	 * Retorna a instância do sistema ou módulo atual acessado pelo usuário.
	 * 
	 * @return Instância contendo as propriedades do sistema.
	 */
    public <S extends SystemModuleModel> S getSystemModule(){
		return (S)systemModule;
	}

	/**
	 * Define a instância do sistema ou módulo atual acessado pelo usuário.
	 * 
	 * @param systemModule Instância contendo as propriedades do sistema.
	 */
	public <S extends SystemModuleModel> void setSystemModule(S systemModule){
		this.systemModule = systemModule;
	}
}