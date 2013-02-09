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
 * Classe que define a estrutura básica para um mecanismo de exibição/armazenamento 
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
	 * Construtor - Inicializa objetos e/ou variáveis internas.
	 * 
	 * @param auditor Instância da classe responsável por efetuar a auditoria.
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
	 * Define a instância da classe responsável pela auditoria.
	 * 
     * @param auditor Instância da classe responsável por efetuar a auditoria.
	 */
	public void setAuditor(Auditor auditor){
	    this.auditor = auditor;
	}
	
	/**
	 * Retorna a instância da classe responsável pela auditoria.
	 * 
	 * @return Instância da classe responsável por efetuar a auditoria.
	 */
	public Auditor getAuditor(){
	    return auditor;
	}
	
	/**
	 * Retorna a instância do modelo de dados de auditoria.
	 * 
	 * @param event Instância contendo as propriedades do evento a ser auditado.
	 * @return Instância do modelo de dados de auditoria.
	 */
    public <A extends AuditorModel> A getModel(LoggingEvent event){
        AuditorModel model = null;
        
        try{
            Class modelClass = Class.forName(this.modelClass);
            
            model = (A)ConstructorUtils.invokeConstructor(modelClass, null);
        }
        catch(Throwable e){
            model = new AuditorModel();
        }

        model.setCreateDate(new DateTime());

        Class     entity     = auditor.getEntity();
        Auditable annotation = (Auditable)entity.getAnnotation(Auditable.class);
            
        if(annotation == null){
            Class superClasses[] = entity.getInterfaces();

            if(superClasses != null && superClasses.length > 0){
                for(Class superClass : superClasses){
                    annotation = (Auditable)superClass.getAnnotation(Auditable.class);
                    
                    if(annotation != null)
                        break;
                }
            }
        }
            
        String entityId = "";
        
        if(annotation != null)
            entityId = annotation.entityId();
        
        if(entityId.length() == 0)
            entityId = entity.getName();
            
        model.setEntityId(entityId);
        
        Method business = auditor.getBusiness();
    
        annotation = (business != null ? business.getAnnotation(Auditable.class) : null);
            
        String businessId = "";
        
        if(annotation != null)
            businessId = annotation.businessId();
            
        if(businessId.length() == 0)
            businessId = business.getName();
            
        model.setBusinessId(businessId);
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

        model.setStatusCode(StringUtil.trim(event.getMessage()));
        
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
     * @param model Instância do modelo de dados de auditoria.
     * @return Lista de complementos da auditoria.
     */
    private <A extends AuditorModel, O, C extends AuditorBusinessComplementModel> Collection<C> buildBusinessComplement(A model){
        O             businessArgumentsValues = auditor.getBusinessArgumentsValues();
        Collection<C> businessComplements     = null;

        if(businessArgumentsValues != null){
            String     businessArgumentsIds[] = auditor.getBusinessArgumentsIds();
            String     businessArgumentId     = "";
            Object     businessArgumentValue  = businessArgumentsValues;
            Collection businessComplement     = null;

            if(businessArgumentsValues instanceof Object[]){
                if(((Object[])businessArgumentsValues).length > 0){
                    businessComplements = new LinkedList<C>();
                    
                    for(Integer cont = 0 ; cont < ((Object[])businessArgumentsValues).length ; cont++){
                        businessArgumentValue = ((Object[])businessArgumentsValues)[cont];
                        
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
                businessComplements = new LinkedList();
                
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
     * @param model Instância do modelo de dados de auditoria.
     * @param businessArgumentValue Instância do complemento.
     * @return Lista de complementos da auditoria.
     */
    private <A extends AuditorModel, O, C extends AuditorBusinessComplementModel> Collection<C> buildBusinessComplement(A model, O businessArgumentValue){
        return buildBusinessComplement(model, "", businessArgumentValue);
    }

    /**
     * Retorna uma lista de complementos da auditoria.
     * 
     * @param model Instância do modelo de dados de auditoria.
     * @param businessArgumentId String contendo o identificador do complemento.
     * @param businessArgumentValue Instância do complemento.
     * @return Lista de complementos da auditoria.
     */
    private <A extends AuditorModel, O, C extends AuditorBusinessComplementModel> Collection<C> buildBusinessComplement(A model, String businessArgumentId, O businessArgumentValue){
        Collection<C> businessComplements = null;
        
        if(businessArgumentValue != null){
            Class     modelClass = model.getClass();
            ModelInfo modelInfo  = ModelUtil.getModelInfo(modelClass);
            
            if(modelInfo != null){
                PropertyInfo propertyInfo = modelInfo.getPropertyInfo("businessComplement");
                
                if(propertyInfo != null){
                    C item = null;

                    modelInfo = ModelUtil.getModelInfo(businessArgumentValue.getClass());
                    
                    if(modelInfo != null){
                        Collection<PropertyInfo> auditablePropertiesInfo = modelInfo.getAuditablePropertiesInfo();
                        
                        if(auditablePropertiesInfo != null && auditablePropertiesInfo.size() > 0){
                            Object        value              = null;
                            Collection<C> businessComplement = null;
                            
                            for(PropertyInfo auditablePropertyInfo : auditablePropertiesInfo){
                                try{
                                    value = PropertyUtil.getProperty(businessArgumentValue, auditablePropertyInfo.getId());
                                    
                                    if(value != null){
                                        if(businessComplements == null)
                                            businessComplements = new LinkedList<C>();
    
                                        if(value instanceof BaseModel){
                                            businessComplement = buildBusinessComplement(model, value);
                                            
                                            if(businessComplement != null)
                                                businessComplements.addAll(businessComplement);
                                        }
                                        else{
                                            item = (C)ConstructorUtils.invokeConstructor((propertyInfo.getCollectionItemsClass().equals(Object.class) ? AuditorBusinessComplementModel.class : propertyInfo.getCollectionItemsClass()), null);
                                        
                                            item.setModel(model);
                                            item.setClazz(modelInfo.getClazz().getName());
                                            item.setId(auditablePropertyInfo.getId());
                                            item.setType(auditablePropertyInfo.getClazz().getName());
                                            item.setValue(value);
                                        
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
                            businessComplements = new LinkedList<C>();

                        try{
                            item = (C)ConstructorUtils.invokeConstructor((propertyInfo.getCollectionItemsClass().equals(Object.class) ? AuditorBusinessComplementModel.class : propertyInfo.getCollectionItemsClass()), null);
                        
                            item.setModel(model);
                            item.setId(businessArgumentId);
                            item.setType(businessArgumentValue.getClass().getName());
                            item.setValue(businessArgumentValue);
                    
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