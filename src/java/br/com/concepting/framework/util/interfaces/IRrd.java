package br.com.concepting.framework.util.interfaces;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import br.com.concepting.framework.util.helpers.RrdArchive;
import br.com.concepting.framework.util.helpers.RrdDatasource;
import br.com.concepting.framework.util.helpers.RrdGraphDefinition;
import br.com.concepting.framework.util.helpers.RrdRow;
import br.com.concepting.framework.util.types.RrdArchiveType;

/**
 * Interface que define a estrutura padrão para implementações de manipulação de arquivos 
 * RRD.
 *
 * @author fvilarinho
 * @since 2.0
 */
public interface IRrd{
	/**
	 * Cria um novo arquivo RRD.
	 * 
	 * @param fileName String contendo o nome do arquivo.
	 * @param datasources Lista contendo os tipos de dados.
	 * @param archives Lista contendo os tipos de armazenmento.
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	public void create(String fileName, Collection<RrdDatasource> datasources, Collection<RrdArchive> archives) throws IOException, IllegalArgumentException;
	
	/**
	 * Cria um novo arquivo RRD.
	 * 
	 * @param fileName String contendo o nome do arquivo.
	 * @param startTime Instância contendo a data de início a ser utilizada para gravação dos 
	 * dados.
	 * @param datasources Lista contendo os tipos de dados.
	 * @param archives Lista contendo os tipos de armazenmento.
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	public void create(String fileName, Date startTime, Collection<RrdDatasource> datasources, Collection<RrdArchive> archives) throws IOException, IllegalArgumentException;
	
	/**
	 * Cria um novo arquivo RRD.
	 * 
	 * @param fileName String contendo o nome do arquivo.
	 * @param datasources Lista contendo os tipos de dados.
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	public void create(String fileName, Collection<RrdDatasource> datasources) throws IOException, IllegalArgumentException;
	
	/**
	 * Cria um novo arquivo RRD.
	 * 
	 * @param fileName String contendo o nome do arquivo.
	 * @param startTime Instância contendo a data de início a ser utilizada para gravação dos 
	 * dados.
	 * @param datasources Lista contendo os tipos de dados.
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	public void create(String fileName, Date startTime, Collection<RrdDatasource> datasources) throws IOException, IllegalArgumentException;

	/**
	 * Cria um novo arquivo RRD.
	 * 
	 * @param fileName String contendo o nome do arquivo.
	 * @param step Valor inteiro contendo o step de armazenamento.
	 * @param datasources Lista contendo os tipos de dados.
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	public void create(String fileName, Integer step, Collection<RrdDatasource> datasources) throws IOException, IllegalArgumentException;
	
	/**
	 * Cria um novo arquivo RRD.
	 * 
	 * @param fileName String contendo o nome do arquivo.
	 * @param startTime Instância contendo a data de início a ser utilizada para gravação dos 
	 * dados.
	 * @param step Valor inteiro contendo o step de armazenamento.
	 * @param datasources Lista contendo os tipos de dados.
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	public void create(String fileName, Date startTime, Integer step, Collection<RrdDatasource> datasources) throws IOException, IllegalArgumentException;
	
	/**
	 * Cria um novo arquivo RRD.
	 * 
	 * @param fileName String contendo o nome do arquivo.
	 * @param step Valor inteiro contendo o step de armazenamento.
	 * @param datasources Lista contendo os tipos de dados.
	 * @param archives Lista contendo os tipos de armazenmento.
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	public void create(String fileName, Integer step, Collection<RrdDatasource> datasources, Collection<RrdArchive> archives) throws IOException, IllegalArgumentException;
	
	/**
	 * Cria um novo arquivo RRD.
	 * 
	 * @param fileName String contendo o nome do arquivo.
	 * @param startTime Instância contendo a data de início a ser utilizada para gravação dos 
	 * dados.
	 * @param step Valor inteiro contendo o step de armazenamento.
	 * @param datasources Lista contendo os tipos de dados.
	 * @param archives Lista contendo os tipos de armazenmento.
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	public void create(String fileName, Date startTime, Integer step, Collection<RrdDatasource> datasources, Collection<RrdArchive> archives) throws IOException, IllegalArgumentException;

	/**
	 * Atualiza os dados de uma linha.
	 *
	 * @param fileName String contendo o nome do arquivo RRD.
	 * @param row Instância contendo as propriedades da linha.
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	public void update(String fileName, RrdRow row) throws IOException, IllegalArgumentException;
	
	/**
	 * Retorna as linhas armazenadas no arquivo RRD.
	 *
	 * @param fileName String contendo o nome do arquivo RRD.
	 * @param startTime Valor inteiro contendo o timestamp inicial, para filtragem das linhas.
	 * @param endTime Valor inteiro contendo o timestamp final, para filtragem das linhas.                 
	 * @param type Instância que define o tipo de dado a ser retornado.
	 * @return Lista contendo as linhas desejadas.
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	public Collection<RrdRow> fetch(String fileName, Date startTime, Date endTime, RrdArchiveType type) throws IOException, IllegalArgumentException;
	
	/**
	 * Retorna a lista de tipos de dados de um arquivo RRD.
	 *
	 * @param fileName String contendo o nome do arquivo RRD.
	 * @return Lista contendo as propriedades dos tipos de dados.
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	public Collection<RrdDatasource> getDatasources(String fileName) throws IOException, IllegalArgumentException;
	
	/**
	 * Retorna a lista de tipos de consolidações de um arquivo RRD.
	 *
	 * @param fileName String contendo o nome do arquivo RRD.
	 * @return Lista contendo as propriedades dos tipos de consolidações.
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	public Collection<RrdArchive> getArchives(String fileName) throws IOException, IllegalArgumentException;
	
	/**
	 * Exporta os dados armazenados para um arquivo XML.
	 *
	 * @param fileName String contendo o nome do arquivo RRD.
	 * @param exportedFileName String contendo o nome do arquivo XML.
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	public void dump(String fileName, String exportedFileName) throws IOException, IllegalArgumentException;

	/**
	 * Restaura os dados armazenados em um arquivo XML.
	 *
	 * @param exportedFileName String contendo o nome do arquivo XML.
	 * @param fileName String contendo o nome do arquivo RRD.
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	public void restore(String exportedFileName, String fileName) throws IOException, IllegalArgumentException;
	
	/**
	 * Gera um gráfico com os dados do arquivo RRD.
	 *
	 * @param startTime Valor inteiro contendo o timestamp inicial, em segundos, para 
	 * filtragem das linhas.
	 * @param endTime Valor inteiro contendo o timestamp final, em segundos, para filtragem 
	 * das linhas.
	 * @param definition Instância contendo as propriedades do gráfico.
	 * @return Array de bytes contendo os dados do gráfico.
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	public byte[] generateGraph(Date startTime, Date endTime, RrdGraphDefinition definition) throws IOException, IllegalArgumentException;
	
	/**
	 * Gera um gráfico com os dados do arquivo RRD.
	 *
	 * @param fileName String contendo o nome do arquivo de imagem a ser gerada.
	 * @param startTime Valor inteiro contendo o timestamp inicial, em segundos, para 
	 * filtragem das linhas.
	 * @param endTime Valor inteiro contendo o timestamp final, em segundos, para filtragem 
	 * das linhas.
	 * @param definition Instância contendo as propriedades do gráfico.
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	public void generateGraph(String fileName, Date startTime, Date endTime, RrdGraphDefinition definition) throws IOException, IllegalArgumentException;
}
