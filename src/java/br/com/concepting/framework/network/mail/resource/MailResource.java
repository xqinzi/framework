package br.com.concepting.framework.network.mail.resource;

import br.com.concepting.framework.network.constants.NetworkConstants;
import br.com.concepting.framework.network.mail.types.MailStorageType;
import br.com.concepting.framework.network.mail.types.MailTransportType;
import br.com.concepting.framework.resource.BaseResource;
import br.com.concepting.framework.util.helpers.XmlNode;

/** 
 * Classe responsável pelo armazenamento das configurações dos protocolos de envio e recebimento 
 * de mensagens.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class MailResource extends BaseResource<XmlNode>{
    private static final long serialVersionUID = -4631808238382919312L;
    
    private MailTransportType transport           = NetworkConstants.DEFAULT_MAIL_TRANSPORT;
    private MailStorageType   storage             = NetworkConstants.DEFAULT_MAIL_STORAGE;
	private String            transportServerName = NetworkConstants.LOCALHOST_ID;
	private Integer           transportServerPort = NetworkConstants.DEFAULT_MAIL_TRANSPORT_PORT;
	private String            transportUser       = "";
	private String            transportPassword   = "";
	private Boolean           transportUseSsl     = false;
	private Boolean           transportUseTls     = false;
    private String            storageServerName   = NetworkConstants.LOCALHOST_ID;
    private Integer           storageServerPort   = NetworkConstants.DEFAULT_MAIL_STORAGE_PORT;
    private String            storageUser         = "";
    private String            storagePassword     = "";
	private Boolean           storageUseSsl       = false;
	private Boolean           storageUseTls       = false;
	
	/**
	 * Construtor - Inicializa objetos e/ou variáveis internas.
	 */
	public MailResource(){
		super();
	}

	/**
	 * Retorna o tipo de transporte de uma mensagem.
	 * 
	 * @return Constante que define o tipo de transporte.
	 */
    public MailTransportType getTransport(){
        return transport;
    }

    /**
     * Define o tipo de transporte de uma mensagem.
     * 
     * @param transport Constante que define o tipo de transporte.
     */
    public void setTransport(MailTransportType transport){
        this.transport = transport;
    }

    /**
     * Retorna o tipo de armazenagem de uma mensagem.
     * 
     * @return Constante que define o tipo de armazenagem.
     */
    public MailStorageType getStorage(){
        return storage;
    }

    /**
     * Define o tipo de armazenagem de uma mensagem.
     * 
     * @param storage Constante que define o tipo de armazenagem.
     */
    public void setStorage(MailStorageType storage){
        this.storage = storage;
    }

    /**
     * Retorna o IP/Hostname do servidor de transporte de mensagens.
     * 
     * @return String contendo o IP/Hostname.
     */
    public String getTransportServerName(){
        return transportServerName;
    }

    /**
     * Define o IP/Hostname do servidor de transporte de mensagens.
     * 
     * @param transportServerName String contendo o IP/Hostname.
     */
    public void setTransportServerName(String transportServerName){
        this.transportServerName = transportServerName;
    }

    /**
     * Retorna a porta de comunicação com o servidor de transporte de mensagens.
     * 
     * @return Valor numérico contendo a porta de comunicação.
     */
    public Integer getTransportServerPort(){
        return transportServerPort;
    }

    /**
     * Define a porta de comunicação com o servidor de transporte de mensagens.
     * 
     * @param transportServerPort Valor numérico contendo a porta de comunicação.
     */
    public void setTransportServerPort(Integer transportServerPort){
        this.transportServerPort = transportServerPort;
    }

    /**
     * Retorna o nome do usuário para autenticação com o servidor de 
     * transporte de mensagens.
     * 
     * @return String contendo o nome do usuário.
     */
    public String getTransportUser(){
        return transportUser;
    }

    /**
     * Define o nome do usuário para autenticação com o servidor de 
     * transporte de mensagens.
     * 
     * @param transportUser String contendo o nome do usuário.
     */
    public void setTransportUser(String transportUser){
        this.transportUser = transportUser;
    }

    /**
     * Retorna a senha do nome do usuário para autenticação com o 
     * servidor de transporte de mensagens.
     * 
     * @return String contendo o nome do usuário.
     */
    public String getTransportPassword(){
        return transportPassword;
    }

    /**
     * Define a senha do nome do usuário para autenticação com o 
     * servidor de transporte de mensagens.
     * 
     * @param transportPassword String contendo o nome do usuário.
     */
    public void setTransportPassword(String transportPassword){
        this.transportPassword = transportPassword;
    }

    /**
     * Indica se a comunicação com o servidor de transporte de 
     * mensagens deve utilizar SSL.
     * 
     * @return True/False.
     */
    public Boolean getTransportUseSsl(){
        return transportUseSsl;
    }

    /**
     * Define se a comunicação com o servidor de transporte de 
     * mensagens deve utilizar SSL.
     * 
     * @param transportUseSsl True/False.
     */
    public void setTransportUseSsl(Boolean transportUseSsl){
        this.transportUseSsl = transportUseSsl;
    }

    /**
     * Indica se a comunicação com o servidor de transporte de 
     * mensagens deve utilizar TLS.
     * 
     * @return True/False.
     */
    public Boolean getTransportUseTls(){
        return transportUseTls;
    }

    /**
     * Define se a comunicação com o servidor de transporte de 
     * mensagens deve utilizar TLS.
     * 
     * @param transportUseTls True/False.
     */
    public void setTransportUseTls(Boolean transportUseTls){
        this.transportUseTls = transportUseTls;
    }

    /**
     * Retorna o IP/Hostname do servidor de armazenamento de mensagens.
     * 
     * @return String contendo o IP/Hostname.
     */
    public String getStorageServerName(){
        return storageServerName;
    }

    /**
     * Define o IP/Hostname do servidor de armazenamento de mensagens.
     * 
     * @param storageServerName String contendo o IP/Hostname.
     */
    public void setStorageServerName(String storageServerName){
        this.storageServerName = storageServerName;
    }

    /**
     * Retorna a porta de comunicação com o servidor de armazenamento de mensagens.
     * 
     * @return Valor numérico contendo a porta de comunicação.
     */
    public Integer getStorageServerPort(){
        return storageServerPort;
    }

    /**
     * Define a porta de comunicação com o servidor de armazenamento de mensagens.
     * 
     * @param storageServerPort Valor numérico contendo a porta de comunicação.
     */
    public void setStorageServerPort(Integer storageServerPort){
        this.storageServerPort = storageServerPort;
    }

    /**
     * Retorna o nome do usuário para autenticação com o servidor de 
     * armazenamento de mensagens.
     * 
     * @return String contendo o nome do usuário.
     */
    public String getStorageUser(){
        return storageUser;
    }

    /**
     * Define o nome do usuário para autenticação com o servidor de 
     * armazenamento de mensagens.
     * 
     * @param storageUser String contendo o nome do usuário.
     */
    public void setStorageUser(String storageUser){
        this.storageUser = storageUser;
    }

    /**
     * Retorna a senha do nome do usuário para autenticação com o 
     * servidor de armazenamento de mensagens.
     * 
     * @return String contendo o nome do usuário.
     */
    public String getStoragePassword(){
        return storagePassword;
    }

    /**
     * Define a senha do nome do usuário para autenticação com o 
     * servidor de armazenamento de mensagens.
     * 
     * @param storagePassword String contendo o nome do usuário.
     */
    public void setStoragePassword(String storagePassword){
        this.storagePassword = storagePassword;
    }

    /**
     * Indica se a comunicação com o servidor de armazenamento de 
     * mensagens deve utilizar SSL.
     * 
     * @return True/False.
     */
    public Boolean getStorageUseSsl(){
        return storageUseSsl;
    }

    /**
     * Define se a comunicação com o servidor de armazenamento de 
     * mensagens deve utilizar SSL.
     * 
     * @param storageUseSsl True/False.
     */
    public void setStorageUseSsl(Boolean storageUseSsl){
        this.storageUseSsl = storageUseSsl;
    }

    /**
     * Retorn se a comunicação com o servidor de armazenamento de 
     * mensagens deve utilizar TLS.
     * 
     * @return True/False.
     */
    public Boolean getStorageUseTls(){
        return storageUseTls;
    }

    /**
     * Define se a comunicação com o servidor de armazenamento de 
     * mensagens deve utilizar TLS.
     * 
     * @param storageUseTls True/False.
     */
    public void setStorageUseTls(Boolean storageUseTls){
        this.storageUseTls = storageUseTls;
    }
}