var sliderBarControl           = null;
var sliderBarControlName       = null;
var sliderBarControlCurrentPos = null;
var sliderBarControlStartPos   = null;

function dragSliderBarControl(object, event){
	sliderBarControl           = object;
	sliderBarControlName       = replaceAll(object.id, ".sliderBarControl", "");
	sliderBarControlCurrentPos = Number(replaceAll(object.style.left, "px", ""));
	
	if(sliderBarControlCurrentPos == null || isNaN(sliderBarControlCurrentPos))
		sliderBarControlCurrentPos = 0;
	
	sliderBarControlStartPos = event.clientX;
}

function dropSliderBarControl(object, event){
	sliderBarControl           = null;
	sliderBarControlName       = null;
	sliderBarControlCurrentPos = null;
	sliderBarControlStartPos   = null;
} 

function slideIt(event){
	if(sliderBarControl != null && sliderBarControlStartPos != null){
		var sliderBarContent = document.getElementById(sliderBarControlName + ".sliderBarContent");
		var width            = sliderBarContent.offsetWidth - sliderBarControl.offsetWidth;
		var pos              = (sliderBarControlCurrentPos + event.clientX - sliderBarControlStartPos);
		
		if(pos <= 0)
			pos = 0;
		
		if(pos >= width)
			pos = width;
		
		sliderBarControl.style.left = pos; 
	}
}