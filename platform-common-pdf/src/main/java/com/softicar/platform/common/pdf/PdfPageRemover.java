package com.softicar.platform.common.pdf;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
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

	private static final int FAST_RENDERING_DPI = 10;

	/**
	 * Identifies all consecutive leading blank pages in the given PDF byte
	 * array, and returns a new PDF byte array in which those leading blank
	 * pages are omitted.
	 *
	 * @param pdfBytes
	 *            the original PDF, as a byte array (never <i>null</i>)
	 * @return the resulting PDF, as a byte array (never <i>null</i>)
	 */
	public byte[] removeLeadingBlankPages(byte[] pdfBytes) {

		try (var document = PDDocument.load(pdfBytes)) {
			determineBlankPageIndexes(pdfBytes)//
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

	private ArrayList<Integer> determineBlankPageIndexes(byte[] pdfBytes) {

		try (var inputStream = new ByteArrayInputStream(pdfBytes)) {
			var blankPageIndexes = new ArrayList<Integer>();
			List<BufferedImage> pageImages = new PdfRenderer().setDpi(FAST_RENDERING_DPI).render(inputStream);
			for (int pageIndex = 0; pageIndex < pageImages.size(); pageIndex++) {
				BufferedImage image = pageImages.get(pageIndex);
				if (isBlank(image)) {
					blankPageIndexes.add(pageIndex);
				}
			}
			return blankPageIndexes;
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private boolean isBlank(BufferedImage image) {

		int height = image.getHeight();
		int width = image.getWidth();

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				var color = new Color(image.getRGB(x, y));
				if (color.getRed() < 255 || color.getGreen() < 255 || color.getBlue() < 255) {
					return false;
				}
			}
		}

		return true;
	}
}
