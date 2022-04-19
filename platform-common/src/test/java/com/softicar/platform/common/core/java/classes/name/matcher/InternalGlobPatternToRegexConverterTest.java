package com.softicar.platform.common.core.java.classes.name.matcher;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class InternalGlobPatternToRegexConverterTest extends AbstractTest {

	@Test
	public void testWithoutAsterisk() {

		assertEquals("foo", convert("foo"));
		assertEquals("foo\\.Bar", convert("foo.Bar"));
	}

	@Test
	public void testSingleAsterisk() {

		assertEquals("[^\\.]*", convert("*"));
		assertEquals("[^\\.]*\\.Bar", convert("*.Bar"));
		assertEquals("foo\\.[^\\.]*", convert("foo.*"));
		assertEquals("foo\\.[^\\.]*\\.Bar", convert("foo.*.Bar"));
	}

	@Test
	public void testDoubleAsterisk() {

		assertEquals(".*", convert("**"));
		assertEquals(".*\\.Bar", convert("**.Bar"));
		assertEquals("foo\\..*", convert("foo.**"));
		assertEquals("foo\\..*\\.Bar", convert("foo.**.Bar"));
	}

	@Test(expected = Exception.class)
	public void testWithEmptyPattern() {

		convert("");
	}

	@Test(expected = Exception.class)
	public void testWithIllegalCharacters() {

		convert("foo@bar");
	}

	private Object convert(String globPattern) {

		return new InternalGlobPatternToRegexConverter(globPattern).convert();
	}
}
