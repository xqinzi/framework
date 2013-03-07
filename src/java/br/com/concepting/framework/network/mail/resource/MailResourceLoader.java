package br.com.concepting.framework.network.mail.resource;

import br.com.concepting.framework.network.constants.NetworkConstants;
import br.com.concepting.framework.network.mail.types.MailStorageType;
import br.com.concepting.framework.network.mail.types.MailTransportType;
import br.com.concepting.framework.network.resource.NetworkResourceLoader;
import br.com.concepting.framework.resource.BaseResource;
import br.com.concepting.framework.resource.exceptions.InvalidResourceException;
import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.XmlNode;

/** 
 * Classe responsável pela leitura/manipulação das configurações do protocolo de envio/recebimento 
 * de mensagens de e-Mail.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class MailResourceLoader extends NetworkResourceLoader{
	/**
	 * Construtor - Inicializa classe de leitura/manipulação do arquivo de configurações do 
	 * protocolo de envio/recebimento de mensagens de e-Mail default.
	 * 
	 * @throws InvalidResourceException
	 */
	public MailResourceLoader() throws InvalidResourceException{
		super();
	}

	/**
	 * @see br.com.concepting.framework.resource.XmlResourceLoader#get(java.lang.String)
	 */
    public <R extends BaseResource> R get(String id) throws InvalidResourceException{
		MailResource mailResource = super.get(id);
		XmlNode      resource     = mailResource.getContent();
		XmlNode      resourceNode = null;

		resourceNode = resource.getNode("serverName");
		if(resourceNode != null){
			String serverName = StringUtil.trim(resourceNode.getValue());

			if(serverName.length() == 0) 
				throw new InvalidResourceException(getResourceId(), resourceNode.getText());

			mailResource.setServerName(serverName);
		}
		else
		    mailResource.setServerName(NetworkConstants.LOCALHOST_ID);

		resourceNode = resource.getNode("transport");
		if(resourceNode != null){
			try{
				String transportType = StringUtil.trim(resourceNode.getAttribute("type")).toUpperCase();

				mailResource.setTransport(MailTransportType.valueOf(transportType));
			}
			catch(IllegalArgumentException e){
				throw new InvalidResourceException(getResourceId(), resourceNode.getText(), e);
			}

			try{
				Integer transportPort = NumberUtil.parseInt(StringUtil.trim(resourceNode.getAttribute("port")));

				mailResource.setTransportPort(transportPort);
			}
			catch(Throwable e){
				throw new InvalidResourceException(getResourceId(), resourceNode.getText(), e);
			}
		}
		else{
		    mailResource.setTransport(NetworkConstants.DEFAULT_MAIL_TRANSPORT);
		    mailResource.setTransportPort(NetworkConstants.DEFAULT_MAIL_TRANSPORT_PORT);
		}

		resourceNode = resource.getNode("storage");
		if(resourceNode != null){
			try{
				String storageType = StringUtil.trim(resourceNode.getAttribute("type"));

				mailResource.setStorage(MailStorageType.valueOf(storageType));
			}
			catch(IllegalArgumentException e){
				throw new InvalidResourceException(getResourceId(), resourceNode.getText(), e);
			}

			try{
				Integer storagePort = NumberUtil.parseInt(StringUtil.trim(resourceNode.getAttribute("port")));

				mailResource.setStoragePort(storagePort);
			}
			catch(Throwable e){
				throw new InvalidResourceException(getResourceId(), resourceNode.getText(), e);
			}
		}
		else{
		    mailResource.setStorage(NetworkConstants.DEFAULT_MAIL_STORAGE);
		    mailResource.setStoragePort(NetworkConstants.DEFAULT_MAIL_STORAGE_PORT);
		}

		String useSsl = StringUtil.trim(resource.getAttribute("useSsl"));
		
		if(useSsl.length() > 0){
			try{
    			mailResource.setUseSsl(Boolean.parseBoolean(useSsl));
			}
			catch(Throwable e){
				throw new InvalidResourceException(getResourceId(), resource.getText(), e);
			}
		}

		String useTls = StringUtil.trim(resource.getAttribute("useTls"));
		
		if(useTls.length() > 0){
			try{
    			mailResource.setUseTls(Boolean.parseBoolean(useTls));
			}
			catch(Throwable e){
				throw new InvalidResourceException(getResourceId(), resource.getText(), e);
			}
		}

		resourceNode = resource.getNode("user");
		if(resourceNode != null){
			String user = StringUtil.trim(resourceNode.getValue());

			mailResource.setUser(user);
		}

		resourceNode = resource.getNode("password");
		if(resourceNode != null){
			String password = StringUtil.trim(resourceNode.getValue());

			mailResource.setPassword(password);
		}

		return (R)mailResource;
	}

	/**
	 * @see br.com.concepting.framework.resource.BaseResourceLoader#parseResource()
	 */
    protected <C> C parseResource() throws InvalidResourceException{
		XmlNode content = super.parseResource();

		content = content.getNode("mailResources");
		if(content == null) 
			throw new InvalidResourceException(getResourceId());

		return (C)content;
	}
}