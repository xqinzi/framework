package br.com.concepting.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.DocumentType;
import org.dom4j.io.SAXReader;

import br.com.concepting.framework.util.constants.XmlConstants;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Classe utilitária responsável por efetuar a leitura/parsing de um conteúdo XML. 
 *
 * @author fvilarinho
 * @since 1.0
 */
public class XmlReader{
	private XmlNode      root         = null;
	private DocumentType documentType = null;
	private String       encoding     = "";

	/**
	 * Construtor - Efetua a leitura/parsing de um arquivo XML sem efetuar a sua validação.
	 *
	 * @param file Instância contendo as informações do arquivo XML.
	 * @throws IOException
	 */
	public XmlReader(File file) throws IOException{
		this(file, false);
	}

	/**
	 * Construtor - Efetua a leitura/parsing de um arquivo XML.
	 *
	 * @param file Instância contendo as informações do arquivo XML.
	 * @param validate True/False.
	 * @throws IOException
	 */
	public XmlReader(File file, Boolean validate) throws IOException{
		this(new FileInputStream(file), validate);
	}

	/**
	 * Construtor - Efetua a leitura/parsing de um stream de dados XML sem efetuar a sua 
	 * validação.
	 *
	 * @param stream Instância do stream contendo os dados XML.
	 * @throws IOException
	 */
	public XmlReader(InputStream stream) throws IOException{
		this(stream, false);
	}

	/**
	 * Construtor - Efetua a leitura/parsing de um stream de dados XML.
	 *
	 * @param stream Instância do stream contendo os dados XML.
	 * @param validate True/False.
	 * @throws IOException
	 */
	public XmlReader(InputStream stream, Boolean validate) throws IOException{
		super();

		try{
			SAXReader reader = new SAXReader(validate);

			try{
				reader.setFeature(XmlConstants.NON_DTD_VALIDATION_FEATURE_ID, validate);
				reader.setFeature(XmlConstants.NON_VALIDATION_FEATURE_ID, validate);
			}
			catch(Throwable e){
			}

			parseDocument(reader.read(stream));
		}
		catch(Throwable e){
			throw new IOException(ExceptionUtil.getTrace(e));
		}
	}

	/**
	 * Construtor - Efetua o parsing de um conteúdo XML armazenado em uma string. 
	 *
	 * @param value String do conteúdo XML.
	 * @throws DocumentException
	 */
	public XmlReader(String value) throws DocumentException{
		parseDocument(DocumentHelper.parseText(value));
	}

	/**
	 * Efetua o parsing do conteúdo XML armazenado em uma instância do documento DOM.
	 *
	 * @param document Instância do conteúdo.
	 */
	private void parseDocument(Document document){
		setDocumentType(document.getDocType());
		setEncoding(document.getXMLEncoding());
		setRoot(new XmlNode(document.getRootElement(), 0, 0));
	}

	/**
	 * Retorna a instância contendo as propriedades da tag raiz. 
	 *
	 * @return Instância contendo as propriedades da tag raiz.
	 */
	public XmlNode getRoot(){
		return root;
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

	/**
	 * Retorna a instância contendo as propriedades do nó raiz do conteúdo XML.
	 *
	 * @param root Instância contendo as propriedades do nó raiz.
	 */
	public void setRoot(XmlNode root){
     	this.root = root;
    }
}