function showDialogBox(name, modal){
	var dialogBoxObject = document.getElementById(name + ".dialogBox");
	var actionFormShade = document.getElementById("shade");
	var windowWidth     = 0;
	var windowHeight    = 0;
	
	if(dialogBoxObject){
		dialogBoxObject.style.visibility = "visible";
		
		if(modal && actionFormShade){
			windowWidth  = (isIe() ? document.body.scrollWidth : window.innerWidth);
			windowHeight = (isIe() ? document.body.scrollHeight : window.innerHeight);

			actionFormShade.style.width      = windowWidth;
			actionFormShade.style.height     = windowHeight;
			actionFormShade.style.visibility = "visible";
		}
		
		centralizeObject(dialogBoxObject);
	}
}

function hideDialogBox(name){
	var dialogBoxObject = document.getElementById(name + ".dialogBox");
	var actionFormShade = document.getElementById("shade");
	
	if(dialogBoxObject){
		dialogBoxObject.style.visibility = "hidden";
		
		if(actionFormShade)
			actionFormShade.style.visibility = "hidden";
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
