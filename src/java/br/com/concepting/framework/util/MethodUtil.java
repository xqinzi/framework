package br.com.concepting.framework.util;

import java.lang.reflect.Method;

import org.apache.commons.beanutils.MethodUtils;

/**
 * Classe utilitária responsável por manipular informações de métodos de via Reflection.
 *
 * @author fvilarinho
 * @since 1.0
 */
public class MethodUtil extends MethodUtils{
	/**
	 * Retorna a instância de um método localiza na pilha de execução da JVM.
	 *
	 * @param position Valor inteiro contendo a posição do método na pilha.
	 * @return Instância contendo as propriedades do método.
	 * @throws ClassNotFoundException
	 */
	public static Method getMethodFromStackTrace(Integer position) throws ClassNotFoundException{
		Thread            currentThread     = Thread.currentThread();
		StackTraceElement stackTraceElement = null;
		StackTraceElement stackTrace[]      = currentThread.getStackTrace();
		Integer           cont              = 0;
		
		for(cont = 0 ; cont < stackTrace.length ; cont++){
			stackTraceElement = stackTrace[cont];
			
			if(stackTraceElement.getMethodName().equals("getMethodFromStackTrace")){
				cont += position;
				
				stackTraceElement = stackTrace[cont];
				
				break;
			}
			
			stackTraceElement = null;
		}
		
		if(stackTraceElement == null)
			return null;
		
		Class  clazz     = Class.forName(stackTraceElement.getClassName());
		Method methods[] = clazz.getDeclaredMethods();
		
		for(Method method : methods)
			if(method.getName().equals(stackTraceElement.getMethodName()))
				return method;
		
		return null; 
	}
}