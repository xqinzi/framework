package br.com.concepting.framework.controller.form.util;

import br.com.concepting.framework.controller.action.BaseAction;
import br.com.concepting.framework.resource.constants.ResourceConstants;
import br.com.concepting.framework.util.StringUtil;

/**
 * Classe utilit�ria para manipula��o das classes de a��es/formul�rios.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class ActionFormUtil{
    /**
     * Retorna o nome do pacote da classe de a��es a partir de seu modelo de dados.
     * 
     * @param modelClass Classe do modelo de dados desejado.
     * @return String contendo o nome do pacote.
     */
    public static String getActionPackageByModel(Class<?> modelClass){
        return StringUtil.replaceAll(modelClass.getPackage().getName(), ".model", ".controller.action");
    }

    /**
     * Retorna o nome completo da classe de a��es a partir de seu modelo de dados.
     * 
     * @param modelClass Classe do modelo de dados desejado.
     * @return String contendo o nome da classe de a��es.
     */
    public static String getActionClassNameByModel(Class<?> modelClass){
        String actionClassId = StringUtil.replaceLast(modelClass.getName(), "Model", "Action");

        return StringUtil.replaceAll(actionClassId, ".model", ".controller.action");
    }
    
    /**
     * Retorna o nome da classe de a��es a partir de seu modelo de dados.
     * 
     * @param modelClass Classe do modelo de dados desejado.
     * @return String contendo o nome da classe de a��es.
     */
    public static String getActionNameByModel(Class<?> modelClass){
        return StringUtil.replaceLast(modelClass.getSimpleName(), "Model", "Action");
    }

    /**
     * Retorna a classe de a��es a partir de seu modelo de dados.
     * 
     * @param modelClass Classe do modelo de dados desejado.
     * @return Classe de a��es desejada.
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public static <A extends BaseAction> Class<A> getActionClassByModel(Class<?> modelClass) throws ClassNotFoundException{
        return (Class<A>)Class.forName(getActionClassNameByModel(modelClass));
    }
    
    /**
     * Retorna o nome do pacote da classe de formul�rio a partir de seu modelo de dados.
     * 
     * @param modelClass Classe do modelo de dados desejado.
     * @return String contendo o nome do pacote.
     */
    public static String getActionFormPackageByModel(Class<?> modelClass){
        return StringUtil.replaceAll(modelClass.getPackage().getName(), ".model", ".controller.form");
    }

    /**
     * Retorna o nome completo da classe de formul�rio a partir de seu modelo de dados.
     * 
     * @param modelClass Classe do modelo de dados desejado.
     * @return String contendo o nome da classe de formul�rio.
     */
    public static String getActionFormClassNameByModel(Class<?> modelClass){
        String actionFormClassId = StringUtil.replaceLast(modelClass.getName(), "Model", "ActionForm");

        return StringUtil.replaceAll(actionFormClassId, ".model", ".controller.form");
    }
    
    /**
     * Retorna o nome da classe do formul�rio a partir de seu modelo de dados.
     * 
     * @param modelClass Classe do modelo de dados desejado.
     * @return String contendo o nome da classe do formul�rio.
     */
    public static String getActionFormNameByModel(Class<?> modelClass){
        return StringUtil.replaceLast(modelClass.getSimpleName(), "Model", "ActionForm");
    }

    /**
     * Retorna a classe do formul�rio a partir de seu modelo de dados.
     * 
     * @param modelClass Classe do modelo de dados desejado.
     * @return Classe de a��es desejada.
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public static <A extends BaseAction> Class<A> getActionFormClassByModel(Class<?> modelClass) throws ClassNotFoundException{
        return (Class<A>)Class.forName(getActionFormNameByModel(modelClass));
    }
    
    /**
     * Retorna o nome do objeto do formul�rio (geralmente utilizado por Javascript) 
     * a partir de seu modelo de dados.
     * 
     * @param modelClass Classe do modelo de dados desejado.
     * @return String contendo o nome do objeto.
     */
    public static String getActionFormObjectIdByModel(Class<?> modelClass){
        String actionFormName = getActionFormNameByModel(modelClass);
        
        return actionFormName.substring(0, 1).toLowerCase().concat(actionFormName.substring(1));
    }

    /**
     * Retorna a URL da a��o do formul�rio a partir de seu modelo de dados.
     * 
     * @param modelClass Classe do modelo de dados desejado.
     * @return String contendo o nome do objeto.
     */
    public static String getActionFormUrlByModel(Class<?> modelClass){
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
     * Retorna o identificador do arquivo de internacionaliza��o de um modelo de dados.
     * 
     * @param modelClass Classe do modelo de dados desejado.
     * @return String contendo o identificador do arquivo de internacionaliza��o.
     */
    public static String getActionFormResourceIdByModel(Class<?> modelClass){
        String        actionFormUrl = getActionFormUrlByModel(modelClass).substring(1);
        StringBuilder resourceId    = new StringBuilder();
        
        resourceId.append(ResourceConstants.DEFAULT_I18N_RESOURCES_DIR);
        resourceId.append(actionFormUrl);
        
        actionFormUrl = StringUtil.replaceAll(resourceId.toString(), "/", ".");
        
        return actionFormUrl;
    }
}