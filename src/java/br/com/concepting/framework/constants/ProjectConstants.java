package br.com.concepting.framework.constants;

/**
 * Classe que define as constantes utilizada na defini��o da estrutura dos arquivos de uma
 * aplica��o e tamb�m na gera��o de c�digo.
 * 
 * @author fvilarinho
 * @since 3.0
 */
public abstract class ProjectConstants{
    /**
     * Constante que define o identificador da task de c�pia de depend�ncias do projeto.
     */
    public static final String COPY_DEPENDENCIES_TASK_ID = "copy.dependencies";
    
    /**
     * Constante que define o identificador do framework.
     */
    public static final String CONCEPTING_FRAMEWORK_ID = "ConceptingFramework";
    
    /**
     * Constante que define o identificador do diret�rio de instala��o do framework.
     */
    public static final String CONCEPTING_FRAMEWORK_HOME_ID = "${CONCEPTING_FRAMEWORK_HOME}";
    
    /**
     * Constante que define o identificador da task de build do projeto.
     */
    public static final String DEFAULT_BUILD_ID = "build";
    
    /**
     * Constante que define o identificador do arquivo de propriedades de build do projeto.
     */
    public static final String DEFAULT_BUILD_PROPERTIES_FILE_ID = DEFAULT_BUILD_ID.concat(".properties");
    
    /**
     * Constante que define o identificador do arquivo de script de build do projeto.
     */
    public static final String DEFAULT_BUILD_FILE_ID = DEFAULT_BUILD_ID.concat(".xml");
    
    /**
     * Constante que define o identificador do diret�rio de compila��o do projeto.
     */
    public static final String DEFAULT_COMPILE_DIR = DEFAULT_BUILD_ID.concat("/compile/");
    
    /**
     * Constante que define o identificador do diret�rio de build dos m�dulos do projeto.
     */
    public static final String DEFAULT_MODULES_DIR = DEFAULT_BUILD_ID.concat("/modules/");
    
    /**
     * Constante que define o identificador do diret�rio do m�dulo EJB do projeto.
     */
    public static final String DEFAULT_EJB_MODULE_DIR = DEFAULT_MODULES_DIR.concat("ejb/");

    /**
     * Constante que define o identificador do diret�rio do m�dulo WEB do projeto.
     */
    public static final String DEFAULT_WEB_MODULE_DIR = DEFAULT_MODULES_DIR.concat("web/");
    
    /**
     * Constante que define o identificador do diret�rio do m�dulo WEB Services do projeto.
     */
    public static final String DEFAULT_WEB_SERVICES_MODULE_DIR = DEFAULT_MODULES_DIR.concat("webServices/");

    /**
     * Constante que define o identificador do arquivo descritor do m�dulo WEB Services do projeto.
     */
    public static final String DEFAULT_WEB_SERVICES_MODULE_DESCRIPTOR_FILE_ID = "META-INF/services.xml";
    
    /**
     * Constante que define o identificador do reposit�rio de WEB Services.
     */
    public static final String DEFAULT_WEB_SERVICES_REPOSITORY_ID = "/axis2/services/";

    /**
     * Constante que define o identificador do diret�rio de distribui��o dos m�dulos do projeto.
     */
    public static final String DEFAULT_DISTRIBUTION_DIR = "dist/";

    /**
     * Constante que define o identificador do diret�rio de fontes do projeto.
     */
    public static final String DEFAULT_SOURCE_DIR = "src/";
    
    /**
     * Constante que define o identificador do diret�rio de fontes java do projeto.
     */
    public static final String DEFAULT_JAVA_DIR = DEFAULT_SOURCE_DIR.concat("java/");
    
    /**
     * Constante que define o identificador do diret�rio dos arquivos de configura��o do projeto.
     */
    public static final String DEFAULT_RESOURCES_DIR = DEFAULT_SOURCE_DIR.concat("resources/");
    
    /**
     * Constante que define o identificador do diret�rio de testes unit�rios do projeto.
     */
    public static final String DEFAULT_TESTS_DIR = DEFAULT_SOURCE_DIR.concat("tests/");

    /**
     * Constante que define o identificador do diret�rio de fontes do UI do projeto.
     */
    public static final String DEFAULT_UI_DIR = DEFAULT_SOURCE_DIR.concat("ui/");
    
    /**
     * Constante que define o identificador do diret�rio de relat�rios do projeto.
     */
    public static final String DEFAULT_REPORTS_DIR = DEFAULT_SOURCE_DIR.concat("reports/");
    
    /**
     * Constante que define o identificador do diret�rio de scripts SQL do projeto.
     */
    public static final String DEFAULT_SQL_DIR = DEFAULT_SOURCE_DIR.concat("sql/");
    
    /**
     * Constante que define o identificador do diret�rio de templates do projeto.
     */
    public static final String DEFAULT_TEMPLATES_DIR = DEFAULT_SOURCE_DIR.concat("templates/");
    
    /**
     * Constante que define o identificador do arquivo descritor para deploy do projeto.
     */
    public static final String DEFAULT_APPLICATION_DESCRIPTOR_FILE_ID = "META-INF/application.xml";
    
    /**
     * Constante que define o identificador do arquivo de mapeamento dos formul�rios do projeto.
     */
    public static final String DEFAULT_ACTION_FORM_MAPPING_FILE_ID = "WEB-INF/struts-config.xml";
    
    /**
     * Constante que define o identificador do arquivo descritor do m�dulo EJB do projeto.
     */
    public static final String DEFAULT_EJB_MODULE_DESCRIPTOR_FILE_ID = "META-INF/ejb-jar.xml";

    /**
     * Constante que define o identificador do arquivo descritor do m�dulo WEB do projeto.
     */
    public static final String DEFAULT_WEB_MODULE_DESCRIPTOR_FILE_ID = "WEB-INF/web.xml";
    
    /**
     * Constante que define o identificador do import dos componentes visuais do framework.
     */
    public static final String DEFAULT_TAGLIBS_ID = "http://www.concepting.com.br/framework/tags";
    
    /**
     * Constante que define o identificador do arquivo que cont�m as defini��es dos componentes visuais do framework.
     */
    public static final String DEFAULT_TAGLIBS_DESCRIPTOR_FILE_ID = "WEB-INF/tld/ConceptingFramework.tld";
    
    /**
     * Constante que define o identificador do diret�rio de armazenamento das depend�ncias do m�dulo WEB do projeto..
     */
    public static final String DEFAULT_WEB_MODULE_LIB_DIR = DEFAULT_UI_DIR.concat("WEB-INF/lib");
    
    /**
     * Constante que define o identificador do diret�rio de compila��o do m�dulo WEB do projeto..
     */
    public static final String DEFAULT_WEB_MODULE_CLASSES_DIR = "WEB-INF/classes";
    
    /**
     * Constante que define o identificador do diret�rio de depend�ncias do projeto.
     */
    public static final String DEFAULT_DEPENDENCIES_DIR = "dependencies/";
    
    /**
     * Constante que define o identificador do arquivo que cont�m das defini��es de depend�ncias do projeto.
     */
    public static final String DEFAULT_DEPENDENCIES_FILE_ID = "dependencies.xml";

    /**
     * Constante que define o identificador do diret�rio de armazenamento das depend�ncias de compila��o do projeto.
     */
    public static final String DEFAULT_COMPILE_DEPENDENCIES_DIR = DEFAULT_DEPENDENCIES_DIR.concat("compile/");
    
    /**
     * Constante que define o identificador do diret�rio de armazenamento das depend�ncias do m�dulo EJB do projeto.
     */
    public static final String DEFAULT_EJB_MODULE_DEPENDENCIES_DIR = DEFAULT_DEPENDENCIES_DIR.concat("ejb/");
    
    /**
     * Constante que define o identificador do diret�rio de armazenamento das depend�ncias do m�dulo WEB Services do projeto.
     */
    public static final String DEFAULT_WEB_SERVICES_MODULE_DEPENDENCIES_DIR = DEFAULT_DEPENDENCIES_DIR.concat("webServices/");
    
    /**
     * Constante que define o identificador do template para a implementa��o de persist�ncia de um modelo de dados.
     */
    public static final String DEFAULT_PERSISTENCE_CLASS_TEMPLATE_FILE_ID = "persistenceClass.xml";

    /**
     * Constante que define o identificador do template para o mapeamento de persist�ncia de um modelo de dados.
     */
    public static final String DEFAULT_PERSISTENCE_MAPPING_TEMPLATE_FILE_ID = "persistenceMapping.xml";
    
    /**
     * Constante que define o identificador do template que define a interface da implementa��o de persist�ncia de um modelo de dados.
     */
    public static final String DEFAULT_PERSISTENCE_INTERFACE_TEMPLATE_FILE_ID = "persistenceInterface.xml";
    
    /**
     * Constante que define o identificador do template da implementa��o da regra de neg�cio de um modelo de dados.
     */
    public static final String DEFAULT_SERVICE_CLASS_TEMPLATE_FILE_ID = "serviceClass.xml";

    /**
     * Constante que define o identificador do template da interface da implementa��o da regra de neg�cio de um modelo de dados.
     */
    public static final String DEFAULT_SERVICE_INTERFACE_TEMPLATE_FILE_ID = "serviceInterface.xml";
    /**
     * Constante que define o identificador do template da interface remota para a implementa��o da regra de neg�cio de um modelo de dados.
     */

    public static final String DEFAULT_SERVICE_REMOTE_INTEFACE_TEMPLATE_FILE_ID = "serviceRemoteInterface.xml";
    
    /**
     * Constante que define o identificador do template da interface EJB para a implementa��o da regra de neg�cio de um modelo de dados.
     */
    public static final String DEFAULT_SERVICE_HOME_INTERFACE_TEMPLATE_FILE_ID  = "serviceHomeInterface.xml";
    
    /**
     * Constante que define o identificador do template do mapeamento das regras de neg�cio dos modelos de dados.
     */
    public static final String DEFAULT_SERVICE_MAPPING_TEMPLATE_FILE_ID = "serviceMapping.xml";

    /**
     * Constante que define o identificador do template das a��es de um p�gina WEB.
     */
    public static final String DEFAULT_ACTION_CLASS_TEMPLATE_FILE_ID = "actionClass.xml";
    
    /**
     * Constante que define o identificador do template do formul�rio de um p�gina WEB.
     */
    public static final String DEFAULT_ACTION_FORM_CLASS_TEMPLATE_FILE_ID = "actionFormClass.xml";
    
    /**
     * Constante que define o identificador do template do mapeamento de a��es dos formul�rios.
     */
    public static final String DEFAULT_ACTION_FORM_MAPPING_TEMPLATE_FILE_ID = "actionFormMapping.xml";

    /**
     * Constante que define o identificador do template de uma p�gina WEB.
     */
    public static final String DEFAULT_UI_PAGE_TEMPLATE_FILE_ID  = "uiPage.xml";

    /**
     * Constante que define o identificador do template para o arquivo de internacionaliza��o.
     */
    public static final String DEFAULT_I18N_RESOURCE_TEMPLATE_FILE_ID = "i18nResource.xml";
    
    /**
     * Constante que define o identificador do template para o mapeamento de um WEB Service.
     */
    public static final String DEFAULT_WEB_SERVICE_MAPPING_TEMPLATE_FILE_ID = "webServiceMapping.xml";
    
    /**
     * Constante que define o identificador da propriedade da mensagem exibida quando o nome do projeto for inv�lido.
     */
    public static final String INVALID_PROJECT_NAME_MESSAGE_KEY = "invalid.project.name.message";
    
    /**
     * Constante que define o identificador da propriedade que cont�m o label do nome do projeto.
     */
    public static final String PROJECT_NAME_LABEL_KEY = "project.name.label";

    /**
     * Constante que define o identificador da propriedade do nome do projeto.
     */
    public static final String PROJECT_NAME_KEY = "projectName";
}
