package br.com.concepting.framework.audit.resource;

import java.util.Collection;
import java.util.LinkedList;

import br.com.concepting.framework.resource.BaseResource;
import br.com.concepting.framework.resource.FactoryResource;

/**
 * Classe que define a estrutura b�sica para armazenamento das configura��es de 
 * auditoria. 
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class AuditorResource extends BaseResource{
	private String                      level     = "";
	private Collection<FactoryResource> appenders = null;
	
	/**
	 * Construtor - Inicializa objetos e/ou vari�veis internas.
	 */
	public AuditorResource(){
		super();
	}

	/**
	 * Retorna o n�vel de auditoria.
	 *
	 * @return String contendo o n�vel de auditoria.
	 */
	public String getLevel(){
     	return level;
     }

	/**
	 * Define o n�vel de auditoria.
	 *
	 * @param level String contendo o n�vel de auditoria.
	 */
	public void setLevel(String level){
     	this.level = level;
     }

	/**
	 * Retorna a lista contendo as configura��es dos mecanismos de exibi��o e
	 * armazenamento das mensagens de auditoria.
	 * 
	 * @return Lista contendo as configura��es definidas.
	 */
	public Collection<FactoryResource> getAppenders(){
		return appenders;
	}

	/**
	 * Adiciona um novo mecanismo de exibi��o e armazenamento das mensagens de 
	 * auditoria.
	 * 
	 * @param appender Inst�ncia contendo a configura��o.
	 */
	public void addAppender(FactoryResource appender){
		if(appenders == null)
			appenders = new LinkedList<FactoryResource>();

		appenders.add(appender);
	}
}