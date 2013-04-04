package br.com.concepting.framework.service.interfaces;

import java.rmi.RemoteException;
import java.util.Collection;

import br.com.concepting.framework.audit.annotations.Auditable;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.exceptions.ItemAlreadyExistsException;
import br.com.concepting.framework.model.exceptions.ItemNotFoundException;
import br.com.concepting.framework.persistence.helpers.ModelFilter;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.service.annotations.ServiceTransaction;
 
/** 
 * Interface que define a estrutura b�sica para um servi�o.
 *
 * @author fvilarinho
 * @since 1.0
 */
public interface IService{
    /**
     * Inicia a transa��o.
     * 
     * @throws RemoteException
     * @throws InternalErrorException
     */
    public void begin() throws RemoteException, InternalErrorException;
    
	/**
	 * Confirma as opera��es da transa��o.
	 * 
	 * @throws RemoteException
	 * @throws InternalErrorException
	 */
	public void commit() throws RemoteException, InternalErrorException;
	
	/**
	 * Descarta as opera��es da transa��o.
	 * 
     * @throws RemoteException
     * @throws InternalErrorException
	 */
	public void rollback() throws RemoteException, InternalErrorException;
	
	/**
	 * Retorna a inst�ncia da sess�o de login do usu�rio.
	 * 
	 * @return Inst�ncia contendo as propriedades da sess�o de login.
	 * @throws RemoteException
	 */
	public <L extends LoginSessionModel> L getLoginSession() throws RemoteException;
	
    /**
     * Define a inst�ncia da sess�o de login do usu�rio.
     * 
     * @param loginSession Inst�ncia contendo as propriedades da sess�o de login.
     * @throws RemoteException
     */
	public <L extends LoginSessionModel> void setLoginSession(L loginSession) throws RemoteException;
	
    /**
     * Retorna o tempo do timeout do bloqueio da transa��o.
     * 
     * @return Valor inteiro contendo o timeout do bloqueio.
     * @throws RemoteException
     */
    public Integer getTransactionTimeout() throws RemoteException;
    
    /**
     * Define o tempo do timeout do bloqueio da transa��o.
     * 
     * @param transactionTimeout Valor inteiro contendo o timeout do bloqueio.
     * @throws RemoteException
     */
    public void setTransactionTimeout(Integer transactionTimeout) throws RemoteException;

    /**
	 * Retorna todos os modelos de dados vinculados a classe de servi�o.
	 *
	 * @return Lista contendo os modelos de dados.
     * @throws RemoteException
	 * @throws InternalErrorException
	 */
    @ServiceTransaction
	public <C extends Collection> C list() throws InternalErrorException, RemoteException;

	/**
	 * Retorna uma lista contendo os modelos de dados a partir dos valores das propriedades de 
	 * pesquisa de um modelo de dados.
	 *
	 * @param model Inst�ncia do modelo de dados contendo os valores para pesquisa.
	 * @return Lista contendo os modelos de dados encontrados.
     * @throws RemoteException
	 * @throws InternalErrorException
	 */
    @ServiceTransaction
	public <M extends BaseModel, C extends Collection> C search(M model) throws InternalErrorException, RemoteException;

	/**
	 * Retorna uma lista contendo os modelos de dados a partir dos valores das propriedades de pesquisa 
	 * de um modelo de dados.
	 *
	 * @param model Inst�ncia do modelo de dados contendo os valores para pesquisa.
	 * @param modelFilter Inst�ncia contendo as propriedades de filtro para a pesquisa.
	 * @return Lista contendo os modelos de dados encontrados.
     * @throws RemoteException
	 * @throws InternalErrorException
	 */
    @ServiceTransaction
	public <M extends BaseModel, C extends Collection> C search(M model, ModelFilter modelFilter) throws InternalErrorException, RemoteException;

	/**
	 * Carrega um �nico modelo de dados.
	 *
	 * @param model Inst�ncia do modelo de dados desejado.
	 * @return Inst�ncia do modelo de dados carregado.
     * @throws RemoteException
	 * @throws ItemNotFoundException
	 * @throws InternalErrorException
	 */
    @ServiceTransaction
	public <M extends BaseModel> M find(M model) throws ItemNotFoundException, InternalErrorException, RemoteException;

	/**
	 * Exclui um modelo de dados.
	 *
	 * @param model Inst�ncia do modelo de dados desejado.
     * @throws RemoteException
	 * @throws InternalErrorException
	 */
	@Auditable
    @ServiceTransaction
	public <M extends BaseModel> void delete(M model) throws InternalErrorException, RemoteException;

	/**
	 * Exclui uma lista de modelos de dados.
	 *
	 * @param modelList Inst�ncia contendo a lista de modelos de dados.
     * @throws RemoteException
	 * @throws InternalErrorException
	 */
	@Auditable
    @ServiceTransaction
	public <M extends BaseModel> void deleteAll(Collection<M> modelList) throws InternalErrorException, RemoteException;

	/**
	 * Exclui todos os modelos de dados.
	 *
     * @throws RemoteException
	 * @throws InternalErrorException
	 */
	@Auditable
    @ServiceTransaction
	public <M extends BaseModel> void deleteAll() throws InternalErrorException, RemoteException;

	/**
	 * Inclui/Atualiza um modelo de dados.
	 *
	 * @param model Inst�ncia do modelo de dados desejado.
     * @throws RemoteException
	 * @throws ItemAlreadyExistsException
	 * @throws InternalErrorException
	 */
	@Auditable
    @ServiceTransaction
	public <M extends BaseModel> void save(M model) throws ItemAlreadyExistsException, InternalErrorException, RemoteException;

	/**
	 * Inclui/Atualiza uma lista de modelos de dados.
	 *
	 * @param modelList Inst�ncia contendo a lista de modelos de dados.
     * @throws RemoteException
	 * @throws ItemAlreadyExistsException
	 * @throws InternalErrorException
	 */
	@Auditable
    @ServiceTransaction
	public <M extends BaseModel> void saveAll(Collection<M> modelList) throws ItemAlreadyExistsException, InternalErrorException, RemoteException;

	/**
	 * Inclui um novo modelo de dados.
	 *
	 * @param model Inst�ncia do novo modelo de dados desejado.
     * @throws RemoteException
	 * @throws ItemAlreadyExistsException
	 * @throws InternalErrorException
	 */
	@Auditable
    @ServiceTransaction
	public <M extends BaseModel> void insert(M model) throws ItemAlreadyExistsException, InternalErrorException, RemoteException;

	/**
	 * Inclui uma lista de novos modelos de dados.
	 *
	 * @param modelList Inst�ncia contendo a lista de modelos de dados.
     * @throws RemoteException
	 * @throws ItemAlreadyExistsException
	 * @throws InternalErrorException
	 */
	@Auditable
    @ServiceTransaction
	public <M extends BaseModel> void insertAll(Collection<M> modelList) throws ItemAlreadyExistsException, InternalErrorException, RemoteException;

	/**
	 * Atualiza um modelo de dados.
	 *
	 * @param model Inst�ncia do modelo de dados desejado.
     * @throws RemoteException
	 * @throws ItemAlreadyExistsException
	 * @throws InternalErrorException
	 */
	@Auditable
    @ServiceTransaction
	public <M extends BaseModel> void update(M model) throws ItemAlreadyExistsException, InternalErrorException, RemoteException;

	/**
	 * Atualiza uma lista de modelos de dados.
	 *
	 * @param modelList Inst�ncia contendo a lista de modelos de dados.
     * @throws RemoteException
	 * @throws ItemAlreadyExistsException
	 * @throws InternalErrorException
	 */
	@Auditable
    @ServiceTransaction
	public <M extends BaseModel> void updateAll(Collection<M> modelList) throws ItemAlreadyExistsException, InternalErrorException, RemoteException;

	/**
	 * Carrega os dados de um relacionamento.
	 *
	 * @param model Inst�ncia do modelo de dados desejado.
	 * @param referencePropertyId String contendo o nome do relacionamento.
	 * @return Inst�ncia do modelo de dados com o relacionamento carregado.
     * @throws RemoteException
	 * @throws InternalErrorException
	 */
    @ServiceTransaction
	public <M extends BaseModel> M loadReference(M model, String referencePropertyId) throws InternalErrorException, RemoteException;

	/**
	 * Salva os dados de um relacionamento.
	 *
	 * @param model Inst�ncia do modelo de dados desejado.
	 * @param referencePropertyId String contendo o nome do relacionamento.
     * @throws RemoteException
	 * @throws InternalErrorException
	 */
	@Auditable
    @ServiceTransaction
	public <M extends BaseModel> void saveReference(M model, String referencePropertyId) throws InternalErrorException, RemoteException;
}