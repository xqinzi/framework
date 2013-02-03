package br.com.concepting.framework.util.helpers;

import java.awt.Color;

import br.com.concepting.framework.util.types.RrdArchiveType;
import br.com.concepting.framework.util.types.RrdGraphType;

/**
 * Classe que define um datasource para um gráfico baseado em um arquivo RRD.
 *
 * @author fvilarinho
 * @since 1.0
 */
public class RrdGraphDatasource{
	private String         filename       = "";
	private String         name           = "";
	private String         label          = "";
	private String         datasourceName = "";
	private RrdGraphType   graphType      = null;
	private RrdArchiveType archiveType    = null;
	private String         formula        = "";
	private Double         value          = 0d;
	private Color          color          = null;
	private Integer        lineWidth      = 1;

    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     *
     * @param name String contendo o nome do datasource.
     * @param filename String contendo o nome do arquivo RRD.
     * @param datasourceName String contendo o nome do datasource.
     * @param archiveType Instância contendo o tipo de consolidação.
     */
	public RrdGraphDatasource(String name, String filename, String datasourceName, RrdArchiveType archiveType){
		super();
		
		setFilename(filename);
		setName(name);
		setDatasourceName(datasourceName);
		setArchiveType(archiveType);
	}

	/**
	 * Construtor - Inicializa objetos e/ou variáveis internas.
	 *
	 * @param name String contendo o nome do datasource para o gráfico.
	 * @param formula String contendo a fórmula (expressão RPN) a ser aplicada no datasource.                       
	 */
	public RrdGraphDatasource(String name, String formula){
		super();
		
		setName(name);
		setFormula(formula);
	}

    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     *
     * @param name String contendo o nome do datasource para o gráfico.
     * @param graphType Instância contendo o tipo de gráfico.
     * @param color Instância contendo as propriedades da cor do gráfico.
     */
	public RrdGraphDatasource(String name, RrdGraphType graphType, Color color){
		super();
		
		setName(name);
		setGraphType(graphType);
		setColor(color);
	}

    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     *
     * @param label String contendo o título do gráfico.
     * @param graphType Instância contendo o tipo de gráfico.
     * @param value Valor numérico que define o gráfico.
     * @param color Instância contendo as propriedades da cor do gráfico.
     */
	public RrdGraphDatasource(String label, RrdGraphType graphType, Double value, Color color){
		this("", label, graphType, color);
		
		setValue(value);
	}

    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     *
     * @param label String contendo o título do gráfico.
     * @param graphType Instância contendo o tipo de gráfico.
     * @param value Valor numérico que define o gráfico.
     * @param color Instância contendo as propriedades da cor do gráfico.
     * @param lineWidth Valor inteiro contendo a largura da linha.
     */
	public RrdGraphDatasource(String label, RrdGraphType graphType, Double value, Color color, Integer lineWidth){
		this(label, graphType, value, color);
		
		setLineWidth(lineWidth);
	}

    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     *
     * @param name String contendo o nome do datasource para o gráfico.
     * @param label String contendo o título do gráfico.
     * @param graphType Instância contendo o tipo de gráfico.
     * @param color Instância contendo as propriedades da cor do gráfico.
     */
	public RrdGraphDatasource(String name, String label, RrdGraphType graphType, Color color){
		this(name, graphType, color);
		
		setLabel(label);
	}

    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     *
     * @param name String contendo o nome do datasource para o gráfico.
     * @param graphType Instância contendo o tipo de gráfico.
     * @param color Instância contendo as propriedades da cor do gráfico.
     * @param lineWidth Valor inteiro contendo a largura da linha.
     */
	public RrdGraphDatasource(String name, RrdGraphType graphType, Color color, Integer lineWidth){
		this(name, graphType, color);

		setLineWidth(lineWidth);
	}

    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     *
     * @param name String contendo o nome do datasource para o gráfico.
     * @param label String contendo o título do gráfico.
     * @param graphType Instância contendo o tipo de gráfico.
     * @param color Instância contendo as propriedades da cor do gráfico.
     * @param lineWidth Valor inteiro contendo a largura da linha.
     */
	public RrdGraphDatasource(String name, String label, RrdGraphType graphType, Color color, Integer lineWidth){
		this(name, label, graphType, color);

		setLineWidth(lineWidth);
	}
	
	/**
	 * Retorna o valor do gráfico.
	 * 
	 * @return Valor numérico que define o gráfico.
	 */
	public Double getValue(){
    	return value;
    }

    /**
     * Define o valor do gráfico.
     * 
     * @param value Valor numérico que define o gráfico.
     */
	public void setValue(Double value){
    	this.value = value;
    }

	/**
	 * Retorna o nome do datasource para o gráfico.
	 *
	 * @return String contendo o nome do datasource.
	 */
	public String getName(){
		return name;
	}

	/**
	 * Define o nome do datasource para o gráfico.
	 *
	 * @param name String contendo o nome do datasource.
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * Retorna o nome do datasource do arquivo RRD.
	 *
	 * @return String contendo o nome do datasource.
	 */
	public String getDatasourceName(){
		return datasourceName;
	}

	/**
	 * Define o nome do datasource do arquivo RRD.
	 *
	 * @param datasourceName String contendo o nome do datasource.
	 */
	public void setDatasourceName(String datasourceName){
		this.datasourceName = datasourceName;
	}

	/**
	 * Retorna o tipo de gráfico.
	 *
	 * @return Instância contendo o tipo de gráfico.
	 */
	public RrdGraphType getGraphType(){
		return graphType;
	}

    /**
     * Define o tipo de gráfico.
     *
     * @param graphType Instância contendo o tipo de gráfico.
     */
	public void setGraphType(RrdGraphType graphType){
		this.graphType = graphType;
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
	 * Retorna a fórmula (expressão RPN) a ser aplicada no datasource.
	 *
	 * @return String contendo a fórmula.
	 */
	public String getFormula(){
		return formula;
	}

	/**
	 * Define a fórmula (expressão RPN) a ser aplicada no datasource.
	 *
	 * @param formula String contendo a fórmula.
	 */
	public void setFormula(String formula){
		this.formula = formula;
	}

	/**
	 * Retorna a cor do gráfico.
	 * 
	 * @return Instância contendo as propriedades da cor do gráfico.
	 */
	public Color getColor(){
		return color;
	}

    /**
     * Define a cor do gráfico.
     * 
     * @param color Instância contendo as propriedades da cor do gráfico.
     */
	public void setColor(Color color){
		this.color = color;
	}

	/**
	 * Retorna a largura da linha do gráfico.
	 *
	 * @return Valor inteiro contendo a largura da linha.
	 */
	public Integer getLineWidth(){
    	return lineWidth;
    }

	/**
	 * Define a largura da linha do gráfico.
	 *
	 * @param lineWidth Valor inteiro contendo a largura da linha.
	 */
	public void setLineWidth(Integer lineWidth){
    	this.lineWidth = lineWidth;
    }

	/**
	 * Retorna o título do gráfico.
	 *
	 * @return String contendo o título do gráfico.
	 */
	public String getLabel(){
    	return label;
    }

	/**
	 * Define o título do gráfico.
	 *
	 * @param label String contendo o título do gráfico.
	 */
	public void setLabel(String label){
    	this.label = label;
    }

	/**
	 * Retorna o nome do arquivo RRD.
	 *
	 * @return String contendo o nome do arquivo RRD.
	 */
	public String getFilename(){
    	return filename;
    }

	/**
	 * Define o nome do arquivo RRD.
	 *
	 * @param filename String contendo o nome do arquivo RRD.
	 */
	public void setFilename(String filename){
    	this.filename = filename;
    }
}