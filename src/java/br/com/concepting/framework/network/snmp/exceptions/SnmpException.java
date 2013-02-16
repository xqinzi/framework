package br.com.concepting.framework.network.snmp.exceptions;

import br.com.concepting.framework.exceptions.InternalErrorException;

/**
 * Classe que define a exce��o para erros em rotina SNMP.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class SnmpException extends InternalErrorException{
	private Integer responseCode = 0;
	
	/**
	 * Construtor - Define os retornos do erro.
	 * 
	 * @param responseCode Valor inteiro contendo o c�digo de retorno.
	 * @param responseMessage String contendo a mensagem de retorno.
	 */
	public SnmpException(Integer responseCode, String responseMessage){
	    super(responseMessage);
	    
	    setResponseCode(responseCode);
    }

	/**
	 * Construtor - Encapsula o erro que original.
	 * 
	 * @param exception Inst�ncia contendo o erro original.
	 */
	public SnmpException(Throwable exception){
	    super(exception);
    }

	/**
	 * Retorna o c�digo de retorno do erro.
	 * 
	 * @return Valor inteiro contendo o c�digo de retorno.
	 */
	public Integer getResponseCode(){
    	return responseCode;
    }

	/**
	 * Define o c�digo de retorno do erro.
	 * 
	 * @param responseCode Valor inteiro contendo o c�digo de retorno.
	 */
	public void setResponseCode(Integer responseCode){
    	this.responseCode = responseCode;
    }
}
