package br.com.concepting.framework.security.model;

import java.util.Collection;

import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;
import br.com.concepting.framework.persistence.types.RelationType;

/**
 * Classe que define o modelo de dados que armazena os parâmetros de login.
 * 
 * @author fvilarinho
 * @since 1.0
 */
@Model 
public class LoginParametersModel extends BaseModel{
    private static final long serialVersionUID = -6631335675531065211L;

    @Property(isIdentity=true)
    private Integer id = 0;
    
	@Property
	private Integer expirePasswordInterval = 0;

	@Property
	private Integer changePasswordInterval = 0;
	
	@Property
	private Boolean multipleLogins = true;
	
	@Property
	private Integer loginTries = 0;
	
	@Property(relationType=RelationType.MANY_TO_MANY, cascadeOnDelete = true, cascadeOnSave = true)
	private Collection<? extends AccessListModel> accessLists = null;

	/**
	 * Retorna o identificador da parametrização do login.
	 * 
	 * @return Valor inteiro contendo o identificador da parametrização.
	 */
	public Integer getId(){
        return id;
    }

    /**
     * Define o identificador da parametrização do login.
     * 
     * @param id Valor inteiro contendo o identificador da parametrização.
     */
    public void setId(Integer id){
        this.id = id;
    }

    /**
	 * Retorna a quantidade de tentativas de login.
	 * 
	 * @return Valor numérico contendo a quantidade de tentativas de login.
	 */
	public Integer getLoginTries(){
        return loginTries;
    }

    /**
     * Define a quantidade de tentativas de login.
     * 
     * @param loginTries Valor numérico contendo a quantidade de tentativas de login.
     */
    public void setLoginTries(Integer loginTries){
        this.loginTries = loginTries;
    }

    /**
     * Retorna as listas de acessos do login.
     * 
     * @return Listas de acesso do login.
     */
    @SuppressWarnings("unchecked")
    public <A extends AccessListModel, C extends Collection<A>> C getAccessLists(){
        return (C)accessLists;
    }

    /**
     * Define as listas de acessos do login.
     * 
     * @param accessLists Listas de acesso do login.
     */
    public <A extends AccessListModel> void setAccessLists(Collection<AccessListModel> accessLists){
        this.accessLists = accessLists;
    }

    /**
	 * Retorna o intervalo para a próxima troca de senha.
	 * 
	 * @return Valor inteiro contendo o número de dias para a próxima troca de senha.
	 */
	public Integer getChangePasswordInterval(){
		return changePasswordInterval;
	}

	/**
	 * Define o intervalo para a próxima troca de senha.
	 * 
	 * @param changePasswordInterval Valor inteiro contendo o número de dias para a próxima troca 
	 * de senha.
	 */
	public void setChangePasswordInterval(Integer changePasswordInterval){
		this.changePasswordInterval = changePasswordInterval;
	}

	/**
	 * Retorna o intervalo para expiração de senha.
	 * 
	 * @return Valor inteiro contendo o número de dias para expiração de senha.
	 */
	public Integer getExpirePasswordInterval(){
		return expirePasswordInterval;
	}

	/**
	 * Define o intervalo para expiração de senha.
	 * 
	 * @param expirePasswordInterval Valor inteiro contendo o número de dias para expiração de senha.
	 */
	public void setExpirePasswordInterval(Integer expirePasswordInterval){
		this.expirePasswordInterval = expirePasswordInterval;
	}

	/**
	 * Indica se o usuário pode logar múltiplas vezes com o mesmo login.
	 * 
	 * @return True/False.
	 */
	public Boolean getMultipleLogins(){
    	return multipleLogins;
    }

	/**
	 * Define se o usuário pode logar múltiplas vezes com o mesmo login.
	 * 
	 * @param multipleLogins True/False.
	 */
	public void setMultipleLogins(Boolean multipleLogins){
    	this.multipleLogins = multipleLogins;
    }
}