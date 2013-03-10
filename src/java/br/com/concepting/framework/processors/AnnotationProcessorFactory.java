package br.com.concepting.framework.processors;

import java.io.File;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import br.com.concepting.framework.audit.constants.AuditorConstants;
import br.com.concepting.framework.audit.resource.AuditorResource;
import br.com.concepting.framework.audit.resource.AuditorResourceLoader;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.processors.ModelAnnotationProcessor;
import br.com.concepting.framework.processors.constants.ProjectConstants;
import br.com.concepting.framework.processors.interfaces.IAnnotationProcessor;
import br.com.concepting.framework.resource.exceptions.InvalidResourceException;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.util.StringUtil;

/**
 * Classe que implementa o processamento das anotações dos modelos de dados.
 * Utilizado para geração de código a partir de templates.
 * 
 * @author fvilarinho
 * @since 1.0
 */
@SupportedAnnotationTypes({"br.com.concepting.framework.model.annotations.Model"})
public class AnnotationProcessorFactory extends AbstractProcessor{
	private AuditorResource       auditorResource = null;
	private ProcessingEnvironment environment     = null;
	
	public AuditorResource getAuditorResource(){
        return auditorResource;
    }

    public void setAuditorResource(AuditorResource auditorResource){
        this.auditorResource = auditorResource;
    }

    public ProcessingEnvironment getEnvironment(){
        return environment;
    }

    public void setEnvironment(ProcessingEnvironment environment){
        this.environment = environment;
    }

    /**
	 * @see javax.annotation.processing.AbstractProcessor#init(javax.annotation.processing.ProcessingEnvironment)
	 */
    public synchronized void init(ProcessingEnvironment environment){
        super.init(environment);
        
        this.environment = environment;
        
        try{
            initialize();
        }
        catch(InvalidResourceException e){
            e.printStackTrace();
        }
    }

    /**
	 * @see javax.annotation.processing.AbstractProcessor#getSupportedSourceVersion()
	 */
	public SourceVersion getSupportedSourceVersion() {
	    return SourceVersion.latestSupported();
	}
	
	/**
	 * Retorna o identificador do projeto contendo as anotações a serem processadas.
	 * 
	 * @return String contendo o identificador do projeto.
	 */
	public String getProjectName(){
	    return StringUtil.trim(this.environment.getOptions().get("projectName"));
	}
	
    /**
     * Retorna o diretório de armazenamento do projeto contendo as anotações a 
     * serem processadas.
     * 
     * @return String contendo o diretório de armazenamento do projeto.
     */
	public String getProjectDir(){
        String  projectName = getProjectName();
        String  projectDir  = new File("").getAbsolutePath();
        Integer pos         = projectDir.lastIndexOf(projectName);
	 
        if(pos >= 0)
            projectDir = projectDir.substring(0, pos + projectName.length());

        if(!projectDir.endsWith(StringUtil.getDirectorySeparator()))
            projectDir = projectDir.concat(StringUtil.getDirectorySeparator());
        
        return projectDir;
	}
	
	public String getProjectResourcesDir(){
        String projectResourcesDir = getProjectDir();
        
        projectResourcesDir = projectResourcesDir.concat(ProjectConstants.DEFAULT_RESOURCES_DIR);

        return projectResourcesDir;
	}

    /**
	 * Inicializa processamento.
	 * 
	 * @throws InvalidResourceException 
	 */
	private void initialize() throws InvalidResourceException{
        AuditorResourceLoader auditorResourceLoader = new AuditorResourceLoader(getProjectResourcesDir(), AuditorConstants.DEFAULT_RESOURCE_ID);

        auditorResource = auditorResourceLoader.get(AuditorConstants.DEFAULT_GENERATE_CODE_RESOURCE_KEY);
	}
	
	/**
	 * @see javax.annotation.processing.AbstractProcessor#process(java.util.Set, javax.annotation.processing.RoundEnvironment)
	 */
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment environment){
        Set<? extends Element> declarations        = null;
        String                 className           = "";
        IAnnotationProcessor   annotationProcessor = null;
        
        if(annotations != null && annotations.size() > 0){
            for(TypeElement annotation : annotations){
                declarations = environment.getElementsAnnotatedWith(annotation);
                
                if(declarations != null && declarations.size() > 0){
                    
                    for(Element declaration : declarations){
                        className           = declaration.toString();
                        annotationProcessor = getAnnotationProcessor(className);
                            
                        if(annotationProcessor != null)
                            annotationProcessor.process();
                    }
                }
            }
        }
        
        return true;
    }
    
    public IAnnotationProcessor getAnnotationProcessor(String className){
        if(className.endsWith(Model.class.getSimpleName()) && 
           !className.startsWith(BaseModel.class.getSimpleName()) &&
           !className.equals(LoginSessionModel.class.getName()))
            return new ModelAnnotationProcessor(className, this);
        
        return null;
    }
}