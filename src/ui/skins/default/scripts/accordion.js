/**
 * Arquivo que cont�m as fun��es/propriedades para manipula��o do componente visual accordion (guia de se��es).
 * 
 * @author fvilarinho
 * @version 3.0
 */
 
/**
 * Exibe/Esconde uma se��o.
 * 
 * @param accordion Identificador do componente.
 * @param section Inst�ncia da se��o
 * @param last Indica se � a �ltima se��o.
 * @param hasMultipleSelection Indica se a sele��o ser� m�ltipla.
 * @param onSelect Fun��o a ser executada na sele��o da se��o.
 * @param onUnSelect Fun��o a ser executada na desele��o da se��o.
 */
function showHideAccordionSection(accordion, section, last, hasMultipleSelection, onSelect, onUnSelect){
	var sectionName    = replaceAll(section.id, ".sectionHeader", "");
	var currentSection = getObject(accordion + ".currentSection");
	var sectionContent = null;
	
	if(currentSection){
		if(!hasMultipleSelection){
			if(currentSection.value != "" && currentSection.value != sectionName){
				sectionHeader  = getObject(currentSection.value + ".sectionHeader");
				sectionContent = getObject(currentSection.value + ".sectionContent");
				
				if(sectionHeader && sectionContent){
					if(sectionContent.className == "lastSectionContent")
						sectionHeader.className = "lastSectionHeader";					
						
					sectionContent.style.display = "NONE";
				}
			}
		}
		else{
			var currentSectionOptions = currentSection.options;
			
			if(currentSectionOptions){
				for(cont = 0 ; cont < currentSectionOptions.length ; cont++){
					currentSectionOption = currentSectionOptions[cont];
					
					if(currentSectionOption.value == sectionName){
						if(currentSectionOption.selected == true)
							currentSectionOption.selected = false;
						else
							currentSectionOption.selected = true;
					}
				}
			}
		}
	}
	
	sectionContent = getObject(sectionName + ".sectionContent");
	
	if(sectionContent){
		if(sectionContent.style.display.toUpperCase() == "NONE"){
			sectionContent.style.display = "";
			
			if(last)
				section.className = "sectionHeader";
			
			if(!hasMultipleSelection)
				currentSection.value = sectionName;
			
			if(onSelect)
				onSelect();
		}
		else{
			sectionContent.style.display = "NONE";
			
			if(last)
				section.className = "lastSectionHeader";
			
			if(!hasMultipleSelection)
				currentSection.value = "";

			if(onUnSelect)
				onUnSelect();
		}
	}
}