package br.com.concepting.framework.resource;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Classe respons�vel pelo armazenamento das configura��es de comunica��o com servidores de aplica��es
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
	 * Construtor - Inicializa objetos e/ou vari�veis internas.
	 */
	public FactoryResource(){
		super();
	}
	
	/**
	 * Retorna o tipo de comunica��o.
	 * 
	 * @return String contendo o tipo de comunica��o.
	 */
    public String getType(){
        return type;
    }

    /**
     * Define o tipo de comunica��o.
     * 
     * @param type String contendo o tipo de comunica��o.
     */
    public void setType(String type){
        this.type = type;
    }

	/**
	 * Retorna o identificador da classe respons�vel para comunica��o.
	 * 
	 * @return String contendo o identificador da classe.
	 */
	public String getClazz(){
		return clazz;
	}

	/**
	 * Define o identificador da classe respons�vel para comunica��o.
	 * 
	 * @param clazz String contendo o identificador da classe.
	 */
	public void setClazz(String clazz){
		this.clazz = clazz;
	}

	/**
	 * Retorna a URL para comunica��o.
	 * 
	 * @return String contendo a URL.
	 */
	public String getUrl(){
		return url;
	}

	/**
	 * Define a URL para comunica��o.
	 * 
	 * @param url String contendo a URL.
	 */
	public void setUrl(String url){
		this.url = url;
	}

	/**
	 * Adiciona uma op��o de comunica��o.
	 * 
	 * @param id String contendo o identificador da op��o.
	 * @param value String contendo o valor da op��o.
	 */
	public void addOption(String id, String value){
		if(options == null)
			options = new LinkedHashMap<String, String>();

		options.put(id, value);
	}

	/**
	 * Define um mapa contendo as op��es de comunica��o.
	 * 
	 * @param options Inst�ncia do mapa contendo as op��es.
	 */
	public void setOptions(Map<String, String> options){
		this.options = options;
	}

	/**
	 * Retorna um mapa contendo as op��es de comunica��o.
	 * 
	 * @return Inst�ncia do mapa contendo as op��es.
	 */
	public Map<String, String> getOptions(){
		return options;
	}
}