package br.com.concepting.framework.processors;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;

import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.annotations.Property;

import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;

/**
 * Classe que define a factory de processamento de anotações a ser executada pelo ferramenta apt.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class AnnotationProcessorFactory implements com.sun.mirror.apt.AnnotationProcessorFactory{
	private static Collection<String> supportedAnnotationTypes = null;
	private static Collection<String> supportedOptions         = null;

	static{
		supportedAnnotationTypes = new LinkedList<String>();
		supportedAnnotationTypes.add(Model.class.getName());
        supportedAnnotationTypes.add(Property.class.getName());
        supportedAnnotationTypes.add(SuppressWarnings.class.getName());
	}

	/**
	 * @see com.sun.mirror.apt.AnnotationProcessorFactory#getProcessorFor(java.util.Set, com.sun.mirror.apt.AnnotationProcessorEnvironment)
	 */
	public com.sun.mirror.apt.AnnotationProcessor getProcessorFor(Set<AnnotationTypeDeclaration> declarations, AnnotationProcessorEnvironment environment){
		return new AnnotationProcessor(environment);
	}

	/**
	 * @see com.sun.mirror.apt.AnnotationProcessorFactory#supportedAnnotationTypes()
	 */
	public Collection<String> supportedAnnotationTypes(){
		return supportedAnnotationTypes;
	}

	/**
	 * @see com.sun.mirror.apt.AnnotationProcessorFactory#supportedOptions()
	 */
	public Collection<String> supportedOptions(){
		return supportedOptions;
	}
}