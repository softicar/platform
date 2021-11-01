package com.softicar.platform.core.module.file.stored.preview.pdf;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.preview.LimitedWidthZoomableImage;
import com.softicar.platform.dom.style.ICssLength;
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

public class StoredFilePdfToZoomableImagesConverter {

	private static final int PREVIEW_DPI = 125;
	private static final MimeType MIME_TYPE = MimeType.IMAGE_JPEG;
	private static final String THUMBNAIL_IMAGE_TYPE = "jpg";

	/**
	 * This converts a given {@link AGStoredFile} of type PDF to an list of
	 * {@link LimitedWidthZoomableImage}. The converted image type is jpg.
	 *
	 * @param file
	 *            The file to convert. Needs to be a PDF, or else an exception
	 *            will be thrown.
	 * @param initialMaxWidth
	 *            The initial max width of the converted images when they are
	 *            not zoomed-in.
	 * @return An {@link ArrayList} consisting of
	 *         {@link LimitedWidthZoomableImage}.
	 */
	public static List<LimitedWidthZoomableImage> convertPagesToImages(AGStoredFile file, ICssLength initialMaxWidth) {

		Optional
			.ofNullable(file.getContentType())
			.filter(contentType -> contentType.contains("pdf"))
			.orElseThrow(() -> new SofticarUserException(EmfI18n.THE_FILE_FORMAT_MUST_BE_PDF));

		List<LimitedWidthZoomableImage> imageList = new ArrayList<>();
		try (InputStream stream = file.getFileContentInputStream()) {
			try (PDDocument pdDocument = PDDocument.load(stream)) {
				for (int index = 0; index < pdDocument.getNumberOfPages(); index++) {
					BufferedImage image = new PDFRenderer(pdDocument).renderImageWithDPI(index, PREVIEW_DPI);
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
					imageList.add(new LimitedWidthZoomableImage(new ImageByteArrayResource(outputStream, fileName, MIME_TYPE), initialMaxWidth));
				}
			}
		} catch (Throwable throwable) {
			//FIXME i75168 Sometimes the server somehow can't load the JBIG2ImageReader class from pdfbox library, check and fix
			DevNull.swallow(throwable);
			return new ArrayList<>();
		}
		return imageList;
	}
}
