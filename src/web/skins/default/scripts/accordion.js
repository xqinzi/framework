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