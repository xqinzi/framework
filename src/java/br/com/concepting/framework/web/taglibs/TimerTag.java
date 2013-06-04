package br.com.concepting.framework.web.taglibs;

import br.com.concepting.framework.util.StringUtil;

public class TimerTag extends BaseActionFormElementTag{
    private Integer seconds            = 0;
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

    public Integer getSeconds(){
        return seconds;
    }

    public void setSeconds(Integer seconds){
        this.seconds = seconds;
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
    
    public void render() throws Throwable{
        String name = getName();

        if(showCountdown){
            println("<table>");
            println("<tr>");
            print("<td id=\"");
            print(name);
            println(".timer\">");
            println(seconds);
            println("</td>");
            println("</tr>");
            println("</table>");
        }
        
        ScriptTag scriptTag = new ScriptTag();
        
        scriptTag.setPageContext(pageContext);
        
        StringBuilder content = new StringBuilder();
        
        content.append("function ");
        content.append(name);
        content.append("TimerDisplay(){");
        content.append("var countdownObject = document.getElementById('");
        content.append(name);
        content.append("');");
        content.append(StringUtil.getLineBreak());
        content.append(StringUtil.getLineBreak());
        content.append("if(countdownObject) countdownObject.innerHTML = parseInt(countdownObject.innerHTML) - 1;");
        content.append("};");
        content.append(StringUtil.getLineBreak());
        content.append("function ");
        content.append(name);
        content.append("TimerAction(){");
        content.append(StringUtil.getLineBreak());
        
        String actionForm = getActionForm();
                
        if(actionForm.length() == 0){
            content.append(action);
            
            if(!action.endsWith(";"))
                content.append(";");
        }
        else{
            content.append("document.");
            content.append(actionForm);
            content.append(".action.value = '");
            content.append(action);
            content.append(";");
            content.append(StringUtil.getLineBreak());
            
            if(forward.length() > 0){
                content.append("document.");
                content.append(actionForm);
                content.append(".forward.value = '");
                content.append(forward);
                content.append("';");
                content.append(StringUtil.getLineBreak());
            }

            if(forwardOnFail.length() > 0){
                content.append("document.");
                content.append(actionForm);
                content.append(".forwardOnFail.value = '");
                content.append(forwardOnFail);
                content.append("';");
                content.append(StringUtil.getLineBreak());
            }
            
            if(updateViews.length() > 0){
                content.append("document.");
                content.append(actionForm);
                content.append(".updateViews.value = '");
                content.append(updateViews);
                content.append("';");
                content.append(StringUtil.getLineBreak());
            }
            
            content.append("document.");
            content.append(actionForm);
            content.append(".validate.value = ");
            content.append(validate);
            content.append(";");
            content.append(StringUtil.getLineBreak());
            
            if(validateProperties.length() > 0){
                content.append("document.");
                content.append(actionForm);
                content.append(".validateProperties.value = '");
                content.append(validateProperties);
                content.append("';");
                content.append(StringUtil.getLineBreak());
            }
            
            content.append("submitForm(document.");
            content.append(actionForm);
            content.append(");");
            content.append(StringUtil.getLineBreak());
        }
        
        content.append("}");
        content.append(StringUtil.getLineBreak());
        content.append(StringUtil.getLineBreak());
        content.append("setInterval(");
        content.append(name);
        content.append("TimerAction, ");
        content.append(seconds * 1000);
        content.append(");");
        content.append(StringUtil.getLineBreak());
        content.append("setInterval(");
        content.append(name);
        content.append("TimerDisplay, ");
        content.append(1000);
        content.append(");");
        content.append(StringUtil.getLineBreak());

        scriptTag.setContent(content.toString());
        scriptTag.doStartTag();
        scriptTag.doEndTag();
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
        setSeconds(0);
        setShowCountdown(true);
    }
}