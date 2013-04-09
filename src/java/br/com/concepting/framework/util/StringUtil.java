package br.com.concepting.framework.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.MaskFormatter;

import org.htmlparser.util.Translate;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.util.helpers.Indent;
import br.com.concepting.framework.util.types.AlignmentType;

/**
 * Classe utilitária para manipulação de strings.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class StringUtil{
	/**
	 * Replica uma string.
	 * 
	 * @param value String desejada.
	 * @param times Valor inteiro contendo o número de vezes.
	 * @return String replicada.
	 */
	public static String replicate(String value, Integer times){
		StringBuilder replicateBuffer = new StringBuilder();

		for(Integer cont = 0 ; cont < times ; cont++)
			replicateBuffer.append(value);

		return replicateBuffer.toString();
	}

	/**
	 * Substitui todas as ocorrências de uma string em outra.
	 * 
	 * @param value String desejada.
	 * @param search String a ser substituída.
	 * @param replace String de substituíção.
	 * @return String após substituição.
	 */
	public static String replaceAll(String value, String search, String replace){
		if(search.length() == 0)
			return value;
		
		Integer pos = value.indexOf(search);

		if(pos < 0)
			return value;

		StringBuilder replaceBuffer = new StringBuilder();

		replaceBuffer.append(value.substring(0, pos));
		replaceBuffer.append(replace);
		replaceBuffer.append(value.substring(pos + search.length()));

		return replaceAll(replaceBuffer.toString(), search, replace);
	}

	/**
	 * Substitui todas as ocorrências de um caracter em uma string.
	 * 
	 * @param value String desejada.
	 * @param search Caracter desejado.
	 * @param replace String de substituíção.
	 * @return String após substituição.
	 */
	public static String replaceLast(String value, char search, String replace){
		return replaceLast(value, String.valueOf(search), replace);
	}

	/**
	 * Substitui a última ocorrência de uma string em outra.
	 * 
	 * @param value String desejada.
	 * @param search String a ser substituída.
	 * @param replace String de substituíção.
	 * @return String após substituição.
	 */
	public static String replaceLast(String value, String search, String replace){
		if(search.length() == 0)
			return value;

		Integer pos = value.lastIndexOf(search);

		if(pos < 0)
			return value;

		StringBuilder replaceBuffer = new StringBuilder();

		replaceBuffer.append(value.substring(0, pos));
		replaceBuffer.append(replace);
		replaceBuffer.append(value.substring(pos + search.length()));

		return replaceBuffer.toString();
	}

	/**
	 * Inverte uma string.
	 * 
	 * @param value String desejada.
	 * @return String invertida.
	 */
	public static String reverse(String value){
		StringBuilder reverseBuffer = new StringBuilder(value);

		return reverseBuffer.reverse().toString();
	}

	/**
	 * Remove os espaços do ínicio e final de uma string.
	 * 
	 * @param value String desejada. Se a string for nula, retornará uma string vazia.
	 * @return String sem os espaços.
	 */
	public static String trim(Object value){
		if(value == null)
			return "";

		if(value.toString() == null)
			return "";

		
		return value.toString().trim();
	}

	/**
	 * Alinha uma string.
	 * 
	 * @param alignment Instância da constante que define o tipo de alinhamento.
	 * @param maxChars Valor inteiro contendo o número máximo da caracteres da string final.
	 * @param value String desejada.
	 * @return String alinhada.
	 */
	public static String align(AlignmentType alignment, Integer maxChars, String value){
		StringBuilder align = new StringBuilder();
		Integer       length      = value.length();

		if(length == 0)
			return replicate(" ", maxChars);

		if(maxChars > length){
			switch(alignment){
				case LEFT: {
					align.append(value);
					align.append(replicate(" ", maxChars - length));

					break;
				}
				case RIGHT: {
					align.append(replicate(" ", maxChars - length));
					align.append(value);

					break;
				}
				default: {
					align.append(replicate(" ", (maxChars - length) / 2));
					align.append(value);
					align.append(replicate(" ", (maxChars - length) / 2));

					break;
				}
			}

			return align.toString();
		}

		return value;
	}

	/**
	 * Retorna o caracter de um código ASCII.
	 * 
	 * @param value Valor inteiro contendo o código ASCII.
	 * @return Representação do caracter.
	 */
	public static char chr(int value){
		return (char)value;
	}

	/**
	 * Retorna o código ASCII de um caracter.
	 * 
	 * @param value Representação do caracter.
	 * @return Valor inteiro contendo o código ASCII.
	 */
	public static int asc(char value){
		return value;
	}

	/**
	 * Retorna o caracter separador de diretórios.
	 * 
	 * @return String contendo o caracter separador.
	 */
	public static String getDirectorySeparator(){
		return System.getProperty("file.separator");
	}

	/**
	 * Retorna o caracter de quebra de linhas.
	 * 
	 * @return String contendo o caracter de quebra de linhas.
	 */
	public static String getLineBreak(){
		return System.getProperty("line.separator");
	}

	/**
	 * Converte um array em uma string delimitada utilizando o delimitador default.
	 * 
	 * @param values Array contendo os valores.
	 * @return String delimitada.
	 */
	public static String merge(Object values[]){
		return merge(values, Constants.DEFAULT_STRING_DELIMITER);
	}

	/**
	 * Converte um array em uma string delimitada utilizando um delimitador específico.
	 * 
	 * @param values Array contendo os valores.
	 * @param delimiter String contendo o delimitador.
	 * @return String delimitada.
	 */
	public static String merge(Object values[], String delimiter){
		StringBuilder result = new StringBuilder();

		for(Integer cont = 0 ; cont < values.length ; cont++){
			if(cont > 0)
				result.append(delimiter);

			result.append(values[cont].toString());
		}

		return result.toString();
	}

	/**
	 * Transforma uma string delimitada em um array utilizando o delimitador default.
	 * 
	 * @param value String delimitada.
	 * @return Array contendo as strings.
	 */
	public static String[] split(String value){
		return split(value, Constants.DEFAULT_STRING_DELIMITER);
	}

	/**
	 * Transforma uma string delimitada em um array utilizando um delimitador específico.
	 * 
	 * @param value String delimitada.
	 * @param delimiter String contendo o delimitador.
	 * @return Array contendo as strings.
	 */
	public static String[] split(String value, String delimiter){
		StringTokenizer tokenizer = new StringTokenizer(value, delimiter);
		String          tokens[]  = new String[tokenizer.countTokens()];

		for(Integer cont = 0 ; cont < tokens.length ; cont++)
			tokens[cont] = tokenizer.nextToken();

		return tokens;
	}

	/**
	 * Coloca em caixa alta os primeiros caracteres de cada palavra em uma string.
	 * 
	 * @param value String desejada.
	 * @param onlyFirstWord Indica se deve considerar somente a primeira palavra.
	 * @return String capitalizada.
	 */
	public static String capitalize(String value, Boolean onlyFirstWord){
		StringBuilder result = new StringBuilder();

		if(onlyFirstWord){
			result.append(value.substring(0, 1).toUpperCase());
			result.append(value.substring(1));
		}
		else{
			String values[] = StringUtil.split(value, " ");

			for(String valueItem : values){
				result.append(valueItem.substring(0, 1).toUpperCase());
				result.append(valueItem.substring(1));
			}
		}

		return result.toString();
	}

	/**
	 * Coloca em caixa alta o primeiro caracter de uma string.
	 * 
	 * @param value String desejada.
	 * @return String capitalizada.
	 */
	public static String capitalize(String value){
		return capitalize(value, true);
	}

	/**
	 * Formata uma string de acordo com uma máscara de formatação.
	 * 
	 * @param value String a ser formatada.
	 * @param pattern String contendo a máscara de formatação.
	 * @return String formatada.
	 */
	public static String format(String value, String pattern){
		pattern = StringUtil.replaceAll(pattern, "#", "A");
		pattern = StringUtil.replaceAll(pattern, "9", "#");
		pattern = StringUtil.replaceAll(pattern, "d", "#");
		pattern = StringUtil.replaceAll(pattern, "M", "#");
		pattern = StringUtil.replaceAll(pattern, "y", "#");
		pattern = StringUtil.replaceAll(pattern, "H", "#");
		pattern = StringUtil.replaceAll(pattern, "h", "#");
		pattern = StringUtil.replaceAll(pattern, "m", "#");
		pattern = StringUtil.replaceAll(pattern, "s", "#");

		String result = StringUtil.trim(value);

		try{
			MaskFormatter formatter = new MaskFormatter(pattern);
			Integer       cont      = 0;

			formatter.setValueContainsLiteralCharacters(false);

			result = StringUtil.trim(formatter.valueToString(result));

			for(cont = 0 ; cont < pattern.length() ; cont++)
				if(pattern.charAt(cont) != ' ' && result.charAt(cont) == ' ')
					break;

			if(pattern.charAt(cont - 1) != '#' && pattern.charAt(cont - 1) != '9' && pattern.charAt(cont - 1) != 'd' && pattern.charAt(cont - 1) != 'M' && pattern.charAt(cont - 1) != 'y' && pattern.charAt(cont - 1) != 'H' && pattern.charAt(cont - 1) != 'h' && pattern.charAt(cont - 1) != 'm' && pattern.charAt(cont - 1) != 's' && pattern.charAt(cont - 1) != 'A')
				result = result.substring(0, cont - 1);
			else
				result = result.substring(0, cont);
		}
		catch(Throwable e){
		}

		return result;
	}

	/**
	 * Desformata uma string.
	 *
	 * @param value String formatada.
	 * @param pattern String contendo a máscara a ser utilizada.
	 * @return String desformatada.
	 */
	public static String unformat(String value, String pattern){
		String buffer = value;

		for(Integer cont = 0 ; cont < pattern.length() ; cont++){
			if(pattern.charAt(cont) != '9' && pattern.charAt(cont) != '#' && pattern.charAt(cont) != '0' && pattern.charAt(cont) != 'd' && pattern.charAt(cont) != 'M' && pattern.charAt(cont) != 'm' && pattern.charAt(cont) != 'y' && pattern.charAt(cont) != 'H' && pattern.charAt(cont) != 'h' && pattern.charAt(cont) != 's')
				buffer = StringUtil.replaceAll(buffer, String.valueOf(pattern.charAt(cont)), "");
		}

		return StringUtil.trim(buffer);
	}

	/**
	 * Efetua a indentação de um conteúdo a partir de uma lista de regras.
	 * 
	 * @param value String do conteúdo a ser indentado.
	 * @param rules Lista contendo as regras de indentação.
	 * @return String indentada.
	 * @throws IOException
	 */
	public static <R extends Indent> String indent(String value, Collection<R> rules) throws IOException{
		ByteArrayInputStream  in                = new ByteArrayInputStream(value.getBytes());
		ByteArrayOutputStream out               = new ByteArrayOutputStream();
		BufferedReader        reader            = new BufferedReader(new InputStreamReader(in));
		PrintWriter           writer            = new PrintWriter(out);
		Stack<Indent>         rulesQueue        = new Stack<Indent>();                 
		Indent                queueRule         = null;
		Indent                currentRule       = null;
		Boolean               closeSameLine     = false;
		String                line              = "";
		Integer               currentIdentCount = 0;
		Integer               startPos          = 0;
		Integer               endPos            = 0;

		while((line = reader.readLine()) != null){
			currentRule   = null;
			closeSameLine = false;
			line          = StringUtil.trim(line);
			line          = StringUtil.replaceAll(line, String.valueOf(StringUtil.chr(9)), "");

			if(line.length() > 0){
     			for(Indent rule : rules){
     				if(rule.getStartChar().length() > 0){
     					startPos = line.indexOf(rule.getStartChar());
     					if(startPos >= 0){
     						endPos = line.indexOf(rule.getEndChar());
     						if(endPos < 0){
     							queueRule   = rule;
          						currentRule = rule;
     						}
     						else{
     							if(!startPos.equals(endPos))
     								closeSameLine = true;
     							
     							currentRule = null;
     						     
          						break;
     						}
     					}
     				}
     			}
     		}

			if(currentRule != null){
				rulesQueue.push(queueRule);
				
				writer.print(StringUtil.replicate(" ", currentIdentCount));
				writer.println(line);
				
				if(line.indexOf(currentRule.getEndChar()) < 0)
					currentIdentCount += currentRule.getIndentCount();
			}
			else{
				if(!closeSameLine && line.length() > 0){
     				for(Indent rule : rules){
     					if(rule.getEndChar().length() > 0){
     						endPos = line.indexOf(rule.getEndChar());
     						if(endPos >= 0){
     							if(queueRule != null && rule.getStartChar().equals(queueRule.getStartChar())){
     								if(rulesQueue.size() > 0)
     									queueRule = rulesQueue.pop();
     								else
     									queueRule = null;
     								
          							currentRule = rule;
          							
          							if(!currentRule.isBackAfterEndChar())
          								currentIdentCount -= currentRule.getIndentCount();
          							
          							break;
     							}
     						}
     					}
     				}
				}

				writer.print(StringUtil.replicate(" ", currentIdentCount));
				writer.println(line);
				
				if(currentRule != null && currentRule.isBackAfterEndChar())
					currentIdentCount -= currentRule.getIndentCount();
			}
		}

		writer.close();

		return new String(out.toByteArray());
	}
	
    /**
     * Efetua a decodificação de um conteúdo.
     *
     * @param value String codificada.
     * @return String decodificada.
     */
    public static String decode(String value){
        String  buffer     = Translate.decode(value);
        Pattern pattern    = Pattern.compile("\\%([0-9a-fA-F]{2})");
        Matcher matcher    = pattern.matcher(buffer);
        String  expression = "";
        String  token      = "";
        
        while(matcher.find()){
            expression = matcher.group();
            token      = matcher.group(1);
            token      = String.valueOf(StringUtil.chr(Integer.parseInt(token, 16)));
            buffer     = StringUtil.replaceAll(buffer, expression, token);
        }
        
        return buffer;
    }
    
    /**
     * Efetua a codificação de um conteúdo.
     *
     * @param value String codificada.
     * @return String decodificada.
     */
    public static String encode(String value){
        return Translate.encode(value);
    }
    
    /**
     * Transforma uma string contendo máscaras para uma expressão regular.
     * 
     * @param expression String desejada.
     * @return String contendo a expressão regular.
     */
    public static String toRegex(String expression){
        expression = StringUtil.replaceAll(expression, ".*", "\\..".concat(Constants.REGEX_ANY_TAG));
        expression = StringUtil.replaceAll(expression, "*.", ".".concat(Constants.REGEX_ANY_TAG).concat("\\."));
        expression = StringUtil.replaceAll(expression, "*", ".".concat(Constants.REGEX_ANY_TAG));
        expression = StringUtil.replaceAll(expression, "?", ".");
        expression = StringUtil.replaceAll(expression, Constants.REGEX_ANY_TAG, "*");
        
        return expression;
    }
}