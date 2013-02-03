/**
 * Arquivo que cont�m as fun��es/propriedades para manipula��o do componente visual 
 * pager (paginador).
 * 
 * @author fvilarinho
 * @version 1.0
 */

/**
 * Executa uma a��o de pagina��o.
 * 
 * @param name String contendo o identificador do componente.
 * @param action String contendo a a��o desejada.
 * @param updateViews
 * @param form String contendo o identificador do formul�rio.
 */
function pagerAction(name, action, updateViews, form){
	if(!form)
		return;

	var object = document.getElementById(name + ".pagerAction");
	
	if(object)
		object.value = action;
	
	object = document.forms[form].elements["updateViews"];
	
	if(object)
		object.value = updateViews;

	if(form)	
		submitForm(document.forms[form]);
}

/**
 * Atualiza a p�gina atual.
 * 
 * @param name String contendo o identificador do componente.
 * @param updateViews
 * @param form String contendo o identificador do formul�rio.
 */
function refreshPage(name, updateViews, form){
	if(form)
		document.forms[form].action.value = "refresh";

	pagerAction(name, "refreshPage", updateViews, form);
}

/**
 * Move para a primeira p�gina.
 * 
 * @param name String contendo o identificador do componente.
 * @param updateViews
 * @param form String contendo o identificador do formul�rio.
 */
function moveToFirstPage(name, updateViews, form){
	if(form)
		document.forms[form].action.value = "refresh";
	
	var pagerOnForm = usePagerOnForm(name);
	
	if(pagerOnForm){
		var object = document.getElementById(name);
		
		if(object)
			object.value = "objectId{0}"; 
	}
	
	pagerAction(name, "firstPage", updateViews, form);
}

/**
 * Move para a p�gina anterior.
 * 
 * @param name String contendo o identificador do componente.
 * @param updateViews
 * @param form String contendo o identificador do formul�rio.
 */
function moveToPreviousPage(name, updateViews, form){
	if(form)
		document.forms[form].action.value = "refresh";

	var pagerOnForm = usePagerOnForm(name);
	
	if(pagerOnForm){
		var pagerIndex = getPagerIndex(name);
		var object     = document.getElementById(name);
			
		if(object)
			object.value = ("objectId{" + (pagerIndex - 1) + "}"); 
	}

	pagerAction(name, "previousPage", updateViews, form);
}

/**
 * Move para a pr�xima p�gina.
 * 
 * @param name String contendo o identificador do componente.
 * @param updateViews
 * @param form String contendo o identificador do formul�rio.
 */
function moveToNextPage(name, updateViews, form){
	if(form)
		document.forms[form].action.value = "refresh";

	var pagerOnForm = usePagerOnForm(name);
	
	if(pagerOnForm){
		var pagerIndex = getPagerIndex(name);
		var object     = document.getElementById(name);
			
		if(object)
			object.value = ("objectId{" + (pagerIndex + 1) + "}");
	}

	pagerAction(name, "nextPage", updateViews, form);
}

/**
 * Move para a �ltima p�gina.
 * 
 * @param name String contendo o identificador do componente.
 * @param updateViews
 * @param form String contendo o identificador do formul�rio.
 */
function moveToLastPage(name, updateViews, form){
	if(form)
		document.forms[form].action.value = "refresh";

	var pagerOnForm = usePagerOnForm(name);
	
	if(pagerOnForm){
		var object = document.getElementById(name + ".dataValuesEndIndex");
		
		if(object){
			var pagerEndIndex = parseInt(object.value); 
			var object        = document.getElementById(name);
			
			if(object)
				object.value = ("objectId{" + pagerEndIndex + "}"); 
		}
	}

	pagerAction(name, "lastPage", updateViews, form);
}

function usePagerOnForm(name){
	var object = document.getElementById(name + ".pagerOnForm");
	
	if(object)
		if(object.value == "true")
			return true;
	
	return false;
}

function getPagerIndex(name){
	var object = document.getElementById(name);
	
	if(object){
		var objectValue = object.value;
		
		if(objectValue.indexOf("objectId") >= 0){
			var objectIndex = replaceAll(objectValue, "objectId{", "");
			
			objectIndex = replaceAll(objectIndex, "}", "");
			
			return parseInt(objectIndex);
		}
	}
	
	return 0;
}