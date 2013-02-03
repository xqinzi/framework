package br.com.concepting.framework.util.helpers;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

/**
 * Classe que define a estrutura básica para uma mensagem.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class Message implements Serializable{
	private Collection to           = null;
	private Collection cc           = null;
	private Collection bcc          = null;
	private Object     from         = null;
	private Date       sentDate     = null;
	private Date       receivedDate = null;
	private Object     content      = null;
	private String     subject      = "";

	/**
	 * Retorna o conteúdo da instância.
	 * 
	 * @return Instância do objeto que define o conteúdo.
	 */
    public <T> T getContent(){
		return (T)content;
	}

	/**
	 * Define o conteúdo da instância.
	 * 
	 * @param content Instância do objeto que define o conteúdo.
	 */
	public <T> void setContent(T content){
		this.content = content;
	}

	/**
	 * Retorna a data/horário de recebimento da mensagem.
	 * 
	 * @return Instância contendo a data/horário de recebimento.
	 */
	public Date getReceivedDate(){
		return receivedDate;
	}

	/**
	 * Define a data/horário de recebimento da mensagem.
	 * 
	 * @param receivedDate Instância contendo a data/horário de recebimento.
	 */
	public void setReceivedDate(Date receivedDate){
		this.receivedDate = receivedDate;
	}

	/**
	 * Retorna a data/horário de envio da mensagem.
	 * 
	 * @return Instância contendo a data/horário de envio.
	 */
	public Date getSentDate(){
		return sentDate;
	}

	/**
	 * Define a data/horário de envio da mensagem.
	 * 
	 * @param sentDate Instância contendo a data/horário de envio.
	 */
	public void setSentDate(Date sentDate){
		this.sentDate = sentDate;
	}

	/**
	 * Retorna o título da mensagem.
	 * 
	 * @return String contendo o título.
	 */
	public String getSubject(){
		return subject;
	}

	/**
	 * Define o título da mensagem.
	 * 
	 * @param subject String contendo o título.
	 */
	public void setSubject(String subject){
		this.subject = subject;
	}

	/**
	 * Retorna a instância contendo os dados do remetente da mensagem.
	 * 
	 * @return Instância contendo os dados do remetente.
	 */
    public <T> T getFrom(){
		return (T)from;
	}

	/**
	 * Define a instância contendo os dados do remetente da mensagem.
	 * 
	 * @param from Instância contendo os dados do remetente.
	 */
	public <T> void setFrom(T from){
		this.from = from;
	}

	/**
	 * Retorna a lista contendo os dados dos destinatários da mensagem.
	 * 
	 * @return Lista contendo os dados dos destinatários.
	 */
    public <T> Collection<T> getToRecipients(){
		return to;
	}

	/**
	 * Define a lista contendo os dados dos destinatários da mensagem.
	 * 
	 * @param to Lista contendo os dados dos destinatários.
	 */
	public <T> void setToRecipients(Collection<T> to){
		this.to = to;
	}

	/**
	 * Retorna a lista contendo os dados dos destinatários com cópia da mensagem.
	 * 
	 * @return Lista contendo os dados dos destinatários.
	 */
    public <T> Collection<T> getCcRecipients(){
		return cc;
	}

	/**
	 * Define a lista contendo os dados dos destinatários com cópia da mensagem.
	 * 
	 * @param cc Lista contendo os dados dos destinatários.
	 */
	public <T> void setCcRecipients(Collection<T> cc){
		this.cc = cc;
	}

	/**
	 * Retorna a lista contendo os dados dos destinatários com cópia oculta da mensagem.
	 * 
	 * @return Lista contendo os dados dos destinatários.
	 */
    public <T> Collection<T> getBccRecipients(){
		return bcc;
	}

	/**
	 * Define a lista contendo os dados dos destinatários com cópia oculta da mensagem.
	 * 
	 * @param bcc Lista contendo os dados dos destinatários.
	 */
	public <T> void setBccRecipients(Collection<T> bcc){
		this.bcc = bcc;
	}

	/**
	 * Adiciona um novo destinatário da mensagem.
	 * 
	 * @param recipient Instância contendo os dados do destinatário.
	 */
    public <T> void addToRecipient(T recipient){
		if(to == null)
			to = new LinkedList<T>();

		to.add(recipient);
	}

	/**
	 * Adiciona um novo destinatário com cópia da mensagem.
	 * 
	 * @param recipient Instância contendo os dados do destinatário.
	 */
    public <T> void addCcRecipient(T recipient){
		if(cc == null)
			cc = new LinkedList<T>();

		cc.add(recipient);
	}

	/**
	 * Adiciona um novo destinatário com cópia oculta da mensagem.
	 * 
	 * @param recipient Instância contendo os dados do destinatário.
	 */
    public <T> void addBccRecipient(T recipient){
		if(bcc == null)
			bcc = new LinkedList<T>();

		bcc.add(recipient);
	}
}