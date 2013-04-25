/**
 * Arquivo que cont�m as fun��es/propriedades comuns utilizadas pelo componente visual messageBox (caixa de mensagens).
 * 
 * @author fvilarinho
 * @version 1.0
 */

/**
 * Exibe/Esconde o detalhamento do erro.
 * 
 * @param name Inst�ncia do objeto que define a caixa de di�logo.
 */
function showHideErrorTraceDetails(name){
	var messageBox           = document.getElementById(name + ".dialogBox");
	var messageBoxErrorTrace = document.getElementById(name + ".errorTrace");

	if(messageBox && messageBoxErrorTrace){
		if(messageBoxErrorTrace.style.display.toUpperCase() == "NONE")
			messageBoxErrorTrace.style.display = "";
		else
			messageBoxErrorTrace.style.display = "NONE";
		
		centralizeObject(messageBox);
	}
}
