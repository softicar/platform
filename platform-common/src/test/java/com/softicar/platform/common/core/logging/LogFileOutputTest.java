package com.softicar.platform.common.core.logging;

import com.softicar.platform.common.testing.AbstractTest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Test;

public class LogFileOutputTest extends AbstractTest {

	@Test
	public void test() throws IOException {

		// setup
		Path tempFile = Files.createTempFile(getClass().getCanonicalName(), null);
		tempFile.toFile().deleteOnExit();

		// execute
		try (var logFileOutput = new LogFileOutput(tempFile)) {
			logFileOutput.logLine("foo");
			logFileOutput.logLine("bar");
		}

		// assert
		List<String> lines = Files.readAllLines(tempFile);
		assertEquals(2, lines.size());
		assertEquals("foo", lines.get(0));
		assertEquals("bar", lines.get(1));
	}
}
