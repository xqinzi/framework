package br.com.concepting.framework.resource;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import br.com.concepting.framework.constants.SystemConstants;
import br.com.concepting.framework.resource.exceptions.InvalidResourceException;
import br.com.concepting.framework.util.LanguageUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Classe responsável pela leitura/manipulação das configurações do sistema.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class SystemResourceLoader extends XmlResourceLoader{
    /**
     * Construtor - Inicializa classe de leitura/manipulação do arquivo de configurações 
     * do sistema.
     * 
     * @throws InvalidResourceException
     */
    public SystemResourceLoader() throws InvalidResourceException{
        super(SystemConstants.DEFAULT_RESOURCE_ID);
    }
    
    /**
     * Construtor - Efetua a leitura de um arquivo de configurações específico.
     * 
     * @param resourceDir String contendo o diretório de armazenamento do arquivo.
     * @param resourceId String contendo o identificador do arquivo.
     * @throws InvalidResourceException
     */
    public SystemResourceLoader(String resourceDir, String resourceId) throws InvalidResourceException{
        super(resourceDir, resourceId);
    }

    /**
     * Construtor - Efetua a leitura de um arquivo de configurações específico.
     * 
     * @param resourceId String contendo o identificador do arquivo.
     * @throws InvalidResourceException
     */
    public SystemResourceLoader(String resourceId) throws InvalidResourceException{
        super(resourceId);
    }

    /**
     * Retorna a instância contendo as configurações.
     * 
     * @return Instância contendo as configurações.
     * @throws InvalidResourceException
     */
    public <R extends BaseResource> R get() throws InvalidResourceException{
        SystemResource systemResource = new SystemResource();
        
        systemResource.setId("default");
        systemResource.setDefault(true);
        
        XmlNode content      = getContent();
        XmlNode mainPageNode = content.getNode("mainPage");
            
        if(mainPageNode == null)
            throw new InvalidResourceException(getResourceId(), content.getText());
            
        systemResource.setMainPage(StringUtil.trim(mainPageNode.getValue()));
                
        XmlNode loginPageNode = content.getNode("loginPage");
        
        if(loginPageNode == null)
            throw new InvalidResourceException(getResourceId(), content.getText());
            
        systemResource.setLoginPage(StringUtil.trim(loginPageNode.getValue()));
        
        XmlNode skinsNode = content.getNode("skins");

        if(skinsNode == null)
            throw new InvalidResourceException(getResourceId(), content.getText());
        
        Collection<XmlNode> skinsChildNodes = skinsNode.getChildNodes();
        
        if(skinsChildNodes == null || skinsChildNodes.size() == 0)
            throw new InvalidResourceException(getResourceId(), content.getText());
        
        List<String> skins       = new LinkedList<String>();
        String       skin        = "";
        String       defaultSkin = "";
        
        for(XmlNode skinChildNode : skinsChildNodes){
            skin = StringUtil.trim(skinChildNode.getAttribute("id"));
            
            if(skin.length() > 0){
                try{
                    if(Boolean.parseBoolean(skinChildNode.getAttribute("default")))
                        defaultSkin = skin;
                }
                catch(Throwable e){
                }

                skins.add(skin);
            }
        }
        
        if(skins.size() == 0)
            throw new InvalidResourceException(getResourceId(), content.getText());
        
        if(defaultSkin.length() == 0)
            defaultSkin = skins.iterator().next();
        
        systemResource.setSkins(skins);
        systemResource.setDefaultSkin(defaultSkin);
        
        XmlNode languagesNode = content.getNode("languages");

        if(languagesNode == null)
            throw new InvalidResourceException(getResourceId(), content.getText());
        
        Collection<XmlNode> languagesChildNodes = languagesNode.getChildNodes();
        
        if(languagesChildNodes == null || languagesChildNodes.size() == 0)
            throw new InvalidResourceException(getResourceId(), content.getText());

        List<Locale> languages       = new LinkedList<Locale>();
        String       languageBuffer  = "";
        Locale       language        = null;
        Locale       defaultLanguage = null;
        
        for(XmlNode languageChildNode : languagesChildNodes){
            languageBuffer = StringUtil.trim(languageChildNode.getAttribute("id"));
            
            if(languageBuffer.length() > 0){
                language = LanguageUtil.getLanguageByString(languageBuffer);
                
                try{
                    if(Boolean.parseBoolean(languageChildNode.getAttribute("default")))
                        defaultLanguage = language;
                }
                catch(Throwable e){
                }

                languages.add(language);
            }
        }
        
        if(languages.size() == 0)
            throw new InvalidResourceException(getResourceId(), content.getText());
        
        if(defaultLanguage == null)
            defaultLanguage = languages.iterator().next();
        
        systemResource.setLanguages(languages);
        systemResource.setDefaultLanguage(defaultLanguage);
        
        return (R)systemResource;
    }

    /**
     * @see br.com.concepting.framework.resource.XmlResourceLoader#get(java.lang.String)
     */
    public <R extends BaseResource> R get(String id) throws InvalidResourceException{
        return get();
    }
}
