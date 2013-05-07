function addSpinnerValue(name, maximumValue){
	var object = document.getElementById(name);
	
	if(object){
		var currentValue = Number(object.value);
		
		if(maximumValue){
			if(currentValue >= maximumValue){
				object.value = maximumValue;
				
				return;
			}
		}
		
		object.value = currentValue + 1;
	}
}

function subtractSpinnerValue(name, minimumValue){
	var object = document.getElementById(name);
	
	if(object){
		var currentValue = Number(object.value);
		
		if(minimumValue){
			if(currentValue <= minimumValue){
				object.value = minimumValue;
				
				return;
			}
		}
		
		object.value = currentValue - 1;
	}
}