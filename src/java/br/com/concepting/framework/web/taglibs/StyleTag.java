package br.com.concepting.framework.web.taglibs;


/**
 * Classe que declara a utilização de um arquivo CSS (Cascade Style Sheet).
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class StyleTag extends BaseTag{
	private String url = "";

    /**
	 * Retorna a URL onde o arquivo CSS está armazenado.
	 * 
	 * @return String contendo a URL do arquivo CSS.
	 */
	public String getUrl(){
		return url;
	}

	/**
	 * Define a URL onde o arquivo CSS está armazenado.
	 * 
	 * @param url String contendo a URL do arquivo CSS.
	 */
	public void setUrl(String url){
		this.url = url;
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#initialize()
	 */
	protected void initialize() throws Throwable{
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderOpen()
	 */
	protected void renderOpen() throws Throwable{
		super.renderOpen();

		print("<link href=\"");
		
        String skin = systemController.getCurrentSkin();
		        
		if(url.startsWith("/")){
			print(systemController.getContextPath());
			print("/skins/");
			print(skin);
		}
			
		print(url);
		print("\" type=\"text/css\" rel=\"stylesheet\"");
	} 

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderClose()
	 */
	protected void renderClose() throws Throwable{
		println("/>");
		
		super.renderClose();
	}
	
	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#clearAttributes()
	 */
	protected void clearAttributes(){
		super.clearAttributes();
		
		setUrl("");
	}
}