package br.com.concepting.framework.ui.taglibs;

import javax.servlet.jsp.tagext.BodyContent;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.resource.PropertiesResource;
import br.com.concepting.framework.ui.constants.TaglibConstants;
import br.com.concepting.framework.ui.types.VisibilityType;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.AlignmentType;

/**
 * Classe que define o componente visual dialogBox (caixa de diálogo).
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class DialogBoxTag extends BaseActionFormElementTag{
    private String  title       = null;
    private Boolean modal       = false;
    private Boolean showOnLoad  = false;
    private Boolean showButtons = true;
    
    /**
     * Indica se os botões do componente devem ser renderizados.
     * 
     * @return True/False.
     */
    public Boolean showButtons(){
        return showButtons;
    }

    /**
     * Indica se os botões do componente devem ser renderizados.
     * 
     * @return True/False.
     */
    public Boolean getShowButtons(){
        return showButtons();
    }

    /**
     * Define se os botões do componente devem ser renderizados.
     * 
     * @param showButtons True/False.
     */
    public void setShowButtons(Boolean showButtons){
        this.showButtons = showButtons;
    }

    /**
     * Indica se o componete deve ser exibida no carregamento da página.
     * 
     * @return True/False.
     */
    public Boolean showOnLoad(){
        return showOnLoad;
    }

    /**
     * Indica se o componete deve ser exibida no carregamento da página.
     * 
     * @return True/False.
     */
    public Boolean getShowOnLoad(){
        return showOnLoad();
    }

    /**
     * Define se o componete deve ser exibida no carregamento da página.
     * 
     * @param showOnLoad True/False.
     */
    public void setShowOnLoad(Boolean showOnLoad){
        this.showOnLoad = showOnLoad;
    }

    /**
     * Retorna o título do componente.
     * 
     * @return String contendo o título.
     */
    public String getTitle(){
        return title;
    }

    /**
     * Define o título do componente.
     * 
     * @param title String contendo o título.
     */
    public void setTitle(String title){
        this.title = title;
    }
    
    /**
     * Indica se o componente deve ter o comportamento modal.
     * 
     * @return True/False.
     */
    public Boolean isModal(){
        return modal;
    }

    /**
     * Indica se o componente deve ter o comportamento modal.
     * 
     * @return True/False.
     */
    public Boolean getModal(){
        return isModal();
    }

    /**
     * Define se o componente deve ter o comportamento modal.
     * 
     * @param modal True/False.
     */
    public void setModal(Boolean modal){
        this.modal = modal;
    }

    /**
     * @see br.com.concepting.framework.ui.taglibs.BaseTag#initialize()
     */
    protected void initialize() throws Throwable{
        super.initialize();
        
        if(title == null){
            String        resourceKey = getResourceKey();
            StringBuilder propertyId  = new StringBuilder();
    
            if(resourceKey.length() > 0)
                propertyId.append(resourceKey);
            else
                propertyId.append(getName());
            
            propertyId.append(".");
            propertyId.append(AttributeConstants.TITLE_KEY);
    
            PropertiesResource resources = getI18nResource();

            title = resources.getProperty(propertyId.toString(), false);
                
            if(title == null){
                resources = getDefaultI18nResource();
                title     = StringUtil.trim(resources.getProperty(propertyId.toString()));
            }
        }
    }

    /**
     * @see br.com.concepting.framework.ui.taglibs.BaseTag#renderOpen()
     */
    protected void renderOpen() throws Throwable{
        print("<div id=\"");
        print(getName());
        print(".");
        print(TaglibConstants.DIALOG_BOX_ID);
        print("\" class=\"");
        print(TaglibConstants.DEFAULT_DIALOG_BOX_STYLE_CLASS);
        print("\" style=\"visibility: ");
        print(VisibilityType.HIDDEN);
        println(";\">");
        
        print("<table class=\"");
        print(TaglibConstants.DEFAULT_DIALOG_BOX_CONTENT_STYLE_CLASS);
        print("\"");
        
        if(getWidth().length() > 0){
            print(" width=\"");
            print(getWidth());
            print("\"");
        }
        
        if(getHeight().length() > 0){
            print(" height=\"");
            print(getHeight());
            print("\"");
        }
        
        println(">");

        println("<tr>");
        print("<td class=\"");
        print(TaglibConstants.DEFAULT_DIALOG_BOX_TITLE_STYLE_CLASS);
        print("\" title=\"");
        print(getTooltip());
        println("\">");
        println(title);
        println("</td>");
        println("</tr>");

        println("<tr>");
        println("<td height=\"10\"></td>");
        println("</tr>");
        
        println("<tr>");
        print("<td class=\"");
        print(TaglibConstants.DEFAULT_DIALOG_BOX_TEXT_STYLE_CLASS);
        println("\">");
    }
    
    /**
     * @see br.com.concepting.framework.ui.taglibs.BaseActionFormElementTag#renderBody()
     */
    protected void renderBody() throws Throwable{
        BodyContent bodyContent = getBodyContent();
        
        if(bodyContent != null)
            println(bodyContent.getString());
    }
    
    /**
     * Rendereiza os botões do componente.
     * 
     * @throws Throwable
     */
    protected void renderButtons() throws Throwable{
        StringBuilder content = new StringBuilder();

        content.append("hideDialogBox('");
        content.append(getName());
        content.append("');");

        ConfirmButtonTag confirmButtonTag = new ConfirmButtonTag();

        confirmButtonTag.setPageContext(pageContext);
        confirmButtonTag.setOnClick(content.toString());
        confirmButtonTag.doStartTag();
        confirmButtonTag.doEndTag();
    }
    
    /**
     * @see br.com.concepting.framework.ui.taglibs.BaseTag#renderClose()
     */
    protected void renderClose() throws Throwable{
        println("</td>");
        println("</tr>");
        
        if(showButtons){
            println("<tr>");
            print("<td valign=\"");
            print(AlignmentType.BOTTOM);
            print("\" align=\"");
            print(AlignmentType.CENTER);
            println("\" height=\"1\">");
            
            renderButtons();
            
            println("</td>");
            println("</tr>");
        }
        
        println("<tr>");
        println("<td height=\"10\"></td>");
        println("</tr>");

        println("</table>");
        
        println("</div>");
        
        if(showOnLoad){
            StringBuilder content = new StringBuilder();
            
            content.append("showDialogBox(\"");
            content.append(getName());
            content.append("\", ");
            content.append(modal);
            content.append(");");
            
            ScriptTag scriptTag = new ScriptTag();
            
            scriptTag.setPageContext(pageContext);
            scriptTag.setContent(content.toString());
            scriptTag.doStartTag();
            scriptTag.doEndTag();
        }
    }
    
    /**
     * @see br.com.concepting.framework.ui.taglibs.BaseActionFormElementTag#clearAttributes()
     */
    protected void clearAttributes(){
        super.clearAttributes();
        
        setTitle(null);
        setShowOnLoad(false);
        setShowButtons(true);
        setModal(false);
    }
}