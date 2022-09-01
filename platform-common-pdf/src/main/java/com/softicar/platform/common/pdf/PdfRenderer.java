package com.softicar.platform.common.pdf;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 * Renders the pages of a PDF file into {@link BufferedImage} instances.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class PdfRenderer {

	private static final int DEFAULT_DPI = 300;
	private static final ImageType DEFAULT_IMAGE_TYPE = ImageType.RGB;

	private int dpi;
	private ImageType imageType;

	public PdfRenderer() {

		this.dpi = DEFAULT_DPI;
		this.imageType = DEFAULT_IMAGE_TYPE;
	}

	/**
	 * Defines the DPI to use for rendering.
	 * <p>
	 * Default is {@value #DEFAULT_DPI}.
	 *
	 * @param dpi
	 *            the number of DPI to use (must be positive)
	 * @return this {@link PdfRenderer}
	 */
	public PdfRenderer setDpi(int dpi) {

		this.dpi = dpi;
		return this;
	}

	/**
	 * Defines the {@link ImageType} to use for rendering.
	 * <p>
	 * Default is {@link ImageType#RGB}.
	 *
	 * @param imageType
	 *            the {@link ImageType} to use (never <i>null</i>)
	 * @return this {@link PdfRenderer}
	 */
	public PdfRenderer setImageType(ImageType imageType) {

		this.imageType = imageType;
		return this;
	}

	/**
	 * Renders the pages of the PDF into one or more {@link BufferedImage}
	 * instances.
	 * <p>
	 * The given {@link InputStream} will <b>not</b> be closed by this method.
	 *
	 * @param inputStream
	 *            the PDF-formatted {@link InputStream} to render; needs to be
	 *            closed by the caller (never <i>null</i>)
	 * @return list of rendered images; one per page (never <i>null</i>)
	 */
	public List<BufferedImage> render(InputStream inputStream) {

		var bufferedImages = new ArrayList<BufferedImage>();
		try (var document = PDDocument.load(inputStream)) {
			var renderer = new PDFRenderer(document);
			renderer.setSubsamplingAllowed(true);
			for (var page = 0; page < document.getNumberOfPages(); page++) {
				bufferedImages.add(renderer.renderImageWithDPI(page, dpi, imageType));
			}
		} catch (IOException exception) {
			throw new SofticarIOException(exception, "Failed to render PDF.");
		}
		return bufferedImages;
	}
}
