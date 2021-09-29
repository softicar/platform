package com.softicar.platform.common.core.logging;

import static org.junit.Assert.assertSame;
import org.junit.After;
import org.junit.Test;

public class CurrentLogLevelTest {

	@After
	public void after() {

		CurrentLogLevel.reset();
	}

	@Test
	public void returnsTheInfoLevelByDefault() {

		CurrentLogLevel.set(null);
		assertSame(LogLevel.INFO, CurrentLogLevel.get());
	}

	@Test
	public void returnsThePreviouslySetLogLevel() {

		CurrentLogLevel.set(LogLevel.ERROR);
		assertSame(LogLevel.ERROR, CurrentLogLevel.get());

		CurrentLogLevel.set(LogLevel.PANIC);
		assertSame(LogLevel.PANIC, CurrentLogLevel.get());

		CurrentLogLevel.set(LogLevel.WARNING);
		assertSame(LogLevel.WARNING, CurrentLogLevel.get());
	}
}
