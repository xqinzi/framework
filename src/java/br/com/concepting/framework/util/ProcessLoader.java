package br.com.concepting.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

/**
 * Classe utilit�ria para execu��o de comandos e processos.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class ProcessLoader{
	private static ProcessLoader instance = null;

	/**
	 * Retorna a inst�ncia da classe utilit�rio para execu��o de comandos e processos.
	 * 
	 * @return Inst�ncia da classe.
	 */
	public static ProcessLoader getInstance(){
		if(instance == null)
			instance = new ProcessLoader();

		return instance;
	}
	
	/**
	 * Executa um comando qualquer com par�metros.
	 * 
	 * @param commandParameters Lista contendo os par�metros desejados.
	 * @return Stream contendo o resultado da execu��o.
	 * @throws IOException
	 */
	public InputStream execute(Collection<String> commandParameters) throws IOException{
		String commandParametersArray[] = new String[(commandParameters != null ? commandParameters.size() : 0)];
		
		if(commandParameters != null && commandParameters.size() > 0)
			commandParameters.toArray(commandParametersArray);

		return execute(commandParametersArray);
	}

	/**
	 * Executa um comando qualquer com par�metros.
	 * 
	 * @param commandParameters Array contendo os par�metros desejados.
	 * @return Stream contendo o resultado da execu��o.
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