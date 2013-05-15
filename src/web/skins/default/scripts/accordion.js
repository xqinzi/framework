/**
 * Arquivo que contém as funções/propriedades para manipulação do componente visual accordion (guia de seções).
 * 
 * @author fvilarinho
 * @version 4.0
 */

/**
 * Exibe/Esconde uma seção.
 * 
 * @param accordion Identificador do componente.
 * @param section Instância da seção
 * @param last Indica se é a última seção.
 * @param onSelect Função a ser executada na seleção da seção.
 * @param onUnSelect Função a ser executada na deseleção da seção.
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