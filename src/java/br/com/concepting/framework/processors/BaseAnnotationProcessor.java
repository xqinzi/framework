package br.com.concepting.framework.processors;

import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.processors.interfaces.IAnnotationProcessor;

/**
 * Classe que define a estrutura b�sica para os processadores de anota��o.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class BaseAnnotationProcessor implements IAnnotationProcessor{
    private Class<BaseModel>           declaration                = null;
    private AnnotationProcessorFactory annotationProcessorFactory = null;
    
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
     * @see br.com.concepting.framework.processors.interfaces.IAnnotationProcessor#process()
     */
    public void process(){
    }
}