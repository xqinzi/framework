package br.com.concepting.framework.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.model.util.PropertyUtil;
import br.com.concepting.framework.util.helpers.Currency;

/**
 * Classe utilit�ria para manipula��o de valores num�ricos.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class NumberUtil{
	private static Map<Class, String>   numberPatterns = null;
	private static Map<Class, Number[]> numberRange    = null;

	static{
		numberPatterns = new LinkedHashMap<Class, String>();
		numberPatterns.put(Byte.class, "###");
		numberPatterns.put(Short.class, "##,###");
		numberPatterns.put(Integer.class, "#,###,###,###");
		numberPatterns.put(Long.class, "#,###,###,###,###,###,###");
		numberPatterns.put(BigInteger.class, "#,###,###,###,###,###,###");
		numberPatterns.put(Float.class, "#,###,###,##0.00");
		numberPatterns.put(Double.class, "#,###,###,###,###,###,##0.00");
		numberPatterns.put(BigDecimal.class, "#,###,###,###,###,###,##0.00");

		numberRange = new LinkedHashMap<Class, Number[]>();
		numberRange.put(Byte.class, new Number[]{Byte.MIN_VALUE, Byte.MAX_VALUE});
		numberRange.put(Short.class, new Number[]{Short.MIN_VALUE, Short.MAX_VALUE});
		numberRange.put(Integer.class, new Number[]{Integer.MIN_VALUE, Integer.MAX_VALUE});
		numberRange.put(Long.class, new Number[]{Long.MIN_VALUE, Long.MAX_VALUE});
		numberRange.put(BigInteger.class, new Number[]{Long.MIN_VALUE, Long.MAX_VALUE});
		numberRange.put(Float.class, new Number[]{Float.MIN_VALUE, Float.MAX_VALUE});
		numberRange.put(Double.class, new Number[]{Double.MIN_VALUE, Double.MAX_VALUE});
		numberRange.put(BigDecimal.class, new Number[]{Double.MIN_VALUE, Double.MAX_VALUE});
	}

	/**
	 * Converte uma string em um valor em ponto flutuante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @param pattern String contendo a m�scara de convers�o.
	 * @return Inst�ncia contendo o valor em ponto flutuante.
	 * @throws ParseException
	 */
	public static Float parseFloat(String value, String pattern) throws ParseException{
		return parse(Float.class, value, pattern, LanguageUtil.getDefaultLanguage());
	}

	/**
	 * Converte uma string em um valor em ponto flutuante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @param pattern String contendo a m�scara de convers�o.
	 * @param language Inst�ncia contendo as propriedades do idioma desejado.
	 * @return Inst�ncia contendo o valor em ponto flutuante.
	 * @throws ParseException
	 */
	public static Float parseFloat(String value, String pattern, Locale language) throws ParseException{
		return parse(Float.class, value, pattern, language);
	}
	
	/**
	 * Converte uma string em um valor em ponto flutuante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @param language Inst�ncia contendo as propriedades do idioma desejado.
	 * @return Inst�ncia contendo o valor em ponto flutuante.
	 * @throws ParseException
	 */
	public static Float parseFloat(String value, Locale language) throws ParseException{
		return parse(Float.class, value, language);
	}

	/**
	 * Converte uma string em um valor em ponto flutuante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @return Inst�ncia contendo o valor em ponto flutuante.
	 * @throws ParseException
	 */
	public static Float parseFloat(String value) throws ParseException{
		return parse(Float.class, value);
	}

	/**
	 * Converte uma string em um valor em ponto flutuante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @param pattern String contendo a m�scara de convers�o.
	 * @param language Inst�ncia contendo as propriedades do idioma desejado.
	 * @return Inst�ncia contendo o valor em ponto flutuante.
	 * @throws ParseException
	 */
	public static Double parseDouble(String value, String pattern, Locale language) throws ParseException{
		return parse(Double.class, value, pattern, language);
	}

	/**
	 * Converte uma string em um valor em ponto flutuante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @param pattern String contendo a m�scara de convers�o.
	 * @return Inst�ncia contendo o valor em ponto flutuante.
	 * @throws ParseException
	 */
	public static Double parseDouble(String value, String pattern) throws ParseException{
		return parse(Double.class, value, pattern, LanguageUtil.getDefaultLanguage());
	}

	/**
	 * Converte uma string em um valor em ponto flutuante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @param language Inst�ncia contendo as propriedades do idioma desejado.
	 * @return Inst�ncia contendo o valor em ponto flutuante.
	 * @throws ParseException
	 */
	public static Double parseDouble(String value, Locale language) throws ParseException{
		return parse(Double.class, value, language);
	}

	/**
	 * Converte uma string em um valor em ponto flutuante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @return Inst�ncia contendo o valor em ponto flutuante.
	 * @throws ParseException
	 */
	public static Double parseDouble(String value) throws ParseException{
		return parse(Double.class, value);
	}

	/**
	 * Converte uma string em um valor em ponto flutuante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @param pattern String contendo a m�scara de convers�o.
	 * @param language Inst�ncia contendo as propriedades do idioma desejado.
	 * @return Inst�ncia contendo o valor em ponto flutuante.
	 * @throws ParseException
	 */
	public static BigDecimal parseBigDecimal(String value, String pattern, Locale language) throws ParseException{
		return parse(BigDecimal.class, value, pattern, language);
	}

	/**
	 * Converte uma string em um valor em ponto flutuante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @param pattern String contendo a m�scara de convers�o.
	 * @return Inst�ncia contendo o valor em ponto flutuante.
	 * @throws ParseException
	 */
	public static BigDecimal parseBigDecimal(String value, String pattern) throws ParseException{
		return parse(BigDecimal.class, value, pattern, LanguageUtil.getDefaultLanguage());
	}

	/**
	 * Converte uma string em um valor em ponto flutuante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @param language Inst�ncia contendo as propriedades do idioma desejado.
	 * @return Inst�ncia contendo o valor em ponto flutuante.
	 * @throws ParseException
	 */
	public static BigDecimal parseBigDecimal(String value, Locale language) throws ParseException{
		return parse(BigDecimal.class, value, language);
	}

	/**
	 * Converte uma string em um valor em ponto flutuante.
	 * 
	 * @param value String contendo o valor desejado.
	 * @return Inst�ncia contendo o valor em ponto flutuante.
	 * @throws ParseException
	 */
	public static BigDecimal parseBigDecimal(String value) throws ParseException{
		return parse(BigDecimal.class, value);
	}

	/**
	 * Converte uma string em um valor inteiro.
	 * 
	 * @param value String contendo o valor desejado.
	 * @param pattern String contendo a m�scara de convers�o.
	 * @param language Inst�ncia contendo as propriedades do idioma desejado.
	 * @return Inst�ncia contendo o valor inteiro.
	 * @throws ParseException
	 */
	public static Short parseShort(String value, String pattern, Locale language) throws ParseException{
		return parse(Short.class, value, pattern, language);
	}

	/**
	 * Converte uma string em um valor inteiro.
	 * 
	 * @param value String contendo o valor desejado.
	 * @param pattern String contendo a m�scara de convers�o.
	 * @return Inst�ncia contendo o valor inteiro.
	 * @throws ParseException
	 */
	public static Short parseShort(String value, String pattern) throws ParseException{
		return parse(Short.class, value, pattern, LanguageUtil.getDefaultLanguage());
	}

	/**
	 * Converte uma string em um valor inteiro.
	 * 
	 * @param value String contendo o valor desejado.
	 * @param language Inst�ncia contendo as propriedades do idioma desejado.
	 * @return Inst�ncia contendo o valor inteiro.
	 * @throws ParseException
	 */
	public static Short parseShort(String value, Locale language) throws ParseException{
		return parse(Short.class, value, language);
	}

	/**
	 * Converte uma string em um valor inteiro.
	 * 
	 * @param value String contendo o valor desejado.
	 * @return Inst�ncia contendo o valor inteiro.
	 * @throws ParseException
	 */
	public static Short parseShort(String value) throws ParseException{
		return parse(Short.class, value);
	}

	/**
	 * Converte uma string em um valor inteiro.
	 * 
	 * @param value String contendo o valor desejado.
	 * @return Inst�ncia contendo o valor inteiro.
	 * @throws ParseException
	 */
	public static Byte parseByte(String value) throws ParseException{
		return parse(Byte.class, value);
	}

	/**
	 * Converte uma string em um valor inteiro.
	 * 
	 * @param value String contendo o valor desejado.
	 * @param pattern String contendo a m�scara de convers�o.
	 * @return Inst�ncia contendo o valor inteiro.
	 * @throws ParseException
	 */
	public static Long parseLong(String value, String pattern) throws ParseException{
		return parse(Long.class, value, pattern, LanguageUtil.getDefaultLanguage());
	}

	/**
	 * Converte uma string em um valor inteiro.
	 * 
	 * @param value String contendo o valor desejado.
	 * @param pattern String contendo a m�scara de convers�o.
	 * @param language Inst�ncia contendo as propriedades do idioma desejado.
	 * @return Inst�ncia contendo o valor inteiro.
	 * @throws ParseException
	 */
	public static Long parseLong(String value, String pattern, Locale language) throws ParseException{
		return parse(Long.class, value, pattern, language);
	}

	/**
	 * Converte uma string em um valor inteiro.
	 * 
	 * @param value String contendo o valor desejado.
	 * @param language Inst�ncia contendo as propriedades do idioma desejado.
	 * @return Inst�ncia contendo o valor inteiro.
	 * @throws ParseException
	 */
	public static Long parseLong(String value, Locale language) throws ParseException{
		return parse(Long.class, value, language);
	}

	/**
	 * Converte uma string em um valor inteiro.
	 * 
	 * @param value String contendo o valor desejado.
	 * @return Inst�ncia contendo o valor inteiro.
	 * @throws ParseException
	 */
	public static Long parseLong(String value) throws ParseException{
		return parse(Long.class, value);
	}

	/**
	 * Converte uma string em um valor inteiro.
	 * 
	 * @param value String contendo o valor desejado.
	 * @param pattern String contendo a m�scara de convers�o.
	 * @param language Inst�ncia contendo as propriedades do idioma desejado.
	 * @return Inst�ncia contendo o valor inteiro.
	 * @throws ParseException
	 */
	public static BigInteger parseBigInteger(String value, String pattern, Locale language) throws ParseException{
		return parse(BigInteger.class, value, pattern, language);
	}

	/**
	 * Converte uma string em um valor inteiro.
	 * 
	 * @param value String contendo o valor desejado.
	 * @param pattern String contendo a m�scara de convers�o.
	 * @return Inst�ncia contendo o valor inteiro.
	 * @throws ParseException
	 */
	public static BigInteger parseBigInteger(String value, String pattern) throws ParseException{
		return parse(BigInteger.class, value, pattern, LanguageUtil.getDefaultLanguage());
	}

	/**
	 * Converte uma string em um valor inteiro.
	 * 
	 * @param value String contendo o valor desejado.
	 * @param language Inst�ncia contendo as propriedades do idioma desejado.
	 * @return Inst�ncia contendo o valor inteiro.
	 * @throws ParseException
	 */
	public static BigInteger parseBigInteger(String value, Locale language) throws ParseException{
		return parse(BigInteger.class, value, language);
	}

	/**
	 * Converte uma string em um valor inteiro.
	 * 
	 * @param value String contendo o valor desejado.
	 * @return Inst�ncia contendo o valor inteiro.
	 * @throws ParseException
	 */
	public static BigInteger parseBigInteger(String value) throws ParseException{
		return parse(BigInteger.class, value);
	}

	/**
	 * Converte uma string em um valor inteiro.
	 * 
	 * @param value String contendo o valor desejado.
	 * @param pattern String contendo a m�scara de convers�o.
	 * @param language Inst�ncia contendo as propriedades do idioma desejado.
	 * @return Inst�ncia contendo o valor inteiro.
	 * @throws ParseException
	 */
	public static Integer parseInt(String value, String pattern, Locale language) throws ParseException{
		return parse(Integer.class, value, pattern, language);
	}

	/**
	 * Converte uma string em um valor inteiro.
	 * 
	 * @param value String contendo o valor desejado.
	 * @param pattern String contendo a m�scara de convers�o.
	 * @return Inst�ncia contendo o valor inteiro.
	 * @throws ParseException
	 */
	public static Integer parseInt(String value, String pattern) throws ParseException{
		return parse(Integer.class, value, pattern, LanguageUtil.getDefaultLanguage());
	}

	/**
	 * Converte uma string em um valor inteiro.
	 * 
	 * @param value String contendo o valor desejado.
	 * @param language Inst�ncia contendo as propriedades do idioma desejado.
	 * @return Inst�ncia contendo o valor inteiro.
	 * @throws ParseException
	 */
	public static Integer parseInt(String value, Locale language) throws ParseException{
		return parse(Integer.class, value, language);
	}

	/**
	 * Converte uma string em um valor inteiro.
	 * 
	 * @param value String contendo o valor desejado.
	 * @return Inst�ncia contendo o valor inteiro.
	 * @throws ParseException
	 */
	public static Integer parseInt(String value) throws ParseException{
		return parse(Integer.class, value);
	}
    
	/**
	 * Converte um tipo de valor num�rico para outro tipo.
	 * 
	 * @param clazz Tipo do valor num�rico.
	 * @param number Inst�ncia do valor num�rico desejado.
	 * @return Valor num�rico convertido.
	 */
    public static <N extends Number> N convert(Class<N> clazz, Number number){
        if(PropertyUtil.isInteger(clazz))
            return (N)new Integer(number.intValue());
        else if(PropertyUtil.isLong(clazz))
            return (N)new Long(number.longValue());
        else if(PropertyUtil.isByte(clazz))
            return (N)new Byte(number.byteValue());
        else if(PropertyUtil.isShort(clazz))
            return (N)new Short(number.shortValue());
        else if(PropertyUtil.isDouble(clazz))
            return (N)new Double(number.doubleValue());
        else if(PropertyUtil.isFloat(clazz))
            return (N)new Float(number.floatValue());
        else if(PropertyUtil.isBigDecimal(clazz))
            return (N)new BigDecimal(number.doubleValue());
        else if(PropertyUtil.isBigInteger(clazz))
            return (N)new BigInteger(number.toString());
        else
            return (N)number;
    }

    /**
	 * Converte uma string em um valor num�rico.
	 * 
	 * @param clazz Classe num�rica desejada.
	 * @param value String contendo o valor desejado.
	 * @return Inst�ncia contendo o valor num�rico.
	 * @throws ParseException
	 */
	public static <N extends Number> N parse(Class<N> clazz, String value) throws ParseException{
		Locale language = LanguageUtil.getDefaultLanguage();

		return parse(clazz, value, language);
	}

	/**
	 * Converte uma string em um valor num�rico.
	 * 
	 * @param clazz Classe num�rica desejada.
	 * @param value String contendo o valor desejado.
	 * @param language Inst�ncia contendo as propriedades do idioma desejado.
	 * @return Inst�ncia contendo o valor num�rico.
	 * @throws ParseException
	 */
	public static <N extends Number> N parse(Class<N> clazz, String value, Locale language) throws ParseException{
		NumberFormat parser = NumberFormat.getInstance(language);
		
		return convert(clazz, parser.parse(value));
	}

	/**
	 * Converte uma string em um valor num�rico.
	 * 
	 * @param clazz Classe num�rica desejada.
	 * @param value String contendo o valor desejado.
	 * @param pattern String contendo a m�scara de convers�o desejada.
	 * @return Inst�ncia contendo o valor num�rico.
	 * @throws ParseException
	 */
	public static <N extends Number> N parse(Class<N> clazz, String value, String pattern) throws ParseException{
		return parse(clazz, value, pattern, LanguageUtil.getDefaultLanguage());
	}

	/**
	 * Converte uma string em um valor num�rico.
	 * 
	 * @param clazz Classe num�rica desejada.
	 * @param value String contendo o valor desejado.
	 * @param pattern String contendo a m�scara de convers�o desejada.
	 * @param language Inst�ncia contendo as propriedades do idioma desejado.
	 * @return Inst�ncia contendo o valor num�rico.
	 * @throws ParseException
	 */
	public static <N extends Number> N parse(Class<N> clazz, String value, String pattern, Locale language) throws ParseException{
		if(pattern.length() == 0)
			return parse(clazz, value, language);
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(language);
		DecimalFormat        parser  = new DecimalFormat(pattern, symbols);
		
		return convert(clazz, parser.parse(value));
	}

    /**
     * Formata um valor num�rico.
     * 
     * @param value Inst�ncia contendo o valor num�rico.
     * @return String contendo o valor num�rico formatado.
     */
    public static String format(Number value){
        return format(value, false, Constants.DEFAULT_NUMBER_PRECISION, LanguageUtil.getDefaultLanguage());
    }

    /**
     * Formata um valor num�rico.
     * 
     * @param value Inst�ncia contendo o valor num�rico.
     * @param language Inst�ncia contendo as propriedades do idioma desejado.
     * @return String contendo o valor num�rico formatado.
     */
    public static String format(Number value, Locale language){
        if(language == null)
            return format(value, false, Constants.DEFAULT_NUMBER_PRECISION, LanguageUtil.getDefaultLanguage());
        
        return format(value, false, Constants.DEFAULT_NUMBER_PRECISION, language);
    }
    
    /**
	 * Formata um valor num�rico.
	 * 
	 * @param value Inst�ncia contendo o valor num�rico.
	 * @param precision Valor inteiro contendo as decimais de precis�o.
	 * @param language Inst�ncia contendo as propriedades do idioma desejado.
	 * @return String contendo o valor num�rico formatado.
	 */
	public static String format(Number value, Integer precision, Locale language){
        if(language == null)
            return format(value, false, precision, LanguageUtil.getDefaultLanguage());

        if(precision == null)
            return format(value, false, Constants.DEFAULT_NUMBER_PRECISION, language);

        return format(value, false, precision, language);
	}

	/**
	 * Formata um valor num�rico.
	 * 
	 * @param value Inst�ncia contendo o valor num�rico.
	 * @param useGroupSeparator Indica se deve usar o caracter separador da parte inteira.
     * @param language Inst�ncia contendo as propriedades do idioma desejado.
	 * @return String contendo o valor num�rico formatado.
	 */
	public static String format(Number value, Boolean useGroupSeparator, Integer precision, Locale language){
		if(language == null)
			return format(value, useGroupSeparator, precision, LanguageUtil.getDefaultLanguage());
		
		if(precision == null)
		    return format(value, useGroupSeparator, Constants.DEFAULT_NUMBER_PRECISION, language);
		
		NumberFormat formatter = null;
		
		if(value instanceof Currency)
		    formatter = NumberFormat.getCurrencyInstance(language);
		else
		    formatter = NumberFormat.getInstance(language);

		formatter.setGroupingUsed(useGroupSeparator);
		formatter.setMaximumFractionDigits(precision);

		return formatter.format(value);
	}

	/**
	 * Formata um valor num�rico.
	 * 
	 * @param value Inst�ncia contendo o valor num�rico.
	 * @param pattern String contendo a m�scara de formata��o.
	 * @return String contendo o valor num�rico formatado.
	 */
	public static String format(Number value, String pattern){
		return format(value, pattern, LanguageUtil.getDefaultLanguage());
	}

	/**
	 * Formata um valor num�rico.
	 * 
	 * @param value Inst�ncia contendo o valor num�rico.
	 * @param pattern String contendo a m�scara de formata��o.
	 * @param language Inst�ncia contendo as propriedades do idioma desejado.
	 * @return String contendo o valor num�rico formatado.
	 */
	public static String format(Number value, String pattern, Locale language){
		if(pattern.length() == 0)
			return format(value, language);
		
		if(language == null)
			return format(value, pattern, LanguageUtil.getDefaultLanguage());
		
		DecimalFormat formatter = new DecimalFormat(pattern, new DecimalFormatSymbols(language));

		return formatter.format(value);
	}

	/**
	 * Retorna uma m�scara de formata��o default de um tipo de valor num�rico.
	 * 
	 * @param clazz Classe do valor num�rico
	 * @param precision Valor inteiro contendo a quantidade de decimais de precis�o.
	 * @return String contendo a m�scara de formata��o.
	 */
	public static String getDefaultPattern(Class clazz, Integer precision){
		String pattern = "";

		if(clazz != null){
			if(clazz.equals(byte.class))
				clazz = Byte.class;
			else if(clazz.equals(short.class))
				clazz = Short.class;
			else if(clazz.equals(int.class))
				clazz = Integer.class;
			else if(clazz.equals(long.class))
				clazz = Long.class;
			else if(clazz.equals(float.class))
				clazz = Float.class;
			else if(clazz.equals(double.class))
				clazz = Double.class;

			pattern = StringUtil.trim(numberPatterns.get(clazz));

			if(clazz.equals(Float.class) || clazz.equals(Double.class) || clazz.equals(BigDecimal.class))
			    pattern = pattern.concat(StringUtil.replicate(pattern, precision - 2));
		}

		return pattern;
	}

	/**
	 * Retorna uma m�scara de formata��o de um tipo de valor num�rico.
	 * 
	 * @param clazz Classe do valor num�rico
	 * @param useGroupSeparator Indica se a m�scara deve conter o caracter separador de 
	 * milhar.
     * @param precision Valor inteiro contendo a quantidade de decimais de precis�o.
	 * @return String contendo a m�scara de formata��o.
	 */
	public static String getDefaultPattern(Class clazz, Boolean useGroupSeparator, Integer precision){
		String pattern = getDefaultPattern(clazz, precision);

		if(!useGroupSeparator)
			pattern = StringUtil.replaceAll(pattern, ",", "");

		return pattern;
	}

	/**
	 * Normaliza uma m�scara de acordo com as propriedades do idioma default.
	 * 
	 * @param pattern String contendo a m�scara desejada.
	 * @return String contendo a m�scara normalizada.
	 */
	public static String normalizePattern(String pattern){
		return normalizePattern(pattern, LanguageUtil.getDefaultLanguage());
	}

	/**
	 * Normaliza uma m�scara de acordo com um idioma espec�fico.
	 * 
	 * @param pattern String contendo a m�scara desejada.
	 * @param language Inst�ncia contendo as propriedades do idioma desejado.
	 * @return String contendo a m�scara normalizada.
	 */
	public static String normalizePattern(String pattern, Locale language){
		DecimalFormatSymbols symbols          = new DecimalFormatSymbols(language);
		String               decimalSeparator = String.valueOf(symbols.getDecimalSeparator());
		String               groupSeparator   = String.valueOf(symbols.getGroupingSeparator());

		pattern = StringUtil.replaceAll(pattern, ".", "a");
		pattern = StringUtil.replaceAll(pattern, ",", "b");
		pattern = StringUtil.replaceAll(pattern, "a", decimalSeparator);
		pattern = StringUtil.replaceAll(pattern, "b", groupSeparator);

		return pattern;
	}

	/**
	 * Retorna array contendo o 'range' de um tipo de n�mero.
	 * O primeiro elemento do array cont�m o valor m�nimo e o segundo elemento cont�m o 
	 * valor m�ximo.
	 * 
	 * @param clazz Classe do n�mero desejado.
	 * @return Array contendo o range.
	 */
	public static Number[] getRange(Class clazz){
		Number range[] = null;

		if(clazz != null){
			if(clazz.equals(byte.class))
				clazz = Byte.class;
			else if(clazz.equals(short.class))
				clazz = Short.class;
			else if(clazz.equals(int.class))
				clazz = Integer.class;
			else if(clazz.equals(long.class))
				clazz = Long.class;
			else if(clazz.equals(float.class))
				clazz = Float.class;
			else if(clazz.equals(double.class))
				clazz = Double.class;

			range = numberRange.get(clazz);
		}

		return range;
	}
	
	/**
	 * Retorna o valor m�nimo suportado por um valor num�rico.
	 * 
	 * @param clazz Classe que define o valor num�rico.
	 * @return Valor num�rico m�nimo suportado.
	 */
	public static Number getMinimumRange(Class clazz){
		Number range[] = getRange(clazz);
		
		return range[0];
	}

	/**
	 * Retorna o valor m�ximo suportado por um valor num�rico.
	 * 
	 * @param clazz Classe que define o valor num�rico.
	 * @return Valor num�rico m�ximo suportado.
	 */
	public static Number getMaximumRange(Class clazz){
		Number range[] = getRange(clazz);
		
		return range[1];
	}

	/**
	 * Converte um valor num�rico para hexadecimal.
	 * 
	 * @param value Inst�ncia contendo o valor num�rico desejado.
	 * @return String contendo o valor num�rico em hexadecimal.
	 */
	public static String toHexadecimal(long value){
		return Long.toHexString(value);
	}

	/**
	 * Converte uma valor hexadecimal em um valor num�rico.
	 * 
	 * @param value String contendo o valor em hexadecimal.
	 * @return Inst�ncia contendo o valor num�rico convertido.
	 */
	public static long fromHexadecimal(String value){
		return Long.parseLong(value, 16);
	}
	
	/**
	 * Efetua a subtra��o entre dois n�meros.
	 * 
	 * @param value1 Valor num�rico 1.
	 * @param value2 Valor n�merico 2.
	 * @return Valor num�rico final.
	 */
	public static Number subtract(Number value1, Number value2){
		if(value1 instanceof Integer || value2 instanceof Integer)
			return (Integer)value1 - (Integer)value2;
		else if(value1 instanceof Long || value2 instanceof Long)
			return (Long)value1 - (Long)value2;
		else if(value1 instanceof Byte || value2 instanceof Byte)
			return (Byte)value1 - (Byte)value2;
		else if(value1 instanceof Short || value2 instanceof Short)
			return (Short)value1 - (Short)value2;
		else if(value1 instanceof Double || value2 instanceof Double)
			return (Double)value1 - (Double)value2;
		else if(value1 instanceof Float || value2 instanceof Float)
			return (Float)value1 - (Float)value2;
		else if(value1 instanceof BigDecimal || value2 instanceof BigDecimal)
			return ((BigDecimal)value1).subtract((BigDecimal)value2);
		else if(value1 instanceof BigInteger || value2 instanceof BigInteger)
			return ((BigInteger)value1).subtract((BigInteger)value2);
		else
			return 0;
	}

	/**
	 * Efetua a soma entre dois n�meros.
	 * 
	 * @param value1 Valor num�rico 1.
	 * @param value2 Valor n�merico 2.
	 * @return Valor num�rico final.
	 */
	public static Number add(Number value1, Number value2){
		if(value1 instanceof Integer || value2 instanceof Integer)
			return (Integer)value1 + (Integer)value2;
		else if(value1 instanceof Long || value2 instanceof Long)
			return (Long)value1 + (Long)value2;
		else if(value1 instanceof Byte || value2 instanceof Byte)
			return (Byte)value1 + (Byte)value2;
		else if(value1 instanceof Short || value2 instanceof Short)
			return (Short)value1 + (Short)value2;
		else if(value1 instanceof Double || value2 instanceof Double)
			return (Double)value1 + (Double)value2;
		else if(value1 instanceof Float || value2 instanceof Float)
			return (Float)value1 + (Float)value2;
		else if(value1 instanceof BigDecimal || value2 instanceof BigDecimal)
			return ((BigDecimal)value1).add((BigDecimal)value2);
		else if(value1 instanceof BigInteger || value2 instanceof BigInteger)
			return ((BigInteger)value1).add((BigInteger)value2);
		else
			return 0;
	}

	/**
	 * Efetua a multiplica��o entre dois n�meros.
	 * 
	 * @param value1 Valor num�rico 1.
	 * @param value2 Valor n�merico 2.
	 * @return Valor num�rico final.
	 */
	public static Number multiply(Number value1, Number value2){
		if(value1 instanceof Integer || value2 instanceof Integer)
			return (Integer)value1 * (Integer)value2;
		else if(value1 instanceof Long || value2 instanceof Long)
			return (Long)value1 * (Long)value2;
		else if(value1 instanceof Byte || value2 instanceof Byte)
			return (Byte)value1 * (Byte)value2;
		else if(value1 instanceof Short || value2 instanceof Short)
			return (Short)value1 * (Short)value2;
		else if(value1 instanceof Double || value2 instanceof Double)
			return (Double)value1 * (Double)value2;
		else if(value1 instanceof Float || value2 instanceof Float)
			return (Float)value1 * (Float)value2;
		else if(value1 instanceof BigDecimal || value2 instanceof BigDecimal)
			return ((BigDecimal)value1).multiply((BigDecimal)value2);
		else if(value1 instanceof BigInteger || value2 instanceof BigInteger)
			return ((BigInteger)value1).multiply((BigInteger)value2);
		else
			return 0;
	}

	/**
	 * Efetua a divis�o entre dois n�meros.
	 * 
	 * @param value1 Valor num�rico 1.
	 * @param value2 Valor n�merico 2.
	 * @return Valor num�rico final.
	 */
	public static Number divide(Number value1, Number value2){
		if(value1 instanceof Integer || value2 instanceof Integer)
			return (Integer)value1 / (Integer)value2;
		else if(value1 instanceof Long || value2 instanceof Long)
			return (Long)value1 / (Long)value2;
		else if(value1 instanceof Byte || value2 instanceof Byte)
			return (Byte)value1 / (Byte)value2;
		else if(value1 instanceof Short || value2 instanceof Short)
			return (Short)value1 / (Short)value2;
		else if(value1 instanceof Double || value2 instanceof Double)
			return (Double)value1 / (Double)value2;
		else if(value1 instanceof Float || value2 instanceof Float)
			return (Float)value1 / (Float)value2;
		else if(value1 instanceof BigDecimal || value2 instanceof BigDecimal)
			return ((BigDecimal)value1).divide((BigDecimal)value2);
		else if(value1 instanceof BigInteger || value2 instanceof BigInteger)
			return ((BigInteger)value1).divide((BigInteger)value2);
		else
			return 0;
	}
}