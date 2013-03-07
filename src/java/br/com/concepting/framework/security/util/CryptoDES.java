package br.com.concepting.framework.security.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import br.com.concepting.framework.security.constants.SecurityConstants;

/**
 * Classe que implementa a criptografia DES.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class CryptoDES extends BaseCrypto{
	private static byte    salt[]         = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07};
    private static Integer iterationCount = 31;
    
    /**
     * Construtor - Inicializa propriedades da criptografia.
     *
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     */
    public CryptoDES() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException{
    	this(true);
    }

    /**
     * Construtor - Inicializa propriedades da criptografia.
     *
	 * @param useBase64 Indica se os dados devem ser encriptados/descriptados usando as codificação 
	 * hexadecimal ou Base 64.
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     */
    public CryptoDES(Boolean useBase64) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException{
    	super(SecurityConstants.DEFAULT_CRYPTO_ALGORITHM_WITHOUT_PASSPHRASE_ID, useBase64);
    }

    /**
     * Construtor - Inicializa propriedades da criptografia.
	 * 
	 * @param passPhrase String contendo a senha da chave.
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     */
    public CryptoDES(String passPhrase) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException{
    	this(passPhrase, true);
    }

    /**
     * Construtor - Inicializa propriedades da criptografia.
	 * 
	 * @param passPhrase String contendo a senha da chave.
	 * @param useBase64 Indica se os dados devem ser encriptados/descriptados usando as codificação 
	 * hexadecimal ou Base 64.
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     */
    public CryptoDES(String passPhrase, Boolean useBase64) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException{
    	this(useBase64);
    	
        KeySpec   keySpec = new PBEKeySpec(passPhrase.toCharArray());
        SecretKey key     = SecretKeyFactory.getInstance(SecurityConstants.DEFAULT_CRYPTO_ALGORITHM_WITH_PASSPHRASE_ID).generateSecret(keySpec);
        
        encrypt = Cipher.getInstance(key.getAlgorithm());
        decrypt = Cipher.getInstance(key.getAlgorithm());

        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

        encrypt.init(Cipher.ENCRYPT_MODE, key, paramSpec);
        decrypt.init(Cipher.DECRYPT_MODE, key, paramSpec);
    }
}