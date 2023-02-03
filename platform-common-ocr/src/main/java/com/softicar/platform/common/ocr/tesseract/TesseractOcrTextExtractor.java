package com.softicar.platform.common.ocr.tesseract;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.common.io.zip.ZipLib;
import com.softicar.platform.common.ocr.IOcrTextExtractor;
import com.softicar.platform.common.ocr.tesseract.trained.data.ITesseractTrainedDataFileStore;
import com.softicar.platform.common.ocr.tesseract.trained.data.TesseractTrainedDataFileResourceContainer;
import com.softicar.platform.common.pdf.PdfRenderer;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.leptonica.PIX;
import org.bytedeco.leptonica.global.lept;
import org.bytedeco.tesseract.TessBaseAPI;

/**
 * A Tesseract4 based implementation of an OCR based text extractor which turns
 * PDF files into plain text.
 *
 * @author Alexander Schmidt
 */
public class TesseractOcrTextExtractor implements IOcrTextExtractor {

	private static final int DPI = 300;
	private final TesseractLanguage language;
	private final Supplier<ITesseractTrainedDataFileStore> trainedDataFileStoreSupplier;
	private ITesseractTrainedDataFileStore trainedDataFileStore;

	/**
	 * Constructs a new {@link TesseractOcrTextExtractor}.
	 *
	 * @param language
	 *            the language of the PDF document to process (never
	 *            <i>null</i>)
	 * @param trainedDataFileStoreSupplier
	 *            a factory which creates an
	 *            {@link ITesseractTrainedDataFileStore} to host Tesseract4
	 *            trained data files
	 */
	public TesseractOcrTextExtractor(TesseractLanguage language, Supplier<ITesseractTrainedDataFileStore> trainedDataFileStoreSupplier) {

		this.language = Objects.requireNonNull(language);
		this.trainedDataFileStoreSupplier = Objects.requireNonNull(trainedDataFileStoreSupplier);
		this.trainedDataFileStore = null;
	}

	@Override
	public String extractText(InputStream inputStream) {

		Collection<ByteBuffer> imageByteBuffers = convertToImageByteBuffers(inputStream);
		if (!imageByteBuffers.isEmpty()) {
			return extractTextFromImages(imageByteBuffers);
		} else {
			return "";
		}
	}

	private String extractTextFromImages(Collection<ByteBuffer> imageByteBuffers) {

		prepareTrainedData(language);

		try (TessBaseAPI tesseractApi = createTesseractApi()) {
			StringBuilder output = new StringBuilder();
			for (ByteBuffer imageByteBuffer: imageByteBuffers) {
				output.append(extractTextFromImage(tesseractApi, imageByteBuffer));
			}
			return output.toString();
		} catch (Exception exception) {
			throw new SofticarException(exception, "An exception occurred during Tesseract OCR processing.");
		}
	}

	private String extractTextFromImage(TessBaseAPI tesseractApi, ByteBuffer imageByteBuffer) {

		try (PIX imagePix = lept.pixReadMem(imageByteBuffer, imageByteBuffer.position() - 1)) {
			tesseractApi.SetImage(imagePix);
			try (BytePointer textPointer = tesseractApi.GetUTF8Text()) {
				return textPointer.getString();
			}
		}
	}

	private Collection<ByteBuffer> convertToImageByteBuffers(InputStream inputStream) {

		return convertToBufferedImages(inputStream)//
			.stream()
			.map(this::convertToByteBuffer)
			.collect(Collectors.toList());
	}

	private ByteBuffer convertToByteBuffer(BufferedImage bufferedImage) {

		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			ImageIO.write(bufferedImage, "png", outputStream);
			return ByteBuffer.wrap(outputStream.toByteArray());
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private Collection<BufferedImage> convertToBufferedImages(InputStream inputStream) {

		return new PdfRenderer().render(inputStream);
	}

	private TessBaseAPI createTesseractApi() {

		File trainedDataDirectory = trainedDataFileStore.getDirectory();
		if (!trainedDataDirectory.exists()) {
			throw new SofticarException("Failed to locate Tesseract trained-data directory at '%s'.", trainedDataDirectory.getAbsolutePath());
		}

		var api = new TessBaseAPI();
		api.Init(trainedDataDirectory.getAbsolutePath(), language.getIso6393Code());
		setVariableOrThrow(api, "user_defined_dpi", DPI + "");
		return api;
	}

	private void setVariableOrThrow(TessBaseAPI api, String key, String value) {

		boolean success = api.SetVariable(key, value);
		if (!success) {
			throw new IllegalArgumentException("Unknown variable: '%s'".formatted(key));
		}
	}

	private void prepareTrainedData(TesseractLanguage language) {

		var resourceContainer = language.getResourceContainer();
		try (InputStream zippedInputStream = resourceContainer.getResource().getResourceAsStream()) {
			String fileName = getExpectedTrainedDataFileName(resourceContainer);
			getPreparedFileStore().put(fileName, unzipToBytes(zippedInputStream));
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	/**
	 * Returns the file name which Tesseract4 expects for a trained data file.
	 * <p>
	 * Expected format: "<code>[ISO 639-3 code of language].traineddata</code>"
	 * <p>
	 * Example: "<code>eng.traineddata</code>" for English
	 *
	 * @return the name of the trained data file (never <i>null</i>)
	 */
	private String getExpectedTrainedDataFileName(TesseractTrainedDataFileResourceContainer resourceContainer) {

		return resourceContainer.getTargetFilename();
	}

	private ITesseractTrainedDataFileStore getPreparedFileStore() {

		if (this.trainedDataFileStore == null) {
			this.trainedDataFileStore = trainedDataFileStoreSupplier.get();
		}
		return this.trainedDataFileStore;
	}

	private byte[] unzipToBytes(InputStream zippedInputStream) {

		byte[] zippedBytes = StreamUtils.toByteArray(zippedInputStream);
		return ZipLib.unzipSingleFile(zippedBytes).getContent();
	}
}
