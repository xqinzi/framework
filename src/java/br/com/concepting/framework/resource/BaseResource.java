package br.com.concepting.framework.resource;

import br.com.concepting.framework.caching.CachedObject;

/**
 * Classe que define a estrutura básica para armazenamento de configurações.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class BaseResource extends CachedObject{
	private Boolean isDefault = false;
	
	/**
	 * Construtor - Inicializa objetos e/ou variáveis internas.
	 */
	public BaseResource(){
		super();
	}

	/**
	 * Define se as configurações são default.
	 * 
	 * @param isDefault True/False.
	 */
	public void setDefault(Boolean isDefault){
		this.isDefault = isDefault;
	}

	/**
	 * Indica se as configurações são default.
	 * 
	 * @return True/False.
	 */
	public Boolean isDefault(){
		return isDefault;
	}
}