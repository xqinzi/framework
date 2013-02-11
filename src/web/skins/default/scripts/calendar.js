/**
 * Arquivo que contém as funções/propriedades para manipulação do componente visual 
 * calendário.
 * 
 * @author fvilarinho
 * @version 1.0
 */
var weekNames  = null;
var monthNames = null;

/**
 * Inicializa lista de nomes dos meses a serem utilizados pelo componente.
 */
function initializeCalendarMonthNames(){
	monthNames = initializeCalendarMonthNames.arguments;
}

/**
 * Inicializa lista de nomes dos dias da semana a serem utilizados pelo componente.
 */
function initializeCalendarWeekNames(){
	weekNames = initializeCalendarWeekNames.arguments;
}

/**
 * Muda para o próximo mês.
 * 
 * @param name String contendo o identificador do componente.
 */
function moveToNextMonth(name){
	var currentDate = parseDateProperty(name);
	
	if(currentDate){
		currentDate.setMonth(currentDate.getMonth() + 1);
		
		updateDateProperty(name, currentDate);
		
		renderCalendar(name);
	}
}

/**
 * Muda para o próximo ano.
 * 
 * @param name String contendo o identificador do componente.
 */
function moveToNextYear(name){
	var currentDate = parseDateProperty(name);
	
	if(currentDate){
		if(currentDate.getFullYear() < 1900)
			currentDate.setYear((currentDate.getFullYear() + 1) + 1900);
		else
			currentDate.setYear((currentDate.getFullYear() + 1));

		updateDateProperty(name, currentDate);
		
		renderCalendar(name);
	}
}

/**
 * Muda para o mês anterior.
 * 
 * @param name String contendo o identificador do componente.
 */
function moveToPreviousMonth(name){
	var currentDate = parseDateProperty(name);
	
	if(currentDate){
		currentDate.setMonth(currentDate.getMonth() - 1);
		
		updateDateProperty(name, currentDate);
		
		renderCalendar(name);
	}
}

/**
 * Muda para o ano anterior.
 * 
 * @param name String contendo o identificador do componente.
 */
function moveToPreviousYear(name){
	var currentDate = parseDateProperty(name);
	
	if(currentDate){
		if(currentDate.getFullYear() < 1900)
			currentDate.setYear((currentDate.getFullYear() - 1) + 1900);
		else
			currentDate.setYear((currentDate.getFullYear() - 1));
 
		updateDateProperty(name, currentDate);
		
		renderCalendar(name);
	}
}

/**
 * Efetua o parse do texto digitado no componente.
 * 
 * @param name String contendo o identificador do componente.
 * @returns Instância do objeto de data/hora desejado.
 */
function parseDateProperty(name){
	var property        = document.getElementById(name);
	var propertyPattern = document.getElementById(name + ".pattern");
	
	if(property && propertyPattern){
		var value       = property.value;
		var currentDate = new Date();
	    var pattern     = propertyPattern.value; 
		var values      = value.split(" ");
		var patterns    = pattern.split(" ");
		var dateValue   = values[0];
		var datePattern = patterns[0];
		var timeValue   = (values.length > 1 ? values[1] : "");
		var timePattern = (patterns.length > 1 ? patterns[1] : "");
	
		if(datePattern.length > 0){
			var dateSeparator = "";
			var pos           = datePattern.indexOf("/");
			
			if(pos < 0){
				pos = datePattern.indexOf(".");
				
				if(pos < 0)
					pos = datePattern.indexOf("-");
			}
			
			if(pos >= 0){
				dateSeparator = datePattern.substring(pos, pos + 1);
			
				var dateValueParts   = dateValue.split(dateSeparator);
				var datePatternParts = datePattern.split(dateSeparator);
				
				if(datePatternParts.length == dateValueParts.length){
					var datePos          = -1;
					var monthPos         = -1;
					var yearPos          = -1;
					
					for(index = 0 ; index < datePatternParts.length ; index++){
						if(datePatternParts[index].indexOf("d") == 0)
							datePos = index;
						else if(datePatternParts[index].indexOf("M") == 0)
							monthPos = index;
						else if(datePatternParts[index].indexOf("y") == 0)
							yearPos = index;
					}
	
					if(datePos >= 0)
						currentDate.setDate(dateValueParts[datePos]);
				
					if(monthPos >= 0)
						currentDate.setMonth(dateValueParts[monthPos] - 1);
				
					if(yearPos >= 0)
						currentDate.setYear(dateValueParts[yearPos]);
				}
			}
		}
		
		if(timeValue.length > 0){
			var timeSeparator    = ":";
			var timeValueParts   = timeValue.split(timeSeparator);
			var timePatternParts = timePattern.split(timeSeparator);
			
			if(timePatternParts.length == timeValueParts.length){
				var hoursPos         = -1;
				var minutesPos       = -1;
				var secondsPos       = -1;
				var millisecondsPos  = -1;
				
				for(index = 0 ; index < timePatternParts.length ; index++){
					if(timePatternParts[index].indexOf("h") == 0 || timePatternParts[index].indexOf("H") == 0)
						hoursPos = index;
					else if(timePatternParts[index].indexOf("m") == 0)
						minutesPos = index;
					else if(timePatternParts[index].indexOf("s") == 0)
						secondsPos = index;
					else if(timePatternParts[index].indexOf("S") == 0)
						millisecondsPos = index;
				}
	
				if(hoursPos >= 0)
					currentDate.setHours(timeValueParts[hoursPos]);
				
				if(minutesPos >= 0)
					currentDate.setMinutes(timeValueParts[minutesPos]);
	
				if(secondsPos >= 0)
					currentDate.setSeconds(timeValueParts[secondsPos]);
	
				if(millisecondsPos >= 0)
					currentDate.setMilliseconds(timeValueParts[millisecondsPos]);
			}
		}
		
		return currentDate;
	}		
}

/**
 * Renderiza o componente.
 * 
 * @param name String contendo o identificador do componente.
 */
function renderCalendar(name){
	var calendar        = document.getElementById(name + ".calendar");
	var calendarDays    = document.getElementById(name + ".calendarDays");
	var calendarDisplay = document.getElementById(name + ".calendarDisplay");
	var currentDate     = parseDateProperty(name);
	
	updateDateProperty(name, currentDate);
	
	var firstDate = new Date(currentDate.getFullYear(), currentDate.getMonth(), 1);
	
	if(calendar && calendarDays && calendarDisplay && currentDate && firstDate){
		var weekDay = firstDate.getDay();
		var rows    = 40;
		var html    = "";
		var date    = null;
		var cont    = null;
		
		html += monthNames[currentDate.getMonth()];
		html += ", ";
		html += currentDate.getFullYear();
		
		calendarDisplay.innerHTML = html;
		
		html  = "";
		html += "<table>";
		html += "<tr>";

		for(cont = 0 ; cont < 7 ; cont++){
			html += "<td class=\"calendarWeekHeader\">";
			html += weekNames[cont].substring(0, 1);
			html += "</td>";
		}

		html += "</tr><tr>";	
		
		for(cont = 0 ; cont < weekDay ; cont++)
			html += "<td></td>";
		
		rows -= cont;
		cont  = 1;	
		
		while(cont <= rows){
			date = new Date(currentDate.getFullYear(), currentDate.getMonth(), cont);

			if(date.getMonth() == currentDate.getMonth()){
				html += "<td class=\"";
				
				if(cont == currentDate.getDate())
					html += "currentCalendarDay";
				else
					html += "calendarDay";
					
				html += "\"";
				html += " id=\"day";
				html += cont;
				html += "\" onClick=\"updateCurrentCalendarDay('";
				html += name;
				html += "', this)\" onMouseOut=\"unselectCalendarDay('";
				html += name;
				html += "', this)\" onMouseOver=\"selectCalendarDay('";
				html += name;
				html += "', this)\">";
				html += date.getDate();
				html += "</td>";

				if(((cont + weekDay) % 7) == 0)
					html += "</tr><tr>";
			}
			else
				break;

			cont++;
		}
		
		if(rows > 35){
			html += "</tr>";
			html += "<tr>";
			html += "<td>&nbsp;</td>";
		}

		html += "</tr>";
		html += "</table>";
		
		calendarDays.innerHTML = html;
	}
}

/**
 * Seleciona um dia específico do calendário.
 * 
 * @param name String contendo o identificador do componente.
 * @param calendarDay Objeto que define o dia desejado.
 */
function selectCalendarDay(name, calendarDay){
	var currentDate = parseDateProperty(name);
	
	if(calendarDay.innerHTML != currentDate.getDate())
		if(calendarDay)
			changeStyle(calendarDay, "calendarSelectedDay");
}

/**
 * Exibe/Esconde o calendário.
 * 
 * @param name String contendo o identificador do componente.
 */
function showHideCalendar(name){
	var calendar = document.getElementById(name + ".calendar");
	
	if(calendar){
		if(calendar.style.visibility == "HIDDEN"){
			renderCalendar(name);
	
			calendar.style.visibility = "VISIBLE";
		}
		else
			calendar.style.visibility = "HIDDEN";
	}
}

/**
 * Deseleciona um dia específico do calendário.
 * 
 * @param name String contendo o identificador do componente.
 * @param calendarDay Objeto que define o dia desejado.
 */
function unselectCalendarDay(name, calendarDay){
	var currentDate = parseDateProperty(name);
	
	if(calendarDay.innerHTML != currentDate.getDate())
		if(calendarDay)
			changeStyle(calendarDay, "calendarDay");
}

/**
 * Atualiza o dia selecionado a partir do texto digitado.
 * 
 * @param name String contendo o identificador do componente.
 * @param day Objeto que define o dia desejado.
 */
function updateCurrentCalendarDay(name, day){
	var currentDate = parseDateProperty(name);
	
	if(currentDate){
		var dayValue  = day.innerHTML;
		var dayObject = document.getElementById("day" + currentDate.getDate());
	
		if(dayObject && currentDate){
			changeStyle(dayObject, "calendarDay");
	
			currentDate.setDate(dayValue);
	
			dayObject = document.getElementById("day" + dayValue);
			
			if(dayObject){
				changeStyle(dayObject, "currentCalendarDay");
	
				updateDateProperty(name, currentDate);
				
				showHideCalendar(name);
			}
		}
	}
}

/**
 * Atualiza o texto da data/hora a partir do dia selecionado.
 * 
 * @param name String contendo o identificador do componente.
 * @param currentDate Instância do objeto de data/hora desejado. 
 */
function updateDateProperty(name, currentDate){
	var property        = document.getElementById(name);
	var propertyPattern = document.getElementById(name + ".pattern");
	
	if(property && propertyPattern){
		var date          = "" + currentDate.getDate();
		var month         = "" + (currentDate.getMonth() + 1);
		var year          = "" + currentDate.getFullYear();
		var hours         = "" + currentDate.getHours();
		var minutes       = "" + currentDate.getMinutes();
		var seconds       = "" + currentDate.getSeconds();
		var milliseconds  = "" + currentDate.getMilliseconds();
	    var pattern       = propertyPattern.value; 
		var patterns      = pattern.split(" ");
		var datePattern   = patterns[0];
		var timePattern   = (patterns.length > 1 ? patterns[1] : "");
		var value         = pattern;
		
		if(datePattern.length > 0){
			var dateSeparator = "";
			var pos           = datePattern.indexOf("/");
		
			if(pos < 0){
				pos = datePattern.indexOf(".");
				
				if(pos < 0)
					pos = datePattern.indexOf("-");
			}
				
			if(pos >= 0){
				dateSeparator = datePattern.substring(pos, pos + 1);
	
				var datePatternParts = datePattern.split(dateSeparator);
				
				for(index = 0 ; index < datePatternParts.length ; index++){
					if(datePatternParts[index].indexOf("d") == 0){
						if(datePatternParts[index].length > date.length)
							date = replicate("0", datePatternParts[index].length - date.length) + date;
						
						value = replaceAll(value, datePatternParts[index], date);
					}
					else if(datePatternParts[index].indexOf("M") == 0){
						if(datePatternParts[index].length > month.length)
							month = replicate("0", datePatternParts[index].length - month.length) + month;
						
						value = replaceAll(value, datePatternParts[index], month);
					}
					else if(datePatternParts[index].indexOf("y") == 0){
						year = year.substring(4 - datePatternParts[index].length, year.length);
						
						if(datePatternParts[index].length > year.length)
							year  = replicate("0", datePatternParts[index].length - year.length) + year;
						
						value = replaceAll(value, datePatternParts[index], year);
					}
				}
			}
		}
		
		if(timePattern.length > 0){
			var timeSeparator    = ":";
			var timePatternParts = timePattern.split(timeSeparator);
				
			for(index = 0 ; index < timePatternParts.length ; index++){
				if(timePatternParts[index].indexOf("h") == 0 || timePatternParts[index].indexOf("H") == 0){
					if(timePatternParts[index].length > hours.length)
						hours = replicate("0", timePatternParts[index].length - hours.length) + hours;
					
					value = replaceAll(value, timePatternParts[index], hours);
				}
				else if(timePatternParts[index].indexOf("m") == 0){
					if(timePatternParts[index].length > minutes.length)
						minutes = replicate("0", timePatternParts[index].length - minutes.length) + minutes;
					
					value = replaceAll(value, timePatternParts[index], minutes);
				}
				else if(timePatternParts[index].indexOf("s") == 0){
					if(timePatternParts[index].length > seconds.length)
						seconds = replicate("0", timePatternParts[index].length - seconds.length) + seconds;
					
					value = replaceAll(value, timePatternParts[index], seconds);
				}
				else if(timePatternParts[index].indexOf("S") == 0){
					if(timePatternParts[index].length > milliseconds.length)
						milliseconds = replicate("0", timePatternParts[index].length - milliseconds.length) + milliseconds;
					
					value = replaceAll(value, timePatternParts[index], milliseconds);
				}
			}
		}
		
		var oldValue = property.value;

		if(oldValue.indexOf("PM") >= 0)
			value = replaceAll(value, "a", "PM");
		else
			value = replaceAll(value, "a", "AM");
		
		property.value = value;

		if(oldValue != value){
			var propertyOnChange = property.onchange;
		
			if(propertyOnChange)
				propertyOnChange();
		}
		
		property.focus();
	}
}