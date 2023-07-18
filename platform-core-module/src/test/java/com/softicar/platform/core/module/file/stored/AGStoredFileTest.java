package com.softicar.platform.core.module.file.stored;

import com.softicar.platform.common.io.mime.IMimeType;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import org.junit.Test;

public class AGStoredFileTest extends AbstractCoreTest {

	@Test
	public void testGetFilenameExtension() {

		assertEquals("txt", insertStoredFile("foo.txt").getFilenameExtension().get());
		assertEquals("txt", insertStoredFile("foo.txt ").getFilenameExtension().get());
		assertEquals("txt", insertStoredFile("foo. txt").getFilenameExtension().get());
		assertEquals("txt", insertStoredFile("FOO.TXT").getFilenameExtension().get());
		assertEquals("txt", insertStoredFile("foo.bar.txt").getFilenameExtension().get());
		assertEquals("txt", insertStoredFile(".txt").getFilenameExtension().get());

		assertTrue(insertStoredFile("foo.").getFilenameExtension().isEmpty());
		assertTrue(insertStoredFile("foo").getFilenameExtension().isEmpty());
		assertTrue(insertStoredFile(".").getFilenameExtension().isEmpty());
	}

	@Test
	public void testHasMimeType() {

		AGStoredFile txtFileWithTextPlain = insertStoredFile("foo.txt", MimeType.TEXT_PLAIN);
		AGStoredFile pngFileWithTextPlain = insertStoredFile("foo.png", MimeType.TEXT_PLAIN);

		assertTrue(txtFileWithTextPlain.hasMimeType(MimeType.TEXT_PLAIN));
		assertFalse(txtFileWithTextPlain.hasMimeType(MimeType.TEXT_CSS));

		assertTrue(pngFileWithTextPlain.hasMimeType(MimeType.TEXT_PLAIN));
	}

	@Test
	public void testHasFileNameExtension() {

		AGStoredFile txtFileWithTextPlain = insertStoredFile("foo.txt", MimeType.TEXT_PLAIN);
		AGStoredFile pngFileWithTextPlain = insertStoredFile("foo.png", MimeType.TEXT_PLAIN);

		assertTrue(txtFileWithTextPlain.hasFileNameExtension("txt"));
		assertTrue(txtFileWithTextPlain.hasFileNameExtension("TXT"));
		assertFalse(txtFileWithTextPlain.hasFileNameExtension("xxx"));

		assertTrue(pngFileWithTextPlain.hasFileNameExtension("png"));
	}

	@Test
	public void testHasFileNameExtensionWithBlankExtensions() {

		AGStoredFile txtFileWithTextPlain = insertStoredFile("foo.txt", MimeType.TEXT_PLAIN);

		assertException(IllegalArgumentException.class, () -> txtFileWithTextPlain.hasFileNameExtension(""));
		assertException(IllegalArgumentException.class, () -> txtFileWithTextPlain.hasFileNameExtension(" "));
	}

	@Test
	public void testHasMimeTypeOrExtension() {

		AGStoredFile txtFileWithTextPlain = insertStoredFile("foo.txt", MimeType.TEXT_PLAIN);
		AGStoredFile pngFileWithTextPlain = insertStoredFile("foo.png", MimeType.TEXT_PLAIN);
		AGStoredFile txtFileWithOctetStream = insertStoredFile("foo.txt", MimeType.APPLICATION_OCTET_STREAM);
		AGStoredFile unknownFileWithTextPlain = insertStoredFile("foo.xxx", MimeType.TEXT_PLAIN);
		AGStoredFile unknownFileWithOctetStream = insertStoredFile("foo.xxx", MimeType.APPLICATION_OCTET_STREAM);

		assertTrue(txtFileWithTextPlain.hasMimeType(MimeType.TEXT_PLAIN));
		assertTrue(pngFileWithTextPlain.hasMimeType(MimeType.TEXT_PLAIN));
		assertFalse(txtFileWithOctetStream.hasMimeType(MimeType.TEXT_PLAIN));
		assertTrue(unknownFileWithTextPlain.hasMimeType(MimeType.TEXT_PLAIN));
		assertFalse(unknownFileWithOctetStream.hasMimeType(MimeType.TEXT_PLAIN));
	}

	@Test
	public void testSetContentType() {

		assertEquals("image/png", new AGStoredFile().setContentType(MimeType.IMAGE_PNG).getContentType());
		assertEquals("application/octet-stream", new AGStoredFile().setContentType(MimeType.APPLICATION_OCTET_STREAM).getContentType());
		assertEquals("foo", new AGStoredFile().setContentType(IMimeType.createCustom("foo")).getContentType());
	}
}
