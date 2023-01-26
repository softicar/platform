package com.softicar.platform.common.pdf;

import com.softicar.platform.common.ui.image.Images;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;

/**
 * Facilitates removing pages from PDF documents.
 *
 * @author Alexander Schmidt
 */
public class PdfPageRemover {

	private static final int DEFAULT_DPI = 30;
	private final byte[] pdfBytes;
	private int dpi;

	/**
	 * Constructs a {@link PdfPageRemover}.
	 *
	 * @param pdfBytes
	 *            the original PDF, as a byte array (never <i>null</i>)
	 */
	public PdfPageRemover(byte[] pdfBytes) {

		this.pdfBytes = pdfBytes;
		this.dpi = DEFAULT_DPI;
	}

	/**
	 * Defines the DPI to use for internal page rendering.
	 * <p>
	 * If a page rendered at this DPI value is all-white, it will be considered
	 * blank.
	 * <p>
	 * The given value may be relatively low, e.g. 30 DPI. Higher values will
	 * increase accuracy of the blankness detection, at the expense of
	 * performance.
	 *
	 * @param dpi
	 *            the DPI for rendering
	 */
	public void setDpi(int dpi) {

		this.dpi = dpi;
	}

	/**
	 * Identifies all blank pages in the original PDF, and returns a new PDF
	 * byte array in which those blank pages are omitted.
	 *
	 * @return the resulting PDF, as a byte array (never <i>null</i>)
	 */
	public byte[] removeBlankPages() {

		try (var document = PDDocument.load(pdfBytes)) {
			determineBlankPageIndexes(dpi, pdfBytes)//
				.stream()
				.sorted(Collections.reverseOrder())
				.forEach(document::removePage);
			var outputStream = new ByteArrayOutputStream();
			document.save(outputStream);
			return outputStream.toByteArray();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private static List<Integer> determineBlankPageIndexes(int dpi, byte[] pdfBytes) {

		var blankPageIndexes = new ArrayList<Integer>();
		List<BufferedImage> pageImages = new PdfRenderer().setDpi(dpi).render(pdfBytes);
		for (int pageIndex = 0; pageIndex < pageImages.size(); pageIndex++) {
			BufferedImage image = pageImages.get(pageIndex);
			if (Images.isSingleColor(image, Color.WHITE)) {
				blankPageIndexes.add(pageIndex);
			}
		}
		return blankPageIndexes;
	}
}
