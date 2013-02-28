package br.com.concepting.framework.context.types;

/**
 * Classe que define as constantes para os tipos de servidores de aplicações.
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
	 * @param description String contendo a descrição desejada.
	 * @param defaultLookupPort Valor inteiro contendo a porta default de comunicação.
     * @param defaultServerPort Valor inteiro contendo a porta default de comunicação.
	 */
	private ContextFactoryType(String description, Integer defaultLookupPort, Integer defaultServerPort){
		setDescription(description);
		setDefaultLookupPort(defaultLookupPort);
		setDefaultServerPort(defaultServerPort);
	}

	/**
	 * Retorna a porta HTTP default para comunicação do servidor de aplicações.
	 * 
	 * @return Valor inteiro contendo o número da porta.
	 */
	public Integer getDefaultServerPort(){
    	return defaultServerPort;
    }

    /**
     * Define a porta HTTP default para comunicação do servidor de aplicações.
     * 
     * @param defaultServerPort Valor inteiro contendo o número da porta.
     */
	public void setDefaultServerPort(Integer defaultServerPort){
    	this.defaultServerPort = defaultServerPort;
    }

    /**
     * Retorna a porta JNDI default para comunicação do servidor de aplicações.
     * 
     * @return Valor inteiro contendo o número da porta.
     */
    public Integer getDefaultLookupPort(){
        return defaultLookupPort;
    }

    /**
     * Define a porta HTTP default para comunicação do servidor de aplicações.
     * 
     * @param defaultLookupPort Valor inteiro contendo o número da porta.
     */
    public void setDefaultLookupPort(Integer defaultLookupPort){
        this.defaultLookupPort = defaultLookupPort;
    }

    /**
	 * Retorna a descrição do servidor de aplicações.
	 *
	 * @return String contendo a descrição do servidor de aplicações.
	 */
	public String getDescription(){
    	return description;
    }

	/**
	 * Define a descrição do servidor de aplicações.
	 *
	 * @param description String contendo a descrição do servidor de aplicações.
	 */
	public void setDescription(String description){
    	this.description = description;
    }
}