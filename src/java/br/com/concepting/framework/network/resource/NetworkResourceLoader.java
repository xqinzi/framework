package br.com.concepting.framework.network.resource;

import br.com.concepting.framework.network.constants.NetworkConstants;
import br.com.concepting.framework.resource.BaseResource;
import br.com.concepting.framework.resource.XmlResourceLoader;
import br.com.concepting.framework.resource.exceptions.InvalidResourceException;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Classe responsável pela leitura/manipulação das configurações dos serviços de rede.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class NetworkResourceLoader<R extends BaseResource<XmlNode>> extends XmlResourceLoader<R>{
	/**
	 * Construtor - Inicializa classe de leitura/manipulação do arquivo de onfigurações dos serviços 
	 * rede default.
	 * 
	 * @throws InvalidResourceException
	 */
	public NetworkResourceLoader() throws InvalidResourceException{
		super(NetworkConstants.DEFAULT_RESOURCE_ID);
	}
}