package br.com.concepting.framework.util;

import br.com.concepting.framework.util.interfaces.IRrd;

/**
 * Classe utilit�ria respons�vel por instanciar uma implementa��o do manipulador de arquivos 
 * RRD.
 *
 * @author fvilarinho
 * @since 1.0
 */
public abstract class RrdFactory{
	private static IRrd instance = null;
	
	/**
	 * Retorna a inst�ncia da implementa��o padr�o do manipulador de arquivos RRD.
	 */
	public static IRrd newInstance(){
		return newInstance(false);
	}
	
	/**
	 * Retorna a inst�ncia da implementa��o do manipulador de arquivos RRD.
	 *
	 * @param rrd4jSupport True/False.
	 * @return Inst�ncia da implementa��o do manipulador de arquivos RRD.
	 */
	public static IRrd newInstance(Boolean rrd4jSupport){
		if(rrd4jSupport){
			if(instance == null || !instance.getClass().equals(Rrd4JImpl.class))
				instance = new Rrd4JImpl();
		}
		else if(instance == null || !instance.getClass().equals(CommandLineRrdImpl.class))
			instance = new CommandLineRrdImpl();
	
		return instance;
	}
}
