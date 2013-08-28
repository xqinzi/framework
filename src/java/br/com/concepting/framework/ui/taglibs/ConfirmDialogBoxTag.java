package br.com.concepting.framework.ui.taglibs;

import br.com.concepting.framework.controller.action.types.ActionType;

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
    
    /**
     * Retorna os identificadores das views que serão atualizadas após o processamento da ação de confirmação.
     * 
     * @return String contendo os identificadores das views.
     */
    public String getOnConfirmActionUpdateViews(){
        return onConfirmActionUpdateViews;
    }

    /**
     * Define os identificadores das views que serão atualizadas após o processamento da ação de confirmação.
     * 
     * @param onConfirmActionUpdateViews String contendo os identificadores das views.
     */
    public void setOnConfirmActionUpdateViews(String onConfirmActionUpdateViews){
        this.onConfirmActionUpdateViews = onConfirmActionUpdateViews;
    }

    /**
     * Retorna as propriedades do modelo de dados do formulário que deverão ser validadas na execução da ação de confirmação.
     * 
     * @return String contendo os identificadores das propriedades.
     */
    public String getOnConfirmActionValidateProperties(){
        return onConfirmActionValidateProperties;
    }

    /**
     * Define as propriedades do modelo de dados do formulário que deverão ser validadas na execução da ação de confirmação.
     * 
     * @param onConfirmActionValidateProperties String contendo os identificadores das propriedades.
     */
    public void setOnConfirmActionValidateProperties(String onConfirmActionValidateProperties){
        this.onConfirmActionValidateProperties = onConfirmActionValidateProperties;
    }

    /**
     * Indica se o modelo de dados do formulário deve ser validado na execução da ação de confirmação.
     * 
     * @return True/False.
     */
    public Boolean getOnConfirmActionValidate(){
        return onConfirmActionValidate;
    }

    /**
     * Define se o modelo de dados do formulário deve ser validado na execução da ação de confirmação.
     * 
     * @param onConfirmActionValidate True/False.
     */
    public void setOnConfirmActionValidate(Boolean onConfirmActionValidate){
        this.onConfirmActionValidate = onConfirmActionValidate;
    }

    /**
     * Retorna o identificador do redirecionamento em caso de falha após a execução da ação de confirmação.
     * 
     * @return String contendo o identificador do redirecionamento.
     */
    public String getOnConfirmActionForwardOnFail(){
        return onConfirmActionForwardOnFail;
    }

    /**
     * Define o identificador do redirecionamento em caso de falha após a execução da ação de confirmação.
     * 
     * @param onConfirmActionForwardOnFail String contendo o identificador do redirecionamento.
     */
    public void setOnConfirmActionForwardOnFail(String onConfirmActionForwardOnFail){
        this.onConfirmActionForwardOnFail = onConfirmActionForwardOnFail;
    }

    /**
     * Retorna o identificador do redirecionamento após a execução da ação de confirmação.
     * 
     * @return String contendo o identificador do redirecionamento.
     */
    public String getOnConfirmActionForward(){
        return onConfirmActionForward;
    }

    /**
     * Define o identificador do redirecionamento após a execução da ação de confirmação.
     * 
     * @param onConfirmActionForward String contendo o identificador do redirecionamento.
     */
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
