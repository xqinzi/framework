package br.com.concepting.framework.web.taglibs;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import br.com.concepting.framework.constants.AttributeConstants;
import br.com.concepting.framework.model.ObjectModel;
import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.resource.PropertiesResource;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.helpers.Node;
import br.com.concepting.framework.util.types.AlignmentType;
import br.com.concepting.framework.util.types.ComponentType;
import br.com.concepting.framework.util.types.PositionType;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;

/**
 * Classe que define o componente visual Treeview (Árvore de visualização).
 * 
 * @author fvilarinho
 * @since 2.0
 */
public class TreeViewPropertyTag extends BaseOptionsPropertyTag{
    private String  nodeLabelProperty                  = "";
    private String  nodeResourceId                     = "";
	private String  nodeIconStyleClass                 = "";
	private String  nodeLabelStyleClass                = "";
	private String  nodeLabelSelectedStyleClass        = "";
    private String  expandedNodeIconStyleClass         = "";
	private String  collapsedNodeIconStyleClass        = "";
	private String  openedNodeIconStyleClass           = "";
	private String  closedNodeIconStyleClass           = "";
    private String  onExpand                           = "";
    private String  onExpandAction                     = "";
    private String  onExpandActionForward              = "";
    private String  onExpandActionForwardOnFail        = "";
    private String  onExpandActionUpdateViews          = "";
    private Boolean onExpandActionValidate             = false;
    private String  onExpandActionValidateProperties   = "";
	private String  onSelect                           = "";
    private String  onSelectAction                     = "";
    private String  onSelectActionForward              = "";
    private String  onSelectActionForwardOnFail        = "";
    private String  onSelectActionUpdateViews          = "";
    private Boolean onSelectActionValidate             = false;
    private String  onSelectActionValidateProperties   = "";
	private String  onUnSelect                         = "";
    private String  onUnSelectAction                   = "";
    private String  onUnSelectActionForward            = "";
    private String  onUnSelectActionForwardOnFail      = "";
    private String  onUnSelectActionUpdateViews        = "";
    private Boolean onUnSelectActionValidate           = false;
    private String  onUnSelectActionValidateProperties = "";
	private Integer expandLevel                        = 0;
	
    /**
     * Retorna o identificador do arquivo de recursos que armazena as propriedades para um
     * nó.
     * 
     * @return String contendo o identificador do arquivo de recursos.
     */
	public String getNodeResourceId(){
        return nodeResourceId;
    }

    /**
     * Define o identificador do arquivo de recursos que armazena as propriedades para um
     * nó.
     * 
     * @param nodeResourceId String contendo o identificador do arquivo de recursos.
     */
    public void setNodeResourceId(String nodeResourceId){
        this.nodeResourceId = nodeResourceId;
    }

    /**
     * Retorna o identificador da propriedade para o label do nó.
     * 
     * @return String contendo o identificador da propriedade.
     */
    public String getNodeLabelProperty(){
        return nodeLabelProperty;
    }

    /**
     * Define o identificador da propriedade para o label do nó.
     * 
     * @param nodeLabelProperty String contendo o identificador da propriedade.
     */
    public void setNodeLabelProperty(String nodeLabelProperty){
        this.nodeLabelProperty = nodeLabelProperty;
    }

    /**
	 * Retorna o evento a ser executado no momento da expansão do nó.
	 *  
	 * @return String contendo o evento a ser executado.
	 */
    public String getOnExpand(){
        return onExpand;
    }

    /**
     * Define o evento a ser executado no momento da expansão do nó.
     *  
     * @param onExpand String contendo o evento a ser executado.
     */
    public void setOnExpand(String onExpand){
        this.onExpand = onExpand;
    }

    /**
     * Retorna o identificador da ação a ser executado no momento da expansão do nó.
     *  
     * @return String contendo o identificador da ação.
     */
    public String getOnExpandAction(){
        return onExpandAction;
    }

    /**
     * Define o identificador da ação a ser executado no momento da expansão do nó.
     *  
     * @param onExpandAction String contendo o identificador da ação.
     */
    public void setOnExpandAction(String onExpandAction){
        this.onExpandAction = onExpandAction;
    }

    /**
     * Retorna o identificador do redirecionamento da ação de expansão do nó.
     *  
     * @return String contendo o identificador do redirecionamento.
     */
    public String getOnExpandActionForward(){
        return onExpandActionForward;
    }

    /**
     * Define o identificador do redirecionamento da ação de expansão do nó.
     *  
     * @param onExpandActionForward String contendo o identificador do redirecionamento.
     */
    public void setOnExpandActionForward(String onExpandActionForward){
        this.onExpandActionForward = onExpandActionForward;
    }

    /**
     * Retorna o identificador do redirecionamento em caso de falha da ação de expansão do nó.
     *  
     * @return String contendo o identificador do redirecionamento.
     */
    public String getOnExpandActionForwardOnFail(){
        return onExpandActionForwardOnFail;
    }

    /**
     * Define o identificador do redirecionamento em caso de falha da ação de expansão do nó.
     *  
     * @param onExpandActionForwardOnFail String contendo o identificador do redirecionamento.
     */
    public void setOnExpandActionForwardOnFail(String onExpandActionForwardOnFail){
        this.onExpandActionForwardOnFail = onExpandActionForwardOnFail;
    }

    /**
     * Retorna os identificadores das views a serem atualizadas após a execução da ação de expansão do nó.
     *  
     * @return String contendo os identificadores das views.
     */
    public String getOnExpandActionUpdateViews(){
        return onExpandActionUpdateViews;
    }

    /**
     * Define os identificadores das views a serem atualizadas após a execução da ação de expansão do nó.
     *  
     * @param onExpandActionUpdateViews String contendo os identificadores das views.
     */
    public void setOnExpandActionUpdateViews(String onExpandActionUpdateViews){
        this.onExpandActionUpdateViews = onExpandActionUpdateViews;
    }

    /**
     * Indica se o modelo de dados do formulário deve ser validado na execução da ação de expansão do nó.
     * 
     * @return True/False.
     */
    public Boolean getOnExpandActionValidate(){
        return onExpandActionValidate;
    }

    /**
     * Define se o modelo de dados do formulário deve ser validado na execução da ação de expansão do nó.
     * 
     * @param onExpandActionValidate True/False.
     */
    public void setOnExpandActionValidate(Boolean onExpandActionValidate){
        this.onExpandActionValidate = onExpandActionValidate;
    }

    /**
     * Retorna as propriedades do modelo de dados do formulário deve ser validado na execução da ação de expansão do nó.
     *  
     * @return String contendo os identificadores das propriedades.
     */
    public String getOnExpandActionValidateProperties(){
        return onExpandActionValidateProperties;
    }

    /**
     * Define as propriedades do modelo de dados do formulário deve ser validado na execução da ação de expansão do nó.
     *  
     * @param onExpandActionValidateProperties String contendo os identificadores das propriedades.
     */
    public void setOnExpandActionValidateProperties(String onExpandActionValidateProperties){
        this.onExpandActionValidateProperties = onExpandActionValidateProperties;
    }

    /**
     * Retorna o identificador da ação do evento de seleção do nó.
     * 
     * @return String contendo o identificador da ação.
     */
    public String getOnSelectAction(){
        return onSelectAction;
    }

    /**
     * Define o identificador da ação do evento de seleção do nó.
     * 
     * @param onSelectAction String contendo o identificador da ação.
     */
    public void setOnSelectAction(String onSelectAction){
        this.onSelectAction = onSelectAction;
    }

    /**
     * Retorna o identificador do redirecionamento da ação do evento de seleção do nó.
     * 
     * @return String contendo o identificador da redirecionamento.
     */
    public String getOnSelectActionForward(){
        return onSelectActionForward;
    }

    /**
     * Define o identificador do redirecionamento da ação do evento de seleção do nó.
     * 
     * @param onSelectActionForward String contendo o identificador da redirecionamento.
     */
    public void setOnSelectActionForward(String onSelectActionForward){
        this.onSelectActionForward = onSelectActionForward;
    }

    /**
     * Retorna o identificador do redirecionamento em caso de falha da ação do evento de seleção do nó.
     * 
     * @return String contendo o identificador da redirecionamento.
     */
    public String getOnSelectActionForwardOnFail(){
        return onSelectActionForwardOnFail;
    }

    /**
     * Define o identificador do redirecionamento em caso de falha da ação do evento de seleção do nó.
     * 
     * @param onSelectActionForwardOnFail String contendo o identificador da redirecionamento.
     */
    public void setOnSelectActionForwardOnFail(String onSelectActionForwardOnFail){
        this.onSelectActionForwardOnFail = onSelectActionForwardOnFail;
    }

    /**
     * Retorna os identificadores das views que serão atualizadas após a execução da ação de seleção do nó.
     *  
     * @return String contendo os identificadores das views.
     */
    public String getOnSelectActionUpdateViews(){
        return onSelectActionUpdateViews;
    }

    /**
     * Define os identificadores das views que serão atualizadas após a execução da ação de seleção do nó.
     *  
     * @param onSelectActionUpdateViews String contendo os identificadores das views.
     */
    public void setOnSelectActionUpdateViews(String onSelectActionUpdateViews){
        this.onSelectActionUpdateViews = onSelectActionUpdateViews;
    }

    /**
     * Indica se o modelo de dados do formulário deve ser validado na execução da ação de seleção do nó.
     *  
     * @return True/False.
     */
    public Boolean getOnSelectActionValidate(){
        return onSelectActionValidate;
    }

    /**
     * Define se o modelo de dados do formulário deve ser validado na execução da ação de seleção do nó.
     *  
     * @param onSelectActionValidate True/False.
     */
    public void setOnSelectActionValidate(Boolean onSelectActionValidate){
        this.onSelectActionValidate = onSelectActionValidate;
    }

    /**
     * Retorna as propriedades do modelo de dados do formulário deve ser validado na execução da ação de seleção do nó.
     *  
     * @return String contendo os identificadores das propriedades.
     */
    public String getOnSelectActionValidateProperties(){
        return onSelectActionValidateProperties;
    }

    /**
     * Define as propriedades do modelo de dados do formulário deve ser validado na execução da ação de seleção do nó.
     *  
     * @param onSelectActionValidateProperties String contendo os identificadores das propriedades.
     */
    public void setOnSelectActionValidateProperties(String onSelectActionValidateProperties){
        this.onSelectActionValidateProperties = onSelectActionValidateProperties;
    }

    /**
     * Retorna o identificador da ação do evento de deseleção do nó.
     * 
     * @return String contendo o identificador da ação.
     */
    public String getOnUnSelectAction(){
        return onUnSelectAction;
    }

    /**
     * Define o identificador da ação do evento de deseleção do nó.
     * 
     * @param onUnSelectAction String contendo o identificador da ação.
     */
    public void setOnUnSelectAction(String onUnSelectAction){
        this.onUnSelectAction = onUnSelectAction;
    }

    /**
     * Retorna o identificador do redirecionamento da ação do evento de deseleção do nó.
     * 
     * @return String contendo o identificador da redirecionamento.
     */
    public String getOnUnSelectActionForward(){
        return onUnSelectActionForward;
    }

    /**
     * Define o identificador do redirecionamento da ação do evento de deseleção do nó.
     * 
     * @param onUnSelectActionForward String contendo o identificador da redirecionamento.
     */
    public void setOnUnSelectActionForward(String onUnSelectActionForward){
        this.onUnSelectActionForward = onUnSelectActionForward;
    }

    /**
     * Retorna o identificador do redirecionamento em caso de falha da ação do evento de deseleção do nó.
     * 
     * @return String contendo o identificador da redirecionamento.
     */
    public String getOnUnSelectActionForwardOnFail(){
        return onUnSelectActionForwardOnFail;
    }

    /**
     * Define o identificador do redirecionamento em caso de falha da ação do evento de deseleção do nó.
     * 
     * @param onUnSelectActionForwardOnFail String contendo o identificador da redirecionamento.
     */
    public void setOnUnSelectActionForwardOnFail(String onUnSelectActionForwardOnFail){
        this.onUnSelectActionForwardOnFail = onUnSelectActionForwardOnFail;
    }

    /**
     * Retorna os identificadores das views que serão atualizadas após a execução da ação de deseleção do nó.
     *  
     * @return String contendo os identificadores das views.
     */
    public String getOnUnSelectActionUpdateViews(){
        return onUnSelectActionUpdateViews;
    }

    /**
     * Define os identificadores das views que serão atualizadas após a execução da ação de deseleção do nó.
     *  
     * @param onUnSelectActionUpdateViews String contendo os identificadores das views.
     */
    public void setOnUnSelectActionUpdateViews(String onUnSelectActionUpdateViews){
        this.onUnSelectActionUpdateViews = onUnSelectActionUpdateViews;
    }

    /**
     * Indica se o modelo de dados do formulário deve ser validado na execução da ação de deseleção do nó.
     *  
     * @return True/False.
     */
    public Boolean getOnUnSelectActionValidate(){
        return onUnSelectActionValidate;
    }

    /**
     * Define se o modelo de dados do formulário deve ser validado na execução da ação de deseleção do nó.
     *  
     * @param onUnSelectActionValidate True/False.
     */
    public void setOnUnSelectActionValidate(Boolean onUnSelectActionValidate){
        this.onUnSelectActionValidate = onUnSelectActionValidate;
    }

    /**
     * Retorna as propriedades do modelo de dados do formulário deve ser validado na execução da ação de deseleção do nó.
     *  
     * @return String contendo os identificadores das propriedades.
     */
    public String getOnUnSelectActionValidateProperties(){
        return onUnSelectActionValidateProperties;
    }

    /**
     * Define as propriedades do modelo de dados do formulário deve ser validado na execução da ação de deseleção do nó.
     *  
     * @param onUnSelectActionValidateProperties String contendo os identificadores das propriedades.
     */
    public void setOnUnSelectActionValidateProperties(String onUnSelectActionValidateProperties){
        this.onUnSelectActionValidateProperties = onUnSelectActionValidateProperties;
    }

    /**
	 * Retorna o evento a ser executado quando um nó do treeView for deselecionado.
	 * 
	 * @return String contendo evento a ser executado.
	 */
	public String getOnUnSelect(){
		return onUnSelect;
	}

	/**
	 * Define o evento a ser executado quando um nó do treeView for deselecionado.
	 * 
	 * @param onUnSelect String contendo evento a ser executado.
	 */
	public void setOnUnSelect(String onUnSelect){
		this.onUnSelect = onUnSelect;
	}

	/**
	 * Retorna o identificador do nível máximo que se deve expandir os nós.
	 * 
	 * @return Valor inteiro contendo o identificador do nível.
	 */
	public Integer getExpandLevel(){
		return expandLevel;
	}

	/**
	 * Define o identificador do nível máximo que se deve expandir os nós.
	 * 
	 * @param expandLevel Valor inteiro contendo o identificador do nível.
	 */
	public void setExpandLevel(Integer expandLevel){
		this.expandLevel = expandLevel;
	}

	/**
	 * Retorna o evento a ser executado quando um nó do treeView for selecionado.
	 * 
	 * @return String contendo evento a ser executado.
	 */
	public String getOnSelect(){
		return onSelect;
	}

	/**
	 * Define o evento a ser executado quando um nó do treeView for selecionado.
	 * 
	 * @param onSelect String contendo evento a ser executado.
	 */
	public void setOnSelect(String onSelect){
		this.onSelect = onSelect;
	}

	/**
	 * Retorna o estilo CSS para o ícone do nó fechado.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getClosedNodeIconStyleClass(){
		return closedNodeIconStyleClass;
	}

	/**
	 * Define o estilo CSS para o ícone do nó fechado.
	 * 
	 * @param closedNodeIconStyleClass String contendo o estilo CSS.
	 */
	public void setClosedNodeIconStyleClass(String closedNodeIconStyleClass){
		this.closedNodeIconStyleClass = closedNodeIconStyleClass;
	}

	/**
	 * Retorna o estilo CSS para o ícone de expansão do nó.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getCollapsedNodeIconStyleClass(){
		return collapsedNodeIconStyleClass;
	}

	/**
	 * Define o estilo CSS para o ícone de expansão do nó.
	 * 
	 * @param collapsedNodeIconStyleClass String contendo o estilo CSS.
	 */
	public void setCollapsedNodeIconStyleClass(String collapsedNodeIconStyleClass){
		this.collapsedNodeIconStyleClass = collapsedNodeIconStyleClass;
	}

	/**
	 * Retorna o estilo CSS para o ícone do nó expandido.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getOpenedNodeIconStyleClass(){
		return openedNodeIconStyleClass;
	}

	/**
	 * Define o estilo CSS para o ícone do nó expandido.
	 * 
	 * @param openedNodeIconStyleClass String contendo o estilo CSS.
	 */
	public void setOpenedNodeIconStyleClass(String openedNodeIconStyleClass){
		this.openedNodeIconStyleClass = openedNodeIconStyleClass;
	}

	/**
	 * Retorna o estilo CSS para o ícone para retrair o nó.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getExpandedNodeIconStyleClass(){
		return expandedNodeIconStyleClass;
	}

	/**
	 * Define o estilo CSS para o ícone para retrair o nó.
	 * 
	 * @param expandedNodeIconStyleClass String contendo o estilo CSS.
	 */
	public void setExpandedNodeIconStyleClass(String expandedNodeIconStyleClass){
		this.expandedNodeIconStyleClass = expandedNodeIconStyleClass;
	}

	/**
	 * Retorna o estilo CSS para o ícone do nó.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getNodeIconStyleClass(){
		return nodeIconStyleClass;
	}

	/**
	 * Define o estilo CSS para o ícone do nó.
	 * 
	 * @param nodeIconStyleClass String contendo o estilo CSS.
	 */
	public void setNodeIconStyleClass(String nodeIconStyleClass){
		this.nodeIconStyleClass = nodeIconStyleClass;
	}

	/**
	 * Retorna o estilo CSS para o label do nó selecionado.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getNodeLabelSelectedStyleClass(){
		return nodeLabelSelectedStyleClass;
	}

	/**
	 * Define o estilo CSS para o label do nó selecionado.
	 * 
	 * @param nodeLabelSelectedStyleClass String contendo o estilo CSS.
	 */
	public void setNodeLabelSelectedStyleClass(String nodeLabelSelectedStyleClass){
		this.nodeLabelSelectedStyleClass = nodeLabelSelectedStyleClass;
	}

	/**
	 * Retorna o estilo CSS para o label do nó.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getNodeLabelStyleClass(){
		return nodeLabelStyleClass;
	}

	/**
	 * Define o estilo CSS para o label do nó.
	 * 
	 * @param nodeLabelStyleClass String contendo o estilo CSS.
	 */
	public void setNodeLabelStyleClass(String nodeLabelStyleClass){
		this.nodeLabelStyleClass = nodeLabelStyleClass;
	}
	
	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseOptionsPropertyTag#initialize()
	 */
	protected void initialize() throws Throwable{
        setComponentType(ComponentType.TREE_VIEW);
        
        if(getLabelPositionType() == null)
            setLabelPositionType(PositionType.TOP);
        
        if(nodeIconStyleClass.length() == 0)
            nodeIconStyleClass = TaglibConstants.DEFAULT_NODE_ICON_STYLE_CLASS;
        
        if(nodeLabelStyleClass.length() == 0)
            nodeLabelStyleClass = TaglibConstants.DEFAULT_NODE_LABEL_STYLE_CLASS;

        if(nodeLabelSelectedStyleClass.length() == 0)
            nodeLabelSelectedStyleClass = TaglibConstants.DEFAULT_NODE_LABEL_SELECTED_STYLE_CLASS;
        
        if(expandedNodeIconStyleClass.length() == 0)
            expandedNodeIconStyleClass = TaglibConstants.DEFAULT_EXPANDED_NODE_ICON_STYLE_CLASS;

        if(collapsedNodeIconStyleClass.length() == 0)
            collapsedNodeIconStyleClass = TaglibConstants.DEFAULT_COLLAPSED_NODE_ICON_STYLE_CLASS;

        if(openedNodeIconStyleClass.length() == 0)
            openedNodeIconStyleClass = TaglibConstants.DEFAULT_OPENED_NODE_ICON_STYLE_CLASS;

        if(closedNodeIconStyleClass.length() == 0)
            closedNodeIconStyleClass = TaglibConstants.DEFAULT_CLOSED_NODE_ICON_STYLE_CLASS;
        
        setLabelAlignmentType(AlignmentType.LEFT);

        super.initialize();
        
        if(onSelectAction.length() > 0){
            String actionForm = getActionForm();
            
            if(actionForm.length() > 0){
                StringBuilder onSelectContent = new StringBuilder();
    
                if(onSelect.length() > 0){
                    onSelectContent.append(onSelect);
                    
                    if(!onSelect.endsWith(";"))
                        onSelectContent.append(";");
                    
                    onSelectContent.append(" ");
                }
                
                onSelectContent.append("document.");
                onSelectContent.append(actionForm);
                onSelectContent.append(".");
                
                if(isForSearch())
                    onSelectContent.append(AttributeConstants.VALIDATE_SEARCH_MODEL_KEY);
                else
                    onSelectContent.append(AttributeConstants.VALIDATE_MODEL_KEY);
                
                onSelectContent.append(".value = ");
                onSelectContent.append(onSelectActionValidate);
                onSelectContent.append(";");
                
                if(onSelectActionValidateProperties.length() > 0){
                    onSelectContent.append(" document.");
                    onSelectContent.append(actionForm);
                    onSelectContent.append(".");
                    onSelectContent.append(AttributeConstants.VALIDATE_PROPERTIES_KEY);
                    onSelectContent.append(".value = '");
                    onSelectContent.append(onSelectActionValidateProperties);
                    onSelectContent.append("'; ");
                }
                
                if(onSelectActionForward.length() > 0){
                    onSelectContent.append("document.");
                    onSelectContent.append(actionForm);
                    onSelectContent.append(".");
                    onSelectContent.append(AttributeConstants.FORWARD_KEY);
                    onSelectContent.append(".value = '");
                    onSelectContent.append(onSelectActionForward);
                    onSelectContent.append("; ");
                }
                
                if(onSelectActionForwardOnFail.length() > 0){
                    onSelectContent.append("document.");
                    onSelectContent.append(actionForm);
                    onSelectContent.append(".");
                    onSelectContent.append(AttributeConstants.FORWARD_ON_FAIL_KEY);
                    onSelectContent.append(".value = '");
                    onSelectContent.append(onSelectActionForwardOnFail);
                    onSelectContent.append("; ");
                }
    
                if(onSelectActionUpdateViews.length() > 0){
                    onSelectContent.append("document.");
                    onSelectContent.append(actionForm);
                    onSelectContent.append(".");
                    onSelectContent.append(AttributeConstants.UPDATE_VIEWS_KEY);
                    onSelectContent.append(".value = '");
                    onSelectContent.append(onSelectActionUpdateViews);
                    onSelectContent.append("; ");
                }
    
                onSelectContent.append("document.");
                onSelectContent.append(actionForm);
                onSelectContent.append(".");
                onSelectContent.append(AttributeConstants.ACTION_KEY);
                onSelectContent.append(".value = '");
                onSelectContent.append(onSelectAction);
                onSelectContent.append("'; submitForm(document.");
                onSelectContent.append(actionForm);
                onSelectContent.append(");");
                
                onSelect = onSelectContent.toString();
            }
        }
        
        if(onUnSelectAction.length() > 0){
            String actionForm = getActionForm();
            
            if(actionForm.length() > 0){
                StringBuilder onUnSelectContent = new StringBuilder();
    
                if(onUnSelect.length() > 0){
                    onUnSelectContent.append(onUnSelect);
                    
                    if(!onUnSelect.endsWith(";"))
                        onUnSelectContent.append(";");
                    
                    onUnSelectContent.append(" ");
                }
                
                onUnSelectContent.append("document.");
                onUnSelectContent.append(actionForm);
                onUnSelectContent.append(".");
                
                if(isForSearch())
                    onUnSelectContent.append(AttributeConstants.VALIDATE_SEARCH_MODEL_KEY);
                else
                    onUnSelectContent.append(AttributeConstants.VALIDATE_MODEL_KEY);
                
                onUnSelectContent.append(".value = ");
                onUnSelectContent.append(onUnSelectActionValidate);
                onUnSelectContent.append(";");
                
                if(onUnSelectActionValidateProperties.length() > 0){
                    onUnSelectContent.append(" document.");
                    onUnSelectContent.append(actionForm);
                    onUnSelectContent.append(".");
                    onUnSelectContent.append(AttributeConstants.VALIDATE_PROPERTIES_KEY);
                    onUnSelectContent.append(".value = '");
                    onUnSelectContent.append(onUnSelectActionValidateProperties);
                    onUnSelectContent.append("'; ");
                }
                
                if(onUnSelectActionForward.length() > 0){
                    onUnSelectContent.append("document.");
                    onUnSelectContent.append(actionForm);
                    onUnSelectContent.append(".");
                    onUnSelectContent.append(AttributeConstants.FORWARD_KEY);
                    onUnSelectContent.append(".value = '");
                    onUnSelectContent.append(onUnSelectActionForward);
                    onUnSelectContent.append("; ");
                }
                
                if(onUnSelectActionForwardOnFail.length() > 0){
                    onUnSelectContent.append("document.");
                    onUnSelectContent.append(actionForm);
                    onUnSelectContent.append(".");
                    onUnSelectContent.append(AttributeConstants.FORWARD_ON_FAIL_KEY);
                    onUnSelectContent.append(".value = '");
                    onUnSelectContent.append(onUnSelectActionForwardOnFail);
                    onUnSelectContent.append("; ");
                }
    
                if(onUnSelectActionUpdateViews.length() > 0){
                    onUnSelectContent.append("document.");
                    onUnSelectContent.append(actionForm);
                    onUnSelectContent.append(".");
                    onUnSelectContent.append(AttributeConstants.UPDATE_VIEWS_KEY);
                    onUnSelectContent.append(".value = '");
                    onUnSelectContent.append(onUnSelectActionUpdateViews);
                    onUnSelectContent.append("; ");
                }
    
                onUnSelectContent.append("document.");
                onUnSelectContent.append(actionForm);
                onUnSelectContent.append(".");
                onUnSelectContent.append(AttributeConstants.ACTION_KEY);
                onUnSelectContent.append(".value = '");
                onUnSelectContent.append(onUnSelectAction);
                onUnSelectContent.append("'; submitForm(document.");
                onUnSelectContent.append(actionForm);
                onUnSelectContent.append(");");
                
                onUnSelect = onUnSelectContent.toString();
            }
        }

        if(onExpandAction.length() > 0){
            String actionForm = getActionForm();
            
            if(actionForm.length() > 0){
                StringBuilder onExpandContent = new StringBuilder();
    
                if(onExpand.length() > 0){
                    onExpandContent.append(onExpand);
                    
                    if(!onExpand.endsWith(";"))
                        onExpandContent.append(";");
                    
                    onExpandContent.append(" ");
                }
                
                onExpandContent.append("document.");
                onExpandContent.append(actionForm);
                onExpandContent.append(".");
                
                if(isForSearch())
                    onExpandContent.append(AttributeConstants.VALIDATE_SEARCH_MODEL_KEY);
                else
                    onExpandContent.append(AttributeConstants.VALIDATE_MODEL_KEY);
                
                onExpandContent.append(".value = ");
                onExpandContent.append(onExpandActionValidate);
                onExpandContent.append(";");
                
                if(onExpandActionValidateProperties.length() > 0){
                    onExpandContent.append(" document.");
                    onExpandContent.append(actionForm);
                    onExpandContent.append(".");
                    onExpandContent.append(AttributeConstants.VALIDATE_PROPERTIES_KEY);
                    onExpandContent.append(".value = '");
                    onExpandContent.append(onExpandActionValidateProperties);
                    onExpandContent.append("'; ");
                }
                
                if(onExpandActionForward.length() > 0){
                    onExpandContent.append("document.");
                    onExpandContent.append(actionForm);
                    onExpandContent.append(".");
                    onExpandContent.append(AttributeConstants.FORWARD_KEY);
                    onExpandContent.append(".value = '");
                    onExpandContent.append(onExpandActionForward);
                    onExpandContent.append("; ");
                }
                
                if(onExpandActionForwardOnFail.length() > 0){
                    onExpandContent.append("document.");
                    onExpandContent.append(actionForm);
                    onExpandContent.append(".");
                    onExpandContent.append(AttributeConstants.FORWARD_ON_FAIL_KEY);
                    onExpandContent.append(".value = '");
                    onExpandContent.append(onExpandActionForwardOnFail);
                    onExpandContent.append("; ");
                }
    
                if(onExpandActionUpdateViews.length() > 0){
                    onExpandContent.append("document.");
                    onExpandContent.append(actionForm);
                    onExpandContent.append(".");
                    onExpandContent.append(AttributeConstants.UPDATE_VIEWS_KEY);
                    onExpandContent.append(".value = '");
                    onExpandContent.append(onExpandActionUpdateViews);
                    onExpandContent.append("; ");
                }
    
                onExpandContent.append("document.");
                onExpandContent.append(actionForm);
                onExpandContent.append(".");
                onExpandContent.append(AttributeConstants.ACTION_KEY);
                onExpandContent.append(".value = '");
                onExpandContent.append(onExpandAction);
                onExpandContent.append("'; submitForm(document.");
                onExpandContent.append(actionForm);
                onExpandContent.append(");");
                
                onExpand = onExpandContent.toString();
            }
        }
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderOpen()
	 */
	protected void renderOpen() throws Throwable{
        PropertyInfo propertyInfo = getPropertyInfo();
        
        if(propertyInfo != null){
            renderDataAttributes();
            renderDataIndexesAttributes();
        }
        
		println("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		println("<tr>");

		renderLabel();

		println("</tr>");
		println("<tr>");
		println("<td>");

		print("<div id=\"");
		print(AttributeConstants.TREE_VIEW_KEY);
		print("\" class=\"");
		print(TaglibConstants.DEFAULT_TREE_VIEW_STYLE_CLASS);
		print("\" style=\"overflow: AUTO;");
		
		String width  = getWidth();
		String height = getHeight();

		if(width.length() > 0){
			print("width: ");
			print(width);
			print(";");
		}

		if(height.length() > 0){
			print("height: ");
			print(height);
			print(";");
		}

		renderTooltip();

		println("\">");
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BasePropertyTag#renderBody()
	 */
	protected void renderBody() throws Throwable{
		println("<div id=\"");
		print(AttributeConstants.TREE_VIEW_CONTENT_KEY);
		print("\" class=\"");
		print(TaglibConstants.DEFAULT_TREE_VIEW_CONTENT_STYLE_CLASS);
		println("\">");
		
		PropertyInfo propertyInfo = getPropertyInfo();
		List         dataValues   = getDataValues();

		if(propertyInfo != null && dataValues != null && dataValues.size() > 0)
			renderNodes();
		else{
			print("<span class=\"");
			print(TaglibConstants.DEFAULT_NODE_LABEL_STYLE_CLASS);
			print("\">");

			if(propertyInfo == null)
				print(getInvalidPropertyMessage());
			else
				print(getDataIsEmptyMessage());

			println("</span>");
		}
		
		println("</div>");
	}
	
	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseOptionsPropertyTag#renderDataIndexesAttributes()
	 */
	protected void renderDataIndexesAttributes() throws Throwable{
        PropertyInfo propertyInfo = getPropertyInfo();

        if(propertyInfo != null && !propertyInfo.isCollection())
            super.renderDataIndexesAttributes();
		
		List dataValues = getDataValues();

		if(propertyInfo != null && dataValues != null && dataValues.size() > 0){
            StringBuilder tagName = new StringBuilder();

            tagName.append(getName());
            tagName.append(".");
            tagName.append(AttributeConstants.CURRENT_NODE_KEY);

            HiddenPropertyTag currentNodeTag = new HiddenPropertyTag();
            
            currentNodeTag.setPageContext(pageContext);
			currentNodeTag.setName(tagName.toString());
			currentNodeTag.setValue("");
			currentNodeTag.doStartTag();
			currentNodeTag.doEndTag();
		}
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseOptionsPropertyTag#renderDataAttributes()
	 */
	protected void renderDataAttributes() throws Throwable{
        PropertyInfo propertyInfo = getPropertyInfo();

        if(propertyInfo != null && !propertyInfo.isCollection())
            super.renderDataAttributes();
		
        List dataValues = getDataValues();

        if(propertyInfo != null && dataValues != null && dataValues.size() > 0){
    		if(propertyInfo.isCollection()){
    			ListPropertyTag selectionTag = new ListPropertyTag();
    
    			selectionTag.setPageContext(pageContext);
    			selectionTag.setPropertyInfo(propertyInfo);
    			selectionTag.setName(getName());
    			selectionTag.setResourceDir(getResourceDir());
    			selectionTag.setResourceId(getResourceId());
    			selectionTag.setStyle("display: NONE;");
    			selectionTag.setData(getData());
    			selectionTag.setDataScope(getDataScope());
    			selectionTag.setShowLabel(false);
    			selectionTag.setShowFirstOption(false);
    			selectionTag.setValue(getValue());
    			selectionTag.doStartTag();
    			selectionTag.doEndTag();
    		}
    		else{
    			HiddenPropertyTag selectionTag = new HiddenPropertyTag();
    
    			selectionTag.setPageContext(pageContext);
                selectionTag.setPropertyInfo(propertyInfo);
    			selectionTag.setName(getName());
                selectionTag.setResourceDir(getResourceDir());
                selectionTag.setResourceId(getResourceId());
                selectionTag.setShowLabel(false);
                selectionTag.setValue(getValue());
    			selectionTag.doStartTag();
    			selectionTag.doEndTag();
    		}
        }
	}
	
	/**
	 * Renderiza a árvore contendo os nós.
	 *
	 * @throws Throwable
	 */
    private void renderNodes() throws Throwable{
		List dataValues = getDataValues();
		
		if(dataValues != null && dataValues.size() > 0)
		    renderNodes(dataValues, null, "", 0);
	}

	/**
	 * Renderiza a árvore contendo os nós.
	 * 
	 * @param nodes Lista contendo as informações dos nós.
	 * @param index String contendo o prefixo da posição atual do nó.
	 * @param parent Instância do nó pai.
	 * @param level Valor inteiro contendo o número de tabulações.
	 * @throws Throwable
	 */
	private void renderNodes(List<Node> nodes, Node parent, String index, Integer level) throws Throwable{
        PropertyInfo       propertyInfo               = getPropertyInfo();
        String             propertyId                 = propertyInfo.getId();
        String             name                       = getName();
        Object             value                      = getValue();
		Node               node                       = null;
        List<Node>         nodeChilds                 = null;             
		StringBuilder      nodeIndex                  = null;
		StringBuilder      nodeId                     = null;
		Object             nodeValue                  = null;
		Object             nodeValueLabel             = null;
		PropertyInfo       nodeValueLabelPropertyInfo = null;
		StringBuilder      nodeExpandedId             = null;
		HiddenPropertyTag  nodeExpandedTag            = null;
		String             nodeIsExpandedBuffer       = "";
		Boolean            nodeIsExpanded             = false;
		Boolean            nodeIsSelected             = false;
		StringBuilder      parentNodeId               = null;
		PropertiesResource resources                  = null;
		
		if(nodeResourceId.length() > 0)
		    resources = getI18nResource(nodeResourceId);
		else
		    resources = getI18nResource();
		
        PropertiesResource defaultResources  = getDefaultI18nResource();
		String             onSelectContent   = "";
		String             onUnSelectContent = "";
        Locale             currentLanguage   = systemController.getCurrentLanguage();
        StringBuilder      content           = null;
        StringBuilder      trace             = null;
        ScriptTag          scriptTag         = null;
        
		for(Integer cont = 0 ; cont < nodes.size() ; cont++){
			node              = nodes.get(cont);
            onSelectContent   = PropertyUtil.fillPropertiesInString(node, onSelect, currentLanguage);
            onUnSelectContent = PropertyUtil.fillPropertiesInString(node, onUnSelect, currentLanguage);
            
			if(nodeIndex == null)
			    nodeIndex = new StringBuilder();
			else
				nodeIndex.delete(0, nodeIndex.length());
			
			if(index.length() > 0){
				nodeIndex.append(index);
				nodeIndex.append("_");
			}
			
			nodeIndex.append(cont);
			
			if(nodeLabelProperty.length() > 0){
			    nodeValueLabelPropertyInfo = PropertyUtil.getPropertyInfo(node.getClass(), nodeLabelProperty);
			    
			    if(nodeValueLabelPropertyInfo != null){
			        nodeValueLabel = PropertyUtil.getProperty(node, nodeLabelProperty);
			        nodeValueLabel = PropertyUtil.format(nodeValueLabel, getValueMapInstance(), nodeValueLabelPropertyInfo.getPattern(), nodeValueLabelPropertyInfo.useAdditionalFormatting(), nodeValueLabelPropertyInfo.getPrecision(), currentLanguage);
			    }
			    else{
			        if(propertyInfo != null && (propertyInfo.isModel() || propertyInfo.hasModel()))
			            nodeValueLabel = node.toString();
			        else
			            nodeValueLabel = PropertyUtil.format(node.toString(), getValueMapInstance(), getPattern(), useAdditionalFormatting(), getPrecision(), currentLanguage);
			    }
			}
            else{
                if(propertyInfo != null && (propertyInfo.isModel() || propertyInfo.hasModel()))
                    nodeValueLabel = node.toString();
                else
                    nodeValueLabel = PropertyUtil.format(node.toString(), getValueMapInstance(), getPattern(), useAdditionalFormatting(), getPrecision(), currentLanguage);
            }
			
            if(node instanceof ObjectModel && StringUtil.trim(nodeValueLabel).length() == 0){
                if(((ObjectModel)node).getType() == ComponentType.MENU_ITEM_SEPARATOR)
                    nodeValueLabel = "-";
                else{
                    nodeValueLabel = resources.getProperty(((ObjectModel)node).getName().concat(".").concat(AttributeConstants.LABEL_KEY), false);
                    
                    if(nodeValueLabel == null)
                        nodeValueLabel = defaultResources.getProperty(((ObjectModel)node).getName().concat(".").concat(AttributeConstants.LABEL_KEY), false);
                    
                    if(nodeValueLabel == null)
                        nodeValueLabel = ((ObjectModel)node).getName();
                }
            }
			
			if(propertyInfo.isModel() || propertyInfo.hasModel())
			    nodeValue = node;
			else
			    nodeValue = PropertyUtil.getProperty(node, propertyId);
			
			if((parent == null && node.getParent() == null) || (parent != null && parent.equals(node.getParent()))){
			    if(nodeId == null)
			        nodeId = new StringBuilder();
			    else
					nodeId.delete(0, nodeId.length());
				
				nodeId.append(name);
				nodeId.append(".");
				nodeId.append(AttributeConstants.NODE_KEY);
				nodeId.append(nodeIndex.toString().hashCode());

				if(parent != null){
				    if(parentNodeId == null)
				        parentNodeId = new StringBuilder();
				    else
						parentNodeId.delete(0, parentNodeId.length());
					
					parentNodeId.append(name);
					parentNodeId.append(".");
					parentNodeId.append(AttributeConstants.NODE_KEY);
					parentNodeId.append(index.hashCode());
				}

				println("<table>");
				println("<tr>");
				
				if(trace == null)
				    trace = new StringBuilder();
				else
				    trace.delete(0, trace.length());
				    
			    trace.append("<td class=\"");
			    trace.append(TaglibConstants.DEFAULT_TRACE_STYLE_CLASS);
			    trace.append("\"></td>");

				println(StringUtil.replicate(trace.toString(), level));

				if(!node.hasChildNodes() && onExpand.length() == 0){
					print("<td class=\"");
					print(TaglibConstants.DEFAULT_TRACE_STYLE_CLASS);
					println("\"></td>");
					
					print("<td class=\"");
					print(nodeIconStyleClass);
					println("\"></td>");
				}
				else{
				    if(nodeExpandedId == null)
				        nodeExpandedId = new StringBuilder();
				    else
				        nodeExpandedId.delete(0, nodeExpandedId.length());
					
				    nodeExpandedId.append(nodeId);
				    nodeExpandedId.append(".");
				    nodeExpandedId.append(AttributeConstants.IS_NODE_EXPANDED_KEY);

					nodeIsExpandedBuffer = StringUtil.trim(systemController.getRequest().getParameter(nodeExpandedId.toString()));

					if(nodeIsExpandedBuffer.length() == 0){
						if(getExpandLevel() <= level)
							nodeIsExpanded = false;
						else
							nodeIsExpanded = true;
					}
					else
					    nodeIsExpanded = Boolean.parseBoolean(nodeIsExpandedBuffer);

					print("<td id=\"");
					print(nodeId);
					print(".");
					print(AttributeConstants.NODE_EXPAND_ICON_KEY);
					print("\" class=\"");

					if(nodeIsExpanded)
						print(expandedNodeIconStyleClass);
					else
						print(collapsedNodeIconStyleClass);

					print("\" onClick=\"");
					
					if(onExpandAction.length() > 0){
						print("selectNode('");
    					print(name);
    					print("', '");
    					print(nodeId);
    					print("', '");
    					print(nodeLabelStyleClass);
    					print("', '");
    					print(nodeLabelSelectedStyleClass);
    					print("'); ");
					}
					
    				print("showHideNode('");
					print(nodeId);
					print("', '");
					print(expandedNodeIconStyleClass);
					print("', '");
					print(collapsedNodeIconStyleClass);
					print("', '");
					print(openedNodeIconStyleClass);
					print("', '");
					print(closedNodeIconStyleClass);
					print("', ");
					
					if(onExpand.length() > 0){
                        print("function() {");
    					print(onExpand);
    					print("}");
					}
					else
					    print("null");
					
					println(");\"></td>");
					
					print("<td id=\"");
					print(nodeId);
					print(".");
					print(AttributeConstants.NODE_ICON_KEY);
					print("\" class=\"");

					if(!nodeIsExpanded)
						print(closedNodeIconStyleClass);
					else
						print(openedNodeIconStyleClass);

					println("\"></td>");
				}

				print("<td id=\"");
				print(nodeId);
				print(".");
				print(AttributeConstants.LABEL_KEY);
				print("\" class=\"");

				if(value != null){
     				if(propertyInfo.isCollection()){
     					nodeIsSelected = ((Collection)value).contains(nodeValue);
     
     					if(nodeIsSelected)
     						print(nodeLabelSelectedStyleClass);
     					else
     						print(nodeLabelStyleClass);
     				}
     				else{
						nodeIsSelected = value.equals(nodeValue);
     
						if(nodeIsSelected)
							print(nodeLabelSelectedStyleClass);
						else
							print(nodeLabelStyleClass);
     				}
				}
				else{
					nodeIsSelected = false;
				
					print(nodeLabelStyleClass);
				}

				print("\" value=\"");
				
	            if(propertyInfo.isModel() || propertyInfo.hasModel()){
	                print("objectId{");
	                print(nodeIndex);
	                print("}");
	            }
	            else
	                print(PropertyUtil.format(nodeValue, getValueMapInstance(), getPattern(), useAdditionalFormatting(), getPrecision(), currentLanguage));

				print("\"");

				if(parent != null){
					print(" parent=\"");
					print(parentNodeId);
					print("\"");
				}

				if(isEnabled()){
					print(" onClick=\"selectUnSelectNode('");
					print(name);
					print("', '");
					print(nodeId);
					print("', '");
					print(nodeLabelStyleClass);
					print("', '");
					print(nodeLabelSelectedStyleClass);
					print("'");

					if(onSelectContent.length() > 0 || onUnSelectContent.length() > 0){
						print(", ");

						if(onSelectContent.length() > 0){
							print("function() {");
							print(onSelectContent);
							print("}");
						}
						else
							print("null");

						print(", ");

						if(onUnSelectContent.length() > 0){
							print("function() {");
							print(onUnSelectContent);
							print("}");
						}
						else
							print("null");
					}

					print(");\"");
				}

				print(">");
				print("&nbsp;");
				print(nodeValueLabel);
				println("&nbsp;</td>");
				println("</tr>");
				println("</table>");

				if(!propertyInfo.isCollection()){
					if(nodeIsSelected){
					    content = new StringBuilder();
					    content.append("document.getElementById('");
					    content.append(name);
					    content.append("').value = '");
					    content.append(nodeValue);
					    content.append("'; document.getElementById('");
					    content.append(name);
					    content.append(".");
					    content.append(AttributeConstants.CURRENT_NODE_KEY);
					    content.append("').value = '");
					    content.append(nodeId);
					    content.append("';");
					    content.append(StringUtil.getLineBreak());
					    
					    scriptTag = new ScriptTag();
					    scriptTag.setPageContext(pageContext);
					    scriptTag.setContent(content.toString());
					    scriptTag.doStartTag();
					    scriptTag.doEndTag();
					}
				}

				print("<div id=\"");
				print(nodeId);
				print("\"");

				if(!nodeIsExpanded)
					print(" style=\"display: NONE;\"");

				println(">");

				if(node.hasChildNodes() || onExpand.length() > 0){
					nodeExpandedTag = new HiddenPropertyTag();
					nodeExpandedTag.setPageContext(pageContext);
					nodeExpandedTag.setName(nodeExpandedId.toString());
					nodeExpandedTag.setValue(nodeIsExpanded);
					nodeExpandedTag.doStartTag();
					nodeExpandedTag.doEndTag();

					if(node.hasChildNodes()){
						nodeChilds = node.getChildNodes();

						renderNodes(nodeChilds, node, nodeIndex.toString(), level + 1);
					}
				}

				println("</div>");
			}
		}
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BaseTag#renderClose()
	 */
	protected void renderClose() throws Throwable{
		println("</div>");

		println("</td>");
		println("</tr>");
		println("</table>");
	}

	/**
	 * @see br.com.concepting.framework.web.taglibs.BasePropertyTag#clearAttributes()
	 */
    protected void clearAttributes(){
	    super.clearAttributes();

	    setWidth("");
	    setHeight("");
	    setNodeIconStyleClass("");
	    setNodeLabelSelectedStyleClass("");
	    setNodeLabelStyleClass("");
	    setNodeLabelProperty("");
	    setNodeResourceId("");
	    setExpandedNodeIconStyleClass("");
	    setCollapsedNodeIconStyleClass("");
	    setOpenedNodeIconStyleClass("");
	    setClosedNodeIconStyleClass("");
        setOnSelect("");
        setOnSelectAction("");
        setOnSelectActionForward("");
        setOnSelectActionForwardOnFail("");
        setOnSelectActionUpdateViews("");
        setOnSelectActionValidate(false);
        setOnSelectActionValidateProperties("");
        setOnUnSelect("");
        setOnUnSelectAction("");
        setOnUnSelectActionForward("");
        setOnUnSelectActionForwardOnFail("");
        setOnUnSelectActionUpdateViews("");
        setOnUnSelectActionValidate(false);
        setOnUnSelectActionValidateProperties("");
        setOnExpand("");
        setOnExpandAction("");
        setOnExpandActionForward("");
        setOnExpandActionForwardOnFail("");
        setOnExpandActionUpdateViews("");
        setOnExpandActionValidate(false);
        setOnExpandActionValidateProperties("");
	    setExpandLevel(0);
    }
}