/**
 * Arquivo que contém as funções/propriedades comuns utilizadas pelo componente visual menuBar (barra de menus).
 * 
 * @author fvilarinho
 * @version 1.0
 */
var currentMenu = null;

/**
 * Encontra qual é o item de menu pai a partir de um item de menu específico.
 * 
 * @param menu Instância contendo as propriedades do item de menu desejado.
 * @returns Instância contendo as propriedades do item de menu pai.
 */
function findAncestorMenu(menu){
	var parentMenu   = menu;
    var parentMenuId = "";
    
    while(true){
    	parentMenu = parentMenu.parentNode;
        
       	if(!parentMenu)
       	  	break;
            
       	parentMenuId = parentMenu.id;
        
       	if(parentMenuId)
       		if(parentMenuId.indexOf(".menuBox") >= 0 || parentMenuId.indexOf(".menuBar") >= 0)
           		break;
    }
    
    return parentMenu;
}

/**
 * Seleciona um item de menu.
 * 
 * @param menuItem Instância contendo as propriedades do item de menu.
 * @param menuItemClass String contendo o identificador do estilo CSS de seleção.
 */
function selectMenuItem(menuItem, menuItemClass){
	if(menuItem){
		var menuItemId    = replaceAll(menuItem.id, ".menuItem", "");
		var currentMenuId = "";
       	var parentMenu    = findAncestorMenu(menuItem);
       	var parentMenuId  = parentMenu.id;
       	var menu          = document.getElementById(menuItemId + ".menuBox");

       	if(parentMenuId.indexOf(".menuBar") < 0)
	     	changeStyle(menuItem.parentNode, menuItemClass);

    	changeStyle(menuItem, menuItemClass);
	    
       	if(currentMenu){
       		currentMenuId = currentMenu.id;
        	
           	if(currentMenuId != parentMenuId){
               	var bufferMenu   = currentMenu;
               	var bufferMenuId = "";

               	while(true){
                   	if(bufferMenu){
    	               	bufferMenuId = bufferMenu.id;
                        
                   		if(bufferMenuId == parentMenuId)
                       		break;

                   		bufferMenu.style.visibility = "HIDDEN";
                   	}
                   	else
                   		break;

                   	bufferMenu = findAncestorMenu(bufferMenu);
               	}
           	}
        }
	
        if(menu){
          	if(parentMenuId.indexOf(".menuBar") >= 0){
        	   	menu.style.left = parseInt(menuItem.offsetLeft);
               	menu.style.top  = parseInt(parentMenu.offsetHeight) + 1;
            }
            else{
               	menu.style.left = parseInt(menuItem.parentNode.offsetWidth) + 4;
               	menu.style.top  = parseInt(menuItem.offsetTop);
            }

            menu.style.visibility = "VISIBLE";
        
            currentMenu = menu;
        }
	}
}

/**
 * Deseleciona um item de menu.
 * 
 * @param menuItem Instância contendo as propriedades do item de menu.
 * @param menuItemClass String contendo o identificador do estilo CSS de deseleção.
 */
function unselectMenuItem(menuItem, menuItemClass){
	if(menuItem){
		var parentMenu   = findAncestorMenu(menuItem);
       	var parentMenuId = parentMenu.id;
        
   		changeStyle(menuItem.parentNode, menuItemClass);
       	changeStyle(menuItem, menuItemClass);
    }
}

/**
 * Esconde todos os menus abertos.
 */
function hideAllMenus(){
    if(currentMenu){
     	var menu   = currentMenu;
       	var menuId = "";

       	while(true){
          	if(menu){
           		menuId = replaceAll(menu.id, ".menuBox", "");
            	
               	if(menuId.indexOf(".menuBar") >= 0)
                   	break;

               	menu.style.visibility = "HIDDEN";
           	}
           	else
               	break;

           	menu = findAncestorMenu(menu);
       	}
    }
}

/**
 * Esconde todos os menus pais abertos.
 */
function hideAllParentMenus(){
	try{
		if(parent)
			parent.hideAllMenus();
	}
	catch(e){
	}
}

/**
 * Mantém o componente com posição fixa.
 * 
 * @param name String contendo o identificador do componente.
 */
function renderFixedMenu(name){
	var object = document.getElementById(name + ".menuBar");
	
	if(object)
		object.style.top = document.body.scrollTop + "px";
}