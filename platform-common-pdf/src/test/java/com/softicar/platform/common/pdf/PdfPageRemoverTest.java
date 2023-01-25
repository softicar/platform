package com.softicar.platform.common.pdf;

import com.softicar.platform.common.io.buffer.ByteBuffer;
import com.softicar.platform.common.testing.AbstractTest;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Test;

public class PdfPageRemoverTest extends AbstractTest {

	@Test
	public void testRemoveLeadingBlankPagesWithLeadingBlankPages() throws IOException {

		byte[] inputPdfBytes = new ByteBuffer(PdfTestFiles.PDF_WITH_TWO_LEADING_BLANK_PAGES.getResource()::getResourceAsStream).getBytes();

		byte[] outputPdfBytes = new PdfPageRemover(inputPdfBytes).removeLeadingBlankPages();

		try (var document = PDDocument.load(outputPdfBytes)) {
			assertEquals(1, document.getNumberOfPages());
			assertEquals("Third page\n", new PDFTextStripper().getText(document));
		}
	}

	@Test
	public void testRemoveLeadingBlankPagesWithoutLeadingBlankPages() throws IOException {

		byte[] inputPdfBytes = new ByteBuffer(PdfTestFiles.PDF_WITHOUT_LEADING_BLANK_PAGES.getResource()::getResourceAsStream).getBytes();

		byte[] outputPdfBytes = new PdfPageRemover(inputPdfBytes).removeLeadingBlankPages();

		try (var document = PDDocument.load(outputPdfBytes)) {
			assertEquals(3, document.getNumberOfPages());
			assertEquals("First page\nSecond page\nThird page\n", new PDFTextStripper().getText(document));
		}
	}
}
