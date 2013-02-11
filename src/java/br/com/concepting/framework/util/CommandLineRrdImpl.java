package br.com.concepting.framework.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.concepting.framework.util.constants.RrdConstants;
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
 * Classe que define a implementação para manipulação de arquivos RRD via linha de comando.
 *
 * @author fvilarinho
 * @since 1.0
 */ 
public class CommandLineRrdImpl extends BaseRrdImpl{
	private static Collection<String> variableOperators = null;
	
	static{
		variableOperators = new LinkedList<String>();
		variableOperators.add("STDEV");
		variableOperators.add("LAST");
		variableOperators.add("FIRST");
		variableOperators.add("TOTAL");
		variableOperators.add("PERCENT");
		variableOperators.add("LSLSLOPE");
		variableOperators.add("LSLINT");
		variableOperators.add("LSLCORREL");
		variableOperators.add("MAXIMUM");
		variableOperators.add("MINIMUM");
		variableOperators.add("AVERAGE");
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IRrd#create(java.lang.String, java.util.Date, java.lang.Integer, java.util.Collection, java.util.Collection)
	 */
	public void create(String fileName, Date startTime, Integer step, Collection<RrdDatasource> datasources, Collection<RrdArchive> archives) throws IOException, IllegalArgumentException{
		Collection<String> commandParameters = new LinkedList<String>();
		
		commandParameters.add(RrdConstants.RRD_COMMAND);
		commandParameters.add(RrdConstants.RRD_CREATE_ARGUMENT);
		commandParameters.add(StringUtil.trim(fileName));
		commandParameters.add("--step");
		commandParameters.add(String.valueOf(step));
		
		if(startTime != null){
			commandParameters.add("--start");
			commandParameters.add(String.valueOf(startTime.getTime() / 1000));
		}
		
		StringBuilder buffer = new StringBuilder();
		
		if(datasources != null && datasources.size() > 0){
    		for(RrdDatasource datasource : datasources) {
    			buffer.delete(0, buffer.length());
    			buffer.append("DS:");
    			buffer.append(datasource.getName());
    			buffer.append(":");
    			buffer.append(datasource.getType());
    			buffer.append(":");
    			buffer.append(datasource.getFailInterval());
    			buffer.append(":");
    			
    			if(datasource.getMinimumValue() != null && !datasource.getMinimumValue().equals(Double.NaN))
    				buffer.append(datasource.getMinimumValue());
    			else
    				buffer.append("nan");
    			
    			buffer.append(":");
    			
    			if(datasource.getMaximumValue() != null && !datasource.getMaximumValue().equals(Double.NaN))
    				buffer.append(datasource.getMaximumValue());
    			else
    				buffer.append("nan");
    			
    			commandParameters.add(buffer.toString());
    		}
		}
		
		if(archives != null && archives.size() > 0){
    		for(RrdArchive archive : archives) {
    			buffer.delete(0, buffer.length());
    			buffer.append("RRA:");
    			buffer.append(archive.getType());
    			buffer.append(":");
    			buffer.append(archive.getFailPercentage());
    			buffer.append(":");
    			buffer.append(archive.getStep());
    			buffer.append(":");
    			buffer.append(archive.getRows());
    
    			commandParameters.add(buffer.toString());
    		}
		}
		
		ProcessLoader  loader = ProcessLoader.getInstance();
		InputStream    stream = loader.execute(commandParameters);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

		try{
    		String line = StringUtil.trim(reader.readLine());
    		
    		if(line.contains("ERROR"))
    			throw new IllegalArgumentException(line);
		} 
		finally{
			try{
				reader.close();
			}
			catch(Throwable e){
			}
		}
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IRrd#fetch(java.lang.String, java.util.Date, java.util.Date, br.com.concepting.framework.util.types.RrdArchiveType)
	 */
	public Collection<RrdRow> fetch(String fileName, Date startTime, Date endTime, RrdArchiveType type) throws IOException, IllegalArgumentException{
		Collection<String> commandParameters = new LinkedList<String>();
		
		commandParameters.add(RrdConstants.RRD_COMMAND);
		commandParameters.add(RrdConstants.RRD_FETCH_ARGUMENT);
		commandParameters.add(StringUtil.trim(fileName));
		
		if(type != null)
			commandParameters.add(type.toString());
		
		if(startTime != null){
			commandParameters.add("--start");
			commandParameters.add(String.valueOf(startTime.getTime() / 1000));
		}
		
		if(endTime != null){
			commandParameters.add("--end");
			commandParameters.add(String.valueOf(endTime.getTime() / 1000));
		}

		ProcessLoader      loader = ProcessLoader.getInstance();
		InputStream        stream = loader.execute(commandParameters);
		BufferedReader     reader = new BufferedReader(new InputStreamReader(stream));
		Collection<RrdRow> rows   = new LinkedList<RrdRow>();
		
		try{
    		String line = StringUtil.trim(reader.readLine());
    		
    		if(line.contains("ERROR"))
    			throw new IllegalArgumentException(line);
    		
    		String    datasourceNames[]  = StringUtil.split(line, " ");
    		String    datasourceValues[] = null;
    		String    datasourceValue    = "";
    		String    timestamp          = "";
    		RrdRow    row                = null;
    		RrdColumn column             = null;
    		Integer   pos                = 0;
    		
    		reader.readLine();
    		
    		while((line = reader.readLine()) != null){
    			pos = line.indexOf(":");
    			if(pos >= 0){
    				timestamp        = line.substring(0, pos);
    				line             = StringUtil.trim(line.substring(pos + 1));
    				datasourceValues = StringUtil.split(line, " "); 
    				
    				row = new RrdRow();
    				row.setDate(new Date(Long.parseLong(timestamp) * 1000));
    				
    				for(Integer cont = 0 ; cont < datasourceNames.length ; cont++){
    					column = new RrdColumn();
    					column.setName(StringUtil.trim(datasourceNames[cont]));
    		
    					datasourceValue = StringUtil.trim(datasourceValues[cont]);
    					datasourceValue = datasourceValue.toLowerCase();
    					datasourceValue = StringUtil.replaceAll(datasourceValue, ",", "."); 
    
    					if(datasourceValue.equals("nan"))
    						column.setValue(Double.NaN);
    					else
    						column.setValue(Double.valueOf(datasourceValue));
    					
    					row.addColumn(column);
    				}
    				
    				rows.add(row);
    			}
    		}
		}
		finally{
			try{
				reader.close();
			}
			catch(Throwable e){
			}
		}
		
		return rows;
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IRrd#update(java.lang.String, br.com.concepting.framework.util.helpers.RrdRow)
	 */
	public void update(String fileName, RrdRow row) throws IOException, IllegalArgumentException{
		Collection<String> commandParameters = new LinkedList<String>();
		
		commandParameters.add(RrdConstants.RRD_COMMAND);
		commandParameters.add(RrdConstants.RRD_UPDATE_ARGUMENT);
		commandParameters.add(StringUtil.trim(fileName));
		commandParameters.add("--template");
		
		if(row != null){
    		StringBuilder columnsBuffer = new StringBuilder();
    		StringBuilder valuesBuffer  = new StringBuilder();
    			
			if(row.getDate() != null){
				valuesBuffer.append(row.getDate().getTime() / 1000);
				valuesBuffer.append(":");
			}
			else
				valuesBuffer.append("N:");
			
			if(row.getColumns() != null && row.getColumns().size() > 0){
    			for(RrdColumn column : row.getColumns()){
    				if(columnsBuffer.length() > 0){
    					columnsBuffer.append(":");
    					valuesBuffer.append(":");
    				}
    				
    				columnsBuffer.append(column.getName());
    				valuesBuffer.append(column.getValue());
    			}
			}

			commandParameters.add(columnsBuffer.toString());
			commandParameters.add(valuesBuffer.toString());
		}
    
		ProcessLoader  loader = ProcessLoader.getInstance();
		InputStream    stream = loader.execute(commandParameters);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		
		try{
			String line = StringUtil.trim(reader.readLine());
	
			if(line.contains("ERROR"))
				throw new IllegalArgumentException(line);
		}
		finally{
			try{
				reader.close();
			}
			catch(Throwable e){
			}
		}
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IRrd#dump(java.lang.String, java.lang.String)
	 */
	public void dump(String fileName, String exportedFileName) throws IOException, IllegalArgumentException{
		String[] commandParameters = new String[4];
		
		commandParameters[0] = RrdConstants.RRD_COMMAND;
		commandParameters[1] = RrdConstants.RRD_DUMP_ARGUMENT;
		commandParameters[2] = fileName;
		commandParameters[3] = exportedFileName;

		ProcessLoader  loader = ProcessLoader.getInstance();
		InputStream    stream = loader.execute(commandParameters);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		
		try{
    		String line = StringUtil.trim(reader.readLine());
    
    		if(line.contains("ERROR"))
    			throw new IllegalArgumentException(line);
		}
		finally{
			try{
				reader.close();
			}
			catch(Throwable e){
			}
		}
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IRrd#restore(java.lang.String, java.lang.String)
	 */
	public void restore(String exportedFileName, String fileName) throws IOException, IllegalArgumentException{
		String[] commandParameters = new String[4];
		
		commandParameters[0] = RrdConstants.RRD_COMMAND;
		commandParameters[1] = RrdConstants.RRD_RESTORE_ARGUMENT;
		commandParameters[2] = exportedFileName;
		commandParameters[3] = fileName;

		ProcessLoader  loader = ProcessLoader.getInstance();
		InputStream    stream = loader.execute(commandParameters);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		
		try{
    		String line = StringUtil.trim(reader.readLine());
    
    		if(line.contains("ERROR"))
    			throw new IllegalArgumentException(line);
		}
		finally{
			try{
				reader.close();
			}
			catch(Throwable e){
			}
		}
    }
	

	/**
	 * @see br.com.concepting.framework.util.interfaces.IRrd#getArchives(java.lang.String)
	 */
	public Collection<RrdArchive> getArchives(String fileName) throws IOException, IllegalArgumentException{
		Collection<String> commandParameters = new LinkedList<String>();
		
		commandParameters.add(RrdConstants.RRD_COMMAND);
		commandParameters.add(RrdConstants.RRD_INFO_ARGUMENT);
		commandParameters.add(StringUtil.trim(fileName));

		ProcessLoader          loader             = ProcessLoader.getInstance();
		InputStream            stream             = loader.execute(commandParameters);
		BufferedReader         reader             = new BufferedReader(new InputStreamReader(stream));
		String                 line               = "";
		Boolean                found              = false;
		Pattern                pattern            = null;
		Matcher                matcher            = null;
		Collection<RrdArchive> archives           = new LinkedList<RrdArchive>();
		RrdArchive             archive            = null;
		String                 currentArchiveName = "";
		String                 archiveName        = "";
		String                 propertyName       = "";
		String                 propertyValue      = "";
		
		try{
    		while((line = reader.readLine()) != null){
    			if(line.contains("ERROR"))
    				throw new IllegalArgumentException(line);
    
    			if(line.contains("rra[")){
    				found   = true;
    				pattern = Pattern.compile("rra\\[(.+)\\]\\.(.+)\\ ?\\=\\ ?(.+)");
    				matcher = pattern.matcher(line);
    				
    				if(matcher.find()){
    					archiveName = StringUtil.trim(matcher.group(1));
    					
    					if(!archiveName.equals(currentArchiveName) && !archiveName.contains("]")){
    						if(archive != null)
    							archives.add(archive);
    					
    						archive = new RrdArchive();
    						
    						currentArchiveName = archiveName;
    					}
    					
    					propertyName  = StringUtil.trim(matcher.group(2));
    					propertyValue = StringUtil.trim(matcher.group(3));
    					propertyValue = StringUtil.replaceAll(propertyValue, "\"", "");
    					propertyValue = StringUtil.replaceAll(propertyValue, ",", ".");
    				
    					if(archive != null){
    						if(propertyName.equals("cf"))
    							archive.setType(RrdArchiveType.valueOf(propertyValue));
    						else if(propertyName.equals("rows"))
    							archive.setRows(Integer.valueOf(propertyValue));
    						else if(propertyName.equals("pdp_per_row"))
    							archive.setStep(Integer.valueOf(propertyValue));
    						else if(propertyName.equals("xff"))
    							archive.setFailPercentage(Double.valueOf(propertyValue));
    					}
    				}
    			}
    			else if(found)
    				break;
    		}
    		
    		if(archive != null)
    			archives.add(archive);
		}
		finally{
			try{
				reader.close();
			}
			catch(Throwable e){
			}
		}
		
		return archives;
    }

	/**
	 * @see br.com.concepting.framework.util.interfaces.IRrd#getDatasources(java.lang.String)
	 */
	public Collection<RrdDatasource> getDatasources(String fileName) throws IOException, IllegalArgumentException{
		Collection<String> commandParameters = new LinkedList<String>();

		commandParameters.add(RrdConstants.RRD_COMMAND);
		commandParameters.add(RrdConstants.RRD_INFO_ARGUMENT);
		commandParameters.add(StringUtil.trim(fileName));

		Collection<RrdDatasource> datasources           = new LinkedList<RrdDatasource>();
		ProcessLoader             loader                = ProcessLoader.getInstance();
		InputStream               stream                = loader.execute(commandParameters);
		BufferedReader            reader                = new BufferedReader(new InputStreamReader(stream));
		String                    line                  = "";
		Boolean                   found                 = false;
		Pattern                   pattern               = null;
		Matcher                   matcher               = null;
		RrdDatasource             datasource            = null;
		String                    currentDatasourceName = "";
		String                    datasourceName        = "";
		String                    propertyName          = "";
		String                    propertyValue         = "";
		
		try{
    		while((line = reader.readLine()) != null){
    			if(line.contains("ERROR"))
    				throw new IllegalArgumentException(line);
    
    			if(line.contains("ds[")){
    				found   = true;
    				pattern = Pattern.compile("ds\\[(.+)\\]\\.(.+)\\ ?\\=\\ ?(.+)");
    				matcher = pattern.matcher(line);
    				
    				if(matcher.find()){
    					datasourceName = StringUtil.trim(matcher.group(1));
    					
    					if(!datasourceName.equals(currentDatasourceName)){
    						if(datasource != null)
    							datasources.add(datasource);
    					
    						datasource = new RrdDatasource();
    						datasource.setName(datasourceName);
    						
    						currentDatasourceName = datasourceName;
    					}
    					
    					propertyName  = StringUtil.trim(matcher.group(2));
    					propertyValue = StringUtil.trim(matcher.group(3));
    					propertyValue = StringUtil.replaceAll(propertyValue, "\"", "");
    					propertyValue = StringUtil.replaceAll(propertyValue, ",", ".");
    				
    					if(datasource != null){
    						if(propertyName.equals("type"))
    							datasource.setType(RrdDatasourceType.valueOf(propertyValue));
    						else if(propertyName.equals("min"))
    							datasource.setMinimumValue(Double.valueOf(propertyValue));
    						else if(propertyName.equals("max"))
    							datasource.setMaximumValue(Double.valueOf(propertyValue));
    						else if(propertyName.equals("minimal_heartbeat"))
    							datasource.setFailInterval(Long.valueOf(propertyValue));
    					}
    				}
    			}
    			else if(found)
    				break;
    		}
    		
    		if(datasource != null)
    			datasources.add(datasource);
		}
		finally{
			try{
				reader.close();
			}
			catch(Throwable e){
			}
		}
		
		return datasources;
    }

	/**
	 * @see br.com.concepting.framework.util.interfaces.IRrd#generateGraph(java.lang.String, java.util.Date, java.util.Date, br.com.concepting.framework.util.helpers.RrdGraphDefinition)
	 */
	public void generateGraph(String fileName, Date startTime, Date endTime, RrdGraphDefinition definition) throws IOException, IllegalArgumentException{
		byte data[] = generateGraph(startTime, endTime, definition);
		
		FileUtil.toBinaryFile(fileName, data);
    }

	/**
	 * @see br.com.concepting.framework.util.interfaces.IRrd#generateGraph(java.util.Date, java.util.Date, br.com.concepting.framework.util.helpers.RrdGraphDefinition)
	 */
	public byte[] generateGraph(Date startTime, Date endTime, RrdGraphDefinition definition) throws IOException, IllegalArgumentException{
		Collection<String> commandParameters = new LinkedList<String>();
		Integer            id                = (int)(Math.random() * 9999);
		
		commandParameters.add(RrdConstants.RRD_COMMAND);
		commandParameters.add(RrdConstants.RRD_GRAPH_ARGUMENT);
		commandParameters.add("/tmp/commandLineRrd".concat(id.toString()));
		commandParameters.add("--no-gridfit");
		commandParameters.add("--rigid");
		commandParameters.add("--no-minor");
		commandParameters.add("--interlaced");
		commandParameters.add("--color");
		commandParameters.add("ARROW#000000");
		commandParameters.add("--color");
		commandParameters.add("SHADEA#ffffff");
		commandParameters.add("--color");
		commandParameters.add("SHADEB#ffffff");

		if(startTime != null){
    		commandParameters.add("--start");
    		commandParameters.add(String.valueOf(startTime.getTime() / 1000));
		}
		
		if(endTime != null){
    		commandParameters.add("--end");
    		commandParameters.add(String.valueOf(endTime.getTime() / 1000));
		}
		
		if(startTime != null && endTime != null){
			Long days   = DateTimeUtil.diff(endTime, startTime, DateFieldType.DAY);
			Long months = DateTimeUtil.diff(endTime, startTime, DateFieldType.MONTH);
		
			if(days >= 21 && months < 3){
				commandParameters.add("--x-grid");
				commandParameters.add("HOUR:8:DAY:1:DAY:7:0:%d/%b");
			}
			else if(months >= 3 && months < 7){
				commandParameters.add("--x-grid");
				commandParameters.add("DAY:1:MONTH:1:MONTH:1:0:%b/%y");
			}
			else if(months >= 7){
				commandParameters.add("--x-grid");
				commandParameters.add("DAY:1:MONTH:1:MONTH:2:0:%b/%y");
			}
		}
		
		if(definition.getWidth() != null && definition.getWidth() > 0){
    		commandParameters.add("--width");
    		commandParameters.add(String.valueOf(definition.getWidth()));
		}
		
		if(definition.getHeight() != null && definition.getHeight() > 0){
    		commandParameters.add("--height");
    		commandParameters.add(String.valueOf(definition.getHeight()));
		}
		
		if(StringUtil.trim(definition.getTitle()).length() > 0){
    		commandParameters.add("--title");
    		commandParameters.add(definition.getTitle());
		}
		
		if(StringUtil.trim(definition.getVerticalLabel()).length() > 0){
    		commandParameters.add("--vertical-label");
    		commandParameters.add(definition.getVerticalLabel());
		}
		
		if(definition.getMaximumValue() != null && definition.getMaximumValue() > 0){
    		commandParameters.add("--upper-limit");
    		commandParameters.add(String.valueOf(definition.getMaximumValue()));
		}
		else
			commandParameters.add("--alt-y-grid");
		
		if(definition.getMinimumValue() != null){
    		commandParameters.add("--lower-limit");
    		commandParameters.add(String.valueOf(definition.getMinimumValue()));
		}

		if(StringUtil.trim(definition.getXAxisLabel()).length() > 0){
    		commandParameters.add("--x-grid");
    		commandParameters.add(definition.getXAxisLabel());
		}

		if(StringUtil.trim(definition.getYAxisLabel()).length() > 0){
    		commandParameters.add("--y-grid");
    		commandParameters.add(definition.getYAxisLabel());
		}
		
		if(definition.getContentType() != null){
    		commandParameters.add("--imgformat");
    		commandParameters.add(definition.getContentType().getExtension().substring(1).toUpperCase());
		}
		
		if(definition.getBackgroundColor() != null){
    		commandParameters.add("--color");
    		commandParameters.add("BACK".concat(ColorUtil.toString(definition.getBackgroundColor())));
		}
		
		if(definition.getGraphBackgroundColor() != null){
    		commandParameters.add("--color");
    		commandParameters.add("CANVAS".concat(ColorUtil.toString(definition.getGraphBackgroundColor())));
		}

		if(definition.getColor() != null){
    		commandParameters.add("--color");
    		commandParameters.add("FONT".concat(ColorUtil.toString(definition.getColor())));
    		commandParameters.add("--color");
    		commandParameters.add("AXIS".concat(ColorUtil.toString(definition.getColor())));
		}
		
		if(definition.getGridLinesColor() != null){
    		commandParameters.add("--color");
    		commandParameters.add("MGRID".concat(ColorUtil.toString(definition.getGridLinesColor())));
    		commandParameters.add("--color");
    		commandParameters.add("GRID".concat(ColorUtil.toString(definition.getGridLinesColor())));
		}
		
		if(definition.getFontSize() != null && definition.getFontSize() > 0){
    		commandParameters.add("--font");
    		commandParameters.add("AXIS:".concat(String.valueOf(definition.getFontSize())).concat(":").concat(StringUtil.trim(definition.getFont()).length() > 0 ? definition.getFont() : "."));
    		commandParameters.add("--font");
    		commandParameters.add("LEGEND:".concat(String.valueOf(definition.getFontSize())).concat(":").concat(StringUtil.trim(definition.getFont()).length() > 0 ? definition.getFont() : "."));
		}

		if(definition.getTitleFontSize() != null && definition.getTitleFontSize() > 0){
    		commandParameters.add("--font");
    		commandParameters.add("TITLE:".concat(String.valueOf(definition.getTitleFontSize())).concat(":").concat(StringUtil.trim(definition.getTitleFont()).length() > 0 ? definition.getTitleFont() : "."));
		}

		Collection         objects    = definition.getObjects(); 
		StringBuilder      buffer     = new StringBuilder();
		RrdGraphText       text       = null;
		RrdGraphDatasource datasource = null;
		Boolean            found      = false;
		
		if(objects != null && objects.size() > 0){
    		for(Object object : objects){
    			if(object instanceof RrdGraphText){
    				text = (RrdGraphText)object;
    				
        			if(text.getDatasourceName().length() > 0){
        				buffer.delete(0, buffer.length());
        				buffer.append("GPRINT:");
        				buffer.append(text.getDatasourceName());
    					buffer.append(":");
    					buffer.append(text.getArchiveType().toString());
        				buffer.append(":");
        				buffer.append(text.getPattern());
    
        				commandParameters.add(buffer.toString());
        			}
        			else{
        				buffer.delete(0, buffer.length());
        				buffer.append("COMMENT:");
        				buffer.append(text.getValue());
    
        				commandParameters.add(buffer.toString());
        			}
    			}
    			else if(object instanceof RrdGraphDatasource){
    				datasource = (RrdGraphDatasource)object;
    				
        			if(datasource.getArchiveType() != null){
        				buffer.delete(0, buffer.length());
        				buffer.append("DEF:");
        				buffer.append(datasource.getName());
        				buffer.append("=");
        				buffer.append(datasource.getFileName());
        				buffer.append(":");
        				buffer.append(datasource.getDatasourceName());
        				buffer.append(":");
        				buffer.append(datasource.getArchiveType());
        		
        				commandParameters.add(buffer.toString());
        			}
        			else if(datasource.getFormula().length() > 0){
        				buffer.delete(0, buffer.length());

        				found = false;
        				
        				for(String variableOperator : variableOperators){
        					if(datasource.getFormula().contains(variableOperator)){
        						found = true;
        						
        						break;
        					}
        				}
        				
        				if(!found)
        					buffer.append("CDEF:");
        				else
        					buffer.append("VDEF:");
        				
        				buffer.append(datasource.getName());
        				buffer.append("=");
        				buffer.append(datasource.getFormula());
        		
        				commandParameters.add(buffer.toString());
        			}
        			else if(datasource.getGraphType() != null){
        				buffer.delete(0, buffer.length());
        				buffer.append(datasource.getGraphType());
        				
        				if(datasource.getGraphType() == RrdGraphType.HRULE){
        					buffer.append(":");
        					buffer.append(datasource.getValue());
        					buffer.append(ColorUtil.toString(datasource.getColor()));
        					buffer.append(":");
        					buffer.append(datasource.getLabel());
        				}
        				else{
            				if(datasource.getLineWidth() != null && datasource.getLineWidth() > 1)
            					buffer.append(datasource.getLineWidth());
            				
            				buffer.append(":");
            				buffer.append(datasource.getName());
        					buffer.append(ColorUtil.toString(datasource.getColor()));
        					buffer.append(":");
        					buffer.append(datasource.getLabel());
        				}
        		
        				commandParameters.add(buffer.toString());
        			}
    			}
    		}
		}
		
		ProcessLoader loader   = ProcessLoader.getInstance();
		InputStream   stream   = loader.execute(commandParameters);
		byte          result[] = ByteUtil.fromBinaryStream(stream);
		
		if(result != null){
    		String resultBuffer = new String(result);
    		
    		if(resultBuffer.contains("ERROR"))
    			throw new IllegalArgumentException(resultBuffer);
    		
    		result = FileUtil.fromBinaryFile("/tmp/commandLineRrd".concat(id.toString()));
    		
    		File file = new File("/tmp/commandLineRrd".concat(id.toString()));
    		
    		if(file.exists())
    			file.delete();
		}
		
		return result;
    }
}
