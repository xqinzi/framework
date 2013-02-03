package br.com.concepting.framework.util.interfaces;

/**
 * Interface que define a estrutura básica para uma constante.
 *  
 * @author fvilarinho
 * @since 1.0
 */
public interface IEnum{
	/**
	 * Transforma um valor em uma instância da constante.
	 * 
	 * @param value Instância contendo o valor desejado.
	 * @return Instância da constante encontrada.
	 * @throws IllegalArgumentException
	 */
	public <O> IEnum toEnum(O value) throws IllegalArgumentException;

	/**
	 * Retorna o identificador da constante.
	 * 
	 * @return Instância contendo o identificador da constante.
	 */
	public <O> O getKey();

	/**
	 * Define o identificador da constante.
	 * 
	 * @param key Instância contendo o identificador da constante.
	 */
	public <O> void setKey(O key);
}