package br.com.concepting.framework.processors.constants;

import br.com.concepting.framework.constants.Constants;

/**
 * Classe que define as constantes utilizada na definição da estrutura dos arquivos de uma
 * aplicação e também na geração de código.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public abstract class ProjectConstants extends Constants{
    public static final String COPY_DEPENDENCIES_TASK_ID                   = "copy.dependencies";
    public static final String CONCEPTING_FRAMEWORK_ID                     = "ConceptingFramework";
    public static final String CONCEPTING_FRAMEWORK_HOME_ID                = "${CONCEPTING_FRAMEWORK_HOME}";
    public static final String DEFAULT_BUILD_ID                            = "build";
    public static final String DEFAULT_BUILD_PROPERTIES_FILE_ID            = DEFAULT_BUILD_ID.concat(".properties");
    public static final String DEFAULT_BUILD_FILE_ID                       = DEFAULT_BUILD_ID.concat(".xml");
    public static final String DEFAULT_COMPILE_DIR                         = DEFAULT_BUILD_ID.concat("/compile/");
    public static final String DEFAULT_MODULES_DIR                         = DEFAULT_BUILD_ID.concat("/modules/");
    public static final String DEFAULT_EJB_MODULE_DIR                      = DEFAULT_MODULES_DIR.concat("ejb/");
    public static final String DEFAULT_WEB_MODULE_DIR                      = DEFAULT_MODULES_DIR.concat("web/");
    public static final String DEFAULT_WEB_SERVICES_MODULE_DIR             = DEFAULT_MODULES_DIR.concat("webServices/");
    public static final String DEFAULT_DISTRIBUTION_DIR                    = "dist/";
    public static final String DEFAULT_SOURCE_DIR                          = "src/";
    public static final String DEFAULT_JAVA_DIR                            = DEFAULT_SOURCE_DIR.concat("java/");
    public static final String DEFAULT_RESOURCES_DIR                       = DEFAULT_SOURCE_DIR.concat("resources/");
    public static final String DEFAULT_TESTS_DIR                           = DEFAULT_SOURCE_DIR.concat("tests/");
    public static final String DEFAULT_WEB_DIR                             = DEFAULT_SOURCE_DIR.concat("web/");
    public static final String DEFAULT_REPORTS_DIR                         = DEFAULT_SOURCE_DIR.concat("reports/");
    public static final String DEFAULT_SQL_DIR                             = DEFAULT_SOURCE_DIR.concat("sql/");
    public static final String DEFAULT_TEMPLATES_DIR                       = DEFAULT_SOURCE_DIR.concat("templates/");
    public static final String DEFAULT_APPLICATION_DESCRIPTOR_FILE_ID      = "META-INF/application.xml";
    public static final String DEFAULT_ACTION_FORM_MAPPING_FILE_ID         = "WEB-INF/struts-config.xml";
    public static final String DEFAULT_EJB_DESCRIPTOR_FILE_ID              = "META-INF/ejb-jar.xml";
    public static final String DEFAULT_WEB_DESCRIPTOR_FILE_ID              = "WEB-INF/web.xml";
    public static final String DEFAULT_WEB_SERVICES_DESCRIPTOR_FILE_ID     = "META-INF/services.xml";
    public static final String DEFAULT_WEB_LIB_DIR                         = DEFAULT_WEB_DIR.concat("WEB-INF/lib");
    public static final String DEFAULT_WEB_CLASSES_DIR                     = "WEB-INF/classes";
    public static final String DEFAULT_WEB_TAGLIBS_FILE_ID                 = "WEB-INF/tld/ConceptingFramework.tld";
    public static final String DEFAULT_WEB_TAGLIBS_ID                      = "http://www.concepting.com.br/framework/tags";
    public static final String DEFAULT_DEPENDENCIES_DIR                    = "dependencies/";
    public static final String DEFAULT_COMPILE_DEPENDENCIES_DIR            = DEFAULT_DEPENDENCIES_DIR.concat("compile/"); 
    public static final String DEFAULT_EJB_DEPENDENCIES_DIR                = DEFAULT_DEPENDENCIES_DIR.concat("ejb/"); 
    public static final String DEFAULT_WEB_SERVICES_DEPENDENCIES_DIR       = DEFAULT_DEPENDENCIES_DIR.concat("webServices/");
    public static final String DEFAULT_DEPENDENCIES_FILE_ID                = "dependencies.xml";
    public static final String DEFAULT_PERSISTENCE_CLASS_TEMPLATE_ID       = "persistenceClass.xml";
    public static final String DEFAULT_PERSISTENCE_MAPPING_TEMPLATE_ID     = "persistenceMapping.xml";
    public static final String DEFAULT_PERSISTENCE_INTERFACE_TEMPLATE_ID   = "persistenceInterface.xml";
    public static final String DEFAULT_SERVICE_CLASS_TEMPLATE_ID           = "serviceClass.xml";
    public static final String DEFAULT_SERVICE_INTERFACE_TEMPLATE_ID       = "serviceInterface.xml";
    public static final String DEFAULT_SERVICE_REMOTE_INTEFACE_TEMPLATE_ID = "serviceRemoteInterface.xml";
    public static final String DEFAULT_SERVICE_HOME_INTERFACE_TEMPLATE_ID  = "serviceHomeInterface.xml";
    public static final String DEFAULT_SERVICE_MAPPING_TEMPLATE_ID         = "serviceMapping.xml";
    public static final String DEFAULT_ACTION_CLASS_TEMPLATE_ID            = "actionClass.xml";
    public static final String DEFAULT_ACTION_FORM_CLASS_TEMPLATE_ID       = "actionFormClass.xml";
    public static final String DEFAULT_ACTION_FORM_MAPPING_TEMPLATE_ID     = "actionFormMapping.xml";
    public static final String DEFAULT_WEB_PAGE_TEMPLATE_ID                = "webPage.xml";
    public static final String DEFAULT_WEB_PAGE_I18N_RESOURCE_TEMPLATE_ID  = "webPageI18nResource.xml";
    public static final String DEFAULT_WEB_SERVICE_MAPPING_TEMPLATE_ID     = "webServiceMapping.xml";
    public static final String INVALID_PROJECT_NAME_MESSAGE_KEY            = "invalid.project.name.message";
    public static final String PROJECT_NAME_LABEL_KEY                      = "project.name.label";
}
