package br.com.concepting.framework.util;

import br.com.concepting.framework.processors.ExpressionProcessor;
import br.com.concepting.framework.processors.ExpressionProcessorUtil;

/**
 * Classe utilit�ria com rotinas gerais de manipula��o de arquivos RRD.
 *
 * @author fvilarinho
 * @since 1.0
 */
public final class RrdUtil{
	/**
	 * Retorna a quantidade de linhas de armazenamento a partir das defini��es de tempo 
	 * desejadas.
	 * 
	 * @param hours Valor num�rico contendo as horas desejadas.
	 * @param days Valor num�rico contendo os dias desejados.
	 * @param step Valor num�rico contendo o step desejado.
	 * @return Valor inteiro contendo o n�mero de linhas.
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