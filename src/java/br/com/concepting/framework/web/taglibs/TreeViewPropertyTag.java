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
 * Classe que define o componente visual Treeview (�rvore de visualiza��o).
 * 
 * @author fvilarinho
 * @since 2.0
 */
public class TreeViewPropertyTag extends BaseOptionsPropertyTag{
	private String  nodeIconStyleClass          = "";
	private String  nodeLabelStyleClass         = "";
	private String  nodeLabelSelectedStyleClass = "";
    private String  expandedNodeIconStyleClass  = "";
	private String  collapsedNodeIconStyleClass = "";
	private String  openedNodeIconStyleClass    = "";
	private String  closedNodeIconStyleClass    = "";
	private String  onSelect                    = "";
	private String  onUnSelect                  = "";
	private Integer expandLevel                 = 0;
	private Boolean processActions              = false;
	private String  updateViews                 = "";

    /**
     * Retorna os identificadores das views (separados por v�rgula) a serem atualizadas ap�s o
     * processamento da a��o requisitada.
     * 
     * @return String contendo os identificadores das views.
     */
    public String getUpdateViews(){
        return updateViews;
    }

    /**
     * Define os identificadores das views (separados por v�rgula) a serem atualizadas ap�s o
     * processamento da a��o requisitada.
     * 
     * @param updateViews String contendo os identificadores das views.
     */
    public void setUpdateViews(String updateViews){
        this.updateViews = updateViews;
    }

    /**
	 * Indica se as a��es devem ser processadas.
	 * 
	 * @return True/False.
	 */
    public Boolean processActions(){
        return processActions;
    }

    /**
     * Indica se as a��es devem ser processadas.
     * 
     * @return True/False.
     */
    public Boolean getProcessActions(){
        return processActions();
    }

    /**
     * Define se as a��es devem ser processadas.
     * 
     * @param processActions True/False.
     */
    public void setProcessActions(Boolean processActions){
        this.processActions = processActions;
    }

    /**
	 * Retorna o evento a ser executado quando um n� do treeView for deselecionado.
	 * 
	 * @return String contendo evento a ser executado.
	 */
	public String getOnUnSelect(){
		return onUnSelect;
	}

	/**
	 * Define o evento a ser executado quando um n� do treeView for deselecionado.
	 * 
	 * @param onUnSelect String contendo evento a ser executado.
	 */
	public void setOnUnSelect(String onUnSelect){
		this.onUnSelect = onUnSelect;
	}

	/**
	 * Retorna o identificador do n�vel m�ximo que se deve expandir os n�s.
	 * 
	 * @return Valor inteiro contendo o identificador do n�vel.
	 */
	public Integer getExpandLevel(){
		return expandLevel;
	}

	/**
	 * Define o identificador do n�vel m�ximo que se deve expandir os n�s.
	 * 
	 * @param expandLevel Valor inteiro contendo o identificador do n�vel.
	 */
	public void setExpandLevel(Integer expandLevel){
		this.expandLevel = expandLevel;
	}

	/**
	 * Retorna o evento a ser executado quando um n� do treeView for selecionado.
	 * 
	 * @return String contendo evento a ser executado.
	 */
	public String getOnSelect(){
		return onSelect;
	}

	/**
	 * Define o evento a ser executado quando um n� do treeView for selecionado.
	 * 
	 * @param onSelect String contendo evento a ser executado.
	 */
	public void setOnSelect(String onSelect){
		this.onSelect = onSelect;
	}

	/**
	 * Retorna o estilo CSS para o �cone do n� fechado.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getClosedNodeIconStyleClass(){
		return closedNodeIconStyleClass;
	}

	/**
	 * Define o estilo CSS para o �cone do n� fechado.
	 * 
	 * @param closedNodeIconStyleClass String contendo o estilo CSS.
	 */
	public void setClosedNodeIconStyleClass(String closedNodeIconStyleClass){
		this.closedNodeIconStyleClass = closedNodeIconStyleClass;
	}

	/**
	 * Retorna o estilo CSS para o �cone de expans�o do n�.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getCollapsedNodeIconStyleClass(){
		return collapsedNodeIconStyleClass;
	}

	/**
	 * Define o estilo CSS para o �cone de expans�o do n�.
	 * 
	 * @param collapsedNodeIconStyleClass String contendo o estilo CSS.
	 */
	public void setCollapsedNodeIconStyleClass(String collapsedNodeIconStyleClass){
		this.collapsedNodeIconStyleClass = collapsedNodeIconStyleClass;
	}

	/**
	 * Retorna o estilo CSS para o �cone do n� expandido.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getOpenedNodeIconStyleClass(){
		return openedNodeIconStyleClass;
	}

	/**
	 * Define o estilo CSS para o �cone do n� expandido.
	 * 
	 * @param openedNodeIconStyleClass String contendo o estilo CSS.
	 */
	public void setOpenedNodeIconStyleClass(String openedNodeIconStyleClass){
		this.openedNodeIconStyleClass = openedNodeIconStyleClass;
	}

	/**
	 * Retorna o estilo CSS para o �cone para retrair o n�.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getExpandedNodeIconStyleClass(){
		return expandedNodeIconStyleClass;
	}

	/**
	 * Define o estilo CSS para o �cone para retrair o n�.
	 * 
	 * @param expandedNodeIconStyleClass String contendo o estilo CSS.
	 */
	public void setExpandedNodeIconStyleClass(String expandedNodeIconStyleClass){
		this.expandedNodeIconStyleClass = expandedNodeIconStyleClass;
	}

	/**
	 * Retorna o estilo CSS para o �cone do n�.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getNodeIconStyleClass(){
		return nodeIconStyleClass;
	}

	/**
	 * Define o estilo CSS para o �cone do n�.
	 * 
	 * @param nodeIconStyleClass String contendo o estilo CSS.
	 */
	public void setNodeIconStyleClass(String nodeIconStyleClass){
		this.nodeIconStyleClass = nodeIconStyleClass;
	}

	/**
	 * Retorna o estilo CSS para o label do n� selecionado.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getNodeLabelSelectedStyleClass(){
		return nodeLabelSelectedStyleClass;
	}

	/**
	 * Define o estilo CSS para o label do n� selecionado.
	 * 
	 * @param nodeLabelSelectedStyleClass String contendo o estilo CSS.
	 */
	public void setNodeLabelSelectedStyleClass(String nodeLabelSelectedStyleClass){
		this.nodeLabelSelectedStyleClass = nodeLabelSelectedStyleClass;
	}

	/**
	 * Retorna o estilo CSS para o label do n�.
	 * 
	 * @return String contendo o estilo CSS.
	 */
	public String getNodeLabelStyleClass(){
		return nodeLabelStyleClass;
	}

	/**
	 * Define o estilo CSS para o label do n�.
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
        
        if(getLabelPosition() == null)
            setLabelPosition(PositionType.TOP);
        
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
	 * Renderiza a �rvore contendo os n�s.
	 *
	 * @throws Throwable
	 */
    private void renderNodes() throws Throwable{
		List dataValues = getDataValues();
		
		if(dataValues != null && dataValues.size() > 0)
		    renderNodes(dataValues, null, "", 0);
	}

	/**
	 * Renderiza a �rvore contendo os n�s.
	 * 
	 * @param nodes Lista contendo as informa��es dos n�s.
	 * @param index String contendo o prefixo da posi��o atual do n�.
	 * @param parent Inst�ncia do n� pai.
	 * @param level Valor inteiro contendo o n�mero de tabula��es.
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
		StringBuilder     nodeExpandedId        = null;
		HiddenPropertyTag nodeExpandedTag       = null;
		String            nodeIsExpandedBuffer  = "";
		Boolean           nodeIsExpanded        = false;
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
				nodeValueLabel = PropertyUtil.format(nodeValue, getValueMapInstance(), getPattern(), useAdditionalFormatting(), getPrecision(), currentLanguage);
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
			    trace.append(TaglibConstants.DEFAULT_TRACE_STYLE_CLASS);
			    trace.append("\"></td>");

				println(StringUtil.replicate(trace.toString(), level));

				if(!node.hasChildNodes() && nodeOnExpandAction.length() == 0){
					print("<td class=\"");
					print(TaglibConstants.DEFAULT_TRACE_STYLE_CLASS);
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
							
							nodeIconId.append(AttributeConstants.NODE_ICON_KEY);
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
					print(expandedNodeIconStyleClass);
					print("', '");
					print(collapsedNodeIconStyleClass);
					print("', '");
					print(openedNodeIconStyleClass);
					print("', '");
					print(closedNodeIconStyleClass);
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

						if(!nodeIsExpanded)
							print(closedNodeIconStyleClass);
						else
							print(openedNodeIconStyleClass);

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
 							print(".attributes['");
 							print(AttributeConstants.ACTION_KEY);
 							print("'].value = '");
 							print(systemController.getContextPath());
 							print("/");
 							print(nodeAction);
 						}
 						else{
 							print(".");
 							print(AttributeConstants.ACTION_KEY);
 							print(".value = '");
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
 						print(".");
 						print(AttributeConstants.FORWARD_KEY);
 						print(".value = '");
 						print(nodeForward);
 						print("'; document.");
 						print(actionForm);
 						print(".");
 						print(AttributeConstants.FORWARD_ON_FAIL_KEY);
 						print(".value = '");
 						print(nodeForwardOnFail);
						print("'; submitForm(document.");
 						print(actionForm);
 						print("); document.");
 						print(actionForm);
						print(".attributes['");
						print(AttributeConstants.ACTION_KEY);
						print("'].value = '");
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
						print(".");
						print(AttributeConstants.FORWARD_KEY);
						print(".value = ''; document.");
 						print(actionForm);
						print(".");
						print(AttributeConstants.FORWARD_ON_FAIL_KEY);
						print(".value = '';");
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

				if(!nodeIsExpanded)
					print(" style=\"display: NONE;\"");

				println(">");

				if(node.hasChildNodes() || nodeOnExpandAction.length() > 0){
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
	    setExpandedNodeIconStyleClass("");
	    setCollapsedNodeIconStyleClass("");
	    setOpenedNodeIconStyleClass("");
	    setClosedNodeIconStyleClass("");
	    setOnSelect("");
	    setOnUnSelect("");
	    setExpandLevel(0);
	    setUpdateViews("");
    }
}