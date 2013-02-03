package br.com.concepting.framework.resource;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.beanutils.ConstructorUtils;

import br.com.concepting.framework.resource.exceptions.InvalidResourceException;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.XmlReader;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Classe que define a estrutura básica para leitura/manipulação de arquivos de configurações no 
 * formato XML.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class XmlResourceLoader extends BaseResourceLoader{
	/**
	 * Construtor - Efetua a leitura de um arquivo de configurações específico.
	 * 
	 * @param resourceId String contendo o identificador do arquivo.
	 * @throws InvalidResourceException
	 */
	protected XmlResourceLoader(String resourceId) throws InvalidResourceException{
		super(resourceId);
	}

	/**
	 * Construtor - Efetua a leitura de um arquivo de configurações específico.
	 * 
	 * @param resourceDir String contendo o diretório de armazenamento do arquivo.
	 * @param resourceId String contendo o identificador do arquivo.
	 * @throws InvalidResourceException
	 */
	protected XmlResourceLoader(String resourceDir, String resourceId) throws InvalidResourceException{
		super(resourceDir, resourceId);
	}

	/**
	 * @see br.com.concepting.framework.resource.BaseResourceLoader#parseResource()
	 */
    protected <C> C parseResource() throws InvalidResourceException{
		try{
			XmlReader   content = null;
			InputStream stream  = null;

			if(getResourceDir().length() == 0)
				stream = getClass().getClassLoader().getResourceAsStream(getResourceId());
			else
				stream = new FileInputStream(getResourceId());

			if(stream == null)
				throw new InvalidResourceException(getResourceId());

			content = new XmlReader(stream);

			return (C)content.getRoot();
		}
		catch(Throwable e){
			throw new InvalidResourceException(getResourceId(), e);
		}
	}

	/**
	 * Retorna o identificador da nó de armazenamento das propriedades do arquivo de recursos.
	 *
	 * @return String contendo o identificador do nó de armazenamento das propriedades.
	 */
	protected String getResourceNodeId(){
		return StringUtil.replaceAll(getClass().getSimpleName(), "ResourceLoader", "").toLowerCase();
	}

	/**
	 * Retorna a classe de armazenamento das propriedades do arquivo de recursos.
	 *
	 * @return Classe que armazenamento das propriedades.
	 */
	protected Class getResourceClass() throws ClassNotFoundException{
		String resourceClassId = StringUtil.replaceAll(getClass().getName(), "Loader", "");
		
		return Class.forName(resourceClassId);
	}

	/**
	 * Retorna um instância contendo configurações.
	 * 
	 * @param id String contendo o identificador das configurações.
	 * @return Instância contendo as configurações.
	 * @throws InvalidResourceException
	 */
    public <R extends BaseResource> R get(String id) throws InvalidResourceException{
		XmlNode content             = getContent();
		XmlNode resourceNode        = null;
		XmlNode defaultResourceNode = null;
		String  resourceNodeId      = getResourceNodeId();
		Integer cont                = 0;

		while(true){
			resourceNode = content.getNode(cont);
			if(resourceNode == null)
				break;

			if(resourceNode.getName().equals(resourceNodeId)){
				if(Boolean.parseBoolean(resourceNode.getAttribute("default")))
					defaultResourceNode = resourceNode;

				if(StringUtil.trim(resourceNode.getAttribute("id")).equals(id))
					break;
			} 

			cont++;
		}

		if(resourceNode == null)
			resourceNode = defaultResourceNode;

		if(resourceNode == null)
			throw new InvalidResourceException(getResourceId());

		id = StringUtil.trim(resourceNode.getAttribute("id"));
		if(id.length() == 0)
			throw new InvalidResourceException(getResourceId(), resourceNode.getText());
		
		try{
			Class        resourceClass    = getResourceClass();
			BaseResource resourceInstance = (BaseResource)ConstructorUtils.invokeConstructor(resourceClass, null);
	
			resourceInstance.setId(id);
			resourceInstance.setContent(resourceNode);
			resourceInstance.setDefault(Boolean.parseBoolean(resourceNode.getAttribute("default")));
			
			return (R)resourceInstance;
		}
		catch(Throwable e){
			throw new InvalidResourceException(getResourceId(), e);
		}
	}

	/**
	 * Retorna a instância contendo as configurações default.
	 * 
	 * @return Instância contendo as configurações.
	 * @throws InvalidResourceException
	 */
    public <R extends BaseResource> R getDefault() throws InvalidResourceException{
		return (R)get("default");
	}
}