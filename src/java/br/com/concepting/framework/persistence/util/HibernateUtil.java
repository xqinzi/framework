package br.com.concepting.framework.persistence.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.dialect.DB2Dialect;
import org.hibernate.dialect.FirebirdDialect;
import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.dialect.SybaseASE15Dialect;

import br.com.concepting.framework.caching.CachedObject;
import br.com.concepting.framework.caching.Cacher;
import br.com.concepting.framework.caching.CacherManager;
import br.com.concepting.framework.context.resource.ContextResource;
import br.com.concepting.framework.model.exceptions.ItemNotFoundException;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.persistence.constants.PersistenceConstants;
import br.com.concepting.framework.persistence.helpers.HibernateSession;
import br.com.concepting.framework.persistence.interfaces.IDAO;
import br.com.concepting.framework.persistence.resource.PersistenceResource;
import br.com.concepting.framework.persistence.types.RepositoryType;
import br.com.concepting.framework.resource.FactoryResource;
import br.com.concepting.framework.resource.exceptions.InvalidResourceException;
import br.com.concepting.framework.util.StringUtil;

/**
 * Classe utilitária com rotinas de persistência utilizando o framework Hibernate.
 *
 * @author fvilarinho
 * @since 1.0
 */ 
public class HibernateUtil{
	/**
	 * Retorna uma sessão Hibernate com o repositório de dados utilizando as configurações de conexão 
	 * default.
	 * 
	 * @return Instância da sessão Hibernate.
	 * @throws HibernateException 
	 * @throws IllegalArgumentException
	 * @throws InvalidResourceException
	 */
	public static Session getSession() throws HibernateException, IllegalArgumentException, InvalidResourceException{
	    PersistenceResource persistenceResource = PersistenceUtil.getDefaultPersistenceResource();
        HibernateSession    hibernateSession    = buildHibernateSession(persistenceResource, true);
        SessionFactory      sessionFactory      = hibernateSession.getFactory();

        return sessionFactory.getCurrentSession();
	}
	
	/**
	 * Retorna uma sessão Hibernate com o repositório de dados utilizando configurações de conexão 
	 * específicas.
	 *
	 * @param dao Instância da classe de persistência contendo as configurações de persistência.
	 * @return Instância da sessão Hibernate.
	 * @throws HibernateException
	 * @throws IllegalArgumentException
	 */
	public static <D extends IDAO> Session getSession(D dao) throws HibernateException, IllegalArgumentException{
        PersistenceResource persistenceResource = dao.getPersistenceResource();
        Boolean             useTransaction      = dao.useTransaction();
        HibernateSession    hibernateSession    = buildHibernateSession(persistenceResource, useTransaction);
        SessionFactory      sessionFactory      = hibernateSession.getFactory();

        try{
            return sessionFactory.openSession();
        }
        catch(Throwable e){
            return sessionFactory.getCurrentSession();
        }
	}
	
	/**
	 * Retorna uma conexão JDBC com o repositório de dados utilizando as configurações de conexão 
	 * default.
	 * 
	 * @return Instância da conexão JDBC.
     * @throws SQLException
     * @throws HibernateException
     * @throws IllegalArgumentException
     * @throws InvalidResourceException
	 */
	public static Connection getConnection() throws SQLException, HibernateException, IllegalArgumentException, InvalidResourceException{
		return getConnection(PersistenceUtil.getDefaultPersistenceResource());
	}

	/**
	 * Retorna uma conexão JDBC com o repositório de dados utilizando configurações de conexão 
	 * específicas.
	 *
	 * @param persistenceResource Instância contendo as configurações de persistência.
	 * @return Instância da conexão JDBC.
	 * @throws SQLException 
	 * @throws HibernateException
	 * @throws IllegalArgumentException
	 */
	public static Connection getConnection(PersistenceResource persistenceResource) throws SQLException, HibernateException, IllegalArgumentException{
		HibernateSession   hibernateSession = buildHibernateSession(persistenceResource, true);
		ConnectionProvider provider         = hibernateSession.getProvider();
	
		return provider.getConnection();
	}

	/**
	 * Efetua a conexão com o repositório de persistência.
	 * 
	 * @param persistenceResource Instância contendo as configurações de persistência desejadas.
	 * @param useTransaction Indica se deve ou não gerenciar transações.
	 * @return Instância da conexão com o repositório de persistência.
	 * @throws HibernateException
	 * @throws IllegalArgumentException
	 */
	private static HibernateSession buildHibernateSession(PersistenceResource persistenceResource, Boolean useTransaction) throws HibernateException, IllegalArgumentException{
		Cacher           cacher  = CacherManager.getInstance().getCacher(HibernateUtil.class);
		CachedObject     object  = null;
		HibernateSession session = null;

		try{
			object  = cacher.get(StringUtil.trim(persistenceResource.getId()));
			session = object.getContent();
		}
		catch(ItemNotFoundException e){
			Configuration   configuration              = new Configuration();
			Properties      hibernateProperties        = new Properties();
			FactoryResource persistenceFactoryResource = persistenceResource.getFactoryResource();
			RepositoryType  repositoryType             = RepositoryType.toRepositoryType(persistenceFactoryResource.getType());

			if(repositoryType == RepositoryType.MYSQL)
				hibernateProperties.setProperty(Environment.DIALECT, MySQLDialect.class.getName());
			else if(repositoryType == RepositoryType.SYBASE)
				hibernateProperties.setProperty(Environment.DIALECT, SybaseASE15Dialect.class.getName());
			else if(repositoryType == RepositoryType.MSSQL)
				hibernateProperties.setProperty(Environment.DIALECT, SQLServerDialect.class.getName());
			else if(repositoryType == RepositoryType.ORACLE)
				hibernateProperties.setProperty(Environment.DIALECT, Oracle10gDialect.class.getName());
			else if(repositoryType == RepositoryType.POSTGRESQL)
				hibernateProperties.setProperty(Environment.DIALECT, PostgreSQLDialect.class.getName());
			else if(repositoryType == RepositoryType.DB2)
				hibernateProperties.setProperty(Environment.DIALECT, DB2Dialect.class.getName());
			else if(repositoryType == RepositoryType.FIREBIRD)
				hibernateProperties.setProperty(Environment.DIALECT, FirebirdDialect.class.getName());

            if(persistenceResource.getLookupName().length() > 0){
                ContextResource contextResource        = persistenceResource.getContextResource();
                FactoryResource contextFactoryResource = contextResource.getFactoryResource();
                
				hibernateProperties.setProperty(Environment.DATASOURCE, persistenceResource.getLookupName());

				String clazz = (contextFactoryResource == null ? "" : StringUtil.trim(contextFactoryResource.getClazz()));

				if(clazz.length() > 0){
					hibernateProperties.setProperty(Environment.JNDI_CLASS, clazz);

					String url = StringUtil.trim(contextFactoryResource.getUrl());

					if(url.length() > 0){
						url = PropertyUtil.fillPropertiesInString(contextResource, url);

						hibernateProperties.setProperty(Environment.JNDI_URL, url);
					}
				}

                if(useTransaction)
                    hibernateProperties.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                else
                    hibernateProperties.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, "jta");
            }
			else{
				String url = persistenceResource.getFactoryResource().getUrl();

				url = PropertyUtil.fillPropertiesInString(persistenceResource, url);

				hibernateProperties.setProperty(Environment.DRIVER, persistenceResource.getFactoryResource().getClazz());
				hibernateProperties.setProperty(Environment.URL, url);
				hibernateProperties.setProperty(Environment.USER, persistenceResource.getUser());
				hibernateProperties.setProperty(Environment.PASS, persistenceResource.getPassword());
				hibernateProperties.setProperty(Environment.C3P0_TIMEOUT, persistenceResource.getTimeout().toString());
                hibernateProperties.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
			}

			Map<String, String> options = persistenceResource.getOptions();

			if(options != null && options.size() > 0)
				for(String optionId : options.keySet())
					hibernateProperties.setProperty(optionId, options.get(optionId));
			
			configuration.setProperties(hibernateProperties);
			
			List<String> persistenceMappings = persistenceResource.getMappings();
			
			if(persistenceMappings != null && persistenceMappings.size() > 0){
			    StringBuilder persistenceMappingResource = new StringBuilder();
			    InputStream   persistenceMappingStream   = null;
			    
			    for(String persistenceMapping : persistenceMappings){
			        persistenceMappingResource.delete(0, persistenceMappingResource.length());
			        persistenceMappingResource.append(PersistenceConstants.DEFAULT_MAPPINGS_DIR);
			        persistenceMappingResource.append(persistenceMapping);
			        persistenceMappingResource.append(".hbm.xml");
			        
			        persistenceMappingStream = HibernateUtil.class.getClassLoader().getResourceAsStream(persistenceMappingResource.toString());
			        
			        if(persistenceMappingStream != null)
			            configuration.addInputStream(persistenceMappingStream);
			    }
			}
			
			session = new HibernateSession();
			session.setProvider(configuration.buildSettings().getConnectionProvider());
			session.setFactory(configuration.buildSessionFactory());
			
			object = new CachedObject();
			object.setId(persistenceResource.getId());
			object.setContent(session);

			try{
				cacher.add(object);
			}
			catch(Throwable e1){
			}
		}
		
		return session;
	}
}