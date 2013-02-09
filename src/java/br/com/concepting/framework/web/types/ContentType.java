package br.com.concepting.framework.web.types;

/** 
 * Classe que define as constantes para os tipos de conte�do suportados no download.
 *
 * @author fvilarinho
 * @since 1.0
 */
public enum ContentType{
	/**
	 * Constante que define o tipo de conte�do para arquivos PDF.
	 */
	PDF("application/pdf", "Acrobat Reader", ".pdf"),

	/**
	 * Constante que define o tipo de conte�do para arquivos HTML.
	 */
	HTML("text/html", "HTML Document", ".html"),
	
	/**
	 * Constante que define o tipo de conte�do para arquivos texto.
	 */
	TEXT("text/plain", "Text", ".txt"),

    /**
     * Constante que define o tipo de conte�do para arquivos do Microsoft Word 2003 ou superior.
     */
	WORD("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "Microsoft Word", ".docx"),
	
    /**
     * Constante que define o tipo de conte�dp para arquivos do Microsoft Excel 2003 ou superior.
     */
    EXCEL("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "Microsoft Excel", ".xlsx"),

    /**
	 * Constante que define o tipo de conte�dp para arquivos do Microsoft Excel 2000 ou inferior.
	 */
	OLD_EXCEL("application/vnd.ms-excel", "Microsoft Excel", ".xls"),
	
    /**
     * Constante que define o tipo de conte�do para arquivos do Microsoft Word 2000 ou inferior.
     */
    OLD_WORD("application/msword", "Microsoft Word", ".doc"),

	/**
	 * Constante que define o tipo de conte�do para arquivos XML.
	 */
	XML("text/xml", "XML", ".xml"),
	
	/**
	 * Constante que define o tipo de conte�do para arquivos bin�rios.
	 */
	BINARY("application/octet-stream", "Binary", ".binary"),

	/**
	 * Constante que define o tipo de conte�do para arquivos RTF (Rich Text File).
	 */
	RICH_TEXT("text/rtf", "Rich Text", ".rtf"),

	/**
	 * Constante que define o tipo de conte�do para imagens no formato PNG.
	 */
	PNG("image/png", "PNG Image", ".png"),

	/**
	 * Constante que define o tipo de conte�do para imagens no formato JPEG.
	 */
	JPEG("image/jpeg", "JPEG Image", ".jpg"),

	/**
	 * Constante que define o tipo de conte�do para imagens no formato GIF.
	 */
	GIF("image/gif", "GIF Image", ".gif");

	private String mimeType  = "";
	private String title     = "";
	private String extension = "";

	/**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param mimeType String contendo o valor desejado.
	 * @param extension String contendo a extens�o.
	 */
	private ContentType(String mimeType, String title, String extension){
		setMimeType(mimeType);
		setTitle(title);
		setExtension(extension);
	}
	
	public String getMimeType(){
        return mimeType;
    }

	public void setMimeType(String mimeType){
        this.mimeType = mimeType;
    }

    /**
	 * Retorna a extens�o do arquivo vinculado ao mime type.
	 *
	 * @return String contendo a extens�o do arquivo.
	 */
	public String getExtension(){
		return extension;
	}
	
	/**
	 * Retorna o t�tulo do mime type.
	 * 
	 * @return Sttring contendo o t�tulo.
	 */
	public String getTitle(){
    	return title;
    }

	/**
	 * Define o t�tulo do mime type.
	 * 
	 * @param title Sttring contendo o t�tulo.
	 */
	public void setTitle(String title){
    	this.title = title;
    }

	/**
	 * Define a extens�o do arquivo vinculado ao mime type.
	 *
	 * @param extension String contendo a extens�o do arquivo.
	 */
	public void setExtension(String extension){
		this.extension = extension;
	}
}