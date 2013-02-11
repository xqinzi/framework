package br.com.concepting.framework.web.taglibs;

import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.util.ByteUtil;
import br.com.concepting.framework.util.ImageUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.web.form.BaseActionForm;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;
import br.com.concepting.framework.web.types.ContentType;
import br.com.concepting.framework.web.types.ScopeType;

/**
 * Classe que define o componente visual para uma caixa de download..
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class DownloadBoxPropertyTag extends BasePropertyTag{
    private String fileNameProperty    = "";
    private String contentTypeProperty = "";
    private String iconWidth           = "";
    private String iconHeight          = "";
    
    /**
     * Retorna a largura para o ícone do download.
     * 
     * @return String contendo a largura para o ícone.
     */
    public String getIconWidth(){
        return iconWidth;
    }

    /**
     * Define a largura para o ícone do download.
     * 
     * @param iconWidth String contendo a largura para o ícone.
     */
    public void setIconWidth(String iconWidth){
        this.iconWidth = iconWidth;
    }

    /**
     * Retorna a altera para o ícone do download.
     * 
     * @return String contendo a altura para o ícone.
     */
    public String getIconHeight(){
        return iconHeight;
    }

    /**
     * Define a altura para o ícone do download.
     * 
     * @param iconHeight String contendo a altura para o ícone.
     */
    public void setIconHeight(String iconHeight){
        this.iconHeight = iconHeight;
    }

    /**
     * Retorna o identificador da propriedade do modelo de dados que
     * contém o nome do arquivo para download.
     * 
     * @return String contendo o identificador da propriedade do 
     * modelo de dados. 
     */
    public String getFileNameProperty(){
        return fileNameProperty;
    }

    /**
     * Define o identificador da propriedade do modelo de dados que
     * contém o nome do arquivo para download.
     * 
     * @param filenameProperty String contendo o identificador da propriedade do 
     * modelo de dados. 
     */
    public void setFileNameProperty(String fileNameProperty){
        this.fileNameProperty = fileNameProperty;
    }

    /**
     * Retorna o identificador da propriedade do modelo de dados que
     * contém o formato do arquivo para download.
     * 
     * @return String contendo o identificador da propriedade do 
     * modelo de dados. 
     */
    public String getContentTypeProperty(){
        return contentTypeProperty;
    }

    /**
     * Define o identificador da propriedade do modelo de dados que
     * contém o formato do arquivo para download.
     * 
     * @param contentTypeProperty String contendo o identificador da propriedade do 
     * modelo de dados. 
     */
    public void setContentTypeProperty(String contentTypeProperty){
        this.contentTypeProperty = contentTypeProperty;
    }
    
    /**
     * @see br.com.concepting.framework.web.taglibs.BasePropertyTag#initialize()
     */
    protected void initialize() throws Throwable{
        super.initialize();
        
        if(getStyleClass().length() == 0)
            setStyleClass(TaglibConstants.DEFAULT_DOWNLOAD_BOX_STYLE_CLASS);
    }
    
    /**
     * Renderiza o ícone para download.
     * 
     * @param fileName String contendo o nome do arquivo
     * @param contentType Constante que define o formato do arquivo.
     * @throws Throwable
     */
    private void renderDownloadIcon(String fileName, ContentType contentType) throws Throwable{
        print("<div class=\"");
        print(contentType.getExtension().substring(1).toLowerCase());
        print("Icon\"");
        
        if(iconWidth.length() > 0 || iconHeight.length() > 0){
            print(" style=\"");
            
            if(iconWidth.length() > 0){
                print("width: ");
                print(iconWidth);
                
                if(iconWidth.endsWith(";"))
                    print(";");
            }

            if(iconHeight.length() > 0){
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
            print("contentId=");
            print(fileName);
            print("&");
        }
        
        print("contentType=");
        print(contentType.getMimeType());
        print("&contentData=");
        print(getActionForm());
        print((isForSearch() ? ".searchModel." : ".model."));
        print(getName());
        println("', 'downloadWindow');\"></div>");
    }

    /**
     * @see br.com.concepting.framework.web.taglibs.BasePropertyTag#renderBody()
     */
    protected void renderBody() throws Throwable{
        String       name         = getName();
        PropertyInfo propertyInfo = getPropertyInfo();
        
        if(propertyInfo != null){
            print("<table class=\"");
            print(getStyleClass());
            println("\">");
            println("<tr>");
            print("<td class=\"");
            print(TaglibConstants.DEFAULT_LABEL_STYLE_CLASS);
            println("\" width=\"1\">");
    
            String         actionFormName = getActionForm();
            BaseActionForm actionForm     = systemController.findAttribute(actionFormName, ScopeType.SESSION);
            BaseModel      model          = (actionForm != null ? actionForm.getModel() : null);
            
            if(model != null){
                ModelInfo modelInfo = ModelUtil.getModelInfo(model.getClass());
                
                if(modelInfo != null){
                    byte content[] = (byte[])getValue();

                    if(content != null){
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
                                imageTag.setActionForm(actionFormName);
                                imageTag.setPropertyInfo(propertyInfo);
                                imageTag.setName(name);
                                imageTag.setValue(getValue());
                                imageTag.setShowLabel(false);
                                imageTag.setWidth(iconWidth);
                                imageTag.setHeight(iconHeight);
                                imageTag.doStartTag();
                                imageTag.doEndTag();
                            }
                            else
                                renderDownloadIcon(fileName, contentType);
                        }
                        
                        println("</td>");
                        
                        print("<td class=\"");
                        print(TaglibConstants.DEFAULT_LABEL_STYLE_CLASS);
                        println("\">");

                        if(fileName.length() > 0){
                            println(fileName);
                            println("<br/>");
                        }
                        
                        println(ByteUtil.formatBytes(new Long(content.length), systemController.getCurrentLanguage()));
                        
                        println("</td>");
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
     * @see br.com.concepting.framework.web.taglibs.BasePropertyTag#clearAttributes()
     */
    protected void clearAttributes(){
        super.clearAttributes();
        
        setFileNameProperty("");
        setContentTypeProperty("");
        setIconWidth("");
        setIconHeight("");
    }
}
