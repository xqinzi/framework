package br.com.concepting.framework.service.util;

import java.lang.reflect.Method;

import javax.xml.namespace.QName;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import br.com.concepting.framework.audit.Auditor;
import br.com.concepting.framework.constants.ProjectConstants;
import br.com.concepting.framework.context.resource.ContextResource;
import br.com.concepting.framework.context.resource.ContextResourceLoader;
import br.com.concepting.framework.context.types.ContextFactoryType;
import br.com.concepting.framework.network.constants.NetworkConstants;
import br.com.concepting.framework.resource.FactoryResource;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.service.annotations.Service;
import br.com.concepting.framework.service.annotations.ServiceTransaction;
import br.com.concepting.framework.service.interfaces.IService;
import br.com.concepting.framework.service.types.ServiceType;
import br.com.concepting.framework.util.ExceptionUtil;
import br.com.concepting.framework.util.Interceptor;
import br.com.concepting.framework.util.StringUtil;

/**
 * Classe que implementa a intercepta��o dos m�todos de uma classe de servi�o.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class ServiceInterceptor extends Interceptor{
	/**
	 * Construtor - Inicializa interceptador dos m�todos de uma classe de servi�o.
	 * 
	 * @param interceptableInstance Inst�ncia da classe de servi�o.
	 * @param interceptableInterfaceClass Interface da classe de servi�o. 
	 * @param method Inst�ncia contendo as propriedades do m�todo a ser interceptado.
	 * @param methodArguments Array contendo os par�metros do m�todo a ser interceptado.
	 * @param auditable
	 */
	public ServiceInterceptor(Object interceptableInstance, Class<? extends ServiceInterceptor> interceptableInterfaceClass, Method method, Object methodArguments[], Boolean auditable){
		super(interceptableInstance, interceptableInterfaceClass, method, methodArguments, auditable);
	}

	/**
	 * @see br.com.concepting.framework.util.Interceptor#before()
	 */
	public void before() throws Throwable{
		if(getInterceptableInstance() instanceof IService){
    		IService service = getInterceptableInstance();
            Auditor  auditor = getAuditor();
    		
            if(auditor != null){
                LoginSessionModel loginSession = service.getLoginSession();
                
                auditor.setLoginSession(loginSession);
            }
    		
            ServiceTransaction serviceTransaction = getMethod().getAnnotation(ServiceTransaction.class);
            
            if(serviceTransaction != null)
                service.setTimeout(serviceTransaction.timeout());
            
            service.begin();
		}
		
		super.before();
	}

	/**
	 * @see br.com.concepting.framework.util.Interceptor#after()
	 */
	public void after() throws Throwable{
	    try{
            if(getInterceptableInstance() instanceof IService){
                IService service = getInterceptableInstance();
                Auditor  auditor = getAuditor();
                
                if(auditor != null){
                    LoginSessionModel loginSession = service.getLoginSession();
                    
                    auditor.setLoginSession(loginSession);
                }
    
                service.commit();
            }
	    }
	    finally{
	        super.after();
	    }
	}

	/**
	 * @see br.com.concepting.framework.util.Interceptor#beforeThrow(java.lang.Throwable)
	 */
	public void beforeThrow(Throwable e) throws Throwable{
	    try{
            if(getInterceptableInstance() instanceof IService){
                IService service = getInterceptableInstance();
                Auditor  auditor = getAuditor();
                
                if(auditor != null){
                    LoginSessionModel loginSession = service.getLoginSession();
                    
                    auditor.setLoginSession(loginSession);
                }
        		
                ServiceTransaction serviceTransaction = getMethod().getAnnotation(ServiceTransaction.class);
                
                if(serviceTransaction != null){
                    Class<?>                   superClass    = ExceptionUtil.getOriginException(e).getClass();
                    Class<? extends Throwable> rollbackFor[] = serviceTransaction.rollbackFor();
                    Boolean                    found         = false;
                    
                    while(superClass != null && !superClass.equals(Object.class)){
                        found = false;
                        
                        for(Class<? extends Throwable> item : rollbackFor){
                            if(item.equals(superClass)){
                                service.rollback();
                                
                                found = true;
                                
                                break;
                            }
                        }
                        
                        if(found)
                            break;
                        
                        superClass = superClass.getSuperclass();
                    }
                    
                    if(!found)
                        service.commit();
                }
                else
                    service.rollback();
            }
	    }
	    finally{
	        super.beforeThrow(e);
	    }
	}

	/**
	 * @see br.com.concepting.framework.util.Interceptor#execute()
	 */
    public <O> O execute() throws Throwable{
        Class<?> interceptableInterfaceClass = getInterceptableInterfaceClass();
        Service  serviceAnnotation           = interceptableInterfaceClass.getAnnotation(Service.class);
        
        if(serviceAnnotation == null || serviceAnnotation.type() != ServiceType.WEB_SERVICE)
            return executeLocalRemoteService();
        
        return executeWebService();
    }
    
    /**
     * Executa o m�todo solicitado do servi�o local ou remoto.
     * 
     * @return Inst�ncia do resultado da execu��o do m�todo.
     * @throws Throwable
     */
    private <O> O executeLocalRemoteService() throws Throwable{
        return super.execute();
    }
    
    /**
     * Executa o m�todo solicitado do WEB Service.
     * 
     * @return Inst�ncia do resultado da execu��o do m�todo.
     * @throws Throwable
     */
    @SuppressWarnings("unchecked")
    private <O> O executeWebService() throws Throwable{
        Class<?>              interceptableInterfaceClass = getInterceptableInterfaceClass();
        Service               serviceAnnotation           = interceptableInterfaceClass.getAnnotation(Service.class);
        ContextResourceLoader contextResourceLoader       = new ContextResourceLoader();
        ContextResource       contextResource             = contextResourceLoader.get(serviceAnnotation.contextResourceId());     
        RPCServiceClient      serviceClient               = new RPCServiceClient();
        Options               serviceOptions              = serviceClient.getOptions();
        StringBuilder         endPointUrl                 = new StringBuilder();
        
        if(contextResource.useSsl())
            endPointUrl.append(NetworkConstants.HTTPS_PROTOCOL_ID);
        else
            endPointUrl.append(NetworkConstants.HTTP_PROTOCOL_ID);
        
        endPointUrl.append("://");
        
        if(contextResource.getServerName().length() == 0)
            endPointUrl.append(NetworkConstants.LOCALHOST_ID);
        else
            endPointUrl.append(contextResource.getServerName());
        
        endPointUrl.append(":");
        
        if(contextResource.getServerPort() == 0){
            FactoryResource    factoryResource = contextResource.getFactoryResource();
            ContextFactoryType factoryType     = null;
            
            try{
                factoryType = ContextFactoryType.valueOf(factoryResource.getType().toUpperCase());
            }
            catch(Throwable e){
                factoryType = ContextFactoryType.TOMCAT;
            }
            
            endPointUrl.append(factoryType.getDefaultServerPort());
        }
        else
            endPointUrl.append(contextResource.getServerPort());
        
        endPointUrl.append(ProjectConstants.DEFAULT_WEB_SERVICES_REPOSITORY_ID);
        endPointUrl.append(serviceAnnotation.name());

        serviceOptions.setTo(new EndpointReference(endPointUrl.toString()));
        
        String        servicePackage    = StringUtil.replaceAll(interceptableInterfaceClass.getPackage().getName(), ".interfaces", "");
        String        servicePackages[] = StringUtil.split(servicePackage, ".");
        StringBuilder serviceNamespace  = new StringBuilder();
        
        if(contextResource.useSsl())
            serviceNamespace.append(NetworkConstants.HTTPS_PROTOCOL_ID);
        else
            serviceNamespace.append(NetworkConstants.HTTP_PROTOCOL_ID);
        
        serviceNamespace.append("://");
        
        for(Integer cont = (servicePackages.length - 1) ; cont >= 0 ; cont--){
            serviceNamespace.append(servicePackages[cont]);
            
            if(cont > 0)
                serviceNamespace.append(".");
        }
        
        Method     serviceMethod            = getMethod();
        Class<?>   serviceReturnClass       = serviceMethod.getReturnType();
        Object     serviceMethodArguments[] = getMethodArguments();
        QName      serviceAction            = new QName(serviceNamespace.toString(), serviceMethod.getName());
        Class<?>[] serviceReturnTypes       = new Class<?>[]{serviceReturnClass};

        return (O)serviceClient.invokeBlocking(serviceAction, serviceMethodArguments, serviceReturnTypes);
   }
}