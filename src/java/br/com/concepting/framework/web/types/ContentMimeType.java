package br.com.concepting.framework.web.types;

import br.com.concepting.framework.util.interfaces.IEnum;

/** 
 * Classe que define as constantes para os tipos de conteúdo suportados no download.
 *
 * @author fvilarinho
 * @since 1.0
 */
public enum ContentMimeType implements IEnum{
	/**
	 * Constante que define o tipo de conteúdo para arquivos PDF.
	 */
	PDF("application/pdf", "Acrobat Reader", ".pdf"),

	/**
	 * Constante que define o tipo de conteúdo para arquivos HTML.
	 */
	HTML("text/html", "HTML Document", ".html"),
	
	/**
	 * Constante que define o tipo de conteúdo para arquivos texto.
	 */
	TEXT("text/plain", "Text", ".txt"),

	WORD("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "Microsoft Word", ".docx"),
	
    EXCEL("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "Microsoft Excel", ".xlsx"),

    /**
	 * Constante que define o tipo de conteúdp para arquivos do Microsoft Excel 2000 ou inferior.
	 */
	OLD_EXCEL("application/vnd.ms-excel", "Microsoft Excel", ".xls"),
	
    /**
     * Constante que define o tipo de conteúdo para arquivos do Microsoft Word 2000 ou inferior.
     */
    OLD_WORD("application/msword", "Microsoft Word", ".doc"),

	/**
	 * Constante que define o tipo de conteúdo para arquivos XML.
	 */
	XML("text/xml", "XML", ".xml"),
	
	/**
	 * Constante que define o tipo de conteúdo para arquivos binários.
	 */
	BINARY("application/octet-stream", "Binary", ".binary"),

	/**
	 * Constante que define o tipo de conteúdo para arquivos RTF (Rich Text File).
	 */
	RICH_TEXT("text/rtf", "Rich Text", ".rtf"),

	/**
	 * Constante que define o tipo de conteúdo para imagens no formato PNG.
	 */
	PNG("image/png", "PNG Image", ".png"),

	/**
	 * Constante que define o tipo de conteúdo para imagens no formato JPEG.
	 */
	JPEG("image/jpeg", "JPEG Image", ".jpg"),

	/**
	 * Constante que define o tipo de conteúdo para imagens no formato GIF.
	 */
	GIF("image/gif", "GIF Image", ".gif");

	private String key;
	private String title;
	private String extension;

	/**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param key String contendo o valor desejado.
	 * @param extension String contendo a extensão.
	 */
	private ContentMimeType(String key, String title, String extension){
		setKey(key);
		setTitle(title);
		setExtension(extension);
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IEnum#getKey()
	 */
    public <O> O getKey(){
		return (O)key;
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IEnum#setKey(java.lang.Object)
	 */
	public <O> void setKey(O key){
		this.key = (String)key;
	}

	/**
	 * Retorna a extensão do arquivo vinculado ao mime type.
	 *
	 * @return String contendo a extensão do arquivo.
	 */
	public String getExtension(){
		return extension;
	}
	
	/**
	 * Retorna o título do mime type.
	 * 
	 * @return Sttring contendo o título.
	 */
	public String getTitle(){
    	return title;
    }

	/**
	 * Define o título do mime type.
	 * 
	 * @param title Sttring contendo o título.
	 */
	public void setTitle(String title){
    	this.title = title;
    }

	/**
	 * Define a extensão do arquivo vinculado ao mime type.
	 *
	 * @param extension String contendo a extensão do arquivo.
	 */
	public void setExtension(String extension){
		this.extension = extension;
	}

	/**
	 * @see java.lang.Enum#toString()
	 */
	public String toString(){
		return key;
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IEnum#toEnum(java.lang.Object)
	 */
	public <O> IEnum toEnum(O value) throws IllegalArgumentException{
		return toContentMimeType((String)value);
	}

	/**
	 * Converte uma string em uma instância da constante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @return Instância da constante.
	 */
	public static ContentMimeType toContentMimeType(String value) throws IllegalArgumentException{
		if(value == null)
			throw new IllegalArgumentException();

		for(ContentMimeType constant : values())
			if(value.equals(constant.getKey()) || value.equals(constant.getExtension()))
				return constant;
		
		throw new IllegalArgumentException(value);
	}
}