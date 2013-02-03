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
	private String            serverName    = "";
	private MailTransportType transport     = NetworkConstants.DEFAULT_MAIL_TRANSPORT;
	private Integer           transportPort = NetworkConstants.DEFAULT_MAIL_TRANSPORT_PORT;
	private MailStorageType   storage       = NetworkConstants.DEFAULT_MAIL_STORAGE;
	private Integer           storagePort   = NetworkConstants.DEFAULT_MAIL_STORAGE_PORT;
	private String            user          = "";
	private String            password      = "";
	private Boolean           useSsl        = false;
	private Boolean           useTls        = false;
	
	/**
	 * Construtor - Inicializa objetos e/ou variáveis internas.
	 */
	public MailResource(){
		super();
	}

	/**
	 * Indica se a conexão com o servidor de e-Mail deve utilizar SSL.
	 *  
	 * @return True/False.
	 */
	public Boolean useSsl(){
    	return useSsl;
    }
	
	/**
	 * Indica se a conexão com o servidor de e-Mail deve utilizar SSL.
	 *  
	 * @return True/False.
	 */
	public Boolean getUseSsl(){
		return useSsl();
	}

	/**
	 * Define se a conexão com o servidor de e-Mail deve utilizar SSL.
	 *  
	 * @param useSsl True/False.
	 */
	public void setUseSsl(Boolean useSsl){
    	this.useSsl = useSsl;
    }
	 
	/**
	 * Indica se a conexão com o servidor de e-Mail deve utilizar TLS.
	 *  
	 * @return True/False.
	 */
	public Boolean useTls(){
		return useTls;
	}

	/**
	 * Indica se a conexão com o servidor de e-Mail deve utilizar TLS.
	 *  
	 * @return True/False.
	 */
	public Boolean getUseTls(){
    	return useTls();
    }

	/**
	 * Define se a conexão com o servidor de e-Mail deve utilizar SSL.
	 *  
	 * @param useTls True/False.
	 */
	public void setUseTls(Boolean useTls){
    	this.useTls = useTls;
    }

	/**
	 * Retorna o nome/IP do servidor de e-Mail.
	 * 
	 * @return String contendo o nome/IP do servidor.
	 */
	public String getServerName(){
		return serverName;
	}

	/**
	 * Define o nome/IP do servidor de e-Mail.
	 * 
	 * @param serverName String contendo o nome/IP do servidor.
	 */
	public void setServerName(String serverName){
		this.serverName = serverName;
	}

	/**
	 * Retorna o protocolo de transporte das mensagens.
	 * 
	 * @return Instância da constante do protocolo de transporte das mensagens.
	 */
	public MailTransportType getTransport(){
		return transport;
	}

	/**
	 * Define o protocolo de transporte das mensagens.
	 * 
	 * @param transport Instância da constante do protocolo de transporte das mensagens.
	 */
	public void setTransport(MailTransportType transport){
		this.transport = transport;
	}

	/**
	 * Retorna a porta utilizada pelo protocolo de transporte das mensagens.
	 * 
	 * @return Valor inteiro contendo a porta utilizada pelo protocolo de transporte.
	 */
	public Integer getTransportPort(){
		return transportPort;
	}

	/**
	 * Define a porta utilizada pelo protocolo de transporte das mensagens.
	 * 
	 * @param transportPort Valor inteiro contendo a porta utilizada pelo protocolo de transporte.
	 */
	public void setTransportPort(Integer transportPort){
		this.transportPort = transportPort;
	}

	/**
	 * Retorna o protocolo de armazenamento das mensagens.
	 * 
	 * @return Instância da constante do protocolo de armazenamento das mensagens.
	 */
	public MailStorageType getStorage(){
		return storage;
	}

	/**
	 * Define o protocolo de armazenamento das mensagens.
	 * 
	 * @param storage Instância da constante do protocolo de armazenamento das mensagens.
	 */
	public void setStorage(MailStorageType storage){
		this.storage = storage;
	}

	/**
	 * Retorna a porta utilizada pelo protocolo de armazenamento das mensagens.
	 * 
	 * @return Valor inteiro contendo a porta utilizada pelo protocolo de armazenamento.
	 */
	public Integer getStoragePort(){
		return storagePort;
	}

	/**
	 * Define a porta utilizada pelo protocolo de armazenamento das mensagens.
	 * 
	 * @param storagePort Valor inteiro contendo a porta utilizada pelo protocolo de armazenamento.
	 */
	public void setStoragePort(Integer storagePort){
		this.storagePort = storagePort;
	}

	/**
	 * Retorna o nome do usuário para conexão com o servidor de e-Mail.
	 * 
	 * @return String contendo o nome do usuário.
	 */
	public String getUser(){
		return user;
	}

	/**
	 * Define o nome do usuário para conexão com o servidor de e-Mail.
	 * 
	 * @param user String contendo o nome do usuário.
	 */
	public void setUser(String user){
		this.user = user;
	}

	/**
	 * Retorna a senha para conexão com o servidor de e-Mail.
	 * 
	 * @return String contendo a senha.
	 */
	public String getPassword(){
		return password;
	}

	/**
	 * Define a senha para conexão com o servidor de e-Mail.
	 * 
	 * @param password String contendo a senha.
	 */
	public void setPassword(String password){
		this.password = password;
	}
}