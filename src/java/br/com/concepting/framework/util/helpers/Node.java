package br.com.concepting.framework.util.helpers;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Classe que define um n� em um estrutura de dados de �rvore.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class Node implements Serializable, Cloneable{
	private Node             parent         = null;
	private Collection<Node> childNodes     = null;

	/**
	 * Retorna a inst�ncia do n� pai.
	 * 
	 * @return Inst�ncia do n� pai.
	 */
	public <N extends Node> N getParent(){
		return (N)parent;
	}

	/**
	 * Define a inst�ncia do n� pai.
	 * 
	 * @param parent Inst�ncia do n� pai.
	 * @param sync Flag que indica se deve sincronizar o n� pai.
	 */
	protected <N extends Node> void setParent(N parent, Boolean sync){
		this.parent = parent;

		try{
    		if(parent == null)
    			return;
    
    		if(sync)
    			parent.addChildNode(this);
		}
		catch(Throwable e){
		}
	}

	/**
	 * Define a inst�ncia do n� pai.
	 * 
	 * @param parent Inst�ncia do n� pai.
	 */
	public <N extends Node> void setParent(N parent){
		setParent(parent, true);
	}

	/**
	 * Retorna a lista de n�s filhos.
	 * 
	 * @return Lista de n�s filhos.
	 */
    public <N extends Node, C extends Collection> C getChildNodes(){
		return (C)childNodes;
	}

	/**
	 * Adiciona um n� filho.
	 * 
	 * @param childNode Inst�ncia do n� filho.
	 */
	public <N extends Node> void addChildNode(N childNode){
		try{
    		if(childNodes == null)
    			childNodes = new LinkedList<Node>();
    
    		if(!childNodes.contains(childNode)){
    			childNode.setParent(this, false);
    			
    			childNodes.add(childNode);
    		}
		}
		catch(Throwable e){
		}
	}
	
	/**
	 * Remove um n� filho.
	 * 
	 * @param childNode Inst�ncia contendo a propriedade do n� a ser removido.
	 */
	public <N extends Node> void removeChildNode(N childNode){
        try{
            if(childNodes != null)
                childNodes.remove(childNode);
        }
        catch(Throwable e){
        }
	}

	/**
	 * Define a lista de n�s filhos.
	 * 
	 * @param childNodes Lista de n�s filhos.
	 */
    public <N extends Node> void setChildNodes(Collection<N> childNodes){
		this.childNodes = (Collection<Node>)childNodes;
	}

	/**
	 * Flag que indica se n� possui filhos.
	 * 
	 * @return True/False.
	 */
	public Boolean hasChildNodes(){
		if(childNodes != null){
			Node childNodeParent = null;

			for(Node childNode : childNodes){
				childNodeParent = childNode.getParent();

				if(childNodeParent != null && childNodeParent.equals(this))
					return true;
			}
		}

		return false;
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
}