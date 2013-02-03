package br.com.concepting.framework.persistence.helpers;

import org.hibernate.SessionFactory;
import org.hibernate.connection.ConnectionProvider;

/**
 * Classe auxiliar que armazena as informa��es de conex�o com o reposit�rio de dados.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class HibernateSession{
	private SessionFactory     factory  = null;
	private ConnectionProvider provider = null;
	
	/**
	 * Retorna a inst�ncia respons�vel pela cria��o de uma conex�o com o reposit�rio de dados.
	 * 
	 * @return Inst�ncia respons�vel pela conex�o com o reposit�rio de dados.
	 */
	public SessionFactory getFactory(){
		return factory;
	}
	
	/**
	 * Define a inst�ncia respons�vel pela cria��o de uma conex�o com o reposit�rio de dados.
	 * 
	 * @param factory Inst�ncia respons�vel pela conex�o com o reposit�rio de dados.
	 */
	public void setFactory(SessionFactory factory){
		this.factory = factory;
	}

	/**
	 * Retorna a inst�ncia contendo as informa��es do provedor da conex�o com o reposit�rio de dados.
	 * 
	 * @return Inst�ncia contendo as informa��es do provedor da conex�o.
	 */
	public ConnectionProvider getProvider(){
    	return provider;
    }

	/**
	 * Define a inst�ncia contendo as informa��es do provedor da conex�o com o reposit�rio de dados.
	 * 
	 * @param provider Inst�ncia contendo as informa��es do provedor da conex�o.
	 */
	public void setProvider(ConnectionProvider provider){
    	this.provider = provider;
    }
}
