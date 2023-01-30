package com.softicar.platform.common.pdf;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.mime.IMimeType;
import com.softicar.platform.common.ui.image.Images;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.function.Supplier;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;

/**
 * Converts images into PDFs.
 * <p>
 * Please note that some image formats, like TIFF, support multiple images per
 * file. This converter reads all images from such files and creates a PDF with
 * all images converted to JPEG, one image per page. The dimensions of each page
 * correspond to the dimensions of each image.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class ImageToPdfConverter {

	private static final float QUALTITY = 1f;
	private final Supplier<InputStream> inputStreamFactory;

	/**
	 * Constructs this {@link ImageToPdfConverter}.
	 *
	 * @param inputStreamFactory
	 *            the factory of the {@link InputStream} (never <i>null</i>)
	 */
	public ImageToPdfConverter(Supplier<InputStream> inputStreamFactory) {

		this.inputStreamFactory = Objects.requireNonNull(inputStreamFactory);
	}

	/**
	 * Converts the image(s) into a PDF.
	 *
	 * @returns a byte array representing the content of the PDF (never
	 *          <i>null</i>)
	 */
	public byte[] convertToPdf() {

		try (var document = new PDDocument()) {
			addToPdf(document);
			var buffer = new ByteArrayOutputStream();
			document.save(buffer);
			return buffer.toByteArray();
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private void addToPdf(PDDocument document) {

		for (var image: Images.readImages(inputStreamFactory)) {
			PDPage page = new PDPage(new PDRectangle(image.getWidth(), image.getHeight()));
			document.addPage(page);
			try (var contentStream = new PDPageContentStream(document, page)) {
				contentStream.drawImage(JPEGFactory.createFromImage(document, image, QUALTITY), 0, 0);
			} catch (IOException exception) {
				throw new SofticarIOException(exception);
			}
		}
	}

	/**
	 * Determines whether {@link ImageToPdfConverter} is able to convert an
	 * image file with the given {@link IMimeType} to PDF.
	 *
	 * @param mimeType
	 *            the image {@link IMimeType} (never <i>null</i>)
	 * @return <i>true</i> if the given image {@link IMimeType} is convertible
	 *         to PDF; <i>false</i> otherwise
	 */
	public static boolean isConvertibleToPdf(IMimeType mimeType) {

		Objects.requireNonNull(mimeType);
		return ImageIO.getImageReadersByMIMEType(mimeType.getIdentifier()).hasNext();
	}

	/**
	 * Determines whether {@link ImageToPdfConverter} is able to convert an
	 * image file with the given suffix (i.e. file name extension) to PDF.
	 *
	 * @param suffix
	 *            the image file name suffix (never <i>null</i>)
	 * @return <i>true</i> if the given file name suffix is convertible to PDF;
	 *         <i>false</i> otherwise
	 */
	public static boolean isConvertibleToPdf(String suffix) {

		Objects.requireNonNull(suffix);
		return ImageIO.getImageReadersBySuffix(suffix).hasNext();
	}
}
