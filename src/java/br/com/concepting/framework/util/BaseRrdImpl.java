package br.com.concepting.framework.util;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import br.com.concepting.framework.util.constants.RrdConstants;
import br.com.concepting.framework.util.helpers.RrdArchive;
import br.com.concepting.framework.util.helpers.RrdDatasource;
import br.com.concepting.framework.util.interfaces.IRrd;
import br.com.concepting.framework.util.types.RrdArchiveType;

/**
 * Classe que define a estrutura básica para as implementações de rotinas que manipulam 
 * arquivos RRD.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class BaseRrdImpl implements IRrd{
	/**
	 * @see br.com.concepting.framework.util.interfaces.IRrd#create(java.lang.String, java.util.Collection)
	 */
	public void create(String fileName, Collection<RrdDatasource> datasources) throws IOException, IllegalArgumentException{
		create(fileName, new Date(), RrdConstants.DEFAULT_RRD_STEP, datasources);
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IRrd#create(java.lang.String, java.util.Collection, java.util.Collection)
	 */
	public void create(String fileName, Collection<RrdDatasource> datasources, Collection<RrdArchive> archives) throws IOException, IllegalArgumentException{
		create(fileName, new Date(), datasources, archives);
	}
	
	/**
	 * @see br.com.concepting.framework.util.interfaces.IRrd#create(java.lang.String, java.lang.Integer, java.util.Collection)
	 */
	public void create(String fileName, Integer step, Collection<RrdDatasource> datasources) throws IOException, IllegalArgumentException{
		create(fileName, step, datasources, getDefaultArchives(step));
	}
	
	/**
	 * @see br.com.concepting.framework.util.interfaces.IRrd#create(java.lang.String, java.lang.Integer, java.util.Collection, java.util.Collection)
	 */
	public void create(String fileName, Integer step, Collection<RrdDatasource> datasources, Collection<RrdArchive> archives) throws IOException, IllegalArgumentException{
		create(fileName, new Date(), step, datasources, archives);
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IRrd#create(java.lang.String, java.util.Date, java.util.Collection)
	 */
	public void create(String fileName, Date startTime, Collection<RrdDatasource> datasources) throws IOException, IllegalArgumentException{
		create(fileName, startTime, RrdConstants.DEFAULT_RRD_STEP, datasources);
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IRrd#create(java.lang.String, java.util.Date, java.util.Collection, java.util.Collection)
	 */
	public void create(String fileName, Date startTime, Collection<RrdDatasource> datasources, Collection<RrdArchive> archives) throws IOException, IllegalArgumentException{
		create(fileName, startTime, RrdConstants.DEFAULT_RRD_STEP, datasources, archives);
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IRrd#create(java.lang.String, java.util.Date, java.lang.Integer, java.util.Collection)
	 */
	public void create(String fileName, Date startTime, Integer step, Collection<RrdDatasource> datasources) throws IOException, IllegalArgumentException{
		create(fileName, startTime, step, datasources, getDefaultArchives(step));
	}

	/**
	 * Retorna a lista de tipos de armazenamentos default dos dados.
	 * 
	 * @param step Valor inteiro contendo o step desejado.
	 * @return Lista contendo os tipos de armazenamento.
	 */
	protected Collection<RrdArchive> getDefaultArchives(Integer step){
		Collection<RrdArchive> archives = new LinkedList<RrdArchive>();
		RrdArchive             archive  = null;
		
		archive  = new RrdArchive();
		archive.setType(RrdArchiveType.AVERAGE);
		archive.setRows(RrdUtil.getArchiveRows(1, 93, step));
		archive.setStep(1);
		archives.add(archive);
		
		archive  = new RrdArchive();
		archive.setType(RrdArchiveType.MAX);
		archive.setRows(RrdUtil.getArchiveRows(1, 93, step));
		archive.setStep(1);
		archives.add(archive);

		archive  = new RrdArchive();
		archive.setType(RrdArchiveType.MIN);
		archive.setRows(RrdUtil.getArchiveRows(1, 93, step));
		archive.setStep(1);
		archives.add(archive);

		archive  = new RrdArchive();
		archive.setType(RrdArchiveType.AVERAGE);
		archive.setRows(RrdUtil.getArchiveRows(6, 31, step));
		archive.setStep(6);
		archives.add(archive);
		
		archive  = new RrdArchive();
		archive.setType(RrdArchiveType.MAX);
		archive.setRows(RrdUtil.getArchiveRows(6, 31, step));
		archive.setStep(6);
		archives.add(archive);

		archive  = new RrdArchive();
		archive.setType(RrdArchiveType.MIN);
		archive.setRows(RrdUtil.getArchiveRows(6, 31, step));
		archive.setStep(6);
		archives.add(archive);

		archive  = new RrdArchive();
		archive.setType(RrdArchiveType.AVERAGE);
		archive.setRows(RrdUtil.getArchiveRows(72, 184, step));
		archive.setStep(72);
		archives.add(archive);
		
		archive  = new RrdArchive();
		archive.setType(RrdArchiveType.MAX);
		archive.setRows(RrdUtil.getArchiveRows(72, 184, step));
		archive.setStep(72);
		archives.add(archive);

		archive  = new RrdArchive();
		archive.setType(RrdArchiveType.MIN);
		archive.setRows(RrdUtil.getArchiveRows(72, 184, step));
		archive.setStep(72);
		archives.add(archive);

		archive  = new RrdArchive();
		archive.setType(RrdArchiveType.AVERAGE);
		archive.setRows(RrdUtil.getArchiveRows(288, 427, step));
		archive.setStep(288);
		archives.add(archive);
		
		archive  = new RrdArchive();
		archive.setType(RrdArchiveType.MAX);
		archive.setRows(RrdUtil.getArchiveRows(288, 427, step));
		archive.setStep(288);
		archives.add(archive);

		archive  = new RrdArchive();
		archive.setType(RrdArchiveType.MIN);
		archive.setRows(RrdUtil.getArchiveRows(288, 427, step));
		archive.setStep(288);
		archives.add(archive);

		return archives;
	}
}
