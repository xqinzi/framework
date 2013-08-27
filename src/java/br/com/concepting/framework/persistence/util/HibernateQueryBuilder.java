package br.com.concepting.framework.persistence.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.beanutils.ConstructorUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.helpers.ModelFilter;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.model.types.ConditionType;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.persistence.HibernateDAO;
import br.com.concepting.framework.persistence.constants.PersistenceConstants;
import br.com.concepting.framework.persistence.types.QueryType;
import br.com.concepting.framework.persistence.types.RelationJoinType;
import br.com.concepting.framework.util.MethodUtil;
import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.PhoneticUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.SortOrderType;
 
/**
 * Classe auxiliar responsável por montar queries HQL baseadas em modelos de dados.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public abstract class HibernateQueryBuilder{
    /**
     * Monta a query HQL (Hibernate Query Language) baseada no modelo de dados especificado.
     * 
     * @param queryType Constante que define o tipo da query.
     * @param model Instância do modelo de dados desejado.
     * @param modelFilter Instância contendo os filtros adicionais do modelo de dados.
     * @param whereClauseParameters Mapa contendo os parâmetros da cláusula WHERE.
     * @param dao Instância do DAO a ser utilizado.
     * @return Instância da query desejada.
     * @throws InternalErrorException
     */
    private static <M extends BaseModel> String buildExpression(QueryType queryType, M model, ModelFilter modelFilter, Map<String, Object> whereClauseParameters, HibernateDAO dao) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException{
        StringBuilder      expression         = new StringBuilder();
        String             propertyPrefix     = "";
        String             propertyAlias      = "";
        StringBuilder      fieldsClause       = new StringBuilder();
        StringBuilder      fromClause         = new StringBuilder();
        StringBuilder      whereClause        = new StringBuilder();
        StringBuilder      joinClause         = new StringBuilder();
        StringBuilder      groupByClause      = new StringBuilder();
        StringBuilder      orderByClause      = new StringBuilder();
        Collection<String> processedRelations = new LinkedList<String>();
        Boolean            considerConditions = true;
        Boolean            needToGroupBy      = false;

        buildExpression(queryType, model, modelFilter, propertyPrefix, propertyAlias, fieldsClause, fromClause, joinClause, whereClause, groupByClause, orderByClause, whereClauseParameters, processedRelations, considerConditions, needToGroupBy, dao);

        if(queryType != QueryType.DELETE){
            if(fieldsClause.length() > 0){
                expression.append(StringUtil.trim(fieldsClause));
                expression.append(" ");
            }
        }

        expression.append(StringUtil.trim(fromClause));

        if(joinClause.length() > 0){
            expression.append(" ");
            expression.append(StringUtil.trim(joinClause));
        }

        if(whereClause.length() > 0){
            expression.append(" ");
            expression.append(StringUtil.trim(whereClause));
        }

        if(queryType != QueryType.DELETE){
            if(needToGroupBy){
                if(groupByClause.length() > 0){
                    expression.append(" ");
                    expression.append(StringUtil.trim(groupByClause));
                }
            }
    
            if(orderByClause.length() > 0){
                expression.append(" ");
                expression.append(StringUtil.trim(orderByClause));
            }
        }
        
        return expression.toString();
    }
    
    /**
     * Monta a query HQL (Hibernate Query Language) baseada no modelo de dados especificado.
     * 
     * @param queryType Constante que define o tipo da query.
     * @param model Instância do modelo de dados desejado.
     * @param modelFilter Instância contendo os filtros adicionais do modelo de dados.
     * @param propertyPrefix String contendo o prefixo das propriedades.
     * @param propertyAlias String contendo o apelido das propriedades.
     * @param fieldsClause String contendo os campos que serão retornados pela query.
     * @param fromClause String contendo a cláusula FROM.
     * @param joinClause String contendo a cláusula JOIN.
     * @param whereClause String contendo a cláusula WHERE.
     * @param groupByClause String contendo a cláusula GROUP BY.
     * @param orderByClause String contendo a cláusula ORDER BY.
     * @param whereClauseParameters Mapa contendo os parâmetros da cláusula WHERE.
     * @param processedRelations Lista contendo os relacionamentos já processados.
     * @param considerConditions Indica se as condições de pesquisa serão consideradas.
     * @param needToGroupBy Indica se a cláusula GROUP BY deve ser considerada.
     * @param dao Instância do DAO a ser utilizado.
     * @return Instância da query desejada.
     * @throws InternalErrorException
     */
    private static <M extends BaseModel> void buildExpression(QueryType queryType, M model, ModelFilter modelFilter, String propertyPrefix, String propertyAlias, StringBuilder fieldsClause, StringBuilder fromClause, StringBuilder joinClause, StringBuilder whereClause, StringBuilder groupByClause, StringBuilder orderByClause, Map<String, Object> whereClauseParameters, Collection<String> processedRelations, Boolean considerConditions, Boolean needToGroupBy, HibernateDAO dao) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException{
        Boolean   modelIsNull = (model == null);
        Class     modelClass  = (modelIsNull ? ModelUtil.getModelClassByPersistence(dao.getClass()) : model.getClass());
        ModelInfo modelInfo   = ModelUtil.getModelInfo(modelClass);

        if(modelIsNull)
            model = (M)ConstructorUtils.invokeConstructor(modelClass, null);

        if(modelInfo != null){
            StringBuilder propertyAliasBuffer = null;

            if(fromClause.length() == 0){
                propertyAliasBuffer = new StringBuilder();
                propertyAliasBuffer.append(modelInfo.getClazz().getSimpleName().toLowerCase());
                propertyAliasBuffer.append((int)(Math.random() * 1000));

                propertyAlias = propertyAliasBuffer.toString();
                
                if(queryType == QueryType.DELETE)
                    fromClause.append("delete ");

                fromClause.append("from ");
                fromClause.append(modelInfo.getClazz().getSimpleName());
                fromClause.append(" ");
                fromClause.append(propertyAlias);
            }

            if(processedRelations == null)
                processedRelations = new LinkedList<String>();

            Collection<String>         returnProperties               = (modelFilter != null ? modelFilter.getReturnProperties() : null);
            Map<String, ConditionType> propertyConditions             = (modelFilter != null ? modelFilter.getPropertyConditions() : null);
            Map<String, Object>        propertyValues                 = (modelFilter != null ? modelFilter.getPropertyValues() : null);
            Collection<PropertyInfo>   propertiesInfo                 = modelInfo.getPropertiesInfo();
            Collection<PropertyInfo>   identitiesPropertiesInfo       = modelInfo.getIdentityPropertiesInfo();
            Collection<PropertyInfo>   identitiesPropertiesInfoBuffer = new LinkedList<PropertyInfo>();
            StringBuilder              propertyIdBuffer               = null;
            StringBuilder              propertyPrefixBuffer           = null;
            StringBuilder              propertyParamBuffer            = null;
            Object                     propertyValue                  = null;
            Object                     propertyValueBuffer            = null;
            SortOrderType              propertySortOrder              = null;
            ConditionType              propertyCondition              = null;
            Map<String, SortOrderType> propertySortOrders             = (modelFilter != null ? modelFilter.getSortOrders() : null);
            ModelInfo                  relationModelInfo              = null;
            M                          relationModel                  = null;
            RelationJoinType           relationJoinType               = null;
            Boolean                    processRelation                = false;
            Boolean                    processCondition               = false;
            Boolean                    needToGroupByBuffer            = needToGroupBy;
            
            for(PropertyInfo propertyInfo : propertiesInfo){
                if(queryType != QueryType.DELETE){
                    if(propertyInfo.getMappedPropertyId().length() > 0){
                        if(groupByClause.length() == 0)
                            groupByClause.append("group by ");
                        else
                            groupByClause.append(", ");
                            
                        groupByClause.append(propertyAlias);
                        groupByClause.append(".");
                        groupByClause.append(propertyInfo.getId());
                    }
                }
                
                if(propertyPrefixBuffer == null)
                    propertyPrefixBuffer = new StringBuilder();
                else
                    propertyPrefixBuffer.delete(0, propertyPrefixBuffer.length());

                if(propertyPrefix.length() > 0){
                    propertyPrefixBuffer.append(propertyPrefix);
                    propertyPrefixBuffer.append(".");
                }

                propertyPrefixBuffer.append(propertyInfo.getId());

                if(propertyIdBuffer == null)
                    propertyIdBuffer = new StringBuilder();
                else
                    propertyIdBuffer.delete(0, propertyIdBuffer.length());

                if(propertyAlias.length() > 0){
                    propertyIdBuffer.append(propertyAlias);
                    propertyIdBuffer.append(".");
                }

                propertyIdBuffer.append(propertyInfo.getId());

                if(queryType != QueryType.DELETE){
                    if(returnProperties != null && returnProperties.contains(propertyPrefixBuffer.toString())){
                        if(fieldsClause.length() == 0)
                            fieldsClause.append("select ");
                        else
                            fieldsClause.append(",");
    
                        fieldsClause.append(propertyIdBuffer);
                    }
                    else if(returnProperties == null){
                        if(fieldsClause.length() == 0){
                            fieldsClause.append("select ");
                            fieldsClause.append(propertyAlias);
                        }
                    }
                
                    propertySortOrder = (propertySortOrders != null ? propertySortOrders.get(propertyPrefixBuffer.toString()) : null);
    
                    if(propertySortOrder == null)
                        propertySortOrder = propertyInfo.getSortOrder();
    
                    if(propertySortOrder != null && propertySortOrder != SortOrderType.NONE){
                        if(orderByClause.length() == 0)
                            orderByClause.append("order by ");
                        else
                            orderByClause.append(", ");
    
                        orderByClause.append(propertyIdBuffer.toString());
                        orderByClause.append(" ");
                        orderByClause.append(propertySortOrder.getOperator());
                    }
                }

                if(propertyInfo.getRelationJoinType() != RelationJoinType.NONE || (propertyInfo.isForSearch() && (propertyInfo.hasModel() || propertyInfo.isModel()))){
                    relationModel     = null;
                    relationModelInfo = null;
                    relationJoinType  = null;
                    processRelation   = !processedRelations.contains(propertyPrefixBuffer.toString()) && !propertyPrefixBuffer.toString().contains("parent.parent");
                    
                    if(processRelation){
                        if(propertyInfo.hasModel())
                            relationModelInfo = ModelUtil.getModelInfo(propertyInfo.getCollectionItemsClass());
                        else if(propertyInfo.isModel())
                            relationModelInfo = ModelUtil.getModelInfo(propertyInfo.getClazz());
                        
                        if(relationModelInfo != null){
                            if(propertyInfo.getRelationJoinType() != RelationJoinType.NONE){
                                if(propertyInfo.isModel())
                                    relationModel = (M)PropertyUtil.getProperty(model, propertyInfo.getId());
                                else if(propertyInfo.hasModel())
                                    relationModel = (M)ConstructorUtils.invokeConstructor(relationModelInfo.getClazz(), null);
                                
                                relationJoinType = propertyInfo.getRelationJoinType();
                            }
                            else if(propertyInfo.isForSearch()){
                                if(propertyInfo.getSearchPropertyId().length() > 0){
                                    PropertyInfo relationSearchPropertyInfo = modelInfo.getPropertyInfo(propertyInfo.getSearchPropertyId());
                                    
                                    if(relationSearchPropertyInfo != null){
                                        if(relationSearchPropertyInfo.isModel()){
                                            relationModel       = (M)PropertyUtil.getProperty(model, propertyInfo.getSearchPropertyId());
                                            relationJoinType    = RelationJoinType.INNER_JOIN;
                                            needToGroupByBuffer = true;
                                        }
                                    }
                                }
                                else
                                    relationModel = (M)PropertyUtil.getProperty(model, propertyInfo.getId());
                            }
                        }
                        
                        if(relationModelInfo != null && relationModel != null){
                            processedRelations.add(propertyPrefixBuffer.toString());
                            
                            if(relationJoinType != null){
                                if(propertyAliasBuffer == null)
                                    propertyAliasBuffer = new StringBuilder();
                                else
                                    propertyAliasBuffer.delete(0, propertyAliasBuffer.length());
    
                                propertyAliasBuffer.append(relationModelInfo.getClazz().getSimpleName().toLowerCase());
                                propertyAliasBuffer.append((int)(Math.random() * 1000));
                            
                                if(joinClause.length() > 0)
                                    joinClause.append(" ");
    
                                joinClause.append(relationJoinType.getOperator());
                                joinClause.append(" ");
                                joinClause.append(propertyIdBuffer);
                                joinClause.append(" ");
                                joinClause.append(propertyAliasBuffer);
                            }
    
                            buildExpression(queryType, relationModel, modelFilter, propertyPrefixBuffer.toString(), propertyAliasBuffer.toString(), fieldsClause, fromClause, joinClause, whereClause, groupByClause, orderByClause, whereClauseParameters, processedRelations, considerConditions, needToGroupByBuffer, dao);
                        }
                    }

                    continue;
                }

                if(propertyInfo.isIdentity() || propertyInfo.isForSearch()){
                    if(considerConditions){
                        processCondition = true;
                        
                        try{
                            propertyValue     = (propertyValues != null ? propertyValues.get(propertyPrefixBuffer.toString()) : null);
                            propertyCondition = (propertyConditions != null ? propertyConditions.get(propertyPrefixBuffer.toString()) : null);
                            
                            if(queryType != QueryType.FIND)
                                if(propertyCondition == null)
                                    propertyCondition = propertyInfo.getSearchCondition();
                            
                            if(propertyCondition == null)
                                propertyCondition = ConditionType.EQUAL;

                            if(propertyValue == null){
                                if(propertyInfo.isForSearch()){
                                    if(propertyInfo.getSearchPropertyId().length() > 0)
                                        propertyValue = PropertyUtil.getProperty(model, propertyInfo.getSearchPropertyId());
                                    else if(propertyCondition == ConditionType.SIMILARITY && propertyInfo.getPhoneticPropertyId().length() > 0)
                                        propertyValue = PropertyUtil.getProperty(model, propertyInfo.getPhoneticPropertyId());
                                    else
                                        propertyValue = PropertyUtil.getProperty(model, propertyInfo.getId());
                                }
                                else if(propertyInfo.isIdentity())
                                    propertyValue = PropertyUtil.getProperty(model, propertyInfo.getId());
                            }

                            switch(propertyCondition){
                                case IS_NULL:{
                                    if(whereClause.length() == 0)
                                        whereClause.append("where ");
                                    else
                                        whereClause.append(" and ");

                                    whereClause.append(propertyIdBuffer);
                                    whereClause.append(" ");
                                    whereClause.append(propertyCondition.getOperator());
                                    
                                    break;
                                }
                                case NOT_IS_NULL:{
                                    if(whereClause.length() == 0)
                                        whereClause.append("where ");
                                    else
                                        whereClause.append(" and ");

                                    whereClause.append(propertyIdBuffer);
                                    whereClause.append(" ");
                                    whereClause.append(propertyCondition.getOperator());
                                    
                                    break;
                                }
                                case NOT_EQUAL: {
                                    if(propertyParamBuffer == null)
                                        propertyParamBuffer = new StringBuilder();
                                    else
                                        propertyParamBuffer.delete(0, propertyParamBuffer.length());

                                    propertyParamBuffer.append("param");

                                    if(whereClauseParameters == null)
                                        whereClauseParameters = new LinkedHashMap<String, Object>();

                                    propertyParamBuffer.append(whereClauseParameters.size());

                                    if(!(propertyValue instanceof String))
                                        whereClauseParameters.put(propertyParamBuffer.toString(), propertyValue);
                                    else{
                                        if(propertyInfo.isCaseSensitiveSearch())
                                            whereClauseParameters.put(propertyParamBuffer.toString(), propertyValue);
                                        else
                                            whereClauseParameters.put(propertyParamBuffer.toString(), StringUtil.trim(propertyValue).toLowerCase());
                                    }

                                    if(whereClause.length() == 0)
                                        whereClause.append("where ");
                                    else
                                        whereClause.append(" and ");

                                    if(!(propertyValue instanceof String))
                                        whereClause.append(propertyIdBuffer);
                                    else{
                                        if(propertyInfo.isCaseSensitiveSearch())
                                            whereClause.append(propertyIdBuffer);
                                        else{
                                            whereClause.append("lower(");
                                            whereClause.append(propertyIdBuffer);
                                            whereClause.append(")");
                                        }
                                    }

                                    whereClause.append(" ");
                                    whereClause.append(propertyCondition.getOperator());
                                    whereClause.append(" :");
                                    whereClause.append(propertyParamBuffer);
    
                                    break;
                                }
                                case GREATER_EQUAL_THAN: {
                                    if(propertyValue == null)
                                        processCondition = false;
    
                                    if(processCondition){
                                        if(propertyParamBuffer == null)
                                            propertyParamBuffer = new StringBuilder();
                                        else
                                            propertyParamBuffer.delete(0, propertyParamBuffer.length());
    
                                        propertyParamBuffer.append("param");
    
                                        if(whereClauseParameters == null)
                                            whereClauseParameters = new LinkedHashMap<String, Object>();
    
                                        propertyParamBuffer.append(whereClauseParameters.size());
    
                                        whereClauseParameters.put(propertyParamBuffer.toString(), propertyValue);
    
                                        if(whereClause.length() == 0)
                                            whereClause.append("where ");
                                        else
                                            whereClause.append(" and ");
    
                                        whereClause.append(propertyIdBuffer);
                                        whereClause.append(" ");
                                        whereClause.append(propertyCondition.getOperator());
                                        whereClause.append(" :");
                                        whereClause.append(propertyParamBuffer);
                                    }
    
                                    break;
                                }
                                case GREATER_THAN: {
                                    if(propertyValue == null)
                                        processCondition = false;
    
                                    if(processCondition){
                                        if(propertyParamBuffer == null)
                                            propertyParamBuffer = new StringBuilder();
                                        else
                                            propertyParamBuffer.delete(0, propertyParamBuffer.length());
    
                                        propertyParamBuffer.append("param");
    
                                        if(whereClauseParameters == null)
                                            whereClauseParameters = new LinkedHashMap<String, Object>();
    
                                        propertyParamBuffer.append(whereClauseParameters.size());
    
                                        whereClauseParameters.put(propertyParamBuffer.toString(), propertyValue);
    
                                        if(whereClause.length() == 0)
                                            whereClause.append("where ");
                                        else
                                            whereClause.append(" and ");
    
                                        whereClause.append(propertyIdBuffer);
                                        whereClause.append(" ");
                                        whereClause.append(propertyCondition.getOperator());
                                        whereClause.append(" :");
                                        whereClause.append(propertyParamBuffer);
                                    }
    
                                    break;
                                }
                                case LESS_EQUAL_THAN: {
                                    if(propertyValue == null)
                                        processCondition = false;
    
                                    if(processCondition){
                                        if(propertyParamBuffer == null)
                                            propertyParamBuffer = new StringBuilder();
                                        else
                                            propertyParamBuffer.delete(0, propertyParamBuffer.length());
    
                                        propertyParamBuffer.append("param");
    
                                        if(whereClauseParameters == null)
                                            whereClauseParameters = new LinkedHashMap<String, Object>();
    
                                        propertyParamBuffer.append(whereClauseParameters.size());
    
                                        whereClauseParameters.put(propertyParamBuffer.toString(), propertyValue);
    
                                        if(whereClause.length() == 0)
                                            whereClause.append("where ");
                                        else
                                            whereClause.append(" and ");
    
                                        whereClause.append(propertyIdBuffer);
                                        whereClause.append(" ");
                                        whereClause.append(propertyCondition.getOperator());
                                        whereClause.append(" :");
                                        whereClause.append(propertyParamBuffer);
                                    }
    
                                    break;
                                }
                                case LESS_THAN: {
                                    if(propertyValue == null)
                                        processCondition = false;
    
                                    if(processCondition){
                                        if(propertyParamBuffer == null)
                                            propertyParamBuffer = new StringBuilder();
                                        else
                                            propertyParamBuffer.delete(0, propertyParamBuffer.length());
    
                                        propertyParamBuffer.append("param");
    
                                        if(whereClauseParameters == null)
                                            whereClauseParameters = new LinkedHashMap<String, Object>();
    
                                        propertyParamBuffer.append(whereClauseParameters.size());
    
                                        whereClauseParameters.put(propertyParamBuffer.toString(), propertyValue);
    
                                        if(whereClause.length() == 0)
                                            whereClause.append("where ");
                                        else
                                            whereClause.append(" and ");
    
                                        whereClause.append(propertyIdBuffer);
                                        whereClause.append(" ");
                                        whereClause.append(propertyCondition.getOperator());
                                        whereClause.append(" :");
                                        whereClause.append(propertyParamBuffer);
                                    }
    
                                    break;
                                }
                                case BETWEEN: {
                                    propertyValue       = PropertyUtil.getProperty(model, propertyInfo.getId());
                                    propertyValueBuffer = PropertyUtil.getProperty(model, propertyInfo.getSearchPropertyId());
    
                                    if(propertyValue instanceof String && StringUtil.trim(propertyValue).length() == 0)
                                        processCondition = false;
    
                                    if(propertyValueBuffer instanceof String && StringUtil.trim(propertyValueBuffer).length() == 0)
                                        processCondition = false;

                                    if(propertyValue == null || propertyValueBuffer == null)
                                        processCondition = false;
    
                                    if(processCondition){
                                        if(propertyParamBuffer == null)
                                            propertyParamBuffer = new StringBuilder();
                                        else
                                            propertyParamBuffer.delete(0, propertyParamBuffer.length());

                                        propertyParamBuffer.append("param");

                                        if(whereClauseParameters == null)
                                            whereClauseParameters = new LinkedHashMap<String, Object>();

                                        propertyParamBuffer.append(whereClauseParameters.size());

                                        if(!(propertyValue instanceof String))
                                            whereClauseParameters.put(propertyParamBuffer.toString(), propertyValue);
                                        else{
                                            if(propertyInfo.isCaseSensitiveSearch())
                                                whereClauseParameters.put(propertyParamBuffer.toString(), propertyValue);
                                            else
                                                whereClauseParameters.put(propertyParamBuffer.toString(), StringUtil.trim(propertyValue).toLowerCase());
                                        }

                                        if(whereClause.length() == 0)
                                            whereClause.append("where ");
                                        else
                                            whereClause.append(" and ");

                                        if(!(propertyValue instanceof String))
                                            whereClause.append(propertyIdBuffer);
                                        else{
                                            if(propertyInfo.isCaseSensitiveSearch())
                                                whereClause.append(propertyIdBuffer);
                                            else{
                                                whereClause.append("lower(");
                                                whereClause.append(propertyIdBuffer);
                                                whereClause.append(")");
                                            }
                                        }

                                        whereClause.append(" ");
                                        whereClause.append(propertyCondition.getOperator());
                                        whereClause.append(" :");
                                        whereClause.append(propertyParamBuffer);

                                        propertyParamBuffer.delete(0, propertyParamBuffer.length());
                                        propertyParamBuffer.append("param");
                                        propertyParamBuffer.append(whereClauseParameters.size());

                                        if(!(propertyValueBuffer instanceof String))
                                            whereClauseParameters.put(propertyParamBuffer.toString(), propertyValueBuffer);
                                        else{
                                            if(propertyInfo.isCaseSensitiveSearch())
                                                whereClauseParameters.put(propertyParamBuffer.toString(), propertyValueBuffer);
                                            else
                                                whereClauseParameters.put(propertyParamBuffer.toString(), StringUtil.trim(propertyValueBuffer).toLowerCase());
                                        }

                                        whereClause.append(" and :");
                                        whereClause.append(propertyParamBuffer);
                                    }
                                    
                                    break;
                                }
                                case SIMILARITY: {
                                    processCondition = (propertyValue instanceof String && StringUtil.trim(propertyValue).length() > 0 && propertyInfo.getPhoneticPropertyId().length() > 0);
                                    
                                    if(processCondition){
                                        if(propertyParamBuffer == null)
                                            propertyParamBuffer = new StringBuilder();
                                        else
                                            propertyParamBuffer.delete(0, propertyParamBuffer.length());
    
                                        propertyParamBuffer.append("param");
    
                                        if(whereClauseParameters == null)
                                            whereClauseParameters = new LinkedHashMap<String, Object>();
    
                                        propertyParamBuffer.append(whereClauseParameters.size());
    
                                        propertyValueBuffer = new StringBuilder();
                                        ((StringBuilder)propertyValueBuffer).append(PhoneticUtil.soundCode(StringUtil.trim(propertyValue)));
                                        ((StringBuilder)propertyValueBuffer).append("%");
    
                                        whereClauseParameters.put(propertyParamBuffer.toString(), propertyValueBuffer.toString());
    
                                        if(whereClause.length() == 0)
                                            whereClause.append("where ");
                                        else
                                            whereClause.append(" and ");
    
                                        propertyIdBuffer.delete(0, propertyIdBuffer.length());
    
                                        if(propertyAlias.length() > 0){
                                            propertyIdBuffer.append(propertyAlias);
                                            propertyIdBuffer.append(".");
                                        }
    
                                        propertyIdBuffer.append(propertyInfo.getPhoneticPropertyId());
    
                                        whereClause.append(propertyIdBuffer);
                                        whereClause.append(" like :");
                                        whereClause.append(propertyParamBuffer);
                                        whereClauseParameters.put(propertyParamBuffer.toString(), propertyValueBuffer.toString());
                                    }
    
                                    break;
                                }
                                case CONTEXT: {
                                    processCondition = (propertyValue instanceof String && StringUtil.trim(propertyValue).length() > 0);
                                    
                                    if(processCondition){
                                        propertyValueBuffer = new StringBuilder();
                                        ((StringBuilder)propertyValueBuffer).append(propertyValue);
    
                                        switch(propertyInfo.getContextSearchType()){
                                            case BOTH: {
                                                ((StringBuilder)propertyValueBuffer).insert(0, "%");
                                                ((StringBuilder)propertyValueBuffer).append("%");
        
                                                propertyValue = propertyValueBuffer.toString();
        
                                                break;
                                            }
                                            case SUFFIX: {
                                                ((StringBuilder)propertyValueBuffer).append("%");
        
                                                propertyValue = propertyValueBuffer.toString();
        
                                                break;
                                            }
                                            case PREFIX: {
                                                ((StringBuilder)propertyValueBuffer).insert(0, "%");
        
                                                propertyValue = propertyValueBuffer.toString();
        
                                                break;
                                            }
                                        }
    
                                        if(propertyParamBuffer == null)
                                            propertyParamBuffer = new StringBuilder();
                                        else
                                            propertyParamBuffer.delete(0, propertyParamBuffer.length());
    
                                        propertyParamBuffer.append("param");
    
                                        if(whereClauseParameters == null)
                                            whereClauseParameters = new LinkedHashMap<String, Object>();
    
                                        propertyParamBuffer.append(whereClauseParameters.size());
    
                                        if(propertyInfo.isCaseSensitiveSearch())
                                            whereClauseParameters.put(propertyParamBuffer.toString(), propertyValue);
                                        else
                                            whereClauseParameters.put(propertyParamBuffer.toString(), StringUtil.trim(propertyValue).toLowerCase());
    
                                        if(whereClause.length() == 0)
                                            whereClause.append("where ");
                                        else
                                            whereClause.append(" and ");
    
                                        if(propertyInfo.isCaseSensitiveSearch())
                                            whereClause.append(propertyIdBuffer);
                                        else{
                                            whereClause.append("lower(");
                                            whereClause.append(propertyIdBuffer);
                                            whereClause.append(")");
                                        }
    
                                        whereClause.append(" ");
                                        whereClause.append(propertyCondition.getOperator());
                                        whereClause.append(" :");
                                        whereClause.append(propertyParamBuffer);
                                    }
    
                                    break;
                                }
                                case IN: {
                                    if(propertyValue != null && ((Collection)propertyValue).size() > 0)
                                        processCondition = false;
    
                                    if(processCondition){
                                        if(propertyParamBuffer == null)
                                            propertyParamBuffer = new StringBuilder();
                                        else
                                            propertyParamBuffer.delete(0, propertyParamBuffer.length());
    
                                        propertyParamBuffer.append("param");
    
                                        if(whereClauseParameters == null)
                                            whereClauseParameters = new LinkedHashMap<String, Object>();
    
                                        propertyParamBuffer.append(whereClauseParameters.size());
    
                                        whereClauseParameters.put(propertyParamBuffer.toString(), propertyValue);
    
                                        if(whereClause.length() == 0)
                                            whereClause.append("where ");
                                        else
                                            whereClause.append(" and ");
    
                                        whereClause.append(propertyIdBuffer);
                                        whereClause.append(" ");
                                        whereClause.append(propertyCondition.getOperator());
                                        whereClause.append(" :");
                                        whereClause.append(propertyParamBuffer);
                                    }
    
                                    break;
                                }
                                case NOT_IN: {
                                    if(propertyValue != null && ((Collection)propertyValue).size() > 0)
                                        processCondition = false;
    
                                    if(processCondition){
                                        if(propertyParamBuffer == null)
                                            propertyParamBuffer = new StringBuilder();
                                        else
                                            propertyParamBuffer.delete(0, propertyParamBuffer.length());
    
                                        propertyParamBuffer.append("param");
    
                                        if(whereClauseParameters == null)
                                            whereClauseParameters = new LinkedHashMap<String, Object>();
    
                                        propertyParamBuffer.append(whereClauseParameters.size());
    
                                        whereClauseParameters.put(propertyParamBuffer.toString(), propertyValue);
    
                                        if(whereClause.length() == 0)
                                            whereClause.append("where ");
                                        else
                                            whereClause.append(" and ");
    
                                        whereClause.append(propertyIdBuffer);
                                        whereClause.append(" ");
                                        whereClause.append(propertyCondition.getOperator());
                                        whereClause.append(" :");
                                        whereClause.append(propertyParamBuffer);
                                    }
    
                                    break;
                                }
                                default: {
                                    if(propertyValue instanceof Number && propertyValue.toString().equals("0"))
                                        processCondition = false;
    
                                    if(propertyValue instanceof String && StringUtil.trim(propertyValue).length() == 0)
                                        processCondition = false;
    
                                    if(propertyValue == null)
                                        processCondition = false;
    
                                    if(processCondition){
                                        if(propertyParamBuffer == null)
                                            propertyParamBuffer = new StringBuilder();
                                        else
                                            propertyParamBuffer.delete(0, propertyParamBuffer.length());
    
                                        propertyParamBuffer.append("param");
    
                                        if(whereClauseParameters == null)
                                            whereClauseParameters = new LinkedHashMap<String, Object>();
    
                                        propertyParamBuffer.append(whereClauseParameters.size());
    
                                        if(!(propertyValue instanceof String))
                                            whereClauseParameters.put(propertyParamBuffer.toString(), propertyValue);
                                        else{
                                            if(propertyInfo.isCaseSensitiveSearch())
                                                whereClauseParameters.put(propertyParamBuffer.toString(), propertyValue);
                                            else
                                                whereClauseParameters.put(propertyParamBuffer.toString(), StringUtil.trim(propertyValue).toLowerCase());
                                        }
    
                                        if(whereClause.length() == 0)
                                            whereClause.append("where ");
                                        else
                                            whereClause.append(" and ");
    
                                        if(!(propertyValue instanceof String))
                                            whereClause.append(propertyIdBuffer);
                                        else{
                                            if(propertyInfo.isCaseSensitiveSearch())
                                                whereClause.append(propertyIdBuffer);
                                            else{
                                                whereClause.append("lower(");
                                                whereClause.append(propertyIdBuffer);
                                                whereClause.append(")");
                                            }
                                        }

                                        whereClause.append(" ");
                                        whereClause.append(propertyCondition.getOperator());
                                        whereClause.append(" :");
                                        whereClause.append(propertyParamBuffer);

                                        if(considerConditions){
                                            identitiesPropertiesInfoBuffer.add(propertyInfo);

                                            if(identitiesPropertiesInfoBuffer.containsAll(identitiesPropertiesInfo))
                                                considerConditions = false;
                                        }
                                    }
    
                                    break;
                                }                                   
                            }
                        }
                        catch(Throwable e){
                        }
                    }
                }
            }
        }
    }    
    
    /**
     * Monta a query HQL (Hibernate Query Language) baseada no modelo de dados especificado.
     * 
     * @param queryType Constante que define o tipo da query.
     * @param model Instância do modelo de dados desejado.
     * @param dao Instância do DAO a ser utilizado.
     * @return Instância da query desejada.
     * @throws InternalErrorException
     */
    public static <M extends BaseModel> Query build(QueryType queryType, M model, HibernateDAO dao) throws InternalErrorException{
        return build(queryType, model, null, dao);
    }

    /**
     * Monta a query HQL (Hibernate Query Language) baseada no modelo de dados especificado.
     * 
     * @param queryType Constante que define o tipo da query.
     * @param model Instância do modelo de dados desejado.
     * @param modelFilter Instância contendo os filtros adicionais do modelo de dados.
     * @param dao Instância do DAO a ser utilizado.
     * @return Instância da query desejada.
     * @throws InternalErrorException
     */
    public static <M extends BaseModel> Query build(QueryType queryType, M model, ModelFilter modelFilter, HibernateDAO dao) throws InternalErrorException{
        try{
            Session             connection            = dao.getConnection();
            String              statementId           = MethodUtil.getMethodFromStackTrace(2).getName();
            Map<String, Object> whereClauseParameters = new LinkedHashMap<String, Object>();
            String              expression            = buildExpression(queryType, model, modelFilter, whereClauseParameters, dao);
            Query               query                 = connection.createQuery(expression);
    
            query.setComment(statementId);
            
            if(modelFilter != null && modelFilter.getReturnProperties() != null && modelFilter.getReturnProperties().size() > 0)
                query.setResultTransformer(Transformers.aliasToBean(model.getClass()));
    
            Object clauseParameterValue = null;
    
            for(String clauseParameter : whereClauseParameters.keySet()){
                clauseParameterValue = whereClauseParameters.get(clauseParameter);
    
                if(clauseParameterValue instanceof Collection)
                    query.setParameterList(clauseParameter, (Collection)clauseParameterValue);
                else
                    query.setParameter(clauseParameter, clauseParameterValue);
            }
    
            if(queryType == QueryType.FIND)
                query.setMaxResults(1);
            else{
                Map<String, String> persistenceOptions = dao.getPersistenceResource().getOptions();
                
                try{
                    String queryMaxResultsBuffer = StringUtil.trim(persistenceOptions.get(PersistenceConstants.DEFAULT_QUERY_MAXIMUM_RESULTS_KEY));
    
                    if(queryMaxResultsBuffer.length() > 0)
                        query.setMaxResults(NumberUtil.parseInt(queryMaxResultsBuffer));
                    else
                        query.setMaxResults(PersistenceConstants.DEFAULT_QUERY_MAXIMUM_RESULTS);
                }
                catch(Throwable e){
                    query.setMaxResults(PersistenceConstants.DEFAULT_QUERY_MAXIMUM_RESULTS);
                }
            }
    
            return query;
        }
        catch(ClassNotFoundException e){
            throw new InternalErrorException(e);
        }
        catch(NoSuchMethodException e){
            throw new InternalErrorException(e);
        }
        catch(IllegalAccessException e){
            throw new InternalErrorException(e);
        }
        catch(InvocationTargetException e){
            throw new InternalErrorException(e);
        }
        catch(InstantiationException e){
            throw new InternalErrorException(e);
        }
    }
}
