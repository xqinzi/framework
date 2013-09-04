package br.com.concepting.framework.model.processors;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.Stateful;
import javax.ejb.Stateless;

import org.dom4j.DocumentType;
import org.dom4j.tree.DefaultDocumentType;

import br.com.concepting.framework.audit.Auditor;
import br.com.concepting.framework.audit.resource.AuditorResource;
import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.constants.ProjectConstants;
import br.com.concepting.framework.constants.SystemConstants;
import br.com.concepting.framework.controller.form.util.ActionFormUtil;
import br.com.concepting.framework.model.helpers.ModelInfo;
import br.com.concepting.framework.model.util.ModelUtil;
import br.com.concepting.framework.persistence.constants.PersistenceConstants;
import br.com.concepting.framework.persistence.util.PersistenceUtil;
import br.com.concepting.framework.processors.AnnotationProcessorFactory;
import br.com.concepting.framework.processors.BaseAnnotationProcessor;
import br.com.concepting.framework.processors.BaseProcessor;
import br.com.concepting.framework.processors.ExpressionProcessorUtil;
import br.com.concepting.framework.processors.ProcessorFactory;
import br.com.concepting.framework.resource.SystemResource;
import br.com.concepting.framework.resource.SystemResourceLoader;
import br.com.concepting.framework.resource.constants.ResourceConstants;
import br.com.concepting.framework.security.constants.SecurityConstants;
import br.com.concepting.framework.service.BaseRemoteService;
import br.com.concepting.framework.service.BaseService;
import br.com.concepting.framework.service.annotations.Service;
import br.com.concepting.framework.service.interfaces.IService;
import br.com.concepting.framework.service.types.ServiceType;
import br.com.concepting.framework.service.util.ServiceUtil;
import br.com.concepting.framework.ui.constants.UIConstants;
import br.com.concepting.framework.util.FileUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.XmlReader;
import br.com.concepting.framework.util.XmlWriter;
import br.com.concepting.framework.util.helpers.JSPIndent;
import br.com.concepting.framework.util.helpers.JavaIndent;
import br.com.concepting.framework.util.helpers.XmlNode;

/**
 * Classe que define o scripting para o processamento das anotações.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class ModelAnnotationProcessor extends BaseAnnotationProcessor{
    private ModelInfo modelInfo  = null;
    private String    templateId = "";
    
    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param declaration Classe contendo a anotação a ser processada.
     * @param annotationProcessorFactory Instância do fábrica de processadores de anotação.
     */
    public ModelAnnotationProcessor(Class declaration, AnnotationProcessorFactory annotationProcessorFactory){
        super(declaration, annotationProcessorFactory);
        
        this.modelInfo = ModelUtil.getModelInfo(declaration);
        
        if(this.modelInfo != null)
            this.templateId = this.modelInfo.getTemplateId();
    }

    /**
     * Retorna a instância contendo as propriedades do modelo de dados que contém
     * a anotação a ser processada.
     * 
     * @return Instância contendo as propriedades do modelo de dados.
     */
    public ModelInfo getModelInfo(){
        return modelInfo;
    }

    /**
     * Define a instância contendo as propriedades do modelo de dados que contém
     * a anotação a ser processada.
     * 
     * @param modelInfo Instância contendo as propriedades do modelo de dados.
     */
    public void setModelInfo(ModelInfo modelInfo){
        this.modelInfo = modelInfo;
    }
    
    /**
     * Retorna o identificador do template de processamento a ser utilizada.
     * 
     * @return String contendo o identificador do template.
     */
    public String getTemplateId(){
        return templateId;
    }

    /**
     * Define o identificador do template de processamento a ser utilizada.
     * 
     * @param templateId String contendo o identificador do template.
     */
    public void setTemplateId(String templateId){
        this.templateId = templateId;
    }

    /**
     * Inicia o processamento do modelo de dados.
     */
    public void process(){
        if(this.modelInfo == null)
            return;
        
        ExpressionProcessorUtil.addVariable(AttributeConstants.USER_KEY, System.getProperty("user.name"));
        ExpressionProcessorUtil.addVariable(AttributeConstants.NOW_KEY, new Date());
        
        StringBuilder templateFilesDirName = new StringBuilder();
        
        templateFilesDirName.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
        templateFilesDirName.append(templateId);
        
        File templateFilesDir = new File(templateFilesDirName.toString());
        
        if(templateFilesDir.exists()){
            File           templateFiles[]     = templateFilesDir.listFiles();
            StringBuilder   templateMethodName = null;
            Auditor         auditor            = null;
            AuditorResource auditorResource    = getAnnotationProcessorFactory().getAuditorResource();
            
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
                            if(auditorResource != null){
                                auditor = new Auditor(getClass(), method, getDeclaration().getName(), auditorResource);
                                auditor.start();
                            }
                        
                            method.invoke(this);
                        
                            if(auditor != null)
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
    
    /**
     * Executa o script para geração de código para a classe que implementa a persistência.
     * 
     * @throws Throwable
     */
    public void generatePersistenceClass() throws Throwable{
        String        persistenceClassName     = PersistenceUtil.getPersistenceClassNameByModel(getDeclaration());
        StringBuilder persistenceClassFileName = new StringBuilder();

        persistenceClassFileName.append(ProjectConstants.DEFAULT_JAVA_DIR);
        persistenceClassFileName.append(StringUtil.replaceAll(persistenceClassName, ".", StringUtil.getDirectorySeparator()));
        persistenceClassFileName.append(".java");
        
        File persistenceClassFile = new File(persistenceClassFileName.toString());
        
        if(getModelInfo().getMappedRepositoryId().length() > 0 || getModelInfo().getGeneratePersistence()){
            if(!persistenceClassFile.exists()){
                StringBuilder persistenceClassTemplateFileName = new StringBuilder();

                persistenceClassTemplateFileName.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
                persistenceClassTemplateFileName.append(getTemplateId());
                persistenceClassTemplateFileName.append(StringUtil.getDirectorySeparator());
                persistenceClassTemplateFileName.append(ProjectConstants.DEFAULT_PERSISTENCE_CLASS_TEMPLATE_FILE_ID);
                
                File             persistenceClassTemplateFile    = new File(persistenceClassTemplateFileName.toString());
                XmlReader        persistenceClassTemplateReader  = new XmlReader(persistenceClassTemplateFile);
                XmlNode          persistenceClassTemplateContent = persistenceClassTemplateReader.getRoot();
                String           encoding                        = persistenceClassTemplateReader.getEncoding(); 
                ProcessorFactory processorFactory                = ProcessorFactory.getInstance();
                BaseProcessor    processor                       = processorFactory.getProcessor(getModelInfo(), persistenceClassTemplateContent);
                String           persistenceClassContent         = StringUtil.indent(processor.process(), JavaIndent.getRules());
                
                persistenceClassContent = StringUtil.decode(persistenceClassContent);
                
                FileUtil.toTextFile(persistenceClassFileName.toString(), persistenceClassContent, encoding);
            }
        }   
        else{
            if(persistenceClassFile.exists())
                persistenceClassFile.delete();
        }
    }

    /**
     * Executa o script para geração de código para interface da classe que implementa a 
     * persistência.
     * 
     * @throws Throwable
     */
    public void generatePersistenceInterface() throws Throwable{
        String        persistenceInterfaceClassName     = PersistenceUtil.getPersistenceInterfaceClassNameByModel(getDeclaration());
        StringBuilder persistenceInterfaceClassFileName = new StringBuilder();

        persistenceInterfaceClassFileName.append(ProjectConstants.DEFAULT_JAVA_DIR);
        persistenceInterfaceClassFileName.append(StringUtil.replaceAll(persistenceInterfaceClassName, ".", StringUtil.getDirectorySeparator()));
        persistenceInterfaceClassFileName.append(".java");

        File persistenceInterfaceFile = new File(persistenceInterfaceClassFileName.toString());

        if(getModelInfo().getMappedRepositoryId().length() > 0 || getModelInfo().getGeneratePersistence()){
            if(!persistenceInterfaceFile.exists()){
                StringBuilder persistenceInterfaceClassTemplateFileName = new StringBuilder();

                persistenceInterfaceClassTemplateFileName.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
                persistenceInterfaceClassTemplateFileName.append(getTemplateId());
                persistenceInterfaceClassTemplateFileName.append(StringUtil.getDirectorySeparator());
                persistenceInterfaceClassTemplateFileName.append(ProjectConstants.DEFAULT_PERSISTENCE_INTERFACE_TEMPLATE_FILE_ID);

                File             persistenceInterfaceTemplate        = new File(persistenceInterfaceClassTemplateFileName.toString());
                XmlReader        persistenceInterfaceTemplateReader  = new XmlReader(persistenceInterfaceTemplate);
                XmlNode          persistenceInterfaceTemplateContent = persistenceInterfaceTemplateReader.getRoot();
                String           encoding                            = persistenceInterfaceTemplateReader.getEncoding(); 
                ProcessorFactory processorFactory                    = ProcessorFactory.getInstance();
                BaseProcessor    processor                           = processorFactory.getProcessor(getModelInfo(), persistenceInterfaceTemplateContent);
                String           persistenceInterfaceContent         = StringUtil.indent(processor.process(), JavaIndent.getRules());
                
                persistenceInterfaceContent = StringUtil.decode(persistenceInterfaceContent);

                FileUtil.toTextFile(persistenceInterfaceClassFileName.toString(), persistenceInterfaceContent, encoding);
            }
        }
        else{
            if(persistenceInterfaceFile.exists())
                persistenceInterfaceFile.delete();
        }
    }

    /**
     * Executa o script para geração de código do mapeamento de persistência.
     * 
     * @throws Throwable
     */
    public void generatePersistenceMapping() throws Throwable{
        Boolean       generatePersistenceMappingFile = false;
        StringBuilder persistenceResourcesFileName   = new StringBuilder();

        persistenceResourcesFileName.append(ProjectConstants.DEFAULT_RESOURCES_DIR);
        persistenceResourcesFileName.append(PersistenceConstants.DEFAULT_RESOURCE_ID);
        
        File      persistenceResourcesFile     = new File(persistenceResourcesFileName.toString());
        XmlReader persistenceResourcesReader   = new XmlReader(persistenceResourcesFile);
        XmlNode   persistenceResourcesContent  = persistenceResourcesReader.getRoot();
        XmlNode   persistenceResourcesMappings = persistenceResourcesContent.getNode("mappings");
        
        if(persistenceResourcesMappings == null)
            persistenceResourcesMappings = new XmlNode("mappings");
        
        String        persistenceMappingName     = getDeclaration().getName();
        StringBuilder persistenceMappingFileName = new StringBuilder();

        persistenceMappingFileName.append(ProjectConstants.DEFAULT_RESOURCES_DIR);
        persistenceMappingFileName.append(PersistenceConstants.DEFAULT_MAPPINGS_DIR);
        persistenceMappingFileName.append(persistenceMappingName);
        persistenceMappingFileName.append(".hbm.xml");
        
        XmlNode       persistenceResourceMapping = new XmlNode("mapping", persistenceMappingName);
        List<XmlNode> persistenceMappings        = persistenceResourcesMappings.getChildNodes(); 
        File          persistenceMappingFile     = new File(persistenceMappingFileName.toString());

        if(getModelInfo().getMappedRepositoryId().length() > 0){
            if(!persistenceMappingFile.exists()){
                StringBuilder persistenceMappingTemplateFileName = new StringBuilder();

                persistenceMappingTemplateFileName.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
                persistenceMappingTemplateFileName.append(getTemplateId());
                persistenceMappingTemplateFileName.append(StringUtil.getDirectorySeparator());
                persistenceMappingTemplateFileName.append(ProjectConstants.DEFAULT_PERSISTENCE_MAPPING_TEMPLATE_FILE_ID);

                File             persistenceMappingTemplateFile    = new File(persistenceMappingTemplateFileName.toString());
                XmlReader        persistenceMappingTemplateReader  = new XmlReader(persistenceMappingTemplateFile);
                XmlNode          persistenceMappingTemplateContent = persistenceMappingTemplateReader.getRoot();
                ProcessorFactory processorFactory                  = ProcessorFactory.getInstance();
                BaseProcessor    processor                         = processorFactory.getProcessor(getModelInfo(), persistenceMappingTemplateContent);
                String           persistenceMappingContent         = processor.process();
                DocumentType     persistenceMappingDocumentType    = new DefaultDocumentType();
                String           persistenceMappingEncoding        = persistenceMappingTemplateReader.getEncoding();
                
                persistenceMappingDocumentType.setName("hibernate-mapping");
                persistenceMappingDocumentType.setPublicID("-//Hibernate/Hibernate Mapping DTD 3.0//EN");
                persistenceMappingDocumentType.setSystemID("http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd");
                
                XmlWriter persistenceMappingTemplateWriter = new XmlWriter(persistenceMappingFile, persistenceMappingDocumentType, persistenceMappingEncoding);

                persistenceMappingTemplateWriter.write(persistenceMappingContent);
            }
                
            Boolean found = false;
                    
            if(persistenceMappings != null){
                for(XmlNode persistenceMapping : persistenceMappings){
                    if(persistenceMapping.getValue().equals(persistenceMappingName)){
                        found = true;
                        
                        break;
                    }
                }
            }
            else
                persistenceResourcesContent.addChildNode(persistenceResourcesMappings);
                
            if(!found){
                generatePersistenceMappingFile = true;
                
                persistenceResourcesMappings.addChildNode(persistenceResourceMapping);
            }
        }
        else{
            if(persistenceMappingFile.exists()){
                if(persistenceMappings != null){
                    XmlNode persistenceMapping = null;
                    
                    for(int cont = 0 ; cont < persistenceMappings.size() ; cont++){
                        persistenceMapping = persistenceMappings.get(cont);
                        
                        if(persistenceMapping.getValue().equals(persistenceMappingName)){
                            generatePersistenceMappingFile = true;
                            
                            persistenceMappings.remove(cont);
                            
                            break;
                        }
                    }
                }
            }
        }
        
        if(generatePersistenceMappingFile){
            XmlWriter persistenceResourcesWriter = new XmlWriter(persistenceResourcesFile, persistenceResourcesReader.getDocumentType(), persistenceResourcesReader.getEncoding());
            
            persistenceResourcesWriter.write(persistenceResourcesContent);
        }
    }

    /**
     * Executa o script para geração de código da classe que implementa a regra de negócio.
     * 
     * @throws Throwable
     */
    public void generateServiceClass() throws Throwable{
        String        serviceClassName     = ServiceUtil.getServiceClassNameByModel(getDeclaration());
        StringBuilder serviceClassFileName = new StringBuilder();

        serviceClassFileName.append(ProjectConstants.DEFAULT_JAVA_DIR);
        serviceClassFileName.append(StringUtil.replaceAll(serviceClassName, ".", StringUtil.getDirectorySeparator()));
        serviceClassFileName.append(".java");

        File serviceClassFile = new File(serviceClassFileName.toString());

        if(getModelInfo().getUseCase().length() > 0 || getModelInfo().getMappedRepositoryId().length() > 0 || getModelInfo().getGenerateService()){
            String serviceClassContent = "";
            
            if(!serviceClassFile.exists()){
                StringBuilder serviceClassTemplateFileName = new StringBuilder();

                serviceClassTemplateFileName.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
                serviceClassTemplateFileName.append(getTemplateId());
                serviceClassTemplateFileName.append(StringUtil.getDirectorySeparator());
                serviceClassTemplateFileName.append(ProjectConstants.DEFAULT_SERVICE_CLASS_TEMPLATE_FILE_ID);

                File             serviceClassTemplateFile    = new File(serviceClassTemplateFileName.toString());
                XmlReader        serviceClassTemplateReader  = new XmlReader(serviceClassTemplateFile);
                XmlNode          serviceClassTemplateContent = serviceClassTemplateReader.getRoot();
                String           encoding                    = serviceClassTemplateReader.getEncoding(); 
                ProcessorFactory processorFactory            = ProcessorFactory.getInstance();
                BaseProcessor    processor                   = processorFactory.getProcessor(getModelInfo(), serviceClassTemplateContent);

                serviceClassContent = StringUtil.indent(processor.process(), JavaIndent.getRules());
                serviceClassContent = StringUtil.decode(serviceClassContent);

                FileUtil.toTextFile(serviceClassFileName.toString(), serviceClassContent, encoding);
            }
            else{
                Class<IService> serviceInterfaceClass = null;
                Service         serviceAnnotation     = null;
                Boolean         isRemoteService       = false;

                try{
                    serviceInterfaceClass = ServiceUtil.getServiceInterfaceClassByModel(getDeclaration());
                    serviceAnnotation     = (Service)serviceInterfaceClass.getAnnotation(Service.class);
                }
                catch(Throwable e){
                }

                if(serviceAnnotation != null && serviceAnnotation.name().length() > 0 && (serviceAnnotation.type() == ServiceType.STATEFUL || serviceAnnotation.type() == ServiceType.STATELESS))
                    isRemoteService = true;

                serviceClassContent = FileUtil.fromTextFile(serviceClassFileName.toString());

                if(isRemoteService){
                    if(serviceClassContent.contains(BaseService.class.getSimpleName())){
                        serviceClassContent = StringUtil.replaceAll(serviceClassContent, BaseService.class.getSimpleName(), BaseRemoteService.class.getSimpleName());
                        
                        FileUtil.toTextFile(serviceClassFileName.toString(), serviceClassContent);
                    }
                }
                else{
                    if(serviceClassContent.contains(BaseRemoteService.class.getSimpleName())){
                        serviceClassContent = StringUtil.replaceAll(serviceClassContent, BaseRemoteService.class.getSimpleName(), BaseService.class.getSimpleName());

                        FileUtil.toTextFile(serviceClassFileName.toString(), serviceClassContent);
                    }
                }
            }
        }
        else{
            if(serviceClassFile.exists())
                serviceClassFile.delete();
        }
    }

    /**
     * Executa o script para geração de código da interface da classe que implementa a regra de negócio.
     * 
     * @throws Throwable
     */
    public void generateServiceInterface() throws Throwable{
        String        serviceInterfaceClassName     = ServiceUtil.getServiceInterfaceClassNameByModel(getDeclaration());
        StringBuilder serviceInterfaceClassFileName = new StringBuilder();

        serviceInterfaceClassFileName.append(ProjectConstants.DEFAULT_JAVA_DIR);
        serviceInterfaceClassFileName.append(StringUtil.replaceAll(serviceInterfaceClassName, ".", StringUtil.getDirectorySeparator()));
        serviceInterfaceClassFileName.append(".java");

        File serviceInterfaceFile = new File(serviceInterfaceClassFileName.toString());

        if(getModelInfo().getUseCase().length() > 0 || getModelInfo().getMappedRepositoryId().length() > 0 || getModelInfo().getGenerateService()){
            if(!serviceInterfaceFile.exists()){
                StringBuilder serviceInterfaceClassTemplateFileName = new StringBuilder();

                serviceInterfaceClassTemplateFileName.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
                serviceInterfaceClassTemplateFileName.append(getTemplateId());
                serviceInterfaceClassTemplateFileName.append(StringUtil.getDirectorySeparator());
                serviceInterfaceClassTemplateFileName.append(ProjectConstants.DEFAULT_SERVICE_INTERFACE_TEMPLATE_FILE_ID);

                File             serviceInterfaceTemplateFile    = new File(serviceInterfaceClassTemplateFileName.toString());
                XmlReader        serviceInterfaceTemplateReader  = new XmlReader(serviceInterfaceTemplateFile);
                XmlNode          serviceInterfaceTemplateContent = serviceInterfaceTemplateReader.getRoot();
                String           encoding                        = serviceInterfaceTemplateReader.getEncoding();
                ProcessorFactory processorFactory                = ProcessorFactory.getInstance();
                BaseProcessor    processor                       = processorFactory.getProcessor(getModelInfo(), serviceInterfaceTemplateContent);
                String           serviceInterfaceContent         = StringUtil.indent(processor.process(), JavaIndent.getRules());
                
                serviceInterfaceContent = StringUtil.decode(serviceInterfaceContent);

                FileUtil.toTextFile(serviceInterfaceClassFileName.toString(), serviceInterfaceContent, encoding);
            }
        }
        else{
            if(serviceInterfaceFile.exists())
                serviceInterfaceFile.delete();
        }
    }

    /**
     * Executa o script para geração de código da interface home da classe que implementa a regra de negócio.
     * 
     * @throws Throwable
     */
    public void generateServiceHomeInterface() throws Throwable{
        String        serviceHomeInterfaceClassName     = ServiceUtil.getServiceHomeInterfaceClassNameByModel(getDeclaration());
        StringBuilder serviceHomeInterfaceClassFileName = new StringBuilder();

        serviceHomeInterfaceClassFileName.append(ProjectConstants.DEFAULT_JAVA_DIR);
        serviceHomeInterfaceClassFileName.append(StringUtil.replaceAll(serviceHomeInterfaceClassName, ".", StringUtil.getDirectorySeparator()));
        serviceHomeInterfaceClassFileName.append(".java");

        File serviceHomeInterfaceFile = new File(serviceHomeInterfaceClassFileName.toString());

        if(getModelInfo().getUseCase().length() > 0 || getModelInfo().getMappedRepositoryId().length() > 0 || getModelInfo().getGenerateService()){
            Boolean         isRemoteService       = false;
            Class<IService> serviceInterfaceClass = null;
            Service         serviceAnnotation     = null;

            try{
                serviceInterfaceClass = ServiceUtil.getServiceInterfaceClassByModel(getDeclaration());
                serviceAnnotation     = (Service)serviceInterfaceClass.getAnnotation(Service.class);
            }
            catch(Throwable e){
            }

            if(serviceAnnotation != null && serviceAnnotation.name().length() > 0 && (serviceAnnotation.type() == ServiceType.STATEFUL || serviceAnnotation.type() == ServiceType.STATELESS))
                isRemoteService = true;
            
            if(isRemoteService && !serviceHomeInterfaceFile.exists()){
                StringBuilder serviceHomeInterfaceClassTemplateFileName = new StringBuilder();

                serviceHomeInterfaceClassTemplateFileName.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
                serviceHomeInterfaceClassTemplateFileName.append(getTemplateId());
                serviceHomeInterfaceClassTemplateFileName.append(StringUtil.getDirectorySeparator());
                serviceHomeInterfaceClassTemplateFileName.append(ProjectConstants.DEFAULT_SERVICE_HOME_INTERFACE_TEMPLATE_FILE_ID);

                File             serviceHomeInterfaceTemplateFile    = new File(serviceHomeInterfaceClassTemplateFileName.toString());
                XmlReader        serviceHomeInterfaceTemplateReader  = new XmlReader(serviceHomeInterfaceTemplateFile);
                XmlNode          serviceHomeInterfaceTemplateContent = serviceHomeInterfaceTemplateReader.getRoot();
                String           encoding                            = serviceHomeInterfaceTemplateReader.getEncoding();
                ProcessorFactory processorFactory                    = ProcessorFactory.getInstance();
                BaseProcessor    processor                           = processorFactory.getProcessor(getModelInfo(), serviceHomeInterfaceTemplateContent);
                String           serviceHomeInterfaceContent         = StringUtil.indent(processor.process(), JavaIndent.getRules());
                
                serviceHomeInterfaceContent = StringUtil.decode(serviceHomeInterfaceContent);
                
                FileUtil.toTextFile(serviceHomeInterfaceClassFileName.toString(), serviceHomeInterfaceContent, encoding);
            }
            else if(!isRemoteService && serviceHomeInterfaceFile.exists())
                serviceHomeInterfaceFile.delete();
        }
        else{
            if(serviceHomeInterfaceFile.exists())
                serviceHomeInterfaceFile.delete();
        }
    }

    /**
     * Executa o script para geração de código da interface remota da classe que implementa a regra de negócio.
     * 
     * @throws Throwable
     */
    public void generateServiceRemoteInterface() throws Throwable{
        String        serviceRemoteInterfaceClassName     = ServiceUtil.getServiceRemoteInterfaceClassNameByModel(getDeclaration());
        StringBuilder serviceRemoteInterfaceClassFileName = new StringBuilder();

        serviceRemoteInterfaceClassFileName.append(ProjectConstants.DEFAULT_JAVA_DIR);
        serviceRemoteInterfaceClassFileName.append(StringUtil.replaceAll(serviceRemoteInterfaceClassName, ".", StringUtil.getDirectorySeparator()));
        serviceRemoteInterfaceClassFileName.append(".java");

        File serviceRemoteInterfaceFile = new File(serviceRemoteInterfaceClassFileName.toString());

        if(getModelInfo().getUseCase().length() > 0 || getModelInfo().getMappedRepositoryId().length() > 0 || getModelInfo().getGenerateService()){
            Boolean         isRemoteService       = false;
            Class<IService> serviceInterfaceClass = null;
            Service         serviceAnnotation     = null;

            try{
                serviceInterfaceClass = ServiceUtil.getServiceInterfaceClassByModel(getDeclaration());
                serviceAnnotation     = (Service)serviceInterfaceClass.getAnnotation(Service.class);
            }
            catch(Throwable e){
            }

            if(serviceAnnotation != null && serviceAnnotation.name().length() > 0 && (serviceAnnotation.type() == ServiceType.STATEFUL || serviceAnnotation.type() == ServiceType.STATELESS))
                isRemoteService = true;
            
            if(isRemoteService && !serviceRemoteInterfaceFile.exists()){
                StringBuilder serviceRemoteInterfaceClassTemplateFileName = new StringBuilder();

                serviceRemoteInterfaceClassTemplateFileName.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
                serviceRemoteInterfaceClassTemplateFileName.append(getTemplateId());
                serviceRemoteInterfaceClassTemplateFileName.append(StringUtil.getDirectorySeparator());
                serviceRemoteInterfaceClassTemplateFileName.append(ProjectConstants.DEFAULT_SERVICE_REMOTE_INTEFACE_TEMPLATE_FILE_ID);

                File             serviceRemoteInterfaceTemplateFile    = new File(serviceRemoteInterfaceClassTemplateFileName.toString());
                XmlReader        serviceRemoteInterfaceTemplateReader  = new XmlReader(serviceRemoteInterfaceTemplateFile);
                XmlNode          serviceRemoteInterfaceTemplateContent = serviceRemoteInterfaceTemplateReader.getRoot();
                String           encoding                              = serviceRemoteInterfaceTemplateReader.getEncoding();
                ProcessorFactory processorFactory                      = ProcessorFactory.getInstance();
                BaseProcessor    processor                             = processorFactory.getProcessor(getModelInfo(), serviceRemoteInterfaceTemplateContent);
                String           serviceRemoteInterfaceContent         = StringUtil.indent(processor.process(), JavaIndent.getRules());
                
                serviceRemoteInterfaceContent = StringUtil.decode(serviceRemoteInterfaceContent);

                FileUtil.toTextFile(serviceRemoteInterfaceClassFileName.toString(), serviceRemoteInterfaceContent, encoding);
            }
            else if(!isRemoteService && serviceRemoteInterfaceFile.exists())
                serviceRemoteInterfaceFile.delete();
        }
        else{
            if(serviceRemoteInterfaceFile.exists())
                serviceRemoteInterfaceFile.delete();
        }
    }

    /**
     * Executa o script para geração de código do mapeamento da classe que implementa a regra de negócio.
     * 
     * @throws Throwable
     */
    public void generateServiceMapping() throws Throwable{
        Boolean            generateServiceMappingFile = false;
        Class<IService>    serviceInterfaceClass      = null;
        Class<BaseService> serviceClass               = null;

        try{
            serviceClass          = ServiceUtil.getServiceClassByModel(getDeclaration());
            serviceInterfaceClass = ServiceUtil.getServiceInterfaceClassByModel(getDeclaration());
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
        String        projectName          = getAnnotationProcessorFactory().getProjectName();
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
        serviceMappingTemplateFileName.append(getTemplateId());
        serviceMappingTemplateFileName.append(StringUtil.getDirectorySeparator());
        serviceMappingTemplateFileName.append(ProjectConstants.DEFAULT_SERVICE_MAPPING_TEMPLATE_FILE_ID);
        
        StringBuilder serviceMappingFileName = new StringBuilder();

        serviceMappingFileName.append(ProjectConstants.DEFAULT_EJB_MODULE_DIR);
        serviceMappingFileName.append(ProjectConstants.DEFAULT_EJB_MODULE_DESCRIPTOR_FILE_ID);
        
        File      serviceMappingTemplateFile    = new File(serviceMappingTemplateFileName.toString());
        File      serviceMappingFile            = new File(serviceMappingFileName.toString());
        String    serviceHomeInterfaceId        = ServiceUtil.getServiceHomeInterfaceClassNameByModel(getDeclaration());
        String    serviceRemoteInterfaceId      = ServiceUtil.getServiceRemoteInterfaceClassNameByModel(getDeclaration());
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
        
        if(getModelInfo().getUseCase().length() > 0 || getModelInfo().getMappedRepositoryId().length() > 0 || getModelInfo().getGenerateService()){
            if(serviceAnnotation != null && serviceAnnotation.name().length() > 0 && (serviceAnnotation.type() == ServiceType.STATEFUL || serviceAnnotation.type() == ServiceType.STATELESS)){
                if(!hasServiceMapping){
                    generateServiceMappingFile = true;

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
                        generateServiceMappingFile = true;
                        
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
                    }
                    else{
                        serviceMappingsItems.remove(serviceMapping);
                    
                        generateServiceMappingFile = true;
                    }
                }
            }
        }
        else{
            if(hasServiceMapping){
                if((serviceMappingsItems.size() - 1) == 0){
                    if(serviceMappingFile.exists())
                        serviceMappingFile.delete();
                }
                else{
                    serviceMappingsItems.remove(serviceMapping);
                
                    generateServiceMappingFile = true;
                }
            }
        }

        if(generateServiceMappingFile){
            serviceMappings.setChildNodes(serviceMappingsItems);

            XmlWriter serviceMappingWriter = new XmlWriter(serviceMappingFile, serviceMappingTemplateReader.getDocumentType(), serviceMappingTemplateReader.getEncoding());

            serviceMappingWriter.write(serviceMappingTemplateContent);
        }
    }

    /**
     * Executa o script para geração de código da classe que implementa as ações do formulário.
     * 
     * @throws Throwable
     */
    public void generateActionClass() throws Throwable{
        String        actionClassName     = ActionFormUtil.getActionClassNameByModel(getDeclaration());
        StringBuilder actionClassFileName = new StringBuilder();

        actionClassFileName.append(ProjectConstants.DEFAULT_JAVA_DIR);
        actionClassFileName.append(StringUtil.replaceAll(actionClassName, ".", StringUtil.getDirectorySeparator()));
        actionClassFileName.append(".java");

        File actionClassFile = new File(actionClassFileName.toString());

        if(getModelInfo().getUseCase().length() > 0){
            if(!actionClassFile.exists()){
                StringBuilder actionClassTemplateFileName = new StringBuilder();

                actionClassTemplateFileName.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
                actionClassTemplateFileName.append(getTemplateId());
                actionClassTemplateFileName.append(StringUtil.getDirectorySeparator());
                actionClassTemplateFileName.append(ProjectConstants.DEFAULT_ACTION_CLASS_TEMPLATE_FILE_ID);

                File             actionClassTemplateFile    = new File(actionClassTemplateFileName.toString());
                XmlReader        actionClassTemplateReader  = new XmlReader(actionClassTemplateFile);
                XmlNode          actionClassTemplateContent = actionClassTemplateReader.getRoot();
                String           encoding                   = actionClassTemplateReader.getEncoding(); 
                ProcessorFactory processorFactory           = ProcessorFactory.getInstance();
                BaseProcessor    processor                  = processorFactory.getProcessor(getModelInfo(), actionClassTemplateContent);
                String           actionClassContent         = StringUtil.indent(processor.process(), JavaIndent.getRules());
                
                actionClassContent = StringUtil.decode(actionClassContent);
                
                FileUtil.toTextFile(actionClassFileName.toString(), actionClassContent, encoding);
            }
        }
        else{
            if(actionClassFile.exists())
                actionClassFile.delete();
        }
    }

    /**
     * Executa o script para geração de código da classe que implementa o formulário.
     * 
     * @throws Throwable
     */
    public void generateActionFormClass() throws Throwable{
        String        actionFormClassId       = ActionFormUtil.getActionFormClassNameByModel(getDeclaration());
        StringBuilder actionFormClassFileName = new StringBuilder();

        actionFormClassFileName.append(ProjectConstants.DEFAULT_JAVA_DIR);
        actionFormClassFileName.append(StringUtil.replaceAll(actionFormClassId, ".", StringUtil.getDirectorySeparator()));
        actionFormClassFileName.append(".java");

        File actionFormClassFile = new File(actionFormClassFileName.toString());

        if(getModelInfo().getUseCase().length() > 0){
            if(!actionFormClassFile.exists()){
                StringBuilder actionFormClassTemplateFileName = new StringBuilder();

                actionFormClassTemplateFileName.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
                actionFormClassTemplateFileName.append(getTemplateId());
                actionFormClassTemplateFileName.append(StringUtil.getDirectorySeparator());
                actionFormClassTemplateFileName.append(ProjectConstants.DEFAULT_ACTION_FORM_CLASS_TEMPLATE_FILE_ID);

                File             actionFormClassTemplateFile    = new File(actionFormClassTemplateFileName.toString());
                XmlReader        actionFormClassTemplateReader  = new XmlReader(actionFormClassTemplateFile);
                XmlNode          actionFormClassTemplateContent = actionFormClassTemplateReader.getRoot();
                String           encoding                       = actionFormClassTemplateReader.getEncoding(); 
                ProcessorFactory processorFactory               = ProcessorFactory.getInstance();
                BaseProcessor    processor                      = processorFactory.getProcessor(getModelInfo(), actionFormClassTemplateContent);
                String           actionFormClassContent         = StringUtil.indent(processor.process(), JavaIndent.getRules());

                actionFormClassContent = StringUtil.decode(actionFormClassContent);
                
                FileUtil.toTextFile(actionFormClassFileName.toString(), actionFormClassContent, encoding);
            }
        }
        else{
            if(actionFormClassFile.exists())
                actionFormClassFile.delete();
        }
    }

    /**
     * 
     * Executa o script para geração do arquivo de mapeamento das ações do formulário.
     * 
     * @throws Throwable
     */
    public void generateActionFormMapping() throws Throwable{
        StringBuilder actionFormMappingTemplateFileName = new StringBuilder();

        actionFormMappingTemplateFileName.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
        actionFormMappingTemplateFileName.append(getTemplateId());
        actionFormMappingTemplateFileName.append(StringUtil.getDirectorySeparator());
        actionFormMappingTemplateFileName.append(ProjectConstants.DEFAULT_ACTION_FORM_MAPPING_TEMPLATE_FILE_ID);

        StringBuilder actionFormMappingFileName = new StringBuilder();

        actionFormMappingFileName.append(ProjectConstants.DEFAULT_UI_DIR);
        actionFormMappingFileName.append(ProjectConstants.DEFAULT_ACTION_FORM_MAPPING_FILE_ID);
        
        File      actionFormMappingFile            = new File(actionFormMappingFileName.toString());
        File      actionFormMappingTemplateFile    = (actionFormMappingFile.exists() ? actionFormMappingFile : new File(actionFormMappingTemplateFileName.toString()));
        XmlReader actionFormMappingTemplateReader  = new XmlReader(actionFormMappingTemplateFile);
        XmlNode   actionFormMappingTemplateContent = actionFormMappingTemplateReader.getRoot();
        
        if(!actionFormMappingFile.exists())
            actionFormMappingTemplateContent = actionFormMappingTemplateContent.getNode("struts-config");
        
        XmlNode             actionMappings                = actionFormMappingTemplateContent.getNode("action-mappings");
        XmlNode             actionMapping                 = null;
        Map<String, String> actionMappingAttributes       = null;
        Collection<XmlNode> actionMappingsItems           = actionMappings.getChildNodes();
        String              actionFormObjectId            = ActionFormUtil.getActionFormObjectIdByModel(getDeclaration());
        String              actionClassName               = ActionFormUtil.getActionClassNameByModel(getDeclaration());
        Boolean             hasActionMapping              = false;
        Boolean             isFirstActionMapping          = false;
        Boolean             generateActionFormMappingFile = false;

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
        
        if(getModelInfo().getUseCase().length() > 0){
            if(!hasActionMapping){
                String        actionFormUrl   = ActionFormUtil.getActionFormUrlByModel(getDeclaration());
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
                
                generateActionFormMappingFile = true;
            }
        }
        else{
            if(hasActionMapping){
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
                
                generateActionFormMappingFile = true;
            }
        }

        XmlNode             actionFormMappings          = actionFormMappingTemplateContent.getNode("form-beans");
        XmlNode             actionFormMapping           = null;
        Map<String, String> actionFormMappingAttributes = null;
        Collection<XmlNode> actionFormMappingsItems     = actionFormMappings.getChildNodes();
        String              actionFormClassName         = ActionFormUtil.getActionFormClassNameByModel(getDeclaration());
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

        if(getModelInfo().getUseCase().length() > 0){
            if(!hasActionFormMapping){
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
                
                generateActionFormMappingFile = true;
            }
        }
        else{
            if(hasActionFormMapping){
                if((actionFormMappingsItems.size() - 1) == 0){
                    actionFormMappingAttributes.put("name", "");
                    actionFormMappingAttributes.put("type", "");
                }
                else
                    actionFormMappingsItems.remove(actionFormMapping);

                actionFormMappings.setChildNodes(actionFormMappingsItems);
                
                generateActionFormMappingFile = true;
            }
        }
        
        if(generateActionFormMappingFile){
            XmlWriter formMappingWriter = new XmlWriter(actionFormMappingFile, actionFormMappingTemplateReader.getDocumentType(), actionFormMappingTemplateReader.getEncoding());

            formMappingWriter.write(actionFormMappingTemplateContent);
        }
    }

    /**
     * Executa o script para geração de código da UI.
     * 
     * @throws Throwable
     */
    public void generateUiPage() throws Throwable{
        String        actionFormUrl        = ActionFormUtil.getActionFormUrlByModel(getDeclaration());
        StringBuilder uiPageDirName        = new StringBuilder();
        StringBuilder uiPageImagesDirName  = new StringBuilder();
        StringBuilder uiPageScriptsDirName = new StringBuilder();
        StringBuilder uiPageStylesDirName  = new StringBuilder();
        StringBuilder uiPageFileName       = new StringBuilder();
        File          uiPageDir            = null;
        File          uiPageImagesDir      = null;
        File          uiPageScriptsDir     = null;
        File          uiPageStylesDir      = null;
        File          uiPageFile           = null;

        uiPageDirName.append(ProjectConstants.DEFAULT_UI_DIR);
        uiPageDirName.append(actionFormUrl);

        uiPageImagesDirName.append(uiPageDirName.toString());
        uiPageImagesDirName.append(UIConstants.DEFAULT_IMAGES_RESOURCES_DIR);

        uiPageScriptsDirName.append(uiPageDirName.toString());
        uiPageScriptsDirName.append(UIConstants.DEFAULT_SCRIPTS_RESOURCES_DIR);

        uiPageStylesDirName.append(uiPageDirName.toString());
        uiPageStylesDirName.append(UIConstants.DEFAULT_STYLES_RESOURCES_DIR);

        uiPageFileName.append(uiPageDirName.toString());
        uiPageFileName.append(StringUtil.getDirectorySeparator());
        uiPageFileName.append("index.jsp");

        uiPageDir        = new File(uiPageDirName.toString());
        uiPageImagesDir  = new File(uiPageImagesDirName.toString());
        uiPageScriptsDir = new File(uiPageScriptsDirName.toString());
        uiPageStylesDir  = new File(uiPageStylesDirName.toString());
        uiPageFile       = new File(uiPageFileName.toString());

        if(getModelInfo().getUseCase().length() > 0){
            if(!uiPageFile.exists() || !uiPageDir.exists() || !uiPageImagesDir.exists() || !uiPageStylesDir.exists() || !uiPageScriptsDir.exists()){
                if(!uiPageDir.exists())
                    uiPageDir.mkdirs();

                if(!uiPageImagesDir.exists())
                    uiPageImagesDir.mkdirs();

                if(!uiPageScriptsDir.exists())
                    uiPageScriptsDir.mkdirs();

                if(!uiPageStylesDir.exists())
                    uiPageStylesDir.mkdirs();

                if(!uiPageFile.exists()){
                    StringBuilder webPageTemplateFileName = new StringBuilder();

                    webPageTemplateFileName.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
                    webPageTemplateFileName.append(getTemplateId());
                    webPageTemplateFileName.append(StringUtil.getDirectorySeparator());
                    webPageTemplateFileName.append(ProjectConstants.DEFAULT_UI_PAGE_TEMPLATE_FILE_ID);

                    File             webPageTemplateFile    = new File(webPageTemplateFileName.toString());
                    XmlReader        webPageTemplateReader  = new XmlReader(webPageTemplateFile);
                    XmlNode          webPageTemplateContent = webPageTemplateReader.getRoot();
                    String           encoding               = webPageTemplateReader.getEncoding(); 
                    ProcessorFactory processorFactory       = ProcessorFactory.getInstance();
                    BaseProcessor    processor              = processorFactory.getProcessor(getModelInfo(), webPageTemplateContent);
                    String           webPageContent         = StringUtil.indent(processor.process(), JSPIndent.getRules());
                    
                    webPageContent = StringUtil.decode(webPageContent);
                    
                    FileUtil.toTextFile(uiPageFileName.toString(), webPageContent, encoding);
                }
            }
            
            generateWebMapping();
        }
        else{
            if(uiPageFile.exists() || uiPageDir.exists() || uiPageImagesDir.exists() || uiPageStylesDir.exists() || uiPageScriptsDir.exists()){
                if(uiPageImagesDir.exists())
                    uiPageImagesDir.delete();

                if(uiPageScriptsDir.exists())
                    uiPageScriptsDir.delete();

                if(uiPageStylesDir.exists())
                    uiPageStylesDir.delete();

                if(uiPageFile.exists())
                    uiPageFile.delete();

                if(uiPageDir.exists())
                    uiPageDir.delete();
            }
        }
    }
    
    /**
     * Gera o arquivo descritor da aplicação.
     * 
     * @throws Throwable
     */
    private void generateWebMapping() throws Throwable{
        File webDir            = new File(ProjectConstants.DEFAULT_UI_DIR);
        File webDescriptorFile = new File(webDir.getAbsolutePath().concat(StringUtil.getDirectorySeparator()).concat(ProjectConstants.DEFAULT_WEB_MODULE_DESCRIPTOR_FILE_ID));

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
            
            taglibNode.addChildNode(new XmlNode("taglib-uri", ProjectConstants.DEFAULT_TAGLIBS_ID));
            taglibNode.addChildNode(new XmlNode("taglib-location", "/".concat(ProjectConstants.DEFAULT_TAGLIBS_DESCRIPTOR_FILE_ID)));
            
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
        Boolean            generateServiceMappingFile = false;
        Class<IService>    serviceInterfaceClass      = null;
        Class<BaseService> serviceClass               = null;

        try{
            serviceClass          = ServiceUtil.getServiceClassByModel(getDeclaration());
            serviceInterfaceClass = ServiceUtil.getServiceInterfaceClassByModel(getDeclaration());
        }
        catch(Throwable e){
            return;
        }
        
        StringBuilder webServicesMappingTemplateFileName = new StringBuilder();

        webServicesMappingTemplateFileName.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
        webServicesMappingTemplateFileName.append(getTemplateId());
        webServicesMappingTemplateFileName.append(StringUtil.getDirectorySeparator());
        webServicesMappingTemplateFileName.append(ProjectConstants.DEFAULT_WEB_SERVICE_MAPPING_TEMPLATE_FILE_ID);
        
        StringBuilder webServicesMappingFileName = new StringBuilder();

        webServicesMappingFileName.append(ProjectConstants.DEFAULT_WEB_SERVICES_MODULE_DIR);
        webServicesMappingFileName.append(ProjectConstants.DEFAULT_WEB_SERVICES_MODULE_DESCRIPTOR_FILE_ID);

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
                generateServiceMappingFile = true;

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
                    generateServiceMappingFile = true;

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
                }
                else{
                    generateServiceMappingFile = true;

                    webServicesMappingsItems.remove(webServicesMapping);
                }
            }
        }

        if(generateServiceMappingFile){
            webServicesMappingTemplateContent.setChildNodes(webServicesMappingsItems);

            XmlWriter webServicesMappingWriter = new XmlWriter(webServicesMappingFile, webServicesMappingTemplateReader.getDocumentType(), webServicesMappingTemplateReader.getEncoding());

            webServicesMappingWriter.write(webServicesMappingTemplateContent);
        }
    }

    /**
     * Executa o script para geração dos arquivos de internacionalização da página WEB.
     * 
     * @throws Throwable
     */
    public void generateWebPageI18nResource() throws Throwable{
        SystemResourceLoader loader                      = new SystemResourceLoader(getAnnotationProcessorFactory().getProjectResourcesDir(), SystemConstants.DEFAULT_RESOURCE_ID);
        SystemResource       resource                    = loader.getDefault();
        List<Locale>         languages                   = resource.getLanguages();
        String               actionFormUrl               = ActionFormUtil.getActionFormUrlByModel(getDeclaration());
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

            if(getModelInfo().getUseCase().length() > 0){
                if(!webPageI18nResourceFile.exists()){
                    StringBuilder webPageI18nResourceTemplateFileName = new StringBuilder();

                    webPageI18nResourceTemplateFileName.append(ProjectConstants.DEFAULT_TEMPLATES_DIR);
                    webPageI18nResourceTemplateFileName.append(getTemplateId());
                    webPageI18nResourceTemplateFileName.append(StringUtil.getDirectorySeparator());
                    webPageI18nResourceTemplateFileName.append(ProjectConstants.DEFAULT_I18N_RESOURCE_TEMPLATE_FILE_ID);

                    File             webPageI18nResourceTemplateFile    = new File(webPageI18nResourceTemplateFileName.toString());
                    XmlReader        webPageI18nResourceTemplateReader  = new XmlReader(webPageI18nResourceTemplateFile);
                    XmlNode          webPageI18nResourceTemplateContent = webPageI18nResourceTemplateReader.getRoot();
                    String           encoding                           = webPageI18nResourceTemplateReader.getEncoding(); 
                    ProcessorFactory processorFactory                   = ProcessorFactory.getInstance();
                    BaseProcessor    processor                          = processorFactory.getProcessor(getModelInfo(), webPageI18nResourceTemplateContent);
                    String           webPageI18nResourceContent         = StringUtil.indent(processor.process(), JavaIndent.getRules());
                    
                    webPageI18nResourceContent = StringUtil.decode(webPageI18nResourceContent);

                    FileUtil.toTextFile(webPageI18nResourceFileName.toString(), webPageI18nResourceContent, encoding);
                }
            }
            else{
                if(webPageI18nResourceFile.exists() && getModelInfo().getMappedRepositoryId().length() == 0)
                    webPageI18nResourceFile.delete();
            }
        }
    }
}