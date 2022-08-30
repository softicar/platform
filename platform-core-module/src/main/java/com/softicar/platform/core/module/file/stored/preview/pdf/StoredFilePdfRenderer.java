package com.softicar.platform.core.module.file.stored.preview.pdf;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.string.formatting.StackTraceFormatting;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.emf.EmfI18n;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

public class StoredFilePdfRenderer {

	private static final int PREVIEW_DPI = 125;
	private static final MimeType MIME_TYPE = MimeType.IMAGE_JPEG;
	private static final String THUMBNAIL_IMAGE_TYPE = "jpg";

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

		List<IResource> imageList = new ArrayList<>();
		try (InputStream stream = file.getFileContentInputStream()) {
			try (PDDocument pdDocument = PDDocument.load(stream)) {
				for (int index = 0; index < pdDocument.getNumberOfPages(); index++) {
					var renderer = new PDFRenderer(pdDocument);
					renderer.setSubsamplingAllowed(true);
					BufferedImage image = renderer.renderImageWithDPI(index, PREVIEW_DPI);
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					ImageIO.write(image, THUMBNAIL_IMAGE_TYPE, outputStream);
					String fileName = file//
						.getFileName()
						.replace(".pdf", "")
						.concat(" ")
						.concat(EmfI18n.PAGE.toString())
						.concat(" ")
						.concat(String.valueOf(index + 1))
						.concat(".")
						.concat(THUMBNAIL_IMAGE_TYPE);
					imageList.add(new ImageByteArrayResource(outputStream, fileName, MIME_TYPE));
				}
			}
		} catch (Throwable throwable) {
			Log.ferror(StackTraceFormatting.getStackTraceAsString(throwable));
			return new ArrayList<>();
		}
		return imageList;
	}
}
