package br.com.concepting.framework.web.taglibs;

import javax.servlet.jsp.tagext.BodyContent;

import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;

/**
 * Classe que declara a utilização de um conteúdo contendo javascripts.
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
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderBody()
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
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderClose()
	 */
	protected void renderClose() throws Throwable{
		println("</script>");

		super.renderClose();
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#clearAttributes()
	 */
	protected void clearAttributes(){
		super.clearAttributes();

		setUrl("");
		setContent("");
	}
}