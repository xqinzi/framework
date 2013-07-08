package br.com.concepting.framework.web.taglibs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;

/**
 * Classe que define o componente visual de um relógio.
 * 
 * @author fvilarinho
 * @since 2.0
 */
public class ClockTag extends BaseTag{
    private String           pattern   = "";
    private SimpleDateFormat formatter = null;
    
    /**
     * Retorna a máscara de formatação para o componente.
     * 
     * @return String contendo a máscara de formatação.
     */
    public String getPattern(){
        return pattern;
    }

    /**
     * Define a máscara de formatação para o componente.
     * 
     * @param pattern String contendo a máscara de formatação.
     */
    public void setPattern(String pattern){
        this.pattern = pattern;
    }

    /**
     * @see br.com.concepting.framework.web.taglibs.BaseTag#clearAttributes()
     */
    protected void clearAttributes(){
        super.clearAttributes();
        
        setPattern("");
    }
    
    /**
     * @see br.com.concepting.framework.web.taglibs.BaseTag#initialize()
     */
    protected void initialize() throws Throwable{
        setName(getTagId());
        
        if(getStyleClass().length() == 0)
            setStyleClass(TaglibConstants.DEFAULT_CLOCK_STYLE_CLASS);
        
        Locale currentLanguage = systemController.getCurrentLanguage();

        if(pattern.length() == 0){
            formatter = (SimpleDateFormat)DateFormat.getTimeInstance(DateFormat.DEFAULT, currentLanguage);
            pattern   = formatter.toPattern();
            
            setPattern(pattern);
        }
        else
            formatter = new SimpleDateFormat(pattern, currentLanguage);

        super.initialize();
    }

    /**
     * @see br.com.concepting.framework.web.taglibs.BaseTag#renderOpen()
     */
    protected void renderOpen() throws Throwable{
        String        name    = getName();
        String        pattern = getPattern();
        StringBuilder tagName = new StringBuilder();
        
        tagName.append(name);
        tagName.append(".");
        tagName.append(AttributeConstants.PATTERN_KEY);
        
        HiddenPropertyTag hiddenPropertyTag = new HiddenPropertyTag();
        
        hiddenPropertyTag.setPageContext(pageContext);
        hiddenPropertyTag.setName(tagName.toString());
        hiddenPropertyTag.setValue(pattern);
        hiddenPropertyTag.doStartTag();
        hiddenPropertyTag.doEndTag();
        
        print("<span");
        
        renderAttributes();
        
        println(">");
    }
    
    /**
     * @see br.com.concepting.framework.web.taglibs.BaseTag#renderBody()
     */
    protected void renderBody() throws Throwable{
        Date now = new Date();
        
        println(formatter.format(now));
    }

    /**
     * @see br.com.concepting.framework.web.taglibs.BaseTag#renderClose()
     */
    protected void renderClose() throws Throwable{
        println("</span>");
        
        ScriptTag scriptTag = new ScriptTag();
        
        scriptTag.setPageContext(pageContext);
        
        StringBuilder content = new StringBuilder();
        
        content.append("addLoadEvent(showClockObject);");
        content.append(StringUtil.getLineBreak());
        
        scriptTag.setContent(content.toString());
        scriptTag.doStartTag();
        scriptTag.doEndTag();
    }
}