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
	var sectionName    = replaceAll(section.id, ".sectionHeader", "");
	var currentSection = document.getElementById(accordion + ".currentSection");
	var sectionContent = null;
	
	if(currentSection){
		if(currentSection.value != "" && currentSection.value != sectionName){
			sectionHeader  = document.getElementById(currentSection.value + ".sectionHeader");
			sectionContent = document.getElementById(currentSection.value + ".sectionContent");
			
			if(sectionHeader && sectionContent){
				if(sectionContent.className == "lastSectionContent")
					sectionHeader.className = "lastSectionHeader";					
					
				sectionContent.style.display = "NONE";
			}
		}
		
		currentSection.value = sectionName;
	}
	
	sectionContent = document.getElementById(sectionName + ".sectionContent");
	
	if(sectionContent){
		if(sectionContent.style.display.toUpperCase() == "NONE"){
			sectionContent.style.display = "";
			
			if(last)
				section.className = "sectionHeader";
			
			if(onSelect)
				onSelect();
		}
		else{
			sectionContent.style.display = "NONE";
			
			if(last)
				section.className = "lastSectionHeader";
			
			if(onUnSelect)
				onUnSelect();
		}
	}
}