package br.com.concepting.framework.persistence;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConstructorUtils;
import org.hibernate.HibernateException;
import org.hibernate.LockOptions;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.ObjectDeletedException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.StaleStateException;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.exceptions.ItemAlreadyExistsException;
import br.com.concepting.framework.model.exceptions.ItemNotFoundException;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.model.types.ConditionType;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.persistence.constants.PersistenceConstants;
import br.com.concepting.framework.persistence.helpers.ModelFilter;
import br.com.concepting.framework.persistence.interfaces.IDAO;
import br.com.concepting.framework.persistence.resource.PersistenceResource;
import br.com.concepting.framework.persistence.types.QueryType;
import br.com.concepting.framework.persistence.types.RelationJoinType;
import br.com.concepting.framework.persistence.types.RelationType;
import br.com.concepting.framework.persistence.util.HibernateUtil;
import br.com.concepting.framework.persistence.util.ModelTransformer;
import br.com.concepting.framework.util.MethodUtil;
import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.PhoneticUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.SortOrderType;
import br.com.concepting.framework.util.types.TransactionType;

/**
 * Classe que define a estrutura básica para as classes de persistência de modelos de dados 
 * utilizando Hibernate.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class HibernateDAO extends BaseDAO{
	private List<PropertyInfo> similarityPropertiesInfo = null;
	private List<String>       similarityPropertiesIds  = null;
	private Collection<String> processedRelations       = null;

    /**
     * Construtor - Inicializa a classe de persistência.
     */
	public HibernateDAO(){
		super();
	}
	
    /**
     * Construtor - Inicializa a classe de persistência.
     * 
     * @param dao Instância da classe de persistência a ser encapsulada.
     */
    public HibernateDAO(IDAO dao){
        super(dao);
    }
    
    /**
     * Reconecta o objeto com o repositório de persistência.
     * 
     * @param model Instância do modelo de dados.
     */
    private <M extends BaseModel> void reattachModel(M model){
        Session connection = getConnection();
        
        try{
            connection.buildLockRequest(LockOptions.NONE).lock(model);
        }
        catch(Throwable e){
        }
    }
    
    /**
     * @see br.com.concepting.framework.persistence.interfaces.IDAO#doTransactionLock(java.lang.Object)
     */
    public <O> void doTransactionLock(O object){
        TransactionType transactionType = getTransactionType();

        if(object instanceof BaseModel){
            Session connection = getConnection();
            
            try{
                if(transactionType == TransactionType.READ_WRITE)
                    connection.buildLockRequest(LockOptions.UPGRADE).lock(object);
                else if(transactionType == TransactionType.READ_ONLY)
                    connection.buildLockRequest(LockOptions.READ).lock(object);
                else if(transactionType == TransactionType.NONE)
                    connection.buildLockRequest(LockOptions.NONE).lock(object);
            }
            catch(Throwable e){
            }
        }
        else if(object instanceof Query){
            if(transactionType == TransactionType.READ_WRITE)
                ((Query)object).setLockOptions(LockOptions.UPGRADE);
            else if(transactionType == TransactionType.READ_ONLY)
                ((Query)object).setLockOptions(LockOptions.READ);
            else if(transactionType == TransactionType.NONE)
                ((Query)object).setLockOptions(LockOptions.NONE);
        }
    }
    
    /**
     * @see br.com.concepting.framework.persistence.interfaces.IDAO#begin()
     */
	public void begin() throws InternalErrorException{
	    try{
    	    Session     connection  = openConnection();
    	    Transaction transaction = connection.getTransaction();
    	    
    	    if(transaction != null){
    	        Integer transactionTimeout = getTransactionTimeout();
    	        
    	        if(transactionTimeout != null && transactionTimeout > 0)
    	            transaction.setTimeout(getTransactionTimeout());
    	        
    	        transaction.begin();
    	    
    	        setTransaction(transaction);
    	    }
	    }
	    catch(Throwable e){
	        throw new InternalErrorException(e);
	    }
    }

	/**
	 * @see br.com.concepting.framework.persistence.interfaces.IDAO#commit()
	 */
    public void commit() throws InternalErrorException{
        try{
            Transaction transaction = getTransaction();

            if(transaction != null)
                transaction.commit();
        }
        catch(Throwable e){
            throw new InternalErrorException(e);
        }
        finally{
            setTransaction(null);
            
            closeConnection();
        }
    }

    /**
     * @see br.com.concepting.framework.persistence.interfaces.IDAO#rollback()
     */
    public void rollback() throws InternalErrorException{
        try{
            Transaction transaction = getTransaction();

            if(transaction != null)
                transaction.commit();
        }
        catch(Throwable e){
            throw new InternalErrorException(e);
        }
        finally{
            setTransaction(null);
            
            closeConnection();
        }
    }

    /**
     * @see br.com.concepting.framework.persistence.interfaces.IDAO#openConnection()
     */
    public <C> C openConnection() throws InternalErrorException{
        Session connection = getConnection();
        
        try{
            if(connection == null || !connection.isOpen()){
                PersistenceResource persistenceRespurce = getPersistenceResource();
                
                connection = HibernateUtil.getSession(persistenceRespurce);
                
                setConnection(connection);
            }
            
            return (C)connection;
        }
        catch(Throwable e){
            throw new InternalErrorException(e);
        }
    }

    /**
     * @see br.com.concepting.framework.persistence.interfaces.IDAO#closeConnection()
     */
    public void closeConnection(){
        try{
            Session connection = getConnection();
            
            connection.close();
        }
        catch(Throwable e){
        }
    }

    /**
	 * Monta a query HQL baseada no modelo de dados.
	 * 
	 * @param model Instância do modelo de dados.
	 * @param referenceProperty Instância do modelo de dados de relacionamento.
	 * @param modelFilter Lista contendo os filtros específicos da query.
	 * @param queryType Constante que define o tipo de query a ser realizado.
	 * @throws InternalErrorException
	 */
	private <M extends BaseModel> Query buildQuery(M model, Object referenceProperty, ModelFilter modelFilter, QueryType queryType) throws InternalErrorException{
		try{
    		Session             connection       = getConnection();
    		String              statementId      = MethodUtil.getMethodFromStackTrace(2).getName();
    		Map<String, Object> clauseParameters = new LinkedHashMap<String, Object>();
    		String              queryString      = buildQueryString(model, modelFilter, clauseParameters, queryType);
    		Query               query            = (queryType != QueryType.LOAD_REFERENCE ? connection.createQuery(queryString) : connection.createFilter(referenceProperty, queryString));
    
    		query.setComment(statementId);
    		
    		doTransactionLock(query);
    		
    		if(modelFilter != null && modelFilter.getReturnProperties() != null && modelFilter.getReturnProperties().size() > 0)
    			query.setResultTransformer(new ModelTransformer(model.getClass()));
    
    		Object clauseParameterValue = null;
    
    		for(String clauseParameter : clauseParameters.keySet()){
    			clauseParameterValue = clauseParameters.get(clauseParameter);
    
    			if(clauseParameterValue instanceof Collection)
    				query.setParameterList(clauseParameter, (Collection)clauseParameterValue);
    			else
    				query.setParameter(clauseParameter, clauseParameterValue);
    		}
    
    		if(queryType == QueryType.FIND)
    			query.setMaxResults(1);
    		else{
    			Map<String, String> persistenceOptions = getPersistenceResource().getOptions();
    		    
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
	
	/**
	 * Monta a string da query HQL baseada no modelo de dados.
	 * 
	 * @param model Instância do modelo de dados.
	 * @param modelFilter Lista contendo os filtros específicos da query.
	 * @param whereClauseParameters Mapa contendo os parâmetros da cláusula where.
	 * @param queryType Constante que define o tipo de query a ser realizado.
	 * @throws InstantiationException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws ClassNotFoundException 
	 */
	private <M extends BaseModel> String buildQueryString(M model, ModelFilter modelFilter, Map<String, Object> whereClauseParameters, QueryType queryType) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException{
		similarityPropertiesInfo = null;
		similarityPropertiesIds  = null;
		processedRelations       = null;

		StringBuilder selectClause   = new StringBuilder();
		StringBuilder fromClause     = new StringBuilder();
		StringBuilder whereClause    = new StringBuilder();
		StringBuilder joinClause     = new StringBuilder();
		StringBuilder groupByClause  = new StringBuilder();
		StringBuilder orderByClause  = new StringBuilder();
		StringBuilder queryBuffer    = new StringBuilder();
		Boolean       hasFormula     = false;
		String        propertyPrefix = (queryType == QueryType.LOAD_REFERENCE ? PersistenceConstants.DEFAULT_MODEL_ALIAS : "");

		buildQueryString(model, propertyPrefix, propertyPrefix, selectClause, fromClause, joinClause, whereClause, groupByClause, orderByClause, whereClauseParameters, modelFilter, true, hasFormula, queryType, false);

		if(selectClause.length() > 0){
			queryBuffer.append(StringUtil.trim(selectClause));
			queryBuffer.append(" ");
		}

		queryBuffer.append(StringUtil.trim(fromClause));

		if(joinClause.length() > 0){
			queryBuffer.append(" ");
			queryBuffer.append(StringUtil.trim(joinClause));
		}

		if(whereClause.length() > 0){
			queryBuffer.append(" ");
			queryBuffer.append(StringUtil.trim(whereClause));
		}

		if(hasFormula && groupByClause.length() > 0){
			queryBuffer.append(" ");
			queryBuffer.append(StringUtil.trim(groupByClause));
		}

		if(orderByClause.length() > 0){
			queryBuffer.append(" ");
			queryBuffer.append(StringUtil.trim(orderByClause));
		}
		
		return queryBuffer.toString();
	}

    /**
     * Monta a string da query HQL baseada no modelo de dados.
     * 
     * @param model Instância do modelo de dados.
     * @param propertyPrefix String contendo o prefixo da propriedade.
     * @param propertyAlias String contendo o alias da propriedade.
     * @param selectClause String contendo a cláusula select.
     * @param fromClause String contendo a cláusula from.
     * @param joinClause String contendo as cláusulas join.
     * @param whereClause String contendo a cláusula where.
     * @param groupByClause String contend a cláusula group by.
     * @param whereClauseParameters Mapa contendo os parâmetros da cláusula where.
     * @param modelFilter Lista contendo os filtros específicos da query.
     * @param queryType Constante que define o tipo de query a ser realizado.
     * @param parentIsComponent Indica se a propriedade faz parte de um componente.
     * @param parentComponentProperties Array contendo as propriedades do componente.
     * @throws ClassNotFoundException 
     * @throws InstantiationException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws NoSuchMethodException 
     */
    private <M extends BaseModel> void buildQueryString(M model, String propertyPrefix, String propertyAlias, StringBuilder selectClause, StringBuilder fromClause, StringBuilder joinClause, StringBuilder whereClause, StringBuilder groupByClause, StringBuilder orderByClause, Map<String, Object> whereClauseParameters, ModelFilter modelFilter, Boolean considerConditions, Boolean hasFormula, QueryType queryType, Boolean parentIsComponent, String... parentComponentProperties) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException{
		Boolean   modelIsNull = (model == null);
		Class     modelClass  = (modelIsNull ? ModelUtil.getModelClassByPersistence(getClass()) : model.getClass());
		ModelInfo modelInfo   = ModelUtil.getModelInfo(modelClass);

		if(modelIsNull)
			model = (M)ConstructorUtils.invokeConstructor(modelClass, null);

		if(modelInfo != null){
			StringBuilder propertyAliasBuffer = null;

			if(fromClause.length() == 0){
				if(queryType != QueryType.LOAD_REFERENCE){
					propertyAliasBuffer = new StringBuilder();
					propertyAliasBuffer.append(modelInfo.getClazz().getSimpleName().toLowerCase());
					propertyAliasBuffer.append((int)(Math.random() * 1000));

					propertyAlias = propertyAliasBuffer.toString();

					fromClause.append("from ");
					fromClause.append(modelInfo.getClazz().getSimpleName());
					fromClause.append(" ");
					fromClause.append(propertyAlias);
				}
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
			Collection                 propertyValueList              = null;
			SortOrderType              propertySortOrder              = null;
			ConditionType              propertyCondition              = null;
			ModelInfo                  propertyModelInfo              = null;
			PropertyInfo               propertyModelIdentity          = null;
			M                          relationModel                  = null;
			Boolean                    processModel                   = false;
			Boolean                    processCondition               = false;
			Map<String, SortOrderType> sortOrders                     = (modelFilter != null ? modelFilter.getSortOrders() : null);
			Integer                    count                          = 0;
			Boolean                    foundParentComponentProperty   = false;

			for(PropertyInfo propertyInfo : propertiesInfo){
			    hasFormula = propertyInfo.getFormula().length() > 0;
			    
                if(groupByClause.length() == 0)
                    groupByClause.append("group by ");
                else
                    groupByClause.append(", ");
                    
                groupByClause.append(propertyAlias);
                groupByClause.append(".");
                groupByClause.append(propertyInfo.getId());

                foundParentComponentProperty = false;

                if(parentIsComponent){
			        if(parentComponentProperties != null && parentComponentProperties.length > 0){
				        for(String parentComponentProperty : parentComponentProperties){
				            if(parentComponentProperty.equals(propertyInfo.getId())){
				                foundParentComponentProperty = true;
				                
				                break;
				            }
				        }
			        }
			    }
			    
			    if(parentIsComponent && !foundParentComponentProperty)
			        continue;
			    
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

				if(returnProperties != null && returnProperties.contains(propertyPrefixBuffer.toString())){
					if(selectClause.length() == 0)
						selectClause.append("select ");
					else
						selectClause.append(",");

					selectClause.append(propertyIdBuffer);
				}
				else if(returnProperties == null){
					if(selectClause.length() == 0){
						selectClause.append("select ");
						selectClause.append(propertyAlias);
					}
				}
				
				propertySortOrder = (sortOrders != null ? sortOrders.get(propertyPrefixBuffer.toString()) : null);

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

				if(queryType != QueryType.LOAD_REFERENCE){
					if((propertyInfo.getRelationJoinType() != RelationJoinType.NONE && propertyInfo.hasModel()) || propertyInfo.isModel()){
						relationModel = null;
						propertyValue = null;
						processModel  = !processedRelations.contains(propertyPrefixBuffer.toString()) && !propertyPrefixBuffer.toString().contains("parent.parent");
						
						if(processModel){
							if(propertyInfo.hasModel()){
								modelInfo     = ModelUtil.getModelInfo(propertyInfo.getCollectionItemsClass());
								relationModel = (M)ConstructorUtils.invokeConstructor(propertyInfo.getCollectionItemsClass(), null);
							}
							else{
								modelInfo         = ModelUtil.getModelInfo(propertyInfo.getClazz());
								propertyCondition = (propertyConditions != null ? propertyConditions.get(propertyPrefixBuffer.toString()) : null);
								
								if(propertyCondition == null)
									propertyCondition = propertyInfo.getSearchCondition();

								try{
    								propertyValue = (propertyValues != null ? propertyValues.get(propertyPrefixBuffer.toString()) : PropertyUtil.getProperty(model, propertyInfo.getId()));
    
    								if(propertyValue == null)
    									propertyValue = PropertyUtil.getProperty(model, propertyInfo.getId());
    								
    								if(propertyValue instanceof BaseModel)
    									relationModel = (M)propertyValue;
    								else if(propertyValue instanceof Collection)
                                        relationModel = (M)ConstructorUtils.invokeConstructor(propertyInfo.getClazz(), null);
								}
								catch(Throwable e){
								}
							}
							
    						if(modelInfo != null && (propertyValue != null || relationModel != null)){
    							processedRelations.add(propertyPrefixBuffer.toString());
    
                                if(propertyInfo.getRelationJoinType() != RelationJoinType.NONE || (propertyInfo.isModel() && propertyInfo.isIdentity())){
        							if(propertyAliasBuffer == null)
        								propertyAliasBuffer = new StringBuilder();
        							else
        								propertyAliasBuffer.delete(0, propertyAliasBuffer.length());
        
        							if(propertyInfo.hasModel())
        								propertyAliasBuffer.append(propertyInfo.getCollectionItemsClass().getSimpleName().toLowerCase());
        							else
        								propertyAliasBuffer.append(propertyInfo.getClazz().getSimpleName().toLowerCase());
        							
        							propertyAliasBuffer.append((int)(Math.random() * 1000));
    							
                                    if(joinClause.length() > 0)
                                        joinClause.append(" ");
    
                                    if(propertyInfo.getRelationJoinType() != RelationJoinType.NONE)
                                        joinClause.append(propertyInfo.getRelationJoinType().getOperator());
                                    else
                                        joinClause.append(RelationJoinType.INNER_JOIN.getOperator());

                                    joinClause.append(" ");
                                    joinClause.append(propertyIdBuffer);
                                    joinClause.append(" ");
                                    joinClause.append(propertyAliasBuffer);

                                    buildQueryString(relationModel, propertyPrefixBuffer.toString(), propertyAliasBuffer.toString(), selectClause, fromClause, joinClause, whereClause, groupByClause, orderByClause, whereClauseParameters, modelFilter, considerConditions, hasFormula, queryType, false);
                                }
                                else
                                    buildQueryString(relationModel, propertyPrefixBuffer.toString(), propertyIdBuffer.toString(), selectClause, fromClause, joinClause, whereClause, groupByClause, orderByClause, whereClauseParameters, modelFilter, considerConditions, hasFormula, queryType, true, propertyInfo.getPropertiesIds());
    						}
						}

						continue;
					}
				}

				if((propertyInfo.isIdentity() || propertyInfo.isForSearch()) && ((parentIsComponent && foundParentComponentProperty) || propertyInfo.getMappedPropertyId().length() > 0 || propertyInfo.getMappedPropertiesIds().length > 0 || propertyInfo.getMappedRelationPropertiesIds().length > 0)){
					if(considerConditions){
						processCondition = true;
						
						try{
							propertyValue     = (propertyValues != null ? propertyValues.get(propertyPrefixBuffer.toString()) : PropertyUtil.getProperty(model, propertyInfo.getId()));
							propertyCondition = (propertyConditions != null ? propertyConditions.get(propertyPrefixBuffer.toString()) : null);
							
							if(queryType != QueryType.FIND)
    							if(propertyCondition == null)
    								propertyCondition = propertyInfo.getSearchCondition();
							
							if(propertyCondition == null)
								propertyCondition = ConditionType.EQUAL;

							if(propertyValue == null)
								propertyValue = PropertyUtil.getProperty(model, propertyInfo.getId());

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
    								}
    
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
    								if(propertyValues != null){
    									if(propertyValue instanceof Object[]){
    										propertyValueBuffer = ((Object[])propertyValue)[1];
    										propertyValue = ((Object[])propertyValue)[0];
    									}
    								}
    
    								if(propertyValue instanceof String && StringUtil.trim(propertyValue).length() == 0)
    									processCondition = false;
    
    								if(propertyValue == null)
    									processCondition = false;
    
    								if(processCondition){
    									try{
        									if(propertyValueBuffer == null)
        										propertyValueBuffer = PropertyUtil.getProperty(model, (propertyInfo.getSearchPropertyId().length() > 0 ? propertyInfo.getSearchPropertyId() : propertyInfo.getId()));
        
        									if(propertyValueBuffer instanceof String && StringUtil.trim(propertyValueBuffer).length() == 0)
        										propertyValueBuffer = propertyValue;
        
        									if(propertyValueBuffer == null)
        										propertyValueBuffer = propertyValue;
        
        									if(propertyValueBuffer instanceof String && StringUtil.trim(propertyValueBuffer).length() == 0)
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
    									}
    									catch(Throwable e){
    									}
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
    
    									if(similarityPropertiesInfo == null){
    										similarityPropertiesInfo = new LinkedList<PropertyInfo>();
    										similarityPropertiesIds = new LinkedList<String>();
    									}
    
    									similarityPropertiesInfo.add(propertyInfo);
    									similarityPropertiesIds.add(propertyPrefixBuffer.toString());
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
    								propertyValueBuffer = (propertyValues != null ? propertyValues.get(propertyPrefixBuffer.toString()) : null);
    								
    								try{
        								if(propertyValueBuffer == null)
        									propertyValueBuffer = PropertyUtil.getProperty(model, (propertyInfo.getSearchPropertyId().length() > 0 ? propertyInfo.getSearchPropertyId() : propertyInfo.getId()));
        
        								if(propertyValueBuffer instanceof Collection){
        									count = ((Collection)propertyValueBuffer).size();
        									
        									if(count > 0){
        										if(propertyInfo.isModel())
        											propertyModelInfo = ModelUtil.getModelInfo(propertyInfo.getClazz());
        										else if(propertyInfo.hasModel())
        											propertyModelInfo = ModelUtil.getModelInfo(propertyInfo.getCollectionItemsClass());
        
        										if(whereClause.length() == 0)
        											whereClause.append("where ");
        										else
        											whereClause.append(" and ");
        
        										whereClause.append(propertyIdBuffer);
        
        										if(propertyModelInfo != null){
        											propertyModelIdentity = propertyModelInfo.getIdentityPropertiesInfo().iterator().next();
        
        											whereClause.append(".");
        											whereClause.append(propertyModelIdentity.getId());
        										}
        
            									whereClause.append(" ");
            									whereClause.append(propertyCondition.getOperator());
            									whereClause.append(" (");
        
        										if(propertyParamBuffer == null)
        											propertyParamBuffer = new StringBuilder();
        										else
        											propertyParamBuffer.delete(0, propertyParamBuffer.length());
        
        										propertyParamBuffer.append("param");
        
        										if(whereClauseParameters == null)
        											whereClauseParameters = new LinkedHashMap<String, Object>();
        
        										propertyParamBuffer.append(whereClauseParameters.size());
        
        										if(propertyModelInfo != null){
        											propertyValueList = new LinkedList();
        
        											for(Object propertyValueListItem : (Collection)propertyValueBuffer)
        												propertyValueList.add(PropertyUtil.getProperty(propertyValueListItem, propertyModelIdentity.getId()));
        
        											propertyValueBuffer = propertyValueList;
        										}
        
        										whereClauseParameters.put(propertyParamBuffer.toString(), propertyValueBuffer);
        
        										whereClause.append(":");
        										whereClause.append(propertyParamBuffer);
        										whereClause.append(")");
        									}
        								}
    								}
    								catch(Throwable e){
    								}
    
    								break;
    							}
    							case NOT_IN: {
    								propertyValueBuffer = (propertyValues != null ? propertyValues.get(propertyPrefixBuffer.toString()) : null);
    								
    								try{
        								if(propertyValueBuffer == null)
        									propertyValueBuffer = PropertyUtil.getProperty(model, (propertyInfo.getSearchPropertyId().length() > 0 ? propertyInfo.getSearchPropertyId() : propertyInfo.getId()));
        
        								if(propertyValueBuffer instanceof Collection){
        									count = ((Collection)propertyValueBuffer).size();
        									
        									if(count > 0){
        										if(propertyInfo.isModel())
        											propertyModelInfo = ModelUtil.getModelInfo(propertyInfo.getClazz());
        										else if(propertyInfo.hasModel())
        											propertyModelInfo = ModelUtil.getModelInfo(propertyInfo.getCollectionItemsClass());
        
        										if(whereClause.length() == 0)
        											whereClause.append("where ");
        										else
        											whereClause.append(" and ");
        
        										whereClause.append(propertyIdBuffer);
        
        										if(propertyModelInfo != null){
        											propertyModelIdentity = propertyModelInfo.getIdentityPropertiesInfo().iterator().next();
        
        											whereClause.append(".");
        											whereClause.append(propertyModelIdentity.getId());
        										}
        
            									whereClause.append(" ");
            									whereClause.append(propertyCondition.getOperator());
            									whereClause.append(" (");
        
        										if(propertyParamBuffer == null)
        											propertyParamBuffer = new StringBuilder();
        										else
        											propertyParamBuffer.delete(0, propertyParamBuffer.length());
        
        										propertyParamBuffer.append("param");
        
        										if(whereClauseParameters == null)
        											whereClauseParameters = new LinkedHashMap<String, Object>();
        
        										propertyParamBuffer.append(whereClauseParameters.size());
        
        										if(propertyModelInfo != null){
        											propertyValueList = new LinkedList();
        
        											for(Object propertyValueListItem : (Collection)propertyValueBuffer)
        												propertyValueList.add(PropertyUtil.getProperty(propertyValueListItem, propertyModelIdentity.getId()));
        
        											propertyValueBuffer = propertyValueList;
        										}
        
        										whereClauseParameters.put(propertyParamBuffer.toString(), propertyValueBuffer);
        
        										whereClause.append(":");
        										whereClause.append(propertyParamBuffer);
        										whereClause.append(")");
        									}
        								}
    								}
    								catch(Throwable e){
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
	 * @see br.com.concepting.framework.persistence.BaseDAO#delete(br.com.concepting.framework.model.BaseModel)
	 */
	public <M extends BaseModel> void delete(M model) throws InternalErrorException{
		Session connection = getConnection();

		try{
		    doTransactionLock(model);
		    
			connection.delete(model);
		}
		catch(ObjectNotFoundException e){
		}
		catch(ObjectDeletedException e){
		}
        catch(StaleStateException e){
        }
		catch(HibernateException e){
			throw new InternalErrorException(e);
		}
	}

	/**
	 * @see br.com.concepting.framework.persistence.BaseDAO#find(br.com.concepting.framework.model.BaseModel)
	 */
    public <M extends BaseModel> M find(M model) throws InternalErrorException, ItemNotFoundException{
		try{
			if(model == null)
				throw new ItemNotFoundException();

			Query query       = buildQuery(model, null, null, QueryType.FIND);
			M     queryResult = (M)query.uniqueResult();

			if(queryResult == null)
				throw new ItemNotFoundException();

			return queryResult;
		}
		catch(ObjectNotFoundException e){
			throw new ItemNotFoundException();
		}
		catch(NonUniqueResultException e){
			throw new ItemNotFoundException();
		}
		catch(HibernateException e){
			throw new InternalErrorException(e);
		}
	}

	/**
	 * @see br.com.concepting.framework.persistence.BaseDAO#list()
	 */
    public <C extends Collection> C list() throws InternalErrorException{
		return (C)search(null);
	}

	/**
	 * @see br.com.concepting.framework.persistence.BaseDAO#search(br.com.concepting.framework.model.BaseModel)
	 */
    public <M extends BaseModel, C extends Collection> C search(M model) throws InternalErrorException{
		return (C)search(model, null);
	}

	/**
	 * @see br.com.concepting.framework.persistence.BaseDAO#search(br.com.concepting.framework.model.BaseModel, br.com.concepting.framework.persistence.helpers.ModelFilter)
	 */
    public <M extends BaseModel, C extends Collection> C search(M model, ModelFilter modelFilter) throws InternalErrorException{
		try{
			Query   query     = buildQuery(model, null, modelFilter, QueryType.SEARCH);
			List<M> modelList = query.list();
			
			modelList = filterBySimilarity(modelList, model);

			return (C)modelList;
		}
        catch(IllegalAccessException e){
            throw new InternalErrorException(e);
        }
        catch(InvocationTargetException e){
            throw new InternalErrorException(e);
        }
        catch(NoSuchMethodException e){
            throw new InternalErrorException(e);
        }
		catch(HibernateException e){
            throw new InternalErrorException(e);
		}
	}

	/**
	 * Filtra uma lista de modelos de dados por similaridade.
	 * 
	 * @param modelList Lista contendo os modelos de dados.
	 * @param model Instância contendo o modelo de dados que servirá como base de comparação.
	 * @return Lista contendo os modelos de dados que satisfazem a(s) regra(s) de similaridade.
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private <M extends BaseModel> List<M> filterBySimilarity(List<M> modelList, M model) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		if(model != null){
    		M modelListItem = null;
    
    		if(similarityPropertiesInfo != null && similarityPropertiesInfo.size() != 0){
    			String  propertyValueBuffer       = "";
    			String  propertyValue             = "";
    			Double  compareSimilarityAccuracy = 0d;
    			Integer similarityAccuracyCount   = 0;
    			Double  similarityAccuracy        = 0d;
    
    			for(Integer cont1 = 0; cont1 < modelList.size() ; cont1++){
    				modelListItem             = modelList.get(cont1);
    				similarityAccuracyCount   = 0;
    				compareSimilarityAccuracy = 0d;
    				similarityAccuracy        = 0d;
    
    				for(Integer cont2 = 0; cont2 < similarityPropertiesInfo.size() ; cont2++){
    					propertyValueBuffer = StringUtil.trim(PropertyUtil.getProperty(modelListItem, similarityPropertiesIds.get(cont2)));
    					propertyValue       = StringUtil.trim(PropertyUtil.getProperty(model, similarityPropertiesIds.get(cont2)));
    
    					if(propertyValue.length() > 0){
    						similarityAccuracy        += PhoneticUtil.similarityAccuracy(propertyValue, propertyValueBuffer);
    						compareSimilarityAccuracy += similarityPropertiesInfo.get(cont2).getSimilarityAccuracy();
    
    						similarityAccuracyCount++;
    					}
    				}
    
    				similarityAccuracy        = similarityAccuracy / similarityAccuracyCount;
    				compareSimilarityAccuracy = compareSimilarityAccuracy / similarityAccuracyCount;
    
    				if(similarityAccuracy < compareSimilarityAccuracy){
    					modelList.remove(modelListItem);
    
    					cont1--;
    				}
    				else{
						modelListItem.setSimilarityAccuracy(similarityAccuracy);

						modelList.set(cont1, modelListItem);
    				}
    			}
    
    			ModelUtil.sort(modelList, "similarityAccuracy", SortOrderType.DESCEND);
    		}
		}
		
		return modelList;
	}

	/**
	 * @see br.com.concepting.framework.persistence.BaseDAO#loadReference(br.com.concepting.framework.model.BaseModel, java.lang.String)
	 */	
	public <M extends BaseModel> M loadReference(M model, String referencePropertyId) throws InternalErrorException{
		try{
			if(model == null)
				return model;
			
            reattachModel(model);
            
			Class     modelClass = model.getClass();
			ModelInfo modelInfo  = ModelUtil.getModelInfo(modelClass);

			if(modelInfo == null)
				return model;

			PropertyInfo propertyInfo = modelInfo.getPropertyInfo(referencePropertyId);

			if(propertyInfo == null || propertyInfo.getRelationType() == RelationType.NONE)
				return model;

			Object referenceProperty = PropertyUtil.getProperty(model, referencePropertyId);
			
			if(propertyInfo.hasModel()){
				List<M> modelList = (List<M>)referenceProperty;
				
				if(modelList != null){
					for(Integer cont = 0 ; cont < modelList.size() ; cont++)
						modelList.get(cont);
				}
			}

			return model;
		}
		catch(Throwable e){
        }
		
		return model;
	}

	/**
	 * @see br.com.concepting.framework.persistence.BaseDAO#saveReference(br.com.concepting.framework.model.BaseModel, java.lang.String)
	 */
	public <M extends BaseModel> void saveReference(M model, String referencePropertyId) throws InternalErrorException{
		try{
			if(model == null)
				return;

			Class     modelClass = model.getClass();
			ModelInfo modelInfo  = ModelUtil.getModelInfo(modelClass); 

			if(modelInfo == null) 
				return;

			PropertyInfo propertyInfo = modelInfo.getPropertyInfo(referencePropertyId);

			if(propertyInfo == null || propertyInfo.getRelationType() == RelationType.NONE)
				return;

			Object referencePropertyValueBuffer = PropertyUtil.getProperty(model, referencePropertyId);

			reattachModel(model);
			doTransactionLock(model);
			
			if(propertyInfo.getRelationType() != RelationType.ONE_TO_ONE)
			    PropertyUtil.setProperty(model, referencePropertyId, null);

			PropertyUtil.setProperty(model, referencePropertyId, referencePropertyValueBuffer);
		}
		catch(Throwable e){
			throw new InternalErrorException(e);
		}
	}

	/**
	 * @see br.com.concepting.framework.persistence.BaseDAO#deleteAll(java.util.Collection)
	 */
	public <M extends BaseModel> void deleteAll(Collection<M> modelList) throws InternalErrorException{
	    if(modelList == null || modelList.size() == 0)
	        return;
	    
		Session connection = getConnection();

		try{
    		M           model    = null;
    		Iterator<M> iterator = modelList.iterator();

    		while(iterator.hasNext()){
    			model = iterator.next();
    			
                doTransactionLock(model);

    			connection.delete(model);
    		}
		}
		catch(ObjectNotFoundException e){
		}
		catch(ObjectDeletedException e){
		}
		catch(HibernateException e){
			throw new InternalErrorException(e);
		}
	}

	/**
	 * @see br.com.concepting.framework.persistence.BaseDAO#deleteAll()
	 */
	public <M extends BaseModel> void deleteAll() throws InternalErrorException{
		Collection<M> modelList = list();

		deleteAll(modelList);
	}

	/**
	 * @see br.com.concepting.framework.persistence.BaseDAO#save(br.com.concepting.framework.model.BaseModel)
	 */
	public <M extends BaseModel> void save(M model) throws ItemAlreadyExistsException, InternalErrorException{
		Session connection = getConnection();

		try{
		    reattachModel(model);
		    doTransactionLock(model);

			connection.saveOrUpdate(model);
		}
		catch(NonUniqueObjectException e){
		    throw new ItemAlreadyExistsException();
		}
		catch(ObjectNotFoundException e){
		}
		catch(StaleStateException e){
		}
		catch(ConstraintViolationException e){
    		if(e.getMessage().toLowerCase().contains("duplicate"))
    			throw new ItemAlreadyExistsException();
    		
			throw new InternalErrorException(e);
		}
		catch(HibernateException e){
			throw new InternalErrorException(e);
		}
	}

	/**
	 * @see br.com.concepting.framework.persistence.BaseDAO#insert(br.com.concepting.framework.model.BaseModel)
	 */
	public <M extends BaseModel> void insert(M model) throws ItemAlreadyExistsException, InternalErrorException{
		Session connection = getConnection();

		try{
            connection.save(model);
		}
		catch(NonUniqueObjectException e){
		    throw new ItemAlreadyExistsException();
		}
		catch(ObjectNotFoundException e){
		}
		catch(StaleStateException e){
		}
		catch(ConstraintViolationException e){
    		if(e.getMessage().toLowerCase().contains("duplicate"))
    			throw new ItemAlreadyExistsException();
    		
			throw new InternalErrorException(e);
		}
		catch(HibernateException e){
			throw new InternalErrorException(e);
		}
	}

	/**
	 * @see br.com.concepting.framework.persistence.BaseDAO#update(br.com.concepting.framework.model.BaseModel)
	 */
	public <M extends BaseModel> void update(M model) throws InternalErrorException{
		Session connection = getConnection();

		try{
            reattachModel(model);
            doTransactionLock(model);

            connection.update(model);
		}
		catch(NonUniqueObjectException e){
		}
		catch(ObjectNotFoundException e){
		}
		catch(StaleStateException e){
		}
		catch(ConstraintViolationException e){
			throw new InternalErrorException(e);
		}
		catch(HibernateException e){
			throw new InternalErrorException(e);
		}
	}

	/**
	 * @see br.com.concepting.framework.persistence.BaseDAO#saveAll(java.util.Collection)
	 */
	public <M extends BaseModel> void saveAll(Collection<M> modelList) throws ItemAlreadyExistsException, InternalErrorException{
	    if(modelList == null || modelList.size() == 0)
	        return;
	     
		Session connection = getConnection();

		try{
            M           model    = null;
			Iterator<M> iterator = modelList.iterator();
			
			while(iterator.hasNext()){
				model = iterator.next();
				
				reattachModel(model);
				doTransactionLock(model);

				connection.saveOrUpdate(model);
			}
		}
		catch(NonUniqueObjectException e){
            throw new ItemAlreadyExistsException();
		}
		catch(ObjectNotFoundException e){
		}
		catch(StaleStateException e){
		}
		catch(ConstraintViolationException e){
    		if(e.getMessage().toLowerCase().contains("duplicate"))
    			throw new ItemAlreadyExistsException();

    		throw new ItemAlreadyExistsException();
		}
		catch(HibernateException e){
			throw new InternalErrorException(e);
		}
	}

	/**
	 * @see br.com.concepting.framework.persistence.BaseDAO#insertAll(java.util.Collection)
	 */
	public <M extends BaseModel> void insertAll(Collection<M> modelList) throws ItemAlreadyExistsException, InternalErrorException{
	    if(modelList == null || modelList.size() == 0)
	        return;
	    
		Session connection = getConnection();

		try{
			M           model    = null;
			Iterator<M> iterator = modelList.iterator();
			
			while(iterator.hasNext()){
    			model = iterator.next();
    			
    			connection.save(model);
    		}
    	}
    	catch(NonUniqueObjectException e){
            throw new ItemAlreadyExistsException();
    	}
		catch(ObjectNotFoundException e){
		}
    	catch(StaleStateException e){
    	}
    	catch(ConstraintViolationException e){
    		if(e.getMessage().toLowerCase().contains("duplicate"))
    			throw new ItemAlreadyExistsException();
    		
    		throw new InternalErrorException(e);
    	}
    	catch(HibernateException e){
    		throw new InternalErrorException(e);
    	}
	}

	/**
	 * @see br.com.concepting.framework.persistence.BaseDAO#updateAll(java.util.Collection)
	 */
	public <M extends BaseModel> void updateAll(Collection<M> modelList) throws InternalErrorException{
	    if(modelList == null || modelList.size() == 0)
	        return;
	    
		Session connection = getConnection();

		try{
    		M           model    = null;
    		Iterator<M> iterator = modelList.iterator();
    		
    		while(iterator.hasNext()){
    			model = iterator.next();
    			
    			reattachModel(model);
    			doTransactionLock(model);

    			connection.update(model);
    		}
		}
		catch(NonUniqueObjectException e){
		}
		catch(ObjectNotFoundException e){
		}
		catch(StaleStateException e){
		}
		catch(ConstraintViolationException e){
			throw new InternalErrorException(e);
		}
		catch(HibernateException e){
			throw new InternalErrorException(e);
		}
	}
}