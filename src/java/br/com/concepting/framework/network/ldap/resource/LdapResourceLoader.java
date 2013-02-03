package br.com.concepting.framework.network.ldap.resource;

import br.com.concepting.framework.network.resource.NetworkResourceLoader;
import br.com.concepting.framework.resource.BaseResource;
import br.com.concepting.framework.resource.exceptions.InvalidResourceException;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Classe responsável pela leitura/manipulação das configurações do serviço de diretórios LDAP.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class LdapResourceLoader extends NetworkResourceLoader{
    /**
     * Construtor - Inicializa classe de leitura/manipulação do arquivo de onfigurações do serviço 
     * de diretórios LDAP default.
     * 
     * @throws InvalidResourceException
     */
	public LdapResourceLoader() throws InvalidResourceException{
		super();
	}

	/**
	 * @see br.com.concepting.framework.resource.XmlResourceLoader#get(java.lang.String)
	 */
    public <R extends BaseResource> R get(String id) throws InvalidResourceException{
		LdapResource ldapResource = super.get(id);
		XmlNode      resource     = ldapResource.getContent();
		XmlNode      resourceNode = null;

		resourceNode = resource.getNode("serverName");
		if(resourceNode != null){
			String serverName = StringUtil.trim(resourceNode.getValue());

			if(serverName.length() == 0) 
				throw new InvalidResourceException(getResourceId(), resourceNode.getText());

			ldapResource.setServerName(serverName);
		}
		else 
			throw new InvalidResourceException(getResourceId(), resource.getText());

		resourceNode = resource.getNode("serverPort");
		if(resourceNode != null){
			try{
    			Integer serverPort = Integer.parseInt(StringUtil.trim(resourceNode.getValue()));
    
    			ldapResource.setServerPort(serverPort);
			}
			catch(Throwable e){
				throw new InvalidResourceException(getResourceId(), resourceNode.getText(), e);
			}
		}
		else 
			throw new InvalidResourceException(getResourceId(), resource.getText());

		resourceNode = resource.getNode("authenticationType");
		if(resourceNode != null){
			String authenticationType = StringUtil.trim(resourceNode.getValue());

			ldapResource.setAuthenticationType(authenticationType);
		}

		resourceNode = resource.getNode("user");
		if(resourceNode != null){
			String user = StringUtil.trim(resourceNode.getValue());

			ldapResource.setUser(user);
		}

		resourceNode = resource.getNode("password");
		if(resourceNode != null){
			String password = StringUtil.trim(resourceNode.getValue());

			ldapResource.setPassword(password);
		}

		resourceNode = resource.getNode("baseDn");
		if(resourceNode != null){
			String baseDn = StringUtil.trim(resourceNode.getValue());

			ldapResource.setBaseDn(baseDn);
		}

		return (R)ldapResource;
	}

	/**
	 * @see br.com.concepting.framework.resource.BaseResourceLoader#parseResource()
	 */
    protected <C> C parseResource() throws InvalidResourceException{
		XmlNode content = super.parseResource();

		content = content.getNode("ldapResources");
		if(content == null) 
			throw new InvalidResourceException(getResourceId());

		return (C)content;
	}
}