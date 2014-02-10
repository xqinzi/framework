package br.com.concepting.framework.network.mail.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import br.com.concepting.framework.util.ByteUtil;
import br.com.concepting.framework.util.types.ContentType;

/**
 * Classe que define a estrutura b�sica para uma mensagem de e-Mail.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class MailMessage implements Serializable{
    private static final long serialVersionUID = 6336269811220022270L;
    
    private Collection<Address>             to           = null;
	private Collection<Address>             cc           = null;
	private Collection<Address>             bcc          = null;
	private Address                         from         = null;
	private Date                            sentDate     = null;
	private Date                            receivedDate = null;
	private Object                          content      = null;
	private String                          subject      = "";
    private ContentType                     contentType  = ContentType.HTML;
    private Collection<Map<String, Object>> attachments  = null;

    /**
     * Retorna o tipo do conte�do da mensagem.
     * 
     * @return Constante que define o tipo do conte�do.
     */
    public ContentType getContentType(){
        return contentType;
    }

    /**
     * Define o tipo do conte�do da mensagem.
     * 
     * @param contentType Constante que define o tipo do conte�do.
     */
    public void setContentType(ContentType contentType){
        this.contentType = contentType;
    }
    
    /**
     * Define o tipo do conte�do da mensagem.
     * 
     * @param contentType String que define o tipo do conte�do.
     */
    public void setContentType(String contentType){
        if(contentType.length() > 0)
            this.contentType = ContentType.toContentType(contentType);
        else
            this.contentType = ContentType.HTML;
    }

    /**
     * Anexa um arquivo � mensagem.
     * 
     * @param fileName String contendo o caminho do arquivo desejado.
     * @throws IOException
     */
    public void attach(String fileName) throws IOException{
        attach(fileName, new FileInputStream(fileName));
    }

    /**
     * Anexa um arquivo � mensagem a partir do stream de dados.
     * 
     * @param fileName String contendo o caminho do arquivo desejado.
     * @param stream Inst�ncia contendo os dados.
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
     * Anexa um arquivo � mensagem a partir de um array de bytes.
     * 
     * @param fileName String contendo o caminho do arquivo desejado.
     * @param content Array de bytes do conte�do.
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
     * Anexa um arquivo � mensagem a partir de uma conte�do em string.
     * 
     * @param fileName String contendo o caminho do arquivo desejado.
     * @param content String do conte�do desejado.
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
	 * Retorna o conte�do da inst�ncia.
	 * 
	 * @return Inst�ncia do objeto que define o conte�do.
	 */
    @SuppressWarnings("unchecked")
    public <C> C getContent(){
		return (C)content;
	}

	/**
	 * Define o conte�do da inst�ncia.
	 * 
	 * @param content Inst�ncia do objeto que define o conte�do.
	 */
	public <C> void setContent(C content){
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
    @SuppressWarnings("unchecked")
    public <F> F getFrom(){
		return (F)from;
	}

	/**
	 * Define a inst�ncia contendo os dados do remetente da mensagem.
	 * 
	 * @param from Inst�ncia contendo os dados do remetente.
	 */
	public void setFrom(Address from){
		this.from = from;
	}

	/**
	 * Retorna a lista contendo os dados dos destinat�rios da mensagem.
	 * 
	 * @return Lista contendo os dados dos destinat�rios.
	 */
    public Collection<?> getToRecipients(){
		return to;
	}

	/**
	 * Define a lista contendo os dados dos destinat�rios da mensagem.
	 * 
	 * @param to Lista contendo os dados dos destinat�rios.
	 */
	public void setToRecipients(Collection<Address> to){
		this.to = to;
	}

	/**
	 * Retorna a lista contendo os dados dos destinat�rios com c�pia da mensagem.
	 * 
	 * @return Lista contendo os dados dos destinat�rios.
	 */
    public Collection<?> getCcRecipients(){
		return cc;
	}

	/**
	 * Define a lista contendo os dados dos destinat�rios com c�pia da mensagem.
	 * 
	 * @param cc Lista contendo os dados dos destinat�rios.
	 */
	public void setCcRecipients(Collection<Address> cc){
		this.cc = cc;
	}

	/**
	 * Retorna a lista contendo os dados dos destinat�rios com c�pia oculta da mensagem.
	 * 
	 * @return Lista contendo os dados dos destinat�rios.
	 */
    public Collection<Address> getBccRecipients(){
		return bcc;
	}

	/**
	 * Define a lista contendo os dados dos destinat�rios com c�pia oculta da mensagem.
	 * 
	 * @param bcc Lista contendo os dados dos destinat�rios.
	 */
	public void setBccRecipients(Collection<Address> bcc){
		this.bcc = bcc;
	}

	/**
	 * Adiciona um novo destinat�rio da mensagem.
	 * 
	 * @param recipient Inst�ncia contendo os dados do destinat�rio.
	 */
    public void addToRecipient(Address recipient){
		if(to == null)
			to = new LinkedList<Address>();

		to.add(recipient);
	}

	/**
	 * Adiciona um novo destinat�rio com c�pia da mensagem.
	 * 
	 * @param recipient Inst�ncia contendo os dados do destinat�rio.
	 */
    public void addCcRecipient(Address recipient){
		if(cc == null)
			cc = new LinkedList<Address>();

		cc.add(recipient);
	}

	/**
	 * Adiciona um novo destinat�rio com c�pia oculta da mensagem.
	 * 
	 * @param recipient Inst�ncia contendo os dados do destinat�rio.
	 */
    public void addBccRecipient(Address recipient){
		if(bcc == null)
			bcc = new LinkedList<Address>();

		bcc.add(recipient);
	}
    
    /**
     * Adiciona um novo destinat�rio com c�pia oculta da mensagem.
     * 
     * @param recipient String contendo os dados do destinat�rio.
     */
    public void addBccRecipient(String recipient){
        try{
            this.addBccRecipient(new InternetAddress(recipient));
        }
        catch(AddressException e){
        }
    }

    /**
     * Adiciona um novo destinat�rio com c�pia da mensagem.
     * 
     * @param recipient String contendo os dados do destinat�rio.
     */
    public void addCcRecipient(String recipient){
        try{
            this.addCcRecipient(new InternetAddress(recipient));
        }
        catch(AddressException e){
        }
    }

    /**
     * Adiciona um novo destinat�rio da mensagem.
     * 
     * @param recipient String contendo os dados do destinat�rio.
     */
    public void addToRecipient(String recipient){
        try{
            this.addToRecipient(new InternetAddress(recipient));
        }
        catch(AddressException e){
        }
    }
}