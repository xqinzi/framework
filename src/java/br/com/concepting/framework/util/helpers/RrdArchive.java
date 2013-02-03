package br.com.concepting.framework.util.helpers;

import br.com.concepting.framework.util.constants.RrdConstants;
import br.com.concepting.framework.util.types.RrdArchiveType;

/**
 * Classe que define o tipo de consolidação de um dado a ser armazenado no arquivo RRD.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class RrdArchive{
	private RrdArchiveType type           = RrdConstants.DEFAULT_RRD_ARCHIVE_TYPE;
	private Double         failPercentage = RrdConstants.DEFAULT_RRD_FAIL_PERCENTAGE;
	private Integer        rows           = 0;
	private Integer        step           = 0;
	
	/**
	 * Retorna o step de armazenamento dos dados.
	 * 
	 * @return Valor inteiro contendo o step de armazenamento.
	 */
	public Integer getStep(){
    	return step;
    }

	/**
	 * Define o step de armazenamento dos dados.
	 * 
	 * @param step Valor inteiro contendo o step de armazenamento.
	 */
	public void setStep(Integer step){
    	this.step = step;
    }

	/**
	 * Retorna o tipo de armazenamento dos dados.
	 * 
	 * @return Constante contendo o tipo de armazenamento.
	 */
	public RrdArchiveType getType(){
		return type;
	}

	/**
	 * Define o tipo de armazenamento dos dados.
	 * 
	 * @param type Constante contendo o tipo de armazenamento.
	 */
	public void setType(RrdArchiveType type){
		this.type = type;
	}

	/**
	 * Retorna o percentual de erros aceitável na gravação dos dados.
	 * 
	 * @return Valor numérico contendo o percentual de erros.
	 */
	public Double getFailPercentage(){
		return failPercentage;
	}

	/**
	 * Define o percentual de erros aceitável na gravação dos dados.
	 * 
	 * @param failPercentage Valor numérico contendo o percentual de erros.
	 */
	public void setFailPercentage(Double failPercentage){
		this.failPercentage = failPercentage;
	}

	/**
	 * Retorna a quantidade de linhas que serão disponibilizadas para o armazenamento dos 
	 * dados.
	 * 
	 * @return Valor inteiro contendo a quantidade de linhas.
	 */
	public Integer getRows(){
    	return rows;
    }

	/**
	 * Define a quantidade de linhas que serão disponibilizadas para o armazenamento dos 
	 * dados.
	 * 
	 * @param rows Valor inteiro contendo a quantidade de linhas.
	 */
	public void setRows(Integer rows){
    	this.rows = rows;
    }
}