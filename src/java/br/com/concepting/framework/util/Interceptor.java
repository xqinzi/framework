package br.com.concepting.framework.util;

import java.lang.reflect.Method;

import br.com.concepting.framework.audit.Auditor;
import br.com.concepting.framework.audit.annotations.Auditable;

/**
 * Classe utilitária que define a estrutura básica para uma interceptador de execução de 
 * métodos.
 *
 * @author fvilarinho
 * @since 1.0
 */
public class Interceptor{
	private Object  interceptableInstance       = null;
	private Class   interceptableInterfaceClass = null;
	private Method  method                      = null;
	private Object  methodArguments[]           = null;
	private Auditor auditor                     = null;

	/**
	 * Construtor - Define as propriedades da classe e do método interceptável. 
	 *
	 * @param interceptableInstance Instância contendo as propriedade da classe interceptável.
	 * @param interceptableInterfaceClass Interface da classe interceptada.
	 * @param method Instância contendo as propriedades do método interceptável.
	 * @param methodArguments Array contendo os argumentos do método interceptável.
	 */
	public Interceptor(Object interceptableInstance, Class interceptableInterfaceClass, Method method, Object methodArguments[]){
		super();

		setInterceptableInstance(interceptableInstance);
		setInterceptableInterfaceClass(interceptableInterfaceClass);
		setMethod(method);
		setMethodArguments(methodArguments);
	}

    public <I> I getInterceptableInstance(){
        return (I)interceptableInstance;
    }
    
    public <I> void setInterceptableInstance(I interceptableInstance){
        this.interceptableInstance = interceptableInstance;
    }

	public Class getInterceptableInterfaceClass(){
     	return interceptableInterfaceClass;
    }
	
	public void setInterceptableInterfaceClass(Class interceptableInterfaceClass){
	    this.interceptableInterfaceClass = interceptableInterfaceClass;
	}

	public Method getMethod(){
		return method;
	}
	
	public void setMethod(Method method){
	    this.method = method;
	}

    public <O> O[] getMethodArguments(){
		return (O[])methodArguments;
	}
	
	public <O> void setMethodArguments(O methodArguments[]){
	    this.methodArguments = methodArguments;
	}

	/**
	 * Retorna a instância contendo as propriedades da auditoria.
	 *
	 * @return Instância contendo as propriedades da auditoria.
	 */
    protected Auditor getAuditor(){
 		Method    method          = getMethod();
 		Object[]  methodArguments = getMethodArguments();
 		Auditable annotation      = (Auditable)interceptableInterfaceClass.getAnnotation(Auditable.class);
 		
 		if(annotation == null){
 	        Class superClasses[] = interceptableInterfaceClass.getInterfaces();

 	        if(superClasses != null && superClasses.length > 0){
     		    for(Class superClass : superClasses){
     	            annotation = (Auditable)superClass.getAnnotation(Auditable.class);
     	            
     	            if(annotation != null)
     	                break;
     		    }
     		}
 		}
 		
 		if(annotation != null){
 			annotation = (Auditable)method.getAnnotation(Auditable.class);
 			
 			if(annotation != null){
              	if(auditor == null)
              		auditor = new Auditor(interceptableInterfaceClass, method, methodArguments);
              	else{
                  	auditor.setEntity(interceptableInterfaceClass);
                  	auditor.setBusiness(method);
                  	auditor.setBusinessArgumentsValues(methodArguments);
              	}
 			}
 			else
 				auditor = null;
 		}
 		else
 			auditor = null;
		
		return auditor;
     }

	/**
	 * Executado quando antes da execução do método interceptado.
	 * 
	 * @throws Throwable
	 */
	public void before() throws Throwable{
		Auditor auditor = getAuditor();
     
		if(auditor != null)
			auditor.start();
	}

	/**
	 * Executado quando após a execução do método interceptado.
	 * 
	 * @throws Throwable
	 */
	public void after() throws Throwable{
		Auditor auditor = getAuditor();
	     
		if(auditor != null)
			auditor.end();
	}

	/**
	 * Executado quando uma exceção for disparada pelo método interceptado.
	 *
	 * @param e Instância da exceção gerada.
	 * @throws Throwable
	 */
	public void beforeThrow(Throwable e) throws Throwable{
		Auditor auditor = getAuditor();
	     
		if(auditor != null)
			auditor.end(e);
	}

	/**
	 * Executa o método interceptado.
	 *
	 * @return Instância contendo o retorno da execução.
	 * @throws Throwable
	 */
    public <O> O execute() throws Throwable{
		return (O)method.invoke(interceptableInstance, methodArguments);
	}
}