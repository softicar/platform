package com.softicar.platform.common.testing;

import com.softicar.platform.common.string.formatting.StackTraceFormatting;
import org.junit.Test;

public class AssertionErrorMessageCollectorTest extends AbstractTest {

	private final AssertionErrorMessageCollector collector;

	public AssertionErrorMessageCollectorTest() {

		this.collector = new AssertionErrorMessageCollector();
	}

	@Test
	public void testThrowIfNecessaryWithoutMessages() {

		collector.throwIfNecessary();
	}

	@Test
	public void testThrowIfNecessaryWithSimpleMessage() {

		collector.add("foo");
		try {
			collector.throwIfNecessary();
		} catch (Throwable throwable) {
			assertTrue(StackTraceFormatting.getStackTraceAsString(throwable).contains("1 error"));
			assertTrue(AssertionError.class.isAssignableFrom(throwable.getClass()));
		}
	}

	@Test
	public void testThrowIfNecessaryWithSimpleMessages() {

		collector.add("foo");
		collector.add("bar");
		try {
			collector.throwIfNecessary();
		} catch (Throwable throwable) {
			assertTrue(StackTraceFormatting.getStackTraceAsString(throwable).contains("2 errors"));
			assertTrue(AssertionError.class.isAssignableFrom(throwable.getClass()));
		}
	}

	@Test
	public void testThrowIfNecessaryWithFormattedMessage() {

		collector.add("foo %s bar", "xxx");
		try {
			collector.throwIfNecessary();
		} catch (Throwable throwable) {
			assertTrue(StackTraceFormatting.getStackTraceAsString(throwable).contains("foo xxx bar"));
			assertTrue(AssertionError.class.isAssignableFrom(throwable.getClass()));
		}
	}
}
