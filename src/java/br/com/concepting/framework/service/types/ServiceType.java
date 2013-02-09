package br.com.concepting.framework.service.types;

/**
 * Constante que define os tipos das classes de servi�o remoto.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum ServiceType{
	/**
	 * Constante que define um servi�o comum.
	 */
	CLASS,

	/**
	 * Constante que define o servi�o do tipo stateless (n�o mant�m estado).
	 */
	STATELESS,

	/**
	 * Constante que define o servi�o do tipo stateful (mant�m estado).
	 */
	STATEFUL,
	
	/**
	 * Constante que define um WEB Service.
	 */
	WEB_SERVICE;
}