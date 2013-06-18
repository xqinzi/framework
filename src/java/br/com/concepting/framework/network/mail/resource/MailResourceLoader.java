package br.com.concepting.framework.network.mail.resource;

import br.com.concepting.framework.network.mail.types.MailTransportType;
import br.com.concepting.framework.network.resource.NetworkResourceLoader;
import br.com.concepting.framework.resource.BaseResource;
import br.com.concepting.framework.resource.exceptions.InvalidResourceException;
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
        XmlNode      resourceNode = resource.getNode("storage");
        
        if(resourceNode != null){
            XmlNode serverNameNode = resourceNode.getNode("serverName");
            
            if(serverNameNode != null){
                String serverName = StringUtil.trim(serverNameNode.getValue());

                if(serverName.length() == 0) 
                    throw new InvalidResourceException(getResourceId(), resourceNode.getText());

                mailResource.setTransportServerName(serverName);
            }
            
            XmlNode serverPortNode = resourceNode.getNode("serverPort");
            
            if(serverPortNode != null){
                try{
                    Integer serverPort = Integer.parseInt(serverPortNode.getValue());

                    mailResource.setTransportServerPort(serverPort);
                }
                catch(Throwable e){
                    throw new InvalidResourceException(getResourceId(), resourceNode.getText(), e);
                }
            }
            
            try{
                String transportType = StringUtil.trim(resourceNode.getAttribute("type")).toUpperCase();

                mailResource.setTransport(MailTransportType.valueOf(transportType));
            }
            catch(IllegalArgumentException e){
                throw new InvalidResourceException(getResourceId(), resourceNode.getText(), e);
            }

            XmlNode userNode = resourceNode.getNode("user");
            
            if(userNode != null){
                String user = StringUtil.trim(userNode.getValue());
                
                mailResource.setTransportUser(user);
            }

            XmlNode passwordNode = resourceNode.getNode("password");
            
            if(passwordNode != null){
                String password = StringUtil.trim(passwordNode.getValue());
                
                mailResource.setTransportPassword(password);
            }
            
            try{
                Boolean useSsl = Boolean.parseBoolean(resourceNode.getAttribute("useSsl"));

                mailResource.setTransportUseSsl(useSsl);
            }
            catch(IllegalArgumentException e){
                throw new InvalidResourceException(getResourceId(), resourceNode.getText(), e);
            }

            try{
                Boolean useTls = Boolean.parseBoolean(resourceNode.getAttribute("useTls"));

                mailResource.setTransportUseTls(useTls);
            }
            catch(IllegalArgumentException e){
                throw new InvalidResourceException(getResourceId(), resourceNode.getText(), e);
            }
        }

        resourceNode = resource.getNode("transport");
		
		if(resourceNode != null){
	        XmlNode serverNameNode = resourceNode.getNode("serverName");
	        
	        if(serverNameNode != null){
	            String serverName = StringUtil.trim(serverNameNode.getValue());

	            if(serverName.length() == 0) 
	                throw new InvalidResourceException(getResourceId(), resourceNode.getText());

	            mailResource.setTransportServerName(serverName);
	        }
		    
            XmlNode serverPortNode = resourceNode.getNode("serverPort");
            
            if(serverPortNode != null){
                try{
                    Integer serverPort = Integer.parseInt(serverPortNode.getValue());

                    mailResource.setTransportServerPort(serverPort);
                }
                catch(Throwable e){
                    throw new InvalidResourceException(getResourceId(), resourceNode.getText(), e);
                }
            }
            
            XmlNode userNode = resourceNode.getNode("user");
            
            if(userNode != null){
                String user = StringUtil.trim(userNode.getValue());
                
                mailResource.setTransportUser(user);
            }

            XmlNode passwordNode = resourceNode.getNode("password");
            
            if(passwordNode != null){
                String password = StringUtil.trim(passwordNode.getValue());
                
                mailResource.setTransportPassword(password);
            }

            try{
                Boolean useSsl = Boolean.parseBoolean(resourceNode.getAttribute("useSsl"));

                mailResource.setTransportUseSsl(useSsl);
            }
            catch(IllegalArgumentException e){
                throw new InvalidResourceException(getResourceId(), resourceNode.getText(), e);
            }

            try{
                Boolean useTls = Boolean.parseBoolean(resourceNode.getAttribute("useTls"));

                mailResource.setTransportUseTls(useTls);
            }
            catch(IllegalArgumentException e){
                throw new InvalidResourceException(getResourceId(), resourceNode.getText(), e);
            }
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