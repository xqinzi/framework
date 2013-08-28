package br.com.concepting.framework.ui.taglibs;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import br.com.concepting.framework.controller.form.BaseActionForm;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.util.types.ScopeType;

/**
 * Classe que define o componente visual para uma coluna em uma tabela de dados.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class GridColumnTag extends BaseOptionsPropertyTag{
	private String                   imageWidth        = "";
	private String                   imageHeight       = "";
    private Boolean                  isImage           = false;
	private Boolean                  isEditable        = null;
    private String                   link              = "";
    private String                   linkStyleClass    = "";
    private String                   linkStyle         = "";
	private Integer                  size              = 0;
	private Integer                  rows              = 0;
	private Integer                  columns           = 0;
	private Boolean                  hasMultipleLines  = null;
	private List<GridColumnStateTag> columnStatesTags  = null;

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
	 * Adiciona uma característica para a coluna.
	 * 
	 * @param columnStateTag Instância contendo as características da coluna.
	 */
	public void addColumnStateTag(GridColumnStateTag columnStateTag){
		if(columnStatesTags == null)
			columnStatesTags = new LinkedList<GridColumnStateTag>();
		
		columnStatesTags.add(columnStateTag);
	}
	
	/**
	 * Retorna uma lista de características para a coluna.
	 * 
	 * @return Lista contendo as características da coluna.
	 */
	protected List<GridColumnStateTag> getColumnStatesTags(){
		return columnStatesTags;
	}
	
	/**
	 * Define uma lista de características para a coluna.
	 * 
	 * @param columnStatesTags Lista contendo as características da coluna.
	 */
	protected void setColumnStatesTags(List<GridColumnStateTag> columnStatesTags){
		this.columnStatesTags = columnStatesTags;
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
	 * @see br.com.concepting.framework.ui.taglibs.BasePropertyTag#initialize()
	 */
	protected void initialize() throws Throwable{
        String          resourceId = getResourceId();
		GridPropertyTag gridTag    = (GridPropertyTag)findAncestorWithClass(this, GridPropertyTag.class);
		
		if(gridTag != null){
		    setActionFormTag(gridTag.getActionFormTag());

            String name = getName();
            
            if(name.length() > 0){
    		    GridColumnGroupTag columnGroupTag = (GridColumnGroupTag)findAncestorWithClass(this, GridColumnGroupTag.class);
    				
    			if(resourceId.length() == 0)
    				if(columnGroupTag != null)
    					resourceId = columnGroupTag.getResourceId();
    				
                String        resourceKey = getResourceKey();
                StringBuilder propertyId  = new StringBuilder();
                
                if(resourceKey.length() == 0){
                    propertyId.append(".");
                    propertyId.append(name);
                    
                    name = gridTag.getName();
                    
                    propertyId.insert(0, name);
                    
                    resourceKey = propertyId.toString();
                    
                    setResourceKey(resourceKey);
                }
                
                ScopeType scopeType = gridTag.getDataScopeType();
                
                if(scopeType == ScopeType.FORM || scopeType == ScopeType.MODEL){
                    String         data         = gridTag.getData();
                    Integer        pos          = data.indexOf(".");
                    String         actionForm   = data.substring(0, pos);
                    BaseActionForm form         = systemController.findAttribute(actionForm, ScopeType.SESSION);
                    PropertyInfo   propertyInfo = PropertyUtil.getPropertyInfo(form, data.substring(pos + 1));
                    
                    if(propertyInfo != null){
                        Class     collectionItemsClass = propertyInfo.getCollectionItemsClass();
                        ModelInfo modelInfo            = ModelUtil.getModelInfo(collectionItemsClass);
                        
                        if(modelInfo != null)
                            setPropertyInfo(modelInfo.getPropertyInfo(name));
                    }
                }
                else{
                    Collection dataValues = gridTag.getDataValues();
    
    				if(dataValues != null && dataValues.size() > 0){
    					Object dataValue = dataValues.iterator().next();
    					
    					if(dataValue != null){
    					    Class     modelClass = dataValue.getClass();
    					    ModelInfo modelInfo  = ModelUtil.getModelInfo(modelClass);
    					    
    					    if(modelInfo != null)
    					        setPropertyInfo(modelInfo.getPropertyInfo(name));
    					}
    				}
                }
            }
            
            if(resourceId.length() == 0)
                resourceId = gridTag.getResourceId();
				
			super.initialize();
		}
	}
	
	/**
	 * @see br.com.concepting.framework.ui.taglibs.BaseActionFormElementTag#render()
	 */
	protected void render() throws Throwable{
		GridPropertyTag gridTag = (GridPropertyTag)findAncestorWithClass(this, GridPropertyTag.class);
		
		if(gridTag != null){
			if(!(this instanceof GridColumnGroupTag) && isRendered()){
                GridColumnTag      columnTag      = (GridColumnTag)this.clone();
                GridColumnGroupTag columnGroupTag = (GridColumnGroupTag)findAncestorWithClass(this, GridColumnGroupTag.class);
			    
		        if(columnGroupTag != null){
		            columnGroupTag = (GridColumnGroupTag)columnGroupTag.clone();
		            
		            columnTag.setParent(columnGroupTag);
		        }

                gridTag.addColumnTag(columnTag);
			}
		}
	}
	
	/**
	 * @see br.com.concepting.framework.ui.taglibs.BasePropertyTag#clearAttributes()
	 */
	protected void clearAttributes(){
		super.clearAttributes();
		
		setAlignment("");
		setWidth("");
		setIsImage(false);
		setImageWidth("");
		setImageHeight("");
		setLink("");
		setLinkStyleClass("");
		setLinkStyle("");
		setSize(0);
		setIsEditable(null);
		setFocus(false);
		setHasMultipleLines(null);
		setRows(0);
		setColumns(0);
		setColumnStatesTags(null);
		setValue(null);
	}
}