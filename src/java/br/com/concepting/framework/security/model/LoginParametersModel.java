package br.com.concepting.framework.security.model;

import java.util.List;

import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;
import br.com.concepting.framework.persistence.types.RelationType;

/**
 * Classe que define o modelo de dados que armazena os par�metros de login.
 * 
 * @author fvilarinho
 * @since 1.0
 */
@Model 
public class LoginParametersModel extends BaseModel{
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
	
	@Property(relationType=RelationType.ONE_TO_MANY)
	private List<AccessListModel> accessLists = null;
	
	/**
	 * Retorna a quantidade de tentativas de login.
	 * 
	 * @return Valor num�rico contendo a quantidade de tentativas de login.
	 */
	public Integer getLoginTries(){
        return loginTries;
    }

    /**
     * Define a quantidade de tentativas de login.
     * 
     * @param loginTries Valor num�rico contendo a quantidade de tentativas de login.
     */
    public void setLoginTries(Integer loginTries){
        this.loginTries = loginTries;
    }

    /**
     * Retorna as listas de acessos do login.
     * 
     * @return Listas de acesso do login.
     */
    public <A extends AccessListModel> List<A> getAccessLists(){
        return (List<A>)accessLists;
    }

    /**
     * Define as listas de acessos do login.
     * 
     * @param accessLists Listas de acesso do login.
     */
    public <A extends AccessListModel> void setAccessLists(List<A> accessLists){
        this.accessLists = (List)accessLists;
    }

    /**
	 * Retorna o intervalo para a pr�xima troca de senha.
	 * 
	 * @return Valor inteiro contendo o n�mero de dias para a pr�xima troca de senha.
	 */
	public Integer getChangePasswordInterval(){
		return changePasswordInterval;
	}

	/**
	 * Define o intervalo para a pr�xima troca de senha.
	 * 
	 * @param changePasswordInterval Valor inteiro contendo o n�mero de dias para a pr�xima troca 
	 * de senha.
	 */
	public void setChangePasswordInterval(Integer changePasswordInterval){
		this.changePasswordInterval = changePasswordInterval;
	}

	/**
	 * Retorna o intervalo para expira��o de senha.
	 * 
	 * @return Valor inteiro contendo o n�mero de dias para expira��o de senha.
	 */
	public Integer getExpirePasswordInterval(){
		return expirePasswordInterval;
	}

	/**
	 * Define o intervalo para expira��o de senha.
	 * 
	 * @param expirePasswordInterval Valor inteiro contendo o n�mero de dias para expira��o de senha.
	 */
	public void setExpirePasswordInterval(Integer expirePasswordInterval){
		this.expirePasswordInterval = expirePasswordInterval;
	}

	/**
	 * Indica se o usu�rio pode logar m�ltiplas vezes com o mesmo login.
	 * 
	 * @return True/False.
	 */
	public Boolean getMultipleLogins(){
    	return multipleLogins;
    }

	/**
	 * Define se o usu�rio pode logar m�ltiplas vezes com o mesmo login.
	 * 
	 * @param multipleLogins True/False.
	 */
	public void setMultipleLogins(Boolean multipleLogins){
    	this.multipleLogins = multipleLogins;
    }
}