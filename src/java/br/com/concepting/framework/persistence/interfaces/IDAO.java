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
    
    /**
     * Abre uma conex�o com o reposit�rio de persist�ncia.
     * 
     * @return Inst�ncia da conex�o com o reposit�rio de persist�ncia.
     * @throws InternalErrorException
     */
    public <C> C openConnection() throws InternalErrorException;
    
    /**
     * Fecha a conex�o com o reposit�rio de persist�ncia.
     */
    public void closeConnection();
    
	/**
	 * Retorna a conex�o com o reposit�rio de persist�ncia.
	 *
	 * @return Inst�ncia contendo a conex�o com o reposit�rio.
	 */
	public <C> C getConnection();
	
    /**
     * Define a conex�o com o reposit�rio de persist�ncia.
     *
     * @param connection Inst�ncia contendo a conex�o com o reposit�rio.
     */
	public <C> void setConnection(C connection);
	
    /**
     * Retorna a transa��o com o reposit�rio de persist�ncia.
     *
     * @return Inst�ncia contendo a transa��o com o reposit�rio.
     */
	public <T> T getTransaction();
	
    /**
     * Define a transa��o com o reposit�rio de persist�ncia.
     *
     * @param transaction Inst�ncia contendo a transa��o com o reposit�rio.
     */
	public <T> void setTransaction(T transaction);
}