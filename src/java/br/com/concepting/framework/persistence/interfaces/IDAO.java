package br.com.concepting.framework.persistence.interfaces;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.persistence.resource.PersistenceResource;

/**
 * Interface que define a estrutura básica para as classes de persistência.
 *
 * @author fvilarinho
 * @since 1.0
 */
public interface IDAO extends ICrud{
    /**
     * Retorna a instância contendo as configurações de conexão com o repositórios de dados.
     * 
     * @return Instância contendo as configurações de conexão.
     */
	public PersistenceResource getPersistenceResource();
	
    /**
     * Define a instância contendo as configurações de conexão com o repositórios de dados.
     * 
     * param persistenceResource Instância contendo as configurações de conexão.
     */
	public void setPersistenceResource(PersistenceResource persistenceResource);
	
	/**
	 * Abre uma conexão com o repositório de persistência a partir de uma instância de propriedades de 
	 * conexão com o repositório. 
	 *
	 * @param persistenceResourceId String contendo o identificador das configurações de conexão com 
	 * o repositório.
	 * @return Instância contendo a conexão com o repositório.
	 * @throws InternalErrorException
	 */
	public <C> C openConnection(String persistenceResourceId) throws InternalErrorException;

	/**
	 * Abre uma conexão com o repositório de persistência a partir de uma instância de propriedades de 
	 * conexão com o repositório. 
	 *
	 * @param persistenceResource Instância contendo as propriedades de conexão com o repositório.
	 * @return Instância contendo a conexão com o repositório.
	 * @throws InternalErrorException
	 */
	public <C> C openConnection(PersistenceResource persistenceResource) throws InternalErrorException;

	/**
	 * Abre uma conexão com o repositório de persistência.
	 *
	 * @return Instância contendo a conexão com o repositório.
	 * @throws InternalErrorException
	 */
	public <C> C openConnection() throws InternalErrorException;
    
    /**
     * Fecha uma conexão com o repositório de persistência.
     */
    public void closeConnection();
	
	/**
	 * Inicia uma transação de persistência.
	 * 
	 * @throws InternalErrorException
	 */
	public void begin() throws InternalErrorException;
	
	/**
	 * Confirma as operações de persistências.
	 */
	public void commit() throws InternalErrorException;

	/**
	 * Descarta as operações de persistências.
	 */
	public void rollback() throws InternalErrorException;
}