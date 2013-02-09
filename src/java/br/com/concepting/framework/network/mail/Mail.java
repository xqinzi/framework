package br.com.concepting.framework.network.mail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.net.ssl.SSLSocketFactory;

import br.com.concepting.framework.network.mail.helpers.MailMessage;
import br.com.concepting.framework.network.mail.resource.MailResource;
import br.com.concepting.framework.network.mail.types.MailStorageType;
import br.com.concepting.framework.network.mail.types.MailTransportType;
import br.com.concepting.framework.util.ByteUtil;
import br.com.concepting.framework.util.FileUtil;

/** 
 * Classe responsável pela envio/recebimento de mensagens de e-Mail.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class Mail{
	private Session          session      = null;
	private Transport        transport    = null;
	private Store            storage      = null;
	private MailResource     mailResource = null;
	private Collection<File> attachments  = null;

	/**
	 * Construtor - Inicializa o ambiente para envio/recebimento de mensagens de e-Mail a partir 
	 * de configurações específicas.
	 * 
	 * @param mailResource Instância contendo as configurações desejadas.
	 * @throws NoSuchProviderException
	 */
	public Mail(MailResource mailResource) throws NoSuchProviderException{
		super();

		this.mailResource = mailResource;

		initialize();
	}

	/**
	 * Inicializa ambiente para envio/recebimento de mensagens de e-Mail.
	 * 
	 * @throws NoSuchProviderException
	 */
	private void initialize() throws NoSuchProviderException{
		Properties properties   = new Properties();
		Boolean    transportSet = false;
		Boolean    storageSet   = false;

		if(mailResource.getTransport() == MailTransportType.SMTP){
			properties.setProperty("mail.transport.protocol", MailTransportType.SMTP.toString());
			properties.setProperty("mail.smtp.host", mailResource.getServerName());
			properties.setProperty("mail.smtp.localhost", mailResource.getServerName());
			properties.setProperty("mail.smtp.port", String.valueOf(mailResource.getTransportPort()));

			if(mailResource.getUser().length() > 0) 
				properties.setProperty("mail.smtp.auth", "true");
			
			if(mailResource.useTls())
				properties.put("mail.smtp.starttls.enable", "true");

			if(mailResource.useSsl()){
				properties.put("mail.smtp.socketFactory.class", SSLSocketFactory.class.getName());
				properties.put("mail.smtp.socketFactory.port", String.valueOf(mailResource.getTransportPort()));
			}
			
			transportSet = true;
		}

		if(mailResource.getStorage() == MailStorageType.POP3){
			properties.setProperty("mail.store.protocol", MailStorageType.POP3.toString());
			properties.setProperty("mail.pop3.host", mailResource.getServerName());
			properties.setProperty("mail.pop3.port", String.valueOf(mailResource.getStoragePort()));
			
			if(mailResource.useTls())
				properties.put("mail.pop3.starttls.enable", "true");

			if(mailResource.useSsl()){
				properties.put("mail.pop3.socketFactory.class", SSLSocketFactory.class.getName());
				properties.put("mail.pop3.socketFactory.port", String.valueOf(mailResource.getStoragePort()));
			}

			storageSet = true;
		}
		else if(mailResource.getStorage() == MailStorageType.IMAP){
			properties.setProperty("mail.store.protocol", MailStorageType.IMAP.toString());
			properties.setProperty("mail.imap.host", mailResource.getServerName());
			properties.setProperty("mail.imap.port", String.valueOf(mailResource.getStoragePort()));

			if(mailResource.useTls())
				properties.put("mail.imap.starttls.enable", "true");
			
			if(mailResource.useSsl()){
				properties.put("mail.imap.socketFactory.class", SSLSocketFactory.class.getName());
				properties.put("mail.imap.socketFactory.port", String.valueOf(mailResource.getStoragePort()));
			}

			storageSet = true;
		}
		
		if(mailResource.useSsl()){
			session = Session.getDefaultInstance(properties, new Authenticator(){
                protected PasswordAuthentication getPasswordAuthentication(){
	                return new PasswordAuthentication(mailResource.getUser(), mailResource.getPassword());
                }
			});
		}
		else
			session = Session.getDefaultInstance(properties, null);

		if(transportSet) 
			transport = session.getTransport();

		if(storageSet) 
			storage = session.getStore();
	}

	/**
	 * Carrega o cabeçalho de uma mensagem de e-Mail.
	 * 
	 * @param message Instância contendo os dados da mensagem de e-Mail.
	 * @param sendMessage Instância contendo a estrutura da mensagem de e-Mail.
	 * @throws AddressException
	 * @throws MessagingException
	 */
	private void buildHeader(MailMessage message, Message sendMessage) throws AddressException, MessagingException{
	    if(message.getFrom() instanceof String)
	        sendMessage.setFrom(new InternetAddress((String)message.getFrom()));
	    else
	        sendMessage.setFrom((Address)message.getFrom());

		if(message.getToRecipients() != null){
			for(Object item : message.getToRecipients()){
			    if(item instanceof String)
			        sendMessage.addRecipient(Message.RecipientType.TO, new InternetAddress((String)item));
			    else
			        sendMessage.addRecipient(Message.RecipientType.TO, (Address)item);
			}
		}
		
		if(message.getCcRecipients() != null){
			for(Object item : message.getCcRecipients()){
                if(item instanceof String)
                    sendMessage.addRecipient(Message.RecipientType.CC, new InternetAddress((String)item));
                else
                    sendMessage.addRecipient(Message.RecipientType.CC, (Address)item);
			}
		}

		if(message.getBccRecipients() != null){
			for(Object item : message.getBccRecipients()){
                if(item instanceof String)
                    sendMessage.addRecipient(Message.RecipientType.BCC, new InternetAddress((String)item));
                else
                    sendMessage.addRecipient(Message.RecipientType.BCC, (Address)item);
			}
		}

		sendMessage.setSubject(message.getSubject());
		sendMessage.setSentDate(message.getSentDate());
	}

	/**
	 * Carrega o corpo de uma mensagem de e-Mail.
	 * 
	 * @param message Instância contendo os dados da mensagem de e-Mail.
	 * @param sendMessage Instância contendo a estrutura da mensagem de e-Mail.
	 * @throws MessagingException
	 * @throws IOException
	 */
	private void buildBody(MailMessage message, Message sendMessage) throws MessagingException, IOException{
		MimeMultipart parts = new MimeMultipart();
		MimeBodyPart  part  = new MimeBodyPart();

		part.setContent(message.getContent(), message.getContentType().getMimeType());

		parts.addBodyPart(part);

		loadAttachments(message, sendMessage, parts);
	}

	/**
	 * Carrega os anexos de uma mensagem de e-Mail.
	 * 
	 * @param message Instância contendo os dados da mensagem de e-Mail.
	 * @param sendMessage Instância contendo a estrutura da mensagem de e-Mail.
	 * @param parts Instância contendo a estrutura dos anexos.
	 * @throws IOException
	 * @throws MessagingException
	 */
	private void loadAttachments(MailMessage message, Message sendMessage, MimeMultipart parts) throws IOException, MessagingException{
		attachments = new LinkedList<File>();

		if(message.getAttachments() != null && message.getAttachments().size() > 0){
			MimeBodyPart   attachPart   = null;
			byte           attachData[] = null;
			String         fileName     = null;
			Boolean        fileBinary   = false;
			Object         fileContent  = null;
			File           file         = null;
			FileDataSource handler      = null;

			for(Map<String, Object> attach : message.getAttachments()){
				attachPart  = new MimeBodyPart();
				fileName    = (String)attach.get("fileName");
				fileContent = attach.get("fileContent");
				fileBinary  = (Boolean)attach.get("fileBinary");

				if(!fileBinary) 
					attachData = fileContent.toString().getBytes();
				else 
					attachData = (byte[])fileContent;

				FileUtil.toBinaryFile(fileName, attachData);

				file = new File(fileName);
				if(file.exists()){
					handler = new FileDataSource(file);

					attachPart.setDataHandler(new DataHandler(handler));
					attachPart.setFileName(handler.getName());

					parts.addBodyPart(attachPart);

					attachments.add(file);
				}
			}
		}

		sendMessage.setContent(parts);
	}

	/**
	 * Limpa os anexos de uma mensagem.
	 */
	private void clearAttachments(){
		if(attachments != null)
			for(File file : attachments)
				file.delete();
	}

	/**
	 * Envia uma mensagem de e-Mail.
	 * 
	 * @param message Instância contendo os dados da mensagem.
	 * @throws AddressException
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void send(MailMessage message) throws AddressException, MessagingException, IOException{
		if(transport != null){
			Message sendMessage = new MimeMessage(session);

			try{
				buildHeader(message, sendMessage);
				buildBody(message, sendMessage);

				if(mailResource.getUser().length() > 0) 
					transport.connect(mailResource.getServerName(), mailResource.getUser(), mailResource.getPassword());
				else 
					transport.connect();

				transport.sendMessage(sendMessage, sendMessage.getAllRecipients());
			}
			finally{
				clearAttachments();

				try{
					transport.close();
				}
				catch(Throwable e1){}
			}
		}
	}

	/**
	 * Retorna uma lista de pastas da caixa de e-Mail.
	 * 
	 * @return Lista contendo as pastas encontradas.
	 * @throws MessagingException
	 */
	public Collection getFolders() throws MessagingException{
		Collection<Folder> folders = null;

		if(storage != null){
			Folder rootFolder = null;

			try{
				storage.connect(mailResource.getServerName(), mailResource.getUser(), mailResource.getPassword());

				rootFolder = storage.getDefaultFolder();
				folders    = Arrays.asList(rootFolder.list());
			}
			finally{
				try{
					storage.close();
				}
				catch(Throwable e1){}
			}
		}

		return folders;
	}

	/**
	 * Prepara uma mensagem de e-Mail para envio.
	 * 
	 * @param message Instância contendo a estrutura da mensagem de e-Mail.
	 * @return Instância contendo os dados da mensagem de e-Mail.
	 * @throws MessagingException
	 * @throws IOException
	 */
	private MailMessage buildMessage(Message message) throws MessagingException, IOException{
		MailMessage mailMessage = new MailMessage();

		mailMessage.setToRecipients(Arrays.asList(message.getRecipients(Message.RecipientType.TO)));
		
		try{
    		mailMessage.setCcRecipients(Arrays.asList(message.getRecipients(Message.RecipientType.CC)));
		}
		catch(Throwable e){
		}
		
		try{
			mailMessage.setBccRecipients(Arrays.asList(message.getRecipients(Message.RecipientType.BCC)));
		}
		catch(Throwable e){
		}
		
		mailMessage.setSubject(message.getSubject());
		mailMessage.setFrom(message.getFrom()[0]);
		mailMessage.setSentDate(message.getSentDate());
		mailMessage.setReceivedDate(message.getReceivedDate());
		
		if(mailMessage.getReceivedDate() == null)
			mailMessage.setReceivedDate(new Date());
		
		mailMessage.setContentType(message.getContentType());
		mailMessage.setContent(buildContent(message, mailMessage));

		return mailMessage;
	}

	/**
	 * Retorna uma mensagem de e-Mail específica.
	 * 
	 * @param folderName String contendo o identificador da pasta onde a mensagem está armazenada.
	 * @param messageNumber Valor inteiro contendo o identificador da mensagem desejada.
	 * @return Instância contendo os dados da mensagem de e-Mail.
	 * @throws MessagingException
	 * @throws IOException
	 */
	public MailMessage retrieve(String folderName, Integer messageNumber) throws MessagingException, IOException{
		Folder  folder  = null;
		Message message = null;

		try{
			if(!storage.isConnected()) 
				storage.connect(mailResource.getServerName(), mailResource.getUser(), mailResource.getPassword());

			folder = storage.getFolder(folderName);
			folder.open(Folder.READ_ONLY);

			message = folder.getMessage(messageNumber);
			
			return buildMessage(message);
		}
		finally{
			try{
				storage.close();
			}
			catch(Throwable e){}
		}
	}

	/**
	 * Retorna todas as mensagens de e-Mail.
	 * 
	 * @param folderName String contendo o identificador da pasta onde a mensagem está armazenada.
	 * @return Instância contendo os dados da mensagem de e-Mail.
	 * @throws MessagingException
	 * @throws IOException
	 */
	public Collection<MailMessage> retrieve(String folderName) throws MessagingException, IOException{
		Folder                  folder       = null;
		Message                 messages[]   = null;
		Collection<MailMessage> mailMessages = new LinkedList<MailMessage>();
		MailMessage             mailMessage  = null;

		try{
			storage.connect(mailResource.getServerName(), mailResource.getUser(), mailResource.getPassword());

			folder = storage.getFolder(folderName);
			folder.open(Folder.READ_ONLY);

			messages = folder.getMessages();

			for(Message message : messages){
				mailMessage = buildMessage(message);

				mailMessages.add(mailMessage);
			}

			return mailMessages;
		}
		finally{
			try{
				storage.close();
			}
			catch(Throwable e){}
		}
	}

	/**
	 * Carrega um conteúdo de uma mensagem de e-Mail.
	 * 
	 * @param part Instância do conteúdo desejado.
	 * @param mailMessage Instância contendo os dados da mensagem de e-Mail.
	 * @return Instância do conteúdo carregado.
	 * @throws MessagingException
	 * @throws IOException
	 */
	private Object buildContent(Object part, MailMessage mailMessage) throws MessagingException, IOException{
		if(part instanceof Message){
			Message message = (Message)part;

			if(message.getContent() instanceof Multipart) 
				return buildContent(message.getContent(), mailMessage);

			return message.getContent();
		}
		else if(part instanceof Multipart){
			Multipart   parts   = (Multipart)part;
			Part        subPart = null;
			InputStream attach  = null;
			Object      buffer  = null;

			for(Integer cont = 0 ; cont < parts.getCount() ; cont++){
				subPart = parts.getBodyPart(cont);
				attach  = subPart.getDataHandler().getDataSource().getInputStream();

				if(attach instanceof InputStream || (subPart.getDisposition() != null && subPart.getDisposition().equals(Part.ATTACHMENT))) 
					mailMessage.attach(subPart.getFileName(), ByteUtil.fromBinaryStream(attach));
				else{
					if(subPart.getContent() instanceof Multipart) 
						buffer = buildContent(subPart.getContent(), mailMessage);
					else if(buffer == null) 
						buffer = subPart.getContent();
				}
			}

			return buffer;
		}
		else 
			return part.toString();
	}
}