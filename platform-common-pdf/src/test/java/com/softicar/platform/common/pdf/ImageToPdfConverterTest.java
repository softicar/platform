package com.softicar.platform.common.pdf;

import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.testing.Asserts;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.junit.Test;

public class ImageToPdfConverterTest extends Asserts {

	@Test
	public void testConvertToPdf() throws IOException {

		var pdfBytes = new ImageToPdfConverter(PdfTestFiles.TIFF_MULTI_IMAGE.getResource()::getResourceAsStream).convertToPdf();

		try (var pdfDocument = PDDocument.load(pdfBytes)) {
			assertEquals(3, pdfDocument.getNumberOfPages());
			assertMediaBox(300, 150, pdfDocument.getPage(0));
			assertMediaBox(350, 200, pdfDocument.getPage(1));
			assertMediaBox(400, 200, pdfDocument.getPage(2));
		}
	}

	@Test
	public void testIsConvertibleToPdfWithMimeTypeJpg() {

		assertTrue(ImageToPdfConverter.isConvertibleToPdf(MimeType.IMAGE_JPEG));
		assertTrue(ImageToPdfConverter.isConvertibleToPdf(MimeType.IMAGE_PNG));
		assertTrue(ImageToPdfConverter.isConvertibleToPdf(MimeType.IMAGE_TIFF));
		assertFalse(ImageToPdfConverter.isConvertibleToPdf(MimeType.APPLICATION_PDF));
	}

	@Test
	public void testIsConvertibleToPdfWithSuffixJpg() {

		assertTrue(ImageToPdfConverter.isConvertibleToPdf("jpg"));
		assertTrue(ImageToPdfConverter.isConvertibleToPdf("JPG"));
		assertTrue(ImageToPdfConverter.isConvertibleToPdf("jpeg"));
		assertTrue(ImageToPdfConverter.isConvertibleToPdf("JPEG"));

		assertTrue(ImageToPdfConverter.isConvertibleToPdf("png"));
		assertTrue(ImageToPdfConverter.isConvertibleToPdf("PNG"));

		assertTrue(ImageToPdfConverter.isConvertibleToPdf("tif"));
		assertTrue(ImageToPdfConverter.isConvertibleToPdf("TIF"));
		assertTrue(ImageToPdfConverter.isConvertibleToPdf("tiff"));
		assertTrue(ImageToPdfConverter.isConvertibleToPdf("TIFF"));

		assertFalse(ImageToPdfConverter.isConvertibleToPdf("pdf"));
		assertFalse(ImageToPdfConverter.isConvertibleToPdf("PDF"));
	}

	private void assertMediaBox(int expectedWidth, int expectedHeight, PDPage page) {

		String expectedMediaBox = "[0.0,0.0,%s.0,%s.0]".formatted(expectedWidth, expectedHeight);
		assertEquals(expectedMediaBox, page.getMediaBox().toString());
	}
}
