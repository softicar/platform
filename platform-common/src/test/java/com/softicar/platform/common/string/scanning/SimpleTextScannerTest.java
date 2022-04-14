package com.softicar.platform.common.string.scanning;

import com.softicar.platform.common.testing.AbstractTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * Test cases for {@link SimpleTextScanner}.
 *
 * @author Oliver Richers
 */
public class SimpleTextScannerTest extends AbstractTest {

	private final SimpleTextScanner scanner;
	private final Callback callback;
	private final Matcher matcher;
	protected final List<String> tokens = new ArrayList<>();

	public SimpleTextScannerTest() {

		this.callback = new Callback();
		this.matcher = new Matcher();
		this.scanner = new SimpleTextScanner(callback);
		this.scanner.addMatcher(matcher);
	}

	@Test
	public void splitsOnWhitespace() {

		scanner.scan("hello world");
		assertTokens("hello", "WS( )", "world");
	}

	@Test
	public void outputsAggregatedWhitespace() {

		scanner.scan("hello \n\tworld");
		assertTokens("hello", "WS( \n\t)", "world");
	}

	@Test
	public void outputsMatch() {

		matcher.addLiteral("**");
		scanner.scan("hello**world");
		assertTokens("hello", "MATCH(**)", "world");
	}

	@Test
	public void outputsConsecutiveKeysCorrectly() {

		matcher.addLiteral("**");
		scanner.scan("hello****world");
		assertTokens("hello", "MATCH(**)", "MATCH(**)", "world");
	}

	@Test
	public void prefersLongerKeysOverShorterKeys() {

		matcher.addLiteral("===");
		matcher.addLiteral("==");
		scanner.scan("hello====world");
		assertTokens("hello", "MATCH(===)", "=world");
	}

	@Test
	public void supportsWhitespaceAsKey() {

		matcher.addLiteral("\n\n");
		scanner.scan("hello\n\nworld");
		assertTokens("hello", "MATCH(\n\n)", "world");
	}

	@Test
	public void supportsMultipleScanCalls() {

		scanner.scan("hello world");
		scanner.scan("hello world");
		assertTokens("hello", "WS( )", "world", "hello", "WS( )", "world");
	}

	private void assertTokens(String...expectedTokens) {

		assertArrayEquals(expectedTokens, tokens.toArray());
	}

	protected class Callback implements ISimpleTextScannerCallback {

		@Override
		public void consumeNormalText(String text) {

			tokens.add(text);
		}

		@Override
		public void consumeWhitespace(String whitespace) {

			tokens.add(String.format("WS(%s)", whitespace));
		}
	}

	protected class Matcher extends AbstractLiteralMatcher {

		@Override
		protected void consumeLiteral(String literal) {

			tokens.add(String.format("MATCH(%s)", literal));
		}
	}
}
