package br.com.concepting.framework.web.taglibs;

import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.util.ByteUtil;
import br.com.concepting.framework.util.ImageUtil;
import br.com.concepting.framework.web.form.BaseActionForm;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;
import br.com.concepting.framework.web.types.ContentMimeType;
import br.com.concepting.framework.web.types.ScopeType;

public class DownloadBoxPropertyTag extends BasePropertyTag{
    private String fileNameProperty    = "";
    private String contentTypeProperty = "";
    private String iconWidth           = "";
    private String iconHeight          = "";
    
    public String getIconWidth(){
        return iconWidth;
    }

    public void setIconWidth(String iconWidth){
        this.iconWidth = iconWidth;
    }

    public String getIconHeight(){
        return iconHeight;
    }

    public void setIconHeight(String iconHeight){
        this.iconHeight = iconHeight;
    }

    public String getFileNameProperty(){
        return fileNameProperty;
    }

    public void setFileNameProperty(String fileNameProperty){
        this.fileNameProperty = fileNameProperty;
    }

    public String getContentTypeProperty(){
        return contentTypeProperty;
    }

    public void setContentTypeProperty(String contentTypeProperty){
        this.contentTypeProperty = contentTypeProperty;
    }
    
    protected void initialize() throws Throwable{
        super.initialize();
        
        String styleClass = getStyleClass();
        
        if(styleClass.length() == 0)
            setStyleClass(TaglibConstants.DEFAULT_DOWNLOAD_BOX_STYLE_CLASS);
    }
    
    private void renderDownloadIcon(String fileName, ContentMimeType contentMimeType) throws Throwable{
        print("<div class=\"");
        print(contentMimeType.getExtension().substring(1).toLowerCase());
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
        print(contentMimeType.getKey());
        print("&contentData=");
        print(getActionForm());
        print((isForSearch() ? ".searchModel." : ".model."));
        print(getName());
        println("', 'downloadWindow');\"></div>");
    }

    protected void renderBody() throws Throwable{
        String       name         = getName();
        PropertyInfo propertyInfo = getPropertyInfo();
        
        if(propertyInfo != null){
            print("<table class=\"");
            print(getStyleClass());
            println("\">");
            println("<tr>");
            println("<td width=\"1\" class=\"label\">");
    
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
                            fileName = (String)PropertyUtil.getProperty(model, fileNameProperty);
                        
                        ContentMimeType contentMimeType         = null;
                        PropertyInfo    contentTypePropertyInfo = modelInfo.getPropertyInfo(contentTypeProperty);
                        
                        if(contentTypePropertyInfo != null){
                            try{
                                contentMimeType = ContentMimeType.toContentMimeType((String)PropertyUtil.getProperty(model, contentTypeProperty));
                            }
                            catch(Throwable e){
                            }
                        }
                        else{
                            try{
                                contentMimeType = ImageUtil.getImageFormat(content);
                            }
                            catch(Throwable e){
                            }
                        }
                        
                        if(contentMimeType == null)
                            contentMimeType = ContentMimeType.BINARY;
                        
                        if(fileNamePropertyInfo != null)
                            renderDownloadIcon(fileName, contentMimeType);
                        else{
                            if(contentMimeType == ContentMimeType.GIF || contentMimeType == ContentMimeType.JPEG || contentMimeType == ContentMimeType.PNG){
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
                                renderDownloadIcon(fileName, contentMimeType);
                        }
                        
                        println("</td>");
                        println("<td class=\"label\">");

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
    
    protected void clearAttributes(){
        super.clearAttributes();
        
        setFileNameProperty("");
        setContentTypeProperty("");
        setIconWidth("");
        setIconHeight("");
    }
}
