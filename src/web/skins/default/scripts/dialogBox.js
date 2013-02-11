/**
 * Arquivo que contém as funções/propriedades comuns utilizadas pelos componentes de caixa de diálogo. 
 * visuais.
 * 
 * @author fvilarinho
 * @version 3.0
 */

/**
 * Exibe uma caixa de diálogo.
 * 
 * @param name String contendo o identificador da caixa de diálogo.
 * @param modal Indica se a caixa deve ser carregada no modo modal.
 */
function showDialogBox(name, modal){
	var dialogBoxObject = document.getElementById(name + ".dialogBox");
	var actionFormShade = document.getElementById("shade");
	var windowWidth     = 0;
	var windowHeight    = 0;
	
	if(dialogBoxObject){
		dialogBoxObject.style.visibility = "VISIBLE";
		
		if(modal && actionFormShade){
			windowWidth  = (isIe() ? document.body.scrollWidth : window.innerWidth);
			windowHeight = (isIe() ? document.body.scrollHeight : window.innerHeight);

			actionFormShade.style.width      = windowWidth;
			actionFormShade.style.height     = windowHeight;
			actionFormShade.style.visibility = "VISIBLE";
		}
		
		centralizeObject(dialogBoxObject);
	}
}

/**
 * Esconde uma caixa de diálogo.
 * 
 * @param name String contendo o identificador da caixa de diálogo.
 */
function hideDialogBox(name){
	var dialogBoxObject = document.getElementById(name + ".dialogBox");
	var actionFormShade = document.getElementById("shade");
	
	if(dialogBoxObject){
		dialogBoxObject.style.visibility = "HIDDEN";
		
		if(actionFormShade)
			actionFormShade.style.visibility = "HIDDEN";
	}
}

/**
 * Centraliza todas as caixas de diálogo da página.
 */
function centralizeDialogBoxes(){
	var actionFormShade = document.getElementById("shade");
	var dialogBoxes     = document.getElementsByTagName("div"); 
    var cont            = 0;
    var dialogBox       = null;
    var dialogBoxId     = "";
    var windowWidth     = (isIe() ? document.body.offsetWidth - 21: window.innerWidth);
    var windowHeight    = (isIe() ? document.body.offsetHeight - 4 : window.innerHeight);
    
	if(actionFormShade){
		actionFormShade.style.width  = windowWidth;
		actionFormShade.style.height = windowHeight;
	}

	for(cont = 0 ; cont < dialogBoxes.length ; cont++){
    	dialogBox   = dialogBoxes[cont];
    	dialogBoxId = dialogBox.id;
			
       	if(dialogBoxId.indexOf("dialogBox") >= 0)
	       	centralizeObject(dialogBox);
    }
}
