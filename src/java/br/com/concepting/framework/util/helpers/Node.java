package br.com.concepting.framework.util.helpers;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Classe que define um nó em um estrutura de dados de árvore.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class Node implements Serializable, Cloneable{
	private Node             parent         = null;
	private Collection<Node> childNodes     = null;

	/**
	 * Retorna a instância do nó pai.
	 * 
	 * @return Instância do nó pai.
	 */
	public <N extends Node> N getParent(){
		return (N)parent;
	}

	/**
	 * Define a instância do nó pai.
	 * 
	 * @param parent Instância do nó pai.
	 * @param sync Flag que indica se deve sincronizar o nó pai.
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
	 * Define a instância do nó pai.
	 * 
	 * @param parent Instância do nó pai.
	 */
	public <N extends Node> void setParent(N parent){
		setParent(parent, true);
	}

	/**
	 * Retorna a lista de nós filhos.
	 * 
	 * @return Lista de nós filhos.
	 */
    public <N extends Node, C extends Collection> C getChildNodes(){
		return (C)childNodes;
	}

	/**
	 * Adiciona um nó filho.
	 * 
	 * @param childNode Instância do nó filho.
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
	 * Remove um nó filho.
	 * 
	 * @param childNode Instância contendo a propriedade do nó a ser removido.
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
	 * Define a lista de nós filhos.
	 * 
	 * @param childNodes Lista de nós filhos.
	 */
    public <N extends Node> void setChildNodes(Collection<N> childNodes){
		this.childNodes = (Collection<Node>)childNodes;
	}

	/**
	 * Flag que indica se nó possui filhos.
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