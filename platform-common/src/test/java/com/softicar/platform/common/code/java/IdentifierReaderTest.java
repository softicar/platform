package com.softicar.platform.common.code.java;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class IdentifierReaderTest extends AbstractTest {

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
