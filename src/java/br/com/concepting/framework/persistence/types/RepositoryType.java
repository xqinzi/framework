package br.com.concepting.framework.persistence.types;

/**
 * Classe que define as constantes para os tipos de repositórios de persistência.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum RepositoryType{
	/**
	 * Constante que define o repositório para o servidor MS-SQL Server.
	 */
	MSSQL("MS-SQL Server", 1433),

	/**
	 * Constante que define o repositório para o servidor Sybase.
	 */
	SYBASE("Sybase ASE/ASA", 2368),

	/**
	 * Constante que define o repositório para o servidor DB2.
	 */
	DB2("IBM DB2", 50000),

	/**
	 * Constante que define o repositório para o servidor Oracle.
	 */
	ORACLE("Oracle", 1521),

	/**
	 * Constante que define o repositório para o servidor MySQL.
	 */
	MYSQL("MySQL", 3306),

	/**
	 * Constante que define o repositório para o servidor PostgreSQL.
	 */
	POSTGRESQL("PostgreSQL", 5432),

	/**
	 * Constante que define o repositório para o servidor Firebird.
	 */
	FIREBIRD("Firebird", 3050),
	
	/**
	 * Constante que define o repositório para o servidor Informix.
	 */
	INFORMIX("Informix", 1532);

	private String  description = "";
	private Integer defaultPort = 0;

	/**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param description String contendo a descrição do repositório.
	 * @param defaultPort Valor inteiro contendo a porta padrão.
	 */
	private RepositoryType(String description, Integer defaultPort){
		setDescription(description);
		setDefaultPort(defaultPort);
	}
	
	/**
	 * Retorna a descrição do repositório.
	 *
	 * @return String contendo a descrição do repositório.
	 */
	public String getDescription(){
    	return description;
    }

	/**
	 * Define a descrição do repositório.
	 *
	 * @param description String contendo a descrição do repositório.
	 */
	public void setDescription(String description){
    	this.description = description;
    }

	/**
	 * Retorna a porta padrão para comunicação com o repositório.
	 *
	 * @return Valor inteiro contendo a porta padrão. 
	 */
	public Integer getDefaultPort(){
    	return defaultPort;
    }

	/**
	 * Define a porta padrão para comunicação com o repositório.
	 *
	 * @param defaultPort Valor inteiro contendo a porta padrão. 
	 */
	public void setDefaultPort(Integer defaultPort){
    	this.defaultPort = defaultPort;
    }
}