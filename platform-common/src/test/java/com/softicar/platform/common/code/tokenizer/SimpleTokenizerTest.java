package com.softicar.platform.common.code.tokenizer;

import com.softicar.platform.common.testing.AbstractTest;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class SimpleTokenizerTest extends AbstractTest {

	@Test
	public void testEmptySequence() {

		List<String> tokens = new SimpleTokenizer("").getTokens();
		assertTrue(tokens.isEmpty());
	}

	@Test
	public void testEmptyStringLiterals() {

		List<String> tokens = new SimpleTokenizer("'' ''").getTokens();
		assertEquals(2, tokens.size());
		assertEquals("", tokens.get(0));
		assertEquals("", tokens.get(1));
	}

	@Test
	public void testWhitespaceSequence() {

		List<String> tokens = new SimpleTokenizer("  \t \n  ").getTokens();
		assertTrue(tokens.isEmpty());
	}

	@Test
	public void testSingleToken() {

		List<String> tokens = new SimpleTokenizer("foo").getTokens();
		assertEquals(Arrays.asList("foo"), tokens);
	}

	@Test
	public void testTimmingOfTokens() {

		List<String> tokens = new SimpleTokenizer("  foo ").getTokens();
		assertEquals(Arrays.asList("foo"), tokens);
	}

	@Test
	public void testTwoTokens() {

		List<String> tokens = new SimpleTokenizer("foo bar").getTokens();
		assertEquals(Arrays.asList("foo", "bar"), tokens);
	}

	@Test
	public void testQuotingTokens() {

		List<String> tokens = new SimpleTokenizer("foo \"hello there\" bar").getTokens();
		assertEquals(Arrays.asList("foo", "hello there", "bar"), tokens);
	}

	@Test
	public void testEscapingInQuotingTokens() {

		List<String> tokens = new SimpleTokenizer("foo \"hello \\\"there\" bar").getTokens();
		assertEquals(Arrays.asList("foo", "hello \"there", "bar"), tokens);
	}
}
