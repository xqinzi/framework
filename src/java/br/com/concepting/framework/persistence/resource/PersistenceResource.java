package br.com.concepting.framework.persistence.resource;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import br.com.concepting.framework.context.resource.ContextResource;
import br.com.concepting.framework.resource.BaseResource;
import br.com.concepting.framework.resource.FactoryResource;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Classe responsável pelo armazenamento das configurações de persistência.
 *
 * @author fvilarinho
 * @since 1.0
 */
public class PersistenceResource extends BaseResource<XmlNode>{
    private static final long serialVersionUID = 5519581642532631902L;
    
    private Integer             timeout         = 0;
	private String              serverName      = "";
	private Integer             serverPort      = 0;
	private String              repositoryId    = "";
	private String              user            = "";
	private String              password        = "";
	private String              lookupName      = "";
	private FactoryResource     factoryResource = null;
	private ContextResource     contextResource = null;
	private Map<String, String> options         = null;
	private List<String>        mappings        = null;

	/**
	 * Construtor - Inicializa objetos e/ou variáveis internas.
	 */
	public PersistenceResource(){
		super();
	}
	
	/**
	 * Retorna a lista de mapeamentos de persistência.
	 * 
	 * @return Lista contendo os mapeamentos de persistência.
	 */
	public List<String> getMappings(){
        return mappings;
    }

    /**
     * Define a lista de mapeamentos de persistência.
     * 
     * @param mappings Lista contendo os mapeamentos de persistência.
     */
    public void setMappings(List<String> mappings){
        this.mappings = mappings;
    }

	/**
	 * Retorna o timeout de conexão com o repositório de persistência.
	 * Utilizado somente quando não for usado Datasource.
	 *
	 * @return Valor inteiro contendo o timeout de conexão.
	 */
	public Integer getTimeout(){
		return timeout;
	}

	/**
	 * Define o timeout de conexão com o repositório de persistência.
	 * Utilizado somente quando não for usado Datasource.
	 *
	 * @param timeout Valor inteiro contendo o timeout de conexão.
	 */
	public void setTimeout(Integer timeout){
		this.timeout = timeout;
	}

	/**
	 * Retorna a instância contendo as configurações de contexto.
	 * Utilizando somente quando for usado Datasource.
	 *
	 * @return Instância contendo as configurações de contexto.
	 */
	public ContextResource getContextResource(){
		return contextResource;
	}

	/**
	 * Define a instância contendo as configurações de contexto.
	 * Utilizando somente quando for usado Datasource.
	 *
	 * @param contextResource Instância contendo as configurações de contexto.
	 */
	public void setContextResource(ContextResource contextResource){
		this.contextResource = contextResource;
	}

	/**
	 * Retorna a instância contendo as configurações do driver de conexão.
	 * Utilizando somente quando for não usado Datasource.
	 *
	 * @return Instância contendo as configurações do driver de conexão.
	 */
	public FactoryResource getFactoryResource(){
		return factoryResource;
	}

	/**
	 * Define a instância contendo as configurações do driver de conexão.
	 * Utilizando somente quando for não usado Datasource.
	 *
	 * @param factoryResource Instância contendo as configurações do driver de conexão.
	 */
	public void setFactoryResource(FactoryResource factoryResource){
		this.factoryResource = factoryResource;
	}

	/**
	 * Retorna o identificador do serviço onde o Datasource está armazenado.
	 *
	 * @return String contendo o identificador do Datasource.
	 */
	public String getLookupName(){
		return lookupName;
	}

	/**
	 * Define o identificador do serviço onde o Datasource está armazenado.
	 *
	 * @param lookupName String contendo o identificador do Datasource.
	 */
	public void setLookupName(String lookupName){
		this.lookupName = lookupName;
	}

	/**
	 * Retorna a senha do usuário para conexão com o repositório de persistência.
	 *
	 * @return String contendo a senha do usuário.
	 */
	public String getPassword(){
		return password;
	}

	/**
	 * Define o identificador do serviço JNDI onde o Datasource está armazenado.
	 *
	 * @param password String contendo a senha do usuário.
	 */
	public void setPassword(String password){
		this.password = password;
	}

	/**
	 * Retorna o identificador do repositório de persistência.
	 *
	 * @return String contendo o identificador do repositório.
	 */
	public String getRepositoryId(){
		return repositoryId;
	}

	/**
	 * Define o identificador do repositório de persistência.
	 *
	 * @param repositoryId String contendo o identificador do repositório.
	 */
	public void setRepositoryId(String repositoryId){
		this.repositoryId = repositoryId;
	}

	/**
	 * Retorna o IP/nome do servidor do repositório de persistência. 
	 *
	 * @return String contendo IP/nome do servidor.
	 */
	public String getServerName(){
		return serverName;
	}

	/**
	 * Define o IP/nome do servidor do repositório de persistência. 
	 *
	 * @param serverName String contendo IP/nome do servidor.
	 */
	public void setServerName(String serverName){
		this.serverName = serverName;
	}

	/**
	 * Retorna a porta de comunicação com o repositório de persistência.
	 *
	 * @return Valor inteiro contendo a porta de comunicação.
	 */
	public Integer getServerPort(){
		return serverPort;
	}

	/**
	 * Define a porta de comunicação com o repositório de persistência.
	 *
	 * @param serverPort Valor inteiro contendo a porta de comunicação.
	 */
	public void setServerPort(Integer serverPort){
		this.serverPort = serverPort;
	}

	/**
	 * Retorna o nome do usuário para conexão com o repositório de persistência.
	 *
	 * @return String contendo o nome do usuário.
	 */
	public String getUser(){
		return user;
	}

	/**
	 * Define o nome do usuário para conexão com o repositório de persistência.
	 *
	 * @param user String contendo o nome do usuário.
	 */
	public void setUser(String user){
		this.user = user;
	}

	/**
	 * Retorna o mapa contendo as opções extras para conexão com o repositório de persistência.
	 *
	 * @return Instância contendo as opções extras para conexão.
	 */
	public Map<String, String> getOptions(){
		return options;
	}

	/**
	 * Define o mapa contendo as opções extras para conexão com o repositório de persistência.
	 *
	 * @param options Instância contendo as opções extras para conexão.
	 */
	public void setOptions(Map<String, String> options){
		this.options = options;
	}

	/**
	 * Adiciona uma opção extra para conexão com o repositório de persistência.
	 *
	 * @param id String contendo o identificador da opção extra.
	 * @param value String contendo o valor da opção extra.
	 */
	public void addOption(String id, String value){
		if(options == null)
			options = new LinkedHashMap<String, String>();

		options.put(id, value);
	}
	
	/**
	 * Adiciona um mapeamento de um modelo de dados.
	 *   
	 * @param mapping String contendo o identificador do mapeamento.
	 */
	public void addMapping(String mapping){
	    if(mappings == null)
	        mappings = new LinkedList<String>();
	    
	    mappings.add(mapping);
	}
}