function initializeRichTextArea(name){
	var object = getObject(name + ".content");
	
	if(object)
		object.addEventListener("keyup", updateRichTextArea, true);
}

function updateRichTextArea(e){
	var divs = document.getElementsByTagName("div");

	for(var i = 0 ; i < divs.length ; i++){
		var div = divs[i];
		var id  = div.id;
		
		if(id.indexOf(".content") >= 0){
			id = replaceAll(id, ".content", "");
			
			if(div)
				setObjectValue(id, div.innerHTML);
		}
	}
}