package com.softicar.platform.common.io.reader;

import com.softicar.platform.common.testing.AbstractTest;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.stream.Collectors;
import org.junit.Test;

public class BufferedReadersTest extends AbstractTest {

	@Test
	public void testReadLines() {

		assertEquals("", readLines(""));
		assertEquals("a", readLines("a"));
		assertEquals("a|b", readLines("a\r\nb"));
		assertEquals("a||b", readLines("a\n\nb"));
	}

	private String readLines(String text) {

		return BufferedReaders//
			.readLines(new BufferedReader(new StringReader(text)))
			.stream()
			.collect(Collectors.joining("|"));
	}

	@Test
	public void testReadLine() {

		BufferedReader reader = new BufferedReader(new StringReader("a\n\nb"));

		assertEquals("a", BufferedReaders.readLine(reader));
		assertEquals("", BufferedReaders.readLine(reader));
		assertEquals("b", BufferedReaders.readLine(reader));
		assertNull(BufferedReaders.readLine(reader));
	}
}
