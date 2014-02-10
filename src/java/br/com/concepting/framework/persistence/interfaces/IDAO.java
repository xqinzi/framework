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
     * Inicializa persistência.
     * 
     * @throws InternalErrorException
     */
    public void initialize() throws InternalErrorException;
    
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
     * Retorna o tempo do timeout de execução das operações.
     * 
     * @return Valor inteiro contendo o timeout.
     */
    public Integer getTimeout();
    
    /**
     * Define o tempo do timeout de execução das operações.
     * 
     * @param timeout Valor inteiro contendo o timeout.
     */
    public void setTimeout(Integer timeout);
    
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
}