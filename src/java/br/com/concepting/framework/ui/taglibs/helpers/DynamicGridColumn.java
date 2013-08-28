package br.com.concepting.framework.ui.taglibs.helpers;

/**
 * Classe auxiliar que define as propriedades de uma coluna para o componente
 * visual de tabela de dados dinâmica.
 * 
 * @author fvilarinho
 * @since 3.0
 */ 
public class DynamicGridColumn{
    private String  name             = "";
    private String  label            = "";
    private String  resourceId       = "";
    private String  resourceKey      = "";
    private String  width            = "";
    private String  height           = "";
    private Boolean focus            = false;
    private String  link             = "";
    private String  linkStyle        = "";
    private String  linkStyleClass   = "";
    private String  style            = "";
    private String  styleClass       = "";
    private Integer columns          = 0;
    private Integer rows             = 0;
    private Integer size             = 0;
    private Boolean isEditable       = false;
    private Boolean hasMultipleLines = false;
    private Boolean isImage          = false;
    private String  imageWidth       = "";
    private String  imageHeight      = "";
    
    /**
     * Indica se o foco deve ser aplicado ao componente.
     *
     * @return True/False.
     */
    public Boolean focus(){
        return focus;
    }
    
    /**
     * Define se o foco deve ser aplicado ao componente.
     *
     * @param focus True/False.
     */
    public void setFocus(Boolean focus){
        this.focus = focus;
    }

    /**
     * Retorna o número máximo de caracteres a serem exibidos.
     * 
     * @return Valor inteiro contendo o número máximo de caracteres.
     */
    public Integer getSize(){
        return size;
    }
    
    /**
     * Define o número máximo de caracteres a serem exibidos.
     * 
     * @param size Valor inteiro contendo o número máximo de caracteres.
     */
    public void setSize(Integer size){
        this.size = size;
    }
    
    /**
     * Retorna o identificador do componente.
     * 
     * @return String contendo identificador.
     */
    public String getName(){
        return name;
    }

    /**
     * Define o identificador do componente.
     * 
     * @param name String contendo identificador.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Retorna o identificador do arquivo de recursos que armazena as propriedades do 
     * componente.
     * 
     * @return String contendo o identificador do arquivo de recursos.
     */
    public String getResourceId(){
        return resourceId;
    }
     
    /**
     * Retorna o identificador da propriedade armazenada no arquivo de recursos.
     *
     * @return String contendo o identificador da propriedade.
     */
    public String getResourceKey(){
        return resourceKey;
    }

    /**
     * Define o identificador do arquivo de recursos que armazena as propriedades do 
     * componente.
     * 
     * @param resourceId String contendo o identificador do arquivo de
     *                   recursos.
     */
    public void setResourceId(String resourceId){
        this.resourceId = resourceId;
    }
    
    /**
     * Define o identificador da propriedade armazenada no arquivo de recursos.
     *
     * @param resourceKey String contendo o identificador da propriedade.
     */
    public void setResourceKey(String resourceKey){
        this.resourceKey = resourceKey;
    }

    /**
     * Retorna o estilo CSS do componente visual.
     * 
     * @return String contendo o estilo CSS.
     */
    public String getStyleClass(){
        return styleClass;
    }

    /**
     * Define o estilo CSS do componente visual.
     * 
     * @param styleClass String contendo o estilo CSS.
     */
    public void setStyleClass(String styleClass){
        this.styleClass = styleClass;
    }

    /**
     * Retorna o estilo CSS do componente visual.
     * 
     * @return String contendo o estilo CSS.
     */
    public String getStyle(){
        return style;
    }

    /**
     * Define o estilo CSS do componente visual.
     * 
     * @param style String contendo o estilo CSS.
     */
    public void setStyle(String style){
        this.style = style;
    }
    
    /**
     * Retorna o label do componente.
     * 
     * @return String contendo o label.
     */
    public String getLabel(){
        return label;
    }

    /**
     * Define o label do componente.
     * 
     * @param label String contendo o label.
     */
    public void setLabel(String label){
        this.label = label;
    }

    /**
     * Retorna o tamanho do componente. 
     *
     * @return String contendo a definição do tamanho do componente.
     */
    public String getWidth(){
        return width;
    }

    /**
     * Define o tamanho do componente. 
     *
     * @param width String contendo a definição do tamanho do componente.
     */
    public void setWidth(String width){
        this.width = width;
    }

    /**
     * Retorna a altura do componente. 
     *
     * @return String contendo a definição da altura do componente.
     */
    public String getHeight(){
        return height;
    }

    /**
     * Define a altura do componente. 
     *
     * @param height String contendo a definição da altura do componente.
     */
    public void setHeight(String height){
        this.height = height;
    }
    
    /**
     * Retorna o link vinculado ao conteúdo da coluna.
     * 
     * @return String contendo o link.
     */
    public String getLink(){
        return link;
    }
    
    /**
     * Define o link vinculado ao conteúdo da coluna.
     * 
     * @param link String contendo o link.
     */
    public void setLink(String link){
        this.link = link;
    }
    
    /**
     * Retorna o estilo CSS do link da coluna.
     * 
     * @return String contendo o estilo CSS.
     */
    public String getLinkStyleClass(){
        return linkStyleClass;
    }
    
    /**
     * Define o estilo CSS do link da coluna.
     * 
     * @param linkStyleClass String contendo o estilo CSS.
     */
    public void setLinkStyleClass(String linkStyleClass){
        this.linkStyleClass = linkStyleClass;
    }
    
    /**
     * Retorna o estilo CSS do link da coluna.
     * 
     * @return String contendo o estilo CSS.
     */
    public String getLinkStyle(){
        return linkStyle;
    }
    
    /**
     * Define o estilo CSS do link da coluna.
     * 
     * @param linkStyle String contendo o estilo CSS.
     */
    public void setLinkStyle(String linkStyle){
        this.linkStyle = linkStyle;
    }
    
    /**
     * Retorna a quantidade de caracteres por coluna quando um texto for editável.
     * 
     * @return Valor inteiro contendo a quantidade de caracteres por coluna.
     */
    public Integer getColumns(){
        return columns;
    }
    
    /**
     * Define a quantidade de caracteres por coluna quando um texto for editável.
     * 
     * @param columns Valor inteiro contendo a quantidade de caracteres por coluna.
     */
    public void setColumns(Integer columns){
        this.columns = columns;
    }
    
    /**
     * Retorna a quantidade de caracteres por linha quando um texto for editável.
     * 
     * @return Valor inteiro contendo a quantidade de caracteres por linha.
     */
    public Integer getRows(){
        return rows;
    }
    
    /**
     * Define a quantidade de caracteres por linha quando um texto for editável.
     * 
     * @param rows Valor inteiro contendo a quantidade de caracteres por linha.
     */
    public void setRows(Integer rows){
        this.rows = rows;
    }
    
    /**
     * Indica se o conteúdo da coluna é editável.
     * 
     * @return True/False.
     */
    public Boolean isEditable(){
        return isEditable;
    }
    
    /**
     * Indica se o conteúdo da coluna é editável.
     * 
     * @return True/False.
     */
    public Boolean getIsEditable(){
        return isEditable();
    }
    
    /**
     * Define se o conteúdo da coluna é editável.
     * 
     * @param isEditable True/False.
     */
    public void setIsEditable(Boolean isEditable){
        this.isEditable = isEditable;
    }
    
    /**
     * Indica se o conteúdo da coluna deve ser exibido em múltiplas linhas.
     * 
     * @return True/False.
     */
    public Boolean hasMultipleLines(){
        return hasMultipleLines;
    }
    
    /**
     * Indica se o conteúdo da coluna deve ser exibido em múltiplas linhas.
     * 
     * @return True/False.
     */
    public Boolean getHasMultipleLines(){
        return hasMultipleLines();
    }
    
    /**
     * Define se o conteúdo da coluna deve ser exibido em múltiplas linhas.
     * 
     * @param hasMultipleLines True/False.
     */
    public void setHasMultipleLines(Boolean hasMultipleLines){
        this.hasMultipleLines = hasMultipleLines;
    }
    
    /**
     * Indica se o conteúdo da coluna é uma imagem.
     * 
     * @return True/False.
     */
    public Boolean isImage(){
        return isImage;
    }
    
    /**
     * Indica se o conteúdo da coluna é uma imagem.
     * 
     * @return True/False.
     */
    public Boolean getIsImage(){
        return isImage();
    }

    /**
     * Define se o conteúdo da coluna é uma imagem.
     * 
     * @param isImage True/False.
     */
    public void setIsImage(Boolean isImage){
        this.isImage = isImage;
    }

    /**
     * Retorna a largura da imagem.
     * 
     * @return String contendo a largura da imagem.
     */
    public String getImageWidth(){
        return imageWidth;
    }
    
    /**
     * Define a largura da imagem.
     * 
     * @param imageWidth String contendo a largura da imagem.
     */
    public void setImageWidth(String imageWidth){
        this.imageWidth = imageWidth;
    }
    
    /**
     * Retorna a altura da imagem.
     * 
     * @return String contendo a altura da imagem.
     */
    public String getImageHeight(){
        return imageHeight;
    }
    
    /**
     * Define a altura da imagem.
     * 
     * @param imageHeight String contendo a altura da imagem.
     */
    public void setImageHeight(String imageHeight){
        this.imageHeight = imageHeight;
    }
}