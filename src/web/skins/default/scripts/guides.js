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
	var currentGuideName = getObjectValue(guidesName + ".currentGuide");
	
	setObjectValue(guidesName + ".currentGuide", guideName);
	
	var currentGuide        = getObject(guidesName + "." + currentGuideName + ".guide");
	var currentGuideContent = getObject(guidesName + "." + currentGuideName + ".guideContent");
	var guide               = getObject(guidesName + "." + guideName + ".guide");
	var guideContent        = getObject(guidesName + "." + guideName + ".guideContent");
	
	if(currentGuide && currentGuideContent){
		currentGuide.className = "guide";

		currentGuideContent.style.display = "NONE";
	}
		
	if(guide && guideContent){
		guide.className = "currentGuide";

		guideContent.style.display = "";
	}
	
	if(guideOnSelect && guideOnSelect.length > 0)
		eval(guideOnSelect);
}