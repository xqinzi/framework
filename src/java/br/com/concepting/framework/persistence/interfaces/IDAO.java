package br.com.concepting.framework.persistence.interfaces;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.persistence.resource.PersistenceResource;

/**
 * Interface que define a estrutura b�sica para as classes de persist�ncia.
 *
 * @author fvilarinho
 * @since 1.0
 */
public interface IDAO extends ICrud{
    /**
     * Inicializa persist�ncia.
     * 
     * @throws InternalErrorException
     */
    public void initialize() throws InternalErrorException;
    
    /**
     * Inicia a transa��o.
     * 
     * @throws InternalErrorException
     */
    public void begin() throws InternalErrorException;
    
    /**
     * Confirma as opera��es da transa��o.
     * 
     * @throws InternalErrorException
     */
    public void commit() throws InternalErrorException;
    
    /**
     * Descarta as opera��es da transa��o.
     * 
     * @throws InternalErrorException
     */
    public void rollback() throws InternalErrorException;
    
    /**
     * Retorna o tempo do timeout de execu��o das opera��es.
     * 
     * @return Valor inteiro contendo o timeout.
     */
    public Integer getTimeout();
    
    /**
     * Define o tempo do timeout de execu��o das opera��es.
     * 
     * @param timeout Valor inteiro contendo o timeout.
     */
    public void setTimeout(Integer timeout);
    
    /**
     * Define a inst�ncia contendo as configura��es de persist�ncia.
     * 
     * @param persistenceResource Inst�ncia contendo as configura��es de persist�ncia.
     */
    public void setPersistenceResource(PersistenceResource persistenceResource);
    
    /**
     * Retorna a inst�ncia contendo as configura��es de persist�ncia.
     * 
     * @return Inst�ncia contendo as configura��es de persist�ncia.
     */
    public PersistenceResource getPersistenceResource();
}