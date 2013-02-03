package br.com.concepting.framework.security.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import br.com.concepting.framework.security.constants.SecurityConstants;
import br.com.concepting.framework.util.ByteUtil;

/**
 * Classe que implementa 'digest' de conteúdo.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class CryptoDigest{
	private MessageDigest digest    = null;
	private Boolean       useBase64 = false;

	/**
	 * Construtor - Inicializa propriedades default da criptografia.
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public CryptoDigest() throws NoSuchAlgorithmException{
		this(true);
	}

	/**
	 * Construtor - Inicializa propriedades default da criptografia.
	 * 
	 * @param useBase64 Indica se os dados devem ser encriptados usando as codificação hexadecimal 
	 * ou Base 64.
	 * @throws NoSuchAlgorithmException
	 */
	public CryptoDigest(Boolean useBase64) throws NoSuchAlgorithmException{
		this(SecurityConstants.DEFAULT_DIGEST_ALGORITHM, useBase64);
	}

	/**
	 * Construtor - Inicializa propriedades da criptografia utilizando um algoritmo específico.
	 * 
	 * @param algorithm String contendo o identificador do algorítmo.
	 * @throws NoSuchAlgorithmException
	 */
	public CryptoDigest(String algorithm) throws NoSuchAlgorithmException{
		this(algorithm, true);
	}
	
	/**
	 * Construtor - Inicializa propriedades da criptografia utilizando um algoritmo específico.
	 * 
	 * @param algorithm String contendo o identificador do algorítmo.
	 * @param useBase64 Indica se os dados devem ser encriptados usando as codificação hexadecimal ou Base 64.
	 * @throws NoSuchAlgorithmException
	 */
	public CryptoDigest(String algorithm, Boolean useBase64) throws NoSuchAlgorithmException{
		super();

		digest = MessageDigest.getInstance(algorithm);
		
		this.useBase64 = useBase64;
	}

	/**
	 * Criptografa uma mensagem.
	 * 
	 * @param message String contendo a mensagem desejada.
	 * @return String contendo a mensagem criptografada.
	 */
	public String encrypt(String message){
		byte buffer[] = digest.digest(message.getBytes());

		return (useBase64 ? ByteUtil.toBase64(buffer) : ByteUtil.toHexadecimal(buffer));
	}
}