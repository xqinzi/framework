package br.com.concepting.framework.util;

import br.com.concepting.framework.util.interfaces.IRrd;

/**
 * Classe utilitária responsável por instanciar uma implementação do manipulador de arquivos 
 * RRD.
 *
 * @author fvilarinho
 * @since 1.0
 */
public abstract class RrdFactory{
	private static IRrd instance = null;
	
	/**
	 * Retorna a instância da implementação padrão do manipulador de arquivos RRD.
	 */
	public static IRrd newInstance(){
		return newInstance(false);
	}
	
	/**
	 * Retorna a instância da implementação do manipulador de arquivos RRD.
	 *
	 * @param rrd4jSupport True/False.
	 * @return Instância da implementação do manipulador de arquivos RRD.
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
