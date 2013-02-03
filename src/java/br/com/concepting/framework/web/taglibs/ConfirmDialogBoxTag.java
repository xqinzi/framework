package br.com.concepting.framework.web.taglibs;

/**
 * Classe que define o componente visual para uma caixa de confirma��o.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class ConfirmDialogBoxTag extends MessageBoxTag{
    private String onConfirmAction = "";
    private String onConfirm       = "";
    private String updateViews     = "";
    
    /**
     * Retorna os identificadores das views (separados por v�rgula) a serem atualizadas ap�s o
     * processamento da a��o requisitada.
     * 
     * @return String contendo os identificadores das views.
     */
    public String getUpdateViews(){
        return updateViews;
    }

    /**
     * Define os identificadores das views (separados por v�rgula) a serem atualizadas ap�s o
     * processamento da a��o requisitada.
     * 
     * @param updateViews String contendo os identificadores das views.
     */
    public void setUpdateViews(String updateViews){
        this.updateViews = updateViews;
    }

    /**
     * Retorna o identificador da a��o da confirma��o.
     * 
     * @return String contendo o identificador da a��o.
     */
    public String getOnConfirmAction(){
        return onConfirmAction;
    }

    /**
     * Define o identificador da a��o da confirma��o.
     * 
     * @param onConfirmAction String contendo o identificador da a��o.
     */
    public void setOnConfirmAction(String onConfirmAction){
        this.onConfirmAction = onConfirmAction;
    }

    /**
     * Retorna o evento de confirma��o.
     * 
     * @return String contendo o evento de confirma��o.
     */
    public String getOnConfirm(){
        return onConfirm;
    }

    /**
     * Define o evento de confirma��o.
     * 
     * @param onConfirm String contendo o evento de confirma��o.
     */
    public void setOnConfirm(String onConfirm){
        this.onConfirm = onConfirm;
    }

    /**
     * @see br.com.concepting.framework.web.taglibs.MessageBoxTag#initialize()
     */
    protected void initialize() throws Throwable{
        super.initialize();
        
        setShowOnLoad(false);
        setModal(true);
    }
    
    /**
     * @see br.com.concepting.framework.web.taglibs.DialogBoxTag#renderButtons()
     */
    protected void renderButtons() throws Throwable{
        StringBuilder cancelContent = new StringBuilder();

        cancelContent.append("hideDialogBox('");
        cancelContent.append(getName());
        cancelContent.append("');");
        
        StringBuilder confirmContent = new StringBuilder();
        
        confirmContent.append(cancelContent);

        if(onConfirm.length() > 0){
            confirmContent.append(" ");
            confirmContent.append(onConfirm);
            
            if(!onConfirm.endsWith(";"))
                confirmContent.append(";");
        }

        ConfirmButtonTag confirmButtonTag = new ConfirmButtonTag();
        
        confirmButtonTag.setPageContext(pageContext);
        confirmButtonTag.setOnClick(confirmContent.toString());
        confirmButtonTag.setAction(onConfirmAction);
        confirmButtonTag.setUpdateViews(updateViews);
        confirmButtonTag.doStartTag();
        confirmButtonTag.doEndTag();

        CloseButtonTag closeButtonTag = new CloseButtonTag();

        closeButtonTag.setPageContext(pageContext);
        closeButtonTag.setOnClick(cancelContent.toString());
        closeButtonTag.doStartTag();
        closeButtonTag.doEndTag();
    }
}
