package com.softicar.platform.common.core.exception;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class ExceptionsCollectorTest extends AbstractTest {

	private final ExceptionsCollector collector;

	public ExceptionsCollectorTest() {

		this.collector = new ExceptionsCollector();
	}

	@Test
	public void test() {

		collector.setPreamble("Preamble");
		collector.add(new RuntimeException("Foo"));

		String message = collector.getMessage();
		assertEquals(1, collector.getExceptionCount());
		assertTrue(message.startsWith("Preamble"));
		assertTrue(message.contains("Foo"));
	}

	@Test
	public void testWithEmptyCollector() {

		assertEquals(0, collector.getExceptionCount());
		assertEquals("", collector.getMessage());
	}
}
