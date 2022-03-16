package com.softicar.platform.common.code.string;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.junit.Test;

public class ClassStringLiteralAnalyzerTest {

	private Set<String> stringLiterals;

	public ClassStringLiteralAnalyzerTest() {

		stringLiterals = null;
	}

	@Test
	public void testClassWithFields() {

		analyze(TestClassWithFields.class);

		assertStrings(//
			"a",
			"b",
			"c",
			"d",
			"123456789");
	}

	@Test
	public void testClassWithMethods() {

		analyze(TestClassWithMethods.class);

		assertStrings(//
			"a",
			"b",
			"c",
			"d");
	}

	@Test
	public void testClassWithInnerClass() {

		analyze(TestClassWithInnerClass.class);

		assertStrings(//
			"a",
			"b",
			"c",
			"d",
			"e");
	}

	@Test
	public void testClassWithMutlipleInnerClasses() {

		analyze(TestClassWithMultipleInnerClasses.class);

		assertStrings(//
			"a",
			"b",
			"c",
			"d",
			"e",
			"f",
			"g",
			"h",
			"i");
	}

	private void analyze(Class<?> classToAnalyze) {

		this.stringLiterals = new TreeSet<>(
			new ClassStringLiteralAnalyzer()//
				.analyze(classToAnalyze)
				.getStringLiterals());
	}

	private void assertStrings(String...expectedStrings) {

		Set<String> expectedStringLiterals = Arrays
			.asList(expectedStrings)//
			.stream()
			.collect(Collectors.toCollection(TreeSet::new));

		Set<String> missing = getDifference(expectedStringLiterals, stringLiterals);
		Set<String> unexpected = getDifference(stringLiterals, expectedStringLiterals);
		String message = new StringBuilder()//
			.append("String mismatch.\n")
			.append(String.format("Missing strings: %s\n", missing))
			.append(String.format("Unexpected strings: %s", unexpected))
			.toString();
		if (!expectedStringLiterals.equals(stringLiterals)) {
			throw new AssertionError(message);
		}
	}

	private static <T> Set<T> getDifference(Set<T> left, Set<T> right) {

		Set<T> difference = new TreeSet<>(left);
		difference.removeAll(right);
		return difference;
	}
}
