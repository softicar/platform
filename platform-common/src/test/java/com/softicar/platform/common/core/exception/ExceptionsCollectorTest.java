package com.softicar.platform.common.core.exception;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.testing.AbstractTest;
import java.util.List;
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

		assertEquals(4, collector.getExceptionCount());
		assertEquals(2, collector.getExceptionCollections().size());
		String message = collector.getMessage();
		assertTrue(message.startsWith("Preamble"));
		assertTrue(message.contains("Foo"));
	}

	@Test
	public void testWithEmptyCollector() {

		assertEquals(0, collector.getExceptionCount());
		assertEquals("", collector.getMessage());
	}

	private void generateExceptionsWithSameStacktrace(int count) {

		for (int i = 0; i < count; i++) {
			try {
				provokeException("exception #" + i);
			} catch (Exception exception) {
				Log.finfo(List.of(exception.getStackTrace()));
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
