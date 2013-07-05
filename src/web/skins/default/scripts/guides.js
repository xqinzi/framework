/**
 * Arquivo que contém as funções/propriedades para manipulação do componente visual guides (guias).
 * 
 * @author fvilarinho
 * @version 1.0
 */

/**
 * Define qual deve ser a guia atual.
 * 
 * @param guideName String contendo o identificador da guia.
 * @param guidesName String contendo o identificador do controlador de guias.
 * @param guideOnSelect Método a ser executado no momento que a guia for selecionada.
 */
function setCurrentGuide(guideName, guidesName, guideOnSelect){
	var currentGuideName = "";
	var currentGuide     = getObject(guidesName + ".currentGuide");
	
	if(currentGuide){
		currentGuideName = currentGuide.value;
		
		currentGuide.value = guideName;
	}
	
	var currentGuideDefinition = getObject(guidesName + "." + currentGuideName + ".guideDefinition");
	var currentGuideContent    = getObject(guidesName + "." + currentGuideName + ".guideContent");
	var guideDefinition        = getObject(guidesName + "." + guideName + ".guideDefinition");
	var guideContent           = getObject(guidesName + "." + guideName + ".guideContent");
	
	if(currentGuideDefinition && currentGuideContent){
		changeStyle(currentGuideDefinition, "guide");

		currentGuideContent.style.display = "NONE";
	}
		
	if(guideDefinition && guideContent){
		changeStyle(guideDefinition, "currentGuide");

		guideContent.style.display = "";
	}
	
	if(guideOnSelect && guideOnSelect.length > 0)
		eval(guideOnSelect);
}