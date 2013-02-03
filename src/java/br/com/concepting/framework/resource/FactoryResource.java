package br.com.concepting.framework.resource;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Classe responsável pelo armazenamento das configurações de comunicação com servidores de aplicações
 * e/ou banco de dados.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class FactoryResource extends BaseResource{
	private String              clazz   = "";
	private String              url     = "";
	private String              type    = "";
	private Map<String, String> options = null;

	/**
	 * Construtor - Inicializa objetos e/ou variáveis internas.
	 */
	public FactoryResource(){
		super();
	}
	
	/**
	 * Retorna o tipo de comunicação.
	 * 
	 * @return String contendo o tipo de comunicação.
	 */
    public String getType(){
        return type;
    }

    /**
     * Define o tipo de comunicação.
     * 
     * @param type String contendo o tipo de comunicação.
     */
    public void setType(String type){
        this.type = type;
    }

	/**
	 * Retorna o identificador da classe responsável para comunicação.
	 * 
	 * @return String contendo o identificador da classe.
	 */
	public String getClazz(){
		return clazz;
	}

	/**
	 * Define o identificador da classe responsável para comunicação.
	 * 
	 * @param clazz String contendo o identificador da classe.
	 */
	public void setClazz(String clazz){
		this.clazz = clazz;
	}

	/**
	 * Retorna a URL para comunicação.
	 * 
	 * @return String contendo a URL.
	 */
	public String getUrl(){
		return url;
	}

	/**
	 * Define a URL para comunicação.
	 * 
	 * @param url String contendo a URL.
	 */
	public void setUrl(String url){
		this.url = url;
	}

	/**
	 * Adiciona uma opção de comunicação.
	 * 
	 * @param id String contendo o identificador da opção.
	 * @param value String contendo o valor da opção.
	 */
	public void addOption(String id, String value){
		if(options == null)
			options = new LinkedHashMap<String, String>();

		options.put(id, value);
	}

	/**
	 * Define um mapa contendo as opções de comunicação.
	 * 
	 * @param options Instância do mapa contendo as opções.
	 */
	public void setOptions(Map<String, String> options){
		this.options = options;
	}

	/**
	 * Retorna um mapa contendo as opções de comunicação.
	 * 
	 * @return Instância do mapa contendo as opções.
	 */
	public Map<String, String> getOptions(){
		return options;
	}
}