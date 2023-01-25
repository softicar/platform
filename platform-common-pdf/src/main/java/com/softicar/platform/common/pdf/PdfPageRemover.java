package com.softicar.platform.common.pdf;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import org.apache.pdfbox.pdmodel.PDDocument;

/**
 * Facilitates removing pages from PDF documents.
 *
 * @author Alexander Schmidt
 */
public class PdfPageRemover {

	private static final int DEFAULT_DPI = 30;
	private final Supplier<PDDocument> documentSupplier;
	private int dpi;

	/**
	 * Constructs a {@link PdfPageRemover}.
	 *
	 * @param pdfBytes
	 *            the original PDF, as a byte array (never <i>null</i>)
	 */
	public PdfPageRemover(byte[] pdfBytes) {

		this(() -> createDocument(pdfBytes));
	}

	/**
	 * Constructs a {@link PdfPageRemover}.
	 *
	 * @param documentSupplier
	 *            a {@link Supplier} of an {@link PDDocument} (never
	 *            <i>null</i>)
	 */
	public PdfPageRemover(Supplier<PDDocument> documentSupplier) {

		this.documentSupplier = documentSupplier;
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
	 * Identifies all consecutive leading blank pages in the original PDF, and
	 * returns a new PDF byte array in which those leading blank pages are
	 * omitted.
	 *
	 * @return the resulting PDF, as a byte array (never <i>null</i>)
	 */
	public byte[] removeLeadingBlankPages() {

		try (var document = documentSupplier.get()) {
			determineBlankPageIndexes(document)//
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

	private ArrayList<Integer> determineBlankPageIndexes(PDDocument document) {

		var blankPageIndexes = new ArrayList<Integer>();
		List<BufferedImage> pageImages = new PdfRenderer().setDpi(dpi).render(document);
		for (int pageIndex = 0; pageIndex < pageImages.size(); pageIndex++) {
			BufferedImage image = pageImages.get(pageIndex);
			if (isBlank(image)) {
				blankPageIndexes.add(pageIndex);
			}
		}
		return blankPageIndexes;
	}

	private boolean isBlank(BufferedImage image) {

		int height = image.getHeight();
		int width = image.getWidth();

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (!new Color(image.getRGB(x, y)).equals(Color.WHITE)) {
					return false;
				}
			}
		}

		return true;
	}

	private static PDDocument createDocument(byte[] pdfBytes) {

		try {
			return PDDocument.load(pdfBytes);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
}
