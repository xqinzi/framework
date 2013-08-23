function addSpinnerValue(name, maximumValue, step){
	var object = getObject(name);
	
	if(object){
		var currentValue = Number(object.value);
		
		if(maximumValue){
			if(currentValue >= maximumValue){
				object.value = maximumValue;
				
				return;
			}
		}
		
		object.value = currentValue + step;
	}
}
 
function subtractSpinnerValue(name, minimumValue, step){
	var object = getObject(name);
	
	if(object){
		var currentValue = Number(object.value);
		
		if(minimumValue){
			if(currentValue <= minimumValue){
				object.value = minimumValue;
				
				return;
			}
		}
		
		object.value = currentValue - step;
	}
}