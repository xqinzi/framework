package br.com.concepting.framework.util.helpers;

import java.awt.Color;
import java.util.Collection;
import java.util.LinkedList;

import br.com.concepting.framework.web.types.ContentType;

/**
 * Classe que define as caracter�sticas de um gr�fico baseado em um arquivo RRD.
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
	 * Indica se deve usar o framework RRD4J para a gera��o do gr�fico.
	 * 
	 * @return True/False.
	 */
    public Boolean useRrd4j(){
        return useRrd4j;
    }

    /**
     * Indica se deve usar o framework RRD4J para a gera��o do gr�fico.
     * 
     * @return True/False.
     */
	public Boolean getUseRrd4j(){
    	return useRrd4j();
    }

    /**
     * Define se deve usar o framework RRD4J para a gera��o do gr�fico.
     * 
     * @param useRrd4j True/False.
     */
	public void setUseRrd4j(Boolean useRrd4j){
    	this.useRrd4j = useRrd4j;
    }

	/**
	 * Adiciona um datasource.
	 * 
	 * @param datasource Inst�ncia contendo as propriedades do datasource.
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
	 * Retorna a largura do gr�fico.
	 * 
	 * @return Valor inteiro contendo a largura do gr�fico.
	 */
	public Integer getWidth(){
		return width;
	}

	/**
	 * Define a largura do gr�fico.
	 * 
	 * @param width Valor inteiro contendo a largura do gr�fico.
	 */
	public void setWidth(Integer width){
		this.width = width;
	}

	/**
	 * Retorna a altura do gr�fico.
	 * 
	 * @return Valor inteiro contendo a altura do gr�fico.
	 */
	public Integer getHeight(){
		return height;
	}

	/**
	 * Define a altura do gr�fico.
	 * 
	 * @param height Valor inteiro contendo a altura do gr�fico.
	 */
	public void setHeight(Integer height){
		this.height = height;
	}

	/**
	 * Retorna o t�tulo do gr�fico.
	 * 
	 * @return String contendo o t�tulo do gr�fico.
	 */
	public String getTitle(){
		return title;
	}

	/**
	 * Define o t�tulo do gr�fico.
	 * 
	 * @param title String contendo o t�tulo do gr�fico.
	 */
	public void setTitle(String title){
		this.title = title;
	}

	/**
	 * Retorna o valor m�ximo a ser plotado no gr�fico.
	 * 
	 * @return Valor em ponto flutuante contendo o valor m�ximo.
	 */
	public Double getMaximumValue(){
		return maximumValue;
	}

	/**
	 * Define o valor m�ximo a ser plotado no gr�fico.
	 * 
	 * @param maximumValue Valor em ponto flutuante contendo o valor m�ximo.
	 */
	public void setMaximumValue(Double maximumValue){
		this.maximumValue = maximumValue;
	}

	/**
	 * Retorna o valor m�nimo a ser plotado no gr�fico.
	 * 
	 * @return Valor em ponto flutuante contendo o valor m�nimo.
	 */
	public Double getMinimumValue(){
		return minimumValue;
	}

	/**
	 * Define o valor m�nimo a ser plotado no gr�fico.
	 * 
	 * @param minimumValue Valor em ponto flutuante contendo o valor m�nimo.
	 */
	public void setMinimumValue(Double minimumValue){
		this.minimumValue = minimumValue;
	}

	/**
	 * Retorna o t�tulo do eixo X.
	 *
	 * @return String contendo o t�tulo do eixo X.
	 */
	public String getXAxisLabel(){
		return xAxisLabel;
	}

	/**
	 * Define o t�tulo do eixo X.
	 *
	 * @param axisLabel String contendo o t�tulo do eixo X.
	 */
	public void setXAxisLabel(String axisLabel){
		xAxisLabel = axisLabel;
	}

	/**
	 * Retorna o t�tulo do eixo Y.
	 *
	 * @return String contendo o t�tulo do eixo Y.
	 */
	public String getYAxisLabel(){
		return yAxisLabel;
	}

	/**
	 * Define o t�tulo do eixo Y.
	 *
	 * @param axisLabel String contendo o t�tulo do eixo Y.
	 */
	public void setYAxisLabel(String axisLabel){
		yAxisLabel = axisLabel;
	}

	/**
	 * Retorna a cor de fundo da �rea extra do gr�fico.
	 *
	 * @return String contendo a cor de fundo.
	 */
	public Color getBackgroundColor(){
		return backgroundColor;
	}

	/**
	 * Define a cor de fundo da �rea extra do gr�fico.
	 *
	 * @param backgroundColor String contendo a cor de fundo.
	 */
	public void setBackgroundColor(Color backgroundColor){
		this.backgroundColor = backgroundColor;
	}

	/**
	 * Retorna a cor das linhas do grid do gr�fico.
	 *
	 * @return String contendo a cor das linhas do grid.
	 */
	public Color getGridLinesColor(){
		return gridLinesColor;
	}

	/**
	 * Define a cor das linhas do grid do gr�fico.
	 *
	 * @param gridLinesColor String contendo a cor das linhas do grid.
	 */
	public void setGridLinesColor(Color gridLinesColor){
		this.gridLinesColor = gridLinesColor;
	}

	/**
	 * Retorna o t�tulo do eixo Y.
	 *
	 * @return String contendo o t�tulo do eixo Y.
	 */
	public String getVerticalLabel(){
		return verticalLabel;
	}

	/**
	 * Define o t�tulo do eixo Y.
	 *
	 * @param verticalLabel String contendo o t�tulo do eixo Y.
	 */
	public void setVerticalLabel(String verticalLabel){
		this.verticalLabel = verticalLabel;
	}

	/**
	 * Retorna o formato da imagem do gr�fico.
	 *
	 * @return Inst�ncia contendo o formato da imagem do gr�fico.
	 */
	public ContentType getContentType(){
		return contentType;
	}

	/**
	 * Define o formato da imagem do gr�fico.
	 *
	 * @param contentType Inst�ncia contendo o formato da imagem do gr�fico.
	 */
	public void setContentType(ContentType contentType){
		this.contentType = contentType;
	}

	/**
	 * Retorna a lista de objetos do gr�fico.
	 *
	 * @return Lista contendo os objetos do gr�fico.
	 */
	public Collection getObjects(){
		return objects;
	}

	/**
	 * Define a lista de objetos do gr�fico.
	 *
	 * @param objects Lista contendo os objetos do gr�fico.
	 */
	public void setObjects(Collection objects){
		this.objects = objects;
	}

	/**
	 * Retorna a fonte do t�tulo do gr�fico.
	 *
	 * @return String contendo a fonte do t�tulo.
	 */
	public String getTitleFont(){
		return titleFont;
	}

	/**
	 * Define a fonte do t�tulo do gr�fico.
	 *
	 * @param titleFont String contendo a fonte do t�tulo.
	 */
	public void setTitleFont(String titleFont){
		this.titleFont = titleFont;
	}

	/**
	 * Retorna o tamanho da fonte do t�tulo do gr�fico.
	 *
	 * @return String contendo o tamanho da fonte do t�tulo.
	 */
	public Integer getTitleFontSize(){
		return titleFontSize;
	}

	/**
	 * Define o tamanho da fonte do t�tulo do gr�fico.
	 *
	 * @param titleFontSize String contendo o tamanho da fonte do t�tulo.
	 */
	public void setTitleFontSize(Integer titleFontSize){
		this.titleFontSize = titleFontSize;
	}

	/**
	 * Retorna a fonte dos textos do gr�fico.
	 *
	 * @return String contendo a fonte dos textos.
	 */
	public String getFont(){
		return font;
	}

	/**
	 * Define a fonte dos textos do gr�fico.
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
	 * Retorna a cor dos dados do gr�fico.
	 * 
	 * @return Inst�ncia contendo a cor dos dados.
	 */
	public Color getColor(){
		return color;
	}

    /**
     * Define a cor dos dados do gr�fico.
     * 
     * @param color Inst�ncia contendo a cor dos dados.
     */
	public void setColor(Color color){
		this.color = color;
	}

	/**
	 * Retorna a cor de fundo do gr�fico.
	 *  
	 * @return Inst�ncia contendo a cor de fundo.
	 */
	public Color getGraphBackgroundColor(){
		return graphBackgroundColor;
	}

    /**
     * Define a cor de fundo do gr�fico.
     *  
     * @param graphBackgroundColor Inst�ncia contendo a cor de fundo.
     */
	public void setGraphBackgroundColor(Color graphBackgroundColor){
		this.graphBackgroundColor = graphBackgroundColor;
	}
}