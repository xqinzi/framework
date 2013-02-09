package br.com.concepting.framework.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;
import br.com.concepting.framework.util.types.BarcodeType;
import br.com.concepting.framework.web.types.ContentType;

/**
 * Classe utilitária para geração de uma imagem de código de barra.
 * 
 * @author fvilarinho
 * @since 1.0
 */
public class BarcodeGenerator{
	private static BarcodeGenerator instance = null;

	/**
	 * Retorna a instância da classe geradora de imagens de código de barra.
	 * 
	 * @return Instância da classe
	 */
	public static BarcodeGenerator getInstance(){
		if(instance == null)
			instance = new BarcodeGenerator();

		return instance;
	}

	/**
	 * Retorna um array de bytes contendo os dados da imagem de código de barra.
	 * 
	 * @param barcodeType Instância contendo a constante do tipo do código de barra.
	 * @param imageType Instância contendo a constante do tipo de imagem a ser gerada.
	 * @param value String contendo o valor do código de barra.
	 * @return Array de bytes contendo os dados da imagem gerada.
	 * @throws BarcodeException
	 * @throws OutputException
	 * @throws IOException
	 */
	public byte[] generate(BarcodeType barcodeType, ContentType imageType, String value) throws BarcodeException, OutputException, IOException{
		Barcode barcode = null;

		switch(barcodeType){
			case BOOKLAND: {
				barcode = BarcodeFactory.createBookland(value);

				break;
			}
			case CODABAR: {
				barcode = BarcodeFactory.createCodabar(value);

				break;
			}
			case CODE128: {
				barcode = BarcodeFactory.createCode128(value);

				break;
			}
			case CODE128A: {
				barcode = BarcodeFactory.createCode128A(value);

				break;
			}
			case CODE128B: {
				barcode = BarcodeFactory.createCode128B(value);

				break;
			}
			case CODE128C: {
				barcode = BarcodeFactory.createCode128C(value);

				break;
			}
			case EAN128: {
				barcode = BarcodeFactory.createEAN128(value);

				break;
			}
			case EAN13: {
				barcode = BarcodeFactory.createEAN13(value);

				break;
			}
			case GLOBAL_TRADEITEM_NUMBER: {
				barcode = BarcodeFactory.createGlobalTradeItemNumber(value);

				break;
			}
			case INTERVEALED_2_OF_5: {
				barcode = BarcodeFactory.createInt2of5(value, true);

				break;
			}
			case MONARCH: {
				barcode = BarcodeFactory.createMonarch(value);

				break;
			}
			case PDF417: {
				barcode = BarcodeFactory.createPDF417(value);

				break;
			}
			case POST_NET: {
				barcode = BarcodeFactory.createPostNet(value);

				break;
			}
			case RANDOM_WEIGHT_UPCA: {
				barcode = BarcodeFactory.createRandomWeightUPCA(value);

				break;
			}
			case SCC14_SHIPPING_CODE: {
				barcode = BarcodeFactory.createSCC14ShippingCode(value);

				break;
			}
			case SHIPMENT_IDENTIFICATION_NUMBER: {
				barcode = BarcodeFactory.createShipmentIdentificationNumber(value);

				break;
			}
			case SSCC18: {
				barcode = BarcodeFactory.createSSCC18(value);

				break;
			}
			case STANDARD_2_OF_5: {
				barcode = BarcodeFactory.createStd2of5(value);

				break;
			}
			case UPCA: {
				barcode = BarcodeFactory.createUPCA(value);

				break;
			}
			case USD4: {
				barcode = BarcodeFactory.createUSD4(value);

				break;
			}
			case USPS: {
				barcode = BarcodeFactory.createUSPS(value);

				break;
			}
		}

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		switch(imageType){
			case JPEG: {
				BarcodeImageHandler.writeJPEG(barcode, outputStream);

				break;
			}
			case GIF: {
				BarcodeImageHandler.writeGIF(barcode, outputStream);

				break;
			}
			default: {
				BarcodeImageHandler.writePNG(barcode, outputStream);

				break;
			}
		}

		return outputStream.toByteArray();
	}
}