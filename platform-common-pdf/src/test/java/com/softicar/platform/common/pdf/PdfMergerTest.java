package com.softicar.platform.common.pdf;

import com.softicar.platform.common.testing.Asserts;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Test;

public class PdfMergerTest extends Asserts {

	@Test
	public void test() throws IOException {

		var pdfABytes = createPdf("A.first", "A.second", "A.third");
		var pdfBBytes = createPdf("B.first");
		var pdfCBytes = createPdf("C.first", "C.second");

		var mergedPdf = new ByteArrayOutputStream();
		new PdfMerger(() -> mergedPdf)//
			.addPdf(() -> new ByteArrayInputStream(pdfABytes))
			.addPdf(() -> new ByteArrayInputStream(pdfBBytes))
			.addPdf(() -> new ByteArrayInputStream(pdfCBytes))
			.merge();

		try (var document = PDDocument.load(mergedPdf.toByteArray())) {
			assertEquals(6, document.getNumberOfPages());
			assertEquals("A.first\nA.second\nA.third\nB.first\nC.first\nC.second\n", new PDFTextStripper().getText(document));
		}
	}

	private byte[] createPdf(String...texts) {

		try (var document = new PDDocument()) {
			for (var text: texts) {
				var page = new PDPage(PDRectangle.A4);
				document.addPage(page);
				try (var contentStream = new PDPageContentStream(document, page)) {
					contentStream.beginText();
					contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
					contentStream.newLineAtOffset(25, 500);
					contentStream.showText(text);
					contentStream.endText();
				}
			}
			var buffer = new ByteArrayOutputStream();
			document.save(buffer);
			return buffer.toByteArray();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
}
