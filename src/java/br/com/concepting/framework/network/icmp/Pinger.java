package br.com.concepting.framework.network.icmp;

import java.io.IOException;
import java.net.InetAddress;

import br.com.concepting.framework.network.constants.NetworkConstants;
import br.com.concepting.framework.network.icmp.helpers.PingerData;

/**
 * Classe responsável por efetuar comandos de ping na rede.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class Pinger{
	public static Pinger instance = null;

	/**
	 * Retorna a única instância da classe.
	 * 
	 * @return Instância da classe.
	 */
	public static Pinger getInstance(){
		if(instance == null)
			instance = new Pinger();
		
		return instance;
	}
	
	/**
	 * Efetua o ping em um elemento de rede específico utilizando as configurações default.
	 * 
	 * @param host String contendo o IP/Hostname. 
	 * @return Instância contendo o resultado do comando.
	 * @throws IOException
	 */
	public PingerData ping(String host) throws IOException{
		return ping(host, NetworkConstants.DEFAULT_PINGER_TIMEOUT, NetworkConstants.DEFAULT_PINGER_TRIES, NetworkConstants.DEFAULT_PINGER_SUCCESS_PERCENTAGE);
	}

	/**
	 * Efetua o ping em um elemento de rede específico.
	 * 
	 * @param host String contendo o IP/Hostname.
	 * @param successPercentage Valor numérico contendo porcentagem de tolerância de erros aceitável.
	 * @return Instância contendo o resultado do comando.
	 * @throws IOException
	 */
	public PingerData ping(String host, Double successPercentage) throws IOException{
		return ping(host, NetworkConstants.DEFAULT_PINGER_TIMEOUT, NetworkConstants.DEFAULT_PINGER_TRIES, successPercentage);
	}
	
	/**
	 * Efetua o ping em um elemento de rede específico.
	 * 
	 * @param host String contendo o IP/Hostname.
	 * @param timeout Valor numérico contendo o timeout desejado.
	 * @return Instância contendo o resultado do comando.
	 * @throws IOException
	 */
	public PingerData ping(String host, Integer timeout) throws IOException{
		return ping(host, timeout, NetworkConstants.DEFAULT_PINGER_TRIES, NetworkConstants.DEFAULT_PINGER_SUCCESS_PERCENTAGE);
	}
	
	/**
	 * Efetua o ping em um elemento de rede específico.
	 * 
	 * @param host String contendo o IP/Hostname.
	 * @param timeout Valor numérico contendo o timeout desejado.
	 * @param tries Valor numérico contendo a quantidade de tentativas.
	 * @return Instância contendo o resultado do comando.
	 * @throws IOException
	 */
	public PingerData ping(String host, Integer timeout, Integer tries) throws IOException{
		return ping(host, timeout, tries, NetworkConstants.DEFAULT_PINGER_SUCCESS_PERCENTAGE);
		
	}

	/**
	 * Efetua o ping em um elemento de rede específico.
	 * 
	 * @param host String contendo o IP/Hostname.
	 * @param timeout Valor numérico contendo o timeout desejado.
	 * @param successPercentage Valor numérico contendo porcentagem de tolerância de erros aceitável.
	 * @return Instância contendo o resultado do comando.
	 * @throws IOException
	 */
	public PingerData ping(String host, Integer timeout, Double successPercentage) throws IOException{
		return ping(host, timeout, NetworkConstants.DEFAULT_PINGER_TRIES, successPercentage);
	}

	/**
	 * Efetua o ping em um elemento de rede específico.
	 * 
	 * @param host String contendo o IP/Hostname.
	 * @param timeout Valor numérico contendo o timeout desejado.
	 * @param tries Valor numérico contendo a quantidade de tentativas.
	 * @param successPercentage Valor numérico contendo porcentagem de tolerância de erros aceitável.
	 * @return Instância contendo o resultado do comando.
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