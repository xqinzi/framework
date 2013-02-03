package br.com.concepting.framework.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.codec.language.RefinedSoundex;

import com.wcohen.ss.Jaro;
import com.wcohen.ss.MongeElkan;

/**
 * Classe utilit�ria para fon�tica.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class PhoneticUtil{
	private static RefinedSoundex      soundexInstance  = null;
	private static Map<String, String> firstSoundexMap  = null;
	private static Map<String, String> middleSoundexMap = null;
	private static Map<String, String> lastSoundexMap   = null;

	static{
		firstSoundexMap = new LinkedHashMap<String, String>();
		firstSoundexMap.put("ka", "ca");
		firstSoundexMap.put("ke", "que");
		firstSoundexMap.put("ki", "qui");
		firstSoundexMap.put("ko", "co");
		firstSoundexMap.put("ku", "cu");
		firstSoundexMap.put("r�", "ha");
		firstSoundexMap.put("r�", "he");
		firstSoundexMap.put("r�", "hi");
		firstSoundexMap.put("r�", "ho");
		firstSoundexMap.put("r�", "hu");
		firstSoundexMap.put("ha", "a");
		firstSoundexMap.put("he", "e");
		firstSoundexMap.put("hi", "i");
		firstSoundexMap.put("ho", "o");
		firstSoundexMap.put("hu", "u");
		firstSoundexMap.put("ja", "xia");
		firstSoundexMap.put("je", "xie");
		firstSoundexMap.put("jo", "xio");
		firstSoundexMap.put("ju", "xiu");

		middleSoundexMap = new LinkedHashMap<String, String>();
		middleSoundexMap.put("8", "b");
		middleSoundexMap.put("0", "o");
		middleSoundexMap.put("7", "t");
		middleSoundexMap.put("�", "a");
		middleSoundexMap.put("�", "e");
		middleSoundexMap.put("�", "i");
		middleSoundexMap.put("�", "o");
		middleSoundexMap.put("�", "u");
		middleSoundexMap.put("�", "a");
		middleSoundexMap.put("�", "e");
		middleSoundexMap.put("�", "o");
		middleSoundexMap.put("�", "an");
		middleSoundexMap.put("�", "on");
		middleSoundexMap.put("�", "ss");
		middleSoundexMap.put("ch", "x");
		middleSoundexMap.put("nh", "ni");
		middleSoundexMap.put("lh", "li");
		middleSoundexMap.put("'", "");

		lastSoundexMap = new LinkedHashMap<String, String>();
		lastSoundexMap.put("az", "as");
		lastSoundexMap.put("ez", "es");
		lastSoundexMap.put("iz", "is");
		lastSoundexMap.put("oz", "os");
		lastSoundexMap.put("uz", "us");
		lastSoundexMap.put("am", "an");
		lastSoundexMap.put("em", "en");
		lastSoundexMap.put("im", "in");
		lastSoundexMap.put("om", "on");
		lastSoundexMap.put("um", "un");
		lastSoundexMap.put("ax", "akis");
		lastSoundexMap.put("ex", "ekis");
		lastSoundexMap.put("ix", "ikis");
		lastSoundexMap.put("ox", "okis");
		lastSoundexMap.put("ux", "ukis");
		lastSoundexMap.put("di", "d");
	}

	/**
	 * Retorna a representa��o fon�tica de uma string.
	 * 
	 * @param value String desejada.
	 * @return String cntendo a representa��o fon�tica da string.
	 */
	public static String soundCode(String value){
		String buffer   = StringUtil.trim(value).toLowerCase();
		String tokens[] = StringUtil.split(buffer, " ");

		for(String soundexKey : firstSoundexMap.keySet()){
			for(Integer cont = 0 ; cont < tokens.length ; cont++){
				if(tokens[cont].indexOf(soundexKey) == 0)
					tokens[cont] = StringUtil.replaceAll(tokens[cont], soundexKey, firstSoundexMap.get(soundexKey));
			}
		}

		for(String soundexKey : lastSoundexMap.keySet()){
			for(Integer cont = 0 ; cont < tokens.length ; cont++){
				if(tokens[cont].indexOf(soundexKey) == (tokens[cont].length() - soundexKey.length()))
					tokens[cont] = StringUtil.replaceAll(tokens[cont], soundexKey, lastSoundexMap.get(soundexKey));
			}
		}

		for(String soundexKey : middleSoundexMap.keySet()){
			for(Integer cont = 0 ; cont < tokens.length ; cont++){
				tokens[cont] = StringUtil.replaceAll(tokens[cont], soundexKey, middleSoundexMap.get(soundexKey));
			}
		}

		if(soundexInstance == null)
			soundexInstance = new RefinedSoundex();

		for(Integer cont = 0 ; cont < tokens.length ; cont++)
			tokens[cont] = soundexInstance.encode(tokens[cont]);

		buffer = StringUtil.merge(tokens, " ");

		return buffer;
	}

	/**
	 * Indica se duas strings s�o similares a partir de uma porcentagem de similaridade 
	 * especificada.
	 * 
	 * @param value1 Primeira string.
	 * @param value2 Segunda string.
	 * @param similarityAccuracy Valor em ponto flutuante contendo a porcentagem de 
	 * similaridade
	 * @return True/False.
	 */
	public static Boolean isSimilar(String value1, String value2, double similarityAccuracy){
		return similarityAccuracy(value1, value2) >= similarityAccuracy;
	}

	/**
	 * Retorna a porcentagem de similaridade entre duas strings.
	 * 
	 * @param value1 Primeira string.
	 * @param value2 Segunda string.
	 * @return Valor em ponto flutuante contendo a porcentagem de similaridade.
	 */
	public static Double similarityAccuracy(String value1, String value2){
		String        valueTokens1[] = StringUtil.split(value1, " ");
		String        valueTokens2[] = StringUtil.split(value2, " ");
		StringBuilder valueBuffer1   = new StringBuilder();
		
		for(Integer cont = 0 ; cont < valueTokens1.length ; cont++){
			if(cont < valueTokens2.length){
				if(soundCode(valueTokens1[cont]).equals(soundCode(valueTokens2[cont]))){
					valueBuffer1.append(valueTokens1[cont]);
					valueBuffer1.append(" ");
				}
			}
		}

		if(valueBuffer1.length() == 0)
			return 0d;

		return ((new MongeElkan().score(valueBuffer1.toString().trim(), value2) + new Jaro().score(valueBuffer1.toString().trim(), value2)) / 2d) * 100d;
	}
}