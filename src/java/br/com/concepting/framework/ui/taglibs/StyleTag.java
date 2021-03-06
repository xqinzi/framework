package br.com.concepting.framework.ui.taglibs;

import br.com.concepting.framework.ui.constants.TaglibConstants;

/**
 * Classe que declara a utiliza��o de um arquivo CSS (Cascade Style Sheet).
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class StyleTag extends BaseTag{
	private String url = "";

    /**
	 * Retorna a URL onde o arquivo CSS est� armazenado.
	 * 
	 * @return String contendo a URL do arquivo CSS.
	 */
	public String getUrl(){
		return url;
	}

	/**
	 * Define a URL onde o arquivo CSS est� armazenado.
	 * 
	 * @param url String contendo a URL do arquivo CSS.
	 */
	public void setUrl(String url){
		this.url = url;
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#initialize()
	 */
	protected void initialize() throws Throwable{
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#renderOpen()
	 */
	protected void renderOpen() throws Throwable{
		super.renderOpen();

		print("<link href=\"");
		
        String skin = systemController.getCurrentSkin();
		        
		if(url.startsWith("/")){
			print(systemController.getContextPath());
			print(TaglibConstants.DEFAULT_SKINS_RESOURCES_DIR);
			print(skin);
		}
			
		print(url);
		print("\" type=\"text/css\" rel=\"stylesheet\"");
	} 

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#renderClose()
	 */
	protected void renderClose() throws Throwable{
		println("/>");
		
		super.renderClose();
	}
	
	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#clearAttributes()
	 */
	protected void clearAttributes(){
		super.clearAttributes();
		
		setUrl("");
	}
}