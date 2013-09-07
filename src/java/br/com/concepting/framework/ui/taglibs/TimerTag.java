package br.com.concepting.framework.ui.taglibs;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.controller.action.types.ActionType;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.ui.constants.TaglibConstants;
import br.com.concepting.framework.util.MethodUtil;
import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.ComponentType;
 
/**
 * Classe que define o componente visual timer (temporizador).
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class TimerTag extends BasePropertyTag{
    private Boolean showCountdown      = true;
    private String  action             = "";
    private String  forward            = "";
    private String  forwardOnFail      = "";
    private String  updateViews        = "";
    private Boolean validate           = false;
    private String  validateProperties = "";

    /**
     * Indica se deve ser exibido a contagem regressiva do timer.
     * 
     * @return True/False.
     */
    public Boolean showCountdown(){
        return showCountdown;
    }

    /**
     * Indica se deve ser exibido a contagem regressiva do timer.
     * 
     * @return True/False.
     */
    public Boolean getShowCountdown(){
        return showCountdown();
    }

    /**
     * Define se deve ser exibido a contagem regressiva do timer.
     * 
     * @param showCountdown True/False.
     */
    public void setShowCountdown(Boolean showCountdown){
        this.showCountdown = showCountdown;
    }

    /**
     * Retorna a ação a ser executada.
     * 
     * @return String que define a ação.
     */
    public String getAction(){
        return action;
    }

    /**
     * Define a ação a ser executada.
     * 
     * @param action String que define a ação.
     */
    public void setAction(String action){
        this.action = action;
    }
    
    /**
     * Define a ação a ser executada.
     * 
     * @param action Constante que define a ação.
     */
    protected void setAction(ActionType action){
        if(action != null)
            this.action = action.getMethod();
        else
            this.action = "";
    }

    /**
     * Retorna o identificador do redirecionamento da ação.
     * 
     * @return String contendo o identificador do redirecionamento.
     */
    public String getForward(){
        return forward;
    }

    /**
     * Define o identificador do redirecionamento da ação.
     * 
     * @param forward String contendo o identificador do redirecionamento.
     */
    public void setForward(String forward){
        this.forward = forward;
    }

    /**
     * Retorna o identificador do redirecionamento da ação quando ocorrer erros.
     * 
     * @return String contendo o identificador do redirecionamento.
     */
    public String getForwardOnFail(){
        return forwardOnFail;
    }

    /**
     * Define o identificador do redirecionamento da ação quando ocorrer erros.
     * 
     * @param forwardOnFail String contendo o identificador do redirecionamento.
     */
    public void setForwardOnFail(String forwardOnFail){
        this.forwardOnFail = forwardOnFail;
    }
    
    /**
     * Retorna os identificadores das views (separados por vírgula) a serem atualizadas após o
     * processamento da ação requisitada.
     * 
     * @return String contendo os identificadores das views.
     */
    public String getUpdateViews(){
        return updateViews;
    }

    /**
     * Define os identificadores das views (separados por vírgula) a serem atualizadas após o
     * processamento da ação requisitada.
     * 
     * @param updateViews String contendo os identificadores das views.
     */
    public void setUpdateViews(String updateViews){
        this.updateViews = updateViews;
    }
    
    /**
     * Indica se a ação deve executar a validação dos dados do formulário.
     * 
     * @return True/False.
     */
    public Boolean validate(){
        return validate;
    }
    
    /**
     * Indica se a ação deve executar a validação dos dados do formulário.
     * 
     * @return True/False.
     */
    public Boolean getValidate(){
        return validate();
    }

    /**
     * Define se a ação deve executar a validação dos dados do formulário.
     * 
     * @param validate True/False.
     */
    public void setValidate(Boolean validate){
        this.validate = validate;
    }
    
    /**
     * Retorna uma string delimitada contendo as propriedades a serem validadas.
     * 
     * @return String contendo as propriedades a serem validadas.
     */
    public String getValidateProperties(){
        return validateProperties;
    }

    /**
     * Defime uma string delimitada contendo as propriedades a serem validadas.
     * 
     * @param validateProperties String contendo as propriedades a serem validadas.
     */
    public void setValidateProperties(String validateProperties){
        this.validateProperties = validateProperties;
    }
    
    /**
     * @see br.com.concepting.framework.ui.taglibs.BasePropertyTag#initialize()
     */
    protected void initialize() throws Throwable{
        setComponentType(ComponentType.TIMER);
        
        super.initialize();
        
        setShowLabel(showCountdown);

        Number  value   = getValue();
        Integer compare = (Integer)MethodUtil.invokeMethod(value, "compareTo", 0);
        
        if(compare <= 0)
            setValue(0);
    }
    
    /**
     * @see br.com.concepting.framework.ui.taglibs.BasePropertyTag#renderOpen()
     */
    protected void renderOpen() throws Throwable{
        super.renderOpen();
        
        HiddenPropertyTag propertyTag = new HiddenPropertyTag();
        
        propertyTag.setPageContext(pageContext);
        propertyTag.setActionFormTag(getActionFormTag());
        propertyTag.setName(getName());
        propertyTag.setValue(getValue());
        propertyTag.doStartTag();
        propertyTag.doEndTag();
    }
    
    /**
     * @see br.com.concepting.framework.ui.taglibs.BasePropertyTag#renderBody()
     */
    protected void renderBody() throws Throwable{
        PropertyInfo propertyInfo = getPropertyInfo();
        
        if(propertyInfo == null || !propertyInfo.isNumber()){
            print("<span class=\"");
            print(TaglibConstants.DEFAULT_LABEL_STYLE_CLASS);
            println("\">");
            println(getFormattedValue());
            println("</span>");
        }
        else{
            String  name  = getName();
            Number  value = getValue();
    
            if(showCountdown){
                print("<span id=\"");
                print(name);
                print(".");
                print(AttributeConstants.TIMER_KEY);
                print("\" class=\"");
                print(getStyleClass());
                print("\"");
                
                String style = getStyle();
                
                if(style.length() > 0){
                    print(" style=\"");
                    print(style);
                    print("\"");
                }
                
                println(">");
                println(value);
                println("</span>");
            }

            Integer   compare   = (Integer)MethodUtil.invokeMethod(value, "compareTo", 0);
            ScriptTag scriptTag = new ScriptTag();
            
            scriptTag.setPageContext(pageContext);
            
            StringBuilder content = new StringBuilder();
            
            content.append("clearAllTimers();");
            content.append(StringUtil.getLineBreak());
            
            if(compare > 0){
                content.append(StringUtil.getLineBreak());
                content.append("function ");
                content.append(name);
                content.append("TimerDisplay(){");
                content.append(StringUtil.getLineBreak());
                content.append("\tsetObjectValue(\"");
                content.append(name);
                content.append("\", (parseInt(timerObject.value) - 1));");
                content.append(StringUtil.getLineBreak());
                content.append(StringUtil.getLineBreak());
                content.append("\tvar countdownObject = getObject(\"");
                content.append(name);
                content.append(".");
                content.append(AttributeConstants.TIMER_KEY);
                content.append("\");");
                content.append(StringUtil.getLineBreak());
                content.append(StringUtil.getLineBreak());
                content.append("\tif(countdownObject)");
                content.append(StringUtil.getLineBreak());
                content.append("\t\tcountdownObject.innerHTML = parseInt(countdownObject.innerHTML) - 1;");
                content.append(StringUtil.getLineBreak());
                content.append("}");
                content.append(StringUtil.getLineBreak());
                content.append(StringUtil.getLineBreak());
                content.append("function ");
                content.append(name);
                content.append("TimerAction(){");
                content.append(StringUtil.getLineBreak());
                content.append("\t");
                
                String actionFormName = getActionFormName();
                        
                if(actionFormName.length() == 0){
                    content.append(action);
                    
                    if(!action.endsWith(";"))
                        content.append(";");
                }
                else{
                    content.append("setObjectValue(\"");
                    content.append(name);
                    content.append("\", (parseInt(timerObject.value) - 1));");
                    content.append(StringUtil.getLineBreak());
                    content.append(StringUtil.getLineBreak());
                    content.append("\tdocument.");
                    content.append(actionFormName);
                    content.append(".");
                    content.append(AttributeConstants.ACTION_KEY);
                    content.append(".value = '");
                    content.append(action);
                    content.append("';");
                    content.append(StringUtil.getLineBreak());
                    
                    if(forward.length() > 0){
                        content.append("\tdocument.");
                        content.append(actionFormName);
                        content.append(".");
                        content.append(AttributeConstants.FORWARD_KEY);
                        content.append(".value = '");
                        content.append(forward);
                        content.append("';");
                        content.append(StringUtil.getLineBreak());
                    }
        
                    if(forwardOnFail.length() > 0){
                        content.append("\tdocument.");
                        content.append(actionFormName);
                        content.append(".");
                        content.append(AttributeConstants.FORWARD_ON_FAIL_KEY);
                        content.append(".value = '");
                        content.append(forwardOnFail);
                        content.append("';");
                        content.append(StringUtil.getLineBreak());
                    }
                    
                    if(updateViews.length() > 0){
                        content.append("\tdocument.");
                        content.append(actionFormName);
                        content.append(".");
                        content.append(AttributeConstants.UPDATE_VIEWS_KEY);
                        content.append(".value = '");
                        content.append(updateViews);
                        content.append("';");
                        content.append(StringUtil.getLineBreak());
                    }
                    
                    content.append("\tdocument.");
                    content.append(actionFormName);
                    content.append(".");
                    
                    if(isForSearch())
                        content.append(AttributeConstants.VALIDATE_SEARCH_MODEL_KEY);
                    else
                        content.append(AttributeConstants.VALIDATE_MODEL_KEY);
                    
                    content.append(".value = ");
                    content.append(validate);
                    content.append(";");
                    content.append(StringUtil.getLineBreak());
                    
                    if(validateProperties.length() > 0){
                        content.append("\tdocument.");
                        content.append(actionFormName);
                        content.append(".");
                        content.append(AttributeConstants.VALIDATE_PROPERTIES_KEY);
                        content.append(" = '");
                        content.append(validateProperties);
                        content.append("';");
                        content.append(StringUtil.getLineBreak());
                    }
                    
                    content.append("\tsubmitForm(document.");
                    content.append(actionFormName);
                    content.append(");");
                    content.append(StringUtil.getLineBreak());
                }
                
                content.append("}");
                content.append(StringUtil.getLineBreak());
                content.append(StringUtil.getLineBreak());
                content.append("timers.push(setTimeout(");
                content.append(name);
                content.append("TimerAction, ");
                content.append(NumberUtil.multiply(value, 1000));
                content.append("));");
                content.append(StringUtil.getLineBreak());
                content.append("timers.push(setInterval(");
                content.append(name);
                content.append("TimerDisplay, ");
                content.append(1000);
                content.append("));");
                content.append(StringUtil.getLineBreak());
            }

            scriptTag.setContent(content.toString());
            scriptTag.doStartTag();
            scriptTag.doEndTag();
        }
    }
    
    /**
     * @see br.com.concepting.framework.ui.taglibs.BaseActionFormElementTag#clearAttributes()
     */
    protected void clearAttributes(){
        super.clearAttributes();
        
        setAction("");
        setForward("");
        setForwardOnFail("");
        setUpdateViews("");
        setValidate(false);
        setValidateProperties("");
        setShowCountdown(true);
    }
}