package br.com.concepting.framework.util.helpers;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

/**
 * Classe utilit�ria respons�vel por armazenada um registro de um arquivo RRD.
 *
 * @author fvilarinho
 * @since 1.0
 */
public class RrdRow{
	private Date                  date    = null;
	private Collection<RrdColumn> columns = null;

	/**
	 * Retorna a data/hor�rio da linha.
	 *
	 * @return Inst�ncia contendo a data/hor�rio da linha.
	 */
	public Date getDate(){
    	return date;
    }
	
	/**
	 * Define a data/hor�rio da linha.
	 *
	 * @param date Inst�ncia contendo a data/hor�rio da linha.
	 */
	public void setDate(Date date){
    	this.date = date;
    }
	
	/**
	 * Retorna a lista de colunas da linha.
	 *
	 * @return Lista contendo as propriedades das colunas.
	 */
	public Collection<RrdColumn> getColumns(){
    	return columns;
    }

	/**
	 * Define todas as colunas da linha.
	 *
	 * @param columns Lista contendo as propriedades das colunas.
	 */
	public void setColumns(Collection<RrdColumn> columns){
    	this.columns = columns;
    }
	
	/**
	 * Adiciona uma coluna.
	 *
	 * @param column Inst�ncia contendo as propriedades da coluna.
	 */
	public void addColumn(RrdColumn column){
		if(columns == null)
			columns = new LinkedList<RrdColumn>();
		
		columns.add(column);
	}
	
	/**
	 * Adiciona uma coluna.
	 *
	 * @param name String contendo o identificador da coluna.
	 * @param value Valor em ponto flutuante contendo o valor da coluna. 
	 */
	public void addColumn(String name, Double value){
		addColumn(new RrdColumn(name, value));
	}
}
