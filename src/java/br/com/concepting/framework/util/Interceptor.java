package br.com.concepting.framework.util;

import java.lang.reflect.Method;

import br.com.concepting.framework.audit.Auditor;
import br.com.concepting.framework.audit.annotations.Auditable;

/**
 * Classe utilit�ria que define a estrutura b�sica para uma interceptador de execu��o de 
 * m�todos.
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
	 * Construtor - Define as propriedades da classe e do m�todo intercept�vel. 
	 *
	 * @param interceptableInstance Inst�ncia contendo as propriedade da classe intercept�vel.
	 * @param interceptableInterfaceClass Interface da classe interceptada.
	 * @param method Inst�ncia contendo as propriedades do m�todo intercept�vel.
	 * @param methodArguments Array contendo os argumentos do m�todo intercept�vel.
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
	 * Retorna a inst�ncia contendo as propriedades da auditoria.
	 *
	 * @return Inst�ncia contendo as propriedades da auditoria.
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
	 * Executado quando antes da execu��o do m�todo interceptado.
	 * 
	 * @throws Throwable
	 */
	public void before() throws Throwable{
		Auditor auditor = getAuditor();
     
		if(auditor != null)
			auditor.start();
	}

	/**
	 * Executado quando ap�s a execu��o do m�todo interceptado.
	 * 
	 * @throws Throwable
	 */
	public void after() throws Throwable{
		Auditor auditor = getAuditor();
	     
		if(auditor != null)
			auditor.end();
	}

	/**
	 * Executado quando uma exce��o for disparada pelo m�todo interceptado.
	 *
	 * @param e Inst�ncia da exce��o gerada.
	 * @throws Throwable
	 */
	public void beforeThrow(Throwable e) throws Throwable{
		Auditor auditor = getAuditor();
	     
		if(auditor != null)
			auditor.end(e);
	}

	/**
	 * Executa o m�todo interceptado.
	 *
	 * @return Inst�ncia contendo o retorno da execu��o.
	 * @throws Throwable
	 */
    public <O> O execute() throws Throwable{
		return (O)method.invoke(interceptableInstance, methodArguments);
	}
}