package br.com.concepting.framework.network.ldap;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import br.com.concepting.framework.network.ldap.resource.LdapResource;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.XmlNode;

/** 
 * Classe respons�vel pela comunica��o com o servi�o de diret�rios LDAP.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class Ldap{
	private LdapResource ldapResource = null;
	private DirContext   context      = null;
	
    /**
     * Construtor - Inicializa a conex�o e autentica��o com o servi�o de diret�rios LDAP.
     * 
     * @param ldapResource Inst�ncia contendo as configura��es desejadas.
     * @throws NamingException
     */
	public Ldap(LdapResource ldapResource) throws NamingException{
		super();

		this.ldapResource = ldapResource;

		initialize();
	}

	/**
	 * Efetua a conex�o e a autentica��o com o servi�o de diret�rios LDAP.
	 * 
	 * @throws NamingException
	 */
	private void initialize() throws NamingException{
		Properties properties = new Properties();
		
		properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		properties.put(Context.REFERRAL, "throw");
		
		StringBuilder urlBuffer = new StringBuilder();
		
		urlBuffer.append("ldap://");
		urlBuffer.append(ldapResource.getServerName());
		urlBuffer.append(":");
		urlBuffer.append(ldapResource.getServerPort());
		urlBuffer.append("/");
		urlBuffer.append(ldapResource.getBaseDn());

		properties.put(Context.PROVIDER_URL, urlBuffer.toString());
		
		if(ldapResource.getTimeout() > 0)
			properties.put("com.sun.jndi.ldap.read.timeout", StringUtil.trim(ldapResource.getTimeout()));
		
		if(ldapResource.getUser().length() > 0){
			StringBuilder securityBuffer = new StringBuilder();
			
			securityBuffer.append("uid=");
			securityBuffer.append(ldapResource.getUser());
			securityBuffer.append(",");
			securityBuffer.append(ldapResource.getUserDn());
			securityBuffer.append(",");
			securityBuffer.append(ldapResource.getBaseDn());
			
			properties.put(Context.SECURITY_AUTHENTICATION, ldapResource.getAuthenticationType());
			properties.put(Context.SECURITY_PRINCIPAL, securityBuffer.toString());
			properties.put(Context.SECURITY_CREDENTIALS, ldapResource.getPassword());
	    }
		
		context = new InitialDirContext(properties);
	}
	
	/**
	 * Retorna a conex�o com o servi�o de diret�rios LDAP.
	 * 
	 * @return Inst�ncia de conex�o com o servi�o de diret�rios LDAP.
	 */
	public DirContext getContext(){
		return context;
	}
	
	/**
	 * Lista os n�s do servi�o de diret�rios LDAP a partir de uma string de pesquisa.
	 * 
	 * @param dn String de pesquisa desejada.
	 * @return Inst�ncia contendo a lista de n�s encontrados.
	 * @throws NamingException
	 */
    @SuppressWarnings("unchecked")
    public XmlNode list(String dn) throws NamingException{
		NamingEnumeration<NameClassPair> list                 = getContext().list(dn);
		NameClassPair                    item                 = null;
		Attributes                       attributes           = null;
		NamingEnumeration<Attribute>     attributesList       = null;
		Attribute                        attribute            = null;
		XmlNode                          root                 = null;
		XmlNode                          child                = null;
		XmlNode                          attributeChild       = null;
		Object                           attributeValue       = null;
		StringBuilder                    attributeValueBuffer = new StringBuilder();
		
		root = new XmlNode("items");
		
		while(list.hasMore()){
			item  = list.next();
			child = new XmlNode("item");
			
			child.addAttribute("name", item.getName());
			
			attributes = getContext().getAttributes(item.getName().concat(",").concat(dn));
			
			if(attributes != null){
				attributesList = (NamingEnumeration<Attribute>)attributes.getAll();
				
				while(attributesList.hasMore()){
					attribute = attributesList.next();
					
					attributeValueBuffer.delete(0, attributeValueBuffer.length());
					
					for(NamingEnumeration<Attribute> e = (NamingEnumeration<Attribute>)attribute.getAll() ; e.hasMore() ; attributeValue = e.next()){
						if(attributeValueBuffer.length() > 0)
							attributeValueBuffer.append(";");
						
						attributeValueBuffer.append(StringUtil.trim(attributeValue));
					}
					
					attributeChild = new XmlNode(attribute.getID(), attributeValueBuffer.toString());
					
					child.addChildNode(attributeChild);
				}
			}
			
			root.addChildNode(child);
		}

		return root;
	}
}
