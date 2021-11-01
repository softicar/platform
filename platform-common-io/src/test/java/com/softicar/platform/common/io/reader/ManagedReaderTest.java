package com.softicar.platform.common.io.reader;

import java.io.StringReader;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;

public class ManagedReaderTest extends Assert {

	@Test
	public void testRead() {

		ManagedReader reader = new ManagedReader("foobar");

		char[] buffer = new char[3];
		reader.read(buffer);

		assertEquals("foo", String.valueOf(buffer));
	}

	@Test
	public void testReadAll() {

		assertEquals("test123", new ManagedReader(new StringReader("test123")).readAll());
	}

	@Test
	public void testReadLines() {

		assertEquals(
			"foo||bar",
			new ManagedReader(new StringReader("foo\n\nbar"))//
				.readLines()
				.stream()
				.collect(Collectors.joining("|")));
	}
}
