package br.com.concepting.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import br.com.concepting.framework.constants.Constants;

/**
 * Classe utilit�ria para manipula��o de arquivos e conte�do bin�rio.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class FileUtil{
	/**
	 * Cria o ponteiro para grava��o dos dados em um arquivo.
	 * 
	 * @param fileName String contendo o none/caminho do arquivo.
	 * @param append Indica se o arquivo dever� ser recriado ou ser� atualizado.
	 * @return Inst�ncia do stream para grava��o.
	 * @throws IOException
	 */
	private static FileOutputStream createStream(String fileName, Boolean append) throws IOException{
		Integer pos     = fileName.lastIndexOf(StringUtil.getDirectorySeparator());
		String  fileDir = "";

		if(pos >= 0)
			fileDir = fileName.substring(0, pos);
		else{
			pos = fileName.lastIndexOf("\\");
			if(pos >= 0)
				fileDir = fileName.substring(0, pos);
			else{
				pos = fileName.lastIndexOf("/");
				if(pos >= 0)
					fileDir = fileName.substring(0, pos);
			}
		}

		if(fileDir.length() > 0){
			File file = new File(fileDir);

			if(!file.exists())
				file.mkdirs();
		}
		
		return new FileOutputStream(fileName, append);
	}

	/**
	 * Retorna a codifica��o default do sistema operacional.
	 * 
	 * @return String contendo a codifica��o.
	 */
	public static String getEncoding(){
		String encoding = System.getProperty("file.encoding");
		
		if(encoding == null || encoding.length() == 0)
		    encoding = Constants.DEFAULT_ENCODING;

		return encoding;
	}
	
	/**
	 * Efetua a grava��o dos dados em um arquivo texto.
	 * 
	 * @param fileName String contendo nome/caminho do arquivo desejado.
	 * @param value String do conte�do do arquivo.
	 * @throws IOException
	 */
	public static void toTextFile(String fileName, String value) throws IOException{
		toTextFile(fileName, value, false);
	}

	/**
	 * Efetua a grava��o dos dados em um arquivo texto.
	 * 
	 * @param fileName String contendo nome/caminho do arquivo desejado.
	 * @param value String do conte�do do arquivo.
	 * @param append Indica se o arquivo dever� ser recriado ou ser� atualizado.
	 * @throws IOException
	 */
	public static void toTextFile(String fileName, String value, Boolean append) throws IOException{
		toTextFile(fileName, value, getEncoding(), append);
	}
	
	/**
	 * Efetua a grava��o dos dados em um arquivo texto.
	 * 
	 * @param fileName String contendo nome/caminho do arquivo desejado.
	 * @param value String do conte�do do arquivo.
	 * @param encoding String contendo a codifica��o.
	 * @throws IOException
	 */
	public static void toTextFile(String fileName, String value, String encoding) throws IOException{
		toTextFile(fileName, value, encoding, false);
	}
	
	/**
	 * Efetua a grava��o dos dados em um arquivo texto.
	 * 
	 * @param fileName String contendo nome/caminho do arquivo desejado.
	 * @param value String do conte�do do arquivo.
	 * @param encoding String contendo a codifica��o.
	 * @param append Indica se o arquivo dever� ser recriado ou ser� atualizado.
	 * @throws IOException
	 */
	public static void toTextFile(String fileName, String value, String encoding, Boolean append) throws IOException{
		if(encoding == null || encoding.length() == 0)
			encoding = getEncoding();
		
        FileOutputStream   out    = createStream(fileName, append);
		OutputStreamWriter writer =  new OutputStreamWriter(out, encoding);

		writer.write(value);
		writer.close();
	}

	/**
	 * Efetua a grava��o dos dados em um arquivo bin�rio.
	 * 
	 * @param fileName String contendo nome/caminho do arquivo desejado.
	 * @param value Array de bytes do conte�do do arquivo.
	 * @throws IOException
	 */
	public static void toBinaryFile(String fileName, byte[] value) throws IOException{
		FileOutputStream out = createStream(fileName, false);

		out.write(value);
		out.close();
	}

	/**
	 * Efetua a leitura do conte�do de um arquivo bin�rio.
	 * 
	 * @param fileName String contendo o nome do arquivo desejado.
	 * @return Array de bytes contendo os dados do arquivo.
	 * @throws IOException
	 */
	public static byte[] fromBinaryFile(String fileName) throws IOException{
		return ByteUtil.fromBinaryStream(new FileInputStream(fileName));
	}

	/**
	 * Efetua a leitura do conte�do de um arquivo texto.
	 * 
	 * @param fileName String contendo o nome do arquivo desejado.
	 * @return String contendo os dados do arquivo.
	 * @throws IOException
	 */
	public static String fromTextFile(String fileName) throws IOException{
		return new String(ByteUtil.fromTextStream(new FileInputStream(fileName)), getEncoding());
	}
}