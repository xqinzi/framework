package br.com.concepting.framework.audit.resource;

import java.util.Collection;
import java.util.LinkedList;

import br.com.concepting.framework.resource.BaseResource;
import br.com.concepting.framework.resource.FactoryResource;

/**
 * Classe que define a estrutura básica para armazenamento das configurações de 
 * auditoria. 
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class AuditorResource extends BaseResource{
	private String                      level     = "";
	private Collection<FactoryResource> appenders = null;
	
	/**
	 * Construtor - Inicializa objetos e/ou variáveis internas.
	 */
	public AuditorResource(){
		super();
	}

	/**
	 * Retorna o nível de auditoria.
	 *
	 * @return String contendo o nível de auditoria.
	 */
	public String getLevel(){
     	return level;
     }

	/**
	 * Define o nível de auditoria.
	 *
	 * @param level String contendo o nível de auditoria.
	 */
	public void setLevel(String level){
     	this.level = level;
     }

	/**
	 * Retorna a lista contendo as configurações dos mecanismos de exibição e
	 * armazenamento das mensagens de auditoria.
	 * 
	 * @return Lista contendo as configurações definidas.
	 */
	public Collection<FactoryResource> getAppenders(){
		return appenders;
	}

	/**
	 * Adiciona um novo mecanismo de exibição e armazenamento das mensagens de 
	 * auditoria.
	 * 
	 * @param appender Instância contendo a configuração.
	 */
	public void addAppender(FactoryResource appender){
		if(appenders == null)
			appenders = new LinkedList<FactoryResource>();

		appenders.add(appender);
	}
}