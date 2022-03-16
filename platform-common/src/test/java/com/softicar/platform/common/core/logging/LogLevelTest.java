package com.softicar.platform.common.core.logging;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import org.junit.Test;

public class LogLevelTest {

	@Test
	public void returnsCorrectPrioritiesOfLogLevels() {

		assertEquals(Log.PANIC_LEVEL, LogLevel.PANIC.getPriority());
		assertEquals(Log.ERROR_LEVEL, LogLevel.ERROR.getPriority());
		assertEquals(Log.WARNING_LEVEL, LogLevel.WARNING.getPriority());
		assertEquals(Log.INFO_LEVEL, LogLevel.INFO.getPriority());
		assertEquals(Log.VERBOSE_LEVEL, LogLevel.VERBOSE.getPriority());
	}

	@Test
	public void returnsCorrectLogLevelsForPriorities() {

		assertSame(LogLevel.PANIC, LogLevel.get(Log.PANIC_LEVEL));
		assertSame(LogLevel.ERROR, LogLevel.get(Log.ERROR_LEVEL));
		assertSame(LogLevel.WARNING, LogLevel.get(Log.WARNING_LEVEL));
		assertSame(LogLevel.INFO, LogLevel.get(Log.INFO_LEVEL));
		assertSame(LogLevel.VERBOSE, LogLevel.get(Log.VERBOSE_LEVEL));
	}

	@Test
	public void returnsCorrectLogLevelsNames() {

		for (LogLevel logLevel: LogLevel.values()) {
			assertSame(logLevel, LogLevel.get(logLevel.toString()));
		}
	}
}
