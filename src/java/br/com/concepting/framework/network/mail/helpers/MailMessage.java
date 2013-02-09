package br.com.concepting.framework.network.mail.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.mail.Address;

import br.com.concepting.framework.util.ByteUtil;
import br.com.concepting.framework.util.helpers.Message;
import br.com.concepting.framework.web.types.ContentType;

/** 
 * Classe que define a estrutura básica para uma mensagem de e-Mail.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class MailMessage extends Message{
	private ContentType                     mimeType    = ContentType.HTML;
	private Collection<Map<String, Object>> attachments = null;

	/**
	 * Retorna o tipo do conteúdo da mensagem.
	 * 
	 * @return Constante que define o tipo do conteúdo.
	 */
	public ContentType getMimeType(){
		return mimeType;
	}

	/**
	 * Define o tipo do conteúdo da mensagem.
	 * 
	 * @param mimeType Constante que define o tipo do conteúdo.
	 */
	public void setMimeType(ContentType mimeType){
		this.mimeType = mimeType;
	}
	
	/**
	 * Define o tipo do conteúdo da mensagem.
	 * 
	 * @param mimeType String que define o tipo do conteúdo.
	 */
	public void setMimeType(String mimeType){
		if(mimeType.length() > 0)
			this.mimeType = ContentType.valueOf(mimeType.toUpperCase());
		else
			this.mimeType = ContentType.HTML;
	}

	/**
	 * Anexa um arquivo à mensagem.
	 * 
	 * @param fileName String contendo o caminho do arquivo desejado.
	 * @throws IOException
	 */
	public void attach(String fileName) throws IOException{
		attach(fileName, new FileInputStream(fileName));
	}

	/**
	 * Anexa um arquivo à mensagem a partir do stream de dados.
	 * 
	 * @param fileName String contendo o caminho do arquivo desejado.
	 * @param stream Instância contendo os dados.
	 * @throws IOException
	 */
	public void attach(String fileName, InputStream stream) throws IOException{
		MimetypesFileTypeMap mimeTypes = new MimetypesFileTypeMap();
		String               mimeType  = mimeTypes.getContentType(new File(fileName));

		if(mimeType.indexOf("plain") < 0)
			attach(fileName, ByteUtil.fromBinaryStream(stream));
		else
			attach(fileName, ByteUtil.fromTextStream(stream));
	}

	/**
	 * Anexa um arquivo à mensagem a partir de um array de bytes.
	 * 
	 * @param fileName String contendo o caminho do arquivo desejado.
	 * @param content Array de bytes do conteúdo.
	 * @throws IOException
	 */
	public void attach(String fileName, byte content[]){
		Map<String, Object> attach = new LinkedHashMap<String, Object>();
		
		if(fileName != null){
    		File temp = new File(fileName);
    
    		attach.put("fileName", temp.getName());
		}
		
		attach.put("fileContent", content);
		attach.put("fileBinary", true);

		if(attachments == null)
			attachments = new LinkedList<Map<String, Object>>();

		attachments.add(attach);
	}

	/**
	 * Anexa um arquivo à mensagem a partir de uma conteúdo em string.
	 * 
	 * @param fileName String contendo o caminho do arquivo desejado.
	 * @param content String do conteúdo desejado.
	 * @throws IOException
	 */
	public void attach(String fileName, String content){
		Map<String, Object> attach = new LinkedHashMap<String, Object>();
		File                temp   = new File(fileName);

		attach.put("fileName", temp.getName());
		attach.put("fileContent", content);
		attach.put("fileBinary", false);

		if(attachments == null)
			attachments = new LinkedList<Map<String, Object>>();

		attachments.add(attach);
	}

	/**
	 * Retorna a lista de anexos da mensagem.
	 * 
	 * @return Lista contendo os anexos da mensagem.
	 */
	public Collection<Map<String, Object>> getAttachments(){
		return attachments;
	}

	/**
	 * Adiciona um novo destinatário da mensagem.
	 * 
	 * @param recipient Instância contendo os dados do destinatário.
	 */
	public void addToRecipient(Address recipient){
		super.addToRecipient(recipient);
	}

	/**
	 * Adiciona um novo destinatário com cópia da mensagem.
	 * 
	 * @param recipient Instância contendo os dados do destinatário.
	 */
	public void addCcRecipient(Address recipient){
		super.addCcRecipient(recipient);
	}

	/**
	 * Adiciona um novo destinatário com cópia oculta da mensagem.
	 * 
	 * @param recipient Instância contendo os dados do destinatário.
	 */
	public void addBccRecipient(Address recipient){
		super.addBccRecipient(recipient);
	}
}