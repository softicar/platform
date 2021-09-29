package com.softicar.platform.common.pdf.converter;

import com.softicar.platform.common.core.exceptions.SofticarException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 * Converts PDF files to {@link BufferedImage} instances.
 *
 * @author Alexander Schmidt
 */
public class PdfToBufferedImageConverter {

	private static final int DEFAULT_RENDERING_DPI = 300;
	private static final ImageType DEFAULT_IMAGE_TYPE = ImageType.RGB;

	private int renderingDpi;
	private ImageType imageType;

	public PdfToBufferedImageConverter() {

		this.renderingDpi = DEFAULT_RENDERING_DPI;
		this.imageType = DEFAULT_IMAGE_TYPE;
	}

	/**
	 * Defines the DPI to use for rendering.
	 * <p>
	 * Default is {@value #DEFAULT_RENDERING_DPI}.
	 *
	 * @param renderingDpi
	 *            the number of DPI to use (must be positive)
	 * @return this {@link PdfToBufferedImageConverter}
	 */
	public PdfToBufferedImageConverter setRenderingDpi(int renderingDpi) {

		this.renderingDpi = renderingDpi;
		return this;
	}

	/**
	 * Defines the {@link ImageType} to use for rendering.
	 * <p>
	 * Default is {@link ImageType#RGB}.
	 *
	 * @param imageType
	 *            the {@link ImageType} to use (never <i>null</i>)
	 * @return this {@link PdfToBufferedImageConverter}
	 */
	public PdfToBufferedImageConverter setImageType(ImageType imageType) {

		this.imageType = imageType;
		return this;
	}

	/**
	 * Converts the given PDF-formatted {@link InputStream} to one or several
	 * {@link BufferedImage} instances.
	 * <p>
	 * Returns one {@link BufferedImage} per page.
	 * <p>
	 * The given {@link InputStream} will <b>not</b> be closed by this method.
	 *
	 * @param inputStream
	 *            the PDF-formatted {@link InputStream} to convert to a series
	 *            of images; needs to be closed by the caller (never
	 *            <i>null</i>)
	 * @return a series of images, extracted from the given {@link InputStream}
	 *         (never <i>null</i>)
	 */
	public List<BufferedImage> convertToBufferedImages(InputStream inputStream) {

		List<BufferedImage> bufferedImages = new ArrayList<>();
		try (PDDocument document = PDDocument.load(inputStream)) {
			PDFRenderer renderer = new PDFRenderer(document);
			for (int page = 0; page < document.getNumberOfPages(); page++) {
				bufferedImages.add(renderer.renderImageWithDPI(page, renderingDpi, imageType));
			}
		} catch (IOException exception) {
			throw new SofticarException(exception, "Failed to convert PDF InputStream to an image.");
		}
		return bufferedImages;
	}
}
