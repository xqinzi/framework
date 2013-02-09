package br.com.concepting.framework.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;

import br.com.concepting.framework.web.types.ContentType;

/**
 * Classe utilitária para manipulação de imagens.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public abstract class ImageUtil{
	/**
	 * Retorna o formato de uma imagem a partir do seu conteúdo.
	 * 
	 * @param imageData Array de bytes que define a imagem.
	 * @return Constante contendo o formato da imagem.
	 * @throws IOException
	 */
	public static ContentType getImageFormat(byte imageData[]) throws IOException{
		ByteArrayInputStream imageStream = new ByteArrayInputStream(imageData);

		return getImageFormat(imageStream);
	}

	/**
	 * Retorna o formato de uma imagem a partir de um stream.
	 * 
	 * @param imageStream Stream que define a imagem.
	 * @return Constante contendo o formato da imagem.
	 * @throws IOException
	 */
	public static ContentType getImageFormat(InputStream imageStream) throws IOException{
		MemoryCacheImageInputStream stream          = new MemoryCacheImageInputStream(imageStream);
		Iterator<ImageReader>       readersIterator = ImageIO.getImageReaders(stream);

		if(!readersIterator.hasNext())
			throw new IOException();

		ImageReader   reader   = readersIterator.next();
		StringBuilder mimeType = new StringBuilder();

		mimeType.append("image/");
		mimeType.append(reader.getFormatName().toLowerCase());
		
		for(ContentType constant : ContentType.values())
		    if(mimeType.toString().equals(constant.getMimeType()))
		        return constant;
		
		throw new IOException(new IllegalArgumentException(mimeType.toString()));
	}

	/**
	 * Retorna o formato de uma imagem a partir do seu caminho de armazenamento.
	 * 
	 * @param imageFilename String contendo o caminho da imagem.
	 * @return Constante contendo o formato da imagem.
	 * @throws IOException
	 */
	public static ContentType getImageFormat(String imageFilename) throws IOException{
		return getImageFormat(new File(imageFilename));
	}

	/**
	 * Retorna o formato de uma imagem a partir do seu caminho de armazenamento.
	 * 
	 * @param imageFile Instância contendo as propriedades do arquivo de imagem.
	 * @return Constante contendo o formato da imagem.
	 * @throws IOException
	 */
	public static ContentType getImageFormat(File imageFile) throws IOException{
		FileInputStream imageStream = new FileInputStream(imageFile);

		return getImageFormat(imageStream);
	}
}