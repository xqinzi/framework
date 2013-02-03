package br.com.concepting.framework.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.DocumentType;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import br.com.concepting.framework.util.constants.Constants;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Classe utilitária responsável por efetuar a gravação de um conteúdo XML. 
 *
 * @author fvilarinho
 * @since 1.0
 */
public class XmlWriter{
	private OutputStream out          = null;
	private DocumentType documentType = null;
	private String       encoding     = "";

	/**
	 * Construtor - Define o arquivo a ser criado para gravação do conteúdo XML.
	 *
	 * @param file Instância contendo as propriedades do arquivo.
	 * @throws IOException
	 */
	public XmlWriter(File file) throws IOException{
		this(file, null, "");
	}

	/**
	 * Construtor - Define o arquivo a ser criado para gravação do conteúdo XML.
	 *
	 * @param file Instância contendo as propriedades do arquivo.
	 * @param documentType Instância contendo as propriedades de DTD.
	 * @throws IOException
	 */
	public XmlWriter(File file, DocumentType documentType) throws IOException{
		this(file, documentType, "");
	}

	/**
	 * Construtor - Define o arquivo a ser criado para gravação do conteúdo XML.
	 *
	 * @param file Instância contendo as propriedades do arquivo.
	 * @param documentType Instância contendo as propriedades de DTD.
	 * @param encoding String contendo a codificação.
	 * @throws IOException
	 */
	public XmlWriter(File file, DocumentType documentType, String encoding) throws IOException{
		super();

		String  fileName = file.getAbsolutePath();
		Integer pos      = fileName.lastIndexOf(StringUtil.getDirectorySeparator());
		String  fileDir  = "";

		if(pos >= 0)
			fileDir = fileName.substring(0, pos);
		else{
			pos = fileName.lastIndexOf("\\");
			if(pos >= 0)
				fileDir = fileName.substring(0, pos);
			else{
				pos = fileName.lastIndexOf("/");
				if(pos >= 0)
					fileDir = fileName.substring(0, pos);
			}
		}

		if(fileDir.length() > 0){
			File fileBuffer = new File(fileDir);

			if(!fileBuffer.exists())
				fileBuffer.mkdirs();
		}

		setOut(new FileOutputStream(file));
		setDocumentType(documentType);
		setEncoding(encoding);
	}

	/**
	 * Construtor - Define o stream a ser utilizado para gravação do conteúdo XML.
	 *
	 * @param out Instância contendo o stream de output.
	 * @param documentType Instância contendo as propriedades de DTD.
	 * @param encoding String contendo a codificação.
	 * @throws IOException
	 */
	public XmlWriter(OutputStream out, DocumentType documentType, String encoding){
		super();

		setOut(out);
		setDocumentType(documentType);
		setEncoding(encoding);
	}

	/**
	 * Grava em um stream/arquivo um conteúdo XML.
	 *
	 * @param value String do conteúdo XML;
	 * @throws IOException
	 * @throws DocumentException
	 */
	public void write(String value) throws IOException, DocumentException{
		OutputFormat format = OutputFormat.createPrettyPrint();

		format.setIndent(Constants.DEFAULT_INDENT_CHARACTER);
		format.setIndentSize(Constants.DEFAULT_INDENT_SIZE);

		write(value, format);
	}

	/**
	 * Grava em um stream/arquivo um conteúdo XML.
	 *
	 * @param value String do conteúdo XML;
	 * @param format Instância contendo as propriedade de formatação do conteúdo.
	 * @throws IOException
	 * @throws DocumentException
	 */
	public void write(String value, OutputFormat format) throws DocumentException, IOException{
		Document  document = DocumentHelper.parseText(value);
		XMLWriter writer   = new XMLWriter(out, format);

		if(documentType != null)
			document.setDocType(documentType);

		if(encoding.length() > 0)
			format.setEncoding(encoding);

		writer.write(document);
		writer.close();
	}

	/**
	 * Grava em um stream/arquivo o conteúdo do nó.
	 *
	 * @param node Instância contendo as propriedades do nó.
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void write(XmlNode node) throws DocumentException, IOException{
		write(XmlUtil.toString(node));
	}

	/**
	 * Grava em um stream/arquivo o conteúdo do nó.
	 *
	 * @param node Instância contendo as propriedades do nó.
	 * @param format Insância contendo as propriedades de formatação.
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void write(XmlNode node, OutputFormat format) throws DocumentException, IOException{
		write(XmlUtil.toString(node), format);
	}

	/**
	 * Retorna o stream de output dos dados. 
	 *
	 * @return Instância contendo o stream de output.
	 */
	public OutputStream getOut(){
     	return out;
    }

	/**
	 * Define o stream de output dos dados. 
	 *
	 * @param out Instância contendo o stream de output.
	 */
	public void setOut(OutputStream out){
     	this.out = out;
    }

	/**
	 * Retorna as propriedades de DTD do conteúdo XML.
	 *
	 * @return Instância contendo as propriedades de DTD.
	 */
	public DocumentType getDocumentType(){
     	return documentType;
    }

	/**
	 * Define as propriedades de DTD do conteúdo XML.
	 *
	 * @param documentType Instância contendo as propriedades de DTD.
	 */
	public void setDocumentType(DocumentType documentType){
     	this.documentType = documentType;
    }

	/**
	 * Retorna a codificação do conteúdo XML.
	 *
	 * @return String contendo a codificação.
	 */
	public String getEncoding(){
     	return encoding;
    }

	/**
	 * Define a codificação do conteúdo XML.
	 *
	 * @param encoding String contendo a codificação.
	 */
	public void setEncoding(String encoding){
     	this.encoding = encoding;
    }
}