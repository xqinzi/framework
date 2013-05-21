package br.com.concepting.framework.audit.appenders;

import java.util.Collection;

import org.apache.log4j.spi.LoggingEvent;

import br.com.concepting.framework.audit.Auditor;
import br.com.concepting.framework.audit.model.AuditorBusinessComplementModel;
import br.com.concepting.framework.audit.model.AuditorModel;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.model.util.ModelUtil;
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
	 * @see org.apache.log4j.AppenderSkeleton#append(org.apache.log4j.spi.LoggingEvent)
	 */
    public void append(LoggingEvent event){
		AuditorModel model = getModel(event);
		
		if(model != null){
            ModelInfo modelInfo = ModelUtil.getModelInfo(model.getClass());
            
            if(modelInfo != null){
                try{
                    Auditor  auditor = getAuditor();
                    IService service = ServiceUtil.getService(model.getClass());         
        
                    service.setLoginSession(auditor.getLoginSession());
                    service.save(model);
                    
                    PropertyInfo propertyInfo = modelInfo.getPropertyInfo("businessComplement");
                    
                    if(!propertyInfo.cascadeOnSave()){
                        Collection<AuditorBusinessComplementModel> auditorInfoComplement = model.getBusinessComplement();
                        
                        if(auditorInfoComplement != null && auditorInfoComplement.size() > 0){
                            try{
                                service = ServiceUtil.getService(propertyInfo.getCollectionItemsClass());
                                
                                service.setLoginSession(auditor.getLoginSession());
                                service.saveAll(auditorInfoComplement);
                            }
                            catch(Throwable e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
                catch(Throwable e){
                    e.printStackTrace();
                }
	        }
		}
	}
}