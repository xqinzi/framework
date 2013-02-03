package br.com.concepting.framework.context.types;

import br.com.concepting.framework.util.interfaces.IEnum;

/**
 * Classe que define as constantes para os tipos de servidores de aplica��es.
 *
 * @author fvilarinho
 * @since 1.0
 */
public enum ContextFactoryType implements IEnum{
    /**
     * Constante que define o servidor JBoss.
     */
    TOMCAT("tomcat", "Apache Tomcat", 1099, 8080),
    
	/**
	 * Constante que define o servidor JBoss.
	 */
	JBOSS("jboss", "JBoss", 1099, 8080),
	
    /**
     * Constante que define o servidor Glassfish.
     */
	GLASSFISH("glassfish", "Oracle GlassFish", 3700, 8080),
	
	/**
	 * Constante que define o servidor Websphere.
	 */
	WEBSPHERE("websphere", "IBM Websphere", 2809, 8080);
	
	private Object  key               = null;
	private String  description       = "";
    private Integer defaultLookupPort = 0;
	private Integer defaultServerPort = 0;
	
	/**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param key String contendo o valor desejado.
	 * @param description String contendo a descri��o desejada.
	 * @param defaultLookupPort Valor inteiro contendo a porta default de comunica��o.
     * @param defaultServerPort Valor inteiro contendo a porta default de comunica��o.
	 */
	private ContextFactoryType(String key, String description, Integer defaultLookupPort, Integer defaultServerPort){
		setKey(key);
		setDescription(description);
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

	/**
	 * @see br.com.concepting.framework.util.interfaces.IEnum#getKey()
	 */
    public <O> O getKey(){
	    return (O)key;
    }

	/**
	 * @see br.com.concepting.framework.util.interfaces.IEnum#setKey(java.lang.Object)
	 */
	public <O> void setKey(O key){
		this.key = key;
    }

	/**
	 * @see java.lang.Enum#toString()
	 */
	public String toString(){
		return key.toString();
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IEnum#toEnum(java.lang.Object)
	 */
	public <O> IEnum toEnum(O value) throws IllegalArgumentException{
		return toContextFactoryType((String)value);
    }
	
	/**
	 * Converte uma string em uma inst�ncia da constante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @return Inst�ncia da constante.
	 */
	public static ContextFactoryType toContextFactoryType(String value) throws IllegalArgumentException{
		if(value == null)
			throw new IllegalArgumentException();
		
		for(ContextFactoryType constant : values())
			if(value.equals(constant.getKey()) || value.equals(constant.getDescription()))
				return constant;
		
		throw new IllegalArgumentException(value);
	}
}