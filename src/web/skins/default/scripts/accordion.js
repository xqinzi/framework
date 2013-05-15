/**
 * Arquivo que cont�m as fun��es/propriedades para manipula��o do componente visual accordion (guia de se��es).
 * 
 * @author fvilarinho
 * @version 4.0
 */

/**
 * Exibe/Esconde uma se��o.
 * 
 * @param accordion Identificador do componente.
 * @param section Inst�ncia da se��o
 * @param last Indica se � a �ltima se��o.
 * @param onSelect Fun��o a ser executada na sele��o da se��o.
 * @param onUnSelect Fun��o a ser executada na desele��o da se��o.
 */
function showHideAccordionSection(accordion, section, last, onSelect, onUnSelect){
	var sectionName          = replaceAll(section.id, ".sectionHeader", "");
	var currentSection       = document.getElementById(accordion + ".currentSection");
	var hasMultipleSelection = document.getElementById(accordion + ".hasMultipleSelection");
	var sectionContent       = null;
	
	if(currentSection && hasMultipleSelection){
		if(hasMultipleSelection.value == "false"){
			if(currentSection.value != "" && currentSection.value != sectionName){
				sectionHeader  = document.getElementById(currentSection.value + ".sectionHeader");
				sectionContent = document.getElementById(currentSection.value + ".sectionContent");
				
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
	
	sectionContent = document.getElementById(sectionName + ".sectionContent");
	
	if(sectionContent){
		if(sectionContent.style.display.toUpperCase() == "NONE"){
			sectionContent.style.display = "";
			
			if(last)
				section.className = "sectionHeader";
			
			if(hasMultipleSelection && hasMultipleSelection.value == "false")
				currentSection.value = sectionName;
			
			if(onSelect)
				onSelect();
		}
		else{
			sectionContent.style.display = "NONE";
			
			if(last)
				section.className = "lastSectionHeader";
			
			if(hasMultipleSelection && hasMultipleSelection.value == "false")
				currentSection.value = "";

			if(onUnSelect)
				onUnSelect();
		}
	}
}