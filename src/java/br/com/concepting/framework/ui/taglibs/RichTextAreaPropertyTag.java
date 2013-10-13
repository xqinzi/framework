package br.com.concepting.framework.ui.taglibs;

import br.com.concepting.framework.ui.constants.TaglibConstants;

public class RichTextAreaPropertyTag extends BasePropertyTag{
    /**
     * @see br.com.concepting.framework.ui.taglibs.BasePropertyTag#renderBody()
     */
    protected void renderOpen() throws Throwable{
        super.renderOpen();
        
        String        name          = getName();
        StringBuilder scriptContent = new StringBuilder();
        
        scriptContent.append("initializeRichTextArea(\"");
        scriptContent.append(name);
        scriptContent.append("\");");
        
        ScriptTag scriptTag = new ScriptTag();
        
        scriptTag.setPageContext(pageContext);
        scriptTag.setContent(scriptContent.toString());
        scriptTag.doStartTag();
        scriptTag.doEndTag();
        
        TextAreaPropertyTag textAreaPropertyTag = new TextAreaPropertyTag();
        
        textAreaPropertyTag.setPageContext(pageContext);
        textAreaPropertyTag.setActionFormTag(getActionFormTag());
        textAreaPropertyTag.setName(name);
        textAreaPropertyTag.setShowLabel(false);
        textAreaPropertyTag.setValue(getValue());
        textAreaPropertyTag.setStyle("display: NONE;");
        textAreaPropertyTag.doStartTag();
        textAreaPropertyTag.doEndTag();
        
        renderToolbar();
        
        print("<div id=\"");
        print(name);
        print(".content\" name=\"");
        print(name);
        print(".content\" contentEditable=\"true\" class=\"");
        print(TaglibConstants.DEFAULT_RICH_TEXT_AREA_STYLE_CLASS);
        print("\"");
        
        String width  = getWidth();
        String height = getHeight();
        
        if(width.length() > 0 || height.length() > 0){
            print(" style=\"");
            
            if(width.length() > 0){
                print("width: ");
                print(width);
                
                if(!width.endsWith(";"))
                    print(";");
            }

            if(height.length() > 0){
                if(width.length() > 0)
                    print(" ");
                
                print("height: ");
                print(height);
                
                if(!height.endsWith(";"))
                    print(";");
            }
            
            print("\"");
        }
        
        println(">");
    }
    
    protected void renderToolbar() throws Throwable{
    }
    
    /**
     * @see br.com.concepting.framework.ui.taglibs.BasePropertyTag#renderBody()
     */
    protected void renderBody() throws Throwable{
        renderValue();
    }
    
    /**
     * @see br.com.concepting.framework.ui.taglibs.BasePropertyTag#renderValue()
     */
    protected void renderValue() throws Throwable{
        println(getValue());
    }
    
    /**
     * @see br.com.concepting.framework.ui.taglibs.BasePropertyTag#renderClose()
     */
    protected void renderClose() throws Throwable{
        println("</div>");
        
        super.renderClose();
    }
}
