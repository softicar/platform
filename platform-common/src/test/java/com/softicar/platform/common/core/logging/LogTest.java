package com.softicar.platform.common.core.logging;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LogTest extends AbstractTest {

	private static final int SOME_NUMBER = 100;
	private final LogBuffer buffer;

	public LogTest() {

		this.buffer = new LogBuffer();
	}

	@Before
	public void before() {

		CurrentLogOuput.set(buffer);
		CurrentLogLevel.reset();
	}

	@After
	public void after() {

		CurrentLogOuput.reset();
		CurrentLogLevel.reset();
	}

	@Test
	public void doesNotFormatTextWhenNoArgumentsGiven() {

		Log.finfo("foo%bar");
		assertEquals("foo%bar\n", buffer.toString());
	}

	@Test
	public void formatsGivenTextCorrectly() {

		Log.finfo("foo %s%%", SOME_NUMBER);
		assertEquals("foo " + SOME_NUMBER + "%\n", buffer.toString());
	}

	@Test
	public void ignoresMessagesCorrectlyWhenLogLevelIsSetToError() {

		LogLevel.ERROR.set();

		Log.error("error");
		Log.warning("warning");
		Log.info("info");
		Log.verbose("verbose");

		assertEquals("error\n", buffer.toString());
	}

	@Test
	public void ignoresMessagesCorrectlyWhenLogLevelIsSetToInfo() {

		LogLevel.INFO.set();

		Log.error("error");
		Log.warning("warning");
		Log.info("info");
		Log.verbose("verbose");

		assertEquals("error\nwarning\ninfo\n", buffer.toString());
	}
}
