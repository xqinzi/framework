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
 * Classe utilit�ria respons�vel por efetuar a grava��o de um conte�do XML. 
 *
 * @author fvilarinho
 * @since 1.0
 */
public class XmlWriter{
	private OutputStream out          = null;
	private DocumentType documentType = null;
	private String       encoding     = "";

	/**
	 * Construtor - Define o arquivo a ser criado para grava��o do conte�do XML.
	 *
	 * @param file Inst�ncia contendo as propriedades do arquivo.
	 * @throws IOException
	 */
	public XmlWriter(File file) throws IOException{
		this(file, null, "");
	}

	/**
	 * Construtor - Define o arquivo a ser criado para grava��o do conte�do XML.
	 *
	 * @param file Inst�ncia contendo as propriedades do arquivo.
	 * @param documentType Inst�ncia contendo as propriedades de DTD.
	 * @throws IOException
	 */
	public XmlWriter(File file, DocumentType documentType) throws IOException{
		this(file, documentType, "");
	}

	/**
	 * Construtor - Define o arquivo a ser criado para grava��o do conte�do XML.
	 *
	 * @param file Inst�ncia contendo as propriedades do arquivo.
	 * @param documentType Inst�ncia contendo as propriedades de DTD.
	 * @param encoding String contendo a codifica��o.
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
	 * Construtor - Define o stream a ser utilizado para grava��o do conte�do XML.
	 *
	 * @param out Inst�ncia contendo o stream de output.
	 * @param documentType Inst�ncia contendo as propriedades de DTD.
	 * @param encoding String contendo a codifica��o.
	 * @throws IOException
	 */
	public XmlWriter(OutputStream out, DocumentType documentType, String encoding){
		super();

		setOut(out);
		setDocumentType(documentType);
		setEncoding(encoding);
	}

	/**
	 * Grava em um stream/arquivo um conte�do XML.
	 *
	 * @param value String do conte�do XML;
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
	 * Grava em um stream/arquivo um conte�do XML.
	 *
	 * @param value String do conte�do XML;
	 * @param format Inst�ncia contendo as propriedade de formata��o do conte�do.
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
	 * Grava em um stream/arquivo o conte�do do n�.
	 *
	 * @param node Inst�ncia contendo as propriedades do n�.
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void write(XmlNode node) throws DocumentException, IOException{
		write(XmlUtil.toString(node));
	}

	/**
	 * Grava em um stream/arquivo o conte�do do n�.
	 *
	 * @param node Inst�ncia contendo as propriedades do n�.
	 * @param format Ins�ncia contendo as propriedades de formata��o.
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void write(XmlNode node, OutputFormat format) throws DocumentException, IOException{
		write(XmlUtil.toString(node), format);
	}

	/**
	 * Retorna o stream de output dos dados. 
	 *
	 * @return Inst�ncia contendo o stream de output.
	 */
	public OutputStream getOut(){
     	return out;
    }

	/**
	 * Define o stream de output dos dados. 
	 *
	 * @param out Inst�ncia contendo o stream de output.
	 */
	public void setOut(OutputStream out){
     	this.out = out;
    }

	/**
	 * Retorna as propriedades de DTD do conte�do XML.
	 *
	 * @return Inst�ncia contendo as propriedades de DTD.
	 */
	public DocumentType getDocumentType(){
     	return documentType;
    }

	/**
	 * Define as propriedades de DTD do conte�do XML.
	 *
	 * @param documentType Inst�ncia contendo as propriedades de DTD.
	 */
	public void setDocumentType(DocumentType documentType){
     	this.documentType = documentType;
    }

	/**
	 * Retorna a codifica��o do conte�do XML.
	 *
	 * @return String contendo a codifica��o.
	 */
	public String getEncoding(){
     	return encoding;
    }

	/**
	 * Define a codifica��o do conte�do XML.
	 *
	 * @param encoding String contendo a codifica��o.
	 */
	public void setEncoding(String encoding){
     	this.encoding = encoding;
    }
}