package br.com.concepting.framework.web.taglibs;

import java.util.LinkedList;
import java.util.List;

import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.constants.AttributeConstants;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;
import br.com.concepting.framework.web.types.ComponentType;

public class AccordionTag extends BaseActionFormElementTag{
    private String           sectionHeaderLabelStyleClass = "";
    private String           sectionHeaderLabelStyle      = "";
    private String           sectionHeaderStyleClass      = "";
    private String           sectionHeaderStyle           = "";
    private String           sectionContentStyleClass     = "";
    private String           sectionContentStyle          = "";
    private List<SectionTag> sectionsTags                 = null;
    
    public String getSectionHeaderLabelStyleClass(){
        return sectionHeaderLabelStyleClass;
    }

    public void setSectionHeaderLabelStyleClass(String sectionHeaderLabelStyleClass){
        this.sectionHeaderLabelStyleClass = sectionHeaderLabelStyleClass;
    }

    public String getSectionHeaderLabelStyle(){
        return sectionHeaderLabelStyle;
    }

    public void setSectionHeaderLabelStyle(String sectionHeaderLabelStyle){
        this.sectionHeaderLabelStyle = sectionHeaderLabelStyle;
    }

    public String getSectionHeaderStyleClass(){
        return sectionHeaderStyleClass;
    }

    public void setSectionHeaderStyleClass(String sectionHeaderStyleClass){
        this.sectionHeaderStyleClass = sectionHeaderStyleClass;
    }

    public String getSectionHeaderStyle(){
        return sectionHeaderStyle;
    }

    public void setSectionHeaderStyle(String sectionHeaderStyle){
        this.sectionHeaderStyle = sectionHeaderStyle;
    }

    public String getSectionContentStyleClass(){
        return sectionContentStyleClass;
    }

    public void setSectionContentStyleClass(String sectionContentStyleClass){
        this.sectionContentStyleClass = sectionContentStyleClass;
    }

    public String getSectionContentStyle(){
        return sectionContentStyle;
    }

    public void setSectionContentStyle(String sectionContentStyle){
        this.sectionContentStyle = sectionContentStyle;
    }

    protected List<SectionTag> getSectionsTags(){
        return sectionsTags;
    }

    protected void setSectionsTags(List<SectionTag> sectionsTags){
        this.sectionsTags = sectionsTags;
    }

    protected void addSectionTag(SectionTag sectionTag){
        if(sectionsTags == null)
            sectionsTags = new LinkedList<SectionTag>();
        
        sectionsTags.add(sectionTag);
    }
    
    private String getCurrentSectionId(){
        StringBuilder currentSectionTagName = new StringBuilder();
        
        currentSectionTagName.append(getName());
        currentSectionTagName.append(".");
        currentSectionTagName.append(AttributeConstants.CURRENT_SECTION_KEY);

        return currentSectionTagName.toString();
    }
    
    private String getCurrentSectionName(){
        return StringUtil.trim(systemController.getRequest().getParameter(getCurrentSectionId()));
    }
    
    /**
     * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#renderOpen()
     */
    protected void renderOpen() throws Throwable{
        HiddenPropertyTag currentSectionTag = new HiddenPropertyTag();
        
        currentSectionTag.setPageContext(pageContext);
        currentSectionTag.setName(getCurrentSectionId());
        currentSectionTag.setValue(getCurrentSectionName());
        currentSectionTag.doStartTag();
        currentSectionTag.doEndTag();
        
        print("<table class=\"");
        print(StringUtil.trim(getStyleClass()));
        print("\"");
        
        String style = StringUtil.trim(getStyle());
        String width = StringUtil.trim(getWidth());
        
        if(style.length() > 0 || width.length() > 0){
            print(" style=\"");
            
            if(style.length() > 0){
                print(style);
                
                if(!style.endsWith(";"))
                    print(";");
            }

            if(width.length() > 0){
                if(style.length() > 0)
                    print(" ");
                
                print("width: ");
                print(width);
                print(";");
            }
            
            print("\"");
        }
        
        println(">");
        println("<tr>");
        println("<td>");
    }
    
    /**
     * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#renderBody()
     */
    protected void renderBody() throws Throwable{
        renderSections();
    }

    /**
     * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#renderClose()
     */
    protected void renderClose() throws Throwable{
        println("</td>");
        println("</tr>");
        println("</table>");
    }
    
    protected void renderSections() throws Throwable{
        print("<table class\"");
        print(TaglibConstants.DEFAULT_PANEL_STYLE_CLASS);
        println("\">");
        
        SectionTag sectionTag = null;
        
        for(Integer cont = 0 ; cont < sectionsTags.size() ; cont++){
            sectionTag = sectionsTags.get(cont);
            
            println("<tr>");
            println("<td>");
            
            renderSectionHeader(sectionTag);
            renderSectionContent(sectionTag);
            
            println("</td>");
            println("</tr>");
        }
        
        println("</table>");
    }
    
    protected void renderSectionHeader(SectionTag sectionTag) throws Throwable{
        print("<div id=\"");
        print(sectionTag.getName());
        print(".");
        print(AttributeConstants.SECTION_HEADER_KEY);
        print("\" class=\"");
        print(StringUtil.trim(sectionTag.getHeaderStyleClass()));
        print("\"");
        
        String style = StringUtil.trim(sectionTag.getHeaderStyle());
        
        if(style.length() > 0){
            print(" style=\"");
            print(style);
                
            if(!style.endsWith(";"))
                print(";");
            
            print("\"");
        }
        
        print(" onClick=\"showHideAccordionSection(this);\" title=\"");
        print(StringUtil.trim(sectionTag.getTooltip()));
        println("\">");

        print("<span class=\"");
        print(StringUtil.trim(sectionTag.getHeaderLabelStyleClass()));
        print("\"");
        
        style = StringUtil.trim(sectionTag.getHeaderLabelStyle());
        
        if(style.length() > 0){
            print(" style=\"");
            print(style);
                
            if(!style.endsWith(";"))
                print(";");
            
            print("\"");
        }
        
        println(">");
        
        println(StringUtil.trim(sectionTag.getLabel()));
        
        println("</span>");
        
        println("</div>");
    }
    
    protected void renderSectionContent(SectionTag sectionTag) throws Throwable{
        String sectionName        = sectionTag.getName();
        String currentSectionName = getCurrentSectionName();

        print("<div id=\"");
        print(sectionName);
        print(".");
        print(AttributeConstants.SECTION_CONTENT_KEY);
        print("\" class=\"");
        print(StringUtil.trim(sectionTag.getContentStyleClass()));
        print("\"");
        
        String style  = StringUtil.trim(sectionTag.getContentStyle());
        
        if(style.length() > 0 || !currentSectionName.equals(sectionName)){
            print(" style=\"");

            if(!currentSectionName.equals(sectionName))
                print(" display: none;");
            
            if(style.length() > 0){
                if(!currentSectionName.equals(sectionName))
                    print(" ");
                
                print(style);
                
                if(!style.endsWith(";"))
                    print(";");
            }
            
            print("\"");
        }

        println(">");
        
        println(sectionTag.getContent());
        
        println("</div>");
    }
    
    /**
     * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#render()
     */
    protected void render() throws Throwable{
        if(hasPermission()){
            if(sectionsTags != null && sectionsTags.size() > 0){
                renderOpen();
                renderBody();
                renderClose();
            }
        }
    }
    
    /**
     * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#initialize()
     */
    protected void initialize() throws Throwable{
        setType(ComponentType.ACCORDION);
        
        if(sectionHeaderLabelStyleClass.length() == 0)
            sectionHeaderLabelStyleClass = TaglibConstants.DEFAULT_SECTION_HEADER_LABEL_STYLE_CLASS;

        if(sectionHeaderStyleClass.length() == 0)
            sectionHeaderStyleClass = TaglibConstants.DEFAULT_SECTION_HEADER_STYLE_CLASS;
        
        if(sectionContentStyleClass.length() == 0)
            sectionContentStyleClass = TaglibConstants.DEFAULT_SECTION_CONTENT_STYLE_CLASS;
        
        super.initialize();
    }

    /**
     * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#clearAttributes()
     */
    protected void clearAttributes(){
        super.clearAttributes();
 
        setSectionsTags(null);
    }
}
