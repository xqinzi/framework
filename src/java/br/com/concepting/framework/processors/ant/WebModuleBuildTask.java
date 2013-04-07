package br.com.concepting.framework.processors.ant;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.taskdefs.Jar;
import org.apache.tools.ant.types.FileSet;
import org.dom4j.DocumentType;
import org.dom4j.tree.DefaultDocumentType;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.constants.ProjectConstants;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.XmlWriter;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Classe que define a tarefa responsável por criar/configurar o pacote WEB da aplicação.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class WebModuleBuildTask extends Task{
    /**
     * Efetua o empacotamento WEB da aplicação.
     */
    public void build() throws Throwable{
        File modulesDir               = new File(ProjectConstants.DEFAULT_MODULES_DIR);
        File moduleFile               = new File(modulesDir.getAbsolutePath().concat(StringUtil.getDirectorySeparator()).concat(getProject().getName()).concat(".jar"));
        File resourcesModuleFile      = new File(modulesDir.getAbsolutePath().concat(StringUtil.getDirectorySeparator()).concat(getProject().getName()).concat("-resources.jar"));
        File webDir                   = new File(ProjectConstants.DEFAULT_WEB_DIR);
        File webModuleDir             = new File(ProjectConstants.DEFAULT_WEB_MODULE_DIR);
        File webModuleLibDir          = new File(ProjectConstants.DEFAULT_WEB_LIB_DIR);
        File webModuleWarFile         = new File(webModuleDir.getAbsolutePath().concat(StringUtil.getDirectorySeparator()).concat(getProject().getName()).concat(".war"));
        File webModuleEarFile         = new File(webModuleDir.getAbsolutePath().concat(StringUtil.getDirectorySeparator()).concat(getProject().getName()).concat("-web.ear"));
        File appDescriptorFile        = new File(webModuleDir.getAbsolutePath().concat(StringUtil.getDirectorySeparator()).concat(ProjectConstants.DEFAULT_APPLICATION_DESCRIPTOR_FILE_ID));
        
        if(!webModuleDir.exists())
            webModuleDir.mkdirs();
        
        if(!webModuleLibDir.exists())
            webModuleLibDir.mkdirs();

        Copy copyTask = new Copy();
        
        copyTask.setProject(getProject());
        copyTask.setTaskName(getTaskName());
        copyTask.setTodir(webModuleLibDir);
        copyTask.setFile(moduleFile);
        copyTask.setOverwrite(true);
        copyTask.execute();

        copyTask = new Copy();
        copyTask.setProject(getProject());
        copyTask.setTaskName(getTaskName());
        copyTask.setTodir(webModuleLibDir);
        copyTask.setFile(resourcesModuleFile);
        copyTask.setOverwrite(true);
        copyTask.execute();

        Jar jar = new Jar();
        
        jar.setProject(getProject());
        jar.setTaskName(getTaskName());
        jar.setDestFile(webModuleWarFile);
            
        FileSet fileset = new FileSet();
        
        fileset.setDir(webDir);
        fileset.setIncludes("**/*");

        jar.addFileset(fileset);
        jar.setUpdate(false);
        jar.execute();
        
        XmlNode node = new XmlNode("application");
        
        node.addChildNode(new XmlNode("display-name", getProject().getName().concat("-web")));
        
        XmlNode moduleNode = new XmlNode("module");
        XmlNode webNode    = new XmlNode("web");

        webNode.addChildNode(new XmlNode("web-uri", getProject().getName().concat(".war")));
        webNode.addChildNode(new XmlNode("context-root", getProject().getName()));
        
        moduleNode.addChildNode(webNode);
    
        node.addChildNode(moduleNode);
        
        DocumentType documentType = new DefaultDocumentType();
        
        documentType.setName("application");
        documentType.setPublicID("-//Sun Microsystems, Inc.//DTD J2EE Application 1.3//EN");
        documentType.setSystemID("http://java.sun.com/dtd/application_1_3.dtd");
        
        XmlWriter writer = new XmlWriter(appDescriptorFile, documentType, Constants.DEFAULT_ENCODING);
        
        writer.write(node);
        
        jar = new Jar();
        jar.setProject(getProject());
        jar.setTaskName(getTaskName());
        jar.setDestFile(webModuleEarFile);
        
        fileset = new FileSet();
        fileset.setDir(webModuleDir);
        fileset.setIncludes(webModuleWarFile.getName());
        
        jar.addFileset(fileset);

        fileset = new FileSet();
        fileset.setDir(webModuleDir);
        fileset.setIncludes(ProjectConstants.DEFAULT_APPLICATION_DESCRIPTOR_FILE_ID);
        
        jar.addFileset(fileset);
        jar.setUpdate(false);
        jar.execute();
    }
    
    /**
     * @see org.apache.tools.ant.Task#execute()
     */
    public void execute() throws BuildException{
        try{
            build();
        }
        catch(Throwable e){
            throw new BuildException(e);
        }
    }
}
