package com.softicar.platform.common.core.logging;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class MultiLogOutputTest extends AbstractTest {

	@Test
	public void test() {

		var firstOutput = new Output();
		var secondOutput = new Output();
		var multiLogOutput = new MultiLogOutput(firstOutput, secondOutput);

		multiLogOutput.logLine("foo");
		multiLogOutput.logLine("bar");

		assertEquals("foo\nbar\n", firstOutput.getText());
		assertEquals("foo\nbar\n", secondOutput.getText());
	}

	private static class Output implements ILogOutput {

		private final StringBuilder lines;

		public Output() {

			this.lines = new StringBuilder();
		}

		@Override
		public void logLine(String line) {

			lines.append(line).append("\n");
		}

		public String getText() {

			return lines.toString();
		}
	}
}
