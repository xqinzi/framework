package br.com.concepting.framework.util.helpers;

/**
 * Classe que define uma coluna armazenada no arquivo RRD.
 *
 * @author fvilarinho
 * @since 1.0
 */
public class RrdColumn{
	private String name  = "";
	private Object value = null;
	
	/**
	 * Construtor - Inicializa objetos e/ou variáveis internas.
	 */
	public RrdColumn(){
		super();
	}
	
	/**
	 * Construtor - Inicializa objetos e/ou variáveis internas.
	 * 
	 * @param name String contendo o nome da coluna.
	 * @param value Valor da coluna.
	 */
	public RrdColumn(String name, Object value){
		this();
		
		setName(name);
		setValue(value);
	}

	/**
	 * Retorna o nome da coluna.
	 *
	 * @return String contendo o nome da coluna.
	 */
	public String getName(){
    	return name;
    }
	
	/**
	 * Define o nome da coluna.
	 *
	 * @param name String contendo o nome da coluna.
	 */
	public void setName(String name){
    	this.name = name;
    }
	
	/**
	 * Retorna o valor da coluna.
	 *
	 * @return Valor da coluna. 
	 */
	public Object getValue(){
    	return value;
    }
	
	/**
	 * Define o valor da coluna.
	 *
	 * @param value Valor da coluna. 
	 */
	public void setValue(Object value){
    	this.value = value;
    }
}
