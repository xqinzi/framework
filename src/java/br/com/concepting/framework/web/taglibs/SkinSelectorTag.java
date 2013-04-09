package br.com.concepting.framework.web.taglibs;

import java.util.List;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.resource.SystemResource;
import br.com.concepting.framework.resource.SystemResourceLoader;
import br.com.concepting.framework.resource.constants.ResourceConstants;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.web.action.types.ActionType;

public class SkinSelectorTag extends ListPropertyTag{
    /**
     * @see br.com.concepting.framework.web.taglibs.ListPropertyTag#initialize()
     */
    protected void initialize() throws Throwable{
        String selectedSkin = StringUtil.trim(systemController.getRequest().getParameter(AttributeConstants.CURRENT_SKIN_KEY));
        
        if(selectedSkin.length() > 0)
            systemController.setCurrentSkin(selectedSkin);
        
        String currentSkin = systemController.getCurrentSkin();
        
        setResourceId(ResourceConstants.DEFAULT_I18N_RESOURCE_ID);
        setName(AttributeConstants.CURRENT_SKIN_KEY);
        setValue(currentSkin);
        setShowFirstOption(false);
        
        String onChangeAction = StringUtil.trim(getOnChangeAction());
        
        if(onChangeAction.length() == 0){
            onChangeAction = ActionType.REFRESH.getMethod();
            
            setOnChangeAction(onChangeAction);
        }
        
        SystemResourceLoader systemResourceLoader = new SystemResourceLoader();
        SystemResource       systemResource       = systemResourceLoader.getDefault();
        List<String>         skins                = systemResource.getSkins();
        
        setDataValues(skins);
        
        super.initialize();
    }
}
