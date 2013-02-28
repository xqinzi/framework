package br.com.concepting.framework.context.types;

/**
 * Classe que define as constantes para os tipos de servidores de aplica��es.
 *
 * @author fvilarinho
 * @since 1.0
 */
public enum ContextFactoryType{
    /**
     * Constante que define o servidor JBoss.
     */
    TOMCAT("Apache Tomcat", 1099, 8080),
    
	/**
	 * Constante que define o servidor JBoss.
	 */
	JBOSS("JBoss", 1099, 8080),
	
    /**
     * Constante que define o servidor Glassfish.
     */
	GLASSFISH("Oracle GlassFish", 3700, 8080),
	
	/**
	 * Constante que define o servidor Websphere.
	 */
	WEBSPHERE("IBM Websphere", 2809, 8080),
	
    /**
     * Constante que define o servidor Weblogic.
     */
	WEBLOGIC("Oracle Weblogic", 7001, 8080);
	
	private String  description       = "";
    private Integer defaultLookupPort = 0;
	private Integer defaultServerPort = 0;
	
	/**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param description String contendo a descri��o desejada.
	 * @param defaultLookupPort Valor inteiro contendo a porta default de comunica��o.
     * @param defaultServerPort Valor inteiro contendo a porta default de comunica��o.
	 */
	private ContextFactoryType(String description, Integer defaultLookupPort, Integer defaultServerPort){
		setDescription(description);
		setDefaultLookupPort(defaultLookupPort);
		setDefaultServerPort(defaultServerPort);
	}

	/**
	 * Retorna a porta HTTP default para comunica��o do servidor de aplica��es.
	 * 
	 * @return Valor inteiro contendo o n�mero da porta.
	 */
	public Integer getDefaultServerPort(){
    	return defaultServerPort;
    }

    /**
     * Define a porta HTTP default para comunica��o do servidor de aplica��es.
     * 
     * @param defaultServerPort Valor inteiro contendo o n�mero da porta.
     */
	public void setDefaultServerPort(Integer defaultServerPort){
    	this.defaultServerPort = defaultServerPort;
    }

    /**
     * Retorna a porta JNDI default para comunica��o do servidor de aplica��es.
     * 
     * @return Valor inteiro contendo o n�mero da porta.
     */
    public Integer getDefaultLookupPort(){
        return defaultLookupPort;
    }

    /**
     * Define a porta HTTP default para comunica��o do servidor de aplica��es.
     * 
     * @param defaultLookupPort Valor inteiro contendo o n�mero da porta.
     */
    public void setDefaultLookupPort(Integer defaultLookupPort){
        this.defaultLookupPort = defaultLookupPort;
    }

    /**
	 * Retorna a descri��o do servidor de aplica��es.
	 *
	 * @return String contendo a descri��o do servidor de aplica��es.
	 */
	public String getDescription(){
    	return description;
    }

	/**
	 * Define a descri��o do servidor de aplica��es.
	 *
	 * @param description String contendo a descri��o do servidor de aplica��es.
	 */
	public void setDescription(String description){
    	this.description = description;
    }
}