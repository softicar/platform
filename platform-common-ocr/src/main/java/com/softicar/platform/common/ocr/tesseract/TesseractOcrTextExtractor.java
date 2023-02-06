package com.softicar.platform.common.ocr.tesseract;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.common.io.zip.ZipLib;
import com.softicar.platform.common.ocr.tesseract.trained.data.ITesseractTrainedDataFileStore;
import com.softicar.platform.common.ocr.tesseract.trained.data.TesseractTrainedDataFileResourceContainer;
import com.softicar.platform.common.ocr.tesseract.trained.data.TesseractTrainedDataTemporaryFileStore;
import com.softicar.platform.common.pdf.IPdfTextExtractor;
import com.softicar.platform.common.pdf.PdfRenderer;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
public class TesseractOcrTextExtractor implements IPdfTextExtractor {

	private static final int DEFAULT_PDF_RENDERING_DPI = 120;
	private static final int DEFAULT_TESSERACT_DPI = 120;
	private static final TesseractPageSegmentationMode DEFAULT_PAGE_SEGMENTATION_MODE = TesseractPageSegmentationMode.PSM_SINGLE_BLOCK;

	private final TesseractLanguage language;
	private final Supplier<ITesseractTrainedDataFileStore> trainedDataFileStoreSupplier;
	private ITesseractTrainedDataFileStore trainedDataFileStore;
	private int pdfRenderingDpi;
	private int tesseractDpi;
	private TesseractPageSegmentationMode pageSegmentationMode;

	/**
	 * Constructs a new {@link TesseractOcrTextExtractor}, using the default
	 * {@link ITesseractTrainedDataFileStore}.
	 *
	 * @param language
	 *            the language of the PDF document to process (never
	 *            <i>null</i>)
	 */
	public TesseractOcrTextExtractor(TesseractLanguage language) {

		this(language, TesseractTrainedDataTemporaryFileStore::new);
	}

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
		this.pdfRenderingDpi = DEFAULT_PDF_RENDERING_DPI;
		this.tesseractDpi = DEFAULT_TESSERACT_DPI;
		this.pageSegmentationMode = DEFAULT_PAGE_SEGMENTATION_MODE;
	}

	@Override
	public String extractText(InputStream inputStream) {

		Collection<byte[]> imageBytesCollection = convertToImageBytesCollection(inputStream);
		if (!imageBytesCollection.isEmpty()) {
			return extractTextFromImages(imageBytesCollection);
		} else {
			return "";
		}
	}

	/**
	 * Defines the DPI for PDF rendering.
	 *
	 * @param dpi
	 *            the DPI value
	 * @return this
	 */
	public TesseractOcrTextExtractor setPdfRenderingDpi(int dpi) {

		this.pdfRenderingDpi = dpi;
		return this;
	}

	/**
	 * Defines the DPI for Tesseract.
	 *
	 * @param dpi
	 *            the DPI value
	 * @return this
	 */
	public TesseractOcrTextExtractor setTesseractDpi(int dpi) {

		this.tesseractDpi = dpi;
		return this;
	}

	/**
	 * Defines the {@link TesseractPageSegmentationMode}.
	 *
	 * @param pageSegmentationMode
	 *            the mode (never <i>null</i>)
	 */
	public void setPageSegmentationMode(TesseractPageSegmentationMode pageSegmentationMode) {

		this.pageSegmentationMode = Objects.requireNonNull(pageSegmentationMode);
	}

	private String extractTextFromImages(Collection<byte[]> imageBytesCollection) {

		prepareTrainedData(language);

		try (TessBaseAPI tesseractApi = createTesseractApi()) {
			StringBuilder output = new StringBuilder();
			for (byte[] imageBytes: imageBytesCollection) {
				output.append(extractTextFromImage(tesseractApi, imageBytes));
			}
			return output.toString();
		} catch (Exception exception) {
			throw new SofticarException(exception, "An exception occurred during Tesseract OCR processing.");
		}
	}

	private String extractTextFromImage(TessBaseAPI tesseractApi, byte[] imageBytes) {

		try (PIX imagePix = lept.pixReadMemBmp(imageBytes, imageBytes.length)) {
			tesseractApi.SetImage(imagePix);

			try (BytePointer textPointer = tesseractApi.GetUTF8Text()) {
				return textPointer.getString();
			}
		}
	}

	private Collection<byte[]> convertToImageBytesCollection(InputStream inputStream) {

		return convertToBufferedImages(inputStream)//
			.stream()
			.map(this::convertToBytes)
			.collect(Collectors.toList());
	}

	private byte[] convertToBytes(BufferedImage bufferedImage) {

		try (var outputStream = new ByteArrayOutputStream()) {
			ImageIO.write(bufferedImage, "bmp", outputStream);
			return outputStream.toByteArray();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private Collection<BufferedImage> convertToBufferedImages(InputStream inputStream) {

		return new PdfRenderer().setDpi(pdfRenderingDpi).render(inputStream);
	}

	private TessBaseAPI createTesseractApi() {

		File trainedDataDirectory = trainedDataFileStore.getDirectory();
		if (!trainedDataDirectory.exists()) {
			throw new SofticarException("Failed to locate Tesseract trained-data directory at '%s'.", trainedDataDirectory.getAbsolutePath());
		}

		var api = new TessBaseAPI();
		api.Init(trainedDataDirectory.getAbsolutePath(), language.getIso6393Code());
		setVariableOrThrow(api, "user_defined_dpi", tesseractDpi + "");
		setVariableOrThrow(api, "tessedit_pageseg_mode", pageSegmentationMode.getModeIndex() + "");
		return api;
	}

	/**
	 * Sets the given variable.
	 * <p>
	 * See the <a href=
	 * "https://tesseract-ocr.github.io/tessapi/4.0.0/a02358.html#pub-attribs">documentation</a>
	 * for a complete list.
	 */
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
