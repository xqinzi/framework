package br.com.concepting.framework.ui.taglibs;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.controller.form.BaseActionForm;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.ui.constants.TaglibConstants;
import br.com.concepting.framework.util.ByteUtil;
import br.com.concepting.framework.util.ImageUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.types.ContentType;
import br.com.concepting.framework.util.types.ScopeType;

/**
 * Classe que define o componente visual downloadBox (caixa de download).
 * 
 * @author fvilarinho
 * @since 3.0
 */ 
public class DownloadBoxPropertyTag extends BasePropertyTag{
    private String  fileNameProperty    = "";
    private String  contentTypeProperty = "";
    private Boolean showFileName        = true;
    private Boolean showContentSize     = true;
    private String  iconWidth           = "";
    private String  iconHeight          = "";
    
    /**
     * Indica se o nome do arquivo deve ser exibido.
     * 
     * @return True/False.
     */
    public Boolean isShowFileName(){
        return showFileName;
    }

    /**
     * Indica se o nome do arquivo deve ser exibido.
     * 
     * @return True/False.
     */
    public Boolean getShowFileName(){
        return isShowFileName();
    }

    /**
     * Define se o nome do arquivo deve ser exibido.
     * 
     * @param showFileName True/False.
     */
    public void setShowFileName(Boolean showFileName){
        this.showFileName = showFileName;
    }

    /**
     * Indica se a quantidade de bytes do conte�do deve ser exibido.
     * 
     * @return True/False.
     */
    public Boolean isShowContentSize(){
        return showContentSize;
    }

    /**
     * Indica se a quantidade de bytes do conte�do deve ser exibido.
     * 
     * @return True/False.
     */
    public Boolean getShowContentSize(){
        return isShowContentSize();
    }

    /**
     * Define se a quantidade de bytes do conte�do deve ser exibido.
     * 
     * @param showContentSize True/False.
     */
    public void setShowContentSize(Boolean showContentSize){
        this.showContentSize = showContentSize;
    }

    /**
     * Retorna a largura para o �cone do download.
     * 
     * @return String contendo a largura para o �cone.
     */
    public String getIconWidth(){
        return iconWidth;
    }

    /**
     * Define a largura para o �cone do download.
     * 
     * @param iconWidth String contendo a largura para o �cone.
     */
    public void setIconWidth(String iconWidth){
        this.iconWidth = iconWidth;
    }

    /**
     * Retorna a altera para o �cone do download.
     * 
     * @return String contendo a altura para o �cone.
     */
    public String getIconHeight(){
        return iconHeight;
    }

    /**
     * Define a altura para o �cone do download.
     * 
     * @param iconHeight String contendo a altura para o �cone.
     */
    public void setIconHeight(String iconHeight){
        this.iconHeight = iconHeight;
    }

    /**
     * Retorna o identificador da propriedade do modelo de dados que
     * cont�m o nome do arquivo para download.
     * 
     * @return String contendo o identificador da propriedade do 
     * modelo de dados. 
     */
    public String getFileNameProperty(){
        return fileNameProperty;
    }

    /**
     * Define o identificador da propriedade do modelo de dados que
     * cont�m o nome do arquivo para download.
     * 
     * @param fileNameProperty String contendo o identificador da propriedade do 
     * modelo de dados. 
     */
    public void setFileNameProperty(String fileNameProperty){
        this.fileNameProperty = fileNameProperty;
    }

    /**
     * Retorna o identificador da propriedade do modelo de dados que
     * cont�m o formato do arquivo para download.
     * 
     * @return String contendo o identificador da propriedade do 
     * modelo de dados. 
     */
    public String getContentTypeProperty(){
        return contentTypeProperty;
    }

    /**
     * Define o identificador da propriedade do modelo de dados que
     * cont�m o formato do arquivo para download.
     * 
     * @param contentTypeProperty String contendo o identificador da propriedade do 
     * modelo de dados. 
     */
    public void setContentTypeProperty(String contentTypeProperty){
        this.contentTypeProperty = contentTypeProperty;
    }
    
    /**
     * @see br.com.concepting.framework.ui.taglibs.BasePropertyTag#initialize()
     */
    protected void initialize() throws Throwable{
        super.initialize();
        
        if(getStyleClass().length() == 0)
            setStyleClass(TaglibConstants.DEFAULT_DOWNLOAD_BOX_STYLE_CLASS);
    }
    
    /**
     * Renderiza o �cone para download.
     * 
     * @param fileName String contendo o nome do arquivo
     * @param contentType Constante que define o formato do arquivo.
     * @throws Throwable
     */
    private void renderDownloadIcon(String fileName, ContentType contentType) throws Throwable{
        print("<div class=\"");
        print(contentType.getExtension().substring(1).toLowerCase());
        print(StringUtil.capitalize(AttributeConstants.ICON_KEY));
        print("\"");
        
        if(iconWidth.length() > 0 || iconHeight.length() > 0){
            print(" style=\"");
            
            if(iconWidth.length() > 0){
                print("width: ");
                print(iconWidth);
                
                if(iconWidth.endsWith(";"))
                    print(";");
            }

            if(iconHeight.length() > 0){
                if(iconWidth.length() > 0)
                    print(" ");
                
                print("height: ");
                print(iconHeight);
                
                if(iconHeight.endsWith(";"))
                    print(";");
            }

            print("\"");
        }
        
        print(" onClick=\"window.open('");
        print(systemController.getContextPath());
        print("/contentLoaderServlet?");
        
        if(fileName.length() > 0){
            print(AttributeConstants.CONTENT_ID_KEY);
            print("=");
            print(fileName);
            print("&");
        }
        
        print(AttributeConstants.CONTENT_TYPE_KEY);
        print("=");
        print(contentType.getMimeType());
        print("&");
        print(AttributeConstants.CONTENT_DATA_KEY);
        print("=");
        print(getActionFormName());
        print(".");
        print((isForSearch() ? AttributeConstants.SEARCH_MODEL_KEY : AttributeConstants.MODEL_KEY));
        print(".");
        print(getName());
        print("', '_");
        print((int)(Math.random() * 9999));
        print("');\"></div>");
    }
 
    /**
     * @see br.com.concepting.framework.ui.taglibs.BasePropertyTag#renderBody()
     */
    protected void renderBody() throws Throwable{
        String       name         = getName();
        PropertyInfo propertyInfo = getPropertyInfo();
        
        if(propertyInfo != null){
            print("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"");
            print(getStyleClass());
            print("\"");
            
            String style = getStyle();
            
            if(style.length() > 0){
                print(" style=\"");
                print(style);
                print("\"");
            }
            
            println(">");
            println("<tr>");
            print("<td class=\"");
            print(TaglibConstants.DEFAULT_LABEL_STYLE_CLASS);
            println("\" width=\"1\">");
    
            String         actionFormName = getActionFormName();
            BaseActionForm actionForm     = systemController.findAttribute(actionFormName, ScopeType.SESSION);
            BaseModel      model          = (actionForm != null ? actionForm.getModel() : null);
            
            if(model != null){
                ModelInfo modelInfo = ModelUtil.getModelInfo(model.getClass());
                
                if(modelInfo != null){
                    byte content[] = getValue();

                    if(content != null && content.length > 0){
                        String       fileName             = "";
                        PropertyInfo fileNamePropertyInfo = modelInfo.getPropertyInfo(fileNameProperty);
                        
                        if(fileNamePropertyInfo != null)
                            fileName = StringUtil.trim(PropertyUtil.getProperty(model, fileNameProperty));
                        
                        ContentType  contentType             = null;
                        PropertyInfo contentTypePropertyInfo = modelInfo.getPropertyInfo(contentTypeProperty);
                        
                        if(contentTypePropertyInfo != null){
                            try{
                                String value = StringUtil.trim(PropertyUtil.getProperty(model, contentTypeProperty)).toUpperCase();
                                
                                contentType = ContentType.valueOf(value);
                            }
                            catch(Throwable e){
                            }
                        }
                        else{
                            try{
                                contentType = ImageUtil.getImageFormat(content);
                            }
                            catch(Throwable e){
                            }
                        }
                        
                        if(contentType == null)
                            contentType = ContentType.BINARY;
                        
                        if(fileNamePropertyInfo != null)
                            renderDownloadIcon(fileName, contentType);
                        else{
                            if(contentType == ContentType.GIF || contentType == ContentType.JPEG || contentType == ContentType.PNG){
                                ImageTag imageTag = new ImageTag();
                                
                                imageTag.setPageContext(pageContext);
                                imageTag.setActionFormName(actionFormName);
                                imageTag.setPropertyInfo(propertyInfo);
                                imageTag.setName(name);
                                imageTag.setValue(getValue());
                                imageTag.setShowLabel(false);
                                imageTag.setStyleClass(TaglibConstants.DEFAULT_IMAGE_THUMBNAIL_STYLE_CLASS);
                                imageTag.setWidth(iconWidth);
                                imageTag.setHeight(iconHeight);
                                imageTag.doStartTag();
                                imageTag.doEndTag();
                            }
                            else
                                renderDownloadIcon(fileName, contentType);
                        }
                        
                        println("</td>");
                        
                        if(showFileName || showContentSize){
                            print("<td class=\"");
                            print(TaglibConstants.DEFAULT_LABEL_STYLE_CLASS);
                            println("\">");
    
                            if(fileName.length() > 0 && showFileName){
                                println(fileName);
                                println("<br/>");
                            }

                            if(showContentSize)
                                println(ByteUtil.formatBytes(new Long(content.length), systemController.getCurrentLanguage()));
                            
                            println("</td>");
                        }
                    }
                    else
                        println(getDataIsEmptyMessage());
                }
                else
                    println(getInvalidPropertyMessage());
            }
            else
                println(getInvalidPropertyMessage());
            
            println("</td>");
            println("</tr>");
            println("</table>");
        }
        else
            super.renderBody();
    }
    
    /**
     * @see br.com.concepting.framework.ui.taglibs.BasePropertyTag#clearAttributes()
     */
    protected void clearAttributes(){
        super.clearAttributes();
        
        setFileNameProperty("");
        setContentTypeProperty("");
        setIconWidth("");
        setIconHeight("");
    }
}
