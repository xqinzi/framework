package br.com.concepting.framework.util.interfaces;

/**
 * Interface que define a estrutura b�sica para uma constante.
 *  
 * @author fvilarinho
 * @since 1.0
 */
public interface IEnum{
	/**
	 * Transforma um valor em uma inst�ncia da constante.
	 * 
	 * @param value Inst�ncia contendo o valor desejado.
	 * @return Inst�ncia da constante encontrada.
	 * @throws IllegalArgumentException
	 */
	public <O> IEnum toEnum(O value) throws IllegalArgumentException;

	/**
	 * Retorna o identificador da constante.
	 * 
	 * @return Inst�ncia contendo o identificador da constante.
	 */
	public <O> O getKey();

	/**
	 * Define o identificador da constante.
	 * 
	 * @param key Inst�ncia contendo o identificador da constante.
	 */
	public <O> void setKey(O key);
}