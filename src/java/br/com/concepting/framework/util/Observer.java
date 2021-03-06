package br.com.concepting.framework.util;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.ConstructorUtils;

import br.com.concepting.framework.exceptions.InternalErrorException;

/**
 * Classe utilitária responsável por interceptar a execução de um método.
 *
 * @author fvilarinho
 * @since 1.0
 */ 
public class Observer implements InvocationHandler{
	private Object      interceptableInstance       = null;
	private Class       interceptableInterfaceClass = null;
	private Class       interceptorClass            = null;
	private Interceptor interceptor                 = null;
	private Boolean     auditable                   = true;

	/**
	 * Retorna a instância única do observador para interceptação.
	 * 
	 * @param interceptableInstance Instância da classe interceptável.
	 * @param interceptableInterfaceClass Interfaces da classe interceptável.
	 * @param auditable Indica se a interceptação será auditada.
	 * @return Inst&ancia do observador.
	 */
	public static <I> I getInstance(I interceptableInstance, Class interceptableInterfaceClass, Boolean auditable){
		return getInstance(interceptableInstance, interceptableInterfaceClass, null, auditable);
	}
	
    /**
     * Retorna a instância única do observador para interceptação.
     * 
     * @param interceptableInstance Instância da classe interceptável.
     * @param interceptableInterfaceClass Interfaces da classe interceptável.
     * @param interceptorClass Classe do interceptador.
     * @param auditable Indica se a interceptação será auditada.
     * @return Inst&ancia do observador.
     */
    public static <I> I getInstance(I interceptableInstance, Class interceptableInterfaceClass, Class interceptorClass, Boolean auditable){
		if(interceptableInstance == null || interceptableInterfaceClass == null)
			return interceptableInstance;
		
		if(interceptorClass == null)
			interceptorClass = Interceptor.class;

		List<Class> interceptableInstanceInterfacesList = getInterceptableInstanceInterfaces(interceptableInstance);
		Class       interceptableInstanceInterfaces[]   = new Class[interceptableInstanceInterfacesList.size()];

		interceptableInstanceInterfaces = interceptableInstanceInterfacesList.toArray(interceptableInstanceInterfaces);
		
		Class interceptableClass = null;
		
		if(interceptableInstance instanceof Class)
		    interceptableClass = (Class)interceptableInstance;
		else
		    interceptableClass = interceptableInstance.getClass();

        return (I)Proxy.newProxyInstance(interceptableClass.getClassLoader(), interceptableInstanceInterfaces, new Observer(interceptableInstance, interceptableInterfaceClass, interceptorClass, auditable));
	}
	
    /**
     * Retorna uma lista de interfaces implementadas pela instância do objeto interceptável.
     * 
     * @param interceptableInstance Instância do objeto interceptável.
     * @return Lista de interfaces do objeto interceptável.
     */
	private static List<Class> getInterceptableInstanceInterfaces(Object interceptableInstance){
	    List<Class> interceptableInterfaces = new LinkedList<Class>();
	    
        if(!(interceptableInstance instanceof Class))
            interceptableInstance = interceptableInstance.getClass();

        loadInterceptableInstanceInterfaces((Class)interceptableInstance, interceptableInterfaces);
	    
	    return interceptableInterfaces;
	}
	
    /**
     * Carrega a lista de interfaces implementadas pela instância do objeto interceptável.
     * 
     * @param interceptableInstance Instância do objeto interceptável.
     * @param interceptableInterfaces Lista de interfaces do objeto interceptável.
     */
	private static void loadInterceptableInstanceInterfaces(Class interceptableInstance, List<Class> interceptableInterfaces){
	    Class   instanceInterfaces[] = interceptableInstance.getInterfaces();
	    Boolean found                = false;
	    
        for(Class item : interceptableInterfaces){
            if(item.isInterface() && item.getName().equals(interceptableInstance.getName())){
                found = true;
                
                break;
            }
        }
        
        if(!found && interceptableInstance.isInterface())
            interceptableInterfaces.add(interceptableInstance);

        if(instanceInterfaces != null && instanceInterfaces.length > 0){
	        for(Class instanceInterface : instanceInterfaces){
	            if(!instanceInterface.getName().endsWith(Serializable.class.getSimpleName()) && !instanceInterface.getName().endsWith(Object.class.getSimpleName())){
	                found = false;
	                
	                for(Class item : interceptableInterfaces){
	                    if(item.isInterface() && item.getName().equals(instanceInterface.getName())){
	                        found = true;
	                        
	                        break;
	                    }
	                }
	                
	                if(!found && instanceInterface.isInterface()) {
	                    interceptableInterfaces.add(instanceInterface);
    	            
	                    loadInterceptableInstanceInterfaces(instanceInterface, interceptableInterfaces);
	                }
	            }
	        }
	    }
	}

	/**
	 * Construtor - Inicializa objetos e/ou variáveis internas.
	 *
	 * @param interceptableInstance Instância da classe a ser interceptada.
	 * @param interceptableInterfaceClass Classe a ser interceptada. 
	 * @param interceptorClass Classe do interceptador.
	 * @param auditable 
	 */
	private <I> Observer(I interceptableInstance, Class interceptableClass, Class interceptorClass, Boolean auditable){
		this.interceptableInstance       = interceptableInstance;
		this.interceptableInterfaceClass = interceptableClass;
		this.interceptorClass            = interceptorClass;
		this.auditable                   = auditable;
	}

	/**
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	public Object invoke(Object proxy, Method method, Object[] methodArguments) throws Throwable{
	    if(interceptor == null){
    		try{
    			interceptor = (Interceptor)ConstructorUtils.invokeConstructor(interceptorClass, new Object[]{interceptableInstance, interceptableInterfaceClass, method, (methodArguments == null ? new Object[0] : methodArguments), auditable});
    		}
    		catch(Throwable e){
    			interceptor = new Interceptor(interceptableInstance, interceptableInterfaceClass, method, methodArguments, auditable);
    		}
	    }
	    else{
	        interceptor.setInterceptableInstance(interceptableInstance);
	        interceptor.setInterceptableInterfaceClass(interceptableInterfaceClass);
	        interceptor.setMethod(method);
	        interceptor.setMethodArguments(methodArguments);
	        interceptor.setAuditable(auditable);
	    }

		interceptor.before();

		Throwable exception = null;
		
		try{
		    return interceptor.execute();
		}
		catch(Throwable e){
		    if(!ExceptionUtil.isExpectedException(e) && !ExceptionUtil.isInternalErrorException(e))
    		    exception = e;
		    else
		        exception = new InternalErrorException(e);
                
            interceptor.beforeThrow(e);

            throw e;
		}
		finally{
			if(exception == null)
				interceptor.after();
		}
	}
}