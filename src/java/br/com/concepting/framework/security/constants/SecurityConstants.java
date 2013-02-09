package br.com.concepting.framework.security.constants;

import br.com.concepting.framework.resource.constants.ResourceConstants;

/**
 * Classe que define as constantes de segurança.
 * 
 * @author fvilarinho
 * @since 3.0
 */ 
public abstract class SecurityConstants extends ResourceConstants{
    /**
     * Constante que define o identificador do arquivo de configurações de auditoria.
     */
    public static final String DEFAULT_RESOURCE_ID = DEFAULT_RESOURCES_DIR.concat("securityResources.xml");
    
    /**
     * Constante que define o algoritmo de digest default.
     */
    public static final String DEFAULT_DIGEST_ALGORITHM = "MD5";
    
    /**
     * Constante que define o algoritmo default de criptografia com chave.
     */
    public static final String DEFAULT_CRYPTO_ALGORITHM_WITH_PASSPHRASE = "PBEWithMD5andDES";

    /**
     * Constante que define o algoritmo default de criptografia sem chave.
     */
    public static final String DEFAULT_CRYPTO_ALGORITHM_WITHOUT_PASSPHRASE = "DES";
    
    /**
     * Constante que define o algoritmo default de criptografia com chave.
     */
    public static final String LOGIN_SESSION_KEY = "loginSession";

    /**
     * Constante que define o identificador da classe para listening (logIn/logOut) das sessões de login.
     */
    public static final String DEFAULT_LOGIN_SESSION_LISTENER_CLASS_ID = "br.com.concepting.framework.security.web.LoginSessionListener";

    /**
     * Constante que define o identificador da classe do filtro de segurança para as sessões de login.
     */
    public static final String DEFAULT_SECURITY_FILTER_CLASS_ID = "br.com.concepting.framework.security.web.SecurityFilter";
} 
