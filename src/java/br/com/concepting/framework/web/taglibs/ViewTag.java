package br.com.concepting.framework.web.taglibs;

import javax.servlet.jsp.tagext.BodyContent;

public class ViewTag extends BaseTag{
    /**
     * @see br.com.concepting.framework.web.taglibs.BaseTag#renderOpen()
     */
    protected void renderOpen() throws Throwable{
        print("<div id=\"");
        print(getName());
        println("\">");
    }

    /**
     * @see br.com.concepting.framework.web.taglibs.BaseTag#renderBody()
     */
    protected void renderBody() throws Throwable{
        BodyContent content = getBodyContent();
        
        if(content != null)
            println(content.getString());
    }

    /**
     * @see br.com.concepting.framework.web.taglibs.BaseTag#renderClose()
     */
    protected void renderClose() throws Throwable{
        println("</div>");
        
        super.renderClose();
    }
}
