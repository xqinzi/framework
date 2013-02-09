package br.com.concepting.framework.util.helpers;

import java.awt.Color;
import java.util.Collection;
import java.util.LinkedList;

import br.com.concepting.framework.web.types.ContentType;

/**
 * Classe que define as características de um gráfico baseado em um arquivo RRD.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class RrdGraphDefinition{
	private ContentType contentType          = ContentType.PNG;
	private Integer         width                = 0;
	private Integer         height               = 0;
	private Double          maximumValue         = 0d;
	private Double          minimumValue         = 0d;
	private String          xAxisLabel           = "";
	private String          yAxisLabel           = "";
	private String          verticalLabel        = "";
	private String          title                = "";
	private String          titleFont            = "";
	private Integer         titleFontSize        = 0;
	private String          font                 = "";
	private Integer         fontSize             = 0;
	private Color           color                = null;
	private Color           backgroundColor      = null;
	private Color           graphBackgroundColor = null;
	private Color           gridLinesColor       = null;
	private Boolean         useRrd4j             = true;
	private Collection      objects              = null;
	
	/**
	 * Indica se deve usar o framework RRD4J para a geração do gráfico.
	 * 
	 * @return True/False.
	 */
    public Boolean useRrd4j(){
        return useRrd4j;
    }

    /**
     * Indica se deve usar o framework RRD4J para a geração do gráfico.
     * 
     * @return True/False.
     */
	public Boolean getUseRrd4j(){
    	return useRrd4j();
    }

    /**
     * Define se deve usar o framework RRD4J para a geração do gráfico.
     * 
     * @param useRrd4j True/False.
     */
	public void setUseRrd4j(Boolean useRrd4j){
    	this.useRrd4j = useRrd4j;
    }

	/**
	 * Adiciona um datasource.
	 * 
	 * @param datasource Instância contendo as propriedades do datasource.
	 */
    public void addDatasource(RrdGraphDatasource datasource){
		if(objects == null)
			objects = new LinkedList<RrdGraphDatasource>();

		objects.add(datasource);
	}

	/**
	 * Adiciona um texto.
	 * 
	 * @param text String contendo as propriedades do texto.
	 */
    public void addText(RrdGraphText text){
		if(objects == null)
			objects = new LinkedList<RrdGraphText>();

		objects.add(text);
	}

	/**
	 * Adiciona uma quebra de linha.
	 */
    public void addLineBreak(){
		if(objects == null)
			objects = new LinkedList<RrdGraphText>();

		objects.add(new RrdGraphText("\\s"));
	}

	/**
	 * Retorna a largura do gráfico.
	 * 
	 * @return Valor inteiro contendo a largura do gráfico.
	 */
	public Integer getWidth(){
		return width;
	}

	/**
	 * Define a largura do gráfico.
	 * 
	 * @param width Valor inteiro contendo a largura do gráfico.
	 */
	public void setWidth(Integer width){
		this.width = width;
	}

	/**
	 * Retorna a altura do gráfico.
	 * 
	 * @return Valor inteiro contendo a altura do gráfico.
	 */
	public Integer getHeight(){
		return height;
	}

	/**
	 * Define a altura do gráfico.
	 * 
	 * @param height Valor inteiro contendo a altura do gráfico.
	 */
	public void setHeight(Integer height){
		this.height = height;
	}

	/**
	 * Retorna o título do gráfico.
	 * 
	 * @return String contendo o título do gráfico.
	 */
	public String getTitle(){
		return title;
	}

	/**
	 * Define o título do gráfico.
	 * 
	 * @param title String contendo o título do gráfico.
	 */
	public void setTitle(String title){
		this.title = title;
	}

	/**
	 * Retorna o valor máximo a ser plotado no gráfico.
	 * 
	 * @return Valor em ponto flutuante contendo o valor máximo.
	 */
	public Double getMaximumValue(){
		return maximumValue;
	}

	/**
	 * Define o valor máximo a ser plotado no gráfico.
	 * 
	 * @param maximumValue Valor em ponto flutuante contendo o valor máximo.
	 */
	public void setMaximumValue(Double maximumValue){
		this.maximumValue = maximumValue;
	}

	/**
	 * Retorna o valor mínimo a ser plotado no gráfico.
	 * 
	 * @return Valor em ponto flutuante contendo o valor mínimo.
	 */
	public Double getMinimumValue(){
		return minimumValue;
	}

	/**
	 * Define o valor mínimo a ser plotado no gráfico.
	 * 
	 * @param minimumValue Valor em ponto flutuante contendo o valor mínimo.
	 */
	public void setMinimumValue(Double minimumValue){
		this.minimumValue = minimumValue;
	}

	/**
	 * Retorna o título do eixo X.
	 *
	 * @return String contendo o título do eixo X.
	 */
	public String getXAxisLabel(){
		return xAxisLabel;
	}

	/**
	 * Define o título do eixo X.
	 *
	 * @param axisLabel String contendo o título do eixo X.
	 */
	public void setXAxisLabel(String axisLabel){
		xAxisLabel = axisLabel;
	}

	/**
	 * Retorna o título do eixo Y.
	 *
	 * @return String contendo o título do eixo Y.
	 */
	public String getYAxisLabel(){
		return yAxisLabel;
	}

	/**
	 * Define o título do eixo Y.
	 *
	 * @param axisLabel String contendo o título do eixo Y.
	 */
	public void setYAxisLabel(String axisLabel){
		yAxisLabel = axisLabel;
	}

	/**
	 * Retorna a cor de fundo da área extra do gráfico.
	 *
	 * @return String contendo a cor de fundo.
	 */
	public Color getBackgroundColor(){
		return backgroundColor;
	}

	/**
	 * Define a cor de fundo da área extra do gráfico.
	 *
	 * @param backgroundColor String contendo a cor de fundo.
	 */
	public void setBackgroundColor(Color backgroundColor){
		this.backgroundColor = backgroundColor;
	}

	/**
	 * Retorna a cor das linhas do grid do gráfico.
	 *
	 * @return String contendo a cor das linhas do grid.
	 */
	public Color getGridLinesColor(){
		return gridLinesColor;
	}

	/**
	 * Define a cor das linhas do grid do gráfico.
	 *
	 * @param gridLinesColor String contendo a cor das linhas do grid.
	 */
	public void setGridLinesColor(Color gridLinesColor){
		this.gridLinesColor = gridLinesColor;
	}

	/**
	 * Retorna o título do eixo Y.
	 *
	 * @return String contendo o título do eixo Y.
	 */
	public String getVerticalLabel(){
		return verticalLabel;
	}

	/**
	 * Define o título do eixo Y.
	 *
	 * @param verticalLabel String contendo o título do eixo Y.
	 */
	public void setVerticalLabel(String verticalLabel){
		this.verticalLabel = verticalLabel;
	}

	/**
	 * Retorna o formato da imagem do gráfico.
	 *
	 * @return Instância contendo o formato da imagem do gráfico.
	 */
	public ContentType getContentType(){
		return contentType;
	}

	/**
	 * Define o formato da imagem do gráfico.
	 *
	 * @param contentType Instância contendo o formato da imagem do gráfico.
	 */
	public void setContentType(ContentType contentType){
		this.contentType = contentType;
	}

	/**
	 * Retorna a lista de objetos do gráfico.
	 *
	 * @return Lista contendo os objetos do gráfico.
	 */
	public Collection getObjects(){
		return objects;
	}

	/**
	 * Define a lista de objetos do gráfico.
	 *
	 * @param objects Lista contendo os objetos do gráfico.
	 */
	public void setObjects(Collection objects){
		this.objects = objects;
	}

	/**
	 * Retorna a fonte do título do gráfico.
	 *
	 * @return String contendo a fonte do título.
	 */
	public String getTitleFont(){
		return titleFont;
	}

	/**
	 * Define a fonte do título do gráfico.
	 *
	 * @param titleFont String contendo a fonte do título.
	 */
	public void setTitleFont(String titleFont){
		this.titleFont = titleFont;
	}

	/**
	 * Retorna o tamanho da fonte do título do gráfico.
	 *
	 * @return String contendo o tamanho da fonte do título.
	 */
	public Integer getTitleFontSize(){
		return titleFontSize;
	}

	/**
	 * Define o tamanho da fonte do título do gráfico.
	 *
	 * @param titleFontSize String contendo o tamanho da fonte do título.
	 */
	public void setTitleFontSize(Integer titleFontSize){
		this.titleFontSize = titleFontSize;
	}

	/**
	 * Retorna a fonte dos textos do gráfico.
	 *
	 * @return String contendo a fonte dos textos.
	 */
	public String getFont(){
		return font;
	}

	/**
	 * Define a fonte dos textos do gráfico.
	 *
	 * @param font String contendo a fonte dos textos.
	 */
	public void setFont(String font){
		this.font = font;
	}

	/**
	 * Retorna o tamanho da fonte dos textos do grafico.
	 *
	 * @return Valor inteiro contendo o tamanho da fonte.
	 */
	public Integer getFontSize(){
		return fontSize;
	}

	/**
	 * Define o tamanho da fonte dos textos do grafico.
	 *
	 * @param fontSize Valor inteiro contendo o tamanho da fonte.
	 */
	public void setFontSize(Integer fontSize){
		this.fontSize = fontSize;
	}

	/**
	 * Retorna a cor dos dados do gráfico.
	 * 
	 * @return Instância contendo a cor dos dados.
	 */
	public Color getColor(){
		return color;
	}

    /**
     * Define a cor dos dados do gráfico.
     * 
     * @param color Instância contendo a cor dos dados.
     */
	public void setColor(Color color){
		this.color = color;
	}

	/**
	 * Retorna a cor de fundo do gráfico.
	 *  
	 * @return Instância contendo a cor de fundo.
	 */
	public Color getGraphBackgroundColor(){
		return graphBackgroundColor;
	}

    /**
     * Define a cor de fundo do gráfico.
     *  
     * @param graphBackgroundColor Instância contendo a cor de fundo.
     */
	public void setGraphBackgroundColor(Color graphBackgroundColor){
		this.graphBackgroundColor = graphBackgroundColor;
	}
}