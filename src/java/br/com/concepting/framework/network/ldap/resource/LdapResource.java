package br.com.concepting.framework.network.ldap.resource;

import br.com.concepting.framework.resource.BaseResource;

/** 
 * Classe respons�vel pelo armazenamento das configura��es do servi�o de diret�rios LDAP.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class LdapResource extends BaseResource{
	private String  serverName         = "";
	private Integer serverPort         = 0;
	private String  authenticationType = "";
	private String  user               = "";
	private String  password           = "";
	private String  baseDn             = "";
	private String  userDn             = "";
	private Integer timeout            = 0;
	
	/**
	 * Construtor - Inicializa objetos e/ou vari�veis internas.
	 */
	public LdapResource(){
		super();
	}
	
	/**
	 * Retorna a string de autentica��o no servi�o de diret�rios LDAP.
	 * 
	 * @return String contendo a string de autentica��o.
	 */
	public String getUserDn(){
    	return userDn;
    }

    /**
     * Define a string de autentica��o no servi�o de diret�rios LDAP.
     * 
     * @param userDn String contendo a string de autentica��o.
     */
	public void setUserDn(String userDn){
    	this.userDn = userDn;
    }

	/**
	 * Retorna o timeout de conex�o com o servi�o de diret�rios LDAP.
	 * 
	 * @return Valor inteiro contendo o timeout de conex�o.
	 */
	public Integer getTimeout(){
    	return timeout;
    }

    /**
     * Define o timeout de conex�o com o servi�o de diret�rios LDAP.
     * 
     * @param timeout Valor inteiro contendo o timeout de conex�o.
     */
	public void setTimeout(Integer timeout){
    	this.timeout = timeout;
    }

	/**
	 * Retorna o tipo de autentica��o com o servi;o de diret�rios LDAP.
	 * 
	 * @return String contendo o tipo de autentica��o.
	 */
	public String getAuthenticationType(){
    	return authenticationType;
    }

    /**
     * Define o tipo de autentica��o com o servi;o de diret�rios LDAP.
     * 
     * @param authenticationType String contendo o tipo de autentica��o.
     */
	public void setAuthenticationType(String authenticationType){
    	this.authenticationType = authenticationType;
    }

	/**
	 * Retorna a porta de conex�o com o servi�o de diret�rios LDAP.
	 * 
	 * @return Valor inteiro contendo a porta de conex�o.
	 */
	public Integer getServerPort(){
    	return serverPort;
    }

    /**
     * Define a porta de conex�o com o servi�o de diret�rios LDAP.
     * 
     * @param serverPort Valor inteiro contendo a porta de conex�o.
     */
	public void setServerPort(Integer serverPort){
    	this.serverPort = serverPort;
    }

	/**
	 * Retorna o nome/IP do servi�o de diret�rios LDAP.
	 * 
	 * @return String contendo o nome/IP do servi�o.
	 */
	public String getServerName(){
		return serverName;
	}

	/**
	 * Define o nome/IP do servi�o de diret�rios LDAP.
	 * 
	 * @param serverName String contendo o nome/IP do servi�o.
	 */
	public void setServerName(String serverName){
		this.serverName = serverName;
	}

	/**
	 * Retorna o nome do usu�rio para conex�o com o servidor de diret�rios LDAP.
	 * 
	 * @return String contendo o nome do usu�rio.
	 */
	public String getUser(){
		return user;
	}

	/**
	 * Define o nome do usu�rio para conex�o com o servidor de diret�rios LDAP.
	 * 
	 * @param user String contendo o nome do usu�rio.
	 */
	public void setUser(String user){
		this.user = user;
	}

	/**
	 * Retorna a senha para conex�o com o servidor de diret�rios LDAP.
	 * 
	 * @return String contendo a senha.
	 */
	public String getPassword(){
		return password;
	}

	/**
	 * Define a senha para conex�o com o servidor de diret�rios LDAP.
	 * 
	 * @param password String contendo a senha.
	 */
	public void setPassword(String password){
		this.password = password;
	}

    /**
     * Retorna a string de pesquisa no servi�o de diret�rios LDAP.
     * 
     * @return String contendo a string de pesquisa.
     */
	public String getBaseDn(){
    	return baseDn;
    }

    /**
     * Define a string de pesquisa no servi�o de diret�rios LDAP.
     * 
     * @param baseDn String contendo a string de pesquisa.
     */
	public void setBaseDn(String baseDn){
    	this.baseDn = baseDn;
    }
}