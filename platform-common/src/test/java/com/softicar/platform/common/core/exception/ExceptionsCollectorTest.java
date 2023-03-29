package com.softicar.platform.common.core.exception;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.common.testing.TextLinesAsserter;
import org.junit.Assert;
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
		generateExceptionsWithSameStacktrace(3);

		assertFalse(collector.isEmpty());
		assertEquals(4, collector.getExceptions().size());
		new TextLinesAsserter(collector.getMessage())//
			.assertLine("Preamble")
			.assertLine("Gathered 3 exceptions with the same stacktrace.")
			.assertLine("java.lang.RuntimeException: exception #0")
			.assertLine("java.lang.RuntimeException: exception #1")
			.assertLine("java.lang.RuntimeException: exception #2")
			.assertLine("Gathered 1 exceptions with the same stacktrace.")
			.assertLine("java.lang.RuntimeException: Foo");
		assertThrows(MultiException.class, collector::throwIfNotEmpty);
		assertThrows(collector.getMessage(), RuntimeException.class, () -> collector.applyIfNotEmpty(message -> {
			throw new RuntimeException(message);
		}));
	}

	@Test
	public void testWithEmptyCollector() {

		assertTrue(collector.isEmpty());
		assertEquals(0, collector.getExceptions().size());
		assertEquals("", collector.getMessage());
		collector.throwIfNotEmpty();
		collector.applyIfNotEmpty(message -> Assert.fail("not expected"));
	}

	private void generateExceptionsWithSameStacktrace(int count) {

		for (int i = 0; i < count; i++) {
			try {
				provokeException("exception #" + i);
			} catch (Exception exception) {
				collector.add(exception);
			}
		}
	}

	private void provokeException(String message) {

		try {
			throw new RuntimeException("inner");
		} catch (Exception exception) {
			throw new RuntimeException(message, exception);
		}
	}
}
