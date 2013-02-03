package br.com.concepting.framework.network.ldap.resource;

import br.com.concepting.framework.resource.BaseResource;

/** 
 * Classe responsável pelo armazenamento das configurações do serviço de diretórios LDAP.
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
	 * Construtor - Inicializa objetos e/ou variáveis internas.
	 */
	public LdapResource(){
		super();
	}
	
	/**
	 * Retorna a string de autenticação no serviço de diretórios LDAP.
	 * 
	 * @return String contendo a string de autenticação.
	 */
	public String getUserDn(){
    	return userDn;
    }

    /**
     * Define a string de autenticação no serviço de diretórios LDAP.
     * 
     * @param userDn String contendo a string de autenticação.
     */
	public void setUserDn(String userDn){
    	this.userDn = userDn;
    }

	/**
	 * Retorna o timeout de conexão com o serviço de diretórios LDAP.
	 * 
	 * @return Valor inteiro contendo o timeout de conexão.
	 */
	public Integer getTimeout(){
    	return timeout;
    }

    /**
     * Define o timeout de conexão com o serviço de diretórios LDAP.
     * 
     * @param timeout Valor inteiro contendo o timeout de conexão.
     */
	public void setTimeout(Integer timeout){
    	this.timeout = timeout;
    }

	/**
	 * Retorna o tipo de autenticação com o servi;o de diretórios LDAP.
	 * 
	 * @return String contendo o tipo de autenticação.
	 */
	public String getAuthenticationType(){
    	return authenticationType;
    }

    /**
     * Define o tipo de autenticação com o servi;o de diretórios LDAP.
     * 
     * @param authenticationType String contendo o tipo de autenticação.
     */
	public void setAuthenticationType(String authenticationType){
    	this.authenticationType = authenticationType;
    }

	/**
	 * Retorna a porta de conexão com o serviço de diretórios LDAP.
	 * 
	 * @return Valor inteiro contendo a porta de conexão.
	 */
	public Integer getServerPort(){
    	return serverPort;
    }

    /**
     * Define a porta de conexão com o serviço de diretórios LDAP.
     * 
     * @param serverPort Valor inteiro contendo a porta de conexão.
     */
	public void setServerPort(Integer serverPort){
    	this.serverPort = serverPort;
    }

	/**
	 * Retorna o nome/IP do serviço de diretórios LDAP.
	 * 
	 * @return String contendo o nome/IP do serviço.
	 */
	public String getServerName(){
		return serverName;
	}

	/**
	 * Define o nome/IP do serviço de diretórios LDAP.
	 * 
	 * @param serverName String contendo o nome/IP do serviço.
	 */
	public void setServerName(String serverName){
		this.serverName = serverName;
	}

	/**
	 * Retorna o nome do usuário para conexão com o servidor de diretórios LDAP.
	 * 
	 * @return String contendo o nome do usuário.
	 */
	public String getUser(){
		return user;
	}

	/**
	 * Define o nome do usuário para conexão com o servidor de diretórios LDAP.
	 * 
	 * @param user String contendo o nome do usuário.
	 */
	public void setUser(String user){
		this.user = user;
	}

	/**
	 * Retorna a senha para conexão com o servidor de diretórios LDAP.
	 * 
	 * @return String contendo a senha.
	 */
	public String getPassword(){
		return password;
	}

	/**
	 * Define a senha para conexão com o servidor de diretórios LDAP.
	 * 
	 * @param password String contendo a senha.
	 */
	public void setPassword(String password){
		this.password = password;
	}

    /**
     * Retorna a string de pesquisa no serviço de diretórios LDAP.
     * 
     * @return String contendo a string de pesquisa.
     */
	public String getBaseDn(){
    	return baseDn;
    }

    /**
     * Define a string de pesquisa no serviço de diretórios LDAP.
     * 
     * @param baseDn String contendo a string de pesquisa.
     */
	public void setBaseDn(String baseDn){
    	this.baseDn = baseDn;
    }
}