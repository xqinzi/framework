package br.com.concepting.framework.processors.ant;

import java.io.File;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Jar;
import org.apache.tools.ant.types.FileSet;

import br.com.concepting.framework.constants.ProjectConstants;
import br.com.concepting.framework.processors.AnnotationProcessorFactory;
import br.com.concepting.framework.util.FileUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.XmlReader;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Classe que define a tarefa responsável por criar/configurar o pacote EJB da aplicação.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class SharedModulesBuildTask extends Task{
    /**
     * Efetua o empacotamento EJB da aplicação.
     */
    public void build() throws Throwable{
        File modulesDir              = new File(ProjectConstants.DEFAULT_MODULES_DIR);
        File resourcesDir            = new File(ProjectConstants.DEFAULT_RESOURCES_DIR);
        File sharedModuleFile        = new File(modulesDir.getAbsolutePath().concat(StringUtil.getDirectorySeparator()).concat(getProject().getName()).concat(".jar"));
        File resourcesModuleFile     = new File(modulesDir.getAbsolutePath().concat(StringUtil.getDirectorySeparator()).concat(getProject().getName()).concat("-resources.jar"));
        File compileDir              = new File(ProjectConstants.DEFAULT_COMPILE_DIR);
        File annotationProcessorFile = null;
        
        if(getProject().getName().equals(ProjectConstants.CONCEPTING_FRAMEWORK_ID)){
            File annotationProcessorDir = new File(modulesDir.getAbsolutePath().concat(StringUtil.getDirectorySeparator()).concat("META-INF").concat(StringUtil.getDirectorySeparator()).concat("services"));
        
            if(!annotationProcessorDir.exists())
                annotationProcessorDir.mkdirs();
            
            annotationProcessorFile = new File(annotationProcessorDir.getAbsolutePath().concat(StringUtil.getDirectorySeparator()).concat("javax.annotation.processing.Processor"));  
            
            FileUtil.toTextFile(annotationProcessorFile.getAbsolutePath(), AnnotationProcessorFactory.class.getName());
        }
        
        StringBuilder remoteServicesFileName = new StringBuilder();
        
        remoteServicesFileName.append(StringUtil.trim(System.getProperty("java.io.tmpdir")));
        remoteServicesFileName.append(StringUtil.getDirectorySeparator());
        remoteServicesFileName.append(getProject().getName());
        remoteServicesFileName.append(StringUtil.getDirectorySeparator());
        remoteServicesFileName.append("remoteServices.xml");
        
        Jar jar = new Jar();
        
        jar.setProject(getProject());
        jar.setTaskName(getTaskName());
        jar.setDestFile(sharedModuleFile);
        
        FileSet fileset = new FileSet();
        
        fileset.setDir(compileDir);
        
        File remoteServicesFile = new File(remoteServicesFileName.toString());
        
        if(remoteServicesFile.exists()){
            XmlReader     reader     = new XmlReader(remoteServicesFile);
            XmlNode       rootNode   = reader.getRoot();
            List<XmlNode> childNodes = rootNode.getChildNodes();
            
            if(childNodes != null && childNodes.size() > 0){
                String remoteServiceClassName     = "";
                String remoteServiceClassFileName = "";
                
                for(XmlNode childNode : childNodes){
                    remoteServiceClassName     = StringUtil.trim(childNode.getAttribute("class"));
                    remoteServiceClassFileName = StringUtil.replaceAll(remoteServiceClassName, ".", StringUtil.getDirectorySeparator()).concat(".class");
                    
                    if(remoteServiceClassFileName.length() > 0)
                        fileset.setExcludes(remoteServiceClassFileName);
                }
            }
        }
           
        jar.addFileset(fileset);
        
        if(annotationProcessorFile != null){
            fileset = new FileSet();
            
            fileset.setDir(modulesDir);
            fileset.setIncludes("*/services/*");
        
            jar.addFileset(fileset);
        }
        
        jar.setUpdate(false);
        jar.execute();
        
        jar = new Jar();
        jar.setProject(getProject());
        jar.setTaskName(getTaskName());
        jar.setDestFile(resourcesModuleFile);
        
        fileset = new FileSet();
        fileset.setDir(resourcesDir);

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
