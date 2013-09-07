package br.com.concepting.framework.ui.taglibs;

import javax.servlet.jsp.tagext.BodyContent;

import br.com.concepting.framework.ui.constants.TaglibConstants;
import br.com.concepting.framework.util.StringUtil;

/**
 * Classe que declara a utilização de um conteúdo javascript.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class ScriptTag extends BaseTag{
	private String url     = "";
	private String content = "";

	/**
	 * Retorna o conteúdo do javascript.
	 *
	 * @return String do conteúdo do javascript.
	 */
	public String getContent(){
		return content;
	}

	/**
	 * Define o conteúdo do javascript.
	 *
	 * @param content String do conteúdo do javascript.
	 */
	public void setContent(String content){
		this.content = content;
	}

	/**
	 * Retorna a URL onde o arquivo javascript está armazenado.
	 * 
	 * @return String contendo a URL do arquivo javascript.
	 */
	public String getUrl(){
		return url;
	}

	/**
	 * Define a URL onde o arquivo javascript está armazenado.
	 * 
	 * @param url String contendo a URL do arquivo javascript.
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

		print("<script type=\"text/javascript\"");
		
		String skin = systemController.getCurrentSkin();

		if(url.length() > 0){
			print(" src=\"");

			if(url.startsWith("/")){
				print(systemController.getContextPath());
				print(TaglibConstants.DEFAULT_SKINS_RESOURCES_DIR);
				print(skin);
			}

			print(url);
			print("\">");
		}
		else
			print(">");
		
		println();
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#renderBody()
	 */
	protected void renderBody() throws Throwable{
		if(url.length() == 0){
            BodyContent bodyContent = getBodyContent();
		    String      content     = StringUtil.trim(getContent());
		    
			if(bodyContent != null && content.length() == 0){
			    content = bodyContent.getString();
			    
			    setContent(content);
			}

			if(content.length() > 0)
				println(content);
		}
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#renderClose()
	 */
	protected void renderClose() throws Throwable{
		println("</script>");

		super.renderClose();
	}

	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseTag#clearAttributes()
	 */
	protected void clearAttributes(){
		super.clearAttributes();

		setUrl("");
		setContent("");
	}
}