package br.com.concepting.framework.service.util;

import java.util.Properties;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import org.apache.commons.beanutils.ConstructorUtils;

import br.com.concepting.framework.caching.CachedObject;
import br.com.concepting.framework.caching.Cacher;
import br.com.concepting.framework.caching.CacherManager;
import br.com.concepting.framework.context.resource.ContextResource;
import br.com.concepting.framework.context.resource.ContextResourceLoader;
import br.com.concepting.framework.context.types.ContextFactoryType;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.exceptions.ItemNotFoundException;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.resource.FactoryResource;
import br.com.concepting.framework.service.annotations.Service;
import br.com.concepting.framework.service.interfaces.IService;
import br.com.concepting.framework.service.types.ServiceType;
import br.com.concepting.framework.util.MethodUtil;
import br.com.concepting.framework.util.Observer;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.exceptions.InternalErrorException;

/**
 * Classe utilitária responsável por localizar um serviço JNDI. 
 *
 * @author fvilarinho
 * @since 1.0
 */
public class ServiceLocator{
	private Cacher contextInstances = CacherManager.getInstance().getCacher(getClass());
    private Class  modelClass       = null;
    
    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param modelClass Classe do modelo de dados desejado.
     */
	public <M extends BaseModel> ServiceLocator(Class<M> modelClass){
		super();
		
		this.modelClass = modelClass;
	}

	/**
	 * Localiza o serviço baseado no modelo de dados.
	 * 
	 * @return Instância do serviço vinculado ao modelo de dados.
	 * @throws InternalErrorException
	 */
    public <S extends IService> S lookup() throws InternalErrorException{
	    try{
    	    S           serviceInstance         = null;
            Class<S>    serviceInterfaceClass   = ServiceUtil.getServiceInterfaceClassByModel(modelClass);
            Class<S>    serviceClass            = ServiceUtil.getServiceClassByModel(modelClass);
            Service     serviceAnnotation       = serviceInterfaceClass.getAnnotation(Service.class);
            Stateless   statelessAnnotation     = serviceClass.getAnnotation(Stateless.class);
            Stateful    statefulAnnotation      = serviceClass.getAnnotation(Stateful.class);
            Class       serviceInterceptorClass = (serviceAnnotation != null ? serviceAnnotation.interceptor() : ServiceInterceptor.class);
            ServiceType serviceType             = null;
            
            if(statelessAnnotation != null)
                serviceType = ServiceType.STATELESS;
            else if(statefulAnnotation != null)
                serviceType = ServiceType.STATEFUL;
            else
                serviceType = (serviceAnnotation != null ? serviceAnnotation.type() : ServiceType.CLASS);
            
            Object lookupObject = null; 
            
            if(serviceType == ServiceType.CLASS){
                serviceClass = ServiceUtil.getServiceClassByModel(modelClass);
            
                lookupObject = ConstructorUtils.invokeConstructor(serviceClass, null);
            }
            else if(serviceType == ServiceType.WEB_SERVICE)
                lookupObject = serviceInterfaceClass;
            else{
                String                contextResourceId      = (serviceAnnotation != null ? serviceAnnotation.contextResourceId() : "");
                ContextResourceLoader contextResourceLoader  = new ContextResourceLoader();
                ContextResource       contextResource        = contextResourceLoader.get(contextResourceId); 
                FactoryResource       contextFactoryResource = contextResource.getFactoryResource();
                Context               context                = null; 
                CachedObject          cachedObject           = null;
                
                try{
                    cachedObject = contextInstances.get(contextResourceId);
                    context      = cachedObject.getContent();
                }
                catch(ItemNotFoundException e){
                    Properties contextProperties   = new Properties();
                    String     contextFactoryClass = (contextFactoryResource == null ? "" : StringUtil.trim(contextFactoryResource.getClazz()));
    
                    if(contextFactoryClass.length() > 0){
                        contextProperties.put(Context.INITIAL_CONTEXT_FACTORY, contextFactoryClass);
    
                        String contextFactoryUrl = StringUtil.trim(contextFactoryResource.getUrl());
    
                        if(contextFactoryUrl.length() > 0){
                            contextFactoryUrl = PropertyUtil.fillPropertiesInString(contextFactoryResource, contextFactoryUrl);
    
                            contextProperties.put(Context.PROVIDER_URL, contextFactoryUrl);
                        }
    
                        context = new InitialContext(contextProperties);
                    }
                    else
                        context = new InitialContext();
    
                    cachedObject = new CachedObject();
                    cachedObject.setId(contextResource.getId());
                    cachedObject.setContent(context);
    
                    try{
                        contextInstances.add(cachedObject);
                    }
                    catch(Throwable e1){
                    }
                }
                
                ContextFactoryType contextType = null;
                
                if(contextFactoryResource != null)
                    contextType = ContextFactoryType.valueOf(contextFactoryResource.getType().toUpperCase());
                else
                    contextType = ContextFactoryType.TOMCAT;
                
                String lookupName = "";
                
                if(statelessAnnotation != null || statefulAnnotation != null){
                    lookupName      = (statelessAnnotation != null ? statelessAnnotation.mappedName() : statefulAnnotation.mappedName());
                    serviceInstance = (S)PortableRemoteObject.narrow(context.lookup(lookupName), serviceInterfaceClass);
                }
                else{
                    Class serviceHomeInterfaceClass = ServiceUtil.getServiceHomeInterfaceClassByModel(modelClass);

                    if(contextType == ContextFactoryType.GLASSFISH)
                        lookupName = serviceHomeInterfaceClass.getName();
                    else if(contextType == ContextFactoryType.JBOSS)
                        lookupName = serviceAnnotation.name();
                    else if(contextType == ContextFactoryType.WEBSPHERE)
                        lookupName = serviceAnnotation.name();

                    lookupObject = PortableRemoteObject.narrow(context.lookup(lookupName), serviceHomeInterfaceClass);
                    lookupObject = MethodUtil.invokeMethod(lookupObject, "create", null);
                }
            }

            if(serviceInterceptorClass != null)
                serviceInstance = (S)Observer.getInstance(lookupObject, serviceInterfaceClass, serviceInterceptorClass);
            
            return serviceInstance;
	    }
	    catch(Throwable e){
	        throw new InternalErrorException(e);
	    }
	}
}