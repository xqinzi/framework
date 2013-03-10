package br.com.concepting.framework.processors;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Date;

import br.com.concepting.framework.audit.Auditor;
import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.processors.constants.ProjectConstants;
import br.com.concepting.framework.processors.interfaces.IAnnotationProcessor;
import br.com.concepting.framework.util.StringUtil;

/**
 * Classe que define a estrutura b�sica para os processadores de anota��o.
 * 
 * @author fvilarinho
 * @since 4.0
 */
public class BaseAnnotationProcessor implements IAnnotationProcessor{
    private Class<BaseModel>           declaration                = null;
    private AnnotationProcessorFactory annotationProcessorFactory = null;
    private ModelInfo                  modelInfo                  = null;
    
    /**
     * Construtor - Inicializa objetos e/ou vari�veis internas.
     * 
     * @param declaration Classe contendo a anota��o a ser processada.
     * @param annotationProcessorFactory Inst�ncia do f�brica de processadores de anota��o.
     */
    public BaseAnnotationProcessor(Class declaration, AnnotationProcessorFactory annotationProcessorFactory){
        super();
        
        this.declaration                = declaration;
        this.annotationProcessorFactory = annotationProcessorFactory;
        this.modelInfo                  = ModelUtil.getModelInfo(this.declaration);
    }

    /**
     * Retorna a classe que possui a anota��o a ser processada.
     * 
     * @return Classe que possui a anota��o a ser processada.
     */
    public Class<BaseModel> getDeclaration(){
        return declaration;
    }

    /**
     * Define a classe que possui a anota��o a ser processada.
     * 
     * @param declaration Classe que possui a anota��o a ser processada.
     */
    public void setDeclaration(Class<BaseModel> declaration){
        this.declaration = declaration;
    }

    /**
     * Retorna as propriedades da classe que define o modelo de dados.
     * 
     * @return Inst�ncia contendo as propriedades da classe que define o
     * modelo de dados.
     */
    public ModelInfo getModelInfo(){
        return modelInfo;
    }

    /**
     * Define as propriedades da classe que define o modelo de dados.
     * 
     * @param modelInfo Inst�ncia contendo as propriedades da classe que define o
     * modelo de dados.
     */
    public void setModelInfo(ModelInfo modelInfo){
        this.modelInfo = modelInfo;
    }

    /**
     * Retorna a inst�ncia da f�brica de processadores de anota��o.
     * 
     * @return Inst�ncia da f�brica de processadores de anota��o.
     */
    public AnnotationProcessorFactory getAnnotationProcessorFactory(){
        return annotationProcessorFactory;
    }

    /**
     * Define a inst�ncia da f�brica de processadores de anota��o.
     * 
     * @param annotationProcessorFactory Inst�ncia da f�brica de processadores de anota��o.
     */
    public void setAnnotationProcessorFactory(AnnotationProcessorFactory annotationProcessorFactory){
        this.annotationProcessorFactory = annotationProcessorFactory;
    }

    /**
     * Inicia o processamento do modelo de dados.
     */
    public void process(){
        ExpressionProcessorUtil.addVariable(AttributeConstants.USER_KEY, System.getProperty("user.name"));
        ExpressionProcessorUtil.addVariable(AttributeConstants.NOW_KEY, new Date());
        
        StringBuilder templateFilesDirName = new StringBuilder();
        
        templateFilesDirName.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
        templateFilesDirName.append(modelInfo.getTemplateId());
        
        File templateFilesDir = new File(templateFilesDirName.toString());
        
        if(templateFilesDir.exists()){
            File           templateFiles[]    = templateFilesDir.listFiles();
            StringBuilder  templateMethodName = null;
            Auditor        auditor            = null;
            
            for(File templateFile : templateFiles){
                if(!templateFile.isDirectory() && templateFile.getName().endsWith(".xml")){
                    if(templateMethodName == null)
                        templateMethodName = new StringBuilder();
                    else
                        templateMethodName.delete(0, templateMethodName.length());
                    
                    templateMethodName.append("generate");
                    templateMethodName.append(StringUtil.split(StringUtil.capitalize(templateFile.getName()), ".")[0]);
                    
                    try{
                        Method method = getClass().getMethod(templateMethodName.toString());
                    
                        if(method != null){
                            auditor = new Auditor(getClass(), method, declaration, annotationProcessorFactory.getAuditorResource());
                            auditor.start();
                        
                            method.invoke(this);
                        
                            auditor.end();
                        }
                    }
                    catch(Throwable e){
                        if(auditor != null)
                            auditor.end(e);
                    }
                }
            }
        }
    }
}