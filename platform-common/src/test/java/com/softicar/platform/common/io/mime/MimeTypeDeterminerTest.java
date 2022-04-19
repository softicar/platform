package com.softicar.platform.common.io.mime;

import com.softicar.platform.common.testing.AbstractTest;
import java.util.Optional;
import org.junit.Test;

public class MimeTypeDeterminerTest extends AbstractTest {

	@Test
	public void test() {

		assertMimeType(MimeType.TEXT_CSS, "foo.css");
		assertMimeType(MimeType.TEXT_JAVASCRIPT, "foo.js");
		assertMimeType(MimeType.TEXT_PLAIN, "foo.txt");
		assertMimeType(MimeType.TEXT_XML, "foo.xml");

		assertMimeType(MimeType.IMAGE_GIF, "foo.gif");
		assertMimeType(MimeType.IMAGE_PNG, "foo.png");
		assertMimeType(MimeType.IMAGE_JPEG, "foo.jpg");
		assertMimeType(MimeType.IMAGE_JPEG, "foo.jpeg");
		assertMimeType(MimeType.IMAGE_SVG_XML, "foo.svg");

		assertMimeType(MimeType.APPLICATION_PDF, "foo.pdf");
	}

	@Test
	public void testPathologicCases() {

		assertNoMimeType("");
		assertNoMimeType("png");
		assertNoMimeType("foo.png.");

		assertMimeType(MimeType.IMAGE_PNG, ".png");
		assertMimeType(MimeType.IMAGE_PNG, "foo.bar.PNG");
		assertMimeType(MimeType.IMAGE_PNG, "foo.bar.png");
	}

	private static void assertNoMimeType(String filename) {

		Optional<MimeType> mimeType = new MimeTypeDeterminer(filename).getMimeType();

		assertFalse(String.format("unexpected mime type determination for '%s'", filename), mimeType.isPresent());
	}

	private static void assertMimeType(MimeType expectedType, String filename) {

		Optional<MimeType> mimeType = new MimeTypeDeterminer(filename).getMimeType();

		assertTrue(String.format("failed mime type determination for '%s'", filename), mimeType.isPresent());
		assertEquals(expectedType, mimeType.get());
	}
}
