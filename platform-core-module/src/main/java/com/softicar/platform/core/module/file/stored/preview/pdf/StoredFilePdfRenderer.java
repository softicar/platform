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
import java.util.Optional;

public class StoredFilePdfRenderer {

	private static final String IMAGE_FORMAT = "jpg";
	private static final MimeType MIME_TYPE = MimeType.IMAGE_JPEG;
	private static final int PREVIEW_DPI = 125;

	/**
	 * Renders the pages of a PDF into a list of images.
	 *
	 * @param file
	 *            the PDF file to convert (never <i>null</i>)
	 * @return a {@link List} of {@link IResource} objects representing the
	 *         pages of the PDF (never <i>null</i>)
	 * @throws SofticarUserException
	 *             if the content type of the given {@link AGStoredFile} is not
	 *             {@link MimeType#APPLICATION_PDF}
	 */
	public static List<IResource> renderPages(AGStoredFile file) {

		Optional
			.ofNullable(file.getContentType())
			.filter(contentType -> contentType.contains("pdf"))
			.orElseThrow(() -> new SofticarUserException(EmfI18n.THE_FILE_FORMAT_MUST_BE_PDF));

		try (InputStream stream = file.getFileContentInputStream()) {
			return renderPages(stream, file.getFileName());
		} catch (Throwable throwable) {
			Log.ferror("Exception while rendering PDF: %s", file.getFileName());
			Log.ferror(StackTraceFormatting.getStackTraceAsString(throwable));
			return new ArrayList<>();
		}
	}

	private static List<IResource> renderPages(InputStream stream, String pdfFilename) {

		var images = new ArrayList<IResource>();
		for (BufferedImage image: new PdfRenderer().setDpi(PREVIEW_DPI).render(stream)) {
			var imageResource = new InMemoryImageResource(image, IMAGE_FORMAT, MIME_TYPE);
			imageResource.setFilename(getImageFilename(pdfFilename, images.size()));
			images.add(imageResource);
		}
		return images;
	}

	private static String getImageFilename(String pdfFilename, int index) {

		return pdfFilename//
			.replace(".pdf", "")
			.concat(" ")
			.concat(EmfI18n.PAGE.toString())
			.concat(" ")
			.concat(String.valueOf(index + 1))
			.concat(".")
			.concat(IMAGE_FORMAT);
	}
}
