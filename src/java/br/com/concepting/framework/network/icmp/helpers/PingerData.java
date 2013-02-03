package br.com.concepting.framework.network.icmp.helpers;

import java.net.InetAddress;

/**
 * Classe auxiliar que armazena o resultado de um comando ping na rede.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class PingerData{
	private InetAddress address   = null;
	private Boolean     reachable = false;
	private Integer     fails     = 0;
	private Integer     tries     = 0;
	
	/**
	 * Retorna a inst�ncia do elemento de rede a ser acessado.
	 * 
	 * @return Inst�ncia do elemento de rede.
	 */
	public InetAddress getAddress(){
		return address;
	}
	
	/**
	 * Define a inst�ncia do elemento de rede a ser acessado.
	 * 
	 * @param address Inst�ncia do elemento de rede.
	 */
	public void setAddress(InetAddress address){
		this.address = address;
	}
	
	/**
	 * Indica se o elemento foi alcan�ado.
	 * 
	 * @return True/False.
	 */
	public Boolean isReachable(){
		return reachable;
	}
	
	/**
	 * Define se o elemento foi alcan�ado.
	 * 
	 * @param reachable True/False.
	 */
	public void setReachable(Boolean reachable){
		this.reachable = reachable;
	}

	/**
	 * Retorna a quantidade de falhas ocorridas.
	 * 
	 * @return Valor num�rico contendo a quantidade de falhas.
	 */
	public Integer getFails(){
		return fails;
	}
	
	/**
	 * Define a quantidade de falhas ocorridas.
	 * 
	 * @param fails Valor num�rico contendo a quantidade de falhas.
	 */
	public void setFails(Integer fails){
		this.fails = fails;
	}
	
	/**
	 * Retorna a quantidade de tentativas..
	 * 
	 * @return Valor num�rico contendo a quantidade de tentativas.
	 */
	public Integer getTries(){
		return tries;
	}
	
	/**
	 * Define a quantidade de tentativas..
	 * 
	 * @param tries Valor num�rico contendo a quantidade de tentativas.
	 */
	public void setTries(Integer tries){
		this.tries = tries;
	}
}