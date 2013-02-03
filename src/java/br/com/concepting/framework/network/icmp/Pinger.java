package br.com.concepting.framework.network.icmp;

import java.io.IOException;
import java.net.InetAddress;

import br.com.concepting.framework.network.constants.NetworkConstants;
import br.com.concepting.framework.network.icmp.helpers.PingerData;

/**
 * Classe respons�vel por efetuar comandos de ping na rede.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class Pinger{
	public static Pinger instance = null;

	/**
	 * Retorna a �nica inst�ncia da classe.
	 * 
	 * @return Inst�ncia da classe.
	 */
	public static Pinger getInstance(){
		if(instance == null)
			instance = new Pinger();
		
		return instance;
	}
	
	/**
	 * Efetua o ping em um elemento de rede espec�fico utilizando as configura��es default.
	 * 
	 * @param host String contendo o IP/Hostname. 
	 * @return Inst�ncia contendo o resultado do comando.
	 * @throws IOException
	 */
	public PingerData ping(String host) throws IOException{
		return ping(host, NetworkConstants.DEFAULT_PINGER_TIMEOUT, NetworkConstants.DEFAULT_PINGER_TRIES, NetworkConstants.DEFAULT_PINGER_SUCCESS_PERCENTAGE);
	}

	/**
	 * Efetua o ping em um elemento de rede espec�fico.
	 * 
	 * @param host String contendo o IP/Hostname.
	 * @param successPercentage Valor num�rico contendo porcentagem de toler�ncia de erros aceit�vel.
	 * @return Inst�ncia contendo o resultado do comando.
	 * @throws IOException
	 */
	public PingerData ping(String host, Double successPercentage) throws IOException{
		return ping(host, NetworkConstants.DEFAULT_PINGER_TIMEOUT, NetworkConstants.DEFAULT_PINGER_TRIES, successPercentage);
	}
	
	/**
	 * Efetua o ping em um elemento de rede espec�fico.
	 * 
	 * @param host String contendo o IP/Hostname.
	 * @param timeout Valor num�rico contendo o timeout desejado.
	 * @return Inst�ncia contendo o resultado do comando.
	 * @throws IOException
	 */
	public PingerData ping(String host, Integer timeout) throws IOException{
		return ping(host, timeout, NetworkConstants.DEFAULT_PINGER_TRIES, NetworkConstants.DEFAULT_PINGER_SUCCESS_PERCENTAGE);
	}
	
	/**
	 * Efetua o ping em um elemento de rede espec�fico.
	 * 
	 * @param host String contendo o IP/Hostname.
	 * @param timeout Valor num�rico contendo o timeout desejado.
	 * @param tries Valor num�rico contendo a quantidade de tentativas.
	 * @return Inst�ncia contendo o resultado do comando.
	 * @throws IOException
	 */
	public PingerData ping(String host, Integer timeout, Integer tries) throws IOException{
		return ping(host, timeout, tries, NetworkConstants.DEFAULT_PINGER_SUCCESS_PERCENTAGE);
		
	}

	/**
	 * Efetua o ping em um elemento de rede espec�fico.
	 * 
	 * @param host String contendo o IP/Hostname.
	 * @param timeout Valor num�rico contendo o timeout desejado.
	 * @param successPercentage Valor num�rico contendo porcentagem de toler�ncia de erros aceit�vel.
	 * @return Inst�ncia contendo o resultado do comando.
	 * @throws IOException
	 */
	public PingerData ping(String host, Integer timeout, Double successPercentage) throws IOException{
		return ping(host, timeout, NetworkConstants.DEFAULT_PINGER_TRIES, successPercentage);
	}

	/**
	 * Efetua o ping em um elemento de rede espec�fico.
	 * 
	 * @param host String contendo o IP/Hostname.
	 * @param timeout Valor num�rico contendo o timeout desejado.
	 * @param tries Valor num�rico contendo a quantidade de tentativas.
	 * @param successPercentage Valor num�rico contendo porcentagem de toler�ncia de erros aceit�vel.
	 * @return Inst�ncia contendo o resultado do comando.
	 * @throws IOException
	 */
	public PingerData ping(String host, Integer timeout, Integer tries, Double successPercentage) throws IOException{
		InetAddress address    = InetAddress.getByName(host);
		PingerData  statistics = new PingerData();
		Integer     fails      = 0;
		
		statistics.setAddress(address);
		statistics.setTries(tries);
		     
		for(Integer cont = 0 ; cont < tries ; cont++){
			if(!address.isReachable(timeout))
				fails++;
			
			try{
	            Thread.sleep(100);
            }
			catch(InterruptedException e){
				break;
            }
		}
		
		statistics.setFails(fails);
		statistics.setReachable(((fails / tries) * 100d) <= successPercentage);
		
		return statistics;
	}
}