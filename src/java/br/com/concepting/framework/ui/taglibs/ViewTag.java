package br.com.concepting.framework.ui.taglibs;

import javax.servlet.jsp.tagext.BodyContent;

/**
 * Classe que define o componente view (agrupamento de renderização).
 * 
 * @author fvilarinho
 * @since 2.0
 */
public class ViewTag extends BaseTag{
    /**
     * @see br.com.concepting.framework.ui.taglibs.BaseTag#renderOpen()
     */
    protected void renderOpen() throws Throwable{
        print("<div id=\"");
        print(getName());
        println("\">");
    }

    /**
     * @see br.com.concepting.framework.ui.taglibs.BaseTag#renderBody()
     */
    protected void renderBody() throws Throwable{
        BodyContent content = getBodyContent();
        
        if(content != null)
            println(content.getString());
    }

    /**
     * @see br.com.concepting.framework.ui.taglibs.BaseTag#renderClose()
     */
    protected void renderClose() throws Throwable{
        println("</div>");
        
        super.renderClose();
    }
}
