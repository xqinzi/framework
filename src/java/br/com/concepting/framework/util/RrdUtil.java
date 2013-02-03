package br.com.concepting.framework.util;

import br.com.concepting.framework.processors.ExpressionProcessor;
import br.com.concepting.framework.processors.ExpressionProcessorUtil;

/**
 * Classe utilitária com rotinas gerais de manipulação de arquivos RRD.
 *
 * @author fvilarinho
 * @since 1.0
 */
public final class RrdUtil{
	/**
	 * Retorna a quantidade de linhas de armazenamento a partir das definições de tempo 
	 * desejadas.
	 * 
	 * @param hours Valor numérico contendo as horas desejadas.
	 * @param days Valor numérico contendo os dias desejados.
	 * @param step Valor numérico contendo o step desejado.
	 * @return Valor inteiro contendo o número de linhas.
	 * @throws IllegalArgumentException
	 */
	public static Integer getArchiveRows(Integer hours, Integer days, Integer step) throws IllegalArgumentException{
		ExpressionProcessorUtil.addVariable("hours", hours);
		ExpressionProcessorUtil.addVariable("days", days);
		ExpressionProcessorUtil.addVariable("step", step);
		
		ExpressionProcessor processor = new ExpressionProcessor();
		
		try{
			Double result = (Double)processor.evaluate("(((24 * 60 * 60) / @{step}) / @{hours}) * @{days}");
	        
			return result.intValue();
        }
        catch(Throwable e){
        	throw new IllegalArgumentException(e);
        }
	}
}