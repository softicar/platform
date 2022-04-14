package com.softicar.platform.common.core.logging;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.testing.AbstractTest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IgnoredPackagesForLoggingTest extends AbstractTest {

	private final LogBuffer buffer;

	public IgnoredPackagesForLoggingTest() {

		this.buffer = new LogBuffer();
	}

	@Before
	public void before() {

		CurrentLogLevel.set(LogLevel.VERBOSE);
		CurrentLogOuput.set(buffer);
	}

	@After
	public void after() {

		CurrentLogLevel.reset();
		CurrentLogOuput.reset();
	}

	@Test
	public void testEmptyIgnoredPackages() {

		IgnoredPackagesForLogging.get().clear();

		Log.verbose("FOO");

		String line = getFirstLogOutputLine();
		assertTrue(line.matches(String.format("^@[0-9]+ %s:[0-9]+: .*", Thread.class.getCanonicalName())));
	}

	@Test
	public void testIgnoredPackages() {

		IgnoredPackagesForLogging.get().clear();
		IgnoredPackagesForLogging.get().addPackagePrefix(Thread.class.getCanonicalName());
		IgnoredPackagesForLogging.get().addPackagePrefix(Log.class.getCanonicalName());

		Log.verbose("FOO");

		String line = getFirstLogOutputLine();
		assertTrue(line.matches(String.format("^@[0-9]+ %s:[0-9]+: .*", IgnoredPackagesForLoggingTest.class.getCanonicalName())));
	}

	private String getFirstLogOutputLine() {

		try {
			return new BufferedReader(new StringReader(buffer.toString())).readLine();
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}
}
