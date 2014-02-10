package br.com.concepting.framework.security.resource;

import java.util.Collection;
import java.util.LinkedList;

import br.com.concepting.framework.resource.XmlResourceLoader;
import br.com.concepting.framework.resource.exceptions.InvalidResourceException;
import br.com.concepting.framework.security.constants.SecurityConstants;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Classe responsável pela leitura/manipulação das configurações de segurança.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class SecurityResourceLoader extends XmlResourceLoader<SecurityResource>{
    /**
     * Construtor - Inicializa classe de leitura/manipulação do arquivo de configurações de segurança 
     * default.
     * 
     * @throws InvalidResourceException
     */
    public SecurityResourceLoader() throws InvalidResourceException{
        super(SecurityConstants.DEFAULT_RESOURCE_ID);
    }
    
    /**
     * Retorna a instância contendo as configurações.
     * 
     * @return Instância contendo as configurações.
     * @throws InvalidResourceException
     */
    public SecurityResource get() throws InvalidResourceException{
        SecurityResource securityResource = new SecurityResource();
        
        securityResource.setId("default");
        securityResource.setDefault(true);
        
        XmlNode content    = getContent();
        XmlNode filterNode = content.getNode("filter");

        if(filterNode != null){
            XmlNode excludePatternsNode = filterNode.getNode("excludePatterns");
        
            if(excludePatternsNode == null)
                throw new InvalidResourceException(getResourceId(), filterNode.getText());
        
            Collection<XmlNode> patternsNode = excludePatternsNode.getChildNodes();
        
            if(patternsNode == null || patternsNode.size() == 0)
                throw new InvalidResourceException(getResourceId(), excludePatternsNode.getText());
        
            Collection<String> excludePatterns = new LinkedList<String>();
        
            for(XmlNode patternNode : patternsNode)
                if(patternNode.getValue().length() > 0)
                    excludePatterns.add(patternNode.getValue());
        
            securityResource.setExcludePatterns(excludePatterns);
        
            XmlNode sessionNode = content.getNode("session");
        
            if(sessionNode != null){
                XmlNode classNode = sessionNode.getNode("class");
                
                if(classNode == null)
                    throw new InvalidResourceException(getResourceId(), sessionNode.getText());
                
                try{
                    Class.forName(classNode.getValue());
                    
                    securityResource.setSessionClass(classNode.getValue());
                }
                catch(Throwable e){
                    throw new InvalidResourceException(getResourceId(), classNode.getText(), e);
                }
                    
                XmlNode timeoutNode = sessionNode.getNode("timeout");
                
                if(timeoutNode == null)
                    throw new InvalidResourceException(getResourceId(), sessionNode.getText());
                
                try{
                    securityResource.setSessionTimeout(Integer.parseInt(timeoutNode.getValue()));
                }
                catch(Throwable e){
                    throw new InvalidResourceException(getResourceId(), timeoutNode.getText());
                }
            }
        }
        
        return securityResource;
    }

    /**
     * @see br.com.concepting.framework.resource.XmlResourceLoader#get(java.lang.String)
     */
    public SecurityResource get(String id) throws InvalidResourceException{
        return get();
    }
}
