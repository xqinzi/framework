package br.com.concepting.framework.web.taglibs;

import br.com.concepting.framework.web.action.types.ActionType;

/**
 * Classe que define o componente visual para uma caixa de confirma��o.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class ConfirmDialogBoxTag extends MessageBoxTag{
    private ActionType onConfirmAction = null;
    private String     onConfirm       = "";
    private String     updateViews     = "";
    
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
     * Retorn a a��o a ser executada ao confirmar a opera��o.
     * 
     * @return Constante que define a a��o a ser executada.
     */
    public ActionType getOnConfirmAction(){
        return onConfirmAction;
    }

    /**
     * Define a a��o a ser executada ao confirmar a opera��o.
     * 
     * @param onConfirmAction Constante que define a a��o a ser executada.
     */
    public void setOnConfirmAction(ActionType onConfirmAction){
        this.onConfirmAction = onConfirmAction;
    }

    /**
     * Define a a��o a ser executada ao confirmar a opera��o.
     * 
     * @param onConfirmAction String que define a a��o a ser executada.
     */
    public void setOnConfirmAction(String onConfirmAction){
        this.onConfirmAction = ActionType.valueOf(onConfirmAction.toUpperCase());
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
        StringBuilder confirmContent = new StringBuilder();

        confirmContent.append("hideDialogBox('");
        confirmContent.append(getName());
        confirmContent.append("');");

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
        closeButtonTag.doStartTag();
        closeButtonTag.doEndTag();
    }
}
