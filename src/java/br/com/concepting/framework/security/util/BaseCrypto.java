package br.com.concepting.framework.security.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import br.com.concepting.framework.util.ByteUtil;

/**
 * Classe que define a estrutura básica para as classes que implementam um tipo criptografia.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class BaseCrypto{
	protected Cipher  encrypt   = null;
	protected Cipher  decrypt   = null;
	private   Boolean useBase64 = false;

	/**
	 * Construtor - Inicializa propriedades da criptografia.
	 * 
	 * @param algorithm String contendo o identificador do algoritmo desejado.
	 * @param useBase64 Indica se os dados devem ser encriptados/descriptados usando as codificação hexadecimal ou Base 64.
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 */
	public BaseCrypto(String algorithm, Boolean useBase64) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException{
		super();

		this.useBase64 = useBase64;
		
		KeyGenerator generator = KeyGenerator.getInstance(algorithm);
		SecretKey    key       = generator.generateKey();

		encrypt = Cipher.getInstance(key.getAlgorithm());
		encrypt.init(Cipher.ENCRYPT_MODE, key);

		decrypt = Cipher.getInstance(algorithm);
		decrypt.init(Cipher.DECRYPT_MODE, key);
	}

	/**
	 * Criptografa uma mensagem.
	 * 
	 * @param message String contendo a mensagem desejada.
	 * @return String contendo a mensagem criptografada.
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws UnsupportedEncodingException 
	 */
	public String encrypt(String message) throws IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
		byte[] buffer = message.getBytes();
		byte[] enc    = encrypt.doFinal(buffer);

		return (useBase64 ? ByteUtil.toBase64(enc) : ByteUtil.toHexadecimal(enc));
	}

	/**
	 * Descriptografa uma mensagem
	 * 
	 * @param message String contendo a mensagem criptografada.
	 * @return String contendo a mensagem descriptografada.
	 */
	public String decrypt(String message) throws Throwable{
		byte[] dec    = (useBase64 ? ByteUtil.fromBase64(message) : ByteUtil.fromHexadecimal(message));
		byte[] buffer = decrypt.doFinal(dec);

		return new String(buffer);
	}
}