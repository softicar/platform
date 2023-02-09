package com.softicar.platform.common.core.logging;

import com.softicar.platform.common.testing.AbstractTest;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.Test;

public class LogFileOutputTest extends AbstractTest {

	@Test
	public void test() throws IOException {

		// setup
		var file = Files.createTempFile(getClass().getCanonicalName(), null).toFile();
		file.deleteOnExit();

		// execute
		try (var logFileOutput = new LogFileOutput(file)) {
			logFileOutput.logLine("foo");
			logFileOutput.logLine("bar");
		}

		// assert
		List<String> lines = Files.readAllLines(file.toPath());
		assertEquals(2, lines.size());
		assertEquals("foo", lines.get(0));
		assertEquals("bar", lines.get(1));
	}
}
