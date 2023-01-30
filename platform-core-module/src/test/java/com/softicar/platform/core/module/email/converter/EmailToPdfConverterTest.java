package com.softicar.platform.core.module.email.converter;

import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.pdf.PdfRenderer;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.common.ui.image.Images;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Test;

public class EmailToPdfConverterTest extends AbstractTest {

	private static final int FAST_RENDERING_DPI = 30;

	private final EmailToPdfConverter converter;
	private byte[] pdfBytes;

	public EmailToPdfConverterTest() {

		this.converter = new EmailToPdfConverter();
		this.pdfBytes = null;
	}

	@Test
	public void testConvertEmlToPdfWithEmlHtml() {

		convertEmlToPdf(EmailToPdfConverterTestFiles.EML_HTML);
		assertPdfContainsText(//
			"Hi,",
			"this is an HTML email.",
			"Bye.");
	}

	@Test
	public void testConvertEmlToPdfWithEmlHtmlAndEmbeddedImageViaContentId() {

		convertEmlToPdf(EmailToPdfConverterTestFiles.EML_HTML_WITH_EMBEDDED_IMAGE_VIA_CONTENT_ID);
		assertPdfContainsText(//
			"Hello,",
			"consider this:",
			"Yadda yadda.",
			"Kind regards.");
		assertPdfContainsPixelsWithColor(new Color(16, 144, 12), 100);
	}

	@Test
	public void testConvertEmlToPdfWithEmlHtmlAndEmbeddedImageViaContentIdAndBodyEncodingBase64() {

		convertEmlToPdf(EmailToPdfConverterTestFiles.EML_HTML_WITH_EMBEDDED_IMAGE_VIA_CONTENT_ID_AND_BODY_ENCODING_BASE64);
		assertPdfContainsText(//
			"Hope you had time to recharge.",
			"Did you find this email helpful?",
			"This is a mandatory service communication.");
		assertPdfContainsPixelsWithColor(new Color(255, 0, 151), 10);
	}

	@Test
	public void testConvertEmlToPdfWithEmlHtmlAndEmbeddedImageViaXAttachmentId() {

		convertEmlToPdf(EmailToPdfConverterTestFiles.EML_HTML_WITH_EMBEDDED_IMAGE_VIA_X_ATTACHMENT_ID);
		assertPdfContainsText(//
			"Hello,",
			"consider this:",
			"Yadda yadda.",
			"Kind regards.");
		assertPdfContainsPixelsWithColor(new Color(16, 144, 12), 100);
	}

	@Test
	public void testConvertEmlToPdfWithEmlPlainFile() {

		convertEmlToPdf(EmailToPdfConverterTestFiles.EML_PLAIN);
		assertPdfContainsText(//
			"Hi,",
			"this is a plain text email.",
			"Bye.");
	}

	@Test(expected = RuntimeException.class)
	public void testConvertEmlToPdfWithMsgFile() {

		convertEmlToPdf(EmailToPdfConverterTestFiles.MSG_HTML);
	}

	@Test
	public void testConvertMsgToPdfWithMsgHtmlFile() {

		convertMsgToPdf(EmailToPdfConverterTestFiles.MSG_HTML);
		assertPdfContainsText(//
			"Hi,",
			"das ist eine Mail mit HTML.",
			"Bye.");
	}

	@Test
	public void testConvertMsgToPdfWithMsgPlainFile() {

		convertMsgToPdf(EmailToPdfConverterTestFiles.MSG_PLAIN);
		assertPdfContainsText(//
			"Hi,",
			"das ist eine Mail ohne HTML.",
			"Bye.");
	}

	@Test(expected = RuntimeException.class)
	public void testConvertMsgToPdfWithEmlFile() {

		convertMsgToPdf(EmailToPdfConverterTestFiles.EML_HTML);
	}

	private void convertEmlToPdf(IResourceSupplier testResourceSupplier) {

		this.pdfBytes = converter.convertEmlToPdf(testResourceSupplier.getResource()::getResourceAsStream);
	}

	private void convertMsgToPdf(IResourceSupplier testResourceSupplier) {

		this.pdfBytes = converter.convertMsgToPdf(testResourceSupplier.getResource()::getResourceAsStream);
	}

	private List<BufferedImage> renderPageImages() {

		return new PdfRenderer().setDpi(FAST_RENDERING_DPI).render(new ByteArrayInputStream(pdfBytes));
	}

	private void assertPdfContainsText(String...expectedTokens) {

		String actualText = extractText(pdfBytes);
		int cursor = -1;
		for (String expectedToken: expectedTokens) {
			cursor = actualText.indexOf(expectedToken, cursor);
			if (cursor < 0) {
				throw new AssertionError(//
					"Expected tokens are out of sequence, or token '%s' was not contained in: '%s'".formatted(expectedToken, actualText));
			}
		}
	}

	private void assertPdfContainsPixelsWithColor(Color expectedColor, int expectedColorPixels) {

		List<BufferedImage> pageImages = renderPageImages();
		assertEquals(1, pageImages.size());
		assertTrue(//
			"Failed to find pixels in the marker color of an embedded image. Assuming that the embedded image is missing from the rendered PDF document.",
			Images.countPixelsWithColor(pageImages.get(0), expectedColor) >= expectedColorPixels);
	}

	private String extractText(byte[] pdfBytes) {

		try (var document = PDDocument.load(pdfBytes)) {
			return new PDFTextStripper().getText(document);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
}
