package br.com.concepting.framework.web.taglibs;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.apache.struts.taglib.TagUtils;

import br.com.concepting.framework.model.helpers.PropertyInfo;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.util.ImageUtil;
import br.com.concepting.framework.util.StringUtil;
import br.com.concepting.framework.util.constants.AttributeConstants;
import br.com.concepting.framework.util.helpers.Node;
import br.com.concepting.framework.util.types.ComponentType;
import br.com.concepting.framework.util.types.PositionType;
import br.com.concepting.framework.web.taglibs.constants.TaglibConstants;
import br.com.concepting.framework.web.types.ScopeType;

/**
 * Classe que define o componente visual Treeview (Árvore de visualização).
 * 
 * @author fvilarinho
 * @since 2.0
 */
public class TreeViewPropertyTag extends BaseOptionsPropertyTag{
	private String  nodeIconStyleClass          = "";
	private String  nodeLabelStyleClass         = "";
	private String  nodeLabelSelectedStyleClass = "";
	private String  openLeafIconStyleClass      = "";
	private String  closeLeafIconStyleClass     = "";
	private String  openedFolderIconStyleClass  = "";
	private String  closedFolderIconStyleClass  = "";
	private String  onSelect                    = "";
	private String  onUnSelect                  = "";
	private Integer expandLevel                 = 0;
	private Boolean processActions              = false;
	private String  updateViews                 = "";

    /**
     * Retorna os identificadores das views (separados por vírgula) a serem atualizadas após o
     * processamento da ação requisitada.
     * 
     * @return String contendo os identificadores das views.
     */
    public String getUpdateViews(){
        return updateViews;
    }

    /**
     * Define os identificadores das views (separados por vírgula) a serem atualizadas após o
     * processamento da ação requisitada.
     * 
     * @param updateViews String contendo os identificadores das views.
     */
    public void setUpdateViews(String updateViews){
        this.updateViews = updateViews;
    }

    /**
	 * Indica se as ações devem ser processadas.
	 * 
	 * @return True/False.
	 */
    public Boolean processActions(){
        return processActions;
    }

    /**
     * Indica se as ações devem ser processadas.
     * 
     * @return True/False.
     */
    public Boolean getProcessActions(){
        return processActions();
    }

    /**
     * Define se as ações devem ser processadas.
     * 
     * @param processActions True/False.
     */
    public void setProcessActions(Boolean processActions){
        this.processActions = processActions;
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
	public String getClosedFolderIconStyleClass(){
		return closedFolderIconStyleClass;
	}

	/**
	 * Define o estilo CSS para o ícone do nó fechado.
	 * 
	 * @param closedFolderIconStyleClass String contendo o estilo CSS.
	 */
	public void setClosedFolderIconStyleClass(String closedFolderIconStyleClass){
		this.closedFolderIconStyleClass = closedFolderIconStyleClass;
	}

	/**
	 * Retorna o estilo CSS para o ícone de expansão do nó.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getCloseLeafIconStyleClass(){
		return closeLeafIconStyleClass;
	}

	/**
	 * Define o estilo CSS para o ícone de expansão do nó.
	 * 
	 * @param closeLeafIconStyleClass String contendo o estilo CSS.
	 */
	public void setCloseLeafIconStyleClass(String closeLeafIconStyleClass){
		this.closeLeafIconStyleClass = closeLeafIconStyleClass;
	}

	/**
	 * Retorna o estilo CSS para o ícone do nó expandido.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getOpenedFolderIconStyleClass(){
		return openedFolderIconStyleClass;
	}

	/**
	 * Define o estilo CSS para o ícone do nó expandido.
	 * 
	 * @param openedFolderIconStyleClass String contendo o estilo CSS.
	 */
	public void setOpenedFolderIconStyleClass(String openedFolderIconStyleClass){
		this.openedFolderIconStyleClass = openedFolderIconStyleClass;
	}

	/**
	 * Retorna o estilo CSS para o ícone para esconder o nó.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getOpenLeafIconStyleClass(){
		return openLeafIconStyleClass;
	}

	/**
	 * Define o estilo CSS para o ícone para esconder o nó.
	 * 
	 * @param openLeafIconStyleClass String contendo o estilo CSS.
	 */
	public void setOpenLeafIconStyleClass(String openLeafIconStyleClass){
		this.openLeafIconStyleClass = openLeafIconStyleClass;
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
        setComponentType(ComponentType.TREEVIEW);
        
        if(getLabelPosition() == null)
            setLabelPosition(PositionType.TOP);
        
        if(nodeIconStyleClass.length() == 0)
            nodeIconStyleClass = TaglibConstants.DEFAULT_TREEVIEW_NODE_ICON_STYLE_CLASS;
        
        if(nodeLabelStyleClass.length() == 0)
            nodeLabelStyleClass = TaglibConstants.DEFAULT_TREEVIEW_NODE_LABEL_STYLE_CLASS;

        if(nodeLabelSelectedStyleClass.length() == 0)
            nodeLabelSelectedStyleClass = TaglibConstants.DEFAULT_TREEVIEW_NODE_LABEL_SELECTED_STYLE_CLASS;
        
        if(openLeafIconStyleClass.length() == 0)
            openLeafIconStyleClass = TaglibConstants.DEFAULT_TREEVIEW_OPEN_LEAF_ICON_STYLE_CLASS;

        if(closeLeafIconStyleClass.length() == 0)
            closeLeafIconStyleClass = TaglibConstants.DEFAULT_TREEVIEW_CLOSE_LEAF_ICON_STYLE_CLASS;

        if(openedFolderIconStyleClass.length() == 0)
            openedFolderIconStyleClass = TaglibConstants.DEFAULT_TREEVIEW_OPENED_FOLDER_ICON_STYLE_CLASS;

        if(closedFolderIconStyleClass.length() == 0)
            closedFolderIconStyleClass = TaglibConstants.DEFAULT_TREEVIEW_CLOSED_FOLDER_ICON_STYLE_CLASS;

        super.initialize();
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
        
		println("<table>");
		println("<tr>");

		renderLabel();

		println("</tr>");
		println("<tr>");
		println("<td>");

		print("<div id=\"");
		print(AttributeConstants.TREEVIEW_KEY);
		print("\" class=\"");
		print(TaglibConstants.DEFAULT_TREEVIEW_STYLE_CLASS);
		print("\" style=\"overflow: auto;");
		
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
		print(AttributeConstants.TREEVIEW_CONTENT_KEY);
		print("\" class=\"");
		print(TaglibConstants.DEFAULT_TREEVIEW_CONTENT_STYLE_CLASS);
		println("\">");
		
		PropertyInfo propertyInfo = getPropertyInfo();
		List         dataValues   = getDataValues();

		if(propertyInfo != null && dataValues != null && dataValues.size() > 0)
			renderNodes();
		else{
			print("<span class=\"");
			print(TaglibConstants.DEFAULT_TREEVIEW_NODE_LABEL_STYLE_CLASS);
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
    			selectionTag.setStyle("display: NONE");
    			selectionTag.setData(getData());
    			selectionTag.setDataScope(getDataScope());
    			selectionTag.setShowLabel(false);
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
        PropertyInfo      propertyInfo          = getPropertyInfo();
        String            propertyId            = propertyInfo.getId();
        String            actionForm            = getActionForm();
        String            action                = getActionFormTag().getAction();
        String            target                = StringUtil.trim(getActionFormTag().getTarget());
        String            name                  = getName();
        Object            value                 = getValue();
		Node              node                  = null;
        List<Node>        nodeChilds            = null;             
		StringBuilder     nodeIndex             = null;
		StringBuilder     nodeId                = null;
		StringBuilder     nodeIconId            = null;
		String            nodeIconUrl           = "";
		byte              nodeIconData[]        = null;
		String            nodeIconWidth         = "";
		String            nodeIconHeight        = "";
		String            nodeLabelName         = "";
		Object            nodeValue             = null;
		String            nodeValueLabel        = "";
		StringBuilder     nodeValueBuffer       = null;
		StringBuilder     nodeCollapsedId       = null;
		HiddenPropertyTag nodeCollapsedTag      = null;
		String            nodeIsCollapsedBuffer = "";
		Boolean           nodeIsCollapsed       = false;
		Boolean           nodeIsSelected        = false;
		String            nodeForward           = "";
		String            nodeForwardOnFail     = "";
        String            nodeAction            = "";
		String            nodeActionTarget      = "";
		String            nodeOnExpandAction    = "";
		String            nodeOnSelectAction    = "";
		StringBuilder     parentNodeId          = null;
		String            onSelectContent       = "";
		String            onUnSelectContent     = "";
        Locale            currentLanguage       = systemController.getCurrentLanguage();
        StringBuilder     content               = null;
        StringBuilder     trace                 = null;
        ScriptTag         scriptTag             = null;
		
		for(Integer cont = 0 ; cont < nodes.size() ; cont++){
			node               = nodes.get(cont);
			nodeLabelName      = node.getLabelName();
            onSelectContent    = PropertyUtil.fillPropertiesInString(node, onSelect, currentLanguage);
            onUnSelectContent  = PropertyUtil.fillPropertiesInString(node, onUnSelect, currentLanguage);
            nodeOnExpandAction = node.getOnExpandAction();
            nodeOnExpandAction = PropertyUtil.fillPropertiesInString(node, nodeOnExpandAction, currentLanguage);
            nodeOnSelectAction = node.getOnSelectAction();
            nodeOnSelectAction = PropertyUtil.fillPropertiesInString(node, nodeOnSelectAction, currentLanguage);
            nodeAction         = node.getAction();
            nodeAction         = PropertyUtil.fillPropertiesInString(node, nodeAction, currentLanguage);
            nodeActionTarget   = node.getActionTarget();
            nodeActionTarget   = PropertyUtil.fillPropertiesInString(node, nodeActionTarget, currentLanguage);
            nodeForward        = node.getForward();
            nodeForward        = PropertyUtil.fillPropertiesInString(node, nodeForward, currentLanguage);
            nodeForwardOnFail  = node.getForwardOnFail();
            nodeForwardOnFail  = PropertyUtil.fillPropertiesInString(node, nodeForwardOnFail, currentLanguage);
            nodeIconWidth      = node.getIconWidth();
            nodeIconHeight     = node.getIconHeight();
            nodeIconUrl        = node.getIconUrl();
            nodeIconData       = node.getIconData();
            
			if(nodeIndex == null)
			    nodeIndex = new StringBuilder();
			else
				nodeIndex.delete(0, nodeIndex.length());
			
			if(index.length() > 0){
				nodeIndex.append(index);
				nodeIndex.append("_");
			}
			
			nodeIndex.append(cont);
			
			if(propertyInfo.isModel() || propertyInfo.hasModel()){
			    nodeValue = node;

			    if(nodeValueBuffer == null)
			        nodeValueBuffer = new StringBuilder();
			    else
	                nodeValueBuffer.delete(0, nodeValueBuffer.length());

	            nodeValueBuffer.append("objectId{");
    			nodeValueBuffer.append(nodeIndex);
    			nodeValueBuffer.append("}");
            
    			nodeValueLabel = nodeValueBuffer.toString();
			}
			else{
				nodeValue      = PropertyUtil.getProperty(node, propertyId);
				nodeValueLabel = PropertyUtil.format(nodeValue, getValueMapInstance(), getPattern(), useAdditionalFormatting(), currentLanguage);
			}
			
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
			    trace.append(TaglibConstants.DEFAULT_TREEVIEW_TRACE_STYLE_CLASS);
			    trace.append("\"></td>");

				println(StringUtil.replicate(trace.toString(), level));

				if(!node.hasChildNodes() && nodeOnExpandAction.length() == 0){
					print("<td class=\"");
					print(TaglibConstants.DEFAULT_TREEVIEW_TRACE_STYLE_CLASS);
					println("\"></td>");
					
					print("<td");
					
					if(nodeIconUrl.length() > 0 || nodeIconData != null){
						println(" width=\"1\">");
						print("<img src=\"");
						print(systemController.getContextPath());
						print("/");
						
						if(nodeIconData != null){
						    if(nodeIconId == null)
						        nodeIconId = new StringBuilder();
						    else
								nodeIconId.delete(0, nodeIconId.length());
							
							nodeIconId.append("nodeIcon");
							nodeIconId.append(nodeIndex.toString().hashCode());
							
							systemController.setAttribute(nodeIconId.toString(), nodeIconData, ScopeType.SESSION);
							
							print("contentLoaderServlet?contentData=");
							print(nodeIconId);
							print("&contentType=");
							println(ImageUtil.getImageFormat(nodeIconData));
						}
						else
							print(nodeIconUrl);
							
						print("\"");

						if(nodeIconWidth.length() > 0){
							print(" width=\"");
							print(nodeIconWidth);
							print("\"");
						}

						if(nodeIconHeight.length() > 0){
							print(" height=\"");
							print(nodeIconHeight);
							print("\"");
						}

						println(">");
					}
					else{
						print(" class=\"");
						print(nodeIconStyleClass);
						println("\">");
					}

					println("</td>");
				}
				else{
				    if(nodeCollapsedId == null)
				        nodeCollapsedId = new StringBuilder();
				    else
						nodeCollapsedId.delete(0, nodeCollapsedId.length());
					
					nodeCollapsedId.append(nodeId);
					nodeCollapsedId.append(".");
					nodeCollapsedId.append(AttributeConstants.IS_COLLAPSED_KEY);

					nodeIsCollapsedBuffer = StringUtil.trim(systemController.getRequest().getParameter(nodeCollapsedId.toString()));

					if(nodeIsCollapsedBuffer.length() == 0){
						if(getExpandLevel() <= level)
							nodeIsCollapsed = false;
						else
							nodeIsCollapsed = true;
					}
					else
						nodeIsCollapsed = Boolean.parseBoolean(nodeIsCollapsedBuffer);

					print("<td id=\"");
					print(nodeId);
					print(".");
					print(AttributeConstants.NODE_EXPAND_ICON_KEY);
					print("\" class=\"");

					if(!nodeIsCollapsed)
						print(openLeafIconStyleClass);
					else
						print(closeLeafIconStyleClass);

					print("\" onClick=\"");
					
					if(nodeOnExpandAction.length() > 0){
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
					print(openLeafIconStyleClass);
					print("', '");
					print(closeLeafIconStyleClass);
					print("', '");
					print(openedFolderIconStyleClass);
					print("', '");
					print(closedFolderIconStyleClass);
					print("'");
					
					if(nodeOnExpandAction.length() > 0){
						print(", document.");
						print(actionForm);
						print(", '");
						print(nodeOnExpandAction);
						print("', '");
						print(nodeActionTarget);
						print("'");
					}
					
					println(");\"></td>");
					print("<td");

					if(nodeIconUrl.length() > 0 || nodeIconData != null){
						println(" width=\"1\">");
						print("<img src=\"");
						print(systemController.getContextPath());
						print("/");
						
						if(nodeIconData != null){
						    if(nodeIconId == null)
						        nodeIconId = new StringBuilder();
						    else
								nodeIconId.delete(0, nodeIconId.length());
							
							nodeIconId.append(AttributeConstants.NODE_ICON_KEY);
							nodeIconId.append(nodeIndex.toString().hashCode());
							
							systemController.setAttribute(nodeIconId.toString(), nodeIconData, ScopeType.SESSION);
							
							print("contentLoaderServlet?contentData=");
							print(nodeIconId);
							print("&contentType=");
							print(ImageUtil.getImageFormat(nodeIconData));
						}
						else
							print(nodeIconUrl);
							
						print("\"");

						if(nodeIconWidth.length() > 0){
							print(" width=\"");
							print(nodeIconWidth);
							print("\"");
						}

						if(nodeIconHeight.length() > 0){
							print(" height=\"");
							print(nodeIconHeight);
							print("\"");
						}

						println(">");
					}
					else{
						print(" id=\"");
						print(nodeId);
						print(".");
						print(AttributeConstants.NODE_ICON_KEY);
						print("\" class=\"");

						if(!nodeIsCollapsed)
							print(closedFolderIconStyleClass);
						else
							print(openedFolderIconStyleClass);

						println("\">");
					}

					println("</td>");
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
				print(nodeValueLabel);
				print("\"");

				if(parent != null){
					print(" parent=\"");
					print(parentNodeId);
					print("\"");
				}

				if(isEnabled()){
					print(" onClick=\"refreshNode('");
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

					print(");");
		
 					if(processActions() && (nodeAction.length() > 0 || nodeOnSelectAction.length() > 0)){
 					    if(updateViews.length() > 0){
 	                        print(" document.");
 	                        print(actionForm);
                            print("updateViews.value = '");
                            print(updateViews);
                            print("';");
 					    }
 					    
 						print(" document.");
 						print(actionForm);
 						
 						if(nodeAction.length() > 0){
 							print(".attributes['action'].value = '");
 							print(systemController.getContextPath());
 							print("/");
 							print(nodeAction);
 						}
 						else{
 							print(".action.value = '");
 							print(nodeOnSelectAction);
 						}

 						print("';");
 						
 						if(nodeActionTarget.length() > 0){
 						    print(" document.");
 						    print(actionForm);
 						    print(".target = '");
 						    print(nodeActionTarget);
 						    print("';");
 						}
 						    
                        print(" document.");
                        print(actionForm);
 						print(".forward.value = '");
 						print(nodeForward);
 						print("'; document.");
 						print(actionForm);
 						print(".forwardOnFail.value = '");
 						print(nodeForwardOnFail);
						print("'; submitForm(document.");
 						print(actionForm);
 						print("); document.");
 						print(actionForm);
						print(".attributes['action'].value = '");
						print(TagUtils.getInstance().getActionMappingURL(action, this.pageContext));
                        print("';");
                        
                        if(nodeActionTarget.length() > 0){
                            print(" document.");
                            print(actionForm);
                            print(".target = '");
                            print(target);
                            print(";");
                        }
                        
						print(" document.");
 						print(actionForm);
						print(".forward.value = ''; document.");
 						print(actionForm);
						print(".forwardOnFail.value = '';");
 					}

 					print("\"");
				}

				print(">");
				print("&nbsp;");
				
				if(nodeLabelName.length() > 0)
					print(StringUtil.trim(PropertyUtil.getProperty(node, nodeLabelName)));
				else
					print(node);
				
				println("&nbsp;</td>");
				println("</tr>");
				println("</table>");

				if(!propertyInfo.isCollection()){
					if(nodeIsSelected){
					    content = new StringBuilder();
					    content.append("document.getElementById('");
					    content.append(name);
					    content.append("').value = '");
					    content.append(nodeValueLabel);
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

				if(!nodeIsCollapsed)
					print(" style=\"display: NONE;\"");

				println(">");

				if(node.hasChildNodes() || nodeOnExpandAction.length() > 0){
					nodeCollapsedTag = new HiddenPropertyTag();
					nodeCollapsedTag.setPageContext(pageContext);
					nodeCollapsedTag.setName(nodeCollapsedId.toString());
					nodeCollapsedTag.setValue(nodeIsCollapsed);
					nodeCollapsedTag.doStartTag();
					nodeCollapsedTag.doEndTag();

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
	    setOpenLeafIconStyleClass("");
	    setCloseLeafIconStyleClass("");
	    setOpenedFolderIconStyleClass("");
	    setClosedFolderIconStyleClass("");
	    setOnSelect("");
	    setOnUnSelect("");
	    setExpandLevel(0);
	    setUpdateViews("");
    }
}