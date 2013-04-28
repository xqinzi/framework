package br.com.concepting.framework.security.web;

import java.io.IOException;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.concepting.framework.model.SystemSessionModel;
import br.com.concepting.framework.resource.SystemResource;
import br.com.concepting.framework.resource.SystemResourceLoader;
import br.com.concepting.framework.security.constants.SecurityConstants;
import br.com.concepting.framework.security.exceptions.LoginSessionExpiredException;
import br.com.concepting.framework.security.exceptions.PermissionDeniedException;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.security.resource.SecurityResource;
import br.com.concepting.framework.security.resource.SecurityResourceLoader;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.web.SystemController;
import br.com.concepting.framework.web.form.ActionFormMessageController;

/**
 * Classe que define o filtro de segurança a ser executado ao acessar uma URL.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class SecurityFilter implements Filter{
    private   SystemResource              systemResource              = null;   
    private   SecurityResource            securityResource            = null;
    protected SystemController            systemController            = null;
    protected ActionFormMessageController actionFormMessageController = null;
    protected SecurityController          securityController          = null;
    
    /**
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy(){
    }
    
    /**
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig filterConfig) throws ServletException{
        try{
            SystemResourceLoader systemResourceLoader = new SystemResourceLoader();
            
            systemResource = systemResourceLoader.getDefault();
            
            SecurityResourceLoader securityLoader = new SecurityResourceLoader();
            
            securityResource = securityLoader.getDefault();
        }
        catch(Throwable e){
            throw new ServletException(e);
        }
    }
    
    /**
     * Efetua a validação do filtro. Não serão considerados as máscaras definidas no atributo 
     * 'exclude-patterns'.
     * 
     * @return True/False.
     */
    protected Boolean validate(){
        Collection<String> excludePatterns = securityResource.getExcludePatterns();
        Boolean            excludeUrl      = false;
        
        if(excludePatterns != null && excludePatterns.size() > 0){
            StringBuilder pattern  = null;
            Pattern       regex    = null;
            Matcher       matcher  = null;
            
            for(String excludePattern : excludePatterns){
                if(pattern == null)
                    pattern = new StringBuilder();
                else
                    pattern.delete(0, pattern.length());

                if(excludePattern.startsWith("/")){
                    pattern.append("\\");
                    pattern.append(systemController.getContextPath());
                    pattern.append("\\");
                }
                
                pattern.append(StringUtil.toRegex(excludePattern));
                
                regex   = Pattern.compile(pattern.toString());
                matcher = regex.matcher(systemController.getRequest().getRequestURI());
                
                if(matcher.matches()){
                    excludeUrl = true;
              
                    break;
                }
            }
        }
        
        Boolean result = true;
        
        if(!excludeUrl){
            if(securityController.isLoginSessionExpired()){
                result = false;
                
                actionFormMessageController.addMessage(new LoginSessionExpiredException());
                
                systemController.removeCookie(SecurityConstants.LOGIN_SESSION_KEY);
            }
            else{
                if(!securityController.isLoginSessionAuthenticated()){
                    result = false;
                         
                    actionFormMessageController.addMessage(new PermissionDeniedException());
                }
            }
        }
        
        return result;
    }
    
    /**
     * Efetua o processamento do filtro.
     * 
     * @throws Throwable
     */
    protected void process() throws Throwable{
        systemController.forward(systemResource.getLoginPage());
    }
    
    /**
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException{
        systemController            = new SystemController((HttpServletRequest)request, (HttpServletResponse)response);
        actionFormMessageController = systemController.getActionFormMessageController();
        securityController          = systemController.getSecurityController();
        
        LoginSessionModel loginSession = securityController.getLoginSession();

        if(loginSession != null){
            SystemSessionModel systemSession = loginSession.getSystemSession();

            systemSession.setId(systemController.getSession().getId());
            systemSession.setIp(systemController.getRequest().getRemoteAddr());
            systemSession.setHostName(systemController.getRequest().getRemoteHost());
            
            loginSession.setSystemSession(systemSession);
            
            securityController.setLoginSession(loginSession);
        }
        
        if(!validate()){
            try{
                process();
                
                return;
            }
            catch(Throwable e){
                throw new ServletException(e);
            }
        }
        
        filterChain.doFilter(request, response);
    }
}