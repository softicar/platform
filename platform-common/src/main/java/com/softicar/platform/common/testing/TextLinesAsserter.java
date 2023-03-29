package com.softicar.platform.common.testing;

import com.softicar.platform.common.string.Imploder;
import java.util.List;
import org.junit.Assert;

public class TextLinesAsserter {

	private final List<String> lines;
	private int currentLine;

	public TextLinesAsserter(String text) {

		this(List.of(text.split("\n")));
	}

	public TextLinesAsserter(List<String> lines) {

		this.lines = lines;
		this.currentLine = 0;
	}

	public TextLinesAsserter assertLine(String expectedLine) {

		// search for expected line
		for (int i = currentLine; i < lines.size(); ++i) {
			if (lines.get(i).equals(expectedLine)) {
				this.currentLine = i + 1;
				return this;
			}
		}

		// not found
		String remainingText = Imploder.implode(lines.subList(currentLine, lines.size()), "\n");
		String message = "Expected to find '%s' in the following:\n---\n%s\n---".formatted(expectedLine, remainingText);
		Assert.fail(message);
		return this;
	}
}
