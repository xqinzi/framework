/**
 * Arquivo que contém as funções/propriedades para manipulação do componente visual 
 * treeView (árvore).
 * 
 * @author fvilarinho
 * @version 1.0
 */

/**
 * Esconde/Exibe o contéudo de um nó.
 * 
 * @param nodeId String contendo o identificador do nó.
 * @param openLeafIconClass String contendo o identificador do estilo de expansão do nó.
 * @param closeLeafIconClass String contendo o identificador do estilo de retração do nó.
 * @param openedFolderIconClass String contendo o identificador do estilo de um nó expandido.
 * @param closedFolderIconClass String contendo o identificador do estilo de um nó não 
 * expandido.
 * @param form Instância contendo as propriedades do formulário.
 * @param onExpandAction String contendo o identificador da ação a ser executada ao expandir 
 * um nó.
 * @param actionTarget String contendo o identificador do destino da ação a ser executada.
 */
function showHideNode(nodeId, openLeafIconClass, closeLeafIconClass, openedFolderIconClass, closedFolderIconClass, form, onExpandAction, actionTarget){
	var node       = document.getElementById(nodeId);
	var submitFlag = false;
	
	if(node){
		if(node.style.display == "none"){
			node.style.display = "";
			
			if(form && onExpandAction)
				submitFlag = true;
		}
		else
			node.style.display = "none";
	}
	
	var nodeLeaf = document.getElementById(nodeId + ".leafIcon");
	
	if(nodeLeaf){ 
		if(nodeLeaf.className == closeLeafIconClass)
			nodeLeaf.className = openLeafIconClass;
		else
			nodeLeaf.className = closeLeafIconClass;
	} 
 
	var nodeFolder = document.getElementById(nodeId + ".folderIcon");
	
	if(nodeFolder){
		if(nodeFolder.className == closedFolderIconClass)
			nodeFolder.className = openedFolderIconClass;
		else
			nodeFolder.className = closedFolderIconClass;
	}
	
	var nodeCollapsed = document.getElementById(nodeId + ".isCollapsed");
	
	if(nodeCollapsed){
		if(nodeCollapsed.value == "false")
			nodeCollapsed.value = "true";
		else
			nodeCollapsed.value = "false";
	}
	
	if(submitFlag){
		form.action.value = onExpandAction;
		form.target       = actionTarget;
			
		submitForm(form);
	}
}

/**
 * Atualiza um nó.
 * 
 * @param name String contendo o identificador do componente.
 * @param nodeId String contendo o identificador do nó.
 * @param nodeLabelClass String contendo o identificador do label do nó.
 * @param nodeLabelSelectedClass String contendo o identificador do label do nó, quando 
 * selecionado.
 * @param onSelect Função a ser executada no momento da seleção do nó.
 * @param onUnSelect Função a ser executada no momento da deseleção do nó.
 */
function refreshNode(name, nodeId, nodeLabelClass, nodeLabelSelectedClass, onSelect, onUnSelect){
	var nodeLabel = document.getElementById(nodeId + ".label");
	
	if(nodeLabel){	
		if(nodeLabel.className == nodeLabelClass)
			selectNode(name, nodeId, nodeLabelClass, nodeLabelSelectedClass, onSelect);
		else
			unselectNode(name, nodeId, nodeLabelClass, onUnSelect);
	}
}

/**
 * Deseleciona um nó.
 * 
 * @param name String contendo o identificador do componente.
 * @param nodeId String contendo o identificador do nó.
 * @param nodeLabelClass String contendo o identificador do label do nó.
 * @param onUnSelect Função a ser executada no momento da deseleção do nó.
 */
function unselectNode(name, nodeId, nodeLabelClass, onUnSelect){
	var nodeLabel            = document.getElementById(nodeId + ".label");
	var hasMultipleSelection = document.getElementById(name + ".hasMultipleSelection");
	var currentNode          = document.getElementById(name + ".currentNode");
		
	if(hasMultipleSelection && nodeLabel){
		nodeLabel.className = nodeLabelClass;
		
		var nodeValue = nodeLabel.getAttribute("value");
		
		if(hasMultipleSelection.value == "true"){
			var options = document.getElementById(name).options;
			var cont    = 0;
			
			for(cont = 0 ; cont < options.length ; cont++)
				if(options[cont].value == nodeValue)
					options[cont].selected = false;
			
			var parentNodeLabelId = nodeLabel.getAttribute("parent");
			var node              = null;
			
			if(parentNodeLabelId){
				node = document.getElementById(parentNodeLabelId);
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
								child = document.getElementById(childId + ".label");

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
			
			node = document.getElementById(nodeId);
			if(node){
				var childs  = node.childNodes;
				var child   = null;
				var childId = null;
				
				for(cont = 0 ; cont < childs.length ; cont++){
					child   = childs[cont];
					childId = child.id;
					
					if(childId){
						if(childId.indexOf("node") >= 0){
							child = document.getElementById(childId + ".label");

							if(child)
								if(nodeLabel.className != child.className)
									unselectNode(name, childId, nodeLabelClass);
						}
					}
				}
			}
		}
		else{
			currentNode.value = "";	

			var option = document.getElementById(name);
			
			if(option)
				option.value = "";
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
 * @param onSelect Função a ser executada no momento da seleção do nó.
 * @param donSelectChilds Indica se ao selecionar o nó, os seus filhos também devem ser 
 * selecionados.
 */
function selectNode(name, nodeId, nodeLabelClass, nodeLabelSelectedClass, onSelect, dontSelectChilds){
	var nodeLabel            = document.getElementById(nodeId + ".label");
	var hasMultipleSelection = document.getElementById(name + ".hasMultipleSelection");
	var currentNode          = document.getElementById(name + ".currentNode");
	
	if(currentNode && hasMultipleSelection && nodeLabel){
		nodeLabel.className = nodeLabelSelectedClass;
		
		var nodeValue = nodeLabel.getAttribute("value");
		
		if(hasMultipleSelection.value == "true"){
			var options = document.getElementById(name).options;
			var cont    = 0;
			
			for(cont = 0 ; cont < options.length ; cont++)
				if(options[cont].value == nodeValue)
					options[cont].selected = true;
			
			var parentNodeLabelId = nodeLabel.getAttribute("parent");
			
			if(parentNodeLabelId)
				selectNode(name, parentNodeLabelId, nodeLabelClass, nodeLabelSelectedClass, null, true);
			
			if(!dontSelectChilds){
				var node = document.getElementById(nodeId);
				
				if(node){
					var childs  = node.childNodes;
					var child   = null;
					var childId = null;
					
					for(cont = 0 ; cont < childs.length ; cont++){
						child   = childs[cont];
						childId = child.id;
						
						if(childId){
							if(childId.indexOf("node") >= 0){
								child = document.getElementById(childId + ".label");
	
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
			var nodeLabel = document.getElementById(currentNode.value + ".label");
			
			if(nodeLabel)
				if(currentNode.value != nodeId)
					nodeLabel.className = nodeLabelClass;
	
			currentNode.value = nodeId;	

			var option = document.getElementById(name);
			
			if(option)
				option.value = nodeValue;
		}

		if(onSelect)
			onSelect();
	}
}