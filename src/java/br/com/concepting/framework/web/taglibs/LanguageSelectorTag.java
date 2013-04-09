package br.com.concepting.framework.web.taglibs;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.resource.SystemResource;
import br.com.concepting.framework.resource.SystemResourceLoader;
import br.com.concepting.framework.resource.constants.ResourceConstants;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.web.action.types.ActionType;

public class LanguageSelectorTag extends ListPropertyTag{
    /**
     * @see br.com.concepting.framework.web.taglibs.ListPropertyTag#initialize()
     */
    protected void initialize() throws Throwable{
        String selectedLanguage = StringUtil.trim(systemController.getRequest().getParameter(AttributeConstants.CURRENT_LANGUAGE_KEY));
        
        if(selectedLanguage.length() > 0)
            systemController.setCurrentLanguage(selectedLanguage);
        
        Locale currentLanguage = systemController.getCurrentLanguage();
        
        setResourceId(ResourceConstants.DEFAULT_I18N_RESOURCE_ID);
        setName(AttributeConstants.CURRENT_LANGUAGE_KEY);
        setValue(currentLanguage);
        setShowFirstOption(false);
        
        String onChangeAction = StringUtil.trim(getOnChangeAction());
        
        if(onChangeAction.length() == 0){
            onChangeAction = ActionType.REFRESH.getMethod();
            
            setOnChangeAction(onChangeAction);
        }
        
        SystemResourceLoader systemResourceLoader = new SystemResourceLoader();
        SystemResource       systemResource       = systemResourceLoader.getDefault();
        List<Locale>         availableLanguages   = systemResource.getLanguages();
        List<String>         languages            = new LinkedList<String>();
        Map<String, String>  languagesMap         = new LinkedHashMap<String, String>();
        
        if(availableLanguages != null && availableLanguages.size() > 0){
            for(Locale availableLanguage : availableLanguages){
                languages.add(availableLanguage.toString());
                languagesMap.put(availableLanguage.toString(), availableLanguage.getDisplayName(currentLanguage));
            }
        }
        
        setDataValues(languages);
        setValueMapInstance(languagesMap);
        
        super.initialize();
    }
}
