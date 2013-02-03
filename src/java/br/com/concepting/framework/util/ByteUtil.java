package br.com.concepting.framework.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Locale;

import org.w3c.tools.codec.Base64Decoder;
import org.w3c.tools.codec.Base64Encoder;

import br.com.concepting.framework.util.constants.Constants;
import br.com.concepting.framework.util.types.ByteMetricType;

/**
 * Classe utilitária para manipulação de arquivos e conteúdo binário.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class ByteUtil{
    /**
     * Transforma um array de bytes em uma string em hexadecimal.
     * 
     * @param values Array de bytes desejado.
     * @return String em hexadecimal.
     */
    public static String toHexadecimal(byte values[]){
        StringBuilder hexBuffer = new StringBuilder();

        for(byte value : values)
            hexBuffer.append(Integer.toHexString(value & 0xff));

        return hexBuffer.toString();
    }

    /**
     * Transforma uma string em hexadecimal em um array de bytes.
     * 
     * @param value String contendo o valor desejado.
     * @return Array de bytes após o processamento.
     */
    public static byte[] fromHexadecimal(String value){
        String character   = "";
        byte   hexBuffer[] = new byte[value.length()];

        for(Integer cont = 0 ; cont < value.length() ; cont++){
            character = String.valueOf(value.charAt(cont));

            hexBuffer[cont] = Byte.parseByte(character, 16);
        }

        return hexBuffer;
    }

    /**
     * Converte um array de bytes em uma string em base 64.
     *
     * @param value Array de bytes desejado.
     * @return String contendo o valor em base 64.
     */
    public static String toBase64(byte value[]){
        Base64Encoder encoder = new Base64Encoder(new String(value));

        return encoder.processString();
    }

    /**
     * Converte uma string em base 64 em um array de bytes.
     *
     * @param value String contendo o valor em base 64.
     * @return Array de bytes desejado.
     * @throws Throwable 
     */
    public static byte[] fromBase64(String value) throws Throwable{
        Base64Decoder decoder = new Base64Decoder(value);

        return decoder.processString().getBytes();
    }

	/**
	 * Efetua a leitura do conteúdo de um arquivo binário.
	 * 
	 * @param stream Ponteiro para leitura do conteúdo do arquivo.
	 * @return Array de bytes contendo os dados do arquivo.
	 * @throws IOException
	 */
	public static byte[] fromBinaryStream(InputStream stream) throws IOException{
		if(stream != null){
           byte[]                buffer = new byte[Constants.DEFAULT_BUFFER_SIZE];
	       Integer               length = 0;
	       ByteArrayOutputStream out    = new ByteArrayOutputStream();

	       while((length = stream.read(buffer)) > 0)
	    	   out.write(buffer, 0, length);
	       
	       out.close();
	       stream.close();
	       
	       return out.toByteArray();
		}

		return null;
	}

	/**
	 * Efetua a leitura do conteúdo de um arquivo texto.
	 * 
	 * @param stream Ponteiro para leitura do conteúdo do arquivo.
	 * @return Array de bytes contendo os dados do arquivo.
	 * @throws IOException
	 */
	public static byte[] fromTextStream(InputStream stream) throws IOException{
		if(stream != null){
			BufferedReader        reader       = new BufferedReader(new InputStreamReader(stream));
			ByteArrayOutputStream streamBuffer = new ByteArrayOutputStream();
			PrintStream           out          = new PrintStream(streamBuffer);
			String                line         = "";

			while((line = reader.readLine()) != null)
				out.println(line);

			reader.close();

			return streamBuffer.toByteArray();
		}

		return null;
	}

	/**
	 * Transforma um valor em bytes para bits.
	 *
	 * @param value Valor numérico desejado.
	 * @return Valor numerico transformado.
	 */
	public static Long bytesToBits(Long value){
		return value * 8;
	}

	/**
	 * Transforma um valor em bits para bytes.
	 *
	 * @param value Valor numérico desejado.
	 * @return Valor numerico transformado.
	 */
	public static Long bitsToBytes(Long value){
		return value / 8;
	}

	/**
	 * Formata um valor numérico utilizando uma representação em bits.  
	 *
	 * @param value Valor numérico desejado.
	 * @return String formatada.
	 */
	public static String formatBits(Long value){
		return formatBits(value.doubleValue());
	}

	/**
	 * Formata um valor numérico utilizando uma representação em bits.  
	 *
	 * @param value Valor numérico desejado.
	 * @return String formatada.
	 */
	public static String formatBits(Double value){
		return formatBits(value, LanguageUtil.getDefaultLanguage());
	}
	
    /**
     * Formata um valor numérico utilizando uma representação em bits.  
     *
     * @param value Valor numérico desejado.
     * @return String formatada.
     */
	public static String formatBits(BigDecimal value){
		return formatBits(value.longValue());
	}

	/**
	 * Formata um valor numérico utilizando uma representação em bits.  
	 *
	 * @param value Valor numérico desejado.
	 * @param language Instância contendo as configurações de idioma desejadas.
	 * @return String formatada.
	 */
	public static String formatBits(Long value, Locale language){
		return formatBits(value.doubleValue(), language);
	}
	
	/**
	 * Formata um valor numérico utilizando uma representação em bits.  
	 *
	 * @param value Valor numérico desejado.
	 * @param language Instância contendo as configurações de idioma desejadas.
	 * @return String formatada.
	 */
	public static String formatBits(Double value, Locale language){
		ByteMetricType currentMetric = null;
		
		for(ByteMetricType metric : ByteMetricType.values())
			if(value >= metric.getBitMetricValue())
				currentMetric = metric; 
		
		return formatBits(value, currentMetric, language);
	}
	
    /**
     * Formata um valor numérico utilizando uma representação em bits.  
     *
     * @param value Valor numérico desejado.
     * @param language Instância contendo as configurações de idioma desejadas.
     * @return String formatada.
     */
	public static String formatBits(BigDecimal value, Locale language){
		return formatBits(value.longValue(), language);
	}

	/**
	 * Formata um valor numérico utilizando uma representação em bits.  
	 *
	 * @param value Valor numérico desejado.
	 * @param metric Constante que define o tipo de representação (kilo, mega, giga, etc).
	 * @return String formatada.
	 */
	public static String formatBits(Long value, ByteMetricType metric){
		return formatBits(value.doubleValue(), metric);
	}
		
	/**
	 * Formata um valor numérico utilizando uma representação em bits.  
	 *
	 * @param value Valor numérico desejado.
	 * @param metric Constante que define o tipo de representação (kilo, mega, giga, etc).
	 * @return String formatada.
	 */
	public static String formatBits(Double value, ByteMetricType metric){
		return formatBits(value, metric, LanguageUtil.getDefaultLanguage());
	}
	
    /**
     * Formata um valor numérico utilizando uma representação em bits.  
     *
     * @param value Valor numérico desejado.
     * @param metric Constante que define o tipo de representação (kilo, mega, giga, etc).
     * @return String formatada.
     */
	public static String formatBits(BigDecimal value, ByteMetricType metric){
		return formatBits(value.longValue(), metric);
	}

	/**
	 * Formata um valor numérico utilizando uma representação em bits.  
	 *
	 * @param value Valor numérico desejado.
	 * @param metric Constante que define o tipo de representação (kilo, mega, giga, etc).
	 * @param language Instância contendo as configurações de idioma desejadas.
	 * @return String formatada.
	 */
	public static String formatBits(Long value, ByteMetricType metric, Locale language){
		return formatBits(value.doubleValue(), metric, language);
	}

	/**
	 * Formata um valor numérico utilizando uma representação em bits.  
	 *
	 * @param value Valor numérico desejado.
	 * @param metric Constante que define o tipo de representação (kilo, mega, giga, etc).
	 * @param language Instância contendo as configurações de idioma desejadas.
	 * @return String formatada.
	 */
	public static String formatBits(Double value, ByteMetricType metric, Locale language){
		StringBuilder buffer      = new StringBuilder();
		Double        metricValue = (metric == null ? 1 : metric.getBitMetricValue());
		String        metricUnit  = (metric == null ? "" : metric.getMetricUnit());
		Double        valueBuffer = value / metricValue;
		Double        modBuffer   = value % metricValue;
		
		if(modBuffer == 0)
			valueBuffer = (double)Math.round(valueBuffer);
		
		buffer.append(NumberUtil.format(valueBuffer, (modBuffer > 0 ? "0.00" : "0"), language));
		buffer.append(" ");

		if(metric != null){
			buffer.append(metricUnit);
			buffer.append("it");

			if(valueBuffer > 1)
				buffer.append("s");
		}
		else{
			buffer.append("bit");
			
			if(value > 1)
				buffer.append("s");
		}
		
		return buffer.toString();
    }
	
    /**
     * Formata um valor numérico utilizando uma representação em bits.  
     *
     * @param value Valor numérico desejado.
     * @param metric Constante que define o tipo de representação (kilo, mega, giga, etc).
     * @param language Instância contendo as configurações de idioma desejadas.
     * @return String formatada.
     */
	public static String formatBits(BigDecimal value, ByteMetricType metric, Locale language){
		return formatBits(value.longValue(), metric, language);
	}

	/**
	 * Formata um valor numérico utilizando uma representação em bytes.  
	 *
	 * @param value Valor numérico desejado.
	 * @return String formatada.
	 */
	public static String formatBytes(Long value, Locale language){
		return formatBytes(value.doubleValue(), language);
	}

	/**
	 * Formata um valor numérico utilizando uma representação em bytes.  
	 *
	 * @param value Valor numérico desejado.
	 * @return String formatada.
	 */
	public static String formatBytes(Double value, Locale language){
		ByteMetricType currentMetric = null;
		
		for(ByteMetricType metric : ByteMetricType.values())
			if(value >= metric.getMetricValue())
				currentMetric = metric; 
		
		return formatBytes(value, currentMetric, language);
	}
	
    /**
     * Formata um valor numérico utilizando uma representação em bytes.  
     *
     * @param value Valor numérico desejado.
     * @return String formatada.
     */
	public static String formatBytes(BigDecimal value, Locale language){
		return formatBytes(value.longValue(), language);
	}

	/**
	 * Formata um valor numérico utilizando uma representação em bytes.  
	 *
	 * @param value Valor numérico desejado.
	 * @param metric Constante que define o tipo de representação (kilo, mega, giga, etc).
	 * @return String formatada.
	 */
	public static String formatBytes(Long value, ByteMetricType metric){
		return formatBytes(value.doubleValue(), metric);
	}

	/**
	 * Formata um valor numérico utilizando uma representação em bytes.  
	 *
	 * @param value Valor numérico desejado.
	 * @param metric Constante que define o tipo de representação (kilo, mega, giga, etc).
	 * @return String formatada.
	 */
	public static String formatBytes(Double value, ByteMetricType metric){
		return formatBytes(value, metric, LanguageUtil.getDefaultLanguage());
	}
	
    /**
     * Formata um valor numérico utilizando uma representação em bytes.  
     *
     * @param value Valor numérico desejado.
     * @param metric Constante que define o tipo de representação (kilo, mega, giga, etc).
     * @return String formatada.
     */
	public static String formatBytes(BigDecimal value, ByteMetricType metric){
		return formatBytes(value.longValue(), metric);
	}

	/**
	 * Formata um valor numérico utilizando uma representação em bytes.  
	 *
	 * @param value Valor numérico desejado.
	 * @param metric Constante que define o tipo de representação (kilo, mega, giga, etc).
	 * @param language Instância contendo as configurações de idioma desejadas.
	 * @return String formatada.
	 */
	public static String formatBytes(Long value, ByteMetricType metric, Locale language){
		return formatBytes(value.doubleValue(), metric, language);
	}

	/**
	 * Formata um valor numérico utilizando uma representação em bytes.  
	 *
	 * @param value Valor numérico desejado.
	 * @param metric Constante que define o tipo de representação (kilo, mega, giga, etc).
	 * @param language Instância contendo as configurações de idioma desejadas.
	 * @return String formatada.
	 */
	public static String formatBytes(Double value, ByteMetricType metric, Locale language){
		StringBuilder buffer      = new StringBuilder();
		Double        metricValue = (metric == null ? 1 : metric.getMetricValue());
		String        metricUnit  = (metric == null ? "" : metric.getMetricUnit());
		Double        valueBuffer = value / metricValue;
		Double        modBuffer   = value % metricValue;
		
		if(modBuffer == 0)
			valueBuffer = (double)Math.round(valueBuffer);
		
		buffer.append(NumberUtil.format(valueBuffer, (modBuffer > 0 ? "0.00" : "0"), language));
		buffer.append(" ");
		
		if(metric != null){
			buffer.append(metricUnit);
		}
		else{
			buffer.append("byte");

			if(value > 1)
				buffer.append("s");
		}
		
		return buffer.toString();
    }
	
    /**
     * Formata um valor numérico utilizando uma representação em bytes.  
     *
     * @param value Valor numérico desejado.
     * @param metric Constante que define o tipo de representação (kilo, mega, giga, etc).
     * @param language Instância contendo as configurações de idioma desejadas.
     * @return String formatada.
     */
	public static String formatBytes(BigDecimal value, ByteMetricType metric, Locale language){
		return formatBytes(value.longValue(), metric, language);
	}

	/**
	 * Formata um valor numérico utilizando uma representação em bytes.  
	 *
	 * @param value Valor numérico desejado.
	 * @return String formatada.
	 */
	public static String formatBytes(Long value){
		return formatBytes(value.doubleValue());
	}

	/**
	 * Formata um valor numérico utilizando uma representação em bytes.  
	 *
	 * @param value Valor numérico desejado.
	 * @return String formatada.
	 */
	public static String formatBytes(Double value){
		return formatBytes(value, LanguageUtil.getDefaultLanguage());
	}
	
    /**
     * Formata um valor numérico utilizando uma representação em bytes.  
     *
     * @param value Valor numérico desejado.
     * @return String formatada.
     */
	public static String formatBytes(BigDecimal value){
		return formatBytes(value.longValue());
	}
}