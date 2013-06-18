package br.com.concepting.framework.network.mail.resource;

import br.com.concepting.framework.network.constants.NetworkConstants;
import br.com.concepting.framework.network.mail.types.MailStorageType;
import br.com.concepting.framework.network.mail.types.MailTransportType;
import br.com.concepting.framework.resource.BaseResource;

/** 
 * Classe responsável pelo armazenamento das configurações dos protocolos de envio e recebimento 
 * de e-Mail.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class MailResource extends BaseResource{
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

    public MailTransportType getTransport(){
        return transport;
    }

    public void setTransport(MailTransportType transport){
        this.transport = transport;
    }

    public MailStorageType getStorage(){
        return storage;
    }

    public void setStorage(MailStorageType storage){
        this.storage = storage;
    }

    public String getTransportServerName(){
        return transportServerName;
    }

    public void setTransportServerName(String transportServerName){
        this.transportServerName = transportServerName;
    }

    public Integer getTransportServerPort(){
        return transportServerPort;
    }

    public void setTransportServerPort(Integer transportServerPort){
        this.transportServerPort = transportServerPort;
    }

    public String getTransportUser(){
        return transportUser;
    }

    public void setTransportUser(String transportUser){
        this.transportUser = transportUser;
    }

    public String getTransportPassword(){
        return transportPassword;
    }

    public void setTransportPassword(String transportPassword){
        this.transportPassword = transportPassword;
    }

    public Boolean getTransportUseSsl(){
        return transportUseSsl;
    }

    public void setTransportUseSsl(Boolean transportUseSsl){
        this.transportUseSsl = transportUseSsl;
    }

    public Boolean getTransportUseTls(){
        return transportUseTls;
    }

    public void setTransportUseTls(Boolean transportUseTls){
        this.transportUseTls = transportUseTls;
    }

    public String getStorageServerName(){
        return storageServerName;
    }

    public void setStorageServerName(String storageServerName){
        this.storageServerName = storageServerName;
    }

    public Integer getStorageServerPort(){
        return storageServerPort;
    }

    public void setStorageServerPort(Integer storageServerPort){
        this.storageServerPort = storageServerPort;
    }

    public String getStorageUser(){
        return storageUser;
    }

    public void setStorageUser(String storageUser){
        this.storageUser = storageUser;
    }

    public String getStoragePassword(){
        return storagePassword;
    }

    public void setStoragePassword(String storagePassword){
        this.storagePassword = storagePassword;
    }

    public Boolean getStorageUseSsl(){
        return storageUseSsl;
    }

    public void setStorageUseSsl(Boolean storageUseSsl){
        this.storageUseSsl = storageUseSsl;
    }

    public Boolean getStorageUseTls(){
        return storageUseTls;
    }

    public void setStorageUseTls(Boolean storageUseTls){
        this.storageUseTls = storageUseTls;
    }
}