package br.com.concepting.framework.controller.form.util;

import br.com.concepting.framework.controller.action.BaseAction;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.resource.constants.ResourceConstants;
import br.com.concepting.framework.util.StringUtil;

/**
 * Classe utilitária para manipulação das classes de ações/formulários.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class ActionFormUtil{
    /**
     * Retorna o nome do pacote da classe de ações a partir de seu modelo de dados.
     * 
     * @param modelClass Classe do modelo de dados desejado.
     * @return String contendo o nome do pacote.
     */
    public static <M extends BaseModel> String getActionPackageByModel(Class<M> modelClass){
        return StringUtil.replaceAll(modelClass.getPackage().getName(), ".model", ".web.action");
    }

    /**
     * Retorna o nome completo da classe de ações a partir de seu modelo de dados.
     * 
     * @param modelClass Classe do modelo de dados desejado.
     * @return String contendo o nome da classe de ações.
     */
    public static <M extends BaseModel> String getActionClassNameByModel(Class<M> modelClass){
        String actionClassId = StringUtil.replaceLast(modelClass.getName(), "Model", "Action");

        return StringUtil.replaceAll(actionClassId, ".model", ".web.action");
    }
    
    /**
     * Retorna o nome da classe de ações a partir de seu modelo de dados.
     * 
     * @param modelClass Classe do modelo de dados desejado.
     * @return String contendo o nome da classe de ações.
     */
    public static <M extends BaseModel> String getActionNameByModel(Class<M> modelClass){
        return StringUtil.replaceLast(modelClass.getSimpleName(), "Model", "Action");
    }

    /**
     * Retorna a classe de ações a partir de seu modelo de dados.
     * 
     * @param modelClass Classe do modelo de dados desejado.
     * @return Classe de ações desejada.
     * @throws ClassNotFoundException
     */
    public static <M extends BaseModel, A extends BaseAction> Class<A> getActionClassByModel(Class<M> modelClass) throws ClassNotFoundException{
        return (Class<A>)Class.forName(getActionClassNameByModel(modelClass));
    }
    
    /**
     * Retorna o nome do pacote da classe de formulário a partir de seu modelo de dados.
     * 
     * @param modelClass Classe do modelo de dados desejado.
     * @return String contendo o nome do pacote.
     */
    public static <M extends BaseModel> String getActionFormPackageByModel(Class<M> modelClass){
        return StringUtil.replaceAll(modelClass.getPackage().getName(), ".model", ".web.form");
    }

    /**
     * Retorna o nome completo da classe de formulário a partir de seu modelo de dados.
     * 
     * @param modelClass Classe do modelo de dados desejado.
     * @return String contendo o nome da classe de formulário.
     */
    public static <M extends BaseModel> String getActionFormClassNameByModel(Class<M> modelClass){
        String actionFormClassId = StringUtil.replaceLast(modelClass.getName(), "Model", "ActionForm");

        return StringUtil.replaceAll(actionFormClassId, ".model", ".web.form");
    }
    
    /**
     * Retorna o nome da classe do formulário a partir de seu modelo de dados.
     * 
     * @param modelClass Classe do modelo de dados desejado.
     * @return String contendo o nome da classe do formulário.
     */
    public static <M extends BaseModel> String getActionFormNameByModel(Class<M> modelClass){
        return StringUtil.replaceLast(modelClass.getSimpleName(), "Model", "ActionForm");
    }

    /**
     * Retorna a classe do formulário a partir de seu modelo de dados.
     * 
     * @param modelClass Classe do modelo de dados desejado.
     * @return Classe de ações desejada.
     * @throws ClassNotFoundException
     */
    public static <M extends BaseModel, A extends BaseAction> Class<A> getActionFormClassByModel(Class<M> modelClass) throws ClassNotFoundException{
        return (Class<A>)Class.forName(getActionFormNameByModel(modelClass));
    }
    
    /**
     * Retorna o nome do objeto do formulário (geralmente utilizado por Javascript) 
     * a partir de seu modelo de dados.
     * 
     * @param modelClass Classe do modelo de dados desejado.
     * @return String contendo o nome do objeto.
     */
    public static <M extends BaseModel> String getActionFormObjectIdByModel(Class<M> modelClass){
        String actionFormName = getActionFormNameByModel(modelClass);
        
        return actionFormName.substring(0, 1).toLowerCase().concat(actionFormName.substring(1));
    }

    /**
     * Retorna a URL da ação do formulário a partir de seu modelo de dados.
     * 
     * @param modelClass Classe do modelo de dados desejado.
     * @return String contendo o nome do objeto.
     */
    public static <M extends BaseModel> String getActionFormUrlByModel(Class<M> modelClass){
        StringBuilder url          = new StringBuilder();
        String        actionPrefix = modelClass.getPackage().getName();
        Integer       pos          = actionPrefix.indexOf(".model");
        
        if(pos >= 0){
            actionPrefix = actionPrefix.substring(0, pos);
            
            String actionPrefixBuffer[] = StringUtil.split(actionPrefix, ".");
            
            if(actionPrefixBuffer.length <= 3)
                actionPrefix = "";
            else{
                pos          = actionPrefix.lastIndexOf(".");
                actionPrefix = actionPrefix.substring(pos + 1);
            }
            
            String actionName = StringUtil.replaceLast(modelClass.getSimpleName(), "Model", "");
            
            url.append("/");
            
            if(actionPrefix.length() > 0){
                url.append(actionPrefix);
                url.append("/");
            }
            
            url.append(actionName.substring(0, 1).toLowerCase());
            url.append(actionName.substring(1));
        }
        
        return url.toString();
    }
    
    /**
     * Retorna o identificador do arquivo de internacionalização de um modelo de dados.
     * 
     * @param modelClass Classe do modelo de dados desejado.
     * @return String contendo o identificador do arquivo de internacionalização.
     */
    public static <M extends BaseModel> String getActionFormResourceIdByModel(Class<M> modelClass){
        String        actionFormUrl = getActionFormUrlByModel(modelClass).substring(1);
        StringBuilder resourceId    = new StringBuilder();
        
        resourceId.append(ResourceConstants.DEFAULT_I18N_RESOURCES_DIR);
        resourceId.append(actionFormUrl);
        
        actionFormUrl = StringUtil.replaceAll(resourceId.toString(), "/", ".");
        
        return actionFormUrl;
    }
}