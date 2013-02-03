package br.com.concepting.framework.web.taglibs;

public class UploadBoxPropertyTag extends DownloadBoxPropertyTag{
    protected void renderBody() throws Throwable{
        String        actionForm = getActionForm();
        String        name       = getName();
        StringBuilder tagId      = new StringBuilder();
        
        tagId.append(name);
        tagId.append(".uploadProperty");

        HiddenPropertyTag uploadPropertyTag = new HiddenPropertyTag();
        
        uploadPropertyTag.setPageContext(pageContext);
        uploadPropertyTag.setId(tagId.toString());
        uploadPropertyTag.setName("uploadProperty");
        uploadPropertyTag.setValue(name);
        uploadPropertyTag.doStartTag();
        uploadPropertyTag.doEndTag();
        
        tagId.delete(0, tagId.length());
        tagId.append(name);
        tagId.append(".uploadFileNameProperty");

        HiddenPropertyTag uploadFileNamePropertyTag = new HiddenPropertyTag();
        
        uploadFileNamePropertyTag.setPageContext(pageContext);
        uploadFileNamePropertyTag.setId(tagId.toString());
        uploadFileNamePropertyTag.setName("uploadFileNameProperty");
        uploadFileNamePropertyTag.setValue(getFileNameProperty());
        uploadFileNamePropertyTag.doStartTag();
        uploadFileNamePropertyTag.doEndTag();

        tagId.delete(0, tagId.length());
        tagId.append(name);
        tagId.append(".uploadContentTypeProperty");

        HiddenPropertyTag contentTypePropertyTag = new HiddenPropertyTag();
        
        contentTypePropertyTag.setPageContext(pageContext);
        contentTypePropertyTag.setId(tagId.toString());
        contentTypePropertyTag.setName("uploadContentTypeProperty");
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
        onChange.append(".action.value = 'uploadFile'; document.");
        onChange.append(actionForm);
        onChange.append(".submit();");
        
        tagId.delete(0, tagId.length());
        tagId.append(name);
        tagId.append(".uploadFile");

        print("<input type=\"file\" id=\"");
        print(tagId);
        print("\" name=\"uploadFile\" style=\"display: none;\" onChange=\"");
        print(onChange);
        println("\">");

        println("<table class=\"uploadBox\">");
        println("<tr>");
        println("<td>");
        
        super.renderBody();
        
        println("</td>");
        println("<td>");
        
        StringBuilder onClick = new StringBuilder();
        
        onClick.append("document.getElementById('");
        onClick.append(name);
        onClick.append(".uploadFile').click();");

        ButtonTag buttonTag = new ButtonTag();
        
        buttonTag.setPageContext(pageContext);
        buttonTag.setName("uploadButton");
        buttonTag.setOnClick(onClick.toString());
        buttonTag.doStartTag();
        buttonTag.doEndTag();
        
        println("</td>");
        println("</tr>");
        println("</table>");
    }
}
