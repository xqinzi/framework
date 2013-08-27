package br.com.concepting.framework.persistence.interfaces;

import java.util.Collection;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.exceptions.ItemAlreadyExistsException;
import br.com.concepting.framework.model.exceptions.ItemNotFoundException;
import br.com.concepting.framework.model.helpers.ModelFilter;

/**
 * Interface que define a estrutura básica para uma classe de persistência.
 *
 * @author fvilarinho
 * @since 3.0
 */
public interface ICrud{
	/**
	 * Retorna todos os modelos de dados vinculados à classe de serviço.
	 *
	 * @return Lista contendo os modelos de dados.
	 * @throws InternalErrorException
	 */
	public <C extends Collection> C list() throws InternalErrorException;

	/**
	 * Retorna uma lista contendo os modelos de dados a partir dos valores das propriedades de 
	 * pesquisa de um modelo de dados.
	 *
	 * @param model Instância do modelo de dados contendo os valores para pesquisa.
	 * @return Lista contendo os modelos de dados encontrados.
	 * @throws InternalErrorException
	 */
	public <M extends BaseModel, C extends Collection> C search(M model) throws InternalErrorException;

	/**
	 * Retorna uma lista contendo os modelos de dados a partir dos valores das propriedades de 
	 * pesquisa de um modelo de dados.
	 *
	 * @param model Instância do modelo de dados contendo os valores para pesquisa.
	 * @param modelFilter Instância contendo as propriedades de filtro para a pesquisa.
	 * @return Lista contendo os modelos de dados encontrados.
	 * @throws InternalErrorException
	 */
	public <M extends BaseModel, C extends Collection> C search(M model, ModelFilter modelFilter) throws InternalErrorException;

	/**
	 * Carrega um único modelo de dados.
	 *
	 * @param model Instância do modelo de dados desejado.
	 * @return Instância do modelo de dados carregado.
	 * @throws ItemNotFoundException
	 * @throws InternalErrorException
	 */
	public <M extends BaseModel> M find(M model) throws ItemNotFoundException, InternalErrorException;

	/**
	 * Exclui um modelo de dados.
	 *
	 * @param model Instância do modelo de dados desejado.
	 * @throws InternalErrorException
	 */
	public <M extends BaseModel> void delete(M model) throws InternalErrorException;

	/**
	 * Exclui uma lista de modelos de dados.
	 *
	 * @param modelList Instância contendo a lista de modelos de dados.
	 * @throws InternalErrorException
	 */
	public <M extends BaseModel> void deleteAll(Collection<M> modelList) throws InternalErrorException;

	/**
	 * Exclui todos os modelos de dados.
	 *
	 * @throws InternalErrorException
	 */
	public <M extends BaseModel> void deleteAll() throws InternalErrorException;

	/**
	 * Inclui/Atualiza um modelo de dados.
	 *
	 * @param model Instância do modelo de dados desejado.
	 * @throws ItemAlreadyExistsException
	 * @throws InternalErrorException
	 */
	public <M extends BaseModel> void save(M model) throws ItemAlreadyExistsException, InternalErrorException;
	
	/**
	 * Inclui/Atualiza uma lista de modelos de dados.
	 *
	 * @param modelList Instância contendo a lista de modelos de dados.
	 * @throws ItemAlreadyExistsException
	 * @throws InternalErrorException
	 */
	public <M extends BaseModel> void saveAll(Collection<M> modelList) throws ItemAlreadyExistsException, InternalErrorException;

	/**
	 * Inclui uma lista de novos modelos de dados.
	 *
	 * @param modelList Instância contendo a lista de novos modelos de dados.
	 * @throws ItemAlreadyExistsException
	 * @throws InternalErrorException
	 */
	public <M extends BaseModel> void insertAll(Collection<M> modelList) throws ItemAlreadyExistsException, InternalErrorException;

	/**
	 * Atualiza uma lista de modelos de dados.
	 *
	 * @param modelList Instância contendo a lista de modelos de dados.
	 * @throws InternalErrorException
	 */
	public <M extends BaseModel> void updateAll(Collection<M> modelList) throws InternalErrorException;
	
	/**
	 * Inclui um novo modelo de dados.
	 *
	 * @param model Instância do novo modelo de dados desejado.
	 * @throws ItemAlreadyExistsException
	 * @throws InternalErrorException
	 */
	public <M extends BaseModel> void insert(M model) throws ItemAlreadyExistsException, InternalErrorException;
	
	/**
	 * Atualiza um modelo de dados.
	 *
	 * @param model Instância do modelo de dados desejado.
	 * @throws ItemAlreadyExistsException
	 * @throws InternalErrorException
	 */
	public <M extends BaseModel> void update(M model) throws InternalErrorException;

	/**
	 * Carrega os dados de um relacionamento.
	 *
	 * @param model Instância do modelo de dados desejado.
	 * @param referencePropertyId String contendo o nome do relacionamento.
	 * @return Instância do modelo de dados com o relacionamento carregado.
	 * @throws InternalErrorException
	 */
	public <M extends BaseModel> M loadReference(M model, String referencePropertyId) throws InternalErrorException;

	/**
	 * Salva os dados de um relacionamento.
	 *
	 * @param model Instância do modelo de dados desejado.
	 * @param referencePropertyId String contendo o nome do relacionamento.
	 * @throws InternalErrorException
	 */
	public <M extends BaseModel> void saveReference(M model, String referencePropertyId) throws InternalErrorException;
}