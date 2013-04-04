/**
 * Arquivo que contém as funções/propriedades comuns utilizadas por todos os componentes 
 * visuais.
 * 
 * @author fvilarinho
 * @version 1.0
 */
var clockTimer     = null;
var requestHandler = null;
var submittedForm  = null;

/**
 * Indica se o browser é o Internet Explorer.
 * 
 * @return True/False.
 */
function isIe(){
	if(document.all)
		return true;
	
	return false;
}

/**
 * Indica se o browser é o Mozilla/Firefox.
 * 
 * @return True/False.
 */
function isMozilla(){
	return !isIe();
}

/**
 * Replica uma string.
 * 
 * @param value String desejada.
 * @param times Valor inteiro contendo o número de replicações.
 * @return String final replicada.
 */
function replicate(value, times){
	var cont   = 0;
	var result = "";
	
	for(cont = 0 ; cont < times ; cont++)
		result += value;
	
	return result;
}

/**
 * Subsititui todas as recorrências de uma string.
 *  
 * @param value String desejada.
 * @param searchValue Valor a ser substituido.
 * @param replaceValue Valor da substituição.
 * @return String final após as substituições.
 */
function replaceAll(value, searchValue, replaceValue){
	var pos    = 0;
	var result = "";
	
	pos = value.indexOf(searchValue);
	if(pos >= 0){
		result  = value.substring(0, pos) + replaceValue;
		result += value.substring(pos + searchValue.length);
			
		return replaceAll(result, searchValue, replaceValue);
	}

	return value;
}

/**
 * Inverte uma string.
 * 
 * @param value String desejada.
 * @return String invertida.
 */
function reverse(value){
	var cont   = 0;
	var result = "";

	for(cont = (value.length - 1) ; cont >= 0 ; cont--)
  		result += value.charAt(cont);

	return result;
}

/**
 * Retorna a posição do cursor em um campo texto.
 * 
 * @param object Objeto que define o campo texto.
 * @return Valor numérico contendo a posição do cursor.
 */
function getCursorPosition(object){
	var range;
	
	if(typeof object.selectionStart == "number")
		return object.selectionStart;
	else if(document.selection){
		range = document.selection.createRange();
		
		range.collapse(true);
		range.moveStart("character", -object.value.length);

		return range.text.length;
	}
}

/**
 * Retorna o código ASCII da tecla pressionada.
 * 
 * @param event Objeto contendo as propriedades do evento.
 * @return Código ASCII da tecla pressionada.
 */
function getKeyPressed(event){
	var key;
  
	if(window.event)
		key = event.keyCode;
	else
     	key = event.which;
        
   	return key;
}

/**
 * Formata o conteúdo de um campo texto utilizando uma máscara.
 * 
 * @param object Objeto que define o campo texto desejado.
 * @param pattern String contendo a máscara de formatação.
 * @param event Objeto que define o evento ocorrido.
 */
function formatProperty(object, pattern, event){
	var caretPos = getCursorPosition(object);
	
	if(caretPos < object.value.length)
		return;
		
	var key = getKeyPressed(event);
	
	if(key == 8 || key == 13 || key == 0)
		return;

	if(object.readOnly || object.disabled){
		if(window.event)
			window.event.returnValue = null;
		else
	       	event.preventDefault();
	       	
	    return;
	}
	
	var cont1           = 0;
	var pos             = 0;
	var delimitersPos   = "";
	var delimitersChars = "";

	for(cont1 = 0 ; cont1 < pattern.length ; cont1++){
		if(pattern.charAt(cont1) != "9" && pattern.charAt(cont1) != "#" && pattern.charAt(cont1) != "d" && pattern.charAt(cont1) != "M" && pattern.charAt(cont1) != "m" && pattern.charAt(cont1) != "y" && pattern.charAt(cont1) != "H" && pattern.charAt(cont1) != "h" && pattern.charAt(cont1) != "s" && pattern.charAt(cont1) != "S" && pattern.charAt(cont1) != "a"){
			if(pos > 0){
				delimitersPos += "|";
				delimitersChars += "|";
			}

			delimitersPos   += cont1;
			delimitersChars += pattern.charAt(cont1);

			pos++;
		}
	}

	var result     = object.value;
	var bufferChar = String.fromCharCode(key);

	if(pattern.charAt(result.length) == "9" || pattern.charAt(result.length) == "d" || pattern.charAt(result.length) == "M" || pattern.charAt(result.length) == "m" || pattern.charAt(result.length) == "y" || pattern.charAt(result.length) == "H" || pattern.charAt(result.length) == "h" || pattern.charAt(result.length) == "s" || pattern.charAt(result.length) == "S"){
		if(isNaN(bufferChar)){
			if(window.event)
				window.event.returnValue = null;
			else
		       	event.preventDefault();

			return;
		}
	}
	else{
		if(bufferChar == pattern.charAt(result.length)){
			if(window.event)
				window.event.returnValue = null;
			else
		       	event.preventDefault();
		       	
		    object.value = result + bufferChar;
		    
		    return;
		}
		
		if(pattern.charAt(result.length + 1) == "9" || pattern.charAt(result.length + 1) == "d" || pattern.charAt(result.length + 1) == "M" || pattern.charAt(result.length + 1) == "m" || pattern.charAt(result.length + 1) == "y" || pattern.charAt(result.length + 1) == "H" || pattern.charAt(result.length + 1) == "h" || pattern.charAt(result.length + 1) == "s" || pattern.charAt(result.length + 1) == "S" || pattern.charAt(result.length + 1) == "a"){
			if(isNaN(bufferChar)){
				if(window.event)
					window.event.returnValue = null;
				else
			       	event.preventDefault();
	
				return;
			}
		}
	}
	
	if(result.length >= pattern.length){
		if(window.event)
			window.event.returnValue = null;
		else
	       	event.preventDefault();

		return;
	}

	if(delimitersPos.length > 0){
		var delimitersPosArray   = delimitersPos.split("|");
		var delimitersCharsArray = delimitersChars.split("|");
		var cont2                = 0;
		var cont3                = 0;
		
		for(cont1 = 0 ; cont1 <= delimitersPosArray.length ; cont1++)
			for(cont2 = 0 ; cont2 <= result.length ; cont2++)
				if(cont2 == delimitersPosArray[cont1])
	  				if(result.substring(cont2, cont2 + 1) != delimitersCharsArray[cont1])
					    result = result.substring(0, cont2) + delimitersCharsArray[cont1] + result.substring(cont2 + 1, result.length);
		
		object.value = result;
	}
}

/**
 * Formata o conteúdo de um campo texto utilizando uma máscara.
 * 
 * @param object Objeto que define o campo texto desejado.
 * @param pattern String contendo a máscara de formatação.
 * @param event Objeto que define o evento ocorrido.
 */
function formatNumberProperty(object, pattern, event){
	var caretPos = getCursorPosition(object);
	
	if(caretPos < object.value.length)
		return;

	var key = getKeyPressed(event);

	if(key == 8 || key == 13 || key == 0)
		return;

	if(object.readOnly || object.disabled){
		if(window.event)
			window.event.returnValue = null;
		else
	       	event.preventDefault();
	       	
	    return;
	}

	var cont             = 0;
	var groupSeparator   = "";
	var groupCount       = 0;
	var decimalSeparator = "";
	var decimalCount     = 0;
	var decimalPos       = 0;

	for(cont = (pattern.length - 1) ; cont >= 0 ; cont--){
		if(pattern.charAt(cont) != "#" && pattern.charAt(cont) != "0"){
			if(decimalSeparator == ""){
				decimalSeparator = pattern.charAt(cont);
				decimalPos       = cont;
			}
		}
		else{
			if(decimalSeparator != ""){
				if(pattern.charAt(cont) == "0")
					groupCount++;
			}
			else
				decimalCount++;
		}	
	}

	for(cont = 0 ; cont < pattern.length ; cont++){
		if(pattern.charAt(cont) != "#" && pattern.charAt(cont) != "0" && cont != decimalPos){
			groupSeparator = pattern.charAt(cont);

			break;
		}
	}

	var objectValue   = object.value;
	var isNegative    = false;
	var bufferChar    = String.fromCharCode(key);
	var groupBuffer   = "";
	var decimalBuffer = "";
  
	if(window.event)
		window.event.returnValue = null;
	else
       	event.preventDefault();
       	
    isNegative = objectValue.indexOf("-") >= 0;
    
    if(bufferChar == "-" && isNegative)
    	return;   	
       	
    if(bufferChar == "-"){
    	if(objectValue == "0")
    		objectValue = bufferChar;
    	else
    		objectValue = bufferChar + objectValue;
    }
    else{
    	if(isNegative)
	    	objectValue = objectValue.substring(1);
    	
	  	if(isNaN(bufferChar))
			return;

		if(groupSeparator != "")
			objectValue = replaceAll(objectValue, groupSeparator, "");
			
		if(decimalSeparator != "")
			objectValue = replaceAll(objectValue, decimalSeparator, "");
			
		if(decimalSeparator != "")
			objectValue = replicate("0", (groupCount + decimalCount) - objectValue.length) + objectValue;
			
		if(objectValue != "0" && bufferChar == "0"){
			if(!isNegative || (isNegative && objectValue != ""))
				objectValue += bufferChar;
		}
		else if(objectValue != "0" && bufferChar != "0")
			objectValue += bufferChar;
		else if(objectValue == "0" && bufferChar != "0")
			objectValue = bufferChar;
		
		if(objectValue.charAt(0) == "0" && objectValue != "0")
			objectValue = objectValue.substring(1);
		
		groupBuffer   = objectValue.substring(0, objectValue.length - decimalCount);
		decimalBuffer = objectValue.substring(objectValue.length - decimalCount, objectValue.length);
		objectValue   = "";
		
		var bufferAux = "";
		var pos       = 0;
		
		for(cont = groupBuffer.length ; cont >= 0 ; cont--){
			bufferAux = groupBuffer.charAt(cont) + bufferAux;
			
			if(pos == 3){
				bufferAux = groupSeparator + bufferAux;
				
				pos = 0;
			}

			pos++;
		}
		
		if(bufferAux.charAt(0) == groupSeparator)
			bufferAux = bufferAux.substring(1);
			
		objectValue += bufferAux;
		objectValue += decimalSeparator;
		objectValue += decimalBuffer;
		
	  	if(isNegative)
	  		objectValue = "-" + objectValue;
	}  	
  	
    if(objectValue.length > (pattern.length + ((isNegative || bufferChar == "-") ? 1 : 0)))
    	return;
       	
  	object.value = objectValue;
}

/**
 * Coloca o foco em um objeto.
 * 
 * @param name String contendo o nome do objeto.
 */
function focusObject(name){
	try{
		var object = document.getElementById(name);
		
		if(object){
			object.focus();
			object.select();
		}
	}
	catch(e){
	}
}

/**
 * Centraliza um objeto.
 * 
 * @param object Objeto desejado.
 * @param target String contendo a janela onde o objeto se localiza.
 */
function centralizeObject(object, target){
	var windowWidth  = null;
	var windowHeight = null;
	var objectWidth  = null;
	var objectHeight = null;
	var objectTop    = null;
	var targetObject = (target ? top.frames[target] : null);

	objectWidth  = object.offsetWidth;
	objectHeight = object.offsetHeight;
	objectTop    = (targetObject ? targetObject.document.body.scrollTop : document.body.scrollTop);

	if(isIe()){
      	windowWidth  = (targetObject ? targetObject.document.body.scrollWidth : document.body.scrollWidth);
    	windowHeight = (targetObject ? targetObject.document.body.scrollHeight : document.body.scrollHeight);
	}
	else{
	  	windowWidth  = (targetObject ? targetObject.window.innerWidth : window.innerWidth);
    	windowHeight = (targetObject ? targetObject.window.innerHeight : window.innerHeight);
	}

	object.style.left = (windowWidth - objectWidth) / 2;
	object.style.top  = objectTop + (windowHeight - objectHeight) / 2;
}

/**
 * Muda o estilo CSS de um objeto.
 * 
 * @param object Objeto desejado.
 * @param styleName String contendo o nome do estilo CSS.
 */
function changeStyle(object, styleName){
	if(object)
		object.className = styleName;
}

/**
 * Adiciona um novo método ao evento de carregamento da página.
 * 
 * @param functionId String contendo a identificação do método.
 */
function addLoadEvent(functionId){
	var loadHistory = window.onload;

	if(typeof loadHistory != "function")
		window.onload = functionId;
	else{
		window.onload = function(){
							if(loadHistory)
		               			loadHistory();
		               		
		               		functionId();
		                }
	}
}

/**
 * Adiciona um novo método ao evento de redimensionamento da página.
 * 
 * @param functionId String contendo a identificação do método.
 */
function addResizeEvent(functionId){
	var resizeHistory = window.onresize;

	if(typeof resizeHistory != "function")
		window.onresize = functionId;
	else{
		window.onresize = function(){
								if(resizeHistory)
									resizeHistory();
		               			
		               			functionId();
		                  }
	}
}

/**
 * Adiciona um novo método ao evento de scrolling da página.
 * 
 * @param functionId String contendo a identificação do método.
 */
function addScrollEvent(functionId){
	var scrollHistory = window.onscroll;

	if(typeof scrollHistory != "function")
		window.onscroll = functionId;
	else{
		window.onscroll = function(){
								if(scrollHistory)
									scrollHistory();
		               			
		               			functionId();
		                  }
	}
}

/**
 * Adiciona um novo método ao evento de click da página.
 * 
 * @param functionId String contendo a identificação do método.
 */
function addClickEvent(functionId){
	var clickHistory = document.onclick;

	if(typeof clickHistory != "function")
		document.onclick = functionId;
	else{
		document.onclick = function(){
								if(clickHistory)
		               				clickHistory();
		               			
		               			functionId();
		                   }
	}
}


/**
 * Retorna a posição X de um objeto.
 * 
 * @param object Objeto desejado.
 * @return Valor numérico contendo a posição X.
 */
function getLeftPos(object){
	var curleft = 0;
	
	if(object.offsetParent){
		while (object.offsetParent){
			curleft += object.offsetLeft;
			
			object = object.offsetParent;
		}
	}
	else if(object.x)
		curleft += object.x;
	
	return curleft;
}

/**
 * Retorna a posição Y de um objeto.
 * 
 * @param object Objeto desejado.
 * @return Valor numérico contendo a posição Y.
 */
function getTopPos(object){
	var curtop = 0;
	
	if(object.offsetParent){
		while (object.offsetParent){
			curtop += object.offsetTop;
			
			object = object.offsetParent;
		}
	}
	else if(object.y)
		curtop += object.y;
	
	return curtop;
}

/**
 * Carrega uma URL.
 * 
 * @param url URL desejada.
 * @param target Objeto
 */
function openUrl(url, target){
	if(target)
		document.getElementById(target).src = url;
	else
		top.location.href = url;
}

/**
 * Carrega o box de carregamento de um formulário.
 * 
 * @param form Instância do formulário desejado.
 */
function showLoadingBox(form){
	var target = null;
	
	if(form)
		submittedForm = form;
	
	if(submittedForm.target && submittedForm.target.length > 0)
		target = submittedForm.target;

	var targetObject          = ((target && target.length > 0) ? top.frames[target] : null);
	var loadingBoxObjectId    = (targetObject ? targetObject.document.forms[0].name + ".loadingBox" : submittedForm.name + ".loadingBox");
	var loadingBoxObject      = (targetObject ? targetObject.document.getElementById(loadingBoxObjectId) : document.getElementById(loadingBoxObjectId));
	var loadingBoxObjectInfo  = (targetObject ? targetObject.document.getElementById(loadingBoxObjectId + "Info") : document.getElementById(loadingBoxObjectId + "Info"));
	var loadingBoxObjectError = (targetObject ? targetObject.document.getElementById(loadingBoxObjectId + "Error") : document.getElementById(loadingBoxObjectId + "Error"));
	
	if(loadingBoxObject && loadingBoxObjectInfo && loadingBoxObjectError){
		loadingBoxObject.style.visibility   = "VISIBLE";
		loadingBoxObjectInfo.style.display  = "";
		loadingBoxObjectError.style.display = "NONE";
			
		centralizeObject(loadingBoxObject, target);
	}	
}

/**
 * Esconde os box de carregamento dos formulários.
 */
function hideParentLoadingBox(){
	try{
		if(parent){
			var frames = parent.frames;
			var cont   = 0;
			
			for(cont = 0 ; cont < frames.length ; cont++)
				frames[cont].hideLoadingBox();
			
			parent.hideLoadingBox();
		}
		
		if(window.opener)
			window.opener.hideLoadingBox();
	}
	catch(e){
	}
}

/**
 * Esconde o box de carregamento de um formulário.
 */
function hideLoadingBox(){
	if(submittedForm){
		var loadingBoxObjectId = submittedForm.name + ".loadingBox";
		var loadingBoxObject   = document.getElementById(loadingBoxObjectId);
		
		if(loadingBoxObject)
			loadingBoxObject.style.visibility = "hidden";
	}
	
	submittedForm = null;
}

/**
 * Monta os parâmetros de submit de um formulário.
 */
function buildFormRequest(){
	var cont1       = 0;
	var cont2       = 0;
	var result      = "";
	var elements    = submittedForm.elements;
	var element     = null;
	var elementType = "";
	var options     = null;
	var option      = null;
	
	for(cont1 = 0 ; cont1 < elements.length ; cont1++){
		element     = elements[cont1];
		elementType = element.type;
		
		if(elementType){
			elementType = elementType.toUpperCase();
			
			if(elementType.indexOf("FILE") < 0){
				if(element.disabled == false && element.name != ""){
					if(elementType){
						elementType = element.type.toUpperCase();
						
						if(elementType.indexOf("SELECT-") >= 0){
							options = element.options;
							
							for(cont2 = 0 ; cont2 < options.length ; cont2++){
								option = options[cont2];
								
								if(option.selected == true){
									if(result.length > 0)
										result += "&";
								
									result += element.name;
									result += "=";
									
									if(option.value && option.value.length > 0)
										result += encodeURI(option.value);
								}
							}
						}
						else if(elementType == "RADIO" || elementType == "CHECKBOX"){
							if(element.checked == true){
								if(result.length > 0)
									result += "&";
									
								result += element.name;
								result += "=";
								
								if(element.value && element.value.length > 0)
									result += encodeURI(element.value);
							}		
						}
						else if(elementType != "BUTTON"){
							if(result.length > 0)
								result += "&";
								
							result += element.name;
							result += "=";
							
							if(element.value && element.value.length > 0)
								result += encodeURI(element.value);
						}
					}	
				}
			}
		}
	}
	
	return result;
}

/**
 * Efetua o submit de um formulário.
 * 
 * @param form Instância do objeto que define as propriedades do formulário.
 */
function submitForm(form){
	if(submittedForm || !form)
		return;
	
	submittedForm = form;
	
	if(submittedForm.encoding.indexOf("multipart") >= 0)
		submittedForm.encoding = "";	
	
	if(submittedForm.target)
		submittedForm.submit();
	else{
		showLoadingBox();
	
		var requestHandler = null;
		
		if(window.XMLHttpRequest)
			requestHandler = new XMLHttpRequest();
		else if(window.ActiveXObject){
			try{
				requestHandler = new ActiveXObject("Msxml2.XMLHTTP");
			}
			catch(e){
				try{
					requestHandler = new ActiveXObject("Microsoft.XMLHTTP");
				}
				catch(e1){
					hideLoadingBox();
					
					return false;
				}
			}
		}
		else{
			hideLoadingBox();
			
			return false;
		}	
		
		var formRequest = buildFormRequest();
		
		requestHandler.onreadystatechange = function(){
												processFormResponse(requestHandler);
											}
		
		requestHandler.open("POST", submittedForm.attributes["action"].value, true);
		requestHandler.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=" + submittedForm.acceptCharset);
		requestHandler.setRequestHeader("Content-length", formRequest.length); 
		requestHandler.setRequestHeader("Connection", "close");

		requestHandler.send(formRequest);
	}
}

/**
 * Processa a resposta da requisição do formulário.
 * 
 * @param requestHandler Instância da requisição.
 */
function processFormResponse(requestHandler){
	if(requestHandler.readyState == 4){
		if(requestHandler.status == 200){
			var responseText = requestHandler.responseText;
			var node         = null;
			
			if(responseText.indexOf(".errorTrace") >= 0){
				node = document.getElementById("errorPage");
				
				if(!node){
					node = document.createElement("div");
					
					node.id = "errorPage";
					
					document.body.appendChild(node);
				}
				
				node.innerHTML = responseText;
			}
			else{
				var object = submittedForm.elements["updateViews"];
				
				if(object){
					var objectValue = object.value;
					
					if(objectValue && objectValue.length > 0){
						var updateViews = objectValue.split(",");
						
						if(updateViews.length > 0){
							var responseDocument = document.createElement("div");
							
							responseDocument.innerHTML = requestHandler.responseText;
							
							var responseDocumentObjects = responseDocument.getElementsByTagName("div");
							var responseDocumentObject  = null;
							var documentObject          = null;
							var updateView              = "";
							
							for(cont1 = 0 ; cont1 < responseDocumentObjects.length ; cont1++){
								responseDocumentObject = responseDocumentObjects[cont1];
								
								for(cont2 = 0 ; cont2 < updateViews.length ; cont2++){
									updateView = updateViews[cont2];
									
									if(responseDocumentObject.id == updateView){
										documentObject = document.getElementById(updateView);
										
										if(documentObject)
											documentObject.innerHTML = responseDocumentObject.innerHTML;
									}
								}
							}
						}
						else
							document.body.innerHTML = responseText;
					}
					else
						document.body.innerHTML = responseText;
				}
				else
					document.body.innerHTML = responseText;
			}

		 	processFormResponseScripts();
			centralizeDialogBoxes();
			hideLoadingBox();
			hideParentLoadingBox();
		}
		else{
			var loadingBoxObjectId    = submittedForm.name + ".loadingBox";
			var loadingBoxObject      = document.getElementById(loadingBoxObjectId);
			var loadingBoxObjectInfo  = document.getElementById(loadingBoxObjectId + "Info");
			var loadingBoxObjectError = document.getElementById(loadingBoxObjectId + "Error");
			
			if(loadingBoxObject && loadingBoxObjectInfo && loadingBoxObjectError){
				loadingBoxObjectInfo.style.display  = "NONE";
				loadingBoxObjectError.style.display = "";
				
				centralizeObject(loadingBoxObject);
			}
		}
	}
}

/**
 * Processa os scripts do resultado de um submit.
 */
function processFormResponseScripts(){
	var childNodes = document.getElementsByTagName("script");
	var childNode  = null;
	var cont       = 0;
	
	if(childNodes){
		for(cont = 0 ; cont < childNodes.length ; cont++){
			childNode = childNodes[cont];
			
			if(childNode.innerHTML.length > 0)
				eval(childNode.innerHTML);
		}
	}
}

/**
 * Exibe o componente de relógio na página.
 */
function showClock(){
	if(clockTimer)
		clearTimeout(clockTimer);

	var clockObject  = document.getElementById("clock");
	var clockPattern = document.getElementById("clock.pattern");

	if(clockObject && clockPattern){
		var pattern      = clockPattern.value;
		var now          = new Date();
		var hours        = now.getHours();
		var minutes      = now.getMinutes();
		var seconds      = now.getSeconds();
		var milliseconds = now.getMilliseconds();
		var ampm         = (hours > 12 ? "PM" : "AM");
		
		pattern = replaceAll(pattern, "HH", (hours < 10 ? ("0" + hours) : hours));
		pattern = replaceAll(pattern, "hh", (hours > 12 ? ((hours - 12) < 10 ? ("0" + (hours - 12)) : (hours - 12)) : (hours < 10 ? ("0" + hours) : hours)));
		pattern = replaceAll(pattern, "h", (hours > 12 ? hours - 12 : hours));
		pattern = replaceAll(pattern, "H", hours);
		pattern = replaceAll(pattern, "mm", (minutes < 10 ? ("0" + minutes) : minutes));
		pattern = replaceAll(pattern, "ss", (seconds < 10 ? ("0" + seconds) : seconds));
		pattern = replaceAll(pattern, "SSS", milliseconds);
		pattern = replaceAll(pattern, "a", ampm);

		clockObject.innerHTML = pattern;

		clockTimer = setTimeout("showClock()", 1000);
	}
}

/**
 * Chama a ação para a troca do tema (skin) atual.
 */
function changeCurrentSkin(){
	document.forms[0].forward.value            = "";
	document.forms[0].forwardOnFail.value      = "";
	document.forms[0].action.value             = "changeCurrentSkin";
	document.forms[0].validateModel.value      = true;
	document.forms[0].validateProperties.value = "currentSkin";
	
	submitForm(document.forms[0]);
}

/**
 * Chama a ação para a troca do idioma atual.
 */
function changeCurrentLanguage(){
	document.forms[0].forward.value            = "";
	document.forms[0].forwardOnFail.value      = "";
	document.forms[0].action.value             = "changeCurrentLanguage";
	document.forms[0].validateModel.value      = true;
	document.forms[0].validateProperties.value = "currentLanguage";
	
	submitForm(document.forms[0]);
}