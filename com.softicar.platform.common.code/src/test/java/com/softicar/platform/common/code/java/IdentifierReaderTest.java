package com.softicar.platform.common.code.java;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class IdentifierReaderTest {

	@Test
	public void stopsAtEndOfString() {

		assertEquals("test", read("test"));
	}

	@Test
	public void stopsAtWhitespace() {

		assertEquals("test", read("test  "));
	}

	@Test
	public void stopsAtOperator() {

		assertEquals("test", read("test+"));
	}

	@Test
	public void allowsDigits() {

		assertEquals("test1337", read("test1337  "));
	}

	@Test
	public void allowsUnderscore() {

		assertEquals("test_1337", read("test_1337  "));
	}

	private String read(String line) {

		return new IdentifierReader(line).read();
	}
}
