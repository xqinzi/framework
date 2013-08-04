/**
 * Arquivo que contém as funções/propriedades para manipulação do componente visual treeView (árvore).
 * 
 * @author fvilarinho
 * @version 1.0
 */
 
/**
 * Esconde/Exibe o contéudo de um nó.
 * 
 * @param nodeId String contendo o identificador do nó.
 * @param expandedNodeIconClass String contendo o identificador do estilo de retração do nó.
 * @param collapsedNodeIconClass String contendo o identificador do estilo de expansão do nó.
 * @param openedNodeIconClass String contendo o identificador do estilo de um nó expandido.
 * @param closedNodeIconClass String contendo o identificador do estilo de um nó não 
 * expandido.
 * @param onExpand String contendo o identificador da ação a ser executada ao expandir 
 * um nó.
 */
function showHideNode(nodeId, expandedNodeIconClass, collapsedNodeIconClass, openedNodeIconClass, closedNodeIconClass, onExpand){
	var node       = getObject(nodeId);
	var submitFlag = false;
	
	if(node){
		if(node.style.display.toUpperCase() == "NONE")
			node.style.display = "";
		else
			node.style.display = "NONE";
	}
	
	var nodeExpandIcon = getObject(nodeId + ".nodeExpandIcon");
	
	if(nodeExpandIcon){ 
		if(nodeExpandIcon.className == collapsedNodeIconClass)
			nodeExpandIcon.className = expandedNodeIconClass;
		else
			nodeExpandIcon.className = collapsedNodeIconClass;
	} 
 
	var nodeIcon = getObject(nodeId + ".nodeIcon");
	
	if(nodeIcon){
		if(nodeIcon.className == closedNodeIconClass)
			nodeIcon.className = openedNodeIconClass;
		else
			nodeIcon.className = closedNodeIconClass;
	}
	
	var nodeExpanded = getObjectValue(nodeId + ".isNodeExpanded");
	
	if(nodeExpanded == "false")
		setObjectValue(nodeId + ".isNodeExpanded", true);
	else
		setObjectValue(nodeId + ".isNodeExpanded", false);
	
	if(onExpand)
		onExpand();
}

/**
 * Seleciona/deseleciona um nó.
 * 
 * @param name String contendo o identificador do componente.
 * @param nodeId String contendo o identificador do nó.
 * @param nodeLabelClass String contendo o identificador do label do nó.
 * @param nodeLabelSelectedClass String contendo o identificador do label do nó, quando 
 * selecionado.
 * @param hasMultipleSelection Indica se a seleção deve ser múltipla.
 * @param onSelect Função a ser executada no momento da seleção do nó.
 * @param onUnSelect Função a ser executada no momento da deseleção do nó.
 */
function selectUnSelectNode(name, nodeId, nodeLabelClass, nodeLabelSelectedClass, hasMultipleSelection, onSelect, onUnSelect){
	if(onSelect || onUnSelect){
		var pos        = name.indexOf(".");
		var parentName = name;
		var complement = ""
		
		if(pos >= 0){
			parentName = parentName.substring(0, pos);
			complement = parentName.substring(pos);
		}
		
		var object = null;
		
		if(complement.length > 0){
			object = getObject(parentName + ".parent" + complement);
	
			if(object)
				object.disabled = true;
		}
	
		object = getObject(parentName + ".parent");
		
		if(object)
			object.disabled = true;
	}
	
	var nodeLabel = getObject(nodeId + ".label");
	
	if(nodeLabel){	
		if(nodeLabel.className == nodeLabelClass)
			selectNode(name, nodeId, nodeLabelClass, nodeLabelSelectedClass, hasMultipleSelection, onSelect);
		else
			unselectNode(name, nodeId, nodeLabelClass, hasMultipleSelection, onUnSelect);
	}
}

/**
 * Deseleciona um nó.
 * 
 * @param name String contendo o identificador do componente.
 * @param nodeId String contendo o identificador do nó.
 * @param nodeLabelClass String contendo o identificador do label do nó.
 * @param hasMultipleSelection Indica se a seleção deve ser múltipla.
 * @param onUnSelect Função a ser executada no momento da deseleção do nó.
 */
function unselectNode(name, nodeId, nodeLabelClass, hasMultipleSelection, onUnSelect){
	var nodeLabel = getObject(nodeId + ".label");
		
	if(nodeLabel){
		nodeLabel.className = nodeLabelClass;
		
		var nodeValue = nodeLabel.getAttribute("value");
		
		if(hasMultipleSelection){
			var options = getObject(name).options;
			var cont    = 0;
			
			for(cont = 0 ; cont < options.length ; cont++)
				if(options[cont].value == nodeValue)
					options[cont].selected = false;
			
			var parentNodeLabelId = nodeLabel.getAttribute("parent");
			var node              = null;
			
			if(parentNodeLabelId){
				node = getObject(parentNodeLabelId);
				
				if(node){
					var childs         = node.childNodes;
					var child          = null;
					var childId        = null;
					var unselectParent = true;
					
					for(cont = 0 ; cont < childs.length ; cont++){
						child   = childs[cont];
						childId = child.id;
						
						if(childId){
							if(childId.indexOf("node") >= 0){
								child = getObject(childId + ".label");

								if(child){
									if(nodeLabel.className != child.className){
										unselectParent = false;
										
										break;
									}
								}
							}
						}
					}
					
					if(unselectParent)
						unselectNode(name, parentNodeLabelId, nodeLabelClass)
				}
			}
			
			node = getObject(nodeId);
			
			if(node){
				var childs  = node.childNodes;
				var child   = null;
				var childId = null;
				
				for(cont = 0 ; cont < childs.length ; cont++){
					child   = childs[cont];
					childId = child.id;
					
					if(childId){
						if(childId.indexOf("node") >= 0){
							child = getObject(childId + ".label");

							if(child)
								if(nodeLabel.className != child.className)
									unselectNode(name, childId, nodeLabelClass);
						}
					}
				}
			}
		}
		else{
			setObjectValue(name + ".currentNode", "");
			setObjectValue(name, "");
		}
	}

	if(onUnSelect)
		onUnSelect();
}

/**
 * Deseleciona um nó.
 * 
 * @param name String contendo o identificador do componente.
 * @param nodeId String contendo o identificador do nó.
 * @param nodeLabelClass String contendo o identificador do label do nó.
 * @param nodeLabelSelectedClass String contendo o identificador do label do nó, quando 
 * selecionado.
 * @param hasMultipleSelection Indica se a seleção deve ser múltipla.
 * @param onSelect Função a ser executada no momento da seleção do nó.
 * @param donSelectChilds Indica se ao selecionar o nó, os seus filhos também devem ser 
 * selecionados.
 */
function selectNode(name, nodeId, nodeLabelClass, nodeLabelSelectedClass, hasMultipleSelection, onSelect, dontSelectChilds){
	var nodeLabel   = getObject(nodeId + ".label");
	
	if(currentNode && nodeLabel){
		nodeLabel.className = nodeLabelSelectedClass;
		
		var nodeValue = nodeLabel.getAttribute("value");
		
		if(hasMultipleSelection){
			var options = getObject(name).options;
			var cont    = 0;
			
			for(cont = 0 ; cont < options.length ; cont++)
				if(options[cont].value == nodeValue)
					options[cont].selected = true;
			
			var parentNodeLabelId = nodeLabel.getAttribute("parent");
			
			if(parentNodeLabelId)
				selectNode(name, parentNodeLabelId, nodeLabelClass, nodeLabelSelectedClass, null, true);
			
			if(!dontSelectChilds){
				var node = getObject(nodeId);
				
				if(node){
					var childs  = node.childNodes;
					var child   = null;
					var childId = null;
					
					for(cont = 0 ; cont < childs.length ; cont++){
						child   = childs[cont];
						childId = child.id;
						
						if(childId){
							if(childId.indexOf("node") >= 0){
								child = getObject(childId + ".label");
	
								if(child)
									if(nodeLabel.className != child.className)
										selectNode(name, childId, nodeLabelClass, nodeLabelSelectedClass, null);
							}
						}
					}
				}
			}
		}
		else{
			var currentNode = getObjectValue(name + ".currentNode");
			var nodeLabel   = getObject(currentNode + ".label");
			
			if(nodeLabel)
				if(currentNode != nodeId)
					nodeLabel.className = nodeLabelClass;
	
			setObjectValue(name + ".currentNode", nodeId);
			setObjectValue(name, nodeValue);
		}

		if(onSelect)
			onSelect();
	}
}