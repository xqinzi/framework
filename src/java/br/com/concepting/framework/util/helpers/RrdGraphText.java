package br.com.concepting.framework.util.helpers;

import br.com.concepting.framework.util.types.RrdArchiveType;
 
/**
 * Classe que define as propriedades de um texto a ser inserido no gráfico.
 *
 * @author fvilarinho
 * @since 1.0
 */
public class RrdGraphText{
	private String         value          = "";
	private String         datasourceName = "";
	private RrdArchiveType archiveType    = null;
	private String         pattern        = "";

	/**
	 * Construtor - Inicializa objetos e/ou variáveis internas.
	 *
	 * @param value String contendo o texto desejado.
	 */
	public RrdGraphText(String value){
		super();

		setValue(value);
	}

    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param datasourceName String contendo o nome do datasource.
     * @param archiveType Instância contendo o tipo de consolidação.
     * @param pattern String contendo a máscara de formatação.
     */
	public RrdGraphText(String datasourceName, RrdArchiveType archiveType, String pattern){
		super();

		setDatasourceName(datasourceName);
		setPattern(pattern);
		setArchiveType(archiveType);
	}
	
    /**
     * Retorna o tipo de consolidação dos dados.
     *
     * @return Instância contendo o tipo de consolidação.
     */
    public RrdArchiveType getArchiveType(){
        return archiveType;
    }

    /**
     * Define o tipo de consolidação dos dados.
     *
     * @param archiveType Instância contendo o tipo de consolidação.
     */
    public void setArchiveType(RrdArchiveType archiveType){
        this.archiveType = archiveType;
    }

	/**
	 * Retorna o valor do texto. 
	 *
	 * @return String contendo o texto.
	 */
	public String getValue(){
		return value;
	}

	/**
	 * Define o valor do texto. 
	 *
	 * @param value String contendo o texto.
	 */
	public void setValue(String value){
		this.value = value;
	}

	/**
	 * Retorna o nome do datasource cujo valor deve ser exibido. 
	 *
	 * @return String contendo o nome do datasource.
	 */
	public String getDatasourceName(){
		return datasourceName;
	}

	/**
	 * Define o nome do datasource cujo valor deve ser exibido. 
	 *
	 * @param datasourceName String contendo o nome do datasource.
	 */
	public void setDatasourceName(String datasourceName){
		this.datasourceName = datasourceName;
	}

	/**
	 * Retorna a máscara de formatação do texto. 
	 *
	 * @return String contendo a máscara de formatação.
	 */
	public String getPattern(){
		return pattern;
	}

	/**
	 * Define a máscara de formatação do texto. 
	 *
	 * @param pattern String contendo a máscara de formatação.
	 */
	public void setPattern(String pattern){
		this.pattern = pattern;
	}
}
