/**
 * Arquivo que contém as funções/propriedades comuns utilizadas pelo componente colorPicker (seletor de cor). 
 * 
 * @author fvilarinho
 * @version 3.0
 */

/**
 * Exibe a cor selecionada no thumbnail.
 * 
 * @param name String contendo o identificador do componente.
 */
function changeColorPickerThumbnail(name){
	var redValue   = getObjectValue(name + ".redValue");
	var greenValue = getObjectValue(name + ".greenValue");
	var blueValue  = getObjectValue(name + ".blueValue");
	var value      = "rgb(" + redValue + ", " + greenValue + ", " + blueValue + ")";
	
	setObjectValue(name, value);
	
	var thumbnailObject = getObject(name + ".colorPickerThumbnail");
	
	if(thumbnailObject)
		thumbnailObject.style.background = value;
}