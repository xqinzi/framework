package br.com.concepting.framework.web.taglibs;

import javax.servlet.jsp.tagext.BodyContent;

import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;
import br.com.concepting.framework.web.types.ComponentType;

public class SectionTag extends BaseActionFormElementTag{
    private String headerLabelStyleClass = "";
    private String headerLabelStyle      = "";
    private String headerStyleClass      = "";
    private String headerStyle           = "";
    private String contentStyleClass     = "";
    private String contentStyle          = "";
    private String onSelect              = "";
    private String content               = null;
    
    public String getHeaderLabelStyleClass(){
        return headerLabelStyleClass;
    }

    public void setHeaderLabelStyleClass(String headerLabelStyleClass){
        this.headerLabelStyleClass = headerLabelStyleClass;
    }

    public String getHeaderLabelStyle(){
        return headerLabelStyle;
    }

    public void setHeaderLabelStyle(String headerLabelStyle){
        this.headerLabelStyle = headerLabelStyle;
    }

    public String getHeaderStyleClass(){
        return headerStyleClass;
    }

    public void setHeaderStyleClass(String headerStyleClass){
        this.headerStyleClass = headerStyleClass;
    }

    public String getHeaderStyle(){
        return headerStyle;
    }

    public void setHeaderStyle(String headerStyle){
        this.headerStyle = headerStyle;
    }

    public String getContentStyleClass(){
        return contentStyleClass;
    }

    public void setContentStyleClass(String contentStyleClass){
        this.contentStyleClass = contentStyleClass;
    }

    public String getContentStyle(){
        return contentStyle;
    }

    public void setContentStyle(String contentStyle){
        this.contentStyle = contentStyle;
    }

    /**
     * Retorna o conteúdo da guia.
     * 
     * @return String contendo o conteúdo da guia.
     */
    public String getContent(){
        return content;
    }

    /**
     * Define o conteúdo da guia.
     * 
     * @param content String contendo o conteúdo da guia.
     */
    public void setContent(String content){
        this.content = content;
    }

    /**
     * Retorna o evento a ser executado quando a aba for selecionada.
     * 
     * @return String contendo o evento a ser executado.
     */
    public String getOnSelect(){
        return onSelect;
    }

    /**
     * Define o evento a ser executado quando a aba for selecionada.
     * 
     * @param onSelect String contendo o evento a ser executado.
     */
    public void setOnSelect(String onSelect){
        this.onSelect = onSelect;
    }

    /**
     * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#initialize()
     */
    protected void initialize() throws Throwable{
        setType(ComponentType.SECTION);

        AccordionTag accordionTag = (AccordionTag)findAncestorWithClass(this, AccordionTag.class);

        if(accordionTag != null){
            if(headerLabelStyleClass.length() == 0)
                headerLabelStyleClass = StringUtil.trim(accordionTag.getSectionHeaderLabelStyleClass());
            
            if(headerLabelStyleClass.length() == 0)
                headerLabelStyleClass = TaglibConstants.DEFAULT_SECTION_HEADER_LABEL_STYLE_CLASS;

            if(headerStyleClass.length() == 0)
                headerStyleClass = StringUtil.trim(accordionTag.getSectionHeaderStyleClass());
            
            if(headerStyleClass.length() == 0)
                headerStyleClass = TaglibConstants.DEFAULT_SECTION_HEADER_STYLE_CLASS;
            
            if(contentStyleClass.length() == 0)
                contentStyleClass = StringUtil.trim(accordionTag.getSectionContentStyleClass());
            
            if(contentStyleClass.length() == 0)
                contentStyleClass = TaglibConstants.DEFAULT_SECTION_CONTENT_STYLE_CLASS;
        }
        
        super.initialize();
    }

    /**
     * @see br.com.concepting.framework.web.taglibs.BaseTag#render()
     */
    protected void render() throws Throwable{
        AccordionTag accordionTag = (AccordionTag)findAncestorWithClass(this, AccordionTag.class);

        if(accordionTag != null){
            if(hasPermission()){
                SectionTag  sectionTag     = (SectionTag)this.clone();
                BodyContent bodyContent    = sectionTag.getBodyContent();
                String      sectionContent = sectionTag.getContent();

                if(bodyContent != null && sectionContent == null){
                    sectionContent = StringUtil.trim(bodyContent.getString());
                    
                    sectionTag.setContent(sectionContent);
                }

                accordionTag.addSectionTag(sectionTag);
            }
        }
    }

    /**
     * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#clearAttributes()
     */
    protected void clearAttributes(){
        super.clearAttributes();

        setHeaderLabelStyleClass("");
        setHeaderLabelStyle("");
        setHeaderStyleClass("");
        setHeaderStyle("");
        setContentStyleClass("");
        setContentStyle("");
        setOnSelect("");
        setContent(null);
    }    
}