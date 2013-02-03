package br.com.concepting.framework.network.resource;

import br.com.concepting.framework.network.constants.NetworkConstants;
import br.com.concepting.framework.resource.XmlResourceLoader;
import br.com.concepting.framework.resource.exceptions.InvalidResourceException;

/**
 * Classe respons�vel pela leitura/manipula��o das configura��es dos servi�os de rede.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class NetworkResourceLoader extends XmlResourceLoader{
	/**
	 * Construtor - Inicializa classe de leitura/manipula��o do arquivo de onfigura��es dos servi�os 
	 * rede default.
	 * 
	 * @throws InvalidResourceException
	 */
	public NetworkResourceLoader() throws InvalidResourceException{
		super(NetworkConstants.DEFAULT_RESOURCE_ID);
	}
}