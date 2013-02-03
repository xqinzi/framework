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
	 * Retorna o identificador da propriedade que cont�m o label do n�.
	 * 
	 * @return String contendo do identificador da propriedade que cont�m o label. 
	 */
	public String getLabelName(){
        return labelName;
    }

    /**
     * Define o identificador da propriedade que cont�m o label do n�.
     * 
     * @param labelName String contendo do identificador da propriedade que cont�m o label. 
     */
	public void setLabelName(String labelName){
        this.labelName = labelName;
    }

    /**
	 * Retorna o identificador do redirecionamento ap�s processamento.
	 * 
	 * @return String contendo o identificador do redirecionamento.
	 */
	public String getForward(){
		return forward;
	}

	/**
	 * Define o identificador do redirecionamento ap�s processamento.
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
	 * Retorna o conte�do bin�rio que define o �cone do n�.
	 *
	 * @return Array de bytes contendo os dados do �cone. 
	 */
	public byte[] getIconData(){
    	return iconData;
    }

	/**
	 * Define o conte�do bin�rio que define o �cone do n�.
	 *
	 * @param iconData Array de bytes contendo os dados do �cone. 
	 */
	public void setIconData(byte[] iconData){
    	this.iconData = iconData;
    }

	/**
	 * Retorna o identificador da a��o a ser executada quando o n� for expandido.
	 * 
	 * @return String contendo o identificador da a��o.
	 */
	public String getOnExpandAction(){
    	return onExpandAction;
    }

	/**
	 * Define o identificador da a��o a ser executada quando o n� for expandido.
	 * 
	 * @param onExpandAction String contendo o identificador da a��o.
	 */
	public void setOnExpandAction(String onExpandAction){
    	this.onExpandAction = onExpandAction;
    }

	/**
	 * Retorna o identificador da a��o a ser executada na sele��o.
	 * 
	 * @return String contendo o identificador da a��o.
	 */
	public String getOnSelectAction(){
		return onSelectAction;
	}

	/**
	 * Define o identificador da a��o a ser executada na sele��o.
	 * 
	 * @param onSelectAction String contendo o identificador da a��o.
	 */
	public void setOnSelectAction(String onSelectAction){
		this.onSelectAction = onSelectAction;
	}

	/**
	 * Retorna a a��o do objeto.
	 * 
	 * @return String contendo a a��o do objeto.
	 */
	public String getAction(){
		return action;
	}

	/**
	 * Define a a��o do objeto.
	 * 
	 * @param action String contendo a a��o do objeto.
	 */
	public void setAction(String action){
		this.action = action;
	}

	/**
	 * Retorna o destino da a��o do objeto.
	 * 
	 * @return String contendo o destino da a��o.
	 */
	public String getActionTarget(){
		return actionTarget;
	}

	/**
	 * Define o destino da a��o do objeto.
	 * 
	 * @param actionTarget String contendo o destino da a��o.
	 */
	public void setActionTarget(String actionTarget){
		this.actionTarget = actionTarget;
	}

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
	 * Retorna a altura do �cone do n�.
	 * 
	 * @return String contendo a altura do n�.
	 */
	public String getIconHeight(){
		return iconHeight;
	}

	/**
	 * Define a altura do �cone do n�.
	 * 
	 * @param iconHeight String contendo a altura do n�.
	 */
	public void setIconHeight(String iconHeight){
		this.iconHeight = iconHeight;
	}

	/**
	 * Retorna a URL onde o �cone do n� est� armazenado.
	 * 
	 * @return String contendo a URL do �cone.
	 */
	public String getIconUrl(){
		return iconUrl;
	}

	/**
	 * Define a URL onde o �cone do n� est� armazenado.
	 * 
	 * @param iconUrl String contendo a URL do �cone.
	 */
	public void setIconUrl(String iconUrl){
		this.iconUrl = iconUrl;
	}

	/**
	 * Retorna a largura do �cone do n�.
	 * 
	 * @return String contendo a largura do n�.
	 */
	public String getIconWidth(){
		return iconWidth;
	}

	/**
	 * Define a largura do �cone do n�.
	 * 
	 * @param iconWidth String contendo a largura do n�.
	 */
	public void setIconWidth(String iconWidth){
		this.iconWidth = iconWidth;
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