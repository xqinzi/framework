package br.com.concepting.framework.util;

import java.awt.Color;

/**
 * Class utilitária responsável pela manipulação de propriedades de cores.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class ColorUtil{
    /**
     * Representa um objeto contendo as propriedades de uma cor em uma string.
     * 
     * @param color Instância contendo as propriedades da cor.
     * @return String que representa a cor desejada em hexadecimal.
     */
	public static String toString(Color color){
		Integer       red         = color.getRed();
		Integer       green       = color.getGreen();
		Integer       blue        = color.getBlue();
		String        redString   = Integer.toString(red, 16);
		String        greenString = Integer.toString(green, 16);
		String        blueString  = Integer.toString(blue, 16);
		StringBuilder value       = new StringBuilder();
		
		value.append("#");
		value.append(StringUtil.replicate("0", 2 - redString.length()));
		value.append(redString);
		value.append(StringUtil.replicate("0", 2 - greenString.length()));
		value.append(greenString);
		value.append(StringUtil.replicate("0", 2 - blueString.length()));
		value.append(blueString);
		
		return value.toString();		
	}
	
	/**
	 * Transforma uma string que possui o código de uma cor em hexadecimal em um objeto 
	 * contendo as propriedades da cor desejada.
	 *  
	 * @param color String contendo o código da cor.
	 * @return Instância contendo as propriedades da cor. 
	 */
	public static Color toColor(String color){
		return Color.decode(color);
	}
}
