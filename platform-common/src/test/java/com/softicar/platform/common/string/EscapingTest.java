package com.softicar.platform.common.string;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class EscapingTest extends AbstractTest {

	@Test
	public void testUnicodeControlCharacters() {

		assertEquals("Hello\\u2028World", Escaping.getEscaped("Hello\u2028World"));
		assertEquals("Hello\\u2029World", Escaping.getEscaped("Hello\u2029World"));
	}

	@Test
	public void testQuotes() {

		assertEquals("Hello\\'World\\'", Escaping.getEscaped("Hello'World'"));
		assertEquals("Hello\\\"World\\\"", Escaping.getEscaped("Hello\"World\""));
	}

	@Test
	public void testAsciiControlCharacters() {

		assertEquals("Hello\\bWorld", Escaping.getEscaped("Hello\bWorld"));
		assertEquals("Hello\\fWorld", Escaping.getEscaped("Hello\fWorld"));
		assertEquals("Hello\\nWorld", Escaping.getEscaped("Hello\nWorld"));
		assertEquals("Hello\\rWorld", Escaping.getEscaped("Hello\rWorld"));
		assertEquals("Hello\\tWorld", Escaping.getEscaped("Hello\tWorld"));
	}
}
