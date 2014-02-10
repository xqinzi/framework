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
 * Classe respons�vel pelo armazenamento das configura��es de persist�ncia.
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
	 * Construtor - Inicializa objetos e/ou vari�veis internas.
	 */
	public PersistenceResource(){
		super();
	}
	
	/**
	 * Retorna a lista de mapeamentos de persist�ncia.
	 * 
	 * @return Lista contendo os mapeamentos de persist�ncia.
	 */
	public List<String> getMappings(){
        return mappings;
    }

    /**
     * Define a lista de mapeamentos de persist�ncia.
     * 
     * @param mappings Lista contendo os mapeamentos de persist�ncia.
     */
    public void setMappings(List<String> mappings){
        this.mappings = mappings;
    }

	/**
	 * Retorna o timeout de conex�o com o reposit�rio de persist�ncia.
	 * Utilizado somente quando n�o for usado Datasource.
	 *
	 * @return Valor inteiro contendo o timeout de conex�o.
	 */
	public Integer getTimeout(){
		return timeout;
	}

	/**
	 * Define o timeout de conex�o com o reposit�rio de persist�ncia.
	 * Utilizado somente quando n�o for usado Datasource.
	 *
	 * @param timeout Valor inteiro contendo o timeout de conex�o.
	 */
	public void setTimeout(Integer timeout){
		this.timeout = timeout;
	}

	/**
	 * Retorna a inst�ncia contendo as configura��es de contexto.
	 * Utilizando somente quando for usado Datasource.
	 *
	 * @return Inst�ncia contendo as configura��es de contexto.
	 */
	public ContextResource getContextResource(){
		return contextResource;
	}

	/**
	 * Define a inst�ncia contendo as configura��es de contexto.
	 * Utilizando somente quando for usado Datasource.
	 *
	 * @param contextResource Inst�ncia contendo as configura��es de contexto.
	 */
	public void setContextResource(ContextResource contextResource){
		this.contextResource = contextResource;
	}

	/**
	 * Retorna a inst�ncia contendo as configura��es do driver de conex�o.
	 * Utilizando somente quando for n�o usado Datasource.
	 *
	 * @return Inst�ncia contendo as configura��es do driver de conex�o.
	 */
	public FactoryResource getFactoryResource(){
		return factoryResource;
	}

	/**
	 * Define a inst�ncia contendo as configura��es do driver de conex�o.
	 * Utilizando somente quando for n�o usado Datasource.
	 *
	 * @param factoryResource Inst�ncia contendo as configura��es do driver de conex�o.
	 */
	public void setFactoryResource(FactoryResource factoryResource){
		this.factoryResource = factoryResource;
	}

	/**
	 * Retorna o identificador do servi�o onde o Datasource est� armazenado.
	 *
	 * @return String contendo o identificador do Datasource.
	 */
	public String getLookupName(){
		return lookupName;
	}

	/**
	 * Define o identificador do servi�o onde o Datasource est� armazenado.
	 *
	 * @param lookupName String contendo o identificador do Datasource.
	 */
	public void setLookupName(String lookupName){
		this.lookupName = lookupName;
	}

	/**
	 * Retorna a senha do usu�rio para conex�o com o reposit�rio de persist�ncia.
	 *
	 * @return String contendo a senha do usu�rio.
	 */
	public String getPassword(){
		return password;
	}

	/**
	 * Define o identificador do servi�o JNDI onde o Datasource est� armazenado.
	 *
	 * @param password String contendo a senha do usu�rio.
	 */
	public void setPassword(String password){
		this.password = password;
	}

	/**
	 * Retorna o identificador do reposit�rio de persist�ncia.
	 *
	 * @return String contendo o identificador do reposit�rio.
	 */
	public String getRepositoryId(){
		return repositoryId;
	}

	/**
	 * Define o identificador do reposit�rio de persist�ncia.
	 *
	 * @param repositoryId String contendo o identificador do reposit�rio.
	 */
	public void setRepositoryId(String repositoryId){
		this.repositoryId = repositoryId;
	}

	/**
	 * Retorna o IP/nome do servidor do reposit�rio de persist�ncia. 
	 *
	 * @return String contendo IP/nome do servidor.
	 */
	public String getServerName(){
		return serverName;
	}

	/**
	 * Define o IP/nome do servidor do reposit�rio de persist�ncia. 
	 *
	 * @param serverName String contendo IP/nome do servidor.
	 */
	public void setServerName(String serverName){
		this.serverName = serverName;
	}

	/**
	 * Retorna a porta de comunica��o com o reposit�rio de persist�ncia.
	 *
	 * @return Valor inteiro contendo a porta de comunica��o.
	 */
	public Integer getServerPort(){
		return serverPort;
	}

	/**
	 * Define a porta de comunica��o com o reposit�rio de persist�ncia.
	 *
	 * @param serverPort Valor inteiro contendo a porta de comunica��o.
	 */
	public void setServerPort(Integer serverPort){
		this.serverPort = serverPort;
	}

	/**
	 * Retorna o nome do usu�rio para conex�o com o reposit�rio de persist�ncia.
	 *
	 * @return String contendo o nome do usu�rio.
	 */
	public String getUser(){
		return user;
	}

	/**
	 * Define o nome do usu�rio para conex�o com o reposit�rio de persist�ncia.
	 *
	 * @param user String contendo o nome do usu�rio.
	 */
	public void setUser(String user){
		this.user = user;
	}

	/**
	 * Retorna o mapa contendo as op��es extras para conex�o com o reposit�rio de persist�ncia.
	 *
	 * @return Inst�ncia contendo as op��es extras para conex�o.
	 */
	public Map<String, String> getOptions(){
		return options;
	}

	/**
	 * Define o mapa contendo as op��es extras para conex�o com o reposit�rio de persist�ncia.
	 *
	 * @param options Inst�ncia contendo as op��es extras para conex�o.
	 */
	public void setOptions(Map<String, String> options){
		this.options = options;
	}

	/**
	 * Adiciona uma op��o extra para conex�o com o reposit�rio de persist�ncia.
	 *
	 * @param id String contendo o identificador da op��o extra.
	 * @param value String contendo o valor da op��o extra.
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