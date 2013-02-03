package br.com.concepting.framework.util.helpers;

import java.awt.Color;

import br.com.concepting.framework.util.types.RrdArchiveType;
import br.com.concepting.framework.util.types.RrdGraphType;

/**
 * Classe que define um datasource para um gr�fico baseado em um arquivo RRD.
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
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     *
     * @param name String contendo o nome do datasource.
     * @param filename String contendo o nome do arquivo RRD.
     * @param datasourceName String contendo o nome do datasource.
     * @param archiveType Inst�ncia contendo o tipo de consolida��o.
     */
	public RrdGraphDatasource(String name, String filename, String datasourceName, RrdArchiveType archiveType){
		super();
		
		setFilename(filename);
		setName(name);
		setDatasourceName(datasourceName);
		setArchiveType(archiveType);
	}

	/**
	 * Construtor - Inicializa objetos e/ou vari�veis internas.
	 *
	 * @param name String contendo o nome do datasource para o gr�fico.
	 * @param formula String contendo a f�rmula (express�o RPN) a ser aplicada no datasource.                       
	 */
	public RrdGraphDatasource(String name, String formula){
		super();
		
		setName(name);
		setFormula(formula);
	}

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     *
     * @param name String contendo o nome do datasource para o gr�fico.
     * @param graphType Inst�ncia contendo o tipo de gr�fico.
     * @param color Inst�ncia contendo as propriedades da cor do gr�fico.
     */
	public RrdGraphDatasource(String name, RrdGraphType graphType, Color color){
		super();
		
		setName(name);
		setGraphType(graphType);
		setColor(color);
	}

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     *
     * @param label String contendo o t�tulo do gr�fico.
     * @param graphType Inst�ncia contendo o tipo de gr�fico.
     * @param value Valor num�rico que define o gr�fico.
     * @param color Inst�ncia contendo as propriedades da cor do gr�fico.
     */
	public RrdGraphDatasource(String label, RrdGraphType graphType, Double value, Color color){
		this("", label, graphType, color);
		
		setValue(value);
	}

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     *
     * @param label String contendo o t�tulo do gr�fico.
     * @param graphType Inst�ncia contendo o tipo de gr�fico.
     * @param value Valor num�rico que define o gr�fico.
     * @param color Inst�ncia contendo as propriedades da cor do gr�fico.
     * @param lineWidth Valor inteiro contendo a largura da linha.
     */
	public RrdGraphDatasource(String label, RrdGraphType graphType, Double value, Color color, Integer lineWidth){
		this(label, graphType, value, color);
		
		setLineWidth(lineWidth);
	}

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     *
     * @param name String contendo o nome do datasource para o gr�fico.
     * @param label String contendo o t�tulo do gr�fico.
     * @param graphType Inst�ncia contendo o tipo de gr�fico.
     * @param color Inst�ncia contendo as propriedades da cor do gr�fico.
     */
	public RrdGraphDatasource(String name, String label, RrdGraphType graphType, Color color){
		this(name, graphType, color);
		
		setLabel(label);
	}

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     *
     * @param name String contendo o nome do datasource para o gr�fico.
     * @param graphType Inst�ncia contendo o tipo de gr�fico.
     * @param color Inst�ncia contendo as propriedades da cor do gr�fico.
     * @param lineWidth Valor inteiro contendo a largura da linha.
     */
	public RrdGraphDatasource(String name, RrdGraphType graphType, Color color, Integer lineWidth){
		this(name, graphType, color);

		setLineWidth(lineWidth);
	}

    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     *
     * @param name String contendo o nome do datasource para o gr�fico.
     * @param label String contendo o t�tulo do gr�fico.
     * @param graphType Inst�ncia contendo o tipo de gr�fico.
     * @param color Inst�ncia contendo as propriedades da cor do gr�fico.
     * @param lineWidth Valor inteiro contendo a largura da linha.
     */
	public RrdGraphDatasource(String name, String label, RrdGraphType graphType, Color color, Integer lineWidth){
		this(name, label, graphType, color);

		setLineWidth(lineWidth);
	}
	
	/**
	 * Retorna o valor do gr�fico.
	 * 
	 * @return Valor num�rico que define o gr�fico.
	 */
	public Double getValue(){
    	return value;
    }

    /**
     * Define o valor do gr�fico.
     * 
     * @param value Valor num�rico que define o gr�fico.
     */
	public void setValue(Double value){
    	this.value = value;
    }

	/**
	 * Retorna o nome do datasource para o gr�fico.
	 *
	 * @return String contendo o nome do datasource.
	 */
	public String getName(){
		return name;
	}

	/**
	 * Define o nome do datasource para o gr�fico.
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
	 * Retorna o tipo de gr�fico.
	 *
	 * @return Inst�ncia contendo o tipo de gr�fico.
	 */
	public RrdGraphType getGraphType(){
		return graphType;
	}

    /**
     * Define o tipo de gr�fico.
     *
     * @param graphType Inst�ncia contendo o tipo de gr�fico.
     */
	public void setGraphType(RrdGraphType graphType){
		this.graphType = graphType;
	}

	/**
	 * Retorna o tipo de consolida��o dos dados.
	 *
	 * @return Inst�ncia contendo o tipo de consolida��o.
	 */
	public RrdArchiveType getArchiveType(){
		return archiveType;
	}

    /**
     * Define o tipo de consolida��o dos dados.
     *
     * @param archiveType Inst�ncia contendo o tipo de consolida��o.
     */
	public void setArchiveType(RrdArchiveType archiveType){
		this.archiveType = archiveType;
	}

	/**
	 * Retorna a f�rmula (express�o RPN) a ser aplicada no datasource.
	 *
	 * @return String contendo a f�rmula.
	 */
	public String getFormula(){
		return formula;
	}

	/**
	 * Define a f�rmula (express�o RPN) a ser aplicada no datasource.
	 *
	 * @param formula String contendo a f�rmula.
	 */
	public void setFormula(String formula){
		this.formula = formula;
	}

	/**
	 * Retorna a cor do gr�fico.
	 * 
	 * @return Inst�ncia contendo as propriedades da cor do gr�fico.
	 */
	public Color getColor(){
		return color;
	}

    /**
     * Define a cor do gr�fico.
     * 
     * @param color Inst�ncia contendo as propriedades da cor do gr�fico.
     */
	public void setColor(Color color){
		this.color = color;
	}

	/**
	 * Retorna a largura da linha do gr�fico.
	 *
	 * @return Valor inteiro contendo a largura da linha.
	 */
	public Integer getLineWidth(){
    	return lineWidth;
    }

	/**
	 * Define a largura da linha do gr�fico.
	 *
	 * @param lineWidth Valor inteiro contendo a largura da linha.
	 */
	public void setLineWidth(Integer lineWidth){
    	this.lineWidth = lineWidth;
    }

	/**
	 * Retorna o t�tulo do gr�fico.
	 *
	 * @return String contendo o t�tulo do gr�fico.
	 */
	public String getLabel(){
    	return label;
    }

	/**
	 * Define o t�tulo do gr�fico.
	 *
	 * @param label String contendo o t�tulo do gr�fico.
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