package com.softicar.platform.common.core.java.classes.name.matcher;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class JavaClassNameGlobPatternMatcherTest extends AbstractTest {

	@Test
	public void testWithoutAsterisk() {

		String globPattern = "foo.Bar";

		assertMatch(globPattern, "foo.Bar");
		assertNoMatch(globPattern, "foo.Baz");
	}

	@Test
	public void testWithSingleAsterisk() {

		String globPattern = "foo.*.Bar";

		assertMatch(globPattern, "foo..Bar");
		assertMatch(globPattern, "foo.bar.Bar");

		assertNoMatch(globPattern, "foo.Bar");
		assertNoMatch(globPattern, "foo.bar.baz.Bar");
	}

	@Test
	public void testWithDoubleAsterisk() {

		String globPattern = "foo.**.Bar";

		assertMatch(globPattern, "foo..Bar");
		assertMatch(globPattern, "foo.bar.Bar");
		assertMatch(globPattern, "foo.bar.baz.Bar");

		assertNoMatch(globPattern, "foo");
		assertNoMatch(globPattern, "foo.Bar");
	}

	private static void assertMatch(String globPattern, String className) {

		assertTrue(matches(globPattern, className));
	}

	private static void assertNoMatch(String globPattern, String className) {

		assertFalse(matches(globPattern, className));
	}

	private static boolean matches(String globPattern, String className) {

		return new JavaClassNameGlobPatternMatcher(globPattern).test(new JavaClassName(className));
	}
}
