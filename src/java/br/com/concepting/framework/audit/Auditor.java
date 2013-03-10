package br.com.concepting.framework.audit;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import br.com.concepting.framework.audit.annotations.Auditable;
import br.com.concepting.framework.audit.appenders.AuditorPatternLayout;
import br.com.concepting.framework.audit.appenders.BaseAuditorAppender;
import br.com.concepting.framework.audit.constants.AuditorConstants;
import br.com.concepting.framework.audit.resource.AuditorResource;
import br.com.concepting.framework.audit.resource.AuditorResourceLoader;
import br.com.concepting.framework.audit.types.AuditorStatusType;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.resource.FactoryResource;
import br.com.concepting.framework.security.model.LoginSessionModel;
import br.com.concepting.framework.util.ExceptionUtil;
import br.com.concepting.framework.util.StringUtil;

/**
 * Classe responsável por efetuar a auditoria.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class Auditor{
	private Logger            logger                  = null;
	private Class             entity                  = null;
	private Method            business                = null;
	private String            businessArgumentsIds[]  = null;
	private Object            businessArgumentsValues = null;
	private LoginSessionModel loginSession            = null;
	private AuditorResource   auditorResource         = null;
	
    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param entity Classe que define a entidade a ser auditada.
     * @param business Instância contendo as propriedades do método da entidade.
     * @param businessArguments Instâncias dos argumentos do método.
     */
	public Auditor(Class entity, Method business, Object businessArguments){
		this(entity, business, businessArguments, null, null);
	}

    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param entity Classe que define a entidade a ser auditada.
     * @param business Instância contendo as propriedades do método da entidade.
     * @param businessArguments Instâncias dos argumentos do método.
     * @param auditorResource Instância das configurações de auditoria.
     */
	public Auditor(Class entity, Method business, Object businessArguments, AuditorResource auditorResource){
		this(entity, business, businessArguments, null, auditorResource);
	}
	
    /**
     * Construtor - Inicializa objetos e/ou variáveis internas.
     * 
     * @param entity Classe que define a entidade a ser auditada.
     * @param business Instância contendo as propriedades do método da entidade.
     * @param businessArguments Instâncias dos argumentos do método.
     * @param loginSession Instância contendo as propriedades da sessão de login.
     * @param auditorResource Instância das configurações de auditoria.
     */
	public Auditor(Class entity, Method business, Object businessArguments, LoginSessionModel loginSession, AuditorResource auditorResource){
		super();

		setEntity(entity);
		setBusiness(business);
		setBusinessArgumentsValues(businessArguments);
		setLoginSession(loginSession);
        setAuditorResource(auditorResource);

		logger = Logger.getLogger(entity);
		
		Logger.getRootLogger().removeAllAppenders();

		loadAuditorResource();
		loadLevel();
		loadAppenders();
	}
    
    /**
     * Retorna os identificadores dos argumentos do método a ser auditado.
     *
     * @return Array contendo os identificadores dos argumentos do método.
     */
    public String[] getBusinessArgumentsIds(){
        return businessArgumentsIds;
    }

    /**
     * Define os identificadores dos argumentos do método a ser auditado.
     *
     * @param businessArgumentsIds Array contendo os identificadores dos argumentos do método.
     */
    public void setBusinessArgumentsIds(String businessArgumentsIds[]){
        this.businessArgumentsIds = businessArgumentsIds;
    }

    /**
	 * Retorna as instâncias dos argumentos do método a ser auditado.
	 *
	 * @return Instâncias dos argumentos do método.
	 */
    public <O> O getBusinessArgumentsValues(){
    	return (O)businessArgumentsValues;
    }

    /**
     * Define as instâncias dos argumentos do método a ser auditado.
     *
     * @param businessArgumentsValues Instâncias dos argumentos do método.
     */
	public <O> void setBusinessArgumentsValues(O businessArgumentsValues){
    	this.businessArgumentsValues = businessArgumentsValues;
	}

	/**
	 * Retorna a classe da entidade a ser auditada.
	 * 
	 * @return Classe da entidade a ser auditada.
	 */
	public Class getEntity(){
		return entity;
	}

	/**
	 * Define a classe da entidade a ser auditada.
	 *
	 * @param entity Classe que define a entidade a ser auditada.
	 */
	public void setEntity(Class entity){
    	this.entity = entity;
    }

	/**
	 * Retorna o método da entidade a ser auditada.
	 * 
	 * @return Instância contendo as propriedades do método da entidade.
	 */
	public Method getBusiness(){
		return business;
	}

	/**
	 * Define o método da entidade a ser auditada.
	 *
	 * @param business Instância contendo as propriedades do método da entidade.
	 */
	public void setBusiness(Method business){
    	this.business = business;
    	
        Auditable auditable              = business.getAnnotation(Auditable.class);
        String    businessArgumentsIds[] = (auditable != null ? auditable.businessArgumentsIds() : null);
        
        setBusinessArgumentsIds(businessArgumentsIds);
    }

	/**
	 * Retorna a instância da sessão de login atual.
	 *
	 * @return Instância contendo as propriedades da sessão de login.
	 */
	public LoginSessionModel getLoginSession(){
    	return loginSession;
    }

	/**
	 * Define a instância da sessão de login atual.
	 *
	 * @param loginSession Instância contendo as propriedades da sessão de login.
	 */
	public void setLoginSession(LoginSessionModel loginSession){
    	this.loginSession = loginSession;
    }

	/**
	 * Retorna a instância das configurações de auditoria.
	 * 
	 * @return Instância das configurações de auditoria.
	 */
	public AuditorResource getAuditorResource(){
		return auditorResource;
	}

	/**
	 * Define a instância das configurações de auditoria.
	 * 
	 * @param auditorResource Instância das configurações de auditoria.
	 */
	public void setAuditorResource(AuditorResource auditorResource){
    	this.auditorResource = auditorResource;
    }

	/**
	 * Carrega as configurações de auditoria.
	 */
    private void loadAuditorResource(){
		if(auditorResource == null){
    		if(entity != null){
    	        Auditable annotation = (Auditable)entity.getAnnotation(Auditable.class);
    	        
    	        if(annotation == null){
    	            Class superClasses[] = entity.getInterfaces();

    	            if(superClasses != null && superClasses.length > 0){
    	                for(Class superClass : superClasses){
    	                    annotation = (Auditable)superClass.getAnnotation(Auditable.class);
    	                    
    	                    if(annotation != null)
    	                        break;
    	                }
    	            }
    	        }
    		    
    			String auditableResourceId = "";
    
    			if(annotation != null)
    				auditableResourceId = annotation.auditorResourceId();
    
    			try{
    				AuditorResourceLoader auditorResourceLoader = new AuditorResourceLoader();
    
    				auditorResource = auditorResourceLoader.get(auditableResourceId);
    			}
    			catch(Throwable e){
    			}
    		}
		}
	}

	/**
	 * Define o nível de auditoria das mensagens. Os níveis definidos são de 'debug', 'info' e/ou 'error'.
	 */
	private void loadLevel(){
		if(auditorResource != null){
			Level  level       = Level.OFF;
			String levelBuffer = auditorResource.getLevel();

			if(levelBuffer.length() > 0)
				level = Level.toLevel(levelBuffer);

			logger.setLevel(level);
		}
	}

	/**
	 * Instância as classes de redirecionamento das mensagens de auditoria.
	 */
    private void loadAppenders(){
		if(auditorResource != null){
			Collection<FactoryResource> appendersResources = auditorResource.getAppenders();

			if(appendersResources != null){
				Class                 appenderClass         = null;
				Appender              appenderInstance      = null;
				Map<String, String>   appenderOptions       = null;
				Class                 appenderLayoutClass   = null;
				Layout                appenderLayout        = null;
				String                appenderLayoutPattern = "";
				Enumeration<Appender> allAppenders          = logger.getAllAppenders();
				Boolean               hasAppender           = false;

				for(FactoryResource appenderResource : appendersResources){
					appenderOptions = appenderResource.getOptions();
					hasAppender     = false;

					while(allAppenders.hasMoreElements()){
						appenderInstance = allAppenders.nextElement();

						if(appenderInstance.getClass().getName().equals(appenderResource.getClazz())){
							hasAppender = true;
							
							if(appenderInstance instanceof BaseAuditorAppender)
							    ((BaseAuditorAppender)appenderInstance).setAuditor(this);

							break;
						}
					}

					if(!hasAppender){
						try{
							appenderClass    = Class.forName(appenderResource.getClazz());
							appenderInstance = (Appender)ConstructorUtils.invokeConstructor(appenderClass, this);

							if(appenderOptions != null && appenderOptions.size() > 0){
								for(String appenderOption : appenderOptions.keySet()){
									if(appenderInstance.requiresLayout()){
										if(appenderOption.equals(AuditorConstants.DEFAULT_LAYOUT_CLASS_KEY)){
											appenderLayoutClass   = Class.forName(appenderOptions.get(appenderOption));
											appenderLayout        = (Layout)ConstructorUtils.invokeConstructor(appenderLayoutClass, null);
											appenderLayoutPattern = StringUtil.trim(appenderOptions.get(AuditorConstants.DEFAULT_LAYOUT_PATTERN_KEY));

											if(appenderLayoutPattern.length() > 0){
												if(appenderLayout instanceof AuditorPatternLayout)
													((AuditorPatternLayout)appenderLayout).setPattern(appenderLayoutPattern);
												else if(appenderLayout instanceof PatternLayout)
													((PatternLayout)appenderLayout).setConversionPattern(appenderLayoutPattern);
											}

											appenderInstance.setLayout(appenderLayout);
										}
									}

									try{
										PropertyUtil.setProperty(appenderInstance, appenderOption, appenderOptions.get(appenderOption));
									}
									catch(Throwable e){
									}
								}
							}
							
							logger.addAppender(appenderInstance);
						}
						catch(Throwable e){
						}
					}
				}
			}
		}
	}

	/**
	 * Gera uma mensagem de informação.
	 * 
	 * @param message String contendo a mensagem desejada.
	 */
	public void info(String message){
		logger.info(message);
	}
	
    /**
     * Gera uma mensagem de informação.
     * 
     * @param status Constante que define o status de processamento da auditoria.
     */
	public void info(AuditorStatusType status){
	    logger.info(status);
	}

	/**
	 * Gera uma mensagem de debug.
	 * 
	 * @param message String contendo a mensagem desejada.
	 */
	public void debug(String message){
		logger.debug(message);
	}

	/**
	 * Gera uma mensagem de erro.
	 * 
	 * @param message String contendo a mensagem desejada.
	 */
	public void error(String message){
		logger.error(message);
	}

	/**
	 * Gera uma mensagem de erro.
	 * 
	 * @param exception Instância da exceção gerada.
	 */
	public void error(Throwable exception){
		StringBuilder buffer = new StringBuilder();

		buffer.append(StringUtil.getLineBreak());
		buffer.append(ExceptionUtil.getTrace(exception));

		error(buffer.toString());
	}

	/**
	 * Inicializa a auditoria.
	 */
	public void start(){
		info(AuditorStatusType.INIT);
	}

	/**
	 * Finaliza a auditoria sem erros.
	 */
	public void end(){
		info(AuditorStatusType.PROCESSED);
	}

	/**
	 * Finaliza a auditoria quando houver erros.
	 * 
	 * @param exception Instância da exceção gerada.
	 */
	public void end(Throwable exception){
	    logger.info(AuditorStatusType.ERROR, exception);
	}
}