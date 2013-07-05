/**
 * Arquivo que contém as funções/propriedades comuns utilizadas pelo componente visual messageBox (caixa de mensagens).
 * 
 * @author fvilarinho
 * @version 1.0
 */

/**
 * Exibe/Esconde o detalhamento do erro.
 * 
 * @param name Instância do objeto que define a caixa de diálogo.
 */
function showHideErrorTraceDetails(name){
	var messageBox           = getObject(name + ".dialogBox");
	var messageBoxErrorTrace = getObject(name + ".errorTrace");

	if(messageBox && messageBoxErrorTrace){
		if(messageBoxErrorTrace.style.display.toUpperCase() == "NONE")
			messageBoxErrorTrace.style.display = "";
		else
			messageBoxErrorTrace.style.display = "NONE";
		
		centralizeObject(messageBox);
	}
}
