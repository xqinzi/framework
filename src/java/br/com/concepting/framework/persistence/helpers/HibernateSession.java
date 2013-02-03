package br.com.concepting.framework.persistence.helpers;

import org.hibernate.SessionFactory;
import org.hibernate.connection.ConnectionProvider;

/**
 * Classe auxiliar que armazena as informações de conexão com o repositório de dados.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class HibernateSession{
	private SessionFactory     factory  = null;
	private ConnectionProvider provider = null;
	
	/**
	 * Retorna a instância responsável pela criação de uma conexão com o repositório de dados.
	 * 
	 * @return Instância responsável pela conexão com o repositório de dados.
	 */
	public SessionFactory getFactory(){
		return factory;
	}
	
	/**
	 * Define a instância responsável pela criação de uma conexão com o repositório de dados.
	 * 
	 * @param factory Instância responsável pela conexão com o repositório de dados.
	 */
	public void setFactory(SessionFactory factory){
		this.factory = factory;
	}

	/**
	 * Retorna a instância contendo as informações do provedor da conexão com o repositório de dados.
	 * 
	 * @return Instância contendo as informações do provedor da conexão.
	 */
	public ConnectionProvider getProvider(){
    	return provider;
    }

	/**
	 * Define a instância contendo as informações do provedor da conexão com o repositório de dados.
	 * 
	 * @param provider Instância contendo as informações do provedor da conexão.
	 */
	public void setProvider(ConnectionProvider provider){
    	this.provider = provider;
    }
}
