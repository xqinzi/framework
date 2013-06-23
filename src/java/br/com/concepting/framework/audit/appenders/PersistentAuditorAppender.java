package br.com.concepting.framework.audit.appenders;

import java.rmi.RemoteException;
import java.util.Collection;

import org.apache.log4j.spi.LoggingEvent;

import br.com.concepting.framework.audit.Auditor;
import br.com.concepting.framework.audit.model.AuditorBusinessComplementModel;
import br.com.concepting.framework.audit.model.AuditorModel;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.service.interfaces.IService;
import br.com.concepting.framework.service.util.ServiceUtil;

/**
 * Classe que define o mecanismo de armazenamento das mensagens de auditoria em um
 * repositório de persistência.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class PersistentAuditorAppender extends BaseAuditorAppender{
    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param auditor Instância da classe responsável por efetuar a auditoria.
     */
	public PersistentAuditorAppender(Auditor auditor){
        super(auditor);
    }

    /**
	 * @see org.apache.log4j.Appender#requiresLayout()
	 */
	public boolean requiresLayout(){
		return false;
	}
	
    /**
     * Retorna a instância da classe de serviço vinculada a um modelo de dados.
     * Esta instância irá utilizar a transação já iniciada, caso houve, senão irá criar uma nova 
     * transação caso a classe de serviço gerencie transações.
     * 
     * @param modelClass Classe do modelo de dados desejado.
     * @param auditable
     * @return Instância da classe de serviço.
     * @throws InternalErrorException
     */
    protected <S extends IService, M extends BaseModel> S getService(Class<M> modelClass, Boolean auditable) throws InternalErrorException{
        S                 service      = ServiceUtil.getService(modelClass, auditable);
        Auditor           auditor      = getAuditor();
        LoginSessionModel loginSession = auditor.getLoginSession();
        
        try{
            service.setLoginSession(loginSession);
        }
        catch(RemoteException e){
        }
        
        return service;
    }
    
    /**
     * Retorna a instância da classe de serviço vinculada a um modelo de dados.
     * Esta instância irá utilizar a transação já iniciada, caso houve, senão irá criar uma nova 
     * transação caso a classe de serviço gerencie transações.
     * 
     * @param modelClass Classe do modelo de dados desejado.
     * @return Instância da classe de serviço.
     * @throws InternalErrorException
     */
    protected <S extends IService, M extends BaseModel> S getService(Class<M> modelClass) throws InternalErrorException{
        return getService(modelClass, true);
    }
    
	/**
	 * @see org.apache.log4j.AppenderSkeleton#append(org.apache.log4j.spi.LoggingEvent)
	 */
    public void append(LoggingEvent event){
		AuditorModel model = getModel(event);
		
		if(model != null){
            ModelInfo modelInfo = ModelUtil.getModelInfo(model.getClass());
            
            if(modelInfo != null){
                try{
                    IService service = getService(model.getClass(), false);         
        
                    service.save(model);
                    
                    PropertyInfo propertyInfo = modelInfo.getPropertyInfo("businessComplement");
                    
                    if(!propertyInfo.cascadeOnSave()){
                        Collection<AuditorBusinessComplementModel> auditorInfoComplement = model.getBusinessComplement();
                        
                        if(auditorInfoComplement != null && auditorInfoComplement.size() > 0){
                            service = getService(propertyInfo.getCollectionItemsClass(), false);
                            
                            service.saveAll(auditorInfoComplement);
                        }
                    }
                }
                catch(Throwable e){
                }
	        }
		}
	}
}