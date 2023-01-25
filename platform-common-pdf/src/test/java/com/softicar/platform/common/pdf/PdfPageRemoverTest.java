package com.softicar.platform.common.pdf;

import com.softicar.platform.common.io.buffer.ByteBuffer;
import com.softicar.platform.common.testing.AbstractTest;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Test;

public class PdfPageRemoverTest extends AbstractTest {

	@Test
	public void testRemoveBlankPagesWithBlankPages() throws IOException {

		byte[] inputPdfBytes = new ByteBuffer(PdfTestFiles.PDF_WITH_BLANK_PAGES.getResource()::getResourceAsStream).getBytes();

		byte[] outputPdfBytes = new PdfPageRemover(inputPdfBytes).removeBlankPages();

		try (var document = PDDocument.load(outputPdfBytes)) {
			assertEquals(2, document.getNumberOfPages());
			assertEquals("Third page\nFifth page\n", new PDFTextStripper().getText(document));
		}
	}

	@Test
	public void testRemoveBlankPagesWithoutBlankPages() throws IOException {

		byte[] inputPdfBytes = new ByteBuffer(PdfTestFiles.PDF_WITHOUT_BLANK_PAGES.getResource()::getResourceAsStream).getBytes();

		byte[] outputPdfBytes = new PdfPageRemover(inputPdfBytes).removeBlankPages();

		try (var document = PDDocument.load(outputPdfBytes)) {
			assertEquals(5, document.getNumberOfPages());
			assertEquals("First page\nSecond page\nThird page\nFourth page\nFifth page\n", new PDFTextStripper().getText(document));
		}
	}
}
