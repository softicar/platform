package com.softicar.platform.common.io.mime;

import com.softicar.platform.common.testing.Asserts;
import org.junit.Test;

public class MimeTypeParserTest extends Asserts {

	@Test
	public void test() {

		// test various types and sub-types
		assertMimeType("application/elm+json", "application/elm+json");
		assertMimeType("application/xliff+xml", "application/xliff+xml");
		assertMimeType("application/atsc-dwd+xml", "application/atsc-dwd+xml");
		assertMimeType("application/vnd.3gpp.access-transfer-events+xml", "application/vnd.3gpp.access-transfer-events+xml");

		// test parameters and whitespace
		assertMimeType("text/plain; charset=utf-8", "text/plain;charset=utf-8");
		assertMimeType("text/plain; charset=utf-8", "text/plain ;charset=utf-8");
		assertMimeType("text/plain; charset=utf-8", "text/plain; charset=utf-8");
		assertMimeType("text/plain; charset=utf-8", "text/plain ; charset = utf-8");

		// test multiple parameters
		assertMimeType("multipart/form-data; boundary=something", "multipart/form-data; boundary=something	");
		assertMimeType("text/html; bar=baz; charset=utf-8; foo=bar", "text/html;charset=utf-8;foo=bar;bar=baz");
	}

	private void assertMimeType(String expected, String input) {

		var mimeType = assertOne(new MimeTypeParser(input).parse());

		assertEquals(expected, mimeType.toString());
	}
}
