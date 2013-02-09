package br.com.concepting.framework.service.types;

/**
 * Constante que define os tipos das classes de serviço remoto.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum ServiceType{
	/**
	 * Constante que define um serviço comum.
	 */
	CLASS,

	/**
	 * Constante que define o serviço do tipo stateless (não mantém estado).
	 */
	STATELESS,

	/**
	 * Constante que define o serviço do tipo stateful (mantém estado).
	 */
	STATEFUL,
	
	/**
	 * Constante que define um WEB Service.
	 */
	WEB_SERVICE;
}