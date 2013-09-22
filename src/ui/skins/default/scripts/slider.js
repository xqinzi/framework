/**
 * Arquivo que contém as funções/propriedades para manipulação do componente visual 
 * slider (barra de deslize para controle de valores numéricos).
 * 
 * @author fvilarinho
 * @version 3.0
 */
var pagePosition         = null;
var currentSliderBarName = null;
var slidersBar           = new Object();

/**
 * Classe que armazena as propriedades do componente.
 */
function SliderBar(){
	var name;
	var width;
	var controlCurrentPosition;
	var controlWidth;
	var maximumValue;
	var value;

	this.getName = function() {
		return name;
	}
	
	this.setName = function(n){
		name = n;
	}
	
	this.getWidth = function() {
		return width;
	}
	
	this.setWidth = function(w) {
		width = w;
	}
	
	this.getControlCurrentPosition = function() {
		return controlCurrentPosition;
	}
	
	this.setControlCurrentPosition = function(cp) {
		controlCurrentPosition = cp;
	}
	
	this.getControlWidth = function() {
		return controlWidth;
	}
	
	this.setControlWidth = function(cw) {
		controlWidth = cw;
	}
	
	this.getMaximumValue = function() {
		return maximumValue;
	}
	
	this.setMaximumValue = function(mv){
		maximumValue = mv;
	}
	
	this.getValue = function() {
		return value;
	}
	
	this.setValue = function(v) {
		value = v;
	}
}

/**
 * Inicializa componente.
 * 
 * @param name String contendo o identificador do componente.
 * @param width Valor numérico contendo a largura do componente.
 * @param maximumValue Valor numérico contendo o valor máximo permitido.
 */
function initializeSlider(name, width, maximumValue){
	var sliderBarObject        = getObject(name + ".sliderBar");
	var sliderBarControlObject = getObject(name + ".sliderBarControl");
	
	if(sliderBarObject && sliderBarControlObject){
		var sliderBar = slidersBar[name];
		
		if(!sliderBar){
			sliderBar = new SliderBar();
			sliderBar.setName(name);
			sliderBar.setWidth(width);
			sliderBar.setControlWidth(sliderBarControlObject.offsetWidth);
			sliderBar.setMaximumValue(maximumValue);
			
			slidersBar[name] = sliderBar; 
		}
		
		setSliderPosition(name);
	}
}

/**
 * Define a posição da barra de controle de acordo com o valor atual.
 * 
 * @param name String contendo o identificador do componente.
 */
function setSliderPosition(name){
	var sliderBarObject        = getObject(name + ".sliderBar");
	var sliderBarControlObject = getObject(name + ".sliderBarControl");
	
	if(sliderBarObject && sliderBarControlObject){
		var sliderBar = slidersBar[name];
		var value     = getObjectValue(name);
		
		if(value < 0)
			value = 0;
		
		if(value > sliderBar.getMaximumValue())
			value = sliderBar.getMaximumValue();
		
		setObjectValue(name, value);
		
		sliderBar.setValue(value);

		var currentPosition = Math.round((sliderBar.getWidth() * sliderBar.getValue()) / sliderBar.getMaximumValue());
		
		sliderBar.setControlCurrentPosition(currentPosition);

		slidersBar[name] = sliderBar; 
		
		sliderBarObject.style.width = (sliderBar.getWidth() + sliderBar.getControlWidth()) + "px";
		
		sliderBarControlObject.style.left = currentPosition + "px";
	}
}
 
/**
 * Inicia o deslize da barra corrente.
 * 
 * @param name String contendo o identificador do componente.
 * @param event Instância contendo as propriedades do evento.
 */
function dragSliderBarControl(name, event){
	if(event){
		currentSliderBarName = name;
		pagePosition         = event.pageX;
	}
}

/**
 * Efetua a parada do deslize da barra corrente.
 */
function dropSliderBarControl(){
	currentSliderBarName = null;
} 

/**
 * Efetua o deslize da barra corrente.
 * 
 * @param event Instância contendo as propriedades do evento.
 */
function slideIt(event){
	if(currentSliderBarName != null && pagePosition != null && event){
		var sliderBar           = slidersBar[currentSliderBarName];
		var currentPagePosition = event.pageX;
		var currentPosition     = (sliderBar.getControlCurrentPosition() + event.pageX - pagePosition);
		 
		pagePosition = currentPagePosition;
		
		if(currentPosition <= 0)
			currentPosition = 0;
		
		if(currentPosition >= sliderBar.getWidth())
			currentPosition = sliderBar.getWidth();
		
		sliderBar.setControlCurrentPosition(currentPosition);
		
		var value = Math.round((sliderBar.getMaximumValue() * currentPosition) / sliderBar.getWidth());
		
		sliderBar.setValue(value);
		
		slidersBar[currentSliderBarName] = sliderBar;
		
		var sliderBarControlObject = getObject(currentSliderBarName + ".sliderBarControl");
		
		if(sliderBarControlObject)
			sliderBarControlObject.style.left = currentPosition; 
			
		setObjectValue(currentSliderBarName, value);
	}
}