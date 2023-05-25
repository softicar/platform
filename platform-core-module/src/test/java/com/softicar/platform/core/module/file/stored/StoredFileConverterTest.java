package com.softicar.platform.core.module.file.stored;

import com.softicar.platform.common.io.mime.IMimeType;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.pdf.PdfRenderer;
import com.softicar.platform.common.ui.image.Images;
import com.softicar.platform.core.module.email.converter.EmailToPdfConverterTestFiles;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.junit.Test;

public class StoredFileConverterTest extends AbstractCoreTest {

	private static final int FAST_RENDERING_DPI = 30;
	private static final int PIXEL_COUNT_TOLERANCE = 25;
	private static final Color EXPECTED_BACKGROUND_COLOR_JPG = new Color(157, 116, 0);
	private static final Color EXPECTED_BACKGROUND_COLOR_PNG = new Color(17, 144, 13);
	private static final Color EXPECTED_BACKGROUND_COLOR_TIFF_PAGE_1 = new Color(205, 74, 144);
	private static final Color EXPECTED_BACKGROUND_COLOR_TIFF_PAGE_2 = new Color(205, 73, 146);

	private final AGStoredFile misclassifiedEmlFile;
	private final AGStoredFile unsupportedFile;

	public StoredFileConverterTest() {

		this.misclassifiedEmlFile = insertStoredFile(//
			"foo.eml",
			MimeType.MESSAGE_RFC822,
			"content of a file that is erroneously stored as EML".getBytes());

		this.unsupportedFile = insertStoredFile(//
			"foo.bar",
			MimeType.APPLICATION_OCTET_STREAM,
			"content of an unsupported file type".getBytes());
	}

	@Test
	public void testToPdfBytesWithEmlFile() {

		AGStoredFile file = insertStoredFile("foo.eml", MimeType.MESSAGE_RFC822, EmailToPdfConverterTestFiles.EML_HTML);
		Optional<byte[]> pdfBytes = new StoredFileConverter(file).toPdfBytes();
		assertTrue(pdfBytes.isPresent());
	}

	@Test
	public void testToPdfBytesWithEmlFileAndOctetStream() {

		AGStoredFile file = insertStoredFile("foo.eml", MimeType.APPLICATION_OCTET_STREAM, EmailToPdfConverterTestFiles.EML_HTML);
		Optional<byte[]> pdfBytes = new StoredFileConverter(file).toPdfBytes();
		assertTrue(pdfBytes.isPresent());
	}

	@Test
	public void testToPdfBytesWithEmlFileAndDifferentExtension() {

		AGStoredFile file = insertStoredFile("foo.xxx", MimeType.MESSAGE_RFC822, EmailToPdfConverterTestFiles.EML_HTML);
		Optional<byte[]> pdfBytes = new StoredFileConverter(file).toPdfBytes();
		assertTrue(pdfBytes.isPresent());
	}

	@Test
	public void testToPdfBytesWithMsgFile() {

		AGStoredFile file = insertStoredFile("foo.msg", MimeType.APPLICATION_VND_MS_OUTLOOK, EmailToPdfConverterTestFiles.MSG_HTML);
		Optional<byte[]> pdfBytes = new StoredFileConverter(file).toPdfBytes();
		assertTrue(pdfBytes.isPresent());
	}

	@Test
	public void testToPdfBytesWithMsgFileAndOctetStream() {

		AGStoredFile file = insertStoredFile("foo.msg", MimeType.APPLICATION_OCTET_STREAM, EmailToPdfConverterTestFiles.MSG_HTML);
		Optional<byte[]> pdfBytes = new StoredFileConverter(file).toPdfBytes();
		assertTrue(pdfBytes.isPresent());
	}

	@Test
	public void testToPdfBytesWithMsgFileAndDifferentExtension() {

		AGStoredFile file = insertStoredFile("foo.xxx", MimeType.APPLICATION_VND_MS_OUTLOOK, EmailToPdfConverterTestFiles.MSG_HTML);
		Optional<byte[]> pdfBytes = new StoredFileConverter(file).toPdfBytes();
		assertTrue(pdfBytes.isPresent());
	}

	@Test
	public void testToPdfBytesWithJpgFile() {

		AGStoredFile file = insertStoredFile("foo.jpg", MimeType.IMAGE_JPEG, StoredFileConverterTestFiles.JPG_IMAGE_300X150);
		Optional<byte[]> pdfBytes = new StoredFileConverter(file).toPdfBytes();
		new Asserter(pdfBytes)//
			.nextPage()
			.assertDimensions(125, 62)
			.assertPixels(EXPECTED_BACKGROUND_COLOR_JPG, 6560)
			.assertNoMorePages();
	}

	@Test
	public void testToPdfBytesWithJpgFileAndOctetStream() {

		AGStoredFile file = insertStoredFile("foo.jpg", MimeType.APPLICATION_OCTET_STREAM, StoredFileConverterTestFiles.JPG_IMAGE_300X150);
		Optional<byte[]> pdfBytes = new StoredFileConverter(file).toPdfBytes();
		new Asserter(pdfBytes)//
			.nextPage()
			.assertDimensions(125, 62)
			.assertPixels(EXPECTED_BACKGROUND_COLOR_JPG, 6560)
			.assertNoMorePages();
	}

	@Test
	public void testToPdfBytesWithJpgFileAndDifferentExtension() {

		AGStoredFile file = insertStoredFile("foo.xxx", MimeType.IMAGE_JPEG, StoredFileConverterTestFiles.JPG_IMAGE_300X150);
		Optional<byte[]> pdfBytes = new StoredFileConverter(file).toPdfBytes();
		new Asserter(pdfBytes)//
			.nextPage()
			.assertDimensions(125, 62)
			.assertPixels(EXPECTED_BACKGROUND_COLOR_JPG, 6560)
			.assertNoMorePages();
	}

	@Test
	public void testToPdfBytesWithPngFile() {

		AGStoredFile file = insertStoredFile("foo.png", MimeType.IMAGE_PNG, StoredFileConverterTestFiles.PNG_IMAGE_350X200);
		Optional<byte[]> pdfBytes = new StoredFileConverter(file).toPdfBytes();
		new Asserter(pdfBytes)//
			.nextPage()
			.assertDimensions(145, 83)
			.assertPixels(EXPECTED_BACKGROUND_COLOR_PNG, 10593)
			.assertNoMorePages();
	}

	@Test
	public void testToPdfBytesWithPngFileAndOctetStream() {

		AGStoredFile file = insertStoredFile("foo.png", MimeType.APPLICATION_OCTET_STREAM, StoredFileConverterTestFiles.PNG_IMAGE_350X200);
		Optional<byte[]> pdfBytes = new StoredFileConverter(file).toPdfBytes();
		new Asserter(pdfBytes)//
			.nextPage()
			.assertDimensions(145, 83)
			.assertPixels(EXPECTED_BACKGROUND_COLOR_PNG, 10593)
			.assertNoMorePages();
	}

	@Test
	public void testToPdfBytesWithPngFileAndDifferentExtension() {

		AGStoredFile file = insertStoredFile("foo.xxx", MimeType.IMAGE_PNG, StoredFileConverterTestFiles.PNG_IMAGE_350X200);
		Optional<byte[]> pdfBytes = new StoredFileConverter(file).toPdfBytes();
		new Asserter(pdfBytes)//
			.nextPage()
			.assertDimensions(145, 83)
			.assertPixels(EXPECTED_BACKGROUND_COLOR_PNG, 10593)
			.assertNoMorePages();
	}

	@Test
	public void testToPdfBytesWithTiffFile() {

		AGStoredFile file = insertStoredFile("foo.tif", MimeType.IMAGE_TIFF, StoredFileConverterTestFiles.TIFF_IMAGE_400X200);
		Optional<byte[]> pdfBytes = new StoredFileConverter(file).toPdfBytes();
		new Asserter(pdfBytes)//
			.nextPage()
			.assertDimensions(166, 83)
			.assertPixels(EXPECTED_BACKGROUND_COLOR_TIFF_PAGE_1, 12235)
			.nextPage()
			.assertDimensions(106, 53)
			.assertPixels(EXPECTED_BACKGROUND_COLOR_TIFF_PAGE_2, 4210)
			.assertNoMorePages();
	}

	@Test
	public void testToPdfBytesWithTiffFileAndOctetStream() {

		AGStoredFile file = insertStoredFile("foo.tif", MimeType.APPLICATION_OCTET_STREAM, StoredFileConverterTestFiles.TIFF_IMAGE_400X200);
		Optional<byte[]> pdfBytes = new StoredFileConverter(file).toPdfBytes();
		new Asserter(pdfBytes)//
			.nextPage()
			.assertDimensions(166, 83)
			.assertPixels(EXPECTED_BACKGROUND_COLOR_TIFF_PAGE_1, 12235)
			.nextPage()
			.assertDimensions(106, 53)
			.assertPixels(EXPECTED_BACKGROUND_COLOR_TIFF_PAGE_2, 4210)
			.assertNoMorePages();
	}

	@Test
	public void testToPdfBytesWithTiffFileAndDifferentExtension() {

		AGStoredFile file = insertStoredFile("foo.xxx", MimeType.IMAGE_TIFF, StoredFileConverterTestFiles.TIFF_IMAGE_400X200);
		Optional<byte[]> pdfBytes = new StoredFileConverter(file).toPdfBytes();
		new Asserter(pdfBytes)//
			.nextPage()
			.assertDimensions(166, 83)
			.assertPixels(EXPECTED_BACKGROUND_COLOR_TIFF_PAGE_1, 12235)
			.nextPage()
			.assertDimensions(106, 53)
			.assertPixels(EXPECTED_BACKGROUND_COLOR_TIFF_PAGE_2, 4210)
			.assertNoMorePages();
	}

	@Test
	public void testToPdfBytesWithUnidentifiedEmailFile() {

		AGStoredFile file = insertStoredFile("foo.xxx", MimeType.APPLICATION_OCTET_STREAM, EmailToPdfConverterTestFiles.EML_HTML);
		Optional<byte[]> pdfBytes = new StoredFileConverter(file).toPdfBytes();
		assertFalse(pdfBytes.isPresent());
	}

	@Test(expected = RuntimeException.class)
	public void testToPdfBytesWithMisclassifiedFile() {

		new StoredFileConverter(misclassifiedEmlFile).toPdfBytes();
	}

	@Test
	public void testToPdfBytesWithUnsupportedFile() {

		Optional<byte[]> pdfBytes = new StoredFileConverter(unsupportedFile).toPdfBytes();
		assertFalse(pdfBytes.isPresent());
	}

	@Test
	public void testIsConvertibleToPdfWithEmlFile() {

		AGStoredFile file = insertStoredFile("foo.eml", MimeType.MESSAGE_RFC822, EmailToPdfConverterTestFiles.EML_HTML);
		boolean convertibleToPdf = new StoredFileConverter(file).isConvertibleToPdf();
		assertTrue(convertibleToPdf);
	}

	@Test
	public void testIsConvertibleToPdfWithEmlFileAndOctetStream() {

		AGStoredFile file = insertStoredFile("foo.eml", MimeType.APPLICATION_OCTET_STREAM, EmailToPdfConverterTestFiles.EML_HTML);
		boolean convertibleToPdf = new StoredFileConverter(file).isConvertibleToPdf();
		assertTrue(convertibleToPdf);
	}

	@Test
	public void testIsConvertibleToPdfWithEmlFileAndDifferentExtension() {

		AGStoredFile file = insertStoredFile("foo.xxx", MimeType.MESSAGE_RFC822, EmailToPdfConverterTestFiles.EML_HTML);
		boolean convertibleToPdf = new StoredFileConverter(file).isConvertibleToPdf();
		assertTrue(convertibleToPdf);
	}

	@Test
	public void testIsConvertibleToPdfWithMsgFile() {

		AGStoredFile file = insertStoredFile("foo.msg", MimeType.APPLICATION_VND_MS_OUTLOOK, EmailToPdfConverterTestFiles.MSG_HTML);
		boolean convertibleToPdf = new StoredFileConverter(file).isConvertibleToPdf();
		assertTrue(convertibleToPdf);
	}

	@Test
	public void testIsConvertibleToPdfWithMsgFileAndOctetStream() {

		AGStoredFile file = insertStoredFile("foo.msg", MimeType.APPLICATION_OCTET_STREAM, EmailToPdfConverterTestFiles.MSG_HTML);
		boolean convertibleToPdf = new StoredFileConverter(file).isConvertibleToPdf();
		assertTrue(convertibleToPdf);
	}

	@Test
	public void testIsConvertibleToPdfWithMsgFileAndDifferentExtension() {

		AGStoredFile file = insertStoredFile("foo.xxx", MimeType.APPLICATION_VND_MS_OUTLOOK, EmailToPdfConverterTestFiles.MSG_HTML);
		boolean convertibleToPdf = new StoredFileConverter(file).isConvertibleToPdf();
		assertTrue(convertibleToPdf);
	}

	@Test
	public void testIsConvertibleToPdfWithJpgFile() {

		AGStoredFile file = insertStoredFile("foo.jpg", MimeType.IMAGE_JPEG, StoredFileConverterTestFiles.JPG_IMAGE_300X150);
		assertTrue(new StoredFileConverter(file).isConvertibleToPdf());
	}

	@Test
	public void testIsConvertibleToPdfWithJpgFileAndOctetStream() {

		AGStoredFile file = insertStoredFile("foo.jpg", MimeType.APPLICATION_OCTET_STREAM, StoredFileConverterTestFiles.JPG_IMAGE_300X150);
		assertTrue(new StoredFileConverter(file).isConvertibleToPdf());
	}

	@Test
	public void testIsConvertibleToPdfWithJpgFileAndDifferentExtension() {

		AGStoredFile file = insertStoredFile("foo.xxx", MimeType.IMAGE_JPEG, StoredFileConverterTestFiles.JPG_IMAGE_300X150);
		assertTrue(new StoredFileConverter(file).isConvertibleToPdf());
	}

	@Test
	public void testIsConvertibleToPdfWithPngFile() {

		AGStoredFile file = insertStoredFile("foo.png", MimeType.IMAGE_PNG, StoredFileConverterTestFiles.PNG_IMAGE_350X200);
		assertTrue(new StoredFileConverter(file).isConvertibleToPdf());
	}

	@Test
	public void testIsConvertibleToPdfWithPngFileAndOctetStream() {

		AGStoredFile file = insertStoredFile("foo.png", MimeType.APPLICATION_OCTET_STREAM, StoredFileConverterTestFiles.PNG_IMAGE_350X200);
		assertTrue(new StoredFileConverter(file).isConvertibleToPdf());
	}

	@Test
	public void testIsConvertibleToPdfWithPngFileAndDifferentExtension() {

		AGStoredFile file = insertStoredFile("foo.xxx", MimeType.IMAGE_PNG, StoredFileConverterTestFiles.PNG_IMAGE_350X200);
		assertTrue(new StoredFileConverter(file).isConvertibleToPdf());
	}

	@Test
	public void testIsConvertibleToPdfWithTiffFile() {

		AGStoredFile file = insertStoredFile("foo.tiff", MimeType.IMAGE_TIFF, StoredFileConverterTestFiles.TIFF_IMAGE_400X200);
		assertTrue(new StoredFileConverter(file).isConvertibleToPdf());
	}

	@Test
	public void testIsConvertibleToPdfWithTiffFileAndOctetStream() {

		AGStoredFile file = insertStoredFile("foo.tiff", MimeType.APPLICATION_OCTET_STREAM, StoredFileConverterTestFiles.TIFF_IMAGE_400X200);
		assertTrue(new StoredFileConverter(file).isConvertibleToPdf());
	}

	@Test
	public void testIsConvertibleToPdfWithTiffFileAndDifferentExtension() {

		AGStoredFile file = insertStoredFile("foo.xxx", MimeType.IMAGE_TIFF, StoredFileConverterTestFiles.TIFF_IMAGE_400X200);
		assertTrue(new StoredFileConverter(file).isConvertibleToPdf());
	}

	@Test
	public void testIsConvertibleToPdfWithUnidentifiedEmailFile() {

		AGStoredFile file = insertStoredFile("foo.xxx", MimeType.APPLICATION_OCTET_STREAM, EmailToPdfConverterTestFiles.EML_HTML);
		boolean convertibleToPdf = new StoredFileConverter(file).isConvertibleToPdf();
		assertFalse(convertibleToPdf);
	}

	@Test
	public void testIsConvertibleToPdfWithMisclassifiedFile() {

		assertTrue(new StoredFileConverter(misclassifiedEmlFile).isConvertibleToPdf());
	}

	@Test
	public void testIsConvertibleToPdfWithUnsupportedFile() {

		assertFalse(new StoredFileConverter(unsupportedFile).isConvertibleToPdf());
	}

	private AGStoredFile insertStoredFile(String filename, MimeType mimeType, IResourceSupplier resourceSupplier) {

		try (var inputStream = resourceSupplier.getResource().getResourceAsStream()) {
			var file = insertStoredFile(filename).setContentType(mimeType.getIdentifier()).save();
			file.uploadFileContent(inputStream);
			return file;
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private AGStoredFile insertStoredFile(String filename, IMimeType mimeType, byte[] bytes) {

		var file = insertStoredFile(filename).setContentType(mimeType.getIdentifier()).save();
		file.uploadFileContent(new ByteArrayInputStream(bytes));
		return file;
	}

	private static class Asserter {

		private final List<BufferedImage> pageImages;
		private int currentPage;

		public Asserter(Optional<byte[]> pdfBytes) {

			assertTrue(pdfBytes.isPresent());
			this.pageImages = new PdfRenderer().setDpi(FAST_RENDERING_DPI).render(new ByteArrayInputStream(pdfBytes.get()));
			this.currentPage = -1;
		}

		public Asserter nextPage() {

			this.currentPage++;
			return this;
		}

		public Asserter assertDimensions(int expectedImageWidth, int expectedImageHeight) {

			BufferedImage image = pageImages.get(currentPage);
			assertEquals(expectedImageWidth, image.getWidth());
			assertEquals(expectedImageHeight, image.getHeight());
			return this;
		}

		public Asserter assertPixels(Color expectedColor, int expectedPixelsWithColor) {

			BufferedImage image = pageImages.get(currentPage);
			long pixels = Images.countPixelsWithColor(image, expectedColor);
			assertTrue(
				String.format("%s is not greater or equal to %s", pixels, expectedPixelsWithColor - PIXEL_COUNT_TOLERANCE),
				pixels >= expectedPixelsWithColor - PIXEL_COUNT_TOLERANCE);
			assertTrue(
				String.format("%s is not less or equal to %s", pixels, expectedPixelsWithColor + PIXEL_COUNT_TOLERANCE),
				pixels <= expectedPixelsWithColor + PIXEL_COUNT_TOLERANCE);
			return this;
		}

		public Asserter assertNoMorePages() {

			assertEquals("Unexpected number of pages.", currentPage + 1, pageImages.size());
			return this;
		}
	}
}
