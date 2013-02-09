package br.com.concepting.framework.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.resource.I18nResourceLoader;
import br.com.concepting.framework.resource.PropertiesResource;
import br.com.concepting.framework.resource.constants.ResourceConstants;
import br.com.concepting.framework.util.constants.ReportConstants;
import br.com.concepting.framework.util.helpers.ReportDataSource;
import br.com.concepting.framework.web.types.ContentType;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
 
/**
 * Classe utilitária responsável por manipular relatórios criados utilizando o framework 
 * JasperReports e a IDE iReport.
 *
 * @author fvilarinho
 * @since 1.0
 */
public abstract class ReportUtil{
 
	/**
	 * Processa um relatório utilizando uma instância de conexão com um repositório de dados.
	 * 
	 * @param resourceId String contendo o identificador do arquivo de relatório desejado.
     * @param datasource Instância contendo o acesso aos dados do relatório.
	 * @return Array de bytes contendo o relatório processado.
	 * @throws JRException
	 */
	public static <M extends BaseModel> byte[] generateReport(String resourceId, Object datasource) throws JRException{
		return generateReport("", resourceId, LanguageUtil.getDefaultLanguage(), datasource);
	}
	
    /**
     * Processa um relatório utilizando uma instância de conexão com um repositório de dados.
     *
     * @param resourceDir String contendo o diretório de armazenamento do arquivo de 
     * relatório desejado.
     * @param resourceId String contendo o identificador do arquivo de relatório desejado.
     * @param datasource Instância contendo o acesso aos dados do relatório.
     * @return Array de bytes contendo o relatório processado.
     * @throws JRException
     */
	public static <M extends BaseModel> byte[] generateReport(String resourceDir, String resourceId, Object datasource) throws JRException{
		return generateReport(resourceDir, resourceId, LanguageUtil.getDefaultLanguage(), datasource);
	}

    /**
     * Processa um relatório utilizando uma instância de conexão com um repositório de dados.
     * 
     * @param resourceId String contendo o identificador do arquivo de relatório desejado.
     * @param language Instância contendo o idioma a ser utilizado no processamento.
     * @param datasource Instância contendo o acesso aos dados do relatório.
     * @return Array de bytes contendo o relatório processado.
     * @throws JRException
     */
	public static <M extends BaseModel> byte[] generateReport(String resourceId, Locale language, Object datasource) throws JRException{
		return generateReport("", resourceId, null, language, datasource);
	}
	
    /**
     * Processa um relatório utilizando uma instância de conexão com um repositório de dados.
     * 
     * @param resourceDir String contendo o diretório de armazenamento do arquivo de 
     * relatório desejado.
     * @param resourceId String contendo o identificador do arquivo de relatório desejado.
     * @param language Instância contendo o idioma a ser utilizado no processamento.
     * @param datasource Instância contendo o acesso aos dados do relatório.
     * @return Array de bytes contendo o relatório processado.
     * @throws JRException
     */
	public static <M extends BaseModel> byte[] generateReport(String resourceDir, String resourceId, Locale language, Object datasource) throws JRException{
		return generateReport(resourceDir, resourceId, null, language, datasource);
	}
	
    /**
     * Processa um relatório utilizando uma instância de conexão com um repositório de dados.
     * 
     * @param resourceId String contendo o identificador do arquivo de relatório desejado.
     * @param reportParameters Mapa contendo os parâmetros utilizados no relatório.
     * @param datasource Instância contendo o acesso aos dados do relatório.
     * @return Array de bytes contendo o relatório processado.
     * @throws JRException
     */
	public static <M extends BaseModel> byte[] generateReport(String resourceId, Map<String, Object> reportParameters, Object datasource) throws JRException{
		return generateReport("", resourceId, reportParameters, LanguageUtil.getDefaultLanguage(), datasource);
	}
	
    /**
     * Processa um relatório utilizando uma instância de conexão com um repositório de dados.
     * 
     * @param resourceDir String contendo o diretório de armazenamento do arquivo de 
     * relatório desejado.
     * @param resourceId String contendo o identificador do arquivo de relatório desejado.
     * @param reportParameters Mapa contendo os parâmetros utilizados no relatório.
     * @param datasource Instância contendo o acesso aos dados do relatório.
     * @return Array de bytes contendo o relatório processado.
     * @throws JRException
     */
	public static <M extends BaseModel> byte[] generateReport(String resourceDir, String resourceId, Map<String, Object> reportParameters, Object datasource) throws JRException{
		return generateReport(resourceDir, resourceId, reportParameters, LanguageUtil.getDefaultLanguage(), datasource);
	}
	
    /**
     * Processa um relatório utilizando uma instância de conexão com um repositório de dados.
     * 
     * @param resourceId String contendo o identificador do arquivo de relatório desejado.
     * @param reportParameters Mapa contendo os parâmetros utilizados no relatório.
     * @param language Instância contendo o idioma a ser utilizado no processamento.
     * @param datasource Instância contendo o acesso aos dados do relatório.
     * @return Array de bytes contendo o relatório processado.
     * @throws JRException
     */
	public static <M extends BaseModel> byte[] generateReport(String resourceId, Map<String, Object> reportParameters, Locale language, Object datasource) throws JRException{
		return generateReport("", resourceId, reportParameters, language, datasource);
	}
	
    /**
     * Processa um relatório utilizando uma instância de conexão com um repositório de dados.
     * 
     * @param resourceDir String contendo o diretório de armazenamento do arquivo de 
     * relatório desejado.
     * @param resourceId String contendo o identificador do arquivo de relatório desejado.
     * @param reportParameters Mapa contendo os parâmetros utilizados no relatório.
     * @param language Instância contendo o idioma a ser utilizado no processamento.
     * @param datasource Instância contendo o acesso aos dados do relatório.
     * @return Array de bytes contendo o relatório processado.
     * @throws JRException
     */
	public static <M extends BaseModel> byte[] generateReport(String resourceDir, String resourceId, Map<String, Object> reportParameters, Locale language, Object datasource) throws JRException{
		String      reportFileId  = buildReportFileId(resourceDir, resourceId);
		InputStream reportStream  = null;
		
		try{
    		if(resourceDir.length() > 0){
    			try{
    				reportStream = new FileInputStream(reportFileId);
    			}
    			catch(Throwable e){
    				throw new JRException(e);
    			}
    		}
    		else
    			reportStream = ReportUtil.class.getClassLoader().getResourceAsStream(reportFileId);
    
    		if(reportStream == null)
    			return null;
    
    		reportParameters = prepareReportParameters(resourceId, reportFileId, reportParameters, language);
    		
    		JasperPrint jasperPrint = null;
    		
    		if(datasource instanceof Connection)
    		    jasperPrint = JasperFillManager.fillReport(reportStream, reportParameters, (Connection)datasource);
    		else
    		    jasperPrint = JasperFillManager.fillReport(reportStream, reportParameters, new ReportDataSource((Collection)datasource));
    		
    		JRExporter            exporter     = null;
    		ContentType       exportType   = (ContentType)reportParameters.get(ReportConstants.REPORT_EXPORT_TYPE_KEY);
    		ByteArrayOutputStream exportStream = new ByteArrayOutputStream();
    		
    		switch(exportType){
    			case HTML: {
    				exporter = new JRHtmlExporter();
    				exporter.setParameter(JRHtmlExporterParameter.IS_WHITE_PAGE_BACKGROUND, true);
    
    				break;
    			}
    			case TEXT: {
    				exporter = new JRTextExporter();
    				exporter.setParameter(JRTextExporterParameter.PAGE_WIDTH, reportParameters.get(ReportConstants.REPORT_TEXT_PAGE_WIDTH_KEY));
    				exporter.setParameter(JRTextExporterParameter.PAGE_HEIGHT, reportParameters.get(ReportConstants.REPORT_TEXT_PAGE_HEIGHT_KEY));
    
    				break;
    			}
    			case EXCEL: {
    				exporter = new JRXlsExporter();
    				exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, true);
    
    				break;
    			}
    			case RICH_TEXT: {
    				exporter = new JRRtfExporter();
    
    				break;
    			}
    			case XML: {
    				exporter = new JRXmlExporter();
    
    				break;
    			}
    			default: {
                    exporter = new JRPdfExporter();
    
                    break;
    			}
    		}
    
    		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
    		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, exportStream);
    		exporter.exportReport();

            return exportStream.toByteArray();
		}
		finally{
            try{
                if(reportStream != null)
                    reportStream.close();
            }
            catch(Throwable e){
            }
		}
	}
	
	/**
	 * Retorna o caminho/nome do arquivo de relatório desejado.
	 * 
	 * @param resourceDir String contendo o diretório de armazenamento do relatório.
	 * @param resourceId String contendo o identificador do relatório.
	 * @return String contendo o caminho/nome do arquivo de relatório.
	 */
	private static String buildReportFileId(String resourceDir, String resourceId){
		return buildReportFileId(resourceDir, resourceId, true);
	}
	
    /**
     * Retorna o caminho/nome do arquivo de relatório desejado.
     * 
     * @param resourceId String contendo o identificador do relatório.
     * @return String contendo o caminho/nome do arquivo de relatório.
     */
	private static String buildReportFileId(String resourceDir, String resourceId, Boolean compiled){
		StringBuilder resourceIdBuffer = new StringBuilder();
		
		if(resourceDir.length() == 0)
			resourceIdBuffer.append(ResourceConstants.DEFAULT_REPORTS_DIR);
		else
			resourceIdBuffer.append(resourceDir);
		
		resourceIdBuffer.append(resourceId);
		
		if(compiled)
			resourceIdBuffer.append(".jasper");
		else
			resourceIdBuffer.append(".jrxml");
		
		return resourceIdBuffer.toString();
	}

	/**
	 * Define os parâmetros do relatório.
	 *
	 * @param resourceId String contendo o identificador do relatório.
	 * @param reportParameters Mapa contendo os parâmetros do relatório.
	 * @param language Instância contendo as propriedades do idioma desejado.
	 * @return Mapa contendo os parâmetros do relatório.
	 */
	private static Map<String, Object> prepareReportParameters(String resourceId, String reportFileId, Map<String, Object> reportParameters, Locale language){
		if(reportParameters == null)
			reportParameters = new LinkedHashMap<String, Object>();

		reportParameters.put(JRParameter.REPORT_LOCALE, language);

		if(reportParameters.get(JRParameter.REPORT_RESOURCE_BUNDLE) == null){
			PropertiesResource resources = null;

			try{
				resources = new I18nResourceLoader(ResourceConstants.DEFAULT_REPORTS_I18N_DIR, resourceId, language).getContent();

				reportParameters.put(JRParameter.REPORT_RESOURCE_BUNDLE, resources.getBundle());
			}
			catch(Throwable e){
			}
		}

		URL url = ReportUtil.class.getClassLoader().getResource(reportFileId);
		
		if(url != null){
			String  subReportDir = url.getFile().substring(1);
			Integer pos          = subReportDir.lastIndexOf("/");
			
			if(pos >= 0){
				subReportDir = subReportDir.substring(0, pos + 1);
				
				reportParameters.put(ReportConstants.DEFAULT_SUB_REPORT_DIR_KEY, subReportDir);
			}
		}

		ContentType exportType = (ContentType)reportParameters.get(ReportConstants.REPORT_EXPORT_TYPE_KEY);
		Integer         pageWidth  = 0;
		Integer         pageHeight = 0;

		if(exportType == null)
			reportParameters.put(ReportConstants.REPORT_EXPORT_TYPE_KEY, ReportConstants.DEFAULT_REPORT_EXPORT_TYPE);
		else{
			if(exportType == ContentType.TEXT){
				pageWidth = (Integer)reportParameters.get(ReportConstants.REPORT_TEXT_PAGE_WIDTH_KEY);
				if(pageWidth == null)
					pageWidth = ReportConstants.DEFAULT_REPORT_TEXT_PAGE_WIDTH;

				pageHeight = (Integer)reportParameters.get(ReportConstants.REPORT_TEXT_PAGE_HEIGHT_KEY);
				if(pageHeight == null)
					pageHeight = ReportConstants.DEFAULT_REPORT_TEXT_PAGE_HEIGHT;

				reportParameters.put(ReportConstants.REPORT_TEXT_PAGE_WIDTH_KEY, pageWidth);
				reportParameters.put(ReportConstants.REPORT_TEXT_PAGE_HEIGHT_KEY, pageHeight);
			}
		}

		return reportParameters;
	}
	
	/**
	 * Efetuar o merge do conteúdo de 2 ou mais relatórios.
	 * 
	 * @param reportsContents Lista de conteúdos dos relatórios.
	 * @return Array de bytes com o conteúdo final.
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static byte[] mergeReportContent(Collection<byte[]> reportsContents) throws IOException, DocumentException{
		return mergeReportContent(reportsContents, false);
	}
	
	/**
	 * Efetuar o merge do conteúdo de 2 ou mais relatórios.
	 * 
	 * @param reportsContents Lista de conteúdos dos relatórios.
	 * @param paginate Indica se deve ser gerado uma paginação única.
	 * @return Array de bytes com o conteúdo final.
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static byte[] mergeReportContent(Collection<byte[]> reportsContents, Boolean paginate) throws IOException, DocumentException{
    	ByteArrayOutputStream out        = new ByteArrayOutputStream();
	    Document              document   = new Document();
    	Collection<PdfReader> readers    = new LinkedList<PdfReader>();
    	PdfReader             reader     = null;
    	Integer               totalPages = 0;
	    
    	for(byte[] reportContent : reportsContents){
    		reader = new PdfReader(reportContent);

    		readers.add(reader);
        
    		totalPages += reader.getNumberOfPages();
    	}

    	PdfWriter writer = PdfWriter.getInstance(document, out);

    	document.open();
    	
    	BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
    	
    	PdfContentByte  directContent       = writer.getDirectContent();
    	PdfImportedPage importedPage        = null;
        Integer         currentPageNumber   = 0;
        Integer         pageOfCurrentReader = 0;

        for(PdfReader item : readers){
        	while(pageOfCurrentReader < item.getNumberOfPages()){
        		document.newPage();
          
        		pageOfCurrentReader++;
        		currentPageNumber++;
          
        		importedPage = writer.getImportedPage(item, pageOfCurrentReader);

        		directContent.addTemplate(importedPage, 0, 0);

        		if(paginate){
        			directContent.beginText();
        			directContent.setFontAndSize(baseFont, 10);
        			directContent.showTextAligned(PdfContentByte.ALIGN_CENTER, currentPageNumber.toString().concat(" / ").concat(totalPages.toString()), 300, 5, 0);
        			directContent.endText();
        		}
        	}
        
        	pageOfCurrentReader = 0;
        }

        out.flush();

		if(document.isOpen())
			document.close();
      
		return out.toByteArray();
	}
}