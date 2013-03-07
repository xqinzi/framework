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
     * Retorna a inst�ncia contendo as configura��es de conex�o com o reposit�rios de dados.
     * 
     * @return Inst�ncia contendo as configura��es de conex�o.
     */
	public PersistenceResource getPersistenceResource();
	
    /**
     * Define a inst�ncia contendo as configura��es de conex�o com o reposit�rios de dados.
     * 
     * param persistenceResource Inst�ncia contendo as configura��es de conex�o.
     */
	public void setPersistenceResource(PersistenceResource persistenceResource);
	
	/**
	 * Abre uma conex�o com o reposit�rio de persist�ncia a partir de uma inst�ncia de propriedades de 
	 * conex�o com o reposit�rio. 
	 *
	 * @param persistenceResourceId String contendo o identificador das configura��es de conex�o com 
	 * o reposit�rio.
	 * @return Inst�ncia contendo a conex�o com o reposit�rio.
	 * @throws InternalErrorException
	 */
	public <C> C openConnection(String persistenceResourceId) throws InternalErrorException;

	/**
	 * Abre uma conex�o com o reposit�rio de persist�ncia a partir de uma inst�ncia de propriedades de 
	 * conex�o com o reposit�rio. 
	 *
	 * @param persistenceResource Inst�ncia contendo as propriedades de conex�o com o reposit�rio.
	 * @return Inst�ncia contendo a conex�o com o reposit�rio.
	 * @throws InternalErrorException
	 */
	public <C> C openConnection(PersistenceResource persistenceResource) throws InternalErrorException;

	/**
	 * Abre uma conex�o com o reposit�rio de persist�ncia.
	 *
	 * @return Inst�ncia contendo a conex�o com o reposit�rio.
	 * @throws InternalErrorException
	 */
	public <C> C openConnection() throws InternalErrorException;
    
    /**
     * Fecha uma conex�o com o reposit�rio de persist�ncia.
     */
    public void closeConnection();
	
	/**
	 * Inicia uma transa��o de persist�ncia.
	 * 
	 * @throws InternalErrorException
	 */
	public void begin() throws InternalErrorException;
	
	/**
	 * Confirma as opera��es de persist�ncias.
	 */
	public void commit() throws InternalErrorException;

	/**
	 * Descarta as opera��es de persist�ncias.
	 */
	public void rollback() throws InternalErrorException;
}