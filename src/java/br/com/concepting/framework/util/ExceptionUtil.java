package br.com.concepting.framework.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

import br.com.concepting.framework.resource.exceptions.InvalidResourceException;
import br.com.concepting.framework.util.exceptions.ExpectedErrorException;
import br.com.concepting.framework.util.exceptions.ExpectedException;
import br.com.concepting.framework.util.exceptions.ExpectedWarningException;
import br.com.concepting.framework.util.exceptions.InternalErrorException;

/**
 * Classe utilit�ria para manipula��o de exce��es.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class ExceptionUtil{
	/**
	 * Retorna uma string contendo o hist�rico da exce��o.
	 * 
	 * @param exception Inst�ncia da exce��o desejada.
	 * @return String contendo o hist�rico gerado.
	 */
	public static String getTrace(Throwable exception){
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		PrintStream           stream = new PrintStream(buffer);

		exception.printStackTrace(stream);

		return new String(buffer.toByteArray());
	}
	
	public static Throwable getOriginException(Throwable exception){
        Throwable e = exception;
        
        while((e instanceof InvocationTargetException) || (e instanceof UndeclaredThrowableException)){
            if(e instanceof InvocationTargetException)
                e = ((InvocationTargetException)e).getTargetException();
            
            if(e instanceof UndeclaredThrowableException)
                e = ((UndeclaredThrowableException)e).getUndeclaredThrowable();
        }
	    
        return e;
	}

	/**
	 * Indica se uma exce��o � um inst�ncia de uma exce��o esperada.
	 * 
	 * @param exception Inst�ncia da exce��o.
	 * @return True/False.
	 */
	public static Boolean isExpectedException(Throwable exception){
	    
		return (exception instanceof ExpectedException);
	}

	/**
	 * Indica se uma exce��o � um inst�ncia de um aviso esperado.
	 * 
	 * @param exception Inst�ncia da exce��o.
	 * @return True/False.
	 */
	public static Boolean isExpectedWarningException(Throwable exception){
		return (exception instanceof ExpectedWarningException);
	}

	/**
	 * Indica se uma exce��o � um inst�ncia de um erro esperado.
	 * 
	 * @param exception Inst�ncia da exce��o.
	 * @return True/False.
	 */
	public static Boolean isExpectedErrorException(Throwable exception){
		return (exception instanceof ExpectedErrorException);
	}

	/**
	 * Indica se uma exce��o � uma inst�ncia de um erro interno.
	 * 
	 * @param exception Inst�ncia da exce��o.
	 * @return True/False.
	 */
	public static Boolean isInternalErrorException(Throwable exception){
		return (exception instanceof InternalErrorException);
	}

	/**
	 * Indica se a exce��o quando um recurso � inv�lido ou n�o foi encontrado.
	 * 
	 * @param exception Inst�ncia da exce��o desejada.
	 * @return True/False.
	 */
	public static Boolean isInvalidResourceException(Throwable exception){
		return (exception instanceof InvalidResourceException);
	}

	/**
	 * Retorna o identificador de uma exce��o.
	 * 
	 * @param exception Inst�ncia da exce��o desejada.
	 * @return String contendo a exce��o desejada.
	 */
	public static String getExceptionId(Throwable exception){
		String        exceptionId = StringUtil.replaceLast(exception.getClass().getSimpleName(), "Exception", "");
		StringBuilder buffer      = new StringBuilder();
		
		buffer.append(exceptionId.substring(0, 1).toLowerCase());
		buffer.append(exceptionId.substring(1));

		return buffer.toString();
	}
}