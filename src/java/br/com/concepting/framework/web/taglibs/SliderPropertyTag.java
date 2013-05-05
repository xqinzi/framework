package br.com.concepting.framework.web.taglibs;

import br.com.concepting.framework.util.StringUtil;

public class SliderPropertyTag extends BasePropertyTag{
    /**
     * @see br.com.concepting.framework.web.taglibs.BasePropertyTag#renderBody()
     */
    protected void renderBody() throws Throwable{
        String name  = StringUtil.trim(getName());
        String width = StringUtil.trim(getWidth());
        
        println("<div style=\"border: 1px solid black\">");
        
        print("<div id=\"");
        print(name);
        println(".sliderBarControl\" class=\"sliderBarControl\"></span>");
        
        print("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"");
        print(width);
        
        if(!width.endsWith(";"))
            print(";");
        
        println("\">");
        println("<tr>");
        println("<td class=\"leftSliderbar\"></td>");
        println("<td class=\"sliderBar\"></td>");
        println("<td class=\"rightSliderBar\"></td>");
        println("</tr>");
        println("</table>");
        
        println("</div>");
        
        println("</div>");
    }
}
