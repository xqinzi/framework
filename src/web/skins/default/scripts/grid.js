/**
 * Arquivo que contém as funções/propriedades para manipulação do componente visual grid.
 * 
 * @author fvilarinho
 * @version 1.0
 */

/**
 * Seleciona/Deseleciona todas as linhas do componente.
 * 
 * @param name String contendo o identificador do componente.
 * @param startIndex Valor inteiro contendo o índice da primeira linha.
 * @param endIndex Valor inteiro contendo o índice da última linha.
 */
function selectDeselectAllGridRows(name, startIndex, endIndex){
	var cont        = 0;
	var object      = null;
	var selectState = false;
	
	for(cont = startIndex ; cont <= endIndex ; cont++){
		object = document.getElementById(name + "" + cont);
		
		if(object){
			if(!object.checked){
				selectState = true;
				
				break;
			}
		}
	}

	for(cont = startIndex ; cont <= endIndex ; cont++){
		object = document.getElementById(name + "" + cont);
		
		if(object)
			if(object.checked != selectState)
				object.checked = selectState;
	}
}