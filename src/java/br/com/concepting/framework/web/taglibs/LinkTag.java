package br.com.concepting.framework.web.taglibs;

import br.com.concepting.framework.util.types.ComponentType;

/**
 * Classe que define o componente visual para um link.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class LinkTag extends BaseActionFormElementTag{
    /**
     * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#initialize()
     */
    protected void initialize() throws Throwable{
        setComponentType(ComponentType.LINK);
            
        super.initialize();
    }
    
    /**
     * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#renderOpen()
     */
    protected void renderOpen() throws Throwable{
        print("<a");
        
        renderAttributes();
        
        println(">");
    }

    /**
     * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#renderBody()
     */
    protected void renderBody() throws Throwable{
        println(getLabel());
    }

    /**
     * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#renderClose()
     */
    protected void renderClose() throws Throwable{
        println("</a>");
    }
}
