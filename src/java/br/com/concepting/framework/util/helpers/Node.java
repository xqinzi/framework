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
    private String           labelName      = "";
	private byte             iconData[]     = null;
	private String           iconUrl        = "";
	private String           iconWidth      = "";
	private String           iconHeight     = "";
	private String           onExpandAction = "";
	private String           onSelectAction = "";
	private String           forward        = "";
	private String           forwardOnFail  = "";
	private String           action         = "";
	private String           actionTarget   = "";
	private Node             parent         = null;
	private Collection<Node> childNodes     = null;
	
	/**
	 * Retorna o identificador da propriedade que contém o label do nó.
	 * 
	 * @return String contendo do identificador da propriedade que contém o label. 
	 */
	public String getLabelName(){
        return labelName;
    }

    /**
     * Define o identificador da propriedade que contém o label do nó.
     * 
     * @param labelName String contendo do identificador da propriedade que contém o label. 
     */
	public void setLabelName(String labelName){
        this.labelName = labelName;
    }

    /**
	 * Retorna o identificador do redirecionamento após processamento.
	 * 
	 * @return String contendo o identificador do redirecionamento.
	 */
	public String getForward(){
		return forward;
	}

	/**
	 * Define o identificador do redirecionamento após processamento.
	 * 
	 * @param forward String contendo o identificador do redirecionamento.
	 */
	public void setForward(String forward){
		this.forward = forward;
	}

	/**
	 * Retorna o identificador do redirecionamento em caso de falha no processamento.
	 * 
	 * @return String contendo o identificador do redirecionamento.
	 */
	public String getForwardOnFail(){
		return forwardOnFail;
	}

	/**
	 * Define o identificador do redirecionamento em caso de falha no
	 * processamento.
	 * 
	 * @param forwardOnFail String contendo o identificador do redirecionamento.
	 */
	public void setForwardOnFail(String forwardOnFail){
		this.forwardOnFail = forwardOnFail;
	}

	/**
	 * Retorna o conteúdo binário que define o ícone do nó.
	 *
	 * @return Array de bytes contendo os dados do ícone. 
	 */
	public byte[] getIconData(){
    	return iconData;
    }

	/**
	 * Define o conteúdo binário que define o ícone do nó.
	 *
	 * @param iconData Array de bytes contendo os dados do ícone. 
	 */
	public void setIconData(byte[] iconData){
    	this.iconData = iconData;
    }

	/**
	 * Retorna o identificador da ação a ser executada quando o nó for expandido.
	 * 
	 * @return String contendo o identificador da ação.
	 */
	public String getOnExpandAction(){
    	return onExpandAction;
    }

	/**
	 * Define o identificador da ação a ser executada quando o nó for expandido.
	 * 
	 * @param onExpandAction String contendo o identificador da ação.
	 */
	public void setOnExpandAction(String onExpandAction){
    	this.onExpandAction = onExpandAction;
    }

	/**
	 * Retorna o identificador da ação a ser executada na seleção.
	 * 
	 * @return String contendo o identificador da ação.
	 */
	public String getOnSelectAction(){
		return onSelectAction;
	}

	/**
	 * Define o identificador da ação a ser executada na seleção.
	 * 
	 * @param onSelectAction String contendo o identificador da ação.
	 */
	public void setOnSelectAction(String onSelectAction){
		this.onSelectAction = onSelectAction;
	}

	/**
	 * Retorna a ação do objeto.
	 * 
	 * @return String contendo a ação do objeto.
	 */
	public String getAction(){
		return action;
	}

	/**
	 * Define a ação do objeto.
	 * 
	 * @param action String contendo a ação do objeto.
	 */
	public void setAction(String action){
		this.action = action;
	}

	/**
	 * Retorna o destino da ação do objeto.
	 * 
	 * @return String contendo o destino da ação.
	 */
	public String getActionTarget(){
		return actionTarget;
	}

	/**
	 * Define o destino da ação do objeto.
	 * 
	 * @param actionTarget String contendo o destino da ação.
	 */
	public void setActionTarget(String actionTarget){
		this.actionTarget = actionTarget;
	}

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
	 * Retorna a altura do ícone do nó.
	 * 
	 * @return String contendo a altura do nó.
	 */
	public String getIconHeight(){
		return iconHeight;
	}

	/**
	 * Define a altura do ícone do nó.
	 * 
	 * @param iconHeight String contendo a altura do nó.
	 */
	public void setIconHeight(String iconHeight){
		this.iconHeight = iconHeight;
	}

	/**
	 * Retorna a URL onde o ícone do nó está armazenado.
	 * 
	 * @return String contendo a URL do ícone.
	 */
	public String getIconUrl(){
		return iconUrl;
	}

	/**
	 * Define a URL onde o ícone do nó está armazenado.
	 * 
	 * @param iconUrl String contendo a URL do ícone.
	 */
	public void setIconUrl(String iconUrl){
		this.iconUrl = iconUrl;
	}

	/**
	 * Retorna a largura do ícone do nó.
	 * 
	 * @return String contendo a largura do nó.
	 */
	public String getIconWidth(){
		return iconWidth;
	}

	/**
	 * Define a largura do ícone do nó.
	 * 
	 * @param iconWidth String contendo a largura do nó.
	 */
	public void setIconWidth(String iconWidth){
		this.iconWidth = iconWidth;
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