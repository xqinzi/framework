package br.com.concepting.framework.service.util;

import java.lang.reflect.Method;

import javax.xml.namespace.QName;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import br.com.concepting.framework.audit.Auditor;
import br.com.concepting.framework.context.resource.ContextResource;
import br.com.concepting.framework.context.resource.ContextResourceLoader;
import br.com.concepting.framework.context.types.ContextFactoryType;
import br.com.concepting.framework.network.constants.NetworkConstants;
import br.com.concepting.framework.resource.FactoryResource;
import br.com.concepting.framework.service.annotations.Service;
import br.com.concepting.framework.service.interfaces.IService;
import br.com.concepting.framework.service.types.ServiceTransactionType;
import br.com.concepting.framework.service.types.ServiceType;
import br.com.concepting.framework.util.Interceptor;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.web.constants.SystemConstants;

/**
 * Classe que implementa a interceptação dos métodos de uma classe de serviço.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class ServiceInterceptor extends Interceptor{
	/**
	 * Construtor - Inicializa interceptador dos métodos de uma classe de serviço.
	 * 
	 * @param interceptableInstance Instância da classe de serviço.
	 * @param interceptableInterfaceClass Interface da classe de serviço. 
	 * @param method Instância contendo as propriedades do método a ser interceptado.
	 * @param methodArguments Array contendo os parâmetros do método a ser interceptado.
	 */
	public <I> ServiceInterceptor(I interceptableInstance, Class interceptableInterfaceClass, Method method, Object methodArguments[]){
		super(interceptableInstance, interceptableInterfaceClass, method, methodArguments);
	}

	/**
	 * @see br.com.concepting.framework.util.Interceptor#before()
	 */
	public void before() throws Throwable{
		if(getInterceptableInstance() instanceof IService){
    		IService service = getInterceptableInstance();
            Auditor  auditor = getAuditor();
    		
    		auditor.setLoginSession(ServiceUtil.getLoginSession(service));
    		
    		Method   method            = getMethod();
    		Service  serviceAnnotation = method.getAnnotation(Service.class);
    		
    		if(serviceAnnotation != null && serviceAnnotation.transactionType() != ServiceTransactionType.NONE){
    		    service.initialize();
    		    
    		    if(serviceAnnotation.transactionType() == ServiceTransactionType.READ_WRITE)
    		        service.begin();
    		}
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
                
                auditor.setLoginSession(ServiceUtil.getLoginSession(service));
    
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
                
                auditor.setLoginSession(ServiceUtil.getLoginSession(service));
        		
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
        Class   interceptableInterfaceClass = getInterceptableInterfaceClass();
        Service serviceAnnotation           = (Service)interceptableInterfaceClass.getAnnotation(Service.class);
        
        if(serviceAnnotation == null || serviceAnnotation.type() != ServiceType.WEB_SERVICE)
            return executeLocalRemoteService();
        
        return executeWebService();
    }
    
    /**
     * Executa o método solicitado do serviço local ou remoto.
     * 
     * @return Instância do resultado da execução do método.
     * @throws Throwable
     */
    private <O> O executeLocalRemoteService() throws Throwable{
        return super.execute();
    }
    
    /**
     * Executa o método solicitado do WEB Service.
     * 
     * @return Instância do resultado da execução do método.
     * @throws Throwable
     */
    private <O> O executeWebService() throws Throwable{
        Class                 interceptableInterfaceClass = getInterceptableInterfaceClass();
        Service               serviceAnnotation           = (Service)interceptableInterfaceClass.getAnnotation(Service.class);
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
        
        endPointUrl.append(SystemConstants.DEFAULT_WEB_SERVICES_REPOSITORY_ID);
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
        
        Method  serviceMethod            = getMethod();
        Class   serviceReturnClass       = serviceMethod.getReturnType();
        Object  serviceMethodArguments[] = getMethodArguments();
        QName   serviceAction            = new QName(serviceNamespace.toString(), serviceMethod.getName());
        Class[] serviceReturnTypes       = new Class[]{serviceReturnClass};

        return (O)serviceClient.invokeBlocking(serviceAction, serviceMethodArguments, serviceReturnTypes);
   }
}