package br.com.concepting.framework.web.taglibs;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.util.MethodUtil;
import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.ComponentType;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;

public class TimerTag extends BasePropertyTag{
    private Boolean showCountdown      = true;
    private String  action             = "";
    private String  forward            = "";
    private String  forwardOnFail      = "";
    private String  updateViews        = "";
    private Boolean validate           = false;
    private String  validateProperties = "";
    
    public Boolean getShowCountdown(){
        return showCountdown;
    }

    public void setShowCountdown(Boolean showCountdown){
        this.showCountdown = showCountdown;
    }

    public String getAction(){
        return action;
    }
    
    public void setAction(String action){
        this.action = action;
    }
    
    public String getForward(){
        return forward;
    }
    
    public void setForward(String forward){
        this.forward = forward;
    }
    
    public String getForwardOnFail(){
        return forwardOnFail;
    }
    
    public void setForwardOnFail(String forwardOnFail){
        this.forwardOnFail = forwardOnFail;
    }
    
    public String getUpdateViews(){
        return updateViews;
    }
    
    public void setUpdateViews(String updateViews){
        this.updateViews = updateViews;
    }
    
    public Boolean getValidate(){
        return validate;
    }
    
    public void setValidate(Boolean validate){
        this.validate = validate;
    }
    
    public String getValidateProperties(){
        return validateProperties;
    }

    public void setValidateProperties(String validateProperties){
        this.validateProperties = validateProperties;
    }
    
    protected void initialize() throws Throwable{
        setComponentType(ComponentType.TIMER);
        
        super.initialize();
        
        setShowLabel(showCountdown);

        Number  value   = getValue();
        Integer compare = (Integer)MethodUtil.invokeMethod(value, "compareTo", 0);
        
        if(compare <= 0)
            setValue(0);
    }
    
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
                print(StringUtil.trim(getStyleClass()));
                print("\"");
                
                String style = StringUtil.trim(getStyle());
                
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
                content.append("\tvar timerObject     = document.getElementById(\"");
                content.append(name);
                content.append("\");");
                content.append(StringUtil.getLineBreak());
                content.append("\tvar countdownObject = document.getElementById(\"");
                content.append(name);
                content.append(".");
                content.append(AttributeConstants.TIMER_KEY);
                content.append("\");");
                content.append(StringUtil.getLineBreak());
                content.append(StringUtil.getLineBreak());
                content.append("\tif(timerObject)");
                content.append(StringUtil.getLineBreak());
                content.append("\t\ttimerObject.value = parseInt(timerObject.value) - 1;");
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
                
                String actionForm = getActionForm();
                        
                if(actionForm.length() == 0){
                    content.append(action);
                    
                    if(!action.endsWith(";"))
                        content.append(";");
                }
                else{
                    content.append("var timerObject = document.getElementById(\"");
                    content.append(name);
                    content.append("\");");
                    content.append(StringUtil.getLineBreak());
                    content.append(StringUtil.getLineBreak());
                    content.append("\tif(timerObject)");
                    content.append(StringUtil.getLineBreak());
                    content.append("\t\ttimerObject.value = parseInt(timerObject.value) - 1;");
                    content.append(StringUtil.getLineBreak());
                    content.append(StringUtil.getLineBreak());
                    content.append("\tdocument.");
                    content.append(actionForm);
                    content.append(".action.value = '");
                    content.append(action);
                    content.append("';");
                    content.append(StringUtil.getLineBreak());
                    
                    if(forward.length() > 0){
                        content.append("\tdocument.");
                        content.append(actionForm);
                        content.append(".forward.value = '");
                        content.append(forward);
                        content.append("';");
                        content.append(StringUtil.getLineBreak());
                    }
        
                    if(forwardOnFail.length() > 0){
                        content.append("\tdocument.");
                        content.append(actionForm);
                        content.append(".forwardOnFail.value = '");
                        content.append(forwardOnFail);
                        content.append("';");
                        content.append(StringUtil.getLineBreak());
                    }
                    
                    if(updateViews.length() > 0){
                        content.append("\tdocument.");
                        content.append(actionForm);
                        content.append(".updateViews.value = '");
                        content.append(updateViews);
                        content.append("';");
                        content.append(StringUtil.getLineBreak());
                    }
                    
                    content.append("\tdocument.");
                    content.append(actionForm);
                    content.append(".validate");
                    
                    if(isForSearch())
                        content.append("Search");
                    
                    content.append("Model.value = ");
                    content.append(validate);
                    content.append(";");
                    content.append(StringUtil.getLineBreak());
                    
                    if(validateProperties.length() > 0){
                        content.append("\tdocument.");
                        content.append(actionForm);
                        content.append(".validateProperties.value = '");
                        content.append(validateProperties);
                        content.append("';");
                        content.append(StringUtil.getLineBreak());
                    }
                    
                    content.append("\tsubmitForm(document.");
                    content.append(actionForm);
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
     * @see br.com.concepting.framework.web.taglibs.BaseActionFormElementTag#clearAttributes()
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