package com.softicar.platform.common.io;

import com.softicar.platform.common.string.charset.Charsets;
import java.io.ByteArrayInputStream;
import org.junit.Assert;
import org.junit.Test;

public class StreamUtilsTest extends Assert {

	@Test
	public void testToString() {

		String text = "abcäöü";
		byte[] utf8 = text.getBytes(Charsets.UTF8);
		byte[] iso8859 = text.getBytes(Charsets.ISO_8859_15);

		assertEquals(text, StreamUtils.toString(new ByteArrayInputStream(utf8), Charsets.UTF8));
		assertEquals(text, StreamUtils.toString(new ByteArrayInputStream(iso8859), Charsets.ISO_8859_15));
	}
}
