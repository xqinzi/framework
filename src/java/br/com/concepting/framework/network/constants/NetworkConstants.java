package br.com.concepting.framework.network.constants;

import br.com.concepting.framework.network.mail.types.MailStorageType;
import br.com.concepting.framework.network.mail.types.MailTransportType;
import br.com.concepting.framework.resource.constants.ResourceConstants;

/**
 * Classe que define as constantes utilizadas pelas rotinas de rede.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public abstract class NetworkConstants extends ResourceConstants{
    /**
     * Constante que define o identificador do protocolo HTTP.
     */
    public static final String HTTP_PROTOCOL_ID = "http";

    /**
     * Constante que define o identificador do protocolo HTTPs.
     */
    public static final String HTTPS_PROTOCOL_ID = "https";
    
    /**
     * Constante que define o identificador do IP/Hostname local.
     */
    public static final String LOCALHOST_ID = "localhost";
    
    /**
     * Constante que define o identificador do domínio local.
     */
    public static final String LOCALDOMAIN_ID = "localdomain";
    
    /**
     * Constante que define a porta padrão do serviço LDAP.
     */
    public static final Integer DEFAULT_LDAP_PORT = 389;
    
	/**
	 * Constante que define o identificador da factory de contexto LDAP.
	 */
	public static final String DEFAULT_LDAP_CONTEXT_FACTORY_ID = "com.sun.jndi.ldap.LdapCtxFactory";
    
	/**
     * Constante que define o protocolo de transporte default para mensagens de e-Mail.
     */
    public static final MailTransportType DEFAULT_MAIL_TRANSPORT = MailTransportType.SMTP;
    
    /**
     * Constante que define a porta default do protocolo de transporte para as mensagens de e-Mail.
     */
    public static final Integer DEFAULT_MAIL_TRANSPORT_PORT = 25;
    
    /**
     * Constante que define o protocolo de armazenamento default das mensagens de e-Mail.
     */
    public static final MailStorageType DEFAULT_MAIL_STORAGE = MailStorageType.POP3;
    
    /**
     * Constante que define a porta default do protocolo de armazenamento das as mensagens de e-Mail.
     */
    public static final Integer DEFAULT_MAIL_STORAGE_PORT = 110;
    
    /**
     * Constante que define o tempo (em milisegundos) de timeout default para ping de um elemento de rede.
     */
    public static final Integer DEFAULT_PINGER_TIMEOUT = 5000;
    
    /**
     * Constante que define o número de tentativos de pings de um elemento de rede.
     */
    public static final Integer DEFAULT_PINGER_TRIES = 4;
    
    /**
     * Constante que define o percentual de sucesso a ser considerado em um ping.
     */
    public static final Double DEFAULT_PINGER_SUCCESS_PERCENTAGE = 75d;
    
    /**
     * Constante que define o identificador do arquivo de configurações default para as rotina de rede.
     */
    public static final String DEFAULT_RESOURCE_ID = DEFAULT_RESOURCES_DIR.concat("networkResources.xml");
    
    /**
     * Constante que define o número máximo de registros que devem retornar em uma consulta SNMP.
     */
    public static final Integer DEFAULT_SNMP_MAX_RESULTS = 3000;
    
    /**
     * Constante que define o tempo (em milisegundos) de timeout default para uma operação SNMP.
     */
    public static final Integer DEFAULT_SNMP_TIMEOUT = 5000;
    
    /**
     * Constante que define a porta default de comunicação do protocolo SNMP.
     */
    public static final Integer DEFAULT_SNMP_PORT = 161;
    
    /**
     * Constante que define a community SNMP default para leitura.
     */
    public static final String DEFAULT_SNMP_READ_COMMUNITY_ID = "public";
    
    /**
     * Constante que define a community SNMP default para escrita.
     */
    public static final String DEFAULT_SNMP_WRITE_COMMUNITY_ID = "public";
}
