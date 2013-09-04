package br.com.concepting.framework.processors.ant;

import java.io.File;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.taskdefs.Delete;
import org.apache.tools.ant.taskdefs.Jar;
import org.apache.tools.ant.taskdefs.Manifest;
import org.apache.tools.ant.taskdefs.Manifest.Attribute;
import org.apache.tools.ant.types.FileSet;
import org.dom4j.DocumentType;
import org.dom4j.tree.DefaultDocumentType;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.constants.ProjectConstants;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.XmlReader;
import br.com.concepting.framework.util.XmlWriter;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Classe que define a tarefa responsável por criar/configurar o pacote EJB da aplicação.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class EjbModuleBuildTask extends Task{
    /**
     * Efetua o empacotamento EJB da aplicação.
     */
    public void build() throws Throwable{
        File modulesDir               = new File(ProjectConstants.DEFAULT_MODULES_DIR);
        File moduleFile               = new File(modulesDir.getAbsolutePath().concat(StringUtil.getDirectorySeparator()).concat(getProject().getName()).concat(".jar"));
        File resourcesModuleFile      = new File(modulesDir.getAbsolutePath().concat(StringUtil.getDirectorySeparator()).concat(getProject().getName()).concat("-resources.jar"));
        File compileDir               = new File(ProjectConstants.DEFAULT_COMPILE_DIR);
        File ejbModuleDir             = new File(ProjectConstants.DEFAULT_EJB_MODULE_DIR);
        File ejbModuleJarFile         = new File(ejbModuleDir.getAbsolutePath().concat(StringUtil.getDirectorySeparator()).concat(getProject().getName()).concat("-ejb.jar"));
        File ejbModuleEarFile         = new File(ejbModuleDir.getAbsolutePath().concat(StringUtil.getDirectorySeparator()).concat(getProject().getName()).concat("-ejb.ear"));
        File ejbModuleDependenciesDir = new File(ProjectConstants.DEFAULT_EJB_MODULE_DEPENDENCIES_DIR);
        File descriptorFile           = new File(ejbModuleDir.getAbsolutePath().concat(StringUtil.getDirectorySeparator()).concat(ProjectConstants.DEFAULT_APPLICATION_DESCRIPTOR_FILE_ID));
        File tempDir                  = new File(System.getProperty("java.io.tmpdir"));
        File dependenciesTempDir      = new File(tempDir.getAbsolutePath().concat(StringUtil.getDirectorySeparator()).concat(ProjectConstants.DEFAULT_DEPENDENCIES_DIR));
        
        if(!ejbModuleDir.exists())
            ejbModuleDir.mkdirs();
        
        if(!ejbModuleDependenciesDir.exists())
            ejbModuleDependenciesDir.mkdirs();

        Copy copyTask = new Copy();
        
        copyTask.setProject(getProject());
        copyTask.setTaskName(getTaskName());
        copyTask.setTodir(ejbModuleDependenciesDir);
        copyTask.setFile(moduleFile);
        copyTask.setOverwrite(true);
        copyTask.setFailOnError(false);
        copyTask.execute();
            
        copyTask = new Copy();
        copyTask.setProject(getProject());
        copyTask.setTaskName(getTaskName());
        copyTask.setTodir(ejbModuleDependenciesDir);
        copyTask.setFile(resourcesModuleFile);
        copyTask.setOverwrite(true);
        copyTask.setFailOnError(false);
        copyTask.execute();

        StringBuilder remoteServicesFileName = new StringBuilder();
        
        remoteServicesFileName.append(StringUtil.trim(System.getProperty("java.io.tmpdir")));
        remoteServicesFileName.append(StringUtil.getDirectorySeparator());
        remoteServicesFileName.append(getProject().getName());
        remoteServicesFileName.append(StringUtil.getDirectorySeparator());
        remoteServicesFileName.append("remoteServices.xml");
        
        File remoteServicesFile = new File(remoteServicesFileName.toString());
        
        if(remoteServicesFile.exists()){
            XmlReader     reader     = new XmlReader(remoteServicesFile);
            XmlNode       rootNode   = reader.getRoot();
            List<XmlNode> childNodes = rootNode.getChildNodes();
            
            if(childNodes != null && childNodes.size() > 0){
                Jar jar = new Jar();
                
                jar.setProject(getProject());
                jar.setTaskName(getTaskName());
                jar.setDestFile(ejbModuleJarFile);
                
                FileSet fileset = new FileSet();
                
                fileset.setDir(compileDir);
                
                String remoteServiceInterfaceName     = "";
                String remoteServiceClassName         = "";
                String remoteServiceInterfaceFileName = "";
                String remoteServiceClassFileName     = "";
                
                for(XmlNode childNode : childNodes){
                    remoteServiceInterfaceName     = StringUtil.trim(childNode.getAttribute("interface"));
                    remoteServiceClassName         = StringUtil.trim(childNode.getAttribute("class"));
                    remoteServiceInterfaceFileName = StringUtil.replaceAll(remoteServiceInterfaceName, ".", "/").concat(".class");
                    remoteServiceClassFileName     = StringUtil.replaceAll(remoteServiceClassName, ".", "/").concat(".class");
                    
                    if(remoteServiceInterfaceFileName.length() > 0  && remoteServiceClassFileName.length() > 0){
                        fileset.setIncludes(remoteServiceInterfaceFileName);
                        fileset.setIncludes(remoteServiceClassFileName);
                    }
                }
            
                jar.addFileset(fileset);
            
                fileset = new FileSet();
                fileset.setDir(ejbModuleDir);
                fileset.setIncludes(ProjectConstants.DEFAULT_EJB_MODULE_DESCRIPTOR_FILE_ID);
            
                StringBuilder classPathBuffer              = new StringBuilder(".");
                File          ejbModuleDependenciesFiles[] = ejbModuleDependenciesDir.listFiles();
                
                for(File ejbModuleDependencyFile : ejbModuleDependenciesFiles){
                    classPathBuffer.append(" ");
                    classPathBuffer.append(ProjectConstants.DEFAULT_DEPENDENCIES_DIR);
                    classPathBuffer.append(ejbModuleDependencyFile.getName());
                }
                
                Attribute classPathAttribute = new Attribute("Class-Path", classPathBuffer.toString());
                Manifest  manifest           = new Manifest();
                
                manifest.addConfiguredAttribute(classPathAttribute);
        
                jar.addConfiguredManifest(manifest);
                jar.addFileset(fileset);
                jar.setUpdate(false);
                jar.execute();
                
                XmlNode node = new XmlNode("application");
                        
                node.addChildNode(new XmlNode("display-name", getProject().getName().concat("-ejb")));
                
                XmlNode moduleNode = new XmlNode("module");
                
                moduleNode.addChildNode(new XmlNode("ejb", getProject().getName().concat("-ejb.jar")));
        
                node.addChildNode(moduleNode);
                        
                copyTask = new Copy();
                copyTask.setProject(getProject());
                copyTask.setTaskName(getTaskName());
                copyTask.setTodir(dependenciesTempDir);
                copyTask.setVerbose(false);
                        
                fileset = new FileSet();
                fileset.setDir(ejbModuleDependenciesDir);
                fileset.setIncludes("*.jar");
                        
                copyTask.addFileset(fileset);
                copyTask.setOverwrite(true);
                copyTask.execute();
                    
                for(File ejbModuleDependencyFile : ejbModuleDependenciesFiles){
                    moduleNode = new XmlNode("module");
                    moduleNode.addChildNode(new XmlNode("java", "dependencies/".concat(ejbModuleDependencyFile.getName())));
                    
                    node.addChildNode(moduleNode);
                }
                        
                DocumentType documentType = new DefaultDocumentType();
                
                documentType.setName("application");
                documentType.setPublicID("-//Sun Microsystems, Inc.//DTD J2EE Application 1.3//EN");
                documentType.setSystemID("http://java.sun.com/dtd/application_1_3.dtd");
                
                XmlWriter writer = new XmlWriter(descriptorFile, documentType, Constants.DEFAULT_ENCODING);
                
                writer.write(node);
        
                jar = new Jar();
                jar.setProject(getProject());
                jar.setTaskName(getTaskName());
                jar.setDestFile(ejbModuleEarFile);
                    
                fileset = new FileSet();
                fileset.setDir(ejbModuleDir);
                fileset.setIncludes(ejbModuleJarFile.getName());
                
                jar.addFileset(fileset);
        
                fileset = new FileSet();
                fileset.setDir(tempDir);
                fileset.setIncludes("**/*.jar");
                        
                jar.addFileset(fileset);
        
                fileset = new FileSet();
                fileset.setDir(ejbModuleDir);
                fileset.setIncludes(ProjectConstants.DEFAULT_APPLICATION_DESCRIPTOR_FILE_ID);
                        
                jar.addFileset(fileset);
                jar.setUpdate(false);
                jar.execute();
                    
                Delete delete = new Delete();
                
                delete.setProject(getProject());
                delete.setTaskName(getTaskName());
                delete.setDir(dependenciesTempDir);
                delete.setIncludes("**/*");
                delete.setIncludeEmptyDirs(true);
                delete.setFailOnError(false);
                delete.execute();
        
                if(dependenciesTempDir.exists())
                    dependenciesTempDir.delete();
            }
        }
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
