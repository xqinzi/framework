package br.com.concepting.framework.context.resource;

import br.com.concepting.framework.resource.BaseResource;
import br.com.concepting.framework.resource.FactoryResource;

/**
 * Classe responsável pelo armazenamento das configurações para comunicação com o 
 * servidor de aplicações.
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
	 * Construtor - Inicializa objetos e/ou variáveis internas.
	 */
	public ContextResource(){
		super();
	}
	
	/**
	 * Indica se o servidor de aplicações utiliza SSL.
	 * 
	 * @return True/False.
	 */
    public Boolean useSsl(){
        return useSsl;
    }

    /**
     * Indica se o servidor de aplicações utiliza SSL.
     * 
     * @return True/False.
     */
    public Boolean getUseSsl(){
        return useSsl();
    }

    /**
     * Define se o servidor de aplicações utiliza SSL.
     * 
     * @param useSsl True/False.
     */
    public void setUseSsl(Boolean useSsl){
        this.useSsl = useSsl;
    }

    /**
	 * Define o nome/IP do servidor de aplicações.
	 * 
	 * @param serverName String contendo o nome/IP do servidor.
	 */
	public void setServerName(String serverName){
		this.serverName = serverName;
	}

	/**
	 * Retorna o nome/IP do servidor de aplicações.
	 * 
	 * @return String contendo o nome/IP do servidor.
	 */
	public String getServerName(){
		return serverName;
	}
	
    /**
     * Retorna o número da porta para comunicação HTTP com o servidor de aplicações.
     * 
     * @return Valor inteiro contendo o número da porta.
     */
	public Integer getServerPort(){
        return serverPort;
    }

    /**
     * Define o número da porta para comunicação HTTP com o servidor de aplicações.
     * 
     * @param lookupPort Valor inteiro contendo o número da porta.
     */
    public void setServerPort(Integer serverPort){
        this.serverPort = serverPort;
    }

    /**
	 * Define o número da porta para comunicação JNDI com o servidor de aplicações.
	 * 
	 * @param lookupPort Valor inteiro contendo o número da porta.
	 */
	public void setLookupPort(Integer lookupPort){
		this.lookupPort = lookupPort;
	}

	/**
	 * Retorna o número da porta para comunicação JNDI com o servidor de aplicações.
	 * 
	 * @return Valor inteiro contendo o número da porta.
	 */
	public Integer getLookupPort(){
		return lookupPort;
	}

	/**
	 * Retorna as configurações da classe responsável pela comunicação 
	 * com o servidor de aplicações.
	 * 
	 * @return Instância contendo as configurações da classe.
	 */
	public FactoryResource getFactoryResource(){
		return factoryResource;
	}

	/**
	 * Define as configurações da classe responsável pela comunicação
	 * com o servidor de aplicações.
	 * 
	 * @param factoryResource Instância contendo as configurações da classe.
	 */
	public void setFactoryResource(FactoryResource factoryResource){
		this.factoryResource = factoryResource;
	}
}