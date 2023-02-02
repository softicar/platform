package com.softicar.platform.core.module.file.stored.preview.pdf;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.in.memory.InMemoryImageResource;
import com.softicar.platform.common.pdf.PdfRenderer;
import com.softicar.platform.common.string.formatting.StackTraceFormatting;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.emf.EmfI18n;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Renders a PDF {@link AGStoredFile} to rasterized images.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class StoredFilePdfRenderer {

	private static final String IMAGE_FORMAT = "jpg";
	private static final MimeType MIME_TYPE = MimeType.IMAGE_JPEG;
	private static final int DEFAULT_DPI = 250;
	private int dpi;

	/**
	 * Constructs a new {@link StoredFilePdfRenderer}.
	 */
	public StoredFilePdfRenderer() {

		this.dpi = DEFAULT_DPI;
	}

	/**
	 * Sets the number of DPI for rendering.
	 *
	 * @param dpi
	 *            the rendering DPI
	 * @return this
	 */
	public StoredFilePdfRenderer setDpi(int dpi) {

		this.dpi = dpi;
		return this;
	}

	/**
	 * Renders the pages of the given PDF into a list of images.
	 * <p>
	 * If rendering fails, an empty {@link List} is returned.
	 *
	 * @param file
	 *            the PDF file to render (never <i>null</i>)
	 * @return a {@link List} of {@link IResource} objects representing the
	 *         pages of the PDF (never <i>null</i>)
	 * @throws SofticarUserException
	 *             if the given {@link AGStoredFile} is not a PDF
	 */
	public List<IResource> renderPages(AGStoredFile file) {

		if (!file.hasMimeTypeOrExtension(MimeType.APPLICATION_PDF)) {
			throw new SofticarUserException(EmfI18n.THE_FILE_FORMAT_MUST_BE_PDF);
		}

		try (InputStream inputStream = file.getFileContentInputStream()) {
			return renderPagesInternal(inputStream, file.getFileName());
		} catch (Exception exception) {
			logRenderingException(exception, file.getFileName());
			return new ArrayList<>();
		}
	}

	/**
	 * Renders the pages of the given PDF into a list of images.
	 * <p>
	 * If rendering fails, an empty {@link List} is returned.
	 * <p>
	 * The caller is obliged to close the given {@link InputStream}.
	 *
	 * @param inputStream
	 *            an {@link InputStream} of the the PDF file to render (never
	 *            <i>null</i>)
	 * @return a {@link List} of {@link IResource} objects representing the
	 *         pages of the PDF (never <i>null</i>)
	 */
	public List<IResource> renderPages(InputStream inputStream, String pdfFilename) {

		try {
			return renderPagesInternal(inputStream, pdfFilename);
		} catch (Exception exception) {
			logRenderingException(exception, pdfFilename);
			return new ArrayList<>();
		}
	}

	private List<IResource> renderPagesInternal(InputStream stream, String pdfFilename) {

		var images = new ArrayList<IResource>();
		for (BufferedImage image: new PdfRenderer().setDpi(dpi).render(stream)) {
			var imageResource = new InMemoryImageResource(image, IMAGE_FORMAT, MIME_TYPE);
			imageResource.setFilename(getImageFilename(pdfFilename, images.size()));
			images.add(imageResource);
		}
		return images;
	}

	private String getImageFilename(String pdfFilename, int index) {

		return pdfFilename//
			.replace(".pdf", "")
			.concat(" ")
			.concat(EmfI18n.PAGE.toString())
			.concat(" ")
			.concat(String.valueOf(index + 1))
			.concat(".")
			.concat(IMAGE_FORMAT);
	}

	private void logRenderingException(Throwable throwable, String pdfFilename) {

		Log.ferror("Exception while rendering PDF: %s", pdfFilename);
		Log.ferror(StackTraceFormatting.getStackTraceAsString(throwable));
	}
}
