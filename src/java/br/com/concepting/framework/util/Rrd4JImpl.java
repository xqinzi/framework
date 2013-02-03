package br.com.concepting.framework.util;

import java.awt.Color;
import java.awt.Font;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import org.rrd4j.ConsolFun;
import org.rrd4j.DsType;
import org.rrd4j.core.Archive;
import org.rrd4j.core.Datasource;
import org.rrd4j.core.FetchData;
import org.rrd4j.core.FetchRequest;
import org.rrd4j.core.RrdDb;
import org.rrd4j.core.RrdDbPool;
import org.rrd4j.core.RrdDef;
import org.rrd4j.core.Sample;
import org.rrd4j.graph.RrdGraph;
import org.rrd4j.graph.RrdGraphConstants;
import org.rrd4j.graph.RrdGraphDef;
import org.rrd4j.graph.RrdGraphInfo;

import br.com.concepting.framework.util.helpers.RrdArchive;
import br.com.concepting.framework.util.helpers.RrdColumn;
import br.com.concepting.framework.util.helpers.RrdDatasource;
import br.com.concepting.framework.util.helpers.RrdGraphDatasource;
import br.com.concepting.framework.util.helpers.RrdGraphDefinition;
import br.com.concepting.framework.util.helpers.RrdGraphText;
import br.com.concepting.framework.util.helpers.RrdRow;
import br.com.concepting.framework.util.types.DateFieldType;
import br.com.concepting.framework.util.types.RrdArchiveType;
import br.com.concepting.framework.util.types.RrdDatasourceType;
import br.com.concepting.framework.util.types.RrdGraphType;

/**
 * Classe que define a implementação para manipulação de arquivos RRD via framework RRD4J.
 *
 * @author fvilarinho
 * @since 1.0
 */
public class Rrd4JImpl extends BaseRrdImpl{
	/**
	 * @see br.com.concepting.framework.util.interfaces.IRrd#create(java.lang.String, java.util.Date, java.lang.Integer, java.util.Collection, java.util.Collection)
	 */
	public void create(String fileName, Date startTime, Integer step, Collection<RrdDatasource> datasources, Collection<RrdArchive> archives) throws IOException, IllegalArgumentException{
		RrdDef rrdDef = new RrdDef(fileName);
		
		rrdDef.setStartTime(startTime);
		rrdDef.setStep(step);
		
		for(RrdDatasource datasource : datasources)
			rrdDef.addDatasource(datasource.getName(), DsType.valueOf(datasource.getType().toString()), datasource.getFailInterval(), datasource.getMinimumValue(), datasource.getMaximumValue());
		
		for(RrdArchive archive : archives)
			rrdDef.addArchive(ConsolFun.valueOf(archive.getType().toString()), archive.getFailPercentage(), archive.getStep(), archive.getRows());

		RrdDbPool rrdPool = RrdDbPool.getInstance();
		RrdDb     rrdDb   = rrdPool.requestRrdDb(rrdDef);
		
		rrdPool.release(rrdDb);
	}
	
	/**
	 * @see br.com.concepting.framework.util.interfaces.IRrd#fetch(java.lang.String, java.util.Date, java.util.Date, br.com.concepting.framework.util.types.RrdArchiveType)
	 */
	public Collection<RrdRow> fetch(String fileName, Date startTime, Date endTime, RrdArchiveType type) throws IOException, IllegalArgumentException{
		RrdDbPool          rrdPool             = RrdDbPool.getInstance();
		RrdDb              rrdDb               = rrdPool.requestRrdDb(fileName);
		FetchRequest       fetchRequest        = rrdDb.createFetchRequest(ConsolFun.valueOf(type.toString()), startTime.getTime() / 1000, endTime.getTime() / 1000);
		FetchData          fetchData           = fetchRequest.fetchData();
		String             datasourcesNames[]  = rrdDb.getDsNames();
		double             datasourcesValues[] = null;
		long               datasourcesTimes[]  = null;
		Collection<RrdRow> rows                = new LinkedList<RrdRow>();
		RrdRow             row                 = null;
		Integer            cont                = 0;
		
		for(String datasourceName : datasourcesNames){
			datasourcesValues = fetchData.getValues(datasourceName);
			datasourcesTimes  = fetchData.getTimestamps();
			
			for(long timestamp : datasourcesTimes){
				row = new RrdRow();
				row.setDate(new Date(timestamp * 1000));
				row.addColumn(new RrdColumn(datasourceName, datasourcesValues[cont]));
		
				rows.add(row);
				
				cont++;
			}
		}
		
		rrdPool.release(rrdDb);

		return rows;
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IRrd#update(java.lang.String, br.com.concepting.framework.util.helpers.RrdRow)
	 */
	public void update(String fileName, RrdRow row) throws IOException, IllegalArgumentException{
		RrdDbPool     rrdPool = RrdDbPool.getInstance();
		RrdDb         rrdDb   = rrdPool.requestRrdDb(fileName);
		Sample        sample  = rrdDb.createSample();
		StringBuilder buffer  = new StringBuilder();
    		
		buffer.append(row.getDate().getTime() / 1000);
		
		for(RrdColumn column : row.getColumns()){
			buffer.append(":");
			buffer.append(column.getValue());
		}
		
		sample.setAndUpdate(buffer.toString());

		rrdPool.release(rrdDb);	
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IRrd#dump(java.lang.String, java.lang.String)
	 */
	public void dump(String fileName, String exportedFileName) throws IOException, IllegalArgumentException{
		RrdDbPool rrdPool = RrdDbPool.getInstance();
		RrdDb     rrdDb   = rrdPool.requestRrdDb(fileName);
		
		rrdDb.dumpXml(new FileOutputStream(exportedFileName));
		
		rrdPool.release(rrdDb);
    }

	/**
	 * @see br.com.concepting.framework.util.interfaces.IRrd#restore(java.lang.String, java.lang.String)
	 */
	public void restore(String exportedFileName, String fileName) throws IOException, IllegalArgumentException{
		RrdDbPool rrdPool = RrdDbPool.getInstance();
		RrdDb     rrdDb   = rrdPool.requestRrdDb(fileName, exportedFileName);
		
		rrdPool.release(rrdDb);
    }

	/**
	 * @see br.com.concepting.framework.util.interfaces.IRrd#getArchives(java.lang.String)
	 */
	public Collection<RrdArchive> getArchives(String fileName) throws IOException, IllegalArgumentException{
		Collection<RrdArchive> archives      = new LinkedList<RrdArchive>();
		RrdArchive             archive       = null;
		RrdDbPool              rrdPool       = RrdDbPool.getInstance();
		RrdDb                  rrdDb         = rrdPool.requestRrdDb(fileName);
		Integer                archivesCount = rrdDb.getArcCount();
		Archive                archiveBuffer = null;
		
		for(Integer cont = 0 ; cont < archivesCount ; cont++){
			archiveBuffer = rrdDb.getArchive(cont);
			
			archive = new RrdArchive();
			archive.setRows(archiveBuffer.getRows());
			archive.setType(RrdArchiveType.valueOf(archiveBuffer.getConsolFun().toString()));
			archive.setStep(archiveBuffer.getSteps());
			archive.setFailPercentage(archiveBuffer.getXff());
			
			archives.add(archive);
		}
		
		rrdPool.release(rrdDb);

	    return archives;
    }

	/**
	 * @see br.com.concepting.framework.util.interfaces.IRrd#getDatasources(java.lang.String)
	 */
	public Collection<RrdDatasource> getDatasources(String fileName) throws IOException, IllegalArgumentException{
		Collection<RrdDatasource> datasources      = new LinkedList<RrdDatasource>();
		RrdDatasource             datasource       = null;
		RrdDbPool                 rrdPool          = RrdDbPool.getInstance();
		RrdDb                     rrdDb            = rrdPool.requestRrdDb(fileName);
		Integer                   datasourcesCount = rrdDb.getDsCount();
		Datasource                datasourceBuffer = null;
		
		for(Integer cont = 0 ; cont < datasourcesCount ; cont++){
			datasourceBuffer = rrdDb.getDatasource(cont);
			
			datasource = new RrdDatasource();
			datasource.setName(datasourceBuffer.getDsName());
			datasource.setType(RrdDatasourceType.valueOf(datasourceBuffer.getDsType().toString()));
			datasource.setFailInterval(datasourceBuffer.getHeartbeat());
			datasource.setMinimumValue(datasourceBuffer.getMinValue());
			datasource.setMaximumValue(datasourceBuffer.getMaxValue());
			
			datasources.add(datasource);
		}

		rrdPool.release(rrdDb);

		return datasources;
    }

	/**
	 * @see br.com.concepting.framework.util.interfaces.IRrd#generateGraph(java.util.Date, java.util.Date, br.com.concepting.framework.util.helpers.RrdGraphDefinition)
	 */
	public byte[] generateGraph(Date startTime, Date endTime, RrdGraphDefinition definition) throws IOException, IllegalArgumentException{
		RrdGraphDef rrdGraphDef = new RrdGraphDef();
		
		rrdGraphDef.setShowSignature(false);
    	rrdGraphDef.setAntiAliasing(true);
		rrdGraphDef.setRigid(true);
		rrdGraphDef.setNoMinorGrid(true);
		rrdGraphDef.setInterlaced(true);
		rrdGraphDef.setColor(RrdGraphConstants.COLOR_ARROW, Color.BLACK);
		rrdGraphDef.setColor(RrdGraphConstants.COLOR_SHADEA, Color.WHITE);
		rrdGraphDef.setColor(RrdGraphConstants.COLOR_SHADEB, Color.WHITE);

		if(startTime != null)
			rrdGraphDef.setStartTime(startTime.getTime() / 1000);
		
		if(endTime != null)
			rrdGraphDef.setEndTime(endTime.getTime() / 1000);
		
		if(startTime != null && endTime != null){
			Long days   = DateTimeUtil.diff(endTime, startTime, DateFieldType.DAY);
			Long months = DateTimeUtil.diff(endTime, startTime, DateFieldType.MONTH);
		
			if(days >= 21 && months < 3)
				rrdGraphDef.setTimeAxis(RrdGraphConstants.HOUR, 8, RrdGraphConstants.DAY, 1, RrdGraphConstants.DAY, 7, 0, "dd/MMM");
			else if(months >= 3 && months < 7)
				rrdGraphDef.setTimeAxis(RrdGraphConstants.DAY, 1, RrdGraphConstants.MONTH, 1, RrdGraphConstants.MONTH, 1, 0, "MMM/yyyy");
			else if(months >= 7)
				rrdGraphDef.setTimeAxis(RrdGraphConstants.DAY, 1, RrdGraphConstants.MONTH, 1, RrdGraphConstants.MONTH, 2, 0, "MMM/yyyy");
		}

		if(definition != null){
    		if(definition.getWidth() != null && definition.getWidth() > 0)
    			rrdGraphDef.setWidth(definition.getWidth());
    
    		if(definition.getHeight() != null && definition.getHeight() > 0)
    			rrdGraphDef.setHeight(definition.getHeight());
    		
    		if(StringUtil.trim(definition.getTitle()).length() > 0)
    			rrdGraphDef.setTitle(definition.getTitle());
    
    		if(StringUtil.trim(definition.getVerticalLabel()).length() > 0)
    			rrdGraphDef.setVerticalLabel(definition.getVerticalLabel());
    
    		if(definition.getMaximumValue() != null && definition.getMaximumValue() > 0)
    			rrdGraphDef.setMaxValue(definition.getMaximumValue());
    		else{
    			rrdGraphDef.setAltAutoscaleMax(true);
    			rrdGraphDef.setAltYGrid(true);
    		}
    			
        	if(definition.getMinimumValue() != null && definition.getMinimumValue() > 0)
        		rrdGraphDef.setMinValue(definition.getMinimumValue());

    		rrdGraphDef.setImageFormat(definition.getContentType().getExtension().substring(1).toUpperCase());

    		if(StringUtil.trim(definition.getBackgroundColor()).length() > 0)
    			rrdGraphDef.setColor(RrdGraphConstants.COLOR_BACK, definition.getBackgroundColor());
    		
    		if(StringUtil.trim(definition.getGraphBackgroundColor()).length() > 0)
    			rrdGraphDef.setColor(RrdGraphConstants.COLOR_CANVAS, definition.getGraphBackgroundColor());

    		if(StringUtil.trim(definition.getColor()).length() > 0)
    			rrdGraphDef.setColor(RrdGraphConstants.COLOR_FONT, definition.getColor());
    		
    		if(StringUtil.trim(definition.getGridLinesColor()).length() > 0){
    			rrdGraphDef.setColor(RrdGraphConstants.COLOR_MGRID, definition.getGridLinesColor());
    			rrdGraphDef.setColor(RrdGraphConstants.COLOR_GRID, definition.getGridLinesColor());
    		}
    		
    		Font font = null;
    		
    		if(StringUtil.trim(definition.getFont()).length() > 0)
    			font = Font.getFont(definition.getFont());
    		else
    			font = Font.getFont(Font.MONOSPACED);
    				
    		if(definition.getFontSize() != null && definition.getFontSize() > 0){
    			font = font.deriveFont(definition.getFontSize());
    		
    			rrdGraphDef.setSmallFont(font);
			}
    		
    		Font titleFont = null;
    		
    		if(StringUtil.trim(definition.getTitleFont()).length() > 0)
    			titleFont = Font.getFont(definition.getTitleFont());
    		else
    			titleFont = Font.getFont(Font.MONOSPACED);
    				
    		if(definition.getTitleFontSize() != null && definition.getTitleFontSize() > 0){
    			titleFont = titleFont.deriveFont(definition.getTitleFontSize());
    		
    			rrdGraphDef.setLargeFont(titleFont);
			}
    		
    		Collection         objects    = definition.getObjects(); 
    		RrdGraphText       text       = null;
    		RrdGraphDatasource datasource = null;
    		
    		if(objects != null && objects.size() > 0){
        		for(Object object : objects){
        			if(object instanceof RrdGraphText){
        				text = (RrdGraphText)object;

        				if(text.getDatasourceName().length() > 0){
        					if(text.getArchiveType() == RrdArchiveType.AVERAGE)
        						rrdGraphDef.gprint(text.getDatasourceName(), ConsolFun.AVERAGE, text.getPattern());
        					else if(text.getArchiveType() == RrdArchiveType.LAST)
        						rrdGraphDef.gprint(text.getDatasourceName(), ConsolFun.LAST, text.getPattern());
        					else if(text.getArchiveType() == RrdArchiveType.MAX)
        						rrdGraphDef.gprint(text.getDatasourceName(), ConsolFun.MAX, text.getPattern());
        					else if(text.getArchiveType() == RrdArchiveType.MIN)
        						rrdGraphDef.gprint(text.getDatasourceName(), ConsolFun.MIN, text.getPattern());
        				}
            			else
            				rrdGraphDef.comment(text.getValue());
        			}
        			else if(object instanceof RrdGraphDatasource){
        				datasource = (RrdGraphDatasource)object;
        				
            			if(datasource.getArchiveType() != null){
        					if(datasource.getArchiveType() == RrdArchiveType.AVERAGE)
        						rrdGraphDef.datasource(datasource.getName(), datasource.getFilename(), datasource.getDatasourceName(), ConsolFun.AVERAGE);
        					else if(datasource.getArchiveType() == RrdArchiveType.LAST)
        						rrdGraphDef.datasource(datasource.getName(), datasource.getFilename(), datasource.getDatasourceName(), ConsolFun.LAST);
        					else if(datasource.getArchiveType() == RrdArchiveType.MAX)
        						rrdGraphDef.datasource(datasource.getName(), datasource.getFilename(), datasource.getDatasourceName(), ConsolFun.MAX);
        					else if(datasource.getArchiveType() == RrdArchiveType.MIN)
        						rrdGraphDef.datasource(datasource.getName(), datasource.getFilename(), datasource.getDatasourceName(), ConsolFun.MIN);
            			}
            			else if(datasource.getFormula().length() > 0)
            				rrdGraphDef.datasource(datasource.getName(), datasource.getFormula());
            			else if(datasource.getGraphType() != null){
            				if(datasource.getGraphType() == RrdGraphType.HRULE)
            					rrdGraphDef.hrule(datasource.getValue(), datasource.getColor(), datasource.getLabel());
            				else if(datasource.getGraphType() == RrdGraphType.AREA)
            					rrdGraphDef.area(datasource.getName(), datasource.getColor(), datasource.getLabel());
               				else if(datasource.getGraphType() == RrdGraphType.LINE)
                				rrdGraphDef.line(datasource.getName(), datasource.getColor(), datasource.getLabel(), datasource.getLineWidth());
               				else if(datasource.getGraphType() == RrdGraphType.STACK)
               					rrdGraphDef.stack(datasource.getName(), datasource.getColor(), datasource.getLabel());
            			}
        			}
        		}
    		}
		}
		
		RrdGraph     rrdGraph     = new RrdGraph(rrdGraphDef);
		RrdGraphInfo rrdGraphInfo = rrdGraph.getRrdGraphInfo();
		
		return rrdGraphInfo.getBytes();
    }

	/**
	 * @see br.com.concepting.framework.util.interfaces.IRrd#generateGraph(java.lang.String, java.util.Date, java.util.Date, br.com.concepting.framework.util.helpers.RrdGraphDefinition)
	 */
	public void generateGraph(String fileName, Date startTime, Date endTime, RrdGraphDefinition definition) throws IOException, IllegalArgumentException{
	    byte rrdGraphData[] = generateGraph(startTime, endTime, definition);
	    
	    FileUtil.toBinaryFile(fileName, rrdGraphData);
    }
}