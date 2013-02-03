package br.com.concepting.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

/**
 * Classe utilitária para execução de comandos e processos.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class ProcessLoader{
	private static ProcessLoader instance = null;

	/**
	 * Retorna a instância da classe utilitário para execução de comandos e processos.
	 * 
	 * @return Instância da classe.
	 */
	public static ProcessLoader getInstance(){
		if(instance == null)
			instance = new ProcessLoader();

		return instance;
	}
	
	/**
	 * Executa um comando qualquer com parâmetros.
	 * 
	 * @param commandParameters Lista contendo os parâmetros desejados.
	 * @return Stream contendo o resultado da execução.
	 * @throws IOException
	 */
	public InputStream execute(Collection<String> commandParameters) throws IOException{
		String commandParametersArray[] = new String[(commandParameters != null ? commandParameters.size() : 0)];
		
		if(commandParameters != null && commandParameters.size() > 0)
			commandParameters.toArray(commandParametersArray);

		return execute(commandParametersArray);
	}

	/**
	 * Executa um comando qualquer com parâmetros.
	 * 
	 * @param commandParameters Array contendo os parâmetros desejados.
	 * @return Stream contendo o resultado da execução.
	 * @throws IOException
	 */
	public InputStream execute(String... commandParameters) throws IOException{
		Process child = Runtime.getRuntime().exec(commandParameters);
		
		try{
			child.waitFor();
		}
		catch(Throwable e){
		}
		
		if(child.exitValue() == 0)
			return child.getInputStream();
		
		return child.getErrorStream();
	}
}