package com.softicar.platform.common.code;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class QualifiedNameParserTest extends org.junit.Assert {

	@Test
	public void testNormal() {

		test(Arrays.asList("foo"), "foo");
		test(Arrays.asList("foo", "bar"), "foo.bar");
		test(Arrays.asList("foo", "bar", "baz"), "foo.bar.baz");
	}

	@Test
	public void testDifferenDelimiters() {

		test(Arrays.asList("com", "example", "Foo"), "com.example.Foo", ".");
		test(Arrays.asList("com", "example", "Foo"), "com/example/Foo", "/");
		test(Arrays.asList("com", "example", "Foo"), "com...example...Foo", "...");
	}

	@Test
	public void testEmptySegments() {

		test(Arrays.asList(""), "");
		test(Arrays.asList("", "foo"), ".foo");
		test(Arrays.asList("foo", ""), "foo.");
		test(Arrays.asList("foo", "", "bar"), "foo..bar");
		test(Arrays.asList("foo", "", "", "bar"), "foo...bar");
		test(Arrays.asList("", "foo", "", "bar", ""), ".foo..bar.");
	}

	private void test(List<String> expectedSegments, String canonicalName) {

		List<String> segments = new QualifiedNameParser().parseSegments(canonicalName);
		assertArrayEquals(expectedSegments.toArray(), segments.toArray());
	}

	private void test(List<String> expectedSegments, String canonicalName, String delimiter) {

		List<String> segments = new QualifiedNameParser(delimiter).parseSegments(canonicalName);
		assertArrayEquals(expectedSegments.toArray(), segments.toArray());
	}
}
