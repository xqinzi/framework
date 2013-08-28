package br.com.concepting.framework.ui.taglibs;

import br.com.concepting.framework.controller.action.types.ActionType;

/**
 * Classe que define o componente visual para uma caixa de confirma��o.
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
    
    /**
     * Retorna os identificadores das views que ser�o atualizadas ap�s o processamento da a��o de confirma��o.
     * 
     * @return String contendo os identificadores das views.
     */
    public String getOnConfirmActionUpdateViews(){
        return onConfirmActionUpdateViews;
    }

    /**
     * Define os identificadores das views que ser�o atualizadas ap�s o processamento da a��o de confirma��o.
     * 
     * @param onConfirmActionUpdateViews String contendo os identificadores das views.
     */
    public void setOnConfirmActionUpdateViews(String onConfirmActionUpdateViews){
        this.onConfirmActionUpdateViews = onConfirmActionUpdateViews;
    }

    /**
     * Retorna as propriedades do modelo de dados do formul�rio que dever�o ser validadas na execu��o da a��o de confirma��o.
     * 
     * @return String contendo os identificadores das propriedades.
     */
    public String getOnConfirmActionValidateProperties(){
        return onConfirmActionValidateProperties;
    }

    /**
     * Define as propriedades do modelo de dados do formul�rio que dever�o ser validadas na execu��o da a��o de confirma��o.
     * 
     * @param onConfirmActionValidateProperties String contendo os identificadores das propriedades.
     */
    public void setOnConfirmActionValidateProperties(String onConfirmActionValidateProperties){
        this.onConfirmActionValidateProperties = onConfirmActionValidateProperties;
    }

    /**
     * Indica se o modelo de dados do formul�rio deve ser validado na execu��o da a��o de confirma��o.
     * 
     * @return True/False.
     */
    public Boolean getOnConfirmActionValidate(){
        return onConfirmActionValidate;
    }

    /**
     * Define se o modelo de dados do formul�rio deve ser validado na execu��o da a��o de confirma��o.
     * 
     * @param onConfirmActionValidate True/False.
     */
    public void setOnConfirmActionValidate(Boolean onConfirmActionValidate){
        this.onConfirmActionValidate = onConfirmActionValidate;
    }

    /**
     * Retorna o identificador do redirecionamento em caso de falha ap�s a execu��o da a��o de confirma��o.
     * 
     * @return String contendo o identificador do redirecionamento.
     */
    public String getOnConfirmActionForwardOnFail(){
        return onConfirmActionForwardOnFail;
    }

    /**
     * Define o identificador do redirecionamento em caso de falha ap�s a execu��o da a��o de confirma��o.
     * 
     * @param onConfirmActionForwardOnFail String contendo o identificador do redirecionamento.
     */
    public void setOnConfirmActionForwardOnFail(String onConfirmActionForwardOnFail){
        this.onConfirmActionForwardOnFail = onConfirmActionForwardOnFail;
    }

    /**
     * Retorna o identificador do redirecionamento ap�s a execu��o da a��o de confirma��o.
     * 
     * @return String contendo o identificador do redirecionamento.
     */
    public String getOnConfirmActionForward(){
        return onConfirmActionForward;
    }

    /**
     * Define o identificador do redirecionamento ap�s a execu��o da a��o de confirma��o.
     * 
     * @param onConfirmActionForward String contendo o identificador do redirecionamento.
     */
    public void setOnConfirmActionForward(String onConfirmActionForward){
        this.onConfirmActionForward = onConfirmActionForward;
    }

    /**
     * Retorn a a��o a ser executada ao confirmar a opera��o.
     * 
     * @return String que define a a��o a ser executada.
     */
    public String getOnConfirmAction(){
        return onConfirmAction;
    }

    /**
     * Define a a��o a ser executada ao confirmar a opera��o.
     * 
     * @param onConfirmAction Constante que define a a��o a ser executada.
     */
    protected void setOnConfirmAction(ActionType onConfirmAction){
        if(onConfirmAction != null)
            this.onConfirmAction = onConfirmAction.getMethod();
        else
            this.onConfirmAction = "";
    }

    /**
     * Define a a��o a ser executada ao confirmar a opera��o.
     * 
     * @param onConfirmAction String que define a a��o a ser executada.
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
     * @see br.com.concepting.framework.ui.taglibs.MessageBoxTag#initialize()
     */
    protected void initialize() throws Throwable{
        super.initialize();
        
        setShowOnLoad(false);
        setModal(true);
    }
    
    /**
     * @see br.com.concepting.framework.ui.taglibs.DialogBoxTag#renderButtons()
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
     * @see br.com.concepting.framework.ui.taglibs.MessageBoxTag#clearAttributes()
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
