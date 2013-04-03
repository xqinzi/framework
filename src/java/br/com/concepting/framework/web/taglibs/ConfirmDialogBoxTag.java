package br.com.concepting.framework.web.taglibs;

import br.com.concepting.framework.web.action.types.ActionType;

/**
 * Classe que define o componente visual para uma caixa de confirmação.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class ConfirmDialogBoxTag extends MessageBoxTag{
    private String  onConfirmActionUpdateViews        = "";
    private String  onConfirmActionValidateProperties = "";
    private Boolean onConfirmActionValidate           = false;
    private String  onConfirmActionForwardOnFail      = "";
    private String  onConfirmActionForward            = "";
    private String  onConfirmAction                   = "";
    private String  onConfirm                         = "";
    
    public String getOnConfirmActionUpdateViews(){
        return onConfirmActionUpdateViews;
    }

    public void setOnConfirmActionUpdateViews(String onConfirmActionUpdateViews){
        this.onConfirmActionUpdateViews = onConfirmActionUpdateViews;
    }

    public String getOnConfirmActionValidateProperties(){
        return onConfirmActionValidateProperties;
    }

    public void setOnConfirmActionValidateProperties(String onConfirmActionValidateProperties){
        this.onConfirmActionValidateProperties = onConfirmActionValidateProperties;
    }

    public Boolean getOnConfirmActionValidate(){
        return onConfirmActionValidate;
    }

    public void setOnConfirmActionValidate(Boolean onConfirmActionValidate){
        this.onConfirmActionValidate = onConfirmActionValidate;
    }

    public String getOnConfirmActionForwardOnFail(){
        return onConfirmActionForwardOnFail;
    }

    public void setOnConfirmActionForwardOnFail(String onConfirmActionForwardOnFail){
        this.onConfirmActionForwardOnFail = onConfirmActionForwardOnFail;
    }

    public String getOnConfirmActionForward(){
        return onConfirmActionForward;
    }

    public void setOnConfirmActionForward(String onConfirmActionForward){
        this.onConfirmActionForward = onConfirmActionForward;
    }

    /**
     * Retorn a ação a ser executada ao confirmar a operação.
     * 
     * @return String que define a ação a ser executada.
     */
    public String getOnConfirmAction(){
        return onConfirmAction;
    }

    /**
     * Define a ação a ser executada ao confirmar a operação.
     * 
     * @param onConfirmAction Constante que define a ação a ser executada.
     */
    protected void setOnConfirmAction(ActionType onConfirmAction){
        if(onConfirmAction != null)
            this.onConfirmAction = onConfirmAction.getMethod();
        else
            this.onConfirmAction = "";
    }

    /**
     * Define a ação a ser executada ao confirmar a operação.
     * 
     * @param onConfirmAction String que define a ação a ser executada.
     */
    public void setOnConfirmAction(String onConfirmAction){
        this.onConfirmAction = onConfirmAction;
    }

    /**
     * Retorna o evento de confirmação.
     * 
     * @return String contendo o evento de confirmação.
     */
    public String getOnConfirm(){
        return onConfirm;
    }

    /**
     * Define o evento de confirmação.
     * 
     * @param onConfirm String contendo o evento de confirmação.
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
                confirmContent.append("; ");
        }

        ConfirmButtonTag confirmButtonTag = new ConfirmButtonTag();
        
        confirmButtonTag.setPageContext(pageContext);
        confirmButtonTag.setOnClick(confirmContent.toString());
        confirmButtonTag.setAction(onConfirmAction);
        confirmButtonTag.setForward(onConfirmActionForward);
        confirmButtonTag.setForwardOnFail(onConfirmActionForwardOnFail);
        confirmButtonTag.setValidate(onConfirmActionValidate);
        confirmButtonTag.setValidateProperties(onConfirmActionValidateProperties);
        confirmButtonTag.setUpdateViews(onConfirmActionUpdateViews);
        confirmButtonTag.doStartTag();
        confirmButtonTag.doEndTag();

        CloseButtonTag closeButtonTag = new CloseButtonTag();

        closeButtonTag.setPageContext(pageContext);
        closeButtonTag.setParent(this);
        closeButtonTag.doStartTag();
        closeButtonTag.doEndTag();
    }
    
    /**
     * @see br.com.concepting.framework.web.taglibs.MessageBoxTag#clearAttributes()
     */
    protected void clearAttributes(){
        super.clearAttributes();
        
        setOnConfirmActionValidateProperties("");
        setOnConfirmActionValidate(false);
        setOnConfirmActionUpdateViews("");
        setOnConfirmActionForwardOnFail("");
        setOnConfirmActionForward("");
        setOnConfirmAction("");
        setOnConfirm("");
    }
}
