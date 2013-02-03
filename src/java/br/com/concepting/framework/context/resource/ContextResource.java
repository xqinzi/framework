package br.com.concepting.framework.context.resource;

import br.com.concepting.framework.resource.BaseResource;
import br.com.concepting.framework.resource.FactoryResource;

/**
 * Classe respons�vel pelo armazenamento das configura��es para comunica��o com o 
 * servidor de aplica��es.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class ContextResource extends BaseResource{
	private String          serverName      = "";
	private Integer         serverPort      = 0; 
	private Integer         lookupPort      = 0;
	private Boolean         useSsl          = false;
	private FactoryResource factoryResource = null;

	/**
	 * Construtor - Inicializa objetos e/ou vari�veis internas.
	 */
	public ContextResource(){
		super();
	}
	
	/**
	 * Indica se o servidor de aplica��es utiliza SSL.
	 * 
	 * @return True/False.
	 */
    public Boolean useSsl(){
        return useSsl;
    }

    /**
     * Indica se o servidor de aplica��es utiliza SSL.
     * 
     * @return True/False.
     */
    public Boolean getUseSsl(){
        return useSsl();
    }

    /**
     * Define se o servidor de aplica��es utiliza SSL.
     * 
     * @param useSsl True/False.
     */
    public void setUseSsl(Boolean useSsl){
        this.useSsl = useSsl;
    }

    /**
	 * Define o nome/IP do servidor de aplica��es.
	 * 
	 * @param serverName String contendo o nome/IP do servidor.
	 */
	public void setServerName(String serverName){
		this.serverName = serverName;
	}

	/**
	 * Retorna o nome/IP do servidor de aplica��es.
	 * 
	 * @return String contendo o nome/IP do servidor.
	 */
	public String getServerName(){
		return serverName;
	}
	
    /**
     * Retorna o n�mero da porta para comunica��o HTTP com o servidor de aplica��es.
     * 
     * @return Valor inteiro contendo o n�mero da porta.
     */
	public Integer getServerPort(){
        return serverPort;
    }

    /**
     * Define o n�mero da porta para comunica��o HTTP com o servidor de aplica��es.
     * 
     * @param lookupPort Valor inteiro contendo o n�mero da porta.
     */
    public void setServerPort(Integer serverPort){
        this.serverPort = serverPort;
    }

    /**
	 * Define o n�mero da porta para comunica��o JNDI com o servidor de aplica��es.
	 * 
	 * @param lookupPort Valor inteiro contendo o n�mero da porta.
	 */
	public void setLookupPort(Integer lookupPort){
		this.lookupPort = lookupPort;
	}

	/**
	 * Retorna o n�mero da porta para comunica��o JNDI com o servidor de aplica��es.
	 * 
	 * @return Valor inteiro contendo o n�mero da porta.
	 */
	public Integer getLookupPort(){
		return lookupPort;
	}

	/**
	 * Retorna as configura��es da classe respons�vel pela comunica��o 
	 * com o servidor de aplica��es.
	 * 
	 * @return Inst�ncia contendo as configura��es da classe.
	 */
	public FactoryResource getFactoryResource(){
		return factoryResource;
	}

	/**
	 * Define as configura��es da classe respons�vel pela comunica��o
	 * com o servidor de aplica��es.
	 * 
	 * @param factoryResource Inst�ncia contendo as configura��es da classe.
	 */
	public void setFactoryResource(FactoryResource factoryResource){
		this.factoryResource = factoryResource;
	}
}