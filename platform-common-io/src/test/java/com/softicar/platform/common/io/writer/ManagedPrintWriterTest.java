package com.softicar.platform.common.io.writer;

import org.junit.Assert;
import org.junit.Test;

public class ManagedPrintWriterTest extends Assert {

	private final StringBuilder output;

	public ManagedPrintWriterTest() {

		this.output = new StringBuilder();
	}

	@Test
	public void testPrintf() {

		new ManagedPrintWriter(output, "\n")//
			.printf("foo%s", "bar")
			.printf("%sbar", "foo");

		assertOutput("foobarfoobar");
	}

	@Test
	public void testPrintln() {

		new ManagedPrintWriter(output, "\r\n")//
			.println("foo%s", "bar")
			.println("%sbar", "foo");

		assertOutput("foobar\r\nfoobar\r\n");
	}

	@Test
	public void testPrintlnWithEmptyParameter() {

		new ManagedPrintWriter(output, "abc")//
			.println()
			.println();

		assertOutput("abcabc");
	}

	private void assertOutput(String expected) {

		assertEquals(expected, output.toString());
	}
}
