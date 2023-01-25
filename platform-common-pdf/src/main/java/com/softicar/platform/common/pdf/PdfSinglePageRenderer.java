package com.softicar.platform.common.pdf;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 * Renders a single page of a PDF document into a {@link BufferedImage}
 * instance.
 *
 * @author Alexander Schmidt
 */
class PdfSinglePageRenderer implements Runnable {

	private final int dpi;
	private final ImageType imageType;
	private final int pageIndex;
	private final byte[] pdfBytes;
	private volatile BufferedImage image;

	public PdfSinglePageRenderer(int dpi, ImageType imageType, int pageIndex, byte[] pdfBytes) {

		this.dpi = dpi;
		this.imageType = imageType;
		this.pageIndex = pageIndex;
		this.pdfBytes = pdfBytes;
		this.image = null;
	}

	/**
	 * Renders the pages of the {@link PDDocument} into a {@link BufferedImage}
	 * instance.
	 * <p>
	 * The resulting {@link BufferedImage} instance can be retrieved via
	 * {@link #getImage()}.
	 */
	@Override
	public void run() {

		try (var document = PDDocument.load(pdfBytes)) {
			var renderer = new PDFRenderer(document);
			renderer.setSubsamplingAllowed(true);
			this.image = renderer.renderImageWithDPI(pageIndex, dpi, imageType);
		} catch (IOException exception) {
			throw new SofticarIOException(exception, "Failed to render PDF.");
		}
	}

	/**
	 * Returns the rendered image, or <i>null</i> if rendering has not yet
	 * concluded.
	 *
	 * @return the rendered image (may be <i>null</i>)
	 */
	public BufferedImage getImage() {

		return image;
	}
}
