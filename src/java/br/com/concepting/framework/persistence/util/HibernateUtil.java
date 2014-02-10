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
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.DB2Dialect;
import org.hibernate.dialect.FirebirdDialect;
import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.dialect.PostgresPlusDialect;
import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.dialect.SybaseASE15Dialect;
import org.hibernate.jdbc.Work;
import org.hibernate.metamodel.MetadataSources;
import org.hibernate.service.ServiceRegistry;

import br.com.concepting.framework.caching.CachedObject;
import br.com.concepting.framework.caching.Cacher;
import br.com.concepting.framework.caching.CacherManager;
import br.com.concepting.framework.context.resource.ContextResource;
import br.com.concepting.framework.model.exceptions.ItemNotFoundException;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.persistence.constants.PersistenceConstants;
import br.com.concepting.framework.persistence.interfaces.IDAO;
import br.com.concepting.framework.persistence.resource.PersistenceResource;
import br.com.concepting.framework.persistence.types.RepositoryType;
import br.com.concepting.framework.resource.FactoryResource;
import br.com.concepting.framework.resource.exceptions.InvalidResourceException;
import br.com.concepting.framework.util.StringUtil;

/**
 * Classe utilit�ria com rotinas de persist�ncia utilizando o framework Hibernate.
 *
 * @author fvilarinho
 * @since 1.0
 */ 
public abstract class HibernateUtil{
	/**
	 * Retorna uma sess�o Hibernate com o reposit�rio de dados utilizando as configura��es de conex�o 
	 * default.
	 * 
	 * @return Inst�ncia da sess�o Hibernate.
	 * @throws HibernateException 
	 * @throws IllegalArgumentException
	 * @throws InvalidResourceException
	 */
	public static Session getSession() throws HibernateException, IllegalArgumentException, InvalidResourceException{
        return getSession(PersistenceUtil.getDefaultPersistenceResource());
	}
	
    /**
     * Retorna uma sess�o Hibernate com o reposit�rio de dados utilizando configura��es de conex�o 
     * espec�ficas.
     *
     * @param persistenceResource Inst�ncia contendo as configura��es de persist�ncia.
     * @return Inst�ncia da sess�o Hibernate.
     * @throws HibernateException
     * @throws IllegalArgumentException
     */
    public static <D extends IDAO> Session getSession(PersistenceResource persistenceResource) throws HibernateException, IllegalArgumentException{
        SessionFactory sessionFactory = buildHibernateSessionFactory(persistenceResource);

        return sessionFactory.getCurrentSession();
    }

    /**
	 * Retorna uma conex�o JDBC com o reposit�rio de dados utilizando as configura��es de conex�o 
	 * default.
	 * 
	 * @return Inst�ncia da conex�o JDBC.
     * @throws SQLException
     * @throws HibernateException
     * @throws IllegalArgumentException
     * @throws InvalidResourceException
	 */
	public static Connection getConnection() throws SQLException, HibernateException, IllegalArgumentException, InvalidResourceException{
        return getConnection(PersistenceUtil.getDefaultPersistenceResource());
	}

    /**
     * Retorna uma conex�o JDBC com o reposit�rio de dados utilizando configura��es de conex�o 
     * espec�ficas.
     *
     * @param persistenceResource Inst�ncia contendo as configura��es de persist�ncia.
     * @return Inst�ncia da conex�o JDBC.
     * @throws SQLException 
     * @throws HibernateException
     * @throws IllegalArgumentException
     */
    public static <D extends IDAO> Connection getConnection(PersistenceResource persistenceResource) throws SQLException, HibernateException, IllegalArgumentException{
        Session session = getSession(persistenceResource);
        
        class ConnectionGetter implements Work{
            private Connection connection = null;
            
            /**
             * Retorna a inst�ncia da conex�o com reposit�rio de dados.
             * 
             * @return Inst�ncia da conex�o. 
             */
            public Connection getConnection(){
                return connection;
            }
            
            /**
             * Define a inst�ncia da conex�o com reposit�rio de dados.
             * 
             * @param connection Inst�ncia da conex�o. 
             */
            public void setConnection(Connection connection){
                this.connection = connection;
            }
            
            /**
             * @see org.hibernate.jdbc.Work#execute(java.sql.Connection)
             */
            public void execute(Connection connection) throws SQLException{
                setConnection(connection);
            }
        }
        
        ConnectionGetter connectionGetter = new ConnectionGetter();
        
        session.doWork(connectionGetter);

        return connectionGetter.getConnection();
    }

    /**
	 * Efetua a conex�o com o reposit�rio de persist�ncia.
	 * 
	 * @param persistenceResource Inst�ncia contendo as configura��es de persist�ncia desejadas.
	 * @return Inst�ncia da conex�o com o reposit�rio de persist�ncia.
	 * @throws HibernateException
	 * @throws IllegalArgumentException
	 */
	private static SessionFactory buildHibernateSessionFactory(PersistenceResource persistenceResource) throws HibernateException, IllegalArgumentException{
		Cacher<SessionFactory>       cacher  = CacherManager.getInstance().getCacher(HibernateUtil.class);
		CachedObject<SessionFactory> object  = null;
		SessionFactory               session = null;

		try{
			object  = cacher.get(StringUtil.trim(persistenceResource.getId()));
			session = object.getContent();
		}
		catch(ItemNotFoundException e){
			Properties      hibernateProperties        = new Properties();
			FactoryResource persistenceFactoryResource = persistenceResource.getFactoryResource();
			RepositoryType  repositoryType             = RepositoryType.valueOf(persistenceFactoryResource.getType().toUpperCase());

			if(repositoryType == RepositoryType.MYSQL)
				hibernateProperties.setProperty(Environment.DIALECT, MySQLDialect.class.getName());
			else if(repositoryType == RepositoryType.SYBASE)
				hibernateProperties.setProperty(Environment.DIALECT, SybaseASE15Dialect.class.getName());
			else if(repositoryType == RepositoryType.MSSQL)
				hibernateProperties.setProperty(Environment.DIALECT, SQLServerDialect.class.getName());
			else if(repositoryType == RepositoryType.ORACLE)
				hibernateProperties.setProperty(Environment.DIALECT, Oracle10gDialect.class.getName());
			else if(repositoryType == RepositoryType.POSTGRESQL)
				hibernateProperties.setProperty(Environment.DIALECT, PostgresPlusDialect.class.getName());
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
            }
			else{
				String url = persistenceResource.getFactoryResource().getUrl();

				url = PropertyUtil.fillPropertiesInString(persistenceResource, url);

				hibernateProperties.setProperty(Environment.DRIVER, persistenceResource.getFactoryResource().getClazz());
				hibernateProperties.setProperty(Environment.URL, url);
				hibernateProperties.setProperty(Environment.USER, persistenceResource.getUser());
				hibernateProperties.setProperty(Environment.PASS, persistenceResource.getPassword());
				hibernateProperties.setProperty(Environment.C3P0_TIMEOUT, persistenceResource.getTimeout().toString());
			}

			Map<String, String> options = persistenceResource.getOptions();

			if(options != null && options.size() > 0)
				for(String optionId : options.keySet())
					hibernateProperties.setProperty(optionId, options.get(optionId));
			
			StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
            ServiceRegistry                serviceRegistry        = serviceRegistryBuilder.applySettings(hibernateProperties).build();
            MetadataSources                metadataSources        = new MetadataSources(serviceRegistry);
			List<String>                   persistenceMappings    = persistenceResource.getMappings();
			
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
			            metadataSources.addInputStream(persistenceMappingStream);
			        else{
			            try{
			                metadataSources.addClass(Class.forName(persistenceMapping));
                        }
                        catch(ClassNotFoundException e1){
                        }
			        }
			    }
			}
			
			session = metadataSources.buildMetadata().buildSessionFactory();
			
			object = new CachedObject<SessionFactory>();
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