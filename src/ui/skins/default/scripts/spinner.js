/**
 * Arquivo que contém as funções/propriedades para manipulação do componente visual spinner (controle de valores numéricos).
 * 
 * @author fvilarinho
 * @version 3.0
 */

/**
 * Adiciona o tamanho do passo no valor atual do componente respeitando o 
 * seu valor máximo estabelecido.
 * 
 * @param name String contendo o identificador do componente.
 * @param maximumValue Valor máximo do componente.
 * @param step Valor numérico contendo o tamanho do passo.
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
 * seu valor mínimo estabelecido.
 * 
 * @param name String contendo o identificador do componente.
 * @param minimumValue Valor mínimo do componente.
 * @param step Valor numérico contendo o tamanho do passo.
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