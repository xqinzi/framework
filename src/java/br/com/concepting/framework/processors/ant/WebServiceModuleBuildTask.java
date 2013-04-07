package br.com.concepting.framework.processors.ant;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.taskdefs.Delete;
import org.apache.tools.ant.taskdefs.Jar;
import org.apache.tools.ant.types.FileSet;

import br.com.concepting.framework.constants.ProjectConstants;
import br.com.concepting.framework.util.StringUtil;

/**
 * Classe que define a tarefa responsável por criar/configurar o pacote de WEB services.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class WebServiceModuleBuildTask extends Task{
    /**
     * Efetua o empacotamento dos WEB services.
     */
    public void build() throws Throwable{
        File modulesDir                       = new File(ProjectConstants.DEFAULT_MODULES_DIR);
        File moduleFile                       = new File(modulesDir.getAbsolutePath().concat(StringUtil.getDirectorySeparator()).concat(getProject().getName()).concat(".jar"));
        File resourcesModuleFile              = new File(modulesDir.getAbsolutePath().concat(StringUtil.getDirectorySeparator()).concat(getProject().getName()).concat("-resources.jar"));
        File webServicesModuleDir             = new File(ProjectConstants.DEFAULT_WEB_SERVICES_MODULE_DIR);
        File webServicesModuleDependenciesDir = new File(ProjectConstants.DEFAULT_WEB_SERVICES_DEPENDENCIES_DIR);
        File webServicesModuleLibDir          = new File(webServicesModuleDir.getAbsolutePath().concat(StringUtil.getDirectorySeparator()).concat("lib"));
        File webServicesModuleAarFile         = new File(webServicesModuleDir.getAbsolutePath().concat(StringUtil.getDirectorySeparator()).concat(getProject().getName()).concat(".aar"));
        
        if(!webServicesModuleDir.exists())
            webServicesModuleDir.mkdirs();

        Copy copyTask = new Copy();
        
        copyTask.setProject(getProject());
        copyTask.setTaskName(getTaskName());
        copyTask.setTodir(webServicesModuleDependenciesDir);
        copyTask.setFile(moduleFile);
        copyTask.setOverwrite(true);
        copyTask.execute();

        copyTask = new Copy();
        copyTask.setProject(getProject());
        copyTask.setTaskName(getTaskName());
        copyTask.setTodir(webServicesModuleDependenciesDir);
        copyTask.setFile(resourcesModuleFile);
        copyTask.setOverwrite(true);
        copyTask.execute();

        copyTask = new Copy();
        copyTask.setProject(getProject());
        copyTask.setTaskName(getTaskName());
        copyTask.setTodir(webServicesModuleLibDir);
        
        FileSet fileset = new FileSet();
        
        fileset.setDir(webServicesModuleDependenciesDir);
        fileset.setIncludes("**/*.jar");
        
        copyTask.addFileset(fileset);
        copyTask.setOverwrite(true);
        copyTask.execute();
           
        Jar jar = new Jar();
        
        jar.setProject(getProject());
        jar.setTaskName(getTaskName());
        jar.setDestFile(webServicesModuleAarFile);
            
        fileset = new FileSet();
        fileset.setDir(webServicesModuleDir);
        fileset.setIncludes("**/*.jar");
        
        jar.addFileset(fileset);

        fileset = new FileSet();
        fileset.setDir(webServicesModuleDir);
        fileset.setIncludes(ProjectConstants.DEFAULT_WEB_SERVICES_DESCRIPTOR_FILE_ID);
        
        jar.addFileset(fileset);
        jar.setUpdate(true);
        jar.execute();
        
        Delete deleteTask = new Delete();
        
        deleteTask.setProject(getProject());
        deleteTask.setTaskName(getTaskName());
        deleteTask.setDir(webServicesModuleDir);
        deleteTask.setIncludes("**/*.jar");
        deleteTask.setIncludeEmptyDirs(true);
        deleteTask.execute();

        webServicesModuleLibDir.delete();
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
