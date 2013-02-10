package br.com.concepting.framework.util;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import br.com.concepting.framework.util.helpers.DateTime;
import br.com.concepting.framework.util.types.DateFieldType;

/**
 * Classe utilit�ria respons�vel por manipular datas/hor�rios.
 *
 * @author fvilarinho
 * @since 1.0
 */
public class DateTimeUtil{
    /**
     * Efetua a formata��o de uma data a partir de uma m�scara de formata��o.
     *
     * @param date Inst�ncia da data desejada. 
     * @param pattern String contendo a m�scara de formata��o.
     * @return String contendo a data formatada.
     */
    public static String format(Date date, String pattern){
        return format(date, pattern, LanguageUtil.getDefaultLanguage());
    } 

    /**
     * Efetua a formata��o de uma data a partir de uma m�scara de formata��o.
     *
     * @param date Inst�ncia da data desejada. 
     * @param pattern String contendo a m�scara de formata��o.
     * @param language Inst�ncia contendo as configura��es do idioma desejadas.
     * @return String contendo a data formatada.
     */
	public static String format(Date date, String pattern, Locale language){
		SimpleDateFormat formatter = new SimpleDateFormat(pattern, language);

		formatter.setLenient(false);

		return formatter.format(date).toString();
	} 

    /**
     * Efetua a formata��o de uma data.
     *
     * @param date Inst�ncia da data desejada. 
     * @return String contendo a data formatada.
     */
	public static String format(Date date){
        return format(date, LanguageUtil.getDefaultLanguage());
	}

    /**
     * Efetua a formata��o de uma data a partir de um idioma espec�fico.
     *
     * @param date String contendo a data desejado. 
     * @param language Inst�ncia contendo as configura��es do idioma desejadas.
     * @return Inst�ncia contendo a data/hor�rio ap�s processamento.
     */
	public static String format(Date date, Locale language){
        return format(date, (date instanceof DateTime), language);
	}
    
    /**
     * Efetua a formata��o de uma data/hor�rio a partir de um idioma espec�fico.
     *
     * @param date String contendo a data/hor�rio desejado.
     * @param isTime Indica se � uma data/hor�rio ou simplesmente uma data. 
     * @param language Inst�ncia contendo as configura��es do idioma desejadas.
     * @return Inst�ncia contendo a data/hor�rio ap�s processamento.
     */
    private static String format(Date date, Boolean isTime, Locale language){
        SimpleDateFormat formatter = new SimpleDateFormat(getDefaultPattern(isTime, language));

        formatter.setLenient(false);

        return formatter.format(date);
    }

    /**
	 * Efetua o parse de uma string que cont�m uma data/hor�rio formatado.
	 *
	 * @param value String contendo a data/hor�rio desejado. 
	 * @return Inst�ncia contendo a data/hor�rio ap�s processamento.
	 * @throws ParseException
	 */
	public static <D extends Date> D parse(String value) throws ParseException{
		return parse(value, LanguageUtil.getDefaultLanguage());
	}

	/**
	 * Efetua o parse de uma string que cont�m uma data/hor�rio formatado.
	 *
	 * @param value String contendo a data/hor�rio desejado. 
	 * @param pattern String contendo a m�scara de formata��o.
	 * @return Inst�ncia contendo a data/hor�rio ap�s processamento.
	 * @throws ParseException
	 */
	public static <D extends Date> D parse(String value, String pattern) throws ParseException{
		SimpleDateFormat parser = new SimpleDateFormat(pattern);

		parser.setLenient(false);

		Calendar result         = Calendar.getInstance();
		Calendar calendarBuffer = Calendar.getInstance();
		
		calendarBuffer.setTime(parser.parse(value));
		
		Integer buffer = calendarBuffer.get(Calendar.DAY_OF_MONTH);
		
		if(buffer > 0)
			result.set(Calendar.DAY_OF_MONTH, buffer);
		
		buffer = calendarBuffer.get(Calendar.MONTH);
		if(buffer >= 0)
			result.set(Calendar.MONTH, buffer);

		buffer = calendarBuffer.get(Calendar.YEAR);
		if(buffer != 1970 && buffer > 0)
			result.set(Calendar.YEAR, buffer);

		buffer = calendarBuffer.get(Calendar.HOUR_OF_DAY);
		if(buffer >= 0)
			result.set(Calendar.HOUR_OF_DAY, buffer);

		buffer = calendarBuffer.get(Calendar.MINUTE);
		if(buffer >= 0)
			result.set(Calendar.MINUTE, buffer);

		buffer = calendarBuffer.get(Calendar.SECOND);
		if(buffer >= 0)
			result.set(Calendar.SECOND, buffer);

		buffer = calendarBuffer.get(Calendar.MILLISECOND);
		if(buffer >= 0)
			result.set(Calendar.MILLISECOND, buffer);

		return (D)result.getTime();
	}

	/**
	 * Efetua o parse de uma string que cont�m uma data/hor�rio formatado.
	 *
	 * @param value String contendo a data/hor�rio desejado. 
	 * @param language Inst�ncia contendo as configura��es do idioma desejadas.
	 * @return Inst�ncia contendo a data/hor�rio ap�s processamento.
	 * @throws ParseException
	 */
	public static <D extends Date> D parse(String value, Locale language) throws ParseException{
		SimpleDateFormat parser = null;
		
		try{
     		parser = new SimpleDateFormat(getDefaultDateTimePattern(language));
     
     		parser.setLenient(false);
     
     		return (D)parser.parse(value);
		}
		catch(Throwable e){
		    if(parser != null){
    			parser.applyPattern(getDefaultDatePattern(language));
    			
    			return (D) parser.parse(value);
		    }
		    
		    return null;
		}
	}

	/**
	 * Retorna a m�scara de formata��o default.
	 *
	 * @param language Inst�ncia contendo as configura��es do idioma desejadas.
	 * @return String contendo a m�scara de formata��o.
	 */
	public static String getDefaultDatePattern(Locale language){
		return getDefaultPattern(false, language);
	}
	
    /**
     * Retorna a m�scara de formata��o default.
     *
     * @param language Inst�ncia contendo as configura��es do idioma desejadas.
     * @return String contendo a m�scara de formata��o.
     */
    public static String getDefaultDateTimePattern(Locale language){
        return getDefaultPattern(true, language);
    }

    /**
	 * Retorna a m�scara de formata��o default.
	 *
	 * @param isTime Indica que a m�scara deve considerar um hor�rio.
	 * @param language Inst�ncia contendo as configura��es do idioma desejadas.
	 * @return String contendo a m�scara de formata��o.
	 */
	private static String getDefaultPattern(Boolean isTime, Locale language){
		DateFormat formatter = null;
		
		if(isTime)
			formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, language);
		else
			formatter = DateFormat.getDateInstance(DateFormat.SHORT, language);
		
		Integer       pos           = 0;
		String        pattern       = ((SimpleDateFormat)formatter).toPattern();
		String        delimiter     = (pattern.indexOf("-") >= 0 ? "-" : "/");
		String        patterns[]    = StringUtil.split(pattern, delimiter);
		StringBuilder patternBuffer = new StringBuilder();

		patternBuffer.append(patterns[0]);
		patternBuffer.insert(0, StringUtil.replicate(patterns[0].substring(0, 1), 2 - patterns[0].length()));

		patterns[0] = patternBuffer.toString();

		patternBuffer.delete(0, patternBuffer.length());
		patternBuffer.append(patterns[1]);
		patternBuffer.insert(0, StringUtil.replicate(patterns[1].substring(0, 1), 2 - patterns[1].length()));

		patterns[1] = patternBuffer.toString();

		pos = patterns[2].indexOf(" ");
		if(pos < 0)
			pos = patterns[2].length();

		patternBuffer.delete(0, patternBuffer.length());
		
		patternBuffer.append(patterns[2].substring(0, pos));
		patternBuffer.insert(0, StringUtil.replicate(patterns[2].substring(0, 1), 4 - pos));
		patternBuffer.append(patterns[2].substring(pos));

		patterns[2] = patternBuffer.toString();

		patternBuffer.delete(0, patternBuffer.length());
		patternBuffer.append(patterns[0]);
		patternBuffer.append(delimiter);
		patternBuffer.append(patterns[1]);
		patternBuffer.append(delimiter);
		patternBuffer.append(patterns[2]);
		
		return patternBuffer.toString();
	}

	/**
	 * Indica se um ano � bissexto.
	 *
	 * @param date Inst�ncia da data/hor�rio desejado.
	 * @return True/False.
	 */
	public static Boolean isLeapYear(Date date){
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		return new GregorianCalendar().isLeapYear(calendar.get(Calendar.YEAR));
	}

	/**
	 * Retorna o �ltimo dia do m�s.
	 *
	 * @param date Inst�ncia da data/hor�rio desejado.
	 * @return Valor inteiro contendo o �ltimo dia do m�s.
	 */
	public static Integer getLastDayOfMonth(Date date){
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * Retorna um array contendo os nomes dos meses do ano utilizando o idioma padr�o.
	 *
	 * @return Array contendo os nomes dos meses do ano.
	 */
	public static String[] getMonthsNames(){
		return getMonthsNames(LanguageUtil.getDefaultLanguage());
	}
	
	/**
	 * Retorna um array contendo os nomes dos meses do ano utilizando um idioma espec�fico.
	 *
	 * @param language Inst�ncia contendo as configura��es do idioma desejado.
	 * @return Array contendo os nomes dos meses do ano.
	 */
	public static String[] getMonthsNames(Locale language){
		DateFormatSymbols symbols = new DateFormatSymbols((language == null ? LanguageUtil.getDefaultLanguage() : language));

		return symbols.getMonths();
	}
	 
	/**
	 * Retorna o nome de um m�s.
	 *
	 * @param date Inst�ncia contendo a data desejada.
	 * @return String contendo o nome do m�s utilizando as configura��es do idioma atual.
	 */
	public static String getMonthName(Date date){
		return getMonthName(date, LanguageUtil.getDefaultLanguage());
	}

	/**
	 * Retorna o nome de um m�s.
	 *
	 * @param date Inst�ncia contendo a data desejada.
	 * @param language Inst�ncia contendo as configura��es de idioma desejado.
	 * @return String contendo o nome do m�s.
	 */
	public static String getMonthName(Date date, Locale language){
		Calendar calendar      = Calendar.getInstance();
		String   monthsNames[] = getMonthsNames(language); 
		
		calendar.setTime(date);

		return monthsNames[calendar.get(Calendar.MONTH)];
	}

	/**
	 * Retorna a diferen�a entre duas datas/hor�rios.
	 *
	 * @param date1 Inst�ncia da data/hor�rio desejado.
	 * @param date2 Inst�ncia da data/hor�rio desejado.
	 * @param dateField Constante que define a propriedade desejada.
	 * @return Valor inteiro contendo a diferen�a.
	 */
	public static Long diff(Date date1, Date date2, DateFieldType dateField){
		if(dateField == DateFieldType.MILISECONDS)
			return (date1.getTime() - date2.getTime());

		Date dateBuffer1 = date1;
		Date dateBuffer2 = date2;

		if(dateField != DateFieldType.HOURS && dateField != DateFieldType.MINUTES && dateField != DateFieldType.SECONDS){
			Calendar calendar = Calendar.getInstance();

			calendar.setTime(dateBuffer1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			
			dateBuffer1 = calendar.getTime();

			calendar.setTime(dateBuffer2);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			
			dateBuffer2 = calendar.getTime();
		}

		return (((dateBuffer1.getTime() - dateBuffer2.getTime()) / dateField.toInteger()) / 1000);
	}

	/**
	 * Calcula a idade a partir da data/hor�rio de nascimento.
	 *
	 * @param birthDate Inst�ncia da data/hor�rio de nascimento.
	 * @return Valor inteiro contendo a idade.
	 */
	public static Integer calculateAge(Date birthDate){
		Calendar calendar = Calendar.getInstance();
		Date     now      = new Date();
		Long     age      = diff(now, birthDate, DateFieldType.YEAR);

		calendar.setTime(birthDate);

		Integer monthBirth   = calendar.get(Calendar.MONTH);
		Integer dateBirth    = calendar.get(Calendar.DATE);
		Integer hoursBirth   = calendar.get(Calendar.HOUR_OF_DAY);
		Integer minutesBirth = calendar.get(Calendar.MINUTE);
		Integer secondsBirth = calendar.get(Calendar.SECOND);

		calendar.setTime(now);

		Integer monthNow   = calendar.get(Calendar.MONTH);
		Integer dateNow    = calendar.get(Calendar.DATE);
		Integer hoursNow   = calendar.get(Calendar.HOUR_OF_DAY);
		Integer minutesNow = calendar.get(Calendar.MINUTE);
		Integer secondsNow = calendar.get(Calendar.SECOND);

		if(monthNow.compareTo(monthBirth) < 0)
			age--;
		else if(monthNow.equals(monthBirth)){
			if(dateNow < dateBirth)
				age--;
			else if(dateNow.equals(dateBirth)){
				if(hoursNow < hoursBirth)
					age--;
				else if(hoursNow.equals(hoursBirth)){
					if(minutesNow < minutesBirth)
						age--;
					else if(minutesNow.equals(minutesBirth)){
						if(secondsNow < secondsBirth)
							age--;
					}
				}
			}
		}

		return age.intValue();
	}

	/**
	 * Adiciona um valor de uma propriedade da data/hor�rio.
	 *
	 * @param date Inst�ncia da data/hor�rio desejado.
	 * @param value Valor inteiro contendo o valor a ser acrescentado.
	 * @param dateField Constante que define a propriedade desejada.
	 * @return Inst�ncia da data/hor�rio ap�s processamento.
	 */
	public static <D extends Date> D add(D date, Integer value, DateFieldType dateField){
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		switch(dateField){
			case DAY: {
				calendar.add(Calendar.DATE, value);

				break;
			}
			case MONTH: {
				calendar.add(Calendar.MONTH, value);

				break;
			}
			case YEAR: {
				calendar.add(Calendar.YEAR, value);

				break;
			}
			case HOURS: {
				calendar.add(Calendar.HOUR, value);

				break;
			}
			case MINUTES: {
				calendar.add(Calendar.MINUTE, value);

				break;
			}
			case SECONDS: {
				calendar.add(Calendar.SECOND, value);

				break;
			}
			case MILISECONDS: {
				calendar.add(Calendar.MILLISECOND, value);

				break;
			}
		}

		return (D)calendar.getTime();
	}

	/**
	 * Subtrai um valor de uma propriedade da data/hor�rio.
	 *
	 * @param date Inst�ncia da data/hor�rio desejado.
	 * @param value Valor inteiro contendo o valor a ser acrescentado.
	 * @param dateField Constante que define a propriedade desejada.
	 * @return Inst�ncia da data/hor�rio ap�s processamento.
	 */
	public static <D extends Date> D subtract(D date, Integer value, DateFieldType dateField){
		return add(date, -value, dateField);
	}
	
	/**
	 * Formata um valor em milisegundos. O resultado da formata��o utilizar� a m�scara 
	 * HH:mm:ss.
	 *
	 * @param milliseconds Valor inteiro contendo os milisegundos.
	 * @return Valor formatado.
	 */
	public static String format(Long milliseconds){   
		StringBuilder result  = new StringBuilder();   
        Integer       seconds = (int)(milliseconds / 1000);
        Integer       hours   = 0;
        Integer       minutes = 0;
        
        if(milliseconds != 0){   
            hours   = seconds / 3600;
            seconds = seconds % 3600;
            minutes = seconds / 60;
            seconds = seconds % 60;   
        }
        
        if(hours < 10)
        	result.append("0");
        
        result.append(hours);
        result.append(":");
        
        if(minutes < 10)
        	result.append("0");
        
        result.append(minutes);
        result.append(":");

        if(seconds < 10)
        	result.append("0");
        
        result.append(seconds);

        return result.toString();   
    }
}