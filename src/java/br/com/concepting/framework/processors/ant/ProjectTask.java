package br.com.concepting.framework.processors.ant;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.taskdefs.Delete;
import org.apache.tools.ant.types.FileSet;

import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.resource.PropertiesResource;
import br.com.concepting.framework.resource.PropertiesResourceLoader;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.XmlReader;
import br.com.concepting.framework.util.XmlWriter;
import br.com.concepting.framework.util.constants.AttributeConstants;
import br.com.concepting.framework.util.constants.ProjectConstants;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Classe que define a tarefa responsável por criar/configurar uma aplicação.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class ProjectTask extends Task{
    private String name   = "";
    private String action = "";
    
    /**
     * Retorna o identificador do projeto.
     * 
     * @return String contendo o identificador do projeto.
     */
    public String getName(){
        return name;
    }

    /**
     * Define o identificador do projeto.
     * 
     * @param name String contendo o identificador do projeto.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Retorna o identificador da ação a ser executada no projeto.
     * 
     * @return String contendo o identificador da ação.
     */
    public String getAction(){
        return action;
    }

    /**
     * Define o identificador da ação a ser executada no projeto.
     * 
     * @param action String contendo o identificador da ação.
     */
    public void setAction(String action){
        this.action = action;
    }

    /**
     * Cria um novo projeto.
     * 
     * @throws Throwable
     */
    public void create() throws Throwable{
        StringBuilder buffer = new StringBuilder();

        buffer.append(getProject().getBaseDir().getAbsolutePath());
        buffer.append(StringUtil.getDirectorySeparator());
        buffer.append(ProjectConstants.DEFAULT_BUILD_PROPERTIES_FILE_ID);
        
        Properties properties = new Properties();
        
        properties.load(new FileInputStream(new File(buffer.toString())));
        
        while(true){
            name = StringUtil.trim(JOptionPane.showInputDialog(properties.getProperty(ProjectConstants.PROJECT_NAME_LABEL_KEY)));
            
            if(name.length() > 0)
                break;
            
            JOptionPane.showMessageDialog(null, properties.getProperty(ProjectConstants.INVALID_PROJECT_NAME_MESSAGE_KEY));
        }
        
        createStructure();
        createBuildFile();
        copyDependencies();
        createIDEFile();
        createIDEClasspathFile();
    }
    
    /**
     * Cria o arquivo que define o classpath do projeto.
     * 
     * @throws Throwable
     */
    private void createIDEClasspathFile() throws Throwable{
        StringBuilder buffer = new StringBuilder();
        
        buffer.append("..");
        buffer.append(StringUtil.getDirectorySeparator());
        buffer.append(name);
        buffer.append(StringUtil.getDirectorySeparator());
        buffer.append(".classpath");
        
        File file = new File(buffer.toString());
        
        if(!file.exists()){
            XmlNode root = new XmlNode("classpath");
            
            XmlNode entry = new XmlNode("classpathentry");
            
            entry.addAttribute("kind", "src");
            entry.addAttribute("path", ProjectConstants.DEFAULT_JAVA_DIR);
            
            root.addChildNode(entry);

            entry = new XmlNode("classpathentry");
            entry.addAttribute("kind", "src");
            entry.addAttribute("path", ProjectConstants.DEFAULT_RESOURCES_DIR);
            
            root.addChildNode(entry);

            entry = new XmlNode("classpathentry");
            entry.addAttribute("kind", "src");
            entry.addAttribute("path", ProjectConstants.DEFAULT_TESTS_DIR);
            
            root.addChildNode(entry);
            
            StringBuilder dependenciesDirName = new StringBuilder();
            
            dependenciesDirName.append("..");
            dependenciesDirName.append(StringUtil.getDirectorySeparator());
            dependenciesDirName.append(name);
            dependenciesDirName.append(StringUtil.getDirectorySeparator());
            dependenciesDirName.append(ProjectConstants.DEFAULT_COMPILE_DEPENDENCIES_DIR);

            File dependenciesDir = new File(dependenciesDirName.toString());
            
            if(dependenciesDir.exists()){
                File dependenciesFiles[] = dependenciesDir.listFiles();
                
                if(dependenciesFiles != null){
                    for(File dependencyFile : dependenciesFiles){
                        entry = new XmlNode("classpathentry");
                        entry.addAttribute("kind", "lib");
                        entry.addAttribute("path", ProjectConstants.DEFAULT_COMPILE_DEPENDENCIES_DIR.concat(dependencyFile.getName()));
                        
                        root.addChildNode(entry);
                    }
                }
            }
            
            entry = new XmlNode("classpathentry");
            entry.addAttribute("kind", "con");
            entry.addAttribute("path", "org.eclipse.jdt.launching.JRE_CONTAINER");

            root.addChildNode(entry);
            
            entry = new XmlNode("classpathentry");
            entry.addAttribute("kind", "output");
            entry.addAttribute("path", ProjectConstants.DEFAULT_WEB_DIR.concat(ProjectConstants.DEFAULT_WEB_CLASSES_DIR));

            root.addChildNode(entry);

            XmlWriter writer = new XmlWriter(file);
            
            writer.write(root);
        }
    }
    
    /**
     * Cria o arquivo que define o projeto.
     * 
     * @throws Throwable
     */
    private void createIDEFile() throws Throwable{
        StringBuilder buffer = new StringBuilder();
        
        buffer.append("..");
        buffer.append(StringUtil.getDirectorySeparator());
        buffer.append(name);
        buffer.append(StringUtil.getDirectorySeparator());
        buffer.append(".project");
        
        File file = new File(buffer.toString());
        
        if(!file.exists()){
            XmlNode root = new XmlNode("projectDescription");
            
            root.addChildNode(new XmlNode("name", name));
            root.addChildNode(new XmlNode("comment"));
            root.addChildNode(new XmlNode("projects"));
            
            XmlNode entry      = new XmlNode("buildSpec");
            XmlNode childEntry = new XmlNode("buildCommand");
            
            childEntry.addChildNode(new XmlNode("name", "org.eclipse.jdt.core.javabuilder"));
            childEntry.addChildNode(new XmlNode("arguments"));
            
            entry.addChildNode(childEntry);
            
            root.addChildNode(entry);
            
            entry = new XmlNode("natures");
            entry.addChildNode(new XmlNode("nature", "org.eclipse.jdt.core.javanature"));
            
            root.addChildNode(entry);

            XmlWriter writer = new XmlWriter(file);
            
            writer.write(root);
        }
    }

    /**
     * Cria o arquivo de compilação/empacotamento do projeto.
     * 
     * @throws Throwable
     */
    private void createBuildFile() throws Throwable{
        StringBuilder buffer = new StringBuilder();
        
        buffer.append("..");
        buffer.append(StringUtil.getDirectorySeparator());
        buffer.append(name);
        buffer.append(StringUtil.getDirectorySeparator());
        buffer.append(ProjectConstants.DEFAULT_BUILD_FILE_ID);
        
        File file = new File(buffer.toString());
        
        if(!file.exists()){
            XmlNode root = new XmlNode("project");
            
            root.addAttribute(AttributeConstants.NAME_KEY, name);
            root.addAttribute("default", ProjectConstants.DEFAULT_BUILD_ID);

            XmlNode child = new XmlNode("property");
            
            child.addAttribute("file", ProjectConstants.DEFAULT_BUILD_PROPERTIES_FILE_ID);

            root.addChildNode(child);
            
            buffer.delete(0, buffer.length());
            buffer.append(ProjectConstants.CONCEPTING_FRAMEWORK_HOME_ID);
            buffer.append(StringUtil.getDirectorySeparator());
            buffer.append(ProjectConstants.DEFAULT_BUILD_FILE_ID);

            child = new XmlNode("import");
            child.addAttribute("file", buffer.toString());
            
            root.addChildNode(child);
            
            XmlWriter writer = new XmlWriter(file);
            
            writer.write(root);
        }
    }
    
    /**
     * Cria a estrutura do projeto.
     */
    public void createStructure(){
        Project project = getProject();

        if(name.length() == 0)
            name = project.getName();  
            
        StringBuilder destination = new StringBuilder();
        
        destination.append("..");
        destination.append(StringUtil.getDirectorySeparator());
        destination.append(name);
        
        File destinationFile = new File(destination.toString());
        
        if(!destinationFile.exists())
            destinationFile.mkdirs();

        String        baseDir = project.getBaseDir().getAbsolutePath();
        StringBuilder source  = new StringBuilder();
        
        source.append(baseDir);
        source.append(StringUtil.getDirectorySeparator());
        source.append(ProjectConstants.DEFAULT_BUILD_PROPERTIES_FILE_ID);
        
        File sourceFile = new File(source.toString());
        
        if(!sourceFile.getAbsolutePath().startsWith(destinationFile.getAbsolutePath())){
            FileSet fileSet = new FileSet();
            
            fileSet.setFile(sourceFile);
            
            Copy copyTask = new Copy();
            
            copyTask.setProject(project);
            copyTask.setTaskName(getTaskName());
            copyTask.setOwningTarget(getOwningTarget());
            copyTask.setOverwrite(false);
            copyTask.addFileset(fileSet);
            copyTask.setTodir(destinationFile);
            copyTask.execute();
        }

        source.delete(0, source.length());
        source.append(baseDir);
        source.append(StringUtil.getDirectorySeparator());
        source.append(ProjectConstants.DEFAULT_DEPENDENCIES_FILE_ID);
        
        sourceFile = new File(source.toString());
        
        if(!sourceFile.getAbsolutePath().startsWith(destinationFile.getAbsolutePath())){
            FileSet fileSet = new FileSet();
            
            fileSet.setFile(sourceFile);

            Copy copyTask = new Copy();
            
            copyTask.setProject(project);
            copyTask.setTaskName(getTaskName());
            copyTask.setOwningTarget(getOwningTarget());
            copyTask.setOverwrite(false);
            copyTask.addFileset(fileSet);
            copyTask.setTodir(destinationFile);
            copyTask.execute();
        }
        
        destination.delete(0, source.length());
        destination.append("..");
        destination.append(StringUtil.getDirectorySeparator());
        destination.append(name);
        destination.append(StringUtil.getDirectorySeparator());
        destination.append(ProjectConstants.DEFAULT_COMPILE_DIR);
        
        destinationFile = new File(destination.toString());
        if(!destinationFile.exists())
            destinationFile.mkdirs();
        
        destination.delete(0, source.length());
        destination.append("..");
        destination.append(StringUtil.getDirectorySeparator());
        destination.append(name);
        destination.append(StringUtil.getDirectorySeparator());
        destination.append(ProjectConstants.DEFAULT_MODULES_DIR);
        
        destinationFile = new File(destination.toString());
        if(!destinationFile.exists())
            destinationFile.mkdirs();

        destination.delete(0, source.length());
        destination.append("..");
        destination.append(StringUtil.getDirectorySeparator());
        destination.append(name);
        destination.append(StringUtil.getDirectorySeparator());
        destination.append(ProjectConstants.DEFAULT_DEPENDENCIES_DIR);
        
        destinationFile = new File(destination.toString());
        if(!destinationFile.exists())
            destinationFile.mkdirs();
        
        destination.delete(0, source.length());
        destination.append("..");
        destination.append(StringUtil.getDirectorySeparator());
        destination.append(name);
        destination.append(StringUtil.getDirectorySeparator());
        destination.append(ProjectConstants.DEFAULT_DISTRIBUTION_DIR);
        
        destinationFile = new File(destination.toString());
        if(!destinationFile.exists())
            destinationFile.mkdirs();
        
        destination.delete(0, source.length());
        destination.append("..");
        destination.append(StringUtil.getDirectorySeparator());
        destination.append(name);
        destination.append(StringUtil.getDirectorySeparator());
        destination.append(ProjectConstants.DEFAULT_JAVA_DIR);
        
        destinationFile = new File(destination.toString());
        if(!destinationFile.exists())
            destinationFile.mkdirs();
        
        destination.delete(0, source.length());
        destination.append("..");
        destination.append(StringUtil.getDirectorySeparator());
        destination.append(name);
        destination.append(StringUtil.getDirectorySeparator());
        destination.append(ProjectConstants.DEFAULT_RESOURCES_DIR);
        
        destinationFile = new File(destination.toString());
        if(!destinationFile.exists())
            destinationFile.mkdirs();
        
        destination.delete(0, source.length());
        destination.append("..");
        destination.append(StringUtil.getDirectorySeparator());
        destination.append(name);
        destination.append(StringUtil.getDirectorySeparator());
        destination.append(ProjectConstants.DEFAULT_TESTS_DIR);
        
        destinationFile = new File(destination.toString());
        if(!destinationFile.exists())
            destinationFile.mkdirs();

        destination.delete(0, source.length());
        destination.append("..");
        destination.append(StringUtil.getDirectorySeparator());
        destination.append(name);
        destination.append(StringUtil.getDirectorySeparator());
        destination.append(ProjectConstants.DEFAULT_WEB_DIR);
        
        destinationFile = new File(destination.toString());
        if(!destinationFile.exists())
            destinationFile.mkdirs();
            
        destination.delete(0, source.length());
        destination.append("..");
        destination.append(StringUtil.getDirectorySeparator());
        destination.append(name);
        destination.append(StringUtil.getDirectorySeparator());
        destination.append(ProjectConstants.DEFAULT_REPORTS_DIR);
        
        destinationFile = new File(destination.toString());
        if(!destinationFile.exists())
            destinationFile.mkdirs();
        
        destination.delete(0, source.length());
        destination.append("..");
        destination.append(StringUtil.getDirectorySeparator());
        destination.append(name);
        destination.append(StringUtil.getDirectorySeparator());
        destination.append(ProjectConstants.DEFAULT_SQL_DIR);
        
        destinationFile = new File(destination.toString());
        if(!destinationFile.exists())
            destinationFile.mkdirs();
    }
    
    /**
     * Importa os arquivos de dependência do projeto.
     * 
     * @throws Throwable
     */
    public void copyDependencies() throws Throwable{
        createStructure();
        
        Project project = getProject();

        if(name.length() == 0)
            name = project.getName();  

        StringBuilder destination = new StringBuilder();

        destination.append("..");
        destination.append(StringUtil.getDirectorySeparator());
        destination.append(name);

        PropertiesResourceLoader propertiesResourceLoader = new PropertiesResourceLoader(destination.toString(), ProjectConstants.DEFAULT_BUILD_ID);
        PropertiesResource       propertiesResource       = propertiesResourceLoader.getContent();
            
        StringBuilder sourceBuffer = new StringBuilder();
        
        sourceBuffer.append(ProjectConstants.CONCEPTING_FRAMEWORK_HOME_ID);
        sourceBuffer.append(StringUtil.getDirectorySeparator());
        sourceBuffer.append(ProjectConstants.DEFAULT_RESOURCES_DIR);
        
        String source = PropertyUtil.fillPropertiesResourceInString(propertiesResource, sourceBuffer.toString());
        
        destination.append(StringUtil.getDirectorySeparator());
        destination.append(ProjectConstants.DEFAULT_RESOURCES_DIR);
        
        File sourceFile      = new File(source);
        File destinationFile = new File(destination.toString());
        
        if(!sourceFile.getAbsolutePath().equals(destinationFile.getAbsolutePath())){
            FileSet fileSet = new FileSet();
                
            fileSet.setDir(sourceFile);
                
            Copy copyTask = new Copy();
            
            copyTask.setProject(project);
            copyTask.setTaskName(getTaskName());
            copyTask.setOwningTarget(getOwningTarget());
            copyTask.addFileset(fileSet);
            copyTask.setTodir(destinationFile);
            copyTask.execute();
        }

        sourceBuffer.delete(0, sourceBuffer.length());
        sourceBuffer.append(ProjectConstants.CONCEPTING_FRAMEWORK_HOME_ID);
        sourceBuffer.append(StringUtil.getDirectorySeparator());
        sourceBuffer.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
        
        source = PropertyUtil.fillPropertiesResourceInString(propertiesResource, sourceBuffer.toString());
        
        destination.delete(0, destination.length());
        destination.append("..");
        destination.append(StringUtil.getDirectorySeparator());
        destination.append(name);
        destination.append(StringUtil.getDirectorySeparator());
        destination.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
        
        sourceFile      = new File(source);
        destinationFile = new File(destination.toString());
        
        if(!sourceFile.getAbsolutePath().equals(destinationFile.getAbsolutePath())){
            FileSet fileSet = new FileSet();
                
            fileSet.setDir(sourceFile);
                
            Copy copyTask = new Copy();
            
            copyTask.setProject(project);
            copyTask.setTaskName(getTaskName());
            copyTask.setOwningTarget(getOwningTarget());
            copyTask.addFileset(fileSet);
            copyTask.setTodir(destinationFile);
            copyTask.execute();
        }

        sourceBuffer.delete(0, sourceBuffer.length());
        sourceBuffer.append(ProjectConstants.CONCEPTING_FRAMEWORK_HOME_ID);
        sourceBuffer.append(StringUtil.getDirectorySeparator());
        sourceBuffer.append(ProjectConstants.DEFAULT_WEB_DIR);
        
        source = PropertyUtil.fillPropertiesResourceInString(propertiesResource, sourceBuffer.toString());
        
        destination.delete(0, destination.length());
        destination.append("..");
        destination.append(StringUtil.getDirectorySeparator());
        destination.append(name);
        destination.append(StringUtil.getDirectorySeparator());
        destination.append(ProjectConstants.DEFAULT_WEB_DIR);
        
        sourceFile      = new File(source);
        destinationFile = new File(destination.toString());
        
        if(!sourceFile.getAbsolutePath().equals(destinationFile.getAbsolutePath())){
            FileSet fileSet = new FileSet();
                
            fileSet.setDir(sourceFile);
                
            Copy copyTask = new Copy();
            
            copyTask.setProject(project);
            copyTask.setTaskName(getTaskName());
            copyTask.setOwningTarget(getOwningTarget());
            copyTask.addFileset(fileSet);
            copyTask.setTodir(destinationFile);
            copyTask.execute();
        }
        
        if(!name.equals(ProjectConstants.CONCEPTING_FRAMEWORK_ID)){
            destination.delete(0, destination.length());
            destination.append("..");
            destination.append(StringUtil.getDirectorySeparator());
            destination.append(name);
            destination.append(StringUtil.getDirectorySeparator());
            destination.append(ProjectConstants.DEFAULT_WEB_LIB_DIR);
        
            Delete deleteTask = new Delete();
            
            deleteTask.setProject(project);
            deleteTask.setTaskName(getTaskName());
            deleteTask.setDir(new File(destination.toString()));
            deleteTask.setIncludes("**/*.jar");
            deleteTask.setIncludeEmptyDirs(true);
            deleteTask.execute();

            destination.delete(0, destination.length());
            destination.append("..");
            destination.append(StringUtil.getDirectorySeparator());
            destination.append(name);
            destination.append(StringUtil.getDirectorySeparator());
            destination.append(ProjectConstants.DEFAULT_DEPENDENCIES_DIR);
        
            deleteTask = new Delete();
            deleteTask.setProject(project);
            deleteTask.setTaskName(getTaskName());
            deleteTask.setDir(new File(destination.toString()));
            deleteTask.setIncludes("**/*.jar");
            deleteTask.setIncludeEmptyDirs(true);
            deleteTask.execute();
        }

        destination.delete(0, destination.length());
        destination.append("..");
        destination.append(StringUtil.getDirectorySeparator());
        destination.append(name);
        destination.append(StringUtil.getDirectorySeparator());
        destination.append(ProjectConstants.DEFAULT_DEPENDENCIES_FILE_ID);
        
        XmlReader reader        = new XmlReader(new File(destination.toString()));
        XmlNode   root          = reader.getRoot();
        XmlNode   child         = null;
        String    targets[]     = null;
        String    targetsBuffer = "";
        Boolean   overwrite     = false;
        Integer   cont          = 0;
        FileSet   fileSet       = null;
        Copy      copyTask      = null;
 
        if(root.hasChildNodes()){
            cont = 0;

            while(true){
                child = root.getNode(cont);
                if(child == null)
                    break;

                source = StringUtil.trim(child.getAttribute("source"));
                
                if((!source.contains(ProjectConstants.CONCEPTING_FRAMEWORK_HOME_ID) && name.equals(ProjectConstants.CONCEPTING_FRAMEWORK_ID)) || !name.equals(ProjectConstants.CONCEPTING_FRAMEWORK_ID)){
                    source        = PropertyUtil.fillPropertiesResourceInString(propertiesResource, source);
                    targetsBuffer = StringUtil.trim(child.getAttribute("targets"));
                    
                    if(source.length() > 0 && targetsBuffer.length() > 0){
                        targets = StringUtil.split(targetsBuffer);                  
    
                        sourceFile = new File(source);
                        
                        if(sourceFile.exists()){
                            for(String target : targets){
                                if((target.equals("ejb") || target.startsWith("web")) && name.equals(ProjectConstants.CONCEPTING_FRAMEWORK_ID))
                                    continue;
                                
                                destination.delete(0, destination.length());
                                destination.append("..");
                                destination.append(StringUtil.getDirectorySeparator());
                                destination.append(name);
                                destination.append(StringUtil.getDirectorySeparator());

                                if(!target.equals("web")){
                                    destination.append(ProjectConstants.DEFAULT_DEPENDENCIES_DIR);
                                    destination.append(target);
                                }
                                else
                                    destination.append(ProjectConstants.DEFAULT_WEB_LIB_DIR);
                                
                                destinationFile = new File(destination.toString());
                                if(!destinationFile.exists())
                                    destinationFile.mkdirs();
                            
                                overwrite = Boolean.parseBoolean(StringUtil.trim(child.getAttribute("overwrite")));
    
                                fileSet = new FileSet();
        
                                if(sourceFile.isDirectory())
                                    fileSet.setDir(sourceFile);
                                else
                                    fileSet.setFile(sourceFile);
    
                                copyTask = new Copy();
                                copyTask.setProject(project);
                                copyTask.setTaskName(getTaskName());
                                copyTask.setOwningTarget(getOwningTarget());
                                copyTask.setOverwrite(overwrite);
                                copyTask.addFileset(fileSet);
                                copyTask.setTodir(destinationFile);
                                copyTask.execute();
                            }
                        }
                    }
                }

                cont++;
            }
        }
    }
    
    /**
     * @see org.apache.tools.ant.Task#execute()
     */
    public void execute() throws BuildException{
        super.execute();
        
        try{
            Method method = getClass().getMethod(action, new Class[]{});
            
            method.invoke(this, new Object[]{});
        }
        catch(Throwable e){
            throw new BuildException(e);
        }
    }
}
