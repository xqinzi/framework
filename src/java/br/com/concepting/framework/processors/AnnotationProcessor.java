package br.com.concepting.framework.processors;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import org.apache.commons.beanutils.MethodUtils;
import org.dom4j.DocumentType;
import org.dom4j.tree.DefaultDocumentType;

import br.com.concepting.framework.audit.Auditor;
import br.com.concepting.framework.audit.constants.AuditorConstants;
import br.com.concepting.framework.audit.resource.AuditorResource;
import br.com.concepting.framework.audit.resource.AuditorResourceLoader;
import br.com.concepting.framework.audit.types.AuditorStatusType;
import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.model.BaseModel;
import br.com.concepting.framework.model.annotations.Model;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.persistence.constants.PersistenceConstants;
import br.com.concepting.framework.persistence.util.PersistenceUtil;
import br.com.concepting.framework.processors.constants.ProjectConstants;
import br.com.concepting.framework.resource.SystemResource;
import br.com.concepting.framework.resource.SystemResourceLoader;
import br.com.concepting.framework.resource.constants.ResourceConstants;
import br.com.concepting.framework.resource.exceptions.InvalidResourceException;
import br.com.concepting.framework.security.constants.SecurityConstants;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.service.BaseRemoteService;
import br.com.concepting.framework.service.BaseService;
import br.com.concepting.framework.service.annotations.Service;
import br.com.concepting.framework.service.interfaces.IService;
import br.com.concepting.framework.service.types.ServiceType;
import br.com.concepting.framework.service.util.ServiceUtil;
import br.com.concepting.framework.util.FileUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.XmlReader;
import br.com.concepting.framework.util.XmlWriter;
import br.com.concepting.framework.util.helpers.JavaIndent;
import br.com.concepting.framework.util.helpers.XmlNode;
import br.com.concepting.framework.web.constants.SystemConstants;
import br.com.concepting.framework.web.form.util.ActionFormUtil;
import br.com.concepting.framework.web.helpers.JSPIndent;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;

/**
 * Classe que implementa o processamento das anotações dos modelos de dados.
 * Utilizado para geração de código a partir de templates.
 * 
 * @author fvilarinho
 * @since 1.0
 */
@SupportedAnnotationTypes({"br.com.concepting.framework.model.annotations.Model"})
public class AnnotationProcessor extends AbstractProcessor{
	private AuditorResource       auditorResource = null;
	private ProcessingEnvironment environment     = null;

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
	
	private String getProjectName(){
	    return StringUtil.trim(this.environment.getOptions().get("projectName"));
	}
	
	private String getProjectDir(){
        String  projectName = getProjectName();
        String  projectDir  = new File("").getAbsolutePath();
        Integer pos         = projectDir.lastIndexOf(projectName);
	 
        if(pos >= 0)
            projectDir = projectDir.substring(0, pos + projectName.length());

        if(!projectDir.endsWith(StringUtil.getDirectorySeparator()))
            projectDir = projectDir.concat(StringUtil.getDirectorySeparator());
        
        return projectDir;
	}
	
	private String getProjectResourcesDir(){
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
        try{
            Set<? extends Element> declarations = null;
            
            if(annotations != null && annotations.size() > 0){
                for(TypeElement annotation : annotations){
                    declarations = environment.getElementsAnnotatedWith(annotation);
                    
                    if(declarations != null && declarations.size() > 0){
                        String className = "";
                        
                        for(Element declaration : declarations){
                            className = declaration.toString();
                                                      
                            if(className.endsWith(Model.class.getSimpleName()) && 
                               !className.startsWith(BaseModel.class.getSimpleName()) &&
                               !className.equals(LoginSessionModel.class.getName())){
                               new AnnotationProcessorVisitor(declaration);
                            }
                        }
                    }
                }
            }
            
            return true;
        }
        catch(Throwable e){
            e.printStackTrace();
            
            return false;
        }
    }

	/**
	 * Classe que define o scripting para o processamento das anotações.
	 * 
	 * @author fvilarinho
	 * @since 1.0
	 */
	public class AnnotationProcessorVisitor{
		private Class<BaseModel> declaration = null;
		private String           templateId  = "";
		private ModelInfo        modelInfo   = null;
		
        private AnnotationProcessorVisitor(Element declaration) throws Throwable{
			super();
			
            this.declaration = (Class<BaseModel>)Class.forName(declaration.toString());
            this.modelInfo   = ModelUtil.getModelInfo(this.declaration);
			
            if(this.modelInfo != null){
                this.templateId = this.modelInfo.getTemplateId();
                
                process();
            }
		}

		/**
		 * Inicia o processamento do modelo de dados.
		 *
		 * @throws Throwable
		 */
		protected void process() throws Throwable{
			ExpressionProcessorUtil.addVariable(AttributeConstants.USER_KEY, System.getProperty("user.name"));
			ExpressionProcessorUtil.addVariable(AttributeConstants.NOW_KEY, new Date());
			
			StringBuilder templateFilesDirName = new StringBuilder();
			
			templateFilesDirName.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
			templateFilesDirName.append(templateId);
			
			File templateFilesDir = new File(templateFilesDirName.toString());
			
			if(templateFilesDir.exists()){
				File           templateFiles[]    = templateFilesDir.listFiles();
				StringBuilder  templateMethodName = null;
				
				for(File templateFile : templateFiles){
					if(!templateFile.isDirectory() && templateFile.getName().endsWith(".xml")){
						if(templateMethodName == null)
							templateMethodName = new StringBuilder();
						else
							templateMethodName.delete(0, templateMethodName.length());
						
						templateMethodName.append("generate");
						templateMethodName.append(StringUtil.split(StringUtil.capitalize(templateFile.getName()), ".")[0]);
						
						MethodUtils.invokeMethod(this, templateMethodName.toString(), null);
					}
				}
			}
		}

		/**
		 * Executa o script para geração de código para a classe que implementa a persistência.
		 * 
		 * @throws Throwable
		 */
		public void generatePersistenceClass() throws Throwable{
			Boolean generateMessage = false;
			Auditor auditor         = new Auditor(declaration.getName(), auditorResource);

			try{
				String        persistenceClassName     = PersistenceUtil.getPersistenceClassNameByModel(declaration);
				StringBuilder persistenceClassFileName = new StringBuilder();

				persistenceClassFileName.append(ProjectConstants.DEFAULT_JAVA_DIR);
				persistenceClassFileName.append(StringUtil.replaceAll(persistenceClassName, ".", StringUtil.getDirectorySeparator()));
				persistenceClassFileName.append(".java");
				
				File persistenceClassFile = new File(persistenceClassFileName.toString());
				
				if(modelInfo.getMappedRepositoryId().length() > 0 || modelInfo.getGeneratePersistence()){
					if(!persistenceClassFile.exists()){
						generateMessage = true;

						StringBuilder persistenceClassTemplateFileName = new StringBuilder();

						persistenceClassTemplateFileName.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
						persistenceClassTemplateFileName.append(templateId);
						persistenceClassTemplateFileName.append(StringUtil.getDirectorySeparator());
						persistenceClassTemplateFileName.append(ProjectConstants.DEFAULT_PERSISTENCE_CLASS_TEMPLATE_ID);
						
						File             persistenceClassTemplateFile    = new File(persistenceClassTemplateFileName.toString());
						XmlReader        persistenceClassTemplateReader  = new XmlReader(persistenceClassTemplateFile);
						XmlNode          persistenceClassTemplateContent = persistenceClassTemplateReader.getRoot();
						String           encoding                        = persistenceClassTemplateReader.getEncoding(); 
						ProcessorFactory processorFactory                = ProcessorFactory.getInstance();
						BaseProcessor    processor                       = processorFactory.getProcessor(modelInfo, persistenceClassTemplateContent);
						String           persistenceClassContent         = StringUtil.indent(processor.process(), JavaIndent.getRules());
						
						persistenceClassContent = StringUtil.decode(persistenceClassContent);
						
						FileUtil.toTextFile(persistenceClassFileName.toString(), persistenceClassContent, encoding);

						auditor.info(AuditorStatusType.PROCESSED);
					}
				}	
				else{
					if(persistenceClassFile.exists()){
						generateMessage = true;

						persistenceClassFile.delete();

						auditor.info(AuditorStatusType.PROCESSED);
					}
				}
			}
			catch(Throwable e){
				if(generateMessage)
					auditor.info(AuditorStatusType.PROCESSED_WITH_ERROR);

				auditor.error(e);
			}
		}

		/**
		 * Executa o script para geração de código para interface da classe que implementa a 
		 * persistência.
		 * 
		 * @throws Throwable
		 */
		public void generatePersistenceInterface() throws Throwable{
			Boolean generateMessage = false;
			Auditor auditor         = new Auditor(declaration.getName(), auditorResource);

			try{
				String        persistenceInterfaceClassName     = PersistenceUtil.getPersistenceInterfaceClassNameByModel(declaration);
				StringBuilder persistenceInterfaceClassFileName = new StringBuilder();

				persistenceInterfaceClassFileName.append(ProjectConstants.DEFAULT_JAVA_DIR);
				persistenceInterfaceClassFileName.append(StringUtil.replaceAll(persistenceInterfaceClassName, ".", StringUtil.getDirectorySeparator()));
				persistenceInterfaceClassFileName.append(".java");

				File persistenceInterfaceFile = new File(persistenceInterfaceClassFileName.toString());

				if(modelInfo.getMappedRepositoryId().length() > 0 || modelInfo.getGeneratePersistence()){
					if(!persistenceInterfaceFile.exists()){
						generateMessage = true;

						StringBuilder persistenceInterfaceClassTemplateFileName = new StringBuilder();

						persistenceInterfaceClassTemplateFileName.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
						persistenceInterfaceClassTemplateFileName.append(templateId);
						persistenceInterfaceClassTemplateFileName.append(StringUtil.getDirectorySeparator());
						persistenceInterfaceClassTemplateFileName.append(ProjectConstants.DEFAULT_PERSISTENCE_INTERFACE_TEMPLATE_ID);

						File             persistenceInterfaceTemplate        = new File(persistenceInterfaceClassTemplateFileName.toString());
						XmlReader        persistenceInterfaceTemplateReader  = new XmlReader(persistenceInterfaceTemplate);
						XmlNode          persistenceInterfaceTemplateContent = persistenceInterfaceTemplateReader.getRoot();
						String           encoding                            = persistenceInterfaceTemplateReader.getEncoding(); 
						ProcessorFactory processorFactory                    = ProcessorFactory.getInstance();
						BaseProcessor    processor                           = processorFactory.getProcessor(modelInfo, persistenceInterfaceTemplateContent);
						String           persistenceInterfaceContent         = StringUtil.indent(processor.process(), JavaIndent.getRules());
						
						persistenceInterfaceContent = StringUtil.decode(persistenceInterfaceContent);

						FileUtil.toTextFile(persistenceInterfaceClassFileName.toString(), persistenceInterfaceContent, encoding);

						auditor.info(AuditorStatusType.PROCESSED);
					}
				}
				else{
					if(persistenceInterfaceFile.exists()){
						generateMessage = true;

						persistenceInterfaceFile.delete();

						auditor.info(AuditorStatusType.PROCESSED);
					}
				}
			}
			catch(Throwable e){
				if(generateMessage)
					auditor.info(AuditorStatusType.PROCESSED_WITH_ERROR);

				auditor.error(e);
			}
		}

		/**
		 * Executa o script para geração de código do mapeamento de persistência.
		 * 
		 * @throws Throwable
		 */
		public void generatePersistenceMapping() throws Throwable{
			Boolean generateMessage = false;
			Auditor auditor         = new Auditor(declaration.getName(), auditorResource);

			try{
                StringBuilder persistenceResourcesFileName = new StringBuilder();

                persistenceResourcesFileName.append(ProjectConstants.DEFAULT_RESOURCES_DIR);
                persistenceResourcesFileName.append(PersistenceConstants.DEFAULT_RESOURCE_ID);
                
                File      persistenceResourcesFile     = new File(persistenceResourcesFileName.toString());
                XmlReader persistenceResourcesReader   = new XmlReader(persistenceResourcesFile);
                XmlNode   persistenceResourcesContent  = persistenceResourcesReader.getRoot();
                XmlNode   persistenceResourcesMappings = persistenceResourcesContent.getNode("mappings");
                
                if(persistenceResourcesMappings == null){
                    persistenceResourcesMappings = new XmlNode("mappings");
                    
                    persistenceResourcesContent.addChildNode(persistenceResourcesMappings);
                }

                String        persistenceMappingName     = declaration.getName();
				StringBuilder persistenceMappingFileName = new StringBuilder();

				persistenceMappingFileName.append(ProjectConstants.DEFAULT_RESOURCES_DIR);
				persistenceMappingFileName.append(PersistenceConstants.DEFAULT_MAPPINGS_DIR);
                persistenceMappingFileName.append(persistenceMappingName);
                persistenceMappingFileName.append(".hbm.xml");
				
                XmlNode persistenceResourceMapping = new XmlNode("mapping", persistenceMappingName);
                File    persistenceMappingFile     = new File(persistenceMappingFileName.toString());

				if(modelInfo.getMappedRepositoryId().length() > 0){
                    if(!persistenceMappingFile.exists()){
    					StringBuilder persistenceMappingTemplateFileName = new StringBuilder();
    
    					persistenceMappingTemplateFileName.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
    					persistenceMappingTemplateFileName.append(templateId);
    					persistenceMappingTemplateFileName.append(StringUtil.getDirectorySeparator());
    					persistenceMappingTemplateFileName.append(ProjectConstants.DEFAULT_PERSISTENCE_MAPPING_TEMPLATE_ID);
    
    					File             persistenceMappingTemplateFile    = new File(persistenceMappingTemplateFileName.toString());
    					XmlReader        persistenceMappingTemplateReader  = new XmlReader(persistenceMappingTemplateFile);
    					XmlNode          persistenceMappingTemplateContent = persistenceMappingTemplateReader.getRoot();
    					ProcessorFactory processorFactory                  = ProcessorFactory.getInstance();
    					BaseProcessor    processor                         = processorFactory.getProcessor(modelInfo, persistenceMappingTemplateContent);
                        String           persistenceMappingContent         = processor.process();
                        DocumentType     persistenceMappingDocumentType    = new DefaultDocumentType();
                        String           persistenceMappingEncoding        = persistenceMappingTemplateReader.getEncoding();
                        
                        persistenceMappingDocumentType.setName("hibernate-mapping");
                        persistenceMappingDocumentType.setPublicID("-//Hibernate/Hibernate Mapping DTD 3.0//EN");
                        persistenceMappingDocumentType.setSystemID("http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd");
                        
    					XmlWriter persistenceMappingTemplateWriter = new XmlWriter(persistenceMappingFile, persistenceMappingDocumentType, persistenceMappingEncoding);
    
    					persistenceMappingTemplateWriter.write(persistenceMappingContent);
                    }
    					
					List<XmlNode> persistenceMappings = persistenceResourcesMappings.getChildNodes();
					Boolean       found               = false;
					
					if(persistenceMappings != null && persistenceMappings.size() > 0){
					    for(XmlNode persistenceMapping : persistenceMappings){
					        if(persistenceMapping.getValue().equals(persistenceMappingName)){
					            found = true;
					            
					            break;
					        }
					    }
					}
					
					if(!found){
                        generateMessage = true;

					    persistenceResourcesMappings.addChildNode(persistenceResourceMapping);
					}
				}
				else{
					if(persistenceMappingFile.exists()){
						generateMessage = true;
						
						List<XmlNode> persistenceMappings = persistenceResourcesMappings.getChildNodes();
	                    
	                    if(persistenceMappings != null && persistenceMappings.size() > 0){
	                        XmlNode persistenceMapping = null;
	                        
	                        for(int cont = 0 ; cont < persistenceMappings.size() ; cont++){
	                            persistenceMapping = persistenceMappings.get(cont);
	                            
	                            if(persistenceMapping.getValue().equals(persistenceMappingName)){
	                                persistenceMappings.remove(cont);
	                                
	                                cont--;
	                                
	                                break;
	                            }
	                        }
	                    }

                        persistenceResourcesMappings.setChildNodes(persistenceMappings);
					}
				}
				
				if(generateMessage){
                    XmlWriter persistenceResourcesWriter = new XmlWriter(persistenceResourcesFile, persistenceResourcesReader.getDocumentType(), persistenceResourcesReader.getEncoding());
                    
                    persistenceResourcesWriter.write(persistenceResourcesContent);
                    
                    auditor.info(AuditorStatusType.PROCESSED);
				}
			}
			catch(Throwable e){
				if(generateMessage)
					auditor.info(AuditorStatusType.PROCESSED_WITH_ERROR);

				auditor.error(e);
			}
		}

		/**
		 * Executa o script para geração de código da classe que implementa a regra de negócio.
		 * 
		 * @throws Throwable
		 */
		public void generateServiceClass() throws Throwable{
			Boolean generateMessage = false;
			Auditor auditor         = new Auditor(declaration.getName(), auditorResource);

			try{
				String        serviceClassName     = ServiceUtil.getServiceClassNameByModel(declaration);
				StringBuilder serviceClassFileName = new StringBuilder();

				serviceClassFileName.append(ProjectConstants.DEFAULT_JAVA_DIR);
				serviceClassFileName.append(StringUtil.replaceAll(serviceClassName, ".", StringUtil.getDirectorySeparator()));
				serviceClassFileName.append(".java");

				File serviceClassFile = new File(serviceClassFileName.toString());

				if(modelInfo.getUseCase().length() > 0 || modelInfo.getMappedRepositoryId().length() > 0 || modelInfo.getGenerateService()){
					String serviceClassContent = "";
					
					if(!serviceClassFile.exists()){
						generateMessage = true;

						StringBuilder serviceClassTemplateFileName = new StringBuilder();

						serviceClassTemplateFileName.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
						serviceClassTemplateFileName.append(templateId);
						serviceClassTemplateFileName.append(StringUtil.getDirectorySeparator());
						serviceClassTemplateFileName.append(ProjectConstants.DEFAULT_SERVICE_CLASS_TEMPLATE_ID);

						File             serviceClassTemplateFile    = new File(serviceClassTemplateFileName.toString());
						XmlReader        serviceClassTemplateReader  = new XmlReader(serviceClassTemplateFile);
						XmlNode          serviceClassTemplateContent = serviceClassTemplateReader.getRoot();
						String           encoding                    = serviceClassTemplateReader.getEncoding(); 
						ProcessorFactory processorFactory            = ProcessorFactory.getInstance();
						BaseProcessor    processor                   = processorFactory.getProcessor(modelInfo, serviceClassTemplateContent);

						serviceClassContent = StringUtil.indent(processor.process(), JavaIndent.getRules());
						serviceClassContent = StringUtil.decode(serviceClassContent);

						FileUtil.toTextFile(serviceClassFileName.toString(), serviceClassContent, encoding);

						auditor.info(AuditorStatusType.PROCESSED);
					}
					else{
						Class<IService> serviceInterfaceClass = null;
						Service         serviceAnnotation     = null;
						Boolean         isRemoteService       = false;

						try{
							serviceInterfaceClass = ServiceUtil.getServiceInterfaceClassByModel(declaration);
							serviceAnnotation     = (Service)serviceInterfaceClass.getAnnotation(Service.class);
						}
						catch(Throwable e){
						}

						if(serviceAnnotation != null && serviceAnnotation.name().length() > 0 && (serviceAnnotation.type() == ServiceType.STATEFUL || serviceAnnotation.type() == ServiceType.STATELESS))
							isRemoteService = true;

						serviceClassContent = FileUtil.fromTextFile(serviceClassFileName.toString());

						if(isRemoteService){
							if(serviceClassContent.contains(BaseService.class.getSimpleName())){
								generateMessage = true;

								serviceClassContent = StringUtil.replaceAll(serviceClassContent, BaseService.class.getSimpleName(), BaseRemoteService.class.getSimpleName());
							}
						}
						else{
							if(serviceClassContent.contains(BaseRemoteService.class.getSimpleName())){
								generateMessage = true;

								serviceClassContent = StringUtil.replaceAll(serviceClassContent, BaseRemoteService.class.getSimpleName(), BaseService.class.getSimpleName());
							}
						}

						if(generateMessage){
							FileUtil.toTextFile(serviceClassFileName.toString(), serviceClassContent);

							auditor.info(AuditorStatusType.PROCESSED);
						}
					}
				}
				else{
					if(serviceClassFile.exists()){
						generateMessage = true;

						serviceClassFile.delete();

						auditor.info(AuditorStatusType.PROCESSED);
					}
				}
			}
			catch(Throwable e){
				if(generateMessage)
					auditor.info(AuditorStatusType.PROCESSED_WITH_ERROR);

				auditor.error(e);
			}
		}

		/**
		 * Executa o script para geração de código da interface da classe que implementa a regra de negócio.
		 * 
		 * @throws Throwable
		 */
		public void generateServiceInterface() throws Throwable{
			Boolean generateMessage = false;
			Auditor auditor         = new Auditor(declaration.getName(), auditorResource);

			try{
				String        serviceInterfaceClassName     = ServiceUtil.getServiceInterfaceClassNameByModel(declaration);
				StringBuilder serviceInterfaceClassFileName = new StringBuilder();

				serviceInterfaceClassFileName.append(ProjectConstants.DEFAULT_JAVA_DIR);
				serviceInterfaceClassFileName.append(StringUtil.replaceAll(serviceInterfaceClassName, ".", StringUtil.getDirectorySeparator()));
				serviceInterfaceClassFileName.append(".java");

				File serviceInterfaceFile = new File(serviceInterfaceClassFileName.toString());

				if(modelInfo.getUseCase().length() > 0 || modelInfo.getMappedRepositoryId().length() > 0 || modelInfo.getGenerateService()){
					if(!serviceInterfaceFile.exists()){
						generateMessage = true;

						StringBuilder serviceInterfaceClassTemplateFileName = new StringBuilder();

						serviceInterfaceClassTemplateFileName.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
						serviceInterfaceClassTemplateFileName.append(templateId);
						serviceInterfaceClassTemplateFileName.append(StringUtil.getDirectorySeparator());
						serviceInterfaceClassTemplateFileName.append(ProjectConstants.DEFAULT_SERVICE_INTERFACE_TEMPLATE_ID);

						File             serviceInterfaceTemplateFile    = new File(serviceInterfaceClassTemplateFileName.toString());
						XmlReader        serviceInterfaceTemplateReader  = new XmlReader(serviceInterfaceTemplateFile);
						XmlNode          serviceInterfaceTemplateContent = serviceInterfaceTemplateReader.getRoot();
						String           encoding                        = serviceInterfaceTemplateReader.getEncoding();
						ProcessorFactory processorFactory                = ProcessorFactory.getInstance();
						BaseProcessor    processor                       = processorFactory.getProcessor(modelInfo, serviceInterfaceTemplateContent);
						String           serviceInterfaceContent         = StringUtil.indent(processor.process(), JavaIndent.getRules());
						
						serviceInterfaceContent = StringUtil.decode(serviceInterfaceContent);

						FileUtil.toTextFile(serviceInterfaceClassFileName.toString(), serviceInterfaceContent, encoding);

						auditor.info(AuditorStatusType.PROCESSED);
					}
				}
				else{
					if(serviceInterfaceFile.exists()){
						generateMessage = true;

						serviceInterfaceFile.delete();

						auditor.info(AuditorStatusType.PROCESSED);
					}
				}
			}
			catch(Throwable e){
				if(generateMessage)
					auditor.info(AuditorStatusType.PROCESSED_WITH_ERROR);

				auditor.error(e);
			}
		}

		/**
		 * Executa o script para geração de código da interface home da classe que implementa a regra de negócio.
		 * 
		 * @throws Throwable
		 */
		public void generateServiceHomeInterface() throws Throwable{
			Boolean generateMessage = false;
			Auditor auditor         = new Auditor(declaration.getName(), auditorResource);

			try{
				String        serviceHomeInterfaceClassName     = ServiceUtil.getServiceHomeInterfaceClassNameByModel(declaration);
				StringBuilder serviceHomeInterfaceClassFileName = new StringBuilder();

				serviceHomeInterfaceClassFileName.append(ProjectConstants.DEFAULT_JAVA_DIR);
				serviceHomeInterfaceClassFileName.append(StringUtil.replaceAll(serviceHomeInterfaceClassName, ".", StringUtil.getDirectorySeparator()));
				serviceHomeInterfaceClassFileName.append(".java");

				File serviceHomeInterfaceFile = new File(serviceHomeInterfaceClassFileName.toString());

				if(modelInfo.getUseCase().length() > 0 || modelInfo.getMappedRepositoryId().length() > 0 || modelInfo.getGenerateService()){
					Boolean         isRemoteService       = false;
					Class<IService> serviceInterfaceClass = null;
					Service         serviceAnnotation     = null;

					try{
						serviceInterfaceClass = ServiceUtil.getServiceInterfaceClassByModel(declaration);
						serviceAnnotation     = (Service)serviceInterfaceClass.getAnnotation(Service.class);
					}
					catch(Throwable e){
					}

                    if(serviceAnnotation != null && serviceAnnotation.name().length() > 0 && (serviceAnnotation.type() == ServiceType.STATEFUL || serviceAnnotation.type() == ServiceType.STATELESS))
						isRemoteService = true;
					
					if(isRemoteService && !serviceHomeInterfaceFile.exists()){
						generateMessage = true;

						StringBuilder serviceHomeInterfaceClassTemplateFileName = new StringBuilder();

						serviceHomeInterfaceClassTemplateFileName.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
						serviceHomeInterfaceClassTemplateFileName.append(templateId);
						serviceHomeInterfaceClassTemplateFileName.append(StringUtil.getDirectorySeparator());
						serviceHomeInterfaceClassTemplateFileName.append(ProjectConstants.DEFAULT_SERVICE_HOME_INTERFACE_TEMPLATE_ID);

						File             serviceHomeInterfaceTemplateFile    = new File(serviceHomeInterfaceClassTemplateFileName.toString());
						XmlReader        serviceHomeInterfaceTemplateReader  = new XmlReader(serviceHomeInterfaceTemplateFile);
						XmlNode          serviceHomeInterfaceTemplateContent = serviceHomeInterfaceTemplateReader.getRoot();
						String           encoding                            = serviceHomeInterfaceTemplateReader.getEncoding();
						ProcessorFactory processorFactory                    = ProcessorFactory.getInstance();
						BaseProcessor    processor                           = processorFactory.getProcessor(modelInfo, serviceHomeInterfaceTemplateContent);
						String           serviceHomeInterfaceContent         = StringUtil.indent(processor.process(), JavaIndent.getRules());
						
						serviceHomeInterfaceContent = StringUtil.decode(serviceHomeInterfaceContent);
						
						FileUtil.toTextFile(serviceHomeInterfaceClassFileName.toString(), serviceHomeInterfaceContent, encoding);

						auditor.info(AuditorStatusType.PROCESSED);
					}
					else if(!isRemoteService && serviceHomeInterfaceFile.exists()) {
						generateMessage = true;

						serviceHomeInterfaceFile.delete();

						auditor.info(AuditorStatusType.PROCESSED);
					}
				}
				else{
					if(serviceHomeInterfaceFile.exists()){
						generateMessage = true;

						serviceHomeInterfaceFile.delete();

						auditor.info(AuditorStatusType.PROCESSED);
					}
				}
			}
			catch(Throwable e){
				if(generateMessage)
					auditor.info(AuditorStatusType.PROCESSED_WITH_ERROR);

				auditor.error(e);
			}
		}

		/**
		 * Executa o script para geração de código da interface remota da classe que implementa a regra de negócio.
		 * 
		 * @throws Throwable
		 */
		public void generateServiceRemoteInterface() throws Throwable{
			Boolean generateMessage = false;
			Auditor auditor         = new Auditor(declaration.getName(), auditorResource);

			try{
				String        serviceRemoteInterfaceClassName     = ServiceUtil.getServiceRemoteInterfaceClassNameByModel(declaration);
				StringBuilder serviceRemoteInterfaceClassFileName = new StringBuilder();

				serviceRemoteInterfaceClassFileName.append(ProjectConstants.DEFAULT_JAVA_DIR);
				serviceRemoteInterfaceClassFileName.append(StringUtil.replaceAll(serviceRemoteInterfaceClassName, ".", StringUtil.getDirectorySeparator()));
				serviceRemoteInterfaceClassFileName.append(".java");

				File serviceRemoteInterfaceFile = new File(serviceRemoteInterfaceClassFileName.toString());

				if(modelInfo.getUseCase().length() > 0 || modelInfo.getMappedRepositoryId().length() > 0 || modelInfo.getGenerateService()){
					Boolean         isRemoteService       = false;
					Class<IService> serviceInterfaceClass = null;
					Service         serviceAnnotation     = null;

					try{
						serviceInterfaceClass = ServiceUtil.getServiceInterfaceClassByModel(declaration);
						serviceAnnotation     = (Service)serviceInterfaceClass.getAnnotation(Service.class);
					}
					catch(Throwable e){
					}

                    if(serviceAnnotation != null && serviceAnnotation.name().length() > 0 && (serviceAnnotation.type() == ServiceType.STATEFUL || serviceAnnotation.type() == ServiceType.STATELESS))
						isRemoteService = true;
					
					if(isRemoteService && !serviceRemoteInterfaceFile.exists()){
						generateMessage = true;

						StringBuilder serviceRemoteInterfaceClassTemplateFileName = new StringBuilder();

						serviceRemoteInterfaceClassTemplateFileName.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
						serviceRemoteInterfaceClassTemplateFileName.append(templateId);
						serviceRemoteInterfaceClassTemplateFileName.append(StringUtil.getDirectorySeparator());
						serviceRemoteInterfaceClassTemplateFileName.append(ProjectConstants.DEFAULT_SERVICE_REMOTE_INTEFACE_TEMPLATE_ID);

						File             serviceRemoteInterfaceTemplateFile    = new File(serviceRemoteInterfaceClassTemplateFileName.toString());
						XmlReader        serviceRemoteInterfaceTemplateReader  = new XmlReader(serviceRemoteInterfaceTemplateFile);
						XmlNode          serviceRemoteInterfaceTemplateContent = serviceRemoteInterfaceTemplateReader.getRoot();
						String           encoding                              = serviceRemoteInterfaceTemplateReader.getEncoding();
						ProcessorFactory processorFactory                      = ProcessorFactory.getInstance();
						BaseProcessor    processor                             = processorFactory.getProcessor(modelInfo, serviceRemoteInterfaceTemplateContent);
						String           serviceRemoteInterfaceContent         = StringUtil.indent(processor.process(), JavaIndent.getRules());
						
						serviceRemoteInterfaceContent = StringUtil.decode(serviceRemoteInterfaceContent);

						FileUtil.toTextFile(serviceRemoteInterfaceClassFileName.toString(), serviceRemoteInterfaceContent, encoding);

						auditor.info(AuditorStatusType.PROCESSED);
					}
					else if(!isRemoteService && serviceRemoteInterfaceFile.exists()){
						generateMessage = true;

						serviceRemoteInterfaceFile.delete();

						auditor.info(AuditorStatusType.PROCESSED);
					}
				}
				else{
					if(serviceRemoteInterfaceFile.exists()){
						generateMessage = true;

						serviceRemoteInterfaceFile.delete();

						auditor.info(AuditorStatusType.PROCESSED);
					}
				}
			}
			catch(Throwable e){
				if(generateMessage)
					auditor.info(AuditorStatusType.PROCESSED_WITH_ERROR);

				auditor.error(e);
			}
		}

		/**
		 * Executa o script para geração de código do mapeamento da classe que implementa a regra de negócio.
		 * 
		 * @throws Throwable
		 */
        public void generateServiceMapping() throws Throwable{
			Boolean generateMessage = false;
			Auditor auditor         = new Auditor(declaration.getName(), auditorResource);

			try{
				Class<IService>    serviceInterfaceClass = null;
				Class<BaseService> serviceClass          = null;

				try{
					serviceClass          = ServiceUtil.getServiceClassByModel(declaration);
					serviceInterfaceClass = ServiceUtil.getServiceInterfaceClassByModel(declaration);
				}
				catch(Throwable e){
					return;
				}

				Stateless     statelessAnnotation  = serviceClass.getAnnotation(Stateless.class);
				Stateful      statefulAnnotation   = serviceClass.getAnnotation(Stateful.class);
                Service       serviceAnnotation    = (Service)serviceInterfaceClass.getAnnotation(Service.class);
                String        tempDir              = StringUtil.trim(System.getProperty("java.io.tmpdir"));
                XmlNode       rootNode             = null;
                XmlNode       childNode            = null;
                List<XmlNode> childNodes           = null;
                String        projectName          = getProjectName();
                File          hasRemoteServicesDir = new File(tempDir.concat(StringUtil.getDirectorySeparator()).concat(projectName));
                
                if(!hasRemoteServicesDir.exists())
                    hasRemoteServicesDir.mkdirs();
                
                File hasRemoteServicesFile = new File(hasRemoteServicesDir.getAbsolutePath().concat(StringUtil.getDirectorySeparator()).concat("remoteServices.xml"));
                
                if(hasRemoteServicesFile.exists()){
                    XmlReader reader = new XmlReader(hasRemoteServicesFile);
                    
                    rootNode = reader.getRoot();
                }
                
				if(statelessAnnotation != null || statefulAnnotation != null || (serviceAnnotation != null && serviceAnnotation.name().length() > 0 && (serviceAnnotation.type() == ServiceType.STATEFUL || serviceAnnotation.type() == ServiceType.STATELESS))){
				    if(rootNode == null){
				        rootNode  = new XmlNode("remoteServices");
				        childNode = new XmlNode("remoteService");
				        
				        childNode.addAttribute("interface", serviceInterfaceClass.getName());
				        childNode.addAttribute("class", serviceClass.getName());
				                
				        rootNode.addChildNode(childNode);
				    }
				    else{
				        childNodes = rootNode.getChildNodes();

                        Boolean hasRemoteService = false;

                        if(childNodes != null && childNodes.size() > 0){
        				    for(XmlNode remoteService : childNodes){
        				        if(remoteService.getValue().equals(serviceClass.getName())){
        				            hasRemoteService = true;
        				            
        				            break;
        				        }
        				    }
				        }
        				    
    				    if(!hasRemoteService){
                            childNode = new XmlNode("remoteService");
                            
                            childNode.addAttribute("interface", serviceInterfaceClass.getName());
                            childNode.addAttribute("class", serviceClass.getName());
    				        
                            rootNode.addChildNode(childNode);
    				    }
				    }
				}
				else{
				    if(rootNode != null){
				        childNodes = rootNode.getChildNodes();

    			        Boolean hasRemoteService = false;
                        XmlNode remoteService    = null;
        
                        if(childNodes != null && childNodes.size() > 0){
                            for(Integer cont = 0 ; cont < childNodes.size() ; cont++){
                                remoteService = childNodes.get(cont);
                                
                                if(remoteService.getValue().equals(serviceClass.getName())){
                                    hasRemoteService = true;
                                    
                                    break;
                                }
                            }
                        }
                            
                        if(hasRemoteService)
                            rootNode.removeChildNode(remoteService);
				    }
				}
				
				if(rootNode != null){
				    childNodes = rootNode.getChildNodes();
				
    				if(childNodes != null && childNodes.size() > 0){
        			    XmlWriter writer = new XmlWriter(hasRemoteServicesFile);
        
        			    writer.write(rootNode);
    				}
    				else{
    				    if(hasRemoteServicesFile.exists())
    				        hasRemoteServicesFile.delete();
    				}
				}
				
                StringBuilder serviceMappingTemplateFileName = new StringBuilder();

                serviceMappingTemplateFileName.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
                serviceMappingTemplateFileName.append(templateId);
                serviceMappingTemplateFileName.append(StringUtil.getDirectorySeparator());
                serviceMappingTemplateFileName.append(ProjectConstants.DEFAULT_SERVICE_MAPPING_TEMPLATE_ID);
                
                StringBuilder serviceMappingFileName = new StringBuilder();

                serviceMappingFileName.append(ProjectConstants.DEFAULT_EJB_MODULE_DIR);
                serviceMappingFileName.append(ProjectConstants.DEFAULT_EJB_DESCRIPTOR_FILE_ID);
                
                File      serviceMappingTemplateFile    = new File(serviceMappingTemplateFileName.toString());
                File      serviceMappingFile            = new File(serviceMappingFileName.toString());
				String    serviceHomeInterfaceId        = ServiceUtil.getServiceHomeInterfaceClassNameByModel(declaration);
				String    serviceRemoteInterfaceId      = ServiceUtil.getServiceRemoteInterfaceClassNameByModel(declaration);
				XmlReader serviceMappingTemplateReader  = new XmlReader((serviceMappingFile.exists() ? serviceMappingFile : serviceMappingTemplateFile));
				XmlNode   serviceMappingTemplateContent = serviceMappingTemplateReader.getRoot();
				
				if(!serviceMappingFile.exists())
				    serviceMappingTemplateContent = serviceMappingTemplateContent.getNode("ejb-jar");
				    
				XmlNode             serviceMappings       = serviceMappingTemplateContent.getNode("enterprise-beans");
				Collection<XmlNode> serviceMappingsItems  = serviceMappings.getChildNodes();
				XmlNode             serviceMapping        = serviceMappingsItems.iterator().next();
				XmlNode             newServiceMapping     = null;
				Boolean             hasServiceMapping     = false;
				Boolean             isFirstServiceMapping = false;

				for(XmlNode serviceMappingItem : serviceMappingsItems){
					serviceMapping = serviceMappingItem;
					
					if(serviceMappingItem.getNode("ejb-class") != null && serviceMappingItem.getNode("ejb-class").getValue().length() == 0){
						isFirstServiceMapping = true;

						break;
					}

					if(serviceMappingItem.getNode("ejb-class") != null && serviceMappingItem.getNode("ejb-class").getValue().equals(serviceClass.getName())){
						hasServiceMapping = true;

						break;
					}
				}
				
				if(modelInfo.getUseCase().length() > 0 || modelInfo.getMappedRepositoryId().length() > 0 || modelInfo.getGenerateService()){
                    if(serviceAnnotation != null && serviceAnnotation.name().length() > 0 && (serviceAnnotation.type() == ServiceType.STATEFUL || serviceAnnotation.type() == ServiceType.STATELESS)){
						if(!hasServiceMapping){
							generateMessage = true;

							if(!isFirstServiceMapping){
								newServiceMapping = new XmlNode();
								newServiceMapping.setName("session");
								newServiceMapping.addChildNode(new XmlNode("ejb-name", serviceAnnotation.name()));
							    newServiceMapping.addChildNode(new XmlNode("home", serviceHomeInterfaceId));
							    newServiceMapping.addChildNode(new XmlNode("remote", serviceRemoteInterfaceId));
							    newServiceMapping.addChildNode(new XmlNode("ejb-class", serviceClass.getName()));
							    newServiceMapping.addChildNode(new XmlNode("session-type", StringUtil.capitalize(serviceAnnotation.type().toString(), true)));
							    newServiceMapping.addChildNode(new XmlNode("transaction-type", "Container"));

								serviceMappingsItems.add(newServiceMapping);
							}
							else{
								serviceMapping.getNode("ejb-name").setValue(serviceAnnotation.name());
							    serviceMapping.getNode("home").setValue(serviceHomeInterfaceId);
							    serviceMapping.getNode("remote").setValue(serviceRemoteInterfaceId);
							    serviceMapping.getNode("ejb-class").setValue(serviceClass.getName());
							    serviceMapping.getNode("session-type").setValue(StringUtil.capitalize(serviceAnnotation.type().toString(), true));
							    serviceMapping.getNode("transaction-type").setValue("Container");
							}
						}
						else{
						    if(!serviceMapping.getNode("ejb-name").getValue().equals(serviceAnnotation.name()) ||
						       !serviceMapping.getNode("home").getValue().equals(serviceHomeInterfaceId) ||
						       !serviceMapping.getNode("remote").getValue().equals(serviceRemoteInterfaceId) ||
                               !serviceMapping.getNode("ejb-class").getValue().equals(serviceClass.getName()) ||
                               !serviceMapping.getNode("session-type").getValue().equals(StringUtil.capitalize(serviceAnnotation.type().toString(), true))){
    							generateMessage = true;
                                
    							serviceMapping.getNode("ejb-name").setValue(serviceAnnotation.name());
    							serviceMapping.getNode("home").setValue(serviceHomeInterfaceId);
    							serviceMapping.getNode("remote").setValue(serviceRemoteInterfaceId);
                                serviceMapping.getNode("ejb-class").setValue(serviceClass.getName());
    							serviceMapping.getNode("session-type").setValue(StringUtil.capitalize(serviceAnnotation.type().toString(), true));
    							serviceMapping.getNode("transaction-type").setValue("Container");
						    }
						}
					}
					else{
						if(hasServiceMapping){
							if((serviceMappingsItems.size() - 1) == 0){
	                            if(serviceMappingFile.exists())
	                                serviceMappingFile.delete();
	                            
                                auditor.info(AuditorStatusType.PROCESSED);
							}
							else{
	                            serviceMappingsItems.remove(serviceMapping);
	                        
	                            generateMessage = true;
	                        }
						}
					}
				}
				else{
					if(hasServiceMapping){
						if((serviceMappingsItems.size() - 1) == 0){
						    if(serviceMappingFile.exists())
						        serviceMappingFile.delete();
						 
					        auditor.info(AuditorStatusType.PROCESSED);
						}
						else{
							serviceMappingsItems.remove(serviceMapping);
						
		                    generateMessage = true;
						}
					}
				}

				if(generateMessage){
	                serviceMappings.setChildNodes(serviceMappingsItems);

	                XmlWriter serviceMappingWriter = new XmlWriter(serviceMappingFile, serviceMappingTemplateReader.getDocumentType(), serviceMappingTemplateReader.getEncoding());
    
    				serviceMappingWriter.write(serviceMappingTemplateContent);
    
                    auditor.info(AuditorStatusType.PROCESSED);
				}
            }
            catch(Throwable e){
                if(generateMessage)
                    auditor.info(AuditorStatusType.PROCESSED_WITH_ERROR);

                auditor.error(e);
            }
		}

		/**
		 * Executa o script para geração de código da classe que implementa as ações do formulário.
		 * 
		 * @throws Throwable
		 */
		public void generateActionClass() throws Throwable{
			Boolean generateMessage = false;
			Auditor auditor         = new Auditor(declaration.getName(), auditorResource);
			
			try{
				String        actionClassName     = ActionFormUtil.getActionClassNameByModel(declaration);
				StringBuilder actionClassFileName = new StringBuilder();

				actionClassFileName.append(ProjectConstants.DEFAULT_JAVA_DIR);
				actionClassFileName.append(StringUtil.replaceAll(actionClassName, ".", StringUtil.getDirectorySeparator()));
				actionClassFileName.append(".java");

				File actionClassFile = new File(actionClassFileName.toString());

				if(modelInfo.getUseCase().length() > 0){
					if(!actionClassFile.exists()){
						generateMessage = true;

						StringBuilder actionClassTemplateFileName = new StringBuilder();

						actionClassTemplateFileName.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
						actionClassTemplateFileName.append(templateId);
						actionClassTemplateFileName.append(StringUtil.getDirectorySeparator());
						actionClassTemplateFileName.append(ProjectConstants.DEFAULT_ACTION_CLASS_TEMPLATE_ID);

						File             actionClassTemplateFile    = new File(actionClassTemplateFileName.toString());
						XmlReader        actionClassTemplateReader  = new XmlReader(actionClassTemplateFile);
						XmlNode          actionClassTemplateContent = actionClassTemplateReader.getRoot();
						String           encoding                   = actionClassTemplateReader.getEncoding(); 
						ProcessorFactory processorFactory           = ProcessorFactory.getInstance();
						BaseProcessor    processor                  = processorFactory.getProcessor(modelInfo, actionClassTemplateContent);
						String           actionClassContent         = StringUtil.indent(processor.process(), JavaIndent.getRules());
						
						actionClassContent = StringUtil.decode(actionClassContent);
						
						FileUtil.toTextFile(actionClassFileName.toString(), actionClassContent, encoding);

						auditor.info(AuditorStatusType.PROCESSED);
					}
				}
				else{
					if(actionClassFile.exists()){
						generateMessage = true;

						actionClassFile.delete();

						auditor.info(AuditorStatusType.PROCESSED);
					}
				}
			}
			catch(Throwable e){
				if(generateMessage)
					auditor.info(AuditorStatusType.PROCESSED_WITH_ERROR);

				auditor.error(e);
			}
		}

		/**
		 * Executa o script para geração de código da classe que implementa o formulário.
		 * 
		 * @throws Throwable
		 */
		public void generateActionFormClass() throws Throwable{
			Boolean generateMessage = false;
			Auditor auditor         = new Auditor(declaration.getName(), auditorResource);

			try{
				String        actionFormClassId       = ActionFormUtil.getActionFormClassNameByModel(declaration);
				StringBuilder actionFormClassFileName = new StringBuilder();

				actionFormClassFileName.append(ProjectConstants.DEFAULT_JAVA_DIR);
				actionFormClassFileName.append(StringUtil.replaceAll(actionFormClassId, ".", StringUtil.getDirectorySeparator()));
				actionFormClassFileName.append(".java");

				File actionFormClassFile = new File(actionFormClassFileName.toString());

				if(modelInfo.getUseCase().length() > 0){
					if(!actionFormClassFile.exists()){
						generateMessage = true;

						StringBuilder actionFormClassTemplateFileName = new StringBuilder();

						actionFormClassTemplateFileName.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
						actionFormClassTemplateFileName.append(templateId);
						actionFormClassTemplateFileName.append(StringUtil.getDirectorySeparator());
						actionFormClassTemplateFileName.append(ProjectConstants.DEFAULT_ACTION_FORM_CLASS_TEMPLATE_ID);

						File             actionFormClassTemplateFile    = new File(actionFormClassTemplateFileName.toString());
						XmlReader        actionFormClassTemplateReader  = new XmlReader(actionFormClassTemplateFile);
						XmlNode          actionFormClassTemplateContent = actionFormClassTemplateReader.getRoot();
						String           encoding                       = actionFormClassTemplateReader.getEncoding(); 
						ProcessorFactory processorFactory               = ProcessorFactory.getInstance();
						BaseProcessor    processor                      = processorFactory.getProcessor(modelInfo, actionFormClassTemplateContent);
						String           actionFormClassContent         = StringUtil.indent(processor.process(), JavaIndent.getRules());

						actionFormClassContent = StringUtil.decode(actionFormClassContent);
                        
						FileUtil.toTextFile(actionFormClassFileName.toString(), actionFormClassContent, encoding);

						auditor.info(AuditorStatusType.PROCESSED);
					}
				}
				else{
					if(actionFormClassFile.exists()){
						generateMessage = true;

						actionFormClassFile.delete();

						auditor.info(AuditorStatusType.PROCESSED);
					}
				}
			}
			catch(Throwable e){
				if(generateMessage)
					auditor.info(AuditorStatusType.PROCESSED_WITH_ERROR);

				auditor.error(e);
			}
		}

		/**
		 * 
		 * Executa o script para geração do arquivo de mapeamento das ações do formulário.
		 * 
		 * @throws Throwable
		 */
		public void generateActionFormMapping() throws Throwable{
			Boolean generateMessage = false;
			Auditor auditor         = new Auditor(declaration.getName(), auditorResource);

			try{
				StringBuilder actionFormMappingTemplateFileName = new StringBuilder();

				actionFormMappingTemplateFileName.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
				actionFormMappingTemplateFileName.append(templateId);
				actionFormMappingTemplateFileName.append(StringUtil.getDirectorySeparator());
				actionFormMappingTemplateFileName.append(ProjectConstants.DEFAULT_ACTION_FORM_MAPPING_TEMPLATE_ID);

				StringBuilder actionFormMappingFileName = new StringBuilder();

				actionFormMappingFileName.append(ProjectConstants.DEFAULT_WEB_DIR);
				actionFormMappingFileName.append(ProjectConstants.DEFAULT_ACTION_FORM_MAPPING_FILE_ID);
				
				File      actionFormMappingFile            = new File(actionFormMappingFileName.toString());
				File      actionFormMappingTemplateFile    = (actionFormMappingFile.exists() ? actionFormMappingFile : new File(actionFormMappingTemplateFileName.toString()));
				XmlReader actionFormMappingTemplateReader  = new XmlReader(actionFormMappingTemplateFile);
				XmlNode   actionFormMappingTemplateContent = actionFormMappingTemplateReader.getRoot();
				
				if(!actionFormMappingFile.exists())
				    actionFormMappingTemplateContent = actionFormMappingTemplateContent.getNode("struts-config");
				
				XmlNode             actionMappings          = actionFormMappingTemplateContent.getNode("action-mappings");
				XmlNode             actionMapping           = null;
				Map<String, String> actionMappingAttributes = null;
				Collection<XmlNode> actionMappingsItems     = actionMappings.getChildNodes();
				String              actionFormObjectId      = ActionFormUtil.getActionFormObjectIdByModel(declaration);
				String              actionClassName         = ActionFormUtil.getActionClassNameByModel(declaration);
				Boolean             hasActionMapping        = false;
				Boolean             isFirstActionMapping    = false;
				Boolean             modifiedFile            = false;

				if(actionMappingsItems != null){
					for(XmlNode actionMappingItem : actionMappingsItems){
						actionMapping           = actionMappingItem;
						actionMappingAttributes = actionMapping.getAttributes();

						if(actionMappingItem.getAttribute("type").length() == 0){
							isFirstActionMapping = true;

							break;
						}

						if(actionMappingItem.getAttribute("type").equals(actionClassName)){
							hasActionMapping = true;

							break;
						}
					}
				}
				
				if(modelInfo.getUseCase().length() > 0){
					if(!hasActionMapping){
						generateMessage = true;
						
						String        actionFormUrl   = ActionFormUtil.getActionFormUrlByModel(declaration);
						StringBuilder actionFormInput = new StringBuilder();
						
						actionFormInput.append(actionFormUrl);
						actionFormInput.append("/index.jsp");

						if(!isFirstActionMapping){
							actionMapping = new XmlNode();

							actionMappingAttributes = new LinkedHashMap<String, String>();
						}
						
						actionMappingAttributes.put("path", actionFormUrl);
						actionMappingAttributes.put("type", actionClassName);
						actionMappingAttributes.put("name", actionFormObjectId);
						actionMappingAttributes.put("parameter", "action");
						actionMappingAttributes.put("input", actionFormInput.toString());

						actionMapping.setName("action");
						actionMapping.setAttributes(actionMappingAttributes);

						if(!isFirstActionMapping)
							actionMappingsItems.add(actionMapping);

						actionMappings.setChildNodes(actionMappingsItems);
						
						modifiedFile = true;
					}
				}
				else{
					if(hasActionMapping){
						generateMessage = true;

						if((actionMappingsItems.size() - 1) == 0){
							actionMappingAttributes.put("path", "");
							actionMappingAttributes.put("type", "");
							actionMappingAttributes.put("name", "");
							actionMappingAttributes.put("parameter", "");
							actionMappingAttributes.put("input", "");
						}
						else
							actionMappingsItems.remove(actionMapping);

						actionMappings.setChildNodes(actionMappingsItems);
						
						modifiedFile = true;
					}
				}

				XmlNode             actionFormMappings          = actionFormMappingTemplateContent.getNode("form-beans");
				XmlNode             actionFormMapping           = null;
				Map<String, String> actionFormMappingAttributes = null;
				Collection<XmlNode> actionFormMappingsItems     = actionFormMappings.getChildNodes();
				String              actionFormClassName         = ActionFormUtil.getActionFormClassNameByModel(declaration);
				Boolean             hasActionFormMapping        = false;
				Boolean             isFirstActionFormMapping    = false;

				if(actionFormMappingsItems != null){
					for(XmlNode formMappingItem : actionFormMappingsItems){
						actionFormMapping           = formMappingItem;
						actionFormMappingAttributes = actionFormMapping.getAttributes();

						if(formMappingItem.getAttribute("type").length() == 0){
							isFirstActionFormMapping = true;

							break;
						}

						if(formMappingItem.getAttribute("type").equals(actionFormClassName)){
							hasActionFormMapping = true;

							break;
						}
					}
				}

				if(modelInfo.getUseCase().length() > 0){
					if(!hasActionFormMapping){
						generateMessage = true;

						if(!isFirstActionFormMapping){
							actionFormMapping = new XmlNode();

							actionFormMappingAttributes = new LinkedHashMap<String, String>();
						}

						actionFormMappingAttributes.put("name", actionFormObjectId);
						actionFormMappingAttributes.put("type", actionFormClassName);

						actionFormMapping.setName("form-bean");
						actionFormMapping.setAttributes(actionFormMappingAttributes);

						if(!isFirstActionFormMapping)
							actionFormMappingsItems.add(actionFormMapping);

						actionFormMappings.setChildNodes(actionFormMappingsItems);
						
						modifiedFile = true;
					}
				}
				else{
					if(hasActionFormMapping){
						generateMessage = true;

						if((actionFormMappingsItems.size() - 1) == 0){
							actionFormMappingAttributes.put("name", "");
							actionFormMappingAttributes.put("type", "");
						}
						else
							actionFormMappingsItems.remove(actionFormMapping);

						actionFormMappings.setChildNodes(actionFormMappingsItems);
						
						modifiedFile = true;
					}
				}
				
				if(modifiedFile){
    				XmlWriter formMappingWriter = new XmlWriter(actionFormMappingFile, actionFormMappingTemplateReader.getDocumentType(), actionFormMappingTemplateReader.getEncoding());
    
    				formMappingWriter.write(actionFormMappingTemplateContent);
    
    				if(generateMessage)
    					auditor.info(AuditorStatusType.PROCESSED);
				}
			}
			catch(Throwable e){
				if(generateMessage)
					auditor.info(AuditorStatusType.PROCESSED_WITH_ERROR);

				auditor.error(e);
			}
		}

		/**
		 * Executa o script para geração de código da página WEB.
		 * 
		 * @throws Throwable
		 */
		public void generateWebPage() throws Throwable{
			Boolean generateMessage = false;
			Auditor auditor         = new Auditor(declaration.getName(), auditorResource);

			try{
			    String        actionFormUrl         = ActionFormUtil.getActionFormUrlByModel(declaration);
				StringBuilder webPageDirName        = new StringBuilder();
				StringBuilder webPageImagesDirName  = new StringBuilder();
				StringBuilder webPageScriptsDirName = new StringBuilder();
				StringBuilder webPageStylesDirName  = new StringBuilder();
				StringBuilder webPageFileName       = new StringBuilder();
				File          webPageDir            = null;
				File          webPageImagesDir      = null;
				File          webPageScriptsDir     = null;
				File          webPageStylesDir      = null;
				File          webPageFile           = null;

				webPageDirName.append(ProjectConstants.DEFAULT_WEB_DIR);
				webPageDirName.append(actionFormUrl);

				webPageImagesDirName.append(webPageDirName.toString());
				webPageImagesDirName.append(TaglibConstants.DEFAULT_IMAGES_RESOURCES_DIR);

				webPageScriptsDirName.append(webPageDirName.toString());
				webPageScriptsDirName.append(TaglibConstants.DEFAULT_SCRIPTS_RESOURCES_DIR);

				webPageStylesDirName.append(webPageDirName.toString());
				webPageStylesDirName.append(TaglibConstants.DEFAULT_STYLES_RESOURCES_DIR);

				webPageFileName.append(webPageDirName.toString());
				webPageFileName.append(StringUtil.getDirectorySeparator());
				webPageFileName.append("index.jsp");

				webPageDir        = new File(webPageDirName.toString());
				webPageImagesDir  = new File(webPageImagesDirName.toString());
				webPageScriptsDir = new File(webPageScriptsDirName.toString());
				webPageStylesDir  = new File(webPageStylesDirName.toString());
				webPageFile       = new File(webPageFileName.toString());

				if(modelInfo.getUseCase().length() > 0){
					if(!webPageFile.exists() || !webPageDir.exists() || !webPageImagesDir.exists() || !webPageStylesDir.exists() || !webPageScriptsDir.exists()){
						generateMessage = true;

						if(!webPageDir.exists())
							webPageDir.mkdirs();

						if(!webPageImagesDir.exists())
							webPageImagesDir.mkdirs();

						if(!webPageScriptsDir.exists())
							webPageScriptsDir.mkdirs();

						if(!webPageStylesDir.exists())
							webPageStylesDir.mkdirs();

						if(!webPageFile.exists()){
							StringBuilder webPageTemplateFileName = new StringBuilder();

							webPageTemplateFileName.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
							webPageTemplateFileName.append(templateId);
							webPageTemplateFileName.append(StringUtil.getDirectorySeparator());
							webPageTemplateFileName.append(ProjectConstants.DEFAULT_WEB_PAGE_TEMPLATE_ID);

							File             webPageTemplateFile    = new File(webPageTemplateFileName.toString());
							XmlReader        webPageTemplateReader  = new XmlReader(webPageTemplateFile);
							XmlNode          webPageTemplateContent = webPageTemplateReader.getRoot();
							String           encoding               = webPageTemplateReader.getEncoding(); 
							ProcessorFactory processorFactory       = ProcessorFactory.getInstance();
							BaseProcessor    processor              = processorFactory.getProcessor(modelInfo, webPageTemplateContent);
							String           webPageContent         = StringUtil.indent(processor.process(), JSPIndent.getRules());
							
							webPageContent = StringUtil.decode(webPageContent);
							
							FileUtil.toTextFile(webPageFileName.toString(), webPageContent, encoding);

							auditor.info(AuditorStatusType.PROCESSED);
						}
					}
					
                    generateWebMapping();
				}
				else{
					if(webPageFile.exists() || webPageDir.exists() || webPageImagesDir.exists() || webPageStylesDir.exists() || webPageScriptsDir.exists()){
						generateMessage = true;

						auditor.info("removing...");

						if(webPageImagesDir.exists())
							webPageImagesDir.delete();

						if(webPageScriptsDir.exists())
							webPageScriptsDir.delete();

						if(webPageStylesDir.exists())
							webPageStylesDir.delete();

						if(webPageFile.exists())
							webPageFile.delete();

						if(webPageDir.exists())
							webPageDir.delete();

						auditor.info(AuditorStatusType.PROCESSED);
					}
				}
			}
			catch(Throwable e){
				if(generateMessage)
					auditor.info(AuditorStatusType.PROCESSED_WITH_ERROR);

				auditor.error(e);
			}
		}
		
		/**
		 * Gera o arquivo descritor da aplicação.
		 * 
		 * @throws Throwable
		 */
		private void generateWebMapping() throws Throwable{
            File webDir            = new File(ProjectConstants.DEFAULT_WEB_DIR);
            File webDescriptorFile = new File(webDir.getAbsolutePath().concat(StringUtil.getDirectorySeparator()).concat(ProjectConstants.DEFAULT_WEB_DESCRIPTOR_FILE_ID));

            if(!webDescriptorFile.exists()){
                XmlNode node = new XmlNode("web-app");
                
                node.addAttribute("version", "2.4");
                node.addAttribute("xmlns", "http://java.sun.com/xml/ns/j2ee");
                node.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
                node.addAttribute("xsi:schemaLocation", "http://java.sun.com/xml/ns/j2ee http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd");
                
                XmlNode filterNode = new XmlNode("filter");

                filterNode.addChildNode(new XmlNode("filter-name", "securityFilter"));
                filterNode.addChildNode(new XmlNode("filter-class", SecurityConstants.DEFAULT_SECURITY_FILTER_CLASS_ID));
                
                node.addChildNode(filterNode);
                
                XmlNode filterMappingNode = new XmlNode("filter-mapping");
                
                filterMappingNode.addChildNode(new XmlNode("filter-name", "securityFilter"));
                filterMappingNode.addChildNode(new XmlNode("url-pattern", "*.do"));
                
                node.addChildNode(filterMappingNode);

                filterMappingNode = new XmlNode("filter-mapping");
                filterMappingNode.addChildNode(new XmlNode("filter-name", "securityFilter"));
                filterMappingNode.addChildNode(new XmlNode("url-pattern", "*.jsp"));
                
                node.addChildNode(filterMappingNode);
                
                filterMappingNode = new XmlNode("filter-mapping");
                filterMappingNode.addChildNode(new XmlNode("filter-name", "securityFilter"));
                filterMappingNode.addChildNode(new XmlNode("url-pattern", "*.html"));
                
                node.addChildNode(filterMappingNode);
                
                filterMappingNode = new XmlNode("filter-mapping");
                filterMappingNode.addChildNode(new XmlNode("filter-name", "securityFilter"));
                filterMappingNode.addChildNode(new XmlNode("url-pattern", "*.htm"));
                
                node.addChildNode(filterMappingNode);
                
                XmlNode listenerNode = new XmlNode("listener");
                
                listenerNode.addChildNode(new XmlNode("listener-class", SecurityConstants.DEFAULT_LOGIN_SESSION_LISTENER_CLASS_ID));
                
                node.addChildNode(listenerNode);
                
                XmlNode servletNode = new XmlNode("servlet");
                
                servletNode.addChildNode(new XmlNode("servlet-name", "action"));
                servletNode.addChildNode(new XmlNode("servlet-class", SystemConstants.DEFAULT_ACTION_SERVLET_CLASS_ID));
                 
                XmlNode initParamNode = new XmlNode("init-param");
                
                initParamNode.addChildNode(new XmlNode("param-name", "config"));
                initParamNode.addChildNode(new XmlNode("param-value", "/".concat(ProjectConstants.DEFAULT_ACTION_FORM_MAPPING_FILE_ID)));
                
                servletNode.addChildNode(initParamNode);
                
                initParamNode = new XmlNode("init-param");
                initParamNode.addChildNode(new XmlNode("param-name", "debug"));
                initParamNode.addChildNode(new XmlNode("param-value", "2"));
                
                servletNode.addChildNode(initParamNode);

                initParamNode = new XmlNode("init-param");
                initParamNode.addChildNode(new XmlNode("param-name", "detail"));
                initParamNode.addChildNode(new XmlNode("param-value", "2"));
                
                servletNode.addChildNode(initParamNode);
                servletNode.addChildNode(new XmlNode("load-on-startup", "2"));
                
                node.addChildNode(servletNode);
                
                XmlNode servletMappingNode = new XmlNode("servlet-mapping");
                
                servletMappingNode.addChildNode(new XmlNode("servlet-name", "action"));
                servletMappingNode.addChildNode(new XmlNode("url-pattern", "*.do"));

                node.addChildNode(servletMappingNode);
                
                servletNode = new XmlNode("servlet");
                servletNode.addChildNode(new XmlNode("servlet-name", "contentLoaderServlet"));
                servletNode.addChildNode(new XmlNode("servlet-class", SystemConstants.DEFAULT_CONTENT_LOADER_SERVLET_CLASS_ID));
                
                node.addChildNode(servletNode);
                
                servletMappingNode = new XmlNode("servlet-mapping");
                servletMappingNode.addChildNode(new XmlNode("servlet-name", "contentLoaderServlet"));
                servletMappingNode.addChildNode(new XmlNode("url-pattern", "/contentLoaderServlet"));
                
                node.addChildNode(servletMappingNode);
                
                XmlNode sessionConfigNode = new XmlNode("session-config");
                
                sessionConfigNode.addChildNode(new XmlNode("session-timeout", "5"));
                
                node.addChildNode(sessionConfigNode);
                
                XmlNode welcomeFileListNode = new XmlNode("welcome-file-list");
                
                welcomeFileListNode.addChildNode(new XmlNode("welcome-file", "index.jsp"));
                welcomeFileListNode.addChildNode(new XmlNode("welcome-file", "index.html"));
                welcomeFileListNode.addChildNode(new XmlNode("welcome-file", "index.htm"));
                
                node.addChildNode(welcomeFileListNode);
                
                XmlNode jspConfigNode = new XmlNode("jsp-config");
                XmlNode taglibNode    = new XmlNode("taglib");
                
                taglibNode.addChildNode(new XmlNode("taglib-uri", ProjectConstants.DEFAULT_WEB_TAGLIBS_ID));
                taglibNode.addChildNode(new XmlNode("taglib-location", "/".concat(ProjectConstants.DEFAULT_WEB_TAGLIBS_FILE_ID)));
                
                jspConfigNode.addChildNode(taglibNode);
                
                node.addChildNode(jspConfigNode);
                
                XmlWriter writer = new XmlWriter(webDescriptorFile);
                
                writer.setEncoding(Constants.DEFAULT_ENCODING);
                writer.write(node);
            }
		}
		
        /**
         * Gera o arquivo descritor do WEB Service.
         * 
         * @throws Throwable
         */
		public void generateWebServiceMapping() throws Throwable{
            Boolean generateMessage = false;
            Auditor auditor         = new Auditor(declaration.getName(), auditorResource);

            try{
                Class<IService>    serviceInterfaceClass = null;
                Class<BaseService> serviceClass          = null;

                try{
                    serviceClass          = ServiceUtil.getServiceClassByModel(declaration);
                    serviceInterfaceClass = ServiceUtil.getServiceInterfaceClassByModel(declaration);
                }
                catch(Throwable e){
                    return;
                }
                
                StringBuilder webServicesMappingTemplateFileName = new StringBuilder();

                webServicesMappingTemplateFileName.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
                webServicesMappingTemplateFileName.append(templateId);
                webServicesMappingTemplateFileName.append(StringUtil.getDirectorySeparator());
                webServicesMappingTemplateFileName.append(ProjectConstants.DEFAULT_WEB_SERVICE_MAPPING_TEMPLATE_ID);
                
                StringBuilder webServicesMappingFileName = new StringBuilder();

                webServicesMappingFileName.append(ProjectConstants.DEFAULT_WEB_SERVICES_MODULE_DIR);
                webServicesMappingFileName.append(ProjectConstants.DEFAULT_WEB_SERVICES_DESCRIPTOR_FILE_ID);

                File      webServicesMappingTemplateFile    = new File(webServicesMappingTemplateFileName.toString());
                File      webServicesMappingFile            = new File(webServicesMappingFileName.toString());
                XmlReader webServicesMappingTemplateReader  = new XmlReader((webServicesMappingFile.exists() ? webServicesMappingFile : webServicesMappingTemplateFile));
                XmlNode   webServicesMappingTemplateContent = webServicesMappingTemplateReader.getRoot();
                
                if(!webServicesMappingFile.exists())
                    webServicesMappingTemplateContent = webServicesMappingTemplateContent.getNode("serviceGroup");
                    
                Collection<XmlNode> webServicesMappingsItems  = webServicesMappingTemplateContent.getChildNodes();
                XmlNode             webServicesMapping        = webServicesMappingsItems.iterator().next();
                XmlNode             newWebServiceMapping     = null;
                XmlNode             node                     = null;
                XmlNode             childNode                = null;
                Boolean             hasWebServiceMapping     = false;
                Boolean             isFirstWebServiceMapping = false;
                
                for(XmlNode webServicesMappingItem : webServicesMappingsItems){
                    webServicesMapping = webServicesMappingItem;
                    
                    if(webServicesMappingItem.getNode("parameter") != null && webServicesMappingItem.getNode("parameter").getValue().length() == 0){
                        isFirstWebServiceMapping = true;

                        break;
                    }

                    if(webServicesMappingItem.getNode("parameter") != null && webServicesMappingItem.getNode("parameter").getValue().equals(serviceClass.getName())){
                        hasWebServiceMapping = true;

                        break;
                    }
                }

                Service serviceAnnotation = (Service)serviceInterfaceClass.getAnnotation(Service.class);

                if(serviceAnnotation != null && serviceAnnotation.name().length() > 0 && serviceAnnotation.type() == ServiceType.WEB_SERVICE){
                    if(!hasWebServiceMapping){
                        generateMessage = true;

                        if(!isFirstWebServiceMapping){
                            newWebServiceMapping = new XmlNode();
                            newWebServiceMapping.setName("service");
                            newWebServiceMapping.addAttribute("name", serviceAnnotation.name());
                            newWebServiceMapping.addAttribute("scope", serviceAnnotation.scope().toString());
                            newWebServiceMapping.addChildNode(new XmlNode("description", serviceAnnotation.name()));
                            
                            node = new XmlNode("messageReceivers");
                            
                            childNode = new XmlNode("messageReceiver");
                            childNode.addAttribute("mep", "http://www.w3.org/2004/08/wsdl/in-only");
                            childNode.addAttribute("class", "org.apache.axis2.rpc.receivers.RPCInOnlyMessageReceiver");
                            
                            node.addChildNode(childNode);
                            
                            childNode = new XmlNode("messageReceiver");
                            childNode.addAttribute("mep", "http://www.w3.org/2004/08/wsdl/in-out");
                            childNode.addAttribute("class", "org.apache.axis2.rpc.receivers.RPCMessageReceiver");
                            
                            node.addChildNode(childNode);
                            
                            newWebServiceMapping.addChildNode(node);
                            
                            node = new XmlNode("parameter");
                            node.addAttribute("name", "ServiceClass");
                            node.setValue(serviceClass.getName());
                            
                            newWebServiceMapping.addChildNode(node);

                            webServicesMappingsItems.add(newWebServiceMapping);
                        }
                        else{
                            webServicesMapping.addAttribute("name", serviceAnnotation.name());
                            webServicesMapping.addAttribute("scope", serviceAnnotation.scope().toString());
                            webServicesMapping.getNode("description").setValue(serviceAnnotation.name());
                            webServicesMapping.getNode("parameter").setValue(serviceClass.getName());
                        }
                    }
                    else{
                        if(!webServicesMapping.getAttribute("name").equals(serviceAnnotation.name())){
                            generateMessage = true;

                            webServicesMapping.addAttribute("name", serviceAnnotation.name());
                            webServicesMapping.addAttribute("scope", serviceAnnotation.scope().toString());
                            webServicesMapping.getNode("description").setValue(serviceAnnotation.name());
                            webServicesMapping.getNode("parameter").setValue(serviceClass.getName());
                        }
                    }
                }
                else{
                    if(hasWebServiceMapping){
                        if((webServicesMappingsItems.size() - 1) == 0){
                            if(webServicesMappingFile.exists())
                                webServicesMappingFile.delete();
                            
                            auditor.info(AuditorStatusType.PROCESSED);
                        }
                        else{
                            webServicesMappingsItems.remove(webServicesMapping);
                        
                            generateMessage = true;
                        }
                    }
                }

                if(generateMessage){
                    webServicesMappingTemplateContent.setChildNodes(webServicesMappingsItems);

                    XmlWriter webServicesMappingWriter = new XmlWriter(webServicesMappingFile, webServicesMappingTemplateReader.getDocumentType(), webServicesMappingTemplateReader.getEncoding());
    
                    webServicesMappingWriter.write(webServicesMappingTemplateContent);
    
                    auditor.info(AuditorStatusType.PROCESSED);
                }
            }
            catch(Throwable e){
                if(generateMessage)
                    auditor.info(AuditorStatusType.PROCESSED_WITH_ERROR);

                auditor.error(e);
            }
		}

		/**
		 * Executa o script para geração dos arquivos de internacionalização da página WEB.
		 * 
		 * @throws Throwable
		 */
		public void generateWebPageI18nResource() throws Throwable{
			Boolean generateMessage = false;
			Auditor auditor         = new Auditor(declaration.getName(), auditorResource);

			try{
			    SystemResourceLoader loader                      = new SystemResourceLoader(getProjectResourcesDir(), SystemConstants.DEFAULT_RESOURCE_ID);
			    SystemResource       resource                    = loader.get();
                List<Locale>         languages                   = resource.getLanguages();
			    String               actionFormUrl               = ActionFormUtil.getActionFormUrlByModel(declaration);
				StringBuilder        webPageI18nResourceFileName = new StringBuilder();
				
				for(Locale language : languages){
				    if(webPageI18nResourceFileName.length() > 0)
				        webPageI18nResourceFileName.delete(0, webPageI18nResourceFileName.length());
				    
    				webPageI18nResourceFileName.append(ProjectConstants.DEFAULT_RESOURCES_DIR);
    				webPageI18nResourceFileName.append(ResourceConstants.DEFAULT_I18N_RESOURCES_DIR);
    				webPageI18nResourceFileName.append(actionFormUrl.toString());
    				webPageI18nResourceFileName.append("_");
    				webPageI18nResourceFileName.append(language.toString());
    				webPageI18nResourceFileName.append(".properties");
    				
    				File webPageI18nResourceFile = new File(webPageI18nResourceFileName.toString());
    
    				if(modelInfo.getUseCase().length() > 0){
    					if(!webPageI18nResourceFile.exists()){
    						generateMessage = true;
    
    						StringBuilder webPageI18nResourceTemplateFileName = new StringBuilder();
    
    						webPageI18nResourceTemplateFileName.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
    						webPageI18nResourceTemplateFileName.append(templateId);
    						webPageI18nResourceTemplateFileName.append(StringUtil.getDirectorySeparator());
    						webPageI18nResourceTemplateFileName.append(ProjectConstants.DEFAULT_WEB_PAGE_I18N_RESOURCE_TEMPLATE_ID);
    
    						File             webPageI18nResourceTemplateFile    = new File(webPageI18nResourceTemplateFileName.toString());
    						XmlReader        webPageI18nResourceTemplateReader  = new XmlReader(webPageI18nResourceTemplateFile);
    						XmlNode          webPageI18nResourceTemplateContent = webPageI18nResourceTemplateReader.getRoot();
    						String           encoding                           = webPageI18nResourceTemplateReader.getEncoding(); 
    						ProcessorFactory processorFactory                   = ProcessorFactory.getInstance();
    						BaseProcessor    processor                          = processorFactory.getProcessor(modelInfo, webPageI18nResourceTemplateContent);
    						String           webPageI18nResourceContent         = StringUtil.indent(processor.process(), JavaIndent.getRules());
    						
    						webPageI18nResourceContent = StringUtil.decode(webPageI18nResourceContent);
    
    						FileUtil.toTextFile(webPageI18nResourceFileName.toString(), webPageI18nResourceContent, encoding);
    
    						auditor.info(AuditorStatusType.PROCESSED);
    					}
    				}
    				else{
    					if(webPageI18nResourceFile.exists() && modelInfo.getMappedRepositoryId().length() == 0){
    						generateMessage = true;
    
    						webPageI18nResourceFile.delete();
    
    						auditor.info(AuditorStatusType.PROCESSED);
    					}
    				}
				}
			}
			catch(Throwable e){
				if(generateMessage)
					auditor.info(AuditorStatusType.PROCESSED_WITH_ERROR);

				auditor.error(e);
			}
		}
	}
}