/**
 * Arquivo que contém as funções/propriedades para manipulação do componente visual accordion (guia de seções).
 * 
 * @author fvilarinho
 * @version 3.0
 */
 
/**
 * Exibe/Esconde uma seção.
 * 
 * @param accordion Identificador do componente.
 * @param section Instância da seção
 * @param last Indica se é a última seção.
 * @param hasMultipleSelection Indica se a seleção será múltipla.
 * @param onSelect Função a ser executada na seleção da seção.
 * @param onUnSelect Função a ser executada na deseleção da seção.
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