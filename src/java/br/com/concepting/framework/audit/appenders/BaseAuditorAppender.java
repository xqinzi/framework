package br.com.concepting.framework.audit.appenders;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.log4j.Layout;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

import br.com.concepting.framework.audit.Auditor;
import br.com.concepting.framework.audit.annotations.Auditable;
import br.com.concepting.framework.audit.constants.AuditorConstants;
import br.com.concepting.framework.audit.model.AuditorBusinessComplementModel;
import br.com.concepting.framework.audit.model.AuditorModel;
import br.com.concepting.framework.audit.types.AuditorStatusType;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.SystemSessionModel;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.security.model.UserModel;
import br.com.concepting.framework.util.ExceptionUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.DateTime;

/**
 * Classe que define a estrutura b�sica para um mecanismo de exibi��o/armazenamento 
 * das mensagens de auditoria.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class BaseAuditorAppender extends WriterAppender{
	private Auditor auditor    = null;
	private String  modelClass = "";
	private Integer queueCount = 0;
	private Integer queueSize  = AuditorConstants.DEFAULT_QUEUE_SIZE;
	
	/**
	 * Construtor - Inicializa objetos e/ou vari�veis internas.
	 * 
	 * @param auditor Inst�ncia da classe respons�vel por efetuar a auditoria.
	 */
	public BaseAuditorAppender(Auditor auditor){
	    super();
	    
	    setAuditor(auditor);
	}
	
	/**
	 * Retorna o identificador do modelo de dados de auditoria.
	 * 
	 * @return String contendo o identificador do modelo de dados.
	 */
	public String getModelClass(){
		return modelClass;
	}

	/**
	 * Define o identificador do modelo de dados de auditoria.
	 * 
	 * @param modelClass String contendo o identificador do modelo de dados.
	 */
	public void setModelClass(String modelClass){
		this.modelClass = modelClass;
	}
	
	/**
	 * Define a inst�ncia da classe respons�vel pela auditoria.
	 * 
     * @param auditor Inst�ncia da classe respons�vel por efetuar a auditoria.
	 */
	public void setAuditor(Auditor auditor){
	    this.auditor = auditor;
	}
	
	/**
	 * Retorna a inst�ncia da classe respons�vel pela auditoria.
	 * 
	 * @return Inst�ncia da classe respons�vel por efetuar a auditoria.
	 */
	public Auditor getAuditor(){
	    return auditor;
	}
	
	/**
	 * Retorna o identificador da entidade a ser auditada.
	 * 
	 * @param entity Classe que define a entidade a ser auditada.
	 * @return String contendo o identificador da entidade.
	 */
	private String getEntityId(Class<?> entity){
	    return getEntityId(entity, true);
	}
	
    /**
     * Retorna o identificador da entidade a ser auditada.
     * 
     * @param entity Classe que define a entidade a ser auditada.
     * @param lastIteration True/False.
     * @return String contendo o identificador da entidade.
     */
	private String getEntityId(Class<?> entity, Boolean lastIteration){
	    String entityId = "";
	    
	    if(entity != null){
            Auditable annotation = entity.getAnnotation(Auditable.class);
            
            if(annotation == null){
                Class<?> superClasses[] = entity.getInterfaces();
    
                if(superClasses != null && superClasses.length > 0){
                    for(Class<?> superClass : superClasses){
                        entityId = StringUtil.trim(getEntityId(superClass, false));
                        
                        if(entityId.length() > 0)
                            break;
                    }
                }
            }
            else
                entityId = StringUtil.trim(annotation.entityId());
            
            if(lastIteration && entityId.length() == 0)
                entityId = entity.getSimpleName();
	    }

	    return entityId;
	}
	
    /**
     * Retorna o identificador do neg�cio a ser auditado.
     * 
     * @param method Classe que define o neg�cio a ser auditado.
     * @return String contendo o identificador da entidade.
     */
	private String getBusinessId(Method method){
        Method    business   = auditor.getBusiness();
        Auditable annotation = (business != null ? business.getAnnotation(Auditable.class) : null);
        String    businessId = "";
        
        if(annotation != null)
            businessId = annotation.businessId();
            
        if(businessId.length() == 0)
            businessId = business.getName();
            
        return businessId;
	}
	
	/**
	 * Retorna a inst�ncia do modelo de dados de auditoria.
	 * 
	 * @param event Inst�ncia contendo as propriedades do evento a ser auditado.
	 * @return Inst�ncia do modelo de dados de auditoria.
	 */
    @SuppressWarnings("unchecked")
    public <A extends AuditorModel> A getModel(LoggingEvent event){
        A model = null;
        
        try{
            Class<A> modelClass = (Class<A>)Class.forName(this.modelClass);
            
            model = (A)ConstructorUtils.invokeConstructor(modelClass, null);
        }
        catch(Throwable e){
            model = (A)new AuditorModel();
        }

        model.setCreateDate(new DateTime());
        model.setEntityId(getEntityId(auditor.getEntity()));
        model.setBusinessId(getBusinessId(auditor.getBusiness()));
        model.setBusinessComplement(buildBusinessComplement(model));
        
        LoginSessionModel loginSession = auditor.getLoginSession();
        
        if(loginSession != null){
            SystemSessionModel systemSession = loginSession.getSystemSession();
            
            model.setSystemSession(systemSession);

            UserModel user = loginSession.getUser();
            
            if(user != null)
                model.setUserName(user.getName());
            
            model.setSystemModule(loginSession.getSystemModule());
        }

        model.setStatusCode(AuditorStatusType.valueOf(StringUtil.trim(event.getMessage()).toUpperCase()));
        
        ThrowableInformation information = event.getThrowableInformation();
        
        if(information != null){
            Throwable e = information.getThrowable();
            
            if(e != null)
                model.setStatusMessage(ExceptionUtil.getExceptionId(e));
        }
  
        return (A)model;
    }
    
    /**
     * Monta a lista de complementos da auditoria.
     * 
     * @param model Inst�ncia do modelo de dados de auditoria.
     * @return Lista de complementos da auditoria.
     */
    private Collection<AuditorBusinessComplementModel> buildBusinessComplement(AuditorModel model){
        Object                                     businessArgumentsValues = auditor.getBusinessArgumentsValues();
        Collection<AuditorBusinessComplementModel> businessComplements     = null;

        if(businessArgumentsValues != null){
            String                                     businessArgumentsIds[] = auditor.getBusinessArgumentsIds();
            String                                     businessArgumentId     = "";
            Object                                     businessArgumentValue  = businessArgumentsValues;
            Collection<AuditorBusinessComplementModel> businessComplement     = null;

            if(businessArgumentsValues instanceof Object[]){
                Object businessArgumentsValuesArray[] = (Object[])businessArgumentsValues;
                
                if(businessArgumentsValuesArray.length > 0){
                    businessComplements = new LinkedList<AuditorBusinessComplementModel>();
                    
                    for(Integer cont = 0 ; cont < businessArgumentsValuesArray.length ; cont++){
                        businessArgumentValue = businessArgumentsValuesArray[cont];
                        
                        if(businessArgumentsIds != null && businessArgumentsIds.length >= (cont + 1))
                            businessArgumentId = businessArgumentsIds[cont];
                        else
                            businessArgumentId = "";
                        
                        businessComplement = buildBusinessComplement(model, businessArgumentId, businessArgumentValue);
                        
                        if(businessComplement != null)
                            businessComplements.addAll(businessComplement);
                    }
                }
            }
            else{
                businessComplements = new LinkedList<AuditorBusinessComplementModel>();
                
                if(businessArgumentsIds != null && businessArgumentsIds.length > 0)
                    businessArgumentId = businessArgumentsIds[0];
                
                businessComplement = buildBusinessComplement(model, businessArgumentId, businessArgumentValue);
                
                if(businessComplement != null)
                    businessComplements.addAll(businessComplement);
            }
        }
        
        return businessComplements;
    }
    
    /**
     * Monta a lista de complementos da auditoria.
     * 
     * @param model Inst�ncia do modelo de dados de auditoria.
     * @param businessArgumentValue Inst�ncia do complemento.
     * @return Lista de complementos da auditoria.
     */
    private Collection<AuditorBusinessComplementModel> buildBusinessComplement(AuditorModel model, Object businessArgumentValue){
        return buildBusinessComplement(model, "", businessArgumentValue);
    }

    /**
     * Retorna uma lista de complementos da auditoria.
     * 
     * @param model Inst�ncia do modelo de dados de auditoria.
     * @param businessArgumentId String contendo o identificador do complemento.
     * @param businessArgumentValue Inst�ncia do complemento.
     * @return Lista de complementos da auditoria.
     */
    private Collection<AuditorBusinessComplementModel> buildBusinessComplement(AuditorModel model, String businessArgumentId, Object businessArgumentValue){
        Collection<AuditorBusinessComplementModel> businessComplements = null;
        
        if(businessArgumentValue != null){
            Class<?>  modelClass = model.getClass();
            ModelInfo modelInfo  = ModelUtil.getModelInfo(modelClass);
            
            if(modelInfo != null){
                PropertyInfo propertyInfo = modelInfo.getPropertyInfo("businessComplement");
                
                if(propertyInfo != null){
                    AuditorBusinessComplementModel item = null;

                    modelInfo = ModelUtil.getModelInfo(businessArgumentValue.getClass());
                    
                    if(modelInfo != null){
                        Collection<PropertyInfo> auditablePropertiesInfo = modelInfo.getAuditablePropertiesInfo();
                        
                        if(auditablePropertiesInfo != null && auditablePropertiesInfo.size() > 0){
                            Object                                     value              = null;
                            Collection<AuditorBusinessComplementModel> businessComplement = null;
                            
                            for(PropertyInfo auditablePropertyInfo : auditablePropertiesInfo){
                                try{
                                    value = PropertyUtil.getProperty(businessArgumentValue, auditablePropertyInfo.getId());
                                    
                                    if(value != null){
                                        if(businessComplements == null)
                                            businessComplements = new LinkedList<AuditorBusinessComplementModel>();
    
                                        if(value instanceof BaseModel){
                                            businessComplement = buildBusinessComplement(model, value);
                                            
                                            if(businessComplement != null)
                                                businessComplements.addAll(businessComplement);
                                        }
                                        else{
                                            Class<?> collectionItemsClass = propertyInfo.getCollectionItemsClass();
                                            
                                            item = (AuditorBusinessComplementModel)ConstructorUtils.invokeConstructor((collectionItemsClass.equals(Object.class) ? AuditorBusinessComplementModel.class : propertyInfo.getCollectionItemsClass()), null);
                                        
                                            item.setAuditor(model);
                                            item.setModelClass(modelInfo.getClazz().getName());
                                            item.setPropertyId(auditablePropertyInfo.getId());
                                            item.setPropertyType(auditablePropertyInfo.getClazz().getName());
                                            item.setPropertyValue(value);
                                        
                                            businessComplements.add(item);
                                        }
                                    }
                                }
                                catch(Throwable e){
                                }
                            }
                        }
                    }
                    else{
                        if(businessComplements == null)
                            businessComplements = new LinkedList<AuditorBusinessComplementModel>();

                        try{
                            item = (AuditorBusinessComplementModel)ConstructorUtils.invokeConstructor((propertyInfo.getCollectionItemsClass().equals(Object.class) ? AuditorBusinessComplementModel.class : propertyInfo.getCollectionItemsClass()), null);
                        
                            item.setAuditor(model);
                            item.setPropertyId(businessArgumentId);
                            item.setPropertyType(businessArgumentValue.getClass().getName());
                            item.setPropertyValue(businessArgumentValue);
                    
                            businessComplements.add(item);
                        }
                        catch(Throwable e){
                        }
                    }
                }
            }
        }
        
        return businessComplements;
    }

	/**
	 * Define o tamanho da fila de mensagens
	 * 
	 * @param queueSize Valor inteiro contendo o tamanho da fila.
	 */
	public void setQueueSize(Integer queueSize){
		this.queueSize = queueSize;
	}

	/**
	 * Retorna o tamanho da fila de mensagens
	 * 
	 * @return Valor inteiro contendo o tamanho da fila.
	 */
	public Integer getQueueSize(){
		return queueSize;
	}

	/**
	 * @see org.apache.log4j.Appender#setLayout(org.apache.log4j.Layout)
	 */
	public void setLayout(Layout layout){
		super.setLayout(layout);

		activateOptions();
	}
	
	/**
	 * @see org.apache.log4j.AppenderSkeleton#append(org.apache.log4j.spi.LoggingEvent)
	 */
	public void append(LoggingEvent event){
		Layout layout = getLayout();
		
		if(layout instanceof AuditorPatternLayout){
			AuditorModel model = getModel(event);
			
			if(model != null){
    			AuditorPatternLayout auditorLayout = (AuditorPatternLayout)layout;
    		
    			auditorLayout.setModel(model);
			}
		}
		
 		super.append(event);
 
 		queueCount++;
 
 		if(queueCount.equals(queueSize)){
 			queueCount = 0;
 
 			flush();
 		}
	}

	/**
	 * Limpa o buffer de mensagens.
	 */
	protected void flush(){
		if(qw != null)
			qw.flush();
	}
}