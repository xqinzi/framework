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
		Integer       red   = color.getRed();
		Integer       green = color.getGreen();
		Integer       blue  = color.getBlue();
		StringBuilder value = new StringBuilder();
		
		value.append("rgb(");
		value.append(red);
		value.append(", ");
		value.append(green);
		value.append(",");
		value.append(blue);
		value.append(")");
		
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
	    String value = StringUtil.replaceAll(color, "rgb(", "");
	    
	    value = StringUtil.replaceAll(value, ")", "");
	    
	    String values[] = StringUtil.split(value);
	    
	    return new Color(Integer.parseInt(StringUtil.trim(values[0])), Integer.parseInt(StringUtil.trim(values[1])), Integer.parseInt(StringUtil.trim(values[2])));
	}
}
