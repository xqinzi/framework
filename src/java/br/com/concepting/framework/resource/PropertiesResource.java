package br.com.concepting.framework.resource;

import java.util.Properties;
import java.util.ResourceBundle;

import br.com.concepting.framework.util.StringUtil;

/**
 * Classe respons�vel pelo armazenamento de propriedades.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class PropertiesResource extends BaseResource{
	private Properties     properties = null;
	private ResourceBundle bundle     = null;

	/**
	 * Construtor - Inicializa objetos e/ou vari�veis internas.
	 * 
	 * @param properties Inst�ncia contendo as propriedades.
	 */
	public PropertiesResource(Properties properties){
		super();

		this.properties = properties;
	}

	/**
	 * Construtor - Inicializa objetos e/ou vari�veis internas.
	 * 
	 * @param bundle Inst�ncia contendo as propriedades.
	 */
	public PropertiesResource(ResourceBundle bundle){
		super();

		this.bundle = bundle;
	}

	/**
	 * Retorna a inst�ncia contendo as propriedades.
	 * 
	 * @return Inst�ncia contendo as propriedades.
	 */
	public ResourceBundle getBundle(){
		return bundle;
	}

	/**
	 * Retorna a inst�ncia contendo as propriedades.
	 * 
	 * @return Inst�ncia contendo as propriedades.
	 */
	public Properties getProperties(){
		return properties;
	}

	/**
	 * Retorna o conte�do de uma propriedade substituindo os identificadores pelos seus respectivos 
	 * par�metros.
	 * 
	 * Ex:
	 * Propriedade : nononono ${0} nonono ${1}
	 * Array de parametros: {"A", "B"}
	 * Resultado : nononono A nonono B
	 * 
	 * @param propertyId String contendo o valor da propriedade.
	 * @param parameters Array contendo os par�metros.
	 * @param returnInvalidIdentifier Indica se o retorno deve ser vazio ou retornar o identificador 
	 * de propriedade inv�lida. Neste caso o retorno ser� definido da seguinte maneira:
	 * ???<Identificador-da-propriedade>???. 
	 * @return String contendo o valor da propriedade ap�s processamento.
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
	 * Retorna o conte�do de uma propriedade substituindo os identificadores pelos seus respectivos 
	 * par�metros.
	 * 
	 * Ex:
	 * Propriedade : nononono ${0} nonono ${1}
	 * Array de parametros: {"A", "B"}
	 * Resultado : nononono A nonono B
	 * 
	 * @param propertyId String contendo o valor da propriedade.
	 * @param parameters Array contendo os par�metros.
	 * @return String contendo o valor da propriedade ap�s processamento.
	 */
	public String getProperty(String propertyId, Object parameters[]){
		return getProperty(propertyId, parameters, true);
	}

	/**
	 * Retorna o conte�do de uma propriedade.
	 * 
	 * @param propertyId String contendo o identificador da propriedade.
	 * @param returnInvalidIdentifier Indica se o retorno deve ser vazio ou retornar o identificador 
	 * de propriedade inv�lida. Neste caso o retorno ser� definido da seguinte maneira:
	 * ???<Identificador-da-propriedade>???. 
	 * @return String contendo o valor da propriedade.
	 */
	public String getProperty(String propertyId, Boolean returnInvalidIdentifier){
		StringBuilder propertyIdBuffer = new StringBuilder();
		String        value            = null;
		
		propertyIdBuffer.append(propertyId);
		
		try{
			if(bundle == null){
				value = properties.getProperty(propertyId);
				if(value == null){
					if(returnInvalidIdentifier){
						propertyIdBuffer.insert(0, "???");
						propertyIdBuffer.append("???");
						
						value = propertyIdBuffer.toString();
					}
				}
			}
			else
				value = bundle.getString(propertyId);
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
	 * Retorna o conte�do de uma propriedade.
	 * 
	 * @param propertyId String contendo o identificador da propriedade.
	 * @return String contendo o valor da propriedade.
	 */
	public String getProperty(String propertyId){
		return getProperty(propertyId, true);
	}
}