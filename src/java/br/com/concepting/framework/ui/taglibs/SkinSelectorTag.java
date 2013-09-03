package br.com.concepting.framework.ui.taglibs;

import java.util.List;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.controller.action.types.ActionType;
import br.com.concepting.framework.resource.SystemResource;
import br.com.concepting.framework.resource.SystemResourceLoader;
import br.com.concepting.framework.resource.constants.ResourceConstants;

/**
 * Classe que define o componente visual skinSelector (seletor de tema).
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class SkinSelectorTag extends ListPropertyTag{
    /**
     * @see br.com.concepting.framework.ui.taglibs.ListPropertyTag#initialize()
     */
    protected void initialize() throws Throwable{
        String currentSkin = systemController.getCurrentSkin();
        
        setResourceId(ResourceConstants.DEFAULT_I18N_RESOURCE_ID);
        setName(AttributeConstants.CURRENT_SKIN_KEY);
        setValue(currentSkin);
        setShowFirstOption(false);
        setOnChangeActionType(ActionType.CHANGE_CURRENT_SKIN);
        
        SystemResourceLoader systemResourceLoader = new SystemResourceLoader();
        SystemResource       systemResource       = systemResourceLoader.getDefault();
        List<String>         skins                = systemResource.getSkins();
        
        setDataValues(skins);
        
        super.initialize();
    }
}
