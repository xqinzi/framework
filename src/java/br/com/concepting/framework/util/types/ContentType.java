package br.com.concepting.framework.util.types;

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
	GIF("image/gif", "GIF Image", ".gif"),
	
    /**
     * Constante que define o tipo de conte�do para anexos.
     */
	MULTIPART_ALTERNATIVE("multipart/alternative"),

    /**
     * Constante que define o tipo de conte�do para anexos.
     */
    MULTIPART_RELATED("multipart/related"),
	
    /**
     * Constante que define o tipo de conte�do para anexos.
     */
	MULTIPART_MIXED("multipart/mixed");

    private String mimeType  = "";
	private String title     = "";
	private String extension = "";

    /**
     * Construtor - Define o valor da constante.
     * 
     * @param mimeType String contendo o valor desejado.
     */
    private ContentType(String mimeType){
        setMimeType(mimeType);
    }

    /**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param mimeType String contendo o valor desejado.
     * @param title Sttring contendo o t�tulo.
	 * @param extension String contendo a extens�o.
	 */
	private ContentType(String mimeType, String title, String extension){
	    this(mimeType);

		setTitle(title);
		setExtension(extension);
	}
	
	/**
	 * Retorna o identificador do mime type.
	 * 
	 * @return String contendo o identificador do mime type.
	 */
	public String getMimeType(){
        return mimeType;
    }

    /**
     * Define o identificador do mime type.
     * 
     * @param mimeType String contendo o identificador do mime type.
     */
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
	
	/**
	 * Retorna a constante do tipo de conte�do baseado em uma string.
	 * 
	 * @param contentType Constante do tipo de conte�do.
	 * @return String desejada.
	 */
	public static ContentType toContentType(String contentType){
	    if(contentType == null)
	        throw new IllegalArgumentException();
	    
	    Integer pos = contentType.indexOf(";");
	    
	    if(pos >= 0)
	        contentType = contentType.substring(0, pos);
	    
	    for(ContentType constant : values())
	        if(constant.getMimeType().toLowerCase().equals(contentType.toLowerCase()))
	            return constant;
	    
	    return valueOf(contentType.toUpperCase());
	}
}