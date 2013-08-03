package br.com.concepting.framework.web.taglibs;

import java.util.LinkedList;
import java.util.List;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.ComponentType;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;
import br.com.concepting.framework.web.types.DisplayType;

/**
 * Classe que define o componente visual para um conjunto de seções.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class AccordionTag extends BaseActionFormElementTag{
    private String           sectionHeaderStyleClass  = "";
    private String           sectionHeaderStyle       = "";
    private String           sectionContentStyleClass = "";
    private String           sectionContentStyle      = "";
    private List<SectionTag> sectionsTags             = null;
    
    /**
     * Retorna o identificador do estilo CSS que define o cabeçalho da seção.
     * 
     * @return String contendo o identificador do estilo CSS.
     */
    public String getSectionHeaderStyleClass(){
        return sectionHeaderStyleClass;
    }

    /**
     * Define o identificador do estilo CSS que define o cabeçalho da seção.
     * 
     * @param sectionHeaderStyleClass String contendo o identificador do estilo CSS.
     */
    public void setSectionHeaderStyleClass(String sectionHeaderStyleClass){
        this.sectionHeaderStyleClass = sectionHeaderStyleClass;
    }

    /**
     * Retorna o identificador do estilo CSS que define o cabeçalho da seção.
     * 
     * @return String contendo o identificador do estilo CSS.
     */
    public String getSectionHeaderStyle(){
        return sectionHeaderStyle;
    }

    /**
     * Define o identificador do estilo CSS que define o cabeçalho da seção.
     * 
     * @param sectionHeaderStyle String contendo o identificador do estilo CSS.
     */
    public void setSectionHeaderStyle(String sectionHeaderStyle){
        this.sectionHeaderStyle = sectionHeaderStyle;
    }

    /**
     * Retorna o identificador do estilo CSS que define o conteúdo da seção.
     * 
     * @return String contendo o identificador do estilo CSS.
     */
    public String getSectionContentStyleClass(){
        return sectionContentStyleClass;
    }

    /**
     * Define o identificador do estilo CSS que define o conteúdo da seção.
     * 
     * @param sectionContentStyleClass String contendo o identificador do estilo CSS.
     */
    public void setSectionContentStyleClass(String sectionContentStyleClass){
        this.sectionContentStyleClass = sectionContentStyleClass;
    }

    /**
     * Retorna o identificador do estilo CSS que define o conteúdo da seção.
     * 
     * @return String contendo o identificador do estilo CSS.
     */
    public String getSectionContentStyle(){
        return sectionContentStyle;
    }

    /**
     * Define o identificador do estilo CSS que define o conteúdo da seção.
     * 
     * @param sectionContentStyle String contendo o identificador do estilo CSS.
     */
    public void setSectionContentStyle(String sectionContentStyle){
        this.sectionContentStyle = sectionContentStyle;
    }

    /**
     * Retorna a lista das seções.
     * 
     * @return Lista contendo as propriedades das seções.
     */
    protected List<SectionTag> getSectionsTags(){
        return sectionsTags;
    }

    /**
     * Define as seções.
     * 
     * @param sectionsTags Lista contendo as propriedades das seções.
     */
    protected void setSectionsTags(List<SectionTag> sectionsTags){
        this.sectionsTags = sectionsTags;
    }

    /**
     * Adiciona uma seção.
     * 
     * @param sectionTag Instância da tag contendo as propriedades da seção.
     */
    protected void addSectionTag(SectionTag sectionTag){
        if(sectionsTags == null)
            sectionsTags = new LinkedList<SectionTag>();
        
        sectionsTags.add(sectionTag);
    }

    /**
     * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#renderOpen()
     */
    protected void renderOpen() throws Throwable{
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
        
        StringBuilder currentSectionTagName = new StringBuilder();
        
        currentSectionTagName.append(getName());
        currentSectionTagName.append(".");
        currentSectionTagName.append(AttributeConstants.CURRENT_SECTION_KEY);

        if(hasMultipleSelection()){
            List<String> sections = new LinkedList<String>();
            
            if(sectionsTags != null && sectionsTags.size() > 0)
                for(SectionTag sectionTag : sectionsTags)
                    sections.add(sectionTag.getName());
            
            ListPropertyTag currentSectionTag = new ListPropertyTag();
            
            currentSectionTag.setPageContext(pageContext);
            currentSectionTag.setName(currentSectionTagName.toString());
            currentSectionTag.setValue(getRequestInfo().getCurrentSection());
            currentSectionTag.setStyle("display: ".concat(DisplayType.NONE.toString()).concat(";"));
            currentSectionTag.setDataValues(sections);
            currentSectionTag.setMultipleSelection(hasMultipleSelection());
            currentSectionTag.setShowFirstOption(false);
            currentSectionTag.setShowLabel(false);
            currentSectionTag.doStartTag();
            currentSectionTag.doEndTag();
        } 
        else{
            HiddenPropertyTag currentSectionTag = new HiddenPropertyTag();
            
            currentSectionTag.setPageContext(pageContext);
            currentSectionTag.setName(currentSectionTagName.toString());
            currentSectionTag.setValue(getRequestInfo().getCurrentSection());
            currentSectionTag.doStartTag();
            currentSectionTag.doEndTag();
        }
    }
    
    /**
     * Renderiza as seções do componente.
     * 
     * @throws Throwable
     */
    protected void renderSections() throws Throwable{
        print("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"");
        print(TaglibConstants.DEFAULT_PANEL_STYLE_CLASS);
        println("\">");
        
        SectionTag sectionTag = null;
        
        for(Integer cont = 0 ; cont < sectionsTags.size() ; cont++){
            sectionTag = sectionsTags.get(cont);
            
            println("<tr>");
            println("<td>");
            
            renderSectionHeader(sectionTag, cont);
            renderSectionContent(sectionTag, cont);
            
            println("</td>");
            println("</tr>");
        }
        
        println("</table>");
    }
    
    /**
     * Renderiza o cabeçalho da seção.
     * 
     * @param sectionTag Instância contendo as propriedades da seção.
     * @param index Valor inteiro contendo o índice da seção.
     * @throws Throwable
     */
    protected void renderSectionHeader(SectionTag sectionTag, Integer index) throws Throwable{
        print("<div id=\"");
        print(sectionTag.getName());
        print(".");
        print(TaglibConstants.DEFAULT_SECTION_HEADER_STYLE_CLASS);
        print("\" class=\"");
        
        String headerStyleClass = StringUtil.trim(sectionTag.getHeaderStyleClass());
        
        if(index == 0){
            print("first");
            print(StringUtil.capitalize(headerStyleClass));
        }
        else if(index == sectionsTags.size() - 1){
            String  sectionName      = sectionTag.getName();
            Boolean isCurrentSection = false;
            
            if(hasMultipleSelection()){
                Object currentSectionsNames = getRequestInfo().getCurrentSection();
                
                if(currentSectionsNames != null && (currentSectionsNames instanceof List) && ((List)currentSectionsNames).size() > 0)
                    isCurrentSection = ((List)currentSectionsNames).contains(sectionName);
            }
            else{
                String currentSectionName = StringUtil.trim(getRequestInfo().getCurrentSection());
                
                isCurrentSection = sectionName.equals(currentSectionName);
            }
            
            if(!isCurrentSection){
                print("last");
                print(StringUtil.capitalize(headerStyleClass));
            }
            else
                print(headerStyleClass);
        }
        else
            print(headerStyleClass);
        
        print("\"");
        
        String style = StringUtil.trim(sectionTag.getHeaderStyle());
        
        if(style.length() > 0){
            print(" style=\"");
            print(style);
                
            if(!style.endsWith(";"))
                print(";");
            
            print("\"");
        }
        
        print(" onClick=\"showHideAccordionSection('");
        print(getName());
        print("', this, ");
        print(index == (sectionsTags.size() - 1));
        print(", ");
        print(hasMultipleSelection());
        
        String onSelect   = StringUtil.trim(sectionTag.getOnSelect());
        String onUnSelect = StringUtil.trim(sectionTag.getOnUnSelect());
        
        if(onSelect.length() > 0){
            print(", function(){");
            print(onSelect);
            print("}");
        }
        
        if(onUnSelect.length() > 0){
            print(", ");
            
            if(onSelect.length() == 0)
                print("null, ");
            
            print("function(){");
            print(onUnSelect);
            print("}");
        }
        
        print(");\" title=\"");
        print(StringUtil.trim(sectionTag.getTooltip()));
        println("\">");
        
        println(StringUtil.trim(sectionTag.getLabel()));
        
        println("</div>");
    }
    
    /**
     * Renderiza o conteúdo da seção.
     * 
     * @param sectionTag Instância da tag contendo as propriedade da seção.
     * @param index Valor inteiro contendo o índice da seção.
     * @throws Throwable
     */
    protected void renderSectionContent(SectionTag sectionTag, Integer index) throws Throwable{
        String sectionName = sectionTag.getName();

        print("<div id=\"");
        print(sectionName);
        print(".");
        print(TaglibConstants.DEFAULT_SECTION_HEADER_STYLE_CLASS);
        print("\" class=\"");
        
        String contentStyleClass = StringUtil.trim(sectionTag.getContentStyleClass());
        
        if(index == 0){
            print("first");
            print(StringUtil.capitalize(contentStyleClass));
        }
        else if(index == sectionsTags.size() - 1){
            print("last");
            print(StringUtil.capitalize(contentStyleClass));
        }
        else
            print(contentStyleClass);
        
        print("\"");
        
        String style  = StringUtil.trim(sectionTag.getContentStyle());
        String height = StringUtil.trim(sectionTag.getHeight());
        
        Boolean isCurrentSection = false;
        
        if(hasMultipleSelection()){
            Object currentSectionsNames = getRequestInfo().getCurrentSection();
            
            if(currentSectionsNames != null && (currentSectionsNames instanceof List) && ((List)currentSectionsNames).size() > 0)
                isCurrentSection = ((List)currentSectionsNames).contains(sectionName);
        }
        else{
            String currentSectionName = StringUtil.trim(getRequestInfo().getCurrentSection());
            
            isCurrentSection = sectionName.equals(currentSectionName);
        }
        
        if(style.length() > 0 || !isCurrentSection || height.length() > 0){
            print(" style=\"");
            
            if(height.length() > 0){
                print("height: ");
                print(height);
                
                if(!height.endsWith(";"))
                    print(";");
            }
            
            if(!isCurrentSection && !sectionTag.focusWhen()){
                print(" display: ");
                print(DisplayType.NONE);
                print(";");
            }
            
            if(style.length() > 0){
                if(!isCurrentSection)
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
        setComponentType(ComponentType.ACCORDION);

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
 
        setSectionHeaderStyleClass("");
        setSectionHeaderStyle("");
        setSectionContentStyleClass("");
        setSectionContentStyle("");
        setSectionsTags(null);
    }
}