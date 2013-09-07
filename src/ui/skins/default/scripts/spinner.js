/**
 * Arquivo que cont�m as fun��es/propriedades para manipula��o do componente visual spinner (controle de valores num�ricos).
 * 
 * @author fvilarinho
 * @version 3.0
 */

/**
 * Adiciona o tamanho do passo no valor atual do componente respeitando o 
 * seu valor m�ximo estabelecido.
 * 
 * @param name String contendo o identificador do componente.
 * @param maximumValue Valor m�ximo do componente.
 * @param step Valor num�rico contendo o tamanho do passo.
 */
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
 
/**
 * Subtrai o tamanho do passo no valor atual do componente respeitando o 
 * seu valor m�nimo estabelecido.
 * 
 * @param name String contendo o identificador do componente.
 * @param minimumValue Valor m�nimo do componente.
 * @param step Valor num�rico contendo o tamanho do passo.
 */
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