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
import javax.tools.Diagnostic.Kind;

import br.com.concepting.framework.audit.constants.AuditorConstants;
import br.com.concepting.framework.audit.resource.AuditorResource;
import br.com.concepting.framework.audit.resource.AuditorResourceLoader;
import br.com.concepting.framework.constants.ProjectConstants;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.processors.ModelAnnotationProcessor;
import br.com.concepting.framework.processors.interfaces.IAnnotationProcessor;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.util.StringUtil;

/**
 * Classe que implementa o processamento das anota��es dos modelos de dados.
 * Utilizado para gera��o de c�digo a partir de templates.
 * 
 * @author fvilarinho
 * @since 1.0
 */
@SupportedAnnotationTypes({"br.com.concepting.framework.model.annotations.Model"})
public class AnnotationProcessorFactory extends AbstractProcessor{
	private AuditorResource       auditorResource = null;
	private ProcessingEnvironment environment     = null;
	
	/**
	 * Retorna a inst�ncia contendo as propriedades de auditoria a ser utilizado.
	 * 
	 * @return Inst�ncia contendo as propriedades de auditoria.
	 */
	public AuditorResource getAuditorResource(){
        return auditorResource;
    }

    /**
     * Define a inst�ncia contendo as propriedades de auditoria a ser utilizado.
     * 
     * @param auditorResource Inst�ncia contendo as propriedades de auditoria.
     */
    public void setAuditorResource(AuditorResource auditorResource){
        this.auditorResource = auditorResource;
    }

    /**
     * Retorna a inst�ncia contendo as propriedades de ambiente de execu��o
     * do processamento de anota��o.
     * 
     * @return Inst�ncia contendo as propriedades de ambiente.
     */
    public ProcessingEnvironment getEnvironment(){
        return environment;
    }

    /**
     * Define a inst�ncia contendo as propriedades de ambiente de execu��o
     * do processamento de anota��o.
     * 
     * @param environment Inst�ncia contendo as propriedades de ambiente.
     */
    public void setEnvironment(ProcessingEnvironment environment){
        this.environment = environment;
    }

    /**
	 * @see javax.annotation.processing.AbstractProcessor#getSupportedSourceVersion()
	 */
	public SourceVersion getSupportedSourceVersion() {
	    return SourceVersion.latestSupported();
	}
	
	/**
	 * Retorna o identificador do projeto contendo as anota��es a serem processadas.
	 * 
	 * @return String contendo o identificador do projeto.
	 */
	public String getProjectName(){
	    return StringUtil.trim(this.environment.getOptions().get("projectName"));
	}
	
    /**
     * Retorna o diret�rio de armazenamento do projeto contendo as anota��es a 
     * serem processadas.
     * 
     * @return String contendo o diret�rio de armazenamento do projeto.
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
	
    /**
     * Retorna o diret�rio de armazenamento das configura��es do projeto 
     * contendo as anota��es a serem processadas.
     * 
     * @return String contendo o diret�rio de armazenamento do projeto.
     */
	public String getProjectResourcesDir(){
        String projectResourcesDir = getProjectDir();
        
        projectResourcesDir = projectResourcesDir.concat(ProjectConstants.DEFAULT_RESOURCES_DIR);

        return projectResourcesDir;
	}

    /**
     * @see javax.annotation.processing.AbstractProcessor#init(javax.annotation.processing.ProcessingEnvironment)
     */
    public synchronized void init(ProcessingEnvironment environment){
        super.init(environment);
        
        this.environment = environment;
        
        try{
            AuditorResourceLoader auditorResourceLoader = new AuditorResourceLoader(getProjectResourcesDir(), AuditorConstants.DEFAULT_RESOURCE_ID);

            auditorResource = auditorResourceLoader.get(AuditorConstants.DEFAULT_GENERATE_CODE_RESOURCE_KEY);
        }
        catch(Throwable e){
            this.environment.getMessager().printMessage(Kind.ERROR, e.getMessage());
        }
    }
	
	/**
	 * @see javax.annotation.processing.AbstractProcessor#process(java.util.Set, javax.annotation.processing.RoundEnvironment)
	 */
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment environment){
        Set<? extends Element> declarations        = null;
        IAnnotationProcessor   annotationProcessor = null;
        Class                  declarationClass    = null;
        
        if(annotations != null && annotations.size() > 0){
            for(TypeElement annotation : annotations){
                declarations = environment.getElementsAnnotatedWith(annotation);
                
                if(declarations != null && declarations.size() > 0){
                    for(Element declaration : declarations){
                        try{
                            declarationClass    = Class.forName(declaration.toString());
                            annotationProcessor = getAnnotationProcessor(declarationClass);

                            if(annotationProcessor != null)
                                annotationProcessor.process();
                        }
                        catch(Throwable e){
                            this.environment.getMessager().printMessage(Kind.ERROR, e.getMessage());
                        }
                    }
                }
            }
        }
        
        return true;
    }
    
    /**
     * Retorna o processador de anota��o de uma classe espec�fica.
     * 
     * @param declaration Classe contendo a anota��o a ser processada.
     * @return Inst�ncia do processador de anota��es.
     */
    public IAnnotationProcessor getAnnotationProcessor(Class declaration){
        String className = declaration.getName();
        
        if(className.endsWith(Model.class.getSimpleName()) && 
           !className.startsWith(BaseModel.class.getSimpleName()) &&
           !className.equals(LoginSessionModel.class.getName()))
            return new ModelAnnotationProcessor(declaration, this);
        
        return null;
    }
}