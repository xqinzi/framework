package br.com.concepting.framework.network.snmp.types;

import org.snmp4j.smi.OID;

/**
 * Constante que define os algoritmos de criptografia e autenticação de requisições SNMP utilizando 
 * a versão 3.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public enum SnmpV3ProtocolsType{
	/**
	 * Define o algoritmo de autenticação MD5.
	 */
	AUTH_MD5("MD5", new OID("1.3.6.1.6.3.10.1.1.2")),

	/**
	 * Define o algoritmo de autenticação SHA.
	 */
	AUTH_SHA("SHA", new OID("1.3.6.1.6.3.10.1.1.3")),
	
	/**
	 * Define o algoritmo de criptografia DES.
	 */
	PRIV_DES("DES", new OID("1.3.6.1.6.3.10.1.2.2")),

	/**
	 * Define o algoritmo de criptografia Triple DES.
	 */
	PRIV_3DES("3DES", new OID("1.3.6.1.6.3.10.1.2.3")),
	
	/**
	 * Define o algoritmo de criptografia AES 128 bits.
	 */
	AES128("AES128", new OID("1.3.6.1.6.3.10.1.2.19")),
	
	/**
	 * Define o algoritmo de criptografia AES 192 bits.
	 */
	AES192("AES192", new OID("1.3.6.1.6.3.10.1.2.20")),
	
	/**
	 * Define o algoritmo de criptografia AES 256 bits.
	 */
	AES256("AES256", new OID("1.3.6.1.6.3.10.1.2.21"));

	private Object key;
	private OID    oid;
	
	/**
	 * Construtor - Define o valor da constante.
	 * 
	 * @param key String contendo o valor desejado.
	 * @páram oid Instância contendo o identificador do algoritmo.
	 */
	private SnmpV3ProtocolsType(String key, OID oid){
		setKey(key);
		setOid(oid);
	}
	
	/**
	 * Retorna o identificador do algoritmo.
	 * 
	 * @return Identificador do algoritmo.
	 */
    public OID getOid(){
    	return oid;
    }

	/**
	 * Define o identificador do algoritmo.
	 * 
	 * @param oid Identificador do algoritmo.
	 */
	public void setOid(OID oid){
    	this.oid = oid;
    }

	/**
	 * Retorna o identificador do algoritmo.
	 * 
	 * @return Identificador do algoritmo.
	 */
    public <O> O getKey(){
	    return (O)key;
    }

	/**
	 * Define o identificador do algoritmo.
	 * 
	 * @param key Identificador do algoritmo.
	 */
    public <O> void setKey(O key){
    	this.key = key;
    }
    
    /**
     * @see java.lang.Enum#toString()
     */
    public String toString(){
    	return getKey();
    }
}
