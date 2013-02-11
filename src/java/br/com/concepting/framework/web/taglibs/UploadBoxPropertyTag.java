package br.com.concepting.framework.web.taglibs;

import br.com.concepting.framework.util.constants.AttributeConstants;
import br.com.concepting.framework.web.action.types.ActionType;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;

/**
 * Classe que define o componente visual para uma caixa de upload.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class UploadBoxPropertyTag extends DownloadBoxPropertyTag{
    /**
     * @see br.com.concepting.framework.web.taglibs.DownloadBoxPropertyTag#renderBody()
     */
    protected void renderBody() throws Throwable{
        String        actionForm = getActionForm();
        String        name       = getName();
        StringBuilder tagId      = new StringBuilder();
        
        tagId.append(name);
        tagId.append(".");
        tagId.append(AttributeConstants.UPLOAD_DATA_PROPERTY_KEY);

        HiddenPropertyTag uploadDataPropertyTag = new HiddenPropertyTag();
        
        uploadDataPropertyTag.setPageContext(pageContext);
        uploadDataPropertyTag.setId(tagId.toString());
        uploadDataPropertyTag.setName(AttributeConstants.UPLOAD_DATA_PROPERTY_KEY);
        uploadDataPropertyTag.setValue(name);
        uploadDataPropertyTag.doStartTag();
        uploadDataPropertyTag.doEndTag();
        
        tagId.delete(0, tagId.length());
        tagId.append(name);
        tagId.append(".");
        tagId.append(AttributeConstants.UPLOAD_FileName_TYPE_PROPERTY_KEY);

        HiddenPropertyTag uploadFileNamePropertyTag = new HiddenPropertyTag();
        
        uploadFileNamePropertyTag.setPageContext(pageContext);
        uploadFileNamePropertyTag.setId(tagId.toString());
        uploadFileNamePropertyTag.setName(AttributeConstants.UPLOAD_FileName_TYPE_PROPERTY_KEY);
        uploadFileNamePropertyTag.setValue(getFileNameProperty());
        uploadFileNamePropertyTag.doStartTag();
        uploadFileNamePropertyTag.doEndTag();

        tagId.delete(0, tagId.length());
        tagId.append(name);
        tagId.append(".");
        tagId.append(AttributeConstants.UPLOAD_CONTENT_TYPE_PROPERTY_KEY);

        HiddenPropertyTag contentTypePropertyTag = new HiddenPropertyTag();
        
        contentTypePropertyTag.setPageContext(pageContext);
        contentTypePropertyTag.setId(tagId.toString());
        contentTypePropertyTag.setName(AttributeConstants.UPLOAD_CONTENT_TYPE_PROPERTY_KEY);
        contentTypePropertyTag.setValue(getContentTypeProperty());
        contentTypePropertyTag.doStartTag();
        contentTypePropertyTag.doEndTag();

        StringBuilder onChange = new StringBuilder();
        
        onChange.append("showLoadingBox(");
        onChange.append("document.");
        onChange.append(actionForm);
        onChange.append("); document.");
        onChange.append(actionForm);
        onChange.append(".enctype = 'multipart/form-data'; document.");
        onChange.append(actionForm);
        onChange.append(".action.value = '");
        onChange.append(ActionType.UPLOAD.getMethod());
        onChange.append("'; document.");
        onChange.append(actionForm);
        onChange.append(".submit();");
        
        tagId.delete(0, tagId.length());
        tagId.append(name);
        tagId.append(".");
        tagId.append(AttributeConstants.UPLOAD_FILE_KEY);

        print("<input type=\"FILE\" id=\"");
        print(tagId);
        print("\" name=\"");
        print(AttributeConstants.UPLOAD_FILE_KEY);
        print("\" style=\"display: NONE;\" onChange=\"");
        print(onChange);
        println("\">");

        print("<table class=\"");
        print(TaglibConstants.DEFAULT_UPLOAD_BOX_STYLE_CLASS);
        println("\">");
        println("<tr>");
        println("<td>");
        
        super.renderBody();
        
        println("</td>");
        println("<td>");
        
        StringBuilder onClick = new StringBuilder();
        
        onClick.append("document.getElementById('");
        onClick.append(name);
        onClick.append(".");
        onClick.append(AttributeConstants.UPLOAD_FILE_KEY);
        onClick.append("').click();");

        UploadButtonTag uploadButtonTag = new UploadButtonTag();
        
        uploadButtonTag.setPageContext(pageContext);
        uploadButtonTag.setOnClick(onClick.toString());
        uploadButtonTag.doStartTag();
        uploadButtonTag.doEndTag();
        
        println("</td>");
        println("</tr>");
        println("</table>");
    }
}