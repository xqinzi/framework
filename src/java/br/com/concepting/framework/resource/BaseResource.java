package br.com.concepting.framework.resource;

import br.com.concepting.framework.caching.CachedObject;

/**
 * Classe que define a estrutura b�sica para armazenamento de configura��es.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class BaseResource extends CachedObject{
	private Boolean isDefault = false;
	
	/**
	 * Construtor - Inicializa objetos e/ou vari�veis internas.
	 */
	public BaseResource(){
		super();
	}

	/**
	 * Define se as configura��es s�o default.
	 * 
	 * @param isDefault True/False.
	 */
	public void setDefault(Boolean isDefault){
		this.isDefault = isDefault;
	}

	/**
	 * Indica se as configura��es s�o default.
	 * 
	 * @return True/False.
	 */
	public Boolean isDefault(){
		return isDefault;
	}
}