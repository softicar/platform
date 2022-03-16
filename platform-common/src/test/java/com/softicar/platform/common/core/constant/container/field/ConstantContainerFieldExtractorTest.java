package com.softicar.platform.common.core.constant.container.field;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;

public class ConstantContainerFieldExtractorTest extends Assert {

	@Test
	public void testWithStringSeveralMatches() {

		var fields = extractFields(ConstantContainerFieldExtractorTestContainerClass.class, String.class);
		expectFields(fields, "FOO_STRING_FIELD", "BAR_STRING_FIELD");
	}

	@Test
	public void testWithSingleMatch() {

		var fields = extractFields(ConstantContainerFieldExtractorTestContainerClass.class, Integer.class);
		expectFields(fields, "INTEGER_FIELD");
	}

	@Test
	public void testWithSingleMatchAndSearchForSuperType() {

		var fields = extractFields(ConstantContainerFieldExtractorTestContainerClass.class, Number.class);
		expectFields(fields, "INTEGER_FIELD");
	}

	@Test
	public void testWithoutMatch() {

		var fields = extractFields(ConstantContainerFieldExtractorTestContainerClass.class, Byte.class);
		expectNoFields(fields);
	}

	private <T> Collection<IConstantContainerField<T>> extractFields(Class<?> containerClass, Class<T> expectedFieldType) {

		return new ConstantContainerFieldExtractor<>(containerClass, expectedFieldType).extractFields();
	}

	private static <T> void expectNoFields(Collection<IConstantContainerField<T>> fields) {

		expectFields(fields, new String[0]);
	}

	private static <T> void expectFields(Collection<IConstantContainerField<T>> fields, String...expectedFieldNames) {

		Set<String> expectedNames = new TreeSet<>(Arrays.asList(expectedFieldNames));
		assertEquals("Unexpected number of fields.", expectedNames.size(), fields.size());

		Set<String> actualNames = fields.stream().map(IConstantContainerField::getName).collect(Collectors.toSet());

		Set<String> missingNames = difference(expectedNames, actualNames);
		assertTrue("Missing expected fields: " + missingNames, missingNames.isEmpty());

		Set<String> unexpectedNames = difference(actualNames, expectedNames);
		assertTrue("Encountered unexpected fields: " + unexpectedNames, unexpectedNames.isEmpty());
	}

	private static Set<String> difference(Set<String> first, Set<String> second) {

		return first.stream().filter(it -> !second.contains(it)).collect(Collectors.toSet());
	}
}
