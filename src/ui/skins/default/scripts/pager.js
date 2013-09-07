/**
 * Arquivo que contém as funções/propriedades para manipulação do componente visual pager (paginador).
 * 
 * @author fvilarinho
 * @version 1.0
 */

/**
 * Executa uma ação de paginação.
 * 
 * @param name String contendo o identificador do componente.
 * @param action String contendo a ação desejada.
 * @param updateViews
 * @param form String contendo o identificador do formulário.
 */
function pagerAction(name, action, updateViews, form){
	if(!form)
		return;

	setObjectValue(name + ".pagerAction", action);
	
	var object = document.forms[form].elements["updateViews"];
	
	if(object)
		object.value = updateViews;

	if(form)	
		submitForm(document.forms[form]);
}

/**
 * Atualiza a página atual.
 * 
 * @param name String contendo o identificador do componente.
 * @param updateViews
 * @param form String contendo o identificador do formulário.
 */
function refreshPage(name, updateViews, form){
	if(form)
		document.forms[form].action.value = "refresh";

	pagerAction(name, "REFRESH_PAGE", updateViews, form);
}

/**
 * Move para a primeira página.
 * 
 * @param name String contendo o identificador do componente.
 * @param updateViews
 * @param form String contendo o identificador do formulário.
 */
function moveToFirstPage(name, updateViews, form){
	if(form)
		document.forms[form].action.value = "refresh";
	
	if(usePagerOnForm(name))
		setObjectValue(name, "objectId{0}"); 
	
	pagerAction(name, "FIRST_PAGE", updateViews, form);
}

/**
 * Move para a página anterior.
 * 
 * @param name String contendo o identificador do componente.
 * @param updateViews
 * @param form String contendo o identificador do formulário.
 */
function moveToPreviousPage(name, updateViews, form){
	if(form)
		document.forms[form].action.value = "refresh";

	if(usePagerOnForm(name)){
		var pagerIndex = getPagerIndex(name);
		
		setObjectValue(name, "objectId{" + (pagerIndex - 1) + "}"); 
	}

	pagerAction(name, "PREVIOUS_PAGE", updateViews, form);
}

/**
 * Move para a próxima página.
 * 
 * @param name String contendo o identificador do componente.
 * @param updateViews
 * @param form String contendo o identificador do formulário.
 */
function moveToNextPage(name, updateViews, form){
	if(form)
		document.forms[form].action.value = "refresh";

	if(usePagerOnForm(name)){
		var pagerIndex = getPagerIndex(name);

		setObjectValue(name, "objectId{" + (pagerIndex + 1) + "}");
	}

	pagerAction(name, "NEXT_PAGE", updateViews, form);
}

/**
 * Move para a última página.
 * 
 * @param name String contendo o identificador do componente.
 * @param updateViews
 * @param form String contendo o identificador do formulário.
 */
function moveToLastPage(name, updateViews, form){
	if(form)
		document.forms[form].action.value = "refresh";

	if(usePagerOnForm(name)){
		var pagerEndIndex = parseInt(getObjectValue(name + ".dataValuesEndIndex"));
		
		setObjectValue(name, "objectId{" + pagerEndIndex + "}"); 
	}

	pagerAction(name, "LAST_PAGE", updateViews, form);
}

/**
 * Indica se o paginador está vinculado ao formulário.
 * 
 * @param name String contendo o identificador do paginador.
 * @return True/False.
 */
function usePagerOnForm(name){
	var object = getObject(name + ".pagerOnForm");
	
	if(object)
		if(object.value == "true")
			return true;
	
	return false;
}

/**
 * Retorna o índice de paginação.
 * 
 * @param name String contendo o identificador do paginador.
 * @return Valor numérico contendo o índice de paginação.
 */
function getPagerIndex(name){
	var object = getObject(name);
	
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