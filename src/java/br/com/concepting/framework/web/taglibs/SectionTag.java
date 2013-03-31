package br.com.concepting.framework.web.taglibs;

import javax.servlet.jsp.tagext.BodyContent;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.ComponentType;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;

/**
 * Classe que define o componente visual de uma seção.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class SectionTag extends BaseActionFormElementTag{
    private String  headerLabelStyleClass            = "";
    private String  headerLabelStyle                 = "";
    private String  headerStyleClass                 = "";
    private String  headerStyle                      = "";
    private String  contentStyleClass                = "";
    private String  contentStyle                     = "";
    private String  onSelect                         = "";
    private String  onSelectAction                   = "";
    private String  onSelectActionForward            = "";
    private String  onSelectActionForwardOnFail      = "";
    private String  onSelectActionUpdateViews        = "";
    private Boolean onSelectActionValidate           = false;
    private String  onSelectActionValidateProperties = "";
    private String  content                          = null;
    
    /**
     * Retorna o identificador da ação do evento de seleção.
     * 
     * @return String contendo o identificador da ação.
     */
    public String getOnSelectAction(){
        return onSelectAction;
    }

    /**
     * Define o identificador da ação do evento de seleção.
     * 
     * @param onSelectAction String contendo o identificador da ação.
     */
    public void setOnSelectAction(String onSelectAction){
        this.onSelectAction = onSelectAction;
    }

    /**
     * Retorna o identificador do redirecionamento da ação do evento de seleção.
     * 
     * @return String contendo o identificador do redirecionamento da ação.
     */
    public String getOnSelectActionForward(){
        return onSelectActionForward;
    }

    /**
     * Define o identificador do redirecionamento da ação do evento de seleção.
     * 
     * @param onSelectActionForward String contendo o identificador do redirecionamento da ação.
     */
    public void setOnSelectActionForward(String onSelectActionForward){
        this.onSelectActionForward = onSelectActionForward;
    }

    /**
     * Retorna o identificador do redirecionamento, em caso de falha, da ação do evento de seleção.
     * 
     * @return String contendo o identificador do redirecionamento da ação.
     */
    public String getOnSelectActionForwardOnFail(){
        return onSelectActionForwardOnFail;
    }

    /**
     * Define o identificador do redirecionamento, em caso de falha, da ação do evento de seleção.
     * 
     * @param onSelectActionForwardOnFail String contendo o identificador do redirecionamento da ação.
     */
    public void setOnSelectActionForwardOnFail(String onSelectActionForwardOnFail){
        this.onSelectActionForwardOnFail = onSelectActionForwardOnFail;
    }

    /**
     * Retorna o identificador das views a serem atualizadas após a execução da ação do evento de seleção.
     * 
     * @return String contendo o identificador das views.
     */
    public String getOnSelectActionUpdateViews(){
        return onSelectActionUpdateViews;
    }

    /**
     * Define o identificador das views a serem atualizadas após a execução da ação do evento de seleção.
     * 
     * @param onSelectActionUpdateViews String contendo o identificador das views.
     */
    public void setOnSelectActionUpdateViews(String onSelectActionUpdateViews){
        this.onSelectActionUpdateViews = onSelectActionUpdateViews;
    }

    /**
     * Indica se o modelo de dados do formulário deve ser validado na execução da ação do evento de seleção.
     * 
     * @return True/False.
     */
    public Boolean getOnSelectActionValidate(){
        return onSelectActionValidate;
    }

    /**
     * Define se o modelo de dados do formulário deve ser validado na execução da ação do evento de seleção.
     * 
     * @param onSelectActionValidate True/False.
     */
    public void setOnSelectActionValidate(Boolean onSelectActionValidate){
        this.onSelectActionValidate = onSelectActionValidate;
    }

    /**
     * Retorna as propriedades do modelo de dados do formulário que devem ser validadas na execução da ação do evento de seleção.
     * 
     * @return String contendo os identificadores das propriedades do modelo de dados do formulário.
     */
    public String getOnSelectActionValidateProperties(){
        return onSelectActionValidateProperties;
    }

    /**
     * Define as propriedades do modelo de dados do formulário que devem ser validadas na execução da ação do evento de seleção.
     * 
     * @param onSelectActionValidateProperties String contendo os identificadores das propriedades do modelo de dados do formulário.
     */
    public void setOnSelectActionValidateProperties(String onSelectActionValidateProperties){
        this.onSelectActionValidateProperties = onSelectActionValidateProperties;
    }

    /**
     * Retorna o estilo CSS para o label do cabeçalho da seção.
     * 
     * @return String contendo o identificador do estilo CSS.
     */
    public String getHeaderLabelStyleClass(){
        return headerLabelStyleClass;
    }

    /**
     * Define o estilo CSS para o label do cabeçalho da seção.
     * 
     * @param headerLabelStyleClass String contendo o identificador do estilo CSS.
     */
    public void setHeaderLabelStyleClass(String headerLabelStyleClass){
        this.headerLabelStyleClass = headerLabelStyleClass;
    }

    /**
     * Retorna o estilo CSS para o label do cabeçalho da seção.
     * 
     * @return String contendo o identificador do estilo CSS.
     */
    public String getHeaderLabelStyle(){
        return headerLabelStyle;
    }

    /**
     * Define o estilo CSS para o label do cabeçalho da seção.
     * 
     * @param headerLabelStyle String contendo o identificador do estilo CSS.
     */
    public void setHeaderLabelStyle(String headerLabelStyle){
        this.headerLabelStyle = headerLabelStyle;
    }

    /**
     * Retorna o estilo CSS para o cabeçalho da seção.
     * 
     * @return String contendo o identificador do estilo CSS.
     */
    public String getHeaderStyleClass(){
        return headerStyleClass;
    }

    /**
     * Define o estilo CSS para o cabeçalho da seção.
     * 
     * @param headerStyleClass String contendo o identificador do estilo CSS.
     */
    public void setHeaderStyleClass(String headerStyleClass){
        this.headerStyleClass = headerStyleClass;
    }

    /**
     * Retorna o estilo CSS para o cabeçalho da seção.
     * 
     * @return String contendo o identificador do estilo CSS.
     */
    public String getHeaderStyle(){
        return headerStyle;
    }

    /**
     * Define o estilo CSS para o cabeçalho da seção.
     * 
     * @param headerStyle String contendo o identificador do estilo CSS.
     */
    public void setHeaderStyle(String headerStyle){
        this.headerStyle = headerStyle;
    }

    /**
     * Retorna o estilo CSS para o conteúdo da seção.
     * 
     * @return String contendo o identificador do estilo CSS.
     */
    public String getContentStyleClass(){
        return contentStyleClass;
    }

    /**
     * Define o estilo CSS para o conteúdo da seção.
     * 
     * @param contentStyleClass String contendo o identificador do estilo CSS.
     */
    public void setContentStyleClass(String contentStyleClass){
        this.contentStyleClass = contentStyleClass;
    }

    /**
     * Retorna o estilo CSS para o conteúdo da seção.
     * 
     * @return String contendo o identificador do estilo CSS.
     */
    public String getContentStyle(){
        return contentStyle;
    }

    /**
     * Define o estilo CSS para o conteúdo da seção.
     * 
     * @param contentStyle String contendo o identificador do estilo CSS.
     */
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
        setComponentType(ComponentType.SECTION);

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
    
        if(onSelectAction.length() > 0){
            String actionForm = getActionForm();
            
            if(actionForm.length() > 0){
                StringBuilder onSelectContent = new StringBuilder();
    
                if(onSelect.length() > 0){
                    onSelectContent.append(onSelect);
                    
                    if(!onSelect.endsWith(";"))
                        onSelectContent.append(";");
                    
                    onSelectContent.append(" ");
                }
                
                onSelectContent.append("document.");
                onSelectContent.append(actionForm);
                onSelectContent.append(".");
                
                SearchPropertiesGroupTag searchPropertiesTag = (SearchPropertiesGroupTag)findAncestorWithClass(this, SearchPropertiesGroupTag.class);
                
                if(searchPropertiesTag != null)
                    onSelectContent.append(AttributeConstants.VALIDATE_SEARCH_MODEL_KEY);
                else
                    onSelectContent.append(AttributeConstants.VALIDATE_MODEL_KEY);
                
                onSelectContent.append(".value = ");
                onSelectContent.append(onSelectActionValidate);
                onSelectContent.append(";");
                
                if(onSelectActionValidateProperties.length() > 0){
                    onSelectContent.append(" document.");
                    onSelectContent.append(actionForm);
                    onSelectContent.append(".");
                    onSelectContent.append(AttributeConstants.VALIDATE_PROPERTIES_KEY);
                    onSelectContent.append(".value = '");
                    onSelectContent.append(onSelectActionValidateProperties);
                    onSelectContent.append("'; ");
                }
                
                if(onSelectActionForward.length() > 0){
                    onSelectContent.append("document.");
                    onSelectContent.append(actionForm);
                    onSelectContent.append(".");
                    onSelectContent.append(AttributeConstants.FORWARD_KEY);
                    onSelectContent.append(".value = '");
                    onSelectContent.append(onSelectActionForward);
                    onSelectContent.append("; ");
                }
                
                if(onSelectActionForwardOnFail.length() > 0){
                    onSelectContent.append("document.");
                    onSelectContent.append(actionForm);
                    onSelectContent.append(".");
                    onSelectContent.append(AttributeConstants.FORWARD_ON_FAIL_KEY);
                    onSelectContent.append(".value = '");
                    onSelectContent.append(onSelectActionForwardOnFail);
                    onSelectContent.append("; ");
                }
    
                if(onSelectActionUpdateViews.length() > 0){
                    onSelectContent.append("document.");
                    onSelectContent.append(actionForm);
                    onSelectContent.append(".");
                    onSelectContent.append(AttributeConstants.UPDATE_VIEWS_KEY);
                    onSelectContent.append(".value = '");
                    onSelectContent.append(onSelectActionUpdateViews);
                    onSelectContent.append("; ");
                }
    
                onSelectContent.append("document.");
                onSelectContent.append(actionForm);
                onSelectContent.append(".");
                onSelectContent.append(AttributeConstants.ACTION_KEY);
                onSelectContent.append(".value = '");
                onSelectContent.append(onSelectAction);
                onSelectContent.append("'; submitForm(document.");
                onSelectContent.append(actionForm);
                onSelectContent.append(");");
                
                onSelect = onSelectContent.toString();
            }
        }
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
        setOnSelectAction("");
        setOnSelectActionForward("");
        setOnSelectActionForwardOnFail("");
        setOnSelectActionUpdateViews("");
        setOnSelectActionValidate(false);
        setOnSelectActionValidateProperties("");
        setContent(null);
    }    
}