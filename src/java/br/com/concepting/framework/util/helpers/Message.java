package br.com.concepting.framework.util.helpers;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

/**
 * Classe que define a estrutura b�sica para uma mensagem.
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
	 * Retorna o conte�do da inst�ncia.
	 * 
	 * @return Inst�ncia do objeto que define o conte�do.
	 */
    public <T> T getContent(){
		return (T)content;
	}

	/**
	 * Define o conte�do da inst�ncia.
	 * 
	 * @param content Inst�ncia do objeto que define o conte�do.
	 */
	public <T> void setContent(T content){
		this.content = content;
	}

	/**
	 * Retorna a data/hor�rio de recebimento da mensagem.
	 * 
	 * @return Inst�ncia contendo a data/hor�rio de recebimento.
	 */
	public Date getReceivedDate(){
		return receivedDate;
	}

	/**
	 * Define a data/hor�rio de recebimento da mensagem.
	 * 
	 * @param receivedDate Inst�ncia contendo a data/hor�rio de recebimento.
	 */
	public void setReceivedDate(Date receivedDate){
		this.receivedDate = receivedDate;
	}

	/**
	 * Retorna a data/hor�rio de envio da mensagem.
	 * 
	 * @return Inst�ncia contendo a data/hor�rio de envio.
	 */
	public Date getSentDate(){
		return sentDate;
	}

	/**
	 * Define a data/hor�rio de envio da mensagem.
	 * 
	 * @param sentDate Inst�ncia contendo a data/hor�rio de envio.
	 */
	public void setSentDate(Date sentDate){
		this.sentDate = sentDate;
	}

	/**
	 * Retorna o t�tulo da mensagem.
	 * 
	 * @return String contendo o t�tulo.
	 */
	public String getSubject(){
		return subject;
	}

	/**
	 * Define o t�tulo da mensagem.
	 * 
	 * @param subject String contendo o t�tulo.
	 */
	public void setSubject(String subject){
		this.subject = subject;
	}

	/**
	 * Retorna a inst�ncia contendo os dados do remetente da mensagem.
	 * 
	 * @return Inst�ncia contendo os dados do remetente.
	 */
    public <T> T getFrom(){
		return (T)from;
	}

	/**
	 * Define a inst�ncia contendo os dados do remetente da mensagem.
	 * 
	 * @param from Inst�ncia contendo os dados do remetente.
	 */
	public <T> void setFrom(T from){
		this.from = from;
	}

	/**
	 * Retorna a lista contendo os dados dos destinat�rios da mensagem.
	 * 
	 * @return Lista contendo os dados dos destinat�rios.
	 */
    public <T> Collection<T> getToRecipients(){
		return to;
	}

	/**
	 * Define a lista contendo os dados dos destinat�rios da mensagem.
	 * 
	 * @param to Lista contendo os dados dos destinat�rios.
	 */
	public <T> void setToRecipients(Collection<T> to){
		this.to = to;
	}

	/**
	 * Retorna a lista contendo os dados dos destinat�rios com c�pia da mensagem.
	 * 
	 * @return Lista contendo os dados dos destinat�rios.
	 */
    public <T> Collection<T> getCcRecipients(){
		return cc;
	}

	/**
	 * Define a lista contendo os dados dos destinat�rios com c�pia da mensagem.
	 * 
	 * @param cc Lista contendo os dados dos destinat�rios.
	 */
	public <T> void setCcRecipients(Collection<T> cc){
		this.cc = cc;
	}

	/**
	 * Retorna a lista contendo os dados dos destinat�rios com c�pia oculta da mensagem.
	 * 
	 * @return Lista contendo os dados dos destinat�rios.
	 */
    public <T> Collection<T> getBccRecipients(){
		return bcc;
	}

	/**
	 * Define a lista contendo os dados dos destinat�rios com c�pia oculta da mensagem.
	 * 
	 * @param bcc Lista contendo os dados dos destinat�rios.
	 */
	public <T> void setBccRecipients(Collection<T> bcc){
		this.bcc = bcc;
	}

	/**
	 * Adiciona um novo destinat�rio da mensagem.
	 * 
	 * @param recipient Inst�ncia contendo os dados do destinat�rio.
	 */
    public <T> void addToRecipient(T recipient){
		if(to == null)
			to = new LinkedList<T>();

		to.add(recipient);
	}

	/**
	 * Adiciona um novo destinat�rio com c�pia da mensagem.
	 * 
	 * @param recipient Inst�ncia contendo os dados do destinat�rio.
	 */
    public <T> void addCcRecipient(T recipient){
		if(cc == null)
			cc = new LinkedList<T>();

		cc.add(recipient);
	}

	/**
	 * Adiciona um novo destinat�rio com c�pia oculta da mensagem.
	 * 
	 * @param recipient Inst�ncia contendo os dados do destinat�rio.
	 */
    public <T> void addBccRecipient(T recipient){
		if(bcc == null)
			bcc = new LinkedList<T>();

		bcc.add(recipient);
	}
}