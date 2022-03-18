package com.softicar.platform.common.code.java.reflection;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.junit.Assert;
import org.junit.Test;

public class TypeParameterAnalyzerTest extends Assert {

	// -------------------------------- test non-parameterized types -------------------------------- //

	@Test
	public void testGetTypeParameterNameWithNonParameterizedType() {

		Field field = TestClass.getLongField();
		assertEquals(//
			"",
			createExtractor(field).getTypeParameterName());
	}

	@Test
	public void testHasExpectedTypeParameterWithNonParameterizedType() {

		Field field = TestClass.getLongField();
		assertFalse(createExtractor(field).hasExpectedTypeParameter(Long.class));
	}

	// -------------------------------- test non-nested parameterized types -------------------------------- //

	@Test
	public void testGetTypeParameterNameWithParameterizedType() {

		Field field = TestClass.getOptionalNumberField();
		assertEquals(//
			"java.lang.Number",
			createExtractor(field).getTypeParameterName());
	}

	@Test
	public void testHasExpectedTypeParameterWithParameterizedTypeAndExpectedSameClass() {

		Field field = TestClass.getOptionalNumberField();
		assertTrue(createExtractor(field).hasExpectedTypeParameter(Number.class));
	}

	@Test
	public void testHasExpectedTypeParameterWithParameterizedTypeAndExpectedSuperClass() {

		Field field = TestClass.getOptionalNumberField();
		assertFalse(createExtractor(field).hasExpectedTypeParameter(Serializable.class));
	}

	@Test
	public void testHasExpectedTypeParameterWithParameterizedTypeAndExpectedSubClass() {

		Field field = TestClass.getOptionalNumberField();
		assertFalse(createExtractor(field).hasExpectedTypeParameter(BigDecimal.class));
	}

	@Test
	public void testHasExpectedTypeParameterWithParameterizedTypeAndExpectedUnrelatedClass() {

		Field field = TestClass.getOptionalNumberField();
		assertFalse(createExtractor(field).hasExpectedTypeParameter(String.class));
	}

	// -------------------------------- test nested parameterized types -------------------------------- //

	@Test
	public void testGetTypeParameterNameWithNestedParameterizedType() {

		Field field = TestClass.getOptionalNumberSupplierField();
		assertEquals(//
			"java.util.function.Supplier<java.lang.Number>",
			createExtractor(field).getTypeParameterName());
	}

	@Test
	public void testHasExpectedTypeParameterWithNestedParameterizedTypeAndExpectedSameClass() {

		Field field = TestClass.getOptionalNumberSupplierField();
		assertTrue(createExtractor(field).hasExpectedTypeParameter(Supplier.class, Number.class));
	}

	@Test
	public void testHasExpectedTypeParameterWithNestedParameterizedTypeAndExpectedDifferentOuterClass() {

		Field field = TestClass.getOptionalNumberSupplierField();
		assertFalse(createExtractor(field).hasExpectedTypeParameter(Predicate.class, Number.class));
	}

	@Test
	public void testHasExpectedTypeParameterWithNestedParameterizedTypeAndExpectedDifferentInnerClass() {

		Field field = TestClass.getOptionalNumberSupplierField();
		assertFalse(createExtractor(field).hasExpectedTypeParameter(Supplier.class, String.class));
	}

	@Test
	public void testHasExpectedTypeParameterWithNestedParameterizedTypeAndExpectedUnrelatedClass() {

		Field field = TestClass.getOptionalNumberSupplierField();
		assertFalse(createExtractor(field).hasExpectedTypeParameter(String.class));
	}

	// -------------------------------- internal -------------------------------- //

	private TypeParameterAnalyzer createExtractor(Field field) {

		return new TypeParameterAnalyzer(field);
	}

	private static class TestClass {

		@SuppressWarnings("unused") public static final Long LONG_FIELD = 0l;
		@SuppressWarnings("unused") public final Optional<Number> optionalNumberField = Optional.empty();
		@SuppressWarnings("unused") public final Optional<Supplier<Number>> optionalNumberSupplierField = Optional.empty();

		public static Field getLongField() {

			try {
				return TestClass.class.getField("LONG_FIELD");
			} catch (NoSuchFieldException | SecurityException exception) {
				throw new RuntimeException(exception);
			}
		}

		public static Field getOptionalNumberField() {

			try {
				return TestClass.class.getField("optionalNumberField");
			} catch (NoSuchFieldException | SecurityException exception) {
				throw new RuntimeException(exception);
			}
		}

		public static Field getOptionalNumberSupplierField() {

			try {
				return TestClass.class.getField("optionalNumberSupplierField");
			} catch (NoSuchFieldException | SecurityException exception) {
				throw new RuntimeException(exception);
			}
		}
	}
}
