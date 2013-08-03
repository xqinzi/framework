package br.com.concepting.framework.processors;

import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.processors.interfaces.IAnnotationProcessor;

/**
 * Classe que define a estrutura básica para os processadores de anotação.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public class BaseAnnotationProcessor implements IAnnotationProcessor{
    private Class<BaseModel>           declaration                = null;
    private AnnotationProcessorFactory annotationProcessorFactory = null;
    
    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param declaration Classe contendo a anotação a ser processada.
     * @param annotationProcessorFactory Instância do fábrica de processadores de anotação.
     */
    public BaseAnnotationProcessor(Class declaration, AnnotationProcessorFactory annotationProcessorFactory){
        super();
        
        this.declaration                = declaration;
        this.annotationProcessorFactory = annotationProcessorFactory;
    }

    /**
     * Retorna a classe que possui a anotação a ser processada.
     * 
     * @return Classe que possui a anotação a ser processada.
     */
    public Class<BaseModel> getDeclaration(){
        return declaration;
    }

    /**
     * Define a classe que possui a anotação a ser processada.
     * 
     * @param declaration Classe que possui a anotação a ser processada.
     */
    public void setDeclaration(Class<BaseModel> declaration){
        this.declaration = declaration;
    }

    /**
     * Retorna a instância da fábrica de processadores de anotação.
     * 
     * @return Instância da fábrica de processadores de anotação.
     */
    public AnnotationProcessorFactory getAnnotationProcessorFactory(){
        return annotationProcessorFactory;
    }

    /**
     * Define a instância da fábrica de processadores de anotação.
     * 
     * @param annotationProcessorFactory Instância da fábrica de processadores de anotação.
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