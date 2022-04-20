package com.softicar.platform.common.io.resource.key;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class ResourceKeySuperMimeTypeTest extends AbstractTest {

	@Test
	public void testFromFilenameWithPng() {

		var superMimeType = fromFilename("foo.png");
		assertEquals("image", superMimeType.get());
	}

	@Test
	public void testFromFilenameWithCss() {

		var superMimeType = fromFilename("foo.css");
		assertEquals("text", superMimeType.get());
	}

	@Test
	public void testFromFilenameWithUnknownExtension() {

		var superMimeType = fromFilename("foo.unknown");
		assertEquals("application", superMimeType.get());
	}

	@Test
	public void testFromFilenameWithoutExtension() {

		var superMimeType = fromFilename("foo");
		assertEquals("application", superMimeType.get());
	}

	@Test
	public void testFromFilenameWithEmptyString() {

		var superMimeType = fromFilename("");
		assertEquals("application", superMimeType.get());
	}

	@Test(expected = NullPointerException.class)
	public void testFromFilenameWithNull() {

		fromFilename(null);
	}

	// -------------------------------- Internal -------------------------------- //

	private ResourceKeySuperMimeType fromFilename(String filename) {

		return ResourceKeySuperMimeType.fromFilename(filename);
	}
}
