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
     * Inicia a transação.
     * 
     * @throws InternalErrorException
     */
    public void begin() throws InternalErrorException;
    
    /**
     * Confirma as operações da transação.
     * 
     * @throws InternalErrorException
     */
    public void commit() throws InternalErrorException;
    
    /**
     * Descarta as operações da transação.
     * 
     * @throws InternalErrorException
     */
    public void rollback() throws InternalErrorException;
    
    /**
     * Define a instância contendo as configurações de persistência.
     * 
     * @param persistenceResource Instância contendo as configurações de persistência.
     */
    public void setPersistenceResource(PersistenceResource persistenceResource);
    
    /**
     * Retorna a instância contendo as configurações de persistência.
     * 
     * @return Instância contendo as configurações de persistência.
     */
    public PersistenceResource getPersistenceResource();
    
    /**
     * Abre uma conexão com o repositório de persistência.
     * 
     * @return Instância da conexão com o repositório de persistência.
     * @throws InternalErrorException
     */
    public <C> C openConnection() throws InternalErrorException;
    
    /**
     * Fecha a conexão com o repositório de persistência.
     */
    public void closeConnection();
    
	/**
	 * Retorna a conexão com o repositório de persistência.
	 *
	 * @return Instância contendo a conexão com o repositório.
	 */
	public <C> C getConnection();
	
    /**
     * Define a conexão com o repositório de persistência.
     *
     * @param connection Instância contendo a conexão com o repositório.
     */
	public <C> void setConnection(C connection);
	
    /**
     * Retorna a transação com o repositório de persistência.
     *
     * @return Instância contendo a transação com o repositório.
     */
	public <T> T getTransaction();
	
    /**
     * Define a transação com o repositório de persistência.
     *
     * @param transaction Instância contendo a transação com o repositório.
     */
	public <T> void setTransaction(T transaction);
}