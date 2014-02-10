package br.com.concepting.framework.util.helpers;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Classe que define um nó em uma estrutura de dados de árvore.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class Node implements Serializable, Cloneable{
    private static final long serialVersionUID = -9107989395547430445L;
    
    private Node             parent     = null;
	private Collection<Node> childNodes = null;

	/**
	 * Retorna a instância do nó pai.
	 * 
	 * @return Instância do nó pai.
	 */
    @SuppressWarnings("unchecked")
    public <N extends Node> N getParent(){
		return (N)parent;
	}

	/**
	 * Define a instância do nó pai.
	 * 
	 * @param parent Instância do nó pai.
	 * @param sync Flag que indica se deve sincronizar o nó pai.
	 */
	protected void setParent(Node parent, Boolean sync){
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
	public void setParent(Node parent){
		setParent(parent, true);
	}

	/**
	 * Retorna a lista de nós filhos.
	 * 
	 * @return Lista de nós filhos.
	 */
    @SuppressWarnings("unchecked")
    public <C extends Collection<? extends Node>> C getChildNodes(){
		return (C)childNodes;
	}

	/**
	 * Adiciona um nó filho.
	 * 
	 * @param childNode Instância do nó filho.
	 */
	public void addChildNode(Node childNode){
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
	public void removeChildNode(Node childNode){
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
    @SuppressWarnings("unchecked")
    public <C extends Collection<? extends Node>> void setChildNodes(C childNodes){
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
    public Node clone() throws CloneNotSupportedException{
		return (Node)super.clone();
	}
}