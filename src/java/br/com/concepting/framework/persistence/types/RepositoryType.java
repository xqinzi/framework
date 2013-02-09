package br.com.concepting.framework.persistence.types;

/**
 * Classe que define as constantes para os tipos de reposit�rios de persist�ncia.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum RepositoryType{
	/**
	 * Constante que define o reposit�rio para o servidor MS-SQL Server.
	 */
	MSSQL("MS-SQL Server", 1433),

	/**
	 * Constante que define o reposit�rio para o servidor Sybase.
	 */
	SYBASE("Sybase ASE/ASA", 2368),

	/**
	 * Constante que define o reposit�rio para o servidor DB2.
	 */
	DB2("IBM DB2", 50000),

	/**
	 * Constante que define o reposit�rio para o servidor Oracle.
	 */
	ORACLE("Oracle", 1521),

	/**
	 * Constante que define o reposit�rio para o servidor MySQL.
	 */
	MYSQL("MySQL", 3306),

	/**
	 * Constante que define o reposit�rio para o servidor PostgreSQL.
	 */
	POSTGRESQL("PostgreSQL", 5432),

	/**
	 * Constante que define o reposit�rio para o servidor Firebird.
	 */
	FIREBIRD("Firebird", 3050),
	
	/**
	 * Constante que define o reposit�rio para o servidor Informix.
	 */
	INFORMIX("Informix", 1532);

	private String  description = "";
	private Integer defaultPort = 0;

	/**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param description String contendo a descri��o do reposit�rio.
	 * @param defaultPort Valor inteiro contendo a porta padr�o.
	 */
	private RepositoryType(String description, Integer defaultPort){
		setDescription(description);
		setDefaultPort(defaultPort);
	}
	
	/**
	 * Retorna a descri��o do reposit�rio.
	 *
	 * @return String contendo a descri��o do reposit�rio.
	 */
	public String getDescription(){
    	return description;
    }

	/**
	 * Define a descri��o do reposit�rio.
	 *
	 * @param description String contendo a descri��o do reposit�rio.
	 */
	public void setDescription(String description){
    	this.description = description;
    }

	/**
	 * Retorna a porta padr�o para comunica��o com o reposit�rio.
	 *
	 * @return Valor inteiro contendo a porta padr�o. 
	 */
	public Integer getDefaultPort(){
    	return defaultPort;
    }

	/**
	 * Define a porta padr�o para comunica��o com o reposit�rio.
	 *
	 * @param defaultPort Valor inteiro contendo a porta padr�o. 
	 */
	public void setDefaultPort(Integer defaultPort){
    	this.defaultPort = defaultPort;
    }
}