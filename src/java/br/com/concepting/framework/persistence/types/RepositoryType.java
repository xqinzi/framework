package br.com.concepting.framework.persistence.types;

import br.com.concepting.framework.util.interfaces.IEnum;

/**
 * Classe que define as constantes para os tipos de reposit�rios de persist�ncia.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum RepositoryType implements IEnum{
	/**
	 * Constante que define o reposit�rio para o servidor MS-SQL Server.
	 */
	MSSQL("mssql", "MS-SQL Server", 1433),

	/**
	 * Constante que define o reposit�rio para o servidor Sybase.
	 */
	SYBASE("sybase", "Sybase ASE/ASA", 2368),

	/**
	 * Constante que define o reposit�rio para o servidor DB2.
	 */
	DB2("db2", "IBM DB2", 50000),

	/**
	 * Constante que define o reposit�rio para o servidor Oracle.
	 */
	ORACLE("oracle", "Oracle", 1521),

	/**
	 * Constante que define o reposit�rio para o servidor MySQL.
	 */
	MYSQL("mysql", "MySQL", 3306),

	/**
	 * Constante que define o reposit�rio para o servidor PostgreSQL.
	 */
	POSTGRESQL("postgresql", "PostgreSQL", 5432),

	/**
	 * Constante que define o reposit�rio para o servidor Firebird.
	 */
	FIREBIRD("firebird", "Firebird", 3050),
	
	/**
	 * Constante que define o reposit�rio para o servidor Informix.
	 */
	INFORMIX("informix", "Informix", 1532);

	private Object  key;
	private String  description;
	private Integer defaultPort;

	/**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param key String contendo o valor desejado.
	 * @param description String contendo a descri��o do reposit�rio.
	 * @param defaultPort Valor inteiro contendo a porta padr�o.
	 */
	private RepositoryType(String key, String description, Integer defaultPort){
		setKey(key);
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
	
	/**
	 * @see br.com.concepting.framework.util.interfaces.IEnum#getKey()
	 */
    public <O> O getKey(){
		return (O)key;
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IEnum#setKey(java.lang.Object)
	 */
	public <O> void setKey(O key){
		this.key = (String)key;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return key.toString();
	}

	/**
	 * @see br.com.concepting.framework.util.interfaces.IEnum#toEnum(java.lang.Object)
	 */
	public <O> IEnum toEnum(O value) throws IllegalArgumentException{
		return toRepositoryType((String)value);
	}

	/**
	 * Converte uma string em uma inst�ncia da constante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @return Inst�ncia da constante.
	 */
	public static RepositoryType toRepositoryType(String value) throws IllegalArgumentException{
		if(value == null)
			throw new IllegalArgumentException();

		try{
			return valueOf(value.toUpperCase());
		}
		catch(Throwable e){
			for(RepositoryType constant : values())
				if(value.equals(constant.getDescription()))
					return constant;
			
			throw new IllegalArgumentException(value);
		}
	}
}