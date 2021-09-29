package com.softicar.platform.common.string.title.capitalizer;

import org.junit.Assert;
import org.junit.Test;

public class TitleCapitalizerTest extends Assert {

	@Test
	public void testWithPreposition() {

		assertEquals("Foo To", capitalize("foo to"));
		assertEquals("To Foo", capitalize("to foo"));
		assertEquals("Foo to Bar", capitalize("foo to bar"));
	}

	@Test
	public void testWithWhitespace() {

		assertEquals("Foo to Bar", capitalize("  foo \t to \n bar "));
		assertEquals("A Bar", capitalize("  a bar"));
	}

	@Test
	public void testWithEmptyString() {

		assertEquals("", capitalize(""));
		assertEquals("", capitalize("  \t \n "));
	}

	private String capitalize(String string) {

		return new TitleCapitalizer(string).capitalize();
	}
}
