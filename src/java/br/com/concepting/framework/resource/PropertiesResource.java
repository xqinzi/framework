package br.com.concepting.framework.resource;

import java.util.Properties;
import java.util.ResourceBundle;

import br.com.concepting.framework.util.StringUtil;

/**
 * Classe responsável pelo armazenamento de propriedades.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class PropertiesResource extends BaseResource<Object>{
    private static final long serialVersionUID = -2809340386475717260L;
    
	/**
	 * Construtor - Inicializa objetos e/ou variáveis internas.
	 * 
	 * @param properties Instância contendo as propriedades.
	 */
	public PropertiesResource(Properties properties){
		super();

		setContent(properties);
	}

	/**
	 * Construtor - Inicializa objetos e/ou variáveis internas.
	 * 
	 * @param bundle Instância contendo as propriedades.
	 */
	public PropertiesResource(ResourceBundle bundle){
		super();

        setContent(bundle);
	}

	/**
	 * Retorna o conteúdo de uma propriedade substituindo os identificadores pelos seus respectivos 
	 * parâmetros.
	 * 
	 * Ex:
	 * Propriedade : nononono ${0} nonono ${1}
	 * Array de parametros: {"A", "B"}
	 * Resultado : nononono A nonono B
	 * 
	 * @param propertyId String contendo o valor da propriedade.
	 * @param parameters Array contendo os parâmetros.
	 * @param returnInvalidIdentifier Indica se o retorno deve ser vazio ou retornar o identificador 
	 * de propriedade inválida. Neste caso o retorno será definido da seguinte maneira:
	 * ???<Identificador-da-propriedade>???. 
	 * @return String contendo o valor da propriedade após processamento.
	 */
	public String getProperty(String propertyId, Object parameters[], Boolean returnInvalidIdentifier){
		StringBuilder replaceValueBuffer = new StringBuilder();
		String        value              = StringUtil.trim(getProperty(propertyId, returnInvalidIdentifier));

		if(parameters != null){
			for(Integer cont = 0 ; cont < parameters.length ; cont++){
				if(replaceValueBuffer.length() > 0)
					replaceValueBuffer.delete(0, replaceValueBuffer.length());
				
				replaceValueBuffer.append("{");
				replaceValueBuffer.append(cont);
				replaceValueBuffer.append("}");
				
				value = StringUtil.replaceAll(value, replaceValueBuffer.toString(), StringUtil.trim(parameters[cont]));
			}
		}
		
		return value;
	}

	/**
	 * Retorna o conteúdo de uma propriedade substituindo os identificadores pelos seus respectivos 
	 * parâmetros.
	 * 
	 * Ex:
	 * Propriedade : nononono ${0} nonono ${1}
	 * Array de parametros: {"A", "B"}
	 * Resultado : nononono A nonono B
	 * 
	 * @param propertyId String contendo o valor da propriedade.
	 * @param parameters Array contendo os parâmetros.
	 * @return String contendo o valor da propriedade após processamento.
	 */
	public String getProperty(String propertyId, Object parameters[]){
		return getProperty(propertyId, parameters, true);
	}

	/**
	 * Retorna o conteúdo de uma propriedade.
	 * 
	 * @param propertyId String contendo o identificador da propriedade.
	 * @param returnInvalidIdentifier Indica se o retorno deve ser vazio ou retornar o identificador 
	 * de propriedade inválida. Neste caso o retorno será definido da seguinte maneira:
	 * ???<Identificador-da-propriedade>???. 
	 * @return String contendo o valor da propriedade.
	 */
	public String getProperty(String propertyId, Boolean returnInvalidIdentifier){
		StringBuilder propertyIdBuffer = new StringBuilder();
		String        value            = null;
		
		propertyIdBuffer.append(propertyId);
		
		try{
		    Object content = getContent();
		    
		    if(content instanceof ResourceBundle){
		        ResourceBundle bundle = (ResourceBundle)content;
		        
                value = bundle.getString(propertyId);
		    }
		    else{
		        Properties properties = (Properties)content;
		        
                value = properties.getProperty(propertyId);
                
				if(value == null){
					if(returnInvalidIdentifier){
						propertyIdBuffer.insert(0, "???");
						propertyIdBuffer.append("???");
						
						value = propertyIdBuffer.toString();
					}
				}
			}
		}
		catch(Throwable e){
			if(returnInvalidIdentifier){
				propertyIdBuffer.insert(0, "???");
				propertyIdBuffer.append("???");
				
				value = propertyIdBuffer.toString();
			}
		}

		return value;
	}

	/**
	 * Retorna o conteúdo de uma propriedade.
	 * 
	 * @param propertyId String contendo o identificador da propriedade.
	 * @return String contendo o valor da propriedade.
	 */
	public String getProperty(String propertyId){
		return getProperty(propertyId, true);
	}
}